package com.pragma.bus.api;

import java.util.List;

import com.pragma.data.genericdao.GenericDao;

public interface GenericABMService<T> {
	List<T> getAll();

	List<T> getActives(boolean active);

	T load(Long id);

	T save(T transientObject);

	T update(T transientObject);

	void delete(T transientObject);

	GenericDao getDao();

	List<T> getSeleccion(boolean active,Long id);
}
