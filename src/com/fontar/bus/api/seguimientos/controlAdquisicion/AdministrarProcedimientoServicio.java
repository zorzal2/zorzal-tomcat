package com.fontar.bus.api.seguimientos.controlAdquisicion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoUffaBid;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

/**
 * Servicios para la administración de procedimientos de Control de Adquisiciones de adquiciones de items del PAC. 
 * Estos procedimientos se relacionan con los proyecto de instrumentos ARAI.
 * 
 * @see com.fontar.data.impl.domain.bean.PacBean
 * @see com.fontar.data.impl.domain.bean.ProcedimientoBean
 * @see com.fontar.bus.api.proyecto.pac.AdministrarPACServicio
 * 
 */
public interface AdministrarProcedimientoServicio {

	/**
	 * Obtiene un listado con los 
	 * tipos de adquisición disponibles.<br>
	 */
	public List<TipoAdquisicionBean> obtenerTiposAdquisicion();
	
	/**
	 * Obtiene el <code>TipoAdquisicion</code>
	 * correspondiente al parámetro <i>idTipoAdquisicion</i>.<br>
	 * @param idTipoAdquisicion
	 * @return el tipo de adquisición correspondiente el parámetro
	 */
	public TipoAdquisicionBean obtenerTipoAdquisicion(Long idTipoAdquisicion);
	
	/**
	 * Obtiene un listado de items de un <code>PAC</code>
	 * correspondientes a un tipo de adquisición
	 * y a un proyecto.
	 * @param idTipoAdquisicion
	 * @param idProyecto
	 * @return listado de items
	 */
	public List<PacItemDTO> obtenerItemsPorTipoAdquisicion(Long idTipoAdquisicion, Long idProyecto);
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con
	 * la <i>listaItems</i> y los datos recibidos por parámetro.<br>
	 * @param idProyecto
	 * @param idTipoAdquisicion
	 * @param listaItems
	 * @param codigo
	 * @param fechaRecepcion
	 * @param descripcion
	 * @return id de procedimiento creado
	 */
	public Long cargarNuevoProcedimiento(Long idProyecto,Long idTipoAdquisicion,String[] listaItems,String codigo,Date fechaRecepcion,String descripcion);
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con los items
	 * de un procedimiento que tuvieron resultado 
	 * <i>Aprobado Pliego</i> o <i>Aprobado P. Preclasificación</i>.<br>
	 * @param idProcedimiento id del procedimiento en base al cual se creará uno nuevo
	 * @param fechaRecepcion
	 * @param descripcion
	 * @return id de procedimiento creado
	 */
	public Long generarPedidoAutorizacion(Long idProcedimiento,Date fechaRecepcion,String descripcion);
	
	/**
	 * Obtiene un item (<code>PacBean</code>)
	 * en base al <i>idItem</i>
	 * @param idItem
	 * @return un item
	 */
	public PacBean obtenerItem(Long idItem);
	
	/**
	 * Obtiene un <code>ProcedimientoBean</code>
	 * en base al <i>idProcedimiento</i> recibido
	 * como parámetro.<br>
	 * @param idProcedimiento
	 * @return un procedimiento
	 */
	public ProcedimientoBean obtenerProcedimiento(Long idProcedimiento);
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento.<br>
	 * @param idProcedimiento
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsPorProcedimiento(Long idProcedimiento);

	/**
	 * Persiste los parámetros en el procedimiento
	 * correspondiente al <i>idProcedimiento</i>.<br>
	 * @param idProcedimiento
	 * @param idPersonaEvaluador
	 * @param fecha
	 * @param descripcion
	 * @throws ConversionException 
	 */
	public void guardarEvaluadorTecnico(Long idProcedimiento,Long idPersonaEvaluador,Date fecha,String descripcion) throws ConversionException;
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de evauador técnico.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoEvaluadorTecnico(Long idProcedimiento);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de fundamentación evauador.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoFundamentacionEvaluador(Long idProcedimiento);
	
	/**
	 * Modifica un procedimiento con los datos
	 * no nulos del <code>ProcedimientoBean</code> recibido
	 * como parámetro.<br>
	 * @param procedimiento
	 */
	public void modificarProcedimiento(ProcedimientoBean procedimiento) throws ConversionException;
	
	/**
	 * Obtiene un <code>ProcedimientoItemBean</code>
	 * en base al <i>idProcedimientoItem</i> recibido
	 * como parámetro.<br>
	 * @param idProcedimientoItem
	 * @return un ProcedimientoItemBean
	 */
	public ProcedimientoItemBean obtenerProcedimientoItem(Long idProcedimientoItem);
	
	/**
	 * Modifica un procedimientoItem con los datos
	 * no nulos del <code>ProcedimientoItemBean</code> recibido
	 * como parámetro.<br>
	 * @param procedimientoItem
	 * @throws ConversionException 
	 */
	public ProcedimientoItemBean modificarProcedimientoItem(ProcedimientoItemBean procedimiento) throws ConversionException;
	
	/**
	 * Modifica el resultado y el monto
	 * de un item.<br>
	 * @param idProcedimientoItem
	 * @param proveedor
	 * @param idMoneda
	 * @param montoFontar
	 * @param resultadoFontar
	 * @throws ConversionException
	 */
	public void modificarResultadoFontarItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoFontar,ResultadoFontar resultadoFontar);
	
	/**
	 * Modifica el resultado uffa y el monto
	 * de un item.<br>
	 * @param idProcedimientoItem
	 * @param proveedor
	 * @param idMoneda
	 * @param montoUffa
	 * @param resultadoUffa
	 * @throws ConversionException
	 */
	public void modificarResultadoUffaItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoUffa,ResultadoUffaBid resultadoUffa);
	
	/**
	 * Modifica el resultado bid y el monto
	 * de un item.<br>
	 * @param idProcedimientoItem
	 * @param proveedor
	 * @param idMoneda
	 * @param montoBid
	 * @param resultadoBid
	 * @throws ConversionException
	 */
	public void modificarResultadoBidItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoBid,ResultadoUffaBid resultadoBid);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene items que deben ser evaluados
	 * por la UFFA.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerTieneItemsParaUffa(Long idProcedimiento);

	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene items que deben ser evaluados
	 * por la BID.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerTieneItemsParaBid(Long idProcedimiento);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene items con el resultado Uffa/Bid igual a 
	 * <i>Aprobado Pliego</i> o <i>Aprobado Pliego PreClasificación</i>.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public boolean obtenerTieneItemsAprobadosParcialmente(Long idProcedimiento);
	
	/**
	 * Determina si cada item de un procedimiento
	 * tiene cargado su resultado fontar: esto
	 * implica tener cagada la moneda, el monto
	 * y el resultado fontar.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoFontar(Long idProcedimiento);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a uffa.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoDatosRemisionUffa(Long idProcedimiento);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a bid.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoDatosRemisionBid(Long idProcedimiento);

	public List<ProcedimientoItemBean> obtenerProcedimientoItemPac(Long id);
	
	/**
	 * Obtiene un listado de <code>ProcedimientoItemBean</code>
	 * para un procedimiento con un resultadoFontar
	 * determinado.<br>
	 * @param idProcedimiento
	 * @param resultadoFontar
	 * @return
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoPorResultadoFontar(Long idProcedimiento, ResultadoFontar resultadoFontar);
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para la Uffa.<br>
	 * @param idProcedimiento
	 * @return lista de items
	 */	
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoUffa(Long idProcedimiento);

	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para el Bid.<br>
	 * @param idProcedimiento
	 * @return lista de items
	 */	
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoBid(Long idProcedimiento);	
	
	/**
	 * Determina si los item de un procedimiento
	 * asignados a la uffa tienen cargado
	 * el resultado uffa: esto implica
	 * tener cargado la moneda, el montoUffa
	 * y el resultadoUffa.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoUffa(Long idProcedimiento);
	
	/**
	 * Determina si los item de un procedimiento
	 * asignados a la bid tienen cargado
	 * el resultado bid: esto implica
	 * tener cargado la moneda, el montoUffa
	 * y el resultadoUffa.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoBid(Long idProcedimiento);
	
	/**
	 * Determina si a un procedimiento se le debe cargar
	 * el resultado Bid y no han sido cargados
	 * los mismos.<br> 
	 * @param idProcedimiento
	 * @return true si tiene items por cargar resultado bid
	 */
	public Boolean obtenerTieneResultadoBidPendiente(Long idProcedimiento);


	/**
	 * Determina si a un procedimiento se le debe cargar
	 * el resultado Uffa y no han sido cargados
	 * los mismos.<br> 
	 * @param idProcedimiento
	 * @return true si tiene items por cargar resultado Uffa
	 */
	public Boolean obtenerTieneResultadoUffaPendiente(Long idProcedimiento);	
	
	/**
	 * Pasa los items uffa de un procedimiento
	 * al estado correspondiente y pone la fecha de 
	 * adjudicacion si el item fue adjudicado.<br>
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */
	public void modificarEstadoItemsUffa(Long idProcedimiento,Date fechaAdjudicacion) throws ConversionException;

	/**
	 * Pasa los items bid de un procedimiento
	 * al estado correspondiente y pone la fecha de 
	 * adjudicacion si el item fue adjudicado.<br>
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */	
	public void modificarEstadoItemsBid(Long idProcedimiento,Date fechaAdjudicacion) throws ConversionException;
		
	/**
	 * Pasa los items de un procedimiento al estado
	 * <i>Pediente de Compra</i> si el resultado fontar
	 * fue <i>Desierto</i> o <i>No Autorizado</i>.<br> 
	 * Esto debe aplicarse cuando se <code>Carga Resultado Fontar</code>.<br> 
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */	
	public void modificarEstadoItemsFontar(Long idProcedimiento) throws ConversionException;
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que no fueron ni 
	 * <i>Aprobado Final</i>, <i>No Autorizado</i> ni <i>Desierto</i>.<br>
	 * Para esto revisa los resultados uffa o bid según corresponda.<br>
	 * @param idProcedimiento
	 * @return lista de items ProcedimientoItemBean
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoNoFinalizados(Long idProcedimiento);
	
}

