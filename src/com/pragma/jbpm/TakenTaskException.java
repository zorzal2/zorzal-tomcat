package com.pragma.jbpm;

public class TakenTaskException extends WorkFlowException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TakenTaskException(String actorId, String ownerId) {
		super("La tarea se encuentra tomada por el actor " + ownerId + ". No podrá ser tomada por el actor " + actorId);
	}
}
