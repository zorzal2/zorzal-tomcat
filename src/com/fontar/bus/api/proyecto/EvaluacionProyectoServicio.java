package com.fontar.bus.api.proyecto;

import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;

public interface EvaluacionProyectoServicio extends BaseProyectoServicio {

	public void pasarAProximaEtapaSinEvaluacion(Long idProyecto, String fundamentacion);

	public void finalizarControlEvaluacion(EvaluacionGeneralDTO evaluacion, Long idProyecto, ResultadoEvaluacion resultado);
}
