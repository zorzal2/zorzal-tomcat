package com.fontar.data.api.assembler;

import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.DTO;

public interface SeguimientoGeneralAssembler {

	public DTO buildDTO(SeguimientoBean bean);

}
