package com.fontar.web.action.configuracion.entidades.persona;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.Rol;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.util.Cuit;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.util.Sexo;
import com.fontar.util.Util;
import com.fontar.web.util.ExtensibleHtmlList;
import com.pragma.util.FormUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;

/**
 * 
 * @author ssanchez
 */
public class PersonaAction extends GenericABMAction<PersonaBean> {

	ConfiguracionServicio configuracionService;

	public void setConfiguracionService(ConfiguracionServicio configuracionService) {
		this.configuracionService = configuracionService;
	}

	public PersonaAction(Class<PersonaBean> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean useToken() {
		return false;
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);

		String target = request.getServletPath().replace("Agregar","Guardar");
		request.setAttribute("target",target);
		// request.setAttribute("localizacionVisible","display:none;");
		// if (!getErrors(request).isEmpty()) {
		//			
		// request.setAttribute("localizacionVisible","display:block;");
		// }
		// TODO Si esta volviendo por un error no debe correr esto

		ActionMessages errors = getErrors(request);
		//si tengo errores en el form es que vengo de una validación NO exitosa
		if (errors.isEmpty()){
			SessionHelper.setEvaluador(request, null);
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
		PersonaDTO personaDTO = configuracionService.obtenerPersona(new Long(id));
		if (personaDTO.getEsEvaluador()) {
			EvaluadorDTO evaluadorDTO = configuracionService.obtenerEvaluador(new Long(id));
			personaDTO.setEvaluadorDTO(evaluadorDTO);
		}
		BeanUtils.setProperty(form, "id", personaDTO.getId());
		BeanUtils.setProperty(form, "localizacion", personaDTO.getLocalizacion());
		BeanUtils.setProperty(form, "nombre", personaDTO.getNombre());
		BeanUtils.setProperty(form, "sexo", personaDTO.getSexo());
		BeanUtils.setProperty(form, "cuit", personaDTO.getCuit());
		BeanUtils.setProperty(form, "cargo", personaDTO.getCargo());
		BeanUtils.setProperty(form, "tituloGrado", personaDTO.getTituloGrado());
		BeanUtils.setProperty(form, "esEvaluador", personaDTO.getEsEvaluador());
		BeanUtils.setProperty(form, "activo", personaDTO.getActivo());
		BeanUtils.setProperty(form, "observacion", personaDTO.getObservacion());
		SessionHelper.setEvaluador(request, personaDTO);

	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
		Collection jurisdicciones = new ArrayList();
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));
		request.setAttribute("jurisdicciones", jurisdicciones);

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PersonaDTO dto = (PersonaDTO) SessionHelper.getEvaluador(request);
		if (dto == null) {
			dto = new PersonaDTO();

		}
		BeanUtils.setProperty(dto, "id", request.getParameter("id"));

		dto.setNombre(BeanUtils.getProperty(form, "nombre"));
		dto.setSexo(BeanUtils.getProperty(form, "sexo"));
		dto.setCuit(BeanUtils.getProperty(form, "cuit"));
		dto.setCargo(BeanUtils.getProperty(form, "cargo"));
		dto.setTituloGrado(BeanUtils.getProperty(form, "tituloGrado"));
		dto.setObservacion(BeanUtils.getProperty(form, "observacion"));
		dto.setActivo((FormUtil.getStringValue(form, "activo") == null || FormUtil.getStringValue(form, "activo").equals("")) ? "true"
				: FormUtil.getStringValue(form, "activo"));
		dto.setEsEvaluador(FormUtil.getBooleanValue(form, "esEvaluador"));
		if (dto.getEsEvaluador() == null) {
			dto.setEsEvaluador(false);
		}

		dto.setLocalizacion((LocalizacionDTO) ((PragmaDynaValidatorForm) form).get("localizacion"));
	
		configuracionService.savePersona(dto);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		setCollections(request);
		super.validateGuardar(mapping, form, request, response, messages);

		String cuit = FormUtil.getStringValue(form, "cuit");
		
		if(!Util.isBlank(cuit)) {
			if(!Cuit.esCuitDePersonaValido(cuit, Sexo.fromString(BeanUtils.getProperty(form, "sexo")))) {
				addError(messages, "app.error.cuitInvalid");
			} else {
				PersonaDTO personaConElMismoCuit = configuracionService.obtenerPersonaConCuit(cuit);
				
				if(personaConElMismoCuit!=null) {
					//Hay una persona con ese cuit
					Long idPersona = FormUtil.getLongValue(form, "id");
					if (idPersona == null || idPersona.equals(0) || !idPersona.equals(personaConElMismoCuit.getId())) {
						addError(messages, "app.configuracion.persona.existeCuit");
					}
				}
			}
		}
		
		Object esEva = FormUtil.getBooleanValue(form, "esEvaluador");
		if(esEva != null) {
			boolean esEvaluador = FormUtil.getBooleanValue(form, "esEvaluador");
			if(esEvaluador) {
				PersonaDTO dto = (PersonaDTO) SessionHelper.getEvaluador(request);
				if(dto == null || dto.getEvaluadorDTO() == null) { 
					addError(messages, "app.configuracion.persona.faltaDatoEvaluador");
				} else {
					if(dto.getEvaluadorDTO().getFechaIngreso() == null)
						addError(messages, "app.configuracion.persona.faltaDatoEvaluador");
				}
			}
		}
	}

	@Override
	protected ActionForward forwardGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return NavigationManager.getPreviousAction(request);
	}	
	
	//Retorna al punto en que fue accedida la creacion
	public ActionForward cancelar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return NavigationManager.getPreviousAction(request);
	}
	
	@Override
	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			configuracionService.deletePersona(new Long(request.getParameter("id")));
		} catch(ObjetoEnUsoException e) {
			ActionMessages errors = getErrors(request);
			StringBuilder error = new StringBuilder();
			error.append("#html:");
			error.append(ResourceManager.getErrorResource("app.configuracion.objetoEnUso.personas"));
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