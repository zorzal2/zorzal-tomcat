package com.pragma.toolbar.data;

import com.pragma.toolbar.exception.ControlledException;
import com.pragma.toolbar.exception.UnControlledException;

/**
 * @author gwinniczuk
 *
 */
public abstract class Command<T> {
	
	public T execute(Object... params) throws ControlledException {
	
		T result = null;
		try {
			// Creo el contexto de ejecucion
			DatabaseContext.create();
			//Ejecuto el comando
			result = this.run(params);

		} catch(ControlledException exception) {
			//Excepción controlada
			throw exception;
		} catch(UnControlledException exception) {
			exception.printStackTrace();
			throw exception;
		} catch(Exception exception) {
			//excepciones no controlas
			exception.printStackTrace();
			throw new UnControlledException(exception);
		} finally {
			// Libero recursos
			DatabaseContext.destroy();
		}
	
		return result;
	}

	public abstract T run(Object[] params) throws ControlledException;
}
