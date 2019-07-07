package com.fontar.web.action.seguimientos.evaluaciones.rendiciones.cf;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.web.action.seguimientos.evaluaciones.rendiciones.EditarMontoRendicionEvaluacionSeguimientoAction;
import com.pragma.util.FormUtil;

public class EditarMontoRendicionEvaluacionSeguimientoCFAction extends EditarMontoRendicionEvaluacionSeguimientoAction {

	protected void setMontoContraparteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception {
		rendicionCuentasBean.setMontoContraparteEvaluacion(FormUtil.getBigDecimalValue(form,"montoTotalEvaluacion"));
	}

	protected void setMontoParteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception {
		rendicionCuentasBean.setMontoParteEvaluacion(new BigDecimal(0.0));
	}

}
