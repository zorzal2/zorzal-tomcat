package com.pragma.util.dataConversion;


public class ConversionUtils {
	
	public static final class ConversionException extends Exception {
		private static final long serialVersionUID = 1L;

		public ConversionException(String message) {
			super(message);
		}
		
	}
	
	private static ConversionUtils Default = new ConversionUtils(DefaultConversorLibrary.instance());
	
	private ConversorLibrary library;
	
	public ConversionUtils(ConversorLibrary library) {
		this.library = library; 
	}
	
	/**
	 * Convierte un objeto a otro de la clase dada. Si no es posible dispara
	 * una excepción de la clase ConversionException.
	 * @param src
	 * @param destClass
	 * @return
	 * @throws ConversionException 
	 * @throws ConversionException
	 */
	public Object convert(Object src, Class destClass) throws ConversionException {
		if(src==null) return null;
		if(destClass.isInstance(src)) {
			return src;
		}

		TypeConverter converter = library.converterFor(src.getClass(), destClass);
		if(converter==null) {
			throw new ConversionException("No hay conversor de "+src.getClass().getName()+" a "+destClass.getName());
		}
		return converter.convert(src, destClass);
	}

	public static ConversionUtils getDefault() {
		return Default;
	}

	public static void setDefault(ConversionUtils default1) {
		Default = default1;
	}

	public ConversorLibrary getLibrary() {
		return library;
	}

	public void setLibrary(ConversorLibrary library) {
		this.library = library;
	}
}