package com.fontar.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class InboxController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub

		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

		GraphSession graphSession = jbpmContext.getGraphSession();
		ProcessDefinition processDefinition = graphSession.findLatestProcessDefinition("PlanEjecucion");

		ProcessInstance processInstance = new ProcessInstance(processDefinition);

		jbpmContext.save(processInstance);
		jbpmContext.close();

		return new ModelAndView("/inbox.jsp");
	}

}
