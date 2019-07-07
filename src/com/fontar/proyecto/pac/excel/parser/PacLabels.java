package com.fontar.proyecto.pac.excel.parser;

import com.pragma.excel.parser.util.Label;

public class PacLabels {
	public static Label SHEET_NAME = new Label("PAC");

	//Campos
	public static Label ITEM = new Label("Item", "?tem");
	public static Label DESCRIPCION = new Label("Descripci?n");
	public static Label ETAPA = new Label("Etapa");
	public static Label RUBRO = new Label("Rubro");
	public static Label MONTO_ESTIMADO = new Label("Monto Estimado");
	public static Label ADQUISICION = new Label("Tipo de Adquisici?n");
	public static Label FECHA_ESTIMADA = new Label("Fecha Estimada");
	
	public static Label[] Campos = {
		ITEM,
		DESCRIPCION,
		ETAPA,
		RUBRO,
		MONTO_ESTIMADO,
		ADQUISICION,
		FECHA_ESTIMADA
	}; 

	//Rubros
	public static Label BIENES = new Label("BIENES");
	public static Label OBRAS = new Label("OBRAS");
	public static Label CONS = new Label("CONS");
	public static Label MAT = new Label("MAT");
	public static Label OTROS = new Label("OTROS");
	public static Label[] Rubros = {
		BIENES,
		OBRAS,
		CONS,
		MAT,
		OTROS
	};
}
