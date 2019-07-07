package com.fontar.web.action.administracion.paquete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.paquete.AdministrarPaqueteServicio;
import com.fontar.bus.api.paquete.EvaluarPaqueteServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarPaqueteDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action para ver detalles de datos de paquetes
 * @author ssanchez
 * @version 1.00, 28/11/06
 */
public class VisualizarPaqueteAction extends BaseMappingDispatchAction {

	AdministrarPaqueteServicio administracionPaqueteService;

	public AdministrarPaqueteServicio getAdministracionPaqueteService() {
		return administracionPaqueteService;
	}

	public void setAdministracionPaqueteService(AdministrarPaqueteServicio administracionPaqueteService) {
		this.administracionPaqueteService = administracionPaqueteService;
	}

	public ActionForward visualizarBitacora(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = getErrors(request);

		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty() ){
		
			Long id = administracionPaqueteService.getIdPaquete(new Long(request.getParameter("id")));
			VisualizarPaqueteDTO visualiarPaqueteDto = administracionPaqueteService.obtenerVisualizacionPaquete(id);
	
			EvaluarPaqueteServicio evaluarPaqueteServicio = (EvaluarPaqueteServicio) ContextUtil.getBean("evaluarPaqueteService");
			PaqueteDTO paqueteDto = evaluarPaqueteServicio.obtenerPaquete(visualiarPaqueteDto.getId());
			
			configurarTipoDeVisualizacionDeProyectos(request, paqueteDto);
			
			FormUtil.copyProperties(form, visualiarPaqueteDto);
			request.setAttribute(CabeceraAttribute.PAQUETE,visualiarPaqueteDto);
	
			request.setAttribute("collection", paqueteDto.getFilasProyectos());
			request.setAttribute("tipoPaquete", visualiarPaqueteDto.getTipo());
			return mapping.findForward("success");
		} else {
			saveErrors(request,messages);
			ActionForward actionForward = NavigationManager.getPreviousAction(request);
			if(actionForward!=null) return actionForward;
			return mapping.findForward("invalid");
		}		
	}

	/**
	 * Muestra listado de proyectos pertenecientes al paquete
	 * @author ssanchez
	 */
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		
		ActionMessages messages = getErrors(request);

		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty() ){
			VisualizarPaqueteDTO visualizarPaqueteDto = administracionPaqueteService.obtenerVisualizacionPaquete(new Long(request.getParameter("id")));

			EvaluarPaqueteServicio evaluarPaqueteServicio = (EvaluarPaqueteServicio) ContextUtil.getBean("evaluarPaqueteService");
			PaqueteDTO paqueteDto = evaluarPaqueteServicio.obtenerPaquete(visualizarPaqueteDto.getId());
			configurarTipoDeVisualizacionDeProyectos(request, paqueteDto);

			FormUtil.copyProperties(form, visualizarPaqueteDto);
			request.setAttribute(CabeceraAttribute.PAQUETE,visualizarPaqueteDto);

			request.setAttribute("collection", paqueteDto.getFilasProyectos());
			request.setAttribute("tipoPaquete", visualizarPaqueteDto.getTipo());
			
			return mapping.findForward("success");
		}
		else {
			saveErrors(request,messages);
			if (mapping.getInput() != null) {
				return mapping.getInputForward();
			}
			else {
				return mapping.findForward("invalid");
			}
		}
	}
	private void configurarTipoDeVisualizacionDeProyectos(HttpServletRequest request, PaqueteDTO paqueteDto) {
		/*
		 * Configuracion de la lista de proyectos. Hay tres posibilidades:
		 * - Matriz de presupuesto NO-CF
		 * - Matriz de presupuesto CF sin carga de alicuota
		 * - Matriz de presupuesto CF con carga de alicuota.
		 * Carga de alicuota:
		 * - Matriz de presupuesto CF
		 * - Paquete de directorio
		 * - El tratamiento del paquete es Adjudicacion o el instrumento tiene
		 *   adjudicacion directa.
		 */
		Boolean noCF,
				CFSinCargaDeAlicuota,
				CFConCargaDeAlicuota;
		if(paqueteDto.getEsCreditoFiscal()) {
			if(
					paqueteDto.getTipoPaquete().equals(TipoPaquete.DIRECTORIO) &&
					(paqueteDto.getTratamiento().equals(TratamientoPaquete.ADJUDICACION) || paqueteDto.getInstrumentoPermiteAdjudicacion())
			) {
				noCF = Boolean.FALSE;
				CFSinCargaDeAlicuota = Boolean.FALSE;
				CFConCargaDeAlicuota = Boolean.TRUE;
			} else {
				noCF = Boolean.FALSE;
				CFSinCargaDeAlicuota = Boolean.TRUE;
				CFConCargaDeAlicuota = Boolean.FALSE;
			}
		} else {
			noCF = Boolean.TRUE;
			CFSinCargaDeAlicuota = Boolean.FALSE;
			CFConCargaDeAlicuota = Boolean.FALSE;
		}
		request.setAttribute("VisualizacionProyectos_noCF", noCF);
		request.setAttribute("VisualizacionProyectos_CFSinCargaDeAlicuota", CFSinCargaDeAlicuota);
		request.setAttribute("VisualizacionProyectos_CFConCargaDeAlicuota", CFConCargaDeAlicuota);
	}
}
