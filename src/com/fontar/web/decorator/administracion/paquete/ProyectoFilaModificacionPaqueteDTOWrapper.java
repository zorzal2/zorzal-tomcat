package com.fontar.web.decorator.administracion.paquete;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.VisualizarLink;

public class ProyectoFilaModificacionPaqueteDTOWrapper extends TableDecoratorSupport {

	/**
	 * Muestra el campo checkbox para seleccionar el proyecto y hacer vaya a
	 * saber que cosa
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getSelectorProyecto() throws UnsupportedEncodingException {
		ProyectoFilaModificacionPaqueteDTO proyecto = (ProyectoFilaModificacionPaqueteDTO) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		String strChecked = new String();

		if (proyecto.getEsActivo()) {
			strChecked = "checked";
		}

		link.append("<input title='seleccionar/deseleccionar' type='checkbox' name='proyectoArray' value='" + proyecto.getIdProyecto().toString() + "' "
				+ strChecked + ">");
		return link.toString();
	}

	/**
	 * Visualiza los detalles de un Proyecto
	 * @author ssanchez
	 */
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		ProyectoFilaModificacionPaqueteDTO proyecto = (ProyectoFilaModificacionPaqueteDTO) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarProyecto.do", "app.alt.visualizarProyecto", proyecto.getIdProyecto());
		visualizarLink.setPermissionsByInstrumentoRequired(proyecto.getIdInstrumento(), "PROYECTOS-VISUALIZAR");
		return visualizarLink.displayValue();
	}
}
