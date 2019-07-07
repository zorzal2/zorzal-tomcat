package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;


public class PresupuestoItemConsejeroTecnologicoDTOAssembler extends PresupuestoItemRecursoHumanoDTOAssembler {
	public static final PresupuestoItemConsejeroTecnologicoDTOAssembler instance = new PresupuestoItemConsejeroTecnologicoDTOAssembler(); 
	@Override
	public DTO buildDto(Bean bean) {
		PresupuestoItemConsejeroTecnologicoDTO dto = (PresupuestoItemConsejeroTecnologicoDTO)super.buildDto(bean);
		dto.setCategoria(((PresupuestoItemConsejeroTecnologicoBean)bean).getCategoria());
		dto.setTitulo(((PresupuestoItemConsejeroTecnologicoBean)bean).getTitulo());
		return dto;
	}
	public boolean canHandle(Bean bean) {
		return bean instanceof PresupuestoItemConsejeroTecnologicoBean;
	}
	public boolean canHandle(DTO dto) {
		return dto instanceof PresupuestoItemConsejeroTecnologicoDTO;
	}
	@Override
	public Bean updateBean(DTO dto) {
		PresupuestoItemConsejeroTecnologicoBean bean = (PresupuestoItemConsejeroTecnologicoBean)super.updateBean(dto);
		bean.setCategoria(((PresupuestoItemConsejeroTecnologicoDTO)dto).getCategoria());
		bean.setTitulo(((PresupuestoItemConsejeroTecnologicoDTO)dto).getTitulo());
		return bean;
	}
	@Override
	protected PresupuestoItemDTO newDTO() {
		return new PresupuestoItemConsejeroTecnologicoDTO();
	}
	@Override
	protected PresupuestoItemBean newBean() {
		return new PresupuestoItemConsejeroTecnologicoBean();
	}
}