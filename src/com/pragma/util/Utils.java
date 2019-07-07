package com.pragma.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.pragma.toolbar.exception.UnControlledException;


/**
 * Funiones útiles de uso general.
 * @author llobeto
 *
 */
public class Utils {
	public static boolean equalsOneOf(Object src, Object... options) {
		for(Object option : options) {
			if(src.equals(option)) return true;
		}
		return false;
	}
	public static <T> T notNullOf(T... objects) {
		for(T object : objects) {
			if(object!=null) return object;
		}
		return null;
	}
	public static boolean isAnyNull(Object... objects) {
		for(Object object : objects) {
			if(object==null) return true;
		}
		return false;
	}
	public static boolean isAnyNotNull(Object... objects) {
		for(Object object : objects) {
			if(object!=null) return true;
		}
		return false;
	}
	
	/**
	 * Las instancias de esta clase muestran un mensaje por consola cuando 
	 * son finalizadas.
	 * @author llobeto
	 *
	 */
	public static class DontKillMe {
		private String message;
		
		public DontKillMe(String message) {
			this.message = message;
		}
		public void invalidate() {
			this.message = null;
		}
		protected void finalize() throws Throwable {
			if(message!=null)System.out.println(message);
			super.finalize();
		}
	}
	
	public static final int BUFFER_SIZE=2048; //2KB
	public static void bufferedStreamCopy(InputStream in, OutputStream out) {
		try {
			byte[] buffer = new byte[BUFFER_SIZE];
			int readCount;
			while ((readCount=in.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch(IOException exception) {
				exception.printStackTrace();
				throw new UnControlledException(exception);
			}
		}
	}
	public static void streamCopy(InputStream in, OutputStream out, int maxBytes) {
		try {
			byte[] buffer = new byte[maxBytes];
			int readCount = in.read(buffer);
			out.write(buffer, 0, readCount);
		} catch (IOException exception) {
			exception.printStackTrace();
			throw new UnControlledException(exception);
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch(IOException exception) {
				exception.printStackTrace();
				throw new UnControlledException(exception);
			}
	    }
	}
	public static <T> T nvl(T t, T defaultValue) {
		if(t==null) return defaultValue;
		else return t;
	}
	public static String nvl(String t, String defaultValue) {
		if(t==null || t.equals("")) return defaultValue;
		else return t;
	}
	public static String nvl(String t) {
		if("".equals(t)) return null;
		else return t;
	}
}