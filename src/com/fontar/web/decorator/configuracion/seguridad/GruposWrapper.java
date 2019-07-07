package com.fontar.web.decorator.configuracion.seguridad;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.pragma.util.ContextUtil;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Instituciones Evaluadoras
 */
public class GruposWrapper extends TableDecoratorSupport {

	private String resolveAction(String action){
		return "Grupos" +  action + ".do";
	}
	
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		GrupoAbstracto grupo = this.getCurrentObject();
		String action =  grupo.getClass().equals(GrupoConInstrumento.class)? this.resolveAction("InstrumentoVisualizar") : this.resolveAction("Visualizar");
		VisualizarLink visualizarLink = new VisualizarLink(action,"app.alt.visualizarGrupo",grupo.getIdGrupo());
		visualizarLink.setSimplePermissionsRequired("GRUPOS-INVENTARIO");
		return visualizarLink.displayValue();
	}
	
	

	public String getLinkEditar() throws UnsupportedEncodingException {
		GrupoAbstracto grupo = this.getCurrentObject(); 
		String action = this.resolveAction("Editar");
		EditarLink editarLink = new EditarLink(action,"app.alt.editarGrupo",grupo.getIdGrupo());
		editarLink.setSimplePermissionsRequired("GRUPOS-EDITAR");
		return editarLink.displayValue();
	}

	public String getLinkBorrar() throws UnsupportedEncodingException {
		GrupoAbstracto grupo = this.getCurrentObject(); 
		String action =  grupo.getClass().equals(GrupoConInstrumento.class)? this.resolveAction("InstrumentoBorrar") : this.resolveAction("Borrar");
		BorrarLink borrarLink = new BorrarLink(action,"app.alt.borrarGrupo",grupo.getIdGrupo());
		borrarLink.setSimplePermissionsRequired("GRUPOS-ELIMINAR");
		return borrarLink.displayValue();
	}

	public String getLinkSeleccionar() throws UnsupportedEncodingException {
		GrupoAbstracto grupo = this.getCurrentObject(); 
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("handleSelectionGrupo({id:'");
		link.append(URLEncoder.encode(String.valueOf(grupo.getIdGrupo()), "UTF-8"));
		link.append("',displayValue:'");
		link.append(URLEncoder.encode(grupo.getNombre(), "UTF-8"));
		link.append("',instrumento:'");
		link.append(URLEncoder.encode(this.getInstrumento(), "UTF-8"));
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}
	
	
	public String getInstrumento() throws UnsupportedEncodingException {
		InstrumentoBean bean = this.getInstrumentoBean();
		return bean==null ? "" : bean.getIdentificador();
	}
	
	private InstrumentoBean getInstrumentoBean() throws UnsupportedEncodingException {
		InstrumentoBean bean = null;
		GrupoAbstracto grupo = this.getCurrentObject();
		if(grupo.getClass().equals(GrupoConInstrumento.class)){
			GrupoConInstrumento grupoConInstrumento = (GrupoConInstrumento) grupo;
			InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");
			bean = instrumentoDAO.read( Long.valueOf(grupoConInstrumento.getIdInstrumento() ));
		}
		return bean;
	}
	
	private GrupoAbstracto getCurrentObject(){
		return (GrupoAbstracto) this.getCurrentRowObject();
	}

}
