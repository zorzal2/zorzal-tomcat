package com.fontar.bus.api.entidad;

/**
 * Excepción que se dispara al intentar obtener la entidad beneficiaria coincidente
 * cuando se provee un numero de cuit que coincide con una entidad registrada pero
 * no coincide el nombre dado.
 * @author llobeto
 *
 */
public class NombreNoCoincideException extends Exception {

	private static final long serialVersionUID = 1L;
}
