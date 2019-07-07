package com.fontar.web.action.administracion.paquete;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.workflow.WFPaqueteServicio;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.PaqueteEvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

public class PaqueteBaseTaskAction extends GenericJbpmTaskAction {

	protected WFPaqueteServicio wfPaqueteServicio;

	public void setWfPaqueteServicio(WFPaqueteServicio wfPaqueteServicio) {
		this.wfPaqueteServicio = wfPaqueteServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		PaqueteDTO paqueteDto = wfPaqueteServicio.obtenerPaquete(idTaskInstance);
		SessionHelper.setPaquete(request,paqueteDto);
		request.setAttribute("id", paqueteDto.getId());
		request.setAttribute("instrumento", paqueteDto.getDescInstrumento());
		request.setAttribute("tipoPaquete", paqueteDto.getTipoPaquete().getName());
		request.setAttribute("tratamiento", paqueteDto.getTratamiento().getDescripcion());
		if(paqueteDto.getComision() != null) {
			request.setAttribute("comision", paqueteDto.getComision()); 
		}
		request.setAttribute("estado", paqueteDto.getEstado());
		// Debo cambiar los valores antes de mostrar
		this.cambiarValorResultado(paqueteDto.getFilasProyectos(), request);
		request.setAttribute("collection", paqueteDto.getFilasProyectos());
		
		// si no vengo de una validacion cargo los datos del FORM
		if (getErrors(request).isEmpty()){
			DynaActionForm dyna = (DynaActionForm) form;

			dyna.set("id", paqueteDto.getId().toString());
			dyna.set("codigoActa", paqueteDto.getCodigoActa());
			dyna.set("observacion", paqueteDto.getObservacion());			
		}
		
		configurarTipoDeVisualizacionDeProyectos(request, paqueteDto);
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
	
	private void cambiarValorResultado(Collection<ProyectoFilaDTO> dto, HttpServletRequest request) {
		Iterator i = dto.iterator();
		while(i.hasNext()) {
			ProyectoFilaDTO proyectoFilaDTO = (ProyectoFilaDTO)i.next();
			
			PaqueteEvaluarResultadoProyectoDTO perpDTO = SessionHelper.getEvaluarPaqueteResultPry(request,proyectoFilaDTO.getIdProyecto());
			if(perpDTO != null && perpDTO.getResultado() != null)
				proyectoFilaDTO.setResultado(ResultadoEvaluacion.valueOf(perpDTO.getResultado()));
		}
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}
}