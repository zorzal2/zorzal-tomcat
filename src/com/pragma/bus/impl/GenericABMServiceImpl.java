package com.pragma.bus.impl;

import java.util.List;

import com.pragma.bus.api.GenericABMService;
import com.pragma.data.genericdao.GenericDao;

public class GenericABMServiceImpl<T> implements GenericABMService<T> {

	protected Class<T> type;

	protected GenericDao dao;

	public void setDao(GenericDao dao) {
		this.dao = dao;
	}

	public GenericABMServiceImpl(Class<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return dao.getAll();
	}

	public T load(Long id) {
		return (T) dao.read(id);
	}

	public T save(T transientObject) {
		return (T) dao.save((T) transientObject);
	}

	public T update(T transientObject) {
		return (T) dao.update((T) transientObject);
	}

	public void delete(T transientObject) {
		dao.delete((T) transientObject);
	}

	public GenericDao getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public List<T> getActives(boolean active) {
		return this.dao.getActives(active);
	}

	public List<T> getSeleccion(boolean active,Long id) {
		return this.dao.getAllConActiveQuitandoElemento(active,id);
	}

}
