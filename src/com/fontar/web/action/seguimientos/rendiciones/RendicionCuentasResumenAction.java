package com.fontar.web.action.seguimientos.rendiciones;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.dto.ResumenDeRendicionesDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.MathUtils;

/**
 * 
 * @author gboaglio
 * 
 */
public abstract class RendicionCuentasResumenAction extends AdministrarRendicionCuentasAction {

	
	/**
	 * Prepara la pantalla de Resumen de Rendiciones para un Seguimiento
	 * 
	 */
	public ActionForward resumen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setCabeceraVisualizacionSeguimiento(request);
		
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		
		List<ResumenDeRendicionesDTO> resumenRendiciones = administrarSeguimientoService.obtenerResumenRendiciones(idSeguimiento);
		
		// Calculo totales
		BigDecimal costoSolicitadoTotalAcumulado = BigDecimal.ZERO;
		BigDecimal montoSolicitadoParteAcumulado = BigDecimal.ZERO;
		BigDecimal montoSolicitadoContraparteAcumulado = BigDecimal.ZERO;
		
		BigDecimal costoAprobadoTotalAcumulado = BigDecimal.ZERO;
		BigDecimal montoAprobadoParteAcumulado = BigDecimal.ZERO;
		BigDecimal montoAprobadoContraparteAcumulado = BigDecimal.ZERO;
		
		for (ResumenDeRendicionesDTO rubro: resumenRendiciones) {
			//Solicitado
			costoSolicitadoTotalAcumulado = costoSolicitadoTotalAcumulado.add(rubro.getSolicitado().getCostoTotal());
			
			BigDecimal montoSolicitadoParte = rubro.getSolicitado().getMontoParte();			
			
			if (montoSolicitadoParte != null) {
				montoSolicitadoParteAcumulado = montoSolicitadoParteAcumulado.add(montoSolicitadoParte);
			}
			
			BigDecimal montoSolicitadoContraparte = rubro.getSolicitado().getMontoContraparte();
			
			if (montoSolicitadoContraparte!= null) {			
				montoSolicitadoContraparteAcumulado = montoSolicitadoContraparteAcumulado.add(montoSolicitadoContraparte);
			}
			
			//Aprobado
			costoAprobadoTotalAcumulado = costoAprobadoTotalAcumulado.add(rubro.getAprobado().getCostoTotal());
			
			BigDecimal montoAprobadoParte = rubro.getAprobado().getMontoParte();			
			
			if (montoAprobadoParte != null) {
				montoAprobadoParteAcumulado = montoAprobadoParteAcumulado.add(montoAprobadoParte);
			}
			
			BigDecimal montoAprobadoContraparte = rubro.getAprobado().getMontoContraparte();
			
			if (montoAprobadoContraparte!= null) {			
				montoAprobadoContraparteAcumulado = montoAprobadoContraparteAcumulado.add(montoAprobadoContraparte);
			}
		}
		
		request.setAttribute("resumenRendicionesList", resumenRendiciones);

		request.setAttribute("costoSolicitadoTotalAcumulado", MathUtils.formatTwoPlaces(costoSolicitadoTotalAcumulado));
		request.setAttribute("montoSolicitadoParteAcumulado", MathUtils.formatTwoPlaces(montoSolicitadoParteAcumulado));
		request.setAttribute("montoSolicitadoContraparteAcumulado", MathUtils.formatTwoPlaces(montoSolicitadoContraparteAcumulado));
		
		request.setAttribute("costoAprobadoTotalAcumulado", MathUtils.formatTwoPlaces(costoAprobadoTotalAcumulado));
		request.setAttribute("montoAprobadoParteAcumulado", MathUtils.formatTwoPlaces(montoAprobadoParteAcumulado));
		request.setAttribute("montoAprobadoContraparteAcumulado", MathUtils.formatTwoPlaces(montoAprobadoContraparteAcumulado));
		
		return mapping.findForward("success");
	}

}
