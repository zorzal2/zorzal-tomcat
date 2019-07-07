package com.fontar.bus.impl.seguimientos.seguimientos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

import org.hibernate.Session;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AnalisisGastosSeguimientoServicio;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.EvaluacionGeneralDAO;
import com.fontar.data.api.dao.EvaluacionSeguimientoDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.api.dao.RendicionCuentasDAO;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.ActividadDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.impl.assembler.EvaluacionSeguimientoAssembler;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.RendicionCuentasAssembler;
import com.fontar.data.impl.assembler.SeguimientoVisualizacionCabeceraAssembler;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDesembolsoBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.desembolso.EstadoProyectoDesembolso;
import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ControlFacturasDTO;
import com.fontar.data.impl.domain.dto.Evaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.EvaluacionesFinalizarControlDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionesDTO;
import com.fontar.data.impl.domain.dto.SeguimientoGestionPagoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.data.impl.domain.dto.analisisDeGastos.CalculosDeAnalisisDeGastosDTO;
import com.fontar.seguimientos.rendiciones.excel.parser.RendicionesParserBuilder;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.util.ExcelUtil;
import com.fontar.util.ResourceManager;
import com.fontar.web.action.administracion.EditarMontoAction.MontoSaver;
import com.fontar.web.util.ActionUtil;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.toolbar.NotImplementedException;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.util.hibernate.HibernateUtil;


/**
 * Servicio para la administración de seguimientos
 * 
 * @author gboaglio 
 * 
 */
public class AdministrarSeguimientoServicioImpl implements AdministrarSeguimientoServicio, MontoSaver {
	
	private SeguimientoDAO seguimientoDao;	
	private RendicionCuentasDAO rendicionCuentasDao;
	private ProyectoDAO proyectoDao;	
	private BitacoraDAO bitacoraDao;	
	private BitacoraBuilder builder = new BitacoraBuilder();
	private EvaluacionGeneralDAO evaluacionGeneralDAO;
	private EvaluacionSeguimientoDAO evaluacionSeguimientoDAO;
	private EvaluacionEvaluadorDAO evaluacionEvaluadorDAO;
	private EvaluacionDAO evaluacionDao;
	private ProyectoRaizDAO proyectoRaizDAO;
	private EtapaDAO etapaDAO;
	private ActividadDAO actividadDAO;
	private RubroDAO rubroDao;
	private PersonaDAO personaDao;
	private LocalizacionDAO localizacionDao;
	private SeguridadObjetoServicio seguridadObjetoServicio;
	private AnalisisGastosSeguimientoServicio analisisGastosSeguimientoService;
	private ConfiguracionServicio configService;

	public void setAnalisisGastosSeguimientoService(AnalisisGastosSeguimientoServicio analisisGastosSeguimientoService) {
		this.analisisGastosSeguimientoService = analisisGastosSeguimientoService;
	}

	public void setSeguridadObjetoServicio(SeguridadObjetoServicio servicio) {
		seguridadObjetoServicio = servicio;
	}
	
	public void setPersonaDao(PersonaDAO personaDao) {
		this.personaDao = personaDao;
	}

	public ActividadDAO getActividadDAO() {
		return actividadDAO;
	}

	public void setActividadDAO(ActividadDAO actividadDAO) {
		this.actividadDAO = actividadDAO;
	}

	public EtapaDAO getEtapaDAO() {
		return etapaDAO;
	}

	public void setEtapaDAO(EtapaDAO etapaDAO) {
		this.etapaDAO = etapaDAO;
	}

	public ProyectoRaizDAO getProyectoRaizDAO() {
		return proyectoRaizDAO;
	}

	public void setProyectoRaizDAO(ProyectoRaizDAO proyectoRaizDAO) {
		this.proyectoRaizDAO = proyectoRaizDAO;
	}

	/**
	 * Devuelve un dto con los datos necesarios para la cabecera de alta de un seguimiento 
	 */
	public ProyectoCabeceraDTO obtenerDatosCabeceraSeguimientoAlta(Long idProyecto) {
		
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);
	
		ProyectoCabeceraAssembler assembler = new ProyectoCabeceraAssembler();
		ProyectoCabeceraDTO cabeceraDTO = assembler.buildDTO(proyectoBean);
		
		return cabeceraDTO;
	}

	/**
	 * Devuelve un dto con los datos necesarios para la cabecera de visualizacion de un seguimiento
	 */
	public SeguimientoVisualizacionCabeceraDTO obtenerDatosCabeceraSeguimientoVisualizacion(Long idSeguimiento) {
		
		SeguimientoBean seguimientoBean = seguimientoDao.read(idSeguimiento);
		
		SeguimientoVisualizacionCabeceraDTO dto = SeguimientoVisualizacionCabeceraAssembler.getInstance().buildDTO(seguimientoBean);
		
		return dto;
		
	}
	
	/**
	 *  Puebla el seguimiento recibido como parámetro, le asigna la bitácora correspondiente, 
	 *  y lo devuelve
	 */
	private SeguimientoBean populateSeguimiento(SeguimientoBean seguimientoBean, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones) {
		
		seguimientoBean.setFecha(DateTimeUtil.getDate()); 
		seguimientoBean.setDescripcion(descripcion);
		seguimientoBean.setEstado(EstadoSeguimiento.INICIADO);
		seguimientoBean.setEsTecnico(esTecnico);
		seguimientoBean.setEsFinanciero(esFinanciero);
		seguimientoBean.setObservacion(observaciones);
		
		return seguimientoBean;
	}
	
	/**
	 * 	Carga un nuevo seguimiento asociado al proyecto del id que se pasa por parámetro
	 */
	public Long cargarSeguimiento(Long idProyecto, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones) {						
		
		SeguimientoBean seguimientoBean = new SeguimientoBean();
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);
		seguimientoBean.setIdProyecto(idProyecto);
		seguimientoBean.setProyecto(proyectoBean);
		
		seguimientoBean = populateSeguimiento(seguimientoBean, esFinanciero, esTecnico, descripcion, observaciones);

		// Creo una bitácora para el seguimiento
		BitacoraBean bitacora = this.builder.guardarSeguimiento(seguimientoBean);				
		seguimientoBean.setBitacora(bitacora);

		seguimientoDao.create(seguimientoBean);
		
		bitacora.setIdSeguimiento(seguimientoBean.getId());
		bitacoraDao.update(bitacora);
		
		// El Proyecto al cual el seguimiento está asociado pasa a estado "EN SEGUIMIENTO"
		proyectoBean.setEstado(EstadoProyecto.SEGUIMIENTO);

		proyectoDao.update(proyectoBean);
		
		return seguimientoBean.getId();
	}
	
	/**
	 *  Modifica el seguimiento con id = @id con los datos pasados por parámetro
	 */
	public void modificarSeguimiento(String id, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones) {		
		
		SeguimientoBean seguimientoBean = seguimientoDao.read(new Long(id));
		seguimientoBean = populateSeguimiento(seguimientoBean, esFinanciero, esTecnico, descripcion, observaciones);

		// Creo bitácora de actualización
		BitacoraBean bitacora = this.builder.guardarSeguimiento(seguimientoBean);				
		bitacoraDao.create(bitacora);
		
		seguimientoDao.update(seguimientoBean);
	}
	

	/**
	 * Actualiza una rendición o crea una nueva dependiendo de  
	 * si <i>rendicionBean</i> tiene seteado el <i>id</i> o no.
	 */
	public void cargarRendicion(RendicionCuentasBean rendicionBean) {
		
		if (rendicionBean.getId() == null) {
			rendicionCuentasDao.create(rendicionBean);
		}
		else {
			rendicionCuentasDao.update(rendicionBean);
		}
		
	}
	
	public void guardarRendicionEnEvaluacionDeGestionDePago(
			Long idRendicion, 
			BigDecimal montoParteGestion, 
			BigDecimal montoContraparteGestion,
			BigDecimal montoTotalGestion,
			String observaciones) throws RendicionesException {
		
		RendicionCuentasBean rendicionCuentasBean = obtenerRendicionCuentas(idRendicion);

		if(rendicionCuentasBean.getSeguimiento().getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) {
			//Hay que validar algo aca?
			rendicionCuentasBean.setMontoTotalGestion(montoTotalGestion);
			rendicionCuentasBean.setMontoParteGestion(BigDecimal.ZERO);
			rendicionCuentasBean.setMontoContraparteGestion(montoTotalGestion);
		} else {			
			if(rendicionCuentasBean.getMontoParteEvaluacion()!=null 
					&& montoParteGestion.compareTo(rendicionCuentasBean.getMontoParteEvaluacion()) == 1) {
				throw new RendicionesException("app.seguimiento.gestionPagos.montoFontarGestionMayorEvaluacion");
			}else {
				if(		rendicionCuentasBean.getMontoTotalEvaluacion()!=null 
						&& montoContraparteGestion.compareTo(rendicionCuentasBean.getMontoTotalEvaluacion()) == 1) {
					throw new RendicionesException("app.seguimiento.gestionPagos.montoContraparteGestionMayorATotalEvaluacion");
				}
			}
			
			rendicionCuentasBean.setMontoParteGestion(montoParteGestion);
			rendicionCuentasBean.setMontoContraparteGestion(montoContraparteGestion);
			rendicionCuentasBean.setMontoTotalGestion(montoContraparteGestion.add(montoParteGestion));
		}
		rendicionCuentasBean.setObservaciones(observaciones);

		cargarRendicion(rendicionCuentasBean);
	}
	

	/**
	 * Borra una rendición
	 * @param idRendicion
	 */
	public void eliminarRendicion(Long idRendicion) {
		
		RendicionCuentasBean bean = rendicionCuentasDao.read(idRendicion);
		rendicionCuentasDao.delete(bean);
	}
	
	/**
	 * Devuelve un <code>SeguimientoBean</code> con <i>id</i> igual al parámetro que recibe. 
	 */
	public SeguimientoBean obtenerSeguimiento(Long idSeguimiento) {
		return seguimientoDao.read(idSeguimiento);
	}

	/**
	 * Anula el <code>Seguimiento</code> identificado mediante
	 * <i>idSeguimiento</i> y guarda la observación ingresada
	 * en la <code>Bitacora</code>.<br>
	 * También vuelve a NO_PAGADO a los items de cronograma de 
	 * desembolso autorizados que estén autorizados en este
	 * seguimiento.
	 * @author ssanchez
	 */	
	public void anularSeguimiento(Long idSeguimiento, String observacion) {
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDao.read(idSeguimiento);

		//Deshago las autorizaciones y los pagos
		Set<ProyectoDesembolsoBean> desembolsos = seguimientoBean.getDesembolsosVinculados();
		for(ProyectoDesembolsoBean desembolso : desembolsos) {
			if(desembolso.yaFueAutorizado()) {
				desembolso.setCodigoEstado(EstadoProyectoDesembolso.NO_PAGADO);
				desembolso.setIdSeguimientoDeAutorizacion(null);
				desembolso.setMontoAutorizado(null);
				desembolso.setMontoDesembolsado(null);
				desembolso.setFechaPago(null);
			}
		}

		seguimientoBean.setEstado(EstadoSeguimiento.ANULADO);
				
		BitacoraBean bitacora = this.builder.anularSeguimiento(seguimientoBean,observacion);				
		bitacoraDao.create(bitacora);
		finDeSeguimiento(seguimientoBean);
		seguimientoDao.update(seguimientoBean);
	}
	
	/**
	 * Devuelve un <code>RendicionCuentasBean</code> con <i>id</i> igual al parámetro que recibe. 
	 */
	public RendicionCuentasBean obtenerRendicionCuentas(Long idRendicion) {
		return rendicionCuentasDao.read(idRendicion);
	}
	
	/**
	 *  Obtiene el resumen de rendiciones para un seguimiento
	 */
	public List<ResumenDeRendicionesDTO> obtenerResumenRendiciones(Long idSeguimiento) {
		
		return seguimientoDao.findResumenRendicionesCompleto(idSeguimiento);
	}
	
	/** Getters y Setters **/
	
	public SeguimientoDAO getSeguimientoDao() {
		return seguimientoDao;
	}

	public void setSeguimientoDao(SeguimientoDAO seguimientoDao) {
		this.seguimientoDao = seguimientoDao;
	}

	public ProyectoDAO getProyectoDao() {
		return proyectoDao;
	}

	public void setProyectoDao(ProyectoDAO proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	public BitacoraDAO getBitacoraDao() {
		return bitacoraDao;
	}

	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}


	public BitacoraBuilder getBuilder() {
		return builder;
	}


	public void setBuilder(BitacoraBuilder builder) {
		this.builder = builder;
	}

	public RendicionCuentasDAO getRendicionCuentasDao() {
		return rendicionCuentasDao;
	}

	public void setRendicionCuentasDao(RendicionCuentasDAO rendicionCuentasDao) {
		this.rendicionCuentasDao = rendicionCuentasDao;
	}

	/**
	 * Carga una evaluación de seguimiento a un
	 * seguimiento.
	 */
	public EvaluacionSeguimientoBean cargarEvaluacionASeguimiento(EvaluacionSeguimientoDTO evaluacionDTO, Long idSeguimiento) {
		EvaluacionSeguimientoAssembler assembler = EvaluacionSeguimientoAssembler.getInstance();
		EvaluacionSeguimientoBean evaluacion = assembler.buildBean(evaluacionDTO);

		SeguimientoBean seguimiento = (SeguimientoBean) seguimientoDao.read(idSeguimiento);
		seguimiento.enProcesoEvaluacion();

		evaluacion.setFecha(DateTimeUtil.getDate());
		evaluacion.setIdProyecto(seguimiento.getIdProyecto());
		evaluacion.setProyecto(seguimiento.getProyecto());
		evaluacion.setEstado(EstadoEvaluacion.PEND_RESULT);
		evaluacion.setIdSeguimiento(idSeguimiento);

		// Los datos de la bitacora deben estar en el servicio
		BitacoraBean bitacora = this.builder.cargarEvaluacionSeguimiento( seguimiento );

		// Actualizo los beans
		evaluacion.setBitacora( bitacora);
		evaluacionSeguimientoDAO.save(evaluacion);
		seguimientoDao.update(seguimiento);
		
		// Grabo los evaluadores
		EvaluacionEvaluadorBean evaluacionEvaluadorBean;
		Collection<EvaluacionEvaluadorDTO> evaluadorListDTO = evaluacionDTO.getEvaluadores();
		if (evaluadorListDTO != null) {
			for(EvaluacionEvaluadorDTO evaluadorDTO : evaluadorListDTO) {
				//Vinculo los evaluadores con la evaluacion
				evaluacionEvaluadorBean = new EvaluacionEvaluadorBean();
				evaluacionEvaluadorBean.setEvaluador(evaluadorDTO.getIdEvaluador());
				evaluacionEvaluadorBean.setInstitucion(evaluadorDTO.getIdEntidadEvaluadora());
				evaluacionEvaluadorBean.setLugarEvaluacion(evaluadorDTO.getLugar());
				evaluacionEvaluadorBean.setIdEvaluacion(evaluacion.getId());
				
				evaluacionEvaluadorDAO.save(evaluacionEvaluadorBean);
			}
		}
		//Esto se hace porque por alguna razón que se desconoce, los cambios que
		//se hacen acá no son visibles luego, dentro de la misma transacción.
		//Se necesita para asignar los permisos por evaluador.
		HibernateUtil.getSessionFactory().getCurrentSession().flush();
		return evaluacion;
	}

	/**
	 * Actualiza los montos de gestión (montos gestionados)
	 * con los montos de evaluación (montos aprobados).
	 * @param rendiciones
	 * @author ssanchez
	 */
	private void actualizarRendicionesDeGestion(Set rendiciones) {
		
		if (rendiciones.size()>0) {
			Iterator iter = rendiciones.iterator();

			while (iter.hasNext()) {
				RendicionCuentasBean rendicionCuentas = (RendicionCuentasBean) iter.next();
				
				rendicionCuentas.setMontoParteGestion(rendicionCuentas.getMontoParteEvaluacion());
				rendicionCuentas.setMontoContraparteGestion(rendicionCuentas.getMontoContraparteEvaluacion());
				
				rendicionCuentasDao.update(rendicionCuentas);
			}
		}
	}
	
	/**
	 * Obtiene las rendiciones de un seguimiento
	 * y actualiza las de gestión con las
	 * aprobadas (de evaluación).
	 * @param idSeguimiento
	 * @author ssanchez
	 */
	public void cargarRendicionesDeGestionConAprobadas(Long idSeguimiento) {

		SeguimientoBean seguimiento = (SeguimientoBean) seguimientoDao.read(idSeguimiento);
		
		actualizarRendicionesDeGestion(seguimiento.getRendiciones());
	}
	
	public EvaluacionEvaluadorDAO getEvaluacionEvaluadorDAO() {
		return evaluacionEvaluadorDAO;
	}

	public void setEvaluacionEvaluadorDAO(EvaluacionEvaluadorDAO evaluacionEvaluadorDAO) {
		this.evaluacionEvaluadorDAO = evaluacionEvaluadorDAO;
	}

	public EvaluacionGeneralDAO getEvaluacionGeneralDAO() {
		return evaluacionGeneralDAO;
	}

	public void setEvaluacionGeneralDAO(EvaluacionGeneralDAO evaluacionGeneralDAO) {
		this.evaluacionGeneralDAO = evaluacionGeneralDAO;
	}

	public void finalizarControlEvaluacion(EvaluacionSeguimientoDTO evaluacionDTO, ResultadoEvaluacion resultado) {
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDao.read(evaluacionDTO.getIdSeguimiento());

		if (resultado.getName().equals("RECHAZADO")) {
			seguimientoBean.setEstado(EstadoSeguimiento.RECHAZADO);
			finDeSeguimiento(seguimientoBean);
		} else {
			if (seguimientoBean.getEsFinanciero()) {
				seguimientoBean.setEstado(EstadoSeguimiento.CONTROLADO);
			} else { 
				seguimientoBean.setEstado(EstadoSeguimiento.FINALIZADO);
			}
		}
		EvaluacionBean evaluacion = new EvaluacionBean();
		evaluacion.setIdProyecto(seguimientoBean.getIdProyecto());
		evaluacion.setTipo(TipoEvaluacion.FINAL_CONTROL);
		Date fecha;
		try {
			fecha = DateTimeUtil.getDate(evaluacionDTO.getFecha());
		}
		catch (ParseException e) {
			fecha = DateTimeUtil.getDate();
		}
		evaluacion.setFechaInicial(fecha);
		evaluacion.setFecha(fecha);
		evaluacion.setResultado(resultado);
		evaluacion.setFundamentacion(evaluacionDTO.getFundamentacion());
		evaluacion.setEstado(EstadoEvaluacion.CONFIRMADA);

		BitacoraBean bitacora = this.builder.finalizarControlEvaluacion(seguimientoBean);
		evaluacion.setBitacora(bitacora);

		evaluacionDao.save(evaluacion);
		seguimientoDao.save(seguimientoBean);
		bitacoraDao.save(bitacora);
		
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}

	public EvaluacionDAO getEvaluacionDao() {
		return evaluacionDao;
	}

	public void setEvaluacionDao(EvaluacionDAO evaluacionDao) {
		this.evaluacionDao = evaluacionDao;
	}

	public void cargarEtapas(String id, String avance, String observaciones) {
		EtapaBean etapaBean = etapaDAO.read(new Long(id));
		etapaBean.setObservaciones(observaciones);
		etapaBean.setAvance(avance);
		etapaDAO.update(etapaBean);
	}

	public void cargarActividades(String id, String avance, String observaciones) {
		ActividadBean actividadBean = actividadDAO.read(new Long(id));
		actividadBean.setObservacion(observaciones);
		actividadBean.setAvance(avance);
		actividadDAO.update(actividadBean);
	}

	public RubroDAO getRubroDao() {
		return rubroDao;
	}

	public void setRubroDao(RubroDAO rubroDao) {
		this.rubroDao = rubroDao;
	}

	public List<ControlFacturasDTO> obtenerFacturasRepetidas(Long idSeguimiento) {
		return seguimientoDao.findFacturasRepetidas(idSeguimiento);
	}

	public List<String> getEvaluacionesAbiertas(Long idSeguimiento) {
		List<BitacoraBean> list = bitacoraDao.findBySeguimiento(idSeguimiento);
		List<String> pendientes = new ArrayList<String>();
		if (list != null){
			for (BitacoraBean bitacoraBean : list) {
				if (bitacoraBean.getTipo().equals(TipoBitacora.EVALUACION)) {
					EvaluacionBean evaluacionBean = evaluacionDao.read(bitacoraBean.getId());
					if (evaluacionBean.getAbierta()) {
						pendientes.add(evaluacionBean.getId().toString());
					}
				}
			}
		}
		return pendientes;
	}

	@SuppressWarnings("unchecked")
	public Collection<Evaluacion> getEvaluaciones(SeguimientoBean seguimiento, String tipo) {
		Collection evaluacionesList = new ArrayList();
		VisualizarEvaluacionAssembler visualizarEvaluacionAssembler = new VisualizarEvaluacionAssembler();
		List<BitacoraBean> list = bitacoraDao.findBySeguimiento(seguimiento.getId());
		Set<EvaluacionBean> evaluaciones = new HashSet<EvaluacionBean>();
		if (list != null){
			for (BitacoraBean bitacoraBean : list) {
				if (bitacoraBean.getIdEvaluacion() != null) {
					EvaluacionBean evaluacionBean = evaluacionDao.read(bitacoraBean.getIdEvaluacion());
					if (!evaluaciones.contains(evaluacionBean)) {
						evaluaciones.add(evaluacionBean);
					}
				}
			}
		}
		
		for (EvaluacionBean evaluacion : evaluaciones) {
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_GEN)) {
				VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator) this.getEvaluacionDTO(evaluacion.getId(), new VisualizarEvaluacionAssembler());
				if(dto.getEstado().equals(EstadoEvaluacion.CONFIRMADA)) {
					if(tipo.equals(Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL)) {
						EvaluacionesFinalizarControlDTO efcDTO = new EvaluacionesFinalizarControlDTO();
						efcDTO.setIdEvaluacion(evaluacion.getId());
						efcDTO.setTipo(obtenerTiposEvaluacionGeneral(dto.getEvaluacionGeneral()));
						efcDTO.setEvaluadores(visualizarEvaluacionAssembler.getShowEvaluadores(dto.getEvaluadores()));
						if(evaluacion.getResultado() != null) {
							efcDTO.setResultado(evaluacion.getResultado().getDescripcion());
						}
						efcDTO.setEstado(evaluacion.getEstado());
						efcDTO.setRecomendacion(evaluacion.getRecomendacion());
						evaluacionesList.add(efcDTO);
					}
//					if((tipo.equals(Constant.AdministrarEvaluacionAttribute.EVALUAR_RESULTADO)) || (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION))) {
//						EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();
//						erpDTO.setIdEvaluacion(evaluacion.getId());
//						erpDTO.setEvaluaciones(visualizarEvaluacionAssembler.getShowEvaluacion((EvaluacionGeneralDTO) dto.getDto()));
//						try {
//							if(evaluacion.getResultado() != null) {
//								erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
//							}
//						} catch (Exception e) {
//							erpDTO.setResultado("CRIPTO");
//						}
//						if (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
//							erpDTO.setEvaluaciones(erpDTO.getEvaluaciones().replace(")<br/>",")"));
//						}
//						erpDTO.setElegibleString("checked");
//						erpDTO.setEsElegible(evaluacion.esElegible());
//						evaluacionesList.add(erpDTO);
//					}
				}
			}
		}
//		if(tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
//			Collection<EvaluarResultadoProyectoDTO> lista = administrarPaqueteProyectoServicio.obtenerEvaluacionesPaquete(proyecto);
//			Iterator i = lista.iterator();
//			while(i.hasNext()) {
//				EvaluarResultadoProyectoDTO erpDTO = (EvaluarResultadoProyectoDTO)i.next();
//				evaluacionesList.add(erpDTO);
//			}
//		}
		return evaluacionesList;
	}	

	private String obtenerTiposEvaluacionGeneral(EvaluacionGeneralDTO evaluacion) {
		Collection<String> tipos = new ArrayList<String>();
		
		if (Boolean.parseBoolean(evaluacion.getEsTecnica())) tipos.add(Constant.TiposEvaluacion.TECNICA);
		if (Boolean.parseBoolean(evaluacion.getEsVisitaTecnica())) tipos.add(Constant.TiposEvaluacion.VISITA_TECNICA);
		if (Boolean.parseBoolean(evaluacion.getEsContable())) tipos.add(Constant.TiposEvaluacion.CONTABLE);
		if (Boolean.parseBoolean(evaluacion.getEsAuditoriaContable())) tipos.add(Constant.TiposEvaluacion.AUDITORIA_CONTABLE);
		if (Boolean.parseBoolean(evaluacion.getEsEconomica())) tipos.add(Constant.TiposEvaluacion.ECONOMICA);
		if (Boolean.parseBoolean(evaluacion.getEsFinanciera())) tipos.add(Constant.TiposEvaluacion.FINANCIERA);
		
		Iterator iterator = tipos.iterator();
	
		return StringUtil.join(iterator," - ");
	}
	
	public EvaluacionDTO getEvaluacionDTO(Long idEvaluacion, EvaluacionAssembler assembler) {
		EvaluacionBean evaluacion  = evaluacionDao.read( idEvaluacion );
		return assembler.buildDTO( evaluacion );
	}
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * es de tipo <code>Finaciero.</code><br>
	 */
	public Boolean esFinanciero(Long idSeguimiento) {
		SeguimientoBean seguimientoBean = seguimientoDao.read(idSeguimiento);
		return seguimientoBean.getEsFinanciero();
	}
	

	public void autorizarPago(Long idSeguimiento, boolean autorizada, EvaluacionSeguimientoDTO evaluacion) {
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDao.read(idSeguimiento);
		
		StringBuffer descripcionBitacora = new StringBuffer("Autorización de Pago: ");
		EvaluacionBean evaluacionBean = new EvaluacionBean();
		evaluacionBean.setIdProyecto(seguimientoBean.getIdProyecto());
//		tipo largo, solo maximo 20
		evaluacionBean.setTipo(TipoEvaluacion.AUTORIZACION_PAGO);
		evaluacionBean.setEstado(EstadoEvaluacion.CONFIRMADA);
		evaluacionBean.setFechaInicial(DateTimeUtil.getDate());
		try {
			evaluacionBean.setFecha(DateTimeUtil.getDate(evaluacion.getFecha()));
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		evaluacionBean.setFundamentacion(evaluacion.getFundamentacion());
		if (autorizada) {
			descripcionBitacora.append("Aprobada ");
			seguimientoBean.setEstado(EstadoSeguimiento.AUTORIZADO);
		}
		else {
			descripcionBitacora.append("Rechazada ");
			seguimientoBean.setEstado(EstadoSeguimiento.NO_AUTORIZADO);
		}
		descripcionBitacora.append(evaluacion.getFundamentacion());

		BitacoraBean bitacora = builder.autorizarPago(seguimientoBean, descripcionBitacora.toString());
		evaluacionBean.setBitacora(bitacora);		
		evaluacionDao.save(evaluacionBean);
		bitacoraDao.save(bitacora);
		seguimientoDao.update(seguimientoBean); //actualiza el estado del seguimiento
	}
	
	/**
	 * Cierra el <code>Seguimiento</code> identificado mediante
	 * <i>idSeguimiento</i> y guarda la observación ingresada
	 * en la <code>Bitacora</code>.<br>
	 * @author ssanchez
	 */
	public void cerrarSeguimiento(Long idSeguimiento,String observacion) {
		
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDao.read(idSeguimiento);
		
		seguimientoBean.setEstado(EstadoSeguimiento.CERRADO);
				
		BitacoraBean bitacora = this.builder.cerrarSeguimiento(seguimientoBean,observacion);				
		bitacoraDao.create(bitacora);
		finDeSeguimiento(seguimientoBean);
		seguimientoDao.update(seguimientoBean);
	}
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas y son de tipo técnica.
	 * @param idSeguimiento
	 * @return List<EvaluacionGeneralBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesTecnicasAbiertas(Long idSeguimiento) {

		List<EvaluacionSeguimientoBean> evalTecnicasAbiertas = new ArrayList<EvaluacionSeguimientoBean>();
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionSeguimientoBean evaluacion: evalAbiertas) {
				if (evaluacion.esTipoTecnica()) {
					evalTecnicasAbiertas.add(evaluacion);
				}
			}
		}

		return evalTecnicasAbiertas;
	}	
	
	/**
	 * Obtiene una lista de Evaluaciones para un 
	 * Seguimiento que estan abiertas y son de tipo contable.
	 * @param idSeguimiento
	 * @return List<EvaluacionGeneralBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesContablesAbiertas(Long idSeguimiento) {
		
		List<EvaluacionSeguimientoBean> evalContablesAbiertas = new ArrayList<EvaluacionSeguimientoBean>();
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionSeguimientoBean evaluacion: evalAbiertas) {
				if (evaluacion.esTipoContable()) {
					evalContablesAbiertas.add(evaluacion);
				}
			}
		}

		return evalContablesAbiertas;
	}
	
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>técnica</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionTecnicaAbierta(Long idSeguimiento) {

		Boolean tieneEvalAbierta = false;
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesTecnicasAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionGeneralBean evaluacion: evalAbiertas) {
				if (evaluacion.getEsTecnica()) {
					tieneEvalAbierta = true;
					return tieneEvalAbierta;
				}
			}
		}

		return tieneEvalAbierta;
	}	
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>visita técnica</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionVisitaTecnicaAbierta(Long idSeguimiento) {

		Boolean tieneEvalAbierta = false;
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesTecnicasAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionSeguimientoBean evaluacion: evalAbiertas) {
				if (evaluacion.getEsVisitaTecnica()) {
					tieneEvalAbierta = true;
					return tieneEvalAbierta;
				}
			}
		}

		return tieneEvalAbierta;
	}	

	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>contable</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionContableAbierta(Long idSeguimiento) {

		Boolean tieneEvalAbierta = false;
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesContablesAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionSeguimientoBean evaluacion: evalAbiertas) {
				if (evaluacion.getEsContable()) {
					tieneEvalAbierta = true;
					return tieneEvalAbierta;
				}
			}
		}

		return tieneEvalAbierta;
	}	
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>auditoría contable</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionAuditoriaContableAbierta(Long idSeguimiento) {

		Boolean tieneEvalAbierta = false;
		
		List<EvaluacionSeguimientoBean> evalAbiertas = evaluacionesContablesAbiertas(idSeguimiento);
		if (evalAbiertas != null) {
			for(EvaluacionSeguimientoBean evaluacion: evalAbiertas) {
				if (evaluacion.getEsAuditoriaContable()) {
					tieneEvalAbierta = true;
					return tieneEvalAbierta;
				}
			}
		}

		return tieneEvalAbierta;
	}	
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas.
	 * @param idSeguimiento
	 * @return List<EvaluacionGeneralBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesAbiertas(Long idSeguimiento) {

		List<EvaluacionSeguimientoBean> evalAbiertas = new ArrayList<EvaluacionSeguimientoBean>();

		for(EvaluacionSeguimientoBean evaluacion : evaluacionSeguimientoDAO.findBySeguimiento(idSeguimiento)) {
			if (evaluacion.getAbierta()) {
				evalAbiertas.add(evaluacion);
			}
		}
		return evalAbiertas;		
	}
	
	/**
	 * Obtiene todas las Evaluaciones de un
	 * Seguimiento que coninciden con el <i>estado</i>.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @return List<EvaluacionGeneralBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesPorEstado(Long idSeguimiento, EstadoEvaluacion estado) {

		List<EvaluacionSeguimientoBean> evaluaciones = new ArrayList<EvaluacionSeguimientoBean>();

		List<EvaluacionSeguimientoBean> todasLasEvaluaciones = evaluacionSeguimientoDAO.findBySeguimiento(idSeguimiento);
		if (todasLasEvaluaciones != null){
			for (EvaluacionSeguimientoBean evaluacion : todasLasEvaluaciones) {
				if (evaluacion.getEstado().equals(estado)) {
					evaluaciones.add(evaluacion);
				}
			}
		}
		
		return evaluaciones;		
	}
	
	/**
	 * Verifica si el Seguimiento tiene al menos
	 * una Evaluación Técnica y una Contable en
	 * estado confirmada.
	 * @param idSeguimento
	 * @return Boolean
	 * @author ssanchez
	 */
	public Boolean tieneEvaluacionesTecnicaYContableConfirmadas(Long idSeguimento) {
		Boolean tieneTecnica = false;
		Boolean tieneContable = false;
		
		List<EvaluacionSeguimientoBean> evaluaciones = evaluacionesPorEstado(idSeguimento,EstadoEvaluacion.CONFIRMADA);
		
		for (int i = 0; i < evaluaciones.size() && (!tieneTecnica || !tieneContable); i++) {
			
			if(!tieneTecnica && evaluaciones.get(i).esTipoTecnica()) 
				tieneTecnica = true;
			
			if(!tieneContable && evaluaciones.get(i).esTipoContable())
				tieneContable = true;
		}
		
		return tieneTecnica && tieneContable;
	}
	
	/**
	 * Obtiene datos resumidos de las rendiciones
	 * de un seguimiento.<br>
	 * @param idSeguimiento
	 * @return total de montos aprobado
	 */
	public SeguimientoGestionPagoDTO obtenerTotalesRendicionesParaEvaluarGestionDePago(Long idSeguimiento) {
		
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);

		SeguimientoGestionPagoDTO seguimientoDTO = new SeguimientoGestionPagoDTO(obtenerTotalesRendicionesParaSeguimiento(seguimiento));

		//Se habilita para gestionar si no tiene desembolsos autorizados que esten
		//pendientes de pago
		seguimientoDTO.setHabilitadoParaGestionar(!seguimiento.tieneDesembolsosAutorizadosPendientesDePago());
		//Siempre puede revaluarse
		seguimientoDTO.setHabilitadoParaRevaluar(true);
		//Siempre puede no gestionarse
		seguimientoDTO.setHabilitadoParaNoGestionar(true);		
		
		return seguimientoDTO;
	}
	
	/**
	 * Modifica el estado del seguimiento según 
	 * el parametro <i>estado</i> y
	 * persiste la observación.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @param observacion
	 * @author ssanchez
	 */
	public void cargarGestionPago(Long idSeguimiento, EstadoSeguimiento estado, String observacion) {
		
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		
		/*
		 * En caso de NO GESTIONAR:
		 * Pasar los items de cronograma pagados y autorizados a pendientes
		 * En caso de REVALUAR
		 * Pasa los items de cronograma de este seguimiento pagados a autorizados 
		 */
		if(estado.equals(EstadoSeguimiento.NO_GESTIONADO)) {
			Set<ProyectoDesembolsoBean> desembolsosVinculados = seguimiento.getDesembolsosVinculados();
			for (ProyectoDesembolsoBean desembolso : desembolsosVinculados) {
				desembolso.setCodigoEstado(EstadoProyectoDesembolso.NO_PAGADO);
				desembolso.setFechaPago(null);
				desembolso.setIdSeguimientoDeAutorizacion(null);
				desembolso.setMontoAutorizado(null);
				desembolso.setMontoDesembolsado(null);
			}
			finDeSeguimiento(seguimiento);
		} else {
			if(estado.equals(EstadoSeguimiento.EVALUACION)) {
				Set<ProyectoDesembolsoBean> desembolsosVinculados = seguimiento.getDesembolsosVinculados();
				for (ProyectoDesembolsoBean desembolso : desembolsosVinculados) {
					if(desembolso.yaFuePagado()) {
						//Lo paso a autorizado
						desembolso.setCodigoEstado(EstadoProyectoDesembolso.AUTORIZADO);
						desembolso.setFechaPago(null);
						desembolso.setMontoDesembolsado(null);
					}
				}
			}	
		}
		
		seguimiento.setEstado(estado);

		BitacoraBean bitacora = this.builder.cargarGestionPago(seguimiento,observacion);				
		
		bitacoraDao.create(bitacora);
		seguimientoDao.update(seguimiento);
	}
	
	public boolean getTieneRendiciones(Long idseguimiento) {
		List<RendicionCuentasBean> rendiciones = rendicionCuentasDao.findRendicionesBeanPorSeguimiento(idseguimiento);
		return !rendiciones.isEmpty();
	}

	public void cargarRendicionDesdeMap(TipoRendicion tipoRendicion, Map<String, Object> map) {
		String[] campos = CamposPorTipoRendicion.get(tipoRendicion);
		
		RendicionCuentasBean rendicion;
		boolean esNueva;
		Long id = (Long)map.get("id");
		if(id==null) {
			//creo una nueva rendicion.
			rendicion = new RendicionCuentasBean();
			esNueva = true;
		} else {
			rendicion = rendicionCuentasDao.read(id);
			esNueva = false;
		}
		
		if(CollectionUtils.contains(campos, "funcion"))
			rendicion.setDescripcion((String) map.get("funcion"));
		else
			rendicion.setDescripcion((String) map.get("descripcion"));
		
		if(tipoRendicion.equals(TipoRendicion.DIRECTOR_EXPERTO))
			rendicion.setDescripcion(ResourceManager.getCodesResource("app.codes.rendicion.tipo.directorExperto"));
		else {
			if(tipoRendicion.equals(TipoRendicion.CANON_INSTITUCIONAL))
				rendicion.setDescripcion(ResourceManager.getCodesResource("app.codes.rendicion.tipo.canonInstitucional"));
		}

		if(tipoRendicion.equals(TipoRendicion.RECURSO_HUMANO_PROPIO))
			rendicion.setMontoParteRendicion(BigDecimal.ZERO);
		else rendicion.setMontoParteRendicion((BigDecimal)map.get("montoParte"));
		
		//Campos comunes obligatorios
		rendicion.setIdRubro((Long) map.get("idRubro"));
		rendicion.setIdSeguimiento((Long) map.get("idSeguimiento"));				
		rendicion.setFecha((Date) map.get("fecha"));
		rendicion.setVersion(new Date());
		
		if(CollectionUtils.contains(campos, "nroFactura")) rendicion.setNumeroFactura((String)map.get("nroFactura"));
		if(CollectionUtils.contains(campos, "nroRecibo")) rendicion.setNumeroRecibo((String)map.get("nroRecibo"));
		if(CollectionUtils.contains(campos, "proveedor")) rendicion.setNombreProveedor((String)map.get("proveedor"));
		if(CollectionUtils.contains(campos, "montoContraparte")) rendicion.setMontoContraparteRendicion((BigDecimal)map.get("montoContraparte"));
		if(CollectionUtils.contains(campos, "tieneCertificado")) rendicion.setTieneCertificadoProveedor((Boolean)map.get("tieneCertificado"));
		if(CollectionUtils.contains(campos, "paisProveedor")) rendicion.setPaisProveedor((String)map.get("paisProveedor"));
		if(CollectionUtils.contains(campos, "idPersona")) rendicion.setIdPersona((Long)map.get("idPersona"));
		if(CollectionUtils.contains(campos, "profesion")) rendicion.setProfesionPersona((String)map.get("profesion"));
		if(CollectionUtils.contains(campos, "sueldo")) rendicion.setMontoSueldoMensual((BigDecimal)map.get("sueldo"));
		if(CollectionUtils.contains(campos, "costoTotalMensual")) rendicion.setMontoSueldoMensual((BigDecimal)map.get("costoTotalMensual"));
		if(CollectionUtils.contains(campos, "dedicacion")) rendicion.setPorcentajeDedicacion((BigDecimal)map.get("dedicacion"));
		if(CollectionUtils.contains(campos, "participacion")) rendicion.setMesesParticipacion((Long)map.get("participacion"));

		rendicion.setMontoTotal(rendicion.getMontoContraparteRendicion().add(rendicion.getMontoParteRendicion()));
		
		
		//Define el valor predeterminado de los Montos de Evaluacion como el Monto de Rendición solcitado
		if(esNueva || rendicion.getSeguimiento().estaRecienIniciado()) {
			rendicion.setMontoContraparteEvaluacion(rendicion.getMontoContraparteRendicion());
			rendicion.setMontoParteEvaluacion(rendicion.getMontoParteRendicion());
			rendicion.setMontoTotalEvaluacion(rendicion.getMontoTotal());
		}

		cargarRendicion(rendicion);
	}
	
	public Collection<RendicionCuentasDTO> parseArchivo(ArchivoDTO dto, Long seguimientoId, boolean borrarExistentes) throws ParsingException, RendicionesExcelParsingException {
		
		/* Ver si no estan faltando precondiciones */

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
			throw new RendicionesExcelParsingException("app.file.fileNotFound");
		}
		catch (Exception e) {
			throw new RendicionesExcelParsingException("app.unknownError");
		}
		
		SeguimientoBean seguimiento = seguimientoDao.read(seguimientoId);
		List<RendicionCuentasBean> newRendiciones;
		newRendiciones = RendicionesParserBuilder.buildFor(seguimiento).parse(workbook, seguimiento);
		
		//Elimino todas las rendiciones actuales
		if(borrarExistentes) {
			for (RendicionCuentasBean rendicion : seguimiento.getRendiciones()) {
				rendicionCuentasDao.delete(rendicion);
			}
		}
		/*
		 * Agrego las nuevas
		 * Esto implica
		 * - Crear las personas que se hayan especificado en las rendiciones y que no existan
		 * - Actualizar las personas existentes
		 * - Guardar las rendiciones 
		 */
		Set<PersonaBean> personas = new HashSet<PersonaBean>();
		for (RendicionCuentasBean rendicion : newRendiciones) {
			if(rendicion.getPersona()!=null)personas.add(rendicion.getPersona());
		}
		for(PersonaBean persona : personas) {
			if(persona.getLocalizacion()==null) {
				if(persona.getLocalizacion().getId()==null) {
					localizacionDao.create(persona.getLocalizacion());
				} else {
					localizacionDao.update(persona.getLocalizacion());
				}
			} else {
				persona.setLocalizacion(new LocalizacionBean());
				localizacionDao.create(persona.getLocalizacion());
			}
			persona.setIdLocalizacion(persona.getLocalizacion().getId());

			if(persona.getId()==null) personaDao.create(persona);
			else personaDao.update(persona);
		}
		for (RendicionCuentasBean rendicion : newRendiciones) {
			//completo los beans
			if(rendicion.getPersona()!=null)rendicion.setIdPersona(rendicion.getPersona().getId());
			rendicionCuentasDao.create(rendicion);
		}

		return RendicionCuentasAssembler.getInstance().buildDto(newRendiciones);
	}

	private static Map<TipoRendicion, String[]>	CamposPorTipoRendicion = CollectionUtils.mapWith(
			TipoRendicion.GENERAL, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"proveedor",
					"montoParte",
					"montoContraparte",
					"tieneCertificado",
					"paisProveedor",
					"descripcion",
			},
			
			TipoRendicion.RECURSO_HUMANO_ADICIONAL, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"montoParte",
					"montoContraparte",
					"idPersona",
					"profesion",
					"funcion",
					"costoTotalMensual",
					"dedicacion",
					"participacion"
			},
			
			TipoRendicion.RECURSO_HUMANO_PROPIO, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"montoParte",
					"montoContraparte",
					"idPersona",
					"profesion",
					"funcion",
					"sueldo",
					"dedicacion",
					"participacion"
			},
			
			TipoRendicion.CANON_INSTITUCIONAL, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"montoParte",
					"montoContraparte",
					"costoTotalMensual"
			},
			
			TipoRendicion.DIRECTOR_EXPERTO, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"montoParte",
					"montoContraparte",
					"idPersona",
					"costoTotalMensual",
					"dedicacion",
					"participacion"
			},
			
			TipoRendicion.CONSEJERO_TECNOLOGICO, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"montoParte",
					"montoContraparte",
					"idPersona",
					"funcion",
					"profesion",
					"costoTotalMensual",
					"dedicacion",
					"participacion"
			},
			
			TipoRendicion.CONSULTOR, 
			new String[] {
					"nroFactura",
					"nroRecibo",
					"proveedor",
					"montoParte",
					"montoContraparte",
					"tieneCertificado",
					"paisProveedor",
					"descripcion",
					"costoTotalMensual",
					"participacion"
			}
	);

	public void setLocalizacionDao(LocalizacionDAO localizacionDao) {
		this.localizacionDao = localizacionDao;
	}
	
	/**
	 * Modifica el estado del <code>Seguimento</code> a
	 * <i>Gestionado</i> y guarda en bitacora la transacción.<br>
	 * Este método es usado en la carga de proyectos históricos
	 * para finalizar el seguimiento.<br>
	 * @param idSeguimiento
	 */
	public void finalizarGestionarSeguimiento(Long idSeguimiento) {		
		
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);

		seguimiento.setEstado(EstadoSeguimiento.GESTIONADO);

		BitacoraBean bitacora = this.builder.cargarGestionPago(seguimiento,Constant.BitacoraDescripcion.SEGUIMIENTO_HISTORICO);				
		
		bitacoraDao.create(bitacora);
		finDeSeguimiento(seguimiento);
		seguimientoDao.update(seguimiento);		
	}
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code> 
	 * tiene <code>EvaluacionesSeguimiento</code> contables confirmadas.<br>
	 * @param idSeguimiento
	 * @return true o false
	 */
	public Boolean tieneEvaluacionesContablesConfirmadas(Long idSeguimiento) {
		
		Boolean tieneEvaluaciones = false;
		
		List<EvaluacionGeneralBean> evaluaciones = obtenerEvaluaciones(idSeguimiento);
		
		for (EvaluacionGeneralBean evaluacion : evaluaciones) {
			
			if(evaluacion.esTipoContable() && EstadoEvaluacion.CONFIRMADA.equals(evaluacion.getEstado())) {
				tieneEvaluaciones = true;
				return tieneEvaluaciones; 
			}
			
		}

		return tieneEvaluaciones;
	}
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento.
	 * @param idSeguimiento
	 * @return List<EvaluacionGeneralBean>
	 * @author ssanchez
	 */
	public List<EvaluacionGeneralBean> obtenerEvaluaciones(Long idSeguimiento) {

		List<EvaluacionGeneralBean> evaluaciones = new ArrayList<EvaluacionGeneralBean>();

		List<BitacoraBean> bitacoras = bitacoraDao.findBySeguimiento(idSeguimiento);
		if (bitacoras != null){
			for (BitacoraBean bitacora : bitacoras) {
				if (bitacora.getTipo().equals(TipoBitacora.EVALUACION)) {
					EvaluacionBean evaluacion = (EvaluacionBean) evaluacionDao.read(bitacora.getId());
					
					if(TipoEvaluacion.EVAL_GEN.equals(evaluacion.getTipo())) {
						evaluaciones.add((EvaluacionGeneralBean)evaluacion);
					}
				}
			}
		}
		
		return evaluaciones;		
	}

	public Long getIdProyectoDeSeguimiento(Long idSeguimiento) {
		return seguimientoDao.read(idSeguimiento).getIdProyecto();
	}

	public Boolean permiteEdicionDeMontosSolicitados(Long idSeguimiento) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		return 
				seguimiento.getEstado().equals(EstadoSeguimiento.INICIADO)
			|| (	seguimiento.getEstado().equals(EstadoSeguimiento.EVALUACION) 
				 &&	!tieneEvaluacionesConfirmadas(idSeguimiento)	);
	}

	public Boolean tieneEvaluacionesConfirmadas(Long idSeguimiento) {
		
		List<EvaluacionSeguimientoBean> evaluaciones = evaluacionesPorEstado(idSeguimiento,EstadoEvaluacion.CONFIRMADA);
		return !evaluaciones.isEmpty();

	}

	public Boolean permiteAgregarOQuitarRendiciones(Long idSeguimiento) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		return 
				seguimiento.getEstado().equals(EstadoSeguimiento.INICIADO)
			|| (	seguimiento.getEstado().equals(EstadoSeguimiento.EVALUACION) 
				 &&	!tieneEvaluacionesConfirmadas(idSeguimiento)	);
	}

	public void setEvaluacionSeguimientoDAO(EvaluacionSeguimientoDAO evaluacionSeguimientoDAO) {
		this.evaluacionSeguimientoDAO = evaluacionSeguimientoDAO;
	}
	/**
	 * Realiza las tareas asociadas al fin de un seguimiento ante cualquier
	 * forma de terminacion. Incluye persistir el presupuesto según informe de
	 * avance y el pendiente de rendición. Debe llamarse como última accion
	 * sobre el seguimiento.
	 * @param seguimiento
	 */
	private void finDeSeguimiento(SeguimientoBean seguimiento) {
		CalculosDeAnalisisDeGastosDTO calculos = analisisGastosSeguimientoService.getCalculosDeAnalisisDeGastosParaFinalizarSeguimiento(seguimiento);
		if(seguimiento.getMontoPresupuestoSegunAvance()==null || seguimiento.getMontoPendienteDeRendicion()==null) {
			if(seguimiento.getMontoPresupuestoSegunAvance()==null) {
				seguimiento.setMontoPresupuestoSegunAvance(calculos.getPresupuestoSegunInformeDeAvance());
			}
			if(seguimiento.getMontoPendienteDeRendicion()==null) {
				seguimiento.setMontoPendienteDeRendicion(calculos.getPendienteDeRendicion());
			}
		}
	}

	public void guardarPresupuestoSegunAvance(Long idSeguimiento, BigDecimal monto) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		seguimiento.setMontoPresupuestoSegunAvance(monto);
		seguimientoDao.update(seguimiento);
	}

	public void guardarPendienteDeRendicion(Long idSeguimiento, BigDecimal monto) {
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		seguimiento.setMontoPendienteDeRendicion(monto);
		seguimientoDao.update(seguimiento);	}

	public Boolean esDeInstrumentoConAlicuotaCF(Long idSeguimiento) {
		SeguimientoBean seguimientoBean = seguimientoDao.read(idSeguimiento);
		return seguimientoBean.getProyecto().getInstrumento().aplicaCargaAlicuotaCF();
	}

	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(Long idEvaluacion) {
		
		SeguimientoBean seguimiento = evaluacionSeguimientoDAO.read(idEvaluacion).getSeguimiento();
		
		ResumenDeRendicionCompactoDTO dto = obtenerTotalesRendicionesParaSeguimiento(seguimiento);
		
		//Le quito los montos de gestion porque no corresponden a esta altura
		dto.setTotalMontoParteGestionado(null);
		dto.setTotalMontoContraparteGestionado(null);
		dto.setTotalMontoTotalGestionado(null);
		
		return dto;
	}

	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(Long idSeguimiento) {
		
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		
		
		ResumenDeRendicionCompactoDTO dto = obtenerTotalesRendicionesParaSeguimiento(seguimiento);
		
		//Le quito los montos de gestion porque no corresponden a esta altura
		dto.setTotalMontoParteGestionado(null);
		dto.setTotalMontoContraparteGestionado(null);
		dto.setTotalMontoTotalGestionado(null);
		
		Collection<Evaluacion> evaluacionesList = this.getEvaluaciones(seguimiento, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);

		if(evaluacionesList!=null){
			
			boolean MostrarMontosAprobados = false; 
			for(Evaluacion evaluacion : evaluacionesList){
				if(evaluacion.getResultado() != ResultadoEvaluacion.RECHAZADO.getDescripcion())	
					MostrarMontosAprobados = true;
			}
			//No mostrar los aprobados si todas las evaluaciones estan rechazadas.
			if (!MostrarMontosAprobados){
				dto.setTotalMontoTotalAprobado(null);
				dto.setTotalMontoParteAprobado(null);
				dto.setTotalMontoContraparteAprobado(null);
			}
		}

		return dto;
	}

	private ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaSeguimiento(SeguimientoBean seguimiento) {

		ResumenDeRendicionCompactoDTO dto = seguimientoDao.selectResumenRendicionCompacto(seguimiento.getId());

		dto.setIdSeguimiento(seguimiento.getId());
		
		if(seguimiento.getProyecto().getInstrumento().aplicaCargaAlicuotaCF()) {
			dto.setTotalMontoParteSolicitado(null);
			dto.setTotalMontoContraparteSolicitado(null);
			dto.setTotalMontoParteAprobado(null);
			dto.setTotalMontoContraparteAprobado(null);
			dto.setTotalMontoParteGestionado(null);
			dto.setTotalMontoContraparteGestionado(null);
		}
		return dto;
	}

	public void setConfigService(ConfiguracionServicio service) {
		this.configService = service;
	}
	
	public void saveEdicionMonto(String entidad, Long id, String propiedad, BigDecimal valorNuevo){
		if(!ActionUtil.isEncryptionContextAvailable()) throw new RuntimeException("app.error.encrypt");
		
		RendicionCuentasBean rendicionCuentasBean = rendicionCuentasDao.read(id);
		
		if(propiedad.equals("montoParteRendicion")){
				rendicionCuentasBean.setMontoParteRendicion(valorNuevo);
		}else{
			if(propiedad.equals("montoContraparteRendicion")){
				rendicionCuentasBean.setMontoContraparteRendicion(valorNuevo);
			}
			else{
				throw (new NotImplementedException());
			}
		}
		
		rendicionCuentasBean.ActualizarMontoTotal();
		
		SeguimientoBean seguimiento = rendicionCuentasBean.getSeguimiento();
		
	/* NUEVO */ 
		
		 
		 if(seguimiento.estaTerminado()) {
			//Actualizo los datos calculados del seguimiento
			seguimiento.setMontoPresupuestoSegunAvance(null);
			seguimiento.setMontoPendienteDeRendicion(null);
			this.finDeSeguimiento(seguimiento);
		 }else{
			//BigDecimal montoTotal = rendicionCuentasBean.getMontoParteRendicion().add(rendicionCuentasBean.getMontoContraparteRendicion()); 
			this.configService.saveEdicionMonto(entidad, id, propiedad, valorNuevo); 
			String query = StringUtil.inject("Update $1 Set $2 = $3 Where id = $4", entidad, "montoTotal", rendicionCuentasBean.getMontoTotal().toString(), id.toString());
			
			Session sesion = HibernateUtil.getSessionFactory().openSession();
			sesion.createQuery(query).executeUpdate();
			sesion.close();
		 } 
		 
	/*	 FIN NUEVO  */
		
		
		
	/* ORIGINAL  
	 	 rendicionCuentasDao.update(rendicionCuentasBean);
	 	
		 if(seguimiento.estaTerminado()) {
			//Actualizo los datos calculados del seguimiento
			seguimiento.setMontoPresupuestoSegunAvance(null);
			seguimiento.setMontoPendienteDeRendicion(null);
			this.finDeSeguimiento(seguimiento);
		} 
   /* FIN ORIGINAL	*/
	
	}
	
}