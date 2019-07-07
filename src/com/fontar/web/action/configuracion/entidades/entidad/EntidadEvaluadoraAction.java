package com.fontar.web.action.configuracion.entidades.entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.EntidadBancariaDTO;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.action.GenericABMAction;

public class EntidadEvaluadoraAction extends GenericABMAction {

	public EntidadEvaluadoraAction(Class type) {
		super(type);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.dataAgregar(mapping, form, request, response);
	}

	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.dataCargar(mapping, form, request, response);
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntidadEvaluadoraDTO dto = null; // (EntidadEvaluadoraDTO)SessionHelper.getLocalizacion(request);
		if (dto != null) {

			BeanUtils.setProperty(form, "nroCBU", dto.getNroCBU());
			BeanUtils.setProperty(form, "nroCuenta", dto.getNroCuenta());
			BeanUtils.setProperty(form, "nombreBeneficiario", dto.getNombreBeneficiario());
			BeanUtils.setProperty(form, "idEntidadBancaria", dto.getIdEntidadBancaria());
			BeanUtils.setProperty(form, "entidadBancaria", dto.getEntidadBancaria());
		}
		// EntidadEvaluadoraDTO dto =
		// (EntidadEvaluadoraDTO)SessionHelper.getLocalizacion(request);
		// if (dto!= null) {
		//			
		// BeanUtils.setProperty(form,"nroCBU",dto.getNroCBU());
		// BeanUtils.setProperty(form,"nroCuenta",dto.getNroCuenta());
		// BeanUtils.setProperty(form,"nombreBeneficiario",dto.getNombreBeneficiario());
		// BeanUtils.setProperty(form,"idEntidadBancaria",dto.getIdEntidadBancaria());
		// BeanUtils.setProperty(form,"entidadBancaria",dto.getEntidadBancaria());
		// }
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// creo una instancia
		EntidadEvaluadoraDTO dto = new EntidadEvaluadoraDTO();

		// copio los datos del Form al Bean
		dto.setId(FormUtil.getLongValue(form, "id"));
		dto.setNroCBU(FormUtil.getStringValue(form, "nroCBU"));
		dto.setNroCuenta(FormUtil.getStringValue(form, "nroCuenta"));
		dto.setNombreBeneficiario(FormUtil.getStringValue(form, "nombreBeneficiario"));
		dto.setEntidadBancaria((EntidadBancariaDTO) ((PragmaDynaValidatorForm) form).get("entidadBancaria"));
		/**
		 * EntidadDAO entidadDAO =
		 * (EntidadDAO)WebContextUtil.getBeanFactory().getBean("entidadDao");
		 */
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);
	}

	
}