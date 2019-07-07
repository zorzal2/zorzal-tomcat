package com.pragma;

import com.fontar.data.impl.domain.dto.DTO;

public class PragmaExceptionDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String message;

	private String detail;

	private String customerMessage;

	private String administratorName;

	public String getCustomerMessage() {
		return customerMessage;
	}

	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAdministratorName() {
		return administratorName;
	}

	public void setAdministratorName(String administratorName) {
		this.administratorName = administratorName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
