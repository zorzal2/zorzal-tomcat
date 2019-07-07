package com.fontar.data.api.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.ControlFacturasDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionesDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastoPorConceptoYRubroDTO;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

/** 
 * @author gboaglio
 */
@HasNamedParams
public interface SeguimientoDAO extends GenericDao<SeguimientoBean, Long> {

	public Collection getSeguimientos();

	List<ResumenDeRendicionesDTO> findResumenRendicionesCompleto(Long idSeguimiento);
	
	List<RendicionCuentasBean> findRendicionPorSeguimiento(Long idSeguimiento);
	
	List<AnalisisDeGastoPorConceptoYRubroDTO> findRendicionActualSolicitada(Long idSeguimiento);
	List<AnalisisDeGastoPorConceptoYRubroDTO> findMontoInversionRendidoAnteriormente(@ParamName("idSeguimiento") Long idSeguimiento, @ParamName("idProyecto")  Long idProyecto);
	
	List<AnalisisDeGastoPorConceptoYRubroDTO> findRendicionActualAprobada(@ParamName("idSeguimiento") Long idSeguimiento);	
	List<AnalisisDeGastoPorConceptoYRubroDTO> findMontoInversionAprobadoAnteriormente(@ParamName("idSeguimiento") Long idSeguimiento, @ParamName("idProyecto") Long idProyecto);

	List<AnalisisDeGastoPorConceptoYRubroDTO> findRendicionActualGestionada(@ParamName("idSeguimiento") Long idSeguimiento);	

	List<AnalisisDeGastoPorConceptoYRubroDTO> findRendicionActualAGestionar(@ParamName("idSeguimiento") Long idSeguimiento);	
	
	List<ControlFacturasDTO> findFacturasRepetidas(Long idSeguimiento);
	
	Object selectMontoParteGestionSeguimientosAprobados(Long idProyecto);
	
	Object selectMontoParteEvaluacionSeguimientosAprobados(Long idProyecto);
	
	Object selectMontoAprobadoSeguimiento(Long idSeguimiento);
	
	Object selectMontoRendidoSeguimiento(Long idSeguimiento);

	BigDecimal selectContraparteRendicionAprobadaDelProyecto(Long idProyecto);
	
	ResumenDeRendicionCompactoDTO selectResumenRendicionCompacto(@ParamName("idSeguimiento") Long idSeguimiento);
}
