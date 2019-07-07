package com.fontar.bus.api.seguimientos.seguimientos;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fontar.bus.impl.seguimientos.seguimientos.RendicionesExcelParsingException;
import com.fontar.bus.impl.seguimientos.seguimientos.RendicionesException;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ControlFacturasDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionesDTO;
import com.fontar.data.impl.domain.dto.SeguimientoGestionPagoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.pragma.excel.exception.ParsingException;

/**
 * Servicio para la administraci�n de seguimientos
 */
public interface AdministrarSeguimientoServicio {

	public Collection<RendicionCuentasDTO> parseArchivo(ArchivoDTO dto, Long seguimientoId, boolean borrarExistentes) throws ParsingException, RendicionesExcelParsingException;
	
	public ProyectoCabeceraDTO obtenerDatosCabeceraSeguimientoAlta(Long idProyecto);
	public SeguimientoVisualizacionCabeceraDTO obtenerDatosCabeceraSeguimientoVisualizacion(Long idSeguimiento);
	
	public SeguimientoBean obtenerSeguimiento(Long idSeguimiento);
	public RendicionCuentasBean obtenerRendicionCuentas(Long idRendicion);
	
	public List<ResumenDeRendicionesDTO> obtenerResumenRendiciones(Long idSeguimiento);
		
	public Long cargarSeguimiento(Long idProyecto, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones);	
	public void modificarSeguimiento(String id, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones);

	public void anularSeguimiento(Long idSeguimiento, String observacion);
	
	public void eliminarRendicion(Long idRendicion);
	/**
	 * Servicio para usar internamente en la capa de servicios para asociar
	 * una evaluacion de seguimiento a un seguimiento.
	 * @param evaluacion
	 * @param idSeguimiento
	 * @return
	 */
	public EvaluacionSeguimientoBean cargarEvaluacionASeguimiento(EvaluacionSeguimientoDTO evaluacion, Long idSeguimiento);
	public void finalizarControlEvaluacion(EvaluacionSeguimientoDTO evaluacion, ResultadoEvaluacion resultado);

	public List<ControlFacturasDTO> obtenerFacturasRepetidas(Long idSeguimiento);

	public void cargarRendicion(RendicionCuentasBean rendicionBean);	
		
	public void cargarEtapas(String id, String avance, String observaciones);
	public void cargarActividades(String id, String avance, String observaciones);
	public List<String> getEvaluacionesAbiertas(Long idSeguimiento);
	public Collection getEvaluaciones(SeguimientoBean seguimientoBean, String finalizar_control);
	public void autorizarPago(Long idSeguimiento, boolean autorizada, EvaluacionSeguimientoDTO evaluacion);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * es de tipo <code>Finaciero.</code><br>
	 */
	public Boolean esFinanciero(Long idSeguimiento);
	/**
	 * Devuelve si el instrumento del proyecto es de cr�dito fiscal y requiere la carga de una
	 * al�cuota.  
	 * @param idSeguimiento
	 * @return
	 */
	public Boolean esDeInstrumentoConAlicuotaCF(Long idSeguimiento);
	
	/**
	 * Cierra el <code>Seguimiento</code> identificado mediante
	 * <i>idSeguimiento</i> y guarda la observaci�n ingresada.<br>
	 * @author ssanchez
	 */
	public void cerrarSeguimiento(Long idSeguimiento, String observacion);
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas y son de tipo t�cnica.
	 * @param idSeguimiento
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesTecnicasAbiertas(Long idSeguimiento);
	
	/**
	 * Obtiene una lista de Evaluaciones para un 
	 * Seguimiento que estan abiertas y son de tipo contable.
	 * @param idSeguimiento
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesContablesAbiertas(Long idSeguimiento);
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas.
	 * @param idSeguimiento
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesAbiertas(Long idSeguimiento);
	
	/**
	 * Obtiene todas las Evaluaciones de un
	 * Seguimiento que coninciden con el <i>estado</i>.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesPorEstado(Long idSeguimiento, EstadoEvaluacion estado);
	
	/**
	 * Verifica si el Seguimiento tiene al menos
	 * una Evaluaci�n T�cnica y una Contable en
	 * estado confirmada.
	 * @param idSeguimento
	 * @return Boolean
	 * @author ssanchez
	 */
	public Boolean tieneEvaluacionesTecnicaYContableConfirmadas(Long idSeguimento);
	
	/**
	 * Obtiene las rendiciones de un seguimiento
	 * y actualiza las de gesti�n con las
	 * aprobadas (de evaluaci�n).
	 * @param idSeguimiento
	 * @author ssanchez
	 */
	public void cargarRendicionesDeGestionConAprobadas(Long idSeguimiento);
	
	/**
	 * Obtiene datos resumidos de las rendiciones para mostrar al momento de
	 * evaluar la gesti�n de pago.
	 * de un seguimiento.<br>
	 * @param idSeguimiento
	 * @return total de montos aprobado
	 */
	public SeguimientoGestionPagoDTO obtenerTotalesRendicionesParaEvaluarGestionDePago(Long idSeguimiento);
	/**
	 * Obtiene datos resumidos de las rendiciones para mostrar al confirmar la 
	 * evaluaci�n de seguimiento.
	 * @param idEvaluacion
	 * @return
	 */
	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(Long idEvaluacion);
	/**
	 * Obtiene datos resumidos de las rendiciones para mostrar al finalizar el
	 * control de un seguimiento. 
	 * @param idSeguimiento
	 * @return
	 */
	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(Long idSeguimiento);

	/**
	 * Modifica el estado del seguimiento seg�n 
	 * el parametro <i>estado</i> y
	 * persiste la observaci�n.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @param observaci�n
	 * @author ssanchez
	 */
	public void cargarGestionPago(Long idSeguimiento, EstadoSeguimiento estado, String observacion);
	/**
	 * Carga los datos del map a la rendicion y la guarda.
	 * @param rendicionBean
	 * @param tipoRendicion
	 * @param map 
	 */
	public void cargarRendicionDesdeMap(TipoRendicion tipoRendicion, Map<String, Object> map);
	
	public boolean getTieneRendiciones(Long idseguimiento);
	
	/**
	 * Modifica el estado del <code>Seguimento</code> a
	 * <i>Gestionado</i> y guarda en bitacora la transacci�n.<br>
	 * Este m�todo es usado en la carga de proyectos hist�ricos
	 * para finalizar el seguimiento.<br>
	 * @param idSeguimiento
	 */
	public void finalizarGestionarSeguimiento(Long idSeguimiento);		
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluaci�n <i>t�cnica</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionTecnicaAbierta(Long idSeguimiento);

	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluaci�n <i>visita t�cnica</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionVisitaTecnicaAbierta(Long idSeguimiento);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluaci�n <i>contable</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionContableAbierta(Long idSeguimiento);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluaci�n <i>auditor�a contable</i> abierta.
	 * @param idSeguimiento
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionAuditoriaContableAbierta(Long idSeguimiento);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code> 
	 * tiene <code>EvaluacionesSeguimiento</code> contables confirmadas.<br>
	 * @param idSeguimiento
	 * @return true o false
	 */
	public Boolean tieneEvaluacionesContablesConfirmadas(Long idSeguimiento);
	/**
	 * Determina si el seguimiento con el id dado tiene evaluaciones confirmadas.
	 * @param idSeguimiento
	 * @return
	 */
	public Boolean tieneEvaluacionesConfirmadas(Long idSeguimiento);
	/**
	 * Determina si el seguimiento con el id dado permite que se le modifiquen
	 * los montos solicitados. Esto es cuando el seguimiento esta iniciado o
	 * evaluado pero sin evaluaciones contables confirmadas.
	 * @param idSeguimiento
	 * @return
	 */
	public Boolean permiteEdicionDeMontosSolicitados(Long idSeguimiento);
	/**
	 * Determina si el seguimiento est� en condiciones de que se le agreguen
	 * nuevas rendiciones de cuentas.
	 * @param idSeguimiento
	 * @return
	 */
	public Boolean permiteAgregarOQuitarRendiciones(Long idSeguimiento);
	
	public Long getIdProyectoDeSeguimiento(Long idSeguimiento);
	/**
	 * Guarda los montos de gesti�n en una rendici�n.
	 * @param idRendicion
	 * @param montoParteGestion
	 * @param montoContraparteGestion
	 * @param montoTotalGestion
	 * @param observaciones
	 * @throws RendicionesException Si los montos no son v�lidos.
	 */
	public void guardarRendicionEnEvaluacionDeGestionDePago(
			Long idRendicion, 
			BigDecimal montoParteGestion, 
			BigDecimal montoContraparteGestion,
			BigDecimal montoTotalGestion,
			String observaciones) throws RendicionesException;
	
	/**
	 * Actualiza el monto del presupuesto segun informe de avance.
	 * @param idSeguimiento
	 * @param monto
	 */
	public void guardarPresupuestoSegunAvance(Long idSeguimiento, BigDecimal monto);
	/**
	 * Actualiza el monto pendiente de rendicion.
	 * @param idSeguimiento
	 * @param monto
	 */
	public void guardarPendienteDeRendicion(Long idSeguimiento, BigDecimal monto);
}
