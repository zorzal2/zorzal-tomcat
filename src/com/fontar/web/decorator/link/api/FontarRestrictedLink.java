package com.fontar.web.decorator.link.api;


public interface FontarRestrictedLink extends AbstractLink {

	public abstract void setSimplePermissionsRequired(String... permissionRequired);

	public abstract void setPermissionsByInstrumentoRequired(Long idInstrumento, String... permissionRequired);
}