package com.fontar.data.impl.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.pragma.data.genericdao.impl.GenericDaoHibernateImpl;

public class PersonaDaoImpl 
	extends GenericDaoHibernateImpl<PersonaBean, Long> implements PersonaDAO {

	public PersonaDaoImpl() {
		super(PersonaBean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaBean> getActives(boolean active) {
		String queryString = "from PersonaBean o where o.activo = "+active+" and o.borrado = false";
		Query query = getSession().createQuery(queryString);
		return (List<PersonaBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaBean> getAllConActiveSumandoElemento(boolean active, Long id) {
		String queryString = "from PersonaBean o where (o.activo = "+active+" and o.borrado = false) or o.id="+id;
		Query query = getSession().createQuery(queryString);
		return (List<PersonaBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaBean> getAll() {
		String queryString = "from PersonaBean o where o.borrado = false";
		Query query = getSession().createQuery(queryString);
		return (List<PersonaBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaBean> getAllConActiveQuitandoElemento(boolean active, Long id) {
		String queryString = "from PersonaBean o where o.activo = "+active+" and o.borrado = false and o.id<>"+id;
		Query query = getSession().createQuery(queryString);
		return (List<PersonaBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<PersonaBean> findByCuit(String cuit) {
		return (List<PersonaBean>) getResults("select p from PersonaBean p where (p.cuit= ?) and p.borrado=false", cuit);
	}

	@SuppressWarnings("unchecked")
	public List<PersonaBean> findByName(String name) {
		return (List<PersonaBean>) getResults("select e from PersonaBean e where lower(?) = lower(e.nombre) and e.borrado=false", name);
	}

	@SuppressWarnings("unchecked")
	public List<PersonaBean> findByUserId(String userId) {
		return (List<PersonaBean>) getResults("select e from PersonaBean e where ? = e.userId and e.borrado=false", userId);		
	}

	@SuppressWarnings("unchecked")
	public List<PersonaBean> findWithUserId() {
		return (List<PersonaBean>) getResults("select e from PersonaBean e where e.userId is not null and e.borrado=false");		
	}
}
