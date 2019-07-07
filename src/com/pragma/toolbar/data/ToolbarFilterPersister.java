package com.pragma.toolbar.data;

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.bean.ToolbarFilterBean;
import com.pragma.toolbar.data.bean.VariantBean;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ExpressionBasedToolbarFilter;
import com.pragma.toolbar.data.filter.PropertyBasedToolbarFilter;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.exception.ControlledException;
import com.pragma.toolbar.exception.UnControlledException;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.HQLFilterType;
import com.pragma.util.CollectionUtils;
import com.pragma.util.Transformations.Transformation;
import com.pragma.util.hibernate.DAO;

//TODO: interfacear
public class ToolbarFilterPersister {
	String 
	userId, 
	toolbarId;
	public ToolbarFilterPersister(String userId, String toolbarId) {
		this.userId = userId;
		this.toolbarId = toolbarId;
	}
	/**
	 * Guarda en la base de datos un filtro asociado a la toolbar
	 * y a un usuario.
	 * @param filter
	 */
	public void saveFilter(ToolbarQueryFilter filter) {
		try {
			(new TransactionalCommand() {
				public Object run(Object[] params) throws ControlledException {
					ConfigurableToolbarFilter filter = (ConfigurableToolbarFilter) params[0];
					//Inicializacion
					DAO variantDao = DAO.getInstance(VariantBean.class);
					DAO toolbarFilterDao = DAO.getInstance(ToolbarFilterBean.class);
					//creo el filtro
					baseSaveFilter(filter, variantDao, toolbarFilterDao);
					return Boolean.TRUE;
				}
			}).execute(filter);
		} catch(ControlledException e) {
			e.printStackTrace();
			throw new UnControlledException(e);
		}
	}
	
	/**
	 * Reemplaza de la base de datos los filtros guardados para este usuario y 
	 * toolbar por los dados.
	 * @param toolbarFilterCollection
	 */
	public void setFilters(Collection toolbarFilterCollection) {
		try {
			(new TransactionalCommand() {
				public Object run(Object[] params) throws ControlledException {
					Collection filters = (Collection) params[0];
					//Inicializacion
					DAO variantDao = DAO.getInstance(VariantBean.class);
					DAO toolbarFilterDao = DAO.getInstance(ToolbarFilterBean.class);
					//elimino los filtros existentes
					baseRemoveAllFilters(toolbarFilterDao, variantDao);
					//creo los filtros nuevos
					baseSaveFilters(filters, variantDao, toolbarFilterDao);
					return Boolean.TRUE;
				}
			}).execute(toolbarFilterCollection);
		} catch(ControlledException e) {
			e.printStackTrace();
			throw new UnControlledException(e);
		}
	}
	/**
	 * Guarda en base de datos un conjunto de filtros asociándolos
	 * a una toolbar y un usuario.
	 * @param toolbarFilterCollection
	 */
	public void saveFilters(Collection toolbarFilterCollection) {
		try {
			(new TransactionalCommand() {
				public Object run(Object[] params) throws ControlledException {
					Collection filters = (Collection) params[0];
					//Inicializacion
					DAO variantDao = DAO.getInstance(VariantBean.class);
					DAO toolbarFilterDao = DAO.getInstance(ToolbarFilterBean.class);
					//creo los filtros
					for (Iterator iter = filters.iterator(); iter.hasNext();) {
						ConfigurableToolbarFilter filter = (ConfigurableToolbarFilter) iter.next();
						baseSaveFilter(filter, variantDao, toolbarFilterDao);
					}
					return Boolean.TRUE;
				}
			}).execute(toolbarFilterCollection);
		} catch(ControlledException e) {
			e.printStackTrace();
			throw new UnControlledException(e);
		}
	}
	/**
	 * Devuelve los filtros persistidos. Si algún filtro no puede ser instanciado (por conflicto entre datos
	 * y clases del negocio) es ignorado.
	 * @return
	 */
	public Collection<ConfigurableToolbarFilter> getAllFilters() {
		try {
			return (Collection<ConfigurableToolbarFilter>) (new Command() {
				public Object run(Object[] params) throws ControlledException {
					DAO filterDao = DAO.getInstance(ToolbarFilterBean.class);
					Criteria criteria= filterDao.newCriteria();
					criteria
					.add(Expression.eq("toolbarId", toolbarId))
					.add(Expression.eq("userId", userId));
					
					Transformation<ToolbarFilterBean, ConfigurableToolbarFilter> Bean2Filter = new Transformation<ToolbarFilterBean, ConfigurableToolbarFilter>() {
						public ConfigurableToolbarFilter applyTo(ToolbarFilterBean o) {
							return buildFilterFromBean(o);
						}
					};
					return CollectionUtils.clean(CollectionUtils.transform(filterDao.getAll(criteria), Bean2Filter));
				}
			}).execute();
		} catch (ControlledException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		}
	}
	public ToolbarQueryFilter getFilter(Long filterId) {
		try {
			return (ToolbarQueryFilter) (new Command() {
				public Object run(Object[] params) throws ControlledException {
					Long id = (Long)params[0];
					DAO filterDao = DAO.getInstance(ToolbarFilterBean.class);
					Criteria criteria= filterDao.newCriteria();
					criteria.add(Expression.eq("id", id));
					ToolbarFilterBean toolbarFilterBean = (ToolbarFilterBean)filterDao.getAll(criteria).get(0);
					
					return buildFilterFromBean(toolbarFilterBean);
				}
			}).execute(filterId);
		} catch (ControlledException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		}
	}
	public void removeFilter(Long filterId) {
		try {
			(new TransactionalCommand() {
				public Object run(Object[] params) throws ControlledException {
					Long id = (Long)params[0];
					DAO filterDao = DAO.getInstance(ToolbarFilterBean.class);
					filterDao.remove(filterDao.get(id));
					return Boolean.TRUE;
				}
			}).execute(filterId);
		} catch (ControlledException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		}
	}
	/**
	 * Elimina todos los filtros que tiene el usuario para la 
	 * toolbar.
	 *
	 */
	public void removeAllFilters() {
		try {
			(new TransactionalCommand() {
				public Object run(Object[] params) throws ControlledException {
					
					DAO filterDao = DAO.getInstance(ToolbarFilterBean.class);
					DAO variantDao = DAO.getInstance(VariantBean.class);
					baseRemoveAllFilters(filterDao, variantDao);
					return Boolean.TRUE;
				}
			}).execute();
		} catch (ControlledException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		}
	}
	
	private ConfigurableToolbarFilter buildFilterFromBean(ToolbarFilterBean toolbarFilterBean) {
		FilterType filterType =  DefaultToolbarConfig.getFilterTypeLibrary().fromName(
				toolbarFilterBean.getFilterTypeDescription()
		);
		if(filterType==null) return null;
		Class classType = null;
		String classTypeName = toolbarFilterBean.getClassTypeName();
		try {
			if(classTypeName!=null && !classTypeName.equals(""))
				classType = Class.forName(classTypeName);
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			throw new RuntimeException("Class not found.");
		}
		return ConfigurableToolbarFilterBuilder.build(
				toolbarFilterBean.getProperty(),
				filterType,
				toolbarFilterBean.getValue().getValue(),
				classType,
				toolbarFilterBean.getExpression()
		);
	}    
	
	private void baseSaveFilters(Collection filters, DAO variantDao, DAO toolbarFilterDao) {
		//creo los filtros
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			ConfigurableToolbarFilter filter = (ConfigurableToolbarFilter) iter.next();
			baseSaveFilter(filter, variantDao, toolbarFilterDao);
		}
	}
	private void baseRemoveAllFilters(DAO filterDao, DAO variantDao) {
		Criteria criteria= filterDao.newCriteria();
		criteria
		.add(Expression.eq("toolbarId", toolbarId))
		.add(Expression.eq("userId", userId));
		Collection filterBeans = filterDao.getAll(criteria);
		
		for (Iterator iter = filterBeans.iterator(); iter.hasNext();) {
			ToolbarFilterBean filterBean = (ToolbarFilterBean) iter.next();
			//Elimino el variant correspondiente
			variantDao.remove(filterBean.getValue());
			filterDao.remove(filterBean);
		}
	}
	private void baseSaveFilter(ConfigurableToolbarFilter filter, DAO variantDao, DAO toolbarFilterDao) {
		//creo el variant para guardar el value del filtro
		VariantBean variantBean = (VariantBean)variantDao.newBean();
		variantBean.setValue(filter.getValue());
		//grabo
		variantDao.create(variantBean);
		
		//creo el bean de toolbarfilter
		ToolbarFilterBean toolbarFilterBean = (ToolbarFilterBean)toolbarFilterDao.newBean();
		
		if(filter.getFilterType()==DefaultFilterTypeLibrary.EXPRESSION) {
			ExpressionBasedToolbarFilter ebFilter = (ExpressionBasedToolbarFilter) filter; 
			toolbarFilterBean.setClassTypeName(ebFilter.getClassType().getName());
			toolbarFilterBean.setExpression(ebFilter.getExpression());
			toolbarFilterBean.setFilterTypeDescription(ebFilter.getFilterType().getName());
			toolbarFilterBean.setProperty("N/A");
		} else {
			PropertyBasedToolbarFilter pbFilter = (PropertyBasedToolbarFilter) filter; 
			toolbarFilterBean.setClassTypeName(pbFilter.getClassType().getName());
			toolbarFilterBean.setExpression("N/A");
			toolbarFilterBean.setFilterTypeDescription(pbFilter.getFilterType().getName());
			toolbarFilterBean.setProperty(pbFilter.getProperty());
		}
		
		toolbarFilterBean.setValue(variantBean);
		toolbarFilterBean.setToolbarId(toolbarId);
		toolbarFilterBean.setUserId(userId);
		//grabo
		toolbarFilterDao.create(toolbarFilterBean);
	}
}
