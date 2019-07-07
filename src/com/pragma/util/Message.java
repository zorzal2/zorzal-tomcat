package com.pragma.util;

import java.util.ArrayList;

public class Message {
	Messages messages;
	String key;
	ArrayList<Object> params = new ArrayList<Object>();
	public Message(Messages messages, String key, Object... params) {
		this.messages = messages;
		this.key = key;
		for(Object param : params) this.params.add(param);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ArrayList<Object> getParams() {
		return params;
	}
	public void addParam(Object param) {
		this.params.add(param);
	}
	public void setParams(ArrayList<Object> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		String[] stringsParams = new String[params.size()];
		for(int i = 0; i<params.size(); i++) {
			stringsParams[i] = String.valueOf(params.get(i));
		}
		return messages.getString(key, stringsParams);
	}
	
	
}
