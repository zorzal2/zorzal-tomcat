package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoDesembolsoBean;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;

public class ProyectoDesembolsoAssembler {

	private static class SingletonHolder {
		private static ProyectoDesembolsoAssembler instance = new ProyectoDesembolsoAssembler();
	}

	public static ProyectoDesembolsoAssembler getInstance() {
		return SingletonHolder.instance;

	}

	public ProyectoDesembolsoDTO buildDto(ProyectoDesembolsoBean bean) {
		ProyectoDesembolsoDTO dto = new ProyectoDesembolsoDTO();
		dto.setId(bean.getId());
		dto.setConcepto(bean.getConcepto());
		dto.setMontoOriginal(bean.getMontoOriginal());
		dto.setMontoAutorizado(bean.getMontoAutorizado());
		dto.setMontoDesembolsado(bean.getMontoDesembolsado());
		dto.setPlazo(bean.getPlazo());
		dto.setCodigoEstado(bean.getCodigoEstado());
		dto.setFechaPago(bean.getFechaPago());
		dto.setProyecto(ProyectoAssembler.buildDto(bean.getProyecto()));
		dto.setIdProyecto(bean.getIdProyecto());
		dto.setObjetivo(bean.getObjetivo());
		dto.setEsAnticipo(bean.getEsAnticipo());
		dto.setIdSeguimientoDeAutorizacion(bean.getIdSeguimientoDeAutorizacion());
		return dto;
	}
	
	public ProyectoDesembolsoBean buildBean(ProyectoDesembolsoDTO dto) {
		ProyectoDesembolsoBean bean = new ProyectoDesembolsoBean();
		bean.setId(dto.getId());
		bean.setConcepto(dto.getConcepto());
		bean.setMontoOriginal(dto.getMontoOriginal());
		bean.setMontoAutorizado(dto.getMontoAutorizado());
		bean.setMontoDesembolsado(dto.getMontoDesembolsado());
		bean.setPlazo(dto.getPlazo());
		bean.setCodigoEstado(dto.getCodigoEstado());
		bean.setFechaPago(dto.getFechaPago());
		bean.setIdProyecto(dto.getIdProyecto());
		bean.setObjetivo(dto.getObjetivo());
		bean.setEsAnticipo(dto.getEsAnticipo());
		bean.setIdSeguimientoDeAutorizacion(dto.getIdSeguimientoDeAutorizacion());
		return bean;
	}

	public ProyectoDesembolsoBean updateBeanNotNull(ProyectoDesembolsoDTO dto, ProyectoDesembolsoBean bean) {
		if(dto.getConcepto()!=null)	bean.setConcepto(dto.getConcepto());
		if(dto.getMontoOriginal()!=null) bean.setMontoOriginal(dto.getMontoOriginal());
		if(dto.getMontoAutorizado()!=null) bean.setMontoAutorizado(dto.getMontoAutorizado());
		if(dto.getMontoDesembolsado()!=null) bean.setMontoDesembolsado(dto.getMontoDesembolsado());
		if(dto.getPlazo()!=null) bean.setPlazo(dto.getPlazo());
		if(dto.getCodigoEstado()!=null) bean.setCodigoEstado(dto.getCodigoEstado());
		if(dto.getFechaPago()!=null) bean.setFechaPago(dto.getFechaPago());
		if(dto.getObjetivo()!=null) bean.setObjetivo(dto.getObjetivo());
		if(dto.getEsAnticipo()!=null) bean.setEsAnticipo(dto.getEsAnticipo());
		if(dto.getIdSeguimientoDeAutorizacion()!=null) bean.setIdSeguimientoDeAutorizacion(dto.getIdSeguimientoDeAutorizacion());
		return bean;
	}

	public List<ProyectoDesembolsoDTO> buildDto(List<ProyectoDesembolsoBean> beans) {
		List<ProyectoDesembolsoDTO> list = new ArrayList<ProyectoDesembolsoDTO>(beans.size());
		for(ProyectoDesembolsoBean bean : beans) {
			list.add(buildDto(bean));
		}
		return list;
	}

}
