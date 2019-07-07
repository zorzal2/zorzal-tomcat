package com.fontar.data.impl.domain.bean;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

import com.pragma.bus.api.GenericABMService;
import com.pragma.util.ContextUtil;

public class EntidadSequence extends SequenceGenerator {

	private static final String SERVICE = "entidadService";

	@SuppressWarnings("unchecked")
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		try {
			Entidable bean = (Entidable) obj;
			EntidadBean entidad = bean.getEntidad();

			GenericABMService service = (GenericABMService) ContextUtil.getBean(SERVICE);
			// Esto esta configurado como GenericABMService en la configuración
			// del bean

			if (entidad.getId() == null) {
				service.save(entidad);
			}
			else {
				service.update(entidad);
			}

			return entidad.getId();
		}
		catch (Exception ex) {
			throw new HibernateException(ex.getMessage());
		}
	}
}