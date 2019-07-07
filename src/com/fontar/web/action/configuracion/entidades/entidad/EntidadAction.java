package com.fontar.web.action.configuracion.entidades.entidad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.ObjetoEnUsoException;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.Rol;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.codes.entidad.TipoEmpresa;
import com.fontar.data.impl.domain.codes.entidad.TipoNoEmpresa;
import com.fontar.data.impl.domain.dto.EntidadBeneficiariaDTO;
import com.fontar.data.impl.domain.dto.EntidadDTO;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.util.Cuit;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ExtensibleHtmlList;
import com.pragma.bus.BusinessException;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;
import com.pragma.web.messages.ErrorMessage;

/**
 * 
 * @author ssanchez
 */
public class EntidadAction extends GenericABMAction<EntidadBean> {

	ConfiguracionServicio configuracionService;

	public void setConfiguracionService(ConfiguracionServicio configuracionService) {
		this.configuracionService = configuracionService;
	}

	public EntidadAction(Class<EntidadBean> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
		
		String target = request.getServletPath().replace("Agregar","Guardar");
		request.setAttribute("target",target);
		
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

		String target = request.getServletPath().replace("Editar","Guardar");
		request.setAttribute("target",target);
		String id = request.getParameter("id");
		EntidadDTO entidadDTO = configuracionService.obtenerEntidad(new Long(id));
		BeanUtils.setProperty(form, "id", entidadDTO.getId());
		BeanUtils.setProperty(form, "denominacion", entidadDTO.getDenominacion());
		BeanUtils.setProperty(form, "cuit", entidadDTO.getCuit());
		BeanUtils.setProperty(form, "contacto", entidadDTO.getContacto());
		BeanUtils.setProperty(form, "descripcion", entidadDTO.getDescripcion());
		BeanUtils.setProperty(form, "beneficiaria", entidadDTO.getBeneficiaria());
		BeanUtils.setProperty(form, "evaluadora", entidadDTO.getEvaluadora());
		BeanUtils.setProperty(form, "bancaria", entidadDTO.getBancaria());
		if (entidadDTO.getLocalizacion() == null) {
			entidadDTO.setLocalizacion(new LocalizacionDTO());
		}
		BeanUtils.setProperty(form, "localizacion", entidadDTO.getLocalizacion());
		BeanUtils.setProperty(form, "activo", entidadDTO.getActivo());
		BeanUtils.setProperty(form, "entidadBeneficiaria", entidadDTO.getEntidadBeneficiaria());
		if (entidadDTO.getEvaluadora()) {
			BeanUtils.setProperty(form, "entidadEvaluadora", entidadDTO.getEntidadEvaluadora());
		}
		SessionHelper.setEntidadBeneficiaria(request, entidadDTO.getEntidadBeneficiaria());
	}

	// Cambio
	@Override
	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.dataVisualizar(mapping, form, request, response);
		// levanto el id de la entidad a visualizar
		setCollections(request);
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		EntidadDTO dto = configuracionService.obtenerEntidad(new Long(id));

		request.setAttribute("denominacion", dto.getDenominacion());
		request.setAttribute("cuit", dto.getCuit());
		request.setAttribute("contacto", dto.getContacto());
		request.setAttribute("descripcion", dto.getDescripcion());
		request.setAttribute("beneficiaria", dto.getBeneficiaria());
		request.setAttribute("evaluadora", dto.getEvaluadora());
		request.setAttribute("bancaria", dto.getBancaria());

		LocalizacionDTO localizacionDTO = dto.getLocalizacion();
		request.setAttribute("codigoPostal", localizacionDTO.getCodigoPostal());
		request.setAttribute("departamento", localizacionDTO.getDepartamento());
		request.setAttribute("direccion", localizacionDTO.getDireccion());
		request.setAttribute("email", localizacionDTO.getEmail());
		request.setAttribute("fax", localizacionDTO.getFax());
		request.setAttribute("nombreJurisdiccion", localizacionDTO.getNombreJurisdiccion());
		request.setAttribute("localidad", localizacionDTO.getLocalidad());
		request.setAttribute("paginaWeb", localizacionDTO.getPaginaWeb());
		request.setAttribute("pais", localizacionDTO.getPais());
		request.setAttribute("telefono", localizacionDTO.getTelefono());

		EntidadBeneficiariaDTO entidadBeneficiariaDTO = dto.getEntidadBeneficiaria();
		request.setAttribute("ciiu", entidadBeneficiariaDTO.getIdCiiu());
		request.setAttribute("codigoCategorizacion", entidadBeneficiariaDTO.getCodigoCategorizacion());
		request.setAttribute("descEmpresa", entidadBeneficiariaDTO.getDescEmpresa());
		request.setAttribute("empleoPermanente", entidadBeneficiariaDTO.getEmpleoPermanente());
		request.setAttribute("fechaInicioActividad", entidadBeneficiariaDTO.getFechaInicioActividad());
		request.setAttribute("id", entidadBeneficiariaDTO.getId());
		request.setAttribute("idCiiu", entidadBeneficiariaDTO.getIdCiiu());
		request.setAttribute("idEmpleoPermanente", entidadBeneficiariaDTO.getIdEmpleoPermanente());
		request.setAttribute("idLocalizacionEconomica", entidadBeneficiariaDTO.getIdLocalizacionEconomica());
		request.setAttribute("localizacionEconomica", entidadBeneficiariaDTO.getLocalizacionEconomica());
		request.setAttribute("numeroConstitucion", entidadBeneficiariaDTO.getNumeroConstitucion());
		request.setAttribute("tipo", entidadBeneficiariaDTO.getTipo());
		request.setAttribute("tipoEmpresa", entidadBeneficiariaDTO.getTipoEmpresa());
		request.setAttribute("tipoNoEmpresa", entidadBeneficiariaDTO.getTipoNoEmpresa());

		EntidadEvaluadoraDTO entidadEvaluadoraDTO = dto.getEntidadEvaluadora();
		request.setAttribute("nroCBU", entidadEvaluadoraDTO.getNroCBU());
		request.setAttribute("nroCuenta", entidadEvaluadoraDTO.getNroCuenta());
		request.setAttribute("nombreBeneficiario", entidadEvaluadoraDTO.getNombreBeneficiario());
		request.setAttribute("idEntidadBancaria", entidadEvaluadoraDTO.getIdEntidadBancaria());
		request.setAttribute("entidadBancaria", entidadEvaluadoraDTO.getEntidadBancaria());
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EntidadDTO dto = new EntidadDTO();

		BeanUtils.setProperty(dto, "id", request.getParameter("id"));

		dto.setDenominacion(BeanUtils.getProperty(form, "denominacion"));
		dto.setCuit(BeanUtils.getProperty(form, "cuit"));
		dto.setContacto(BeanUtils.getProperty(form, "contacto"));
		dto.setActivo((FormUtil.getStringValue(form, "activo") == null || FormUtil.getStringValue(form, "activo").equals("")) ? true
				: FormUtil.getBooleanValue(form, "activo"));
		dto.setDescripcion(BeanUtils.getProperty(form, "descripcion"));

		dto.setBeneficiaria(FormUtil.getBooleanValueNotNull(form, "beneficiaria"));
		dto.setEvaluadora(FormUtil.getBooleanValueNotNull(form, "evaluadora"));
		dto.setBancaria(FormUtil.getBooleanValueNotNull(form, "bancaria"));

		dto.setLocalizacion((LocalizacionDTO) ((PragmaDynaValidatorForm) form).get("localizacion"));
		dto.setEntidadEvaluadora((EntidadEvaluadoraDTO) ((PragmaDynaValidatorForm) form).get("entidadEvaluadora"));
		dto.setEntidadBeneficiaria((EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request));
		try {
			configuracionService.saveEntidad(dto);
		} catch(BusinessException e) {
			ActionMessages errors = this.getErrors( request );
			errors.add("business", new ErrorMessage(e.getMessage(), false));
		}
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		setCollections(request);
		super.validateGuardar(mapping, form, request, response, messages);

		List<EntidadBean> list;
		String cuit = FormUtil.getStringValue(form, "cuit");
		
		if(cuit!=null){
			//El CUIT puede ser para una entidad JURIDICA o FISICA, como no se sabe el sexo se pruab con los dos. 
			if(!Cuit.esCuitValido(cuit))
				addError(messages, "app.error.cuitInvalid");

				
			String idEntidad = request.getParameter("id");
	
			EntidadDAO entidadDAO = (EntidadDAO) getServicio().getDao();
	
			if (idEntidad == "" || idEntidad.equals("0")) {
				list = entidadDAO.findByCuit(cuit);
			}
			else {
				list = entidadDAO.findByCuitEntidad(new Long(idEntidad), cuit);
			}
			if (!list.isEmpty()) {
				addError(messages, "app.configuracion.entidad.existeCuit");
			}
		}		
		if (FormUtil.getBooleanValue(form, "beneficiaria") != null) {
			if (FormUtil.getBooleanValue(form, "beneficiaria")) {
				EntidadBeneficiariaDTO entidadBeneficiariaDTO = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
				if (entidadBeneficiariaDTO == null) {
					addError(messages, "app.configuracion.entidad.emptyEntidadBeneficiaria");
				} else {
					//chequeo mas restrictivo del cuit
					if(!StringUtil.isEmpty(cuit)) {
						if(
								TipoEmpresa.UNIPERSONAL.name().equals(entidadBeneficiariaDTO.getTipoEmpresa()) ||
								TipoNoEmpresa.PERSONA.name().equals(entidadBeneficiariaDTO.getTipoNoEmpresa())
						) {
							//El cuit debe ser de persona
							if(!Cuit.esCuitDePersonaValido(cuit)) {
								addError(messages, "app.configuracion.entidad.cuitNoConcuerda");
							}
						} else {
							//Si el tipo es OTRO se admite cualquier cuit valido, de otro modo debe ser de entidad
							if(!TipoEmpresa.OTRO.name().equals(entidadBeneficiariaDTO.getTipoEmpresa())) {
								//El cuit debe ser de una entidad
								if(!Cuit.esCuitDeEntidadValido(cuit)) {
									addError(messages, "app.configuracion.entidad.cuitNoConcuerda");
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
		Collection jurisdicciones = new ArrayList();
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));
		request.setAttribute("jurisdicciones", jurisdicciones);

		EntidadBancariaDAO entidadBancariaDAO = (EntidadBancariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBancariaDao");
		Collection entidadBancariaList = new ArrayList();
		entidadBancariaList.addAll(collectionHandler.getEntidadesBancarias(entidadBancariaDAO));
		request.setAttribute("entidadesBancarias", entidadBancariaList);

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}
	
	@Override
	protected ActionForward forwardGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return NavigationManager.getPreviousAction(request);
	}

	@Override
	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long idEntidad = new Long(request.getParameter("id"));
		
		try {
			configuracionService.deleteEntidad(idEntidad);
		} catch(ObjetoEnUsoException e) {
			ActionMessages errors = getErrors(request);
			StringBuilder error = new StringBuilder();
			error.append("#html:");
			error.append(ResourceManager.getErrorResource("app.configuracion.objetoEnUso.entidades"));
			error.append("<br/><br/><table class=recuadro>");
			for(Entry<Rol, Collection<String>> uso : e.getUsos().entrySet()) {
				Rol rol = uso.getKey();
				error.append("<tr><td>");
				
				ExtensibleHtmlList htmlList = new ExtensibleHtmlList(ResourceManager.getErrorResource("app.configuracion.objetoEnUso.rol."+rol.name()));
				htmlList.addAll(uso.getValue());
				
				error.append(htmlList);
				
				error.append("</td></tr>");
			}
			error.append("</table>");
			addLiteralError(errors, error.toString());
			saveErrors(request, errors);
		}
	}
}