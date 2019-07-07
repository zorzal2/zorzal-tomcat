package com.fontar.bus.api.proyecto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompletarDatosInicialesDTO;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.cripto.AccesoDenegadoException;

/**
 * Servicios para la administración de proyectos.
 */
public interface AdministrarProyectoServicio {

	/**
	 * Obtiene un dto desde un bean El dto es construido en base al assembler
	 * enviado por parametro
	 */
	public DTO getProyectoDTO(Long idProyecto, ProyectoGeneralAssembler assembler);
	
	/**
	 * A partir de un identificador de proyecto obtiene un DTO con los datos del proyecto.  
	 * @param idProyecto
	 * @return
	 */
	public ProyectoDTO obtenerProyecto(Long idProyecto);

	/**
	 * Obtiene el DAO para bitacoras.
	 * @return
	 */
	public abstract BitacoraDAO getBitacoraDAO();

	/**
	 * Define el DAO para bitacora.
	 * 
	 * @param bitacoraDAO
	 */
	public abstract void setBitacoraDAO(BitacoraDAO bitacoraDAO);

	/**
	 * Obtiene el DAO para proyectos
	 * @return
	 */
	public abstract ProyectoDAO getProyectoDAO();

	/**
	 * Define el DAO para proyectos.
	 * @param proyectoDAO
	 */
	public abstract void setProyectoDAO(ProyectoDAO proyectoDAO);

	
	/**
	 * Registra la alicuota para 
	 * @param idProyecto
	 * @param porcentaje
	 * @param observaciones
	 */
	public abstract void cargarAlicuota(Long idProyecto, BigDecimal porcentaje, String observaciones);

	public BigDecimal obtenerAlicuotaSolicitada(Long idProyecto);
	
	public abstract ProyectoPresupuestoDTO obtenerPresupuesto(Long idProyecto, Long idEvaluacion);

	public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idProyecto,
			Long idEvaluacion);

	public void finalizarPosibilidadReconsideracion(BitacoraDTO bitacoraDTO, Long idProyecto);

	public ProyectoAgregarDTO obtenerDatosAgregarProyecto(Long idProyecto);

//	public ProyectoDatosBean obtenerProyectoDatos(Long idProyecto);

	public void guardarDatosEdicionProyecto(ProyectoEdicionDTO datosDto);

	public ProyectoVisualizacionDTO obtenerDatosVisualizacionProyecto(Long idProyecto);

	/**
	 * Informa si el proyecto actualmente esta siendo tratado en un paquete que esta activo.
	 * @param idProyecto
	 * @return
	 */
	public Boolean enPaquete(Long idProyecto);

	/**
	 * Informa el estado actual del proyecto.
	 * @param idProyecto
	 * @return
	 */
	public Boolean obtenerEstadoProyecto(Long idProyecto);

	/**
	 * Registra un nuevo proyecto en funcion de los datos informatos.
	 * @param datos datos del proyecto.
	 * @param vieneDePresentacion registra si el proyecto esta relacionado con una presentacion a convocatoria 
	 * @param idInstrumento Define el instrumento de beneficio.
	 * @param idPresentacion En caso que vieneDePresentacion sea TRUE, en este dato se indica el identificador de presentacion a convocatoria.
	 * @param idProyectoPitec En caso que el proyecto este relacionado con un proyecto Pitec se indica en este parametro.
	 * @return identificador del nuevo proyecto
	 */
	public Long cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento, Long idPresentacion, Long idProyectoPitec);
	
	/**
	 * Permite cargar un nuevo Proyecto, ProyectoPitec o IdeaProyectoPitec.
	 * @param datos datos del proyecto
	 * @param vieneDePresentacion registra si el proyecto esta relacionado con una presentacion a convocatoria
	 * @param idInstrumento Define el instrumento de beneficio.
	 * @param idPresentacion En caso que vieneDePresentacion sea TRUE, en este dato se indica el identificador de presentacion a convocatoria.
	 * @param idProyectoPitec En caso que el proyecto este relacionado con un proyecto Pitec se indica en este parametro.
	 * @param presupuestableBean debe ser del tipo ProyectoBean, ProyectoPitecBean o IdeaProyectoPitecBean
	 * @return identificador del nuevo proyecto
	 */
	public Long cargarPresupuestable(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento, Long idPresentacion, Long idProyectoPitec, ProyectoBean presupuestableBean);

	/**
	 * Devuelve un DTO con los datos para mostrar la cabecera en funcion de un identificador de presentacion de comvocatira. 
	 * @param idPresentacion
	 * @return
	 */
	public PresentacionCabeceraDTO obtenerDatosCabeceraProyecto(Long idPresentacion);

	/**
	 * Devuelve el monto Solicitado por el beneficiario
	 * @param idProyecto
	 * @return
	 */
	public BigDecimal obtenerMontoSolicitadoProyecto(Long idProyecto);
	
	/**
	 * Registra que se concreto la Firma de Contrato del proyecto.
	 * @param idResponsableLegal
	 * @param txtResponsableLegal
	 * @param fechaFirma
	 * @param observaciones
	 * @param idProyecto
	 */
	public void guardarFirmaContrato(Long idResponsableLegal, String txtResponsableLegal, Date fechaFirma, String observaciones, Long idProyecto);

	/**
	 * Registra un movimiento fisico del expediente del proyecto.
	 * @param fecha
	 * @param ubicacion
	 * @param fechaDevolucion
	 * @param observaciones
	 * @param idPersona
	 * @param idProyecto
	 */
	public void guardarMovimiento(Date fecha, String ubicacion, Date fechaDevolucion, String observaciones, Long idPersona, Long idProyecto);

	/**
	 * Registra cambios en la estructura fisica del expediente del proyecto.
	 * @param cuerpo
	 * @param folioDesde
	 * @param folioHasta
	 * @param idProyecto
	 */
	public void guardarExpediente(String[] cuerpo, String[] folioDesde, String[] folioHasta, Long idProyecto);

	/**
	 * Retorna la informacion de movimientos del expediente fisico.
	 * @param idProyecto identificador del proyecto.
	 * @return
	 */
	public ExpedienteMovimientoDTO obtenerExpedienteMov(Long idProyecto);

	/**
	 * Permite modificar el tipo del proyecto. 
	 * @param idProyecto identificador del proyecto.
	 * @param proyectoTipo nuevo tipo de proyecto.
	 */
	public void cargarCriterio(Long idProyecto, String proyectoTipo);

	/**
	 * Informa el tipo de un proyecto.
	 * @param id identificador del proyecto.
	 * @return
	 */
	public String obtenerTipoProyecto(String id);

	/**
	 * Este método es para la actualización de la recomendación final de los proyectos ya cargados (se corrió una única vez en fontar). 
	 */
	public void modificarRecomendaciones();
	
	/**
	 * Registra el cierra un proyecto por finalizacion normal del mismo. 
	 * @param idProyecto
	 * @param observacion
	 */
	public void finalizarProyecto(Long idProyecto, String observacion);
	
	/**
	 * Devuelve el tipo de presupuesto que corresponde al instrumento de beneficio para el identificador de proyecto informado
	 * @param idProyecto
	 * @return
	 */
	public String getTipoMatrizPresupuesto(Long idProyecto);
	
	/**
	 * Obtiene del <code>ProyectoBean</code> en
	 * base al <i>idProyecto</i>.<br>
	 * @param idProyecto
	 * @return el <code>ProyectoBean</code>
	 */
	public ProyectoBean obtenerProyectoBean(Long idProyecto);
	
	/**
	 * Persiste los datos ingresados en el
	 * proyecto histórico correspondiente
	 * al <i>idProyecto</i>.<br>
	 * @param idProyecto
	 * @param datosInicialesDTO
	 */
	public void completarDatosIniciales(Long idProyecto, Long idSeguimiento, CompletarDatosInicialesDTO datosInicialesDTO);

	/**
	 * Devuelve la recomendacion del proyecto, null si no tiene una recomendacion ingresada
	 * o dispara una SecurityException si no hay contexto de encriptacion.
	 * @param idProyecto
	 * @return
	 * @throws SecurityException
	 * @throws AccesoDenegadoException 
	 */
	public Recomendacion obtenerRecomendacionDeProyecto(Long idProyecto) throws SecurityException, AccesoDenegadoException;
	/**
	 * Devuelve la recomendacion del proyecto como un objeto encriptado, siempre y cuando el usuario
	 * actual tenga privilegios que lo habiliten a verla. En otro caso devuelven null.
	 * @return
	 */
	public EncryptedObject getRecomendacionSiEsAccesible(ProyectoBean proyecto);
}



