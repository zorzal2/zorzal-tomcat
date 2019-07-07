package com.fontar.proyecto.presupuesto.xml.reader.readers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanImp;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.reader.Reader;
import com.pragma.xml.reader.ReaderLibrary;
import com.pragma.xml.reader.readers.DefaultReaderLibrary;

public class PresupuestoRubroCollectionReader implements Reader {

	@SuppressWarnings("unchecked")
	public Object readFrom(Node node, ReaderLibrary library) {
		Element presupuestoRubroCollectionNode = XMLUtils.findChild(node, Names.presupuestoRubros);
		//raiz
		if(presupuestoRubroCollectionNode==null)
			throw new IllegalArgumentException("El nodo no tiene un presupuesto");
		
		PresupuestoRubroCollectionBean presupuestoRubros = new PresupuestoRubroCollectionBeanImp();

		Reader collectionReader = DefaultReaderLibrary.instance.oneCanHandle(presupuestoRubroCollectionNode);
		for(PresupuestoRubroBean presupuestoRubro : 
			(Iterable<PresupuestoRubroBean>)collectionReader.readFrom(presupuestoRubroCollectionNode, library)) {
			presupuestoRubros.add(presupuestoRubro);
		}
		return presupuestoRubros;
	}

}
