package com.fontar.data.impl.domain.dto;

import java.util.Collection;
import java.util.Iterator;

import com.fontar.data.impl.assembler.EvaluacionEvaluadorAssembler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacionFinanciera;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.EncryptedObject;

public class VisualizarEvaluacionGeneralDecorator extends VisualizarEvaluacionProyectoDTO {

	private VisualizarEvaluacionProyectoDTO visualizarEvaluacionDTO;

	private EvaluacionGeneralDTO evaluacionGeneral;
	
	public VisualizarEvaluacionGeneralDecorator(VisualizarEvaluacionProyectoDTO evaluacionDTO) {
		this.visualizarEvaluacionDTO = evaluacionDTO;
		this.evaluacionGeneral = (EvaluacionGeneralDTO) evaluacionDTO.getDto();
	}

	public Boolean getAceptada() {
		return evaluacionGeneral.getAceptada();
	}

	public String getEsAuditoriaContable() {
		return evaluacionGeneral.getEsAuditoriaContable();
	}

	public String getEsContable() {
		return evaluacionGeneral.getEsContable();
	}

	public String getEsEconomica() {
		return evaluacionGeneral.getEsEconomica();
	}

	public String getEsFinanciera() {
		return evaluacionGeneral.getEsFinanciera();
	}

	public String getEsTecnica() {
		return evaluacionGeneral.getEsTecnica();
	}

	public String getEsVisitaTecnica() {
		return evaluacionGeneral.getEsVisitaTecnica();
	}

	public Collection<EvaluacionEvaluadorDTO> getEvaluadores() {
		return evaluacionGeneral.getEvaluadores();
	}
	
	
	public String getTipos() {
		StringBuffer buffer = new StringBuffer();

		Boolean token = false;

		if (Boolean.parseBoolean(this.getEsTecnica())) {
			buffer.append("Técnica");
			buffer.append(" ");
			token = true;
		}

		if (Boolean.parseBoolean(this.getEsEconomica())) {
			if (token)
				buffer.append(" - ");
			else
				token = true;
			buffer.append("Económica");
		}

		if (Boolean.parseBoolean(this.getEsFinanciera())) {
			if (token)
				buffer.append(" - ");
			buffer.append("Financiera");
			buffer.append(" (");
			buffer.append(this.getTipoEvaluacionFinanciera());
			buffer.append(") ");
		}

		return buffer.toString();
	}

	
	
	
	public String getShowEvaluacion(EvaluacionGeneralDTO dto) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(this.getTipos());
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

	public boolean equals(Object obj) {
		return visualizarEvaluacionDTO.equals(obj);
	}

	public BitacoraDTO getBitacora() {
		return visualizarEvaluacionDTO.getBitacora();
	}

	public CabeceraDTO getCabecera() {
		return visualizarEvaluacionDTO.getCabecera();
	}

	public EvaluacionDTO getDto() {
		return visualizarEvaluacionDTO.getDto();
	}

	public EstadoEvaluacion getEstado() {
		return visualizarEvaluacionDTO.getEstado();
	}

	public EvaluableDTO getEvaluable() {
		return visualizarEvaluacionDTO.getEvaluable();
	}

	public String getFecha() {
		return visualizarEvaluacionDTO.getFecha();
	}

	public String getFechaInicial() {
		return visualizarEvaluacionDTO.getFechaInicial();
	}

	public String getFundamentacion() {
		return visualizarEvaluacionDTO.getFundamentacion();
	}

	public Long getId() {
		return visualizarEvaluacionDTO.getId();
	}

	public Long getIdCronograma() {
		return visualizarEvaluacionDTO.getIdCronograma();
	}

	public Long getIdPlanTrabajo() {
		return visualizarEvaluacionDTO.getIdPlanTrabajo();
	}

	public Long getIdPresupuesto() {
		return visualizarEvaluacionDTO.getIdPresupuesto();
	}

	public Long getIdProyecto() {
		return visualizarEvaluacionDTO.getIdProyecto();
	}

	public Long getIdWorkFlow() {
		return visualizarEvaluacionDTO.getIdWorkFlow();
	}

	public Boolean getMarcarAceptada() {
		return visualizarEvaluacionDTO.getMarcarAceptada();
	}

	public String getObservacion() {
		return visualizarEvaluacionDTO.getObservacion();
	}

	public EvaluableDTO getProyecto() {
		return visualizarEvaluacionDTO.getProyecto();
	}

	public ProyectoPresupuestoDTO getProyectoPresupuesto() {
		return visualizarEvaluacionDTO.getProyectoPresupuesto();
	}

	public String getRecomendacion() {
		return visualizarEvaluacionDTO.getRecomendacion();
	}

	public ResultadoEvaluacion getResultado() {
		return visualizarEvaluacionDTO.getResultado();
	}

	public String getResultadoDescripcion() {
		return visualizarEvaluacionDTO.getResultadoDescripcion();
	}

	public EncryptedObject getResultadoEvaluacion() {
		return visualizarEvaluacionDTO.getResultadoEvaluacion();
	}

	public Boolean getResumido() {
		return visualizarEvaluacionDTO.getResumido();
	}

	public Boolean getTieneResultado() {
		return visualizarEvaluacionDTO.getTieneResultado();
	}

	public TipoEvaluacion getTipo() {
		return visualizarEvaluacionDTO.getTipo();
	}

	public String getTipoDescripcion() {
		return visualizarEvaluacionDTO.getTipoDescripcion();
	}

	public int hashCode() {
		return visualizarEvaluacionDTO.hashCode();
	}

	public void setBitacora(BitacoraDTO bitacora) {
		visualizarEvaluacionDTO.setBitacora(bitacora);
	}

	public void setCabecera(CabeceraDTO cabecera) {
		visualizarEvaluacionDTO.setCabecera(cabecera);
	}

	public void setEstado(EstadoEvaluacion estado) {
		visualizarEvaluacionDTO.setEstado(estado);
	}

	public void setEvaluable(EvaluableDTO dto) {
		visualizarEvaluacionDTO.setEvaluable(dto);
	}

	public void setFecha(String fecha) {
		visualizarEvaluacionDTO.setFecha(fecha);
	}

	public void setFechaInicial(String fechaInicial) {
		visualizarEvaluacionDTO.setFechaInicial(fechaInicial);
	}

	public void setFundamentacion(String fundamentacion) {
		visualizarEvaluacionDTO.setFundamentacion(fundamentacion);
	}

	public void setId(Long id) {
		visualizarEvaluacionDTO.setId(id);
	}

	public void setIdCronograma(Long idCronograma) {
		visualizarEvaluacionDTO.setIdCronograma(idCronograma);
	}

	public void setIdPlanTrabajo(Long idPlanTrabajo) {
		visualizarEvaluacionDTO.setIdPlanTrabajo(idPlanTrabajo);
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		visualizarEvaluacionDTO.setIdPresupuesto(idPresupuesto);
	}

	public void setIdProyecto(Long idProyecto) {
		visualizarEvaluacionDTO.setIdProyecto(idProyecto);
	}

	public void setIdWorkFlow(Long idWorkFlow) {
		visualizarEvaluacionDTO.setIdWorkFlow(idWorkFlow);
	}

	public void setObservacion(String observacion) {
		visualizarEvaluacionDTO.setObservacion(observacion);
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoDTO proyectoPresupuesto) {
		visualizarEvaluacionDTO.setProyectoPresupuesto(proyectoPresupuesto);
	}

	public void setRecomendacion(String recomendacion) {
		visualizarEvaluacionDTO.setRecomendacion(recomendacion);
	}

	public void setResultadoEvaluacion(EncryptedObject resultadoEvaluacion) {
		visualizarEvaluacionDTO.setResultadoEvaluacion(resultadoEvaluacion);
	}

	public void setResumido() {
		visualizarEvaluacionDTO.setResumido();
	}

	public void setResumido(Boolean resumido) {
		visualizarEvaluacionDTO.setResumido(resumido);
	}

	public void setTipo(TipoEvaluacion tipo) {
		visualizarEvaluacionDTO.setTipo(tipo);
	}

	public void setTipos(String tipos) {
		visualizarEvaluacionDTO.setTipos(tipos);
	}

	public String toString() {
		return visualizarEvaluacionDTO.toString();
	}

	public String getFechaEntregaComprometida() {
		return evaluacionGeneral.getFechaEntregaComprometida();
	}

	public TipoEvaluacionFinanciera getTipoEvaluacionFinanciera() {
		return evaluacionGeneral.getTipoEvaluacionFinanciera();
	}

	public EvaluacionGeneralDTO getEvaluacionGeneral() {
		return evaluacionGeneral;
	}

	public void setEvaluacionGeneral(EvaluacionGeneralDTO evaluacionGeneral) {
		this.evaluacionGeneral = evaluacionGeneral;
	}
	
	public String getIdTipoProyecto() {
		return evaluacionGeneral.getProyecto().getId();
	}

	public String getMontoFontarAprobado() {
		return visualizarEvaluacionDTO.getMontoFontarAprobado();
	}

	public String getMontoFontarSolicitado() {
		return visualizarEvaluacionDTO.getMontoFontarSolicitado();
	}

	public String getMontoTotalAprobado() {
		return visualizarEvaluacionDTO.getMontoTotalAprobado();
	}

	public String getMontoTotalSolicitado() {
		return visualizarEvaluacionDTO.getMontoTotalSolicitado();
	}

	public void setMontoFontarAprobado(String montoFontarAprobado) {
		visualizarEvaluacionDTO.setMontoFontarAprobado(montoFontarAprobado);
	}

	public void setMontoFontarSolicitado(String montoFontarSolicitado) {
		visualizarEvaluacionDTO.setMontoFontarSolicitado(montoFontarSolicitado);
	}

	public void setMontoTotalAprobado(String montoTotalAprobado) {
		visualizarEvaluacionDTO.setMontoTotalAprobado(montoTotalAprobado);
	}

	public void setMontoTotalSolicitado(String montoTotalSolicitado) {
		visualizarEvaluacionDTO.setMontoTotalSolicitado(montoTotalSolicitado);
	}
}
