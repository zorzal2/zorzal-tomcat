package com.fontar.web.action.seguimientos.rendiciones.cf;

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
public class RendicionCuentasCFResumenAction extends RendicionCuentasResumenAction {
	protected BigDecimal getMontoContraparte(ActionForm form) throws Exception {
		return FormUtil.getBigDecimalValue(form,"montoTotal");
	}

	protected BigDecimal getMontoParte(ActionForm form) throws Exception {
		return BigDecimal.ZERO;
	}

	@Override
	protected void setMontos(PragmaDynaValidatorForm dyna, RendicionCuentasBean rendicion) {
		BigDecimal montoTotal = rendicion.getMontoTotal();
		if (montoTotal != null) {
			String strMontoTotal = montoTotal.toString();				
			dyna.set("montoTotal", strMontoTotal);				
		}
	}
}
