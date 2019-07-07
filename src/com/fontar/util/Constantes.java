package com.fontar.util;

public class Constantes {

	// TODO: pasar constantes a mayúsculas, usar interfaces y dejar una entidad
	// Constants
	/**
	 * Algoritmos de generacion de claves
	 */
	public static final String algGeneracionClaveSimetrica = "DES";

	public static final String algGeneracionParClaves = "RSA";

	public static final String algGeneracionClaveContrasenia = "PBEWithMD5AndDES";

	/**
	 * Algoritmos de certificacion/firmas digitales
	 */
	public static final String algCertificacionClavePublica = "SHA1withRSA";

	/**
	 * Algoritmos de encriptación
	 */
	public static final String algEncriptacionContrasenia = "PBEWithMD5AndDES";

	public static final String algEncriptacionClaveSimetrica = "RSA";

	public static final String algEncriptacionDatos = "DES";

	/**
	 * Rutas de archivos de claves privadas de usuarios
	 */
	public static final String rutaClavesPrivadasUsuarios = "c:\\fontar\\claves\\";

	public static final String rutaClavePublicaAplicacion = "c:\\fontar\\claves\\";

	public static final String rutaClavePrivadaAplicacion = "c:\\fontar\\claves\\";

	/**
	 * Constantes varias
	 */
	public static final int tamanioSalt = 8;

	public static final int tamanioBytesAleatorios = 16;

	public static final int tamanioParClaves = 1024;

	public static final int tamanioClaveSimetrica = 56;

	public static final int iteraciones = 1000;

	/**
	 * Constantes variables de Session
	 */
	public static final String criptoPasswordKey = "CRIPTO_PASS";

	public static final String criptoDataMensaje = "[cripto]";

}
