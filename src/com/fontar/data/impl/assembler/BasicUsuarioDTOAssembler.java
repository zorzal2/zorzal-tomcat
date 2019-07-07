package com.fontar.data.impl.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.web.action.configuracion.seguridad.BasicUsuarioDTO;
import com.pragma.data.api.assembler.Assembler;
import com.pragma.toolbar.NotImplementedException;
import com.pragma.util.ContextUtil;

public class BasicUsuarioDTOAssembler implements Assembler<Usuario, BasicUsuarioDTO>{

	private static class SingletonHolder {
		private static BasicUsuarioDTOAssembler instance = new BasicUsuarioDTOAssembler();
	}

	public static BasicUsuarioDTOAssembler getInstance() {
		return SingletonHolder.instance;

	}
	
	public BasicUsuarioDTO buildDto(Usuario usuario) {
		BasicUsuarioDTO dto =  this.BuildBaseDto(usuario);
		
		PersonaDAO personaDao = (PersonaDAO) ContextUtil.getBean("personaDao");
		List<PersonaBean> personas = personaDao.findByUserId(usuario.getUserId());
		if(!personas.isEmpty()) {
			PersonaBean personaBean = personas.get(0);
			dto.setNombrePersona(personaBean.getNombre());
		} else {
			dto.setNombrePersona("");
		}
		return dto;
	}

	protected BasicUsuarioDTO BuildBaseDto(Usuario usuario) {
		BasicUsuarioDTO dto = new BasicUsuarioDTO();
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setUsername(usuario.getUsername());
		dto.setUserId(usuario.getUserId());
		return dto;
	}

	
	public Usuario buildBean(BasicUsuarioDTO dto) {
		throw new NotImplementedException();
	}

	public List<BasicUsuarioDTO> buildDto(List<Usuario> beans) {
		
		//obtiene la información de Persona-Usuario
		PersonaDAO personaDao = (PersonaDAO) ContextUtil.getBean("personaDao");
		List<PersonaBean> personas = personaDao.findWithUserId();
		
		HashMap<String,PersonaBean> personasUsuarias = new HashMap<String,PersonaBean>();
		for(PersonaBean persona: personas) {
			personasUsuarias.put(persona.getUserId(),persona);
		}
		
		//Obtiene la información de Usuario (LDAP) y completar con la Persona
		List<BasicUsuarioDTO> list = new ArrayList<BasicUsuarioDTO>(beans.size());
		for(Usuario bean : beans) {
			BasicUsuarioDTO usuario = BuildBaseDto(bean);
			if(personasUsuarias.containsKey(usuario.getUserId())) {
				PersonaBean personaBean = personasUsuarias.get(usuario.getUserId());
				usuario.setNombrePersona(personaBean.getNombre());
			}
			else {
				usuario.setNombrePersona("");
			}
			list.add(usuario);
		}
		return list;
	}

	public void updateBeanNotNull(Usuario bean, BasicUsuarioDTO dto) {
		throw new NotImplementedException();		
	}

	public void updateDtoNotNull(BasicUsuarioDTO dto, Usuario bean) {
		throw new NotImplementedException();
	}
}
