package com.fontar.proyecto.datos.message;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.pragma.util.Messages;

public class ProyectosExcelMessages extends Messages {
	private ProyectosExcelMessages() {
		super(ResourceBundle.getBundle("resources.ProyectosExcelMessages"));
	}

	private static class InstanceHolder {
		public static ProyectosExcelMessages instance = new ProyectosExcelMessages(); 
	}
	
	public static ProyectosExcelMessages instance() {
		return InstanceHolder.instance;
	}
	
	public Iterable<String> all(String prefix) {
		List<String> list = new ArrayList<String>();
		for(String key : all()) {
			if(
					key.length()>prefix.length() &&
					key.startsWith(prefix) &&
					(key.lastIndexOf('.')==prefix.length() || (prefix.length()==0 && !key.contains(".")))
				) 
				list.add(key);
		}
		return list;
	}
}
