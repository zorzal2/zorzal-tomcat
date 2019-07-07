package com.pragma.util.event;
/**
 * Representacion de una ocurrencia particular de un evento. Este objeto
 * solo debe usarse en el contexto de la recepcion de una notificacion y
 * no debe almacenarse.
 * @author llobeto
 *
 */
public interface Notification {
	public Event<? extends Notification> event();
}
