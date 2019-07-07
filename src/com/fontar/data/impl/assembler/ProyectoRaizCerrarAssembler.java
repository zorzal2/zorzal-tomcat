package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;

/**
 * Dto para Cierre de Proyecto / Idea Proyecto
 * @author gboaglio
 * 
 */
public class ProyectoRaizCerrarAssembler {

	public static ProyectoRaizCerrarDTO buildDto(ProyectoBean bean) {
		ProyectoRaizCerrarDTO dto = new ProyectoRaizCerrarDTO();

		dto.setEstadoEnPaquete(bean.getEstadoEnPaquete().toString());
		dto.setClase("Proyecto");

		return dto;
	}

	public static ProyectoRaizCerrarDTO buildDto(IdeaProyectoBean bean) {
		ProyectoRaizCerrarDTO dto = new ProyectoRaizCerrarDTO();

		dto.setEstadoEnPaquete("false");
		dto.setClase("IdeaProyecto");

		return dto;
	}

	public static ProyectoRaizCerrarDTO buildDto(ProyectoRaizBean proyecto) {
		if (proyecto instanceof ProyectoBean) {
			return buildDto((ProyectoBean) proyecto);
		}
		else {
			return buildDto((IdeaProyectoBean) proyecto);
		}
	}

}
