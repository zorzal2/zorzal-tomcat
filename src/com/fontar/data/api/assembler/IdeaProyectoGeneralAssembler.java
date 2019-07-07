package com.fontar.data.api.assembler;

import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.dto.DTO;

public interface IdeaProyectoGeneralAssembler {

	public DTO buildDTO(IdeaProyectoBean bean);
}
