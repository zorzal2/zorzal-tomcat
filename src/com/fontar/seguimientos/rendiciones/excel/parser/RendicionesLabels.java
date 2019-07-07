package com.fontar.seguimientos.rendiciones.excel.parser;

import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.Label;

public class RendicionesLabels {
	public static Label SHEET_NAME = new Label("Datos");

	//Campos
	public static Label RUBRO = new Label("general.rubro");
	public static Label NRO_FACTURA = new Label("general.nroFactura");
	public static Label NRO_RECIBO = new Label("general.nroRecibo");
	public static Label FECHA = new Label("general.fecha");
	public static Label MONTO_TOTAL = new Label("general.total");
	public static Label MONTO_FONTAR = new Label("general.montoFontar");
	public static Label MONTO_CONTRAPARTE = new Label("general.montoContraparte");
	public static Label DESCRIPCION_GASTO = new Label("general.descripcion");
	public static Label NOMBRE = new Label("persona.nombre");
	public static Label SEXO = new Label("persona.sexo");
	public static Label CUIT_CUIL = new Label("persona.cuit");
	public static Label PROFESION = new Label("persona.profesion");
	public static Label FUNCION = new Label("persona.funcion");
	public static Label PARTICIPACION = new Label("persona.participacion");
	public static Label DEDICACION = new Label("persona.dedicacion");
	public static Label COSTO_MENSUAL = new Label("persona.sueldo");
	public static Label DESCRIPCION_PROVEEDOR = new Label("proveedor.nombre");
	public static Label TIENE_CERTIFICADO = new Label("proveedor.tieneCertificado");
	public static Label PAIS_DE_CERTIFICADO = new Label("proveedor.paisCertificado");
	public static Label VERSION = new Label("version");
	
	public static Field[] CamposCF = {
		new Field(RUBRO, true),
		new Field(NRO_FACTURA, true),
		new Field(NRO_RECIBO, false),
		new Field(FECHA, true),
		new Field(MONTO_TOTAL, false),
		new Field(DESCRIPCION_GASTO, false),
		new Field(NOMBRE, false),
		new Field(SEXO, false),
		new Field(CUIT_CUIL, false),
		new Field(PROFESION, false),
		new Field(FUNCION, false),
		new Field(PARTICIPACION, false),
		new Field(DEDICACION, false),
		new Field(COSTO_MENSUAL, false),
		new Field(DESCRIPCION_PROVEEDOR, false),
		new Field(TIENE_CERTIFICADO, false),
		new Field(PAIS_DE_CERTIFICADO, false),
		new Field(PAIS_DE_CERTIFICADO, false),
		new Field(VERSION, false)
	}; 
	
	public static Field[] CamposANR = {
		new Field(RUBRO, true),
		new Field(NRO_FACTURA, true),
		new Field(NRO_RECIBO, false),
		new Field(FECHA, true),
		new Field(MONTO_FONTAR, false),
		new Field(MONTO_CONTRAPARTE, false),
		new Field(DESCRIPCION_GASTO, false),
		new Field(NOMBRE, false),
		new Field(SEXO, false),
		new Field(CUIT_CUIL, false),
		new Field(PROFESION, false),
		new Field(FUNCION, false),
		new Field(PARTICIPACION, false),
		new Field(DEDICACION, false),
		new Field(COSTO_MENSUAL, false),
		new Field(DESCRIPCION_PROVEEDOR, false),
		new Field(TIENE_CERTIFICADO, false),
		new Field(PAIS_DE_CERTIFICADO, false),
		new Field(VERSION, false)
	}; 
	
	//Rubros
	public static Label bienes_infraestructura = new Label("Infraestructura");
	public static Label bienes_maquinarias = new Label("Maquinaria, Equipos e Instrumentos");
	public static Label bienes_otros = new Label("Otros Bienes de Capital");
	public static Label consejerosTecnologicos = new Label("Consejeto Tecnológico");
	public static Label cys_canonInstitucional = new Label("Canon Institucional");
	public static Label cys_general = new Label("Consultoría y Servicios General");
	public static Label directorExperto = new Label("Honorarios del Director Experto");
	public static Label insumos = new Label("Materiales e Insumos");
	public static Label otrosCostos = new Label("Otros Costos");
	public static Label recursosHumanos_adicionales = new Label("RRHH Adicionales");
	public static Label recursosHumanos_propios = new Label("RRHH Propios");
	
	//Valores de verdad
	public static Label SI = new Label("Si", "S");
	public static Label NO = new Label("No", "N");
}
