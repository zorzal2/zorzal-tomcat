package com.fontar.data.impl.domain.dto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;

/**
 * Dto de pasar a proxima etapa de proyectos
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoPasarProximaEtapaDTO extends ProyectoCabeceraDTO {

	private static final long serialVersionUID = 1L;

	private Recomendacion recomendacion;
	private ProyectoCabeceraDTO proyectoCabeceraDTO;


	public ProyectoCabeceraDTO getProyectoCabeceraDTO() {
		return proyectoCabeceraDTO;
	}

	public void setProyectoCabeceraDTO(ProyectoCabeceraDTO proyectoCabeceraDTO) {
		this.proyectoCabeceraDTO = proyectoCabeceraDTO;
	}

	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getCodigo() {
		return proyectoCabeceraDTO.getCodigo();
	}

	public Enumerable getEstado() {
		return proyectoCabeceraDTO.getEstado();
	}

	public Boolean getEstaEnPaquete() {
		return proyectoCabeceraDTO.getEstaEnPaquete();
	}

	public Boolean getEstaEnReconsideracion() {
		return proyectoCabeceraDTO.getEstaEnReconsideracion();
	}

	public String getInstrumento() {
		return proyectoCabeceraDTO.getInstrumento();
	}

	public String getTxtEntidadBeneficiaria() {
		return proyectoCabeceraDTO.getTxtEntidadBeneficiaria();
	}

	public void setCodigo(String codigo) {
		proyectoCabeceraDTO.setCodigo(codigo);
	}

	public void setEstado(EstadoProyecto estado) {
		proyectoCabeceraDTO.setEstado(estado);
	}

	public void setEstaEnPaquete(Boolean estaEnPaquete) {
		proyectoCabeceraDTO.setEstaEnPaquete(estaEnPaquete);
	}

	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		proyectoCabeceraDTO.setEstaEnReconsideracion(estaEnReconsideracion);
	}

	public void setInstrumento(String instrumento) {
		proyectoCabeceraDTO.setInstrumento(instrumento);
	}

	public void setTxtEntidadBeneficiaria(String txtEntidadBeneficiaria) {
		proyectoCabeceraDTO.setTxtEntidadBeneficiaria(txtEntidadBeneficiaria);
	}
}
