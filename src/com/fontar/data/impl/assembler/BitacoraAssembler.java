package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.pragma.util.DateTimeUtil;

public class BitacoraAssembler {

	public static BitacoraDTO buildDto (BitacoraBean bean)  {

		BitacoraDTO dto = new BitacoraDTO();

		dto.setId(bean.getId());
		dto.setIdProyecto(bean.getIdProyecto());
		dto.setIdSeguimiento(bean.getIdSeguimiento());
		dto.setIdEvaluacion(bean.getIdEvaluacion());
		dto.setIdUsuario(bean.getIdUsuario());
		dto.setFechaAsunto(DateTimeUtil.formatDate(bean.getFechaAsunto()));
		dto.setTipo(bean.getTipo());
		dto.setFechaRegistro(DateTimeUtil.formatDateTime(bean.getFechaRegistro()));
		dto.setTema(bean.getTema());
		dto.setDescripcion(bean.getDescripcion());

		return dto;
	}
}