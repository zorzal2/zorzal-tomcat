package com.fontar.bus.impl.instrumento;

import java.util.List;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.InstrumentoDefDAO;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.dto.InstrumentoVisualizacionDTO;
import com.fontar.seguridad.acegi.SecuredService;

/**
 * Servicio para la administración de Instrumentos
 * @author ssanchez
 * @version 1.00, 30/11/06
 */
 
@SecuredService
public class AdministrarInstrumentosServicioImpl implements AdministrarInstrumentosServicio {

	InstrumentoDAO instrumentoDao;

	InstrumentoDefDAO instrumentoDefDao;

	public void setInstrumentoDao(InstrumentoDAO instrumentoDao) {
		this.instrumentoDao = instrumentoDao;
	}

	/**
	 * Obtiene una lista de instrumentos activos/abiertos
	 */
	public List<InstrumentoBean> obtenerInstrumentos() {

		List<InstrumentoBean> instrumentoList = instrumentoDao.findAllActivos();

		return instrumentoList;
	}

	public InstrumentoVisualizacionDTO obtenerDatosVisualizacion(Long idInstrumento, Boolean isBeanInstrumento) {
		InstrumentoVisualizacionDTO visualizacionDTO = new InstrumentoVisualizacionDTO();
		if (isBeanInstrumento) {
				InstrumentoBean bean = instrumentoDao.read(idInstrumento);
				visualizacionDTO.setId(bean.getId().toString());
				visualizacionDTO.setIdentificador(bean.getIdentificador());
		}
		else {
			InstrumentoDefBean bean = instrumentoDefDao.read(idInstrumento);
			visualizacionDTO.setId(bean.getId().toString());
			visualizacionDTO.setIdentificador(bean.getIdentificador());
		}
		
		return visualizacionDTO;
	}

	public void setInstrumentoDefDao(InstrumentoDefDAO instrumentoDefDao) {
		this.instrumentoDefDao = instrumentoDefDao;
	}
	
	/**
	 * Obtiene el <code>InstrumentoBean</code> correspondiente
	 * al parámetro <i>idInstrumento</i>.<br>
	 * @param idLlamado
	 * @return el instrumento...
	 */
	public InstrumentoBean obtenerInstrumento(Long idInstrumento) {
		
		return instrumentoDao.read(idInstrumento);
	}	
}
