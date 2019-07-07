package com.fontar.web.decorator.instrumentos.ventanilla.ideaproyecto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.util.StringUtil;

/**
 * Cargar, editar, eliminar de pagina Ideas Proyecto
 * @author ssanchez 
 */
public class IdeaProyectoWrapper extends BaseEntityWorkFlowWrapper {


	public String getLinkVisualizar() throws UnsupportedEncodingException {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("IdeaProyectoVisualizar.do", "app.alt.visualizarIdeaProyecto", ideaProyectoBean.getId());
		visualizarLink.setSimplePermissionsRequired("IDEASPROYECTO-VISUALIZAR");
		return visualizarLink.displayValue();
	}

	@Deprecated
	public String getLinkCargar() throws UnsupportedEncodingException {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		if (ideaProyectoBean.estaElegible()) {
			link.append("<a href=\"IdeaProyectoCargarVentanilla.do?");
			link.append("id=" + URLEncoder.encode(String.valueOf(ideaProyectoBean.getId()), "UTF-8"));
			link.append("\"><img src='images/cargarproyecto.gif' title=");
			link.append("'" + ResourceManager.getAltResource("app.alt.cargarProyecto") + "' border=0 /></a>");
		}

		return link.toString();
	}

	public String getLinkEdicion() throws UnsupportedEncodingException {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("IdeaProyectoEditar.do","app.alt.editarIdeaProyecto",ideaProyectoBean.getId());
		editarLink.setSimplePermissionsRequired("IDEASPROYECTO-EDITAR");
		return editarLink.displayValue();
	}
	/**
	 * Devuelve el instrumento recomendado por la última evaluación por junta.
	 * Si no hubo evaluaciones devuelve una cadena vacía. Si el texto es demasiado
	 * largo lo trunca.
	 * @return
	 */
	public String getInstrumentoRecomendado() {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) this.getCurrentRowObject();
		
		String recomendacion = ideaProyectoBean.getInstrumentoRecomendado();
		if(recomendacion==null) return "";
		return StringUtil.trimToSize(recomendacion, 30);
	}
}
