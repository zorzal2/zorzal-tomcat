package com.fontar.bus.impl.seguimientos.seguimientos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.CannotCreateTransactionException;

import com.fontar.bus.api.proyecto.desembolso.ProyectoDesembolsoService;
import com.fontar.bus.api.seguimientos.seguimientos.AnalisisGastosSeguimientoServicio;
import com.fontar.data.Constant.BitacoraTema;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.EvaluacionSeguimientoDAO;
import com.fontar.data.api.dao.RendicionCuentasDAO;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.assembler.SeguimientoVisualizacionCabeceraAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.instrumentoDef.TipoInstrumentoDef;
import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastoPorConceptoYRubroDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastosDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastosPorConceptoDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.CalculosDeAnalisisDeGastosDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.CuadroDeAnalisisDeGastosDTO;
import com.fontar.seguridad.cripto.AccesoDenegadoException;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.Utils;


/**
 * Servicio para el Análisis de Gastos de un seguimiento
 * 
 * @author gboaglio, llobeto
 * 
 */
public class AnalisisGastosSeguimientoServicioImpl implements AnalisisGastosSeguimientoServicio {
	
	private SeguimientoDAO seguimientoDao;		
	private RubroDAO rubroDao;
	private EvaluacionDAO evaluacionDAO;
	private EvaluacionSeguimientoDAO evaluacionSeguimientoDAO;
	private RendicionCuentasDAO rendicionCuentasDao;
	private ProyectoDesembolsoService proyectoDesembolsoService;
	private BitacoraDAO bitacoraDao;
	
	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public void setRendicionCuentasDao(RendicionCuentasDAO rendicionCuentasDao) {
		this.rendicionCuentasDao = rendicionCuentasDao;
	}
	
	public void setEvaluacionSeguimientoDAO(EvaluacionSeguimientoDAO evaluacionSeguimientoDAO) {
		this.evaluacionSeguimientoDAO = evaluacionSeguimientoDAO;
	}

	/**
	 * Devuelve un DTO con los datos del cuadro de análisis de pertinencia de gastos
	 * para el seguimiento con el id dado.
	 */
	private CuadroDeAnalisisDeGastosDTO obtenerCuadroDeAnalisisDeGastos(SeguimientoBean seguimiento, ConceptoDeAnalisisDeGasto... conceptos) {

		Long idProyecto = seguimiento.getIdProyecto();
		ProyectoPresupuestoBean presupuestoActual   = seguimiento.getProyecto().getProyectoPresupuesto();
		
		// Si el proyecto no tiene presupuesto cargado, devuelvo un dto nulo.		
		// Este caso no debería darse en el flujo normal de la aplicación, pero de cualquier 
		// forma acá se tiene en cuenta para evitar errores en la aplicación debido a datos 
		// de prueba mal cargados en la base.
		if (presupuestoActual != null) {
			
			List<AnalisisDeGastosPorConceptoDTO> gastosPorConcepto =  new ArrayList<AnalisisDeGastosPorConceptoDTO>();
			
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto)) {
				gastosPorConcepto.add(obtenerCuadroCostosTotalesAnalisis(seguimiento));
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.MontoDeInversionRendidoAnteriormente)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findMontoInversionRendidoAnteriormente(seguimiento.getId(), idProyecto)
					,	ConceptoDeAnalisisDeGasto.MontoDeInversionRendidoAnteriormente
				);
				gastosPorConcepto.add(bloque);
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findMontoInversionAprobadoAnteriormente(seguimiento.getId(), idProyecto)
					,	ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente
				);
				gastosPorConcepto.add(bloque);
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.RendicionActualSolicitada)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findRendicionActualSolicitada(seguimiento.getId())
					,	ConceptoDeAnalisisDeGasto.RendicionActualSolicitada
				);
				gastosPorConcepto.add(bloque);
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.RendicionActualAprobada)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findRendicionActualAprobada(seguimiento.getId())
					,	ConceptoDeAnalisisDeGasto.RendicionActualAprobada
				);
				gastosPorConcepto.add(bloque);
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.RendicionActualGestionada)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findRendicionActualGestionada(seguimiento.getId())
						,	ConceptoDeAnalisisDeGasto.RendicionActualGestionada
				);
				gastosPorConcepto.add(bloque);
			}
			if(CollectionUtils.contains(conceptos, ConceptoDeAnalisisDeGasto.RendicionActualAGestionar)) {
				AnalisisDeGastosPorConceptoDTO bloque = new AnalisisDeGastosPorConceptoDTO(
						seguimientoDao.findRendicionActualAGestionar(seguimiento.getId())
					,	ConceptoDeAnalisisDeGasto.RendicionActualAGestionar
				);
				gastosPorConcepto.add(bloque);
			}
			CuadroDeAnalisisDeGastosDTO cuadro = new CuadroDeAnalisisDeGastosDTO(gastosPorConcepto);
			cuadro.setDesplegarEnParteYContraparte(
					!seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()
				);
			return cuadro;
		}
		return null;
	}
	
	/**	 
	 *	 A partir del id de seguimiento, obtiene el presupuesto actual u original 
	 *   del proyecto según corresponda y con sus datos construye el 
	 *   cuadro de "Costos Totales del Proyecto" para el Análisis
	 */
	private AnalisisDeGastosPorConceptoDTO obtenerCuadroCostosTotalesAnalisis(SeguimientoBean seguimiento) {

		List<AnalisisDeGastoPorConceptoYRubroDTO> listaCostosTotales = costosTotalesPorRubro();
		
		ProyectoPresupuestoBean presupuesto = seguimiento.getProyecto().getProyectoPresupuesto();
		PresupuestoRubroCollectionBean  costosTotales = presupuesto.getPresupuestoRubros();
		
		for(PresupuestoRubroBean costoRubro : costosTotales) {
			boolean esNuevo = true; 
			//procesa todas las hojas del presupuesto y actualiza la lista o las agrega segun corresponda
			if (costoRubro.getRubro().esHoja()) {
				Long idRubro = costoRubro.getRubro().getId();
				BigDecimal montoParte = new BigDecimal(costoRubro.getMontoParte());
				BigDecimal montoContraparte = new BigDecimal(costoRubro.getMontoContraparte());			
				BigDecimal costoTotal = montoParte.add(montoContraparte);
				
				//Si existe como rubro padre, inicializa los datos, sino lo agrega (es una hoja que luego se agrupa en un padre)
				for(AnalisisDeGastoPorConceptoYRubroDTO dto : listaCostosTotales) {
					if (dto.getRubro().getId().equals(idRubro)) {
						dto.setMontoFontar(montoParte);
						dto.setMontoContraparte(montoContraparte);
						dto.setCostoTotal(costoTotal);
						esNuevo = false;
					}
				}
				if (esNuevo) {
					AnalisisDeGastoPorConceptoYRubroDTO dto = new AnalisisDeGastoPorConceptoYRubroDTO(costoRubro.getRubro(), montoParte, montoContraparte);
					listaCostosTotales.add(dto);
				}
			}
		}
		return new AnalisisDeGastosPorConceptoDTO(listaCostosTotales, ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto);
	}

	private List<AnalisisDeGastoPorConceptoYRubroDTO> costosTotalesPorRubro() {
		List<RubroBean> rubros = this.rubroDao.findSinPadresOrdenados();
		List<AnalisisDeGastoPorConceptoYRubroDTO> listaCostosTotales = new ArrayList<AnalisisDeGastoPorConceptoYRubroDTO>();
		for(RubroBean rubro : rubros) {
			AnalisisDeGastoPorConceptoYRubroDTO dto = new AnalisisDeGastoPorConceptoYRubroDTO(rubro, BigDecimal.ZERO, BigDecimal.ZERO);
			listaCostosTotales.add(dto);
		}
		return listaCostosTotales;
	}
	
	public ResumenRendicionesPorProyecto rendicionCuentasAnalisisGastosSeguimiento(Long idEvaluacion){

		EvaluacionBean evaluacion = this.evaluacionDAO.read( idEvaluacion );
		ProyectoPresupuestoBean presupuesto = evaluacion.getProyecto().getProyectoPresupuesto();
		BigDecimal rendicionEnEvaluacion =  (BigDecimal) this.seguimientoDao.selectMontoParteEvaluacionSeguimientosAprobados( evaluacion.getIdProyecto() );
		BigDecimal rendicionEnGestion  =  (BigDecimal) this.seguimientoDao.selectMontoParteGestionSeguimientosAprobados( evaluacion.getIdProyecto());
		
		BigDecimal totalRendido =  (BigDecimal) this.seguimientoDao.selectMontoRendidoSeguimiento( evaluacion.getBitacora().getIdSeguimiento());
		BigDecimal totalAprobado  =  (BigDecimal) this.seguimientoDao.selectMontoAprobadoSeguimiento( evaluacion.getBitacora().getIdSeguimiento());
		
		ResumenRendicionesPorProyecto rendiciones = new ResumenRendicionesPorProyecto(presupuesto,rendicionEnEvaluacion, rendicionEnGestion );
		rendiciones.setTotalAprobadoSeguimientoActual(totalAprobado);
		rendiciones.setTotalRendidoSeguimientoActual( totalRendido );
		return rendiciones;
	}
	
	/** Getters y Setters **/
	
	public SeguimientoDAO getSeguimientoDao() {
		return seguimientoDao;
	}

	public void setSeguimientoDao(SeguimientoDAO seguimientoDao) {
		this.seguimientoDao = seguimientoDao;
	}
	
	public RubroDAO getRubroDao() {
		return rubroDao;
	}

	public void setRubroDao(RubroDAO rubroDao) {
		this.rubroDao = rubroDao;
	}

	public EvaluacionDAO getEvaluacionDAO() {
		return evaluacionDAO;
	}

	public void setEvaluacionDAO(EvaluacionDAO evaluacionDAO) {
		this.evaluacionDAO = evaluacionDAO;
	}

	public BigDecimal montoRendicionTotalAprobado(Long idProyecto) {
		List<RendicionCuentasBean> rendiciones = rendicionCuentasDao.findRendicionesBeanPorProyecto(idProyecto);
		BigDecimal total = BigDecimal.ZERO;
		for (RendicionCuentasBean rendicion : rendiciones) {
			if(rendicion.getSeguimiento().esPositivo()) {
				total = total.add(rendicion.getMontoParteAjustado());
			}
		}
		return total;
	}

	public EvaluacionSeguimientoDAO getEvaluacionSeguimientoDAO() {
		return evaluacionSeguimientoDAO;
	}

	public void setProyectoDesembolsoService(ProyectoDesembolsoService proyectoDesembolsoService) {
		this.proyectoDesembolsoService = proyectoDesembolsoService;
	}

	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaVisualizarEvaluacion(Long idEvaluacionDeSeguimiento) throws AccesoDenegadoException {
		validateEncryptionContext();
		EvaluacionSeguimientoBean evaluacion = evaluacionSeguimientoDAO.read(idEvaluacionDeSeguimiento);
		SeguimientoBean seguimiento = evaluacion.getSeguimiento();

		AnalisisDeGastosDTO analisisDeGastos = new AnalisisDeGastosDTO(seguimiento.getId());
		analisisDeGastos.setCuadroDeAnalisisDeGastos(
				obtenerCuadroDeAnalisisDeGastos(
					seguimiento,
					ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
					ConceptoDeAnalisisDeGasto.MontoDeInversionRendidoAnteriormente,
					ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente,
					ConceptoDeAnalisisDeGasto.RendicionActualSolicitada,
					ConceptoDeAnalisisDeGasto.RendicionActualAprobada
				)
			);
		//Quito la diferencia autorizado-gestionado porque no corresponde
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarFontar(null);
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarContraparte(null);
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarTotal(null);
		
		analisisDeGastos.setFundamentacionFontar(Utils.nvl(evaluacion.getFundamentacion()));
		if(seguimiento.getProyecto().getInstrumento().getInstrumentoDef().getTipo().equals(TipoInstrumentoDef.CREDITO)) {
			analisisDeGastos.setCronogramaDeDesembolsos(proyectoDesembolsoService.obtenerCronogramaParaVisualizarEvaluacionDeSeguimiento(idEvaluacionDeSeguimiento));
			ponerCalculosParaVisualizarEvaluacion(analisisDeGastos, seguimiento);
			if(analisisDeGastos.getCalculos()!=null) {
				analisisDeGastos.getCalculos().setPermiteModificarPresupuestoSegunInformeDeAvance(false);
				analisisDeGastos.getCalculos().setPermiteModificarPendienteDeRendicion(false);
			}
		}
		return analisisDeGastos;
	}

	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaCargarResultadoDeEvaluacion(Long idEvaluacionDeSeguimiento) throws AccesoDenegadoException {
		validateEncryptionContext();
		EvaluacionSeguimientoBean evaluacion = evaluacionSeguimientoDAO.read(idEvaluacionDeSeguimiento);
		SeguimientoBean seguimiento = evaluacion.getSeguimiento();

		AnalisisDeGastosDTO analisisDeGastos = new AnalisisDeGastosDTO(seguimiento.getId());
		analisisDeGastos.setCuadroDeAnalisisDeGastos(
				obtenerCuadroDeAnalisisDeGastos(
					seguimiento,
					ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
					ConceptoDeAnalisisDeGasto.MontoDeInversionRendidoAnteriormente,
					ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente,
					ConceptoDeAnalisisDeGasto.RendicionActualSolicitada,
					ConceptoDeAnalisisDeGasto.RendicionActualAprobada
				)
			);
		//Quito la diferencia autorizado-gestionado porque no corresponde
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarFontar(null);
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarContraparte(null);
		analisisDeGastos.getCuadroDeAnalisisDeGastos().setPorGestionarTotal(null);
		
		analisisDeGastos.setFundamentacionFontar(Utils.nvl(evaluacion.getFundamentacion()));
		if(seguimiento.getProyecto().getInstrumento().getInstrumentoDef().getTipo().equals(TipoInstrumentoDef.CREDITO)) {
			analisisDeGastos.setCronogramaDeDesembolsos(proyectoDesembolsoService.obtenerCronogramaParaCargarResultadoDeEvaluacionDeSeguimiento(idEvaluacionDeSeguimiento));
			ponerCalculosParaCargarResultadoDeEvaluacion(analisisDeGastos, seguimiento);
			if(analisisDeGastos.getCalculos()!=null) {
				analisisDeGastos.getCalculos().setPermiteModificarPresupuestoSegunInformeDeAvance(true);
				analisisDeGastos.getCalculos().setPermiteModificarPendienteDeRendicion(true);
			}
		}
		return analisisDeGastos;
	}

	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaVisualizarSeguimiento(Long idSeguimiento) throws AccesoDenegadoException {
		validateEncryptionContext();
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);

		AnalisisDeGastosDTO analisisDeGastos = new AnalisisDeGastosDTO(idSeguimiento);
		analisisDeGastos.setCuadroDeAnalisisDeGastos(
				obtenerCuadroDeAnalisisDeGastos(
					seguimiento,
					ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
					ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente,
					ConceptoDeAnalisisDeGasto.RendicionActualSolicitada,
					ConceptoDeAnalisisDeGasto.RendicionActualAprobada,
					ConceptoDeAnalisisDeGasto.RendicionActualGestionada
				)
			);
		analisisDeGastos.setFundamentacionFontar(fundamentacionFontar(seguimiento));
		analisisDeGastos.setFundamentacionUFFA(fundamentacionUFFA(seguimiento));
		if(seguimiento.getProyecto().getInstrumento().getInstrumentoDef().getTipo().equals(TipoInstrumentoDef.CREDITO)) {
			CronogramaDeDesembolsosDTO cronograma;
			try {
				cronograma = proyectoDesembolsoService.obtenerCronogramaParaVisualizarSeguimiento(idSeguimiento);
			} catch(CannotCreateTransactionException e) {
				/*
				 * FIXME: Quitar este bloque cuando se resuelva el problema con la transaccion
				 * de Spring.
				 */
				cronograma = ((ProyectoDesembolsoService)ContextUtil.getBean("proyectoDesembolsoServiceTarget")).obtenerCronogramaParaVisualizarSeguimiento(idSeguimiento);
			}
			analisisDeGastos.setCronogramaDeDesembolsos(cronograma);
			ponerCalculosParaVisualizarSeguimiento(analisisDeGastos, seguimiento);
			if(analisisDeGastos.getCalculos()!=null) {
				analisisDeGastos.getCalculos().setPermiteModificarPresupuestoSegunInformeDeAvance(false);
				analisisDeGastos.getCalculos().setPermiteModificarPendienteDeRendicion(false);
			}
		}
		return analisisDeGastos;
	}

	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaEvaluarGestionDePago(Long idSeguimiento) throws AccesoDenegadoException {
		validateEncryptionContext();
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);

		AnalisisDeGastosDTO analisisDeGastos = new AnalisisDeGastosDTO(idSeguimiento);
		analisisDeGastos.setCuadroDeAnalisisDeGastos(
				obtenerCuadroDeAnalisisDeGastos(
					seguimiento,
					ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
					ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente,
					ConceptoDeAnalisisDeGasto.RendicionActualSolicitada,
					ConceptoDeAnalisisDeGasto.RendicionActualAprobada,
					ConceptoDeAnalisisDeGasto.RendicionActualAGestionar
				)
			);
		analisisDeGastos.setFundamentacionFontar(fundamentacionFontar(seguimiento));
		analisisDeGastos.setFundamentacionUFFA(fundamentacionUFFA(seguimiento));
		if(seguimiento.getProyecto().getInstrumento().getInstrumentoDef().getTipo().equals(TipoInstrumentoDef.CREDITO)) {
			analisisDeGastos.setCronogramaDeDesembolsos(proyectoDesembolsoService.obtenerCronogramaParaEvaluarGestionDePago(idSeguimiento));
			ponerCalculosParaEvaluarGestionDePago(analisisDeGastos, seguimiento);
			if(analisisDeGastos.getCalculos()!=null) {
				analisisDeGastos.getCalculos().setPermiteModificarPresupuestoSegunInformeDeAvance(true);
				analisisDeGastos.getCalculos().setPermiteModificarPendienteDeRendicion(true);
			}
		}
		return analisisDeGastos;
	}
	
	private CalculosDeAnalisisDeGastosDTO calculosParaVisualizarSeguimiento(CuadroDeAnalisisDeGastosDTO cuadroDeAnalisisDeGastos, CronogramaDeDesembolsosDTO cronograma, SeguimientoBean seguimiento) {
		//No esta definido para CFs
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) return null;
		
		CalculosDeAnalisisDeGastosDTO calculos = new CalculosDeAnalisisDeGastosDTO();
		//Presupuesto segun avance
		calculos.setPresupuestoSegunInformeDeAvance(getPresupuestoSegunInformeDeAvance(seguimiento, cronograma));
		//Monto desembolsado
		calculos.setMontoDesembolsado(cronograma.getMontoTotalDesembolsado());
		//Total rendido gestionado
		BigDecimal totalGestionadoFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualGestionada).getMontoFontarTotal();
		BigDecimal totalAprobadoAnteriorFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getMontoFontarTotal();
		calculos.setTotalRendidoGestionado(
				totalAprobadoAnteriorFontar.add(totalGestionadoFontar)
		);
		//Pendiente de rendicion
		if(seguimiento.getMontoPendienteDeRendicion()!=null) {
			calculos.setPendienteDeRendicion(seguimiento.getMontoPendienteDeRendicion());
		} else {
			calculos.setPendienteDeRendicion(
					calculos.getMontoDesembolsado().subtract(calculos.getTotalRendidoGestionado())
			);
		}
		//A pagar
		calcularAPagar(calculos);
		return calculos;
	}
	
	private CalculosDeAnalisisDeGastosDTO calculosParaFinalizarSeguimiento(CuadroDeAnalisisDeGastosDTO cuadroDeAnalisisDeGastos, CronogramaDeDesembolsosDTO cronograma, SeguimientoBean seguimiento) {
		
		CalculosDeAnalisisDeGastosDTO calculos = new CalculosDeAnalisisDeGastosDTO();
		//Presupuesto segun avance
		calculos.setPresupuestoSegunInformeDeAvance(getPresupuestoSegunInformeDeAvance(seguimiento, cronograma));
		//Monto desembolsado
		calculos.setMontoDesembolsado(cronograma.getMontoTotalDesembolsado());

		//Total rendido gestionado
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) {
			BigDecimal totalGestionado = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualGestionada).getMontoTotal();
			BigDecimal totalAprobadoAnterior = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getMontoTotal();
			calculos.setTotalRendidoGestionado(
					totalAprobadoAnterior.add(totalGestionado)
			);
		} else {
			BigDecimal totalGestionadoFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualGestionada).getMontoFontarTotal();
			BigDecimal totalAprobadoAnteriorFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getMontoFontarTotal();
			calculos.setTotalRendidoGestionado(
					totalAprobadoAnteriorFontar.add(totalGestionadoFontar)
			);
		}
		//Pendiente de rendicion
		if(seguimiento.getMontoPendienteDeRendicion()!=null) {
			calculos.setPendienteDeRendicion(seguimiento.getMontoPendienteDeRendicion());
		} else {
			calculos.setPendienteDeRendicion(
					calculos.getMontoDesembolsado().subtract(calculos.getTotalRendidoGestionado())
			);
		}

		//A pagar
		calcularAPagar(calculos);
		return calculos;
	}
	
	private CalculosDeAnalisisDeGastosDTO calculosParaEvaluarGestionDePago(CuadroDeAnalisisDeGastosDTO cuadroDeAnalisisDeGastos, CronogramaDeDesembolsosDTO cronograma, SeguimientoBean seguimiento) {
		//No esta definido para CFs
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) return null;
		
		CalculosDeAnalisisDeGastosDTO calculos = new CalculosDeAnalisisDeGastosDTO();
		//Presupuesto segun avance
		calculos.setPresupuestoSegunInformeDeAvance(getPresupuestoSegunInformeDeAvance(seguimiento, cronograma));
		//Monto desembolsado
		calculos.setMontoDesembolsado(cronograma.getMontoTotalDesembolsado());
		//Total rendido gestionado
		BigDecimal totalGestionadoFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAGestionar).getMontoFontarTotal();
		BigDecimal totalAprobadoAnteriorFontar = cuadroDeAnalisisDeGastos.analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getMontoFontarTotal();
		calculos.setTotalRendidoGestionado(
				totalAprobadoAnteriorFontar.add(totalGestionadoFontar)
		);
		//Pendiente de rendicion
		if(seguimiento.getMontoPendienteDeRendicion()!=null) {
			calculos.setPendienteDeRendicion(seguimiento.getMontoPendienteDeRendicion());
		} else {
			calculos.setPendienteDeRendicion(
					calculos.getMontoDesembolsado().subtract(calculos.getTotalRendidoGestionado())
			);
		}
		//A pagar
		calcularAPagar(calculos);
		return calculos;
	}
	private void ponerCalculosParaEvaluarGestionDePago(AnalisisDeGastosDTO analisisDeGastos, SeguimientoBean seguimiento) {
		analisisDeGastos.setCalculos(calculosParaEvaluarGestionDePago(analisisDeGastos.getCuadroDeAnalisisDeGastos(), analisisDeGastos.getCronogramaDeDesembolsos(), seguimiento));
	}
	private void ponerCalculosParaVisualizarSeguimiento(AnalisisDeGastosDTO analisisDeGastos, SeguimientoBean seguimiento) {
		analisisDeGastos.setCalculos(calculosParaVisualizarSeguimiento(analisisDeGastos.getCuadroDeAnalisisDeGastos(), analisisDeGastos.getCronogramaDeDesembolsos(), seguimiento));
	}
	private void ponerCalculosParaVisualizarEvaluacion(AnalisisDeGastosDTO analisisDeGastos, SeguimientoBean seguimiento) {
		basePonerCalculosFontar(analisisDeGastos, seguimiento);
	}
	private void ponerCalculosParaCargarResultadoDeEvaluacion(AnalisisDeGastosDTO analisisDeGastos, SeguimientoBean seguimiento) {
		basePonerCalculosFontar(analisisDeGastos, seguimiento);
	}
	private void basePonerCalculosFontar(AnalisisDeGastosDTO analisisDeGastos, SeguimientoBean seguimiento) {
		//No esta definido para CFs
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) return;

		CalculosDeAnalisisDeGastosDTO calculos = new CalculosDeAnalisisDeGastosDTO();
		//Presupuesto segun avance
		calculos.setPresupuestoSegunInformeDeAvance(getPresupuestoSegunInformeDeAvance(seguimiento, analisisDeGastos.getCronogramaDeDesembolsos()));
		//Monto desembolsado
		calculos.setMontoDesembolsado(analisisDeGastos.getCronogramaDeDesembolsos().getMontoTotalDesembolsado());
		//Total rendido aprobado
		BigDecimal totalAprobadoFontar = analisisDeGastos.getCuadroDeAnalisisDeGastos().analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada).getMontoFontarTotal();
		BigDecimal totalAprobadoAnteriorFontar = analisisDeGastos.getCuadroDeAnalisisDeGastos().analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getMontoFontarTotal();
		calculos.setTotalRendidoAprobado(
				totalAprobadoAnteriorFontar.add(totalAprobadoFontar)
				);
		//Pendiente de rendicion
		if(seguimiento.getMontoPendienteDeRendicion()!=null) {
			calculos.setPendienteDeRendicion(seguimiento.getMontoPendienteDeRendicion());
		} else {
			calculos.setPendienteDeRendicion(
					calculos.getMontoDesembolsado().subtract(calculos.getTotalRendidoAprobado())
			);
		}
		calcularAPagar(calculos);
		analisisDeGastos.setCalculos(calculos);
	}
	
	private void calcularAPagar(CalculosDeAnalisisDeGastosDTO calculos) {
		if(calculos.getPendienteDeRendicion().doubleValue()<0) {
			calculos.setPorPagar(
					calculos.getPresupuestoSegunInformeDeAvance().subtract(calculos.getMontoDesembolsado())
			);
		} else {
			calculos.setPorPagar(
					(calculos.getPresupuestoSegunInformeDeAvance().subtract(calculos.getMontoDesembolsado())).subtract(calculos.getPendienteDeRendicion())
			);
		}
	}
	
	/**
	 * Calcula el presupuesto segun el informe de avance. Si fue modificado manualmente devuelve el
	 * valor que se ingresó. Si no, devuelve la suma de los montos desembolsados o autorizados según
	 * el cronograma de desembolsos a la actualidad.
	 * @param seguimiento
	 * @param cronograma
	 * @return
	 */
	public BigDecimal getPresupuestoSegunInformeDeAvance(SeguimientoBean seguimiento, CronogramaDeDesembolsosDTO cronograma) {
		//Si ya fue ingresado lo devuelvo
		if(seguimiento.getMontoPresupuestoSegunAvance()!=null) { 
			return seguimiento.getMontoPresupuestoSegunAvance();
		} else {
			//Lo calculo
			BigDecimal ret = BigDecimal.ZERO;
			for(ProyectoDesembolsoDTO desembolso : cronograma.getDesembolsos()) {
				if(desembolso.getEsAnticipo()) {
					ret = ret.add(desembolso.getMontoOriginal());
				} else {
					if(desembolso.yaFueAutorizado()) {
						ret = ret.add(desembolso.getMontoOriginal());
					}
				}
			}
			return ret;
		}
	}
	public BigDecimal getPresupuestoSegunInformeDeAvance(SeguimientoBean seguimiento) {
		CronogramaDeDesembolsosDTO cronograma = proyectoDesembolsoService.obtenerCronogramaParaVisualizarSeguimiento(seguimiento.getId());
		return getPresupuestoSegunInformeDeAvance(seguimiento, cronograma);
	}

	/**
	 * Devuelve la fundamentación de la última evaluación/auditoría contable no anulada.
	 * @param seguimiento
	 * @return
	 */
	private String fundamentacionFontar(SeguimientoBean seguimiento) {
		
		List<EvaluacionSeguimientoBean> evaluaciones = evaluacionSeguimientoDAO.findBySeguimiento(seguimiento.getId());
		
		long idEvaluacionUsada = 0;
		String fundamentacion = null;
		for(EvaluacionSeguimientoBean evaluacion : evaluaciones) {
			//Chequeo posterioridad
			if(evaluacion.getId().longValue()>idEvaluacionUsada) {
				//chequeo que sea contable
				if(evaluacion.esTipoContable()) {
					//chequeo que sea valida
					if(!evaluacion.esAnulada()) {
						idEvaluacionUsada = evaluacion.getId().longValue();
						fundamentacion = evaluacion.getFundamentacion();
					}
				}
			}
		}
		return fundamentacion;
	}
	
	private String fundamentacionUFFA(SeguimientoBean seguimiento) {
		//Busco la descripcion de la ultima bitacora de gestion de pago
		List<BitacoraBean> bitacoras = bitacoraDao.findBySeguimientoTipo(seguimiento.getId(), TipoBitacora.SEGUIMIENTO.getName());
		long idBitacora = 0;
		String descripcion = null;
		for(BitacoraBean bitacora : bitacoras) {
			if(BitacoraTema.GESTION_PAGO_SEGUIMIENTO.equals(bitacora.getTema())) {
				if(bitacora.getId().longValue()>idBitacora) {
					//Es mejor que la que tengo
					idBitacora = bitacora.getId().longValue();
					descripcion = bitacora.getDescripcion();
				}
			}
		}
		return descripcion;
	}

	public SeguimientoVisualizacionCabeceraDTO obtenerCabeceraDeSeguimiento(Long idSeguimiento) {
		return SeguimientoVisualizacionCabeceraAssembler.getInstance().buildDTO(seguimientoDao.read(idSeguimiento));
	}
	
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaVisualizarSeguimiento(Long idSeguimiento) {
		return getCalculosDeAnalisisDeGastosParaVisualizarSeguimiento(seguimientoDao.read(idSeguimiento));
	}
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(Long idSeguimiento) {
		return getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(seguimientoDao.read(idSeguimiento));
	}
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaVisualizarSeguimiento(SeguimientoBean seguimiento) {
		return calculosParaVisualizarSeguimiento(
				obtenerCuadroDeAnalisisDeGastos(
						seguimiento, 
						ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
						ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente, 
						ConceptoDeAnalisisDeGasto.RendicionActualAprobada,
						ConceptoDeAnalisisDeGasto.RendicionActualGestionada
				),
				proyectoDesembolsoService.obtenerCronogramaParaVisualizarSeguimiento(seguimiento.getId()),
				seguimiento
		);
	}
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(SeguimientoBean seguimiento) {
		return calculosParaEvaluarGestionDePago(
				obtenerCuadroDeAnalisisDeGastos(
						seguimiento, 
						ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
						ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente, 
						ConceptoDeAnalisisDeGasto.RendicionActualAprobada,
						ConceptoDeAnalisisDeGasto.RendicionActualAGestionar
				),
				proyectoDesembolsoService.obtenerCronogramaParaVisualizarSeguimiento(seguimiento.getId()),
				seguimiento
		);
	}
	private void validateEncryptionContext() throws AccesoDenegadoException {
		if(!((FontarCryptographicService) ContextUtil.getBean("cryptographicService")).encyptionAvailable())
			throw new AccesoDenegadoException();
	}

	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaFinalizarSeguimiento(SeguimientoBean seguimiento) {
		return calculosParaFinalizarSeguimiento(
				obtenerCuadroDeAnalisisDeGastos(
						seguimiento, 
						ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto,
						ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente, 
						ConceptoDeAnalisisDeGasto.RendicionActualAprobada,
						ConceptoDeAnalisisDeGasto.RendicionActualGestionada
				),
				proyectoDesembolsoService.obtenerCronogramaParaVisualizarSeguimiento(seguimiento.getId()),
				seguimiento
		);
	}
}