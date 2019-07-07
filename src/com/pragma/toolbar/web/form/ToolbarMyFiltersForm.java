package com.pragma.toolbar.web.form;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import com.pragma.toolbar.NotImplementedException;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;

@Deprecated
public class ToolbarMyFiltersForm extends ValidatorForm implements ToolbarFiltersForm {

    private Map filters;
    ActionErrors reflectionException = new ActionErrors();
    private static Log log;
    private Map attributes = new HashMap();

    static{
	log = LogFactory.getLog(ToolbarMyFiltersForm.class);
    }

    /**
     * Used by Struts to populate the form
     * Setea un filtro del form.
     * Si esta el filtro le seteo el valor, si no, creo el filtro correspondiente,
     * le seteo el valor y lo agrego al mapa
     */
    public void setFilter(String propertyKey, Object value)
    {
	String strValue = (String) value;
	ConfigurableToolbarFilter filter = (ConfigurableToolbarFilter)filters.get(propertyKey);

	if (strValue != null && strValue.length() > 0)
	{
		Class typeClazz = filter.getClassType();
	    try {
		Object objValue = ConstructorUtils.invokeConstructor(typeClazz, value);
		filter.setValue(objValue);
	    } catch (IllegalAccessException ex) {
		log.error("Error in setFilter on  " + propertyKey + "  " + typeClazz.getName() ,ex);
	    } catch (InstantiationException ex) {
		log.error("Error in setFilter on  " + propertyKey + "  " + typeClazz.getName() ,ex);
	    } catch (NoSuchMethodException ex) {
		log.error("Error in setFilter on  " + propertyKey + "  " + typeClazz.getName() ,ex);
	    } catch (InvocationTargetException ex) {
		// goes to the form
		reflectionException.add(propertyKey,new ActionMessage("error.filter." + typeClazz.getName(),true));
	    }
	}
	else {
	    filter.setValue(null);
	}
    }

    /**
     * Used by Struts to get data
     */
    public Object getFilter(String propertyKey) {
	if(filters==null) return null;

	Object filter = filters.get(propertyKey);

	if (filter != null) {
	    return ((ConfigurableToolbarFilter) filter).getValue();
	} else{
	    return null;
	}
    }

    /**
     * Reset del formulario, vacia el hashmap de filtros
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {        
	reflectionException.clear();
    }
    
    public void clear() {
	filters=null;
    }

    /**
     * /GB:
     *
     * Método que queremos utilizar para validar el tipo de los distintos filtros 
     * que el usuario decida aplicar. (date con formato, int, float, etc)
     * 
     * cuando se hace submit del form, este método se ejecuta siempre 
     *
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	ActionErrors errors = super.validate(mapping, request);
	errors.add(reflectionException);
	return errors;
    }

    public Collection getFilters() {
	// TODO Auto-generated method stub
	return filters.values();
    }

    public Map getFiltersMap(){
	return this.filters;
    }

    public void setFiltersMap(Map filtersMap){
	this.filters = filtersMap;
    }

    public String getAttribute(String attribute) {
	if (this.attributes.containsKey(attribute)) {
	    return (String) this.attributes.get(attribute);
	} else {
	    return "";
	}
    }

    public void setAttribute(String attribute, String value) {
	this.attributes.put(attribute, value);
    }
    
	public void setFilter(String propertyKey, ConfigurableToolbarFilter filter) {
		throw new NotImplementedException();
	}
}
