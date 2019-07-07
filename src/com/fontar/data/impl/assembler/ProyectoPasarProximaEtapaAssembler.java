package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.ProyectoPasarProximaEtapaDTO;

/**
 * Assembler para armar Dto de cabecera de proyectos
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoPasarProximaEtapaAssembler implements ProyectoGeneralAssembler {

	public ProyectoPasarProximaEtapaDTO buildDTO(ProyectoBean bean) {
		ProyectoPasarProximaEtapaDTO proximaEtapaDTO = new ProyectoPasarProximaEtapaDTO();

		proximaEtapaDTO.setRecomendacion(Recomendacion.A_DEFINIR);
		
		ProyectoCabeceraAssembler assembler = new ProyectoCabeceraAssembler();
		proximaEtapaDTO.setProyectoCabeceraDTO(assembler.buildDTO(bean));

		return proximaEtapaDTO;
	}
}
