package com.pragma.data.genericdao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.pragma.data.genericdao.GenericDao;
import com.pragma.data.genericdao.finder.FinderArgumentTypeFactory;
import com.pragma.data.genericdao.finder.FinderExecutor;
import com.pragma.data.genericdao.finder.FinderNamingStrategy;
import com.pragma.data.genericdao.finder.impl.ArgumentMap;
import com.pragma.data.genericdao.finder.impl.SimpleFinderArgumentTypeFactory;
import com.pragma.data.genericdao.finder.impl.SimpleFinderNamingStrategy;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.exception.IllegalArgumentException;

/**
 * Hibernate implementation of GenericDao A typesafe implementation of CRUD and
 * finder methods based on Hibernate and Spring AOP The finders are implemented
 * through the executeFinder method. Normally called by the
 * FinderIntroductionInterceptor
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK>, FinderExecutor<T> {

	private SessionFactory sessionFactory;

	private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default.
																					// Can
																					// override
																					// in
																					// config

	private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default.
																									// Can
																									// override
																									// in
																									// config

	protected Class<T> type;

	private static Log log;

	/**
	 * Creates a new GenericDaoHibernateImpl object.
	 * 
	 * @param type DOCUMENT ME!
	 */
	public GenericDaoHibernateImpl(Class<T> type) {
		this.type = type;
		log = LogFactory.getLog(this.getClass().getName());
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param o DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	@SuppressWarnings("unchecked")
	public PK create(T o) {
		save(o);
		try {
			return (PK)BeanUtils.getMethodWithNoParams(o, "getId").invoke(o);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param id DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	@SuppressWarnings("unchecked")
	public T read(PK id) {
		log.info("Leyendo el objeto " + type.getName() + " con ID " + id.toString());
		return (T) getSession().get(type, id);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param o DOCUMENT ME!
	 */
	public T update(T o) {
		log.info("Actualizando el objeto " + o.getClass().getName());
		getSession().update(type.getName(), o);
		return o;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param o DOCUMENT ME!
	 */
	public void delete(T o) {
		log.info("Borrando el objeto " + o.getClass().getName());
		if(tieneBorradoLogico()) {
			/*
			 * Borro logicamente y destruyo (si hay un pseudo-destructor definido)
			 */
			destroy(o);
			borrar(o);
			update(o);
			
		} else {
			getSession().delete(type.getName(), o);
		}
	}
	
	private void destroy(T o) {
		try {
			BeanUtils.getMethodWithNoParams(o, "destroy").invoke(o);
		}
		catch (NoSuchMethodException e) {
			//No hay un destroy
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void borrar(T o) {
		setBorrado(o, true);
	}
	private void setBorrado(T o, boolean b) {
		try {
			BeanUtils.getMethod(o, "setBorrado", Boolean.class).invoke(o, b);
		}
		catch (NoSuchMethodException e) {
			//No hay setter
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param method DOCUMENT ME!
	 * @param queryArgs DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public List<T> executeFinder(Method method, ArgumentMap queryArgs) {
		Query namedQuery = prepareQuery(method, queryArgs);

		return (List<T>) namedQuery.list();
	}

	/**
	 * 
	 */
	public Iterator<T> iterateFinder(Method method, final ArgumentMap queryArgs) {
		final Query namedQuery = prepareQuery(method, queryArgs);

		return (Iterator<T>) namedQuery.iterate();
	}

	public Object executeSelect(Method method, ArgumentMap queryArgs) {
		final Query namedQuery = prepareQuery(method, queryArgs);

		return namedQuery.uniqueResult();
	}

	/**
	 * Prepara los parametros de la consulta para poder ejecutarla
	 * 
	 */
	protected Query prepareQuery(Method method, Object[] queryArgs) {
		final String queryName = getNamingStrategy().queryNameFromMethod(type, method);

		log.info("Preparando la consulta " + queryName);

		final Query namedQuery = getSession().getNamedQuery(queryName);
		String[] namedParameters = namedQuery.getNamedParameters();

		if (namedParameters.length == 0) {
			setPositionalParams(queryArgs, namedQuery);
		}
		else {
			setNamedParams(namedParameters, queryArgs, namedQuery);
		}

		return namedQuery;
	}
	/**
	 * Prepara los parametros por nombre de la consulta para poder ejecutarla
	 * 
	 */
	protected Query prepareQuery(Method method, ArgumentMap queryArgs) {
		final String queryName = getNamingStrategy().queryNameFromMethod(type, method);

		log.info("Preparando la consulta " + queryName);

		Session session = getSession();
		//Evita un bug en hibernate por el cual hace un flush automáticamente.
		//http://opensource.atlassian.com/projects/hibernate/browse/HHH-1508
		session.setFlushMode(FlushMode.COMMIT);
		Query namedQuery = session.getNamedQuery(queryName);
		
		String[] namedParameters = namedQuery.getNamedParameters();

		if (namedParameters.length == 0) {
			//Asume que el mapa es un LinkedHashMap con orden de iteracion predecible
			setPositionalParams(queryArgs.arguments(), namedQuery);
		}
		else {
			if(queryArgs.areNamed()) {
				/*
				 * Codigo nuevo. Se usan anotaciones para nombrar a los parametros
				 * del metodo. 
				 */
				setNamedParams(namedParameters, queryArgs, namedQuery);
			} else {
				/*
				 * Compatibilidad con codigo anterior. Los argumentos se aparean 
				 * uno a uno con los parametros con nombre de la query
				 */
				setNamedParams(namedParameters, queryArgs.arguments(), namedQuery);
			}
		}

		return namedQuery;
	}
	/**
	 * Setea los parametros en la named query según su posición
	 * @param queryArgs argumentos de la consulta, es posicional segun el HQL
	 * @param namedQuery nombre de la consulta, se encuentra en el .hbm.xml de
	 * la entidad
	 */
	protected void setPositionalParams(Object[] queryArgs, Query namedQuery) {
		// Set parameter. Use custom Hibernate Type if necessary
		boolean debugLog = log.isDebugEnabled();

		if (queryArgs != null) {
			for (int i = 0; i < queryArgs.length; i++) {

				Object arg = queryArgs[i];
				Type argType = getArgumentTypeFactory().getArgumentType(arg);

				// logueo los parametros solo en modo debug
				if (debugLog) {
					log.debug("Parameter position: " + i + " string value: " + arg.toString());
				}

				if (argType != null) {
					namedQuery.setParameter(i, arg, argType);
				}
				else {
					namedQuery.setParameter(i, arg);
				}
			}
		}
	}

	/**
	 * Setea los parametros en la named query según su nombre
	 * @param namedParameters nombre de parametros
	 * @param queryArgs valores de los parametros
	 * @param namedQuery nombre de la consulta, se encuentra en el .hbm.xml de
	 * la entidad
	 */
	protected void setNamedParams(String[] namedParameters, Object[] queryArgs, Query namedQuery) {
		// Set parameter. Use custom Hibernate Type if necessary
		boolean debugLog = log.isDebugEnabled();
		if (queryArgs != null) {
			for (int i = 0; i < queryArgs.length; i++) {
				Object arg = queryArgs[i];
				Type argType = getArgumentTypeFactory().getArgumentType(arg);

				// logueo los parametros solo en modo debug
				if (debugLog) {
					log.debug("Parameter name: " + namedParameters[i] + " string value: " + arg.toString());
				}

				if (argType != null) {
					namedQuery.setParameter(namedParameters[i], arg, argType);
				}
				else {
					if (arg instanceof Collection) {
						namedQuery.setParameterList(namedParameters[i], (Collection) arg);
					}
					else {
						namedQuery.setParameter(namedParameters[i], arg);
					}
				}
			}
		}
	}
	private void setNamedParams(String[] namedParameters, ArgumentMap queryArgs, Query namedQuery) {
		boolean debugLog = log.isDebugEnabled();
		if (queryArgs != null) {
			for (int i = 0; i < namedParameters.length; i++) {
				String paramName = namedParameters[i];
				if(!queryArgs.hasNamedArgument(paramName)) throw new IllegalArgumentException("Falta el parametro "+paramName+" para realizar la consulta.");
				Object argumentValue = queryArgs.getNamedArgument(paramName);
				
				Type argType = getArgumentTypeFactory().getArgumentType(argumentValue);

				// logueo los parametros solo en modo debug
				if (debugLog) {
					log.debug("Parameter name: " + paramName + " string value: " + argumentValue.toString());
				}

				if (argType != null) {
					namedQuery.setParameter(paramName, argumentValue, argType);
				}
				else {
					if (argumentValue instanceof Collection) {
						namedQuery.setParameterList(paramName, (Collection) argumentValue);
					}
					else {
						namedQuery.setParameter(paramName, argumentValue);
					}
				}
			}
		}
	}
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Session getSession() {
		boolean allowCreate = true;

		return SessionFactoryUtils.getSession(sessionFactory, allowCreate);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param sessionFactory DOCUMENT ME!
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public FinderNamingStrategy getNamingStrategy() {
		return namingStrategy;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param namingStrategy DOCUMENT ME!
	 */
	public void setNamingStrategy(FinderNamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public FinderArgumentTypeFactory getArgumentTypeFactory() {
		return argumentTypeFactory;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param argumentTypeFactory DOCUMENT ME!
	 */
	public void setArgumentTypeFactory(FinderArgumentTypeFactory argumentTypeFactory) {
		this.argumentTypeFactory = argumentTypeFactory;
	}

	/**
	 * Trae todas las entidades persistidas activas o no, salvo borradas.
	 */
	public List<T> getAll() {
		Query query = getSession().createQuery("select o from " + type.getName() +" o where " + whereBorradoSiSeAplica("o", false));
		return (List<T>) query.list();
	}

	private boolean hasProperty(String propertyName) {
		boolean found = false;
		ClassMetadata clazzMetaData = sessionFactory.getClassMetadata(type);
		String names[] = clazzMetaData.getPropertyNames();
		for (int i = 0; i < names.length && !found; i++)
			found = names[i].equals(propertyName);

		return found;
	}
	/**
	 * Devuelve los objetos activos o inactivos. No incluye los borrados.
	 */
	public List<T> getActives(boolean active) {
		if (esActivable()) {
			StringBuffer queryString = new StringBuffer();
			queryString.append("from ");
			queryString.append(type.getName());
			queryString.append(" o where ");
			queryString.append(whereActivoSiSeAplica("o", active));
			queryString.append(" and ");
			queryString.append(whereBorradoSiSeAplica("o", false));
			Query query = getSession().createQuery(queryString.toString());
			return (List<T>) query.list();
		}
		else
			return this.getAll();
	}
	
	private Boolean esActivable = null;
	public boolean esActivable() {
		if(esActivable==null)esActivable = this.hasProperty(activeFieldName());
		return esActivable;
	}

	private Boolean tieneBorradoLogico = null;
	public boolean tieneBorradoLogico() {
		if(tieneBorradoLogico==null)tieneBorradoLogico=this.hasProperty(deletedFieldName());
		return tieneBorradoLogico;
	}

	/**
	 * Graba la entidad
	 */
	public T save(T persistentObject) {
		log.info("Grabando el Objeto " + persistentObject.getClass().getName());
		if(tieneBorradoLogico()) setBorrado(persistentObject, false);
		getSession().save(type.getName(), persistentObject);
		return persistentObject;
	}

	public List<T> getAllConActiveQuitandoElemento(boolean active,Long id) {
		// TODO ver si el control de la propiedad es necesario, en caso de que
		// lo sea, proponer un esquema de cache?

		StringBuffer queryString = new StringBuffer();
		queryString.append("from ");
		queryString.append(type.getName());
		queryString.append(" o where ");
		queryString.append(whereActivoSiSeAplica("o", active));
		queryString.append(" and ");
		queryString.append(whereBorradoSiSeAplica("o", false));
		queryString.append(" and ");
		queryString.append(" o.id <>");
		Query query = getSession().createQuery(queryString.toString());
		
		return (List<T>) query.list();
	}

	/**
	 * Devuelve todos los objetos no borrados con la propiedad active dada y agrega el objeto
	 * con el id dado.
	 */
	public List<T> getAllConActiveSumandoElemento(boolean active,Long id) {

			StringBuffer queryString = new StringBuffer();
			queryString.append("from ");
			queryString.append(type.getName());
			queryString.append(" o where (");
			queryString.append(whereActivoSiSeAplica("o", active));
			queryString.append(" and ");
			queryString.append(whereBorradoSiSeAplica("o", false));
			queryString.append(") or o.id=");
			queryString.append(id);
			Query query = getSession().createQuery(queryString.toString());
			return (List<T>) query.list();
	}
	/**
	 * Si se sobreescribe hay que sobreescribir o verifiar que aplique la implementacion
	 * default de esActivable. 
	 * @return
	 */
	private String activeFieldName() {
		return "activo";
	}
	/**
	 * Si se sobreescribe hay que sobreescribir o verifiar que aplique la implementacion
	 * default de tieneBorradoLogico. 
	 * @return
	 */
	private String deletedFieldName() {
		return "borrado";
	}
	
	protected Object getUniqueResult(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.uniqueResult();
	}
	
	protected Object getUniqueResult(String hql, Map<String, Object> parameters) {
		Query query = getSession().createQuery(hql);
		for(Entry<String, Object> parameter : parameters.entrySet()) {
			query.setParameter(parameter.getKey(), parameter.getValue());
		}
		return query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	protected List<? extends Object> getResults(String hql, Map<String, Object> parameters) {
		Query query = getSession().createQuery(hql);
		for(Entry<String, Object> parameter : parameters.entrySet()) {
			query.setParameter(parameter.getKey(), parameter.getValue());
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	protected List<? extends Object> getResults(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
	/**
	 * Devuelve la condición del where, en hql, para filtrar objetos según estén
	 * o no borrados. La condición debe predicar sobre el nombre de variable dado.
	 * Ej: "o.borrado=true or o.padre.borrado=true"
	 * @param varName
	 * @param borrado
	 * @return
	 */
	protected String whereBorrado(String varName, boolean borrado) {
		return varName + "." + deletedFieldName() + " = " + borrado;
	}
	
	private String whereBorradoSiSeAplica(String varName, boolean borrado) {
		if(tieneBorradoLogico()) {
			return "("+whereBorrado(varName, borrado)+")";
		} else {
			return borrado? "(1=2)" : "(1=1)";
		}
	}

	/**
	 * Devuelve la condición del where, en hql, para filtrar objetos según estén
	 * o no activos. La condición debe predicar sobre el nombre de variable dado.
	 * Ej: "o.activo=true and o.padre.activo=true"
	 * @param varName
	 * @param borrado
	 * @return
	 */
	protected String whereActivo(String varName, boolean activo) {
		return varName + "." + activeFieldName() + " = " + activo;
	}
	
	private String whereActivoSiSeAplica(String varName, boolean activo) {
		if(esActivable()) {
			return "("+whereActivo(varName, activo)+")";
		} else {
			return activo ? "(1=1)" : "(1=2)";
		}
	}
}
