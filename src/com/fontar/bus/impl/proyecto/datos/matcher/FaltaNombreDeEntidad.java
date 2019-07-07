package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.List;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.util.ResourceManager;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.LabelValueImpl;

public class FaltaNombreDeEntidad extends AbstractUserFeedbackRequest<EntidadBeneficiariaBean> {
	private static final String OPCION_USAR_EXISTENTE = "opcionUsarExistente";
	
	public FaltaNombreDeEntidad(EntidadBeneficiariaBean entidad) {
		super(
				generateUniqueId(entidad), 
				ResourceManager.getMessageResource(
						"msj.entidad.faltaNombre.pregunta", 
						entidad.getCuit(), 
						entidad.getDenominacion()
					),
				OPCION_USAR_EXISTENTE,
				createOptions(entidad)	
			);
	}

	private static String generateUniqueId(EntidadBeneficiariaBean entidad) {
		return "FaltaNombreDeEntidad::"+entidad.getId();
	}
	
	private static List<LabelValue> createOptions(EntidadBeneficiariaBean entidad) {
		return CollectionUtils.listWith(
				(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.entidad.faltaNombre.opcionUsarExistente"), OPCION_USAR_EXISTENTE),
				(LabelValue)new LabelValueImpl(ResourceManager.getMessageResource("msj.entidad.faltaNombre.opcionError"), OPCION_ERROR)			
			);
	}

	@Override
	protected EntidadBeneficiariaBean solve(Matching<EntidadBeneficiariaBean> matching, String value) {
		if(value.equals(OPCION_USAR_EXISTENTE)) {
			return CollectionUtils.getAny(matching.getCandidates());
		} else {
			return null;
		}
	}
}