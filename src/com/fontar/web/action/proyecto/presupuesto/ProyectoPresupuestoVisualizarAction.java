package com.fontar.web.action.proyecto.presupuesto;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.api.proyecto.presupuesto.ProyectoPresupuestoServicio;
import com.fontar.data.Constant;
import com.fontar.data.impl.assembler.ProyectoRaizCabeceraAssembler;
import com.fontar.data.impl.domain.dto.ProyectoRaizCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle.ProyectoPresupuestoDetalleDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ProyectoPresupuestoVisualizarAction extends BaseMappingDispatchAction {

	protected AdministrarProyectoRaizServicio proyectoServicio;
	protected ProyectoPresupuestoServicio presupuestoServicio;
	
	protected static Map<String, String> PaginaDetalle = new Hashtable<String, String>();
	static {
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.ANR, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.ARAI, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CF, "detalle.cf.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CF_CONSEJERIAS, "detalle.cf.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CONSEJERIAS, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.PATENTE, "detalle.patente.jsp");
	}

	public ProyectoPresupuestoServicio getPresupuestoServicio() {
		return presupuestoServicio;
	}

	public void setPresupuestoServicio(ProyectoPresupuestoServicio presupuestoServicio) {
		this.presupuestoServicio = presupuestoServicio;
	}

	public AdministrarProyectoRaizServicio getProyectoServicio() {
		return proyectoServicio;
	}

	public void setProyectoServicio(AdministrarProyectoRaizServicio proyectoServicio) {
		this.proyectoServicio = proyectoServicio;
	}

	private Long getIdProyecto(HttpServletRequest request){
		Long idProyecto = null;
		//veo si viene por parametro 
		if (validateParameter(request,"id")){
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		} else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		return idProyecto;
	}
	
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Long idProyecto = getIdProyecto(request);
		//Obtengo el presupuesto vigente
		ProyectoRaizDTO proyecto = proyectoServicio.getProyectoRaizDTO(idProyecto);
		ProyectoRaizCabeceraDTO proyectoRaizCabecera = ProyectoRaizCabeceraAssembler.buildDTO(proyecto);
		request.setAttribute("proyecto", proyectoRaizCabecera);
		request.setAttribute("paginaDetalle", PaginaDetalle.get(proyecto.getTipoMatrizPresupuesto()));
		request.setAttribute("ES_ADQUISICION", proyectoRaizCabecera.getPermiteAdquisicion());
		request.setAttribute("tipoInstrumento", proyectoRaizCabecera.getTipoInstrumentoDef());
		
		if (ActionUtil.isEncryptionContextAvailable()) {
			Long idPresupuesto = proyecto.getIdPresupuesto();
			if(idPresupuesto==null) idPresupuesto = proyecto.getIdPresupuestoOriginal();
			
			ProyectoPresupuestoDTO presupuesto = null;
			if(idPresupuesto!=null) {
				//cargo el presupuesto
				presupuesto = presupuestoServicio.load(idPresupuesto);
			}
			
			request.setAttribute("presupuesto", new ProyectoPresupuestoDetalleDTO(presupuesto, "id:"+(idPresupuesto==null? 0 : idPresupuesto)));
		} else {
			request.setAttribute("bundle", "errors");
			request.setAttribute("message", "app.error.encrypt");
		}
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward visualizarPresupuesto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Long idProyecto = new Long(request.getParameter("idProyecto"));
		Long idPresupuesto = new Long(request.getParameter("id"));
		
		//Obtengo el presupuesto vigente
		ProyectoRaizDTO proyecto = proyectoServicio.getProyectoRaizDTO(idProyecto);
		request.setAttribute("proyecto", ProyectoRaizCabeceraAssembler.buildDTO(proyecto));
		request.setAttribute("paginaDetalle", PaginaDetalle.get(proyecto.getTipoMatrizPresupuesto()));

		if (ActionUtil.isEncryptionContextAvailable()) {
			
			ProyectoPresupuestoDTO presupuesto = presupuestoServicio.load(idPresupuesto);
			request.setAttribute("presupuesto", new ProyectoPresupuestoDetalleDTO(presupuesto, "id:"+(idPresupuesto==null? 0 : idPresupuesto)));
		} else {
			request.setAttribute("bundle", "errors");
			request.setAttribute("message", "app.error.encrypt");
		}
		
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
