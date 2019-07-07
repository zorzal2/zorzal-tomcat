package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros;

import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.assembler.proyecto.presupuesto.AssemblerFactory;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.flujo.FlujoDTOAssembler;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroGeneralDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.flujo.FlujoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

public class PresupuestoRubroGeneralDTOAssembler extends PresupuestoRubroDTOAssembler {

	public static final PresupuestoRubroGeneralDTOAssembler instance = new PresupuestoRubroGeneralDTOAssembler();

	public DTO buildDto(Bean bean) {
		if(bean==null) return null;
		PresupuestoRubroGeneralBean presupuestoRubroGeneralBean = (PresupuestoRubroGeneralBean) bean;
		PresupuestoRubroGeneralDTO dto = (PresupuestoRubroGeneralDTO)super.buildDto(presupuestoRubroGeneralBean);
		
		List<FlujoDTO> flujos = null;
		if(presupuestoRubroGeneralBean.getFlujoDeFondos()!=null) {
			flujos = new ArrayList<FlujoDTO>();
			for(FlujoBean flujo : presupuestoRubroGeneralBean.getFlujoDeFondos()) {
				flujos.add(FlujoDTOAssembler.buildDto(flujo));
			}
		}
		dto.setFlujoDeFondos(flujos);

		List<PresupuestoItemDTO> items = new ArrayList<PresupuestoItemDTO>();
		if(presupuestoRubroGeneralBean.getItems()!=null) {
			for(PresupuestoItemBean item : presupuestoRubroGeneralBean.getItems()) {
				items.add((PresupuestoItemDTO) AssemblerFactory.forBean(item).buildDto(item));
			}
		}
		dto.setItems(items);
		return dto;
	}
	
	public Bean updateBean(DTO dto) {
		if(dto==null) return null;
		PresupuestoRubroGeneralDTO presupuestoRubroGeneralDTO = (PresupuestoRubroGeneralDTO)dto;
		PresupuestoRubroGeneralBean bean = (PresupuestoRubroGeneralBean)super.updateBean(presupuestoRubroGeneralDTO);
		
		List<PresupuestoItemBean> items = new ArrayList<PresupuestoItemBean>();
		for(PresupuestoItemDTO item : presupuestoRubroGeneralDTO.getItems()) {
			items.add((PresupuestoItemBean) AssemblerFactory.forDTO(item).updateBean(item));
		}
		bean.setItems(items);

		List<FlujoBean> flujos = null;
		if(presupuestoRubroGeneralDTO.getFlujoDeFondos()!=null) {
			flujos = new ArrayList<FlujoBean>();
			for(FlujoDTO flujo : presupuestoRubroGeneralDTO.getFlujoDeFondos()) {
				flujos.add(FlujoDTOAssembler.updateBean(flujo));
			}
		}
		bean.setFlujoDeFondos(flujos);
		
		return bean;
	}

	@Override
	public PresupuestoRubroBean newBean() {
		return new PresupuestoRubroGeneralBean();
	}

	@Override
	public PresupuestoRubroDTO newDTO() {
		return new PresupuestoRubroGeneralDTO();
	}

	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoRubroGeneralBean;
	}

	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoRubroGeneralDTO;
	}
}
