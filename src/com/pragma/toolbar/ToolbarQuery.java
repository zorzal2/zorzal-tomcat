/*
 * ToolbarQuery.java
 *
 * Created on 2 de noviembre de 2006, 18:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.pragma.hibernate.BeforeQueryInvocationHandler;
import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;
import com.pragma.toolbar.data.ToolbarFilterPersister;
import com.pragma.toolbar.data.ToolbarOrder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.util.StringUtil;


/**
 *
 * @author fferrara
 */
public class ToolbarQuery implements PaginatedList {
	
	private int pageNumber;
	private int pageSize;
	private int fullSize;
	private Collection<ToolbarQueryFilter> filters;
	private Session session;
	private String baseQuery;
	private boolean hasPersistentFilters;
	private UserToolbarConfiguration toolbarConfig;
	
    private BeforeQueryInvocationHandler queryInvocationHandler;
	
	/**
	 * Creates a new instance of ToolbarQuery
	 * @param rapidFilters 
	 * @param pageNumber 
	 * @param idUser 
	 * @param idToolbar 
	 * @param query 
	 * @param session 
	 * @param classMetadata 
	 */
	public ToolbarQuery(
			Collection<ToolbarQueryFilter> rapidFilters, 
			int pageNumber, 
			boolean paging,
			String idUser, 
			String idToolbar, 
			String queryString, 
			Session session) {
		this.toolbarConfig = UserToolbarConfiguration.get(idUser, idToolbar);
		ToolbarFilterPersister persister = new ToolbarFilterPersister(idUser, idToolbar);
		setPaging(pageNumber, paging);
		
		Collection<? extends ToolbarQueryFilter> persistentFilters = persister.getAllFilters();
		this.hasPersistentFilters = !persistentFilters.isEmpty();
		this.filters = new ArrayList<ToolbarQueryFilter>();
		this.filters.addAll(rapidFilters);
		this.filters.addAll(persistentFilters);
		this.session = session;
		
		///ToolbarManager toolbarManager = new ToolbarManager(idToolbar,idUser);
		UserToolbarConfiguration toolbarConfig = UserToolbarConfiguration.get(idUser, idToolbar); 
        queryInvocationHandler = toolbarConfig.getBeforeQueryInvocationHandler();
		//Obtener filtros persistidos
		
		//Obtener ordenes persistido o del osXML
		//Obtener pageSize
		
		//orders = new ArrayList();
		this.baseQuery = queryString;
	}
	
	private void setPaging(int pageNumber, boolean paging) {
		if(paging) {
			this.pageNumber = pageNumber;
			this.pageSize = toolbarConfig.getPageSize().intValue();
		} else {
			this.pageNumber = 1;
			this.pageSize = Integer.MAX_VALUE;
		}		
	}
	
	public List getList() {
		// SB para consulta filtrada y ordenada
		StringBuffer sbQueryFilters = new StringBuffer();
		//filtros a aplicar en hql
		List queryFilters = new ArrayList();
		// SB para consulta de ordenes
		StringBuffer sbOrderFilters = new StringBuffer();
		
		String baseQueryString = baseQuery;
		boolean addWhereClause = !baseQueryString.toUpperCase().contains("WHERE");
		
		// SB para consulta de cantidad de registros
	    StringBuffer sbCount = new StringBuffer("select ");

	    //Notar que hasDistinct => hasSelect
	    boolean hasSelect = baseQueryString.toUpperCase().contains("SELECT");
	    boolean hasDistinct = baseQueryString.toUpperCase().contains("DISTINCT");
	    	    
	    // elimino el SELECT
	    String selectClause;
	    if (hasSelect){
	    	int indexOfFrom = baseQuery.toUpperCase().indexOf("FROM");
	    	//Guardo el select original
	    	selectClause = baseQueryString.substring(0, indexOfFrom); 

	    	baseQueryString = baseQuery.substring(indexOfFrom);
	    } else {
	    	//Clausula select default
	    	selectClause = "select o ";
	    }

	    if (hasDistinct){
	    	sbCount.append("count(distinct o)");
	    } else{
	    	sbCount.append("count(o)");
	    }
		
		// Mapa de parametros de la consulta
		Map parameterMap = new Hashtable();  
		
		// add filters to QueryString
		for (ToolbarQueryFilter filter : filters) {
			// skip empty filters
			if (filter.isEmpty()) continue;
			
			String filterHql = filter.getHqlForObject("o");
			parameterMap.putAll(filter.getVariables());
			queryFilters.add(filterHql);
			
		}
		if(queryFilters.size()>0) {
			sbQueryFilters.append(addWhereClause? " where " : " and ");
			StringUtil.joinOn(sbQueryFilters, queryFilters.iterator(), " and ");
		}
		
		
		// add orders to QueryString
		boolean firstClause = true;
		
		
		Comparator<ToolbarOrder> comparator = new Comparator<ToolbarOrder>() {
			public int compare(ToolbarOrder o1, ToolbarOrder o2) {
				ToolbarOrder order1 = (ToolbarOrder)o1;
				ToolbarOrder order2 = (ToolbarOrder)o2;
				return order1.getOrder().compareTo(order2.getOrder());
			}
		};
		
		SortedSet<ToolbarOrder> ordersByOrder = new TreeSet<ToolbarOrder>(comparator);
		ordersByOrder.addAll(toolbarConfig.getColumnsOrders());
		
		for (ToolbarOrder order : ordersByOrder) {
			
			sbOrderFilters.append(firstClause ? " order by " : ",");
			sbOrderFilters.append("o.");
			sbOrderFilters.append(order.getProperty());
			sbOrderFilters.append(" ");
			sbOrderFilters.append(order.getOrderType().getHqlCode());        
			
			firstClause = false;
		}
		
		// Create filtered and ordered Query
		StringBuffer sbFilteredOrderedQuery = new StringBuffer();
		
		//sbFilteredOrderedQuery.append("select distinct o ");
		sbFilteredOrderedQuery.append(selectClause);
		sbFilteredOrderedQuery.append(baseQueryString);
		sbFilteredOrderedQuery.append(sbQueryFilters);
		sbFilteredOrderedQuery.append(sbOrderFilters);
		
		sbCount.append(baseQueryString);
		sbCount.append(sbQueryFilters);
		sbCount.append(sbOrderFilters);
		
		//com.arcor.util.log.Log.getInstance().debug(" HQL FilteredOrderedQuery " + sbFilteredOrderedQuery.toString());
		//com.arcor.util.log.Log.getInstance().debug(" HQL CountQuery " + sbCount.toString());
		
		
		List retvalue = null;
		
		try {
			
			Query filteredOrderedQuery = session.createQuery(sbFilteredOrderedQuery.toString());
			Query recordCountQuery = session.createQuery(sbCount.toString());
			
			// add values to query
			for (Iterator iter = parameterMap.entrySet().iterator(); iter.hasNext();) {
				Map.Entry keyVal = (Map.Entry) iter.next();
				filteredOrderedQuery.setParameter(keyVal.getKey().toString(),keyVal.getValue());
				recordCountQuery.setParameter(keyVal.getKey().toString(),keyVal.getValue());
				//com.arcor.util.log.Log.getInstance().debug(keyVal.getKey()+" -> "+keyVal.getValue());
			}
			
			
			// tamaño total de la query
			//Log.getInstance().debug("Query: " + filteredOrderedQuery.getQueryString());
			fullSize = this.resolveCountQuery(recordCountQuery);
			int objsFrom = (pageNumber - 1) * getObjectsPerPage();        
			if(fullSize<objsFrom) {
				pageNumber = (fullSize/getObjectsPerPage())+1;
				objsFrom = (pageNumber - 1) * getObjectsPerPage();        
			}
			// record from / to
			filteredOrderedQuery.setFirstResult(objsFrom);
			filteredOrderedQuery.setMaxResults(getObjectsPerPage());      
	        retvalue = this.resolveQuery(filteredOrderedQuery);
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return retvalue;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public int getObjectsPerPage() {
		return pageSize;
	}
	
	public int getFullListSize() {
		return fullSize;
	}
	
	public String getSortCriterion() {
		return "";
	}
	
	public SortOrderEnum getSortDirection() {
		return SortOrderEnum.DESCENDING;
	}
	
	public String getSearchId() {
		return "1";
	}
	
	public boolean hasPersistentFilters() {
		return this.hasPersistentFilters;
	}
	public boolean getHasPersistentFilters() {
		return this.hasPersistentFilters;
	}
    private List resolveQuery(Query targetQuery){
    	
    	if(this.queryInvocationHandler!=null)
    		return this.queryInvocationHandler.adapt(targetQuery,this.session).list();
    	
    	return targetQuery.list();
    	
    }
    
    private int resolveCountQuery(Query targetQuery){
    	
    	if(this.queryInvocationHandler!=null)
    		return ( (Integer) this.queryInvocationHandler.adapt(targetQuery,this.session).uniqueResult()).intValue();
    	
    	return ((Integer)targetQuery.uniqueResult()).intValue();
    	
    }
}
