package com.fontar.data.impl.assembler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.pragma.data.assembler.GenericAssembler;

/**
 * 
 * @author gboaglio
 * 
 */

public class ProyectoEdicionAssembler extends GenericAssembler<ProyectoDatosBean, ProyectoEdicionDTO> {
	private static class SingletonHolder {
		private static ProyectoEdicionAssembler instance = new ProyectoEdicionAssembler();
	}

	public static ProyectoEdicionAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(ProyectoDatosBean datosBean) {

		Map map = new HashMap();

		map.put("titulo", datosBean.getTitulo());
		map.put("resumen", datosBean.getResumen());
		map.put("objetivo", datosBean.getObjetivo());
		map.put("palabraClave", datosBean.getPalabraClave());

		Integer duracion = datosBean.getDuracion();
		if (duracion != null) {
			map.put("duracion", duracion.toString());
		}

		map.put("observacion", datosBean.getObservacion());
		map.put("idPersonaLegal", datosBean.getIdPersonaLegal());

		PersonaBean personaLegal = datosBean.getPersonaLegal();
		if (personaLegal != null) {
			map.put("txtPersonaLegal", personaLegal.getNombre());
		}

		map.put("idPersonaRepresentante", datosBean.getIdPersonaRepresentante());

		PersonaBean personaRepresentante = datosBean.getPersonaRepresentante();
		if (personaRepresentante != null) {
			map.put("txtPersonaRepresentante", personaRepresentante.getNombre());
		}

		map.put("idPersonaDirector", datosBean.getIdPersonaDirector());

		PersonaBean personaDirector = datosBean.getPersonaDirector();
		if (personaDirector != null) {
			map.put("txtPersonaDirector", personaDirector.getNombre());
		}

		map.put("idTipoProyecto", datosBean.getIdTipoProyecto());

		CiiuBean ciiu = datosBean.getCiiu();
		if (ciiu != null) {
			map.put("idCiiu", ciiu.getId());
			map.put("txtCiiu", ciiu.getNombre());
			map.put("codigoCiiu", ciiu.getCodigo());
		}

		map.put("idEntidadBancaria", datosBean.getIdEntidadBancaria());
		map.put("descripcionEntidadBancaria", datosBean.getDescripcionEntidadBancaria());

		BigDecimal porcentajeTasaInteres = datosBean.getPorcentajeTasaInteres();
		if (porcentajeTasaInteres != null) {
			map.put("porcentajeTasaInteres", porcentajeTasaInteres);
		}

		BigDecimal tir = datosBean.getTir();
		if (tir != null) {
			map.put("tir", tir);
		}

		BigDecimal van = datosBean.getVan();
		if (van != null) {
			map.put("van", van);
		}

		EntidadBeneficiariaBean entidadBeneficiariaBean = datosBean.getEntidadBeneficiaria();

		if (entidadBeneficiariaBean != null) {
			map.put("idEntidadBeneficiaria", entidadBeneficiariaBean.getId());
			map.put("txtEntidadBeneficiaria", entidadBeneficiariaBean.getDenominacion());
		}

		LocalizacionBean localizacionBean = datosBean.getLocalizacion();

		if (localizacionBean != null) {
			LocalizacionDTO localizacionDto = LocalizacionAssembler.getInstance().buildDto(localizacionBean);

			map.put("localizacion", localizacionDto);
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(ProyectoEdicionDTO dto) {
		Map map = new HashMap();

		map.put("titulo", dto.getTitulo());
		map.put("resumen", dto.getResumen());
		map.put("objetivo", dto.getObjetivo());
		map.put("palabraClave", dto.getPalabraClave());
		map.put("duracion", dto.getDuracion());
		map.put("observacion", dto.getObservacion());
		map.put("idPersonaLegal", dto.getIdPersonaLegal());
		map.put("idPersonaRepresentante", dto.getIdPersonaRepresentante());
		map.put("idPersonaDirector", dto.getIdPersonaDirector());
		map.put("idTipoProyecto", dto.getIdTipoProyecto());
		map.put("idCiiu", dto.getIdCiiu());
		map.put("tir", dto.getTir());
		map.put("van", dto.getVan());
		map.put("idEntidadBancaria", dto.getIdEntidadBancaria());
		map.put("descripcionEntidadBancaria", dto.getDescripcionEntidadBancaria());
		map.put("porcentajeTasaInteres", dto.getPorcentajeTasaInteres());
		map.put("idEntidadBeneficiaria", dto.getIdEntidadBeneficiaria());

		return map;
	}

	@Override
	protected ProyectoDatosBean newBeanInstance() {
		return new ProyectoDatosBean();
	}

	@Override
	protected ProyectoEdicionDTO newDtoInstance() {
		return new ProyectoEdicionDTO();
	}

}
