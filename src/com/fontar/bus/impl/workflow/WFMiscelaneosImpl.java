package com.fontar.bus.impl.workflow;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.fontar.bus.api.workflow.WFMiscelaneos;
import com.fontar.data.api.domain.Workflowable;

public class WFMiscelaneosImpl implements WFMiscelaneos {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Devuelve la Entidad Workflowable para el id pasado como parametro. Aplica
	 * a las entidades que implementan esta interfaz, por ejemplo:
	 * <li>ProyectoBean</li>
	 * <li>PaqueteBean</li>
	 * <li>IdeaProyectoBean</li>
	 * <li>EvaluacionBean</li>
	 */
	public Workflowable obtenerWorkflowable(Long idWorkflow) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);

		Query query = session.createQuery("from com.fontar.data.api.domain.Workflowable where idWorkFlow =:idWorkFlow");
		query.setLong("idWorkFlow", idWorkflow);

		Workflowable bean = (Workflowable) query.uniqueResult();

		return bean;
	}

}
