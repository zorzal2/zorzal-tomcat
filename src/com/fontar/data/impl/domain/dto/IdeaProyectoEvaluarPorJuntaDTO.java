package com.fontar.data.impl.domain.dto;

import java.util.Collection;

import com.fontar.bus.api.workflow.OpcionDeEvaluacionPorJunta;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;


/**
 * DTO para la pantalla de evaluar por junta de Idea Proyecto
 * @author gboaglio, ssanchez
 * @version 1.01, 10/01/07
 */

public class IdeaProyectoEvaluarPorJuntaDTO extends IdeaProyectoCabeceraDTO {

	private static final long serialVersionUID = 1L;
	private String id;
	private IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO;
	private Collection<OpcionDeEvaluacionPorJunta> opcionesDeEvaluacion;

	public Collection<OpcionDeEvaluacionPorJunta> getOpcionesDeEvaluacion() {
		return opcionesDeEvaluacion;
	}
	public void setOpcionesDeEvaluacion(Collection<OpcionDeEvaluacionPorJunta> opcionesDeEvaluacion) {
		this.opcionesDeEvaluacion = opcionesDeEvaluacion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public IdeaProyectoCabeceraDTO getIdeaProyectoCabeceraDTO() {
		return ideaProyectoCabeceraDTO;
	}
	public void setIdeaProyectoCabeceraDTO(IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO) {
		this.ideaProyectoCabeceraDTO = ideaProyectoCabeceraDTO;
	}
	public Long getCodigoIdeaProyecto() {
		return ideaProyectoCabeceraDTO.getCodigoIdeaProyecto();
	}
	public String getEntidadBeneficiaria() {
		return ideaProyectoCabeceraDTO.getEntidadBeneficiaria();
	}
	public EstadoIdeaProyecto getEstado() {
		return ideaProyectoCabeceraDTO.getEstado();
	}
	public String getJurisdiccion() {
		return ideaProyectoCabeceraDTO.getJurisdiccion();
	}
	public void setCodigoIdeaProyecto(Long codigoIdeaProyecto) {
		ideaProyectoCabeceraDTO.setCodigoIdeaProyecto(codigoIdeaProyecto);
	}
	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		ideaProyectoCabeceraDTO.setEntidadBeneficiaria(entidadBeneficiaria);
	}
	public void setEstado(EstadoIdeaProyecto estado) {
		ideaProyectoCabeceraDTO.setEstado(estado);
	}
	public void setJurisdiccion(String jurisdiccion) {
		ideaProyectoCabeceraDTO.setJurisdiccion(jurisdiccion);
	}
}
