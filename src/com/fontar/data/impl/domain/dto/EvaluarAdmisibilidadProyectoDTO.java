package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;

/**
 * Dto de admisibilidad de un proyecto
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */
public class EvaluarAdmisibilidadProyectoDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String instrumento;

	private String txtEntidadBeneficiaria;
	
	private String resolucion;

	private EstadoProyecto estado;

	private Boolean estaEnReconsideracion;

	private Boolean estaEnPaquete;

	private String motivoCierre;
	
	public String getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(String motivoCierre) {
		if (com.fontar.util.Util.isBlank(motivoCierre)) {
			this.motivoCierre = motivoCierre;
		}
		else {
			this.motivoCierre = MotivoCierre.valueOf(motivoCierre).getDescripcion();
		}
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public Boolean getEstaEnPaquete() {
		return estaEnPaquete;
	}

	public void setEstaEnPaquete(Boolean estaEnPaquete) {
		this.estaEnPaquete = estaEnPaquete;
	}

	public Boolean getEstaEnReconsideracion() {
		return estaEnReconsideracion;
	}

	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		this.estaEnReconsideracion = estaEnReconsideracion;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getTxtEntidadBeneficiaria() {
		return txtEntidadBeneficiaria;
	}

	public void setTxtEntidadBeneficiaria(String txtEntidadBeneficiaria) {
		this.txtEntidadBeneficiaria = txtEntidadBeneficiaria;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	public Boolean getIsCerrado() {
		return (this.estado.equals(EstadoProyecto.CERRADO));
	}
}
