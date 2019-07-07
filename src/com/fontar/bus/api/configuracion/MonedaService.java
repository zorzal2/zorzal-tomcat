package com.fontar.bus.api.configuracion;

import java.util.List;

import com.fontar.data.impl.domain.dto.MonedaDTO;

/**
 *	Servicios para registrar las diferentes monedas utilizas en el sistema.   
 */
public interface MonedaService {

	public final Long PESO_ARGENTINO_ID = new Long(1);

	/**
	 * Define una nueva moneda.
	 * @param moneda
	 */
    public void create(MonedaDTO moneda);

    /**
     * Actualiza los datos de una moneda previamente existente.
     * @param moneda
     */
    public void update(MonedaDTO moneda);

    /**
     * Dado un identificador de moneda retorna los datos de la moneda.
     * @param id identificador de moneda
     * @return
     */
    public MonedaDTO load(Long id);

    /**
     * Devuelve una lista de todas las monedas registradas.
     * @return
     */
    public List<MonedaDTO> getAll();
    
    /**
     * Devuelve la moneda relacionada con Argentina.
     * @return
     */
    public MonedaDTO getPesoArgentino();
}
