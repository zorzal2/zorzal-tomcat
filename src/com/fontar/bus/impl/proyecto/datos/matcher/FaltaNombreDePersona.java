package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.List;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class FaltaNombreDePersona extends AbstractUserFeedbackRequest<PersonaBean> {
	private static final String OPCION_USAR_EXISTENTE = "opcionUsarExistente";
	
	public FaltaNombreDePersona(PersonaBean persona) {
		super(
				generateUniqueId(persona), 
				ResourceManager.getMessageResource(
						"msj.faltaNombreDePersona.pregunta", 
						persona.getCuit(), 
						persona.getNombre()
					),
				OPCION_USAR_EXISTENTE,
				createOptions(persona)	
			);
	}

	private static String generateUniqueId(PersonaBean persona) {
		return "FaltaNombreDePersona::"+persona.getId();
	}
	
	private static List<LabelValue> createOptions(PersonaBean persona) {
		return CollectionUtils.listWith(
				(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.faltaNombreDePersona.opcionUsarExistente"), OPCION_USAR_EXISTENTE),
				(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.faltaNombreDePersona.opcionError"), OPCION_ERROR)			
			);
	}

	@Override
	protected PersonaBean solve(Matching<PersonaBean> matching, String value) {
		if(value.equals(OPCION_USAR_EXISTENTE)) {
			return CollectionUtils.getAny(matching.getCandidates());
		} else {
			return null;
		}
	}
}