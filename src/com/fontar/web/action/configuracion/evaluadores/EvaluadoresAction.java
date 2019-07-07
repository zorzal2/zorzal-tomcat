package com.fontar.web.action.configuracion.evaluadores;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.pragma.bus.impl.GenericABMServiceImpl;

/**
 * 
 * @author gboaglio
 * 
 */
public class EvaluadoresAction extends MappingDispatchAction {

	private GenericABMServiceImpl evaluadorServicio;

	public void setEvaluadorServicio(GenericABMServiceImpl evaluadorServicio) {
		this.evaluadorServicio = evaluadorServicio;
	}

	/**
	 * Muestra el formulario para cargar un proyecto de una presentación
	 * determinada
	 */
	public ActionForward cargarProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EvaluadorBean evaluadorBean = new EvaluadorBean();

		// levantamos el parametro de identificación del objeto
		String strId = request.getParameter("id");

		if (!(strId == null || strId == "")) {
			evaluadorBean = (EvaluadorBean) evaluadorServicio.load(new Long(strId));
		}
		else {
			// TODO: levantar la excepción correspondiente
		}

		request.setAttribute("evaluador", evaluadorBean);

		// seteamos el idEvaluador
		BeanUtils.setProperty(form, "idEvaluador", strId);

		return mapping.findForward("cargar");
	}

	/**
	 * Muestra el formulario para dar de alta o editar la presentación de un
	 * proyecto
	 * 
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EvaluadorBean evaluadorBean = new EvaluadorBean();

		// levantamos el parametro de identificación del objeto
		String strId = request.getParameter("id");

		if (!(strId == null || strId == "")) {
			evaluadorBean = (EvaluadorBean) evaluadorServicio.load(new Long(strId));
		}
		else {
			// TODO: levantar la excepción correspondiente
		}

		// llena la lista de datos de los combos de la pantalla
		request.setAttribute("evaluador", evaluadorBean);

		// copiamos los datos al form
		BeanUtils.copyProperties(form, evaluadorBean);

		request.setAttribute("estado", "presentada");
		return mapping.findForward("editar");
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// llena la lista de datos de los combos de la pantalla
		request.setAttribute("estado", "presentada");
		return mapping.findForward("agregar");
	}

	/**
	 * Muestra el formulario para eliminar la presentación de un proyecto
	 */
	public ActionForward eliminar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluadorBean evaluadorBean = new EvaluadorBean();

		// Obtiene el id de la convocatoria para eliminar
		// LlamadoConvocatoriaBean =
		// (LlamadoConvocatoriaBean)request.getAttribute("id");

		String strId = request.getParameter("id");

		if (!(strId == null || strId == "")) {
			evaluadorBean = (EvaluadorBean) evaluadorServicio.load(new Long(strId));
		}
		else {
			// TODO: levantar la excepción correspondiente
		}

		evaluadorServicio.delete(evaluadorBean);
		return verInventario(mapping, form, request, response);
	}

	/**
	 * Guarda los datos ingresados en la pantalla de Alta/Edicion Presentacion
	 * 
	 */
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluadorBean evaluadorBean = new EvaluadorBean();

		// copio los datos del Form al Bean
		BeanUtils.copyProperties(evaluadorBean, form);

		// Guardo los datos en DB
		evaluadorServicio.save(evaluadorBean);

		return mapping.findForward("inventario");
	}

	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Collection evaluadores;

		// cargo la lista de evaluadores
		evaluadores = evaluadorServicio.getAll();

		// agrega en el request del la coleccion de evaluadores
		request.setAttribute("evaluadores", evaluadores);

		// TODO: Prueba
		if (request.getParameter("pageSize") == null || request.getParameter("pageSize") == "") {
			request.setAttribute("pageSize", new Integer(10));
		}
		else {
			request.setAttribute("pageSize", new Integer(request.getParameter("pageSize")));
		}

		return mapping.findForward("inventario");
	}

}
