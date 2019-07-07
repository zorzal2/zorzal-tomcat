package com.fontar.data.filter;

import com.pragma.toolbar.data.filter.HQLPropertyBasedToolbarFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;

public class ProyectIdToolbarFilter extends HQLPropertyBasedToolbarFilter {

	private static final long serialVersionUID = 1L;

	public ProyectIdToolbarFilter(Long value) {
		super("idProyecto", DefaultFilterTypeLibrary.NUMBER_EQUAL, value, Long.class);
	}
	public ProyectIdToolbarFilter() {
		super("idProyecto", DefaultFilterTypeLibrary.NUMBER_EQUAL, Long.class);
	}
}
