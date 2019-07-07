package com.fontar.web.action.configuracion.seguridad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.configuracion.GrupoService;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.dao.ldap.NotInFilter;
import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class AdministracionGruposAction extends BaseMappingDispatchAction {

	
	 
	
	private GrupoService grupoService;
	
	
	private CollectionHandler collectionHandler = new CollectionHandler();
	
	public GrupoService getGrupoService() {
		return grupoService;
	}


	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}


	

	
	/** 
	 * Inicializa los datos necesarios para la carga de un nuevo grupo
	 * **/
	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Permisos seleccionables
		//String[] selectedItems =  FormUtil.getStringArray(form, "permisosSeleccionados"); 
		Grupo grupo = null;
		CheckBoxControl control = new CheckBoxControl(new String[]{}, this.grupoService.getPermissionsAsignable(grupo));
		request.setAttribute("permisos",control.getSelectableItems());
		
		//Instrumentos seleccionables
		request.setAttribute("instrumentos", this.collectionHandler.getInstrumentosConfiguracion() );
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String idGrupo = FormUtil.getStringValue(form,"id");
		String nombre = FormUtil.getStringValue(form,"nombre");
		String[] permisosSeleccionados = FormUtil.getStringArray(form,"permisosSeleccionados");
		
		//edicion
		if(idGrupo !=null){
			this.grupoService.update(idGrupo, nombre, permisosSeleccionados);
		}else{
			//Nuevo
			String instrumento  = FormUtil.getStringValue(form,"instrumento");
			Long lInstrumento=null;
			if(instrumento!=null) {
				lInstrumento = new Long(instrumento);
			}
			this.grupoService.create(nombre, lInstrumento,permisosSeleccionados);
		}
		return mapping.findForward("success");
	}

	
	
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String  grupo = request.getParameter("id");
		if( grupo!=null)
			this.grupoService.delete( grupo );
		return mapping.findForward("inventario");
				
	}

	public ActionForward borrarInstrumentable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String  grupo = request.getParameter("id");
		if( grupo!=null)
			this.grupoService.deleteInstrumentable( grupo );
		return mapping.findForward("inventario");
				
	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		
		//Creacion
		if(id==null)
			return this.agregar(mapping,form,request,response);
		
		GrupoAbstracto grupo = this.grupoService.getGrupo( id );
		
		CheckBoxControl control;
		if(!this.getErrors(request).isEmpty()){
			String[] permisosSeleccionados = FormUtil.getStringArray(form,"permisosSeleccionados");
			control = new CheckBoxControl( permisosSeleccionados, this.grupoService.getPermissionsAsignable(grupo));
		}else
			control = new CheckBoxControl( grupo.getPermisos(), this.grupoService.getPermissionsAsignable(grupo));
		request.setAttribute("permisos",control.getSelectableItems());
		BeanUtils.setProperty(form,"nombre",grupo.getNombre());
		
		request.setAttribute("grupo", new VisualizarGrupoDTO( grupo ) );
		return mapping.findForward("success");
	}

	
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String  grupo  = request.getParameter("id");
		request.setAttribute("grupo", new VisualizarGrupoDTO( this.grupoService.findByPrimaryKey( grupo ) ));
		return mapping.findForward("visualizar");
	}
	
	public ActionForward visualizarInstrumentable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String  grupo  = request.getParameter("id");
		request.setAttribute("grupo", new VisualizarGrupoDTO( this.grupoService.findInstrumentableByPrimaryKey( grupo ) ));
		return mapping.findForward("visualizar");
	}
	
	public ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("grupos", this.grupoService.findAll());
		return mapping.findForward("inventario");
	}

	
	public ActionForward selector(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] selectedGroups = request.getParameter("selectedGroups").split(",");
		NotInFilter filter = new NotInFilter( selectedGroups );
		request.setAttribute("grupos", this.grupoService.filter( filter ) );
		return mapping.findForward("inventario");
	}
}
