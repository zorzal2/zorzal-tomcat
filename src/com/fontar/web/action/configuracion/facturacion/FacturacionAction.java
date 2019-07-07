package com.fontar.web.action.configuracion.facturacion;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.EntidadBeneficiariaDTO;
import com.fontar.data.impl.domain.dto.FacturacionDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericAction;

public class FacturacionAction extends GenericAction {

	// @SuppressWarnings("unchecked")
	// public FacturacionAction(Class type) {
	// super(type);
	// // TODO Auto-generated constructor stub
	// }

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EntidadBeneficiariaDTO entidadBeneficiariaDTO = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
		if (entidadBeneficiariaDTO == null) {
			entidadBeneficiariaDTO = new EntidadBeneficiariaDTO();
		}

		if (entidadBeneficiariaDTO.getFacturacionDTO() == null) {
			FacturacionDTO dto = new FacturacionDTO();
			entidadBeneficiariaDTO.setFacturacionDTO(dto);
		}

		// Object test = dyna.get("numeroFacturacion");
		// String[] strFac =
		// ((PragmaDynaValidatorForm)form).getStrings("numeroFacturacion");
		// String[] strPer =
		// ((PragmaDynaValidatorForm)form).getStrings("numeroPeriodico");

		String[] strNumeroFacturacion = FormUtil.getStringArrayValue(form, "numeroFacturacion");
		String[] strNumeroPeriodico = FormUtil.getStringArrayValue(form, "numeroPeriodico");

		entidadBeneficiariaDTO.getFacturacionDTO().setNumeroFacturacion(strNumeroFacturacion);
		entidadBeneficiariaDTO.getFacturacionDTO().setNumeroPeriodico(strNumeroPeriodico);
		if (entidadBeneficiariaDTO.getId() != null) {
			entidadBeneficiariaDTO.getFacturacionDTO().setIdEntidadBeneficiaria(entidadBeneficiariaDTO.getId());
		}

		SessionHelper.setEntidadBeneficiaria(request, entidadBeneficiariaDTO);
		BeanUtils.setProperty(form, "windowClose", "true");
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@Override
	protected boolean useToken() {
		return false;
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.dataEditar(mapping, form, request, response);
		ActionMessages errors = getErrors(request);
		// si tengo errores en el form es que vengo de una validación NO exitosa
		if (errors.isEmpty()){
			EntidadBeneficiariaDTO entidadBeneficiariaDTO = (EntidadBeneficiariaDTO) SessionHelper.getEntidadBeneficiaria(request);
			if (entidadBeneficiariaDTO != null) {
				FacturacionDTO dto = entidadBeneficiariaDTO.getFacturacionDTO();
				if (dto != null) {
	
					List facturaciones = new ArrayList();
					String[] numeroPeriodico = dto.getNumeroPeriodico();
					String[] numeroFacturacion = dto.getNumeroFacturacion();
					if (numeroPeriodico != null) {
						for (int i = 0; i < dto.getNumeroPeriodico().length; i++) {
						// FacturacionDTO facturacionDTO = new FacturacionDTO();
						// facturacionDTO.setId(dto.getId());
						// facturacionDTO.setIdEntidadBeneficiaria(dto.getIdEntidadBeneficiaria());
						// facturacionDTO.setNumeroFacturacion(facturacion[i]);
						// facturacionDTO.setNumeroPeriodico(periodico[i]);
						// facturaciones.add( facturacionDTO);
						// }
							String[] datos = { numeroPeriodico[i], numeroFacturacion[i] };
							facturaciones.add(datos);
						}
					}
					request.setAttribute("facturaciones", facturaciones);
	
					// request.setAttribute("numeroPeriodico",dto.getNumeroPeriodico());
					// request.setAttribute("numeroFacturacion",dto.getNumeroFacturacion());
	
					BeanUtils.setProperty(form, "id", dto.getId());
					BeanUtils.setProperty(form, "idEntidadBeneficiaria", dto.getIdEntidadBeneficiaria());
					// BeanUtils.setProperty(form, "numeroFacturacion",
					// dto.getNumeroFacturacion());
					// BeanUtils.setProperty(form, "numeroPeriodico",
					// dto.getNumeroPeriodico());
				}
			}
		}
		else {
			List facturaciones = new ArrayList();
			String[] numeroFacturacion = FormUtil.getStringArrayValue(form, "numeroFacturacion");
			String[] numeroPeriodico = FormUtil.getStringArrayValue(form, "numeroPeriodico");
			if (numeroPeriodico != null) {
				for (int i = 0; i < numeroPeriodico.length; i++) {
					String[] datos = { numeroPeriodico[i], numeroFacturacion[i] };
					facturaciones.add(datos);
				}
			}
			request.setAttribute("facturaciones", facturaciones);
		}
	}
	
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);

		String[] strNumeroPeriodico = FormUtil.getStringArrayValue(form, "numeroPeriodico");

		if (strNumeroPeriodico != null) {	
			for (int i = 0; i < strNumeroPeriodico.length; i++) {
				for (int j = i+1; j < strNumeroPeriodico.length; j++) {
					if (strNumeroPeriodico[i].equals(strNumeroPeriodico[j])) {
						addError(messages, "app.configuracion.entidad.anio");
						BeanUtils.setProperty(form, "windowClose", "false");
					}
				}
			}
		}
	}
}