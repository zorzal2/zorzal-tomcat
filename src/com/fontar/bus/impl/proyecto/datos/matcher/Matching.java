package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.Map;
import java.util.Set;

import com.pragma.web.userfeedback.UserFeedbackRequest;
import com.pragma.web.userfeedback.UserFeedbackResponse;

interface Matching<Bean> {

	public abstract Set<Bean> getCandidates();

	public abstract UserFeedbackRequest getFeedbackRequired();

	//public abstract MatchingType getMatchingType();

	public abstract Bean solveWith(Map<String, UserFeedbackResponse> responses);
}