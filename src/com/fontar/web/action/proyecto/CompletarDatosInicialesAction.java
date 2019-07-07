package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.CompletarDatosInicialesDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.web.form.administracion.proyecto.CompletarDatosInicialesDynaForm;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.Utils;

/**
 * Action para <i>Cargar los Datos Iniciales</i>
 * a un <code>Proyecto Histórico</code>.<br>
 * @author ssanchez
 */
public class CompletarDatosInicialesAction extends ProyectoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ProyectoCabeceraDTO cabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance,new ProyectoCabeceraAssembler());
		request.setAttribute(PROYECTO, cabeceraDTO);
		((CompletarDatosInicialesDynaForm)form).setAplicaCargaAlicuotaCF(cabeceraDTO.getAplicaCargaAlicuotaCF());
		setDatosIniciales(request,idTaskInstance);
		ActionMessages infoMessages = getMessages(request);
		ActionUtil.alertForEncription(request, infoMessages);
		saveMessages(request, infoMessages);
	}
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		CompletarDatosInicialesDTO datosInicialesDTO = populateData(form);
		
		wfProyectoServicio.completarDatosIniciales(idTaskInstance,datosInicialesDTO);
	}
	
	private CompletarDatosInicialesDTO populateData(ActionForm form) throws Exception {
		
		CompletarDatosInicialesDTO datosInicialesDTO = new CompletarDatosInicialesDTO();
		
		String codigoRecomendacionFinal = FormUtil.getStringValue(form,"recomendacionFinal");
		datosInicialesDTO.setRecomendacionFinal(Recomendacion.valueOf(codigoRecomendacionFinal));
		datosInicialesDTO.setFechaResolucion(FormUtil.getDateValue(form,"fechaResolucion"));
		datosInicialesDTO.setFechaFirmaDeContrato(FormUtil.getDateValue(form,"fechaFirmaDeContrato"));
		datosInicialesDTO.setCodigoResolucion(FormUtil.getStringValue(form,"codigoResolucion"));
		datosInicialesDTO.setPorcentajeAlicuotaAdjudicada(FormUtil.getBigDecimalValue(form,"porcentajeAlicuotaAdjudicada"));
		datosInicialesDTO.setPorcentajeAlicuotaSolicitada(FormUtil.getBigDecimalValue(form,"porcentajeAlicuotaSolicitada"));
		datosInicialesDTO.setMontoFontarRrhh(Utils.nvl(FormUtil.getBigDecimalValue(form,"montoFontarRrhh"), BigDecimal.ZERO));
		datosInicialesDTO.setMontoContraparteRrhh(FormUtil.getBigDecimalValue(form,"montoContraparteRrhh"));
		datosInicialesDTO.setMontoFontarBienCapital(Utils.nvl(FormUtil.getBigDecimalValue(form,"montoFontarBienCapital"), BigDecimal.ZERO));
		datosInicialesDTO.setMontoContraparteBienCapital(FormUtil.getBigDecimalValue(form,"montoContraparteBienCapital"));
		datosInicialesDTO.setMontoFontarConsultoriaServicio(Utils.nvl(FormUtil.getBigDecimalValue(form,"montoFontarConsultoriaServicio"), BigDecimal.ZERO));
		datosInicialesDTO.setMontoContraparteConsultoriaServicio(FormUtil.getBigDecimalValue(form,"montoContraparteConsultoriaServicio"));
		datosInicialesDTO.setMontoFontarMaterialInsumo(Utils.nvl(FormUtil.getBigDecimalValue(form,"montoFontarMaterialInsumo"), BigDecimal.ZERO));
		datosInicialesDTO.setMontoContraparteMaterialInsumo(FormUtil.getBigDecimalValue(form,"montoContraparteMaterialInsumo"));
		datosInicialesDTO.setMontoFontarOtro(Utils.nvl(FormUtil.getBigDecimalValue(form,"montoFontarOtro"), BigDecimal.ZERO));
		datosInicialesDTO.setMontoContraparteOtro(FormUtil.getBigDecimalValue(form,"montoContraparteOtro"));
		datosInicialesDTO.setObservacion(FormUtil.getStringValue(form,"observacion"));
		
		return datosInicialesDTO;
	}
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
		super.validateEjecutarYTerminarTarea(mapping, form, request, response,
				messages, idTaskInstance);
	}

	private void setDatosIniciales(HttpServletRequest request,Long idTaskInstance){
		
		Collection recomendacionesFinales = collectionHandler.getRecomendacionPostAprobacion();
		request.setAttribute("recomendacionesFinales",recomendacionesFinales);
		
		ProyectoBean proyecto = wfProyectoServicio.obtenerProyectoBean(idTaskInstance);
		Boolean aplicaCargaDeAlicuotaCF = proyecto.getInstrumento().aplicaCargaAlicuotaCF(); 
		request.setAttribute("permiteAlicuotaAdjudicada",aplicaCargaDeAlicuotaCF);
		request.setAttribute("aplicaCargaDeAlicuotaCF",aplicaCargaDeAlicuotaCF);
	}
}