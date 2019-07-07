package com.fontar.bus.impl.proyecto.desembolso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fontar.bus.api.proyecto.desembolso.MontoAutorizadoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.MontoDesembolsadoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.MontoPrevistoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.ProyectoDesembolsoService;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionSeguimientoDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoDesembolsoDAO;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.assembler.ProyectoDesembolsoAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDesembolsoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.desembolso.EstadoProyectoDesembolso;
import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.acegi.SecuredService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;


@SecuredService
public class ProyectoDesembolsoServiceImpl implements ProyectoDesembolsoService {

	private ProyectoDesembolsoDAO proyectoDesembolsoDAO;
	private ProyectoDAO proyectoDao;
	private SeguimientoDAO seguimientoDao;
	private EvaluacionSeguimientoDAO evaluacionSeguimientoDao;
	private BitacoraDAO bitacoraDao;

	public void setProyectoDesembolsoDAO(ProyectoDesembolsoDAO proyectoDesembolsoDAO) {
		this.proyectoDesembolsoDAO = proyectoDesembolsoDAO;
	}

	public void setProyectoDao(ProyectoDAO proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	public void create(ProyectoDesembolsoDTO proyectoDesembolso) throws MontoPrevistoSuperaMontoDelBeneficio {

		/*
		 * Chequeo que el pago agregado junto con los demas no suman mas que
		 * el monto del beneficio.
		 */
		ProyectoBean proyecto = proyectoDao.read(proyectoDesembolso.getIdProyecto());
		ProyectoPresupuestoBean presupuesto = proyecto.getProyectoPresupuesto();
		//A esta altura debe haber un presupuesto vinculado al proyecto
		if(presupuesto==null) throw new RuntimeException("El proyecto no tiene un presupuesto.");
		BigDecimal montoBeneficio = presupuesto.getMontoSolicitado();
		List<ProyectoDesembolsoBean> desembolsos = proyectoDesembolsoDAO.findByProyecto(proyecto.getId());
		BigDecimal montoTotalADesembolsar = proyectoDesembolso.getMontoOriginal();
		for (ProyectoDesembolsoBean desembolso : desembolsos) {
			montoTotalADesembolsar = montoTotalADesembolsar.add(getMonto(desembolso.montoVigente()));
		}
		
		if(montoTotalADesembolsar.compareTo(montoBeneficio)>0) 
			throw new MontoPrevistoSuperaMontoDelBeneficio(montoBeneficio, montoTotalADesembolsar);

		ProyectoDesembolsoBean proyectoDesembolsoBean = ProyectoDesembolsoAssembler.getInstance().buildBean(proyectoDesembolso);

		//Valores por defecto
		if(proyectoDesembolsoBean.getEsAnticipo()==null) {
			proyectoDesembolsoBean.setEsAnticipo(desembolsos.size()==0);			
		}
		proyectoDesembolsoBean.setCodigoEstado(EstadoProyectoDesembolso.NO_PAGADO);
		
		proyectoDesembolsoDAO.create(proyectoDesembolsoBean);
	}

	public void update(ProyectoDesembolsoDTO proyectoDesembolso) throws MontoPrevistoSuperaMontoDelBeneficio, MontoAutorizadoSuperaMontoDelBeneficio, MontoDesembolsadoSuperaMontoDelBeneficio {
		/*
		 * Chequeo que los pagos previstos, autorizados y realizados 
		 * suman lo mismo o menos que el monto del beneficio.
		 */
		ProyectoDesembolsoBean bean = proyectoDesembolsoDAO.read(proyectoDesembolso.getId());
		ProyectoPresupuestoBean presupuesto = bean.getProyecto().getProyectoPresupuesto();
		//A esta altura debe haber un presupuesto vinculado al proyecto
		if(presupuesto==null) throw new RuntimeException("El proyecto no tiene un presupuesto.");
		BigDecimal montoBeneficio = presupuesto.getMontoSolicitado();
		
		//Copio el estado para completar el DTO
		proyectoDesembolso.setCodigoEstado(bean.getCodigoEstado());
		
		List<ProyectoDesembolsoBean> desembolsos = proyectoDesembolsoDAO.findByProyecto(bean.getProyecto().getId());
		BigDecimal montoTotalVigente = getMonto(proyectoDesembolso.montoVigente());
		for (ProyectoDesembolsoBean desembolso : desembolsos) {
			//Salteo el desembolso que estoy actualizando porque ya los sumé
			if(!desembolso.getId().equals(proyectoDesembolso.getId())) {
				montoTotalVigente = montoTotalVigente.add(getMonto(desembolso.montoVigente()));
			}
		}
		
		if(montoTotalVigente.compareTo(montoBeneficio)>0) {
			if(proyectoDesembolso.getMontoDesembolsado()==null) {
				if(proyectoDesembolso.getMontoAutorizado()==null) {
					throw new MontoPrevistoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente);
				} else {
					throw new MontoAutorizadoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente);
				}
			} else {
				throw new MontoDesembolsadoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente); 
			}
		} 
		
		ProyectoDesembolsoAssembler.getInstance().updateBeanNotNull(proyectoDesembolso, bean);
		proyectoDesembolsoDAO.update(bean);
	}
	/**
	 * Auxiliar
	 */
	private BigDecimal getMonto(BigDecimal monto) {
		if(monto==null) return BigDecimal.ZERO;
		else return monto; 
	}

	public ProyectoDesembolsoDTO load(Long id) {
		return ProyectoDesembolsoAssembler.getInstance().buildDto(proyectoDesembolsoDAO.read(id));
	}

	public List<ProyectoDesembolsoDTO> getAll() {
		return ProyectoDesembolsoAssembler.getInstance().buildDto(proyectoDesembolsoDAO.getAll());
	}
	
	public List<ProyectoDesembolsoDTO> findByProyecto(Long idProyecto) {
		return ProyectoDesembolsoAssembler.getInstance().buildDto(proyectoDesembolsoDAO.findByProyecto(idProyecto));
	}

	/**
	 * Devuelve una lista de desembolsos para usar en el cronograma de un seguimiento.
	 * Incluye:
	 * <ul>
	 * 	<li>Anticipo</li>
	 * 	<li>Desembolsos no pagados de todo el proyecto</li>
	 * 	<li>Desembolsos autorizados o pagados en seguimientos anteriores al dado</li>
	 * 	<li>Desembolsos autorizados o pagados en el seguimiento dado</li>
	 * </ul>
	 * @param idSeguimiento
	 * @return
	 */
	private List<ProyectoDesembolsoDTO> desembolsosUtilesAlseguimiento(SeguimientoBean seguimiento) {
		List<ProyectoDesembolsoDTO> ret = new ArrayList<ProyectoDesembolsoDTO>();
		List<ProyectoDesembolsoDTO> desembolsosDelProyecto = ProyectoDesembolsoAssembler.getInstance().buildDto(proyectoDesembolsoDAO.findByProyecto(seguimiento.getIdProyecto()));
		for(ProyectoDesembolsoDTO desembolso : desembolsosDelProyecto) {
			if(desembolso.getIdSeguimientoDeAutorizacion()==null) {
				//Esto incluye: anticipos y todos los no pagados.
				ret.add(desembolso);
			} else {
				//Esta autorizado ¿en que seguimiento?
				if(desembolso.getIdSeguimientoDeAutorizacion().longValue() <= seguimiento.getId().longValue()) {
					ret.add(desembolso);	
				}
			}
		}
		return ret;
	}

	public void delete(Long id) {
		proyectoDesembolsoDAO.delete(proyectoDesembolsoDAO.read(id));
	}

	public void pagarAnticipo(Long idDesembolso, BigDecimal monto, Date fecha)  throws MontoAutorizadoSuperaMontoDelBeneficio, MontoDesembolsadoSuperaMontoDelBeneficio {
		ProyectoDesembolsoBean bean = proyectoDesembolsoDAO.read(idDesembolso);
		//Chequeo que el item sea realmente un anticipo
		if(!bean.getEsAnticipo()) throw new RuntimeException("No puede pagarse sin autorizacion de por medio un item que no es anticipo.");
		/*
		 * Chequeo que los pagos realizados suman lo mismo
		 * o menos que el monto del beneficio.
		 */
		ProyectoPresupuestoBean presupuesto = bean.getProyecto().getProyectoPresupuesto();
		//A esta altura debe haber un presupuesto vinculado al proyecto
		if(presupuesto==null) throw new RuntimeException("El proyecto no tiene un presupuesto.");
		BigDecimal montoBeneficio = presupuesto.getMontoSolicitado();
		//Chequeo que pueda autorizarse y pagarse
		List<ProyectoDesembolsoBean> desembolsos = proyectoDesembolsoDAO.findByProyecto(bean.getProyecto().getId());
		BigDecimal montoTotalVigente = monto;
		for (ProyectoDesembolsoBean desembolso : desembolsos) {
			//Salteo el desembolso que estoy actualizando porque ya los sumé
			if(!desembolso.getId().equals(idDesembolso)) {
				montoTotalVigente = montoTotalVigente.add(getMonto(desembolso.montoVigente()));
			}
		}
		
		if(montoTotalVigente.compareTo(montoBeneficio)>0) 
			throw new MontoDesembolsadoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente);
		
		bean.setFechaPago(fecha);
		bean.setMontoAutorizado(monto);
		bean.setMontoDesembolsado(monto);
		bean.setCodigoEstado(EstadoProyectoDesembolso.PAGADO);
		proyectoDesembolsoDAO.update(bean);
		
		//Actualizo el estado del proyecto
		if(!bean.getProyecto().getEstado().equals(EstadoProyecto.SEGUIMIENTO)) {
			bean.getProyecto().setEstado(EstadoProyecto.SEGUIMIENTO);
			proyectoDao.update(bean.getProyecto());
		}
		
		//bitacoreo
		BitacoraBean bitacoraProy = new BitacoraBean();
		bitacoraProy.setTipo(TipoBitacora.BASICO);
		bitacoraProy.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraProy.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraProy.setTema("Pago de Anticipo");
		bitacoraProy.setDescripcion("NA");
		
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		bitacoraProy.setIdUsuario(authenticationService.getUserId());
		bitacoraProy.setIdProyecto(bean.getIdProyecto());
		
		bitacoraDao.save(bitacoraProy);
			
	}

	public void registrarPago(Long idDesembolso, BigDecimal monto, Date fecha)  throws MontoDesembolsadoSuperaMontoDelBeneficio {
		ProyectoDesembolsoBean bean = proyectoDesembolsoDAO.read(idDesembolso);
		//Precondiciones
		if(bean.getEsAnticipo())
			throw new RuntimeException("No puede pagarse un anticipo con este método.");
		if( !bean.getCodigoEstado().equals(EstadoProyectoDesembolso.AUTORIZADO) &&
			!bean.getCodigoEstado().equals(EstadoProyectoDesembolso.PAGADO))
			throw new RuntimeException("No puede pagarse un item que no está autorizado o pagado.");
		/*
		 * Chequeo que los pagos realizados suman lo mismo
		 * o menos que el monto del beneficio.
		 */
		ProyectoPresupuestoBean presupuesto = bean.getProyecto().getProyectoPresupuesto();
		//A esta altura debe haber un presupuesto vinculado al proyecto
		if(presupuesto==null) throw new RuntimeException("El proyecto no tiene un presupuesto.");
		BigDecimal montoBeneficio = presupuesto.getMontoSolicitado();
		
		List<ProyectoDesembolsoBean> desembolsos = proyectoDesembolsoDAO.findByProyecto(bean.getProyecto().getId());
		BigDecimal montoTotalVigente = monto;
		for (ProyectoDesembolsoBean desembolso : desembolsos) {
			//Salteo el desembolso que estoy actualizando porque ya los sumé
			if(!desembolso.getId().equals(idDesembolso))
				montoTotalVigente = montoTotalVigente.add(getMonto(desembolso.montoVigente()));
		}
		
		if(montoTotalVigente.compareTo(montoBeneficio)>0) 
			throw new MontoDesembolsadoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente);
		
		bean.setFechaPago(fecha);
		bean.setMontoDesembolsado(monto);
		bean.setCodigoEstado(EstadoProyectoDesembolso.PAGADO);
		proyectoDesembolsoDAO.update(bean);
		
//		bitacoreo
		BitacoraBean bitacoraProy = new BitacoraBean();
		bitacoraProy.setTipo(TipoBitacora.BASICO);
		bitacoraProy.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraProy.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraProy.setTema("Registro de Pago");
		bitacoraProy.setDescripcion("NA");
		
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		bitacoraProy.setIdUsuario(authenticationService.getUserId());
		bitacoraProy.setIdProyecto(bean.getIdProyecto());
		
		bitacoraDao.save(bitacoraProy);
		
	}

	public void autorizarPago(Long idDesembolso, BigDecimal monto, Long idSeguimientoDeAutorizacion) throws MontoAutorizadoSuperaMontoDelBeneficio {
		ProyectoDesembolsoBean bean = proyectoDesembolsoDAO.read(idDesembolso);
		//Precondiciones
		if(bean.getEsAnticipo())
			throw new RuntimeException("Los anticipos no requieren autirización.");
		if( !bean.getCodigoEstado().equals(EstadoProyectoDesembolso.NO_PAGADO) &&
			!bean.getCodigoEstado().equals(EstadoProyectoDesembolso.AUTORIZADO))
			throw new RuntimeException("No puede autrizarse un item que ya fue pagado.");
		/*
		 * Chequeo que los pagos realizados suman lo mismo
		 * o menos que el monto del beneficio.
		 */
		ProyectoPresupuestoBean presupuesto = bean.getProyecto().getProyectoPresupuesto();
		//A esta altura debe haber un presupuesto vinculado al proyecto
		if(presupuesto==null) throw new RuntimeException("El proyecto no tiene un presupuesto.");
		BigDecimal montoBeneficio = presupuesto.getMontoSolicitado();
		
		List<ProyectoDesembolsoBean> desembolsos = proyectoDesembolsoDAO.findByProyecto(bean.getProyecto().getId());
		BigDecimal montoTotalVigente = monto;
		for (ProyectoDesembolsoBean desembolso : desembolsos) {
			//Salteo el desembolso que estoy actualizando porque ya los sumé
			if(!desembolso.getId().equals(idDesembolso))
				montoTotalVigente = montoTotalVigente.add(getMonto(desembolso.montoVigente()));
		}
		
		if(montoTotalVigente.compareTo(montoBeneficio)>0) 
			throw new MontoAutorizadoSuperaMontoDelBeneficio(montoBeneficio, montoTotalVigente);
		
		bean.setMontoAutorizado(monto);
		bean.setCodigoEstado(EstadoProyectoDesembolso.AUTORIZADO);
		bean.setIdSeguimientoDeAutorizacion(idSeguimientoDeAutorizacion);
		proyectoDesembolsoDAO.update(bean);
	}

	public CronogramaDeDesembolsosDTO obtenerCronogramaDelProyecto(Long idProyecto) {
		List<ProyectoDesembolsoDTO> desembolsos = findByProyecto(idProyecto);
		
		CronogramaDeDesembolsosDTO cronograma = new CronogramaDeDesembolsosDTO();
		
		calcularTotales(desembolsos, cronograma);
		
		//Acciones globales
		cronograma.setPermiteAgregar(true);
		cronograma.setPermiteEditar(true);
		cronograma.setPermiteEliminar(true);
		cronograma.setPermiteAutorizar(false);
		cronograma.setPermitePagarAnticipo(true);
		cronograma.setPermitePagar(false);

		//Acciones por elemento
		setearAccionesIndividuales(desembolsos, idProyecto);

		cronograma.setDesembolsos(desembolsos);
		cronograma.setIdProyecto(idProyecto);
		cronograma.setPorProyecto(true);
		return cronograma;
	}
	
	private void calcularTotales(
			List<ProyectoDesembolsoDTO> desembolsos, 
			CronogramaDeDesembolsosDTO cronograma) {
		
		BigDecimal montoTotalPrevisto = BigDecimal.ZERO;
		BigDecimal montoTotalAutorizado = BigDecimal.ZERO;
		BigDecimal montoTotalDesembolsado = BigDecimal.ZERO;
		for(ProyectoDesembolsoDTO desembolso : desembolsos) {
			if(desembolso.getMontoOriginal()!=null)montoTotalPrevisto = montoTotalPrevisto.add(desembolso.getMontoOriginal());
			if(desembolso.getMontoAutorizado()!=null)montoTotalAutorizado = montoTotalAutorizado.add(desembolso.getMontoAutorizado());
			if(desembolso.getMontoDesembolsado()!=null)montoTotalDesembolsado = montoTotalDesembolsado.add(desembolso.getMontoDesembolsado());
			
		}
		cronograma.setMontoTotalAutorizado(montoTotalAutorizado);
		cronograma.setMontoTotalDesembolsado(montoTotalDesembolsado);
		cronograma.setMontoTotalPrevisto(montoTotalPrevisto);
	}

	private void setearAccionesIndividuales(
			List<ProyectoDesembolsoDTO> desembolsos, 
			Long idProyecto) {
		setearAccionesIndividuales(desembolsos, idProyecto, null);
	}
	private void setearAccionesIndividuales(
			List<ProyectoDesembolsoDTO> desembolsos, 
			Long idProyecto,
			Long idSeguimiento) {
		//Acciones por elemento
		ProyectoBean proyecto = proyectoDao.read(idProyecto);
		if(proyecto.estaAbierto()) {
			for(ProyectoDesembolsoDTO desembolso : desembolsos) {
				/*
				 * Notar que si idSeguimiento es nulo (visualizacion de proyecto)
				 * ninguno puede autorizarse.
				 */ 
				boolean puedeAutorizarse = !desembolso.yaFuePagado() &&
						(
							//No tiene autorizacion o... 
							desembolso.getIdSeguimientoDeAutorizacion()==null ||
							//... fue autorizado en este mismo seguimiento
							desembolso.getIdSeguimientoDeAutorizacion().equals(idSeguimiento)
						)
						&& !desembolso.getEsAnticipo();
				desembolso.setPuedeAutorizarse(puedeAutorizarse);
				//Dejo disponible en esta propiedad el desembolso de autorizacion para el caso
				//en que se quiera autorizar.
				desembolso.setIdSeguimientoCorriente(idSeguimiento);
				
				desembolso.setPuedeEliminarse(
						!EstadoProyectoDesembolso.PAGADO.equals(desembolso.getCodigoEstado())
				);
				
				desembolso.setPuedeModificarseElMontoPrevisto(
						desembolso.getCodigoEstado().equals(EstadoProyectoDesembolso.NO_PAGADO)
				);
				
				desembolso.setPuedePagarse(
						(		desembolso.getProyecto().getEstado().equals(EstadoProyecto.SEGUIMIENTO) ||
								desembolso.getProyecto().getEstado().equals(EstadoProyecto.CONTRATO) ) 
					&&	desembolso.yaFueAutorizado() 
					&&	!desembolso.getEsAnticipo() 
					&&	desembolso.getIdSeguimientoDeAutorizacion().equals(idSeguimiento)
				);
				desembolso.setPuedePagarseComoAnticipo(
						(	desembolso.getProyecto().getEstado().equals(EstadoProyecto.SEGUIMIENTO) 
								||  desembolso.getProyecto().getEstado().equals(EstadoProyecto.CONTRATO) ) 
								&&	desembolso.getEsAnticipo()
				);
			}
		}
	}
	
	public CronogramaDeDesembolsosDTO obtenerCronogramaParaVisualizarSeguimiento(Long idSeguimiento) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		Long idProyecto = seguimiento.getIdProyecto();
		List<ProyectoDesembolsoDTO> desembolsos = desembolsosUtilesAlseguimiento(seguimiento);
		
		CronogramaDeDesembolsosDTO cronograma = new CronogramaDeDesembolsosDTO();
		
		calcularTotales(desembolsos, cronograma);
		
		//Acciones globales
		cronograma.setPermiteAgregar(false);
		cronograma.setPermiteEditar(false); 
		cronograma.setPermiteEliminar(false); 
		cronograma.setPermiteAutorizar(false);
		cronograma.setPermitePagarAnticipo(false);
		cronograma.setPermitePagar(false);

		//Acciones por elemento
		setearAccionesIndividuales(desembolsos, idProyecto, idSeguimiento);

		cronograma.setDesembolsos(desembolsos);
		cronograma.setIdProyecto(idProyecto);
		cronograma.setIdSeguimiento(idSeguimiento);
		cronograma.setPorSeguimiento(true);
		return cronograma;
	}

	public CronogramaDeDesembolsosDTO obtenerCronogramaParaVisualizarEvaluacionDeSeguimiento(Long idEvaluacionSeguimiento) {
		SeguimientoBean seguimiento = evaluacionSeguimientoDao.read(idEvaluacionSeguimiento).getSeguimiento();
		Long idProyecto = seguimiento.getIdProyecto();
		List<ProyectoDesembolsoDTO> desembolsos = desembolsosUtilesAlseguimiento(seguimiento);
		
		CronogramaDeDesembolsosDTO cronograma = new CronogramaDeDesembolsosDTO();
		
		calcularTotales(desembolsos, cronograma);
		
		//Acciones globales
		cronograma.setPermiteAgregar(false);
		cronograma.setPermiteEliminar(false);
		cronograma.setPermiteEditar(false);
		cronograma.setPermiteAutorizar(false);
		cronograma.setPermitePagarAnticipo(false);
		cronograma.setPermitePagar(false);
		cronograma.setDesembolsos(desembolsos);
		cronograma.setIdProyecto(idProyecto);
		cronograma.setIdSeguimiento(seguimiento.getId());
		cronograma.setPorSeguimiento(true);
		return cronograma;
	}

	public CronogramaDeDesembolsosDTO obtenerCronogramaParaCargarResultadoDeEvaluacionDeSeguimiento(Long idEvaluacionSeguimiento) {
		SeguimientoBean seguimiento = evaluacionSeguimientoDao.read(idEvaluacionSeguimiento).getSeguimiento();
		Long idProyecto = seguimiento.getIdProyecto();
		List<ProyectoDesembolsoDTO> desembolsos = desembolsosUtilesAlseguimiento(seguimiento);
		
		CronogramaDeDesembolsosDTO cronograma = new CronogramaDeDesembolsosDTO();
		
		calcularTotales(desembolsos, cronograma);
		
		//Acciones globales
		cronograma.setPermiteAgregar(false);
		cronograma.setPermiteEditar(false); 
		cronograma.setPermiteEliminar(false); 
		cronograma.setPermiteAutorizar(true);
		cronograma.setPermitePagarAnticipo(false);
		cronograma.setPermitePagar(false);

		//Acciones por elemento
		setearAccionesIndividuales(desembolsos, idProyecto, seguimiento.getId());

		cronograma.setDesembolsos(desembolsos);
		cronograma.setIdProyecto(idProyecto);
		cronograma.setIdSeguimiento(seguimiento.getId());
		cronograma.setPorSeguimiento(true);
		return cronograma;
	}

	public CronogramaDeDesembolsosDTO obtenerCronogramaParaEvaluarGestionDePago(Long idSeguimiento) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		Long idProyecto = seguimiento.getIdProyecto();
		List<ProyectoDesembolsoDTO> desembolsos = desembolsosUtilesAlseguimiento(seguimiento);
		
		CronogramaDeDesembolsosDTO cronograma = new CronogramaDeDesembolsosDTO();
		
		calcularTotales(desembolsos, cronograma);
		
		//Acciones globales
		cronograma.setPermiteAgregar(false);
		cronograma.setPermiteEditar(false); 
		cronograma.setPermiteEliminar(false); 
		cronograma.setPermiteAutorizar(false);
		cronograma.setPermitePagarAnticipo(false);
		cronograma.setPermitePagar(true);

		//Acciones por elemento
		setearAccionesIndividuales(desembolsos, idProyecto, seguimiento.getId());

		cronograma.setDesembolsos(desembolsos);
		cronograma.setIdProyecto(idProyecto);
		cronograma.setIdSeguimiento(seguimiento.getId());
		cronograma.setPorSeguimiento(true);
		return cronograma;
	}

	public void setSeguimientoDao(SeguimientoDAO seguimientoDao) {
		this.seguimientoDao = seguimientoDao;
	}

	public void setEvaluacionSeguimientoDao(EvaluacionSeguimientoDAO evaluacionSeguimientoDao) {
		this.evaluacionSeguimientoDao = evaluacionSeguimientoDao;
	}

	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}
}
