package com.fontar.data.impl.domain.dto;



/**
 * Dto para la tarea gestión de pago de un 
 * seguimiento.<br>  
 * Contiene datos de totales de rendiciones
 * de un seguimiento.<br>
 * @author llobeto
 */
public class SeguimientoGestionPagoDTO extends ResumenDeRendicionCompactoDTO {

	public SeguimientoGestionPagoDTO(ResumenDeRendicionCompactoDTO resumen) {
		super(	resumen.getTotalMontoParteSolicitado(), 
				resumen.getTotalMontoContraparteSolicitado(), 
				resumen.getTotalMontoTotalSolicitado(), 
				resumen.getTotalMontoParteAprobado(),
				resumen.getTotalMontoContraparteAprobado(), 
				resumen.getTotalMontoTotalAprobado(), 
				resumen.getTotalMontoParteGestionado(), 
				resumen.getTotalMontoContraparteGestionado(),
				resumen.getTotalMontoTotalGestionado());
	}
	private static final long serialVersionUID = 1L;

	private boolean habilitadoParaGestionar = true;
	private boolean habilitadoParaNoGestionar = true;
	private boolean habilitadoParaRevaluar = true;

	public boolean estaHabilitadoParaGestionar() {
		return habilitadoParaGestionar;
	}
	public void setHabilitadoParaGestionar(boolean habilitadoParaGestionar) {
		this.habilitadoParaGestionar = habilitadoParaGestionar;
	}
	public boolean estaHabilitadoParaNoGestionar() {
		return habilitadoParaNoGestionar;
	}
	public void setHabilitadoParaNoGestionar(boolean habilitadoParaNoGestionar) {
		this.habilitadoParaNoGestionar = habilitadoParaNoGestionar;
	}
	public boolean estaHabilitadoParaRevaluar() {
		return habilitadoParaRevaluar;
	}
	public void setHabilitadoParaRevaluar(boolean habilitadoParaRevaluar) {
		this.habilitadoParaRevaluar = habilitadoParaRevaluar;
	}
}