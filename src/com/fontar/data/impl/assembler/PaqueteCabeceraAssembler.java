package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.dto.PaqueteCabeceraDTO;

/**
 * Assembler para armar Dto de cabecera de paquetes
 * @author ssanchez
 * @version 1.00, 12/01/07
 */

public class PaqueteCabeceraAssembler implements PaqueteGeneralAssembler {

	public PaqueteCabeceraDTO buildDTO(PaqueteBean bean) {
		PaqueteCabeceraDTO paqueteCabeceraDTO = new PaqueteCabeceraDTO();

		paqueteCabeceraDTO.setIdPaquete(bean.getId());
		paqueteCabeceraDTO.setInstrumento(bean.getInstrumento().getIdentificador());
		
		ComisionBean comisionBean = bean.getComision();
		if (comisionBean != null) {
			paqueteCabeceraDTO.setComision(bean.getComision().getDenominacion());
		}
		
		paqueteCabeceraDTO.setTratamiento(bean.getTratamiento());
		paqueteCabeceraDTO.setEstado(bean.getEstado());
		paqueteCabeceraDTO.setTipoPaquete(bean.getTipo());

		return paqueteCabeceraDTO;
	}
}
