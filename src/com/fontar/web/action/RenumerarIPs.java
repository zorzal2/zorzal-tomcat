package com.fontar.web.action;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.pragma.toolbar.data.TransactionalCommand;
import com.pragma.toolbar.exception.ControlledException;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class RenumerarIPs extends BaseMappingDispatchAction {
	
	public ActionForward doIt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().append("Reenumerando...");
		try {
		    (new TransactionalCommand() {
			public Object run(Object[] params) throws ControlledException {
				for (Entry<IdeaProyectoBean, Long> entry : numeration().entrySet()) {
					IdeaProyectoBean ideaProyecto = entry.getKey();
					Long k = entry.getValue();
					ideaProyecto.setCodigoIdeaProyecto(k);
					ideaProyecto.setCodigo(code(k));
				}
			    return Boolean.TRUE;
			}
		    }).execute();
		} catch(ControlledException e) {
		    e.printStackTrace(response.getWriter());
		    throw e;
		}
		response.getWriter().append("Ok!");
		return null;
	}
	public ActionForward renumerar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.flushBuffer();
		
		for (Entry<IdeaProyectoBean, Long> entry : numeration().entrySet()) {
			response.getWriter().append(entry.getKey().getCodigo());
			response.getWriter().append(" -> ");
			response.getWriter().append(code(entry.getValue())+"<br>");
		}
		response.getWriter().append("<br><br><a href=\"renumerarDoIt.do\">Proceder</a>");
		return null;
	}
	private Map<IdeaProyectoBean, Long> numeration() {
		Map<IdeaProyectoBean, Long> ret = new LinkedHashMap<IdeaProyectoBean, Long>();
		IdeaProyectoDAO ideaProyectoDao = (IdeaProyectoDAO)ContextUtil.getBean("ideaProyectoDao");
		List<IdeaProyectoBean> all = ideaProyectoDao.getAll();
		TreeSet<IdeaProyectoBean> ideaProyectos = new TreeSet<IdeaProyectoBean>(new Comparator<IdeaProyectoBean>() {
			public int compare(IdeaProyectoBean o1, IdeaProyectoBean o2) {
				return o1.getCodigoIdeaProyecto().compareTo(o2.getCodigoIdeaProyecto());
			}
		});
		ideaProyectos.addAll(all);
		long i = 2314l;
		for (IdeaProyectoBean ideaProyecto : ideaProyectos) {
			ret.put(ideaProyecto, i);
			i++;
		}
		return ret;
	}
	private DecimalFormat iPCodeFormatter = new DecimalFormat("00000000");
	private String code(Long code) {
		return "IP-" + iPCodeFormatter.format(code);
	}
}
