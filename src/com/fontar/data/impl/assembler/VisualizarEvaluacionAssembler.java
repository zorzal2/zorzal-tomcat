package com.fontar.data.impl.assembler;

import java.util.Collection;
import java.util.Iterator;

import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.CabeceraDTO;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTODecorator;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionProyectoDTO;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.util.StringUtil;

public class VisualizarEvaluacionAssembler implements EvaluacionAssembler {

	private VisualizarEvaluacionProyectoDTO buildDTO(EvaluacionBean bean , EvaluacionDTO dto) {
		CabeceraDTO cabecera = new CabeceraDTO();
		cabecera.setEstadoProyecto(  dto.getEvaluable().getDescripcionEstado()  );
		cabecera.setEntidadBeneficiaria( this.getDenominacionEntidadBeneficiaria(bean) );
		cabecera.setDenominacionInstrumento(this.getDenominacionInstrumento(bean));
		cabecera.setEstaEnReconsideracion( this.getEstaEnReconsideracion(bean));
		VisualizarEvaluacionProyectoDTO visualizarEvaluacionProyectoDTO = new VisualizarEvaluacionProyectoDTO(dto);
		visualizarEvaluacionProyectoDTO.setCabecera( cabecera );
		
		//Solo tiene montos un proyecto que no es idea proyecto
		if(bean.getProyecto() instanceof ProyectoBean) {
			ProyectoBean proyecto = (ProyectoBean) bean.getProyecto();
			try {
				visualizarEvaluacionProyectoDTO.setMontoFontarAprobado(StringUtil.formatMoneyForPresentation(proyecto.getMontoBeneficioFONTARAprobado()));
			} catch(SecurityException e) {
				visualizarEvaluacionProyectoDTO.setMontoFontarAprobado(ObjectUtils.ENCRIPTION_WARNING);
			}
			try {
				visualizarEvaluacionProyectoDTO.setMontoTotalAprobado(StringUtil.formatMoneyForPresentation(proyecto.getMontoAprobado()));
			} catch(SecurityException e) {
				visualizarEvaluacionProyectoDTO.setMontoTotalAprobado(ObjectUtils.ENCRIPTION_WARNING);
			}
			try {
				visualizarEvaluacionProyectoDTO.setMontoFontarSolicitado(StringUtil.formatMoneyForPresentation(proyecto.getMontoBeneficioFONTARSolicitado()));
			} catch(SecurityException e) {
				visualizarEvaluacionProyectoDTO.setMontoFontarSolicitado(ObjectUtils.ENCRIPTION_WARNING);
			}
			try {
				visualizarEvaluacionProyectoDTO.setMontoTotalSolicitado(StringUtil.formatMoneyForPresentation(proyecto.getMontoSolicitado()));
			} catch(SecurityException e) {
				visualizarEvaluacionProyectoDTO.setMontoTotalSolicitado(ObjectUtils.ENCRIPTION_WARNING);
			}
		}
		
		return visualizarEvaluacionProyectoDTO;
	}

	public Boolean getEstaEnReconsideracion(EvaluacionBean bean) {
		return bean.getProyecto().estaEnReconsideracion();
	}

	public String getDenominacionEntidadBeneficiaria(EvaluacionBean bean) {
		return bean.getProyecto().getProyectoDatos().getEntidadBeneficiaria().getEntidad().getDenominacion();
	}

	public String getDenominacionInstrumento(EvaluacionBean bean) {
		if(bean.getProyecto() instanceof ProyectoBean)
			return bean.getProyecto().getInstrumento().getIdentificador();
		else
			return "";
	}

	/*
	public String getDetalleEstadoProyecto(EvaluacionDTO dto) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(dto.getEvaluable().getDescripcionEstado());
		ProyectoDTO proyectoDTO = (ProyectoDTO) dto.getEvaluable();
		Boolean estaEnReconsideracion = Boolean.valueOf(proyectoDTO.getEstadoReconsideracion());
		if (estaEnReconsideracion) {
			buffer.append("( ");
			buffer.append("reconsideración");
			buffer.append(" ");
		}
		return buffer.toString();
	}
	*/

	
	public String getShowEvaluacion(EvaluacionGeneralDTO dto) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(this.getTipos(dto));
		buffer.append("  -  (");
		
		Collection<EvaluacionEvaluadorDTO> evaluadores = dto.getEvaluadores();
		if(evaluadores != null) {
	
			EvaluacionEvaluadorAssembler eeAssembler = EvaluacionEvaluadorAssembler.getInstance();
			
			Iterator<EvaluacionEvaluadorDTO> eva = evaluadores.iterator();
			while(eva.hasNext()){
				EvaluacionEvaluadorDTO eeDto = eva.next();
				EvaluacionEvaluadorDTODecorator eeDtoDecorator = eeAssembler.buildDto(eeDto);

				String nombre = eeDtoDecorator.getEvaluador(); 
				if(nombre != null && nombre != "") {
					buffer.append(nombre);
					buffer.append("/");
				}
				buffer.append(eeDtoDecorator.getEntidadEvaluadora());
			}
		}
			
		buffer.append(")<br/>");
		return buffer.toString();
	}
	
	
	public String getTipos(EvaluacionGeneralDTO dto) {
		StringBuffer buffer = new StringBuffer();

		Boolean token = false;

		if (Boolean.parseBoolean(dto.getEsTecnica())) {
			buffer.append("Técnica");
			//buffer.append(" ");
			token = true;
		}

		if (Boolean.parseBoolean(dto.getEsEconomica())) {
			if (token)
				buffer.append("-");
			else
				token = true;
			buffer.append("Económica");
		}

		if (Boolean.parseBoolean(dto.getEsFinanciera())) {
			if (token)
				buffer.append("-");
			buffer.append("Financiera");
			buffer.append("(");
			buffer.append(dto.getTipoEvaluacionFinanciera().getDescripcion());
			buffer.append(")");
		}

		return buffer.toString();
	}
	
	public String getShowEvaluadores(Collection<EvaluacionEvaluadorDTO> evaluadores) {
		StringBuffer buffer = new StringBuffer();
		if(evaluadores != null) {
			
			EvaluacionEvaluadorAssembler eeAssembler = EvaluacionEvaluadorAssembler.getInstance();
			
			Iterator<EvaluacionEvaluadorDTO> eva = evaluadores.iterator();
			while(eva.hasNext()){
				EvaluacionEvaluadorDTO eeDto = eva.next();
				EvaluacionEvaluadorDTODecorator eeDtoDecorator = eeAssembler.buildDto(eeDto);

				buffer.append(eeDtoDecorator.getEvaluador());
				buffer.append(" - (");
				buffer.append(eeDtoDecorator.getEntidadEvaluadora());
				buffer.append(")");
				
				buffer.append("<br/>");
			}
		}
		return buffer.toString();
	}
	
	public EvaluacionDTO buildDTO(EvaluacionBean bean) {
		//Resuelve el DTO si es una evaluacion general
		if( bean instanceof EvaluacionGeneralBean){
			EvaluacionGeneralDTO dto = EvaluacionGeneralAssembler.getInstance().buildDto((EvaluacionGeneralBean) bean );
			return new VisualizarEvaluacionGeneralDecorator( this.buildDTO( bean, dto));
			//Construye el dto y lo decora los datos de visualizacion de evaluacion general
		}
	
		if( bean.getClass().equals(EvaluacionBean.class)  || bean instanceof EvaluacionPaqueteBean){
			//Para EvaluacionPaquete se trata como Evaluacion (no hay particualridades)
			EvaluacionDTO dto = com.fontar.data.impl.assembler.EvaluacionAssembler.getInstance().buildDto( bean );
			return this.buildDTO(bean, dto);
		}else{
			//No es posible resolver el DTO
			throw new IllegalArgumentException("Tipo de evaluacion no soportada");
		}
	}
}