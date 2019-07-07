package com.fontar.web.action.seguimientos.evaluaciones.rendiciones.anr;

import org.apache.struts.action.ActionForm;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.web.action.seguimientos.evaluaciones.rendiciones.EditarMontoRendicionEvaluacionSeguimientoAction;
import com.pragma.util.FormUtil;

public class EditarMontoRendicionEvaluacionSeguimientoANRAction extends EditarMontoRendicionEvaluacionSeguimientoAction {

	protected void setMontoContraparteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception {
		rendicionCuentasBean.setMontoContraparteEvaluacion(FormUtil.getBigDecimalValue(form,"montoContraparteEvaluacion"));
	}

	protected void setMontoParteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception {
		rendicionCuentasBean.setMontoParteEvaluacion(FormUtil.getBigDecimalValue(form,"montoParteEvaluacion"));
	}

}
