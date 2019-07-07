package com.fontar.proyecto.pac.message;

import java.util.ResourceBundle;

import com.pragma.util.Messages;

public class PacMessages {
	private static final String BUNDLE_NAME = "resources.PacMessages";

	private static Messages messages = new Messages(ResourceBundle.getBundle(BUNDLE_NAME));

	public static String noSeEncuentraPac() {
		return messages.getString("pac.validation.error.noSeEncuentraPac");
	}
	public static String pacInvalido(String sheetName) {
		return messages.getString("pac.validation.error.pacInvalido", sheetName);
	}
	public static String itemsRepetidos() {
		return messages.getString("pac.validation.error.itemsRepetidos");
	}
	public static String itemNoNumerco(String descripcion) {
		return messages.getString("pac.validation.error.itemNoNumerico", descripcion);
	}
	public static String etapaInvalida(String itemId) {
		return messages.getString("pac.validation.error.etapaInvalida", itemId);
	}
	public static String rubroInvalido(String itemId) {
		return messages.getString("pac.validation.error.rubroInvalido", itemId);
	}
	public static String faltanCampos(String itemId) {
		return messages.getString("pac.validation.error.faltanCampos", itemId);
	}
	public static String tipoDeAdquisicionNoValido(String itemId) {
		return messages.getString("pac.validation.error.tipoDeAdquisicionNoValido", itemId);
	}
	public static String montoEstimadoNoNumerico(String itemId) {
		return messages.getString("pac.validation.error.montoEstimadoNoNumerico", itemId);
	}
	public static String montoEstimadoNoValido(String montoEsperado) {
		return messages.getString("pac.validation.error.montoEstimadoNoValido", montoEsperado);
	}
}
