package com.pragma.web.userfeedback;

/**
 * Representa la opcion elegida por el usuario ante un pedido de feedback por parte de
 * la aplicacion.
 * @author llobeto
 *
 */
public class UserFeedbackResponse {
	private String id;
	private String userSelectionValue;
	
	
	public UserFeedbackResponse(String id, String userSelectionValue) {
		this.id = id;
		this.userSelectionValue = userSelectionValue;
	}
	/**
	 * Identificacion del request que se esta respondiendo.
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * Respuesta elegida por el usuario.
	 * @return
	 */
	public String getUserSelectionValue() {
		return userSelectionValue;
	}
}