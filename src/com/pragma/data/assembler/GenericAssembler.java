package com.pragma.data.assembler;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pragma.data.api.assembler.Assembler;

/**
 * Assembler Generico para Bean-2-DTO y DTO-2-Bean
 * @author fferrara
 * 
 * @param <T>
 * @param <D>
 */
public abstract class GenericAssembler<T, D> implements Assembler<T, D> {

	private static Log log;

	/**
	 * Las subclases DEBEN reimplementar el constructor
	 * @param beanType Class del Bean
	 * @param dtoType Class del DTO
	 */
	protected GenericAssembler() {
		// TODO: FF / ver donde van estas cosas!
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new LongConverter(null), Long.TYPE);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.TYPE);

		log = LogFactory.getLog(this.getClass().getName());
	}

	/**
	 * Crea un DTO del tipo especificado en el constructor con los valores del
	 * Bean T
	 */
	public D buildDto(T bean) {
		if(bean==null) return null;
		D dto = null;
		try {
			dto = newDtoInstance();
		}
		catch (Exception e) {
			log.error(this, e);
		}

		Map map = null;
		try {
			map = this.getBean2DtoMap(bean);
		}
		catch (ParseException e) {
			log.error(this, e);
		}
		try {
			BeanUtils.populate(dto, map);
		}
		catch (IllegalAccessException e) {
			log.error(this, e);
		}
		catch (InvocationTargetException e) {
			log.error(this, e);
		}

		return dto;
	}

	/**
	 * Actualiza los valores del Bean T con los valores No Nulos del Dto D
	 */
	public final void updateBeanNotNull(T bean, D dto) {
		Map map = null;
		try {
			if (dto != null) {
				map = this.getDto2BeanMap(dto);

				Set properties = map.keySet();

				for (Object property : properties) {
					Object value = map.get(property);
					String propertyName = property.toString();
					if (value != null) {
						try {
							BeanUtils.setProperty(bean, propertyName, value);
						}
						catch (IllegalAccessException e) {
							log.error(this, e);
						}
						catch (InvocationTargetException e) {
							log.error(this, e);
						}
					}
				}
			}
		}
		catch (ParseException e1) {
			log.error(this, e1);
		}
	}

	/**
	 * Actualiza los valores del Dto D con los valores No Nulos del Bean T
	 */
	public final void updateDtoNotNull(D dto, T bean) {
		Map map = null;
		try {
			if (bean != null) {

				map = this.getBean2DtoMap(bean);

				Set properties = map.keySet();

				for (Object property : properties) {
					Object value = map.get(property);
					String propertyName = property.toString();
					if (value != null) {
						try {
							BeanUtils.setProperty(dto, propertyName, value);
						}
						catch (IllegalAccessException e) {
							log.error(this, e);
						}
						catch (InvocationTargetException e) {
							log.error(this, e);
						}
					}
				}
			}
		}
		catch (ParseException e1) {
			log.error(this, e1);
		}
	}

	/**
	 * Crea un Bean del tipo especificado en el constructor con los valores del
	 * DTO D se usa generalmente cuando el DTO tiene TODOS los datos que
	 * queremos en el BEAN por ejemplo el alta del Bean en el sistema a partir
	 * del DTO.
	 */
	public T buildBean(D dto) {
		T bean = null;
		try {
			bean = newBeanInstance();
		}
		catch (Exception e) {
			log.error(this, e);
		}

		Map map = null;
		try {
			map = this.getDto2BeanMap(dto);
		}
		catch (ParseException e) {
			log.error(this, e);
		}
		try {
			BeanUtils.populate(bean, map);
		}
		catch (IllegalAccessException e) {
			log.error(this, e);
		}
		catch (InvocationTargetException e) {
			log.error(this, e);
		}
		return bean;
	}

	public T emptyBean() {
		T bean = null;
		try {
			bean = newBeanInstance();
		}
		catch (Exception e) {
			log.error(this, e);
		}
		return bean;
	}

	/**
	 * Crea una lista de DTO del tipo especificado en el constructor con los
	 * valores de los Beans de la lista
	 */
	public final List<D> buildDto(List<T> beans) {
		List<D> dtos = new ArrayList<D>();
		for (T bean : beans) {
			dtos.add(this.buildDto(bean));
		}
		return dtos;
	}

	/**
	 * Por si hay problemas con el BeanUtils.populate
	 * @param dest
	 * @param propertiesValues
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unused")
	private void populate(Object dest, Map<String, Object> propertiesValues) throws IllegalAccessException, InvocationTargetException {
		Set<String> properties = propertiesValues.keySet();

		for (String property : properties) {
			Object value = propertiesValues.get(property);
			BeanUtils.setProperty(dest, property, value);
		}
	}

	protected abstract Map<String, Object> getBean2DtoMap(T bean) throws ParseException;

	protected abstract Map<String, Object> getDto2BeanMap(D dto) throws ParseException;
	
	protected abstract T newBeanInstance();
	
	protected abstract D newDtoInstance();
}
