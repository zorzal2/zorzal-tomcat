package com.pragma.util.event;
/**
 * Interfaz que deben cumplir los objetos que requieran ser notificados sobre
 * un evento. Es responsabilidad del listener, en caso de estar suscripto a
 * varios eventos, determinar cual efectivamente ocurrio a partir del metodo
 * event() de la notificacion y actuar acorde.
 * @author llobeto
 *
 */
public interface NotificationListener<NotificationType> {
	public void listen(NotificationType notification);
}