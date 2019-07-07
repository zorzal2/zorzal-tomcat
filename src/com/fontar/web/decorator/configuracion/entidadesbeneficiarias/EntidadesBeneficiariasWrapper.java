package com.fontar.web.decorator.configuracion.entidadesbeneficiarias;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.web.decorator.TableDecoratorSupport;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Entidades Beneficiaras
 */
public class EntidadesBeneficiariasWrapper extends TableDecoratorSupport {

	public String getLinkAlta() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img src='images/cargarproyecto.gif' border=0></a>";

		return link;
	}

	public String getLinkEdicion() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img class=\"imageButton\" src='images/edicion.gif' border=0></a>";

		return link;
	}

	public String getLinkBorrado() throws UnsupportedEncodingException {
		String link = "";
		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img class=\"imageButton\" src='images/eliminar.gif' border=0></a>";
		return link;
	}

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		EntidadBeneficiariaBean lObject = (EntidadBeneficiariaBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('EntidadBeneficiaria','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("');");
		link.append("\"><img class=\"imageButton\" src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}

	public String getLinkSeleccionInventario() throws UnsupportedEncodingException {
		EntidadBeneficiariaBean lObject = (EntidadBeneficiariaBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		link.append("<a href='#' onclick=\"");
		link.append("handleSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",displayValue:'");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("',cuit:'");
		link.append(String.valueOf(lObject.getCuit()));
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}

	public String getLinkSeleccionEntidades() throws UnsupportedEncodingException {
		EntidadBeneficiariaBean eObject = (EntidadBeneficiariaBean) this.getCurrentRowObject();
		
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatosEntidad('tablaEntidad','");
		link.append(URLEncoder.encode(String.valueOf(eObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(eObject.getDenominacion()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
	
	public String getLinkSeleccionEntidadesInventario() throws UnsupportedEncodingException {
		EntidadBeneficiariaBean lObject = (EntidadBeneficiariaBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("handleSelectionEntidad({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",cuit:'");
		link.append(String.valueOf(lObject.getCuit()));
		link.append("',displayValue:'");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("',tabla:");
		link.append("'tablaEntidad'");
		link.append("});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}


}
