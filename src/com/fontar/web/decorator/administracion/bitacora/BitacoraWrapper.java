package com.fontar.web.decorator.administracion.bitacora;

import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.pragma.util.DecoratorUtil;

public class BitacoraWrapper extends TableDecoratorSupport {

	public String getLinkBorrar() {
		BitacoraBean bitacora = (BitacoraBean) this.getCurrentRowObject();
		String link = "";

		if (bitacora.esManual()) {
			link = DecoratorUtil.getLinkBorrado("BitacoraBorrar.do", "app.alt.borrarBitacora", bitacora.getId(), true);
		}

		return link;
	}

	public String getLinkEditar() {
		BitacoraBean bitacora = (BitacoraBean) this.getCurrentRowObject();

		String link = "";

		if (bitacora.esManual()) {
			link = DecoratorUtil.getLinkEdicion("BitacoraEditar.do", "app.alt.editarBitacora", bitacora.getId());
		}

		return link;
	}

	public String getLinkVisualizar() {
		BitacoraBean bitacora = (BitacoraBean) this.getCurrentRowObject();
		Long idInstrumento = (bitacora.getProyecto()!=null)
									? bitacora.getProyecto().getIdInstrumento()
									: null;

		if (bitacora.esAdmision()) {
			VisualizarLink visualizarLink = new VisualizarLink("VisualizarAdmisibilidadProyecto.do","app.alt.visualizarAdmisibilidad", bitacora.getId());
			visualizarLink.setPermissionsByInstrumentoRequired(idInstrumento, "PROYECTOS-VISUALIZAR");
			return visualizarLink.displayValue();
		}

		if (bitacora.tieneEvaluacionAsociada()) {
			VisualizarLink visualizarLink = new VisualizarLink("VisualizarEvaluacion.do","app.alt.visualizarEvaluacion", bitacora.idEvaluacionAsociada());
			visualizarLink.setPermissionsByInstrumentoRequired(idInstrumento, "EVALUACIONES-VISUALIZAR");
			return visualizarLink.displayValue();
		}
		
		if (bitacora.esProyectoPaquete()) {
			VisualizarLink visualizarLink = new VisualizarLink("VisualizarPaqueteBitacora.do","app.alt.visualizarPaquete", bitacora.getId());
			visualizarLink.setPermissionsByInstrumentoRequired(idInstrumento, "PAQUETES-VISUALIZAR");
			return visualizarLink.displayValue();
		}

		if (bitacora.esSeguimiento()) {
			if (bitacora.getIdSeguimiento() == null) {return "";}
			else {
				VisualizarLink visualizarLink = new VisualizarLink("SeguimientoVisualizar.do","app.alt.visualizarSeguimiento", bitacora.getIdSeguimiento());
				visualizarLink.setPermissionsByInstrumentoRequired(idInstrumento, "SEGUIMIENTOS-VISUALIZAR");
				return visualizarLink.displayValue();
			}
		}

		if (bitacora.esPresupuesto()) {
			String link = "";
			//Solo muestra el presupuesto si no es una IdeaProyecto
			if (bitacora.getProyecto().getIdInstrumento()!= null) {				
				VisualizarLink visualizarLink = new VisualizarLink("VisualizarPresupuesto.do?idProyecto="+bitacora.getIdProyecto(),"app.alt.visualizarPresupuesto", bitacora.getId());
				visualizarLink.setPermissionsByInstrumentoRequired(idInstrumento, "PROYECTOS-VISUALIZAR");
				link = visualizarLink.displayValue();
			}
			return link;
		}

		if (bitacora.esAnalisisDeReconsideracion()) {
			String link = "";
			VisualizarLink visualizarLink = new VisualizarLink("VisualizarReconsideracion.do","app.alt.visualizarReconsideracion", bitacora.getId());
			link = visualizarLink.displayValue();
			return link;
		}
		return "";
	}
}