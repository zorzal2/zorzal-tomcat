/**
 * 
 */
package com.fontar.bus.api.seguridad;

import com.fontar.data.api.domain.Workflowable;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.exception.IllegalArgumentException;

public class PermissionDescriptorImpl extends BasePermissionDescriptor {
	
	Long objectId;
	Class objectClass;
	String accionSobreObjeto;
	Long idProcessInstance;
	
	public PermissionDescriptorImpl(Object object, String accion) {
		objectId = BeanUtils.getId(object);
		if(objectId==null) throw new IllegalArgumentException("El id del objeto es nulo");
		if(object instanceof Workflowable) {
			idProcessInstance = ((Workflowable)object).getIdWorkFlow();
		}
		objectClass = object.getClass();
		accionSobreObjeto = accion;
	}
	public PermissionDescriptorImpl(Class clazz, Long id, String accion, Long idProcessInstance) {
		objectId = id;
		if(objectId==null) throw new IllegalArgumentException("El id del objeto es nulo");
		objectClass = clazz;
		accionSobreObjeto = accion;
		this.idProcessInstance = idProcessInstance;
	}
	public Class getObjectClass() {
		return objectClass;
	}

	public Long getObjectId() {
		return objectId;
	}
	
	public String getAccionSobreObjeto() {
		return accionSobreObjeto;
	}
	public Long getIdProcessInstance() {
		return idProcessInstance;
	}
	public void setIdProcessInstance(Long idProcessInstance) {
		this.idProcessInstance = idProcessInstance;
	}
}