/**
 * 
 */
package com.fontar.bus.api.seguridad;

import java.util.Arrays;

public abstract class BasePermissionDescriptor implements PermissionDescriptor {
	public int hashCode() {
		return Arrays.hashCode(new Object[] {getAccionSobreObjeto(), getObjectClass(), getObjectId()});
	}
	public boolean equals(Object other) {
		if(other instanceof PermissionDescriptor) {
			PermissionDescriptor descriptor = (PermissionDescriptor) other;
			return 
				this.getAccionSobreObjeto().equals(descriptor.getAccionSobreObjeto()) &&
				this.getObjectClass().equals(descriptor.getObjectClass()) &&
				this.getObjectId().equals(descriptor.getObjectId());
		} else return false;
	}
}