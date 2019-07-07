package com.fontar.bus.api.workflow;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompletarDatosInicialesDTO;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.cripto.AccesoDenegadoException;

public interface WFProyectoServicio {

	public void pasarAProximaEtapaSinEvaluacion(String fundamentacion, Long idTaskInstance);

	public void finalizarControlEvaluacion(ProyectoPresupuestoDTO presupuesto, EvaluacionGeneralDTO evaluacion, Long idProyecto,
			ResultadoEvaluacion resultado, Long idTaskInstance);

	public void cargarAlicuota(BigDecimal porcentaje, String observaciones, Long idTaskInstance);

	public ProyectoPresupuestoDTO obtenerPresupuesto(Long idTaskInstance);

	public ProyectoDTO obtenerProyecto(Long idTaskInstance);

	public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idTaskInstance);

	public void finalizarPosibilidadReconsideracion(BitacoraDTO bitacora, Long idTaskInstance);

	public EstadoProyecto obtenerEstadoProyecto(Long idProyecto);

	public Recomendacion obtenerRecomendacionProyecto(Long idProyecto) throws SecurityException, AccesoDenegadoException;

	public void cargarAdmisionAlProyecto( java.util.Date fecha, String fundamentacion,
			String disposicion, String resultado, String observacion, Long idTaskInstance);

	public void solicitarReadmisionAlProyecto(java.util.Date fecha, String observacion, Long idTaskInstance);

	public Boolean enPaquete(Long idProyecto);

	public DTO getProyectoDTO(Long idTaskInstance, ProyectoGeneralAssembler assembler);

	public void cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento,
			Long idPresentacion, Long idProyectoPitec);

	public void crearInstanciaWF(Long idProyecto, boolean vieneDePresentacion);

	public ProyectoAgregarDTO obtenerDatosAgregarProyecto(Long idProyecto);

	public ProyectoVisualizacionDTO obtenerDatosVisualizacionProyecto(Long idProyecto);

	public void guardarDatosEdicionProyecto(ProyectoEdicionDTO datosDto);

	public PresentacionCabeceraDTO obtenerDatosCabeceraProyecto(Long idPresentacion);

	public void cargarReadmisionAlProyecto(Date fecha, String fundamentacion, String resultado, String resolucion, String observacion, Long idTaskInstance);

	public BigDecimal obtenerMontoSolicitadoProyecto(Long idTaskInstance);

	public void guardarFirmaContrato(Long idResponsableLegal, String txtResponsableLegal, Date fechaFirma, String observaciones, Long idTaskInstance);

	public void analizarReconsideracionAlProyecto(Date fecha, String fundamentacion, String resultado, String resolucion, String observacion, Long idTaskInstance, String dictamen);

	public void reconsiderarProyecto(Date fecha, String paso, String observacion, Long idTaskInstance);

	public void guardarMovimiento(Date fecha, String ubicacion, Date fechaDevolucion, String observaciones, Long idPersona, Long idProyecto);

	public void guardarExpediente(String[] cuerpo, String[] folioDesde, String[] folioHasta, Long idProyecto);

	public ExpedienteMovimientoDTO obtenerDatosExpedienteMov(Long idProyecto);

	public void actualizarPresupuestoActual(Long proyectoId, ProyectoPresupuestoDTO presupuesto);
	
	public void finalizarProyecto(Long idTaskInstance, String observacion);
	
	/**
	 * Obtiene del <code>ProyectoBean</code> en
	 * base al <i>idTaskInstance</i>.<br>
	 * @param idTaskInstance
	 * @return el <code>ProyectoBean</code>
	 */
	public ProyectoBean obtenerProyectoBean(Long idTaskInstance);
	
	/**
	 * Ejecuta la acción <i>Completar Datos Iniciales</i>
	 * del workflow de <code>ProyectoHistorico</code>.<br>
	 * Persiste los datos ingresados y patea el token a la siguiente
	 * tarea de workflow.<br>
	 * @param idTaskInstance
	 * @param datosInicialesDTO
	 */
	public void completarDatosIniciales(Long idTaskInstance, CompletarDatosInicialesDTO datosInicialesDTO);
	/**
	 * Devuelve un DTO con los montos solicitados y aprobados previstos al
	 * finalizar el control de las evaluaciones del seguimiento.
	 * @param idTaskInstance
	 * @return
	 */
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuestoParaFinalizarControl(Long idTaskInstance);
}