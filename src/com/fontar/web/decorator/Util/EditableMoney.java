package com.fontar.web.decorator.Util;

import java.math.BigDecimal;
import com.fontar.web.decorator.link.api.AbstractLink;

/**
*
* @author DBerkovics
*/
public class EditableMoney {

	private BigDecimal monto;
	private AbstractLink link;
	
	
	public EditableMoney(BigDecimal monto, AbstractLink link) {
		this.monto = monto;
		this.link = link;
	}
	
	public AbstractLink getLink(){
		return this.link;
	}
	
	public BigDecimal getMonto(){
		return this.monto;
	}

}
