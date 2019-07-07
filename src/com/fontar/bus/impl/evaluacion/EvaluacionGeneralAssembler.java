package com.fontar.bus.impl.evaluacion;

import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;

public interface EvaluacionGeneralAssembler {

	EvaluacionGeneralDTO buildDTO(EvaluacionGeneralBean bean);
}
