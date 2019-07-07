package com.pragma.util;


/**
 * Declaraciones e implementaciones de transformaciones utiles. para
 * @author llobeto
 *
 */
public class Operations {

	public static abstract class Operation<Operand, Result> {
		/**
		 * Aplica la operacion a un elemento.
		 * @param o
		 * @return
		 */
		public abstract Result operateOn(Operand o);
		/**
		 * Agrega (con algun criterio) un nuevo resultado al resultado acumulado
		 * hasta el momento. 
		 * @param acumulator Valor actual del acumulador
		 * @param result resultado de operar con el siguiente elemento
		 * @return nuevo valor del acumulador
		 */
		public abstract Result accumulate(Result accumulator, Result result);
		/**
		 * Devuelve el valor inicial del acumulador.
		 * @return
		 */
		public abstract Result initialAcumulator();
		
		public Result applyTo(Iterable<Operand> operands) {
			Result accumulator = initialAcumulator();
			for(Operand operand : operands) {
				accumulator = accumulate(accumulator, operateOn(operand));
			}
			return accumulator;
		}

		public Result applyTo(Operand[] operands) {
			Result accumulator = initialAcumulator();
			for(Operand operand : operands) {
				accumulator = accumulate(accumulator, operateOn(operand));
			}
			return accumulator;
		}
	}
	public abstract static class Action<Operand> extends Operation<Operand, Object> {

		public abstract void doWith(Operand o);
		
		public Object operateOn(Operand o) {
			doWith(o);
			return null;
		}
		public Object accumulate(Object acumulator, Object result) {
			return null;
		}
		public Object initialAcumulator() {
			return null;
		}
    }
}
