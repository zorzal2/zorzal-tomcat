package com.fontar.bus.impl.seguridad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fontar.bus.api.seguridad.PermissionDescriptor;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.data.api.dao.SeguridadObjetoDAO;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.bean.seguridad.SeguridadObjetoBean;
import com.pragma.util.CollectionUtils;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.event.Event;
import com.pragma.util.event.Notification;
import com.pragma.util.exception.IllegalArgumentException;

public class SeguridadObjetoServicioImpl implements SeguridadObjetoServicio {
	private SeguridadObjetoDAO seguridadObjetoDAO;

	public void setSeguridadObjetoDAO(SeguridadObjetoDAO seguridadObjetoDAO) {
		this.seguridadObjetoDAO = seguridadObjetoDAO;
	}
	
	public void permitir(String userId, String accion, Object bean) {
		Long id = BeanUtils.getId(bean);
		if(id==null) throw new IllegalArgumentException("El id del objeto es nulo");
		Long idProcessInstance = null;
		if(bean instanceof Workflowable) {
			idProcessInstance = ((Workflowable)bean).getIdWorkFlow();
		}
		permitir(userId, accion, bean.getClass(), id, idProcessInstance);
	}
	/**
	 * Permite al usuario <code>userId</code> realizar la accion 
	 * <code>accion</code> sobre el objeto definido por la clase 
	 * <code>clazz</code> y el id <code>idObjeto</code>.
	 * @param userId
	 * @param accion
	 * @param bean
	 */
	public void permitir(String userId, String accion, Class clazz, Long idObjeto, Long idPocessInstance) {
		if(idObjeto==null) throw new IllegalArgumentException("El id del objeto es nulo");
		if(seguridadObjetoDAO.findByAccionObjetoYUsuario(accion, clazz.getName(), idObjeto, userId).isEmpty()){
			SeguridadObjetoBean seguridadObjetoBean = new SeguridadObjetoBean();
			seguridadObjetoBean.setAccion(accion);
			seguridadObjetoBean.setClase(clazz.getName());
			seguridadObjetoBean.setIdObjeto(idObjeto);
			seguridadObjetoBean.setUsuario(userId);
			seguridadObjetoBean.setIdProcessInstance(idPocessInstance);
			seguridadObjetoDAO.create(seguridadObjetoBean);
			notifyEvent(seguridadObjetoBean);
		}
	}
	
	public void anularPermiso(String accion, Object bean) {
		Long id = BeanUtils.getId(bean);
		anularPermiso(null, accion, bean.getClass(), id);
	}
	
	public void anularPermiso(String userId, String accion, Object bean) {
		Long id = BeanUtils.getId(bean);
		anularPermiso(userId, accion, bean.getClass(), id);
	}
	
	public void anularPermiso(String userId, String accion, Class clazz, Long idObjeto) {
		if(idObjeto==null) throw new IllegalArgumentException("El id del objeto es nulo");
		
		List<SeguridadObjetoBean> seguridadObjetoBeans = null;
		if (userId==null)
			seguridadObjetoBeans = seguridadObjetoDAO.findByObjetoYAccion(clazz.getName(), idObjeto, accion);
		else
			seguridadObjetoBeans = seguridadObjetoDAO.findByAccionObjetoYUsuario(accion, clazz.getName(), idObjeto, userId);
		
		if(!seguridadObjetoBeans.isEmpty()) {
			for(SeguridadObjetoBean seguridadObjetoBean : seguridadObjetoBeans) {
				seguridadObjetoDAO.delete(seguridadObjetoBean);
				notifyEvent(seguridadObjetoBean);
			}
		}
	}

	public List<Long> instanciasPermitidas(String userId, Class clase, String accion) {
		List<SeguridadObjetoBean> beans = seguridadObjetoDAO.findInstancias(clase.getName(), userId, accion);
		List<Long> ret = new ArrayList<Long>(beans.size());
		for(SeguridadObjetoBean bean : beans) {
			ret.add(bean.getIdObjeto());
		}
		return ret;
	}

	public List<String> usuariosAutorizados(PermissionDescriptor permissionDescriptor) {
		List<SeguridadObjetoBean> beans = seguridadObjetoDAO.findByObjetoYAccion(permissionDescriptor.getObjectClass().getName(), permissionDescriptor.getObjectId(), permissionDescriptor.getAccionSobreObjeto());
		List<String> ret = new ArrayList<String>(beans.size());
		for(SeguridadObjetoBean bean : beans) {
			ret.add(bean.getUsuario());
		}
		return ret;
	}

	private static Event<CambioDePermisos> onCambioDePermisos = null;
	/**
	 * Implementacion del evento de cambio de permisos
	 * @author llobeto
	 *
	 */
	protected static class CambioDePermisosImpl 
		implements CambioDePermisos {

		private Collection<SeguridadObjetoBean> permisosAfectados;
		
		public CambioDePermisosImpl(SeguridadObjetoBean... permisosAfectados) {
			this.permisosAfectados = CollectionUtils.collectionWith(permisosAfectados);
		}

		public Collection<SeguridadObjetoBean> permisosAfectados() {
			return permisosAfectados;
		}

		public Event<? extends Notification> event() {
			return onCambioDePermisos;
		}
	}
	public Event<CambioDePermisos> onCambioDePermisos() {
		if(onCambioDePermisos==null) onCambioDePermisos = Event.create();
		return onCambioDePermisos;
	}
	/**
	 * Notifica sobre los cambios solo si hay un evento creado.
	 * @param beans
	 */
	private void notifyEvent(SeguridadObjetoBean...beans) {
		if(onCambioDePermisos!=null)
			onCambioDePermisos.notifyEvent(new CambioDePermisosImpl(beans));
	}

	public Collection<PermissionDescriptor> permisosAsignados(String userId) {
		List<SeguridadObjetoBean> seguridadObjetos = seguridadObjetoDAO.findByUsuario(userId);
		Collection<PermissionDescriptor> ret = new ArrayList<PermissionDescriptor>(seguridadObjetos.size());
		for(SeguridadObjetoBean bean : seguridadObjetos) {
			ret.add(bean.getPermissionDescriptor());
		}
		return ret;
	}
}
