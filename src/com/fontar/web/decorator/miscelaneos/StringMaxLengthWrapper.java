package com.fontar.web.decorator.miscelaneos;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.lowagie.text.html.HtmlEncoder;

public class StringMaxLengthWrapper implements DisplaytagColumnDecorator {
	

	private static int MAX_LENGTH = 50;
	
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum)
			throws DecoratorException {

		String stringValue = "";

		if (columnValue != null) {
			stringValue = columnValue.toString();
		}
		
		if(mediaTypeEnum.equals(MediaTypeEnum.HTML)) {
			//Si el texto es muy largo lo trunca y lo muestra en un tooltip
			if (stringValue.length() > MAX_LENGTH){

				StringBuffer html = new StringBuffer();
				
				html.append("<span title=\"");
				html.append(StringEscapeUtils.escapeHtml(stringValue));
				html.append("\">");
				html.append(HtmlEncoder.encode(stringValue.substring(0,MAX_LENGTH)));
				html.append("&nbsp;...");
				html.append("</span>");
				return html.toString();
				
			} else {
				stringValue = HtmlEncoder.encode(stringValue);
				return stringValue;
			}
		}
		// caso Exportacion a Excel 
		else if(mediaTypeEnum.equals(MediaTypeEnum.EXCEL)) 
			return stringValue;	
		else //caso Exportación a PDF 
			return stringValue;	
	}
}
