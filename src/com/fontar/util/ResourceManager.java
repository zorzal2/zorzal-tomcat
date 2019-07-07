package com.fontar.util;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manejador de archivos de recursos
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 */
public class ResourceManager {
	// ~ Static fields/initializers
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static String messages = "resources.Messages";

	private static String errors = "resources.ErrorMessages";

	private static String informationals = "resources.InformationalMessages";

	private static String labels = "resources.FieldLabels";

	private static String headers = "resources.TableHeader";

	private static String cells = "resources.CellText";

	private static String menus = "resources.OptionsMenu";

	private static String titles = "resources.Titles";

	private static String alts = "resources.Alt";

	private static String codes = "resources.Codes";
	
	private static String wfTask = "resources.WFTasks";
	
    private static Log log;
    
    static {
        log = LogFactory.getLog(ResourceManager.class);
    }

	// ~ Methods
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Accede al archivo de recurso resources.Messages para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getMessageResource(String key) {
		return ResourceManager.getResource(key, messages);
	}
	public static String getMessageResource(String key, String... parameters) {
		return ResourceManager.getResource(key, messages, parameters);
	}
	public static String getMessageResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, messages, parameters);
	}

	/**
	 * Accede al archivo de recurso resources.ErrorMessages para obtener el
	 * valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getErrorResource(String key) {
		return ResourceManager.getResource(key, errors);
	}
	public static String getErrorResource(String key, String... parameters) {
		return ResourceManager.getResource(key, errors, parameters);
	}
	public static String getErrorResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, errors, parameters);
	}

	/**
	 * Accede al archivo de recurso resources.InformationalMessages para obtener
	 * el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getInformationalResource(String key) {
		return ResourceManager.getResource(key, informationals);
	}
	public static String getInformationalResource(String key, String... parameters) {
		return ResourceManager.getResource(key, informationals, parameters);
	}
	public static String getInformationalResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, informationals, parameters);
	}

	/**
	 * Accede al archivo de recurso resources.FieldLabels para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getLabelResource(String key) {
		return ResourceManager.getResource(key, labels);
	}
	public static String getLabelResource(String key, String... parameters) {
		return ResourceManager.getResource(key, labels, parameters);
	}
	public static String getLabelResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, labels, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.TableHeader para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getHeaderResource(String key) {
		return ResourceManager.getResource(key, headers);
	}
	public static String getHeaderResource(String key, String... parameters) {
		return ResourceManager.getResource(key, headers, parameters);
	}
	public static String getHeaderResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, headers, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.CellText para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getCellResource(String key) {
		return ResourceManager.getResource(key, cells);
	}
	public static String getCellResource(String key, String... parameters) {
		return ResourceManager.getResource(key, cells, parameters);
	}
	public static String getCellResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, cells, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.OptionsMenu para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getMenuResource(String key) {
		return ResourceManager.getResource(key, menus);
	}
	public static String getMenuResource(String key, String... parameters) {
		return ResourceManager.getResource(key, menus, parameters);
	}
	public static String getMenuResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, menus, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.Titles para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getTitleResource(String key) {
		return ResourceManager.getResource(key, titles);
	}
	public static String getTitleResource(String key, String... parameters) {
		return ResourceManager.getResource(key, titles, parameters);
	}
	public static String getTitleResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, titles, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.Alt para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getAltResource(String key) {
		return ResourceManager.getResource(key, alts);
	}
	public static String getAltResource(String key, String... parameters) {
		return ResourceManager.getResource(key, alts, parameters);
	}
	public static String getAltResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, alts, parameters);
	}
	/**
	 * Accede al archivo de recurso resources.codes para obtener el valor
	 * 
	 * @param key del bundle
	 * 
	 * @return value del key
	 */
	public static String getCodesResource(String key) {
		return ResourceManager.getResource(key, codes);
	}
	public static String getCodesResource(String key, String... parameters) {
		return ResourceManager.getResource(key, codes, parameters);
	}
	public static String getCodesResource(String key, Map<String, String> parameters) {
		return ResourceManager.getResource(key, codes, parameters);
	}
	
	public static String getWFTasksResource(String key) {
		return ResourceManager.getResource(key, wfTask);
	}
	public static String getWFTasksResource(String key, String... parameters) {
		return ResourceManager.getResource(key, wfTask, parameters);
	}
	/**
	 * Accede al recurso especificado para obtener el valor de la key
	 * 
	 * @param key clave del bundle
	 * @param resource nombre del archivo de recursos
	 * 
	 * @return value de la clave
	 */
	private static String getResource(String key, String resource) {
		String descripcion = "";
		try {
			ResourceBundle rb = ResourceBundle.getBundle(resource);
			try {
				descripcion = rb.getString(key);
			}
			catch (Exception ex) {
				log.error(ex);
				descripcion = key;
			}
		}
		catch (Exception e) {
			log.error(e);
			descripcion = "Falta Archivo de Recurso " + resource;
		}
		
		return descripcion;
	}
	/**
	 * Accede al recurso especificado para obtener el valor de la key
	 * 
	 * @param key clave del bundle
	 * @param resource nombre del archivo de recursos
	 * @param parameters parametros
	 * 
	 * @return value de la clave
	 */
	public static String getResource(String key, String resource, String... parameters) {
		String ret = getResource(key, resource);
		//reemplazo los parametros
		for(int i = 0; i<parameters.length; i++) {
			String param = parameters[i];
			ret = ret.replaceAll("\\{"+i+"\\}", param);
		}
		return ret;
	}
	public static String getResource(String key, String resource, Map<String, String> parameters) {
		String ret = getResource(key, resource);
		//reemplazo los parametros
		for(Entry<String, String> entry : parameters.entrySet()) {
			ret = ret.replaceAll("\\{"+entry.getKey()+"\\}", entry.getValue());
		}
		return ret;
	}
}