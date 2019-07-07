package com.fontar.bus.api.proyecto.pac;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.impl.proyecto.pac.MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException;
import com.fontar.bus.impl.proyecto.pac.PacExcelParsingException;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.CuadroResumenPacDTO;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.fontar.data.impl.domain.dto.PacDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.pragma.excel.exception.ParsingException;


/**
 * El PAC define una planificación de adquisición de bienes y servicios informando los montos 
 * y las fechas estimadas y el tipo de procedimiento a utilizar para los proyecto de tipo ARAI.
 * Estos tipos de proyecto están obligados a realizar los procedimientos establecidos en las políticas de adquisiciones del BID.
 * Mediante estos servicios se realizar las tareas de negocio relacionadas con el PAC de un proyecto.
 * @see com.fontar.data.impl.domain.bean.PacBean
 */
public interface AdministrarPACServicio {

	public Collection<PacDTO> parseArchivo(ArchivoDTO dto, Long proyectoId) throws ParsingException, PacExcelParsingException;

	public PacDTO loadPac(Long id);

	/**
	 * Define un nuevo PAC
	 * @param pacDTO
	 */
	public void createPac(PacDTO pacDTO);

	/**
	 * Actualiza los datos de un PAC existente con los del DTO.
	 * @param pacDTO
	 * @param id
	 */
	public void actualizarPac(PacDTO pacDTO, Long id);

	/**
	 * Elimina un item del PAC dejando constancia del motivo.
	 * @param id
	 * @param observaciones
	 */
	public void anularBean(Long id, String observaciones);

	/**
	 * Obtiene un DTO para la visualización de un PAC determinado.
	 * @param id
	 * @return
	 * @throws FaltanCotizacionesException
	 */
	public VisualizarPacItemDTO obtenerDatosItemTabla(Long id) throws FaltanCotizacionesException;

	/**
	 * Obtiene todos los desembolsos asociados con un PAC determinado.
	 * @param idPac
	 * @return
	 */
	public Collection<DesembolsoUFFADTO> obtenerDesembolsos(Long idPac);
	/**
	 * Ingresa un pedido de desembolso. Si ya existe un desembolso con la misma cuota lo actualiza.
	 * En el caso de edicion, solo cuenta el booleano esUltimo si la cuota que se está modificando
	 * es la ultima. Si no, no es utilizado.
	 * @param dto
	 * @param esUltimo
	 * @throws FaltanCotizacionesException
	 */
	public void createOrUpdateDesembolso(DesembolsoUFFADTO dto, Boolean esUltimo) throws FaltanCotizacionesException;

	/**
	 * Actualiza la información de un desembolso relaciona con el PAC.
	 * @param dto
	 * @throws MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException
	 * @throws FaltanCotizacionesException
	 */
	public void updateDesembolso(DesembolsoUFFADTO dto) throws MontoDelPagoMayorAMontoDelPedidoDeDesembolsoException, FaltanCotizacionesException;
	/**
	 * Determina si se puede desembolsar la cantidad dada, asumiendo que esta en la moneda
	 * con el idMoneda dado. Tiene en cuenta la suma de lo desembolsado y asume como limite
	 * el monto de adjudicacion. Requiere la fecha de desembolso para calcular las cotizaciones.
	 * @param idPac
	 * @param monto
	 * @param idMoneda 
	 * @param fechaDeDesembolso
	 * @return
	 * @throws FaltanCotizacionesException
	 */
	public Boolean puedeDesembolsar(Long idPac, BigDecimal monto, Long idMoneda, Date fechaDeDesembolso) throws FaltanCotizacionesException;
	/**
	 * Determina si el desembolso de la cuota dada puede ser cambiado por el monto dado expresado
	 * en la moneda con el id idMoneda, sin que sea sobrepasado el monto de adjudicacion.
	 * Requiere la fecha de desembolso para calcular las cotizaciones. 
	 * @param idPac
	 * @param cuota
	 * @param monto
	 * @param idMoneda
	 * @param fechaDeDesembolso
	 * @return
	 * @throws FaltanCotizacionesException
	 */
	public Boolean puedeCambiarDesembolso(Long idPac, Integer cuota, BigDecimal monto, Long idMoneda, Date fechaDeDesembolso) throws FaltanCotizacionesException;
	
	/**
	 * Determina si ya existe una solicitud de desembolso en un PAC con un número determinado de cuota. 
	 * @param idPac
	 * @param cuota
	 * @return
	 */
	public Boolean existePedidoDeDesembolsoConEseNumeroDeCuota(Long idPac, Integer cuota);

	/**
	 * registra que el beneficiario ha recibido el bien o servicio solicitado correspondiente al item de PAC.   
	 * @param id item de PAC
	 */
	public void updatePatrimonio(String id);

	/**
	 * registra el motno de adjudicación del item del PAC.
	 * @param id item de PAC
	 * @param montoAdjudicacion monto de aadjudicacición
	 */
	public void updateMontoAdjudicacion(Long id, BigDecimal montoAdjudicacion);

	/**
	 * Obtiene los datos para confeccionar un cuadro resumen con la situación del PAC del propyecto.
	 * @param idProyecto
	 * @param bean 
	 * @return
	 */
	public CuadroResumenPacDTO obtenerDatosCuadroResumen(Long idProyecto, ProyectoBean bean);
	
	/**
	 * Determina si un item de pac es parte de un procedimiento activo.
	 * @param pacBeanId
	 * @return
	 */
	public boolean bloqueadoPorProcedimiento(Long pacBeanId);
	/**
	 * Determina si un item está en espera de resolucion por UFFA o BID y por lo tanto
	 * no puede anularse ni modificarse.
	 * @return
	 */
	public boolean enEsperaDeAprobacionExterna(Long pacBeanId);
	
	/**
	 * Determina el monto total pendiente de desembolso para un item de PAC. 
	 * @param bean item de PAC.
	 * @return monto expresado en la moneda de adjudicación del item.
	 * @throws FaltanCotizacionesException
	 */
	public BigDecimal getMontoPedidoPendienteDeDesembolsoEnMonedaDeAdjudicacion(PacBean bean) throws FaltanCotizacionesException;
}