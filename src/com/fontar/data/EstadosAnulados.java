package com.fontar.data;

import java.util.Set;

import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.presentacionConvocatoria.EstadoPresentacion;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente;
import com.pragma.util.CollectionUtils;
import com.pragma.util.StringUtil;

public abstract class EstadosAnulados {

	private static final Set<String> ESTADOS = CollectionUtils.setWith( 
			EstadoProyecto.ANULADO.getName(),
			EstadoIdeaProyecto.ANULADO.getName(),
			EstadoEvaluacion.ANULADA.getName(),
			EstadoPaquete.ANULADO.getName(),
			EstadoLlamadoConvocatoria.ANULADO.getName(),
			EstadoNotificacion.ANULADA.getName(),
			EstadoPresentacion.ANULADA.getName(),
			EstadoPacItem.ANULADO.getName(),
			EstadoSeguimiento.ANULADO.getName(),
			EstadoVentanillaPermanente.ANULADO.getName()
	);
	public static String getHQLStringAnulados(){
		return "'" + StringUtil.join(ESTADOS, "','") + "'";
	}

}
