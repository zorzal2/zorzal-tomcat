package com.fontar.bus.impl.proyecto;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.data.Constant;
import com.fontar.data.Constant.BitacoraTema;
import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.EvaluacionGeneralDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.EvaluacionGeneralAssembler;
import com.fontar.data.impl.assembler.ProyectoRaizAssembler;
import com.fontar.data.impl.assembler.ProyectoRaizCerrarAssembler;
import com.fontar.data.impl.assembler.ProyectoRaizEvaluarAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.Evaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.hibernate.HibernateUtil;

/**
 * Servicio para administrar las acciones comunes de un proyecto
 * @author gboaglio
 * 
 */
public class AdministrarProyectoRaizServicioImpl implements AdministrarProyectoRaizServicio {

	private ProyectoRaizDAO proyectoRaizDAO;
	
	private ProyectoDAO proyectoDAO;

	private BitacoraDAO bitacoraDAO;

	private EvaluacionGeneralDAO evaluacionGeneralDAO;

	private EvaluacionEvaluadorDAO evaluacionEvaluadorDAO;
	
	private AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	
	
	private BitacoraBuilder builder = new BitacoraBuilder();
	
	public DTO getProyectoRaizDTO(Long idProyecto, ProyectoRaizGeneralAssembler assembler) {
		ProyectoRaizBean proyectoRaizBean = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		DTO dto = assembler.buildDTO(proyectoRaizBean);

		return dto;
	}
	public ProyectoRaizDTO getProyectoRaizDTO(Long idProyecto) {
		return (ProyectoRaizDTO) getProyectoRaizDTO(idProyecto, new ProyectoRaizAssembler());
	}

	/**
	 * Obtiene los datos para el Cierre de un Proyecto / Idea Proyecto
	 */
	public ProyectoRaizCerrarDTO obtenerDatosCierre(Long idProyecto) {
		ProyectoRaizBean proyectoRaizBean = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		ProyectoRaizCerrarDTO dto = ProyectoRaizCerrarAssembler.buildDto(proyectoRaizBean);

		return dto;
	}

	/**
	 * Cierra un Proyecto / Idea Proyecto
	 */
	public void cerrarProyecto(Long idProyecto, MotivoCierre motivo, String observacion) {
			
		ProyectoRaizBean proyecto = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		proyecto.cerrarProyecto();
		
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setDescripcion(observacion);
		bitacora.setIdUsuario(this.getUserId());
		
		// GB/ FIXME: Arreglar cuando se rediseñe la creación de bitácoras
		String tema = "Cierre de Proyecto. ";
		if (proyecto instanceof IdeaProyectoBean) {
			tema = "Cierre de Idea Proyecto. ";
		}
		bitacora.setTema(tema + " Motivo: "+motivo.getDescripcion() );
		proyectoRaizDAO.update(proyecto);
		bitacoraDAO.save(bitacora);
	}

	/**
	 * Anular un Proyecto / Idea Proyecto
	 */
	public void anularProyecto(Long idProyecto, String observacion) {
		
		ProyectoRaizBean proyecto = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		proyecto.anularProyecto();
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setDescripcion(observacion);
		bitacora.setIdUsuario(this.getUserId());
		
		// GB/ FIXME: Arreglar cuando se rediseñe la creación de bitácoras
		String tema = "Anular el Proyecto: ";
		if (proyecto instanceof IdeaProyectoBean) {
			tema = "Anular la Idea Proyecto: ";
		}
		bitacora.setTema(tema);
		proyectoRaizDAO.update(proyecto);
		bitacoraDAO.save(bitacora);
	}

	/**
	 * Obtiene los datos para la Evaluación de un Proyecto / Idea Proyecto
	 */
	public ProyectoRaizEvaluarDTO obtenerDatosEvaluacion(Long idProyecto) {
		ProyectoRaizBean proyectoRaizBean = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		ProyectoRaizEvaluarDTO dto = ProyectoRaizEvaluarAssembler.buildDto(proyectoRaizBean);

		return dto;
	}

	/**
	 * Carga una evaluación a un Proyecto / Idea Proyecto
	 */
	public Long cargarEvaluacionAProyecto(EvaluacionGeneralDTO evaluacionDTO, Long idProyecto) {
		EvaluacionGeneralAssembler assembler = EvaluacionGeneralAssembler.getInstance();
		EvaluacionGeneralBean evaluacion = assembler.buildBean(evaluacionDTO);

		ProyectoRaizBean proyecto = (ProyectoRaizBean) proyectoRaizDAO.read(idProyecto);

		proyecto.enProcesoEvaluacion();

		evaluacion.setFecha(DateTimeUtil.getDate());
		evaluacion.setIdProyecto(idProyecto);
		evaluacion.setProyecto(proyecto);
		Long idPresupuesto = (proyecto.getIdPresupuesto()==null)? proyecto.getIdPresupuestoOriginal() : proyecto.getIdPresupuesto();
		evaluacion.setIdPresupuestoInicial(idPresupuesto);
		evaluacion.iniciar();

		// Los datos de la bitacora deben estar en el servicio
		BitacoraBean bitacora = this.builder.cargarEvaluacion( proyecto );

		// Actualizo los beans
		//bitacoraDAO.save( bitacora );
		evaluacion.setBitacora( bitacora);
		evaluacionGeneralDAO.save(evaluacion);
		proyectoRaizDAO.update(proyecto);

		// Grabo los evaluadores
		EvaluacionEvaluadorBean evaluacionEvaluadorBean;
		Collection<EvaluacionEvaluadorDTO> evaluadorListDTO = evaluacionDTO.getEvaluadores();
		if (evaluadorListDTO != null) {
			Iterator iter = evaluadorListDTO.iterator();

			while (iter.hasNext()) {
				EvaluacionEvaluadorDTO evaluadorDTO = (EvaluacionEvaluadorDTO) iter.next();
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
		return evaluacion.getId();
	}
	
	/**
	 * Obtiene que clase de objeto ProyectoRaiz es: Proyecto, IdeaProyecto
	 * @author ssanchez
	 */
	public ProyectoRaizEvaluarDTO obtenerClaseProyectoRaiz(Long idProyectoRaiz) {
		ProyectoRaizBean proyectoRaizBean = (ProyectoRaizBean) proyectoRaizDAO.read(idProyectoRaiz);

		ProyectoRaizEvaluarDTO dto = ProyectoRaizEvaluarAssembler.buildDto(proyectoRaizBean);

		return dto;
	}	
	
	/**
	 * Pone un ProyectoRaiz(proyecto o ideaproyecto) en solicitud de reconsideración
	 * @author ssanchez
	 */
	public void solicitarReconsideracionDeProyectoRaiz(Long idProyectoRaiz, Date fecha, String observacion) {
		
		BitacoraBean bitacora = new BitacoraBean();
		
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setIdProyecto(idProyectoRaiz);
		bitacora.setFechaAsunto(fecha);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema(BitacoraTema.PEDIDO_DE_RECONSIDERACION);
		bitacora.setDescripcion(observacion);
		bitacora.setIdUsuario(this.getUserId());
		bitacoraDAO.save(bitacora);

		// cambio estado y marca de reconsideración del proyecto
		ProyectoRaizBean proyectoRaizBean = proyectoRaizDAO.read(idProyectoRaiz);
		proyectoRaizBean.reconsiderarProyecto();
		proyectoRaizBean.setEstadoReconsideracion( true);

		// actualizo el proyecto
		proyectoRaizDAO.update(proyectoRaizBean);
	}
	

	public ProyectoRaizDAO getProyectoRaizDAO() {
		return proyectoRaizDAO;
	}

	public void setProyectoRaizDAO(ProyectoRaizDAO proyectoRaizDAO) {
		this.proyectoRaizDAO = proyectoRaizDAO;
	}

	public BitacoraDAO getBitacoraDAO() {
		return bitacoraDAO;
	}

	public void setBitacoraDAO(BitacoraDAO bitacoraDAO) {
		this.bitacoraDAO = bitacoraDAO;
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

	
	public String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
	public void actualizarPresupuestoActual(Long proyectoId, ProyectoPresupuestoDTO presupuesto) {
		ProyectoRaizBean proyectoBean = proyectoRaizDAO.read(proyectoId);
		ProyectoPresupuestoDAO proyectoPresupuestoDAO = (ProyectoPresupuestoDAO)ContextUtil.getBean("proyectoPresupuestoDao");
		ProyectoPresupuestoBean presupuestoBean = proyectoPresupuestoDAO.read(presupuesto.getId());		
	
		//Propago datos del presupuesto anterior
		if(proyectoBean.getUltimoPresupuesto()!=null) proyectoBean.getUltimoPresupuesto().propagarDatosA(presupuestoBean);
		proyectoPresupuestoDAO.update(presupuestoBean);
		
		if(proyectoBean.getProyectoPresupuestoOriginal()==null) {
			proyectoBean.setProyectoPresupuestoOriginal(presupuestoBean);
			proyectoBean.setIdPresupuestoOriginal(presupuestoBean.getId());
		}
		proyectoBean.setProyectoPresupuesto(presupuestoBean);
		proyectoBean.setIdPresupuesto(presupuestoBean.getId());
		proyectoRaizDAO.update(proyectoBean);
	}
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuestoParaFinalizarControl(Long idProyecto) {
		ProyectoBean proyecto = proyectoDAO.read(idProyecto);
		
		EvaluacionResumenDePresupuestoDTO dto = new EvaluacionResumenDePresupuestoDTO();
		
		InstrumentoBean instrumento = proyecto.getInstrumento();
		if(instrumento==null) return null;
		
		boolean desplegarEnParteYContraparte = !instrumento.aplicaCargaAlicuotaCF();
		dto.setDesplegarEnParteYContraparte(desplegarEnParteYContraparte);
		
		try {
			//Monto solicitado: es el monto del presupuesto original del proyecto
			ProyectoPresupuestoBean presupuestoOriginal = proyecto.getProyectoPresupuestoOriginal();
			if(presupuestoOriginal!=null) {
				dto.setMontoTotalSolicitado(presupuestoOriginal.getMontoTotal());
				if(desplegarEnParteYContraparte) {
					dto.setMontoFontarSolicitado(presupuestoOriginal.getMontoSolicitado());
					dto.setMontoContraparteSolicitado(presupuestoOriginal.getMontoEmpresa());
				}
			}
			
			Collection<Evaluacion> evaluacionesList = administrarEvaluacionesServicio.getEvaluaciones(proyecto, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);
			
			boolean MostrarMontosAprobados = false; 
			for(Evaluacion evaluacion : evaluacionesList){
				if(evaluacion.getResultado() != ResultadoEvaluacion.RECHAZADO.getDescripcion())	
					MostrarMontosAprobados = true;
			}
			
						//Monto aprobado: es el presupuesto que quedó luego de las evaluaciones
			ProyectoPresupuestoBean presupuestoFinal = proyecto.getUltimoPresupuesto();			
			if(presupuestoFinal!=null) {
				//Si todas las evaluaciones estan rechazadas no muestra Montos Aprobados
				if (MostrarMontosAprobados){
					dto.setMontoTotalAprobado(presupuestoFinal.getMontoTotal());
					if(desplegarEnParteYContraparte) {
						dto.setMontoFontarAprobado(presupuestoFinal.getMontoSolicitado());
						dto.setMontoContraparteAprobado(presupuestoFinal.getMontoEmpresa());
					}
				}
				//Si este es el primer presupuesto, lo asumo como original
				if(presupuestoOriginal==null) {
					dto.setMontoTotalSolicitado(presupuestoFinal.getMontoTotal());
					if(desplegarEnParteYContraparte) {
						dto.setMontoFontarSolicitado(presupuestoFinal.getMontoSolicitado());
						dto.setMontoContraparteSolicitado(presupuestoFinal.getMontoEmpresa());
					}
				}
			}
		} catch (SecurityException e) {
			dto.setRequiereContextoDeEncriptacion(true);
		}
		return dto;
	}
	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}
	public void setProyectoDAO(ProyectoDAO proyectoDAO) {
		this.proyectoDAO = proyectoDAO;
	}
}