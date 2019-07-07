package com.fontar.data.impl.assembler;

import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * Assembler para la cabecera de Evaluación de Seguimiento. 
 * @author ssanchez
 */
public class EvaluacionSeguimientoCabeceraAssembler {
	
	public EvaluacionSeguimientoCabeceraDTO buildDTO(EvaluacionGeneralBean bean) {
		
		EvaluacionSeguimientoCabeceraDTO cabeceraDTO = new EvaluacionSeguimientoCabeceraDTO();
		
		cabeceraDTO.setIdProyecto(bean.getProyecto().getCodigo());
		cabeceraDTO.setIdInstrumento(bean.getProyecto().getInstrumento().getIdentificador());
		cabeceraDTO.setIdSeguimiento(bean.getBitacora().getIdSeguimiento().toString());
		cabeceraDTO.setIdEvaluacion(bean.getId().toString());
		cabeceraDTO.setEstado(bean.getEstado());
		cabeceraDTO.setFecha(DateTimeUtil.formatDate(bean.getFecha()));
		
		cabeceraDTO.setTipo(bean.obtenerTiposDeEvaluacion());
		
		
		
//		Nombre de la entidad beneficiaria
		cabeceraDTO.setEntidadBeneficiaria(bean.getProyecto().getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
//		Nombre del seguimiento
		SeguimientoDAO seguimientoDAO = (SeguimientoDAO)ContextUtil.getBean("seguimientoDao");
		SeguimientoBean seguimiento = seguimientoDAO.read(bean.getBitacora().getIdSeguimiento());
		cabeceraDTO.setDescripcionDeSeguimiento(seguimiento.getDescripcion());
		return cabeceraDTO;
	}

}
