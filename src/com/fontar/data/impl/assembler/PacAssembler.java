package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.RubroDTOAssembler;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.dto.PacDTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;

public class PacAssembler extends GenericAssembler<PacBean, PacDTO> {

	private static class SingletonHolder {
		private static PacAssembler instance = new PacAssembler();
	}

	public static PacAssembler getInstance() {
		return SingletonHolder.instance;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(PacBean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("codigoEstado", bean.getCodigoEstado());
		map.put("descripcion", bean.getDescripcion());
		map.put("etapa", bean.getEtapa());
		map.put("fecha", DateTimeUtil.formatDate(bean.getFecha()));
		map.put("fechaEstado", DateTimeUtil.formatDate(bean.getFechaEstado()));
		map.put("idProyecto", bean.getIdProyecto());
		map.put("idRubro", bean.getIdRubro());
		map.put("idTipoAdquisicion", bean.getIdTipoAdquisicion());
		map.put("item", bean.getItem());
		map.put("montoFontar", bean.getMontoFontar());
		if(bean.getMontoAdjudicacion()!=null)map.put("montoAdjudicacion", bean.getMontoAdjudicacion());
		if(bean.getMontoDesembolsado()!=null)map.put("montoDesembolsado", bean.getMontoDesembolsado());
		map.put("rubro", RubroDTOAssembler.instance.buildDto(bean.getRubro()));
		map.put("tipoAdquisicion", TipoAdquisicionAssembler.getInstance().buildDto(bean.getTipoAdquisicion()));
		map.put("esPatrimonio", bean.getEsPatrimonio());
		if(bean.getIdMoneda()!=null)map.put("idMoneda", bean.getIdMoneda());
		if(bean.getMoneda()!=null)map.put("descripcionMoneda", bean.getMoneda().getDescripcion());

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(PacDTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigoEstado", dto.getCodigoEstado());
		map.put("descripcion", dto.getDescripcion());
		map.put("etapa", dto.getEtapa());
		map.put("fecha", DateTimeUtil.formatDate(dto.getFecha()));
		map.put("fechaEstado", DateTimeUtil.formatDate(dto.getFecha()));
		map.put("idProyecto", dto.getIdProyecto());
		map.put("idRubro", dto.getIdRubro());
		map.put("idTipoAdquisicion", dto.getIdTipoAdquisicion());
		map.put("item", dto.getItem());
		map.put("montoFontar", dto.getMontoFontar());
		if(dto.getMontoAdjudicacion()!=null) map.put("montoAdjudicacion", dto.getMontoAdjudicacion());
		if(dto.getMontoDesembolsado()!=null) map.put("montoDesembolsado", dto.getMontoDesembolsado());
		if(dto.getIdMoneda()!=null)map.put("idMoneda", dto.getIdMoneda());
		if (dto.getRubro() != null) {
			map.put("rubro", RubroDTOAssembler.instance.updateBean(dto.getRubro()));
		}
		if (dto.getTipoAdquisicion() != null) {
			map.put("tipoAdquisicion", TipoAdquisicionAssembler.getInstance().buildBean(dto.getTipoAdquisicion()));
		}
		map.put("esPatrimonio", dto.getEsPatrimonio());
		return map;
	}

	@Override
	protected PacBean newBeanInstance() {
		return new PacBean();
	}

	@Override
	protected PacDTO newDtoInstance() {
		return new PacDTO();
	}

}
