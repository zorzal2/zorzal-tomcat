package com.fontar.data.impl.domain.bean;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import com.pragma.bus.DeveloperException;
import com.pragma.bus.api.GenericService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * @author jdaccorso
 * 
 */
public class BitacoraSequence extends SequenceGenerator {

	private static final String SERVICE = "bitacoraService";

	private static final String TYPE = "tipo";

	private String tipo;

	@SuppressWarnings("unchecked")
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		try {

			Auditable audit = (Auditable) obj;
			BitacoraBean bitacora = audit.getBitacora();

			GenericService service = (GenericService) ContextUtil.getBean(SERVICE);

			if (bitacora.getId() == null) {
				if (bitacora.getIdProyecto() == null) {

					throw new DeveloperException("Falta agregar el ID del proyecto a la bitacora");
				}

			//	bitacora.setTipo(tipo); FIXME
				bitacora.setFechaRegistro(DateTimeUtil.getDate());
				if (bitacora.getFechaAsunto() == null) {
					bitacora.setFechaAsunto(DateTimeUtil.getDate());
				}


				service.save(bitacora);
			}
			else {
				service.update(bitacora);
			}
			
			return bitacora.getId();
		}
		catch (Exception ex) {
			throw new HibernateException(ex.getMessage());
		}
	}

	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		super.configure(type, params, dialect);

		tipo = params.getProperty(TYPE);
	}

}
