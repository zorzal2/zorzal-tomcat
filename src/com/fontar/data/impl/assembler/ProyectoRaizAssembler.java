package com.fontar.data.impl.assembler;

import com.fontar.data.Constant;
import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;

/**
 * Dto para proyecto raiz
 * @author ssanchez
 * 
 */
public class ProyectoRaizAssembler implements ProyectoRaizGeneralAssembler{

	public ProyectoRaizDTO buildDTO(ProyectoRaizBean proyectoRaiz) {
		ProyectoRaizDTO dto = new ProyectoRaizDTO();

		dto.setId(proyectoRaiz.getId() == null ? "" : proyectoRaiz.getId().toString());
		dto.setIdEntidadBeneficiaria(proyectoRaiz.getProyectoDatos().getEntidadBeneficiaria().getId().toString());
		dto.setEntidadBeneficiaria(
						proyectoRaiz.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		dto.setDuracion(proyectoRaiz.getProyectoDatos().getDuracion());
		dto.setCodigo(proyectoRaiz.getCodigo());
		dto.setIdPresupuestoOriginal(proyectoRaiz.getIdPresupuestoOriginal());
		dto.setIdPresupuesto(proyectoRaiz.getIdPresupuesto());
		if(proyectoRaiz.getInstrumento() == null) {
			dto.setInstrumento("");
		} else {
			dto.setInstrumento(proyectoRaiz.getInstrumento().getIdentificador());
			dto.setTipoInstrumentoDef(proyectoRaiz.getInstrumento().getInstrumentoDef().getCodigoTipo());
			dto.setPermiteAdquisicion(proyectoRaiz.getInstrumento().permiteAdquisicion());
		}
		dto.setEstado(proyectoRaiz.getEstado());
		if(proyectoRaiz.getIdInstrumento()!=null)
			dto.setTipoMatrizPresupuesto(proyectoRaiz.getInstrumento().getMatrizPresupuesto().getTipo());
		
		if (proyectoRaiz instanceof ProyectoBean) {
			dto.setClase(Constant.InstanciaProyectoRaiz.PROYECTO);
		}
		else if (proyectoRaiz instanceof IdeaProyectoBean) {
			dto.setClase(Constant.InstanciaProyectoRaiz.IDEA_PROYECTO);
		}
		else {
			dto.setClase(Constant.InstanciaProyectoRaiz.PROYECTO_PITEC);
		}
		
		PersonaAssembler personaAssembler = PersonaAssembler.getInstance();
		PersonaDTO director = personaAssembler.buildDto(proyectoRaiz.getProyectoDatos().getPersonaDirector());
		if(director!=null) {
			dto.setIdPersonaDirector(director.getId());
			dto.setPersonaDirector(director);
		}
		PersonaDTO responsableLegal = personaAssembler.buildDto(proyectoRaiz.getProyectoDatos().getPersonaLegal());
		if(responsableLegal!=null) {
			dto.setIdPersonaLegal(responsableLegal.getId());
			dto.setPersonaLegal(responsableLegal);
		}
		PersonaDTO representante = personaAssembler.buildDto(proyectoRaiz.getProyectoDatos().getPersonaRepresentante());
		if(representante!=null) {
			dto.setIdPersonaRepresentante(representante.getId());
			dto.setPersonaRepresentante(representante);
		}

		
		return dto;
	}
}
