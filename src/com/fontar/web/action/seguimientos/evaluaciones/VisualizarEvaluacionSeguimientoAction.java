package com.fontar.web.action.seguimientos.evaluaciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.Constant.MatrizPresupuestoTipo;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;

/**
 * Action para visualizar una <code>Evaluación de Seguimiento</code>.<br>
 * @author ssanchez
 */
public class VisualizarEvaluacionSeguimientoAction extends EvaluacionSeguimientoBaseAction {

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		ActionUtil.checkValidEncryptionContext(messages);		
		
		if (messages.isEmpty()) {
			Long idEvaluacion = getIdEvaluacion(request);
			
			EvaluacionGeneralDTO evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
	
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
			
			setIdSeguimiento(request,evaluacionGeneralDTO.getIdSeguimiento());

			//seteo en sesión si la evaluacion es de la clase tecnica o contable
			Boolean esTecnica = false;
			if (Boolean.valueOf(evaluacionGeneralDTO.getEsTecnica()) 
					|| Boolean.valueOf(evaluacionGeneralDTO.getEsVisitaTecnica())) esTecnica = true;
			
			setEsTecnica(request,esTecnica);

			
			Boolean esContable = false;
			if (Boolean.valueOf(evaluacionGeneralDTO.getEsContable()) 
					|| Boolean.valueOf(evaluacionGeneralDTO.getEsAuditoriaContable())) esContable = true;
			
			setEsContable(request,esContable);			


			String tipoMatriz = ((AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService")).getTipoMatrizPresupuesto(evaluacionGeneralDTO.getIdProyecto());

			SessionHelper.setEsANR(request, tipoMatriz.equals(MatrizPresupuestoTipo.ANR));
			SessionHelper.setEsARAI(request, tipoMatriz.equals(MatrizPresupuestoTipo.ARAI));
			SessionHelper.setEsCreditoFiscal(request, tipoMatriz.equals(MatrizPresupuestoTipo.CF));
			SessionHelper.setEsCreditoFiscalConsejerias(request, tipoMatriz.equals(MatrizPresupuestoTipo.CF_CONSEJERIAS));
			SessionHelper.setEsPatente(request, tipoMatriz.equals(MatrizPresupuestoTipo.PATENTE));
			
			//seteo un valor en sesión para No poder ver los link de edición
			setActionAuthorize(request,false);
		}

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward("invalid");
		}
		else {
			return mapping.findForward("success");
		}		
	}
}
