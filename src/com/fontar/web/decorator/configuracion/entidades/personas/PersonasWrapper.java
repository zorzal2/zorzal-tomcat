package com.fontar.web.decorator.configuracion.entidades.personas;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Personas
 * @author ssanchez
 * @version 1.01, 21/12/06
 */
public class PersonasWrapper extends TableDecoratorSupport {

	public String getLinkEditar() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("PersonaEditar.do","app.alt.editarPersona",lObject.getId());
		editarLink.setSimplePermissionsRequired("PERSONAS-EDITAR");
		return editarLink.displayValue();		
	}
	
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("PersonaVisualizar.do","app.alt.visualizarPersona",lObject.getId());
		visualizarLink.setSimplePermissionsRequired("PERSONAS-VISUALIZAR");
		return visualizarLink.displayValue();
	}
	
	public String getLinkBorrar() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		BorrarLink borrarLink = new BorrarLink("PersonaBorrar.do","app.alt.borrarPersona",lObject.getId());
		borrarLink.openInCurrentWindow();
		borrarLink.setSimplePermissionsRequired("PERSONAS-ELIMINAR");
		return borrarLink.displayValue();
	}

	public String getLinkSeleccionPersonaLegal() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('PersonaLegal','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}

	public String getLinkSeleccionPersonaDirector() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('PersonaDirector','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}

	public String getLinkSeleccionPersonaRepresentante() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('PersonaRepresentante','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
}
