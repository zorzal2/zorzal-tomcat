package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;

/**
 * Assembler para crear una IdeaProyectoEvaluarPorJuntaDTO
 * @author gboaglio, ssanchez
 * @version 1.01, 10/01/07
 */

public class IdeaProyectoEvaluarPorJuntaAssembler implements IdeaProyectoGeneralAssembler {

	/**
	 * Crea dto IdeaProyecto a partir del bean
	 */
	public IdeaProyectoEvaluarPorJuntaDTO buildDTO(IdeaProyectoBean bean) {
		IdeaProyectoEvaluarPorJuntaDTO dto = new IdeaProyectoEvaluarPorJuntaDTO();

		dto.setId(bean.getId().toString());
		
		IdeaProyectoCabeceraAssembler assembler = new IdeaProyectoCabeceraAssembler();
		dto.setIdeaProyectoCabeceraDTO(assembler.buildDTO(bean));

		return dto;
	}

}
