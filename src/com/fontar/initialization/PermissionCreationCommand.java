package com.fontar.initialization;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ldap.EntryNotFoundException;

import com.fontar.data.impl.dao.ldap.PermisoDao;
import com.fontar.data.impl.domain.ldap.Permiso;

public class PermissionCreationCommand  extends InitializationCommand {

	private PermisoDao permisoDao;
	
	private Map<String, List<String> > permissionMap;
	
	@Override
	public void execute() {
		//Da de alta/actualiza en el LDAP los permisos   
		for (String moduleName: this.getPermissionMap().keySet()) {
			List<String> permissions = this.getPermissionMap().get(moduleName); 
			for (String permission: permissions) {
				this.addPermission(moduleName,permission);
			}
		}
		
		//Descarta en el LDAP los permisos que no aparecen en la definición
		List<Permiso> permisosLdap = this.permisoDao.findAll();
		for (Permiso permisoLdap : permisosLdap) {
			if (!this.PermissionMapContains(permisoLdap)) {
				try{
					this.getLogger().info("Permiso @" + permisoLdap.getIdPermiso() + " en Ldap borrado");
					this.permisoDao.delete(permisoLdap);
				}catch(DataIntegrityViolationException dataIntegrityViolationException){
					this.getLogger().error(dataIntegrityViolationException);	
				}
			}
		}
	}

	private Boolean PermissionMapContains(Permiso permiso) {
		
		if (!this.getPermissionMap().containsKey(permiso.getModulo())) {
			return false;
		}
		else {
			List<String> permissionsModule = this.getPermissionMap().get(permiso.getModulo());
			return permissionsModule.contains(permiso.getIdPermiso());
		}
	}
	
	private void addPermission(String moduleName, String idPermission){
		Permiso permiso = null;
		try{
			permiso = this.permisoDao.findByPrimaryKey(idPermission);
			this.getLogger().info("Permiso @" + idPermission + " encontrado");
			if (!permiso.getModulo().equals(moduleName) ) {
				permiso.setModulo(moduleName);
				this.permisoDao.update(permiso);
				this.getLogger().info("Permiso @" + idPermission + " actualizado");
			}
		}catch (EntryNotFoundException e) {
			permiso = new Permiso();
			permiso.setModulo(moduleName);
			permiso.setIdPermiso(idPermission);
			try{
				this.permisoDao.create(permiso);
				this.getLogger().info("Permiso @" + idPermission + " creado");
			}catch(DataIntegrityViolationException dataIntegrityViolationException){
				this.getLogger().error(dataIntegrityViolationException);
			}
		}
	}

	@Override
	public void initializeImpl() {

	}

	public PermisoDao getPermisoDao() {
		return permisoDao;
	}

	public void setPermisoDao(PermisoDao permisoDao) {
		this.permisoDao = permisoDao;
	}

	public Map<String, List<String>> getPermissionMap() {
		return permissionMap;
	}

	public void setPermissionMap(Map<String, List<String>> permissionMap) {
		this.permissionMap = permissionMap;
	}


	
	
	
}
