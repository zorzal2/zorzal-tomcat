package com.fontar.bus.impl.proyecto.pac;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

import org.apache.commons.logging.LogFactory;

import com.fontar.bus.api.configuracion.CotizacionService;
import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.api.configuracion.MonedaService;
import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.data.api.dao.DesembolsoUFFADAO;
import com.fontar.data.api.dao.PacDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoDesembolsoDAO;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.assembler.DesembolsoUFFAAssembler;
import com.fontar.data.impl.assembler.PacAssembler;
import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.CuadroResumenPacDTO;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.data.impl.domain.dto.PacDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.proyecto.pac.excel.parser.PacParser;
import com.fontar.util.ExcelUtil;
import com.fontar.util.ResourceManager;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.util.exception.IllegalArgumentException;

/**
 * 
 * @author ttoth
 */
public class AdministrarPACServicioImpl implements AdministrarPACServicio {
	
	private PacDAO pacDAO;

	private DesembolsoUFFADAO desembolsoDAO;
	
	private CotizacionService cotizacionService;
	
	private ProyectoDesembolsoDAO proyectoDesembolsoDAO;
	
	private AdministrarProcedimientoServicio administrarProcedimientoService;
	
	public ProyectoDesembolsoDAO getProyectoDesembolsoDAO() {
		return proyectoDesembolsoDAO;
	}

	public void setProyectoDesembolsoDAO(ProyectoDesembolsoDAO proyectoDesembolsoDAO) {
		this.proyectoDesembolsoDAO = proyectoDesembolsoDAO;
	}

	public DesembolsoUFFADAO getDesembolsoDAO() {
		return desembolsoDAO;
	}

	public void setDesembolsoDAO(DesembolsoUFFADAO desembolsoDAO) {
		this.desembolsoDAO = desembolsoDAO;
	}

	public PacDAO getPacDAO() {
		return pacDAO;
	}

	public void setPacDAO(PacDAO pacDAO) {
		this.pacDAO = pacDAO;
	}

	public Collection<PacDTO> parseArchivo(ArchivoDTO dto, Long proyectoId) throws ParsingException, PacExcelParsingException {
		/*
		 * Chequeo precondiciones:
		 *  - El proyecto tiene presupuesto con etapas.
		 *  - El proyecto no tiene pacs salvo en estado Anulado o 
		 *    Pendiente de compra
		 */
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyecto = proyectoDAO.read(proyectoId);

		ProyectoPresupuestoBean presupuesto = proyecto.getProyectoPresupuesto();
		if(presupuesto==null)
			throw new PacExcelParsingException("app.pac.excel.noHayPresupuesto");
		if(presupuesto.getEtapas().size()==0)
			throw new PacExcelParsingException("app.pac.excel.noHayEtapas");

		List<PacBean> pacsActuales = pacDAO.findByProyecto(proyectoId);
		for(PacBean pac : pacsActuales) {
			if(
				!pac.getCodigoEstado().equals(EstadoPacItem.ANULADO) &&
				!pac.getCodigoEstado().equals(EstadoPacItem.PENDIENTE_DE_COMPRA)
			) throw new PacExcelParsingException("app.pac.excel.hayItemsActivos");
		}

		/* Carga del archivo */
		
		InputStream inputStream = new ByteArrayInputStream(dto.getBytes());
		Workbook workbook;
		try {
			workbook = ExcelUtil.getWorkbook(inputStream); 
		}
		catch (PasswordException e) {
			throw new IllegalFormatException(ResourceManager.getErrorResource("app.file.requierePassword"));
		}
		catch (BiffException e) {
			throw new IllegalFormatException(ResourceManager.getErrorResource("app.file.invalidFormat"));
		}
		catch (IOException e) {
			throw new PacExcelParsingException("app.file.fileNotFound");
		}
		catch (Exception e) {
			throw new PacExcelParsingException("app.unknownError");
		}
		
		List<PacBean> newPacs = new PacParser().parse(workbook, proyecto);
		//Elimino todos los items de PAC actuales
		for (PacBean pac : pacsActuales) {
			pacDAO.delete(pac);
		}
		//Agrego los nuevos
		for (PacBean pac : newPacs) {
			pacDAO.create(pac);
		}
		
		return PacAssembler.getInstance().buildDto(newPacs);
	}

	public PacDTO loadPac(Long id) {
		PacBean bean = pacDAO.read(id);
		return PacAssembler.getInstance().buildDto(bean);
	}

	public void createPac(PacDTO pacDTO) {
		PacBean bean = new PacBean();
		bean = PacAssembler.getInstance().buildBean(pacDTO);
		pacDAO.create(bean);
	}

	public void actualizarPac(PacDTO pacDTO, Long id) {
		PacBean bean = pacDAO.read(id);
		PacAssembler.getInstance().updateBeanNotNull(bean, pacDTO);
		pacDAO.update(bean);
	}

	public void anularBean(Long id, String observaciones) {
		PacBean bean = pacDAO.read(id);
		bean.setCodigoEstado(EstadoPacItem.ANULADO);
		bean.setObservaciones(observaciones);
	}

	public VisualizarPacItemDTO obtenerDatosItemTabla(Long id) throws FaltanCotizacionesException {
		PacBean bean = pacDAO.read(id);
		VisualizarPacItemDTO itemDTO = new VisualizarPacItemDTO();
		itemDTO.setAdquisicion(bean.getTipoAdquisicion().getDescripcion());
		itemDTO.setDescripcion(bean.getDescripcion());
		itemDTO.setEtapa(bean.getEtapa());
		itemDTO.setFechaEstimada(StringUtil.formatDate(bean.getFecha()));
		itemDTO.setId(id.toString());
		itemDTO.setItem(bean.getItem().toString());
		itemDTO.setRubro(bean.getRubro().getNombre());
		if(bean.getIdMoneda()!=null) itemDTO.setIdMoneda(bean.getIdMoneda().toString());
		if (bean.getMontoAdjudicacion() != null) {
			itemDTO.setFechaAdjudicado(StringUtil.formatDate(bean.getFechaAdjudicacion()));
			itemDTO.setMontoAdjudicado(StringUtil.formatMoneyForPresentation(bean.getMontoAdjudicacion(), bean.getMoneda().getTipoMoneda()));
			itemDTO.setMontoAdjudicadoValor(bean.getMontoAdjudicacion());
		}
		if (bean.getMontoDesembolsado() != null) {
			itemDTO.setMontoTotalPedidoStr(StringUtil.formatMoneyForPresentation(bean.getMontoDesembolsado(), bean.getMoneda().getTipoMoneda()));
			itemDTO.setMontoDesembolsado(bean.getMontoDesembolsado());
			
			//Monto Total Pedido - Monto Pagado
			itemDTO.setMontoADesembolsarStr(
					StringUtil.formatMoneyForPresentation(
							getMontoPedidoPendienteDeDesembolsoEnMonedaDeAdjudicacion(bean)
						, 	bean.getMoneda().getTipoMoneda()
					)		
			);
		}
		if (bean.getMontoFontar() != null) {
			itemDTO.setMontoEstimado(StringUtil.formatMoneyForPresentation(bean.getMontoFontar()));
		}
		return itemDTO;
	}

	public Collection<DesembolsoUFFADTO> obtenerDesembolsos(Long idPac) {
		Collection<DesembolsoUFFADTO> desembolsos = new ArrayList<DesembolsoUFFADTO>();
		List<DesembolsoUFFABean> beans = desembolsoDAO.findByPac(idPac);
		for (DesembolsoUFFABean bean : beans) {
			desembolsos.add(DesembolsoUFFAAssembler.getInstance().buildDto(bean));
		}
		return desembolsos;
	}

	/*
	 *  (non-Javadoc)
	 *  monto = monto actual del desembolso
	 *  segun la fecha del pedido, buscando la cotizacion 
	 * 
	 *  monto del PAC-Item = monto * cotizacion + monto del PAC-Item anterior
	 * 
	 */
	public void createDesembolso(DesembolsoUFFADTO dto, Boolean esUltimo) throws FaltanCotizacionesException {
		DesembolsoUFFABean bean = DesembolsoUFFAAssembler.getInstance().buildBean(dto);
		desembolsoDAO.save(bean);
		/*
		 * Actualizo el campo totalizador en el pacbean. Este campo se expresa 
		 * en la moneda propia del pacbean, por lo que es posible que sea
		 * necesaria una conversion.
		 * Si no hubo desembolsos este campo es null. Si ya los hubo solo hay
		 * que sumarle el nuevo desembolso.
		 */
		PacBean pacBean = pacDAO.read(bean.getIdPac());
		BigDecimal desembolsoEnLaMonedaDelPacBean = cotizacionService.convertir(
				bean.getMontoDesembolsado(), 
				bean.getIdMoneda(), 
				pacBean.getIdMoneda(), 
				bean.getFechaPedido()
			);
		BigDecimal desembolsoAcumuladoActual = pacBean.getMontoDesembolsado();
		if(desembolsoAcumuladoActual==null) {
			//No habia desembolsos previos. Este es el unico y total
			pacBean.setMontoDesembolsado(desembolsoEnLaMonedaDelPacBean);
		} else {
			//Habia desembolsos. Sumo el actual al acumulado.
			pacBean.setMontoDesembolsado(desembolsoAcumuladoActual.add(desembolsoEnLaMonedaDelPacBean));
		}
		if (esUltimo) {
			pacBean.setCodigoEstado(EstadoPacItem.DESEMBOLSADO);
			pacBean.setFechaDesembolso(DateTimeUtil.getDate());
			pacBean.setFechaEstado(DateTimeUtil.getDate());
		}
		pacDAO.update(pacBean);
	}

	public CotizacionService getCotizacionService() {
		return cotizacionService;
	}

	public void setCotizacionService(CotizacionService cotizacionService) {
		this.cotizacionService = cotizacionService;
	}

	public void updateDesembolso(DesembolsoUFFADTO dto) throws MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException, FaltanCotizacionesException {
		List<DesembolsoUFFABean> list = desembolsoDAO.findByPacCuota(dto.getIdPac(),dto.getCuota());
		DesembolsoUFFABean bean = list.get(0);
		
		//Verifico que el monto del pago ingresado no supere al monto del pedido del desembolso
		BigDecimal montoPedidoEnPesos = cotizacionService.convertir(
				bean.getMontoDesembolsado(),//Monto del pedido de desembolso 
				bean.getIdMoneda(), 
				MonedaService.PESO_ARGENTINO_ID, 
				dto.getFechaPago()
		);
		if(dto.getMontoPago().compareTo(montoPedidoEnPesos)>0) {
			throw new MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException();
		}		
		bean.setFechaPago(dto.getFechaPago());
		bean.setMontoPago(dto.getMontoPago());
		bean.setEsAnticipo(dto.getEsAnticipo());
//		DesembolsoUFFAAssembler.getInstance().updateBeanNotNull(bean, dto);
		desembolsoDAO.update(bean);
	}

	public void updatePatrimonio(String id) {
		PacBean bean = pacDAO.read(new Long(id));
		if (bean.getEsPatrimonio() == null) {
			bean.setEsPatrimonio(true);
		}
		else {
			bean.setEsPatrimonio(!bean.getEsPatrimonio());
		}
		pacDAO.update(bean);
	}

	public CuadroResumenPacDTO obtenerDatosCuadroResumen(Long idProyecto, ProyectoBean bean) {
		CuadroResumenPacDTO cuadroDTO = new CuadroResumenPacDTO();
		List<PacBean> list = pacDAO.findByProyecto(idProyecto);
		
		//Cargo los montos de adjudicación y desembolso ya convertidos a pesos
		MontosConvertidosList montosConvertidos = convertirMontos(list);
		//Devuelvo en el DTO las monedas sin cotizacion
		cuadroDTO.setMonedasSinCotizacion(montosConvertidos.monedasSinCotizacion());
		
		//Monto pendiente de compra. Siempre puede ser calculado.
		BigDecimal montoPendiente = getMontoPendienteDeCompra(list);
		cuadroDTO.setMontoPendiente(StringUtil.formatMoneyForPresentation(montoPendiente));

		//Items en proceso de compra. Pueden no ser calculables si no hay cotizaciones.
		BigDecimal montoEnProcesoDeCompra = getMontoEnProcesoDeCompra(list, montosConvertidos);
		cuadroDTO.setMontoProceso(StringUtil.formatMoneyForPresentation(montoEnProcesoDeCompra));
		
		//Monto pagado. Siempre es calculable y esta en pesos.
		BigDecimal montoPagado = getMontoPagado(list);
		cuadroDTO.setMontoDesembolsado(StringUtil.formatMoneyForPresentation(montoPagado));
		
		//Intento calcular el adjudicado-desembolsado
		BigDecimal montoAdjudicado = getMontoAdjudicado(list, montosConvertidos);
		try {
			cuadroDTO.setMontoAdjudicacionDesembolso(StringUtil.formatMoneyForPresentation(montoAdjudicado.subtract(montoPagado)));
		} catch(NullPointerException e) {
			cuadroDTO.setMontoAdjudicacionDesembolso("");
		}
		//monto de anticipo. no puede ser null.
		BigDecimal montoAnticipo = getMontoAnticipo(idProyecto, list);
		cuadroDTO.setMontoAnticipio(StringUtil.formatMoneyForPresentation(montoAnticipo));
		
		//monto de total
		BigDecimal montoFontarTotal;
		try {
			montoFontarTotal = montoAdjudicado.add(montoAnticipo.add(montoEnProcesoDeCompra.add(montoPendiente)));
			cuadroDTO.setMontoFontarTotal(StringUtil.formatMoneyForPresentation(montoFontarTotal));
		} catch(NullPointerException e) {
			montoFontarTotal = null;
			cuadroDTO.setMontoFontarTotal("");
		}

		//total contraparte
		SeguimientoDAO seguimientoDao = (SeguimientoDAO) ContextUtil.getBean("seguimientoDao");
		BigDecimal contraparte = seguimientoDao.selectContraparteRendicionAprobadaDelProyecto(idProyecto);
		cuadroDTO.setMontoCPTotal(StringUtil.formatMoneyForPresentation(contraparte));
		
		ProyectoPresupuestoBean presupuesto = bean.getProyectoPresupuesto();
		if (presupuesto != null) {
			BigDecimal montoSolicitadoEnPresupuesto = presupuesto.getMontoSolicitado();
			BigDecimal montoContraparteFinanciamiento = presupuesto.getMontoEmpresa();
			cuadroDTO.setMontoFontarFinanciamiento(StringUtil.formatMoneyForPresentation(montoSolicitadoEnPresupuesto));
			cuadroDTO.setMontoCPFinanciamiento(StringUtil.formatMoneyForPresentation(montoContraparteFinanciamiento));
			cuadroDTO.setMontoCPDif(StringUtil.formatMoneyForPresentation(contraparte.subtract(montoContraparteFinanciamiento)));
			if(montoFontarTotal==null) {
				cuadroDTO.setMontoFontarDif("");
				//FIXME: Ver si esto esta bien. No estaba tenida en cuenta esta variante
				cuadroDTO.setEsPermitidoDesembolsar(false);
				//FIXME: Ver si esto esta bien. No estaba tenida en cuenta esta variante
				cuadroDTO.setEsDistintoZero(false);
			} else {
				cuadroDTO.setMontoFontarDif(StringUtil.formatMoneyForPresentation(montoFontarTotal.subtract(montoSolicitadoEnPresupuesto)));
				if(montoFontarTotal.compareTo(montoSolicitadoEnPresupuesto)>0) {
					cuadroDTO.setEsPermitidoDesembolsar(false);
				}
				else {
					cuadroDTO.setEsPermitidoDesembolsar(true);
				}
				cuadroDTO.setEsDistintoZero(!montoFontarTotal.equals(montoSolicitadoEnPresupuesto));
			}
			
		} else {
			//FIXME: Ver si esto esta bien. No estaba tenida en cuenta esta variante
			cuadroDTO.setEsPermitidoDesembolsar(false);
			cuadroDTO.setMontoFontarFinanciamiento("");
			cuadroDTO.setMontoCPFinanciamiento("");
			cuadroDTO.setMontoFontarDif("");
			//FIXME: Ver si esto esta bien. No estaba tenida en cuenta esta variante
			cuadroDTO.setEsDistintoZero(false);
			cuadroDTO.setMontoCPDif("");
		}
		
		return cuadroDTO;
	}

	private BigDecimal getMontoAnticipo(Long idProyecto, List<PacBean> list) {
		BigDecimal montoAnticipo = BigDecimal.ZERO;
		for (PacBean pacBean : list) {
			Object object = desembolsoDAO.selectMontoPagoAnticipo(pacBean.getId());
			if (object != null) {
				montoAnticipo = montoAnticipo.subtract((BigDecimal)object);
			}
		}
		Object monto = proyectoDesembolsoDAO.selectMontoPago(idProyecto);
		if (monto != null) {
			montoAnticipo = montoAnticipo.add((BigDecimal)monto);
		}
		return montoAnticipo;
	}
	private BigDecimal getMontoPendienteDeCompra(List<PacBean> list) {
		BigDecimal montoPendienteDeCompra = BigDecimal.ZERO;
		for (PacBean pacBean : list) {
			if(EstadoPacItem.PENDIENTE_DE_COMPRA.equals(pacBean.getCodigoEstado())){
				montoPendienteDeCompra = montoPendienteDeCompra.add(pacBean.getMontoFontar());
			}
		}
		return montoPendienteDeCompra;
	}
	private BigDecimal getMontoEnProcesoDeCompra(List<PacBean> list, MontosConvertidosList montosConvertidosList) {
		Iterator<MontosConvertidos> montosConvertidos = montosConvertidosList.iterator();
		BigDecimal montoEnProcesoDeCompra = BigDecimal.ZERO;
		for (PacBean pacBean : list) {
			//montos contiene el monto desembolsado y el de adjudicacion del pacbean actual
			MontosConvertidos montos = montosConvertidos.next();
			if(EstadoPacItem.EN_PROCESO_DE_COMPRA.equals(pacBean.getCodigoEstado())){
				if (pacBean.getMontoAdjudicacion() != null) {
					/*
					 * Si tienen definido un monto de adjudicación lo uso pero convertido a $.
					 * Puede ocurrir que haya monto de adjudicacion pero no haya una cotización
					 * para convertirlo a $. En ese caso no sigo calculando y devuelvo null.
					 */
					if(montos.montoAdjudicacion==null) {
						//No tengo alguna cotizacion.
						return null;
					} else {
						montoEnProcesoDeCompra = montoEnProcesoDeCompra.add(montos.montoAdjudicacion);
					}
				}
				else {
					//No hay monto de adjudicacion. Uso el monto estimado.
					montoEnProcesoDeCompra = montoEnProcesoDeCompra.add(pacBean.getMontoFontar());
				}
			}
		}
		return montoEnProcesoDeCompra;
	}
	private BigDecimal getMontoAdjudicado(List<PacBean> list, MontosConvertidosList montosConvertidosList) {
		Iterator<MontosConvertidos> montosConvertidos = montosConvertidosList.iterator();
		BigDecimal montoAdjudicacionTotal = BigDecimal.ZERO;
		for (PacBean pacBean : list) {
			//montos contiene el monto desembolsado y el de adjudicacion del pacbean actual
			MontosConvertidos montos = montosConvertidos.next();
			//El monto adjudicado se saca de los items ya desembolsados y en los adjudicados
			if(EstadoPacItem.ADJUDICADO.equals(pacBean.getCodigoEstado()) || EstadoPacItem.DESEMBOLSADO.equals(pacBean.getCodigoEstado())){
				if (pacBean.getMontoAdjudicacion() != null) {
					/*
					 * Si tienen definido un monto de adjudicacion lo uso pero convertido a $.
					 * Puede ocurrir que haya monto de adjudicacion pero no haya una cotización
					 * para convertirlo a $. En ese caso no sigo calculando y devuelvo null.
					 */
					if(montos.montoAdjudicacion==null) {
						//No tengo alguna cotizacion.
						return null;
					} else {
						montoAdjudicacionTotal = montoAdjudicacionTotal.add(montos.montoAdjudicacion);
					}
				}
			}
			//Si no hay un monto de adjudicacion ni lo cuento, aunque creo que siempre lo hay
		}
		return montoAdjudicacionTotal;
	}
	private BigDecimal getMontoPagado(List<PacBean> list) {
		BigDecimal montoPagadoTotal = BigDecimal.ZERO;
		for (PacBean pacBean : list) {
			//El monto pagado esta presente en los items ya desembolsados y en los adjudicados
			if(EstadoPacItem.ADJUDICADO.equals(pacBean.getCodigoEstado()) || EstadoPacItem.DESEMBOLSADO.equals(pacBean.getCodigoEstado())){
				montoPagadoTotal = montoPagadoTotal.add(pacBean.getMontoPagado()); 
			}
		}
		return montoPagadoTotal;
	}
	/*
	 * Auxiliares para hacer una sola vez las conversiones ya que
	 * son muy costosas.
	 */
	private class MontosConvertidos {
		//Monto de adjudicacion. Queda null si no corresponde o no se lo puede convertir a $.
		public BigDecimal montoAdjudicacion = null;
	}
	private class MontosConvertidosList extends ArrayList<MontosConvertidos> {
		private static final long serialVersionUID = 1L;
		private Map<Long, MonedaDTO> monedasSinCotizacion = new HashMap<Long, MonedaDTO>();
		public void agregarMoneda(MonedaDTO moneda) {
			if(monedasSinCotizacion.containsKey(moneda.getId())) return;
			monedasSinCotizacion.put(moneda.getId(), moneda);
		}
		public void agregarMonedas(Collection<MonedaDTO> monedas) {
			for(MonedaDTO moneda : monedas) {
				agregarMoneda(moneda);
			}
		}
		public Collection<MonedaDTO> monedasSinCotizacion() {
			return monedasSinCotizacion.values();
		}
	}
	/**
	 * Genera una lista de MontosConvertidos a partir de una lista de PacBean. La lista
	 * resultante tiene los montos de adjudicación y total desembolsado convertidos a 
	 * pesos siempre que estos montos ya estén disponibles y mientras existan las
	 * cotizaciones necesarias para las conversiones.
	 * @param list
	 * @return
	 */
	private MontosConvertidosList convertirMontos(List<PacBean> list) {
		MontosConvertidosList montosConvertidosList = new MontosConvertidosList();
		for (PacBean pacBean : list) {
			MontosConvertidos montosConvertidos = new MontosConvertidos();
			
			//Monto de adjudicacion del item
			if ((pacBean.getMontoAdjudicacion() != null) && (pacBean.getFechaAdjudicacion() != null)) {
				try {
					montosConvertidos.montoAdjudicacion = cotizacionService.convertir(
							pacBean.getMontoAdjudicacion(), 
							pacBean.getIdMoneda(), 
							MonedaService.PESO_ARGENTINO_ID, 
							pacBean.getFechaAdjudicacion());
				}
				catch (FaltanCotizacionesException e) {
					//No se puede convertir. Guardo la moneda sin cotizacion
					montosConvertidosList.agregarMonedas(e.monedasSinCotizacion());
				}
			}
			montosConvertidosList.add(montosConvertidos);
		}
		return montosConvertidosList;
	}
	/**
	 * Guarda en el pac el monto de adjudicacion. Además, si el monto de adjudicacion
	 * que se esta editando corresponde a un item que está en proceso de compra, este monto
	 * representa lo mismo que el monto Fontar del item de procedimiento asociado y por lo
	 * tanto también se actualiza ese valor.
	 */
	public void updateMontoAdjudicacion(Long id, BigDecimal montoAdjudicacion) {
		PacBean bean = pacDAO.read(id);
		bean.setMontoAdjudicacion(montoAdjudicacion);
		pacDAO.update(bean);
		if(bean.getCodigoEstado().equals(EstadoPacItem.EN_PROCESO_DE_COMPRA)) {
			//En este punto el elemento no pudo haber sido remitido a la Uffa o al Bid
			ProcedimientoItemBean procedimientoItemBean = itemDeProcedimientoActivo(id);
			procedimientoItemBean.setMontoFontar(montoAdjudicacion);
			ResultadoFontar resultadoFontar = procedimientoItemBean.getResultadoFontar();
			switch(resultadoFontar) {
			case APROB_PEND_UFFA:
				procedimientoItemBean.setMontoUffa(montoAdjudicacion);
				break;
			case APROB_PEND_BID:
				procedimientoItemBean.setMontoBid(montoAdjudicacion);
				break;
			default:
				//Código inalcanzable
				LogFactory.getLog(this.getClass()).warn("updateMontoAdjudicacion: Codigo inalcanzable alcanzado.");
			}
		}
	}

	private BigDecimal calcularTotalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota(PacBean pac, Integer cuota) throws FaltanCotizacionesException {
		Collection<DesembolsoUFFADTO> desembolsos = obtenerDesembolsos(pac.getId());
		BigDecimal totalDesembolsadoEnMonedaDeAdjudicacion = BigDecimal.ZERO;
		Long idMonedaDeAdjudicacion = pac.getIdMoneda();
		//recorro todos los desembolsos y los sumo convertidos a la moneda del pac
		for(DesembolsoUFFADTO desembolso : desembolsos) {
			//salteo la cuota que me pasaron
			//Notar que si la cuota que me pasan es null, todos los desembolsos son sumados.
			if(!desembolso.getCuota().equals(cuota)) {
				BigDecimal montoDesembolsadoConvertido = cotizacionService.convertir(
						desembolso.getMontoDesembolsado(), 
						desembolso.getIdMoneda(), 
						idMonedaDeAdjudicacion, 
						desembolso.getFechaPedido());
				totalDesembolsadoEnMonedaDeAdjudicacion = 
					totalDesembolsadoEnMonedaDeAdjudicacion.add(montoDesembolsadoConvertido);
			}
		}
		return totalDesembolsadoEnMonedaDeAdjudicacion;
	}
	public Boolean puedeDesembolsar(Long idPac, BigDecimal monto, Long idMoneda, Date fechaDeDesembolso) throws FaltanCotizacionesException {
		PacBean pac = pacDAO.read(idPac);
		Long idMonedaDeAdjudicacion = pac.getIdMoneda();
		BigDecimal montoADesembolsarEnMonedaDeAdjudicacion = cotizacionService.convertir(
				monto, 
				idMoneda, 
				idMonedaDeAdjudicacion, 
				fechaDeDesembolso);
		//El monto adjudicado deberia estar definido siempre. Pero en todo caso, el default es cero.
		BigDecimal montoAdjudicado = BigDecimal.ZERO;
		if(pac.getMontoAdjudicacion()!=null) {
			montoAdjudicado = pac.getMontoAdjudicacion();
		}
		//chequeo que la suma con lo desembolsado no supere lo adjudicado.
		BigDecimal montoDesembolsadoActualmente = pac.getMontoDesembolsado();
		if(montoDesembolsadoActualmente==null) montoDesembolsadoActualmente = BigDecimal.ZERO;
		return montoDesembolsadoActualmente.add(montoADesembolsarEnMonedaDeAdjudicacion).compareTo(montoAdjudicado)<=0;
	}

	public Boolean puedeCambiarDesembolso(Long idPac, Integer cuota, BigDecimal monto, Long idMoneda, Date fechaDeDesembolso) throws FaltanCotizacionesException {
		//Desembolso con la cuota a reemplazar
		DesembolsoUFFABean desembolsoAModificar = desembolsoDAO.findByPacCuota(idPac, cuota).get(0);
		
		PacBean pac = pacDAO.read(idPac);
		BigDecimal nuevoMontodeLaCuotaEnMonedaDeAdjudicacion = cotizacionService.convertir(
				monto, 
				idMoneda, 
				pac.getIdMoneda(), 
				desembolsoAModificar.getFechaPedido());
		
		BigDecimal totalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota = calcularTotalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota(pac, cuota);
		BigDecimal nuevoTotalDesembolsado = totalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota.add(nuevoMontodeLaCuotaEnMonedaDeAdjudicacion);
		//El monto adjudicado deberia estar definido siempre. Pero en todo caso, el default es cero.
		BigDecimal montoAdjudicado = BigDecimal.ZERO;
		if(pac.getMontoAdjudicacion()!=null) {
			montoAdjudicado = pac.getMontoAdjudicacion();
		}
		//chequeo que la suma con lo desembolsado no supere lo adjudicado.
		return nuevoTotalDesembolsado.compareTo(montoAdjudicado)<=0;
	}
	
	public Boolean existePedidoDeDesembolsoConEseNumeroDeCuota(Long idPac, Integer cuota) {
		return !desembolsoDAO.findByPacCuota(idPac, cuota).isEmpty();
	}

	public void createOrUpdateDesembolso(DesembolsoUFFADTO dto, Boolean esUltimo) throws FaltanCotizacionesException {
		PacBean pac = pacDAO.read(dto.getIdPac());
		if(existePedidoDeDesembolsoConEseNumeroDeCuota(dto.getIdPac(), dto.getCuota())) {
			/*
			 * Si ya existe un desembolso pedido con el mismo numero de cuota, lo actualizo
			 * y recalculo el totalizador en el pacbean. De paso chequeo que todo este bien
			 * y que el total de desembolsos no supere al monto de adjudicacion.
			 */ 
			BigDecimal montoDeCuotaEnMonedaDeAdjudicacion = cotizacionService.convertir(
					dto.getMontoDesembolsado(), 
					dto.getIdMoneda(), 
					pac.getIdMoneda(), 
					dto.getFechaPedido());
			BigDecimal totalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota = calcularTotalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota(pac, dto.getCuota());
			BigDecimal nuevoTotalDesembolsado = totalDesembolsadoEnMonedaDeAdjudicacionSacandoCuota.add(montoDeCuotaEnMonedaDeAdjudicacion);
			/*
			 * nuevoTotalDesembolsado no deberia pasar el monto adjudicado. Esto deberia estar 
			 * chequeado antes, pero en todo caso, no quiero dejar datos inconsistentes.
			 */
			if(nuevoTotalDesembolsado.compareTo(pac.getMontoAdjudicacion())>0) throw new IllegalArgumentException("Se supera el monto de desembolso");
			//actualizo el desembolso con los nuevos datos.
			DesembolsoUFFABean desembolso = desembolsoDAO.findByPacCuota(dto.getIdPac(), dto.getCuota()).get(0);
			DesembolsoUFFAAssembler.getInstance().updateBeanNotNull(desembolso, dto);
			/*
			 * actualizo el pac. Esto implica:
			 * 1) Actualizar el totalizador de desembolsos
			 * 2) Cambiar de estado el pac si este fue el ultimo desembolso
			 */
			pac.setMontoDesembolsado(nuevoTotalDesembolsado);
			if (esUltimo) {
				pac.setCodigoEstado(EstadoPacItem.DESEMBOLSADO);
				pac.setFechaDesembolso(DateTimeUtil.getDate());
				pac.setFechaEstado(DateTimeUtil.getDate());
			}
			pacDAO.update(pac);
			
		} else {
			createDesembolso(dto, esUltimo);
		}
	}
	public AdministrarProcedimientoServicio getAdministrarProcedimientoService() {
		return administrarProcedimientoService;
	}

	public void setAdministrarProcedimientoService(AdministrarProcedimientoServicio administrarProcedimientoService) {
		this.administrarProcedimientoService = administrarProcedimientoService;
	}
	
	/**
	 * Determina si hay items de procedimiento activos que hacen referencia 
	 * a este item. 
	 */
	public boolean bloqueadoPorProcedimiento(Long id) {
		return itemDeProcedimientoActivo(id)!=null;
	}
	
	public boolean enEsperaDeAprobacionExterna(Long id) {
		ProcedimientoItemBean procedimientoItemBean = itemDeProcedimientoActivo(id);
		if(procedimientoItemBean==null) return false;
		else return 
				administrarProcedimientoService.obtenerEstadoDatosRemisionUffa(procedimientoItemBean.getIdProcedimiento())
			|| 	administrarProcedimientoService.obtenerEstadoDatosRemisionBid(procedimientoItemBean.getIdProcedimiento());
	}
	/**
	 * Devuelve el unico item de procedimiento activo asociado al item de pac.
	 * Por definicion no puede haber dos procedimientos abiertos que incluyan
	 * un mismo item de pac, por lo tanto siempre se devuelve a lo sumo un 
	 * objeto. Si no hay procedimientos activos que involucren al item devuelve
	 * null. Esto deberia coincidir con que el estado del item de pac no sea
	 * EN_PROCESO_DE_COMPRA.
	 * @param idPacItem
	 * @return
	 */
	private ProcedimientoItemBean itemDeProcedimientoActivo(Long idPacItem) {
		List<ProcedimientoItemBean> procedimientoItems = administrarProcedimientoService.obtenerProcedimientoItemPac(idPacItem);
		for (ProcedimientoItemBean procedimientoItem : procedimientoItems) {
			if(procedimientoItem.getProcedimiento().getEstado().estaActivo())
				return procedimientoItem;
		}
		return null;
	}

	public BigDecimal getMontoPedidoPendienteDeDesembolsoEnMonedaDeAdjudicacion(PacBean bean) throws FaltanCotizacionesException {
		if(bean.getMontoDesembolsado()==null) return null;
		return bean.getMontoDesembolsado().subtract(calcularTotalPagadoEnMonedaDeAdjudicacion(bean));		
	}
	
	private BigDecimal calcularTotalPagadoEnMonedaDeAdjudicacion(PacBean pac) throws FaltanCotizacionesException {
		Collection<DesembolsoUFFADTO> desembolsos = obtenerDesembolsos(pac.getId());
		BigDecimal totalPagadoEnMonedaDeAdjudicacion = BigDecimal.ZERO;
		Long idMonedaAdjudicacion = pac.getIdMoneda();
		//recorro todos los desembolsos pagados y los sumo convertidos a la moneda del pac
		for(DesembolsoUFFADTO desembolso : desembolsos) {
			if( desembolso.getMontoPago()!=null) {
				totalPagadoEnMonedaDeAdjudicacion = totalPagadoEnMonedaDeAdjudicacion.add(
														cotizacionService.convertir(
																desembolso.getMontoPago(),
																MonedaService.PESO_ARGENTINO_ID,
																idMonedaAdjudicacion,
																desembolso.getFechaPago()
														)
				);
			}
		}
		return totalPagadoEnMonedaDeAdjudicacion;
	}
}