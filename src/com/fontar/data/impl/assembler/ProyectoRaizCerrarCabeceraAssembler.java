package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;

/**
 * Dto para Cabecera Cierre de Proyecto / Idea Proyecto
 * @author ssanchez
 * 
 */
public class ProyectoRaizCerrarCabeceraAssembler implements ProyectoRaizGeneralAssembler {

	public ProyectoRaizCerrarCabeceraDTO buildDTO(ProyectoRaizBean proyectoRaizBean) {

		ProyectoRaizCerrarCabeceraDTO cerrarDTO = new ProyectoRaizCerrarCabeceraDTO();

		ProyectoRaizCerrarDTO proyectoRaizCerrarDTO = ProyectoRaizCerrarAssembler.buildDto(proyectoRaizBean);
		cerrarDTO.setProyectoRaizCerrarDTO(proyectoRaizCerrarDTO);

		InstrumentoBean instrumentoBean = proyectoRaizBean.getInstrumento();
		if (instrumentoBean != null) {
			cerrarDTO.setInstrumento(instrumentoBean.getIdentificador());
		}

		cerrarDTO.setCodigo(proyectoRaizBean.getCodigo());
		cerrarDTO.setTxtEntidadBeneficiaria(proyectoRaizBean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		cerrarDTO.setEstado(proyectoRaizBean.getEstado());

		if (proyectoRaizBean instanceof ProyectoBean) {
			cerrarDTO.setEstaEnReconsideracion(((ProyectoBean) proyectoRaizBean).estaEnReconsideracion());
			cerrarDTO.setEstaEnPaquete(((ProyectoBean) proyectoRaizBean).estaEnPaquete());
		}

		return cerrarDTO;
	}
}
