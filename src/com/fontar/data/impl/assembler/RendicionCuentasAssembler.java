package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.pragma.util.MathUtils;

public class RendicionCuentasAssembler {

	private static class SingletonHolder {
		private static RendicionCuentasAssembler instance = new RendicionCuentasAssembler();
	}

	public static RendicionCuentasAssembler getInstance() {
		return SingletonHolder.instance;

	}
	
	public RendicionCuentasDTO buildDto(RendicionCuentasBean bean) {
		RendicionCuentasDTO rendicionCuentasDTO = new RendicionCuentasDTO();
		buildDto(bean, rendicionCuentasDTO);
		return rendicionCuentasDTO;
	}
	
	public void buildDto(RendicionCuentasBean bean, RendicionCuentasDTO rendicionCuentasDTO) {
		rendicionCuentasDTO.setId(bean.getId());
		rendicionCuentasDTO.setDescripcion(bean.getDescripcion());
		rendicionCuentasDTO.setFecha(bean.getFecha());
		rendicionCuentasDTO.setIdPersona(bean.getIdPersona());
		rendicionCuentasDTO.setIdRubro(bean.getIdRubro());
		rendicionCuentasDTO.setIdSeguimiento(bean.getIdSeguimiento());
		rendicionCuentasDTO.setMesesParticipacion(bean.getMesesParticipacion());
		if(bean.getSeguimiento().getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) {
			//Hay que computar la alicuota
			BigDecimal porcentajeAlicuotaSolicitada = bean.getSeguimiento().getProyecto().getPorcentajeAlicuotaSolicitada();
			if(porcentajeAlicuotaSolicitada==null) porcentajeAlicuotaSolicitada = BigDecimal.ZERO;
			
			if(bean.getMontoTotal()==null) {
				rendicionCuentasDTO.setMontoParteRendicion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoContraparteRendicion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoTotal(BigDecimal.ZERO);
			} else {
				BigDecimal montoParteRendicion = MathUtils.getPorcientoDe(porcentajeAlicuotaSolicitada, bean.getMontoTotal()); 
				rendicionCuentasDTO.setMontoParteRendicion(montoParteRendicion);
				rendicionCuentasDTO.setMontoContraparteRendicion(bean.getMontoTotal().subtract(montoParteRendicion));
				rendicionCuentasDTO.setMontoTotal(bean.getMontoTotal());
			}
			
			BigDecimal porcentajeAlicuotaAdjudicada = bean.getSeguimiento().getProyecto().getPorcentajeAlicuotaAdjudicada();
			if(porcentajeAlicuotaAdjudicada==null) porcentajeAlicuotaSolicitada = BigDecimal.ZERO;
			
			if(bean.getMontoTotalEvaluacion()==null) {
				rendicionCuentasDTO.setMontoParteEvaluacion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoContraparteEvaluacion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoTotalEvaluacion(BigDecimal.ZERO);				
			} else {
				BigDecimal montoParteEvaluacion = MathUtils.getPorcientoDe(porcentajeAlicuotaAdjudicada, bean.getMontoTotalEvaluacion());
				rendicionCuentasDTO.setMontoParteEvaluacion(montoParteEvaluacion);
				rendicionCuentasDTO.setMontoContraparteEvaluacion(bean.getMontoTotalEvaluacion().subtract(montoParteEvaluacion));
				rendicionCuentasDTO.setMontoTotalEvaluacion(bean.getMontoTotalEvaluacion());
			}

			if(bean.getMontoTotalGestion()==null) {
				rendicionCuentasDTO.setMontoParteGestion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoContraparteGestion(BigDecimal.ZERO);
				rendicionCuentasDTO.setMontoTotalGestion(BigDecimal.ZERO);
			} else {
				BigDecimal montoParteGestion = MathUtils.getPorcientoDe(porcentajeAlicuotaAdjudicada, bean.getMontoTotalGestion());
				rendicionCuentasDTO.setMontoParteGestion(montoParteGestion);
				rendicionCuentasDTO.setMontoContraparteGestion(bean.getMontoTotalGestion().subtract(montoParteGestion));
				rendicionCuentasDTO.setMontoTotalGestion(bean.getMontoTotalGestion());
			}
		} else {
			//Computo normal
			rendicionCuentasDTO.setMontoParteRendicion(bean.getMontoParteRendicion());
			rendicionCuentasDTO.setMontoContraparteRendicion(bean.getMontoContraparteRendicion());
			rendicionCuentasDTO.setMontoTotal(bean.getMontoTotal());
			
			rendicionCuentasDTO.setMontoParteEvaluacion(bean.getMontoParteEvaluacion());
			rendicionCuentasDTO.setMontoContraparteEvaluacion(bean.getMontoContraparteEvaluacion());
			rendicionCuentasDTO.setMontoTotalEvaluacion(bean.getMontoTotalEvaluacion());
			
			rendicionCuentasDTO.setMontoParteGestion(bean.getMontoParteGestion());
			rendicionCuentasDTO.setMontoContraparteGestion(bean.getMontoContraparteGestion());
			rendicionCuentasDTO.setMontoTotalGestion(bean.getMontoTotalGestion());
		}

		rendicionCuentasDTO.setMontoSueldoMensual(bean.getMontoSueldoMensual());
		rendicionCuentasDTO.setNombrePersona(bean.getPersona()==null? "":bean.getPersona().getNombre());
		rendicionCuentasDTO.setNombreProveedor(bean.getNombreProveedor());
		rendicionCuentasDTO.setNombreRubro(bean.getRubro().getNombre());
		rendicionCuentasDTO.setTipo(bean.getRubro().getTipoRendicion());
		rendicionCuentasDTO.setNumeroFactura(bean.getNumeroFactura());
		rendicionCuentasDTO.setNumeroRecibo(bean.getNumeroRecibo());
		rendicionCuentasDTO.setObservaciones(bean.getObservaciones());
		rendicionCuentasDTO.setPaisProveedor(bean.getPaisProveedor());
		rendicionCuentasDTO.setPorcentajeDedicacion(bean.getPorcentajeDedicacion());
		rendicionCuentasDTO.setProfesionPersona(bean.getProfesionPersona());
		rendicionCuentasDTO.setTieneCertificadoProveedor(bean.getTieneCertificadoProveedor());
	}

	public List<RendicionCuentasDTO> buildDto(List<RendicionCuentasBean> beans) {
		List<RendicionCuentasDTO> list = new ArrayList<RendicionCuentasDTO>(beans.size());
		for(RendicionCuentasBean bean : beans) {
			list.add(buildDto(bean));
		}
		return list;
	}
}
