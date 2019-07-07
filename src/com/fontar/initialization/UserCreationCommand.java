package com.fontar.initialization;

import java.util.HashSet;
import java.util.Set;

import com.fontar.bus.api.configuracion.GrupoService;
import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.Usuario;

public class UserCreationCommand extends InitializationCommand {

	private UsuarioService usuarioService;
	
	private GrupoService grupoService;
	
	private Set<GrupoAbstracto> groups;

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
	

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	@Override
	public void execute() {
		Usuario usuario = this.usuarioService.findByPrimaryKey("administrador");
		if(usuario==null)
			this.usuarioService.create("Aministrador", "Fontar","administrador","font4r",this.groups, false, null, null);
	}

	@Override
	public void initializeImpl() {
		GrupoAbstracto grupo = this.grupoService.findByName(GroupCreationCommand.DEFAULT_GROUP);
		this.groups = new HashSet<GrupoAbstracto>();
		this.groups.add( grupo );
	}

}
