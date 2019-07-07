package com.fontar.web.action.configuracion.seguridad;

import java.util.Collection;

import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.data.impl.domain.ldap.UserStatus;

public class UsuarioDTO extends BasicUsuarioDTO   {
	private PersonaDTO persona;
	private Collection<VisualizarGrupoDTO> grupos;
	private UserStatus status;

	public Collection<VisualizarGrupoDTO> getGrupos() {
		return this.grupos;
	}

	public UserStatus getStatus() {
		return status;
	}

	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	public void setGrupos(Collection<VisualizarGrupoDTO> grupos) {
		this.grupos = grupos;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
}
