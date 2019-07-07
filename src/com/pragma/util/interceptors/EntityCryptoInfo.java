/**
 * 
 */
package com.pragma.util.interceptors;

import java.util.HashMap;

/**
 * @author fferrara
 * 
 */
public class EntityCryptoInfo {

	private String entityName;

	private String tableName;

	private HashMap<String, String> crytoFields;

	public HashMap<String, String> getCrytoFields() {
		return crytoFields;
	}

	public void setCrytoFields(HashMap<String, String> crytoFields) {
		this.crytoFields = crytoFields;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
