package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoFilaControlPaqueteDTO;

/**
 * Assembler para crear un ProyectoFilaDTO a partir de ProyectoPaqueteBean
 * 
 * @author gboaglio
 */
public class ProyectoFilaControlPaqueteDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 * @param bean
	 * @return dto
	 */
	public static ProyectoFilaControlPaqueteDTO buildDto(ProyectoBean proyectoBean, boolean activo) {
		ProyectoFilaControlPaqueteDTO dto = new ProyectoFilaControlPaqueteDTO();

		dto.setIdProyecto(proyectoBean.getId());
		dto.setRecomendacion(proyectoBean.getRecomendacion());
		dto.setEntidadBeneficiaria(proyectoBean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		dto.setTitulo(proyectoBean.getProyectoDatos().getTitulo());
		dto.setEsActivo(activo);

		return dto;
	}

	/**
	 * Crea lista de dto a partir de lista de beans
	 * 
	 * @param List of beans : Lista de ProyectoBean's que se va usar para
	 * obtener una lista de DTO's
	 * 
	 * @param boolean : activo indica el seteo que se le dará al atributo
	 * "esActivo" a TODOS los DTO que se van a crear a partir de los beans
	 * pasados como parámetro
	 * 
	 * @return List of dto
	 */
	public static List buildDto(List beans, boolean activo) {
		List dtos = new ArrayList();
		if (beans != null) {
			for (Iterator iter = beans.iterator(); iter.hasNext();) {
				ProyectoBean bean = (ProyectoBean) iter.next();
				dtos.add(ProyectoFilaControlPaqueteDTOAssembler.buildDto(bean, activo));
			}
		}
		return dtos;
	}
}
