package com.fontar.web.decorator.configuracion.jurisdicciones;

import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Jurisdicciones
 * @author ssanchez
 */
public class JurisdiccionesWrapper extends TableDecoratorSupport {

	public String getLinkVisualizar() {
		JurisdiccionBean jurisdiccionBean = (JurisdiccionBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("#","app.alt.visualizarJurisdiccion",jurisdiccionBean.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() {
		JurisdiccionBean jurisdiccionBean = (JurisdiccionBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("JurisdiccionEditar.do","app.alt.editarJurisdiccion",jurisdiccionBean.getId());
		editarLink.setSimplePermissionsRequired("JURISDICCIONES-EDITAR");  
		return editarLink.displayValue();
	}
}
