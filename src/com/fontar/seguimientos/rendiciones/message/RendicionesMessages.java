package com.fontar.seguimientos.rendiciones.message;

import java.util.ResourceBundle;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.pragma.util.Messages;
import com.pragma.util.StringUtil;

public class RendicionesMessages {
	private static final String BUNDLE_NAME = "resources.RendicionesMessages";

	private static Messages messages = new Messages(ResourceBundle.getBundle(BUNDLE_NAME));

	public static String noSeEncuentranRendiciones() {
		return messages.getString("rendiciones.validation.error.noSeEncuentranRendiciones");
	}
	public static String rendicionesInvalidas(String sheetName) {
		return messages.getString("rendiciones.validation.error.rendicionesInvalidas", sheetName);
	}
	public static String rubroNoValidoOVacio(String rubroName) {
		if(StringUtil.isEmpty(rubroName))
			return messages.getString("rendiciones.validation.error.rubroVacio");
		else
			return messages.getString("rendiciones.validation.error.rubroNoValido", rubroName);
	}
	public static String campoNumericoPositivoNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoNumericoPositivoNoValido", userFriendlyLabel(campo));
	}
	public static String campoNumericoNoNegativoNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoNumericoNoNegativoNoValido", userFriendlyLabel(campo));
	}
	public static String campoNumericoNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoNumericoNoValido", userFriendlyLabel(campo));
	}
	public static String campoEnteroPositivoNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoEnteroPositivoNoValido", userFriendlyLabel(campo));
	}
	public static String campoEnteroNoNegativoNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoEnteroNoNegativoNoValido", userFriendlyLabel(campo));
	}
	public static String campoPorcentualNoValido(String campo) {
		return messages.getString("rendiciones.validation.error.campoPorcentualNoValido", userFriendlyLabel(campo));
	}
	public static String fechaNoValida(RendicionCuentasBean bean, String nombreCampo) {
		return messages.getString("rendiciones.validation.error.fechaNoValida", identificadorDeItem(bean), userFriendlyLabel(nombreCampo));
	}
	public static String campoNoValido(RendicionCuentasBean bean, String nombreCampo) {
		return messages.getString("rendiciones.validation.error.valorNoValido", identificadorDeItem(bean), userFriendlyLabel(nombreCampo));
	}
	public static String personaNoIdentificable(RendicionCuentasBean bean) {
		return messages.getString("rendiciones.validation.error.personaNoIdentificable", identificadorDeItem(bean));
	}
	public static String costoNoImputable(RendicionCuentasBean bean) {
		return messages.getString("rendiciones.validation.error.costoNoImputable", identificadorDeItem(bean));
	}
	public static String textoDemasiadoLargo(RendicionCuentasBean bean, String nombreCampo, Integer len) {
		return messages.getString("rendiciones.validation.error.textoDemasiadoLargo", identificadorDeItem(bean), userFriendlyLabel(nombreCampo), len.toString());
	}
	public static String faltaCampo(RendicionCuentasBean bean, String nombreCampo) {
		return messages.getString("rendiciones.validation.error.faltaCampo", identificadorDeItem(bean), userFriendlyLabel(nombreCampo));
	}
	
	private static String identificadorDeItem(RendicionCuentasBean bean) {
		if(bean.getNumeroFactura()!=null) {
			return messages.getString("rendiciones.validation.partial.itemConFactura", bean.getNumeroFactura());
		} else {
			if(bean.getNumeroRecibo()!=null) {
				return messages.getString("rendiciones.validation.partial.itemConRecibo", bean.getNumeroRecibo());
			} else {
				return messages.getString("rendiciones.validation.partial.itemDeRubro", bean.getRubro().getNombre());
			}
		}
	}
	private static String userFriendlyLabel(String nombreCampo) {
		return messages.getString(nombreCampo);
	}
}
