package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.seguridad.SeguridadObjetoBean;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

/** 
 * @author llobeto
 */
@HasNamedParams public interface SeguridadObjetoDAO extends GenericDao<SeguridadObjetoBean, Long> {
	/**
	 * Devuelve los permisos por instancia relacionados con el usuario dado.
	 * @param userId
	 * @return
	 */
	public List<SeguridadObjetoBean> findByUsuario(@ParamName("userId") String userId);
	/**
	 * Devuelve los permisos para realizas una accion para el usuario dado.
	 * @return
	 */
	public List<SeguridadObjetoBean> findByUsuarioYAccion(@ParamName("userId") String userId, @ParamName("accion") String accion);
	/**
	 * Devuelve los permisos por instancia relacionados con el objeto determinado
	 * por los argumentos clase y id.
	 * @param bean
	 * @return
	 */
	public List<SeguridadObjetoBean> findByObjeto(@ParamName("className") String className, @ParamName("idObjeto") Long idObjeto);
	/**
	 * Devuelve los permisos por instancia definidos para la accion dada sobre el
	 * objeto determinado por los argumentos clase y id.
	 * @return
	 */
	public List<SeguridadObjetoBean> findByObjetoYAccion(@ParamName("className") String className, @ParamName("idObjeto") Long idObjeto, @ParamName("accion") String accion);
	/**
	 * Devuelve los permisos por instancia definidos para el usuario dado sobre el
	 * objeto determinado por los argumentos clase y id.
	 * @return
	 */
	public List<SeguridadObjetoBean> findByObjetoYUsuario(@ParamName("className") String className, @ParamName("idObjeto") Long idObjeto, @ParamName("userId") String userId);
	/**
	 * Devuelve una lista con el unico permiso por instancia definidos para el 
	 * usuario dado sobre el objeto determinado por los argumentos accion, clase 
	 * y id. Si no existe el permiso devuelve una lista vacia.
	 * @return
	 */
	public List<SeguridadObjetoBean> findByAccionObjetoYUsuario(@ParamName("accion") String accion, @ParamName("className") String className, @ParamName("idObjeto") Long idObjeto, @ParamName("userId") String userId);
	/**
	 * Devuelve los permisos para realizar una accion determinada, por un usuario
	 * sobre cualquier instancia de la calse dada.
	 * @param className
	 * @param userId
	 * @param accion
	 * @return
	 */
	public List<SeguridadObjetoBean> findInstancias(@ParamName("className") String className, @ParamName("userId") String userId, @ParamName("accion") String accion);
}
