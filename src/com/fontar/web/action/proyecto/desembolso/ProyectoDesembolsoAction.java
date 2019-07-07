package com.fontar.web.action.proyecto.desembolso;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.proyecto.desembolso.MontoAutorizadoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.MontoDesembolsadoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.MontoElegidoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.MontoPrevistoSuperaMontoDelBeneficio;
import com.fontar.bus.api.proyecto.desembolso.ProyectoDesembolsoService;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ProyectoDesembolsoAction extends BaseMappingDispatchAction {

	private ProyectoDesembolsoService proyectoDesembolsoService;

	protected WFProyectoServicio wfProyectoServicio;
	
	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	public ActionForward agregar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		Long idProyecto = null;
		
		idProyecto = SessionHelper.getIdProyecto(request);
		dynaForm.set("idProyecto", idProyecto);
		
		//Agrego un mensaje de advertencia si no hay contraseña de encriptacion
		if (!ActionUtil.isEncryptionContextAvailable()){
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.debeIngresarContraseniaParaContinuar");
			saveMessages(request, infoMessages);
		}
		return mapping.findForward("success");
	}

	public ActionForward editar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
//		Long idProyecto = null;
		
//		idProyecto = SessionHelper.getIdProyecto(request);
		
		ProyectoDesembolsoDTO proyectoDesembolso = proyectoDesembolsoService.load(id);
		
//		dynaForm.set("idProyecto", idProyecto);
		
		if(StringUtil.isEmpty((String)dynaForm.get("concepto"))) dynaForm.set("concepto", proyectoDesembolso.getConcepto());
		if(StringUtil.isEmpty((String)dynaForm.get("montoOriginal"))) dynaForm.set("montoOriginal", proyectoDesembolso.getMontoOriginal().toString());
		if(StringUtil.isEmpty((String)dynaForm.get("plazo"))) dynaForm.set("plazo", proyectoDesembolso.getPlazo().toString());
		dynaForm.set("idProyecto", proyectoDesembolso.getIdProyecto());
		if(StringUtil.isEmpty((String)dynaForm.get("objetivo"))) dynaForm.set("objetivo", proyectoDesembolso.getObjetivo());
		
		//Agrego un mensaje de advertencia si no hay contraseña de encriptacion
		if (!ActionUtil.isEncryptionContextAvailable()){
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.debeIngresarContraseniaParaContinuar");
			saveMessages(request, infoMessages);
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		//Debe haber clave de encriptacion
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if (!messages.isEmpty()){
			saveErrors(request,messages);
			return mapping.getInputForward();
		}
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		ProyectoDesembolsoDTO proyectoDesembolso = new ProyectoDesembolsoDTO();
		proyectoDesembolso.setId((Long) dynaForm.get("id"));
		proyectoDesembolso.setConcepto(dynaForm.getString("concepto"));
		proyectoDesembolso.setMontoOriginal(new BigDecimal(dynaForm.getString("montoOriginal")));
		proyectoDesembolso.setObjetivo(dynaForm.getString("objetivo"));
		proyectoDesembolso.setPlazo(new Integer(dynaForm.getString("plazo")));

		try {
			proyectoDesembolsoService.update(proyectoDesembolso);
		}
		catch (MontoElegidoSuperaMontoDelBeneficio e) {
			addError(
					messages,
					"app.montoPrevistoSuperaMontoDelBeneficio", 
					StringUtil.formatMoneyForPresentation(e.getMontoElegido()), 
					StringUtil.formatMoneyForPresentation(e.getMontoBeneficio())
				);
			saveErrors(request,messages);
			//Vuelvo al formulario
			return mapping.getInputForward();
		}


		return mapping.findForward("success");
	}
	
	public ActionForward registrarPago(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		
		//Debe haber clave de encriptacion
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if (!messages.isEmpty()){
			saveErrors(request,messages);
			return mapping.getInputForward();
		}

		
		DynaActionForm dynaForm = (DynaActionForm) form;
		Long idDesembolso = (Long) dynaForm.get("id");
		BigDecimal monto = new BigDecimal(dynaForm.getString("monto"));
		Date fecha = DateTimeUtil.getDate(dynaForm.getString("fechaPago"));

		try {
			proyectoDesembolsoService.registrarPago(idDesembolso, monto, fecha);
		} catch(MontoDesembolsadoSuperaMontoDelBeneficio e) {
			addError(
					messages,
					"app.montoDesembolsadoSuperaMontoDelBeneficio", 
					StringUtil.formatMoneyForPresentation(e.getMontoElegido()), 
					StringUtil.formatMoneyForPresentation(e.getMontoBeneficio())
				);
			saveErrors(request,messages);
			//Vuelvo al formulario
			return mapping.getInputForward();
		}
		
		return mapping.findForward("success");
	}

	public ActionForward crear(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		//Debe haber clave de encriptacion
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if (!messages.isEmpty()){
			saveErrors(request,messages);
			return mapping.getInputForward();
		}
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		ProyectoDesembolsoDTO proyectoDesembolso = new ProyectoDesembolsoDTO();
		proyectoDesembolso.setConcepto(dynaForm.getString("concepto"));
		proyectoDesembolso.setIdProyecto((Long) dynaForm.get("idProyecto"));
		proyectoDesembolso.setMontoOriginal(new BigDecimal(dynaForm.getString("montoOriginal")));
		proyectoDesembolso.setObjetivo(dynaForm.getString("objetivo"));
		proyectoDesembolso.setPlazo(new Integer(dynaForm.getString("plazo")));
		
		try {
			proyectoDesembolsoService.create(proyectoDesembolso);
		} catch(MontoPrevistoSuperaMontoDelBeneficio e) {
			addError(
					messages,
					"app.montoPrevistoSuperaMontoDelBeneficio", 
					StringUtil.formatMoneyForPresentation(e.getMontoElegido()), 
					StringUtil.formatMoneyForPresentation(e.getMontoBeneficio())
				);
			saveErrors(request,messages);
			//Vuelvo al formulario
			return mapping.getInputForward();
		}
		
		return mapping.findForward("success");
	}

	public ActionForward inventario(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		Long idProyecto = null;
		
		idProyecto = SessionHelper.getIdProyecto(request);
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
		CronogramaDeDesembolsosDTO cronograma = proyectoDesembolsoService.obtenerCronogramaDelProyecto(idProyecto);

		request.setAttribute("cronograma", cronograma);
		
		//Define si ya tiene presupuesto cargado.
		request.setAttribute("presupuesto",null);
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean bean = proyectoDAO.read(idProyecto);
		ProyectoPresupuestoBean presupuesto = bean.getProyectoPresupuesto();
		if (presupuesto!=null) {
			request.setAttribute("presupuesto","si");
		}
		return mapping.findForward("success");
	}

	public ActionForward eliminar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id =  (Long) dynaForm.get("id");
		
		proyectoDesembolsoService.delete(id);
		
		return mapping.findForward("success");
	}
	
	public ActionForward pagar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		ProyectoDesembolsoDTO proyectoDesembolso = proyectoDesembolsoService.load(id);
		//ACA DEBERIA SUGERIR EL CALCULO DEL MONTO EN FUNCION DE LAS RENDICIONES
		if(StringUtil.isEmpty(dynaForm.getString("monto"))) dynaForm.set("monto", proyectoDesembolso.getMontoAutorizado().toString());
		
		//Agrego un mensaje de advertencia si no hay contraseña de encriptacion
		if (!ActionUtil.isEncryptionContextAvailable()){
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.debeIngresarContraseniaParaContinuar");
			saveMessages(request, infoMessages);
		}
		
		return mapping.findForward("success");
	}

	public ActionForward pagoDeAnticipo(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		ProyectoDesembolsoDTO proyectoDesembolso = proyectoDesembolsoService.load(id);
		if(StringUtil.isEmpty(dynaForm.getString("monto"))) 
			dynaForm.set("monto", 
					proyectoDesembolso.getMontoDesembolsado()==null? 
							proyectoDesembolso.getMontoOriginal().toString() : 
							proyectoDesembolso.getMontoDesembolsado().toString());
		if(StringUtil.isEmpty(dynaForm.getString("fechaPago")) && proyectoDesembolso.getFechaPago()!=null) {
			dynaForm.set("fechaPago", StringUtil.formatDate(proyectoDesembolso.getFechaPago()));
		}
		//Agrego un mensaje de advertencia si no hay contraseña de encriptacion
		if (!ActionUtil.isEncryptionContextAvailable()){
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.debeIngresarContraseniaParaContinuar");
			saveMessages(request, infoMessages);
		}
		
		return mapping.findForward("success");
	}
	public ActionForward pagarAnticipo(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		
		//Debe haber clave de encriptacion
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if (!messages.isEmpty()){
			saveErrors(request,messages);
			return mapping.getInputForward();
		}

		DynaActionForm dynaForm = (DynaActionForm) form;
		Long idDesembolso = (Long) dynaForm.get("id");
		BigDecimal monto = new BigDecimal(dynaForm.getString("monto"));
		Date fecha = DateTimeUtil.getDate(dynaForm.getString("fechaPago"));

		try {
			proyectoDesembolsoService.pagarAnticipo(idDesembolso, monto, fecha);
						
		} catch(MontoDesembolsadoSuperaMontoDelBeneficio e) {
			addError(
					messages,
					"app.montoDesembolsadoSuperaMontoDelBeneficio", 
					StringUtil.formatMoneyForPresentation(e.getMontoElegido()), 
					StringUtil.formatMoneyForPresentation(e.getMontoBeneficio())
				);
			saveErrors(request,messages);
			//Vuelvo al formulario
			return mapping.getInputForward();
		}
		
		return mapping.findForward("success");
	}

	public ActionForward ingresarAutorizacion(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		ProyectoDesembolsoDTO proyectoDesembolso = proyectoDesembolsoService.load(id);
		if(StringUtil.isEmpty(dynaForm.getString("monto"))) {
			BigDecimal sugerido = proyectoDesembolso.getMontoAutorizado();
			if(sugerido==null) sugerido = proyectoDesembolso.getMontoOriginal();
			
			dynaForm.set("monto", sugerido.toString());
		}
		
		//Agrego un mensaje de advertencia si no hay contraseña de encriptacion
		if (!ActionUtil.isEncryptionContextAvailable()){
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.debeIngresarContraseniaParaContinuar");
			saveMessages(request, infoMessages);
		}
		
		return mapping.findForward("success");
	}

	public ActionForward autorizar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		
		//Debe haber clave de encriptacion
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if (!messages.isEmpty()){
			saveErrors(request,messages);
			return mapping.getInputForward();
		}

		DynaActionForm dynaForm = (DynaActionForm) form;
		Long idDesembolso = (Long) dynaForm.get("id");
		Long idSeguimiento = (Long) dynaForm.get("idSeguimientoDeAutorizacion");
		BigDecimal monto = new BigDecimal(dynaForm.getString("monto"));

		try {
			proyectoDesembolsoService.autorizarPago(idDesembolso, monto, idSeguimiento);
		} catch(MontoAutorizadoSuperaMontoDelBeneficio e) {
			addError(
					messages,
					"app.montoDesembolsadoSuperaMontoDelBeneficio", 
					StringUtil.formatMoneyForPresentation(e.getMontoElegido()), 
					StringUtil.formatMoneyForPresentation(e.getMontoBeneficio())
				);
			saveErrors(request,messages);
			//Vuelvo al formulario
			return mapping.getInputForward();
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward volver(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		//Vuelvo al inventario de desembolsos
		return NavigationManager.getSavedLocation(request, "proyecto.desembolso.returnAddress");
	}

	public ProyectoDesembolsoService getProyectoDesembolsoService() {
		return proyectoDesembolsoService;
	}

	public void setProyectoDesembolsoService(ProyectoDesembolsoService proyectoDesembolsoService) {
		this.proyectoDesembolsoService = proyectoDesembolsoService;
	}
}
