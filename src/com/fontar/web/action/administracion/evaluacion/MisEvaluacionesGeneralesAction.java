package com.fontar.web.action.administracion.evaluacion;

import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;


public class MisEvaluacionesGeneralesAction extends MisEvaluacionesAction {

	protected String hqlQueryString() {
		return 
			"select o																"+ 
			"from EvaluacionGeneralBean o 											"+ 
			"where o.id in (														"+
			"		select so.idObjeto												"+ 
			"		from SeguridadObjetoBean so										"+ 
			"		where															"+ 
			"			so.usuario = '$1' and										"+
			"			so.clase = '"+EvaluacionGeneralBean.class.getName()+"' and	"+
			"			so.accion = 'WF-EVALUACION-PROYECTO-CARGAR-RESULTADO'		"+
			"	) and 																"+
			"	o.bitacora.idSeguimiento is null									";
	}
}
