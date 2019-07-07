package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.List;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class NombreDePersonaDistinto extends AbstractUserFeedbackRequest<PersonaBean> {

	private static final String OPCION_ACTUALIZAR_DATOS = "opcionActualizarDatos";
	private static final String OPCION_CONSERVAR_DATOS = "opcionConservarDatos";
	
	String nombreIngresado;
	
	public NombreDePersonaDistinto(String nombreIngresado, PersonaBean personaCoincidente) {
		super(
				generateUniqueId(nombreIngresado, personaCoincidente), 
				ResourceManager.getMessageResource(
						"msj.persona.nombreDistinto.pregunta", 
						personaCoincidente.getCuit(), 
						personaCoincidente.getNombre(),
						nombreIngresado
					),
				OPCION_CONSERVAR_DATOS,
				createOptions(nombreIngresado, personaCoincidente)	
			);
		this.nombreIngresado = nombreIngresado;
	}

	private static String generateUniqueId(String nombreIngresado, PersonaBean personaCoincidente) {
		return "NombreDePersonaDistinto::"+personaCoincidente.getId()+"::"+((nombreIngresado==null)? 0 : nombreIngresado.hashCode());
	}
	
	protected static List<LabelValue> createOptions(String nombreIngresado, PersonaBean personaCoincidente) {
			return CollectionUtils.listWith(
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.persona.nombreDistinto.opcionActualizarDatos"), OPCION_ACTUALIZAR_DATOS),
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.persona.nombreDistinto.opcionConservarDatos"), OPCION_CONSERVAR_DATOS),
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.persona.nombreDistinto.opcionError"), OPCION_ERROR)
				);
	}

	@Override
	protected PersonaBean solve(Matching<PersonaBean> matching, String value) {
		if(value.equals(OPCION_ERROR)) return null;
		else {
			PersonaBean ret = CollectionUtils.getAny(matching.getCandidates());
			if(value.equals(OPCION_CONSERVAR_DATOS)) return ret;
			else {
				ret.setNombre(nombreIngresado);
				return ret;
			}
		}
	}
}