package com.pragma.util;

import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class URL {

	private String protocol = null;
	private String host = null;
	private String port = null;
	private String pathFile = null;
	private Map<String, String> parameters = new Hashtable<String, String>();
	private String fragment = null;

	public String getFragment() {
		return fragment;
	}
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setParameter(String name, Object value) {
		if(value!=null) parameters.put(name, value.toString());
	}
	public String getParameter(String name) {
		return parameters.get(name);
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
	
		StringBuilder url = new StringBuilder();
		if(host!=null) {
			if(protocol!=null) {
				url.append(protocol);
				url.append("://");
			}
			url.append(host);
			if(port!=null) url.append(port);
		}
		if(pathFile!=null) {
			if(host!=null && (pathFile.length()<1 || pathFile.charAt(0)!='/')) url.append('/');
			String[] pathParts = pathFile.split("/"); 
			for(int i = 0; i<pathParts.length; i++) {
				pathParts[i] = URLEncoder.encode(pathParts[i]);
			}
			url.append(StringUtil.join(pathParts, "/"));
		}
		if(!parameters.isEmpty()) {
			url.append('?');
			for(Entry<String, String> entry : parameters.entrySet()) {
				url.append(URLEncoder.encode(entry.getKey()));
				url.append('=');
				url.append(URLEncoder.encode(entry.getValue()));
				url.append('&');
			}
			//elimino el ultimo
			url.setLength(url.length()-1);
		}
		if(fragment!=null) {
			url.append('#'); 
			url.append(fragment); 
		}
		return url.toString();
	}
}
