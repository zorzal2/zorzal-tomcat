package com.pragma.bus.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pragma.data.genericdao.GenericDao;

public interface GenericService {
	public abstract Map<String, GenericDao> getHashDao();

	public abstract void setHashDao(Map<String, GenericDao> hashDao);

	public abstract List getAll(Class clase);

	public abstract List getAll();

	public abstract Object load(Long id);

	public abstract Object load(Class clase, Long id);

	public abstract Object save(Object transientObject);

	public abstract Object update(Object transientObject);

	public abstract void save(Object... arguments);

	public abstract void update(Object... arguments);

	public abstract void delete(Object... arguments);

	public abstract Class getType();

	public void delete(Object transientObject);

	public GenericDao getDao(Class clase);
}