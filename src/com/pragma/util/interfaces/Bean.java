package com.pragma.util.interfaces;

import java.io.Serializable;
/**
 * Los objetos Beans son clases de Java sin comportamiento que representan a los objetos del negocio. Tienen una correspondencia biunívoca con registros en tablas y generalmente tienen una propiedad por cada campo de su registro correspondiente. La comunicación entre los servicios y los DAOs se realiza mediante el intercambio de beans.
 */
public interface Bean extends Serializable {
	/**
	 * Identificador único del bean
	 * @return
	 */
	public Long getId();
}
