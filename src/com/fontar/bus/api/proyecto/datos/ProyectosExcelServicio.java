package com.fontar.bus.api.proyecto.datos;

import java.util.Collection;

import com.fontar.bus.impl.proyecto.datos.ProyectosExcelFeedbackRequiredException;
import com.fontar.bus.impl.proyecto.datos.ProyectosExcelParsingException;
import com.fontar.bus.impl.proyecto.datos.UserCancelledException;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.pragma.excel.exception.ParsingException;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * Servicios para la carga de proyectos a partir de una planilla Excel. 
 * Estas operaciones construyen un listado de DTOs de proyectos para los datos en la planilla. 
 */
public interface ProyectosExcelServicio {
	
	/**
	 * 
	 * @param dto
	 * @return
	 * @throws ProyectosExcelParsingException
	 * @throws ParsingException
	 * @throws ProyectosExcelFeedbackRequiredException
	 * @throws UserCancelledException
	 */
	public Collection<ProyectoDTO> parseArchivo(ArchivoDTO dto) throws ProyectosExcelParsingException, ParsingException, ProyectosExcelFeedbackRequiredException, UserCancelledException;
	
	/**
	 * 
	 * @param dto
	 * @param feedbackResponses
	 * @return
	 * @throws ProyectosExcelParsingException
	 * @throws ParsingException
	 * @throws ProyectosExcelFeedbackRequiredException
	 * @throws UserCancelledException
	 */
	public Collection<ProyectoDTO> parseArchivo(ArchivoDTO dto, Collection<UserFeedbackResponse> feedbackResponses) throws ProyectosExcelParsingException, ParsingException, ProyectosExcelFeedbackRequiredException, UserCancelledException;
}
