package com.fontar.data.impl.dao.ldap;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.AttributesMapper;

public class UserProjection implements AttributesMapper {
	
	private UsuarioDao usuarioDao;
	
	public UserProjection(UsuarioDao usuarioDao) {
		super();
		this.usuarioDao = usuarioDao;
	}

	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		List<String> list = new LinkedList<String>();
		Attribute attribute = attributes.get("usuario");
		if(attribute!=null){
			NamingEnumeration enumeration = attribute.getAll();
			while(enumeration.hasMore()){
				String dn = (String) enumeration.next();
				list.add( this.usuarioDao.findByDn(dn).getUsername());
			}
		}
		return list;
	}
}

