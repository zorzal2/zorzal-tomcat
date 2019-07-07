package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;

/**
 * Assembler para armar Dto de cabecera de proyectos
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class IdeaProyectoCabeceraAssembler implements IdeaProyectoGeneralAssembler {

	public IdeaProyectoCabeceraDTO buildDTO(IdeaProyectoBean bean) {
		IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO = new IdeaProyectoCabeceraDTO();

		ideaProyectoCabeceraDTO.setCodigoIdeaProyecto(bean.getCodigoIdeaProyecto());

		EntidadBeneficiariaBean entidadBeneficiaria = bean.getProyectoDatos().getEntidadBeneficiaria();
		if (entidadBeneficiaria != null) {
			ideaProyectoCabeceraDTO.setEntidadBeneficiaria(entidadBeneficiaria.getEntidad().getDenominacion());
		}
		
		ProyectoJurisdiccionBean jurisdiccionBean = bean.getProyectoJurisdiccion();
		if (jurisdiccionBean != null) {
			ideaProyectoCabeceraDTO.setJurisdiccion(jurisdiccionBean.getJurisdiccion().getDescripcion());
		}

		ideaProyectoCabeceraDTO.setEstado(bean.getEstado());
		
		return ideaProyectoCabeceraDTO;
	}
}
