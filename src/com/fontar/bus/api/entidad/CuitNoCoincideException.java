package com.fontar.bus.api.entidad;

/**
 * Excepción que se dispara al intentar obtener la entidad beneficiaria coincidente
 * cuando se provee un nombre que coincide exactamente con el de una entidad registrada
 * pero el numero de cuit dado no coincide.
 * @author llobeto
 *
 */
public class CuitNoCoincideException extends Exception {

	private static final long serialVersionUID = 1L;
}
