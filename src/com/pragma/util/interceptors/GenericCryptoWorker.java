/**
 * 
 */
package com.pragma.util.interceptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.seguridad.cripto.CriptoSistema;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;
import com.pragma.util.ReflectionUtil;

/**
 * @author fferrara
 * 
 */
// TODO localizar clave privada del usuario
// TODO localizar usuario
// TODO crear las entidades de las claves - DEBERIA IR CONFIGURADO POR PAR
// ATRIBUTO:_ATRIBUTO_DATA
// TODO implementar crypt/decrypt - DONE
public class GenericCryptoWorker implements CryptoWorker {

	/*
	 * =======================================================================================
	 * ATRIBUTOS DE INSTANCIA - Encapsulamiento ThreadLocal (thread safe).
	 * ====================================================================================
	 */

	private HashMap<String, EntityCryptoInfo> criptoInfo = new HashMap<String, EntityCryptoInfo>();

	private ThreadLocal<Session> session = new ThreadLocal<Session>();

	private ThreadLocal<PrivateKey> privateKey = new ThreadLocal<PrivateKey>();

	private ThreadLocal<byte[]> simetricKey = new ThreadLocal<byte[]>();

	private ThreadLocal<Object> entity = new ThreadLocal<Object>();

	private ThreadLocal<String> entityName = new ThreadLocal<String>();

	private ThreadLocal<Object[]> state = new ThreadLocal<Object[]>();

	private ThreadLocal<String[]> propertyNames = new ThreadLocal<String[]>();
	
	private CriptoSistema criptoSistema;
	

	/*
	 * =======================================================================================
	 * METODOS DE CIFRADO Y DESCIFRADO
	 * ====================================================================================
	 */


	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#crypt()
	 */
	public void crypt() {

		String username = getUserName();
		byte[] claveTabla = obtenerClaveTabla(username, getEntityName());
		if (claveTabla == null) {
			// TODO: Loguear la falta de clave. El dato pasa a ser -CRYPT-

		}
		setSimetricKey(claveTabla);
		String dataAttribute;
		EntityCryptoInfo entityCryptoInfo = getCriptoInfo().get(getEntityName());
		for (String visibleAttribute : entityCryptoInfo.getCrytoFields().keySet()) {
			dataAttribute = entityCryptoInfo.getCrytoFields().get(visibleAttribute);
			// ReflectionUtil.setAttribute(getEntity(),dataAttribute,doCrypt(ReflectionUtil.getAttribute(getEntity(),visibleAttribute)));
			// modifyState(dataAttribute,doCrypt(retrieveState(visibleAttribute)));

			modifyState(dataAttribute, doCrypt(ReflectionUtil.getAttribute(getEntity(), visibleAttribute)));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#decrypt()
	 */
	public void decrypt() {
		String username = getUserName();
		byte[] claveTabla = obtenerClaveTabla(username, getEntityName());
		if (claveTabla == null) {
			// TODO si no tiene clave???
			return;
		}
		setSimetricKey(claveTabla);
		String dataAttribute;
		EntityCryptoInfo entityCryptoInfo = getCriptoInfo().get(getEntityName());
		for (String visibleAttribute : entityCryptoInfo.getCrytoFields().keySet()) {
			dataAttribute = entityCryptoInfo.getCrytoFields().get(visibleAttribute);
			// modifyState(visibleAttribute,doDecrypt(retrieveState(dataAttribute)));
			/*
			 * ReflectionUtil.setAttribute( getEntity(), visibleAttribute,
			 * doDecrypt( ReflectionUtil.getAttribute( getEntity(),
			 * dataAttribute ) ) );
			 */
			ReflectionUtil.setAttribute(getEntity(), visibleAttribute, doDecrypt(retrieveState(dataAttribute)));
		}
	}

	/*
	 * =======================================================================================
	 * HELPERS: ACCESO A LA API DE CRIPTOSISTEMA
	 * ====================================================================================
	 */

	/**
	 * cifra el objeto property y devuelve un byte[] desde la api de
	 * CriptoSistema.
	 * @param property
	 * @return
	 */
	protected Object doCrypt(Object property) {
		if (property == null)
			return null;

		if (getPrivateKey() == null) {
			throw new NoEncryptContextException(null);
		}

		try {
			// TODO: FF / que pasa cuando es nula la secret key???
			SecretKey secretKey = criptoSistema.desencriptarCrearClaveSimetricaConClavePrivada(getSimetricKey(), getPrivateKey());
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(property);
			return criptoSistema.encriptarDatosConClaveSimetrica(bos.toByteArray(), secretKey);
		}
		catch (Exception e) {
			throw new RuntimeException("Error durante la encriptación", e);
			// TODO crear, declarar y lanzar excepcion acorde.
		}
	}

	/**
	 * recibe un byte[] attribute y devuelve un objeto del tipo correspondiente
	 * desencriptado por la api de CriptoSistema.
	 * @param attribute
	 * @return
	 */
	protected Object doDecrypt(Object attribute) {
		if (attribute == null)
			return null;
		try {

			if (getPrivateKey() != null) {
				SecretKey secretKey = criptoSistema.desencriptarCrearClaveSimetricaConClavePrivada(getSimetricKey(), getPrivateKey());
				byte[] data = criptoSistema.desencriptarDatosConClaveSimetrica((byte[]) attribute, secretKey);
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				ObjectInputStream ois = new ObjectInputStream(bis);
				return ois.readObject();
			}
			else {
				return null;
			}

		}
		catch (Exception e) {
			throw new RuntimeException("Error durante la desencriptación", e);
			// TODO crear, declarar y lanzar excepcion acorde.
		}
	}

	/*
	 * =======================================================================================
	 * HELPERS: STATE ACCESS
	 * ====================================================================================
	 */
	private void modifyState(String propertyName, Object object) {
		int i = 0;
		for (String name : getPropertyNames()) {
			if (name.equals(propertyName)) {
				getState()[i] = object;
				return;
			}
			i++;
		}
	}

	private Object retrieveState(String propertyName) {
		int i = 0;
		for (String name : getPropertyNames()) {
			if (name.equals(propertyName)) {
				return getState()[i];
			}
			i++;
		}
		return null;
	}

	/*
	 * =======================================================================================
	 * HELPERS: CLAVES
	 * ====================================================================================
	 */

	protected byte[] obtenerClaveTabla(String username, String bean) {
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		Query query = getSession().createQuery("select claves.clave from ClavesBean claves Where claves.nombreUsuario=:username And claves.bean=:bean");
		query.setString("username", username);
		query.setString("bean", service.getType(bean));
		byte[] clave = (byte[]) query.uniqueResult();
		return clave;
	}

	/*
	 * =======================================================================================
	 * ACCESSORS AND MUTATORS
	 * ====================================================================================
	 */

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getCriptoInfo()
	 */
	public HashMap<String, EntityCryptoInfo> getCriptoInfo() {
		return criptoInfo;
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setCriptoInfo(java.util.HashMap)
	 */
	public void setCriptoInfo(HashMap<String, EntityCryptoInfo> criptoInfo) {
		this.criptoInfo = criptoInfo;
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getSession()
	 */
	public Session getSession() {
		return session.get();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setSession(org.hibernate.Session)
	 */
	public void setSession(Session session) {
		this.session.set(session);
	}

	protected String getUserName() {
		return this.getUserDetails().getUsername();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getPrivateKey()
	 */
	protected PrivateKey getPrivateKey() {
		return this.getUserDetails().getPrivateKey();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setPrivateKey(java.security.PrivateKey)
	 */
	protected void setPrivateKey(PrivateKey privateKey) {
		this.privateKey.set(privateKey);
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getSimetricKey()
	 */
	protected byte[] getSimetricKey() {
		return simetricKey.get();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setSimetricKey(byte[])
	 */
	protected void setSimetricKey(byte[] simetricKey) {
		this.simetricKey.set(simetricKey);
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getEntity()
	 */
	public Object getEntity() {
		return entity.get();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setEntity(java.lang.Object)
	 */
	public void setEntity(Object entity) {
		this.entity.set(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#getEntityName()
	 */
	public String getEntityName() {
		return this.entityName.get();
	}

	/*
	 * (non-Javadoc)
	 * @see test.pragma.fontar.interceptors.CryptoWorker#setEntityName(java.lang.String)
	 */
	public void setEntityName(String entityName) {
		this.entityName.set(entityName);
	}

	public String[] getPropertyNames() {
		return propertyNames.get();
	}

	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames.set(propertyNames);
	}

	public Object[] getState() {
		return state.get();
	}

	public void setState(Object[] state) {
		this.state.set(state);
	}

	public Usuario getUserDetails(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserDetails();
	}

	public CriptoSistema getCriptoSistema() {
		return criptoSistema;
	}

	public void setCriptoSistema(CriptoSistema criptoSistema) {
		this.criptoSistema = criptoSistema;
	}
}
