package com.fontar.data.api.assembler;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.DTO;

public interface ProyectoGeneralAssembler {

	public DTO buildDTO(ProyectoBean bean);

}
