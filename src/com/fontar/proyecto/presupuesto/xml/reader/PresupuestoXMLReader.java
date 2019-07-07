package com.fontar.proyecto.presupuesto.xml.reader;

import java.util.Collection;

import org.w3c.dom.Document;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanLazyProxy;
import com.fontar.proyecto.presupuesto.xml.reader.readers.PresupuestoReaderLibrary;
import com.pragma.xml.reader.XMLReader;

public class PresupuestoXMLReader {
	public static final PresupuestoXMLReader instance = new PresupuestoXMLReader();

	public PresupuestoRubroCollectionBean unserialize(Document document) {
		XMLReader reader = new XMLReader(document, PresupuestoReaderLibrary.instance);
		return (PresupuestoRubroCollectionBean)reader.getBean();
	}
	public Collection<PresupuestoRubroBean> unserialize(String xml) {
		/*XMLReader reader = new XMLReader(PresupuestoReaderLibrary.instance);
		reader.readString(xml);
		return reader.getBean();*/
		return new PresupuestoRubroCollectionBeanLazyProxy(xml);
	}
}
