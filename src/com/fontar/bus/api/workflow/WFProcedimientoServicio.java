package com.fontar.bus.api.workflow;

import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

/**
 * Servicios para la administracion de Workflows procedimientos de adquiciones. 
 * Estos procedimientos se relacionan con los proyecto de instrumentos ARAI.
 */
public interface WFProcedimientoServicio {

	/**
	 * Obtiene un listado con los 
	 * tipos de adquisición disponibles.<br> 
	 * @author ssanchez
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
	 * @param idTaskInstance
	 * @return listado de items
	 */
	public List<PacItemDTO> obtenerItemsPorTipoAdquisicion(Long idTipoAdquisicion, Long idTaskInstance);
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con
	 * la <i>listaItems</i> y los datos recibidos por parámetro.<br>
	 * Genera un workflow <code>ControlAdquisicion</code> 
	 * respectivo al procedimiento.<br>
	 * @param idTaskInstance
	 * @param idTipoAdquisicion
	 * @param listaItems
	 * @param codigo
	 * @param fechaRecepcion
	 * @param descripcion
	 */
	public void cargarNuevoProcedimiento(Long idTaskInstance,Long idTipoAdquisicion,String[] listaItems,String codigo,Date fechaRecepcion,String descripcion);
	
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con los items
	 * que de un procedimiento que tuvieron resultado 
	 * <i>Aprobado Pliego</i> o <i>Aprobado P. Preclasificación</i>.<br>
	 * Genera un workflow <code>ControlAdquisicion</code> 
	 * respectivo al procedimiento.<br>
	 * @param idTaskInstance
	 * @param fechaRecepcion
	 * @param descripcion
	 */
	public void generarPedidoAutorizacion(Long idTaskInstance,Date fechaRecepcion,String descripcion);
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsPorProcedimiento(Long idTaskInstance);
	
	/**
	 * Obtiene un <code>ProcedimientoBean</code>
	 * en base al <i>idTaskInstance</i> recibido
	 * como parámetro.<br>
	 * @param idTaskInstance
	 * @return un procedimiento
	 */
	public ProcedimientoBean obtenerProcedimiento(Long idTaskInstance);
	
	/**
	 * Finaliza la tarea especificada por <i>idTaskInstance</i>
	 * del workflow <code>ControlAdquisición</code>.<br>
	 * @param idTaskInstance
	 */
	public void finalizarTarea(Long idTaskInstance);

	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de evauador técnico.<br>
	 * @param idTaskInstance
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoEvaluadorTecnico(Long idTaskInstance);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de fundamentación evauador.<br>
	 * @param idTaskInstance
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoFundamentacionEvaluador(Long idTaskInstance);
	
	/**
	 * Modifica el estado de un <code>ProcedimientoBean</code>.<br>
	 * @param procedimiento
	 * @throws ConversionException 
	 */
	public void modificarEstado(Long idTaskInstance, EstadoProcedimiento estado) throws ConversionException;

	/**
	 * Modifica un procedimiento con los datos no nulos
	 * del <code>ProcedimientoBean</code> recibido
	 * como parámetro.<br>
	 * @param idTaskInstance
	 * @param procedimiento
	 * @throws ConversionException 
	 */
	public void modificarProcedimiento(Long idTaskInstance, ProcedimientoBean procedimiento) throws ConversionException;
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Fontar</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoFontar
	 * @param observacionResultadoFontar
	 * @throws ConversionException
	 */
	public void cargarResultadoFontar(Long idTaskInstance, Date fechaResultadoFontar, String observacionResultadoFontar) throws ConversionException;
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Uffa</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * Cambia el estado de los items que fueron
	 * <i>Aprobados Final</i> al estado <i>Adjudicado</i>.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoUffa
	 * @param observacionResultadoUffa
	 * @throws ConversionException
	 */
	public void cargarResultadoUffa(Long idTaskInstance, Date fechaResultadoUffa, String observacionResultadoUffa) throws ConversionException;
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Bid</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoBid
	 * @param observacionResultadoBid
	 * @throws ConversionException
	 */
	public void cargarResultadoBid(Long idTaskInstance, Date fechaResultadoBid, String observacionResultadoBid) throws ConversionException;
	
	/**
	 * Determina si cada item de un procedimiento
	 * tiene cargado su resultado fontar: esto
	 * implica tener cagada la moneda, el monto
	 * y el resultado fontar.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoFontar(Long idTaskInstance);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a uffa.<br>
	 * @param idTaskInstance
	 * @return true o false 
	 */
	public Boolean obtenerEstadoDatosRemisionUffa(Long idTaskInstance);
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a bid.<br>
	 * @param idTaskInstance
	 * @return true o false 
	 */
	public Boolean obtenerEstadoDatosRemisionBid(Long idTaskInstance);

	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para la Uffa.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoUffa(Long idTaskInstance);
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para la Bid.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoBid(Long idTaskInstance);

	/**
	 * Determina si los item de un procedimiento
	 * asignados para la uffa tienen cargado
	 * su resultado uffa: esto implica tener
	 * cargado la moneda, el montoUffa
	 * y el resultadoUffa.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoUffa(Long idTaskInstance);
	
	/**
	 * Determina si los item de un procedimiento
	 * asignados para la bid tienen cargado
	 * su resultado bid: esto implica tener
	 * cargado la moneda, el montoBid
	 * y el resultadoBid.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoBid(Long idTaskInstance);
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que no fueron ni 
	 * <i>Aprobado Final</i>, <i>No Autorizado</i> ni <i>Desierto</i>.<br>
	 * Para esto revisa los resultados uffa o bid según corresponda.<br>
	 * @param idTaskInstance
	 * @return lista de items ProcedimientoItemBean
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoNoFinalizados(Long idTaskInstance);
	
}





