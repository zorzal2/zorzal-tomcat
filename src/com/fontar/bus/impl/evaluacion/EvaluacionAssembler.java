package com.fontar.bus.impl.evaluacion;

import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;

public interface EvaluacionAssembler  {

	
	EvaluacionDTO buildDTO(EvaluacionBean bean);
}
