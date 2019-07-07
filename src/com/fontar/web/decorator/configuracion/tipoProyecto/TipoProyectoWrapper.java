package com.fontar.web.decorator.configuracion.tipoProyecto;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper Tipo Proyectos
 * @author ttoth, ssanchez
 * @version 1.01, 20/12/06
 */
public class TipoProyectoWrapper extends TableDecoratorSupport {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		TipoProyectoBean lObject = (TipoProyectoBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("TipoProyectoVisualizar.do","app.alt.visualizarTipoProyecto",lObject.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		TipoProyectoBean lObject = (TipoProyectoBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("TipoProyectoEditar.do","app.alt.editarTipoProyecto",lObject.getId());
		editarLink.setSimplePermissionsRequired("TIPOSPROYECTO-EDITAR");
		return editarLink.displayValue();
	}
}
