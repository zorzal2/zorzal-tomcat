package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.Constant.MatrizPresupuestoTipo;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author gboaglio
 * 
 */
public class VisualizarSeguimientoAction extends SeguimientoBaseAction {

	/**
	 * Prepara la pantalla para la visualización de un seguimiento
	 * 
	 */
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Me guardo en una variable de sesión la acción de donde vengo.
		this.setForward(request, NavigationManager.getPreviousURIHref(request,BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD));		

		// Levanto el id de seguimientos
		Long idSeguimiento = getIdSeguimiento(request);
		
		// Guardo el id del seguimiento en la sesión para su uso entre las solapas
		SessionHelper.setIdSeguimiento(request, idSeguimiento);
		
		//
		SeguimientoBean seguimiento = administrarSeguimientoServicio.obtenerSeguimiento(idSeguimiento);
		request.setAttribute("iniciado", seguimiento.getEstado().equals(EstadoSeguimiento.INICIADO));
		
		// Seteo los datos que se van a visualizar
		request.setAttribute("seguimientoVisualizar", seguimiento);

		// Seteo el id del seguimiento necesario para pasárselo al link de "editar"
		request.setAttribute("id", idSeguimiento);
		
		Long idProyecto = seguimiento.getIdProyecto();
		SessionHelper.setIdProyecto(request, idProyecto);
		
		// Seteo una variable que determinará si se muestra la solapa de rendición de cuentas o no
		Boolean esFinanciero = seguimiento.getEsFinanciero();
		SessionHelper.setEsFinanciero(request, esFinanciero);
		
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio) ContextUtil.getBean("administrarProyectoService");
		String tipoMatriz = administrarProyectoServicio.getTipoMatrizPresupuesto(idProyecto);

		SessionHelper.setEsANR(request, tipoMatriz.equals(MatrizPresupuestoTipo.ANR));
		SessionHelper.setEsARAI(request, tipoMatriz.equals(MatrizPresupuestoTipo.ARAI));
		SessionHelper.setEsCreditoFiscal(request, tipoMatriz.equals(MatrizPresupuestoTipo.CF));
		SessionHelper.setEsCreditoFiscalConsejerias(request, tipoMatriz.equals(MatrizPresupuestoTipo.CF_CONSEJERIAS));
		SessionHelper.setEsPatente(request, tipoMatriz.equals(MatrizPresupuestoTipo.PATENTE));
		
		
		//	Cargo datos para la cabecera	
		SeguimientoVisualizacionCabeceraDTO dto = administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);
		
		
		
		return mapping.findForward("success");
	}
	
	

	
}
