package com.fontar.seguridad.cripto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;

public class EncryptedMoneyWrapper implements DisplaytagColumnDecorator {
    
	static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0.00");
	
	public Object decorate(Object columnValue, PageContext pageContext,MediaTypeEnum mediaTypeEnum) throws DecoratorException {
            	
		if(columnValue!=null){
            try{
            	BigDecimal value =  (BigDecimal) ((EncryptedObject) columnValue).getObject();
            	return value!=null? this.format(value,mediaTypeEnum) :  "";
            }catch(SecurityException e){
            	return ObjectUtils.ENCRIPTION_WARNING;
            }
		}
		return "";
	}
	
	private Object format(BigDecimal bigDecimal , MediaTypeEnum mediaTypeEnum){
		return mediaTypeEnum.equals(MediaTypeEnum.EXCEL)? bigDecimal : DECIMAL_FORMAT.format(bigDecimal);
	}
}

	