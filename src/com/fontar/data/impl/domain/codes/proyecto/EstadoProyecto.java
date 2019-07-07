package com.fontar.data.impl.domain.codes.proyecto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Proyecto
 */
public enum EstadoProyecto implements Enumerable {

	INICIADO("app.codes.proyecto.estado.iniciado"),
	SOL_ADM("app.codes.proyecto.estado.solicitudAdmision"),
	NO_ADMITIDO("app.codes.proyecto.estado.noAdmitido"),
	ADMITIDO("app.codes.proyecto.estado.admitido"),
	EVALUACION("app.codes.proyecto.estado.evaluacion"),
	CONT_COM("app.codes.proyecto.estado.controladoComision"),
	CONT_SEC("app.codes.proyecto.estado.controladoSecretaria"),
	CONT_DIR_EVAL("app.codes.proyecto.estado.controladoDirectorioEval"),
	PEND_ALIC("app.codes.proyecto.estado.pendienteAlicuota"),
	CONT_DIR_ADJ("app.codes.proyecto.estado.controladoDirectorioAdj"),
	POS_RECON("app.codes.proyecto.estado.posibilidadReconsideracion"),
	PED_RECON("app.codes.proyecto.estado.pedidoReconsideracion"),
	RECON("app.codes.proyecto.estado.reconsideracion"),
	ANAL_RECON("app.codes.proyecto.estado.analisisReconsideracion"),
	//FIXME: en principio no se usa.
	//APROBADO("app.codes.proyecto.estado.aprobado"),
	ADJUDICADO("app.codes.proyecto.estado.adjudicado"),
	//FIXME: en principio no se usa.
	//NO_ADJUDICADO("app.codes.proyecto.estado.noAdjudicado"),
	CONTRATO("app.codes.proyecto.estado.contrato"), 
	SEGUIMIENTO("app.codes.proyecto.estado.seguimiento"), 
	FINALIZADO("app.codes.proyecto.estado.finalizado"),
	CERRADO("app.codes.proyecto.estado.cerrado"),
	ANULADO("app.codes.proyecto.estado.anulado");

	private String descripcion;

	private EstadoProyecto(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
	/**
	 * Determina si el estado es
	 * @return
	 */
	public boolean esTerminal() {
		return 
				this.equals(CERRADO) 
			||  this.equals(FINALIZADO) 
			||  this.equals(ANULADO); 
	}
}
