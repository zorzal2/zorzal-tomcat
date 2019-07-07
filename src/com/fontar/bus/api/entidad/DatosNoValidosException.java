package com.fontar.bus.api.entidad;

/**
 * Excepción que se dispara al intentar obtener la entidad beneficiaria coincidente
 * cuando se proveen datos que no permiten identificar una persona pero tampoco crear
 * una nueva. Por ejemplo, si coincide el numero de CUIT pero no el nombre en caso de
 * que se use el flag COINCIDENCIA_ESTRICTA, no se puede crear una entidad con el mismo
 * numero de cuit que una existente.
 * @author llobeto
 *
 */
public class DatosNoValidosException extends Exception {

	private static final long serialVersionUID = 1L;
}
