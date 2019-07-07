package com.fontar.web.action.administracion.evaluacion;

import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;


public class MisEvaluacionesDeSeguimientoAction extends MisEvaluacionesAction {

	protected String hqlQueryString() {
		return 
			"select o																	"+ 
			"from EvaluacionSeguimientoBean o 											"+ 
			"where o.id in (															"+
			"		select so.idObjeto													"+ 
			"		from SeguridadObjetoBean so											"+ 
			"		where																"+ 
			"			so.usuario = '$1' and											"+
			"			so.clase = '"+EvaluacionSeguimientoBean.class.getName()+"' and	"+
			"			so.accion = 'WF-EVALUACION-SEGUIMIENTO-CARGAR-RESULTADO'		"+
			"	)																		";
	}
}
