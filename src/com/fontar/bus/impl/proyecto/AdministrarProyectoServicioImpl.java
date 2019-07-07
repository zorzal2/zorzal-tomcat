package com.fontar.bus.impl.proyecto;

import static com.pragma.util.StringUtil.isEmpty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.impl.NoBeanIdException;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.data.Constant;
import com.fontar.data.Constant.BitacoraTema;
import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.EmpleoPermanenteDAO;
import com.fontar.data.api.dao.EntidadIntervinientesDAO;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.ExpedienteDAO;
import com.fontar.data.api.dao.ExpedienteMovimientoDAO;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoDatosDAO;
import com.fontar.data.api.dao.ProyectoJurisdiccionDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.assembler.EmpleoPermanenteAssembler;
import com.fontar.data.impl.assembler.ExpedienteMovimientoAssembler;
import com.fontar.data.impl.assembler.LocalizacionAssembler;
import com.fontar.data.impl.assembler.PresentacionCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoAgregarAssembler;
import com.fontar.data.impl.assembler.ProyectoAssembler;
import com.fontar.data.impl.assembler.ProyectoEdicionAssembler;
import com.fontar.data.impl.assembler.ProyectoPresupuestoDTOAssembler;
import com.fontar.data.impl.assembler.ProyectoVisualizacionAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ExpedienteBean;
import com.fontar.data.impl.domain.bean.ExpedienteMovimientoBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.entidad.TipoEntidad;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompletarDatosInicialesDTO;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.EntidadIntervinientesDTO;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.AuthorizationService;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.cripto.AccesoDenegadoException;
import com.fontar.util.Util;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;

/**
 * Servicio para administrar las acciones de un proyecto
 * @author jdaccorso, gboaglio
 * 
 */
public class AdministrarProyectoServicioImpl implements AdministrarProyectoServicio {

	private ProyectoDAO proyectoDAO;

	private PresentacionConvocatoriaDAO presentacionConvocatoriaDAO;

	private ProyectoDatosDAO proyectoDatosDAO;

	private BitacoraDAO bitacoraDAO;

	private EvaluacionDAO evaluacionDAO;

	private ProyectoPresupuestoDAO presupuestoDAO;

	private LocalizacionDAO localizacionDAO;

	private EmpleoPermanenteDAO empleoPermanenteDAO;

	private EntidadIntervinientesDAO entidadIntervinientesDAO;
	
	private ExpedienteMovimientoDAO expedienteMovimientoDAO;
	
	private ExpedienteDAO expedienteDAO;
	
	private TipoProyectoDAO tipoProyectoDAO;
	
	private BitacoraBuilder builder = new BitacoraBuilder();
	
	private AdministrarSeguimientoServicio administrarSeguimientoServicio;
	
	private AuthorizationService authorizationService;
	
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	/**
	 * Carga un Proyecto, ProyectoPitec o IdeaProyectoPitec.
	 * 
	 * @presupuestable : debe ser del tipo ProyectoBean, ProyectoPitecBean o
	 * IdeaProyectoPitecBean
	 */
	public Long cargarPresupuestable(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento,
			Long idPresentacion, Long idProyectoPitec, ProyectoBean presupuestable) {

		presupuestable.setCodigo(datos.getCodigo());
		ProyectoJurisdiccionBean jurisdiccionBean;
		// cargo el estado
		if (vieneDePresentacion) {
			presupuestable.setEstado(EstadoProyecto.INICIADO);
			// cargo el id de presentacion si viene de presetnacion convocatoria
			presupuestable.setIdPresentacion(idPresentacion);


			// TODO: este DAO debería inyectarse en la clase, no ir acá.
			PresentacionConvocatoriaDAO presentacionConvocatoriaDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
			PresentacionConvocatoriaBean convocatoriaBean = (PresentacionConvocatoriaBean) presentacionConvocatoriaDAO.read(idPresentacion);
			convocatoriaBean.setEstadoFinalizada();
			presentacionConvocatoriaDAO.update(convocatoriaBean);
			
			// ProyectoJurisdiccion, lo toma de la jurisdiccion de la convocatoria
			jurisdiccionBean = new ProyectoJurisdiccionBean();
			jurisdiccionBean.setIdJurisdiccion(convocatoriaBean.getIdJurisdiccion());
			jurisdiccionBean.setCodigo(presupuestable.getCodigo());
		}

		// Viene de los wizards de Idea Proyecto / Idea Proyecto Pitec
		else {

			presupuestable.setEstado(EstadoProyecto.ADMITIDO);
			
			InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");
			InstrumentoBean instrumento = instrumentoDAO.read(datos.getIdInstrumento());
			if(instrumento.getParaProyectoHistorico()) {
				presupuestable.setEstado(EstadoProyecto.INICIADO);
			}
			
			// Referencia a la Idea Proyecto / Idea Proyecto Pitec que originó este proyecto
			Long idProyectoOrigen = datos.getIdProyecto();
			
			//Puede ser un proyecto CAE, en este caso no hay origen
			if(idProyectoOrigen!=null){
				presupuestable.setIdProyectoOrigen(idProyectoOrigen);
	
				ProyectoRaizDAO proyectoRaizDAO = (ProyectoRaizDAO) WebContextUtil.getBeanFactory().getBean("proyectoRaizDao");
				ProyectoRaizBean ideaProyectoBean = (ProyectoRaizBean) proyectoRaizDAO.read(idProyectoOrigen);
	
				if (ideaProyectoBean instanceof IdeaProyectoBean) {
					((IdeaProyectoBean) ideaProyectoBean).setEstadoFinalizada();
				}
				else {
					((IdeaProyectoPitecBean) ideaProyectoBean).setEstado(EstadoProyecto.FINALIZADO);
				}
	
				proyectoRaizDAO.update(ideaProyectoBean);

				// ProyectoJurisdiccion, lo toma de la jurisdiccion de la idea proyecto
				jurisdiccionBean = new ProyectoJurisdiccionBean();
				jurisdiccionBean.setIdJurisdiccion(ideaProyectoBean.getProyectoJurisdiccion().getIdJurisdiccion());
				jurisdiccionBean.setCodigo(presupuestable.getCodigo());

			
			} else {
				//CAE
				//TODO Setear jurisdiccion!
				jurisdiccionBean = new ProyectoJurisdiccionBean();
				jurisdiccionBean.setIdJurisdiccion(datos.getIdJurisdiccion());
				jurisdiccionBean.setCodigo(presupuestable.getCodigo());
			}

		}

		// BitacoraJurisdiccion
		BitacoraBean bitacoraJurisdiccion = jurisdiccionBean.getBitacora();
		bitacoraJurisdiccion.setTipo(TipoBitacora.PROY_JURISDICCION);
		bitacoraJurisdiccion.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setTema("Jurisdicción");
		bitacoraJurisdiccion.setDescripcion("Asociación de Jurisdicción");
		bitacoraJurisdiccion.setIdUsuario(this.getUserId());

		presupuestable.setEstadoEnPaquete(false);

		// creo instancias de objetos relacionados a un proyecto para agregar
		LocalizacionDTO localizacionDTO = datos.getLocalizacion();
		EmpleoPermanenteDTO empleoPermanenteDTO = datos.getEmpleo();
		presupuestable.setIdEmpleoPermanente(empleoPermanenteDTO.getId());
		presupuestable.setEstadoReconsideracion(false);

		presupuestable.setIdInstrumento(idInstrumento);
		presupuestable.setIdProyectoPitec(idProyectoPitec);
		presupuestable.setProyectoPitec(datos.getProyectoPitec());
		Long emerix = null;
		if (!Util.isBlank(datos.getEmerix())) {
			emerix = new Long(datos.getEmerix());
		}
		presupuestable.setEmerix(emerix);

		proyectoDAO.save(presupuestable);

		// Bitacora Proy
		BitacoraBean bitacoraProy = new BitacoraBean();
		bitacoraProy.setIdProyecto(presupuestable.getId());

		// TODO ver como afecta esto a la edición
		// cargo datos de bitacora correspondiente al bean Proyecto
		bitacoraProy.setTipo(TipoBitacora.BASICO);
		bitacoraProy.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraProy.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraProy.setTema("Alta Proyecto"); // FIXME: ver. Puede ser P, PP, o IPP
		bitacoraProy.setDescripcion("NA");
		bitacoraProy.setIdUsuario(this.getUserId());
		bitacoraDAO.save(bitacoraProy);

		// -- ProyectoDatos

		ProyectoDatosBean proyectoDatosBean = new ProyectoDatosBean();

		proyectoDatosBean.setIdEntidadBeneficiaria(datos.getIdEntidadBeneficiaria());
		proyectoDatosBean.setTitulo(datos.getTitulo());
		proyectoDatosBean.setObjetivo(datos.getObjetivo());
		proyectoDatosBean.setIdPersonaLegal(datos.getIdPersonaLegal());
		proyectoDatosBean.setIdPersonaDirector(datos.getIdPersonaDirector());
		proyectoDatosBean.setIdPersonaRepresentante(datos.getIdPersonaRepresentante());
		proyectoDatosBean.setIdCiiu(datos.getIdCiiu());
		proyectoDatosBean.setResumen(datos.getResumen());
		proyectoDatosBean.setPalabraClave(datos.getPalabraClave());

		String strDuracion = datos.getDuracion();

		if (!Util.isBlank(strDuracion)) {
			proyectoDatosBean.setDuracion(Integer.parseInt(strDuracion));
		}

		proyectoDatosBean.setIdLocalizacion(localizacionDTO.getId());
		proyectoDatosBean.setIdTipoProyecto(datos.getIdTipoProyecto());
		proyectoDatosBean.setIdEntidadBancaria(datos.getIdEntidadBancaria());
		proyectoDatosBean.setDescripcionEntidadBancaria(datos.getDescripcionEntidadBancaria());
		proyectoDatosBean.setPorcentajeTasaInteres(datos.getPorcentajeTasaInteres());
		proyectoDatosBean.setObservacion(datos.getObservacion());
		proyectoDatosBean.setFechaIngreso(DateTimeUtil.getDate());

		LocalizacionBean localizacionBean = LocalizacionAssembler.getInstance().buildBean(localizacionDTO);
		EmpleoPermanenteBean empleoPermanenteBean = EmpleoPermanenteAssembler.getInstance().buildBean(empleoPermanenteDTO);

		if (localizacionBean != null) { // Si es una idea proyecto
			localizacionDAO.save(localizacionBean);
			proyectoDatosBean.setIdLocalizacion(localizacionBean.getId());
		}

		if (empleoPermanenteBean != null) {
			empleoPermanenteDAO.save(empleoPermanenteBean);
			presupuestable.setIdEmpleoPermanente(empleoPermanenteBean.getId());
		}

		// Bitacora Proyecto Datos
		BitacoraBean bitacoraDatos = proyectoDatosBean.getBitacora();
		bitacoraDatos.setTipo(TipoBitacora.PROY_DATOS);
		bitacoraDatos.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraDatos.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraDatos.setTema("Proyecto Datos del Proyecto");
		bitacoraDatos.setDescripcion("NA");
		bitacoraDatos.setIdProyecto(presupuestable.getId());
		bitacoraDatos.setIdUsuario(this.getUserId());
		proyectoDatosDAO.save(proyectoDatosBean);

		// TODO: este dao debería inyectarse en esta clase, no ir acá.
		ProyectoJurisdiccionDAO proyectoJurisdiccionDAO = (ProyectoJurisdiccionDAO) ContextUtil.getBean("proyectoJurisdiccionDao");
		
		// FIXME: FF / parche debido a que no injecta bien el DAO y aun no sabemos porque
		entidadIntervinientesDAO = (EntidadIntervinientesDAO)ContextUtil.getBean("entidadIntervinientesDao");
				
		bitacoraJurisdiccion.setIdProyecto(presupuestable.getId());
		proyectoJurisdiccionDAO.save(jurisdiccionBean);					
		
		// Cargo más datos en el bean presupuestable y lo actualizo en la bd
		presupuestable.setIdDatos(proyectoDatosBean.getId());
		presupuestable.setIdProyectoJurisdiccion(jurisdiccionBean.getId());

		proyectoDAO.update(presupuestable);

		EntidadIntervinientesBean bean;
		BitacoraBean bitacora;
		Collection<EntidadIntervinientesDTO> listDTO = datos.getIntervinientes();
		if (listDTO != null) {
			Iterator iter = listDTO.iterator();

			while (iter.hasNext()) {
				EntidadIntervinientesDTO dto = (EntidadIntervinientesDTO) iter.next();
				bean = new EntidadIntervinientesBean();
				bitacora = this.builder.cargarEntidadIntervinientes(presupuestable);
				bitacoraDAO.save(bitacora);
				bean.setTipoEntidad(TipoEntidad.valueOf(dto.getTipoEntidad()));
				bean.setIdEntidad(new Long(dto.getIdEntidad()));
				bean.setRelacion(dto.getRelacion());
				bean.setFuncion(dto.getFuncion());
				bean.setId(bitacora.getId());
				bean.setBitacora(bitacora);
				bean.setActivo(true);
				entidadIntervinientesDAO.save(bean);
			}
		}

		return presupuestable.getId();
	}

	/**
	 * Persiste los datos ingresados al cargar un proyecto
	 * 
	 */
	public Long cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento,
			Long idPresentacion, Long idProyectoPitec) {
		ProyectoBean proyectoBean = new ProyectoBean();
		try {
			BeanUtils.copyProperties(proyectoBean, datos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cargarPresupuestable(datos, vieneDePresentacion, idInstrumento, idPresentacion, idProyectoPitec, new ProyectoBean());
	}

	/**
	 * Obtiene un dto desde un bean El dto es construido en base al assembler
	 * enviado por parametro
	 */
	public DTO getProyectoDTO(Long idProyecto, ProyectoGeneralAssembler assembler) {
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);
		return assembler.buildDTO(proyectoBean);
	}

	public void cargarAlicuota(Long idProyecto, BigDecimal porcentaje, String observaciones) {
		
		ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);

		proyecto.setEstado(EstadoProyecto.CONT_DIR_ADJ);
		proyecto.setPorcentajeAlicuotaSolicitada(porcentaje);
		//Cargo el valor default para la alicuota adjudicada
		if(proyecto.getPorcentajeAlicuotaAdjudicada()==null)
			proyecto.setPorcentajeAlicuotaAdjudicada(porcentaje);

		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Carga alícuota solicitada por el beneficiario");
		bitacora.setDescripcion(observaciones);
		bitacora.setIdUsuario(this.getUserId());
		proyectoDAO.update(proyecto);
		bitacoraDAO.save(bitacora);
	}

	public BigDecimal obtenerAlicuotaSolicitada(Long idProyecto) {
		ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);
		return proyecto.getPorcentajeAlicuotaSolicitada();
	}
	
	/**
	 * Obtiene el presupuesto de un proyecto. 
	 * Si el idEvaluacion<>null se obtiene el presupuesto de la evaluacion
	 */
	public ProyectoPresupuestoDTO obtenerPresupuesto(Long idProyecto, Long idEvaluacion) {
		ProyectoPresupuestoDTO presupuestoDTO = new ProyectoPresupuestoDTO();

		if (idEvaluacion == null) {
			ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);
			if (proyecto.getProyectoPresupuesto() != null) {
				presupuestoDTO = ProyectoPresupuestoDTOAssembler.buildDto(proyecto.getProyectoPresupuesto());
			}
		}
		else {
			EvaluacionBean evaluacion = (EvaluacionBean) evaluacionDAO.read(idEvaluacion);
			if (evaluacion.getProyectoPresupuesto() != null) {
				presupuestoDTO = ProyectoPresupuestoDTOAssembler.buildDto(evaluacion.getProyectoPresupuesto());
			}
			presupuestoDTO = ProyectoPresupuestoDTOAssembler.buildDto(evaluacion.getProyectoPresupuesto());
		}
		return presupuestoDTO;
	}

	/**
	 * Guarda el presupuesto del <code>Proyecto</code>.
	 */
	public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idProyecto,
			Long idEvaluacion) {
		ProyectoPresupuestoDTO presupuestoDTO = new ProyectoPresupuestoDTO();

		if (idEvaluacion == null) {
			ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);

			ProyectoPresupuestoBean presupuestoOriginal = proyecto.getProyectoPresupuestoOriginal();
			if (presupuestoOriginal == null) {

				// cargo los datos del formulario al presupuesto y la bitacora
				ProyectoPresupuestoBean presupuestoBean = ProyectoPresupuestoDTOAssembler.updateBean(presupuesto);
				BitacoraBean bitacoraPresupuesto = presupuestoBean.getBitacora();
				bitacoraPresupuesto.setIdProyecto(idProyecto);
				bitacoraPresupuesto.setTipo(TipoBitacora.PRESUPUESTO);
				bitacoraPresupuesto.setFechaAsunto(DateTimeUtil.getDate());
				bitacoraPresupuesto.setFechaRegistro(DateTimeUtil.getDate());
				bitacoraPresupuesto.setIdUsuario(this.getUserId());
				bitacoraPresupuesto.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);
				
				// Propago datos del presupuesto anterior
				if(proyecto.getUltimoPresupuesto()!=null) proyecto.getUltimoPresupuesto().propagarDatosA(presupuestoBean);
				
				presupuestoDAO.save(presupuestoBean);

				// Cargo los datos del formulario al presupuestoOriginal y la bitacora
				ProyectoPresupuestoBean presupuestoOriginalBean = ProyectoPresupuestoDTOAssembler.updateBean(presupuesto);
				BitacoraBean bitacoraPresupuestoOriginal = presupuestoOriginalBean.getBitacora();
				bitacoraPresupuestoOriginal.setIdProyecto(idProyecto);
				bitacoraPresupuestoOriginal.setTipo(TipoBitacora.PRESUPUESTO);
				bitacoraPresupuestoOriginal.setFechaAsunto(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setFechaRegistro(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setIdUsuario(this.getUserId());
				bitacoraPresupuestoOriginal.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);
				presupuestoDAO.save(presupuestoOriginalBean);

				// Actualizo referencia de proyecto a presupuesto
				proyecto.setIdPresupuesto(presupuestoBean.getId());
				proyecto.setIdPresupuestoOriginal(presupuestoOriginalBean.getId());
				proyectoDAO.update(proyecto);

			}
			else {

				// Cargo los datos del formulario al presupuesto y la bitacora
				ProyectoPresupuestoBean presupuestoBean = new ProyectoPresupuestoBean();
				presupuestoBean.setMontoTotal(presupuesto.getMontoTotal());
				presupuestoBean.setMontoSolicitado(presupuesto.getMontoSolicitado());

				BitacoraBean bitacoraPresupuestoOriginal = presupuestoBean.getBitacora();
				bitacoraPresupuestoOriginal.setIdProyecto(idProyecto);
				bitacoraPresupuestoOriginal.setTipo(TipoBitacora.PRESUPUESTO);
				bitacoraPresupuestoOriginal.setFechaAsunto(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setFechaRegistro(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setIdUsuario(this.getUserId());
				bitacoraPresupuestoOriginal.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);

				//Propago datos del presupuesto anterior
				if(proyecto.getUltimoPresupuesto()!=null) proyecto.getUltimoPresupuesto().propagarDatosA(presupuestoBean);
				
				presupuestoDAO.save(presupuestoBean);

				// actualizo referencia de proyecto a presupuesto
				proyecto.setIdPresupuesto(presupuestoBean.getId());
				proyectoDAO.update(proyecto);

			}

		}
		else {

			EvaluacionBean evaluacion = (EvaluacionBean) evaluacionDAO.read(idEvaluacion);
			// ProyectoRaizBean proyecto =
			// proyectoDAO.read(evaluacion.getIdProyecto());

			ProyectoPresupuestoBean presupuestoEvaluacion = evaluacion.getProyectoPresupuesto();
			if (presupuestoEvaluacion != null) {
				// cargo datos de presupuestoEvaluacion para modificar

				presupuestoEvaluacion.setMontoTotal(presupuesto.getMontoTotal());
				presupuestoEvaluacion.setMontoSolicitado(presupuesto.getMontoSolicitado());
				presupuestoDAO.update(presupuestoEvaluacion);

			}
			else {

				// ProyectoPresupuestoBean presupuestoProyecto =
				// proyecto.getProyectoPresupuesto();
				ProyectoPresupuestoBean presupuestoEvaluacionNuevo = new ProyectoPresupuestoBean();

				// cargo datos del nuevo presupuestoEvaluacion
				presupuestoEvaluacionNuevo.setMontoTotal(presupuesto.getMontoTotal());

				// BigDecimal montoSolicitadoDecrementado =
				// presupuestoProyecto.getMontoSolicitado().multiply(Constant.PorcentajesPresupuesto.PORCENTAJE_DECREMENTO);
				presupuestoEvaluacionNuevo.setMontoSolicitado(presupuesto.getMontoSolicitado());

				BitacoraBean bitacoraPresupuestoOriginal = presupuestoEvaluacionNuevo.getBitacora();
				bitacoraPresupuestoOriginal.setIdProyecto(evaluacion.getProyecto().getId());
				bitacoraPresupuestoOriginal.setTipo(TipoBitacora.PRESUPUESTO);
				bitacoraPresupuestoOriginal.setFechaAsunto(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setFechaRegistro(DateTimeUtil.getDate());
				bitacoraPresupuestoOriginal.setIdUsuario(this.getUserId());
				bitacoraPresupuestoOriginal.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);
				presupuestoDAO.save(presupuestoEvaluacionNuevo);

				// actualizo referencia de proyecto a presupuesto
				evaluacion.setIdPresupuesto(presupuestoEvaluacionNuevo.getId());
				evaluacionDAO.update(evaluacion);
			}
		}
		return presupuestoDTO;
	}

	/**
	 * Finaliza la posibilidad de reconsideracion de un proyecto, es decir,
	 * AdministrarProyectoServicioImpl -> finalizarPosibilidadReconsideracion
	 */
	public void finalizarPosibilidadReconsideracion(BitacoraDTO bitacoraDTO, Long idProyecto) {
		// cambio estado dependiendo de un par de cosas
		ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);
		Boolean aplicaCargaAlicuotaCF = proyecto.getInstrumento().aplicaCargaAlicuotaCF();
		
		//InstrumentoDefBean instrumentoDef = (InstrumentoDefBean) proyecto.getInstrumento().getInstrumentoDef();
		
		if (Recomendacion.RECHAZADO.equals(proyecto.getRecomendacion())) {
			proyecto.cerrarProyecto();
			proyecto.setMotivoCierre(MotivoCierre.FINALIZO_POSIBILIDAD_RECONSIDERACION.getName());
		}
		else if (!Recomendacion.RECHAZADO.equals(proyecto.getRecomendacion())
				&& aplicaCargaAlicuotaCF) {
			proyecto.setEstado(EstadoProyecto.PEND_ALIC);
		}
		else if (!Recomendacion.RECHAZADO.equals(proyecto.getRecomendacion())
				&& !aplicaCargaAlicuotaCF) {
			proyecto.setEstado(EstadoProyecto.CONT_DIR_ADJ);
		}
	
		// genero bitacora comun a todas las operaciones
		BitacoraBean bitacora = new BitacoraBean();

		// cargo datos de bitacora
		bitacora.setIdProyecto(idProyecto);
		bitacora.setDescripcion(bitacoraDTO.getDescripcion());
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setIdUsuario(this.getUserId());
		bitacora.setTema(Constant.BitacoraTema.FINALIZAR_POSIBILIDAD_RECONSIDERACION);

		// persisto los datos
		proyectoDAO.update(proyecto);
		bitacoraDAO.save(bitacora);
	}

	/**
	 * Obtiene los datos para la pantalla de edición de un proyecto
	 * @param idProyecto
	 */
	public void obtenerDatosEdicionProyecto(Long idProyecto, ProyectoVisualizacionDTO datosEdicionDto) {
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);

		datosEdicionDto.updateWith(ProyectoEdicionAssembler.getInstance().buildDto(proyectoBean.getProyectoDatos()));
		PresentacionConvocatoriaBean convocatoriaBean = proyectoBean.getPresentacionConvocatoria();

		if (convocatoriaBean != null) {
			datosEdicionDto.setDatosProyectoDto(ProyectoAgregarAssembler.getInstance().buildDto(convocatoriaBean));
			datosEdicionDto.setIdPresentacion(convocatoriaBean.getId());
		}

		datosEdicionDto.setIdProyecto(idProyecto);
		datosEdicionDto.setCodigo(proyectoBean.getCodigo());
		datosEdicionDto.setProyectoPitec(proyectoBean.getProyectoPitec());

		EmpleoPermanenteBean empleoBean = proyectoBean.getEmpleoPermanente();
		if (empleoBean != null) {
			EmpleoPermanenteDTO empleoDto = EmpleoPermanenteAssembler.getInstance().buildDto(empleoBean);

			datosEdicionDto.setEmpleo(empleoDto);
		}
	}

	/**
	 * Persiste los datos ingresados por el usuario en la edición de un proyecto
	 * 
	 */
	public void guardarDatosEdicionProyecto(ProyectoEdicionDTO datosDto) {
		Long id = datosDto.getIdProyecto();
		
		if (id != null) {
			// Creo proyecto datos, le cargo la info del dto.

			ProyectoBean proyectoBean = this.getProyectoDAO().read(id);
			ProyectoDatosBean datosBean = new ProyectoDatosBean();
			ProyectoEdicionAssembler.getInstance().updateBeanNotNull(datosBean, datosDto);

			proyectoBean.setCodigo(datosDto.getCodigo());
			if (!isEmpty(datosDto.getEmerix()))
				proyectoBean.setEmerix(new Long(datosDto.getEmerix()));
			else proyectoBean.setEmerix(null);
			
			proyectoBean.setProyectoPitec(datosDto.getProyectoPitec());
			
			/*
			 * Recomendacion
			 * Solo se tiene en cuenta si:
			 * - El usuario tiene el permiso PROYECTOS-CAMBIAR-RECOMENDACION
			 * - El bean tenia una recomendacion no vacia (ya que, sin evaluacion, solo puede ser cambiada)
			 * - El dto viene con una recomendacion no vacia (ya que no se puede quitar la recomendacion)
			 * - Hay contexto de encriptacion
			 */ 
			AuthorizationService authorizationService = (AuthorizationService) ContextUtil.getBean("authorizationService");
			if(authorizationService
					.grantedPermissionByInstrumento("PROYECTOS-CAMBIAR-RECOMENDACION", 
							proyectoBean.getIdInstrumento())) {
				try {
					if(proyectoBean.getRecomendacion()!=null) {
						Recomendacion recomendacion = datosDto.getRecomendacion();
						if(recomendacion!=null) {
							proyectoBean.setRecomendacion(recomendacion);
							if (proyectoBean.getRecomendacionFinal()!=null) {
								//si ya tienen recomendación Final, también la actualiza.
								proyectoBean.setRecomendacionFinal(recomendacion);
							}
						}
					}
				} catch(SecurityException e) {
					//No hay contexto de encriptacion. Ignoro el dato.
					/*
					 * FIXME Podria pasar que el usuario ponga la clave de encriptacion
					 * al momento de entrar al formulario de edicion y sacarla antes de
					 * hacer clic en Aceptar. En este caso el usuario puede cambiar la
					 * recomendacion y el cambio no seria tomado. De todos modos es una
					 * situacion muy poco probable.
					 */ 
				}
			}

			// Esto se pone porque en particular estos dos datos no son
			// requeridos y pueden venir nulos del form,
			// y en ese caso updateBeanNotNull no los actualiza

			datosBean.setFechaIngreso(proyectoBean.getProyectoDatos().getFechaIngreso());
			datosBean.setIdEntidadBancaria(datosDto.getIdEntidadBancaria());
			datosBean.setPorcentajeTasaInteres(datosDto.getPorcentajeTasaInteres());
			datosBean.setTir(datosDto.getTir());
			datosBean.setVan(datosDto.getVan());

			// Ahora q tengo los nuevos datos completos, creo la nueva instancia
			// de localización,
			// la persisto y actualizo la referencia en datosBean.

			LocalizacionBean localizacionBean = new LocalizacionBean();
			LocalizacionAssembler.getInstance().updateBeanNotNull(localizacionBean, datosDto.getLocalizacion());
			localizacionDAO.save(localizacionBean);

			datosBean.setIdLocalizacion(localizacionBean.getId());

			// Creo bitácora de modificación de datos y se la asigno a
			// datosBean.

			BitacoraBean bitacoraDatos = new BitacoraBean();
			bitacoraDatos.setIdProyecto(datosDto.getIdProyecto());

			bitacoraDatos.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraDatos.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraDatos.setTema(Constant.BitacoraTema.PROY_DATOS_PROYECTO);
			bitacoraDatos.setDescripcion("NA");
			bitacoraDatos.setTipo(TipoBitacora.PROY_DATOS);
			bitacoraDatos.setIdUsuario(this.getUserId());
			datosBean.setBitacora(bitacoraDatos);

			// Persisto la bitácora y los datos
			bitacoraDAO.save(bitacoraDatos);
			proyectoDatosDAO.save(datosBean);

			// Levanto de la bd el empleo permanente de proyectoBean y lo
			// actualizo

			EmpleoPermanenteBean empleoBean = empleoPermanenteDAO.read(proyectoBean.getIdEmpleoPermanente());
			EmpleoPermanenteAssembler.getInstance().updateBeanNotNull(empleoBean, datosDto.getEmpleo());

			// Actualizo el proyecto para que apunte a los nuevos datos
			proyectoBean.setIdDatos(datosBean.getId());
			

			EntidadIntervinientesBean bean;
			BitacoraBean bitacora;
			Collection<EntidadIntervinientesDTO> listDTO = datosDto.getIntervinientes();
			
			List<BitacoraBean> list;
			List<EntidadIntervinientesBean> intervinientesList = new ArrayList<EntidadIntervinientesBean>();
			list = bitacoraDAO.findByProyectoTipo(id, TipoBitacora.ENTIDAD_INTERVINIENTE.getName());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					EntidadIntervinientesBean intervinientesBean = (EntidadIntervinientesBean) entidadIntervinientesDAO.read(list.get(i).getId());
					intervinientesList.add(intervinientesBean);
				}
			}
			// (fferrara) 16-Mayo-2007 el DAO de bitacora se encuentra inicializado!
			//bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
			entidadIntervinientesDAO = (EntidadIntervinientesDAO)ContextUtil.getBean("entidadIntervinientesDao");
			if (intervinientesList != null) {
				for (int i = 0; i < intervinientesList.size(); i++) {
					EntidadIntervinientesBean intervinientesBean = intervinientesList.get(i);
					Boolean exist = false;
					if (listDTO != null) {
						Iterator iter = listDTO.iterator();

						while (iter.hasNext()) {
							EntidadIntervinientesDTO dto = (EntidadIntervinientesDTO) iter.next();
							if (compararEntidadIntervinientes(intervinientesBean,dto)) {
								intervinientesBean.setActivo(true);
								entidadIntervinientesDAO.update(intervinientesBean);
								exist = true;
							}
						}
					}
					if (!exist) {
						//preguntar q pasa con la bitacora
						intervinientesBean.setActivo(false);
						entidadIntervinientesDAO.update(intervinientesBean);
					}
				}
			}

			if (listDTO != null) {
				Iterator iter = listDTO.iterator();

				while (iter.hasNext()) {

					Boolean exist = false;
					EntidadIntervinientesDTO dto = (EntidadIntervinientesDTO) iter.next();
					if (intervinientesList != null) {
						for (int i = 0; i < intervinientesList.size(); i++) {
							bean = intervinientesList.get(i);
							if (compararEntidadIntervinientes(bean,dto)) {
								bean.setActivo(true);
								entidadIntervinientesDAO.update(bean);
								exist = true;
							}
						}
					}
					if (!exist) {
						bean = new EntidadIntervinientesBean();
						bitacora = this.builder.cargarEntidadIntervinientes(proyectoBean);
						bitacoraDAO.save(bitacora);
						bean.setTipoEntidad(TipoEntidad.valueOf(dto.getTipoEntidad()));
						bean.setIdEntidad(new Long(dto.getIdEntidad()));
						bean.setRelacion(dto.getRelacion());
						bean.setFuncion(dto.getFuncion());
						bean.setId(bitacora.getId());
						bean.setBitacora(bitacora);
						bean.setActivo(true);
						entidadIntervinientesDAO.save(bean);
					}
				}
			}


		}
		else {
			throw new NoBeanIdException();
		}
	}

	public ProyectoDTO obtenerProyecto(Long idProyecto) {
		ProyectoBean proyectoBean = (ProyectoBean) proyectoDAO.read(idProyecto);

		ProyectoDTO proyectoDto = ProyectoAssembler.buildDto(proyectoBean);

		return proyectoDto;
	}

	//TODO: no es idProyecto sino idDatos. Buscar donde lo esta usando este metode
//	public ProyectoDatosBean obtenerProyectoDatos(Long idProyecto) {
//		ProyectoDatosBean datosBean = proyectoDatosDAO.read(idProyecto);
//		return datosBean;
//	}

	/**
	 * Obtiene los datos para la pantalla de agregar un proyecto
	 * @param idProyecto
	 */
	public ProyectoAgregarDTO obtenerDatosAgregarProyecto(Long idPresentacion) {
		PresentacionConvocatoriaBean presentacionBean = presentacionConvocatoriaDAO.read(idPresentacion);
		ProyectoAgregarDTO dto = new ProyectoAgregarDTO();
		dto = ProyectoAgregarAssembler.getInstance().buildDto(presentacionBean);

		return dto;
	}

	/**
	 * Obtiene los datos para la pantalla de Visualización de un proyecto
	 * @param idProyecto
	 */
	public ProyectoVisualizacionDTO obtenerDatosVisualizacionProyecto(Long idProyecto) {
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);
		
		ProyectoVisualizacionAssembler assembler = new ProyectoVisualizacionAssembler();
		ProyectoVisualizacionDTO proyectoVisualizacionDto = assembler.buildDTO(proyectoBean);

		obtenerDatosEdicionProyecto(idProyecto, proyectoVisualizacionDto);
		
		if (proyectoBean.getIdPresupuesto() != null) {
			ProyectoPresupuestoBean presupuestoBean = presupuestoDAO.read(proyectoBean.getIdPresupuesto());
			proyectoVisualizacionDto.setMontoSolicitado(presupuestoBean.getMontoPresupuestoSolicitado());
		}
		if (proyectoBean.getIdPresupuestoOriginal() != null) {			
			ProyectoPresupuestoBean presupuestoBeanOriginal = presupuestoDAO.read(proyectoBean.getIdPresupuestoOriginal());
			proyectoVisualizacionDto.setMontoSolicitadoOriginal(presupuestoBeanOriginal.getMontoPresupuestoSolicitado());
		}
		
		return proyectoVisualizacionDto;
	}

	public EvaluacionDAO getEvaluacionDAO() {
		return evaluacionDAO;
	}

	public ProyectoPresupuestoDAO getPresupuestoDAO() {
		return presupuestoDAO;
	}

	public void setPresupuestoDAO(ProyectoPresupuestoDAO presupuestoDAO) {
		this.presupuestoDAO = presupuestoDAO;
	}

	public void setEvaluacionDAO(EvaluacionDAO evaluacionDAO) {
		this.evaluacionDAO = evaluacionDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.AdministrarProyectoServicio#getBitacoraDAO()
	 */
	public BitacoraDAO getBitacoraDAO() {
		return bitacoraDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.AdministrarProyectoServicio#setBitacoraDAO(com.fontar.data.api.dao.BitacoraDAO)
	 */
	public void setBitacoraDAO(BitacoraDAO bitacoraDAO) {
		this.bitacoraDAO = bitacoraDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.AdministrarProyectoServicio#getProyectoDAO()
	 */
	public ProyectoDAO getProyectoDAO() {
		return proyectoDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.AdministrarProyectoServicio#setProyectoDAO(com.fontar.data.api.dao.ProyectoDAO)
	 */
	public void setProyectoDAO(ProyectoDAO proyectoDAO) {
		this.proyectoDAO = proyectoDAO;
	}

	public ProyectoDatosDAO getProyectoDatosDAO() {
		return proyectoDatosDAO;
	}

	public void setProyectoDatosDAO(ProyectoDatosDAO proyectoDatosDAO) {
		this.proyectoDatosDAO = proyectoDatosDAO;
	}

	public EmpleoPermanenteDAO getEmpleoPermanenteDAO() {
		return empleoPermanenteDAO;
	}

	public void setEmpleoPermanenteDAO(EmpleoPermanenteDAO empleoPermanenteDAO) {
		this.empleoPermanenteDAO = empleoPermanenteDAO;
	}

	public LocalizacionDAO getLocalizacionDAO() {
		return localizacionDAO;
	}

	public void setLocalizacionDAO(LocalizacionDAO localizacionDAO) {
		this.localizacionDAO = localizacionDAO;
	}

	public Boolean enPaquete(Long idProyecto) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean obtenerEstadoProyecto(Long idProyecto) {
		// TODO Auto-generated method stub
		return null;
	}

	public PresentacionConvocatoriaDAO getPresentacionConvocatoriaDAO() {
		return presentacionConvocatoriaDAO;
	}

	public void setPresentacionConvocatoriaDAO(PresentacionConvocatoriaDAO presentacionConvocatoriaDAO) {
		this.presentacionConvocatoriaDAO = presentacionConvocatoriaDAO;
	}

	public PresentacionCabeceraDTO obtenerDatosCabeceraProyecto(Long idPresentacion) {
		PresentacionConvocatoriaBean presentacionConvocatoriaBean = presentacionConvocatoriaDAO.read(idPresentacion);
		PresentacionCabeceraAssembler assembler = new PresentacionCabeceraAssembler();
		PresentacionCabeceraDTO cabeceraDTO = assembler.buildDTO(presentacionConvocatoriaBean);

		return cabeceraDTO;
	}

	public EntidadIntervinientesDAO getEntidadIntervinientesDAO() {
		return entidadIntervinientesDAO;
	}

	public void setEntidadIntervinientesDAO(EntidadIntervinientesDAO entidadIntervinientesDAO) {
		this.entidadIntervinientesDAO = entidadIntervinientesDAO;
	}

	public Boolean compararEntidadIntervinientes(EntidadIntervinientesBean bean, EntidadIntervinientesDTO dto) {
		Boolean igual = true;
		if(StringUtil.isEmpty(bean.getFuncion())) {
			igual = igual && StringUtil.isEmpty(dto.getFuncion());
		} else {
			igual = igual && bean.getFuncion().equals(dto.getFuncion());
		} 
		if (!(bean.getIdEntidad().toString().equals(dto.getIdEntidad()))) {
			igual = false;
		}
		if(StringUtil.isEmpty(bean.getRelacion())) {
			igual = igual && StringUtil.isEmpty(dto.getRelacion());
		} else {
			igual = igual && bean.getRelacion().equals(dto.getRelacion());
		}
		if (!(bean.getTipoEntidad().getName().equals(dto.getTipoEntidad()))) {
			igual = false;
		}
		return igual;
	}

	public BigDecimal obtenerMontoSolicitadoProyecto(Long idProyecto) {
		
		ProyectoBean proyecto = proyectoDAO.read(idProyecto);
		ProyectoPresupuestoBean presupuestoBean = proyecto.getProyectoPresupuesto();
		
		if(presupuestoBean==null) return null;
		
		BigDecimal montoSolicitado = null;
		if(proyecto.getInstrumento().aplicaCargaAlicuotaCF()) {
			BigDecimal alicuotaAdjudicada = proyecto.getPorcentajeAlicuotaAdjudicada();
			if(alicuotaAdjudicada==null) return null;
			montoSolicitado = presupuestoBean.getMontoTotal().multiply(alicuotaAdjudicada).divide(new BigDecimal(100)); 
		} else {
			montoSolicitado = proyecto.getProyectoPresupuesto().getMontoSolicitado();
		}
				
		return montoSolicitado;
	}
	
	/**
	 * Firmar el Contrato de un Proyecto.
	 */
	public void guardarFirmaContrato(Long idResponsableLegal, String txtResponsableLegal, Date fechaFirma, String observaciones, Long idProyecto) {
				
		// Crea bitácora de firma de contrato		
		BitacoraBean bitacora = builder.firmarContratoProyecto(idProyecto, fechaFirma, observaciones, txtResponsableLegal);
		bitacoraDAO.save(bitacora);
		
		// Actualiza el estado del proyecto		
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);		
		proyectoBean.setEstado(EstadoProyecto.CONTRATO);								
		proyectoBean.getProyectoDatos().setIdPersonaLegal(idResponsableLegal);
		proyectoBean.setFechaFirmaDeContrato(fechaFirma);
		
	}

	public void guardarMovimiento(Date fecha, String ubicacion, Date fechaDevolucion, String observaciones, Long idPersona, Long idProyecto) {

	
		bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		expedienteMovimientoDAO = (ExpedienteMovimientoDAO)ContextUtil.getBean("expedienteMovimientoDao");
		List<BitacoraBean> bitacoraList = bitacoraDAO.findByProyectoTipo(idProyecto,TipoBitacora.MOV_EXPEDIENTE.getName());
		Boolean exist = false;
		if (!bitacoraList.isEmpty()) {
			for (int i = 0; i < bitacoraList.size(); i++) {
				ExpedienteMovimientoBean bean = expedienteMovimientoDAO.read(bitacoraList.get(i).getId());
				if (!bean.getEstado()) {
					if (fechaDevolucion != null) {
						bean.setFechaDevolucion(fechaDevolucion);
						bean.setEstado(true);
					}
					bean.setBitacora(bitacoraList.get(i));
					bean.setFecha(fecha);
					bean.setIdPersona(idPersona);
					bean.setObservacion(observaciones);
					bean.setUbicacion(ubicacion);
					expedienteMovimientoDAO.update(bean);
					exist = true;
				}
			}
		}
		if (!exist) {
			ExpedienteMovimientoBean bean = new ExpedienteMovimientoBean();
			bean.setFecha(fecha);
			bean.setIdPersona(idPersona);
			bean.setObservacion(observaciones);
			bean.setUbicacion(ubicacion);
			bean.setEstado(false);
			BitacoraBuilder builder = new BitacoraBuilder();
			BitacoraBean bitacoraBean = builder.cargarMovExp(idProyecto);
			bean.setBitacora( bitacoraBean);
			
			expedienteMovimientoDAO.save(bean);
		}
	}

	public ExpedienteMovimientoDAO getExpedienteMovimientoDAO() {
		return expedienteMovimientoDAO;
	}

	public void setExpedienteMovimientoDAO(ExpedienteMovimientoDAO expedienteMovimientoDAO) {
		this.expedienteMovimientoDAO = expedienteMovimientoDAO;
	}

	public void guardarExpediente(String[] cuerpo, String[] folioDesde, String[] folioHasta, Long idProyecto) {
		expedienteDAO = (ExpedienteDAO)ContextUtil.getBean("expedienteDao");
		bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");

		List<BitacoraBean> list = bitacoraDAO.findByProyectoTipo(idProyecto,TipoBitacora.EXPEDIENTE.getName());
		if (!(list.isEmpty())) {
			for (int i = 0; i < list.size(); i++) {
				ExpedienteBean bean = (ExpedienteBean) expedienteDAO.read(list.get(i).getId());
				Boolean exist = false;
				if (cuerpo != null) {
					for (int j = 0; j < cuerpo.length && !exist; j++) {
						if (bean.getCuerpo().toString().equals(cuerpo[j])) {
							exist = true;
						}
					}
				}
				if (!exist) {
					expedienteDAO.delete(bean);
					bitacoraDAO.delete(list.get(i));
					list.remove(list.get(i));
				}
			}
		}
		//para guardar con bitacora
		if (cuerpo != null) {
			for (int i = 0; i < cuerpo.length; i++) {

				ExpedienteBean bean = new ExpedienteBean();
				bean.setCuerpo(new Long(cuerpo[i]));
				bean.setFolioHasta(new Long(folioHasta[i]));
				bean.setFolioDesde(new Long(folioDesde[i]));
				
				BitacoraBean bitacoraBean = bean.getBitacora();
				bitacoraBean.setIdProyecto(idProyecto);
				bitacoraBean.setFechaAsunto(GregorianCalendar.getInstance().getTime());
				bitacoraBean.setFechaRegistro(GregorianCalendar.getInstance().getTime());
				bitacoraBean.setTema("Cuerpo de Expedientes");
				bitacoraBean.setTipo(TipoBitacora.EXPEDIENTE);

				Boolean exist = false;
				for (int j=0; j < list.size() && !exist; j++) {
					ExpedienteBean expedienteBean = (ExpedienteBean) expedienteDAO.read(list.get(j).getId());
					if (cuerpo[i].equals(expedienteBean.getCuerpo().toString())) {
						expedienteBean.setCuerpo(new Long(cuerpo[i]));
						expedienteBean.setFolioHasta(new Long(folioHasta[i]));
						expedienteBean.setFolioDesde(new Long(folioDesde[i]));
						expedienteDAO.update(expedienteBean);
						exist = true;
					}
				}
				if (!exist) {
					expedienteDAO.save(bean);
				}
			}
		}
	}

	public ExpedienteDAO getExpedienteDAO() {
		return expedienteDAO;
	}

	public void setExpedienteDAO(ExpedienteDAO expedienteDAO) {
		this.expedienteDAO = expedienteDAO;
	}

	public ExpedienteMovimientoDTO obtenerExpedienteMov(Long idProyecto) {

		bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		expedienteMovimientoDAO = (ExpedienteMovimientoDAO)ContextUtil.getBean("expedienteMovimientoDao");
		List<BitacoraBean> bitacoraList = bitacoraDAO.findByProyectoTipo(idProyecto,TipoBitacora.MOV_EXPEDIENTE.getName());
		if (!bitacoraList.isEmpty()) {
			for (int i=0;i<bitacoraList.size();i++) {
				ExpedienteMovimientoBean bean = expedienteMovimientoDAO.read(bitacoraList.get(i).getId());
				if (!bean.getEstado()) {
					return ExpedienteMovimientoAssembler.getInstance().buildDto(bean);
				}
			}
		}
		return null;
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}

	public void cargarCriterio(Long idProyecto, String proyectoTipo) {
		if (!(Util.isBlank(proyectoTipo))) {
			ProyectoBean proyectoBean = (ProyectoBean) proyectoDAO.read(idProyecto);
			DistribucionTipoProyectoDAO distTipoProyectoDAO = (DistribucionTipoProyectoDAO) ContextUtil.getBean("distribucionTipoProyectoDao");
			DistribucionTipoProyectoBean distribucionTipoProyectoBean = distTipoProyectoDAO.read(new Long(proyectoTipo));
			tipoProyectoDAO = (TipoProyectoDAO)ContextUtil.getBean("tipoProyectoDao");
			if (distribucionTipoProyectoBean != null) {
				TipoProyectoBean tipoProyecto = new TipoProyectoBean();
				tipoProyecto = tipoProyectoDAO.read(distribucionTipoProyectoBean.getIdTipoProyecto());
				proyectoBean.getProyectoDatos().setTipoProyecto(tipoProyecto);
				proyectoBean.getProyectoDatos().setIdTipoProyecto(tipoProyecto.getId());
				proyectoDatosDAO.update(proyectoBean.getProyectoDatos());
			}
		}
	}

	public void setTipoProyectoDAO(TipoProyectoDAO tipoProyectoDAO) {
		this.tipoProyectoDAO = tipoProyectoDAO;
	}

	public String obtenerTipoProyecto(String idProyecto) {
		ProyectoBean proyectoBean = (ProyectoBean) proyectoDAO.read(new Long(idProyecto));
		Long idInstrumento = proyectoBean.getIdInstrumento();
		Long idTipoProyecto = proyectoBean.getProyectoDatos().getIdTipoProyecto();
		if ((idTipoProyecto == null) || (idInstrumento == null)) {
			return null;
		}
		DistribucionTipoProyectoDAO distTipoProyectoDAO = (DistribucionTipoProyectoDAO) ContextUtil.getBean("distribucionTipoProyectoDao");
		List<DistribucionTipoProyectoBean> list = distTipoProyectoDAO.findByInstrumentoTipoProyecto(idTipoProyecto,idInstrumento);
		if (list.isEmpty()) {
			return null;
		}
		DistribucionTipoProyectoBean bean = (DistribucionTipoProyectoBean)list.get(0);
		return bean.getId().toString();
	}

	/** 
	 Este metodo fue agregado para realizar la actualizacion de un campo en la base de datos
	**/
	public void modificarRecomendaciones() {
		Log log = LogFactory.getLog(this.getClass());
		Collection<ProyectoBean> proyectos = this.getProyectoDAO().getAll();
		for (ProyectoBean bean : proyectos) {
			try{
				bean.setRecomendacionFinal( bean.getRecomendacion() );
				this.getProyectoDAO().update(bean);
			}catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	/**
	 * @author ssanchez
	 */
	public void finalizarProyecto(Long idProyecto, String observacion) {
			
		ProyectoBean proyecto = (ProyectoBean) proyectoDAO.read(idProyecto);
		proyecto.finalizarProyecto();
		proyecto.setFechaCierreFinal(DateTimeUtil.getDate());
		proyectoDAO.update(proyecto);
		
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setDescripcion(observacion);
		bitacora.setIdUsuario(this.getUserId());
		bitacora.setTema(BitacoraTema.FINALIZACION_PROYECTO);
		bitacoraDAO.save(bitacora);
	}

	public String getTipoMatrizPresupuesto(Long idProyecto) {
		ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);
		return proyectoBean.getInstrumento().getMatrizPresupuesto().getTipo();
	}
	
	/**
	 * Obtiene del <code>ProyectoBean</code> en
	 * base al <i>idProyecto</i>.<br>
	 * @param idProyecto
	 * @return el <code>ProyectoBean</code>
	 */
	public ProyectoBean obtenerProyectoBean(Long idProyecto) {
		
		ProyectoBean proyectoBean = (ProyectoBean) proyectoDAO.read(idProyecto);

		return proyectoBean;
	}
	
	/**
	 * Persiste los datos ingresados en el
	 * proyecto histórico correspondiente
	 * al <i>idProyecto</i>.<br>
	 * @param idProyecto
	 * @param datosInicialesDTO
	 */
	public void completarDatosIniciales(Long idProyecto, Long idSeguimiento, CompletarDatosInicialesDTO datosInicialesDTO) {
		
		ProyectoBean proyecto = obtenerProyectoBean(idProyecto);
		
		proyecto.setRecomendacionFinal(datosInicialesDTO.getRecomendacionFinal());
		proyecto.setFechaResolucion(datosInicialesDTO.getFechaResolucion());
		proyecto.setCodigoResolucion(datosInicialesDTO.getCodigoResolucion());
		proyecto.setPorcentajeAlicuotaAdjudicada(datosInicialesDTO.getPorcentajeAlicuotaAdjudicada());
		proyecto.setPorcentajeAlicuotaSolicitada(datosInicialesDTO.getPorcentajeAlicuotaSolicitada());
		proyecto.setFechaFirmaDeContrato(datosInicialesDTO.getFechaFirmaDeContrato());
		
		proyectoDAO.update(proyecto);
		
		cargarRendicionesHistoricas(idProyecto,idSeguimiento,datosInicialesDTO);
	}
	
	/**
	 * Guarda los datos de rendiciones históricos
	 * en el seguimiento
	 * @param idProyecto
	 * @param datosInicialesDTO
	 */
	private void cargarRendicionesHistoricas(Long idProyecto, Long idSeguimiento, CompletarDatosInicialesDTO datosInicialesDTO) {
		
		RubroDAO rubroDAO = (RubroDAO) WebContextUtil.getBeanFactory().getBean("rubroDao");
		List<RubroBean> listaRubros = rubroDAO.findSinPadres();
		
		BigDecimal montoTotal = null;
		List<BigDecimal> montoParte = new ArrayList<BigDecimal>();
		BigDecimal cero = BigDecimal.ZERO;
		montoParte.add(datosInicialesDTO.getMontoFontarRrhh()==null ? cero : datosInicialesDTO.getMontoFontarRrhh());
		montoParte.add(datosInicialesDTO.getMontoFontarBienCapital()==null ? cero : datosInicialesDTO.getMontoFontarBienCapital());
		montoParte.add(datosInicialesDTO.getMontoFontarMaterialInsumo()==null ? cero : datosInicialesDTO.getMontoFontarMaterialInsumo());
		montoParte.add(datosInicialesDTO.getMontoFontarOtro()==null ? cero : datosInicialesDTO.getMontoFontarOtro());
		montoParte.add(datosInicialesDTO.getMontoFontarConsultoriaServicio()==null ? cero : datosInicialesDTO.getMontoFontarConsultoriaServicio());

		List<BigDecimal> montoContraparte = new ArrayList<BigDecimal>();
		montoContraparte.add(datosInicialesDTO.getMontoContraparteRrhh()==null ? cero : datosInicialesDTO.getMontoContraparteRrhh());
		montoContraparte.add(datosInicialesDTO.getMontoContraparteBienCapital()==null ? cero : datosInicialesDTO.getMontoContraparteBienCapital());
		montoContraparte.add(datosInicialesDTO.getMontoContraparteMaterialInsumo()==null ? cero : datosInicialesDTO.getMontoContraparteMaterialInsumo());
		montoContraparte.add(datosInicialesDTO.getMontoContraparteOtro()==null ? cero : datosInicialesDTO.getMontoContraparteOtro());
		montoContraparte.add(datosInicialesDTO.getMontoContraparteConsultoriaServicio()==null ? cero : datosInicialesDTO.getMontoContraparteConsultoriaServicio());

		RubroBean rubro;
		for (int i = 0; i < listaRubros.size(); i++) {
				
			rubro = listaRubros.get(i);
			
			RendicionCuentasBean rendicion = new RendicionCuentasBean();		
			rendicion.setIdRubro(rubro.getId());
			rendicion.setIdSeguimiento(idSeguimiento);				
			rendicion.setFecha(DateTimeUtil.getDate());
			rendicion.setVersion(DateTimeUtil.getDate());
			rendicion.setDescripcion(Constant.RendicionCuentasDescripcion.RENDICION_HISTORICA);
			rendicion.setMontoParteRendicion(montoParte.get(i));
			rendicion.setMontoContraparteRendicion(montoContraparte.get(i));
			rendicion.setMontoParteEvaluacion(montoParte.get(i));
			rendicion.setMontoContraparteEvaluacion(montoContraparte.get(i));
			rendicion.setMontoParteGestion(montoParte.get(i));
			rendicion.setMontoContraparteGestion(montoContraparte.get(i));

			if(montoParte.get(i)!=null) 
				montoTotal = ((BigDecimal)montoParte.get(i)).add((BigDecimal)montoContraparte.get(i));
			else if(montoContraparte.get(i)!=null) 
				montoTotal = ((BigDecimal)montoContraparte.get(i)).add((BigDecimal)montoParte.get(i));
			
			rendicion.setMontoTotal(montoTotal);
			rendicion.setMontoTotalEvaluacion(montoTotal);
			rendicion.setMontoTotalGestion(montoTotal);
			
			administrarSeguimientoServicio.cargarRendicion(rendicion);
		}
	}

	public Recomendacion obtenerRecomendacionDeProyecto(Long idProyecto) throws SecurityException, AccesoDenegadoException {
		ProyectoBean proyecto = proyectoDAO.read(idProyecto);
		EncryptedObject recomendacionEncrypted = getRecomendacionSiEsAccesible(proyecto);
		if(recomendacionEncrypted==null)
			throw new AccesoDenegadoException("recomendacion");
		else 
			return (Recomendacion) recomendacionEncrypted.getObject();
	}

	public EncryptedObject getRecomendacionSiEsAccesible(ProyectoBean proyecto) {
		if(
			authorizationService.grantedPermissionByInstrumento("PROYECTOS-VISUALIZAR-RECOMENDACION", proyecto.getIdInstrumento())
		||  authorizationService.grantedPermissionByInstrumento("PROYECTOS-CAMBIAR-RECOMENDACION", proyecto.getIdInstrumento())
		) return proyecto.getRecomendacionProyecto();
		else return null;
	}
}