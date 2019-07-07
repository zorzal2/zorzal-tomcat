package com.fontar.web.decorator.configuracion.seguridad;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.web.action.configuracion.seguridad.BasicUsuarioDTO;
import com.fontar.web.action.configuracion.seguridad.UsuarioDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.ResetClaveLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Instituciones Evaluadoras
 */
public class UsuariosWrapper extends TableDecoratorSupport {

	private String resolveAction(String action){
		return "Usuarios" +  action + ".do";
	}
	/**
	 * Requiere que el dto decorado sea UsuarioDTO y no BasicUsuarioDTO
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPersonaVinculada() throws UnsupportedEncodingException {
		UsuarioDTO usuario = (UsuarioDTO) this.getCurrentRowObject();
		PersonaDTO persona = usuario.getPersona();
		if(persona==null) return "";
		else return persona.getNombre();
	}
	
	public String getLinkEditar() throws UnsupportedEncodingException {
		BasicUsuarioDTO usuario = (BasicUsuarioDTO) this.getCurrentRowObject();
		String action = this.resolveAction("Editar");
		EditarLink editarLink = new EditarLink(action,"app.alt.editarUsuario",usuario.getUserId());
		editarLink.setSimplePermissionsRequired("USUARIOS-EDITAR");
		return editarLink.displayValue();
	}

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		BasicUsuarioDTO usuario = (BasicUsuarioDTO) this.getCurrentRowObject();
		String action = this.resolveAction("Visualizar");
		VisualizarLink visualizarLink = new VisualizarLink(action,"app.alt.visualizarUsuario",usuario.getUserId());
		visualizarLink.setSimplePermissionsRequired("USUARIOS-INVENTARIO");
		return visualizarLink.displayValue();
	}

	public String getLinkBorrar() throws UnsupportedEncodingException {
		BasicUsuarioDTO usuario = (BasicUsuarioDTO) this.getCurrentRowObject();
		String action = this.resolveAction("Borrar");
		BorrarLink borrarLink = new BorrarLink(action,"app.alt.borrarUsuario",usuario.getUserId());
		borrarLink.setSimplePermissionsRequired("USUARIOS-ELIMINAR");
		borrarLink.openInCurrentWindow();
		return borrarLink.displayValue();
	}

	
	public String getLinkResetearClave() throws UnsupportedEncodingException {
		BasicUsuarioDTO usuario = (BasicUsuarioDTO) this.getCurrentRowObject();
		String action = this.resolveAction("ResetearClaveAutenticacionInput");
		ResetClaveLink resetClaveLink = new ResetClaveLink(action,"app.alt.resetearClaveUsuario",usuario.getUserId());
		resetClaveLink.setSimplePermissionsRequired("USUARIOS-EDITAR");
		return resetClaveLink.displayValue();
	}
}
