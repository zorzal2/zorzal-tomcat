package com.fontar.web.action.consultas;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;

public class Result implements PaginatedList {

	private List list;
	private int pageNumber;
	private int pageSize;
	private int fullSize;
	
	private boolean paging;
	
	private ResultTransformer transformer;
	
	public Result() {
		this(0,0);
	}
	
	public Result(int pageNumber , int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public void init(List list, int fullSize){
		this.list = list;
		this.fullSize = fullSize;
	}
	
	
	public int getFirstResult(){
		return (this.pageNumber - 1) * this.getObjectsPerPage();      
	}

	public int getMaxResults(){
		return this.getObjectsPerPage();
	}

	
	public List getList() {
		return list;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public int getObjectsPerPage() {
		return this.pageSize;
	}

	public int getFullListSize() {
		return this.fullSize;
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

	
	public ResultTransformer getTransformer() {
		return transformer;
	}

	public void setTransformer(ResultTransformer transformer) {
		this.transformer = transformer;
	}

	public void init(Criteria criteria) {
		//Paginado
		if(this.isPaging()){
			criteria.setFirstResult(this.getFirstResult());
			criteria.setMaxResults( this.getMaxResults());
		}
		if(this.getTransformer()!=null)
			criteria.setResultTransformer( this.getTransformer());
		this.list = criteria.list();
		
		//Full size
		criteria.setFirstResult(0);
		criteria.setProjection(Projections.rowCount());
		this.fullSize = ( (Integer)criteria.uniqueResult() ).intValue();
	}


	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public boolean isPaging() {
		return paging;
	}
	
	

}
