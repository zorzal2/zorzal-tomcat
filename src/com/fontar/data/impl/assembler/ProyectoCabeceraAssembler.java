package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;

/**
 * Assembler para armar Dto de cabecera de proyectos
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoCabeceraAssembler implements ProyectoGeneralAssembler {

	
	public ProyectoCabeceraDTO buildDTO(ProyectoBean bean) {
		return buildDTO((ProyectoRaizBean)bean);
	}
	public ProyectoCabeceraDTO buildDTO(ProyectoRaizBean bean) {
		ProyectoCabeceraDTO proyectoCabeceraDTO = new ProyectoCabeceraDTO();

		proyectoCabeceraDTO.setCodigo(bean.getCodigo());

		InstrumentoBean instrumentoBean = bean.getInstrumento();
		if (instrumentoBean != null) {
			proyectoCabeceraDTO.setInstrumento(instrumentoBean.getIdentificador());
		}

		ProyectoDatosBean proyectoDatos = bean.getProyectoDatos();
		EntidadBeneficiariaBean entidadBeneficiaria = proyectoDatos.getEntidadBeneficiaria();
		if (entidadBeneficiaria != null) {
			proyectoCabeceraDTO.setTxtEntidadBeneficiaria(entidadBeneficiaria.getEntidad().getDenominacion());
		}

		proyectoCabeceraDTO.setEstado(bean.getEstado());
		proyectoCabeceraDTO.setEstaEnReconsideracion(bean.estaEnReconsideracion());
		if(bean instanceof ProyectoBean) {
			proyectoCabeceraDTO.setEstaEnPaquete(((ProyectoBean)bean).estaEnPaquete());
		} else {
			proyectoCabeceraDTO.setEstaEnPaquete(false);
		}
		InstrumentoBean instrumento = bean.getInstrumento();
		if(instrumento!=null) {
			proyectoCabeceraDTO.setAplicaCargaAlicuotaCF(instrumento.aplicaCargaAlicuotaCF());
		}

		return proyectoCabeceraDTO;
	}
}
