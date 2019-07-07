package com.fontar.bus.api.configuracion;

import java.util.Collection;

import com.fontar.data.api.dao.InstitucionesDAO;
import com.fontar.data.impl.domain.bean.EntidadBean;

/**
 * Servicios para la administracion de instituciones (Entidades Beneficiarias, Bancaria, Evaluadora, etc) 
 */
public interface InstitucionServicio {

	/**
	 * Asigna el DAO para el manejo de instituciones  
	 * @param institucionDao
	 */
	public abstract void setInstitucionDao(InstitucionesDAO institucionDao);

	/**
	 * Obitene todas las instituciones  
	 * @return
	 */
	public abstract Collection getInstituciones();

	/**
	 * Persiste una entidad
	 * @param nuevaEntidad
	 * @return
	 */
	public abstract EntidadBean storeInstitucion(EntidadBean nuevaEntidad);

	/**
	 * Obtiene una institucion a partir de su identificador.
	 * @param id
	 * @return
	 */
	public abstract EntidadBean getInstitucionById(String id);
	
	/**
	 * Actualiza una institucion con los datos del Bean.
	 * @param institucionBean
	 * @return
	 */
	public abstract EntidadBean updateInstitucion(EntidadBean institucionBean);
	
	/**
	 * Eliminar una institucion.
	 * @param institucionBean
	 */
	public abstract void deleteInstitucion(EntidadBean institucionBean);

}