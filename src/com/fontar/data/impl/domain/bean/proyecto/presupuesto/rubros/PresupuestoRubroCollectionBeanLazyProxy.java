package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros;

import java.util.Collection;
import java.util.Iterator;

import com.fontar.proyecto.presupuesto.xml.reader.readers.PresupuestoReaderLibrary;
import com.pragma.util.StringUtil;
import com.pragma.xml.reader.XMLReader;


public class PresupuestoRubroCollectionBeanLazyProxy implements PresupuestoRubroCollectionBean {
	
	private PresupuestoRubroCollectionBean collection = null;
	private String xmlString = null;
	
	

	public PresupuestoRubroCollectionBeanLazyProxy(String xmlString) {
		this.xmlString = xmlString;
	}

	public PresupuestoRubroCollectionBeanLazyProxy() {
		this.collection = new PresupuestoRubroCollectionBeanImp();
	}

	public int size() {
		resolveCollection();
		return collection.size();
	}

	public boolean isEmpty() {
		resolveCollection();
		return collection.isEmpty();
	}

	public boolean contains(Object o) {
		resolveCollection();
		return collection.contains(o);
	}

	public Iterator<PresupuestoRubroBean> iterator() {
		resolveCollection();
		return collection.iterator();
	}

	public Object[] toArray() {
		resolveCollection();
		return collection.toArray();
	}

	public <T> T[] toArray(T[] a) {
		resolveCollection();
		return collection.toArray(a);
	}

	public boolean add(PresupuestoRubroBean o) {
		resolveCollection();
		return collection.add(o);
	}

	public boolean remove(Object o) {
		resolveCollection();
		return collection.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		resolveCollection();
		return collection.containsAll(c);
	}

	public boolean addAll(Collection<? extends PresupuestoRubroBean> c) {
		resolveCollection();
		return collection.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		resolveCollection();
		return collection.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		resolveCollection();
		return collection.retainAll(c);
	}

	public void clear() {
		resolveCollection();
		collection.clear();
	}

	private void resolveCollection() {
		if(collection==null) {
			if(StringUtil.isEmpty(xmlString)) {
				collection = new PresupuestoRubroCollectionBeanImp();
			} else {
				//Cargo el objeto
				XMLReader reader = new XMLReader(PresupuestoReaderLibrary.instance);
				reader.readString(xmlString);
				collection = reader.getBean();
			}
		}
	}
}
