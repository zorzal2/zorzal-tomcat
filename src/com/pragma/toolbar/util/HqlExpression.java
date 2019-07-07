package com.pragma.toolbar.util;

import java.util.HashMap;
import java.util.Map;

public class HqlExpression {
	private String hqlMain;
	private Map hqlFilters = new HashMap();
	
	public HqlExpression(String hqlMain) {
		this.hqlMain = hqlMain;
	}
	
	public String getHqlMain() {
		return this.hqlMain;
	}
	
	public void addHqlFilter(String filterKey, String hqlFilter) {
		this.hqlFilters.put(filterKey, hqlFilter);
	}
	
	public String getHqlFilter(String filterKey) {
		return (String) this.hqlFilters.get(filterKey);
	}
	
}
