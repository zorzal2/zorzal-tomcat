package com.fontar.web.action.configuracion.criterios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.AdministrarMatrizCriteriosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.dto.MatrizCriterioDTO;
import com.fontar.data.impl.domain.dto.MatrizCriterioItemDTO;
import com.fontar.util.Util;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.action.GenericABMAction;
/**
 * 
 * @author ssanchez
 */
public class MatrizCriteriosAction extends GenericABMAction<MatrizCriterioBean> {

	private AdministrarMatrizCriteriosServicio administrarMatrizCriteriosServicio;

	public void setAdministrarMatrizCriteriosServicio(AdministrarMatrizCriteriosServicio matrizCriterioServicio) {
		this.administrarMatrizCriteriosServicio = matrizCriterioServicio;
	}

	public MatrizCriteriosAction(Class<MatrizCriterioBean> type) {
		super(type);
	}
	
	@Override
	protected boolean useToken() {
		return false;
	}
	
	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
		
		ActionMessages errores = getErrors(request);
		if(!errores.isEmpty()) {
		// Esto es para volver a poblar el formulario con los datos que cargó
		// el usuario cuando la validación no es exitosa.				
		
			PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
			String[] idItem   = (String[]) dyna.get("idItem");
			String[] tipoItem = (String[]) dyna.get("tipoItem");
			String[] criterio = (String[]) dyna.get("criterio");
			String[] puntaje  = (String[]) dyna.get("puntaje");
			
			// TODO: no hay q perder el combo! se puede tomar el valor de String[] criterio
			
			int i = 0;			
			MatrizCriterioItemDTO itemDTO = null; 
			List criterioList = null;			
			criterioList = new ArrayList();
			
			if (criterio != null) {
				while (i < criterio.length) {
					itemDTO = new MatrizCriterioItemDTO();
					if(idItem[i] == "")
						itemDTO.setIdItem(new Long(0));
					else
						itemDTO.setIdItem(new Long(idItem[i]));
					itemDTO.setTipoItem(tipoItem[i]);
					itemDTO.setCriterio(criterio[i]);
					itemDTO.setPuntaje(puntaje[i]);
					
					criterioList.add(itemDTO);
					
					i++;
				}
				request.setAttribute("criterioList", criterioList);
			}	
		}
	}
	
	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
		
		String id = request.getParameter("id");
		
		MatrizCriterioDTO mc = administrarMatrizCriteriosServicio.obtenerMatrizCriterio(new Long(id));
		
		BeanUtils.setProperty(form, "id", mc.getIdMatrizCriterio());
		BeanUtils.setProperty(form, "activo", mc.getActivo());
		BeanUtils.setProperty(form, "denominacion", mc.getDenominacion());
		request.setAttribute("criterioList", mc.getCriterioList());
	}

	@Override
	protected void initGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
			BeanUtils.setProperty(form, "activo", true);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {

		super.validateGuardar(mapping, form, request, response, messages);
		String id = request.getParameter("id");
		
		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
		
		String   denominacion = (String)   dyna.get("denominacion");
		if(denominacion == null || denominacion == "") {
			addError(messages, "app.configuracion.criterio.faltaDenominacion");
		}
		
		
		String[] tipoItem     = (String[]) dyna.get("tipoItem");	
		String[] txtItem      = (String[]) dyna.get("criterio");
		String[] puntaje      = (String[]) dyna.get("puntaje");

		int i = 0;		
		
		if(id == null || id == "") {
			// No puede existir en la base una matriz con la denominacion ingresada
			if (administrarMatrizCriteriosServicio.existeMatrizConNombre(denominacion)) {		
				addError(messages, "app.configuracion.criterio.existeMatriz");
			}
		}
		
		// Debe ingresarse al menos un Criterio y una Categoria
		if ((tipoItem.length < 2) && messages.isEmpty())  {
			addError(messages, "app.configuracion.criterio.criterioYCategoria");			
		}
		
		while ((i < tipoItem.length) && messages.isEmpty()) {
			
			// Debe ingresar una descripción para todos los items de la matriz
			if (txtItem[i]=="") {
				addError(messages, "app.configuracion.criterio.faltaDescripcion");
			}
			
			// Todos los criterios ingresados deben tener por lo menos una categoría
			if (  tipoItem[i].equals("criterio") && 
				 (i == tipoItem.length-1 || tipoItem[i+1].equals("criterio"))) {				
				addError(messages, "app.configuracion.criterio.minItems");					
			}			
			
			// Todas las categorías deben tener un puntaje válido asociado
			if ( tipoItem[i].equals("categoria") && !Util.isPositiveInteger(puntaje[i])) 
			{
				addError(messages, "app.configuracion.criterio.puntaje");
			}
			i++;
		}
	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
		
		boolean activo = FormUtil.getBooleanValue(form, "activo");
		
		String denominacion	= (String)   dyna.get("denominacion");	// nombre para la Matriz de Criterios		
		String[] idItem 	= (String[]) dyna.get("idItem");
		String[] tipoItem 	= (String[]) dyna.get("tipoItem");	
		String[] txtItem 	= (String[]) dyna.get("criterio");
		String[] puntaje 	= (String[]) dyna.get("puntaje");
		
		administrarMatrizCriteriosServicio.updateDatosMatriz(id, activo, denominacion, idItem, tipoItem, txtItem, puntaje);
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estados", estadosEntidad);
	}

}