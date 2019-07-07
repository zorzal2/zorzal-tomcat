package com.fontar.web.action.seguimientos.gestionPagos;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.SeguimientoGestionPagoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.web.action.seguimientos.seguimientos.SeguimientoBaseAction;

/**
 * Action para visualizar/editar los datos generales de una 
 * las rendiciones de un seguimiento para evaluar
 * la gestión de pago.<br>
 * @author ssanchez
 */
public class DatosGeneralesGestionPagoAction extends SeguimientoBaseAction {

	public ActionForward obtenerDatosResumen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idSeguimiento = getIdSeguimiento(request);
		
		SeguimientoVisualizacionCabeceraDTO dto = administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);

		SeguimientoGestionPagoDTO seguimientoDTO = administrarSeguimientoServicio.obtenerTotalesRendicionesParaEvaluarGestionDePago(idSeguimiento);
		request.setAttribute("rendiciones", seguimientoDTO);
		
		/*
		 * Si el seguimiento tiene desembolsos pendientes de pago no
		 * doy la posibilidad de gestionar y aviso con un mensaje.
		 */
		if(!seguimientoDTO.estaHabilitadoParaGestionar()) {
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.noPuedeGestionarPorItemsAutorizadosPendientesDePago");
			saveMessages(request, infoMessages);
		}
		
		setCollections(request, seguimientoDTO);
		
		return mapping.findForward("success");
	}
	
	private void setCollections(HttpServletRequest request, SeguimientoGestionPagoDTO seguimiento) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection<LabelValueBean> resultados = new ArrayList<LabelValueBean>();
		resultados.addAll(collectionHandler.getResultadosGestionSeguimiento(seguimiento));

		request.setAttribute("resultados", resultados);
	}
}
