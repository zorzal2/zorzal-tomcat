package com.fontar.web.action.instrumento.ventanilla.ventanilla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.CargarProyectoExistenteException;
import com.fontar.bus.impl.ProyectoConFinanciamientoBancarioSinEntidadException;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.domain.dto.ProyectoCargarDTO;
import com.fontar.web.form.administracion.proyecto.CargarProyectoDynaForm;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericAction;

/**
 * 
 * @author ssanchez
 */
public class VentanillaPermanenteAction extends GenericAction {
	
	
	WFProyectoServicio proyectoServicio;
	
	CollectionHandler collectionHandler = new CollectionHandler();

	

	
	public WFProyectoServicio getProyectoServicio() {
		return proyectoServicio;
	}


	public void setProyectoServicio(WFProyectoServicio proyectoServicio) {
		this.proyectoServicio = proyectoServicio;
	}


	/** 
	 * Proyectos CAE
	 * Da de alta un proyecto CAE y comienza el workflow de proyecto 
	 **/
	public ActionForward cargarProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CargarProyectoDynaForm cargarProyectoDynaForm = (CargarProyectoDynaForm) form;
		ProyectoCargarDTO dto = null;
		try {
			dto = cargarProyectoDynaForm.getDTO(request);
		} catch (CargarProyectoExistenteException e) {
			ActionMessages messages = this.getErrors(request);
			this.addError(messages, "app.proyecto.existeProyecto");
			this.saveErrors(request, messages);
			return mapping.getInputForward();
		} catch (ProyectoConFinanciamientoBancarioSinEntidadException e) {
			ActionMessages messages = this.getErrors(request);
			addError(messages, "app.proyecto.entidadFinanciera");
			this.saveErrors(request, messages);
			return mapping.getInputForward();
		}
		this.proyectoServicio.cargarProyecto(dto,false,dto.getIdInstrumento(),null,null);
		
		return mapping.findForward("success");
	}
	
	
	/** Proyectos CAE **/
	public ActionForward cargaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			ActionMessages errors = this.getErrors(request);
			PragmaDynaValidatorForm dynaForm = (PragmaDynaValidatorForm) form;
			
			if(errors.isEmpty()){
				//Ventanilla
				dynaForm.set("idInstrumento", request.getParameter("id"));
			}
			
			//No esta asociado a un proyecto de origen
			dynaForm.set("id", null);
			
			//Por ser CAE requiere que el usuario complete la jurisdiccion
			dynaForm.set("requiereJurisdiccion", true);
			request.setAttribute("requiereJurisdiccion", true);
			
			//Collections
			TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
			JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
			request.setAttribute("jurisdicciones", collectionHandler.getJurisdicciones(jurisdiccionDAO));
			request.setAttribute("tiposProyectos", collectionHandler.getTiposProyectos(tipoProyectoDAO));
			

			request.setAttribute("accion", "altaProyecto");	
			request.setAttribute("cancelAction", "/VentanillaPermanenteInventario");
			request.setAttribute("submitAction","/VentanillaPermanenteCargarProyecto");
		
			return mapping.findForward("success");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	public void asignarMonto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// return mapping.findForward("asignarMonto");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */

	public void guardarMonto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// return mapping.findForward("editar");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	public void distribuir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// return mapping.findForward("distribuir");
	}

	/**
	 * Documentar el objetivo del metodo!
	 */
	public void verHistorial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// return mapping.findForward("verHistorial");
	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	}
}