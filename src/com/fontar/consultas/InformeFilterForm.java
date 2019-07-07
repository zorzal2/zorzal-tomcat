package com.fontar.consultas;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public abstract class InformeFilterForm extends ValidatorForm  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 386180250542223177L;

	
	private Map<String, Object> map;
	
	
	public InformeFilterForm(){
		this.map = new HashMap<String,Object>();
	}
	 /**
     * Used by Struts to populate the form
     * Setea un filtro del form.
     * Si esta el filtro le seteo el valor, si no, creo el filtro correspondiente,
     * le seteo el valor y lo agrego al mapa
     */
	public void setFilter(String propertyKey, Object value){
    	this.map.put(propertyKey,value);
    }
    
    public Object getFilter(String propertyKey){
    	return this.map.get(propertyKey);
    }
    
    public String getFilterAsString(String propertyKey){
    	return (String) this.map.get(propertyKey);
    }
    
	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.map.clear();
	}


}










