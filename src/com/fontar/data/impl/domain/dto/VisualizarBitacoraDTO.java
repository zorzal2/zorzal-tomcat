package com.fontar.data.impl.domain.dto;

import java.util.Collection;

public class VisualizarBitacoraDTO implements CompositeBitacoraDTO {

	private Collection items;

	public VisualizarBitacoraDTO(Collection items) {
		super();
		// TODO Auto-generated constructor stub
		this.items = items;
	}

	public Collection getItems() {
		return items;
	}

	public void setItems(Collection items) {
		this.items = items;
	}
	
	
}
