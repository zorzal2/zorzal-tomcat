package com.fontar.web.action.seguimientos.evaluaciones;

import static com.pragma.util.FormUtil.getStringValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
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
import com.pragma.util.FormUtil;

/**
 * Action para cargar el resultado de <code>Evalución de Seguimiento</code>.<br>
 * Este action muestra una visualización inicial de la evaluación
 * y tiene links para agregar/editar el resultado de la evaluación y
 * tabs para ingresar otros datos.<br> 
 * @author ssanchez
 */
public class CargarResultadoEvaluacionSeguimientoAction extends EvaluacionSeguimientoBaseTaskAction {

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		EvaluacionGeneralDTO evaluacionGeneralDTO = wfEvaluacionServicio.obtenerEvaluacionGeneral(idTaskInstance);
		request.setAttribute("evaluacion", evaluacionGeneralDTO);
		
		EvaluacionSeguimientoCabeceraDTO cabeceraDTO = wfEvaluacionServicio.obtenerCabeceraEvaluacionSeguimiento(idTaskInstance);
		request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
		
		setIdEvaluacion(request,evaluacionGeneralDTO.getId());
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

		//seteo un valor en sesión para poder ver los link de edición
		setActionAuthorize(request,true);
	}

	/**
	 * Persiste los datos ingresados por el usuario durante la edición.<br>
	 */
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		EvaluacionGeneralDTO evaluacionGeneralDTO = populateDTO(form);
		evaluacionGeneralDTO.setId(getIdEvaluacion(request));
		
		wfEvaluacionServicio.cargarResultadoEvaluacion(evaluacionGeneralDTO,evaluacionGeneralDTO.getAceptada(),idTaskInstance);
	}

	/**
	 * Obtiene los datos desde el formulario y los carga 
	 * en el dto <code>EvaluacionGeneralDTO</code>.
	 * @return EvaluacionGeneralDTO
	 * @throws Exception
	 */
	private EvaluacionGeneralDTO populateDTO(ActionForm form) throws Exception {

		EvaluacionGeneralDTO evaluacionGeneralDTO = new EvaluacionGeneralDTO();
		evaluacionGeneralDTO.setFundamentacion(FormUtil.getStringValue(form,"fundamentacion"));
		evaluacionGeneralDTO.setFecha(getStringValue(form,"fecha"));
		evaluacionGeneralDTO.setAceptada(FormUtil.getBooleanValue(form,"aceptada"));
		
		return evaluacionGeneralDTO;
	}	
}