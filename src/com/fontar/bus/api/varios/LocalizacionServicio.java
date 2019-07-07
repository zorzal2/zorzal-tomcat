package com.fontar.bus.api.varios;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.LocalizacionBean;
/**
 * Servicios para la administraci�n de localizaciones. 
 * Los <b>localizaciones<(b> se emplean en entidades como empresas, proyectos, personas, etc. 
 * para registar datos como el domicilio, tel�fono, e-mail, etc.    
 */
public interface LocalizacionServicio {
	/**
	 * 	Obtiene todas los localizaciones registradas en el sistema
	 * @return
	 */
	public Collection getLocalizaciones();

	/**
	 * Registra en el sistema una nueva localizaci�n
	 * @param localizacionBean identificador de la nueva localizaci�n
	 * @return
	 */
	
	public LocalizacionBean storeLocalizacion(LocalizacionBean localizacionBean);

	/**
	 * Obtiene una entidad de localizaci�n en funci�n del identificador 
	 */
	public LocalizacionBean getLocalizacionById(String id);

	/**
	 * Registra en el sistema la actualizaci�n de una localizaci�n
	 * @param localizacionBean datos de la localizaci�n a actualizar.
	 * @return
	 */
	public LocalizacionBean updateLocalizacion(LocalizacionBean localizacionBean);

	/**
	 * Eliminar una localizaci�n del sistema.
	 * @param localizacionBean
	 * @return
	 */
	public void deleteLocalizacion(LocalizacionBean localizacion);
}
