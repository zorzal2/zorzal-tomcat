package com.fontar.proyecto.presupuesto.xml.writer;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.proyecto.presupuesto.xml.writer.writers.PresupuestoWriterLibrary;
import com.pragma.xml.writer.XMLWriter;

public class PresupuestoXMLSerializer {
	public static final PresupuestoXMLSerializer instance = new PresupuestoXMLSerializer();
	
	public String serialize(PresupuestoRubroCollectionBean presupuesto) {
		XMLWriter writer = new XMLWriter(presupuesto, PresupuestoWriterLibrary.instance);
		return writer.asXML();
	}
}
