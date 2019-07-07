package com.pragma.web.userfeedback;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa un pedido de feedback para el usuario. El usuario deberá elegir una entre
 * un conjunto de opciones disponibles.
 * @author llobeto
 *
 */
public abstract class UserFeedbackRequest implements Collection<LabelValue> {
	
	private String id;
	private String message;
	private Map<String, LabelValue> options;
	private LabelValue suggestedDefaultValue;
	/**
	 * Crea una confirmacion.
	 * @param id Identificador unico.
	 * @param messageKey Mensaje para el usuario
	 * @param messageParameters Parametros del mensaje para el usuario
	 */
	public UserFeedbackRequest(String id, String message, String suggestedDefaultValue, List<LabelValue> options) {
		this.id = id;
		this.message = message;
		this.options = new LinkedHashMap<String, LabelValue>();
		for(LabelValue labelValue : options) {
			this.options.put(labelValue.getValue(), labelValue);
			if(labelValue.getValue().equals(suggestedDefaultValue))
				this.suggestedDefaultValue = labelValue;
		}
	}

	/**
	 * Devuelve el mensaje que vera el usuario.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	public LabelValue getSuggestedDefaultValue() {
		return suggestedDefaultValue;
	}

	public String getId() {
		return id;
	}

	public Collection<LabelValue> getOptions() {
		return options.values();
	}
	
	public LabelValue getUserSelection(UserFeedbackResponse response) {
		return options.get(response.getUserSelectionValue());
	}

	@Override
	public boolean equals(Object obj) {
		return
				(obj instanceof UserFeedbackRequest)
			&&	getId().equals(((UserFeedbackRequest)obj).getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	public Iterator<LabelValue> iterator() {
		return getOptions().iterator();
	}

	public boolean add(LabelValue o) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends LabelValue> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(Object o) {
		return getOptions().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getOptions().containsAll(c);
	}

	public boolean isEmpty() {
		return getOptions().isEmpty();
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return getOptions().size();
	}

	public Object[] toArray() {
		return getOptions().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return getOptions().toArray(a);
	}
	
}