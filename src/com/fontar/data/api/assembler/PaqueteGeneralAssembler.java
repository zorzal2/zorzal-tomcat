package com.fontar.data.api.assembler;

import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.dto.DTO;

public interface PaqueteGeneralAssembler {

	public DTO buildDTO(PaqueteBean bean);
	
}
