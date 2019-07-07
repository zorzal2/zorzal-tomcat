package com.fontar.bus.api.ventanilla;

import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;

/***
 * Servicios para la administración de Idea Proyectos (sin workflow).
 */
public interface AdministrarIdeaProyectoServicio {

	public abstract IdeaProyectoBean cargarIdeaProyecto(IdeaProyectoDTO datosIdeaProyecto);

	public abstract void modificarIdeaProyecto(String idIdeaProyecto, IdeaProyectoDTO datosIdeaProyecto);
	
	public DTO getIdeaProyectoDTO(Long idIdeaProyecto, IdeaProyectoGeneralAssembler assembler);

	public abstract IdeaProyectoVisualizarDTO getIdeaProyectoVisualizarDTO(Long idProyecto, IdeaProyectoDTOAssembler assembler);

}
