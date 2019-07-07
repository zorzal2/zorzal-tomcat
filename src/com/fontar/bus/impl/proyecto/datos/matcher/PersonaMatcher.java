package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.Map;
import java.util.Set;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.PersonaDatosInsuficientesException;
import com.fontar.data.impl.assembler.PersonaAssembler;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.proyecto.datos.modelo.PersonaInfo;
import com.pragma.util.StringUtil;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * Clase auxiliar que premite ubicar a las personas a partir de objeto PersonaInfo.
 * @author llobeto
 *
 */
public class PersonaMatcher extends Matcher<PersonaInfo, PersonaBean> {
	
	private ConfiguracionServicio configuracionServicio;
	
	public PersonaMatcher(ConfiguracionServicio configuracionServicio, Map<String, UserFeedbackResponse> feedbackResponses) {
		super(feedbackResponses);
		this.configuracionServicio = configuracionServicio;
	}
	
	protected PersonaBean crearBean(PersonaInfo persona) throws PersonaDatosInsuficientesException {
		PersonaDTO dto = new PersonaDTO();
		dto.setNombre(persona.getNombre());
		dto.setCuit(persona.getCuit());
		if(StringUtil.isEmpty(persona.getNombre()) || StringUtil.isEmpty(persona.getSexo()))
			throw new PersonaDatosInsuficientesException(dto);

		if(persona.getLocalizacion()!=null) {
			dto.setLocalizacion(persona.getLocalizacion().toDto());
		}

		dto.setSexo(persona.getSexo());
		PersonaBean bean = PersonaAssembler.getInstance().buildBean(dto);
		return bean;
	}

	@Override
	protected boolean isEmpty(PersonaInfo persona) {
		return persona.isEmpty();
	}

	@Override
	protected AbstractUserFeedbackRequest<PersonaBean> nombreDistinto(String nombre, PersonaBean personaCoincidentePorCuit) {
		return new NombreDePersonaDistinto(nombre, personaCoincidentePorCuit);
	}

	@Override
	protected AbstractUserFeedbackRequest<PersonaBean> cuitDistinto(Set<PersonaBean> personasCoincidentes, PersonaBean bean, String cuit) {
		return new CuitDePersonaDistinto(personasCoincidentes, bean, cuit);
	}

	@Override
	protected AbstractUserFeedbackRequest<PersonaBean> faltaNombre(PersonaBean personaBean) {
		return new FaltaNombreDePersona(personaBean);
	}

	@Override
	protected Set<PersonaBean> obtenerBeansCoincidentes(String nombre, String cuit) {
		return configuracionServicio.obtenerPersonasCoincidentes(nombre, cuit);
	}

	@Override
	protected String cuitInfo(PersonaInfo persona) {
		return persona.getCuit();
	}

	@Override
	protected String cuitBean(PersonaBean persona) {
		return persona.getCuit();
	}

	@Override
	protected String nombreInfo(PersonaInfo persona) {
		return persona.getNombre();
	}

	@Override
	protected String nombreBean(PersonaBean persona) {
		return persona.getNombre();
	}
}
