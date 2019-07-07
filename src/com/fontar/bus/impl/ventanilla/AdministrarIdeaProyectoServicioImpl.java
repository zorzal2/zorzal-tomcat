package com.fontar.bus.impl.ventanilla;

import java.text.DecimalFormat;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.api.dao.ProyectoDatosDAO;
import com.fontar.data.api.dao.ProyectoJurisdiccionDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * Servicio para administrar las acciones de una idea proyecto
 * @author gboaglio
 * 
 */
public class AdministrarIdeaProyectoServicioImpl implements AdministrarIdeaProyectoServicio {
	private IdeaProyectoDAO ideaProyectoDao;

	private ProyectoDatosDAO proyectoDatosDao;

	private ProyectoPresupuestoDAO proyectoPresupuestoDao;

	private ProyectoJurisdiccionDAO proyectoJurisdiccionDao;

	private BitacoraDAO bitacoraDao;
	
	private DecimalFormat iPCodeFormatter = new DecimalFormat("00000000");

	/**
	 * Obtiene un dto desde un bean. El dto es construido en base al assembler
	 * enviado por parametro
	 */
	public DTO getIdeaProyectoDTO(Long idIdeaProyecto, IdeaProyectoGeneralAssembler assembler) {
		IdeaProyectoBean ideaProyectoBean = ideaProyectoDao.read(idIdeaProyecto);
		return assembler.buildDTO(ideaProyectoBean);
	}
	
	public IdeaProyectoVisualizarDTO getIdeaProyectoVisualizarDTO(Long idIdeaProyecto, IdeaProyectoDTOAssembler assembler) {
		IdeaProyectoBean ideaProyectoBean = ideaProyectoDao.read(idIdeaProyecto);
		return assembler.buildDtoVisualizar(ideaProyectoBean);
	}
	
	// Los params deben unificarse en un DTO
	public BitacoraBean procesarDatosIdeaProyecto(IdeaProyectoBean ideaProyectoBean,
			ProyectoDatosBean proyectoDatosBean, ProyectoJurisdiccionBean proyectoJurisdiccionBean,
			ProyectoPresupuestoBean proyectoPresupuestoBean) {
	
		// Cargo datos de bitacora correspondiente a Idea Proyecto
		BitacoraBean bitacoraIdeaProy = new BitacoraBean();
		bitacoraIdeaProy.setTipo(TipoBitacora.BASICO);
		bitacoraIdeaProy.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraIdeaProy.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraIdeaProy.setTema(Constant.BitacoraTema.ALTA_IDEA_PROYECTO);
		bitacoraIdeaProy.setDescripcion(Constant.BitacoraDescripcion.NA);
		bitacoraIdeaProy.setIdUsuario(this.getUserId());
		
		// Cargo datos de bitacora correspondiente a Proyecto Datos
		BitacoraBean bitacoraDatos = proyectoDatosBean.getBitacora();
		bitacoraDatos.setTipo( TipoBitacora.PROY_DATOS);
		bitacoraDatos.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraDatos.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraDatos.setTema(Constant.BitacoraTema.PROY_DATOS_IDEA_PROYECTO);
		bitacoraDatos.setDescripcion(Constant.BitacoraDescripcion.NA);
		bitacoraDatos.setIdUsuario(this.getUserId());
		
		// Cargo datos de bitacora correspondiente a Proyecto Presupuesto
		BitacoraBean bitacoraPresupuesto = proyectoPresupuestoBean.getBitacora();
		bitacoraPresupuesto.setTipo( TipoBitacora.PRESUPUESTO);
		bitacoraPresupuesto.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraPresupuesto.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraPresupuesto.setTema(Constant.BitacoraTema.PRESUPUESTO);
		bitacoraPresupuesto.setDescripcion(Constant.BitacoraDescripcion.ASOCIACION_PRESUPUESTO);
		bitacoraPresupuesto.setIdUsuario(this.getUserId());
		
		// Cargo datos de bitacora correspondiente a Proyecto Jurisdicción
		BitacoraBean bitacoraJurisdiccion = proyectoJurisdiccionBean.getBitacora();
		bitacoraJurisdiccion.setTipo( TipoBitacora.PROY_JURISDICCION);
		bitacoraJurisdiccion.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setTema(Constant.BitacoraTema.JURISDICCIÓN);
		bitacoraJurisdiccion.setDescripcion(Constant.BitacoraDescripcion.NA);
		bitacoraJurisdiccion.setIdUsuario(this.getUserId());
		
		return bitacoraIdeaProy;
	}

	public void modificarIdeaProyecto(String id, IdeaProyectoDTO datosIdeaProyecto) {
		IdeaProyectoBean ideaProyectoBean;
		ProyectoDatosBean proyectoDatosBean;
		ProyectoJurisdiccionBean proyectoJurisdiccionBean;
		ProyectoPresupuestoBean proyectoPresupuestoBean;

		// Si estoy editanto una Idea Proyecto levanto los datos de los objetos
		ideaProyectoBean = ideaProyectoDao.read(new Long(id));
		proyectoDatosBean = proyectoDatosDao.read(ideaProyectoBean.getIdDatos());
		proyectoJurisdiccionBean = proyectoJurisdiccionDao.read(ideaProyectoBean.getIdProyectoJurisdiccion());
		proyectoPresupuestoBean = proyectoPresupuestoDao.read(ideaProyectoBean.getIdPresupuestoOriginal());

		proyectoDatosBean.setFechaIngreso(datosIdeaProyecto.getFechaIngreso());
		proyectoDatosBean.setIdTipoProyecto(datosIdeaProyecto.getIdTipoProyecto());
		proyectoDatosBean.setTitulo(datosIdeaProyecto.getTitulo());
		proyectoDatosBean.setResumen(datosIdeaProyecto.getResumen());
		proyectoDatosBean.setInstrumentoSolicitado(datosIdeaProyecto.getInstrumentoSolicitado());
		proyectoDatosBean.setDuracion(datosIdeaProyecto.getDuracion());
		
		proyectoDatosBean.setIdPersonaDirector(datosIdeaProyecto.getIdPersonaDirector());
		proyectoDatosBean.setIdPersonaLegal(datosIdeaProyecto.getIdPersonaLegal());
		proyectoDatosBean.setIdPersonaRepresentante(datosIdeaProyecto.getIdPersonaRepresentante());

		proyectoPresupuestoBean.setMontoTotal(datosIdeaProyecto.getMontoTotal());
		proyectoPresupuestoBean.setMontoSolicitado(datosIdeaProyecto.getMontoSolicitado());

		proyectoJurisdiccionBean.setIdJurisdiccion(datosIdeaProyecto.getIdJurisdiccion());

		proyectoDatosBean.setIdEntidadBeneficiaria(datosIdeaProyecto.getIdEntidadBeneficiaria());
		proyectoDatosBean.setObservacion(datosIdeaProyecto.getObservaciones());

		procesarDatosIdeaProyecto(ideaProyectoBean, proyectoDatosBean, proyectoJurisdiccionBean, proyectoPresupuestoBean);

		// Modifico los datos en DB

		// Modifico los datos el proyecto
		ideaProyectoDao.update(ideaProyectoBean);

		// Agrego datos del proyecto
		proyectoDatosBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		proyectoDatosDao.save(proyectoDatosBean);

		// Agrego una relación entre un proyecto y una jurisdicción
		proyectoPresupuestoBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		proyectoPresupuestoDao.save(proyectoPresupuestoBean);

		// Agrego una relación entre un proyecto y una jurisdicción
		proyectoJurisdiccionBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		proyectoJurisdiccionDao.save(proyectoJurisdiccionBean);

		// Actualizo el proyecto
		ideaProyectoBean.setIdDatos(proyectoDatosBean.getId());
		ideaProyectoBean.setIdPresupuestoOriginal(proyectoPresupuestoBean.getId());
		ideaProyectoBean.setIdProyectoJurisdiccion(proyectoJurisdiccionBean.getId());
		ideaProyectoDao.update(ideaProyectoBean);

	}

	public IdeaProyectoBean cargarIdeaProyecto(IdeaProyectoDTO datosIdeaProyecto) {
				
		IdeaProyectoBean ideaProyectoBean = new IdeaProyectoBean();
		ProyectoDatosBean proyectoDatosBean = new ProyectoDatosBean();
		ProyectoJurisdiccionBean proyectoJurisdiccionBean = new ProyectoJurisdiccionBean();
		ProyectoPresupuestoBean proyectoPresupuestoBean = new ProyectoPresupuestoBean();
		//Le pongo el código
		Long availableCode = ideaProyectoDao.selectLastAvailableCode();
		if (availableCode == null) availableCode = 1l;
		ideaProyectoBean.setCodigoIdeaProyecto(availableCode);
		ideaProyectoBean.setCodigo("IP-" + iPCodeFormatter.format(availableCode));

		proyectoDatosBean.setFechaIngreso(datosIdeaProyecto.getFechaIngreso());
		proyectoDatosBean.setIdTipoProyecto(datosIdeaProyecto.getIdTipoProyecto());
		proyectoDatosBean.setTitulo(datosIdeaProyecto.getTitulo());
		proyectoDatosBean.setResumen(datosIdeaProyecto.getResumen());
		proyectoDatosBean.setInstrumentoSolicitado(datosIdeaProyecto.getInstrumentoSolicitado());
		proyectoDatosBean.setDuracion(datosIdeaProyecto.getDuracion());

		proyectoPresupuestoBean.setMontoTotal(datosIdeaProyecto.getMontoTotal());
		proyectoPresupuestoBean.setMontoSolicitado(datosIdeaProyecto.getMontoSolicitado());

		proyectoJurisdiccionBean.setIdJurisdiccion(datosIdeaProyecto.getIdJurisdiccion());
		proyectoJurisdiccionBean.setCodigo(ideaProyectoBean.getCodigo());

		proyectoDatosBean.setIdEntidadBeneficiaria(datosIdeaProyecto.getIdEntidadBeneficiaria());
		proyectoDatosBean.setObservacion(datosIdeaProyecto.getObservaciones());
		
		
		proyectoDatosBean.setIdPersonaDirector(datosIdeaProyecto.getIdPersonaDirector());
		proyectoDatosBean.setIdPersonaLegal(datosIdeaProyecto.getIdPersonaLegal());
		proyectoDatosBean.setIdPersonaRepresentante(datosIdeaProyecto.getIdPersonaRepresentante());

		BitacoraBean bitacoraIdeaProy = procesarDatosIdeaProyecto(ideaProyectoBean, proyectoDatosBean, proyectoJurisdiccionBean, proyectoPresupuestoBean);

		ideaProyectoBean.setEstado(EstadoIdeaProyecto.INICIADO);
		
		// FIXME: FF / lo sacamos durante refactor ProyectoRaiz 11/12/2006.
		// ideaProyectoBean.setCodigoIdeaProyecto(datosIdeaProyecto.getCodigoIdeaProyecto());
		// // TODO Cambiar esto

		// guardo nuevo proyecto
		ideaProyectoDao.save(ideaProyectoBean);

		// guardo la bitacora de la Idea Proyecto
		bitacoraIdeaProy.setIdProyecto(ideaProyectoBean.getId());
		bitacoraIdeaProy.setIdUsuario(this.getUserId());
		bitacoraDao.save(bitacoraIdeaProy);

		// guardo nuevo proyectoDatos
		Long idIdeaProyecto = ideaProyectoBean.getId();
		proyectoDatosBean.getBitacora().setIdProyecto(idIdeaProyecto);
		proyectoPresupuestoBean.getBitacora().setIdProyecto(idIdeaProyecto);
		proyectoJurisdiccionBean.getBitacora().setIdProyecto(idIdeaProyecto);

		proyectoDatosDao.save(proyectoDatosBean);

		// guardo relación entre un proyecto y una jurisdicción
		proyectoPresupuestoDao.save(proyectoPresupuestoBean);

		// guardo relación entre un proyecto y una jurisdicción
		proyectoJurisdiccionDao.save(proyectoJurisdiccionBean);

		// actualizo las referencias de el objeto Proyecto a sus objetos
		// relacionados
		ideaProyectoBean.setIdDatos(proyectoDatosBean.getId());
		ideaProyectoBean.setIdPresupuestoOriginal(proyectoPresupuestoBean.getId());
		ideaProyectoBean.setIdProyectoJurisdiccion(proyectoJurisdiccionBean.getId());

		ideaProyectoDao.update(ideaProyectoBean);

		return ideaProyectoBean;
	}

	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public void setIdeaProyectoDao(IdeaProyectoDAO ideaProyectoDao) {
		this.ideaProyectoDao = ideaProyectoDao;
	}

	public void setProyectoDatosDao(ProyectoDatosDAO proyectoDatosDao) {
		this.proyectoDatosDao = proyectoDatosDao;
	}

	public void setProyectoJurisdiccionDao(ProyectoJurisdiccionDAO proyectoJurisdiccionDao) {
		this.proyectoJurisdiccionDao = proyectoJurisdiccionDao;
	}

	public void setProyectoPresupuestoDao(ProyectoPresupuestoDAO proyectoPresupuestoDao) {
		this.proyectoPresupuestoDao = proyectoPresupuestoDao;
	}

	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}
