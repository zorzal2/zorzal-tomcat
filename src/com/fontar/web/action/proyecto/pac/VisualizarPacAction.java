package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.data.impl.domain.dto.VisualizarProcedimientoDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class VisualizarPacAction extends BaseMappingDispatchAction {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	AdministrarProcedimientoServicio administrarProcedimientoServicio;

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = getErrors(request);		

		Long idProyecto = null;

		String sId = null;
		
		if (errors.isEmpty()) {
			sId = request.getParameter("id");
		}
		else {
			sId = FormUtil.getStringValue(form,"id");
		}
		Long id = new Long(sId);
//		setCollections(request,id);
		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		VisualizarPacItemDTO pacDTO = administrarPACServicio.obtenerDatosItemTabla(id);
		request.setAttribute("pac", pacDTO);
		
		List<ProcedimientoItemBean> list = administrarProcedimientoServicio.obtenerProcedimientoItemPac(id);
		List<VisualizarProcedimientoDTO> displayList = new ArrayList<VisualizarProcedimientoDTO>(); 
		DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
		for (ProcedimientoItemBean bean : list) {
			VisualizarProcedimientoDTO dto = new VisualizarProcedimientoDTO();
			dto.setId(bean.getIdProcedimiento());
			dto.setCodigo(bean.getProcedimiento().getCodigo());
			if (bean.getProcedimiento().getEvaluador() != null) {
				dto.setEvaluador(bean.getProcedimiento().getEvaluador().getNombre());
			}
			dto.setFechaRecepcion(StringUtil.formatDate(bean.getProcedimiento().getFechaRecepcion()));
			dto.setMontoAdjudicacion(twoPlaces.format(bean.getMontoAdjudicacion()));
			if (bean.getResultadoFontar() != null) {
				if (bean.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_BID)) {
					dto.setRemision("Al BID");
				}
				if (bean.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_UFFA)) {
					dto.setRemision("A la UFFA");
				}
			}
			displayList.add(dto);
		}
		request.setAttribute("procedimiento" , displayList);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public AdministrarPACServicio getAdministrarPACServicio() {
		return administrarPACServicio;
	}

	public void setAdministrarPACServicio(AdministrarPACServicio administrarPACServicio) {
		this.administrarPACServicio = administrarPACServicio;
	}

	public AdministrarProcedimientoServicio getAdministrarProcedimientoServicio() {
		return administrarProcedimientoServicio;
	}

	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}

	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

}