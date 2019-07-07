package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.ProyectoRaizCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.util.ResourceManager;

/**
 * Assembler para armar Dto de cabecera de proyectos
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoRaizCabeceraAssembler {

	public static ProyectoRaizCabeceraDTO buildDTO(ProyectoRaizBean bean) {
		ProyectoRaizCabeceraDTO proyectoRaizCabeceraDTO = new ProyectoRaizCabeceraDTO();

		proyectoRaizCabeceraDTO.setCodigo(bean.getCodigo());

		InstrumentoBean instrumentoBean = bean.getInstrumento();
		if (instrumentoBean != null) {
			proyectoRaizCabeceraDTO.setInstrumento(instrumentoBean.getIdentificador());
			proyectoRaizCabeceraDTO.setTipoInstrumentoDef(instrumentoBean.getInstrumentoDef().getCodigoTipo());
			proyectoRaizCabeceraDTO.setPermiteAdquisicion(instrumentoBean.getPermiteAdjudicacion());
		}

		ProyectoDatosBean proyectoDatos = bean.getProyectoDatos();
		EntidadBeneficiariaBean entidadBeneficiaria = proyectoDatos.getEntidadBeneficiaria();
		if (entidadBeneficiaria != null) {
			proyectoRaizCabeceraDTO.setEntidadBeneficiaria(entidadBeneficiaria.getEntidad().getDenominacion());
		}

		Integer duracion = proyectoDatos.getDuracion();
		if(duracion!=null) {
			proyectoRaizCabeceraDTO.setDuracion(duracion);
		}

		proyectoRaizCabeceraDTO.setEstado(bean.getEstado().getDescripcion());
		
		if(bean instanceof ProyectoBean) {
			ProyectoBean proyectoBean = (ProyectoBean) bean;
			String extraInfo = "";
			if(proyectoBean.estaEnReconsideracion() || proyectoBean.estaEnPaquete()) {
				
				if(proyectoBean.estaEnReconsideracion()) {
					extraInfo += ResourceManager.getLabelResource("app.label.enReconsideracion");
					if(proyectoBean.estaEnPaquete()) {
						extraInfo += " - "; 
					}
				}
				if(proyectoBean.estaEnPaquete()) {
					if(proyectoBean.estaEnReconsideracion()) {
						extraInfo += " - ";
					}
					extraInfo += ResourceManager.getLabelResource("app.label.enPaquete");
				}
			}
			if(proyectoBean.getEstado().equals(EstadoProyecto.CERRADO)) {
				extraInfo = proyectoBean.getMotivoCierre();
			}
			
			if(extraInfo.length()>0) {
				proyectoRaizCabeceraDTO.setEstadoExtraInfo(extraInfo);
			}
		}
		return proyectoRaizCabeceraDTO;
	}
	public static ProyectoRaizCabeceraDTO buildDTO(ProyectoRaizDTO dto) {
		ProyectoRaizCabeceraDTO proyectoRaizCabeceraDTO = new ProyectoRaizCabeceraDTO();

		proyectoRaizCabeceraDTO.setCodigo(dto.getCodigo());

		proyectoRaizCabeceraDTO.setInstrumento(dto.getInstrumento());
		proyectoRaizCabeceraDTO.setTipoInstrumentoDef(dto.getTipoInstrumentoDef());
		proyectoRaizCabeceraDTO.setPermiteAdquisicion(dto.getPermiteAdquisicion());

		proyectoRaizCabeceraDTO.setEntidadBeneficiaria(dto.getEntidadBeneficiaria());

		proyectoRaizCabeceraDTO.setDuracion(dto.getDuracion());
		
		proyectoRaizCabeceraDTO.setEstado(dto.getEstado().getDescripcion());
		
		/*if(dto instanceof ProyectoDTO) {
			ProyectoDTO proyectoDTO = (ProyectoDTO) dto;
			String extraInfo = "";
			if(proyectoDTO.estaEnReconsideracion || proyectodto.estaEnPaquete()) {
				
				if(proyectodto.estaEnReconsideracion()) {
					extraInfo += ResourceManager.getLabelResource("app.label.enReconsideracion");
					if(proyectodto.estaEnPaquete()) {
						extraInfo += " - "; 
					}
				}
				if(proyectodto.estaEnPaquete()) {
					if(proyectodto.estaEnReconsideracion()) {
						extraInfo += " - ";
					}
					extraInfo += ResourceManager.getLabelResource("app.label.enPaquete");
				}
			}
			if(proyectodto.getEstado().equals(EstadoProyecto.CERRADO)) {
				extraInfo = proyectodto.getMotivoCierre();
			}
			
			if(extraInfo.length()>0) {
				proyectoRaizCabeceraDTO.setEstadoExtraInfo(extraInfo);
			}
		}*/
		return proyectoRaizCabeceraDTO;
	}
}
