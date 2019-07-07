/*
 * ToolbarFiltersForm.java
 *
 * Created on 1 de noviembre de 2006, 09:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.web.form;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.FilterType;

/**
 * Formulario para almacenar los filtros rápidos de la Toolbar
 * Pragma Consultores S.A.
 *
 * @author fferrara
 */
public class ToolbarAllFiltersForm extends ValidatorForm implements ToolbarFiltersForm {
    
    /** Creates a new instance of ToolbarFiltersForm */
    private Map filters = new HashMap();
    ActionErrors reflectionException = new ActionErrors();
    
    private static Log log;
    
    static{
        log = LogFactory.getLog(ToolbarAllFiltersForm.class);
    }
    
    public ToolbarAllFiltersForm() {
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
        if (strValue != null && strValue.length() > 0)
        {
        	// propertyKey = controlType&propertyName&filterTypeName
            StringTokenizer st = new StringTokenizer(propertyKey,"%20");

            String controlType = (String)st.nextElement();
            String propertyName = (String)st.nextElement();
            String filterTypeName = (String)st.nextElement();
            String classNameType = (String)st.nextElement();
            
            String filterExpression = "";
            
            if (st.hasMoreElements()){
            	filterExpression = (String)st.nextElement();	
            }
            
            ToolbarQueryFilter aFilter;

            Class typeClazz = null;
            Object objValue = null;
            try {
				typeClazz = Class.forName(classNameType);
				objValue = ConstructorUtils.invokeConstructor(typeClazz, value);

            } catch (IllegalAccessException ex) {
                log.error("Error in setFilter on  " + propertyName + "  " + classNameType ,ex);
                return;
            } catch (InstantiationException ex) {
                log.error("Error in setFilter on  " + propertyName + "  " + classNameType ,ex);
                return;
            } catch (ClassNotFoundException ex) {
                log.error("Error in setFilter on  " + propertyName + "  " + classNameType ,ex);
                return;
            } catch (NoSuchMethodException ex) {
                log.error("Error in setFilter on  " + propertyName + "  " + classNameType ,ex);
                return;
            } catch (InvocationTargetException ex) {
                // goes to the form
                reflectionException.add(propertyKey,new ActionMessage("error.filter." + classNameType,true));
                return;
            }
            if (filters.containsKey(propertyKey)){
                ((ConfigurableToolbarFilter)filters.get(propertyKey)).setValue(objValue);
            } else {
                ConfigurableToolbarFilter filter;
                FilterType filterType = DefaultToolbarConfig.getFilterTypeLibrary().fromName(filterTypeName);
                
                filter = ConfigurableToolbarFilterBuilder.build(
                		propertyName,
                		filterType,
                		objValue,
                		typeClazz,
                		filterExpression
                );
                setFilter(propertyKey, filter);
            }
        }
        else {
            if (filters.containsKey(propertyKey)) {
               filters.remove(propertyKey);
            }
        }
    }


	public void setFilter(String propertyKey, ConfigurableToolbarFilter filter) {
		this.filters.put(propertyKey, filter);
	}
    
    /**
     * Used by Struts to get data
     */
    public Object getFilter(String propertyKey) {
        Object filter = filters.get(propertyKey);
        
        if (filter != null) {
            return ((ConfigurableToolbarFilter) filter).getValue();
        } else{
            return null;
        }
    }
    
    /**
     * Return Filters applied
     */
    public Collection getFilters() {
        return filters.values();
    }
    
    /**
     * Reset del formulario, vacia el hashmap de filtros
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {        
        reflectionException.clear();
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


	public void setFiltersMap(Map filtersMap) {
		filters = filtersMap;
	}


	public Map getFiltersMap() {
		return filters;
	}
    

	
}
