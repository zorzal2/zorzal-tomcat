package com.fontar.seguridad.cripto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fontar.seguridad.AuthenticationService;
import com.pragma.bus.BusinessException;
import com.pragma.util.ContextUtil;

/**
 * Ofrece funcionalidades de: encripción/desencripción de datos, generación de
 * claves publicas y privadas, generación de claves simétricas, generacion de
 * claves basadas en password, encripción/desencripción de claves, generación de
 * claves en base a encode de claves, almacenamiento/lectura del claves
 * encriptadas en/desde archivos
 * @author ssanchez
 */
public class CriptoSistema {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static final Log logger;
	static {
		logger = LogFactory.getLog(CriptoSistema.class);
	}

	// default values
	private String algGeneracionClaveSimetrica = "DES";

	private String algGeneracionParClaves = "RSA";

	private String algGeneracionClaveContrasenia = "PBEWithMD5AndDES";

	private String algCertificacionClavePublica = "SHA1withRSA";

	private String algEncriptacionContrasenia = "PBEWithMD5AndDES";

	private String algEncriptacionClaveSimetrica = "RSA";

	private String algEncriptacionDatos = "DES";

	private String rutaClavesPrivadasUsuarios;

	private String rutaClavePublicaAplicacion;

	private String rutaClavePrivadaAplicacion;

	private int tamanioSalt = 8;

	private int tamanioBytesAleatorios = 16;

	private int tamanioParClaves = 1024;

	private int tamanioClaveSimetrica = 56;

	private int iteraciones = 1000;

	public String getAlgCertificacionClavePublica() {
		return algCertificacionClavePublica;
	}

	public void setAlgCertificacionClavePublica(String algCertificacionClavePublica) {
		this.algCertificacionClavePublica = algCertificacionClavePublica;
	}

	public String getAlgEncriptacionClaveSimetrica() {
		return algEncriptacionClaveSimetrica;
	}

	public void setAlgEncriptacionClaveSimetrica(String algEncriptacionClaveSimetrica) {
		this.algEncriptacionClaveSimetrica = algEncriptacionClaveSimetrica;
	}

	public String getAlgEncriptacionContrasenia() {
		return algEncriptacionContrasenia;
	}

	public void setAlgEncriptacionContrasenia(String algEncriptacionContrasenia) {
		this.algEncriptacionContrasenia = algEncriptacionContrasenia;
	}

	public String getAlgEncriptacionDatos() {
		return algEncriptacionDatos;
	}

	public void setAlgEncriptacionDatos(String algEncriptacionDatos) {
		this.algEncriptacionDatos = algEncriptacionDatos;
	}

	public String getAlgGeneracionClaveContrasenia() {
		return algGeneracionClaveContrasenia;
	}

	public void setAlgGeneracionClaveContrasenia(String algGeneracionClaveContrasenia) {
		this.algGeneracionClaveContrasenia = algGeneracionClaveContrasenia;
	}

	public String getAlgGeneracionClaveSimetrica() {
		return algGeneracionClaveSimetrica;
	}

	public void setAlgGeneracionClaveSimetrica(String algGeneracionClaveSimetrica) {
		this.algGeneracionClaveSimetrica = algGeneracionClaveSimetrica;
	}

	public String getAlgGeneracionParClaves() {
		return algGeneracionParClaves;
	}

	public void setAlgGeneracionParClaves(String algGeneracionParClaves) {
		this.algGeneracionParClaves = algGeneracionParClaves;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
	}

	public String getRutaClavePrivadaAplicacion() {
		return rutaClavePrivadaAplicacion;
	}

	public void setRutaClavePrivadaAplicacion(String rutaClavePrivadaAplicacion) {
		this.rutaClavePrivadaAplicacion = rutaClavePrivadaAplicacion;
	}

	public String getRutaClavePublicaAplicacion() {
		return rutaClavePublicaAplicacion;
	}

	public void setRutaClavePublicaAplicacion(String rutaClavePublicaAplicacion) {
		this.rutaClavePublicaAplicacion = rutaClavePublicaAplicacion;
	}

	public String getRutaClavesPrivadasUsuarios() {
		return rutaClavesPrivadasUsuarios;
	}

	public void setRutaClavesPrivadasUsuarios(String rutaClavesPrivadasUsuarios) {
		this.rutaClavesPrivadasUsuarios = rutaClavesPrivadasUsuarios;
	}

	public int getTamanioBytesAleatorios() {
		return tamanioBytesAleatorios;
	}

	public void setTamanioBytesAleatorios(int tamanioBytesAleatorios) {
		this.tamanioBytesAleatorios = tamanioBytesAleatorios;
	}

	public int getTamanioClaveSimetrica() {
		return tamanioClaveSimetrica;
	}

	public void setTamanioClaveSimetrica(int tamanioClaveSimetrica) {
		this.tamanioClaveSimetrica = tamanioClaveSimetrica;
	}

	public int getTamanioParClaves() {
		return tamanioParClaves;
	}

	public void setTamanioParClaves(int tamanioParClaves) {
		this.tamanioParClaves = tamanioParClaves;
	}

	public int getTamanioSalt() {
		return tamanioSalt;
	}

	public void setTamanioSalt(int tamanioSalt) {
		this.tamanioSalt = tamanioSalt;
	}

	/**
	 * Genera un par de claves de 1024bits mediante el algoritmo pasado por
	 * parámetro
	 * 
	 * @param algoritmo Algoritmo que se usa para generar el par de claves
	 * 
	 * @return Claves de 1024 bits generada con el algoritmo recibida como
	 * parametro
	 * 
	 * @throws Exception
	 */
	private KeyPair generarParClaves(String algoritmo) {
		KeyPair keyPair = null;

		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algoritmo);

			keyGen.initialize(this.tamanioParClaves);

			keyPair = keyGen.generateKeyPair();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la generación del par de claves");
		}

		return keyPair;
	}

	/**
	 * Genera una clave simétrica de 56 bits mediante el algoritmo pasado por
	 * parámetro
	 * 
	 * @return Clave simétrica de 56 bits generada
	 * 
	 * @throws Exception
	 */
	public SecretKey generarClaveSimetrica(String algoritmo) {
		SecretKey secretKey = null;

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algoritmo);

			keyGenerator.init(this.tamanioClaveSimetrica);

			secretKey = keyGenerator.generateKey();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la generación de la clave simétrica");
		}

		return secretKey;
	}

	/**
	 * Genera una Clave Simétrica mediante una especificacion de clave (KeySpec)
	 * 
	 * @param keySpec Especificación de la clave
	 * 
	 * @return Clave simétrica generada
	 * 
	 * @throws Exception
	 */
	public SecretKey generarClaveSimetricaConEspecDeClave(KeySpec keySpec) {
		SecretKey secretKey = null;

		try {
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance(this.algGeneracionClaveContrasenia);

			secretKey = keyFac.generateSecret(keySpec);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la generación de la clave simétrica");
		}
		finally {
			keySpec = (KeySpec) liberarObjeto();
		}

		return secretKey;
	}

	/**
	 * Genera una PBEParameterSpec mediante un Salt aleatorio y un Iterator
	 * 
	 * @return PBEParameterSpec generado
	 */
	public PBEParameterSpec generarPBEParameterSpec() {
		PBEParameterSpec pbeParamSpec = null;
		byte[] salt = null;

		try {
			int saltSize = this.tamanioSalt;

			salt = generarRandomByte(saltSize);

			int count = this.iteraciones;

			pbeParamSpec = new PBEParameterSpec(salt, count);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la generación de la clave simétrica");
		}
		finally {
			salt = (byte[]) liberarObjeto();
		}

		return pbeParamSpec;
	}

	/**
	 * Crea un objeto clave simétrica desde el byte encode de otra clave
	 * simétrica
	 * 
	 * @param encodeSecretKey Encode de la clave simetrica a generar
	 * 
	 * @return Clave simétrica generada
	 * 
	 * @throws Exception
	 */
	public SecretKey crearClaveSimetricaDesdeEncode(byte[] encodeSecretKey) {
		SecretKey secretKey = null;

		try {
			secretKey = new SecretKeySpec(encodeSecretKey, this.algGeneracionClaveSimetrica);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante creación de la clave simétrica");
		}
		finally {
			encodeSecretKey = (byte[]) liberarObjeto();
		}

		return secretKey;
	}

	/**
	 * Crea un objeto Clave publica desde el byte encode de una Clave Publica
	 * 
	 * @param encondePublicKey
	 * 
	 * @return Clave publica obtenida desde un byte encode
	 * 
	 * @throws Exception
	 */
	public PublicKey crearClavePublicaDesdeEncode(byte[] encondePublicKey) {
		PublicKey publicKey = null;
		X509EncodedKeySpec encondePublicKeySpec = null;

		try {
			encondePublicKeySpec = new X509EncodedKeySpec(encondePublicKey);

			KeyFactory keyFactory = KeyFactory.getInstance(this.algGeneracionParClaves);

			publicKey = keyFactory.generatePublic(encondePublicKeySpec);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante creación de la clave pública");
		}
		finally {
			encondePublicKey = (byte[]) liberarObjeto();
			encondePublicKeySpec = (X509EncodedKeySpec) liberarObjeto();
		}

		return publicKey;
	}

	/**
	 * Crea un objeto Clave privada desde el byte encode de una Clave privada
	 * 
	 * @param encondePrivateKey
	 * 
	 * @return Clave Privada obtenida desde un byte encode
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 */
	public PrivateKey crearClavePrivadaDesdeEncode(byte[] encondePrivateKey) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec encondePrivateKeySpec = null;

		/*
		 * try { encondePrivateKeySpec = new
		 * PKCS8EncodedKeySpec(encondePrivateKey);
		 * 
		 * KeyFactory keyFactory =
		 * KeyFactory.getInstance(this.algGeneracionParClaves);
		 * 
		 * privateKey = keyFactory.generatePrivate(encondePrivateKeySpec); }
		 * catch(Exception e) { System.out.println(e.getMessage()); } finally {
		 * encondePrivateKey = (byte[]) liberarObjeto(); encondePrivateKeySpec =
		 * (PKCS8EncodedKeySpec) liberarObjeto(); }
		 */
		encondePrivateKeySpec = new PKCS8EncodedKeySpec(encondePrivateKey);

		KeyFactory keyFactory = KeyFactory.getInstance(this.algGeneracionParClaves);
		privateKey = keyFactory.generatePrivate(encondePrivateKeySpec);

		encondePrivateKey = (byte[]) liberarObjeto();
		encondePrivateKeySpec = (PKCS8EncodedKeySpec) liberarObjeto();

		return privateKey;
	}

	/**
	 * Encripta los datos recibidos con la clave simetrica
	 * 
	 * @param secretKey
	 * @param data
	 * 
	 * @return byte array de los datos encriptados con la clave simetrica
	 * 
	 * @throws Exception
	 */
	public byte[] encriptarDatosConClaveSimetrica(byte[] data, SecretKey secretKey) {
		byte[] encriptData = null;
		Cipher desCipher = null;

		try {
			desCipher = Cipher.getInstance(this.algEncriptacionDatos);

			desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

			encriptData = desCipher.doFinal(agregarBytesAleatoriosADatos(data));
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la cifrado de datos con clave simétrica");
		}
		finally {
			data = (byte[]) liberarObjeto();
			secretKey = (SecretKey) liberarObjeto();
			desCipher = (Cipher) liberarObjeto();
			// liberarObjetosNoUsados();
		}

		return encriptData;
	}

	/**
	 * Desencripta los datos recibidos con la clave simetrica
	 * 
	 * @param secretKey
	 * @param data
	 * 
	 * @return byte array de los datos desencriptados con la clave simetrica
	 * 
	 * @throws Exception
	 */
	public byte[] desencriptarDatosConClaveSimetrica(byte[] data, SecretKey secretKey) {
		byte[] decriptData = null;
		Cipher desCipher = null;

		try {
			desCipher = Cipher.getInstance(this.algEncriptacionDatos);

			desCipher.init(Cipher.DECRYPT_MODE, secretKey);

			decriptData = desCipher.doFinal(limpiarBytesAleatoriosDeDatos(data));
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante el descifrado de datos con clave simétrica");
		}
		finally {
			data = (byte[]) liberarObjeto();
			secretKey = (SecretKey) liberarObjeto();
			desCipher = (Cipher) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return decriptData;
	}

	/**
	 * Generacion aleatoria de un byte array de size bytes
	 * 
	 * @return Devuelve un byte array de size bytes aleatorios
	 */
	public byte[] generarRandomByte(int size) {
		byte[] bytes = new byte[size];

		try {
			Random rand = new Random(GregorianCalendar.getInstance().getTimeInMillis());

			rand.nextBytes(bytes);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return bytes;
	}

	/**
	 * Agrega bytes aleatorios al principio de un array de bytes
	 * 
	 * @param datos Datos originales a los que se le agregan los bytes
	 * aleatorios
	 * 
	 * @return Byte array de datos junto a los byte array aleatorios
	 */
	private byte[] agregarBytesAleatoriosADatos(byte[] datos) {
		byte[] info = null;
		byte[] randomBytes = null;

		try {
			randomBytes = generarRandomByte(this.tamanioBytesAleatorios);

			info = new byte[(randomBytes.length + datos.length)];

			System.arraycopy(randomBytes, 0, info, 0, randomBytes.length);

			System.arraycopy(datos, 0, info, randomBytes.length, datos.length);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al agregar datos aleatorios a datos");
		}
		finally {
			datos = (byte[]) liberarObjeto();
			randomBytes = (byte[]) liberarObjeto();
		}

		return info;
	}

	/**
	 * Limpia bytes aleatorios ubicados al principio de un array de bytes
	 * 
	 * @param datos Datos originales que se le sacan los primeros bytes
	 * aleatorios
	 * 
	 * @return Byte array de datos con byte array aleatorios limpiados
	 */
	public byte[] limpiarBytesAleatoriosDeDatos(byte[] datos) {
		byte[] info = null;

		try {
			if (datos.length <= this.tamanioBytesAleatorios) {
				throw new Exception("Error en limpiar bytes aleatorios de datos");
			}

			int infoSize = datos.length - this.tamanioBytesAleatorios;

			info = new byte[infoSize];

			System.arraycopy(datos, this.tamanioBytesAleatorios, info, 0, infoSize);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante el limpieado de bytes aleatorios");
		}
		finally {
			datos = (byte[]) liberarObjeto();
		}

		return info;
	}

	/**
	 * Crea un objeto con valor null el cual será asignado a otro objeto que
	 * desamos eliminar
	 * 
	 * @return Objeto null
	 */
	public static Object liberarObjeto() {
		Object object = null;

		try {
			object = null;
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la liberacion del objeto");
		}

		return object;
	}

	/**
	 * Elimina de la memoria los objetos no usados
	 */
	public void liberarObjetosNoUsados() {
		try {
			System.gc();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error durante la liberacion no usados");
		}
	}

	/***************************************************************************
	 * 
	 * Separación entre clase con primitivas de encriptación y clase con métodos
	 * especificos para la aplicación.
	 * 
	 */

	/**
	 * Genera el par de claves para el usuario
	 * 
	 * @return Par de claves del usuario
	 * 
	 * @throws Exception
	 */
	public KeyPair generarClavesUsuario() {
		KeyPair key = null;
		try {
			key = (KeyPair) generarParClaves(this.algGeneracionParClaves);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Error durante la generación de claves de usuario");
		}
		return key;
	}

	/**
	 * Genera la Clave simétrica para encriptar/desencriptar los datos de las
	 * tablas con campos a encriptar
	 * 
	 * @return Clave simétrica
	 * 
	 * @throws Exception
	 */
	public SecretKey generarClaveDatos() {
		return generarClaveSimetrica(this.algGeneracionClaveSimetrica);
	}

	/**
	 * Certifica/firma la clave publica del usuario con la clave privada de la
	 * aplicación
	 * 
	 * @param appPrivateKey Clave privada de la aplicación. Se usa para
	 * certificar la clave publica del usuario.
	 * @param userPublicKey Clave pública del usuario. De la clave pública se
	 * extrae el encode, el cual será certificado
	 * 
	 * @return Byte encode de la clave publica certificada
	 * 
	 * @throws Exception
	 */
	// TODO: la appPrivateKey se puede obtener dentro del método
	public byte[] certificarClavePublica(PublicKey userPublicKey, PrivateKey appPrivateKey) {
		byte[] certifiedUserPublicKey = null;

		try {
			Signature signature = Signature.getInstance(this.algCertificacionClavePublica);

			signature.initSign(appPrivateKey);

			signature.update(userPublicKey.getEncoded());

			certifiedUserPublicKey = signature.sign();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Error durante la certificación de la clave pública");
		}
		finally {
			appPrivateKey = (PrivateKey) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return certifiedUserPublicKey;
	}

	/**
	 * Valida que la clave publica del usuario no haya sido alterada ni
	 * cambiada, mediante la clave pública certificada
	 * 
	 * @param usuario Objeto usuario, este contiene la clave pública y la clave
	 * pública certificada
	 * 
	 * @return Valor de verdad de la validación de la clave pública
	 * 
	 * @throws Exception
	 */
	// public boolean validarClavePublicaUsuario(Usuario usuario, PublicKey
	// appPublicKey) throws Exception {
	// boolean verificacion = false;
	//
	// try {
	// Signature signature =
	// Signature.getInstance(this.algCertificacionClavePublica);
	//
	// // TODO: usar clave de aplicacion de enserio
	// signature.initVerify(clavesAplicacion.getPublic());
	//
	// signature.update(usuario.getClavePublica());
	//
	// verificacion = signature.verify(usuario.getClavePublicaCertificada());
	// }
	// catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	//
	// return verificacion;
	// }
	public PrivateKey getPrivateKey(File file, char[] password) throws PasswordInvalidaException {
		try {
			PBEParameterSpec parameterSpec = obtenerPBEParameterSpec(file);
			byte[] encriptPrivateKey = leerClavePrivadaDesdeArchivo(file);
			byte[] rawPrivateKey = desencriptarClavePrivadaConContraseniaYParametros(encriptPrivateKey, password, parameterSpec);
			return crearClavePrivadaDesdeEncode(rawPrivateKey);
		}
		catch (PasswordInvalidaException pi) {
			throw pi;
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave privada");
		}
	}

	/**
	 * Valida que la contraseña del usuario sea valida
	 * 
	 * @param usuario Usuario dueño de la clave privada
	 * @param contrasenia Contrasenia del usuario
	 * 
	 * @return Clave Privada del Usuario en caso de que la password sea valida,
	 * sino devuelve null
	 * 
	 * @throws Exception
	 */
	public PrivateKey validarPasswordEncriptacion(String usuarioId, char[] contrasenia)
			throws PasswordInvalidaException {
		/*
		 * PrivateKey privateKey = null; byte[] rawPrivateKey = null;
		 * PBEParameterSpec parameterSpec = null;
		 * 
		 * File file = new File(this.rutaClavesPrivadasUsuarios + usuarioId +
		 * ".key");
		 * 
		 * parameterSpec = obtenerPBEParameterSpec(file);
		 * 
		 * byte[] encriptPrivateKey; try { encriptPrivateKey =
		 * leerClavePrivadaDesdeArchivo(file); rawPrivateKey =
		 * desencriptarClavePrivadaConContraseniaYParametros(encriptPrivateKey,
		 * contrasenia, parameterSpec); privateKey =
		 * crearClavePrivadaDesdeEncode(rawPrivateKey); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InvalidKeySpecException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } finally {
		 * contrasenia = (char[]) liberarObjeto(); rawPrivateKey = (byte[])
		 * liberarObjeto(); parameterSpec = (PBEParameterSpec) liberarObjeto(); //
		 * liberarObjetosNoUsados(); }
		 */
		File file = new File(this.rutaClavesPrivadasUsuarios + usuarioId + ".key");
		return this.getPrivateKey(file, contrasenia);
	}

	/**
	 * Genera el par de claves para la aplicación
	 * 
	 * @return Par de claves de la aplicación
	 * 
	 * @throws Exception
	 */

	// TODO: obtener el par de claves de la aplicacion (App Server)
	public KeyPair generarClavesAplicacion() {
		return (KeyPair) generarParClaves(this.algGeneracionParClaves);
	}

	/**
	 * Guarda la clave privada del usuario en un archivo txt, previa
	 * encriptación mediante la contrasenia del usuario
	 * 
	 * @param usuario
	 * @param userKeyPair
	 * @param contrasenia
	 * 
	 * @throws Exception
	 * 
	 * public void guardarClavePrivadaDeUsuario(Usuario usuario, PrivateKey
	 * privateKey, char[] contrasenia) throws Exception {
	 * this.guardarClavePrivadaDeUsuario(usuario.getNombre(),privateKey,contrasenia); }
	 */

	public void guardarClavePrivadaDeUsuario(com.fontar.data.impl.domain.ldap.Usuario usuario, PrivateKey privateKey,
			char[] contrasenia) {
		this.guardarClavePrivadaDeUsuario(usuario.getUserId(), privateKey, contrasenia);
	}

	public void guardarClavePrivadaDeUsuario(File file, PrivateKey privateKey, char[] password) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			PBEParameterSpec parameterSpec = generarPBEParameterSpec();
			byte[] encriptPrivateKey = encriptarClavePrivadaConContraseniaYParametros(privateKey.getEncoded(), password, parameterSpec);
			fos.write(parameterSpec.getSalt());
			fos.write(encriptPrivateKey);
			fos.close();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave privada");
		}

	}

	public void guardarClavePrivadaDeUsuario(String userId, PrivateKey privateKey, char[] contrasenia) {
		File file = new File(this.rutaClavesPrivadasUsuarios + userId + ".key");
		this.guardarClavePrivadaDeUsuario(file, privateKey, contrasenia);
		/*
		 * PBEParameterSpec parameterSpec = null;
		 * 
		 * try { File file = new File(this.rutaClavesPrivadasUsuarios + userId +
		 * ".key");
		 * 
		 * FileOutputStream fos = new FileOutputStream(file);
		 * 
		 * parameterSpec = generarPBEParameterSpec();
		 * 
		 * byte[] encriptPrivateKey =
		 * encriptarClavePrivadaConContraseniaYParametros(privateKey.getEncoded(),
		 * contrasenia, parameterSpec);
		 * 
		 * fos.write(parameterSpec.getSalt());
		 * 
		 * fos.write(encriptPrivateKey);
		 * 
		 * fos.close(); } catch (Exception e) {
		 * System.out.println(e.getMessage()); } finally { contrasenia =
		 * (char[]) liberarObjeto(); parameterSpec = (PBEParameterSpec)
		 * liberarObjeto(); privateKey = (PrivateKey) liberarObjeto(); //
		 * liberarObjetosNoUsados(); }
		 */
	}

	/**
	 * Guarda la clave privada de la aplicación en un archivo txt, previa
	 * encriptación mediante la contrasenia del usuario aplicación
	 * 
	 * @param usuario
	 * @param userKeyPair
	 * @param contrasenia
	 * 
	 * @throws Exception
	 */
	public void guardarClavePrivadaDeAplicacion(PrivateKey privateKey, char[] contrasenia) {
		PBEParameterSpec parameterSpec = null;

		try {
			File file = new File(this.rutaClavePrivadaAplicacion + "appPrivKey.key");

			FileOutputStream fos = new FileOutputStream(file);

			parameterSpec = generarPBEParameterSpec();

			byte[] encriptPrivateKey = encriptarClavePrivadaConContraseniaYParametros(privateKey.getEncoded(), contrasenia, parameterSpec);

			fos.write(parameterSpec.getSalt());

			fos.write(encriptPrivateKey);

			fos.close();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave privada de la aplicación");
		}
		finally {
			contrasenia = (char[]) liberarObjeto();
			parameterSpec = (PBEParameterSpec) liberarObjeto();
			privateKey = (PrivateKey) liberarObjeto();

			// liberarObjetosNoUsados();
		}
	}

	/**
	 * Guarda el encode de la clave publica de la aplicación en archivo
	 * 
	 * @param publicKey
	 * 
	 * @throws Exception
	 */
	public void guardarClavePublicaDeAplicacion(PublicKey publicKey) {
		byte[] encodePublicKey = null;

		try {
			File file = new File(this.rutaClavePublicaAplicacion + "appPubKey.key");

			FileOutputStream fos = new FileOutputStream(file);

			encodePublicKey = publicKey.getEncoded();

			fos.write(encodePublicKey);

			fos.close();
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave pública de la aplicación");
		}
		finally {
			encodePublicKey = (byte[]) liberarObjeto();

			// liberarObjetosNoUsados();
		}
	}

	/**
	 * Obtiene la clave privada del usuario desde archivo
	 * 
	 * @param usuario Usuario dueño de la clave privada
	 * @param contrasenia Contrasenia del usuario, utilizada para desblindar la
	 * clave privada que está encriptada
	 * 
	 * @return Clave Privada del Usuario
	 * 
	 * @throws Exception
	 */
	// public PrivateKey obtenerClavePrivadaDeUsuario(Usuario usuario, char[]
	// contrasenia)
	// throws PasswordInvalidaException, Exception {
	// PrivateKey privateKey = null;
	// byte[] rawPrivateKey = null;
	// PBEParameterSpec parameterSpec = null;
	//
	// try {
	// File file = new File(this.rutaClavesPrivadasUsuarios +
	// usuario.getNombre() + ".key");
	//
	// parameterSpec = obtenerPBEParameterSpec(file);
	//
	// byte[] encriptPrivateKey = leerClavePrivadaDesdeArchivo(file);
	//
	// rawPrivateKey =
	// desencriptarClavePrivadaConContraseniaYParametros(encriptPrivateKey,
	// contrasenia, parameterSpec);
	//
	// privateKey = crearClavePrivadaDesdeEncode(rawPrivateKey);
	// }
	// catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// finally {
	// contrasenia = (char[]) liberarObjeto();
	// rawPrivateKey = (byte[]) liberarObjeto();
	// parameterSpec = (PBEParameterSpec) liberarObjeto();
	//
	// // liberarObjetosNoUsados();
	// }
	//
	// return privateKey;
	// }
	/**
	 * Obtiene la clave privada de la aplicacion
	 * 
	 * @param contrasenia Contraseña de usuario aplicación
	 * 
	 * @return Clave privada de la aplicación
	 * 
	 * @throws PasswordInvalidaException
	 * @throws Exception
	 */
	public PrivateKey obtenerClavePrivadaAplicacion(char[] contrasenia) throws PasswordInvalidaException {
		PrivateKey privateKey = null;
		byte[] rawPrivateKey = null;
		PBEParameterSpec parameterSpec = null;

		try {
			File file = new File(this.rutaClavePrivadaAplicacion + "appPrivKey.key");

			parameterSpec = obtenerPBEParameterSpec(file);

			byte[] encriptPrivateKey = leerClavePrivadaDesdeArchivo(file);

			rawPrivateKey = desencriptarClavePrivadaConContraseniaYParametros(encriptPrivateKey, contrasenia, parameterSpec);

			privateKey = crearClavePrivadaDesdeEncode(rawPrivateKey);
		}
		catch (PasswordInvalidaException pi) {
			throw pi;
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave privada de la aplicación");
		}
		finally {
			contrasenia = (char[]) liberarObjeto();
			rawPrivateKey = (byte[]) liberarObjeto();
			parameterSpec = (PBEParameterSpec) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return privateKey;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws PasswordInvalidaException Documentar la excepcion!
	 * @throws Exception Documentar la excepcion!
	 */
	public PublicKey obtenerClavePublicaAplicacion() throws PasswordInvalidaException, Exception {
		PublicKey publicKey = null;
		byte[] encodePublicKey = null;

		try {
			File file = new File(this.rutaClavePublicaAplicacion + "appPubKey.key");

			encodePublicKey = leerClavePublicaDesdeArchivo(file);

			publicKey = crearClavePublicaDesdeEncode(encodePublicKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave pública de la aplicación");
		}
		finally {
			encodePublicKey = (byte[]) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return publicKey;
	}

	/***************************************************************************
	 * Obtiene una clava publica guardada en el file system
	 **************************************************************************/
	public PublicKey loadPublicKey(File file) {
		try {
			byte[] encodePublicKey = leerClavePublicaDesdeArchivo(file);
			return crearClavePublicaDesdeEncode(encodePublicKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al obtener la clave pública de la aplicación");
		}
	}

	/**
	 * Lee el Salt en formato byte desde un archivo
	 * 
	 * @param file Archivo que se desea leer
	 * 
	 * @return byte array de la salt
	 * 
	 * @throws IOException
	 */
	private byte[] leerSaltDesdeArchivo(File file) {
		byte[] salt = new byte[this.tamanioSalt];
		FileInputStream fis = null;
		byte[] bytes = null;

		try {
			fis = new FileInputStream(file);

			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, excede el tamaño máximo: "
						+ file.getName());
			}
			else if (length <= 0) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, no tiene datos: "
						+ file.getName());
			}

			bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;

			while ((offset < bytes.length) && ((numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0)) {
				offset += numRead;
			}

			System.arraycopy(bytes, 0, salt, 0, this.tamanioSalt);
		}
		catch (IOException ex) {
			logger.error(ex);
			throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios " + file.getName());
		}
		finally {
			bytes = (byte[]) liberarObjeto();
			try {
				if (fis != null)
					fis.close();
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		return salt;
	}

	/**
	 * Lee la clave privada en formato byte desde un archivo
	 * 
	 * @param file Archivo que se desea leer
	 * 
	 * @return byte array de la clave privada
	 * 
	 * @throws IOException
	 */
	private byte[] leerClavePrivadaDesdeArchivo(File file) {
		byte[] privateKey = null;
		FileInputStream fis = null;
		byte[] bytes = null;

		try {
			fis = new FileInputStream(file);

			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, excede el tamaño máximo: "
						+ file.getName());
			}
			else if (length <= 0) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, no tiene datos: "
						+ file.getName());
			}

			bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;

			while ((offset < bytes.length) && ((numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0)) {
				offset += numRead;
			}

			int keySize = bytes.length - this.tamanioSalt;
			privateKey = new byte[keySize];
			System.arraycopy(bytes, this.tamanioSalt, privateKey, 0, keySize);
		}
		catch (IOException ex) {
			logger.error(ex);
			throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios " + file.getName());
		}
		finally {
			bytes = (byte[]) liberarObjeto();
			try {
				if (fis != null)
					fis.close();
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		return privateKey;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param file Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws IOException Documentar la excepcion!
	 */
	private byte[] leerClavePublicaDesdeArchivo(File file) {
		FileInputStream fis = null;
		byte[] bytes = null;

		try {
			fis = new FileInputStream(file);

			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, excede el tamaño máximo: "
						+ file.getName());
			}
			else if (length <= 0) {
				throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios, no tiene datos: "
						+ file.getName());
			}

			bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;

			while ((offset < bytes.length) && ((numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0)) {
				offset += numRead;
			}
		}
		catch (IOException ex) {
			logger.error(ex);
			throw new BusinessException("No se pudo leer el archivo de claves privadas de usuarios " + file.getName());
		}
		finally {
		//	bytes = (byte[]) liberarObjeto();
			try {
				if (fis != null)
					fis.close();
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		return bytes;
	}

	/**
	 * Obtiene el PBEParameterSpec perteneciente al usuario
	 * 
	 * @param usuario Usuario del cual se quiere obtener la especificación del
	 * parametro PBE
	 * @param file Archivo que se desea leer
	 * 
	 * @return
	 */
	private PBEParameterSpec obtenerPBEParameterSpec(File file) {
		PBEParameterSpec pbeParamSpec = null;
		byte[] salt = null;

		salt = leerSaltDesdeArchivo(file);
		int count = this.iteraciones;
		pbeParamSpec = new PBEParameterSpec(salt, count);
		salt = (byte[]) liberarObjeto();
		/*
		 * 
		 * try { salt = leerSaltDesdeArchivo(file);
		 * 
		 * int count = this.iteraciones;
		 * 
		 * pbeParamSpec = new PBEParameterSpec(salt, count); } catch(Exception
		 * ex){ throw new Busex; } finally { salt = (byte[]) liberarObjeto(); }
		 */

		return pbeParamSpec;
	}

	/**
	 * Encripta el encode de una clave privada con una contraseña y parametros
	 * recibidos
	 * 
	 * @param privateKey Encode de la clave privada a encriptar
	 * @param contrasenia Contraseña usada para encriptar la clave privada
	 * @param parameterSpec Parametros utilizados para encriptar la clave
	 * privada
	 * 
	 * @return Clave privada encriptada
	 */
	public byte[] encriptarClavePrivadaConContraseniaYParametros(byte[] privateKey, char[] contrasenia,
			AlgorithmParameterSpec parameterSpec) {
		byte[] encriptUserPrivateKey = null;
		SecretKey pbeKey = null;
		PBEKeySpec pbeKeySpec = null;

		try {
			pbeKeySpec = new PBEKeySpec(contrasenia);

			pbeKey = generarClaveSimetricaConEspecDeClave(pbeKeySpec);

			Cipher pbeCipher = Cipher.getInstance(this.algEncriptacionContrasenia);

			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, parameterSpec);

			encriptUserPrivateKey = pbeCipher.doFinal(privateKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error encriptarClavePrivadaConContraseniaYParametros");
		}
		finally {
			contrasenia = (char[]) liberarObjeto();
			pbeKey = (SecretKey) liberarObjeto();
			pbeKeySpec = (PBEKeySpec) liberarObjeto();
			privateKey = (byte[]) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return encriptUserPrivateKey;
	}

	/**
	 * Desencripta una clave privada encriptada con una contraseña y parametros
	 * recibidos
	 * 
	 * @param encriptPrivateKey Encode de la clave privada a encriptar
	 * @param contrasenia Contraseña usada para desencriptar la clave privada
	 * @param parameterSpec Parametros utilizados para desencriptar la clave
	 * privada
	 * 
	 * @return Clave privada desencriptada
	 * 
	 * @throws PasswordInvalidaException
	 */
	public byte[] desencriptarClavePrivadaConContraseniaYParametros(byte[] encriptPrivateKey, char[] contrasenia,
			AlgorithmParameterSpec parameterSpec) throws PasswordInvalidaException {
		byte[] privateKey = null;
		PBEKeySpec pbeKeySpec = null;
		SecretKey pbeKey = null;

		try {
			pbeKeySpec = new PBEKeySpec(contrasenia);

			pbeKey = generarClaveSimetricaConEspecDeClave(pbeKeySpec);

			Cipher pbeCipher = Cipher.getInstance(this.algEncriptacionContrasenia);

			pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, parameterSpec);

			privateKey = pbeCipher.doFinal(encriptPrivateKey);
		}
		catch (BadPaddingException bpe) {
			throw new PasswordInvalidaException("La contraseña de encriptación es inválida");
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error desencriptarClavePrivadaConContraseniaYParametros");
		}
		finally {
			contrasenia = (char[]) liberarObjeto();
			encriptPrivateKey = (byte[]) liberarObjeto();
			pbeKey = (SecretKey) liberarObjeto();
			pbeKeySpec = (PBEKeySpec) liberarObjeto();
			parameterSpec = (AlgorithmParameterSpec) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return privateKey;
	}

	/**
	 * Verifica que la clave publica del usuario no haya sido alterada y
	 * encripta la clave simétrica con la clave pública del usuario
	 * 
	 * @param secretKey Encode de la clave simetrica a encriptar
	 * @param usuario Usuario que contiene la clave publica usada para la
	 * encriptación
	 * 
	 * @return byte array de la clave simetrica encriptada
	 * 
	 * @throws Exception
	 */
	// public byte[] encriptarClaveSimetricaConClavePublicaDeUsuario(byte[]
	// secretKey, Usuario usuario) throws Exception {
	// byte[] encriptSerializeSecretKey = null;
	// PublicKey userPublicKey = null;
	//
	// try {
	// if (!validarClavePublicaUsuario(usuario, null)) {
	// throw new Exception("La clave pública del usuario ha sido modificada: " +
	// usuario.getNombre());
	// }
	//
	// userPublicKey = crearClavePublicaDesdeEncode(usuario.getClavePublica());
	//
	// encriptSerializeSecretKey =
	// encriptarClaveSimetricaConClavePublica(secretKey, userPublicKey);
	// }
	// catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// finally {
	// secretKey = (byte[]) liberarObjeto();
	//
	// // liberarObjetosNoUsados();
	// }
	//
	// return encriptSerializeSecretKey;
	// }
	/**
	 * Encripta la clave simetrica con una clave publica
	 * 
	 * @param secretKey Encode de la clave simetrica a encriptar
	 * @param publicKey Clave pública usada para encriptar la clave simétrica
	 * 
	 * @return byte array de la clave simetrica encriptada
	 * 
	 * @throws Exception
	 */
	public byte[] encriptarClaveSimetricaConClavePublica(byte[] secretKey, PublicKey publicKey) {
		byte[] encriptSerializeSecretKey = null;
		Cipher rsaCipher = null;

		try {
			rsaCipher = Cipher.getInstance(this.algEncriptacionClaveSimetrica);

			rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

			encriptSerializeSecretKey = rsaCipher.doFinal(secretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error al encriptar clave simétrica con clave pública");
		}
		finally {
			secretKey = (byte[]) liberarObjeto();
			rsaCipher = (Cipher) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return encriptSerializeSecretKey;
	}

	/**
	 * Desencripta el encode de una clave simetrica encriptado y crea el objeto
	 * clave simetrica
	 * 
	 * @param encriptSecretKey Encode encriptado de la clave simetrica
	 * @param privateKey Clave privada para desencriptar el encode
	 * 
	 * @return Clave simetrica desencriptada y creada
	 * 
	 * @throws Exception
	 */
	public SecretKey desencriptarCrearClaveSimetricaConClavePrivada(byte[] encriptSecretKey, PrivateKey privateKey) {
		SecretKey secretKey = null;
		byte[] encodeSecretKey = null;

		try {
			encodeSecretKey = desencriptarClaveSimetricaConClavePrivada(encriptSecretKey, privateKey);

			secretKey = crearClaveSimetricaDesdeEncode(encodeSecretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error desencriptarCrearClaveSimetricaConClavePrivada");
		}
		finally {
			encriptSecretKey = (byte[]) liberarObjeto();
			encodeSecretKey = (byte[]) liberarObjeto();
			privateKey = (PrivateKey) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return secretKey;
	}

	/**
	 * Desencripta el encode de una clave simetrica encriptado y crea el objeto
	 * clave simetrica con la clave privada de la aplicacion
	 * 
	 * @param encriptSecretKey Encode encriptado de la clave simetrica
	 * @param privateKey Clave privada de la aplicación para desencriptar el
	 * encode
	 * 
	 * @return Clave simetrica desencriptada y creada
	 * 
	 * @throws Exception
	 */
	public SecretKey desencriptarCrearClaveSimetricaConClavePrivadaAplicacion(byte[] encriptSecretKey,
			PrivateKey appPrivateKey) {
		SecretKey secretKey = null;
		byte[] encodeSecretKey = null;

		try {
			// TODO: usar clave de aplicacion de enserio
			encodeSecretKey = desencriptarClaveSimetricaConClavePrivada(encriptSecretKey, appPrivateKey);

			secretKey = crearClaveSimetricaDesdeEncode(encodeSecretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error desencriptarCrearClaveSimetricaConClavePrivadaAplicacion");
		}
		finally {
			encriptSecretKey = (byte[]) liberarObjeto();
			encodeSecretKey = (byte[]) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return secretKey;
	}

	/**
	 * Desencripta la clave simetrica de un campo de una tabala con la clave
	 * publica del usuario
	 * 
	 * @param userPrivateKey
	 * @param encriptSecretKey
	 * 
	 * @return byte array de la clave simetrica desencriptada con la clave
	 * privada del usuario
	 * 
	 * @throws Exception
	 */
	public byte[] desencriptarClaveSimetricaConClavePrivada(byte[] encriptSecretKey, PrivateKey privateKey) {
		byte[] encodeSecretKey = null;
		Cipher rsaCipher = null;

		try {
			rsaCipher = Cipher.getInstance(this.algEncriptacionClaveSimetrica);

			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

			encodeSecretKey = rsaCipher.doFinal(encriptSecretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error desencriptarClaveSimetricaConClavePrivada");
		}
		finally {
			encriptSecretKey = (byte[]) liberarObjeto();
			privateKey = (PrivateKey) liberarObjeto();
			rsaCipher = (Cipher) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return encodeSecretKey;
	}

	/**
	 * Desencripta la clave simetrica de un campo de una tabala con la clave
	 * publica del usuario
	 * 
	 * @param userPrivateKey
	 * @param encriptSecretKey
	 * 
	 * @return byte array de la clave simetrica desencriptada con la clave
	 * privada del usuario
	 * 
	 * @throws Exception
	 */
	public byte[] desencriptarClaveSimetricaConClavePrivadaAplicacion(byte[] encriptSecretKey, PrivateKey appPrivateKey) {
		byte[] encodeSecretKey = null;
		Cipher rsaCipher = null;

		try {
			rsaCipher = Cipher.getInstance(this.algEncriptacionClaveSimetrica);

			// TODO: usar clave de aplicacion de enserio
			rsaCipher.init(Cipher.DECRYPT_MODE, appPrivateKey);

			encodeSecretKey = rsaCipher.doFinal(encriptSecretKey);
		}
		catch (Exception e) {
			logger.error(e);
			throw new BusinessException("Error desencriptarClaveSimetricaConClavePrivadaAplicacion");
		}
		finally {
			encriptSecretKey = (byte[]) liberarObjeto();
			rsaCipher = (Cipher) liberarObjeto();

			// liberarObjetosNoUsados();
		}

		return encodeSecretKey;
	}

	/**
	 * Obtiene la contraseña de datos sensibles del ususario, desde la sesión
	 * 
	 * @return
	 */
	public char[] obtenerContraseniaUsuario() {
		// TODO: obtener la contraseña del usuario desde la session, debe estar
		// en memoria encriptada
		return "contrasenia".toCharArray();
	}

	/**
	 * Obtiene la contraseña del usuario aplicación
	 * 
	 * @return
	 */
	public char[] obtenerContraseniaAplicacion() {
		// TODO: obtener la contraseña de la aplicación desde la session, debe
		// estar en memoria encriptada
		return "appcontrasenia".toCharArray();
	}

	/**
	 * Genera la clave simétrica para datos sensibles de una tabla y la encripta
	 * con la clave publica de la aplicacion. Cada tabla de claves tiene como
	 * primer usuario a la aplicación con esta clave almacenada.
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public byte[] generarClaveInicial(PublicKey appPublicKey) {
		SecretKey secretKey = null;
		byte[] key = null;

		try {
			secretKey = generarClaveDatos();
			// TODO: usar clave de aplicacion de enserio
			key = encriptarClaveSimetricaConClavePublica(secretKey.getEncoded(), appPublicKey);
		}
		finally {
			secretKey = (SecretKey) liberarObjeto();
			// liberarObjetosNoUsados();
		}

		return key;
	}

	/**
	 * Genera la clave simétrica para datos sensibles de una tabla y la encripta
	 * con la clave publica de la aplicacion. Cada tabla de claves tiene como
	 * primer usuario a la aplicación con esta clave almacenada.
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public byte[] newSecretKey(PublicKey publicKey) throws Exception {
		SecretKey secretKey = generarClaveDatos();
		return encriptarClaveSimetricaConClavePublica(secretKey.getEncoded(), publicKey);
	}

	public PrivateKey validarPasswordEncriptacion(com.fontar.data.impl.domain.ldap.Usuario current, char[] password)
			throws PasswordInvalidaException {
		return this.validarPasswordEncriptacion(current.getUserId(), password);
	}

	public PrivateKey validarPasswordEncriptacion(char[] password) throws PasswordInvalidaException {
		return this.validarPasswordEncriptacion(this.getUserId(), password);
	}

	protected String getUserId() {
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}