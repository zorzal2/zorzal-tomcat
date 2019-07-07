package com.pragma.data.api.assembler;

import java.util.List;

public interface Assembler<T, D> {
	public D buildDto(T bean);

	public T buildBean(D dto);

	public void updateBeanNotNull(T bean, D dto);

	public List<D> buildDto(List<T> beans);

	public void updateDtoNotNull(D dto, T bean);
}
