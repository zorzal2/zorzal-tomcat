package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.ExpedienteMovimientoBean;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;

/**
 * Assembler para armar Dto de expediente Movimiento
 * @author ttoth
 * @version 1.00, 01/03/07
 */

public class ExpedienteMovimientoAssembler {


	private static class SingletonHolder {
		private static ExpedienteMovimientoAssembler instance = new ExpedienteMovimientoAssembler();
	}

	public static ExpedienteMovimientoAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	public ExpedienteMovimientoDTO buildDto(ExpedienteMovimientoBean bean) {
		ExpedienteMovimientoDTO movimientoDTO = new ExpedienteMovimientoDTO();
		
		movimientoDTO.setId(bean.getId());
		movimientoDTO.setFecha(bean.getFecha());
		movimientoDTO.setFechaDevolucion(bean.getFechaDevolucion());
		movimientoDTO.setIdPersona(bean.getIdPersona());
		movimientoDTO.setObservacion(bean.getObservacion());
		movimientoDTO.setUbicacion(bean.getUbicacion());

		return movimientoDTO;
	}
}
