package com.fontar.bus.api.intrumento;

import java.util.List;

import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.dto.InstrumentoVisualizacionDTO;

/**
 * Servicio para la administracion de Instrumentos de Beneficio.
 */
public interface AdministrarInstrumentosServicio {

	/**
	 *Obtiene una lista de todos los instrumentos de beneficio. 
	 * @return
	 */
	public List<InstrumentoBean> obtenerInstrumentos();

	/**
	 * A aparir de un identificador y segun se indique isBeanInstrumento, 
	 * en caso verdadero se asume que es un identificador de instrumento y se obtiene el DTO para visualizacion.
	 * En caso contrario se asume que es un identificador de definicion de instrumento y se obtiene el DTO para su visualizacion.
	 * @param idInstrumento
	 * @param isBeanInstrumento
	 * @return
	 */
	public InstrumentoVisualizacionDTO obtenerDatosVisualizacion(Long idInstrumento, Boolean isBeanInstrumento);
	
	/**
	 * Obtiene el InstrumentoBean correspondiente al parámetro idInstrumento.
	 * @param idLlamado
	 */
	public InstrumentoBean obtenerInstrumento(Long idInstrumento);
}