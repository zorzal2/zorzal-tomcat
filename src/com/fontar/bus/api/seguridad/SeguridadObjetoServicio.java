package com.fontar.bus.api.seguridad;

import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.seguridad.SeguridadObjetoBean;
import com.pragma.util.event.Event;
import com.pragma.util.event.Notification;


/**
 * Servicio para administración de permisos asociados a entidades individuales.
 * Permite asignar privilegios a un usuario sobre un objeto específico en lugar
 * de involucrar a todos las entidades de una clase. La acción puede ser un texto 
 * cualquiera que identifique con la suficiente precisión una acción aplicable al
 * objeto. Por convención se utilizan como acciones nombres de permisos definidos
 * en initialization.xml. Por ejemplo WF-EVALUACION-PROYECTO-CARGAR-RESULTADO
 * 
 * @author llobeto
 */
public interface SeguridadObjetoServicio {
	/**
	 * Permite al usuario <code>userId</code> realizar la accion 
	 * <code>accion</code> sobre el objeto <code>bean</code>.
	 * El objeto dado debe tener la propiedad <code>id</code> y
	 * ésta debe tener un valor no nulo de tipo Long.
	 * @param userId
	 * @param accion
	 * @param bean
	 */
	public void permitir(String userId, String accion, Object bean);
	/**
	 * Elimina el permiso del usuario <code>userId</code> para realizar la 
	 * <code>accion</code> sobre el objeto <code>bean</code>.
	 * El objeto dado debe tener la propiedad <code>id</code> y
	 * ésta debe tener un valor no nulo de tipo Long. Si el permiso no estaba
	 * concedido no hace nada.
	 * @param userId
	 * @param accion
	 * @param bean
	 */
	public void anularPermiso(String accion, Object bean);
	/**
	 * Elimina el permiso de todos los usaurios para realizar la 
	 * <code>accion</code> sobre el objeto definido por <code>idObjeto</code>
	 * y <code>clazz</code>. Si el permiso no estaba concedido no hace nada.
	 * @param accion
	 * @param clazz
	 * @param idObjeto
	 */
	
	public void anularPermiso(String userId, String accion, Object bean);
	/**
	 * Elimina el permiso del usuario <code>userId</code> para realizar la 
	 * <code>accion</code> sobre el objeto definido por <code>idObjeto</code>
	 * y <code>clazz</code>. Si el permiso no estaba concedido no hace nada.
	 * @param userId
	 * @param accion
	 * @param clazz
	 * @param idObjeto
	 */
	public void anularPermiso(String userId, String accion, Class clazz, Long idObjeto);
	/**
	 * Devuelve el listado de ids de instancias de la clase dada que pueden ser
	 * accedidas por el usuario <code>userId</code> para realizar la accion. 
	 * @param userId
	 * @param clase
	 * @param accion
	 * @return
	 */
	public List<Long> instanciasPermitidas(String userId, Class clase, String accion);
	
	/**
	 * Obtiene el listado de usuarios autorizados para un permissionDescriptor.
	 * @param permissionDescriptor
	 * @return
	 */
	public List<String> usuariosAutorizados(PermissionDescriptor permissionDescriptor);
	
	/**
	 * Devuelve una colección de <code>PermissionDescriptor</code> asociados a este usuario.
	 * @param userId
	 * @return
	 */
	public Collection<PermissionDescriptor> permisosAsignados(String userId);
	
	/**
	 *  
	 * @return
	 */
	public Event<CambioDePermisos> onCambioDePermisos();
	
	/**
	 * Notificacion de cambio en la vinculacion entre un usuario y una persona.
	 */
	public interface CambioDePermisos extends Notification {
		public Collection<SeguridadObjetoBean> permisosAfectados(); 
	}
}