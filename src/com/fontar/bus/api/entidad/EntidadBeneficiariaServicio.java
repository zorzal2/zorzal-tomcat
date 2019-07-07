package com.fontar.bus.api.entidad;

import java.util.Set;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.pragma.bus.api.GenericABMService;

/**
 * Servicios para adminsitrar las entidades beneficiarias del sistema.
 */
public interface EntidadBeneficiariaServicio extends GenericABMService<EntidadBeneficiariaBean> {
	public void create(EntidadBeneficiariaBean entidadBeneficiaria);
	public EntidadBeneficiariaBean createOrUpdate(EntidadBeneficiariaBean entidadBeneficiaria);
	/**
	 * Devuelve aquellas entidades que tienen el nombre o el cuit buscado.
	 * @param entidadBeneficiaria
	 * @param cuit
	 * @return
	 */
	public Set<EntidadBeneficiariaBean> obtenerEntidadesCoincidentes(String denominacion, String cuit);
}
