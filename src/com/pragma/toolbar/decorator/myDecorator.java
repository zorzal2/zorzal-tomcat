package com.pragma.toolbar.decorator;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

/**
 * 
 * @author gboaglio
 * arma, para mostrar, links de ver, editar, y eliminar un elemento del inventario
 */
public class myDecorator extends TableDecorator {

	public String getEdit() throws UnsupportedEncodingException
	{
            StringBuffer sb = new StringBuffer();            
            sb.append("unLinkDeEdicion");            
            return sb.toString();
	}	 
	
	public String getView() throws UnsupportedEncodingException
	{
            StringBuffer sb = new StringBuffer();
            sb.append("unLinkDeVisualizacion");
            return sb.toString();
	}
		
	public String getDelete() throws UnsupportedEncodingException
	{
            StringBuffer sb = new StringBuffer();
            sb.append("unLinkDeBorrado");
            return sb.toString();
	}
}
