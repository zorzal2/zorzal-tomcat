package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pragma.util.CollectionUtils;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.writer.Writer;
import com.pragma.xml.writer.WriterLibrary;

public abstract class ItemWriter implements Writer {
	//Overridable
	protected Map<String, String> attribute2PropertyMap() {
		return CollectionUtils.mapWith();
	}
	//Overridable	
	protected Collection<String> attributesToWrite() {
		return CollectionUtils.collectionWith();
	}
	public Element writeOn(Node node, Object bean, WriterLibrary library) {
		Element child = XMLUtils.appendChild(node, tagName());

		Map<String, String> map = attribute2PropertyMap();
		if(map!=null) {
			for(Entry<String, String> entry : map.entrySet()) {
				writeValue(child, entry.getKey(), bean, entry.getValue());
			}
		}
		Collection<String> attributeNames = attributesToWrite();
		if(attributeNames!=null) {
			for(String attributeName : attributeNames) {
				writeValue(child, bean, attributeName);
			}
		}
		
		return child;
	}
	protected void writeValue(Element node, Object item, String propertyName) {
		writeValue(node, propertyName, item, propertyName);
	}
	protected void writeValue(Element node, String attributeName, Object item, String propertyName) {
		String value;
		try {
			value = BeanUtils.getProperty(item, propertyName);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No se puede leer '"+propertyName+"' en el objeto "+item);
		}
		if(value!=null) {
			node.setAttribute(attributeName, value);
		}
	}
	
	protected abstract String tagName();
}
