package com.fontar.web.decorator.configuracion.entidades.entidades;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.Entidable;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Entidades
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */

public class EntidadesWrapper extends TableDecoratorSupport {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		EntidadBean lObject = this.getEntidad();
		VisualizarLink visualizar = new VisualizarLink("EntidadVisualizar.do","app.alt.visualizarEntidad",lObject.getId());
		return visualizar.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		EntidadBean lObject = this.getEntidad();		
		EditarLink editarLink = new EditarLink("EntidadEditar.do","app.alt.editarEntidad",lObject.getId());
		editarLink.setSimplePermissionsRequired("ENTIDADES-EDITAR");
		return editarLink.displayValue();
	}
	
	public String baseLinkBorrar(String tipoEntidad) {
		EntidadBean lObject = this.getEntidad();		
		BorrarLink link = new BorrarLink("Entidad"+(tipoEntidad==null? "" : tipoEntidad)+"Borrar.do","app.alt.eliminarEntidad",lObject.getId());
		link.openInCurrentWindow();
		link.setSimplePermissionsRequired("ENTIDADES-ELIMINAR");
		return link.displayValue();
	}
	
	public String getLinkBorrar() throws UnsupportedEncodingException {
		return baseLinkBorrar(null);
	}
	
	public String getLinkBorrarEntidadBeneficiaria() throws UnsupportedEncodingException {
		return baseLinkBorrar("Beneficiaria");
	}
	
	public String getLinkBorrarEntidadEvaluadora() throws UnsupportedEncodingException {
		return baseLinkBorrar("Evaluadora");
	}
	
	public String getLinkBorrarEntidadBancaria() throws UnsupportedEncodingException {
		return baseLinkBorrar("Bancaria");
	}
	
	
	public String getLinkSeleccion() throws UnsupportedEncodingException {
		EntidadBean eObject = this.getEntidad();
		StringBuffer link = new StringBuffer();
		link.append("<a href=# onclick=\"");
		link.append("handleSelectionEntidad({id:");
		link.append(URLEncoder.encode(String.valueOf(eObject.getId()), "UTF-8"));
		link.append(",cuit:'");
		link.append(String.valueOf(eObject.getCuit()));
		link.append("',displayValue:'");
		link.append(String.valueOf(eObject.getDenominacion()));
		link.append("',tabla:");
		link.append("'tablaEntidad'");
		link.append("});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}
	
	private EntidadBean getEntidad(){
		if (this.getCurrentRowObject() instanceof Entidable) {
			return ((Entidable) this.getCurrentRowObject()).getEntidad();
		} else {
			return (EntidadBean) this.getCurrentRowObject();	
		}
	}
	
}
