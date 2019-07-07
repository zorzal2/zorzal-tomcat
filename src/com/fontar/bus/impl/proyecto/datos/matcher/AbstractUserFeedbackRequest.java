package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.List;

import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.web.userfeedback.LabelValue;
import com.pragma.web.userfeedback.UserFeedbackRequest;
import com.pragma.web.userfeedback.UserFeedbackResponse;

public abstract class AbstractUserFeedbackRequest<T> extends UserFeedbackRequest {
	
	protected static final String OPCION_ERROR = "opcionError";
	
	public AbstractUserFeedbackRequest(String id, String message, String suggestedDefaultValue, List<LabelValue> options) {
		super(id, message, suggestedDefaultValue, options);
	}
	public T solve(Matching<T> matchingInfo, UserFeedbackResponse response) {
		return solve(matchingInfo, getUserSelection(response).getValue());
	}
	
	protected abstract T solve(Matching<T> matching, String value);
	
	protected T byId(Long id, Iterable<T> beans) {
		if(id==null) {
			for(T bean : beans) {
				if(BeanUtils.getId(bean)==null) return bean;
			}
		} else {
			for(T bean : beans) {
				if(id.equals(BeanUtils.getId(bean))) return bean;
			}
		}
		return null;
	}
	
	public static boolean canelled(UserFeedbackResponse response) {
		return response.getUserSelectionValue().equals(OPCION_ERROR);
	}
}
