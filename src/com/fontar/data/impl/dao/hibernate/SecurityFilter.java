package com.fontar.data.impl.dao.hibernate;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Filter;

public class SecurityFilter  {

	public static String FILTER_NAME = "securityFilter"; 
	Filter filter;

	public SecurityFilter(Filter filter) {
		super();
		this.filter = filter;
	}
	

	public void setAllowedInstances(Collection allowedInstances){
		Object object = allowedInstances.iterator().next();
		if(object.getClass().equals(Long.class)){
			 CollectionUtils.transform(allowedInstances, new LongToStringTransformer() );
		}
		this.filter.setParameterList("allowedInstances", allowedInstances);
	}
	
}
