package com.fontar.web.decorator.configuracion.regiones;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.RegionBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Regiones
 * @author ssanchez
 * @version 1.01, 20/12/06
 */
public class RegionWrapper extends TableDecoratorSupport {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		RegionBean lObject = (RegionBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("RegionVisualizar.do","app.alt.visualizarRegion",lObject.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		RegionBean lObject = (RegionBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("RegionEditar.do","app.alt.editarRegion",lObject.getId());
		editarLink.setSimplePermissionsRequired("REGIONES-EDITAR");
		return editarLink.displayValue();
	}
}
