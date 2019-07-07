package com.fontar.seguridad.cripto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.fontar.data.api.dao.ClavesBeanDAO;
import com.fontar.data.impl.domain.bean.ClavesBean;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.bus.BusinessException;
import com.pragma.util.ContextUtil;

public class FontarCryptographicService {

	private static final Log logger;
	static {
		logger = LogFactory.getLog(FontarCryptographicService.class);
	}

	
	private String applicationUserName = "application";

	private CriptoSistema criptoSistema;

	private String privateKeysLocation;

	private String applicationPrivateKeyLocation;

	private String applicationPublicKeyLocation;

	// Mantiene la lista de datos que requiere encriptacion
	private Collection<String> requiredEncryptionData;
	
	private Properties typeToEncryptionData;

	// Administra las claves
	private ClavesBeanDAO clavesBeanDAO;
	
	private AuthenticationService authenticationService;
	
	public Properties getTypeToEncryptionData() {
		return typeToEncryptionData;
	}

	public void setTypeToEncryptionData(Properties typeToEncryptionData) {
		this.typeToEncryptionData = typeToEncryptionData;
	}

	public AuthenticationService getAuthenticationService() {
		if (this.authenticationService == null)
			this.setAuthenticationService((AuthenticationService) ContextUtil.getBean("authenticationService"));
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public ClavesBeanDAO getClavesBeanDAO() {
		if (this.clavesBeanDAO == null)
			this.setClavesBeanDAO((ClavesBeanDAO) ContextUtil.getBean("clavesBeanDao"));
		return clavesBeanDAO;
	}

	public CriptoSistema getCriptoSistema() {
		return criptoSistema;
	}

	public void setCriptoSistema(CriptoSistema criptoSistema) {
		this.criptoSistema = criptoSistema;
	}

	public void setClavesBeanDAO(ClavesBeanDAO clavesBeanDAO) {
		this.clavesBeanDAO = clavesBeanDAO;
	}

	public Collection<String> getRequiredEncryptionData() {
		return requiredEncryptionData;
	}

	public void setRequiredEncryptionData(Collection<String> requiredEncryptionData) {
		this.requiredEncryptionData = requiredEncryptionData;
	}

	public String getApplicationPublicKeyLocation() {
		return applicationPublicKeyLocation;
	}

	public void setApplicationPublicKeyLocation(String applicationPublicKeyLocation) {
		this.applicationPublicKeyLocation = applicationPublicKeyLocation;
	}

	public String getApplicationPrivateKeyLocation() {
		return applicationPrivateKeyLocation;
	}

	public void setApplicationPrivateKeyLocation(String applicationPrivateKeyLocation) {
		this.applicationPrivateKeyLocation = applicationPrivateKeyLocation;
	}

	private char[] getTmpPrivateKeyPassword() {
		return "private.key.tmp.password".toCharArray();
	}

	public String getPrivateKeysLocation() {
		return privateKeysLocation;
	}

	public void setPrivateKeysLocation(String privateKeysLocation) {
		this.privateKeysLocation = privateKeysLocation;
	}

	protected KeyPair generateUserKeyPar() {
		return criptoSistema.generarClavesUsuario();
	}

	public KeyPair generateUserKeys() {
		return this.generateUserKeyPar();

	}

	private String resolvePrivateKeyFile(String userId) {
		return this.privateKeysLocation + userId + ".key";
	}

	private String resolveTmpPrivateKeyFile(String userId) {
		return this.privateKeysLocation + userId + ".key.tmp";
	}

	public void saveUserTmpPrivateKey(String userId, PrivateKey privateKey) {
		File file = new File(this.resolveTmpPrivateKeyFile(userId));
		this.criptoSistema.guardarClavePrivadaDeUsuario(file, privateKey, this.getTmpPrivateKeyPassword());
	}

	public PrivateKey loadUserTmpPrivateKey(String userId) throws PasswordInvalidaException {
		File file = new File(this.resolveTmpPrivateKeyFile(userId));
		return this.criptoSistema.getPrivateKey(file, this.getTmpPrivateKeyPassword());
	}

	public void encryptTmpPrivateKey(String userId, String password) {

		PrivateKey privateKey;
		try {
			privateKey = this.loadUserTmpPrivateKey(userId);

			// Elimina la clave private temporal
			File file = new File(this.resolveTmpPrivateKeyFile(userId));
			file.delete();

			// Encripta con la clave ingresada por el usuario
			File privateKeyFile = new File(this.resolvePrivateKeyFile(userId));
			this.criptoSistema.guardarClavePrivadaDeUsuario(privateKeyFile, privateKey, password.toCharArray());

		}
		catch (PasswordInvalidaException e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}

	}

	public PrivateKey loadUserPrivateKey(String userId, String password) throws PasswordInvalidaException {
		File file = new File(this.resolvePrivateKeyFile(userId));
		return this.criptoSistema.getPrivateKey(file, password.toCharArray());
	}

	public void updatePrivateKey(String userId, String currentPassword, String newPassword) {
		try {
			PrivateKey privateKey = this.criptoSistema.validarPasswordEncriptacion(userId, currentPassword.toCharArray());
			this.criptoSistema.guardarClavePrivadaDeUsuario(userId, privateKey, newPassword.toCharArray());
		}
		catch (PasswordInvalidaException e) {
			logger.info(e);
			throw new BusinessException(e.getMessage());
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la actualización de la clave privada del usuario");
		}
	}

	public byte[] certifyPublicKey(PublicKey publicKey , String appPassword) {
		PrivateKey privateKey;
		try {
			privateKey = this.criptoSistema.obtenerClavePrivadaAplicacion(appPassword.toCharArray());
			return this.criptoSistema.certificarClavePublica(publicKey, privateKey);
		}
		catch (PasswordInvalidaException e) {
			logger.info(e);
			throw new BusinessException(e.getMessage());
		}
	}

	public SecretKey decrypt(byte[] encryptedSecretKey, PrivateKey privateKey) throws Exception {
		byte[] encodeSecretKey = this.criptoSistema.desencriptarClaveSimetricaConClavePrivada(encryptedSecretKey, privateKey);
		return this.criptoSistema.crearClaveSimetricaDesdeEncode(encodeSecretKey);
	}

	// Application Keys
	private PrivateKey getApplicationPrivateKey(char[] password) throws PasswordInvalidaException {
		File file = new File(this.getApplicationPrivateKeyLocation());
		return this.criptoSistema.getPrivateKey(file, password);
	}
	
	public boolean validatetApplicationPrivateKey(char[] password){
		try{
			PrivateKey privateKey = this.getApplicationPrivateKey(password);
			return privateKey != null;
		}catch (Exception e) {
			return false;
		}
	}

	private PrivateKey getApplicationPrivateKey(String password) throws PasswordInvalidaException {
		return this.getApplicationPrivateKey(password.toCharArray());
	}


	public PublicKey getApplicationPublicKey() {
		File file = new File(this.getApplicationPublicKeyLocation());
		return this.criptoSistema.loadPublicKey(file);
	}

	/**
	 * Genera una clave simetrica aleatoria por cada tipo de dato que requiere
	 * encriptacion. La clave es encriptada con la clave publica del usuario y
	 * es persisitada en ClavesBean.
	 */

	private void initiazeEncriptionKeys(String userId, PublicKey publicKey , String appPassword) {
		for (String dataType : this.getRequiredEncryptionData()) {
			SecretKey secretKey = this.getSimetricKeyFromApplicationUser(dataType , appPassword);
			byte[] encryptedKey = this.criptoSistema.encriptarClaveSimetricaConClavePublica(secretKey.getEncoded(), publicKey);
			ClavesBean clavesBean = new ClavesBean(dataType, userId, encryptedKey);
			this.getClavesBeanDAO().create(clavesBean);
		}
	}

	
	
	/**
	 * Genera una clave simetrica aleatoria por cada tipo de dato que requiere
	 * encriptacion. La clave es encriptada con la clave publica de la
	 * aplicacion y es persisitada en ClavesBean.
	 */
	private void initiazeEncriptionKeys(String appPassword , PublicKey publicKey) {
		for (String dataType : this.getRequiredEncryptionData()) {
			//Genera un secret key para el tipo de datos
			SecretKey secretKey = this.criptoSistema.generarClaveDatos();
			//Se encripta con la clave publica
			byte[] encryptedKey = this.criptoSistema.encriptarClaveSimetricaConClavePublica(secretKey.getEncoded(), publicKey);
			//Se asocia al clave al usuario de apliaccion y se persiste
			ClavesBean clavesBean = new ClavesBean(dataType, this.applicationUserName, encryptedKey);
			this.getClavesBeanDAO().create(clavesBean);
		}
	}
	
	public void initialiazeRequiredEncryptionData(String userId, PublicKey publicKey , String appPassword) {
		this.initiazeEncriptionKeys(userId, publicKey, appPassword);
	}

	public void removeRequiredEncryptionData(String userId) {
		// Remueve las claves
		List<ClavesBean> claves = this.clavesBeanDAO.findByUser(userId);
		for (ClavesBean clave : claves) {
			this.clavesBeanDAO.delete(clave);
		}
		// Remueve la clave privada encriptada en el fs
		File file = new File(this.resolvePrivateKeyFile(userId));
		if (file != null)
			file.delete();
	}

	/**
	 * Recupera la SecretKey para un dato desde el Usuario Application 
	 * @param dataType
	 * @return SecretKey
	 */
	protected SecretKey getSimetricKeyFromApplicationUser(String dataType , String appPassword) {
		ClavesBean claveBean = this.clavesBeanDAO.selectByUserAndDataType(dataType,applicationUserName);
		if (claveBean != null){		
			try {
				return this.criptoSistema.desencriptarCrearClaveSimetricaConClavePrivada(claveBean.getClave(), this.getApplicationPrivateKey( appPassword));
			}catch (PasswordInvalidaException e) {
				throw new BusinessException(MessageFormat.format("La clave de applicacion ingreasada por {0} no es correcta",this.authenticationService.getUserName(),dataType));
			}
		} else {
			throw new BusinessException(MessageFormat.format("El usuario {0} no tiene clave de encriptación para el bean {1}",applicationUserName,dataType));
		}
	}

	/**
	 * Obtiene la clava privada del usuario, se desecntripta con la password y
	 * se deja el contexto de encriptacion habilitado para su posterior uso
	 * @throws PasswordInvalidaException
	 */
	public void startUpEncryptionService(String password) throws PasswordInvalidaException {
		PrivateKey privateKey = this.criptoSistema.validarPasswordEncriptacion(password.toCharArray());
		Usuario usuario = this.getCurrentUser();
		usuario.setPrivateKey(privateKey);
	}

	/**
	 * Retorna el usuario autenticado
	 */
	private Usuario getCurrentUser() {
		return this.getAuthenticationService().getUserDetails();
	}

	/**
	 * La clave privada del usuario debe estar disponible
	 */
	public boolean encyptionAvailable() {
		return this.getCurrentUser().getPrivateKey() != null;
	}

	public byte[] encrypt(Object object, Class clazz) {
		Usuario usuario = this.getCurrentUser();
		
		if (usuario.getPrivateKey() == null){
			throw new BusinessException(MessageFormat.format("El usuario {0} no tiene contexto de encriptación",usuario.getUsername()));
		}
		
		try {
			SecretKey secretKey = this.getSimetricKey(usuario, clazz);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			return criptoSistema.encriptarDatosConClaveSimetrica(bos.toByteArray(), secretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al cifrar el dato para el usuario " + usuario.getUserId());
		}

	}

	public Object decrypt(byte[] value, Class clazz) {
		Usuario usuario = this.getCurrentUser();
		
		if (usuario.getPrivateKey() == null){
			throw new BusinessException(MessageFormat.format("El usuario {0} no posee contexto de encriptación",usuario.getUsername()));
		}
		
		try {
			SecretKey secretKey = this.getSimetricKey(usuario, clazz);
			byte[] data = criptoSistema.desencriptarDatosConClaveSimetrica((byte[]) value, secretKey);
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();

		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al descifrar el dato para el usuario " + usuario.getUserId());
		}

	}

	private SecretKey getSimetricKey(Usuario usuario, Class userType) {
		return this.getSimetricKey(usuario,userType.getSimpleName());
	}
	
	
	public String getType(String userType) {
		return typeToEncryptionData.getProperty(userType);
	}
 
	private SecretKey getSimetricKey(Usuario usuario, String userType) { 
        try { 
             SessionFactory sessionFactory = (SessionFactory) ContextUtil.getBean("sessionFactory"); 
             Query query = sessionFactory.openSession().createQuery("select claves.clave from ClavesBean claves Where claves.nombreUsuario=:username And claves.bean=:bean");  
             query.setString("username", usuario.getUsername()); 
             query.setString("bean", this.getType(userType)); 
             byte[] clave = (byte[]) query.uniqueResult(); 
             return this.criptoSistema.desencriptarCrearClaveSimetricaConClavePrivada(clave, usuario.getPrivateKey()); 
        }catch (Exception e) { 
             throw new RuntimeException(e); 
        } 
   }

	public void shutDownEncryptionService() {
		Usuario usuario = this.getCurrentUser();
		usuario.setPrivateKey(null);
	}

	
	public void startApplicationEncryptionContext(String appPassword){
		//Par de claves para la aplicacion
		KeyPair keyPair = this.generateUserKeyPar();
		
		//Se guarda la clave privada blindada con @appPassword
		this.criptoSistema.guardarClavePrivadaDeAplicacion(keyPair.getPrivate(), appPassword.toCharArray());
		
		//Se guarda la clave publica de la aplicacion
		PublicKey publicKey = keyPair.getPublic();
		this.criptoSistema.guardarClavePublicaDeAplicacion(publicKey);
		
		//Se generan las claves por tipo de datos requeridas para encriptar datos
		this.initiazeEncriptionKeys(appPassword, publicKey);
		
	}
	
	public Status getStatus(){
		Status status = new Status();
		try{
			status.setApplicationKeys( this.getApplicationPublicKey() != null);
		}catch (Exception e) {
			status.setApplicationKeys(false);
		}
		status.setClaves( this.clavesBeanDAO.findByUser(this.applicationUserName));
		
		return status;
	}
}
