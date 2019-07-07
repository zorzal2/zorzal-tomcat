package com.fontar.web.decorator.configuracion.emerix;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

import com.fontar.data.impl.domain.bean.TributariaBean;
import com.fontar.web.decorator.link.impl.EditarLink;

/**
 * Wrapper de Tributaria
 * @author ttoth
 * @version 1.01, 21/12/06
 */
public class TributariaWrapper extends TableDecorator {

	public String getLinkEditar() throws UnsupportedEncodingException {
		TributariaBean lObject = (TributariaBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("TributariaEditar.do","app.alt.editarTributaria",lObject.getId());
		editarLink.setSimplePermissionsRequired("CONDICIONTRIBUTARIA-EDITAR");
		return editarLink.displayValue();
	}
}
