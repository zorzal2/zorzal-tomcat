package com.fontar.bus.impl.configuracion;

import java.util.ArrayList;
import java.util.List;

import com.fontar.bus.api.configuracion.EntidadEvaluadoraServicio;
import com.fontar.data.impl.assembler.EntidadEvaluadoraAssembler;
import com.fontar.data.impl.assembler.EvaluadorAssembler;
import com.fontar.data.impl.domain.bean.EntidadBancariaBean;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.pragma.bus.impl.GenericABMServiceImpl;

public class EntidadEvaluadoraServicioImpl extends GenericABMServiceImpl<EntidadEvaluadoraBean> implements
		EntidadEvaluadoraServicio {

	public EntidadEvaluadoraServicioImpl(Class<EntidadEvaluadoraBean> type) {
		super(type);
	}

	public Long agregarEntidadEvaluadora(String nroCBU, String nroCuenta, String nombreBeneficiario,
			Long idEntidadBancaria, EntidadBancariaBean entidadBancaria) {
		EntidadEvaluadoraBean eeBean = new EntidadEvaluadoraBean();
		eeBean.setNroCBU(nroCBU);
		eeBean.setNroCuenta(nroCuenta);
		eeBean.setNombreBeneficiario(nombreBeneficiario);
		eeBean.setIdEntidadBancaria(idEntidadBancaria);
		eeBean.setEntidadBancaria(entidadBancaria);

		// Agrego en la BD
		this.getDao().save(eeBean);

		return eeBean.getId();
	}

	public void modificarEntidadEvaluadora(Long id, String nroCBU, String nroCuenta, String nombreBeneficiario,
			Long idEntidadBancaria, EntidadBancariaBean entidadBancaria) {
		EntidadEvaluadoraBean eeBean = (EntidadEvaluadoraBean) this.getDao().read(id);

		eeBean.setNroCBU(nroCBU);
		eeBean.setNroCuenta(nroCuenta);
		eeBean.setNombreBeneficiario(nombreBeneficiario);
		eeBean.setIdEntidadBancaria(idEntidadBancaria);
		eeBean.setEntidadBancaria(entidadBancaria);

		// Actualizo en la BD
		this.getDao().update(eeBean);
	}

	/**
	 * Retorna las Entidades Evaluadoras que existen
	 */
	public List<? extends EntidadEvaluadoraDTO> obtenerEntidadEvaluadora() {
		// recupera todos los registros
		List<EntidadEvaluadoraBean> eeBeanList = this.getDao().getAll();

		// transforma de List<Bean> a List<DTO>
		EntidadEvaluadoraAssembler assembler = EntidadEvaluadoraAssembler.getInstance();
		return assembler.buildDto(eeBeanList);
	}

	/**
	 * Retorna una Entidad Evaluadora
	 */
	public EntidadEvaluadoraDTO obtenerEntidadEvaluadora(Long id) {
		EntidadEvaluadoraBean eeBean = (EntidadEvaluadoraBean) this.getDao().read(id);

		// transforma de Bean a DTO
		EntidadEvaluadoraAssembler assembler = EntidadEvaluadoraAssembler.getInstance();
		return assembler.buildDto(eeBean);
	}
	
	@SuppressWarnings("unchecked")
	public List<EvaluadorDTO> obtenerEvaluadoresDeLaEntidadEvaluadora(Long id) {
		EntidadEvaluadoraBean eeBean = (EntidadEvaluadoraBean) this.getDao().read(id);
		//Quito los evaluadores borrados
		List<EvaluadorDTO> evaluadoresDTO = new ArrayList<EvaluadorDTO>(eeBean.getEvaluadores().size());
		for(EvaluadorBean evaluador : eeBean.getEvaluadores()) {
			if(!evaluador.getBorrado()) {
				evaluadoresDTO.add(EvaluadorAssembler.getInstance().buildDto(evaluador));
			}
		}
		return evaluadoresDTO;
	}
}