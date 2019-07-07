package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.web.action.configuracion.seguridad.UsuarioDTO;
import com.fontar.web.action.configuracion.seguridad.VisualizarGrupoDTO;
import com.pragma.data.api.assembler.Assembler;
import com.pragma.toolbar.NotImplementedException;
import com.pragma.util.ContextUtil;

public class UsuarioDTOAssembler implements Assembler<Usuario, UsuarioDTO> {

	private static class SingletonHolder {
		private static UsuarioDTOAssembler instance = new UsuarioDTOAssembler();
	}

	public static UsuarioDTOAssembler getInstance() {
		return SingletonHolder.instance;

	}
	
	public UsuarioDTO buildDto(Usuario usuario) {
		UsuarioDTO dto =  this.BuildBaseDto(usuario);
		
		PersonaDAO personaDao = (PersonaDAO) ContextUtil.getBean("personaDao");
		List<PersonaBean> personas = personaDao.findByUserId(usuario.getUserId());
		if(!personas.isEmpty()) {
			PersonaDTO personaDTO = PersonaAssembler.getInstance().buildDto(personas.get(0));
			dto.setPersona(personaDTO);
			dto.setNombrePersona(personaDTO.getNombre());
		} else {
			dto.setNombrePersona("");
		}
		return dto;
	}

	private UsuarioDTO BuildBaseDto(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setUsername(usuario.getUsername());
		dto.setUserId(usuario.getUserId());
		dto.setStatus(usuario.getStatus());
		
		ArrayList<VisualizarGrupoDTO> grupos = new ArrayList<VisualizarGrupoDTO>(usuario.getGrupos().size());
		for(Object object : usuario.getGrupos()) {
		  GrupoAbstracto grupo = (GrupoAbstracto) object;
		  grupos.add(new VisualizarGrupoDTO(grupo));
		}
		dto.setGrupos(grupos);
		return dto;
	}

	
	public Usuario buildBean(UsuarioDTO dto) {
		throw new NotImplementedException();
	}

	public List<UsuarioDTO> buildDto(List<Usuario> beans) {
		
		//obtiene la información de Persona-Usuario
		PersonaDAO personaDao = (PersonaDAO) ContextUtil.getBean("personaDao");
		List<PersonaBean> personas = personaDao.findWithUserId();
		
		HashMap<String,PersonaBean> personasUsuarias = new HashMap<String,PersonaBean>();
		for(PersonaBean persona: personas) {
			personasUsuarias.put(persona.getUserId(),persona);
		}
		
		//Obtiene la información de Usuario (LDAP) y completar con la Persona
		List<UsuarioDTO> list = new ArrayList<UsuarioDTO>(beans.size());
		for(Usuario bean : beans) {
			UsuarioDTO usuario = BuildBaseDto(bean);
			if(personasUsuarias.containsKey(usuario.getUserId())) {
				PersonaBean personaBean = personasUsuarias.get(usuario.getUserId());
				PersonaDTO personaDTO = PersonaAssembler.getInstance().buildDto(personaBean);
				usuario.setNombrePersona(personaBean.getNombre());
				usuario.setPersona(personaDTO);
			}
			else {
				usuario.setNombrePersona("");
			}
			list.add(usuario);
		}
		return list;
	}

	public void updateBeanNotNull(Usuario bean, UsuarioDTO dto) {
		throw new NotImplementedException();
	}

	public void updateDtoNotNull(UsuarioDTO dto, Usuario bean) {
		throw new NotImplementedException();
	}
}
