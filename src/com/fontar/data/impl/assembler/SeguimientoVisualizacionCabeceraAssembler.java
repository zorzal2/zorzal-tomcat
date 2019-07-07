package com.fontar.data.impl.assembler;

import java.util.Date;

import com.fontar.data.api.assembler.SeguimientoGeneralAssembler;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.pragma.util.DateTimeUtil;

/**
 * Assembler para armar Dto de cabecera de seguimientos
 * @author gboaglio
 * 
 */

public class SeguimientoVisualizacionCabeceraAssembler implements SeguimientoGeneralAssembler {
	private static SeguimientoVisualizacionCabeceraAssembler instance = new SeguimientoVisualizacionCabeceraAssembler();

	public static SeguimientoVisualizacionCabeceraAssembler getInstance() {
		return instance;
	}
	
	private SeguimientoVisualizacionCabeceraAssembler() {}

	public SeguimientoVisualizacionCabeceraDTO buildDTO(SeguimientoBean bean) {
		
		SeguimientoVisualizacionCabeceraDTO seguimientoVisualizacionCabeceraDTO = new SeguimientoVisualizacionCabeceraDTO();
		
		// Armo la cabecera con los datos del proyecto
		ProyectoCabeceraAssembler cabeceraProyectoAssembler = new ProyectoCabeceraAssembler();
		ProyectoCabeceraDTO cabeceraProyecto = cabeceraProyectoAssembler.buildDTO(bean.getProyecto()); 
		seguimientoVisualizacionCabeceraDTO.setCabeceraProyecto(cabeceraProyecto);
		
		// Agrego los datos del seguimiento
		seguimientoVisualizacionCabeceraDTO.setNumeroSeguimiento(bean.getId().toString());
		
		Date fechaSeguimiento = bean.getFecha();				
		String fecha = DateTimeUtil.formatDate(fechaSeguimiento);
		seguimientoVisualizacionCabeceraDTO.setFecha(fecha);
		
		seguimientoVisualizacionCabeceraDTO.setDescripcion(bean.getDescripcion());
		
		seguimientoVisualizacionCabeceraDTO.setDescripcionEstado(bean.getEstado().getDescripcion());
		
		return seguimientoVisualizacionCabeceraDTO;
	}
}
