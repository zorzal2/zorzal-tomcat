package com.fontar.proyecto.presupuesto.excel.parser.labels;

import com.pragma.excel.parser.util.Label;

public class DefaultLabels {
	public static DefaultLabels instance = new DefaultLabels();
	
	public DefaultLabels() {
		itemBienes = new ItemBienes();
		itemInsumos = new ItemInsumos();
		itemRecursosHumanosPropios = new ItemRecursosHumanosPropios();
		itemRecursosHumanosAdicionales = new ItemRecursosHumanosAdicionales();
		itemConsultores = new ItemConsultores();
		flujo = new Flujo();
		plan = new Plan();
	}
	
	public static class ItemBienes {
		public Label DESCRIPCION = new Label("descripci?n");
		public Label COSTO_TOTAL = new Label("costo total");
		public Label PARTE = new Label("a financiar por fontar*");
		public Label CONTRAPARTE = new Label("a financiar por la contraparte*");
	}
	public ItemBienes itemBienes;
	
	public static class ItemInsumos {
		public Label DETALLE = new Label("detalle*");
		public Label UNIDAD_DE_MEDIDA = new Label("Unidad de medida*");
		public Label CANTIDAD = new Label("cant.*", "cantidad*");
		public Label COSTO_UNITARIO = new Label("costo unitario*");
		public Label COSTO_TOTAL = new Label("costo total");
		public Label PARTE = new Label("a financiar por fontar*");
		public Label CONTRAPARTE = new Label("a financiar por la contraparte*");
	}
	public ItemInsumos itemInsumos;
	
	public static class ItemRecursosHumanosPropios {
		public Label NOMBRE = new Label("nombre", "nombre y apellido*");
		public Label PROFESION = new Label("profesi?n u oficio*");
		public Label ID_TRIBUTARIA = new Label("CUIL / CUIT*", "CUIT / CUIL*");
		public Label FUNCION = new Label("FUNCI?N EN EL PROYECTO*");
		public Label COSTO_MENSUAL = new Label("SUELDO MENSUAL ASIGNADO*");
		public Label DEDICACION = new Label("% DEDICACI?N AL PROYECTO*");
		public Label PARTICIPACION = new Label("MESES DE PARTICIPACI?N EN EL PROY*");
		public Label COSTO_TOTAL = new Label("costo total");
	}
	public ItemRecursosHumanosPropios itemRecursosHumanosPropios;
	
	public static class ItemRecursosHumanosAdicionales {
		public Label PROFESION = new Label("profesi?n u oficio*");
		public Label FUNCION = new Label("FUNCI?N EN EL PROYECTO*");
		public Label COSTO_MENSUAL = new Label("COSTO TOTAL MENSUAL*", "SUELDO MENSUAL ASIGNADO*");
		public Label ID_TRIBUTARIA = new Label("CUIL / CUIT*", "CUIT / CUIL*");
		public Label PARTICIPACION = new Label("MESES DE PARTICIPACI?N EN EL PROY*");
		public Label DEDICACION = new Label("% DEDICACI?N AL PROYECTO*");
		public Label COSTO_TOTAL = new Label("costo total");
		public Label PARTE = new Label("a financiar por fontar*");
		public Label CONTRAPARTE = new Label("a financiar por la contraparte*");
	}
	public ItemRecursosHumanosAdicionales itemRecursosHumanosAdicionales;

	public static class ItemConsultores {
		public Label DESCRIPCION = new Label("descripci?n");
		public Label COSTO_MENSUAL = new Label("COSTO MENSUAL*");
		public Label PARTICIPACION = new Label("MESES DE PARTICIPACI?N EN EL PROY*");
		public Label COSTO_TOTAL = new Label("costo total");
		public Label PARTE = new Label("a financiar por fontar*");
		public Label CONTRAPARTE = new Label("a financiar por la contraparte*");
	}
	public ItemConsultores itemConsultores;
	
	public static class Flujo {
		//Abstract
		public Label TOTAL = null;
		public Label PARTE = null;
		public Label CONTRAPARTE = null;

		public Label SUBTOTALES = new Label("subtotal*");
	}
	public Flujo flujo;
	
	public static class Plan {
		//Abstract
		public Label ETAPAS = null;
		public Label ACTIVIDADES = null;
		
		public Label COD_ETAPA = new Label("codigo etapa", "cod. etapa", "codigo de etapa");
		public Label DESCRIPCION = new Label("descripci?n");
		public Label INICIO = new Label("mes de inicio", "fecha de inicio");
		public Label FIN = new Label("mes de finalizaci?n", "fecha de finalizaci?n");
		public Label COD_ACTIVIDAD = new Label("codigo actividad", "cod. actividad", "codigo de actividad");
	}
	public Plan plan;
	
	public Label SUBTOTAL = new Label("subtotal", "total", "total:", "subtotal:");
	
}
