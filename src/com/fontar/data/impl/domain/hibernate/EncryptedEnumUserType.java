package com.fontar.data.impl.domain.hibernate;

import java.io.Serializable;
import java.security.Guard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.FontarEncryptedGuard;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;


public class EncryptedEnumUserType<E extends Enum<E>> implements UserType {

	private static final int[] SQL_TYPES = {Types.VARCHAR};
	
	private Class<E> clazz = null; 
    
	protected EncryptedEnumUserType(Class<E> c) { 
		this.clazz = c; 
	} 
	
    public int[] sqlTypes() { 
        return SQL_TYPES; 
    } 
 
    public Class returnedClass() { 
        return EncryptedObject.class; 
    } 
 
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
    	Guard guard = new FontarEncryptedGuard();
    	EncryptedObject value = null;
	    byte[] bytes = resultSet.getBytes(names[0]);
        if (!resultSet.wasNull()) {
        	try {
        		String enumValue = (String) this.getCryptographicService().decrypt( bytes, this.getClass() );
        		//El dato no es nulo y fue desencriptado correctamente
                value = new EncryptedObject(Enum.valueOf(clazz, enumValue),guard,true);
        	}catch (Exception e) {
        		//El dato no es nulo, pero no pudo ser desencriptado
        		value = new EncryptedObject(null,guard,false);
        		value.setBytes( bytes );
			}
        	 
        }else{
        	//El dato es nulo
        	value = new EncryptedObject(null,guard,true);
        }
        return value; 
    } 
 
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
    	if(value == null){
    		preparedStatement.setNull(index, Types.VARCHAR);
    	}else{
    		try{
		    	Enumerable enumerable = (Enumerable) ((EncryptedObject) value).getObject();
		        if (null == enumerable) { 
		            preparedStatement.setNull(index, Types.VARCHAR); 
		        } else {
		        	String enumValue = enumerable.getName();
		            preparedStatement.setBytes(index, this.getCryptographicService().encrypt(enumValue, this.getClass())); 
		        }
    		}catch (SecurityException e) {
				preparedStatement.setBytes(index, ((EncryptedObject) value).getBytes() );
			}
    	}
    } 
 
    public Object deepCopy(Object value) throws HibernateException{ 
        return value; 
    } 
 
    public boolean isMutable() { 
        return false; 
    } 
 
    public Object assemble(Serializable cached, Object owner) throws HibernateException  {
         return cached;
    } 

    public Serializable disassemble(Object value) throws HibernateException { 
        return (Serializable)value; 
    } 
 
    public Object replace(Object original, Object target, Object owner) throws HibernateException { 
        return original; 
    }
    
    public int hashCode(Object x) throws HibernateException { 
        return x.hashCode(); 
    } 
    
    public boolean equals(Object x, Object y) throws HibernateException { 
        if (x == y) 
            return true; 
        if (null == x || null == y) 
            return false; 
        return x.equals(y); 

    } 

 
    private FontarCryptographicService getCryptographicService(){
    	return (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
    }
}
