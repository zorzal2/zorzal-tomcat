package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.List;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class NombreDeEntidadDistinto extends AbstractUserFeedbackRequest<EntidadBeneficiariaBean> {

	private static final String OPCION_ACTUALIZAR_DATOS = "opcionActualizarDatos";
	private static final String OPCION_CONSERVAR_DATOS = "opcionConservarDatos";
	
	String nombreIngresado;
	
	public NombreDeEntidadDistinto(String nombreIngresado, EntidadBeneficiariaBean entidadCoincidente) {
		super(
				generateUniqueId(nombreIngresado, entidadCoincidente), 
				ResourceManager.getMessageResource(
						"msj.entidad.nombreDistinto.pregunta", 
						entidadCoincidente.getCuit(), 
						entidadCoincidente.getDenominacion(),
						nombreIngresado
					),
				OPCION_CONSERVAR_DATOS,
				createOptions(nombreIngresado, entidadCoincidente)	
			);
		this.nombreIngresado = nombreIngresado;
	}

	private static String generateUniqueId(String nombreIngresado, EntidadBeneficiariaBean entidadCoincidente) {
		return "NombreDeEntidadDistinto::"+entidadCoincidente.getId()+"::"+((nombreIngresado==null)? 0 : nombreIngresado.hashCode());
	}
	
	protected static List<LabelValue> createOptions(String nombreIngresado, EntidadBeneficiariaBean entidadCoincidente) {
			return CollectionUtils.listWith(
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.entidad.nombreDistinto.opcionActualizarDatos"), OPCION_ACTUALIZAR_DATOS),
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.entidad.nombreDistinto.opcionConservarDatos"), OPCION_CONSERVAR_DATOS),
					(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.entidad.nombreDistinto.opcionError"), OPCION_ERROR)
				);
	}

	@Override
	protected EntidadBeneficiariaBean solve(Matching<EntidadBeneficiariaBean> matching, String value) {
		if(value.equals(OPCION_ERROR)) return null;
		else {
			EntidadBeneficiariaBean ret = CollectionUtils.getAny(matching.getCandidates());
			if(value.equals(OPCION_CONSERVAR_DATOS)) return ret;
			else {
				ret.setDenominacion(nombreIngresado);
				return ret;
			}
		}
	}
}