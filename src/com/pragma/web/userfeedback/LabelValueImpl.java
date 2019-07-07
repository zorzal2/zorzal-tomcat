package com.pragma.web.userfeedback;

import org.apache.struts.util.LabelValueBean;

public class LabelValueImpl extends LabelValueBean implements LabelValue {

	private static final long serialVersionUID = 1L;
	
	public LabelValueImpl(String label, String value) {
		super(label, value);
	}
}
