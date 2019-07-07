package com.fontar.web.decorator.configuracion.institucionesEvaluadoras;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Instituciones Evaluadoras
 */
public class InstitucionesEvaluadorasWrapper extends TableDecorator {

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
