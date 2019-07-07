package com.fontar.bus.api.configuracion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.dto.CotizacionDTO;
import com.fontar.data.impl.domain.dto.MonedaDTO;

/**
 * Servicios para registrar las cotizaciones entre las diferentes monedas 
 * y realizar conversiones de importes entre las mismas.   
 *
 */
public interface CotizacionService {

    /**
     * Registra una nueva cotización dentro del sistema. 
     * @param cotizacion
     */
	public void create(CotizacionDTO cotizacion);

	/**
	 * Actualiza una cotización previamente definida en el sistema. 
	 * @param cotizacion
	 */
    public void update(CotizacionDTO cotizacion);
    
    /**
     * Se utliza para anular el registro de una cotización existente en el sistema.
     * @param id
     */
    public void delete(Long id);

    /**
     * Obtiene los datos para una cotización registrada en del sistema.
     * @param id
     * @return
     */
    public CotizacionDTO load(Long id);

    /**
     * Obtiene todas las cotizaciones registradas en el sistema.
     * @return
     */
    public List<CotizacionDTO> getAll();
    
    /**
     * Obtiene todas las cotizaciones registradas en el sistema para una cierta moneda. 
     * @param idMoneda
     * @return
     */
    public List<CotizacionDTO> getAllByMoneda(Long idMoneda);
    
    /**
     * Obtiene todas las moenedas para las cuales se registró alguna cotización.
     * @return
     */
    public List<MonedaDTO> getMonedasCotizables();
    
    /**
     * Calcula la cotización en <b>idMonedaDestino</b> para un <b>monto</b> 
     * que corresponde a <b>idMonedaOrigen</b>.      
     * @param monto importe origen
     * @param idMonedaOrigen moneda del importe
     * @param idMonedaDestino moneda a la cual se desea convertir el importe
     * @param fecha fecha de referencia para la cotización
     * @return importe cotizado en la moneda destino  
     * @throws FaltanCotizacionesException
     */
    public BigDecimal convertir(BigDecimal monto, Long idMonedaOrigen, Long idMonedaDestino, Date fecha) throws FaltanCotizacionesException;
}
