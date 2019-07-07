package com.pragma.toolbar.data;

import com.pragma.toolbar.exception.ControlledException;
import com.pragma.toolbar.exception.UnControlledException;

/**
 * @author gwinniczuk
 *
 */
public abstract class TransactionalCommand extends Command {

	public final Object execute(Object... params) throws ControlledException {
		
		Object result = null;
		// Creo el contexto de ejecucion
		DatabaseContext context = DatabaseContext.create();
		// Encierro la ejecucion del Comando en una transaccion
		context.beginTransaction();
		try {
			//Ejecuto el comando
			result = this.run(params);
			context.commitTransaction();
			
		} catch (ControlledException exception) {
			context.rollbackTransaction();
			throw exception;
		} catch (Exception exception) {
			context.rollbackTransaction();
			exception.printStackTrace();
			throw new UnControlledException(exception);
			
		} finally {
			// Libero recursos
			DatabaseContext.destroy();
		}

		// Ejecuto el Comando Dummy para forzar el flush de cache
		// Solucion fea y temporal
		//new DummyCreateCommand().execute();
		
		return result;
	}
}