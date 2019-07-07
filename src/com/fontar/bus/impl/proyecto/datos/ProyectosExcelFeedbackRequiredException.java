package com.fontar.bus.impl.proyecto.datos;

import java.util.Collection;
import java.util.Set;

import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.UserFeedbackRequest;

public class ProyectosExcelFeedbackRequiredException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Set<UserFeedbackRequest> feedbackRequests;
	
	public ProyectosExcelFeedbackRequiredException(UserFeedbackRequest... feedbackRequests) {
		this.feedbackRequests = CollectionUtils.linkedSetWith(feedbackRequests); 
	}

	public Set<UserFeedbackRequest> getFeedbackRequests() {
		return feedbackRequests;
	}
	
	public void addFeedbackRequest(UserFeedbackRequest feedbackRequest) {
		this.feedbackRequests.add(feedbackRequest);
	}

	public void addFeedbackRequests(Collection<UserFeedbackRequest> feedbackRequests) {
		this.feedbackRequests.addAll(feedbackRequests);
	}
}
