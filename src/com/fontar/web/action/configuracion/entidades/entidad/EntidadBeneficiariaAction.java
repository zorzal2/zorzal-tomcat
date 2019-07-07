package com.fontar.web.action.configuracion.entidades.entidad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.TributariaDAO;
import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.codes.entidad.Categorizacion;
import com.fontar.data.impl.domain.codes.entidad.TipoEmpresa;
import com.fontar.data.impl.domain.codes.entidad.TipoNoEmpresa;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.EntidadBeneficiariaDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;

public class EntidadBeneficiariaAction extends GenericABMAction<EntidadBeneficiariaBean> {

	ConfiguracionServicio configuracionService;

	public void setConfiguracionService(ConfiguracionServicio configuracionService) {
		this.configuracionService = configuracionService;
	}

	public EntidadBeneficiariaAction(Class<EntidadBeneficiariaBean> type) {
		super(type);
	}

	

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
		Collection jurisdicciones = new ArrayList();
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));
		request.setAttribute("jurisdicciones", jurisdicciones);

		Collection tiposEmp = new ArrayList();
		tiposEmp = collectionHandler.getComboFiltro(TipoEmpresa.class);

		request.setAttribute("tipoEmp", tiposEmp);

		Collection tiposNoEmp = new ArrayList();
		tiposNoEmp = collectionHandler.getComboFiltro(TipoNoEmpresa.class);

		request.setAttribute("tipoNoEmp", tiposNoEmp);

		Collection tiposCat = new ArrayList();
		tiposCat = collectionHandler.getComboFormulario(Categorizacion.class, false);

		request.setAttribute("tipoCat", tiposCat);

		TributariaDAO tributariaDAO = (TributariaDAO) WebContextUtil.getBeanFactory().getBean("tributariaDao");
		Collection tributarias = new ArrayList();
		Long idTributaria = null;
		EntidadBeneficiariaDTO beneficiariaDTO = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
		if (beneficiariaDTO != null) {
			idTributaria = beneficiariaDTO.getIdTributaria();
		}
		tributarias.addAll(collectionHandler.getTributaria(tributariaDAO,idTributaria));
		request.setAttribute("tributarias", tributarias);
		// CiiuDAO ciiuDAO =
		// (CiiuDAO)WebContextUtil.getBeanFactory().getBean("ciiuDao");
		// Collection ciius = new ArrayList();
		//      
		// ciius.addAll(collectionHandler.getCiius(ciiuDAO));
		// request.setAttribute("ciius",ciius);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		setCollections(request);
		ActionMessages errors = getErrors(request);
		
		// si tengo errores en el form es que vengo de una validación NO exitosa
		if (errors.isEmpty()){
			SessionHelper.setEntidadBeneficiaria(request, null);
		}
		
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.dataEditar(mapping, form, request, response);
		
		setCollections(request);
		ActionMessages errors = getErrors(request);
		
		// si tengo errores en el form es que vengo de una validación NO exitosa
		if (errors.isEmpty()){
		
			EntidadBeneficiariaDTO entidadBeneficiariaDTO = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
	
			if (entidadBeneficiariaDTO == null) {
				// String id = request.getParameter("id");
				// if((id == null) || (id == "") || (id == "0")) {
				dataAgregar(mapping, form, request, response);
	
			}
			else {
				List entidades = new ArrayList();
				String[] idEntidades = entidadBeneficiariaDTO.getIdEntidades();
				String[] cuits = entidadBeneficiariaDTO.getCuits();
				String[] denominaciones = entidadBeneficiariaDTO.getDenominaciones();
				if (idEntidades != null) {
					for (int i = 0; i < entidadBeneficiariaDTO.getIdEntidades().length; i++) {
						String[] datos = { idEntidades[i], cuits[i], denominaciones[i] };
						entidades.add(datos);
					}
				}
				request.setAttribute("entidades", entidades);
	
	
				BeanUtils.setProperty(form, "id", entidadBeneficiariaDTO.getId());
				BeanUtils.setProperty(form, "tipo", entidadBeneficiariaDTO.getTipo());
	
				if (entidadBeneficiariaDTO.getTipo().equals("EMPRESA")) {
					BeanUtils.setProperty(form, "fechaInicioActividad", entidadBeneficiariaDTO.getFechaInicioActividad());
					BeanUtils.setProperty(form, "descEmpresa", entidadBeneficiariaDTO.getDescEmpresa());
					BeanUtils.setProperty(form, "numeroConstitucion", entidadBeneficiariaDTO.getNumeroConstitucion());
					BeanUtils.setProperty(form, "idCiiu", entidadBeneficiariaDTO.getIdCiiu());
					BeanUtils.setProperty(form, "codigoCategorizacion", entidadBeneficiariaDTO.getCodigoCategorizacion());
					BeanUtils.setProperty(form, "empleoPermanente", entidadBeneficiariaDTO.getEmpleoPermanente());
					BeanUtils.setProperty(form, "tipoEmpresa", entidadBeneficiariaDTO.getTipoEmpresa());
					setSelectors(form, request);
				}
				else {
					BeanUtils.setProperty(form, "tipoNoEmpresa", entidadBeneficiariaDTO.getTipoNoEmpresa());
				}
				BeanUtils.setProperty(form, "localizacion", entidadBeneficiariaDTO.getLocalizacionEconomica());
				BeanUtils.setProperty(form, "emerix", entidadBeneficiariaDTO.getEmerix());
				BeanUtils.setProperty(form, "idTributaria", entidadBeneficiariaDTO.getIdTributaria());
	
				SessionHelper.setEntidadBeneficiaria(request, entidadBeneficiariaDTO);
			}
		}
		else {
			List entidades = new ArrayList();
			String[] idEntidades = FormUtil.getStringArrayValue(form, "idEntidades");
			String[] cuits = FormUtil.getStringArrayValue(form, "cuits");
			String[] denominaciones = FormUtil.getStringArrayValue(form, "denominaciones");
			if (idEntidades != null) {
				for (int i = 0; i < idEntidades.length; i++) {
					String[] datos = { idEntidades[i], cuits[i], denominaciones[i] };
					entidades.add(datos);
				}
			}
			request.setAttribute("entidades", entidades);

		}
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EntidadBeneficiariaDTO dto = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
		if (dto == null) {
			dto = new EntidadBeneficiariaDTO();
		}
		BeanUtils.setProperty(dto, "id", request.getParameter("id"));
		dto.setTipo(BeanUtils.getProperty(form, "tipo"));

		if (BeanUtils.getProperty(form, "tipo").equals("EMPRESA")) {

			dto.setTipoEmpresa(BeanUtils.getProperty(form, "tipoEmpresa"));
			dto.setFechaInicioActividad(BeanUtils.getProperty(form, "fechaInicioActividad"));
			dto.setDescEmpresa(BeanUtils.getProperty(form, "descEmpresa"));
			String numero = BeanUtils.getProperty(form, "numeroConstitucion");
			if (numero != null) {
				dto.setNumeroConstitucion(new Integer(numero));
			}
			dto.setCodigoCategorizacion(BeanUtils.getProperty(form, "codigoCategorizacion"));
			dto.setIdCiiu(FormUtil.getLongValue(form, "idCiiu"));
			dto.setTxtCiiu(BeanUtils.getProperty(form, "txtCiiu"));
			dto.setEmpleoPermanente((EmpleoPermanenteDTO) ((PragmaDynaValidatorForm) form).get("empleoPermanente"));

			// if (BeanUtils.getProperty(form,"tipoEmpresa").equals("OTRO")) {
			// dto.setOtro(BeanUtils.getProperty(form,"otro"));
			// }
		}
		else {
			dto.setTipoNoEmpresa(BeanUtils.getProperty(form, "tipoNoEmpresa"));
		}
		dto.setLocalizacionEconomica((LocalizacionDTO) ((PragmaDynaValidatorForm) form).get("localizacion"));

		String[] idEntidades = FormUtil.getStringArrayValue(form, "idEntidades");
		String[] cuits = FormUtil.getStringArrayValue(form, "cuits");
		String[] denominaciones = FormUtil.getStringArrayValue(form, "denominaciones");

		dto.setIdEntidades(idEntidades);
		dto.setCuits(cuits);
		dto.setDenominaciones(denominaciones);
		dto.setEmerix(FormUtil.getStringValue(form, "emerix"));
		dto.setIdTributaria(FormUtil.getLongValue(form, "idTributaria"));
		SessionHelper.setEntidadBeneficiaria(request, dto);
		BeanUtils.setProperty(form, "windowClose", "true");
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		setCollections(request);
		super.validateGuardar(mapping, form, request, response, messages);

		if (!StringUtil.isEmpty(request.getParameter("numeroConstitucion"))) {
			if (GenericTypeValidator.formatInt(request.getParameter("numeroConstitucion")) == null) {
				addError(messages, "app.configuracion.entidad.numeroConstitucion");
				BeanUtils.setProperty(form, "numeroConstitucion", request.getParameter("numeroConstitucion"));
			}
		}
		
		if (BeanUtils.getProperty(form, "tipo").equals("EMPRESA")) {
			if (StringUtil.isEmpty(BeanUtils.getProperty(form, "tipoEmpresa"))) {
				addError(messages, "app.configuracion.entidad.emptySubTipo");
			}
		}
		if (BeanUtils.getProperty(form, "tipo").equals("NO_EMPRESA")) {
			if (StringUtil.isEmpty(BeanUtils.getProperty(form, "tipoNoEmpresa"))) {
				addError(messages, "app.configuracion.entidad.emptySubTipo");
			}
		}
		String[] idEntidades = FormUtil.getStringArrayValue(form, "idEntidades");

		if (idEntidades != null) {	
			for (int i = 0; i < idEntidades.length; i++) {
				for (int j = i+1; j < idEntidades.length; j++) {
					if (idEntidades[i].equals(idEntidades[j])) {
						addError(messages, "app.configuracion.entidad.composicion");
						BeanUtils.setProperty(form, "windowClose", "false");
					}
				}
			}
		}

	}

	/**
	 * Selectores la pantalla
	 */
	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {
		CiiuDAO ciiuDao = (CiiuDAO) WebContextUtil.getBeanFactory().getBean("ciiuDao");
		String idCiiu = BeanUtils.getProperty(form, "idCiiu");

		if (idCiiu != null && !idCiiu.equals("")) {
			CiiuBean ciiuBean = new CiiuBean();
			ciiuBean = (CiiuBean) ciiuDao.read(new Long(idCiiu));
			BeanUtils.copyProperty(form, "idCiiu", ciiuBean.getId());
			BeanUtils.copyProperty(form, "txtCiiu", ciiuBean.getNombre());
		}
	}

	@Override
	protected boolean useToken() {
		return false;
	}

	public ConfiguracionServicio getConfiguracionService() {
		return configuracionService;
	}

	@Override
	public ActionForward selector(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtil.isEmpty(request.getParameter("idEntidad"))) {
			super.selector(mapping,form,request,response);
		}
		else {
			String id = request.getParameter(SELECTED_OBJECT);
			Long  objectId = (!GenericValidator.isBlankOrNull(id))?GenericTypeValidator.formatLong(id) : null;
			Long  idEntidad = (!GenericValidator.isBlankOrNull(request.getParameter("idEntidad")))?GenericTypeValidator.formatLong(request.getParameter("idEntidad")) : null;
			request.setAttribute(SELECTOR_COLLECTION, this.coleccionSeleccion( objectId,idEntidad));
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
	/** 
	 * Debe sobreescribirse en caso de querer agregar a la coleccion
	 * el objeto seleccionado
	 ***/
	@SuppressWarnings("unchecked")
	protected  Collection coleccionSeleccion(Long objectId, Long Id){
		Collection collection = this.getServicio().getSeleccion(true,Id); 
		if(objectId!=null) //FIXME controlar que no este en la coleccion
			collection.add( this.getServicio().load( objectId));
		return collection;
	}
	
	
}