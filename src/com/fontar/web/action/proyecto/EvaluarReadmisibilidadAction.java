package com.fontar.web.action.proyecto;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.AdmisibilidadProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.impl.assembler.EvaluarAdmisibilidadProyectoAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.dto.AdmisibilidadVisualizarDTO;
import com.fontar.data.impl.domain.dto.EvaluarAdmisibilidadProyectoDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class EvaluarReadmisibilidadAction extends ProyectoBaseTaskAction {

	private AdmisibilidadProyectoServicio admisibilidadProyectoServicio;
	
	public void setAdmisibilidadProyectoServicio(AdmisibilidadProyectoServicio admisibilidadProyectoServicio) {
		this.admisibilidadProyectoServicio = admisibilidadProyectoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);

		EvaluarAdmisibilidadProyectoDTO proyectoDTO = (EvaluarAdmisibilidadProyectoDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new EvaluarAdmisibilidadProyectoAssembler());

		request.setAttribute(CabeceraAttribute.PROYECTO, proyectoDTO);
		
		BitacoraDAO bitacoraDAO = (BitacoraDAO) ContextUtil.getBean("bitacoraDao");
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		List<BitacoraBean> bitacora = bitacoraDAO.findByProyectoTipo(idProyecto, TipoBitacora.ADMISION.getName());
		if (bitacora != null) {
			for (int i =0; i < bitacora.size(); i++) {
				BitacoraBean bitacoraBean = bitacora.get(i);
				AdmisibilidadVisualizarDTO admisibilidadVisualizarDTO = admisibilidadProyectoServicio.getVisualizacionAdmisibilidad(bitacoraBean.getId());
		
				request.setAttribute("admisibilidad", admisibilidadVisualizarDTO);
			}
		}
		request.setAttribute("idProyecto", idProyecto);
		
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Date fecha = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fecha"));

//		TODO: NotificacionBean
//		Date fechaAcuse = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fechaAcuse"));
		String fundamentacion = BeanUtils.getSimpleProperty(form, "fundamentacion");
		String resultado = BeanUtils.getSimpleProperty(form, "resultado");
		String resolucion = BeanUtils.getSimpleProperty(form, "resolucion");
		String observacion = BeanUtils.getSimpleProperty(form, "observacion");

		wfProyectoServicio.cargarReadmisionAlProyecto(fecha, fundamentacion, resultado, resolucion, observacion, idTaskInstance);

	}
}