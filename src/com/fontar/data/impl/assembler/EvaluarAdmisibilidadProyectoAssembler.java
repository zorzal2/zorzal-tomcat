package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluarAdmisibilidadProyectoDTO;

/**
 * Assembler para armar Dto de admisibilidad de proyectos
 * @author ssanchez
 * @version 1.01, 21/12/06
 */

public class EvaluarAdmisibilidadProyectoAssembler implements ProyectoGeneralAssembler {

	public EvaluarAdmisibilidadProyectoDTO buildDTO(ProyectoBean bean) {
		EvaluarAdmisibilidadProyectoDTO admisibilidadProyectoDTO = new EvaluarAdmisibilidadProyectoDTO();

		InstrumentoBean instrumentoBean = bean.getInstrumento();
		if (instrumentoBean != null) {
			admisibilidadProyectoDTO.setInstrumento(instrumentoBean.getIdentificador());
		}

		admisibilidadProyectoDTO.setCodigo(bean.getCodigo());
		admisibilidadProyectoDTO.setTxtEntidadBeneficiaria(bean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		admisibilidadProyectoDTO.setEstado(bean.getEstado());
		admisibilidadProyectoDTO.setEstaEnReconsideracion(bean.estaEnReconsideracion());
		admisibilidadProyectoDTO.setEstaEnPaquete(bean.estaEnPaquete());
		admisibilidadProyectoDTO.setMotivoCierre(bean.getMotivoCierre());

		return admisibilidadProyectoDTO;
	}
}
