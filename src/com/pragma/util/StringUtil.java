package com.pragma.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.FastDateFormat;


/**
 * Funciones útiles referentes a cadenas
 * @author llobeto
 *
 */
public class StringUtil {

	private static FastDateFormat dateFormat = FastDateFormat.getInstance("dd/MM/yyyy"); //$NON-NLS-1$
    private static FastDateFormat dateTimeFormat = FastDateFormat.getInstance("dd/MM/yyyy HH:mm");

	/**
	 * Crear una cadena uniendo los elementos de un array
	 * y poniendo entre medio de los elementos un separador.
	 */    
	public static String join (Object[] iterable, String separator )
	{
		StringBuilder sbCadena = new StringBuilder();
		String _separator = "";
		for(Object element : iterable) {
			sbCadena.append(_separator);
			sbCadena.append(element);
			_separator = separator;
		}
		return sbCadena.toString();
	}
	/**
	 * Crear una cadena uniendo los elementos de un iterable
	 * y poniendo entre medio de los elementos un separador.
	 */    
	public static String join (Iterable iterable, String separator )
	{
		StringBuilder sbCadena = new StringBuilder();
		String _separator = "";
		for(Object element : iterable) {
			sbCadena.append(_separator);
			sbCadena.append(element);
			_separator = separator;
		}
		return sbCadena.toString();
	}
    /**
	 * Crear una cadena uniendo los elementos de un iterador
	 * y poniendo entre medio de los elementos un separador.
	 */    
    public static String join (Iterator iterator, String separator )
    {
    	if(!iterator.hasNext()) return "";

        StringBuffer sbCadena = new StringBuffer();
        String _separator = "";
        while(iterator.hasNext()) {
        	sbCadena.append(_separator);
        	sbCadena.append(iterator.next());
			_separator = separator;
        }

        return sbCadena.toString();
   }
    /**
	 * Crear una cadena uniendo los elementos de una enumeracion
	 * y poniendo entre medio de los elementos un separador.
	 */    
    public static String join (Enumeration enumetarion, String separator )
    {
        if(!enumetarion.hasMoreElements()) return "";

        StringBuffer sbCadena = new StringBuffer();
        
        sbCadena.append(enumetarion.nextElement());
        
        while(enumetarion.hasMoreElements()) {
        	sbCadena.append(enumetarion.nextElement());
        	sbCadena.append(separator);
        }

        return sbCadena.toString();
    }
    
    /**
     * Devuelve una lista de Strings con los elementos que 
     * se obtienen separando una cadena por medio de un
     * separador dado.
     */
    public static List<String> split(String  string, String separator) {
    	List<String> list=new ArrayList<String>();
    	int index=(separator.length()>0) ? string.indexOf(separator):-1, pointer=0, separatorLenght=separator.length();
    	while(index>=0) {
    		list.add(string.substring(pointer, index));
    		pointer=index+separatorLenght;
    		index=string.indexOf(separator, pointer);
    	}
    	//agrego el ultimo elemento
    	list.add(string.substring(pointer));
    	return list;
    }
    
    /**
     * Devuelve true si una cadena es nula o vacia
     */
    public static boolean isEmpty(String string){
    	return string==null || string.equals("");
    }
    /**
     * Devuelve true si una cadena no es nula ni vacia
     */
    public static boolean isNotEmpty(String string){
    	return !isEmpty(string);
    }

    /**
     * Devuelve true si no se le pasan cadenas vacias o nulas
     */
    public static boolean areNotEmpty(String string1, String string2){
    	
    	return Conditions.isNotEmpty().isTrueFor(string1) && Conditions.isNotEmpty().isTrueFor(string2);
    }
    /**
     * Devuelve true si no se le pasan cadenas vacias o nulas
     */
    public static boolean areNotEmpty(String... strings){
    	return CollectionUtils.noneSatisfy(strings, Conditions.isEmpty());
    }

    /**
     * Devuelve true si no se le pasan cadenas vacias o nulas
     */
    public static boolean anyNotEmpty(String... strings){
    	return CollectionUtils.anySatisfy(strings, Conditions.isNotEmpty());
    }
    
    /**
     * Devuelve true si la primer cadena es igual a alguna otra de las que recibe.
     * @param st1
     * @param strings
     * @return
     */
    public static boolean equalsOneOf(String st1, String...strings) {
    	for (String string : strings) {
			if(st1.equals(string)) return true;
		}
    	return false;
    }
    
    public static boolean contains(Object source, String substring) {
    	return source.toString().indexOf(substring)>=0;
    }
    
    public static String firstLetterUpperCase(String s){
    	if(s==null)return null;
    	if(s.equals(""))return "";
    	return s.substring(0, 1).toUpperCase()+s.substring(1);
    }
    public static String firstLetterLowerCase(String s){
    	if(s==null)return null;
    	if(s.equals(""))return "";
 	    	
    	return s.substring(0, 1).toLowerCase()+s.substring(1);
    }
    
    public static String replace(String source, String pattern, String replace)
    {
        if (source!=null) {
            final int len = pattern.length();
            StringBuffer sb = new StringBuffer();
            int found = -1;
            int start = 0;
    
            while( (found = source.indexOf(pattern, start) ) != -1) {
                sb.append(source.substring(start, found));
                sb.append(replace);
                start = found + len;
            }
    
            sb.append(source.substring(start));
    
            return sb.toString();
        }
        else return "";
    }
    
    public static Long nullSafeParseLong(String string) {
    	if(string==null) return null;
    	else return Long.valueOf(string); 
    }
    public static String formatMoneyForPresentation(Number money) {
    	return formatTwoDecimalForPresentation(money);
    }
    public static String formatTwoDecimalForPresentation(Number x) {
    	if(x==null) return "";
    	DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
    	return twoPlaces.format(x);
    }
    /**
     * Formatea un porcentaje con dos digitos decimales y el simboo %.
     * Si recibe null escribe 'N/A'
     * @param x
     * @return
     */
	public static String formatPercentageForPresentation(Number x) {
		if(x==null) return "N/A";
		return formatTwoDecimalForPresentation(x)+"%";
	}
	/**
	 *  "esto es una prueba" -> "EstoEsUnaPrueba"
	 */
	public static String toCamelCase(String name) {
		StringBuilder ret = new StringBuilder();
		String[] parts = name.split(" ");
		for(int i=0; i<parts.length;i++) 
			ret.append(firstLetterUpperCase(parts[i]));
		return ret.toString();
	}
    /**
     * Join agregando a un StringBuffer dado.
     * @param sbCadena
     * @param iterator
     * @param separator
     * @return
     */
    public static void joinOn (StringBuffer sbCadena, Iterator iterator, String separator )
    {
        if(!iterator.hasNext()) return;

        sbCadena.append(iterator.next());
        
        while(iterator.hasNext()) {
            sbCadena.append(separator);
            sbCadena.append(iterator.next());
        }
   }
    
    
    /** 
     * Retorna true si todos los caracteres son alfanumericos , espacio o - 
     **/
    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ( ! ( Character.isLetterOrDigit(str.charAt(i))  || str.charAt(i)== ' ' || str.charAt(i) == '-')) {
                return false;
            }
        }
        return true;
    }
    
    /** 
     * Retorna true si todos los caracteres son alfanumericos ,sin espacio 
     **/
    public static boolean esSinEspacio(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        if (sz != str.trim().length()) {
        	return false;
        }
        if (str.contains("  ")) {
        	return false;
        }
        return true;
    }
    
    /** 
     * Retorna true si todos los caracteres son alfanumericos ,sin espacio 
     **/
    public static boolean esMayuscula(String str) {
        if (str == null) {
            return false;
        }
        if (!str.equals(str.toUpperCase())) {
        	return false;
        }
        return true;
    }

    public static String formatDate(Date fecha) {
		if(fecha==null) return "";
    	return dateFormat.format(fecha);
    }
    
	public static String formatDateTime(Date fecha) {
		if(fecha==null) return "";
        return dateTimeFormat.format(fecha);
	}
    /**
     * Recorta la cadena dada de manera de que entre en la longitud dada.
     * Agrega puntos suspensivos.
     * @param string
     * @param size
     * @return
     */
    public static String trimToSize(String string, int size) {
    	if(string==null) return null;
    	if(string.length()>size) {
	    	if(size>3) {
	    		string = string.substring(0, size-3)+"...";
	    	} else {
	    		string = string.substring(0, size);
	    	}
    	}
    	return string;
    }
    /**
     * Reemplaza en la cadena pasada como primer argumento las apariciones
     * de $i, con i un entero mayor a cero, por las demas cadenas pasadas
     * como parametro numeradas desde 1. Por ejemplo:<br>
     * StringUtils.inject("$1, $2!", "Hola", "mundo") -> "Hola, mundo!" 
     * @param baseString
     * @param params
     * @return
     */
    public static String inject(String baseString, Object... params) {
    	for(int i=0; i<params.length; ) {
    		String replacement = String.valueOf(params[i++]);
    		baseString=baseString.replace("$"+i, replacement);
    	}
    	return baseString;
    }
    /**
     * Reemplaza las expresiones de tipo <code>${&lt;identificador&gt;}</code> de la
     * cadena dada por los valores del diccionario para cada identificador.
     * Ej:<br>
     * "Hola, ${name}" con ("name" -&gt; "mundo") devuelve "Hola, mundo"
     * @param baseString
     * @param values
     * @return
     */
    @SuppressWarnings("el-syntax")
    public static String injectMap(String baseString, Map<String, Object> values) {
    	for(Entry<String, Object> entry : values.entrySet()) {
    		String replacement = String.valueOf(entry.getValue());
    		baseString=baseString.replace("${"+entry.getKey()+"}", replacement);    		
    	}
    	return baseString;
    }
	public static String formatMoneyForPresentation(Number money, String symbol) {
		return formatMoneyForPresentation(money)+ " " + symbol;
	}
	/**
	 * Devuelve la representacion como Long de una cadena numerica.
	 * Si la cadena es vacia o nula devuelve null.
	 * @param numeric
	 * @return
	 */
	public static Long parseLong(String numeric) {
		if(isEmpty(numeric)) {
			return  null;
		} else {
			return new Long(numeric);
		}
	}
}