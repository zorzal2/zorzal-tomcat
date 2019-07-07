package com.fontar.data.impl.domain.bean;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

import com.pragma.bus.api.GenericABMService;
import com.pragma.util.ContextUtil;

public class PersonaSequence extends SequenceGenerator {

	private static final String SERVICE = "personaService";

	@SuppressWarnings("unchecked")
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		try {
			Personable bean = (Personable) obj;
			PersonaBean persona = bean.getPersona();

			GenericABMService service = (GenericABMService) ContextUtil.getBean(SERVICE);
			// Esto esta configurado como GenericABMService en la configuración
			// del bean

			// Se debería chequear q los atributos obligatorios no sean nulos
			if (persona.getId() == null) {
				service.save(persona);
			}
			else {
				service.update(persona);
			}

			return persona.getId();
		}
		catch (Exception ex) {
			throw new HibernateException(ex.getMessage());
		}
	}
}