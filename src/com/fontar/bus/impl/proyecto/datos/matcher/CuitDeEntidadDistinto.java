package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class CuitDeEntidadDistinto extends AbstractUserFeedbackRequest<EntidadBeneficiariaBean> {
	protected static final String OPCION_CREAR_NUEVA = "opcionCrearNueva";
	
	public CuitDeEntidadDistinto(
			Collection<EntidadBeneficiariaBean> entidadesConElMismoNombre,
			EntidadBeneficiariaBean entidadNueva,
			String cuitIngresado) {
		super(
				generateUniqueId(entidadesConElMismoNombre, cuitIngresado), 
				buildMessage(entidadesConElMismoNombre, cuitIngresado, entidadNueva),
				OPCION_ERROR,
				createOptions(entidadesConElMismoNombre, entidadNueva)	
			);
	}
	private static String buildMessage(Collection<EntidadBeneficiariaBean> entidadesConElMismoNombre, String cuitIngresado, EntidadBeneficiariaBean entidadNueva) {
		String key = "msj.entidad.cuitDistinto.pregunta";
		if(entidadNueva==null) {
			key = key+"NoSePuedeCrear";
		}
		if(cuitIngresado==null || cuitIngresado.length()==0) {
			return ResourceManager.getMessageResource(
					key+"SinCuit", 
					entidadesConElMismoNombre.iterator().next().getDenominacion() 
			);
		} else {
			return ResourceManager.getMessageResource(
					key, 
					entidadesConElMismoNombre.iterator().next().getDenominacion(),
					cuitIngresado
			);
		}
	}
	private static String generateUniqueId(
			Collection<EntidadBeneficiariaBean> entidadesConElMismoNombre,
			String cuitIngresado) {
		StringBuilder ret = new StringBuilder();
		ret.append("CuitDeEntidadDistinto::");
		SortedSet<Long> ids = new TreeSet<Long>();
		for(EntidadBeneficiariaBean entidad : entidadesConElMismoNombre) {
			ids.add(entidad.getId());
		}
		for(Long id : ids) {
			ret.append(id);
			ret.append('!');
		}
		ret.append(cuitIngresado);
		return ret.toString();
	}

	private static String messageUsarExistente(EntidadBeneficiariaBean entidad) {
		if(entidad.getCuit()==null)
			return ResourceManager.getMessageResource("msj.entidad.cuitDistinto.opcionUsarExistenteSinCuit");
		else
			return ResourceManager.getMessageResource("msj.entidad.cuitDistinto.opcionUsarExistente", entidad.getCuit());
	}
	
	private static List<LabelValue> createOptions(Collection<EntidadBeneficiariaBean> entidadesConElMismoNombre, EntidadBeneficiariaBean entidadNueva) {
		List<LabelValue> ret;
		
		if(entidadNueva==null) {
			ret = new ArrayList<LabelValue>(entidadesConElMismoNombre.size()+1);
		} else {
			ret = CollectionUtils.listWith(
					(LabelValue)new LabelValueImpl(
							ResourceManager.getMessageResource("msj.entidad.cuitDistinto.opcionCrearNueva")
							, OPCION_CREAR_NUEVA)
			);
		}
		for(EntidadBeneficiariaBean entidad : entidadesConElMismoNombre) {
			ret.add(new LabelValueImpl(messageUsarExistente(entidad), entidad.getId().toString()));
		}
		ret.add(
				new LabelValueImpl(
						ResourceManager.getMessageResource("msj.entidad.cuitDistinto.opcionError"), 
						OPCION_ERROR)
			);
		return ret;
	}
	@Override
	protected EntidadBeneficiariaBean solve(Matching<EntidadBeneficiariaBean> matching, String value) {
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