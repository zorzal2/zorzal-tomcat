package com.fontar.data.impl.domain.bean.seguridad;

import com.fontar.bus.api.seguridad.BasePermissionDescriptor;
import com.fontar.bus.api.seguridad.PermissionDescriptor;

/**
 * Bean que representa la asignación a un usuario de un permiso sobre una 
 * instancia particular para un usuario.
 * 
 * @author llobeto
 * 
 */
public class SeguridadObjetoBean {

	private Long id;
	private String usuario;
	private String clase;
	private Long idObjeto;
	private String accion;
	private Long idProcessInstance;
	
	private SeguridadObjetoPermissionDescriptor descriptor = null;

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	private class SeguridadObjetoPermissionDescriptor extends BasePermissionDescriptor {
		public Class getObjectClass() {
			try {
				return Class.forName(clase);
			}
			catch (ClassNotFoundException e) {
				return null;
			}
		}
		public Long getObjectId() {return idObjeto;}
		public String getAccionSobreObjeto() {return accion;}
		public Long getIdProcessInstance() {
			return idProcessInstance;
		}
		
	}
	public PermissionDescriptor getPermissionDescriptor() {
		if(descriptor==null) descriptor = new SeguridadObjetoPermissionDescriptor();
		return descriptor;
	}

	public Long getIdProcessInstance() {
		return idProcessInstance;
	}

	public void setIdProcessInstance(Long idProcessInstance) {
		this.idProcessInstance = idProcessInstance;
	}
}
