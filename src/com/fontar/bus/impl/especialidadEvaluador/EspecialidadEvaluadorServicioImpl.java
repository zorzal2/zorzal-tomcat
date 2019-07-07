package com.fontar.bus.impl.especialidadEvaluador;

import java.util.List;

import com.fontar.bus.api.especialidadEvaluador.EspecialidadEvaluadorServicio;
import com.fontar.data.api.dao.EspecialidadEvaluadorDAO;
import com.fontar.data.impl.assembler.EspecialidadEvaluadorAssembler;
import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.fontar.data.impl.domain.dto.EspecialidadEvaluadorDTO;

/**
 * Servicio para la administración de Especialidad Evaluador (armado y
 * modificación)
 * 
 * @author mdberra
 */
public class EspecialidadEvaluadorServicioImpl implements EspecialidadEvaluadorServicio {

	private EspecialidadEvaluadorBean eeBean;

	private EspecialidadEvaluadorDAO eeDAO;

	public Long agregarEspecialidadEvaluador(String codigo, String nombre) {
		// creo y lo lleno de datos
		eeBean = new EspecialidadEvaluadorBean();
		eeBean.setCodigo(codigo);
		eeBean.setNombre(nombre);

		// Agrego en la BD
		eeDAO.save(eeBean);

		return eeBean.getId();
	}

	public void modificarEspecialidadEvaluador(Long id, String codigo, String nombre) {
		// busco para modificarlo
		eeBean = eeDAO.read(id);

		eeBean.setCodigo(codigo);
		eeBean.setNombre(nombre);

		// Actualizo en la BD
		eeDAO.update(eeBean);
	}

	/**
	 * Retorna todos las entidades de evaluadores que existen
	 */
	public List<EspecialidadEvaluadorDTO> obtenerEspecialidadEvaluador() {
		// recupera todos los registros
		List<EspecialidadEvaluadorBean> eeBeanList = eeDAO.getAll();

		// transforma de List<Bean> a List<DTO>
		EspecialidadEvaluadorAssembler assembler = EspecialidadEvaluadorAssembler.getInstance();
		return assembler.buildDto(eeBeanList);
	}

	/**
	 * Retorna una entidad evaluadora
	 */
	public EspecialidadEvaluadorDTO obtenerEspecialidadEvaluador(Long id) {
		eeBean = eeDAO.read(id);

		// transforma de Bean a DTO
		EspecialidadEvaluadorAssembler assembler = EspecialidadEvaluadorAssembler.getInstance();
		return assembler.buildDto(eeBean);

		// Tambien podría escribirse
		// return assembler.buildDto(ffDAO.read(id));
	}
}