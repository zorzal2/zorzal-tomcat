package com.fontar.web.decorator.configuracion.especialidadEvaluador;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Especialidad Evaluador
 * @author mdberra, ssanchez
 * @version 1.01, 20/12/06
 */
public class EspecialidadEvaluadorWrapper extends TableDecoratorSupport {

	private EspecialidadEvaluadorBean eeObject;


	public String getLinkVisualizar() throws UnsupportedEncodingException {
		EspecialidadEvaluadorBean lObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("EspecialidadEvaluadorVisualizar.do","app.alt.visualizarEspecialidadEvaluador",lObject.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		EspecialidadEvaluadorBean lObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("EspecialidadEvaluadorEditar.do","app.alt.editarEspecialidadEvaluador",lObject.getId());
		editarLink.setSimplePermissionsRequired("ESPECIALIDADESEVALUADOR-EDITAR");
		return editarLink.displayValue();
	}

	public String getLinkSeleccionPrimaria() throws UnsupportedEncodingException {
		EspecialidadEvaluadorBean  eeObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Primaria','");
		link.append(URLEncoder.encode(String.valueOf(eeObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(eeObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}

	public String getLinkSeleccionSecundaria() throws UnsupportedEncodingException {
		eeObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Secundaria','");
		link.append(URLEncoder.encode(String.valueOf(eeObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(eeObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
	
	public String getLinkSeleccionInventario() throws UnsupportedEncodingException {
		eeObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();
		link.append("<a href='#' onclick=\"");
		link.append("handleSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(eeObject.getId()), "UTF-8"));
		link.append(",displayValue:'");
		link.append(String.valueOf(eeObject.getNombre()));
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}

	public String getLinkSeleccionSecundariaInventario() throws UnsupportedEncodingException {
		eeObject = (EspecialidadEvaluadorBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Secundaria','");
		link.append(URLEncoder.encode(String.valueOf(eeObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(eeObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
	
	
	
}