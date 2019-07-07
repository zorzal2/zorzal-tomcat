package com.fontar.bus.impl.proyecto;

import com.fontar.bus.api.proyecto.BaseProyectoServicio;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.assembler.ProyectoAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoDTO;

public class BaseProyectoServicioImpl implements BaseProyectoServicio {

	protected ProyectoDAO proyectoDao;

	public void setProyectoDao(ProyectoDAO proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	@SuppressWarnings("unchecked")
	public ProyectoDTO obtenerProyecto(Long idProyecto) {
		ProyectoBean bean = (ProyectoBean) proyectoDao.read(idProyecto);
		ProyectoDTO dto = ProyectoAssembler.buildDto(bean);
		return dto;
	}
}
