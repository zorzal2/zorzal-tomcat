package com.fontar.proyecto.presupuesto.message;

import java.util.ResourceBundle;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.pragma.util.Messages;

public class PresupuestoMessages {
	private static final String BUNDLE_NAME = "resources.PresupuestoMessages";

	private static Messages messages = new Messages(ResourceBundle.getBundle(BUNDLE_NAME));

	public static class global {
		public static String flujoNoCoincideConDetalleDelRubro(RubroBean rubro) {
			return messages.getString("presupuesto.validation.error.flujoNoCoincideConDetalleDelRubro", rubro.getNombre());
		}
	}
	
	public static class flujo {
		public static String faltanFlujos() {
			return messages.getString("presupuesto.flujo.validation.error.faltanFlujos");
		}
		public static String periodosDiferentes() {
			return messages.getString("presupuesto.flujo.validation.error.periodosDiferentes");
		}
		public static String totalDifiereDePartes(String periodo) {
			return messages.getString("presupuesto.flujo.validation.error.totalDifiereDePartes", periodo);
		}
		public static String valoresNegativos(RubroBean rubro) {
			return messages.getString("presupuesto.flujo.validation.error.valoresNegativos", rubro.getNombre());
		}
	}
	public static class plan {
		public static String inicioDeEtapaInvalido(EtapaBean etapa) {
			return messages.getString("presupuesto.plan.format.error.inicioDeEtapaInvalido", etapa.getNombre());
		}
		public static String finDeEtapaInvalido(EtapaBean etapa) {
			return messages.getString("presupuesto.plan.format.error.finDeEtapaInvalido", etapa.getNombre());
		}
		public static String periodoDeEtapaInvalido(EtapaBean etapa) {
			return messages.getString("presupuesto.plan.format.error.periodoDeEtapaInvalido", etapa.getNombre());
		}
		public static String inicioDeActividadInvalido(ActividadBean actividad) {
			return messages.getString("presupuesto.plan.format.error.inicioDeActividadInvalido", actividad.getNombre(), actividad.getEtapa().getNombre());
		}
		public static String finDeActividadInvalido(ActividadBean actividad) {
			return messages.getString("presupuesto.plan.format.error.finDeActividadInvalido", actividad.getNombre(), actividad.getEtapa().getNombre());
		}
		public static String etapaInvalida(String nombreEtapa, ActividadBean actividad) {
			return messages.getString("presupuesto.plan.format.error.etapaInvalida", nombreEtapa, actividad.getNombre());
		}
		public static String periodoDeActividadInvalido(ActividadBean actividad) {
			return messages.getString("presupuesto.plan.format.error.periodoDeActividadInvalido", actividad.getNombre(), actividad.getEtapa().getNombre());
		}
		public static String periodoDeActividadNoCoincide(ActividadBean actividad) {
			return messages.getString("presupuesto.plan.format.error.periodoDeActividadNoCoincide", actividad.getNombre(), actividad.getEtapa().getNombre());
		}
		public static String planInvalido() {
			return messages.getString("presupuesto.plan.format.error.planInvalido");
		}
	}
	public static class presupuestoRubro {
		public static String totalDifiereDePartes(PresupuestoItemBean item, RubroBean rubro) {
			return messages.getString("presupuesto.presupuestoRubro.validation.error.totalDifiereDePartes", item.getNombre(), rubro.getNombre());
		}
		public static String totalDifiereDePartes() {
			return messages.getString("presupuesto.presupuestoRubro.validation.error.totalDifiereDePartesNoData");
		}
		public static String valoresNegativos(PresupuestoItemBean item, RubroBean rubro) {
			return messages.getString("presupuesto.presupuestoRubro.validation.error.valoresNegativos", item.getNombre(), rubro.getNombre());
		}
		public static String valoresNegativos() {
			return messages.getString("presupuesto.presupuestoRubro.validation.error.valoresNegativosNoData");
		}
		public static String formatoNoValido() {
			return messages.getString("presupuesto.presupuestoRubro.format.error.formatoNoValido");
		}
		public static String faltanCampos(RubroBean rubro) {
			return messages.getString("presupuesto.presupuestoRubro.validation.error.faltanCampos", rubro.getNombre());
		}
		public static class consultoria {
			public static String totalIncoherente(PresupuestoItemBean item, RubroBean rubro) {
				return messages.getString("presupuesto.presupuestoRubro.consultor.validation.error.totalDifiereDeCostos", item.getNombre(), rubro.getNombre());
			}
		}
		public static class insumo {
			public static String totalIncoherente(PresupuestoItemBean item, RubroBean rubro) {
				return messages.getString("presupuesto.presupuestoRubro.insumo.validation.error.totalDifiereDeCostos", item.getNombre(), rubro.getNombre());
			}
		}
		public static class rh {
			public static String totalIncoherente(PresupuestoItemBean item, RubroBean rubro) {
				if(item.getNombre()==null && item.getNombre().equals(""))
					return messages.getString("presupuesto.presupuestoRubro.rh.validation.error.totalDifiereDeCostos_NoItemName", rubro.getNombre());
				else
					return messages.getString("presupuesto.presupuestoRubro.rh.validation.error.totalDifiereDeCostos", item.getNombre(), rubro.getNombre());
				
			}
		}
		public static class directorExperto {
			public static String datosIncompletos(PresupuestoItemBean item) {
				return messages.getString("presupuesto.presupuestoRubro.directorExperto.validation.error.datosIncompletos");				
			}
		}
	}
}
