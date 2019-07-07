package com.fontar.web.decorator.administracion.paquete;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.dto.ProyectoFilaControlPaqueteDTO;
import com.fontar.web.decorator.TableDecoratorSupport;

/**
 * @author ssanchez
 */
public class ProyectoFilaControlPaqueteDTOWrapper extends TableDecoratorSupport {

	/**
	 * Muestra el campo checkbox para seleccionar el proyecto y hacer vaya a
	 * saber que cosa
	 */
	public String getSelectorProyecto() throws UnsupportedEncodingException {
		ProyectoFilaControlPaqueteDTO proyecto = (ProyectoFilaControlPaqueteDTO) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		String strChecked = new String();

		if (proyecto.getEsActivo()) {
			strChecked = "checked";
		}

		link.append("<input type='checkbox' name='proyectoArray' value='" + proyecto.getIdProyecto().toString() + "' "
				+ strChecked + ">");
		return link.toString();
	}
}
