package com.fontar.bus.impl.paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fontar.bus.api.paquete.AdministrarPaqueteServicio;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.data.Constant;
import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionPaqueteDAO;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoPaqueteDAO;
import com.fontar.data.impl.assembler.ProyectoFilaModificacionPaqueteDTOAssembler;
import com.fontar.data.impl.assembler.VisualizarPaqueteDTOAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarPaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarProyectoFilaDTO;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * Servicio para la administración de paquetes (armado y modificación)
 * 
 * @author gboaglio, ssanchez
 * @version 1.01, 12/01/07
 */
public class AdministrarPaqueteServicioImpl implements AdministrarPaqueteServicio {

	private InstrumentoDAO instrumentoDao;
	private ProyectoDAO proyectoDao;
	private ProyectoPaqueteDAO proyectoPaqueteDao;
	private PaqueteDAO paqueteDao;
	private EvaluacionPaqueteDAO evaluacionPaqueteDao;
	private BitacoraDAO bitacoraDao;
	
	public Long getIdPaquete(Long idProyectoPaquete) {
		ProyectoPaqueteBean bean = proyectoPaqueteDao.read(idProyectoPaquete);
		return bean.getIdPaquete();
	}
	
	/**
	 * Obtiene un dto desde un bean. El dto es construido en base al assembler
	 * enviado por parametro
	 */
	public DTO getPaqueteDTO(Long idPaquete, PaqueteGeneralAssembler assembler) {
		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);
		return assembler.buildDTO(paqueteBean);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.paquete.AdministrarPaqueteServicio3#armarPaquete(java.lang.String[],
	 * java.lang.Long, java.lang.String, java.lang.String)
	 */
	public Long armarPaquete(String[] proyectoArray, Long idInstrumento, String tratamiento, String tipoPaquete) {
		// creo el paquete y lo lleno de datos
		PaqueteBean paqueteBean = new PaqueteBean();
		paqueteBean.setEstado(EstadoPaquete.INICIADO);
		paqueteBean.setIdInstrumento(idInstrumento);
		paqueteBean.setTratamiento(TratamientoPaquete.valueOf(tratamiento));
		paqueteBean.setTipo(TipoPaquete.valueOf(tipoPaquete));
		// rrescato la comision del instrumento
		// InstrumentoBean instrumentoBean = (InstrumentoBean)
		// (getServicio().load(InstrumentoBean.class,
		// FormUtil.getLongValue(form, "instrumento")));
		InstrumentoBean instrumentoBean = instrumentoDao.read(idInstrumento);

		// se asigna el idcomision del instrumento solo a paquetes de comisión
		if (paqueteBean.getTipo().equals(TipoPaquete.COMISION)) {
			paqueteBean.setIdComision(instrumentoBean.getIdComision());
		}
		// Guardo en la BD
		paqueteDao.save(paqueteBean);

		agregarProyectosAPaquete(proyectoArray, paqueteBean);

		return paqueteBean.getId();
	}

	public void agregarProyectosAPaquete(String[] proyectoArray, PaqueteBean paqueteBean) {
		Collection<ProyectoBean> proyectos = new ArrayList<ProyectoBean>(proyectoArray.length);
		for (String id : proyectoArray) {
			proyectos.add(proyectoDao.read(new Long(id)));
		}
		agregarProyectosAPaquete(proyectos, paqueteBean);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.paquete.AdministrarPaqueteServicio3#modificarPaquete(java.lang.Long,
	 * java.lang.String[])
	 */
	public void modificarPaquete(Long idPaquete, String[] proyectosSeleccionados) {

		//Convierto el array de strings en un array de longs
		List<Long> idsProyectosSeleccionados = new ArrayList<Long>(proyectosSeleccionados.length);
		
		for(String id : proyectosSeleccionados)
			idsProyectosSeleccionados.add(new Long(id));
		
		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);
		Collection<ProyectoBean> proyectosDelPaquete = proyectoDao.findByPaqueteActivo(paqueteBean.getId());
		Collection<ProyectoBean> proyectosAEliminar = new ArrayList<ProyectoBean>();
		
		for(ProyectoBean proyecto : proyectosDelPaquete) {
			if(!idsProyectosSeleccionados.contains(proyecto.getId())) proyectosAEliminar.add(proyecto);
		}
		eliminarProyectosDePaquete(proyectosAEliminar, paqueteBean);
	}

	/**
	 * Retorna los proyectos de un paquete (un proyecto inactivo NO pertenece a
	 * un paquete)
	 * @author ssanchez
	 */
	@SuppressWarnings("unchecked")
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectosPaquete(Long idPaquete, String tipoPaquete) {
		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);

		String tratamiento = paqueteBean.getTratamiento().name();
		Long instrumento = paqueteBean.getIdInstrumento();

		String estado = null;
		if (tipoPaquete.equals(TipoPaquete.COMISION.getDescripcion())) {
			estado = EstadoProyecto.CONT_COM.name();
		}
		else if (tipoPaquete.equals(TipoPaquete.SECRETARIA.getDescripcion())) {
			estado = EstadoProyecto.CONT_SEC.name();
		}
		else if (tipoPaquete.equals(TipoPaquete.DIRECTORIO.getDescripcion())) {
			estado = EstadoProyecto.CONT_DIR_EVAL.name();
		}

		List<ProyectoBean> proyectosList = null;
		if (tratamiento.equals("")) {
			// obtengo los proyecto filtrando por paquete e instrumento si el
			// segundo no es null
			proyectosList = proyectoDao.findByPaqueteInstrumento(idPaquete, instrumento);
		}
		else {
			// obtengo los proyectos respectivos al tratamiento y al
			// instrumento, si es que inst no es null
			if (tratamiento.equals(TratamientoPaquete.EVALUACION.name())) {
				proyectosList = proyectoDao.findByPaqueteInstrumentoEvaluacionEstado(estado, idPaquete, instrumento);
			}
			else if (tratamiento.equals(TratamientoPaquete.RECONSIDERACION.name())) {
				proyectosList = proyectoDao.findByPaqueteInstrumentoReconsideracionEstado(estado, idPaquete, instrumento);
			}
			else if (tratamiento.equals(TratamientoPaquete.ADJUDICACION.name())) {
				proyectosList = proyectoDao.findByPaqueteInstrumentoAdjudicacion(idPaquete, instrumento);
			}
		}

		List<ProyectoFilaModificacionPaqueteDTO> proyectosDTO = new ArrayList();

		proyectosDTO = ProyectoFilaModificacionPaqueteDTOAssembler.buildDto(proyectosList, true);

		return proyectosDTO;
	}

	/**
	 * Obtiene los proyectos correspondientes a un instrumento, tratamiento y
	 * tipo de proyecto
	 * @author ssanchez
	 */
	@SuppressWarnings("unchecked")
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectos(Long instrumento, String tratamiento,
			String tipoPaquete) {
		List<ProyectoBean> proyectosList = null;

		String estado = null;
		if (tipoPaquete.equals(TipoPaquete.COMISION.name())) {
			estado = EstadoProyecto.CONT_COM.name();
		}
		else if (tipoPaquete.equals(TipoPaquete.SECRETARIA.name())) {
			estado = EstadoProyecto.CONT_SEC.name();
		}
		else if (tipoPaquete.equals(TipoPaquete.DIRECTORIO.name())) {
			estado = EstadoProyecto.CONT_DIR_EVAL.name();
		}

		if (!instrumento.equals("") && tratamiento.equals("")) {
			// obtengo los proyecto filtrando por instrumento
			proyectosList = proyectoDao.findByInstrumento(instrumento);
		}
		else if (!tratamiento.equals("")) {
			// obtengo los proyectos respectivos al tratamiento y al
			// instrumento, si es que inst no es null
			if (tratamiento.equals(TratamientoPaquete.EVALUACION.name())) {
				proyectosList = proyectoDao.findByInstrumentoEvaluacionEstado(estado, instrumento);
			}
			else if (tratamiento.equals(TratamientoPaquete.RECONSIDERACION.name())) {
				proyectosList = proyectoDao.findByInstrumentoReconsideracionEstado(estado, instrumento);
			}
			else if (tratamiento.equals(TratamientoPaquete.ADJUDICACION.name())) {
				proyectosList = proyectoDao.findByInstrumentoAdjudicacion(instrumento);
			}
		}

		List<ProyectoFilaModificacionPaqueteDTO> proyectosDTO = new ArrayList();

		proyectosDTO = ProyectoFilaModificacionPaqueteDTOAssembler.buildDto(proyectosList, false);

		return proyectosDTO;
	}

	/**
	 * Obtiene los datos de un Paquete para la vista visualizacion de paquete
	 * @param idPaquete
	 * @author ssanchez
	 */
	public VisualizarPaqueteDTO obtenerVisualizacionPaquete(Long idPaquete) {

		PaqueteBean paqueteBean = obtenerPaquete(idPaquete);

		VisualizarPaqueteDTO dto = VisualizarPaqueteDTOAssembler.buildDto(paqueteBean);

		// TODO: SS-implementar alguna forma de saber cual es la
		// evaluacionPaquete de un proyecto
		// Asigno a cada proyectoFilaDto el resultado actual de la evaluación
		List<EvaluacionPaqueteBean> evaluacionList;
		for (VisualizarProyectoFilaDTO proyectoFilaDto : dto.getFilasProyectos()) {
			evaluacionList = evaluacionPaqueteDao.findByProyectoPaqueteActivo(proyectoFilaDto.getIdProyecto(), paqueteBean.getId());
			if (evaluacionList.size() > 0) {
				proyectoFilaDto.setResultado(ObjectUtils.encriptedEnumSafeGet(evaluacionList.get(0).getResultadoEvaluacion()));
			}
		}

		return dto;
	}

	/**
	 * Obtiene los datos completos de un Paquete
	 * @param idPaquete
	 * @author ssanchez
	 */
	public PaqueteBean obtenerPaquete(Long idPaquete) {

		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);

		return paqueteBean;
	}

	public void setInstrumentoDao(InstrumentoDAO instrumentoDao) {
		this.instrumentoDao = instrumentoDao;
	}

	public void setPaqueteDao(PaqueteDAO paqueteDao) {
		this.paqueteDao = paqueteDao;
	}

	public void setProyectoDao(ProyectoDAO proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	public void setProyectoPaqueteDao(ProyectoPaqueteDAO proyectoPaqueteDao) {
		this.proyectoPaqueteDao = proyectoPaqueteDao;
	}

	public void anularPaquete(Long idPaquete, String descripcion) {
		
		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);
		paqueteBean.setEstado(EstadoPaquete.ANULADO);

		paqueteDao.update(paqueteBean);

		Set listProyecto = paqueteBean.getProyectosPaquete();
		
		Iterator iter = listProyecto.iterator();

		while (iter.hasNext()) {

			ProyectoPaqueteBean proyectoPaqueteBean = (ProyectoPaqueteBean) iter.next();
			ProyectoBean proyectoBean = proyectoPaqueteBean.getProyecto();
			proyectoBean.setEstadoEnPaquete(false);
			proyectoDao.update(proyectoBean);
			
			EvaluacionPaqueteBean evaluacionPaqueteBean = proyectoPaqueteBean.getEvaluacion();
			evaluacionPaqueteBean.setEstado(EstadoEvaluacion.ANULADA);
			evaluacionPaqueteDao.update(evaluacionPaqueteBean);
			
			BitacoraBean bitacoraBean = new BitacoraBean();
			bitacoraBean.setIdProyecto(proyectoBean.getId());
			bitacoraBean.setTipo(TipoBitacora.BASICO);
			bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraBean.setTema("Anulación del Paquete Nro. " + paqueteBean.getId());
			bitacoraBean.setDescripcion(descripcion);
			bitacoraBean.setIdUsuario(this.getUserId());
			bitacoraDao.save(bitacoraBean);
		}
	}

	public void setEvaluacionPaqueteDao(EvaluacionPaqueteDAO evaluacionPaqueteDao) {
		this.evaluacionPaqueteDao = evaluacionPaqueteDao;
	}
	
	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}
	
	public String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}

	public void eliminarProyectosDePaquete(Collection<ProyectoBean> proyectos, PaqueteBean paqueteBean) {
		for (ProyectoBean proyectoBean : proyectos) {
			// indico en proyecto que ya no pertenece a un paquete
			proyectoBean.setEstadoEnPaquete(false);
			proyectoDao.update(proyectoBean);

			// indico que la relacion paquete-proyecto ya no esta vigente
			ProyectoPaqueteBean proyectoPaqueteBean = proyectoPaqueteDao.findByProyectoPaquete(proyectoBean.getId(), paqueteBean.getId()).get(0);
			proyectoPaqueteBean.setEsActivo(false);

			// bitacora de modificacion
			BitacoraBean bitacoraBean = proyectoPaqueteBean.getBitacora();
			bitacoraBean.setTipo(TipoBitacora.BASICO);
			bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraBean.setTema(Constant.BitacoraTema.MODIFICACION_PROYECTOS_PAQUETES + " Nro. " + paqueteBean.getId());
			bitacoraBean.setDescripcion( "El proyecto dejó de pertenecer al paquete");
			bitacoraBean.setIdUsuario(this.getUserId());
			
			proyectoPaqueteDao.update(proyectoPaqueteBean);
			
		}		
	}
	public void eliminarProyectosDePaquete(String[] ids, PaqueteBean paqueteBean) {
		Collection<ProyectoBean> proyectos = new ArrayList<ProyectoBean>(ids.length);
		for (String id : ids) {
			proyectos.add(proyectoDao.read(new Long(id)));
		}		
	}

	public void agregarProyectosAPaquete(Collection<ProyectoBean> proyectos, PaqueteBean paqueteBean) {
		// obtengo lista de proyectos y proyectoPaquete a modificar/agregar
		ProyectoPaqueteBean proyectoPaqueteBean;
		
		for (ProyectoBean proyectoBean : proyectos) {

			// Marco al proyecto como que esta en un paquete.
			proyectoBean.setEstadoEnPaquete(true);
						

			// creo un proyectopaquete activo
			proyectoPaqueteBean = new ProyectoPaqueteBean();
			proyectoPaqueteBean.setEsActivo(true);
			proyectoPaqueteBean.setIdProyecto(proyectoBean.getId());
			proyectoPaqueteBean.setIdPaquete(paqueteBean.getId());

			
			// cargo datos de bitacora
			BitacoraBean bitacoraBean = proyectoPaqueteBean.getBitacora();
			bitacoraBean.setIdProyecto(proyectoBean.getId());
			bitacoraBean.setTipo(TipoBitacora.PROY_PAQUETE);
			bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraBean.setTema(Constant.BitacoraTema.CREACION_PROYECTOS_PAQUETES + " Nro. " + paqueteBean.getId());
			bitacoraBean.setDescripcion("Paquete de tipo "+paqueteBean.getTipo()+" (tratamiento "+paqueteBean.getTratamiento()+")" );
			bitacoraBean.setIdUsuario(this.getUserId());

			proyectoPaqueteDao.save(proyectoPaqueteBean);
			proyectoBean.setIdProyectoPaquete(proyectoPaqueteBean.getId());
			proyectoDao.update(proyectoBean);
			
			// Creo la evaluacion
			EvaluacionPaqueteBean evaluacion = new EvaluacionPaqueteBean();
			evaluacion.setIdProyectoPaquete(proyectoPaqueteBean.getId());
			evaluacion.setIdProyecto(proyectoBean.getId());
			evaluacion.setIdPaquete(paqueteBean.getId());
			evaluacion.setTipo(TipoEvaluacion.EVAL_PAQ);
			evaluacion.setFechaInicial(DateTimeUtil.getDate());
			evaluacion.setFecha(DateTimeUtil.getDate());
			Boolean permiteAdj = proyectoBean.getInstrumento().getPermiteAdjudicacion() && TipoPaquete.DIRECTORIO.equals(paqueteBean.getTipo());
			evaluacion.setResultado(ResultadoEvaluacion.paraRecomendacion(proyectoBean.getRecomendacion(), permiteAdj));
			evaluacion.setEstado(EstadoEvaluacion.PEND_RESULT);
			Long idPresupuesto = null;
			if(proyectoBean.getProyectoPresupuesto()!=null) 
				idPresupuesto = proyectoBean.getProyectoPresupuesto().getId();
			evaluacion.setIdPresupuestoInicial(idPresupuesto);
			evaluacion.setIdPresupuesto(idPresupuesto);
			
			//Bitacora de la evaluacion
			BitacoraBean bitacora = new BitacoraBuilder().cargarEvaluacion(proyectoBean); 
			bitacora.setIdEvaluacion(evaluacion.getId());
			bitacora.setTema( "Alta de Evaluación de Paquete Nro. " +paqueteBean.getId());
			
			evaluacion.setBitacora(bitacora);

			evaluacionPaqueteDao.save(evaluacion);
		}
	}
}