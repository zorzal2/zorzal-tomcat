package com.pragma.data.genericdao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * The basic GenericDao interface with CRUD methods Finders are added with
 * interface inheritance and AOP introductions for concrete implementations
 * 
 * Extended interfaces may declare methods starting with find... list...
 * iterate... or scroll... They will execute a preconfigured query that is
 * looked up based on the rest of the method name
 */
public interface GenericDao<Bean, PK extends Serializable> {

	PK create(Bean newInstance);

	Bean read(PK id);

	void delete(Bean persistentObject);

	List<Bean> getAll();

	List<Bean> getActives(boolean active);

	Bean save(Bean persistentObject);

	Bean update(Bean persistentObject);

	public Session getSession();

	List<Bean> getAllConActiveQuitandoElemento(boolean active,Long id);

	List<Bean> getAllConActiveSumandoElemento(boolean active,Long id);
}
