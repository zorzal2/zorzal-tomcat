package com.fontar.bus.api.configuracion;

import java.util.List;

import com.fontar.data.impl.domain.bean.EntidadBancariaBean;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;

/**
 * Servicios para administrar las distintas entidades evaluadoras del sistema.
 * Estas entidades son empleadas en relación a las evaluaciones de proyectos.   
 */
public interface EntidadEvaluadoraServicio {

	/**
	 * Incorpora una nueva entidad evaluadora en el sistema, los datos corresponden a datos bancarios para el pago de honorarios. 
	 * @param nroCBU número de CBU
	 * @param nroCuenta número de cuenta bancaria
	 * @param nombreBeneficiario nombre del beneficiario
	 * @param idEntidadBancaria identificador de Entidad Bancaria
	 * @param entidadBancaria Entidad Bancaria
	 * @return identificador de la nueva entidad evaluadora
	 */public abstract Long agregarEntidadEvaluadora(String nroCBU, String nroCuenta, String nombreBeneficiario,
			Long idEntidadBancaria, EntidadBancariaBean entidadBancaria);

	/**
	 * Actualiza los datos de una entidad evaluadora del sistema
	 * @param id identificador de la entidad evaluadora
	 * @param nroCBU número de CBU
	 * @param nroCuenta número de cuenta bancaria
	 * @param nombreBeneficiario nombre del beneficiario 
	 * @param idEntidadBancaria Identificador de Entidad Bancaria
	 * @param entidadBancaria Entidad Bancaria
	 */
	public abstract void modificarEntidadEvaluadora(Long id, String nroCBU, String nroCuenta,
			String nombreBeneficiario, Long idEntidadBancaria, EntidadBancariaBean entidadBancaria);

	/**
	 * Retorna todas las entidades evaluadoras existentes
	 * @return
	 */
	public abstract List<? extends EntidadEvaluadoraDTO> obtenerEntidadEvaluadora();

	/**
	 * Retorna la Entidad Evaluadora registrada bajo un identificador dado.
	 * @param id
	 * @return
	 */
	public abstract EntidadEvaluadoraDTO obtenerEntidadEvaluadora(Long id);
	/**
	 * Devuelve una lista de valuadores asociados a la entidad. Solo devuelve los
	 * evaluadores NO borrados.
	 * @param id
	 * @return
	 */
	public List<EvaluadorDTO> obtenerEvaluadoresDeLaEntidadEvaluadora(Long id);
}