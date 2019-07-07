package com.fontar.data.impl.domain.dto;

import java.util.Collection;

import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;

/**
 * Dto para la visualizacion de un paquete
 * @author ssanchez
 * @version 1.00, 29/11/06
 */
public class VisualizarPaqueteDTO {

	private Long id;

	private String codigoActa;

	private String observacion;

	private String tipo;

	private Collection<VisualizarProyectoFilaDTO> filasProyectos;
	
	private PaqueteCabeceraDTO paqueteCabeceraDTO;
	
	public String getComision() {
		return paqueteCabeceraDTO.getComision();
	}

	public EstadoPaquete getEstado() {
		return paqueteCabeceraDTO.getEstado();
	}

	public Long getIdPaquete() {
		return paqueteCabeceraDTO.getIdPaquete();
	}

	public TratamientoPaquete getTratamiento() {
		return paqueteCabeceraDTO.getTratamiento();
	}

	public void setComision(String comision) {
		paqueteCabeceraDTO.setComision(comision);
	}

	public void setEstado(EstadoPaquete estado) {
		paqueteCabeceraDTO.setEstado(estado);
	}

	public void setIdPaquete(Long idPaquete) {
		paqueteCabeceraDTO.setIdPaquete(idPaquete);
	}

	public void setTratamiento(TratamientoPaquete tratamiento) {
		paqueteCabeceraDTO.setTratamiento(tratamiento);
	}

	public PaqueteCabeceraDTO getPaqueteCabeceraDTO() {
		return paqueteCabeceraDTO;
	}

	public void setPaqueteCabeceraDTO(PaqueteCabeceraDTO paqueteCabeceraDTO) {
		this.paqueteCabeceraDTO = paqueteCabeceraDTO;
	}

	public String getCodigoActa() {
		return codigoActa;
	}

	public void setCodigoActa(String codigoActa) {
		this.codigoActa = codigoActa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Collection<VisualizarProyectoFilaDTO> getFilasProyectos() {
		return filasProyectos;
	}

	public void setFilasProyectos(Collection<VisualizarProyectoFilaDTO> filasProyectos) {
		this.filasProyectos = filasProyectos;
	}

	public String getInstrumento() {
		return paqueteCabeceraDTO.getInstrumento();
	}

	public void setInstrumento(String instrumento) {
		paqueteCabeceraDTO.setInstrumento(instrumento);
	}
	
}
