package com.fontar.data.impl.assembler;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.EntidadBancariaBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.util.StringUtil;

/**
 * Assembler para armar Dto de visualizacion de proyectos
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06 re-factorín
 */

public class ProyectoVisualizacionAssembler implements ProyectoGeneralAssembler {

	public ProyectoVisualizacionDTO buildDTO(ProyectoBean bean) {
		ProyectoVisualizacionDTO visualizacionDTO = new ProyectoVisualizacionDTO();

		ProyectoDatosBean proyectoDatos = bean.getProyectoDatos();

		if(proyectoDatos.getPersonaDirector()!=null) {
			visualizacionDTO.setPersonaDirector(PersonaAssembler.getInstance().buildDto(proyectoDatos.getPersonaDirector()));
		}
		if(proyectoDatos.getPersonaLegal()!=null) {
			visualizacionDTO.setPersonaLegal(PersonaAssembler.getInstance().buildDto(proyectoDatos.getPersonaLegal()));
		}
		if(proyectoDatos.getPersonaRepresentante()!=null) {
			visualizacionDTO.setPersonaRepresentante(PersonaAssembler.getInstance().buildDto(proyectoDatos.getPersonaRepresentante()));
		}

		TipoProyectoBean tipoProyectoBean = proyectoDatos.getTipoProyecto();
		if (tipoProyectoBean != null) {
			visualizacionDTO.setTxtTipoProyecto(tipoProyectoBean.getNombre());
		}

		EntidadBancariaBean entidadBancariaBean = proyectoDatos.getEntidadBancaria();
		if (entidadBancariaBean != null) {
			visualizacionDTO.setTxtEntidadBancaria(entidadBancariaBean.getEntidad().getDenominacion());
		}

		InstrumentoBean instrumentoBean = bean.getInstrumento();
		if (instrumentoBean != null) {
			visualizacionDTO.setInstrumento(instrumentoBean.getIdentificador());
			visualizacionDTO.setIdInstrumento(instrumentoBean.getId());
			visualizacionDTO.setTipoInstrumentoDef(instrumentoBean.getInstrumentoDef().getCodigoTipo());
			visualizacionDTO.setPermiteAdquisicion(instrumentoBean.permiteAdquisicion());

		
			//Alicuotas para CF
			if(instrumentoBean.aplicaCargaAlicuotaCF()) {
				visualizacionDTO.setPorcentajeAlicuotaSolicitada(StringUtil.formatPercentageForPresentation(bean.getPorcentajeAlicuotaSolicitada()));
				visualizacionDTO.setPorcentajeAlicuotaAdjudicada(StringUtil.formatPercentageForPresentation(bean.getPorcentajeAlicuotaAdjudicada()));
				visualizacionDTO.setAplicaCargaAlicuotaCF(true);
			} else {
				visualizacionDTO.setAplicaCargaAlicuotaCF(false);
			}
		} else {
			visualizacionDTO.setAplicaCargaAlicuotaCF(false);
		}

		visualizacionDTO.setEstado(bean.getEstado());
		visualizacionDTO.setEstaEnReconsideracion(bean.estaEnReconsideracion());
		visualizacionDTO.setEstaEnPaquete(bean.estaEnPaquete());
		visualizacionDTO.setMotivoCierre(bean.getMotivoCierre());
		visualizacionDTO.setCodigo(bean.getCodigo());
		visualizacionDTO.setProyectoPitec(bean.getProyectoPitec());
		visualizacionDTO.setTxtEntidadBeneficiaria(proyectoDatos.getEntidadBeneficiaria().getDenominacion());
		if(bean.getEmerix()!=null)
			visualizacionDTO.setEmerix(bean.getEmerix().toString());
		
		// MontoBeneficioFONTARSolicitado
		String montoBeneficioFONTARSolicitado;
		try {
			montoBeneficioFONTARSolicitado = StringUtil.formatMoneyForPresentation(bean.getMontoBeneficioFONTARSolicitado());
		} catch(SecurityException exception) {
			montoBeneficioFONTARSolicitado = ObjectUtils.ENCRIPTION_WARNING;
		}
		visualizacionDTO.setMontoBeneficioFONTARSolicitado(montoBeneficioFONTARSolicitado);
		
		// MontoBeneficioFONTARAprobado
		String montoBeneficioFONTARAprobado;
		try {
			montoBeneficioFONTARAprobado = StringUtil.formatMoneyForPresentation(bean.getMontoBeneficioFONTARAprobado());
		} catch(SecurityException exception) {
			montoBeneficioFONTARAprobado = ObjectUtils.ENCRIPTION_WARNING;
		}
		visualizacionDTO.setMontoBeneficioFONTARAprobado(montoBeneficioFONTARAprobado);

		return visualizacionDTO;
	}
}
