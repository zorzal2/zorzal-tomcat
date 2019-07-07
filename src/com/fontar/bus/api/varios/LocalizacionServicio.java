package com.fontar.bus.api.varios;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.LocalizacionBean;
/**
 * Servicios para la administración de localizaciones. 
 * Los <b>localizaciones<(b> se emplean en entidades como empresas, proyectos, personas, etc. 
 * para registar datos como el domicilio, teléfono, e-mail, etc.    
 */
public interface LocalizacionServicio {
	/**
	 * 	Obtiene todas los localizaciones registradas en el sistema
	 * @return
	 */
	public Collection getLocalizaciones();

	/**
	 * Registra en el sistema una nueva localización
	 * @param localizacionBean identificador de la nueva localización
	 * @return
	 */
	
	public LocalizacionBean storeLocalizacion(LocalizacionBean localizacionBean);

	/**
	 * Obtiene una entidad de localización en función del identificador 
	 */
	public LocalizacionBean getLocalizacionById(String id);

	/**
	 * Registra en el sistema la actualización de una localización
	 * @param localizacionBean datos de la localización a actualizar.
	 * @return
	 */
	public LocalizacionBean updateLocalizacion(LocalizacionBean localizacionBean);

	/**
	 * Eliminar una localización del sistema.
	 * @param localizacionBean
	 * @return
	 */
	public void deleteLocalizacion(LocalizacionBean localizacion);
}
