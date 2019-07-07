package com.fontar.proyecto.presupuesto.xml.writer.writers;

import com.fontar.proyecto.presupuesto.xml.Names;

public class ItemDirectorExpertoWriter extends ItemRecursoHumanoWriter {

	@Override
	public String tagName() {
		return Names.presupuestoItemDirectorExperto;
	}

}
