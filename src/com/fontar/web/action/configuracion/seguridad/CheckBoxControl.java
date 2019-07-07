package com.fontar.web.action.configuracion.seguridad;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class CheckBoxControl {
	

	
	private Collection selectableItems;
	
	@SuppressWarnings("unchecked")
	public CheckBoxControl(String[] selected, Collection items){
		this.selectableItems = new LinkedList();
		Iterator iterator = items.iterator();
		while(iterator.hasNext()){
			Object item = iterator.next();
			SelectableItem selectableItem = new SelectableItem();
			selectableItem.setSelected( this.contains( item.toString() , selected));
			selectableItem.setSelectable( item );
			this.selectableItems.add( selectableItem );
		}
	}

	
	@SuppressWarnings("unchecked")
	public CheckBoxControl(Set permisos, Collection items){
		this.selectableItems = new LinkedList();
		for (Object item : items) {
			SelectableItem selectableItem = new SelectableItem();
			selectableItem.setSelected( permisos.contains( item ));
			selectableItem.setSelectable( item );
			this.selectableItems.add( selectableItem );
		}
	}


	private Boolean contains(String objectId, String[] objects){
		for (String object : objects) {
			if(object.equals( objectId))
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
	}
	
	public Collection getSelectableItems() {
		return selectableItems;
	}

	public void setSelectableItems(Collection selectableItems) {
		this.selectableItems = selectableItems;
	}
	
		
}
