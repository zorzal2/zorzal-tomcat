package com.fontar.data.api.assembler;

import java.util.Collection;

import com.fontar.data.impl.domain.dto.CompositeBitacoraDTO;

public interface BitacoraDTOAssembler  {

	public CompositeBitacoraDTO buildDTO(Collection bitacora );
	
}
