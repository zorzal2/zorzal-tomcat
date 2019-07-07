package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.EntidadBeneficiariaDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

public class EntidadBeneficiariaAssembler extends GenericAssembler<EntidadBeneficiariaBean, EntidadBeneficiariaDTO> {

	private static class SingletonHolder {
		private static EntidadBeneficiariaAssembler instance = new EntidadBeneficiariaAssembler();
	}

	public static EntidadBeneficiariaAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(EntidadBeneficiariaBean bean) {

		Map map = new HashMap();

		LocalizacionBean localizacionBean = bean.getLocalizacionEconomica();
		LocalizacionDTO localizacion = new LocalizacionDTO();

		if (localizacionBean != null) {
			localizacion = LocalizacionAssembler.getInstance().buildDto(localizacionBean);
		}

		if (bean.getEmpleoPermanente() == null) {

			map.put("id", bean.getId());
			map.put("tipoNoEmpresa", bean.getTipoNoEmpresa());
			map.put("tipo", bean.getTipo());
			map.put("localizacionEconomica", localizacion);
			map.put("idLocalizacionEconomica", bean.getIdLocalizacionEconomica());
		}
		else {

			EmpleoPermanenteDTO empleoPermanenteDTO = EmpleoPermanenteAssembler.getInstance().buildDto(bean.getEmpleoPermanente());

			map.put("id", bean.getId());
			map.put("tipoEmpresa", bean.getTipoEmpresa());
			map.put("tipoNoEmpresa", bean.getTipoNoEmpresa());
			map.put("tipo", bean.getTipo());
			map.put("fechaInicioActividad", DateTimeUtil.formatDate(bean.getFechaInicioActividad()));
			map.put("codigoCategorizacion", bean.getCodigoCategorizacion());
			map.put("descEmpresa", bean.getDescEmpresa());
			map.put("numeroConstitucion", bean.getNumeroConstitucion());
			map.put("localizacionEconomica", localizacion);
			map.put("idLocalizacionEconomica", bean.getIdLocalizacionEconomica());
			map.put("empleoPermanente", empleoPermanenteDTO);
			map.put("idEmpleoPermanente", bean.getIdEmpleoPermanente());
			map.put("idCiiu", bean.getIdCiiu());
		}

		map.put("descEmpresa", bean.getDescEmpresa());
		map.put("emerix", bean.getEmerix());
		map.put("idTributaria", bean.getIdTributaria());
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(EntidadBeneficiariaDTO dto) {

		Map map = new HashMap();

		LocalizacionBean localizacion = LocalizacionAssembler.getInstance().buildBean(dto.getLocalizacionEconomica());
		if (dto.getEmpleoPermanente() == null) {
			map.put("id", dto.getId());
			map.put("tipoNoEmpresa", dto.getTipoNoEmpresa());
			map.put("tipo", dto.getTipo());
			map.put("localizacionEconomica", localizacion);
			map.put("idLocalizacionEconomica", dto.getIdLocalizacionEconomica());

		}
		else {

			EmpleoPermanenteBean empleoPermanenteBean = EmpleoPermanenteAssembler.getInstance().buildBean(dto.getEmpleoPermanente());

			map.put("id", dto.getId());
			map.put("tipoEmpresa", dto.getTipoEmpresa());
			map.put("tipo", dto.getTipo());
			map.put("fechaInicioActividad", dto.getFechaInicioActividad());
			map.put("codigoCategorizacion", dto.getCodigoCategorizacion());
			map.put("descEmpresa", dto.getDescEmpresa());
			map.put("numeroConstitucion", dto.getNumeroConstitucion());
			map.put("localizacionEconomica", localizacion);
			map.put("idLocalizacionEconomica", dto.getIdLocalizacionEconomica());
			map.put("empleoPermanente", empleoPermanenteBean);
			map.put("idEmpleoPermanente", dto.getIdEmpleoPermanente());
			map.put("idCiiu", dto.getIdCiiu());
		}

		if (!StringUtil.isEmpty(dto.getEmerix()))
			map.put("emerix", new Long(dto.getEmerix()));
		else 
			map.put("emerix", null);
		
		map.put("idTributaria", dto.getIdTributaria());
		return map;
	}

	@Override
	protected EntidadBeneficiariaBean newBeanInstance() {
		return new EntidadBeneficiariaBean();
	}

	@Override
	protected EntidadBeneficiariaDTO newDtoInstance() {
		return new EntidadBeneficiariaDTO();
	}

}
