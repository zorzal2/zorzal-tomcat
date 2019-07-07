package com.fontar.seguridad;

public interface SecurityConfigInterceptor {

	public void update(UserUpdateEvent userUpdateEvent);

	public void update(GroupUpdateEvent updateEvent);
}