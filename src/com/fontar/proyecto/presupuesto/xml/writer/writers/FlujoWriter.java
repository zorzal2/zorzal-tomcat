package com.fontar.proyecto.presupuesto.xml.writer.writers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.writer.Writer;
import com.pragma.xml.writer.WriterLibrary;

public class FlujoWriter implements Writer {

	public Element writeOn(Node node, Object flujoBean, WriterLibrary library) {
		FlujoBean flujo = (FlujoBean) flujoBean;

		Element flujoNode = XMLUtils.appendChild(node, Names.flujo);
		//Atributos
		flujoNode.setAttribute(Names.parte, flujo.getParte().toString());
		flujoNode.setAttribute(Names.contraparte, flujo.getContraparte().toString());
		flujoNode.setAttribute(Names.total, flujo.getTotal().toString());
		flujoNode.setAttribute(Names.periodo, flujo.getPeriodo());
		
		return flujoNode;
	}

}
