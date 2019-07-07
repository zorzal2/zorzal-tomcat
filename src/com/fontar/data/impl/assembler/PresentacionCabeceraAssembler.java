package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.PresentacionGeneralAssembler;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;

/**
 * Assembler para armar Dto de cabecera de presentacion
 * @author ssanchez
 * @version 1.00, 10/01/07
 */

public class PresentacionCabeceraAssembler implements PresentacionGeneralAssembler {

	public PresentacionCabeceraDTO buildDTO(PresentacionConvocatoriaBean bean) {
		PresentacionCabeceraDTO presentacionCabeceraDTO = new PresentacionCabeceraDTO();

		presentacionCabeceraDTO.setCodigo(bean.getCodigo());
		presentacionCabeceraDTO.setConvocatoria(bean.getInstrumento().getIdentificador());
		presentacionCabeceraDTO.setJurisdiccion(bean.getJurisdiccion().getDescripcion());
		presentacionCabeceraDTO.setSolicitante(bean.getNombreEntidad());

		return presentacionCabeceraDTO;
	}
}
