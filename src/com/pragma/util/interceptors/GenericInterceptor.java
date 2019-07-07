/**
 * 
 */
package com.pragma.util.interceptors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.EmptyInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

import com.fontar.data.api.dao.CambioEstadoProyectoDao;
import com.fontar.data.impl.domain.bean.CambioEstadoProyecto;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.pragma.util.ContextUtil;

/**
 * Especializacion de ejemplo para esquema de cifrado de datos.
 * 
 * @author fferrara
 */
public class GenericInterceptor extends EmptyInterceptor {

	protected HashMap<String, CryptoWorker> matches = new HashMap<String, CryptoWorker>();

	protected HashMap<String, String> bitacoras = new HashMap<String, String>();

	protected SessionFactory sessionFactory;

	
	protected CambioEstadoProyectoDao getDao(){
		return  (CambioEstadoProyectoDao) ContextUtil.getBean("cambioEstadoProyectoDao");
	}
	
	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		CryptoWorker worker = getWorker(entity, state, propertyNames);
		if (worker != null) {
			worker.decrypt();
			return true;
		}
		return false;
		// return super.onLoad(entity,)
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		boolean result = false;
		CryptoWorker worker = getWorker(entity, state, propertyNames);
		if (worker != null) {
			worker.crypt();
			result = true;
		}
		//		
		// if (bitacoras.containsKey(getEntityName(entity))) {
		//			
		// SaveEntity(entity,state,propertyNames);
		// result = true;
		// }
		return result;
	}

	/**
	 * Sería el evento onUpdate
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] state, Object[] oldValues,
			String[] propertyNames, Type[] types) {
		boolean onFlushDirty = false;
		CryptoWorker worker = getWorker(entity, state, propertyNames);
		if (worker != null) {
			worker.crypt();
			onFlushDirty = true;
		}
		
		//Interceptor de proyecto FIXME
		if(entity.getClass().equals(ProyectoBean.class)){
			this.onProyectoFlushDirty((ProyectoBean)entity,id,state,oldValues,propertyNames,types);
		}
		
		return onFlushDirty;
	}

	protected CryptoWorker getWorker(Object entity, Object[] state, String[] propertyNames) {

		String entityName = getEntityName(entity);
		if (matches.containsKey(entityName)) {
			CryptoWorker worker = matches.get(entityName);
			worker.setState(state);
			worker.setPropertyNames(propertyNames);
			worker.setEntityName(getEntityName(entity));
			worker.setEntity(entity);
			worker.setSession(getSessionFactory().openSession()); // TODO
																	// setear
																	// sesion de
																	// hibernate
			return worker;
		}
		return null;
	}

	public String getEntityName(Object entity) {
		return entity.getClass().getSimpleName();
	}

	public HashMap<String, CryptoWorker> getMatches() {
		return matches;
	}

	public void setMatches(HashMap<String, CryptoWorker> matches) {
		this.matches = matches;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private void modifyProperty(String propertyName, Object object, Object[] state, String[] propertyNames) {
		int i = 0;
		for (String name : propertyNames) {
			if (name.equals(propertyName)) {
				state[i] = object;
			}
			i++;
		}
	}

	public HashMap<String, String> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(HashMap<String, String> bitacoras) {
		this.bitacoras = bitacoras;
	}
	
	private Object retrieveState(String propertyName, String[] properties, Object[] state) {
		int i = 0;
		for (String name : properties) {
			if (name.equals(propertyName)) {
				return state[i];
			}
			i++;
		}
		return null;
	}
	
	
	public void onProyectoFlushDirty(ProyectoBean proyecto, Serializable serializable, Object[] state, Object[] previous, String[] properties, Type[] types) {
		Long id = (Long) this.retrieveState("id",properties,previous);
		String estadoInicial = (String) this.retrieveState("codigoEstado",properties,previous);
		String estadoFinal = (String) this.retrieveState("codigoEstado",properties,state);
		//El proyecto es nuevo o el estado fue modificado
		
		if(id==null || !estadoFinal.equals(estadoInicial)){
			CambioEstadoProyecto cambioEstadoProyecto = new CambioEstadoProyecto();
			//EL ID se asigna a nivel de trigger en la BD, para que no falle el incidente 43738 en la instalación del Fontar.
			cambioEstadoProyecto.setIdProyecto( proyecto.getId() );
			cambioEstadoProyecto.setEstadoInicial( EstadoProyecto.valueOf(estadoInicial));
			cambioEstadoProyecto.setEstadoFinal( EstadoProyecto.valueOf(estadoFinal));
			cambioEstadoProyecto.setFechaRegistro(new Date());
			this.getDao().save( cambioEstadoProyecto );
		}
		
	}

}