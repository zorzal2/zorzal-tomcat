package com.fontar.bus.api.configuracion;

import java.math.BigDecimal;
import java.util.Set;

import com.fontar.bus.impl.CuitNoConcuerdaException;
import com.fontar.bus.impl.ObjetoEnUsoException;
import com.fontar.bus.impl.PersonaDatosInsuficientesException;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.dto.ComisionDTO;
import com.fontar.data.impl.domain.dto.EntidadDTO;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;

/**
 * 
 * 
 */
public interface ConfiguracionServicio {
	/**
	 * Persiste los datos de un DTO de Persona.
	 * @param dto
	 */
	public abstract void savePersona(PersonaDTO dto);

	/**
	 * Permite definir o bien actualizar los datos de una persona. 
	 * @param dto
	 * @throws PersonaDatosInsuficientesException
	 */
	public abstract void createOrUpdatePersona(PersonaDTO dto) throws PersonaDatosInsuficientesException;
	
	/**
	 * Devuelve un Bean de persona a partide un DTO de Persona.
	 * @param dto
	 * @return
	 * @throws PersonaDatosInsuficientesException
	 */
	public abstract PersonaBean obtenerPersonaCoincidente(PersonaDTO dto) throws PersonaDatosInsuficientesException;

	/**
	 * Obtiene el conjuto de las personas relacionadas con el nombre y cuit informados.
	 * @param nombre
	 * @param cuit
	 * @return
	 */
	public abstract Set<PersonaBean> obtenerPersonasCoincidentes(String nombre, String cuit);

	/**
	 * Obtiene el DTO de persona para el identificador informado.
	 * @param idPersona
	 * @return
	 */
	public abstract PersonaDTO obtenerPersona(Long idPersona);

	/**
	 * Persiste los datos de la entidad.
	 * @param dto
	 * @throws CuitNoConcuerdaException
	 */
	public abstract void saveEntidad(EntidadDTO dto) throws CuitNoConcuerdaException;

	/**
	 * Devuelve un DTO para la entidad a partir de un identificador de la misma.  
	 * @param idEntidad
	 * @return
	 */
	public abstract EntidadDTO obtenerEntidad(Long idEntidad);

	/***
	 * Devuelve un DTO evaluador a partir de un identificador de evaluador.
	 * @param id
	 * @return
	 */
	public abstract EvaluadorDTO obtenerEvaluador(Long id);

	/**
	 * Obtiene un DTO de comision a partir de un identificador de comision.
	 * @param idComision
	 * @return
	 */
	public abstract ComisionDTO obtenerComision(Long idComision);

	/**
	 * Persiste los cambios en una comision.
	 * @param comisionDTO
	 */
	public void saveComision(ComisionDTO comisionDTO);
	
	/**
	 * Obtiene un Bean para la entidad evaluadora en al cual se desempeña un cierto evaluador.
	 * @param bean
	 * @return
	 */
	public EntidadEvaluadoraBean obtenerEntidadDesemp(EvaluadorBean bean);

	public abstract void saveEdicionMonto(String entidad, Long id, String propiedad, BigDecimal valorNuevo);
	
	/**
	 * Elimina una persona cuando no está en uso en el sistema. Si la persona está participando
	 * de un proyecto, una comisión, etc., no puede eliminarse y se dispara la excepción
	 * <code>ObjetoEnUsoException</code> con una descripción de los lugares en los cuales
	 * se utiliza.
	 * @param persona
	 * @throws ObjetoEnUsoException
	 */
	public void deletePersona(Long idPersona) throws ObjetoEnUsoException;
	
	/**
	 * Elimina una entidad cuando no está en uso en el sistema. Si la entidad está en uso
	 * se dispara la excepción <code>ObjetoEnUsoException</code> con una descripción de los 
	 * lugares en los cuales en donde se utiliza.
	 * @param persona
	 * @throws ObjetoEnUsoException
	 */
	public void deleteEntidad(Long idPersona) throws ObjetoEnUsoException;
	/**
	 * Devuelve la persona con el cuit dado o null si no hay ninguna. Si encontrara
	 * más de una persona con el mismo cuit devuelve una arbitraria.
	 * @param cuit
	 * @return
	 */
	public PersonaDTO obtenerPersonaConCuit(String cuit);
}