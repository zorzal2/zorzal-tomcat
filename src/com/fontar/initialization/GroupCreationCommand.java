package com.fontar.initialization;

import com.fontar.bus.api.configuracion.GrupoService;

public class GroupCreationCommand extends InitializationCommand {

	protected static String DEFAULT_GROUP = "ADMINISTRACION DE SEGURIDAD";
	
	private GrupoService grupoService;
	
	
	private String[] permissions;

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	@Override
	public void execute() {
		if(this.grupoService.findByName(DEFAULT_GROUP)==null){
			this.grupoService.create(DEFAULT_GROUP,permissions);
		}
	}

	@Override
	public void initializeImpl() {
		this.permissions = new String[]{"USUARIOS-AGREGAR","USUARIOS-EDITAR","USUARIOS-INVENTARIO",
										"GRUPOS-AGREGAR" , "GRUPOS-EDITAR","GRUPOS-INVENTARIO" };	
	}

}
