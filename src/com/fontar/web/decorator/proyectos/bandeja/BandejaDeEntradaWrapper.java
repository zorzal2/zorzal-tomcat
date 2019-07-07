package com.fontar.web.decorator.proyectos.bandeja;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.data.impl.domain.bean.BandejaDeEntradaBean;
import com.fontar.web.decorator.TableDecoratorSupport;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Bandeja de Entrada
 */
public class BandejaDeEntradaWrapper extends TableDecoratorSupport  {

	public String getAcciones() {
		BandejaDeEntradaBean bean = (BandejaDeEntradaBean) this.getCurrentRowObject();
		Collection acciones = bean.getAcciones();

		StringBuffer sb = new StringBuffer();

		sb.append("<select name='acciones_");
		sb.append(bean.getId());
		sb.append("'>");

		for (Iterator iter = acciones.iterator(); iter.hasNext();) {
			TaskInstance element = (TaskInstance) iter.next();
			sb.append("<option value='");
			sb.append(element.getId());
			sb.append("'>");
			sb.append(element.getName());
			sb.append("</option>");
		}
		sb.append("</select>");
		sb.append("<a href=\"BandejaDeEntrada.do?accion=signal&idProcessInstance=");
		sb.append(bean.getId());
		sb.append("\">");
		sb.append("<img src='images/aceptar.gif' border=0>");
		sb.append("</a>");

		return sb.toString();
	}

	public String getLinkAlta() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img src='images/cargarproyecto.gif' border=0></a>";

		return link;
	}

	public String getLinkEdicion() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img src='images/edicion.gif' border=0></a>";

		return link;
	}

	public String getLinkBorrado() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img src='images/eliminar.gif' border=0></a>";

		return link;
	}

}
