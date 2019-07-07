package com.fontar.seguridad;

import java.security.Guard;

public class EncryptedObject {

	
	private Object object; // Objecto protegido
	
	private Boolean initialiazed; //Si el objeto fue inicializado correctamente
	
    private Guard guard;   // Responsable de controlar el acceso
    
    private byte[] bytes; //Valor encriptado 
 
    public EncryptedObject(Object object, Guard guard, Boolean initialized){
        this.guard = guard;
        this.object = object;
        this.initialiazed = initialized;
    }
    
    
    private void checkGuard()  throws SecurityException {
    	if(!this.initialiazed)
    		throw new SecurityException("El objeto no ha sido inicializado correctamente");
    	 if (guard != null)
 	        guard.checkGuard(object);
    }
    
    public Object getObject() throws SecurityException {
    	this.checkGuard();
	    return object;
    }
    
    public void setObject(Object object) throws SecurityException {
    	this.checkGuard();
	    this.object = object;
    }


	public Boolean isInitialiazed() {
		return initialiazed;
	}


	public EncryptedObject update(Object object) {
		this.checkGuard();
		EncryptedObject encryptedObject = new EncryptedObject(object,this.guard,true);
		return encryptedObject;
	}
    
    @Override
	public String toString(){
		try{
			return this.getObject().toString();
		}catch(SecurityException securityException){
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}


	public byte[] getBytes() {
		return bytes;
	}


	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}


	
    
    
}
