/**
 * 
 */
package com.pragma.util.interceptors;

import java.util.Map;

/**
 * @author fferrara
 * 
 */
public class EntityCryptoInfo {

	private String entityName;

	private String tableName;

	private Map<String, String> crytoFields;

	public Map<String, String> getCrytoFields() {
		return crytoFields;
	}

	public void setCrytoFields(Map<String, String> crytoFields) {
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
