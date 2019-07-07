package com.fontar.web.form.administracion.proyecto;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.fontar.util.ResourceManager;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;

public class CompletarDatosInicialesDynaForm extends PragmaDynaValidatorForm {

	private static final long serialVersionUID = 1L;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors actionErrors = super.validate(mapping, request);
		boolean aplicaAlicuotaCF;
		try {
			aplicaAlicuotaCF = FormUtil.getBooleanValueNotNull(this, "aplicaCargaAlicuotaCF");
		}
		catch (Exception e) {
			return actionErrors;
		}
		if(aplicaAlicuotaCF) {
			//Debe estar al menos el campo de alicuota adjudicada
			try {
				if(FormUtil.getBigDecimalValue(this, "porcentajeAlicuotaAdjudicada")==null) {
					actionErrors.add("porcentajeAlicuotaAdjudicada", new ActionMessage(ResourceManager.getErrorResource("app.proyectoHistorico.cargaDeDatosIniciales.requiereAlicuotaAdjudicada"), false));
				}
			}
			catch (Exception e) {
				actionErrors.add("porcentajeAlicuotaAdjudicada", new ActionMessage(ResourceManager.getErrorResource("app.proyectoHistorico.cargaDeDatosIniciales.requiereAlicuotaAdjudicada"), false));
			}
		}
		return actionErrors;
	}
	public void setAplicaCargaAlicuotaCF(Boolean bool) {
		this.set("aplicaCargaAlicuotaCF", bool);
	}
}
