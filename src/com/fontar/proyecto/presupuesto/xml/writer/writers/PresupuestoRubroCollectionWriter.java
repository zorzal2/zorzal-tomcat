package com.fontar.proyecto.presupuesto.xml.writer.writers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.UnknownException;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.writer.Writer;
import com.pragma.xml.writer.WriterLibrary;
import com.pragma.xml.writer.writers.IterableWriter;

public class PresupuestoRubroCollectionWriter implements Writer {

	public Element writeOn(Node node, Object presupuestoRubroCollectionBean, WriterLibrary library) {
		PresupuestoRubroCollectionBean presupuestoRubroCollection = (PresupuestoRubroCollectionBean) presupuestoRubroCollectionBean;

		//Presupuestos por rubro
		Element presupuestoRubrosNode = XMLUtils.appendChild(node, Names.presupuestoRubros);
		IterableWriter iterableWriter = new IterableWriter();
		if(!iterableWriter.canHandle(presupuestoRubroCollection)) throw new UnknownException("IterableWriter no puede serializar PresupuestoRubroCollection");
		iterableWriter.writeOn(presupuestoRubrosNode, presupuestoRubroCollection, library);
		return presupuestoRubrosNode;
	}

}
