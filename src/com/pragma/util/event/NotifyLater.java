package com.pragma.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda eventos y notificaciones para dispararlas luego todas juntas.
 * @author llobeto
 */
public class NotifyLater {
	private class LaterNotification<NotificationType extends Notification> {
		public final NotificationType notification;
		public final Event<NotificationType> event;

		public LaterNotification(NotificationType notification, Event<NotificationType> event) {
			this.notification = notification;
			this.event = event;
		}
		public void notifyEvent() {
			event.notifyEvent(notification);
		}
	}
	private List<LaterNotification<? extends Notification>> pendingNotifications = new ArrayList<LaterNotification<? extends Notification>>();
	
	public <N extends Notification> void addNotification(N notification, Event<N> event) {
		pendingNotifications.add(new LaterNotification<N>(notification, event));
	}
	public void notifyAllEvents() {
		for(LaterNotification<? extends Notification> pending : pendingNotifications) {
			pending.notifyEvent();
		}
	}
}
