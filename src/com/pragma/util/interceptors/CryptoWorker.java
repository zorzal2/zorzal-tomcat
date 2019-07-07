package com.pragma.util.interceptors;

import java.util.HashMap;

import org.hibernate.Session;

public interface CryptoWorker {

	/**
	 * encripta los campos configurados por EntityCryptoInfo para el entity
	 * asociado segun entityName.
	 * 
	 */
	void crypt();

	/**
	 * desencripta los campos configurados por EntityCryptoInfo para el entity
	 * asociado segun entityName.
	 * 
	 */
	void decrypt();

	HashMap<String, EntityCryptoInfo> getCriptoInfo();

	void setCriptoInfo(HashMap<String, EntityCryptoInfo> criptoInfo);

	Session getSession();

	void setSession(Session session);

	Object getEntity();

	void setEntity(Object entity);

	String getEntityName();

	void setEntityName(String entityName);

	void setState(Object[] state);

	void setPropertyNames(String[] propertyNames);

}