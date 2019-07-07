package com.fontar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

public class ExcelUtil {
	/**
	 * Abre un cuaderno de excel asumiendo la configuracion regional de Argentina.
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	public static Workbook getWorkbook(InputStream inputStream) throws IOException, PasswordException, BiffException {
		Workbook workbook;
		WorkbookSettings settings = new WorkbookSettings();
		settings.setLocale(new Locale("ES", "ar"));
		settings.setEncoding("Cp1252");
		settings.setExcelDisplayLanguage("ES");
		workbook = Workbook.getWorkbook(inputStream, settings);
		return workbook;
	}
}
