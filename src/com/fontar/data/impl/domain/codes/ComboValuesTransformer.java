package com.fontar.data.impl.domain.codes;

import org.apache.commons.collections.Transformer;
import org.apache.struts.util.LabelValueBean;

import com.fontar.data.api.domain.codes.Enumerable;

/**
 * Tranforma cada valor de una enumeración al formato LabelValueBean para cargar
 * los valores de los combos
 * @author mrouaux,ssanchez
 * @version 1.00, 06/12/06
 */
public class ComboValuesTransformer implements Transformer {

	public Object transform(Object object) {

		if (object instanceof Enumerable) {
			Enumerable enumeracion = (Enumerable) object;

			LabelValueBean labelValueBean = new LabelValueBean(enumeracion.getDescripcion(), enumeracion.getName());

			return labelValueBean;
		}

		return object;
	}
}
