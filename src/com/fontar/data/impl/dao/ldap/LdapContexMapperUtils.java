package com.fontar.data.impl.dao.ldap;

import org.springframework.ldap.support.DirContextOperations;

public class LdapContexMapperUtils extends UsuarioContextMapper {

	
	public static String resolveOctetString(DirContextOperations dirContextOperations, String propertyName){
		Object retrievedOctetString = dirContextOperations.getObjectAttribute(propertyName);
		return new String((byte[]) retrievedOctetString);
	}
}
