package com.fontar.proyecto.presupuesto.xml.reader.readers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.reader.Reader;
import com.pragma.xml.reader.ReaderLibrary;

public class FlujoReader implements Reader {

	public Object readFrom(Node node, ReaderLibrary library) {
		Element flujoNode = XMLUtils.asElement(node);
		FlujoBean flujo = new FlujoBean();
		//Atributos
		flujo.setParte(Double.valueOf(flujoNode.getAttribute(Names.parte)));
		flujo.setContraparte(Double.valueOf(flujoNode.getAttribute(Names.contraparte)));
		flujo.setTotal(Double.valueOf(flujoNode.getAttribute(Names.total)));
		flujo.setPeriodo(flujoNode.getAttribute(Names.periodo));
		
		return flujo;
	}
}
