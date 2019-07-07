package com.pragma.util;

import com.fontar.util.ResourceManager;

public class DecoratorUtil {

	/**
	 * Construye el image link básico de acción de borrado
	 * 
	 * @param action nombre del Action Struts de Borrado, debe includir el .do y no empezar con /
	 * @param altKey key del bundle de tooltips
	 * @param id id del objectoa borrar, se pasa como parametro id
	 * @param confirm Si se incluye un mensaje de Confirmación previo a la llamada de la acción
	 * @return String con el link
	 */
	public static String getLinkBorrado(String action, String altKey, Long id, Boolean confirm){
		StringBuffer link = new StringBuffer();
		
		if (confirm){
			link.append("<a href=# onclick=\"if (confirm('");
			link.append(ResourceManager.getInformationalResource("app.msj.confirmaEliminacion"));
			link.append("')){ window.location.href='");
			link.append(action);	
		} else {
			link.append("<a href='");
			link.append(action);
		}

		link.append("?id=");
		link.append(id);
		link.append("';}\"><img class=\"imageButton\" src='images/eliminar.gif' title=");
		link.append("'" + ResourceManager.getAltResource(altKey) + "' border=0 /></a>");
		
		return link.toString();
	}
	
	/**
	 * Construye el image link básico de acción de edición
	 * 
	 * @param action nombre del Action Struts de Edición, debe includir el .do y no empezar con /
	 * @param altKey key del bundle de tooltips
	 * @param id id del objecto a editar, se pasa como parametro id
	 * @return String con el link
	 */
	public static String getLinkEdicion(String action, String altKey, Long id){

		StringBuffer link = new StringBuffer();
		
		link.append("<a href='");
		link.append(action);
		link.append("?id=");
		link.append(id);
		link.append("'><img src='images/edicion.gif' title='");
		link.append(ResourceManager.getAltResource(altKey));
		link.append("' border=0 /></a>");
		
		return link.toString();
		
	}
	
	/**
	 * Construye el image link básico de acción de edición
	 * 
	 * @param action nombre del Action Struts de Edición, debe includir el .do y no empezar con /
	 * @param altKey key del bundle de tooltips
	 * @param id id del objecto a editar, se pasa como parametro id
	 * @return String con el link
	 */
	public static String getLinkVisualizar(String action, String altKey, Long id){

		StringBuffer link = new StringBuffer();
		
		link.append("<a href='");
		link.append(action);
		link.append("?id=");
		link.append(id);
		link.append("'><img src='images/visualizar.gif' title='");
		link.append(ResourceManager.getAltResource(altKey));
		link.append("' border=0 /></a>");
		
		return link.toString();
		
	}
	
}
