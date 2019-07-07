package com.fontar.web.decorator.configuracion.emerix;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

import com.fontar.data.impl.domain.bean.CarteraBean;
import com.fontar.web.decorator.link.impl.EditarLink;

/**
 * Wrapper de Cartera
 * @author ttoth
 * @version 1.01, 21/12/06
 */
public class CarteraWrapper extends TableDecorator {

	public String getLinkEditar() throws UnsupportedEncodingException {
		CarteraBean lObject = (CarteraBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("CarteraEditar.do","app.alt.editarCartera",lObject.getId());
		editarLink.setSimplePermissionsRequired("CARTERA-EDITAR");
		return editarLink.displayValue();
	}
}
