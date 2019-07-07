package com.fontar.jbpm.handler.assigner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.bus.api.seguridad.PermissionDescriptor;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.data.impl.assembler.BasicUsuarioDTOAssembler;
import com.fontar.web.action.configuracion.seguridad.BasicUsuarioDTO;
import com.pragma.util.ContextUtil;

/**
 * Asigna los pooledActors a la tarea consultando al AuthorizationService y
 * tomando en cuenta la tabla de permisos por instancia. 
 * @author llobeto
 * 
 */
public abstract class PerInstanceActorAssigner extends ActorAssigner {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String[] getPooledActors(TaskInstance taskInstance) {

		PermissionDescriptor[] descriptors = involvedPermissions(taskInstance);
		if(descriptors==null) {
			//no aplican los permisos sobre instancias
			return super.getPooledActors(taskInstance);
		} else {
			Set<String> userIds;
			if(descriptors.length==0) {
				/*
				 * OJO: No se necesitan permisos especiales por objeto. 
				 * Esto NO SIGNIFICA que nadie pueda realizar la tarea,
				 * sino, por el contrario, TODOS LOS USUARIOS pueden hacerla.
				 */
				//Obtengo todos los usuarios
				UsuarioService usuarioService = (UsuarioService) ContextUtil.getBean("usuarioService");
				Collection<BasicUsuarioDTO> allUsers = usuarioService.findAll(BasicUsuarioDTOAssembler.getInstance());
				userIds = new HashSet<String>();
				for(BasicUsuarioDTO user : allUsers) userIds.add(user.getUserId());
				return userIds.toArray(new String[]{});
			} else {
				//Obtengo los usuarios que tienen los permisos adecuados
				//sobre los objetos involucrados en la tarea. Esto asume que 
				//no hay demasiados permisos involucrados (tipicamente uno solo)
				SeguridadObjetoServicio seguridadObjetoServicio = (SeguridadObjetoServicio) ContextUtil.getBean("seguridadObjetoServicio");
				userIds = null;
				for(PermissionDescriptor descriptor : descriptors) {
					if(userIds==null) {
						//Obtengo los usuarios con el permiso especificado 
						userIds = new HashSet<String>(seguridadObjetoServicio.usuariosAutorizados(descriptor));
					} else {
						//intersecto los usuarios que hubiera con los que tienen este permiso
						userIds.retainAll(seguridadObjetoServicio.usuariosAutorizados(descriptor));
					}
				}
				//Uno estos usuarios a los que tienen acceso garantizado por la superclase
				String[] superPooledActors = super.getPooledActors(taskInstance);
				if (superPooledActors!=null) {
					for(String userId : superPooledActors) {
						userIds.add(userId);
					}
				}
				return userIds.toArray(new String[]{});
			}
		}		
	}
	
	/**
	 * Devuelve descriptores de todos los permisos necesarios para realizar
	 * la tarea. Los actores asignados a la tarea seran aquellos que tengan
	 * cada uno de estos permisos. 
	 * Si este método devuelve una lista vacia se asume que NO se necesitan
	 * permisos especiales para realizar esta tarea y por lo tanto TODOS
	 * los usuarios podrán hacerla.
	 * Si el método devuelve null se asume que no aplican los permisos por
	 * instancia. En este caso no se hacen consultas sobre la tabla de
	 * seguridad por objeto y se le concede permisos solo a los usuarios que
	 * los tienen garantizados por el AuthorizationService. 
	 * @param taskInstance
	 * @return
	 */
	protected abstract PermissionDescriptor[] involvedPermissions(TaskInstance taskInstance);
}
