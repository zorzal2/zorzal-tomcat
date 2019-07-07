package com.fontar.bus.impl.evaluacion;

import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;

public interface EvaluacionSeguimientoAssembler {

	EvaluacionSeguimientoDTO buildDTO(EvaluacionSeguimientoBean bean);
}
