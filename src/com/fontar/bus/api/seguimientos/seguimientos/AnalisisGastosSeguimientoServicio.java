package com.fontar.bus.api.seguimientos.seguimientos;

import java.math.BigDecimal;

import com.fontar.bus.impl.seguimientos.seguimientos.ResumenRendicionesPorProyecto;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastosDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.CalculosDeAnalisisDeGastosDTO;
import com.fontar.seguridad.cripto.AccesoDenegadoException;


public interface AnalisisGastosSeguimientoServicio {
	/**
	 * Devuelve un DTO con todos los datos completos para mostrar la solapa de Análisis de Gastos
	 * de la visualización de evaluaciones contables de seguimiento. 
	 * @param idEvaluacionDeSeguimiento
	 * @return
	 * @throws AccesoDenegadoException 
	 */
	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaVisualizarEvaluacion(Long idEvaluacionDeSeguimiento) throws AccesoDenegadoException;
	/**
	 * Devuelve un DTO con todos los datos completos para mostrar la solapa de Análisis de Gastos
	 * durante la carga de resultado de evaluaciones contables de seguimiento. 
	 * @param idEvaluacionDeSeguimiento
	 * @return
	 * @throws AccesoDenegadoException 
	 */
	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaCargarResultadoDeEvaluacion(Long idEvaluacionDeSeguimiento) throws AccesoDenegadoException;
	/**
	 * Devuelve un DTO con todos los datos completos para mostrar la solapa de Análisis de Gastos
	 * de la visualización de seguimientos. 
	 * @param idSeguimiento
	 * @return
	 * @throws AccesoDenegadoException 
	 */
	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaVisualizarSeguimiento(Long idSeguimiento) throws AccesoDenegadoException;
	/**
	 * Devuelve un DTO con todos los datos completos para mostrar la solapa de Análisis de Gastos
	 * durante la evaluacion de gestion de pago de un seguimiento. 
	 * @param idSeguimiento
	 * @return
	 * @throws AccesoDenegadoException 
	 */
	public AnalisisDeGastosDTO obtenerDatosDeAnalisisDeGastosParaEvaluarGestionDePago(Long idSeguimiento) throws AccesoDenegadoException;
	
	public ResumenRendicionesPorProyecto rendicionCuentasAnalisisGastosSeguimiento(Long idEvaluacion);
	/**
	 * Devuelve la suma total de los montos FONTAR de las rendiciones del
	 * proyecto que correspondan a un seguimiento positivo (no anulado ni
	 * rechazado, etc.). Toma los montos mas ajustados de las rendiciones.
	 * @param idProyecto
	 * @return
	 */
	public BigDecimal montoRendicionTotalAprobado(Long idProyecto);
	/**
	 * Devualve un DTO con los datos necesarios para la visualización de la cabecera del seguimiento. 
	 * @param idSeguimiento
	 * @return
	 */
	public SeguimientoVisualizacionCabeceraDTO obtenerCabeceraDeSeguimiento(Long idSeguimiento);
	/**
	 * Devuelve los calculos complementarios que se muestran en el analisis de gasto de un seguimiento.
	 * @param seguimiento
	 * @return
	 */
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaVisualizarSeguimiento(SeguimientoBean seguimiento);
	/**
	 * Devuelve los calculos complementarios al cuadro de analisis de gastos que se necesitan para 
	 * finalizar un seguimiento. Solo se calcula el Presupuesto Segun Informe De Avance y el
	 * Monto Pendiente De Rendicion.
	 * @param seguimiento
	 * @return
	 */
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaFinalizarSeguimiento(SeguimientoBean seguimiento);
	/**
	 * Devuelve los calculos complementarios que se muestran en el analisis de gasto de un seguimiento.
	 * @param seguimiento
	 * @return
	 */
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(SeguimientoBean seguimiento);
	/**
	 * Devuelve los calculos complementarios que se muestran en el analisis de gasto de un seguimiento.
	 * @param idSeguimiento
	 * @return
	 */
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaVisualizarSeguimiento(Long idSeguimiento);
	/**
	 * Devuelve los calculos complementarios que se muestran en el analisis de gasto de un seguimiento.
	 * @param idSeguimiento
	 * @return
	 */
	public CalculosDeAnalisisDeGastosDTO getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(Long idSeguimiento);
}
