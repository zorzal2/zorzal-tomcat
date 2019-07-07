package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class CuitDePersonaDistinto extends AbstractUserFeedbackRequest<PersonaBean> {
	protected static final String OPCION_CREAR_NUEVA = "opcionCrearNueva";
	
	public CuitDePersonaDistinto(
			Collection<PersonaBean> personasConElMismoNombre,
			PersonaBean personaNueva,
			String cuitIngresado) {
		super(
				generateUniqueId(personasConElMismoNombre, cuitIngresado), 
				buildMessage(personasConElMismoNombre, cuitIngresado, personaNueva),
				OPCION_ERROR,
				createOptions(personasConElMismoNombre, personaNueva)	
			);
	}
	private static String buildMessage(Collection<PersonaBean> personasConElMismoNombre, String cuitIngresado, PersonaBean personaNueva) {
		String key = "msj.persona.cuitDistinto.pregunta";
		if(personaNueva==null) {
			key = key + "NoSePuedeCrear";
		}
		if(cuitIngresado==null || cuitIngresado.length()==0) {
			return ResourceManager.getMessageResource(
					key + "SinCuit", 
					personasConElMismoNombre.iterator().next().getNombre() 
			);
		} else {
			return ResourceManager.getMessageResource(
					key, 
					personasConElMismoNombre.iterator().next().getNombre(), 
					cuitIngresado
			);
		}
	}
	private static String generateUniqueId(
			Collection<PersonaBean> personasConElMismoNombre,
			String cuitIngresado) {
		StringBuilder ret = new StringBuilder();
		ret.append("CuitDePersonaDistinto::");
		SortedSet<Long> ids = new TreeSet<Long>();
		for(PersonaBean persona : personasConElMismoNombre) {
			if(persona.getId()!=null) ids.add(persona.getId());
		}
		for(Long id : ids) {
			ret.append(id);
			ret.append('!');
		}
		ret.append(cuitIngresado);
		return ret.toString();
	}

	private static String messageUsarExistente(PersonaBean persona) {
		if(persona.getCuit()==null)
			return ResourceManager.getMessageResource("msj.persona.cuitDistinto.opcionUsarExistenteSinCuit");
		else
			return ResourceManager.getMessageResource("msj.persona.cuitDistinto.opcionUsarExistente", persona.getCuit());
	}
	
	private static List<LabelValue> createOptions(Collection<PersonaBean> personasConElMismoNombre, PersonaBean personaNueva) {
		List<LabelValue> ret;
		if(personaNueva==null) {
			ret = new ArrayList<LabelValue>(personasConElMismoNombre.size()+1);
		} else {
			ret = CollectionUtils.listWith(
					(LabelValue)new LabelValueImpl(
							ResourceManager.getMessageResource("msj.persona.cuitDistinto.opcionCrearNueva")
							, OPCION_CREAR_NUEVA)
			);
		}
		for(PersonaBean persona : personasConElMismoNombre) {
			ret.add(new LabelValueImpl(messageUsarExistente(persona), persona.getId().toString()));
		}
		ret.add(
				new LabelValueImpl(
						ResourceManager.getMessageResource("msj.persona.cuitDistinto.opcionError"), 
						OPCION_ERROR)
			);
		return ret;
	}
	@Override
	protected PersonaBean solve(Matching<PersonaBean> matching, String value) {
		if(value.equals(OPCION_CREAR_NUEVA)) {
			return byId(null, matching.getCandidates());
		} else {
			if(value.equals(OPCION_ERROR)) {
				return null;
			} else {
				return byId(Long.valueOf(value), matching.getCandidates());
			}
		}
	}
}