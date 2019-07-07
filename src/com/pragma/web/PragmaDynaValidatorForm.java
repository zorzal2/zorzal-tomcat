package com.pragma.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.ValidatorResults;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

@SuppressWarnings("serial")
public class PragmaDynaValidatorForm extends DynaValidatorForm {

	
	
	@Override
	public void setValidatorResults(ValidatorResults arg0) {
		// TODO Auto-generated method stub
		super.setValidatorResults(arg0);
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return super.validate(arg0, arg1);
	}

	public boolean hasError(ActionMapping mapping, HttpServletRequest request) {
		return !validate(mapping, request).isEmpty();
	}

	@Override
	public void set(String arg0, int arg1, Object arg2) {
		// TODO Auto-generated method stub
		super.set(arg0, arg1, arg2);
	}

	@Override
	public void set(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		super.set(arg0, arg1);
	}

	@Override
	public void set(String arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		super.set(arg0, arg1, arg2);
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		super.reset(arg0, arg1);
	}
	
	

}
