package com.fontar.bus.api.proyecto.desembolso;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;

/**
 *	Servicio para la administración de los desembolsos de un proyecto.
 *	Permite, entre otros, definir un desembolso planificado, autorizarlo, pagarlo.    
 */
public interface ProyectoDesembolsoService {

	/**
	 * Crea un nuevo item de desembolso. El monto del ítem sumado a los
	 * anteriores no debe superar el benficio del presupuesto. Si no hay
	 * ítems previos (sea porque este es el primero o porque se eliminaron
	 * los anteriores) y si no se especifica lo contrario, asume que el item
	 * es un anticipo. Si no, asume que el item no es anticipo.
	 * @param desembolso
	 * @throws MontoPrevistoSuperaMontoDelBeneficio
	 * @throws MontoAutorizadoSuperaMontoDelBeneficio
	 * @throws MontoDesembolsadoSuperaMontoDelBeneficio
	 */
    public void create(ProyectoDesembolsoDTO desembolso) throws MontoPrevistoSuperaMontoDelBeneficio, MontoAutorizadoSuperaMontoDelBeneficio, MontoDesembolsadoSuperaMontoDelBeneficio;
    /**
     * Actualiza los campos no nulos del desembolso. Chequea que el monto vigente
     * que queda sea coherente con el beneficio del presupuesto.
     * @param desembolso
     * @throws MontoPrevistoSuperaMontoDelBeneficio
     * @throws MontoAutorizadoSuperaMontoDelBeneficio
     * @throws MontoDesembolsadoSuperaMontoDelBeneficio
     */
    public void update(ProyectoDesembolsoDTO desembolso) throws MontoPrevistoSuperaMontoDelBeneficio, MontoAutorizadoSuperaMontoDelBeneficio, MontoDesembolsadoSuperaMontoDelBeneficio;
	/**
	 * Registra el pago de un anticipo. La unica diferencia con los
	 * pagos normales es que no se realiza en el contexto de un
	 * seguimiento ni requiere autorización. Además, este pago cambia
	 * el estado del proyecto a EN_SEGUIMIENTO.
	 * @param idDesembolso
	 * @param monto
	 * @param fecha
	 * @throws MontoPrevistoSuperaMontoDelBeneficio
	 */
	public void pagarAnticipo(Long idDesembolso, BigDecimal monto, Date fecha)  throws MontoAutorizadoSuperaMontoDelBeneficio, MontoDesembolsadoSuperaMontoDelBeneficio;
	/**
	 * Autoriza el pago de cierta cantidad de dinero. Vincula la autorización
	 * al seguimiento dado. El ítem no debe ser anticipo y debe estar como
	 * NO PAGADO o AUTORIZADO (en este caso funciona como una edición).
	 * @param idDesembolso
	 * @param monto
	 * @throws MontoDesembolsadoSuperaMontoDelBeneficio
	 */
	public void autorizarPago(Long idDesembolso, BigDecimal monto, Long idSeguimientoDeAutorizacion) throws MontoAutorizadoSuperaMontoDelBeneficio;
	/**
	 * Registra el desembolso de cierta cantidad de dinero. El item debe
	 * estar en estado autorizado o pagado (en este último caso funciona
	 * como una edición) y no debe ser un anticipo.
	 * @param idDesembolso
	 * @param monto
	 * @param fecha
	 * @throws MontoDesembolsadoSuperaMontoDelBeneficio
	 */
    public void registrarPago(Long idDesembolso, BigDecimal monto, Date fecha) throws MontoDesembolsadoSuperaMontoDelBeneficio;

    public ProyectoDesembolsoDTO load(Long id);

    public List<ProyectoDesembolsoDTO> findByProyecto(Long idProyecto);

    /**
     * Devuelve el cronograma de desembolsos para todo el proyecto.
     * Este cronograma permite:
     * - Agregar elementos
     * - Editar los montos previstos
     * - Eliminar
     * - Pagar anticipo
     * @param idProyecto
     * @return
     */
    public CronogramaDeDesembolsosDTO obtenerCronogramaDelProyecto(Long idProyecto);
    /**
     * Devuelve el cronograma de desembolsos para mostrar en la vusualizacion de seguimiento.
     * Este cronograma permite 
     * - Editar los montos previstos
     * - Eliminar
     * - Pagar anticipo
     * @param idSeguimiento
     * @return
     */
    public CronogramaDeDesembolsosDTO obtenerCronogramaParaVisualizarSeguimiento(Long idSeguimiento);
    /**
     * Devuelve el cronograma de desembolsos para mostrar en la vusualizacion de evaluacion
     * de seguimiento.
     * Este cronograma no permite acciones. 
     * @param idEvaluacionSeguimiento
     * @return
     */
    public CronogramaDeDesembolsosDTO obtenerCronogramaParaVisualizarEvaluacionDeSeguimiento(Long idEvaluacionSeguimiento);
    /**
     * Devuelve el cronograma de desembolsos para mostrar en la carga de resultado
     * de evaluaciones de seguimiento.
     * Este cronograma permite 
     * - Autorizar desembolsos
     * @param idEvaluacionSeguimiento
     * @return
     */
    public CronogramaDeDesembolsosDTO obtenerCronogramaParaCargarResultadoDeEvaluacionDeSeguimiento(Long idEvaluacionSeguimiento);
    /**
     * Devuelve el cronograma de desembolsos para mostrar en la evaluacion de gestion
     * de pago (UFFA).
     * Este cronograma permite 
     * - Pagar items autorizados en este seguimiento que no sean anticipos
     * @param idSeguimiento
     * @return
     */
    public CronogramaDeDesembolsosDTO obtenerCronogramaParaEvaluarGestionDePago(Long idSeguimiento);
    
	public void delete(Long id);
}
