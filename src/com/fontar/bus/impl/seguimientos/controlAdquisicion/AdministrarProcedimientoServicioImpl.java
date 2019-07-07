package com.fontar.bus.impl.seguimientos.controlAdquisicion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.data.api.dao.PacDAO;
import com.fontar.data.api.dao.ProcedimientoDAO;
import com.fontar.data.api.dao.ProcedimientoItemDAO;
import com.fontar.data.api.dao.TipoAdquisicionDAO;
import com.fontar.data.impl.assembler.PacItemDTOAssembler;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoUffaBid;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;


/**
 * Implementación de servicio para procedimientos
 * del modulo Control de Adquisiciones.<br> 
 * @author ssanchez
 */
public class AdministrarProcedimientoServicioImpl implements AdministrarProcedimientoServicio {
	
	private static class PacBeanUpdate extends HashMap<String, Object> {
		private static final long serialVersionUID = 1L;
		private Long id;
		public PacBeanUpdate(Long id) {
			super();
			this.id = id;
		}
		public Long getId() { return id; }
	}
	
	private TipoAdquisicionDAO tipoAdquisicionDAO;
	private PacDAO pacDAO;
	private ProcedimientoDAO procedimientoDAO;
	private ProcedimientoItemDAO procedimientoItemDAO;
	
	public void setTipoAdquisicionDAO(TipoAdquisicionDAO tipoAdquisicionDAO) {
		this.tipoAdquisicionDAO = tipoAdquisicionDAO;
	}
	public void setPacDAO(PacDAO pacDAO) {
		this.pacDAO = pacDAO;
	}
	public void setProcedimientoDAO(ProcedimientoDAO procedimientoDAO) {
		this.procedimientoDAO = procedimientoDAO;
	}
	public void setProcedimientoItemDAO(ProcedimientoItemDAO procedimientoItemDAO) {
		this.procedimientoItemDAO = procedimientoItemDAO;
	}

	/**
	 * Obtiene un listado con los 
	 * tipos de adquisición disponibles.<br> 
	 * @author ssanchez
	 */
	public List<TipoAdquisicionBean> obtenerTiposAdquisicion() {
		
		List<TipoAdquisicionBean> list = tipoAdquisicionDAO.getAll();
		
		return list;
	}
	
	/**
	 * Obtiene el <code>TipoAdquisicion</code>
	 * correspondiente al parámetro <i>idTipoAdquisicion</i>.<br>
	 * @param idTipoAdquisicion
	 * @return el tipo de adquisición correspondiente el parámetro
	 */
	public TipoAdquisicionBean obtenerTipoAdquisicion(Long idTipoAdquisicion) {
		
		return tipoAdquisicionDAO.read(idTipoAdquisicion);
	}
	
	/**
	 * Obtiene un listado de items de un <code>PAC</code>
	 * correspondientes a un tipo de adquisición
	 * y a un proyecto.
	 * @param idTipoAdquisicion
	 * @param idProyecto
	 * @return listado de items
	 */
	public List<PacItemDTO> obtenerItemsPorTipoAdquisicion(Long idTipoAdquisicion, Long idProyecto) {
		
		List<PacBean> list = pacDAO.findByTipoAdquisicionYProyecto(idTipoAdquisicion,idProyecto); 
		
		List<PacItemDTO> listaDTO = PacItemDTOAssembler.buildListDTO(list);
		
		return listaDTO;
	}
	
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
	public Long cargarNuevoProcedimiento(Long idProyecto,Long idTipoAdquisicion,String[] listaItems,String codigo,Date fechaRecepcion,String descripcion) {
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setIdProyecto(idProyecto);
		procedimiento.setIdTipoAdquisicion(idTipoAdquisicion);
		procedimiento.setCodigo(codigo);
		procedimiento.setFechaRecepcion(fechaRecepcion);
		procedimiento.setDescripcion(descripcion);
		procedimiento.setEstado(EstadoProcedimiento.INGRESADO);
		
		procedimientoDAO.save(procedimiento);
		
		Long idItem;
		for (int i = 0; i < listaItems.length; i++) {
			idItem = Long.valueOf(listaItems[i]);
			
			ProcedimientoItemBean procedimientoItem = new ProcedimientoItemBean();
			procedimientoItem.setIdProcedimiento(procedimiento.getId());
			procedimientoItem.setIdPacItem(idItem);
			
			procedimientoItemDAO.save(procedimientoItem);
			
			PacBean item = obtenerItem(idItem);
			item.setCodigoEstado(EstadoPacItem.EN_PROCESO_DE_COMPRA);
			pacDAO.update(item);
		}
		
		return procedimiento.getId();
	}
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con los items
	 * de un procedimiento que tuvieron resultado 
	 * <i>Aprobado Pliego</i> o <i>Aprobado P. Preclasificación</i>.<br>
	 * @param idProcedimiento id del procedimiento en base al cual se creará uno nuevo
	 * @param fechaRecepcion
	 * @param descripcion
	 * @return id de procedimiento creado
	 */
	public Long generarPedidoAutorizacion(Long idProcedimiento,Date fechaRecepcion,String descripcion) {
		
		ProcedimientoBean procedimientoAnt = obtenerProcedimiento(idProcedimiento);
		procedimientoAnt.setEstado(EstadoProcedimiento.FINALIZADO);
		procedimientoDAO.update(procedimientoAnt);
		
		ProcedimientoBean procedimientoNuevo = new ProcedimientoBean();
		procedimientoNuevo.setIdProyecto(procedimientoAnt.getIdProyecto());
		procedimientoNuevo.setIdTipoAdquisicion(procedimientoAnt.getIdTipoAdquisicion());
		procedimientoNuevo.setCodigo(procedimientoAnt.getCodigo());
		procedimientoNuevo.setFechaRecepcion(fechaRecepcion);
		procedimientoNuevo.setDescripcion(descripcion);
		procedimientoNuevo.setEstado(EstadoProcedimiento.INGRESADO);
		procedimientoDAO.save(procedimientoNuevo);
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsProcedimientoNoFinalizados(idProcedimiento);
		for (ProcedimientoItemBean procedimientoItemAnt : listaItems) {

			PacBean item = obtenerItem(procedimientoItemAnt.getIdPacItem());
			item.setCodigoEstado(EstadoPacItem.EN_PROCESO_DE_COMPRA);
			pacDAO.update(item);

			ProcedimientoItemBean procedimientoItem = new ProcedimientoItemBean();
			procedimientoItem.setIdProcedimiento(procedimientoNuevo.getId());
			procedimientoItem.setIdPacItem(procedimientoItemAnt.getIdPacItem());
			procedimientoItem.setIdMoneda(item.getIdMoneda());
			procedimientoItem.setMontoFontar(item.getMontoAdjudicacion());
			
			procedimientoItemDAO.save(procedimientoItem);
		}
		
		return procedimientoNuevo.getId();
	}
	
	
	/**
	 * Obtiene un item (<code>PacBean</code>)
	 * en base al <i>idItem</i>
	 * @param idItem
	 * @return un item
	 */
	public PacBean obtenerItem(Long idItem) {
		
		return pacDAO.read(idItem);
	}
	
	/**
	 * Obtiene un <code>ProcedimientoBean</code>
	 * en base al <i>idProcedimiento</i> recibido
	 * como parámetro.<br>
	 * @param idProcedimiento
	 * @return un procedimiento
	 */
	public ProcedimientoBean obtenerProcedimiento(Long idProcedimiento) {
		
		return procedimientoDAO.read(idProcedimiento);
	}
	
	/**
	 * Obtiene un <code>PacBean</code>
	 * en base al <i>idPac</i> recibido
	 * como parámetro.<br>
	 * @param idPac
	 * @return un PacBean
	 */
	public PacBean obtenerPacBean(Long idPac) {
		
		return pacDAO.read(idPac);
	}	
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento.<br>
	 * @param idProcedimiento
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsPorProcedimiento(Long idProcedimiento) {
		
		return procedimientoItemDAO.findByProcedimiento(idProcedimiento);
	}
	
	/**
	 * Obtiene un listado de <code>ProcedimientoItemBean</code>
	 * para un procedimiento con un resultadoFontar
	 * determinado.<br>
	 * @param idProcedimiento
	 * @param resultadoFontar
	 * @return
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoPorResultadoFontar(Long idProcedimiento, ResultadoFontar resultadoFontar) {
		
		return procedimientoItemDAO.findByProcedimientoResultadoFontar(idProcedimiento, resultadoFontar.getName());
	}	

	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * del procedimiento determinado por el id dado que estan en espera
	 * de resultado por parte de la UFFA.
	 * @param idProcedimiento
	 * @return lista de items
	 */	
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoUffa(Long idProcedimiento) {
		
		return obtenerItemsProcedimientoPorResultadoFontar(idProcedimiento,ResultadoFontar.APROB_PEND_UFFA);
	}	

	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * del procedimiento determinado por el id dado que estan en espera
	 * de resultado por parte de BID.
	 * @param idProcedimiento
	 * @return lista de items
	 */	
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoBid(Long idProcedimiento) {
		
		return obtenerItemsProcedimientoPorResultadoFontar(idProcedimiento,ResultadoFontar.APROB_PEND_BID);
	}	
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que no fueron ni 
	 * <i>Aprobado Final</i>, <i>No Autorizado</i> ni <i>Desierto</i>.<br>
	 * Para esto revisa los resultados uffa o bid según corresponda.<br>
	 * @param idProcedimiento
	 * @return lista de items ProcedimientoItemBean
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoNoFinalizados(Long idProcedimiento) {
		
		return procedimientoItemDAO.findByProcedimientoConResultadoPliego(idProcedimiento);
	}
	
	/**
	 * Persiste los parámetros en el procedimiento
	 * correspondiente al <i>idProcedimiento</i>.<br>
	 * @param idProcedimiento
	 * @param idPersonaEvaluador
	 * @param fecha
	 * @param descripcion
	 * @throws ConversionException 
	 */
	public void guardarEvaluadorTecnico(Long idProcedimiento,Long idPersonaEvaluador,Date fecha,String descripcion) throws ConversionException {
		
		ProcedimientoBean procedimientoDest = obtenerProcedimiento(idProcedimiento);
		ProcedimientoBean procedimientoOrigen = new ProcedimientoBean(idPersonaEvaluador,fecha,descripcion);
		
		BeanUtils.copyNotNullProperties(procedimientoOrigen,procedimientoDest);
		
		procedimientoDAO.update(procedimientoDest);
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de evauador técnico.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoEvaluadorTecnico(Long idProcedimiento) {
		
		ProcedimientoBean procedimiento = procedimientoDAO.read(idProcedimiento);
		
		return procedimiento.tieneDatosEvaluadorTecnico();
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de fundamentación evauador.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoFundamentacionEvaluador(Long idProcedimiento) {
		
		ProcedimientoBean procedimiento = procedimientoDAO.read(idProcedimiento);
		
		return procedimiento.tieneDatosFundamentacionEvaluador();
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a uffa.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoDatosRemisionUffa(Long idProcedimiento) {
		
		ProcedimientoBean procedimiento = procedimientoDAO.read(idProcedimiento);
		
		return procedimiento.tieneDatosRemisionUffa();
	}	
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a bid.<br>
	 * @param idProcedimiento
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoDatosRemisionBid(Long idProcedimiento) {
		
		ProcedimientoBean procedimiento = procedimientoDAO.read(idProcedimiento);
		
		return procedimiento.tieneDatosRemisionBid();
	}	

	/**
	 * Modifica un procedimiento con los datos
	 * no nulos del <code>ProcedimientoBean</code> recibido
	 * como parámetro.<br>
	 * @param procedimiento
	 * @throws ConversionException 
	 */
	public void modificarProcedimiento(ProcedimientoBean procedimiento) throws ConversionException {
		
		ProcedimientoBean procedimientoDest = obtenerProcedimiento(procedimiento.getId());
		
		BeanUtils.copyNotNullProperties(procedimiento,procedimientoDest);
		
		procedimientoDAO.update(procedimientoDest);
	}
	
	/**
	 * Obtiene un <code>ProcedimientoItemBean</code>
	 * en base al <i>idProcedimientoItem</i> recibido
	 * como parámetro.<br>
	 * @param idProcedimientoItem
	 * @return un ProcedimientoItemBean
	 */
	public ProcedimientoItemBean obtenerProcedimientoItem(Long idProcedimientoItem) {
		
		return procedimientoItemDAO.read(idProcedimientoItem);
	}
	
	/**
	 * Modifica un procedimientoItem con los datos
	 * no nulos del mapa recibido como parámetro.<br>
	 * @param procedimientoItem
	 * @throws ConversionException 
	 */
	private PacBean modificarPacBean(PacBeanUpdate properties) throws ConversionException {
		
		PacBean pacBeanDest = obtenerPacBean(properties.getId());
		
		BeanUtils.setProperties(properties, pacBeanDest);
		
		pacDAO.update(pacBeanDest);
		
		return pacBeanDest;
	}	
	
	/**
	 * Modifica un procedimientoItem con los datos
	 * no nulos del <code>ProcedimientoItemBean</code> recibido
	 * como parámetro.<br>
	 * @param procedimientoItem
	 * @throws ConversionException 
	 */
	public ProcedimientoItemBean modificarProcedimientoItem(ProcedimientoItemBean procedimiento) throws ConversionException {
		
		ProcedimientoItemBean procedimientoDest = obtenerProcedimientoItem(procedimiento.getId());
		
		BeanUtils.copyNotNullProperties(procedimiento,procedimientoDest);
		
		procedimientoItemDAO.update(procedimientoDest);
		
		return procedimientoDest;
	}
	
	/**
	 * Modifica el resultado fontar y el monto
	 * de un item.<br>
	 * @param idProcedimientoItem
	 * @param proveedor
	 * @param idMoneda
	 * @param montoFontar
	 * @param resultadoFontar
	 * @throws ConversionException
	 */
	public void modificarResultadoFontarItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoFontar,ResultadoFontar resultadoFontar) {
		
		ProcedimientoItemBean procedimientoItem = obtenerProcedimientoItem(idProcedimientoItem);
		procedimientoItem.setResultadoFontar(resultadoFontar);
		PacBean pacItem = obtenerPacBean(procedimientoItem.getIdPacItem());
		if(resultadoFontar.esNegativo()) {
			procedimientoItem.setIdMoneda(null);
			procedimientoItem.setMontoFontar(null);
			
			pacItem.setIdMoneda(null);
			pacItem.setMontoAdjudicacion(null);
			pacItem.setFechaAdjudicacion(null);
			pacItem.setProveedor(null);
		} else {
			procedimientoItem.setIdMoneda(idMoneda);
			
			procedimientoItem.setMontoFontar(montoFontar);
			if(ResultadoFontar.APROB_PEND_UFFA.equals(resultadoFontar)) {
				procedimientoItem.setMontoUffa(montoFontar);
			} else if(ResultadoFontar.APROB_PEND_BID.equals(resultadoFontar)) {
				procedimientoItem.setMontoBid(montoFontar);
			}
			
			pacItem.setIdMoneda(idMoneda);
			pacItem.setMontoAdjudicacion(montoFontar);
			pacItem.setFechaAdjudicacion(DateTimeUtil.getDate());
			pacItem.setProveedor(proveedor);
		}
		
		procedimientoItemDAO.update(procedimientoItem);
		
		pacDAO.update(pacItem);
	}
	
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
	public void modificarResultadoUffaItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoUffa,ResultadoUffaBid resultadoUffa) {
		
		ProcedimientoItemBean procedimientoItem = obtenerProcedimientoItem(idProcedimientoItem);
		PacBean pacItem = obtenerPacBean(procedimientoItem.getIdPacItem());
		if(resultadoUffa.esNegativo()) {
			procedimientoItem.setIdMoneda(null);
			procedimientoItem.setMontoUffa(null);
			
			pacItem.setIdMoneda(null);
			pacItem.setMontoAdjudicacion(null);
			pacItem.setFechaAdjudicacion(null);
			pacItem.setProveedor(null);
		} else {
			procedimientoItem.setIdMoneda(idMoneda);
			procedimientoItem.setMontoUffa(montoUffa);
			
			pacItem.setIdMoneda(idMoneda);
			pacItem.setMontoAdjudicacion(montoUffa);
			pacItem.setFechaAdjudicacion(DateTimeUtil.getDate());
			pacItem.setProveedor(proveedor);
		}
		procedimientoItem.setResultadoUffa(resultadoUffa);
		
		procedimientoItemDAO.update(procedimientoItem);
		
		
		pacDAO.update(pacItem);
	}
	
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
	public void modificarResultadoBidItem(Long idProcedimientoItem,String proveedor,Long idMoneda,BigDecimal montoBid,ResultadoUffaBid resultadoBid) {
		
		ProcedimientoItemBean procedimientoItem = obtenerProcedimientoItem(idProcedimientoItem);
		PacBean pacItem = obtenerPacBean(procedimientoItem.getIdPacItem());
		
		if(resultadoBid.esNegativo()) {
			procedimientoItem.setIdMoneda(null);
			procedimientoItem.setMontoBid(null);
			
			pacItem.setIdMoneda(null);
			pacItem.setMontoAdjudicacion(null);
			pacItem.setFechaAdjudicacion(null);
			pacItem.setProveedor(null);
		} else {
			procedimientoItem.setIdMoneda(idMoneda);
			procedimientoItem.setMontoBid(montoBid);
			
			pacItem.setIdMoneda(idMoneda);
			pacItem.setMontoAdjudicacion(montoBid);
			pacItem.setFechaAdjudicacion(DateTimeUtil.getDate());
			pacItem.setProveedor(proveedor);
		}
		procedimientoItem.setResultadoBid(resultadoBid);
		procedimientoItemDAO.update(procedimientoItem);
		pacDAO.update(pacItem);
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene items que deben ser evaluados
	 * por la UFFA.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerTieneItemsParaUffa(Long idProcedimiento) {
		
		Boolean conItemsParaUFFA = false;
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsPorProcedimiento(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(item.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_UFFA)) {
				conItemsParaUFFA = true;
				break;
			} 
		}
		
		return conItemsParaUFFA;
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene items que deben ser evaluados
	 * por la BID.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerTieneItemsParaBid(Long idProcedimiento) {
		
		Boolean conItemsParaBID = false;
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsPorProcedimiento(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(item.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_BID)) {
				conItemsParaBID = true;
				break;
			} 
		}
		
		return conItemsParaBID;
	}
	
	/**
	 * Determina si el procedimiento tiene algún item
	 * con aprobación incompleta y por lo tanto requiere
	 * otra vuelta.
	 * @param idProcedimiento
	 * @return true o false
	 */
	public boolean obtenerTieneItemsAprobadosParcialmente(Long idProcedimiento) {
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsPorProcedimiento(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(
					(		ResultadoFontar.APROB_PEND_UFFA.equals(item.getResultadoFontar()) 
							&&	item.tieneAprobacionParcialDeUffa()
					) ||
					(		ResultadoFontar.APROB_PEND_BID.equals(item.getResultadoFontar()) 
						&&	item.tieneAprobacionParcialDelBid()
					)
			) return true;
		}
		return false;
	}	
	
	/**
	 * Determina si cada item no anulado de un procedimiento
	 * tiene cargado su resultado fontar: esto
	 * implica tener cagada la moneda, el monto
	 * y el resultado fontar.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoFontar(Long idProcedimiento) {
		
		Boolean cadaItemTieneResultadoFontar = true;
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsPorProcedimiento(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(!item.getCodigoEstado().equals(EstadoPacItem.ANULADO) && !item.tieneDatosResultadoFontar()) {
				cadaItemTieneResultadoFontar = false;
				break;
			}
		}
		
		return cadaItemTieneResultadoFontar;
	}
	public List<ProcedimientoItemBean> obtenerProcedimientoItemPac(Long idPacItem) {
		return procedimientoItemDAO.findByPac(idPacItem);
	}
	
	/**
	 * Determina si los items de un procedimiento
	 * asignados a la uffa tienen cargado
	 * el resultado uffa: esto implica
	 * tener cargado la moneda, el montoUffa
	 * y el resultadoUffa.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoUffa(Long idProcedimiento) {
		
		Boolean cadaItemTieneResultadoUffa = true;
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsDeProcedimientoPendientesDeResultadoUffa(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(!item.tieneDatosResultadoUffa()) {
				cadaItemTieneResultadoUffa = false;
				break;
			}
		}
		
		return cadaItemTieneResultadoUffa;
	}	

	/**
	 * Determina si los item de un procedimiento
	 * asignados a la bid tienen cargado
	 * el resultado bid: esto implica
	 * tener cargado la moneda, el montoBid
	 * y el resultadoBid.<br>
	 * @param idProcedimiento
	 * @return true o false
	 */
	public Boolean obtenerEstadoItemsResultadoBid(Long idProcedimiento) {
		
		Boolean cadaItemTieneResultadoBid = true;
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsDeProcedimientoPendientesDeResultadoBid(idProcedimiento);
		
		for (ProcedimientoItemBean item : listaItems) {
			if(!item.tieneDatosResultadoBid()) {
				cadaItemTieneResultadoBid = false;
				break;
			}
		}
		
		return cadaItemTieneResultadoBid;
	}		
	
	/**
	 * Determina si a un procedimiento se le debe cargar
	 * el resultado Bid y no han sido cargados
	 * los mismos.<br> 
	 * @param idProcedimiento
	 * @return true si tiene items por cargar resultado bid
	 */
	public Boolean obtenerTieneResultadoBidPendiente(Long idProcedimiento) {
		
		List<ProcedimientoItemBean> listBid = obtenerItemsDeProcedimientoPendientesDeResultadoBid(idProcedimiento);
		ProcedimientoBean procedimiento = obtenerProcedimiento(idProcedimiento);
		
		return listBid.size()>0 
			&& !procedimiento.tieneDatosResultadoBid();
	}
	
	/**
	 * Determina si a un procedimiento se le debe cargar
	 * el resultado Uffa (ya que tiene items pendientes de
	 * resultado UFFA)y aún no han sido cargados.
	 * @param idProcedimiento
	 * @return true si tiene items por cargar resultado Uffa
	 */
	public Boolean obtenerTieneResultadoUffaPendiente(Long idProcedimiento) {
		
		List<ProcedimientoItemBean> listUffa = obtenerItemsDeProcedimientoPendientesDeResultadoUffa(idProcedimiento);
		ProcedimientoBean procedimiento = obtenerProcedimiento(idProcedimiento);
		
		return listUffa.size()>0 
			&& !procedimiento.tieneDatosResultadoUffa();
	}	
	
	/**
	 * Pasa los items uffa de un procedimiento
	 * al estado correspondiente y pone la fecha de 
	 * adjudicacion si el item fue adjudicado.<br>
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */
	public void modificarEstadoItemsUffa(Long idProcedimiento,Date fechaAdjudicacion) throws ConversionException {
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsDeProcedimientoPendientesDeResultadoUffa(idProcedimiento);
		
		for (ProcedimientoItemBean procedimientoItem : listaItems) {
			
			if(ResultadoUffaBid.APROB_FINAL.equals(procedimientoItem.getResultadoUffa())) {

				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("fechaAdjudicacion", fechaAdjudicacion);
				item.put("codigoEstado", EstadoPacItem.ADJUDICADO);
				modificarPacBean(item);
			}
			else if(procedimientoItem.getResultadoUffa().esNegativo()) {
					PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
					item.put("codigoEstado", EstadoPacItem.PENDIENTE_DE_COMPRA);
					item.put("moneda", null);
					item.put("idMoneda", null);
					item.put("montoAdjudicacion", null);
					modificarPacBean(item);
			}
			else {
				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("codigoEstado", EstadoPacItem.EN_PROCESO_DE_COMPRA);
				modificarPacBean(item);
			}
		}
	}

	/**
	 * Pasa los items bid de un procedimiento
	 * al estado correspondiente y pone la fecha de 
	 * adjudicacion si el item fue adjudicado.<br>
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */	
	public void modificarEstadoItemsBid(Long idProcedimiento,Date fechaAdjudicacion) throws ConversionException {
		
		List<ProcedimientoItemBean> listaItems = obtenerItemsDeProcedimientoPendientesDeResultadoBid(idProcedimiento);
		
		for (ProcedimientoItemBean procedimientoItem : listaItems) {
			
			if(ResultadoUffaBid.APROB_FINAL.equals(procedimientoItem.getResultadoBid())) {
				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("fechaAdjudicacion", fechaAdjudicacion);
				item.put("codigoEstado", EstadoPacItem.ADJUDICADO);
				modificarPacBean(item);
			} 
			else if(procedimientoItem.getResultadoBid().esNegativo()) {
				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("codigoEstado", EstadoPacItem.PENDIENTE_DE_COMPRA);
				item.put("moneda", null);
				item.put("idMoneda", null);
				item.put("montoAdjudicacion", null);
				modificarPacBean(item);
			}
			else {
				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("codigoEstado", EstadoPacItem.EN_PROCESO_DE_COMPRA);
				modificarPacBean(item);
			}
		}		
	}
	
	/**
	 * Pasa los items de un procedimiento al estado
	 * <i>Pediente de Compra</i> si el resultado fontar
	 * fue <i>Desierto</i> o <i>No Autorizado</i>.<br> 
	 * Esto debe aplicarse cuando se <code>Carga Resultado Fontar</code>.<br> 
	 * @param idProcedimiento
	 * @param fechaAdjudicacion 
	 * @throws ConversionException
	 */	
	public void modificarEstadoItemsFontar(Long idProcedimiento) throws ConversionException {
		List<ProcedimientoItemBean> listaItems = obtenerItemsPorProcedimiento(idProcedimiento);
		
		for (ProcedimientoItemBean procedimientoItem : listaItems) {
			
			if(procedimientoItem.getResultadoFontar().esNegativo()) {
				PacBeanUpdate item = new PacBeanUpdate(procedimientoItem.getIdPacItem());
				item.put("codigoEstado", EstadoPacItem.PENDIENTE_DE_COMPRA);
				item.put("moneda", null);
				item.put("idMoneda", null);
				item.put("montoAdjudicacion", null);
				modificarPacBean(item);
			}
		}		
	}
}
