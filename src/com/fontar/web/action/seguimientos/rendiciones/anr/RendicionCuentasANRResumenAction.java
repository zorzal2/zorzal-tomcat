package com.fontar.web.action.seguimientos.rendiciones.anr;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.web.action.seguimientos.rendiciones.RendicionCuentasResumenAction;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;

/**
 * 
 * @author gboaglio
 * 
 */
public class RendicionCuentasANRResumenAction extends RendicionCuentasResumenAction {
	protected BigDecimal getMontoContraparte(ActionForm form) throws Exception {
		return FormUtil.getBigDecimalValue(form,"montoContraparte");
	}

	protected BigDecimal getMontoParte(ActionForm form) throws Exception {
		return FormUtil.getBigDecimalValue(form,"montoParte");
	}

	@Override
	protected void setMontos(PragmaDynaValidatorForm dyna, RendicionCuentasBean rendicion) {
		BigDecimal montoParte = rendicion.getMontoParteRendicion();
		if (montoParte != null) {				
			String strMontoParte = montoParte.toString();
			dyna.set("montoParte", strMontoParte);
		}
		
		BigDecimal montoContraparte = rendicion.getMontoContraparteRendicion();
		if (montoContraparte != null) {
			String strMontoContraparte = montoContraparte.toString();				
			dyna.set("montoContraparte", strMontoContraparte);				
		}
	}
}
