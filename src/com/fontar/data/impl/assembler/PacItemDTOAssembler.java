package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

/**
 * Clase para copiar los datos desde una <code>NotificacionBean</code>
 * a los DTO's que pueda tener el objeto <code>Notificacion</code>.<br> 
 * @author ssanchez
 */
public class PacItemDTOAssembler {

	/**
	 * Llena una <code>PacItemDTO</code> con los datos
	 * del bean <code>PacBean</code> que es recibido como
	 * parámetro.<br>
	 * @param bean
	 * @return dto
	 */
	public static PacItemDTO buildDto(PacBean bean) {
		PacItemDTO dto = new PacItemDTO();

		dto.setId(bean.getId());
		dto.setIdProyecto(bean.getIdProyecto());
		dto.setProyecto(bean.getProyecto());
		dto.setCodigo(bean.getItem());
		dto.setEtapa(bean.getEtapa());
		dto.setIdRubro(bean.getIdRubro());
		dto.setRubro(bean.getRubro());
		dto.setMontoEstimadoFontarDTO(StringUtil.formatTwoDecimalForPresentation(bean.getMontoFontar()));
		dto.setIdTipoAdquisicion(bean.getIdTipoAdquisicion());
		dto.setFechaEstimadaCompraDTO(DateTimeUtil.formatDate(bean.getFecha()));
		dto.setDescripcion(bean.getDescripcion());
		dto.setEstado(bean.getCodigoEstado());
		dto.setFechaEstado(DateTimeUtil.formatDate(bean.getFechaEstado()));
		dto.setObservaciones(bean.getObservaciones());
		
		return dto;
	}
	
	/**
	 * Devuelve una lista de <code>PacItemDTO</code>
	 * obtenidas a partir de una lista
	 * de <code>PacBean</code>.<br>
	 * @param listaBeans
	 * @return lista de items con formato PacItemDTO
	 */
	public static List<PacItemDTO> buildListDTO(List<PacBean> listaBeans) {
		
		List<PacItemDTO> listaDTOs = new ArrayList<PacItemDTO>();
		
		Iterator iter = listaBeans.iterator();
		while (iter.hasNext()) {
			listaDTOs.add(buildDto((PacBean)iter.next()));
		}
		
		return listaDTOs;
	}
}
