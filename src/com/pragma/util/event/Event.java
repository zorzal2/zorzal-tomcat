package com.pragma.util.event;

import java.util.IdentityHashMap;
import java.util.Map;

import com.pragma.util.exception.IllegalArgumentException;
/**
 * Representa un evento al que se pueden suscribir NotificationListeners.
 * Ante una ocurrencia del evento, los listeners reciben una Notification,
 * que representa la ocurrencia particular del evento. No se puede asumir
 * ningún orden en el envío de notificaciones. Los objetos Event son thread-
 * safe.
 * @author llobeto
 *
 */
public class Event<TipoDeNotificacion extends Notification> {
	private Map<NotificationListener<TipoDeNotificacion>, NotificationListener<TipoDeNotificacion>> listeners;
	/**
	 * Suscribe al listener dado al evento. El listener sera notificado
	 * exactamente una vez independientemente de la cantidad de veces
	 * que se suscriba, y las notificaciones se darán en un orden
	 * arbitrario.
	 */
	public synchronized void subscribe(NotificationListener<TipoDeNotificacion> listener) {
		if(listeners==null) listeners = new IdentityHashMap<NotificationListener<TipoDeNotificacion>, NotificationListener<TipoDeNotificacion>>();
		if(listener==null) throw new IllegalArgumentException("Listener null!");
		listeners.put(listener, listener);
	}
	/**
	 * Retira la suscripcion al evento del listener dado. Si el listener no estaba
	 * suscripto no hace nada. Aunque el metodo subscribe se habia llamado varias 
	 * veces con el mismo listener, basta una sola invocacion a este metodo para
	 * dessuscribir al objeto. 
	 * @param listener
	 */
	public synchronized void unSubscribe(NotificationListener<TipoDeNotificacion> listener) {
		if(listeners==null || listener==null) return;
		listeners.remove(listener);
	}
	/**
	 * Notifica la ocurrencia de un evento. Todos los listeners son notificados
	 * exactamente una vez en algun orden no especificado. Todos reciben el mismo
	 * objeto Notification. Esto puede asumirse para, por ejemplo, agregar un valor
	 * de retorno a la notificacion que permita consultar a los listeners. Una
	 * notificacion nula es tolerada.
	 * @param notification
	 */
	public synchronized TipoDeNotificacion notifyEvent(TipoDeNotificacion notification) {
		if(listeners==null) return notification;
		for(NotificationListener<TipoDeNotificacion> listener : listeners.keySet()) {
			listener.listen(notification);
		}
		return notification;
	}
	/**
	 * Crea una nueva instancia de evento. Solo para acortar codigo. Por
	 * ejemplo, el codigo:<br>
	 * 		x = Event.create();               <br>
	 * hace exactamente lo mismo que:         <br>
	 * 		x = new Event&lt;CambioDeEstadoDeProyecto>();
	 * @param <T>
	 * @return
	 */
	public static <T extends Notification> Event<T> create() {
		return new Event<T>();
	}
}
