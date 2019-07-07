package com.pragma.toolbar.config.user_config;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.pragma.toolbar.data.TransactionalCommand;
import com.pragma.toolbar.data.bean.UserConfigItemBean;
import com.pragma.toolbar.data.bean.VariantBean;
import com.pragma.toolbar.exception.ControlledException;
import com.pragma.toolbar.exception.UnControlledException;
import com.pragma.util.hibernate.DAO;

/**
 * Guarda y persiste propiedades que determinan la configuracion propia de un uduario.
 * @author llobeto
 *
 */
//TODO Interfaceame, animal!
public class UserConfiguration {
    private static Map UserId2Uri2Value = new Hashtable();

    public static UserConfiguration forUser(String userId) {
	return new UserConfiguration(userId);
    }
    
    private String userId;
    private UserConfiguration(String userId) {
	this.userId = userId;
    }
    public Object getProperty(String uri) {
	return getProperty(userId, uri);
    }
    public void setProperty(String uri, Object value) {
	if(value==null) throw new NullPointerException(uri+" cannot be null");
	setProperty(userId, uri, value);
    }
    public boolean hasProperty(String uri) {
	return hasProperty(userId, uri);
    }
    public void removeProperty(String uri) {
	removeProperty(userId, uri);
    }
    //TODO ver si se deja esto o algo similar para no sobrecargar la memoria
/*    protected void finalize() throws Throwable {
        unloadUser(userId);
        super.finalize();
    }*/
    
    /**
     * Carga en el map las configuracines de un usuario.
     * @param userId
     */
    private static void loadUser(String userId) {
	Map userConfigMap;
	try {
	    userConfigMap=(Map)(new TransactionalCommand() {
		public Object run(Object[] params) throws ControlledException {
		    String userId = (String) params[0];
		
		DAO userConfigItemBeanDao = DAO.getInstance(UserConfigItemBean.class);
		
		Criteria criteria = userConfigItemBeanDao.newCriteria();
		criteria.add(Expression.eq("userId", userId));
		Collection configItems = userConfigItemBeanDao.getAll(criteria);
		Map retMap = new Hashtable();
		for (Iterator iter = configItems.iterator(); iter.hasNext();) {
		    UserConfigItemBean bean = (UserConfigItemBean) iter.next();
		    retMap.put(bean.getUri(), bean.getValue().getValue());
		}
		return retMap;
	    }
	}).execute(userId);
	} catch(ControlledException e) {
		e.printStackTrace();
	    throw new UnControlledException(e);
	}
	UserId2Uri2Value.put(userId, userConfigMap);
    }
    /**
     * Elimina del mapa las configuraciones que hubiera de un usuario.
     * @param userId
     */
    private static void unloadUser(String userId) {
	UserId2Uri2Value.remove(userId);
    }
    private static Object getProperty(String userId, String uri) {
	if(!isLoaded(userId)) loadUser(userId);
	
	return ((Map)UserId2Uri2Value.get(userId)).get(uri);
    }
    private static boolean isLoaded(String userId) {
	return UserId2Uri2Value.get(userId)!=null;
    }
    private static boolean hasProperty(String userId, String uri) {
	if(!isLoaded(userId)) loadUser(userId);
	Map uri2value = (Map)UserId2Uri2Value.get(userId);
	return uri2value.containsKey(uri);
    }
    private static void removeProperty(String userId, String uri) {
	try {
	    (new TransactionalCommand() {
		public Object run(Object[] params) throws ControlledException {
		    String userId = (String) params[0];
		    String uri = (String) params[1];

		    DAO variantDao = DAO.getInstance(VariantBean.class);
		    DAO userConfigItemDao = DAO.getInstance(UserConfigItemBean.class);
		    
		    //Obtengo la asignacion previa
		    Criteria criteria = userConfigItemDao.newCriteria();
		    criteria
		    	.add(Expression.eq("userId", userId))
		    	.add(Expression.eq("uri", uri));


		    UserConfigItemBean itemBean = (UserConfigItemBean)userConfigItemDao.getUnique(criteria);
		    if(itemBean==null) {
			//No existia la propiedad. No hay nada que hacer.
			return Boolean.FALSE;
		    }
		    
		    //elimino la propiedad
		    userConfigItemDao.remove(itemBean);
		    variantDao.remove(itemBean.getValue());
		    
		    //Elimino la propiedad del mapa
		    Map userMap = (Map)UserId2Uri2Value.get(userId);
		    if(userMap!=null) {
			userMap.remove(uri);
		    }
		    
		    return Boolean.TRUE;
		}
	    }).execute(userId, uri);
	} catch(ControlledException e) {
	    e.printStackTrace();
	    throw new UnControlledException(e);
	}
    }
    private void setProperty(String userId, String uri, Object value) {
	if(!isLoaded(userId)) loadUser(userId);
	Map uri2value = (Map)UserId2Uri2Value.get(userId);
	if(uri2value.containsKey(uri)) {
	    changeProperty(userId, uri, value);
	} else {
	    addProperty(userId, uri, value);
	}
	uri2value.put(uri, value);
    }
    private void addProperty(String userId, String uri, Object value) {
	try {
	    (new TransactionalCommand() {
		public Object run(Object[] params) throws ControlledException {
		    String userId = (String) params[0];
		    String uri = (String) params[1];
		    Object value = params[2];

		    DAO variantDao = DAO.getInstance(VariantBean.class);
		    DAO userConfigItemDao = DAO.getInstance(UserConfigItemBean.class);

		    UserConfigItemBean itemBean = (UserConfigItemBean)userConfigItemDao.newBean();
		    itemBean.setUri(uri);
		    itemBean.setUserId(userId);
		    
		    VariantBean variantBean = (VariantBean)variantDao.newBean();
		    variantBean.setValue(value);
		    variantDao.create(variantBean);
		    
		    itemBean.setValue(variantBean);		    
		    userConfigItemDao.create(itemBean);
		    
		    return Boolean.TRUE;
		}
	    }).execute(userId, uri, value);
	} catch(ControlledException e) {
		e.printStackTrace();
	    throw new UnControlledException(e);
	}	
    }
    private void changeProperty(String userId, String uri, Object value) {
	try {
	    (new TransactionalCommand() {
		public Object run(Object[] params) throws ControlledException {
		    String userId = (String) params[0];
		    String uri = (String) params[1];
		    Object value = params[2];

		    DAO variantDao = DAO.getInstance(VariantBean.class);
		    DAO userConfigItemDao = DAO.getInstance(UserConfigItemBean.class);
		    
		    //Obtengo la asignacion previa
		    Criteria criteria = userConfigItemDao.newCriteria();
		    criteria
		    	.add(Expression.eq("userId", userId))
		    	.add(Expression.eq("uri", uri));


		    UserConfigItemBean itemBean = (UserConfigItemBean)userConfigItemDao.getUnique(criteria);
		    
		    //elimino el valor anterior
		    variantDao.remove(itemBean.getValue());
		    
		    //creo el nuevo valor
		    VariantBean variantBean = (VariantBean)variantDao.newBean();
		    variantBean.setValue(value);
		    variantDao.create(variantBean);
		    
		    //actualizo el item
		    itemBean.setValue(variantBean);
		    
		    userConfigItemDao.update(itemBean);
		    
		    return Boolean.TRUE;
		}
	    }).execute(userId, uri, value);
	} catch(ControlledException e) {
	    e.printStackTrace();
	    throw new UnControlledException(e);
	}	
    }
}
