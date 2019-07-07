package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

public class DesembolsoUFFAAssembler extends GenericAssembler<DesembolsoUFFABean, DesembolsoUFFADTO> {

	private static class SingletonHolder {
		private static DesembolsoUFFAAssembler instance = new DesembolsoUFFAAssembler();
	}

	public static DesembolsoUFFAAssembler getInstance() {
		return SingletonHolder.instance;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBean2DtoMap(DesembolsoUFFABean bean) {

		Map map = new HashMap();

		map.put("id", bean.getId());
		map.put("idPac", bean.getIdPac());
		map.put("pac", PacAssembler.getInstance().buildDto(bean.getPac()));
		map.put("ordenPago", bean.getOrdenPago());
		map.put("cuota", bean.getCuota());
		map.put("fechaPedido", StringUtil.formatDate(bean.getFechaPedido()));
		map.put("montoDesembolsado", bean.getMontoDesembolsado());
		if (bean.getMontoPago() != null) {
			map.put("montoPago", bean.getMontoPago());
		}
		if (bean.getFechaPago() != null) {
			map.put("fechaPago", StringUtil.formatDate(bean.getFechaPago()));
		}
		map.put("esAnticipo", bean.getEsAnticipo());
		map.put("moneda", MonedaAssembler.getInstance().buildDto(bean.getMoneda()));
		map.put("idMoneda", bean.getIdMoneda());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDto2BeanMap(DesembolsoUFFADTO dto) {

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("idPac", dto.getIdPac());
		if (dto.getPac() != null) {
			map.put("pac", PacAssembler.getInstance().buildBean(dto.getPac()));
		}
		map.put("ordenPago", dto.getOrdenPago());
		map.put("cuota", dto.getCuota());
		map.put("fechaPedido", DateTimeUtil.formatDate(dto.getFechaPedido()));
		map.put("montoDesembolsado", dto.getMontoDesembolsado());
		if (dto.getFechaPago() != null) {
			map.put("fechaPago", DateTimeUtil.formatDate(dto.getFechaPago()));
		}
		if (dto.getMontoPago() != null) {
			map.put("montoPago", dto.getMontoPago());
		}
		map.put("esAnticipo", dto.getEsAnticipo());
		if (dto.getMoneda() != null) {
			map.put("moneda", MonedaAssembler.getInstance().buildBean(dto.getMoneda()));
		}
		map.put("idMoneda", dto.getIdMoneda());
		return map;
	}

	@Override
	protected DesembolsoUFFABean newBeanInstance() {
		return new DesembolsoUFFABean();
	}

	@Override
	protected DesembolsoUFFADTO newDtoInstance() {
		return new DesembolsoUFFADTO();
	}

}
