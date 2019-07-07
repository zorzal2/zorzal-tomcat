package com.fontar.seguridad.cripto;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.ClavesBean;

public class Status {
	
	private boolean applicationKeys;
	
	private Collection<ClavesBean> claves;

	public boolean isApplicationKeys() {
		return applicationKeys;
	}

	public void setApplicationKeys(boolean applicationKeys) {
		this.applicationKeys = applicationKeys;
	}

	public Collection<ClavesBean> getClaves() {
		return claves;
	}

	public void setClaves(Collection<ClavesBean> claves) {
		this.claves = claves;
	}
	
	public boolean isStarted(){
		return !this.claves.isEmpty();
	}
	
	public boolean getStarted(){
		return isStarted();
	}
	
}
