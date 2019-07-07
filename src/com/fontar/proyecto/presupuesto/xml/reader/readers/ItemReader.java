package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.pragma.util.CollectionUtils;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.reader.Reader;
import com.pragma.xml.reader.ReaderLibrary;

public abstract class ItemReader implements Reader {

	//Overridable
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.mapWith();
	}
	//Overridable	
	protected Collection<String> attributesToRead() {
		return CollectionUtils.collectionWith();
	}
	protected abstract PresupuestoItemBean emptyInstance();
	
	public Object readFrom(Node node, ReaderLibrary library) {
		Element element = XMLUtils.asElement(node);
		PresupuestoItemBean item = emptyInstance();

		Map<String, String> map = attribute2PropertyMap();
		if(map!=null) {
			for(Entry<String, String> entry : map.entrySet()) {
				readValue(element, entry.getKey(), item, entry.getValue());
			}
		}
		Collection<String> attributeNames = attributesToRead();
		if(attributeNames!=null) {
			for(String attributeName : attributeNames) {
				readValue(element, attributeName, item);
			}
		}
		return item;
	}
	
	protected void readValue(Element node, String attributeName, PresupuestoItemBean item, String propertyName) {
		//Element bienNode = XMLUtils.asElement(node);
		//bien.setNombre(bienNode.getAttribute(Names.nombre));
		try {
			if(node.hasAttribute(attributeName)) {
				BeanUtils.setProperty(item, propertyName, node.getAttribute(attributeName));
			} else {
				BeanUtils.setProperty(item, propertyName, null);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("No se pudo leer el atributo.");
		}
	}
	protected void readValue(Element node, String attributeName, PresupuestoItemBean item) {
		readValue(node, attributeName, item, attributeName);
	}
	protected void readValues(Element node, PresupuestoItemBean item, String...attributeNames) {
		for(String attributeName : attributeNames) {
			readValue(node, attributeName, item);
		}
	}
}
