package com.pragma.web;

/**
 * Pila de navegación, por el momento solo recuerda la ultima página visitada
 * @author fferrara
 *
 */
public class NavigationStack {

	String lastVisited = null;
	String defaultLocation = null;
	
	public NavigationStack(String defaultLocation) {
		this.defaultLocation = defaultLocation; 
	}
	public NavigationStack() {
		this.defaultLocation = null; 
	}
	
	public void pushAction(String url){
		lastVisited = url;
	}
	
	public String popAction(){
		if(lastVisited==null) return defaultLocation;
		else return lastVisited;
	}
	
	public String topAction(){
		if(lastVisited==null) return defaultLocation;
		else return lastVisited;
	}
	
	public boolean isEmpty(){
		return lastVisited==null && defaultLocation==null; 
	}
}
