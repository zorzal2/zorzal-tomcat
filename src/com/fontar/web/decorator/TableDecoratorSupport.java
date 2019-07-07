package com.fontar.web.decorator;


import org.displaytag.decorator.TableDecorator;

import com.lowagie.text.Font;
import com.lowagie.text.Table;

public class TableDecoratorSupport  extends TableDecorator implements org.displaytag.render.ItextTableWriter.ItextDecorator{

	/** 
	 *  Estos metodos son requeridos para la exportacion a PDF
	 * 
	 * **/
	
		public void setTable(Table arg0) {
		// TODO Auto-generated method stub
			arg0.setCellsFitPage(false);
			arg0.setTableFitsPage(false);
	}

	public void setFont(Font arg0) {
		// TODO Auto-generated method stub
		arg0.setSize(7);
	}
}
