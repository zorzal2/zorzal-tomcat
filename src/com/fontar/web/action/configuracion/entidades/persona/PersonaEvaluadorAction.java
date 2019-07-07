package com.fontar.web.action.configuracion.entidades.persona;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.data.api.dao.EntidadEvaluadoraDAO;
import com.fontar.data.api.dao.EspecialidadEvaluadorDAO;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;

public class PersonaEvaluadorAction extends GenericABMAction<EvaluadorBean> {

	ConfiguracionServicio configuracionService;

	public void setConfiguracionService(ConfiguracionServicio configuracionService) {
		this.configuracionService = configuracionService;
	}

	public PersonaEvaluadorAction(Class<EvaluadorBean> type) {
		super(type);
	}

	

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PersonaDTO personaDto = (PersonaDTO) SessionHelper.getEvaluador(request);
		if (personaDto == null) {
			// String id = request.getParameter("id");
			// if((id == null) || (id == "") || (id == "0")) {
			dataAgregar(mapping, form, request, response);

		}
		else {
			request.setAttribute("nombre", personaDto.getNombre());
			EvaluadorDTO dto = personaDto.getEvaluadorDTO();
			if (dto != null) {
				BeanUtils.setProperty(form, "id", dto.getId());
				BeanUtils.setProperty(form, "fechaIngreso", dto.getFechaIngreso());
				BeanUtils.setProperty(form, "idEntidadEvaluadora", dto.getIdEntidadesDesemp());
				BeanUtils.setProperty(form, "tituloPosgrado", dto.getTituloPosgrado());
				BeanUtils.setProperty(form, "idPrimaria", dto.getIdEspecialidadPrimaria());
				BeanUtils.setProperty(form, "idSecundaria", dto.getIdEspecialidadSecundaria());

				setSelectors(form, request);
			}
		}
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PersonaDTO personaDTO = (PersonaDTO) SessionHelper.getEvaluador(request);
		if (personaDTO == null) {
			personaDTO = new PersonaDTO();
		}
		EvaluadorDTO evaluadorDTO = new EvaluadorDTO();
		personaDTO.setEvaluadorDTO(evaluadorDTO);
		personaDTO.getEvaluadorDTO().setFechaIngreso(FormUtil.getStringValue(form, "fechaIngreso"));
		personaDTO.getEvaluadorDTO().setIdEntidadesDesemp(FormUtil.getLongValue(form, "idEntidadEvaluadora"));
		personaDTO.getEvaluadorDTO().setTxtEntidadesDesemp(FormUtil.getStringValue(form, "txtEntidadEvaluadora"));
		personaDTO.getEvaluadorDTO().setTituloPosgrado(FormUtil.getStringValue(form, "tituloPosgrado"));
		personaDTO.getEvaluadorDTO().setIdEspecialidadPrimaria(FormUtil.getLongValue(form, "idPrimaria"));
		personaDTO.getEvaluadorDTO().setTxtEspecialidadPrimaria(FormUtil.getStringValue(form, "txtPrimaria"));
		personaDTO.getEvaluadorDTO().setIdEspecialidadSecundaria(FormUtil.getLongValue(form, "idSecundaria"));
		personaDTO.getEvaluadorDTO().setTxtEspecialidadSecundaria(FormUtil.getStringValue(form, "txtSecundaria"));
		if (personaDTO.getId() != null) {
			personaDTO.getEvaluadorDTO().setId(personaDTO.getId());
			personaDTO.setEsEvaluador(true);
		}
		SessionHelper.setEvaluador(request, personaDTO);
		BeanUtils.setProperty(form, "windowClose", "true");
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	public ConfiguracionServicio getConfiguracionService() {
		return configuracionService;
	}

	/**
	 * Selectores la pantalla
	 */
	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {
		EspecialidadEvaluadorDAO especialidadEvaluadorDao = (EspecialidadEvaluadorDAO) WebContextUtil.getBeanFactory().getBean("especialidadEvaluadorDao");

		String idEspecialidadPrimaria = BeanUtils.getProperty(form, "idPrimaria");
		if (idEspecialidadPrimaria != null && !idEspecialidadPrimaria.equals("")) {
			EspecialidadEvaluadorBean especialidadEvaluadorBean = new EspecialidadEvaluadorBean();
			especialidadEvaluadorBean = (EspecialidadEvaluadorBean) especialidadEvaluadorDao.read(new Long(idEspecialidadPrimaria));
			BeanUtils.copyProperty(form, "idPrimaria", especialidadEvaluadorBean.getId());
			BeanUtils.copyProperty(form, "txtPrimaria", especialidadEvaluadorBean.getNombre());
		}

		String idEspecialidadSecundaria = BeanUtils.getProperty(form, "idSecundaria");
		if (idEspecialidadSecundaria != null && !idEspecialidadSecundaria.equals("")) {
			EspecialidadEvaluadorBean especialidadEvaluadorBean = new EspecialidadEvaluadorBean();
			especialidadEvaluadorBean = (EspecialidadEvaluadorBean) especialidadEvaluadorDao.read(new Long(idEspecialidadSecundaria));
			BeanUtils.copyProperty(form, "idSecundaria", especialidadEvaluadorBean.getId());
			BeanUtils.copyProperty(form, "txtSecundaria", especialidadEvaluadorBean.getNombre());
		}

		EntidadEvaluadoraDAO entidadEvaluadoraDao = (EntidadEvaluadoraDAO) ContextUtil.getBean("entidadEvaluadoraDao");
		String idEntidadEvaluadora = BeanUtils.getProperty(form, "idEntidadEvaluadora");
		if (idEntidadEvaluadora != null && !idEntidadEvaluadora.equals("")) {
			EntidadEvaluadoraBean entidadEvaluadoraBean = new EntidadEvaluadoraBean();
			entidadEvaluadoraBean = (EntidadEvaluadoraBean) entidadEvaluadoraDao.read(new Long(idEntidadEvaluadora));
			BeanUtils.copyProperty(form, "idEntidadEvaluadora", entidadEvaluadoraBean.getId());
			BeanUtils.copyProperty(form, "txtEntidadEvaluadora", entidadEvaluadoraBean.getDenominacion());
		}
	}

	@Override
	protected boolean useToken() {
		return false;
	}

}