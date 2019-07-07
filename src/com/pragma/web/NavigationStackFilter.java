/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.pragma.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.pragma.util.CollectionUtils;
import com.pragma.util.StringUtil;


public class NavigationStackFilter implements Filter, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Set<String> stackableActions;
	

	/**
	 * nothing to do
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		NavigationManager.setDefaultLocation("/"+filterConfig.getInitParameter("defaultAction")+".do");
		String stackableActionsList = filterConfig.getInitParameter("stackableActions");
		stackableActions = CollectionUtils.setWith(stackableActionsList.trim().split("\\s*,\\s*"));
	}

	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String action = httpServletRequest.getServletPath();
			if(StringUtil.isNotEmpty(action)) {
				int posOfSlash = action.lastIndexOf('/');
				int posOfDot = action.lastIndexOf(".do");
				if(posOfSlash>=0 && posOfDot>=0 && posOfDot>posOfSlash) {
					action = action.substring(posOfSlash+1, posOfDot);
					if(stackableActions.contains(action)) {
						NavigationManager.proccessStackableAction(httpServletRequest);
					} else {
						NavigationManager.proccessNonStackableAction(httpServletRequest);
					}
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
	}
}