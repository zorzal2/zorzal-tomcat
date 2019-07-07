package com.fontar.consultas;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.util.Util;

public class ProyectoPorEmpresaYConvocatoriaResolver extends ConsultaProyectoResolver implements Resolver {

	private SessionFactory sessionFactory;
	
	private CollectionHandler collectionHandler = new CollectionHandler();
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setInitialContextImpl(HttpServletRequest request) {
		
		request.setAttribute("instrumentos", this.collectionHandler.getInstrumentos());
		// request.setAttribute("entidadesBeneficiarias", this.collectionHandler.getEntidadesBeneficiarias());
		
	}

	public Map getParameters(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
			
		String strInstrumento = (String) form.getFilter("instrumento");
		
		if (!Util.isBlank(strInstrumento)) {
			Long idInstrumento = new Long(strInstrumento);
			proyecto.add(Property.forName("idInstrumento").eq(idInstrumento));
		}
		
		String strIdEntidadBeneficiaria = (String) form.getFilter("idEntidadBeneficiaria");		
		
		if (!Util.isBlank(strIdEntidadBeneficiaria)) {			
			Long idEntidadBeneficiaria = new Long(strIdEntidadBeneficiaria);
			proyectoDatos.add(Property.forName("idEntidadBeneficiaria").eq(idEntidadBeneficiaria));
		}

		
		Criteria beneficiariaCriteria = proyectoDatos.createCriteria("entidadBeneficiaria");
		Criteria entidadCriteria = beneficiariaCriteria.createCriteria("entidad");
		
		entidadCriteria.addOrder(Order.asc("denominacion"));		
		proyecto.addOrder(Order.asc("idInstrumento"));
		proyecto.addOrder(Order.asc("codigo"));
		
	}
	
}
