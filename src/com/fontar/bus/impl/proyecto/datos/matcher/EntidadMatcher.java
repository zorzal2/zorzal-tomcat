package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.Map;
import java.util.Set;

import com.fontar.bus.api.entidad.EntidadBeneficiariaServicio;
import com.fontar.bus.impl.EntidadBeneficiariaDatosInsuficientesException;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.proyecto.datos.modelo.EmpresaInfo;
import com.fontar.proyecto.datos.modelo.LocalizacionInfo;
import com.pragma.util.StringUtil;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * Clase auxiliar que premite ubicar a las entidades a partir de objeto EntidadInfo.
 * @author llobeto
 *
 */
public class EntidadMatcher extends Matcher<EmpresaInfo, EntidadBeneficiariaBean> {
	
	private EntidadBeneficiariaServicio entidadBeneficiariaServicio;
	private JurisdiccionDAO jurisdiccionDao;
	
	public EntidadMatcher(EntidadBeneficiariaServicio entidadBeneficiariaServicio, JurisdiccionDAO jurisdiccionDao, Map<String, UserFeedbackResponse> feedbackResponses) {
		super(feedbackResponses);
		this.entidadBeneficiariaServicio = entidadBeneficiariaServicio;
		this.jurisdiccionDao = jurisdiccionDao;
	}
	
	protected EntidadBeneficiariaBean crearBean(EmpresaInfo empresa) throws EntidadBeneficiariaDatosInsuficientesException {
		if(StringUtil.isNotEmpty(empresa.getNombre())) {
			EntidadBeneficiariaBean eb = new EntidadBeneficiariaBean();
			eb.getEntidad().setCuit(empresa.getCuit());
			eb.getEntidad().setDenominacion(empresa.getNombre());
			eb.getEntidad().setLocalizacion(toBean(empresa.getLocalizacionActividad()));
			
			eb.setLocalizacionEconomica(toBean(empresa.getLocalizacion()));
			if(empresa.getEsEmpresa()!=null) {
				eb.setTipo(
						empresa.getEsEmpresa() ?
								"EMPRESA" : 
									"NO_EMPRESA"
				);
			} else {
				throw new EntidadBeneficiariaDatosInsuficientesException(empresa.getNombre());
			}
			eb.setTipoEmpresa((empresa.getSubTipoEmpresaEnum()==null) ? null : empresa.getSubTipoEmpresaEnum().getName());
			eb.setTipoNoEmpresa((empresa.getSubTipoNoEmpresaEnum()==null) ? null : empresa.getSubTipoNoEmpresaEnum().getName());
			return eb;
		} else {
			throw new EntidadBeneficiariaDatosInsuficientesException(empresa.getNombre()); 
		}
	}
	
	/**
	 * Convierte a Localizacion un DomicilioInfo
	 * @param localizacion
	 * @return
	 */
	private LocalizacionBean toBean(LocalizacionInfo localizacion) {
		LocalizacionBean bean = new LocalizacionBean();
		bean.setCodigoPostal(localizacion.getCp());
		bean.setDireccion(localizacion.getDireccion());
		bean.setEmail(localizacion.getEmail());
		bean.setFax(localizacion.getFax());
		Long idJurisdiccion = localizacion.getIdJurisdiccion();
		bean.setIdJurisdiccion(idJurisdiccion);
		if(idJurisdiccion != null)bean.setJurisdiccion(jurisdiccionDao.read(idJurisdiccion));
		bean.setLocalidad(localizacion.getLocalidad());
		bean.setPaginaWeb(localizacion.getWeb());
		bean.setPais(localizacion.getPais());
		bean.setTelefono(localizacion.getTelefono());
		return bean;
	}

	@Override
	protected boolean isEmpty(EmpresaInfo empresa) {
		return empresa.isEmpty();
	}

	@Override
	protected AbstractUserFeedbackRequest<EntidadBeneficiariaBean> nombreDistinto(String nombre, EntidadBeneficiariaBean empresaCoincidentePorCuit) {
		return new NombreDeEntidadDistinto(nombre, empresaCoincidentePorCuit);
	}

	@Override
	protected AbstractUserFeedbackRequest<EntidadBeneficiariaBean> cuitDistinto(Set<EntidadBeneficiariaBean> entidadesCoincidentes, EntidadBeneficiariaBean bean, String cuit) {
		return new CuitDeEntidadDistinto(entidadesCoincidentes, bean, cuit);
	}

	@Override
	protected AbstractUserFeedbackRequest<EntidadBeneficiariaBean> faltaNombre(EntidadBeneficiariaBean entidadBean) {
		return new FaltaNombreDeEntidad(entidadBean);
	}

	@Override
	protected Set<EntidadBeneficiariaBean> obtenerBeansCoincidentes(String nombre, String cuit) {
		return entidadBeneficiariaServicio.obtenerEntidadesCoincidentes(nombre, cuit);
	}

	@Override
	protected String cuitInfo(EmpresaInfo empresa) {
		return empresa.getCuit();
	}

	@Override
	protected String cuitBean(EntidadBeneficiariaBean entidad) {
		return entidad.getCuit();
	}

	@Override
	protected String nombreInfo(EmpresaInfo empresa) {
		return empresa.getNombre();
	}

	@Override
	protected String nombreBean(EntidadBeneficiariaBean empresa) {
		return empresa.getDenominacion();
	}
}
