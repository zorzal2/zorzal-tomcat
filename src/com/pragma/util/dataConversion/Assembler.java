package com.pragma.util.dataConversion;

import java.util.Collection;

import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

@Deprecated
public interface Assembler {

	/**
	 * Obtiene bean a partir del dto
	 * @param bean
	 * @return dto
	 */
	public abstract Bean getBean(DTO dto);

	public abstract DTO buildDTO(Bean bean);

	/**
	 * Actualiza un bean a partir de un DTO. No copia los valores nulos.
	 * @param tabla1Dto
	 * @return
	 * @throws Exception 
	 */
	public abstract Bean updateBean(DTO dto);

	/**
	 * Crea un bean nuevo a partir del Dto
	 * @param tabla1Dto
	 * @return
	 * @throws Exception 
	 */
	public abstract Bean createBean(DTO dto);

	/**
	 * Crea una lista de DTOs a partir de una lista de beans
	 * @param List of beans
	 * @return List of dtos
	 */
	public abstract Collection buildDTOs(Collection beans);

	/**
	 * Crea una lista de beans a partir de una lista de DTOs
	 * @param List of DTOs
	 * @return List of Beans
	 */
	public abstract Collection getBeans(Collection dtos);
	
	public abstract Class getBeanClass();
	public abstract Class getDTOClass();

}