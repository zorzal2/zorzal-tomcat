package com.fontar.data.impl.dao.hibernate;

import org.apache.commons.collections.Transformer;

public class LongToStringTransformer implements Transformer {

	public Object transform(Object object) {
		Long longObject = (Long) object;
		return String.valueOf( longObject);
	}

}
