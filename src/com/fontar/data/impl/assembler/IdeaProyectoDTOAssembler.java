package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.pragma.util.DateTimeUtil;
import com.pragma.web.WebContextUtil;

/**
 * Assembler para crear una IdeaProyecto*DTO
 * @author ssanchez
 * @version 1.01, 11/01/07
 */
public class IdeaProyectoDTOAssembler implements IdeaProyectoGeneralAssembler {

	public DTO buildDTO(IdeaProyectoBean bean) {

		// New instance
		IdeaProyectoDTO dto = new IdeaProyectoDTO();

		// Populate
		dto.setId(String.valueOf(bean.getId()));
		dto.setEstado(bean.getEstado());
		dto.setEntidadBeneficiaria(bean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		dto.setCodigoIdeaProyecto(bean.getCodigoIdeaProyecto());
		dto.setFechaIngreso(bean.getProyectoDatos().getFechaIngreso());
		dto.setIdTipoProyecto(bean.getProyectoDatos().getIdTipoProyecto());
		dto.setTitulo(bean.getProyectoDatos().getTitulo());
		dto.setResumen(bean.getProyectoDatos().getResumen());
		dto.setInstrumentoSolicitado(bean.getProyectoDatos().getInstrumentoSolicitado());
		dto.setDuracion(bean.getProyectoDatos().getDuracion());
		dto.setIdEntidadBeneficiaria(bean.getProyectoDatos().getIdEntidadBeneficiaria());
		dto.setObservaciones(bean.getProyectoDatos().getObservacion());
		
		PersonaAssembler personaAssembler = PersonaAssembler.getInstance();
		PersonaDTO director = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaDirector());
		if(director!=null) {
			dto.setIdPersonaDirector(director.getId());
			dto.setPersonaDirector(director);
		}
		PersonaDTO responsableLegal = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaLegal());
		if(responsableLegal!=null) {
			dto.setIdPersonaLegal(responsableLegal.getId());
			dto.setPersonaLegal(responsableLegal);
		}
		PersonaDTO representante = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaRepresentante());
		if(representante!=null) {
			dto.setIdPersonaRepresentante(representante.getId());
			dto.setPersonaRepresentante(representante);
		}
		
		ProyectoPresupuestoBean presupuestoBean = bean.getProyectoPresupuestoOriginal();
		if (presupuestoBean != null) {
			try {
				dto.setMontoTotal(presupuestoBean.getMontoTotal());
				dto.setMontoSolicitado(presupuestoBean.getMontoSolicitado());
			}
			catch (Exception e) {
				
			}
		}
		dto.setIdJurisdiccion(bean.getProyectoJurisdiccion().getJurisdiccion().getId());
		dto.setJurisdiccion(bean.getProyectoJurisdiccion().getJurisdiccion().getDescripcion());

		return dto;
	}

	public IdeaProyectoVisualizarDTO buildDtoVisualizar(IdeaProyectoBean bean) {

		// New instance
		IdeaProyectoVisualizarDTO dto = new IdeaProyectoVisualizarDTO();

		// Populate
		dto.setId(String.valueOf(bean.getId()));
		dto.setEntidadBeneficiaria(bean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		dto.setCodigoIdeaProyecto(String.valueOf(bean.getCodigoIdeaProyecto()));
		
		dto.setFechaIngreso(DateTimeUtil.formatDate(bean.getProyectoDatos().getFechaIngreso()));
		Long idTipoProyecto = bean.getProyectoDatos().getIdTipoProyecto();
		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		String tipoProyecto = "";
		if (idTipoProyecto != null) {
			TipoProyectoBean tipoProyectoBean = tipoProyectoDAO.read(idTipoProyecto);
			tipoProyecto = tipoProyectoBean.getNombre();
		}
		dto.setTipoProyecto(tipoProyecto);
		dto.setTitulo(bean.getProyectoDatos().getTitulo());
		dto.setResumen(bean.getProyectoDatos().getResumen());
		dto.setInstrumentoSolicitado(bean.getProyectoDatos().getInstrumentoSolicitado());
		dto.setObservaciones(bean.getProyectoDatos().getObservacion());
		dto.setInstrumentoRecomendado(bean.getInstrumentoRecomendado());
		dto.setDuracion(bean.getProyectoDatos().getDuracion());
		
		PersonaAssembler personaAssembler = PersonaAssembler.getInstance();
		PersonaDTO director = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaDirector());
		if(director!=null) {
			dto.setIdPersonaDirector(director.getId());
			dto.setPersonaDirector(director);
		}
		PersonaDTO responsableLegal = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaLegal());
		if(responsableLegal!=null) {
			dto.setIdPersonaLegal(responsableLegal.getId());
			dto.setPersonaLegal(responsableLegal);
		}
		PersonaDTO representante = personaAssembler.buildDto(bean.getProyectoDatos().getPersonaRepresentante());
		if(representante!=null) {
			dto.setIdPersonaRepresentante(representante.getId());
			dto.setPersonaRepresentante(representante);
		}
		
		
		ProyectoPresupuestoBean presupuestoBean = bean.getProyectoPresupuestoOriginal();
		if (presupuestoBean != null) {
			try {
				dto.setMontoTotal(presupuestoBean.getMontoPresupuestoTotal());
				dto.setMontoSolicitado(presupuestoBean.getMontoPresupuestoSolicitado());
			}
			catch (Exception e) {
				
			}
		}
		dto.setJurisdiccion(bean.getProyectoJurisdiccion().getJurisdiccion().getDescripcion());

		return dto;
	}

}
