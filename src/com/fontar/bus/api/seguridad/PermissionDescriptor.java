/**
 * 
 */
package com.fontar.bus.api.seguridad;


public interface PermissionDescriptor {
	
	public Class getObjectClass();

	public Long getObjectId();
	
	public String getAccionSobreObjeto();
	
	/**
	 * Opcional. Disponible solo cuando el permiso es de workflow.
	 * @return
	 */
	public Long getIdProcessInstance();
}