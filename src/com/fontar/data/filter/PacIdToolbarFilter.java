package com.fontar.data.filter;

import com.pragma.toolbar.data.filter.HQLPropertyBasedToolbarFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;

public class PacIdToolbarFilter extends HQLPropertyBasedToolbarFilter {

	private static final long serialVersionUID = 1L;

	public PacIdToolbarFilter(Long value) {
		super("idPacItem", DefaultFilterTypeLibrary.NUMBER_EQUAL, value, Long.class);
	}
	public PacIdToolbarFilter() {
		super("idPacItem", DefaultFilterTypeLibrary.NUMBER_EQUAL, Long.class);
	}
}
