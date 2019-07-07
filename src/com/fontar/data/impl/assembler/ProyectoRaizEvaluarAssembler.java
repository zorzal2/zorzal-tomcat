package com.fontar.data.impl.assembler;

import com.fontar.data.Constant.InstanciaProyectoRaiz;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;

/**
 * Dto para proyectos
 * @author gboaglio
 * 
 */
public class ProyectoRaizEvaluarAssembler {

	@SuppressWarnings("unchecked")
	public static ProyectoRaizEvaluarDTO buildDto(ProyectoBean bean) {
		ProyectoRaizEvaluarDTO dto = new ProyectoRaizEvaluarDTO();

		dto.setId(bean.getId() == null ? "" : bean.getId().toString());
		dto.setCodigo(bean.getCodigo());
		dto.setEstado((EstadoProyecto) bean.getEstado());
		dto.setClase(InstanciaProyectoRaiz.PROYECTO);

		return dto;
	}

	@SuppressWarnings("unchecked")
	public static ProyectoRaizEvaluarDTO buildDto(IdeaProyectoBean bean) {
		ProyectoRaizEvaluarDTO dto = new ProyectoRaizEvaluarDTO();

		dto.setId(bean.getId() == null ? "" : bean.getId().toString());
		dto.setCodigo(bean.getCodigo());
		dto.setEstado((EstadoIdeaProyecto) bean.getEstado());
		dto.setClase(InstanciaProyectoRaiz.IDEA_PROYECTO);

		return dto;
	}

	public static ProyectoRaizEvaluarDTO buildDto(ProyectoRaizBean proyecto) {
		if (proyecto instanceof ProyectoBean) {
			return buildDto((ProyectoBean) proyecto);
		}
		else {
			return buildDto((IdeaProyectoBean) proyecto);
		}
	}

}
