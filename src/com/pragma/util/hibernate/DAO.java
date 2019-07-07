package com.pragma.util.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pragma.toolbar.data.DatabaseContext;
import com.pragma.toolbar.exception.UnControlledException;
import com.pragma.util.exception.dao.BeanNotFoundException;
import com.pragma.util.interfaces.Bean;

/**
 * Los Data Access Object (DAO) implementan las operaciones básicas de búsqueda, inserción, modificación y eliminación de la base de datos de objetos del negocio. Se basan Hibernate para encontrar la correspondencia entre clases de Java y tablas en la base de datos.
 *
 */
public class DAO 
{
	private static Log log = LogFactory.getLog( DAO.class );
	
	private Class beanClass = null;
	private Map beans = new HashMap();
	
	private void unRegisterBean(Object bean) {
		Object id = ((Bean)bean).getId();
		beans.remove(id);
	}
	private void registerBean(Object bean){
		Object id = ((Bean)bean).getId();
		beans.put(id, bean);
	}
	private boolean isRegistered(Bean bean){
		Long id = bean.getId();
		return getRegisteredBean(id)==bean;
	}
	private Bean getRegisteredBean(Long id) {
		return (Bean)beans.get(id);
	}
	
	private static Map Instances = new HashedMap();

	protected void addInstance() {
		Session session = getSession();
		Set daosForSession = (Set)Instances.get(session);
		if(daosForSession==null) {
			daosForSession = new HashSet();
		}
		daosForSession.add(this);
		Instances.put(session, daosForSession);
	}
	protected void removeInstance() throws Exception {
		Session session = getSession();
		Set daosForSession = (Set)Instances.get(session);
		daosForSession.remove(this);
		if(daosForSession.isEmpty()) {
			//elimino la sesion
			Instances.remove(session);
		}
	}
	private DAO(Class beanClass) {
		this.beanClass = beanClass;
		addInstance();
	}
	protected void finalize() throws Throwable {
		removeInstance();
		super.finalize();
	}
	
	public static DAO getInstance(Class beanClass) {
		Session session = CurrentSession();
		Set daosForSession = (Set)Instances.get(session);
		if(daosForSession!=null) {
			//busco una instancia
			for (Iterator iter = daosForSession.iterator(); iter.hasNext();) {
				DAO element = (DAO) iter.next();
				if(element.getBeanClass().equals(beanClass)) {
					return element;
				}
			}			
		}
		//no hay una instancia creada
		return new DAO(beanClass);
	}
	
	/**
	 * Devuelve la Session de Hibernate donde voy a ejecutar el CRUD
	 * @return Session
	 * @throws HibernateException 
	 * @throws SQLException 
	 */
	public final Session getSession()
	{
		Session session = DatabaseContext.getCurrent().getSession();
		if (session == null)
			throw new IllegalStateException("Could not retrieve current session.");
		return session;
	}

	public static final Session CurrentSession() 
	{
		Session session = DatabaseContext.getCurrent().getSession();
		if (session == null)
	          throw new IllegalStateException("Could not retrieve current session.");
		return session;
	}
	
	/**
	 * Da de alta un registro
	 * @param obj Entidad a persistir (no posee id)
	 * @return id (PK) de la entidad
	 */
	public Serializable create(Bean obj)
	{
		showDebugInfo("CREATE", obj);
		
		Serializable serializable;
		try {
			serializable = this.getSession().save(getBeanClass().getName(), obj);
		} catch (HibernateException exeption) {
			exeption.printStackTrace();
			throw new UnControlledException("Hibernate Exception"); 
		}
		//El objeto no deberia estar registrado.
		registerBean(obj);
		return serializable;
	}

	/**
	 * Actualiza un registro 
	 * @param obj Entidad que representa el registro a actualizar
	 */
	public void update(Bean obj)
	{
		showDebugInfo("UPDATE", obj);
		//El objeto debe haber sido registrado previamente
		if(!isRegistered(obj)) throw new IllegalArgumentException("Instance does not exist.");
		try {
			this.getSession().update(getBeanClass().getName(),obj);
		} catch (HibernateException exeption) {
			exeption.printStackTrace();
			throw new UnControlledException(exeption);
		}
	}
	
	/**
	 * Borra el registro correspondiente
	 * @param obj Entidad a borrar
	 */
	public void remove(Bean obj)
	{
		showDebugInfo("REMOVE", obj);
		unRegisterBean(obj);
		try {
			this.getSession().delete(getBeanClass().getName(),obj);
		} catch (HibernateException exeption) {
			exeption.printStackTrace();
			throw new UnControlledException(exeption);
		}
	}

	public Object get(String id) {
		if(id==null || id.equals("") || id.equals("0")) return newBean();
		if(id==null) return newBean();
		return get(Long.valueOf(id));
	}
	public Object get(Object id) {
		if (id instanceof Long) {
			Long longId = (Long) id;
			return get(longId);
		} else return get((String)id);
	}
	/**
	 * Devuelve una instancia vacia para llenarla y grabarla.
	 * No se le debe asignart id
	 * @return
	 * @throws Exception
	 */
	public Bean newBean() {
		try {
			return (Bean)getBeanClass().newInstance();
		} catch (InstantiationException exeption) {
			exeption.printStackTrace();
			throw new UnControlledException(exeption);
		} catch (IllegalAccessException exeption) {
			exeption.printStackTrace();
			throw new UnControlledException(exeption);
		}
	}

	/**
	 * Devuelve TODOS los registros de la tabla
	 * @throws SQLException 
	 * @throws IllegalStateException 
	 */
	public List getAll()
	{
		return getAll(this.getSession().createCriteria(getBeanClass()));
	}
	
	public List getAll(Criteria criteria) 
	{
		//TODO: no se chequea que esten registrados. ver si es conveniente hacerlo
		try {
			return criteria.list();
		} catch (HibernateException exeption) {
			log.fatal(exeption);
			throw new UnControlledException(exeption);
		}
	}
	

	public Criteria newCriteria() {
		return getSession().createCriteria(getBeanClass());
	}
	
	/**
	 * Manejo de paginación
	 * @author llobeto
	 */
	/*public class Paginator 
	{
		public int DEFAULT_PAGE_SIZE=50; 
		int pageSize = DEFAULT_PAGE_SIZE;
		int currentPage = 0;
		Criteria criteria;
	
		protected Paginator(Criteria criteria){
			this.criteria=criteria;
		}
		
		public void nextPage() {
			currentPage++;
		}
		
		public int getFirstResultNumber() {
			return getPageSize()*getCurrentPage();
		}
		
		public Criteria getCriteria() {
			return criteria;
		}
		
		public void setCriteria(Criteria criteria) {
			this.criteria = criteria;
		}
		
		public int getCurrentPage() {
			return currentPage;
		}
		
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		
		public int getPageSize() {
			return pageSize;
		}
		
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		
		public List getPage() throws HibernateException {
			return getCriteria()
						.setFirstResult(getFirstResultNumber())
						.setMaxResults(getPageSize())
						.list();
		}
	}
	
	public List getPage(Paginator paginator) throws HibernateException 
	{
		return paginator.getPage();
	}
	
	public Paginator createPaginator() throws IllegalStateException, HibernateException 
	{
		return createPaginator(this.getSession().createCriteria(getBeanClass()));
	}
	
	private Paginator createPaginator(Criteria criteria) 
	{
		return new Paginator(criteria);
	}*/

	public Class getBeanClass() {
		return this.beanClass;
	}
	
	protected void showDebugInfo(String action, Object obj) {
		
		try {
			System.out.println("** "+action+" **");
			System.out.println("Session:"+this.getSession());
			System.out.println("Object:"+obj);
			Class[] empty = {};
			System.out.println("Object ID:"+obj.getClass().getMethod("getId", empty).invoke(obj, empty));
		} catch (Exception exeption) {
			System.out.println("Error in showDebugInfo");
		}
	}
	
	public Object getUnique(Criteria criteria) 
	{
            try {
                return baseGetWithDefault((Bean)criteria.uniqueResult());
            } catch (HibernateException exeption) {
                exeption.printStackTrace();
                throw new UnControlledException(exeption);
            }
	}
	
	private Bean baseGetWithDefault(Bean defaultBean)
	{
            Bean bean = getRegisteredBean(defaultBean.getId());
            if(bean==null && defaultBean!=null) {
		registerBean(defaultBean);
		bean = defaultBean;
            }
            return bean;
	}
	
	public Bean get(Long id)
	{
	    if(id==null || id.longValue()==0) return newBean();
		return baseGet(id); 
	}

	private Bean baseGet(Long id)
	{
	    Bean bean = getRegisteredBean(id);
	    if(bean==null) {
		try {
		    bean = (Bean)this.getSession().get(getBeanClass(), id);
		} catch (HibernateException exeption) {
		    exeption.printStackTrace();
		    throw new UnControlledException("Hibernate Exception");
		}
		if (bean==null) throw new BeanNotFoundException("id ="+id);
		registerBean(bean);
	    }
	    return bean;
	}
}
