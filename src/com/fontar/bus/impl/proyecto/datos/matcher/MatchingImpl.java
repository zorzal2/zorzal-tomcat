/**
 * 
 */
package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.Map;
import java.util.Set;

import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.UserFeedbackResponse;

class MatchingImpl<Bean> implements Matching<Bean> {
	private Set<Bean> candidates;
	private AbstractUserFeedbackRequest<Bean> feedbackRequired = null;
	
	public MatchingImpl(Set<Bean> candidates, AbstractUserFeedbackRequest<Bean> feedbackRequired) {
		super();
		this.candidates = candidates;
		this.feedbackRequired = feedbackRequired;
	}
	public MatchingImpl(Set<Bean> candidates) {
		super();
		this.candidates = candidates;
	}

	/* (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.datos.matcher.MatchingInfo#getCandidates()
	 */
	public Set<Bean> getCandidates() {
		return candidates;
	}

	/* (non-Javadoc)
	 * @see com.fontar.bus.impl.proyecto.datos.matcher.MatchingInfo#getFeedbackRequired()
	 */
	public AbstractUserFeedbackRequest<Bean> getFeedbackRequired() {
		return feedbackRequired;
	}

	public Bean solveWith(Map<String, UserFeedbackResponse> responses) {
		AbstractUserFeedbackRequest<Bean> request = getFeedbackRequired();
		if(request==null) return CollectionUtils.getAny(candidates);
		UserFeedbackResponse userFeedbackResponse = responses.get(getFeedbackRequired().getId());
		if(userFeedbackResponse==null) return null;
		else return request.solve(this, userFeedbackResponse);
	}
}