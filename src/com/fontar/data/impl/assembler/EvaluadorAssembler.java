package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class EvaluadorAssembler extends GenericAssembler<EvaluadorBean, EvaluadorDTO> {

	private static class SingletonHolder {
		private static EvaluadorAssembler instance = new EvaluadorAssembler();
	}

	public static EvaluadorAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBean2DtoMap(EvaluadorBean bean) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", bean.getId());
		map.put("fechaIngreso", DateTimeUtil.formatDate(bean.getFechaIngreso()));
		map.put("idEspecialidadPrimaria", bean.getIdEspecialidadPrimaria());
		map.put("idEspecialidadSecundaria", bean.getIdEspecialidadSecundaria());
		map.put("tituloPosgrado", bean.getTituloPosgrado());
		ConfiguracionServicio servicio = (ConfiguracionServicio) ContextUtil.getBean("configuracionService");
		EntidadEvaluadoraBean entidad = servicio.obtenerEntidadDesemp( bean );
		if(entidad!=null) {
			map.put("idEntidadesDesemp", entidad.getId() );
		    map.put("txtEntidadesDesemp", entidad.getDenominacion());
		}
		map.put("cuit", bean.getPersona().getCuit());
		map.put("nombre", bean.getPersona().getNombre());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDto2BeanMap(EvaluadorDTO dto) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", dto.getId());
		map.put("fechaIngreso", dto.getFechaIngreso());
		map.put("idEspecialidadPrimaria", dto.getIdEspecialidadPrimaria());
		map.put("idEspecialidadSecundaria", dto.getIdEspecialidadSecundaria());
		map.put("tituloPosgrado", dto.getTituloPosgrado());
		
		return map;
	}

	@Override
	protected EvaluadorBean newBeanInstance() {
		return new EvaluadorBean();
	}

	@Override
	protected EvaluadorDTO newDtoInstance() {
		return new EvaluadorDTO();
	}
}