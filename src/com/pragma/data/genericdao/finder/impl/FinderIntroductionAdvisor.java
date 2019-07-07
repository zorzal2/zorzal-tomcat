package com.pragma.data.genericdao.finder.impl;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class FinderIntroductionAdvisor extends DefaultIntroductionAdvisor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778397910359376090L;

	public FinderIntroductionAdvisor() {
		super(new FinderIntroductionInterceptor());
	}
}
