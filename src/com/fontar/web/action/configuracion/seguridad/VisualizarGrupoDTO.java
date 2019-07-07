package com.fontar.web.action.configuracion.seguridad;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.impl.dao.ldap.PermisoComparator;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.pragma.util.ContextUtil;

public class VisualizarGrupoDTO {

	private GrupoAbstracto grupo;

	private String convocatoria;
	
	private SortedSet permisos;
	
	public VisualizarGrupoDTO(GrupoAbstracto grupo) {
		super();
		this.grupo = grupo;
		if(this.getInstrumentable()){
			GrupoConInstrumento grupoConInstrumento = (GrupoConInstrumento) grupo;
			InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");
			InstrumentoBean instrumentoBean = instrumentoDAO.read( Long.valueOf(grupoConInstrumento.getIdInstrumento() ));
			this.convocatoria = instrumentoBean!=null ? instrumentoBean.getIdentificador(): "NA";
		}
	}

	public String getIdGrupo() {
		return grupo.getIdGrupo();
	}

	public String getNombre() {
		return grupo.getNombre();
	}

	@SuppressWarnings("unchecked")
	public Set getPermisos() {
		if(this.permisos ==null){
			this.permisos = new TreeSet( new PermisoComparator() );
			this.permisos.addAll( this.grupo.getPermisos() );
		}
		return permisos;
	}

	public Set getUsuarios() {
		return grupo.getUsuarios();
	}

	public void setIdGrupo(String idGrupo) {
		grupo.setIdGrupo(idGrupo);
	}

	public void setNombre(String nombre) {
		grupo.setNombre(nombre);
	}

	public void setPermisos(Set permisos) {
		grupo.setPermisos(permisos);
	}

	public void setUsuarios(Set usuarios) {
		grupo.setUsuarios(usuarios);
	}
	
	public Boolean instrumentable(){
		return this.grupo.getClass().equals(GrupoConInstrumento.class);
	}
	
	public Boolean getInstrumentable(){
		return this.instrumentable();
	}
	
	public String getInstrumento(){
		return this.convocatoria;
	}
	
}
