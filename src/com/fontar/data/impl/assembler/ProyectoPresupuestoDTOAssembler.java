package com.fontar.data.impl.assembler;

import java.util.HashSet;
import java.util.Set;

import com.fontar.data.impl.assembler.proyecto.presupuesto.AssemblerFactory;
import com.fontar.data.impl.assembler.proyecto.presupuesto.plan.EtapaDTOAssembler;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanImp;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.EtapaDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;

/**
 * Assembler para crear un ProyectoPresupuestoDTO a partir de
 * ProyectoPresupuestoBean
 * 
 * @author ssanchez
 */
public class ProyectoPresupuestoDTOAssembler {

	/**
	 * Crea dto a partir del bean
	 */
	public static ProyectoPresupuestoDTO buildDto(ProyectoPresupuestoBean presupuestoBean) {
		ProyectoPresupuestoDTO dto = new ProyectoPresupuestoDTO();

		dto.setId(presupuestoBean.getId());
		dto.setMontoTotal(presupuestoBean.getMontoTotal());
		dto.setMontoSolicitado(presupuestoBean.getMontoSolicitado());
		dto.setFundamentacion(presupuestoBean.getFundamentacion());
		
		//Etapas
		Set<EtapaDTO> etapas = new HashSet<EtapaDTO>();
		for(EtapaBean etapa : presupuestoBean.getEtapas()) {
			etapas.add(EtapaDTOAssembler.buildDto(etapa));
		}
		dto.setEtapas(etapas);

		//Presupuesto por rubros
		Set<PresupuestoRubroDTO> presupuestoRubros = new HashSet<PresupuestoRubroDTO>();
		if(presupuestoBean.getPresupuestoRubros()!=null) {
			for(PresupuestoRubroBean presupuestoRubro : presupuestoBean.getPresupuestoRubros()) {
				presupuestoRubros.add(
					(PresupuestoRubroDTO) AssemblerFactory.forBean(presupuestoRubro).buildDto(presupuestoRubro)
				);
			}
		}
		dto.setPresupuestoRubros(presupuestoRubros);
		
		return dto;
	}

	/**
	 * Crea un bean a partir del Dto
	 */
	public static ProyectoPresupuestoBean updateBean(ProyectoPresupuestoDTO presupuestoDTO) {
		return updateBean(new ProyectoPresupuestoBean(), presupuestoDTO);
	}
	public static ProyectoPresupuestoBean updateBean(ProyectoPresupuestoBean presupuestoBean, ProyectoPresupuestoDTO presupuestoDTO) {

		presupuestoBean.setId(presupuestoDTO.getId());
		presupuestoBean.setMontoTotal(presupuestoDTO.getMontoTotal());
		presupuestoBean.setMontoSolicitado(presupuestoDTO.getMontoSolicitado());
		presupuestoBean.setFundamentacion(presupuestoDTO.getFundamentacion());

		//Etapas
		Set<EtapaBean> etapas;
		etapas = new HashSet<EtapaBean>();
		for(EtapaDTO etapa : presupuestoDTO.getEtapas()) {
			etapas.add(EtapaDTOAssembler.updateBean(etapa));
			presupuestoBean.setEtapas(etapas);
		}
		presupuestoBean.setEtapas(etapas);

		//Presupuesto por rubros
		PresupuestoRubroCollectionBean presupuestoRubros = new PresupuestoRubroCollectionBeanImp();
		for(PresupuestoRubroDTO presupuestoRubro : presupuestoDTO.getPresupuestoRubros()) {
			presupuestoRubros.add((PresupuestoRubroBean) AssemblerFactory.forDTO(presupuestoRubro).updateBean(presupuestoRubro));
		}
		presupuestoBean.setPresupuestoRubros(presupuestoRubros);
		return presupuestoBean;
	}
}
