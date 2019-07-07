/*
 * ConfigurationXMLHelper.java
 *
 * Created on November 7, 2006, 6:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.config.default_config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pragma.hibernate.BeforeQueryInvocationHandler;
import com.pragma.toolbar.ToolbarConfigurationException;
import com.pragma.toolbar.data.DataType;
import com.pragma.toolbar.data.ToolbarAction;
import com.pragma.toolbar.data.ToolbarButton;
import com.pragma.toolbar.data.ToolbarOrder;
import com.pragma.toolbar.properties.FilterTypeLibrary;
import com.pragma.toolbar.properties.OrderTypeEnum;
import com.pragma.toolbar.util.Validator;

/**
 *
 * @author fferrara
 */
//TODO Sacar de aca los defaults!
class ConfigurationXMLHelper {
    
    private static String CONFIG_FILE = "/configuracion-inicial.xml";
    private static String SCHEMA_FILE = "/configuracion-inicial.xsd";
    
    private static Element root;
    private static Document configuration;
    private static Log log;
    
    static {
        // init log
        log = LogFactory.getLog(ConfigurationXMLHelper.class);
        SAXReader reader = new SAXReader();                
                
        // read the configuration file
        try 
        {
            configuration = reader.read(ConfigurationXMLHelper.class.getResourceAsStream(CONFIG_FILE));                        

            // Validación del archivo de configuración usando el esquema XML
            Validator validator = new Validator(configuration,SCHEMA_FILE);            
            boolean isValid = validator.validate();
                        
            if (isValid) {
                root = configuration.getRootElement();
            }            
            else {
                log.error("Invalid configuration file. See error log under class com.pragma.toolbar.util.Validator");
            }
        }
        catch(DocumentException ex) {            
            log.error(ex.getMessage());
            configuration = null;            
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }
         
    public static Boolean getPaging(String idToolbar) {
        checkNotNullRoot();
        
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        Boolean paging = Boolean.valueOf(toolbar.attributeValue("paging"));
        return paging;
    }
        
    public static Integer getPageSize(String idToolbar){
        checkNotNullRoot();
        
        
        
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        Integer pageSize = Integer.valueOf(toolbar.attributeValue("pageSize"));
        return pageSize;
    }
    
    /**
     */
    public static List getColumnsOrders(String idToolbar) throws DocumentException 
    {
        checkNotNullRoot();
        
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        List columnsNodes = toolbar.selectNodes("columns/column");
        
        Element elem = null;

        Iterator it = columnsNodes.iterator(); 
        List columns = new ArrayList();
        
        while(it.hasNext())
        {
            elem = (Element) it.next();                        
            String  property  = elem.attributeValue("orderProperty");
            if(property==null) property  = elem.attributeValue("property");
            
            String strOrder = elem.attributeValue("order");
            String strOrderTypeName = elem.attributeValue("orderType");
            OrderTypeEnum orderTypeEnum = OrderTypeEnum.fromName(strOrderTypeName);
            if(orderTypeEnum==null) orderTypeEnum = OrderTypeEnum.ASC; 
            
            if (strOrder != null && !strOrder.equals("0") && strOrderTypeName != null){            
                Integer order    = Integer.valueOf(strOrder);
                columns.add(new ToolbarOrder(property,order,orderTypeEnum));
            }
        }
        return columns;
    }


    /**
     *   Devuelve las columnas de la configuracion inicial para la toolbar indicada
     */
    public static List getColumns(String idToolbar) throws DocumentException 
    {                      
        checkNotNullRoot();
        
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        List columnsNodes = toolbar.selectNodes("columns/column");
        
        Element elem = null;
                
        List columns = new ArrayList();
        Iterator it = columnsNodes.iterator(); 
        
        while(it.hasNext())
        {
            elem = (Element) it.next();                        
            
            String property  = elem.attributeValue("property");
            String showProperty = elem.attributeValue("showProperty");
            String orderProperty = elem.attributeValue("orderProperty");

            String title     = elem.attributeValue("title");   
            String decorator = elem.attributeValue("decorator");
            Boolean visible  = Boolean.valueOf(elem.attributeValue("visible"));
            String align = elem.attributeValue("align");
            DataType dataType = DataType.forName(elem.attributeValue("data-type"));
            if(dataType==null) dataType = DataType.STRING;
            
            String allowFilteringStr = elem.attributeValue("allow-filtering");
            if(allowFilteringStr==null) allowFilteringStr = "true";
            Boolean allowFiltering  = Boolean.valueOf(allowFilteringStr);
            
            String escapeHtmlStr = elem.attributeValue("escape-html");
            if(escapeHtmlStr==null) escapeHtmlStr = "true";
            Boolean escapeHtml  = Boolean.valueOf(escapeHtmlStr);

            String allowSortingStr = elem.attributeValue("allow-sorting");
            if(allowSortingStr==null) allowSortingStr = "true";
            Boolean allowSorting  = Boolean.valueOf(allowSortingStr);
            
            Integer order = null;
            String sOrder = elem.attributeValue("order");
            OrderTypeEnum orderType = OrderTypeEnum.fromName(elem.attributeValue("orderType"));
            
            if(sOrder!=null) {
        	order = new Integer(sOrder);
            } else {
        	order = new Integer(0);
            }
            if(orderType==null) orderType = OrderTypeEnum.ASC;
            
            columns.add(
        	    new DefaultColumnConfiguration(
        		    property,
        		    showProperty,
        		    orderProperty,
        		    visible, 
        		    title, 
        		    decorator, 
        		    align, 
        		    dataType,
        		    allowFiltering,
        		    allowSorting,
        		    order,
        		    orderType,
        		    escapeHtml
        	    )
            );
        }
        
        return columns;
    }
    
    /**
     *   Parsea el archivo de configuración inicial y devuelve los botones
     *   que originalmente están disponibles para el usuario
     */
    public static List getButtons(String idToolbar) throws DocumentException 
    {                      
        checkNotNullRoot();
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        List buttonsNodes = toolbar.selectNodes("buttons/button");
        
        Element elem = null;
                
        List buttons = new ArrayList();
        Iterator it = buttonsNodes.iterator(); 
        
        while(it.hasNext())
        {
            elem = (Element) it.next();                        
            Integer id  = Integer.valueOf(elem.attributeValue("id"));
            buttons.add(new ToolbarButton(id.intValue()));
        }
        return buttons;
    }

    /**
     *   Parsea el archivo de configuración inicial y devuelve los botones
     *   que originalmente están disponibles para el usuario
     */
    public static List getActions(String idToolbar) throws DocumentException 
    {                      
        checkNotNullRoot();
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        List actionsNodes = toolbar.selectNodes("actions/action");
        
        Element elem = null;
                
        List actions = new ArrayList();
        Iterator it = actionsNodes.iterator(); 
        
        while(it.hasNext())
        {
            elem = (Element) it.next();                        
            String name  = elem.attributeValue("name");
            actions.add(new ToolbarAction(name));
        }
        return actions;
    }

    public static String getDecorator(String idToolbar) 
    {
        checkNotNullRoot();
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        String decorator= toolbar.attributeValue("decorator");
        return decorator;
    }

    /**
     *   Parsea el archivo de configuración inicial y devuelve un
     *   título único para las acciones
     */
    public static String getActionsTitle(String idToolbar) throws DocumentException 
    {                      
        checkNotNullRoot();
        
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        Element actionsNode = (Element) toolbar.selectSingleNode("actions");
                
        String actionsTitle = actionsNode.attributeValue("title");
                    
        return actionsTitle;
    }
    
    /**
     *   Parsea el archivo de configuración inicial y devuelve 
     *   la clase del estilo que usará el inventario
     */
    public static String getStyleClass(String idToolbar) throws DocumentException 
    {                   
        checkNotNullRoot();
        Element toolbar = (Element)root.selectSingleNode("//toolbar[@id='"+idToolbar+"']");
        String styleClass = toolbar.attributeValue("styleClass");
        return styleClass;
    }
    
    /**
     *  Chequea que el root Element no sea nulo
     */
    private static void checkNotNullRoot(){
        if (root==null) {
            throw new NullArgumentException("root");
        }
    }
    public static BeforeQueryInvocationHandler getQueryInvocationHandler(String idToolbar)
    throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	
    	Element toolbar = getToolbarElement(idToolbar);
    	String handlerClass = toolbar.attributeValue("queryInvocationHandler");
    
    	return (BeforeQueryInvocationHandler) ((handlerClass!=null)? Class.forName( handlerClass ).newInstance() : null);
    }

    private static Element getToolbarElement(String idToolbar){
    	checkNotNullRoot();
    	Element toolbar = (Element) root.selectSingleNode("//toolbar[@id='" +
                idToolbar + "']");
    	
    	if (toolbar == null)
    		throw new ToolbarConfigurationException("Toolbar Configuration under id " + idToolbar + " does not exists ");
    	
    	return toolbar;
    	
    }
       
    public static String getFilterTypeLibraryClassName() {
        checkNotNullRoot();
        
        if (root.attribute("library") == null){
        	return null;
        }               
        return root.attribute("library").getStringValue(); 
        
    }
}





