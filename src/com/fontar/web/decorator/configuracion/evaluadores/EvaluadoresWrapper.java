package com.fontar.web.decorator.configuracion.evaluadores;

import java.io.UnsupportedEncodingException;

import com.fontar.web.decorator.TableDecoratorSupport;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Evaluadores
 */
public class EvaluadoresWrapper extends TableDecoratorSupport {

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
