package com.fontar.initialization;



import org.apache.log4j.Logger;

import com.pragma.util.ContextUtil;

public abstract class InitializationCommand  {

	private Logger logger;
	
	public abstract void  execute();
	
	public abstract void  initializeImpl();
	
	protected Object getSingletonInstance(String instanceName){
		return ContextUtil.getBean(instanceName);
	}
	
	protected void initialize(){
		this.initializeImpl();
		this.setLogger( Logger.getLogger( this.getClass()) );
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	
}
