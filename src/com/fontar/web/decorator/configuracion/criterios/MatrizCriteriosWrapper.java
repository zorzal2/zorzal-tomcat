package com.fontar.web.decorator.configuracion.criterios;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;

/**
 * Wrapper de Matrices de Criterios
 * @author gboaglio 
 */
public class MatrizCriteriosWrapper extends TableDecoratorSupport {
/**
	public String getLinkVisualizar() {
		MatrizCriterioBean matrizCriterioBean = (MatrizCriterioBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("/MatrizCriteriosVisualizar.do","app.alt.visualizarMatriz", matrizCriterioBean.getId());
		return visualizarLink.displayValue();
	}
*/
	public String getLinkEditar() throws UnsupportedEncodingException {
		MatrizCriterioBean matrizCriterioBean = (MatrizCriterioBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("MatrizCriteriosEditar.do","app.alt.editarMatriz", matrizCriterioBean.getId());
		editarLink.setSimplePermissionsRequired("CRITERIOS-EDITAR");
		return editarLink.displayValue();
	}
}