package com.pragma.bus.impl;

import java.util.HashMap;
import java.util.List;

import com.fontar.seguridad.AuthenticationService;
import com.pragma.bus.api.GenericService;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.ContextUtil;

/**
 * Documentar el proposito de la clase!
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.2 $
 */
public class GenericServiceImpl implements GenericService {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	protected HashMap<String, GenericDao> hashDao = new HashMap<String, GenericDao>();

	protected Class type;

	// ~ Constructors
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Crea un objeto <code>GenericServiceImpl</code>.
	 * 
	 * @param type Documentar el parametro!
	 */
	public GenericServiceImpl(Class type) {
		this.type = type;
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Class getType() {
		return type;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public HashMap<String, GenericDao> getHashDao() {
		return hashDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pragma.bus.impl.GenericService#setHashDao(java.util.HashMap)
	 */
	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param hashDao Documentar el parametro!
	 */
	public void setHashDao(HashMap<String, GenericDao> hashDao) {
		this.hashDao = hashDao;
	}

	//
	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param clase Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public List getAll(Class clase) {
		return ((GenericDao) hashDao.get(clase.getSimpleName())).getAll();
	}

	public List getAll() {
		return ((GenericDao) hashDao.get(type.getSimpleName())).getAll();
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param id Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Object load(Long id) {
		return load(type, id);
	}

	@SuppressWarnings("unchecked")
	public Object load(Class clase, Long id) {
	
		return ((GenericDao) hashDao.get(clase.getSimpleName())).read(id);
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param transientObject Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Object save(Object transientObject) {
		String clase = transientObject.getClass().getSimpleName();

		return ((GenericDao) hashDao.get(clase)).save(transientObject);
	}

	public Object update(Object transientObject) {
		return ((GenericDao) hashDao.get(transientObject.getClass().getSimpleName())).update(transientObject);
	}

	/**
	 * Graba una serie de objetos
	 */
	public final void save(Object... arguments) {
		storeBeans(arguments);
	}

	/**
	 * Método que se usa para implementar el grabado datos para un determinado
	 * action
	 */
	public void storeBeans(Object... arguments) {
	}

	/**
	 * Guarda un objeto DAO enviado como parametro
	 */
	protected final void saveBean(Object bean) {

		(hashDao.get(bean.getClass().getSimpleName())).save(bean);
	}

	/**
	 * Modifica una serie de objetos
	 */
	public final void update(Object... arguments) {
		updateBeans(arguments);
	}

	/**
	 * Método que se usa para implementar la modificación de datos para un
	 * determinado action
	 */
	public void updateBeans(Object... arguments) {
	}

	/**
	 * Modifica un objeto DAO enviado como parametro
	 */
	protected final void updateBean(Object bean) {

		(hashDao.get(bean.getClass().getSimpleName())).update(bean);
	}

	/**
	 * Elimina una serie de objetos
	 */
	public final void delete(Object... arguments) {
		deleteBeans(arguments);
	}

	/**
	 * Método que se usa para implementar la eliminación de objetos para un
	 * determinado action
	 */
	public void deleteBeans(Object... arguments) {
	}

	public void delete(Object transientObject) {

		((GenericDao) hashDao.get(transientObject.getClass().getSimpleName())).delete(transientObject);
	}

	/**
	 * Elimina un objeto DAO enviado como parametro
	 */
	protected final void deleteBean(Object bean) {

		(hashDao.get(bean.getClass().getSimpleName())).delete(bean);
	}

	public GenericDao getDao(Class clase) {
		return (GenericDao) hashDao.get(clase.getSimpleName());
	}
	
	public String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}

}