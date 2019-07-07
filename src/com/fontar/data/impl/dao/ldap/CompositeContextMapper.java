package com.fontar.data.impl.dao.ldap;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.support.DirContextOperations;

public class CompositeContextMapper implements ContextMapper {

	private Map<String, ContextMapper> mappers = new HashMap<String, ContextMapper>();
	
	
	public void addMapper(String objectClass, ContextMapper mapper){
		this.mappers.put(objectClass, mapper);
	}
	
	public Object mapFromContext(Object ctx) {
		
		 DirContextOperations dirContext = (DirContextOperations) ctx;
		 String[] objectClasses = dirContext.getStringAttributes("objectClass");
		 String objectClass = objectClasses[1];
		 ContextMapper contextMapper = this.mappers.get( objectClass );
		 return contextMapper.mapFromContext(ctx);
	}

}
