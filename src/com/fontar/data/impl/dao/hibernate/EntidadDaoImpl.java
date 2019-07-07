package com.fontar.data.impl.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.Rol;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.pragma.data.genericdao.impl.GenericDaoHibernateImpl;
import com.pragma.util.StringUtil;

public class EntidadDaoImpl 
	extends GenericDaoHibernateImpl<EntidadBean, Long> implements EntidadDAO {

	public EntidadDaoImpl() {
		super(EntidadBean.class);
	}
	/**
	 * Devuelve un Map:Rol->Lista de objetos, con parte de los usos que tiene
	 * esta entidad con respecto a otras del sistema.
	 * @param idEntidad
	 * @return
	 */
	public Map<Rol, Collection<Object>> buscarUsosDeEntidad(Long idEntidad) {
		Map<Rol, Collection<Object>> ret = new HashMap<Rol, Collection<Object>>();
		
		Collection<Object> usos = usosPorEntidadInterviniente(idEntidad);
		if(!usos.isEmpty()) ret.put(Rol.ENTIDAD_INTERVINIENTE_DEL_PROYECTO, usos);
		usos = usosPorEntidadBeneficiaria(idEntidad);
		if(!usos.isEmpty()) ret.put(Rol.ENTIDAD_BENEFICIARIA_DEL_PROYECTO, usos);
		usos = usosPorEntidadBancariaEnProyectos(idEntidad);
		if(!usos.isEmpty()) ret.put(Rol.ENTIDAD_BANCARIA_DEL_PROYECTO, usos);
		usos = usosPorEntidadBancariaEnEvaluadoras(idEntidad);
		if(!usos.isEmpty()) ret.put(Rol.ENTIDAD_BANCARIA_DE_LA_EVALUADORA, usos);
		usos = evaluadoresDeLaEntidad(idEntidad);
		if(!usos.isEmpty()) ret.put(Rol.ENTIDAD_EVALUADORA_DEL_EVALUADOR, usos);
		
		
		return ret;
	}
	/**
	 * Devuelve los proyectos con financiacion en los cuales esta entidad es
	 * la entidad bancaria elegida. 
	 * @param idEntidad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> usosPorEntidadBancariaEnProyectos(Long idEntidad) {
		return getSession().createQuery(
				"select p from ProyectoRaizBean p where p.proyectoDatos.idEntidadBancaria is not null and p.proyectoDatos.idEntidadBancaria  = "+idEntidad
		).list();
	}
	/**
	 * Devuelve los evaluadores asociados a esta entidad.
	 * @param idEntidad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> evaluadoresDeLaEntidad(Long idEntidad) {
		EntidadEvaluadoraBean entidadEvaluadora = (EntidadEvaluadoraBean) getSession().get(EntidadEvaluadoraBean.class, idEntidad);
		if(entidadEvaluadora==null) return new ArrayList<Object>();
		else return (Collection) entidadEvaluadora.getEvaluadores();
	}
	/**
	 * Devuelve las entidades en las cuales esta entidad es su entidad bancaria.
	 * @param idEntidad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> usosPorEntidadBancariaEnEvaluadoras(Long idEntidad) {
		return getSession().createQuery(
				"select e from EntidadEvaluadoraBean e where e.idEntidadBancaria is not null and e.entidad.borrado=false and e.idEntidadBancaria = "+idEntidad
				).list();
	}
	/**
	 * Devuelve proyectos en los cuales interviene esta entidad.
	 * @param idEntidad
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> usosPorEntidadInterviniente(Long idEntidad) {
		//Primero me fijo si la entidad interviene en algo
		List<Long> idsEntidadesIntervinientes = getSession().createQuery(
				"select i.id from EntidadIntervinientesBean i, EntidadBean e where i.idEntidad = e.id and e.borrado=false and e.id="+idEntidad
		).list();
		
		if(idsEntidadesIntervinientes.size()==0) return new ArrayList<Object>();
		
		String idsEntidadesIntervinientesStr = StringUtil.join(idsEntidadesIntervinientes, ",");
		
		//Los IDs de entidades intervinientes coinciden con los elementos de bitacora
		
		Query query = getSession().createQuery(	
				"select p " +
				"from ProyectoRaizBean p " +
				"where p.id in ( " +
				"	select b.idProyecto " +
				"	from BitacoraBean b " +
				"	where b.tipo = :tipo and b.id in ("+idsEntidadesIntervinientesStr+") )");
		query.setParameter("tipo", TipoBitacora.ENTIDAD_INTERVINIENTE.getName());
		
		return query.list();
	}
	/**
	 * Devuelve proyectos en los cuales esta entidad es beneficiaria.
	 * @param idEntidad
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> usosPorEntidadBeneficiaria(Long idEntidad) {
		return getSession().createQuery(
				"select p from ProyectoRaizBean p where p.proyectoDatos.idEntidadBeneficiaria = "+idEntidad
				).list();
	}
	@SuppressWarnings("unchecked")
	public List<EntidadBean> findByCuit(String cuit) {
		return (List<EntidadBean>) getResults("select e from EntidadBean e where (e.cuit= ?) and e.borrado=false", cuit);
	}
	@SuppressWarnings("unchecked")
	public List<EntidadBean> findByName(String name) {
		return (List<EntidadBean>) getResults("select e from EntidadBean e where lower(?) = lower(e.denominacion) and e.borrado=false", name);
	}
	@SuppressWarnings("unchecked")
	public List<EntidadBean> findByCuitEntidad(Long idEntidad, String cuit) {
		return (List<EntidadBean>) getResults("select e from EntidadBean e where (? = e.cuit) AND (? <> e.id) and e.borrado=false", cuit, idEntidad);
	}
}
