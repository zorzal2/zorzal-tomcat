package com.fontar.data.api.assembler;

import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.dto.DTO;

public interface PresentacionGeneralAssembler {

	public DTO buildDTO(PresentacionConvocatoriaBean bean);
}
