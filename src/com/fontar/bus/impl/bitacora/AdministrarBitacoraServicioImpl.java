package com.fontar.bus.impl.bitacora;

import java.util.Collection;
import java.util.Date;

import com.fontar.bus.api.bitacora.AdministrarBitacoraServicio;
import com.fontar.data.api.assembler.BitacoraDTOAssembler;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.impl.assembler.BitacoraAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompositeBitacoraDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * Servicio para administrar Bitacoras
 */

public class AdministrarBitacoraServicioImpl implements AdministrarBitacoraServicio {

	private BitacoraDAO bitacoraDAO;
	
	
	public BitacoraDAO getDao() {
		return bitacoraDAO;
	}

	public void setDao(BitacoraDAO dao) {
		this.bitacoraDAO = dao;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.bitacora.AdministrarBitacoraServicio#getBitacora(java.lang.Long,
	 * com.fontar.data.api.assembler.BitacoraDTOAssembler)
	 */
	public CompositeBitacoraDTO getBitacora(Long proyectoId, BitacoraDTOAssembler assembler) {
		Collection collection = bitacoraDAO.findByProyecto(proyectoId);
		return assembler.buildDTO(collection);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.bitacora.AdministrarBitacoraServicio#cargarBitacora(java.lang.Long,
	 * java.util.Date, java.lang.String)
	 */
	public void cargarBitacora(Long idProyecto, String tema, Date fechaAsunto, String descripcion) {
		BitacoraBuilder bitacoraBuilder = new BitacoraBuilder();
		BitacoraBean bitacora = bitacoraBuilder.bitacoraManual(idProyecto, tema, fechaAsunto, descripcion);
		bitacoraDAO.save(bitacora);
	}
	
	/**
	 * Actualiza los datos de la bitacora
	 * @param bitacora
	 */
	public void actualizarBitacora(Long idBitacora, String tema, Date fechaAsunto, String descripcion) {
		BitacoraBean bean = bitacoraDAO.read(idBitacora);
		
		bean.setTema(tema);
		bean.setDescripcion(descripcion);
		bean.setFechaAsunto(fechaAsunto);
		bean.setFechaRegistro(DateTimeUtil.getDate());
		bean.setIdUsuario(this.getUserId());
	}

	/**
	 * 
	 * @param idBitacora
	 * @return
	 */
	public BitacoraDTO obtenerBitacora(Long idBitacora) {
		BitacoraBean bean = bitacoraDAO.read(idBitacora);
		return BitacoraAssembler.buildDto(bean);
	}
	
	/**
	 * Borra los datos de la bitacora
	 * @param bitacora
	 */
	public void eliminarBitacoraManual(Long idBitacora) {		
		BitacoraBean bean = bitacoraDAO.read(idBitacora);
		
		if (bean.esManual()){
			bitacoraDAO.delete(bean);
		}
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
		
	}

}
