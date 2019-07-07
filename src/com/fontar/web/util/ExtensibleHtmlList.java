package com.fontar.web.util;

import java.util.ArrayList;
import java.util.List;

import com.fontar.util.ResourceManager;
import com.pragma.util.StringPattern;
import com.pragma.util.StringUtil;
import com.pragma.util.WebUtils;

public class ExtensibleHtmlList extends ArrayList<String> implements List<String> {
	private static final long serialVersionUID = 1L;

	private String id = "ExtHtmlLst_"+WebUtils.randomId();
	private String header;
	private int maxSize = 4;
	
	private String collapsedId = "ExtHtmlLst_"+WebUtils.randomId();
	private String expandedId = "ExtHtmlLst_"+WebUtils.randomId();
	
	public ExtensibleHtmlList(String header) {
		super();
		this.header = header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	public String toString() {
		return controlHtmlPattern()
					.set("id", id)
					.set("header", header)
					.set("expandedList", generateExpandedList())
					.set("collapsedList", generateCollapsedList())
					.toString();
		
	}
	
	private String generateExpandedList() {
		//Si no hay muchos elementos no genero esta lista.
		if(size()<=maxSize) return "";
		
		return listHTMLPattern()
			.set("style", "display: none")
			.set("id", expandedId)
			.set("content", listElements(Integer.MAX_VALUE))
			.set("footer", 
					extraLink(
						ResourceManager.getInformationalResource("app.msj.listados.menosElementos"), 
						collapsedId, 
						expandedId)
			).toString();
	}
	private String generateCollapsedList() {
		//Si no hay muchos elementos no genero footer.
		if(size()==0) return "";
		String footer = "";
		if(size()>maxSize) {
			footer = extraLink(
					ResourceManager.getInformationalResource("app.msj.listados.masElementos", ""+(size()-maxSize)), 
						expandedId, 
						collapsedId);
		}
		return listHTMLPattern()
			.set("style", "")
			.set("id", collapsedId)
			.set("content", listElements(maxSize))
			.set("footer", footer)
			.toString();
	}
	private static String extraLink(String label, String elementToShow, String elementToHide) {
		//Agrego un link para mostrar mas elementos
		String javascript = 
			StringUtil.inject(
					"document.getElementById('$1').style.display='none';document.getElementById('$2').style.display=''",
					elementToHide,
					elementToShow);
		
		return StringUtil.inject( 
				"<a onclick=\"$1\" href=\"#\">$2</a>",
				javascript,
				label
		);
	}
	
	private String listElements(int maxSize) {
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i<size() && i<maxSize; i++) {
			ret.append(listElementPattern().set("text", get(i)));
		}
		return ret.toString();
	}
	/**
	 * Pattern correspondiente a un elemento.<br>
	 * Tiene la variable <code>text</code>
	 * @return
	 */
	private static StringPattern listElementPattern() {
		return new StringPattern(
				"<li>${text}</li>"
				);
	}
	/**
	 * Pattern correspondiente a una lista con un pie de elemento
	 * Tiene las variable <code>style, id, content, footer</code>
	 * @return
	 */
	private static StringPattern listHTMLPattern() {
		return new StringPattern(
				"<span style=\"${style}\" id=\"${id}\"><ul>${content}</ul><span class=\"footer\">${footer}</span></span>"
				);
	}
	
	/**
	 * Pattern correspondiente a la totalidad del control.
	 * Tiene las variable <code>id, header expandedList, collapsedList</code>
	 * @return
	 */
	private static StringPattern controlHtmlPattern() {
		return new StringPattern(
				"<span id=\"${id}\" class=\"bulletedList\"><span class=\"header\">${header}</span>${expandedList}${collapsedList}</span>"
				);
	}	
}
