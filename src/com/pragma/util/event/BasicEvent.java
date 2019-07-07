package com.pragma.util.event;


public class BasicEvent extends Event<Notification> {
	/**
	 * Notificacion simple basada en una notificacion con interfaz minima.
	 */
	public Notification notifyEvent() {
		return super.notifyEvent(createNotification());
	}
	/**
	 * Crea un objeto de notificacion minimo que cumple con el contrato de
	 * Notification.
	 * @return
	 */
	public Notification createNotification() {
		return new VeryBasicNotification(this);
	}
	private static class VeryBasicNotification implements Notification {
		private Event<? extends Notification> event;
		public VeryBasicNotification(Event<? extends Notification> event) {
			this.event = event;
		}
		public Event<? extends Notification> event() {
			return event;
		}
	}
}
