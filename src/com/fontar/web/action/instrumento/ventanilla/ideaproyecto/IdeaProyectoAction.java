package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static org.apache.commons.beanutils.BeanUtils.getProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.VentanillaPermanenteDAO;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericAction;

/**
 * 
 * @author ssanchez
 * @version 1.02, 11/01/07
 */
public class IdeaProyectoAction extends GenericAction {

	private WFIdeaProyectoServicio wfIdeaProyectoServicio;

	private AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;

	@Override
	/**
	 * Método que muestra pantalla para agregar una idea proyecto Previamente
	 * llena los combos con datos
	 */
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
	}
	
	@Override
	/**
	 * Método que muestra pantalla para editar una Idea Proyecto Previamente
	 * carga los datos de la idea proyecto en un bean
	 */
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.dataEditar(mapping, form, request, response);
		setCollections(request);

		// levanto el codigo de la idea proyecto a editar
		String idIdeaProyecto = request.getParameter("id");

		IdeaProyectoDTO ideaProyectoDTO = (IdeaProyectoDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(new Long(idIdeaProyecto), new IdeaProyectoDTOAssembler());
		BeanUtils.copyProperties(form, ideaProyectoDTO);
		request.setAttribute(CabeceraAttribute.IDEA_PROYECTO, ideaProyectoDTO);

		setSelectors((DynaActionForm) form, request);
	}

	@Override
	/**
	 * Método que guarda los datos de una Idea Proyecto, ya sea se esté
	 * agregando o editando una Previo carga los beans correspondientes con los
	 * datos enviádos desde la pantalla
	 */
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Levanto datos del request
		// ver : if (BeanUtils.getProperty(form,"id").equals("")) {

		String id = request.getParameter("id");

		if (id.equals("")) {
			cargarIdeaProyecto(mapping, form, request, response);
		}
		else {
			modificarIdeaProyecto(id, mapping, form, request, response);
		}
	}

	private void modificarIdeaProyecto(String id, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Levanto datos del form
		Date fechaIngreso = FormUtil.getDateValue(form, "fechaIngreso");
		Long idTipoProyecto = FormUtil.getLongValue(form, "idTipoProyecto");
		String titulo = BeanUtils.getProperty(form, "titulo");
		String resumen = BeanUtils.getProperty(form, "resumen");
		String instrumentoSolicitado = BeanUtils.getProperty(form, "instrumentoSolicitado");
		BigDecimal montoTotal = FormUtil.getBigDecimalValue(form, "montoTotal");
		BigDecimal montoSolicitado = FormUtil.getBigDecimalValue(form, "montoSolicitado");
		Integer duracion = FormUtil.getIntegerValue(form, "duracion");
		Long idJurisdiccion = FormUtil.getLongValue(form, "idJurisdiccion");
		Long idEntidadBeneficiaria = FormUtil.getLongValue(form, "idEntidadBeneficiaria");
		String observaciones = getProperty(form, "observaciones");

		// Construyo un DTO con los datos del form
		IdeaProyectoDTO datosIdeaProyecto = new IdeaProyectoDTO();

		//Personas
		Long idPersonaDirector = FormUtil.getLongValue(form, "idPersonaDirector");
		Long idPersonaRepresentante = FormUtil.getLongValue(form, "idPersonaRepresentante");
		Long idPersonaLegal = FormUtil.getLongValue(form, "idPersonaLegal");
		datosIdeaProyecto.setIdPersonaDirector(idPersonaDirector);
		datosIdeaProyecto.setIdPersonaRepresentante(idPersonaRepresentante);
		datosIdeaProyecto.setIdPersonaLegal(idPersonaLegal);
		
		datosIdeaProyecto.setFechaIngreso(fechaIngreso);
		datosIdeaProyecto.setIdEntidadBeneficiaria(idEntidadBeneficiaria);
		datosIdeaProyecto.setIdJurisdiccion(idJurisdiccion);
		datosIdeaProyecto.setIdTipoProyecto(idTipoProyecto);
		datosIdeaProyecto.setInstrumentoSolicitado(instrumentoSolicitado);
		datosIdeaProyecto.setMontoSolicitado(montoSolicitado);
		datosIdeaProyecto.setMontoTotal(montoTotal);
		datosIdeaProyecto.setDuracion(duracion);
		datosIdeaProyecto.setObservaciones(observaciones);
		datosIdeaProyecto.setResumen(resumen);
		datosIdeaProyecto.setTitulo(titulo);

		// Llamo al servicio de WF
		wfIdeaProyectoServicio.modificarIdeaProyecto(id, datosIdeaProyecto);

		// limpio el formulario para que elimine los datos en session
		((DynaActionForm) form).getMap().clear();
	}

	private void cargarIdeaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Levanto datos del form
		Date fechaIngreso = FormUtil.getDateValue(form, "fechaIngreso");
		Long idTipoProyecto = FormUtil.getLongValue(form, "idTipoProyecto");
		String titulo = BeanUtils.getProperty(form, "titulo");
		String resumen = BeanUtils.getProperty(form, "resumen");
		String instrumentoSolicitado = BeanUtils.getProperty(form, "instrumentoSolicitado");
		BigDecimal montoTotal = FormUtil.getBigDecimalValue(form, "montoTotal");
		BigDecimal montoSolicitado = FormUtil.getBigDecimalValue(form, "montoSolicitado");
		Integer duracion = FormUtil.getIntegerValue(form, "duracion");
		Long idJurisdiccion = FormUtil.getLongValue(form, "idJurisdiccion");
		Long idEntidadBeneficiaria = FormUtil.getLongValue(form, "idEntidadBeneficiaria");
		String observaciones = getProperty(form, "observaciones");

		IdeaProyectoDTO datosIdeaProyecto = new IdeaProyectoDTO();

		//Personas
		Long idPersonaDirector = FormUtil.getLongValue(form, "idPersonaDirector");
		Long idPersonaRepresentante = FormUtil.getLongValue(form, "idPersonaRepresentante");
		Long idPersonaLegal = FormUtil.getLongValue(form, "idPersonaLegal");
		datosIdeaProyecto.setIdPersonaDirector(idPersonaDirector);
		datosIdeaProyecto.setIdPersonaRepresentante(idPersonaRepresentante);
		datosIdeaProyecto.setIdPersonaLegal(idPersonaLegal);
		
		datosIdeaProyecto.setFechaIngreso(fechaIngreso);
		datosIdeaProyecto.setIdEntidadBeneficiaria(idEntidadBeneficiaria);
		datosIdeaProyecto.setIdJurisdiccion(idJurisdiccion);
		datosIdeaProyecto.setIdTipoProyecto(idTipoProyecto);
		datosIdeaProyecto.setInstrumentoSolicitado(instrumentoSolicitado);
		datosIdeaProyecto.setMontoSolicitado(montoSolicitado);
		datosIdeaProyecto.setMontoTotal(montoTotal);
		datosIdeaProyecto.setDuracion(duracion);
		datosIdeaProyecto.setObservaciones(observaciones);
		datosIdeaProyecto.setResumen(resumen);
		datosIdeaProyecto.setTitulo(titulo);

		// Llamo al servicio de WF
		wfIdeaProyectoServicio.cargarIdeaProyecto(datosIdeaProyecto);

		// limpio el formulario para que elimine los datos en session
		((DynaActionForm) form).getMap().clear();
	}

	@Override
	/**
	 * Elimina una idea proyecto
	 */
	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) getServicio().load(new Long(id));

		getServicio().delete(ideaProyectoBean);
	}

	/**
	 * LLeno los combos para agregar y editar
	 * @param request: usado para setear collections
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection tiposProyectos = new ArrayList();
		Collection ventanillasPermanentes = new ArrayList();
		Collection jurisdicciones = new ArrayList();

		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		VentanillaPermanenteDAO ventanillaPermanenteDAO = (VentanillaPermanenteDAO) WebContextUtil.getBeanFactory().getBean("ventanillaPermanenteDao");
		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");

		tiposProyectos.addAll(collectionHandler.getTiposProyectos(tipoProyectoDAO));
		ventanillasPermanentes.addAll(collectionHandler.getVentanillas(ventanillaPermanenteDAO));
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));

		request.setAttribute("tiposProyectos", tiposProyectos);
		request.setAttribute("ventanillasPermanentes", ventanillasPermanentes);
		request.setAttribute("jurisdicciones", jurisdicciones);
	}

	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {
		EntidadBeneficiariaDAO entidadesBeneficiariasDao = (EntidadBeneficiariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBeneficiariaDao");
		String idEntidadBeneficiaria = BeanUtils.getProperty(form, "idEntidadBeneficiaria");

		if (idEntidadBeneficiaria != null && !idEntidadBeneficiaria.equals("")) {
			EntidadBeneficiariaBean entidadBeneficiariaBean = new EntidadBeneficiariaBean();
			entidadBeneficiariaBean = (EntidadBeneficiariaBean) entidadesBeneficiariasDao.read(new Long(idEntidadBeneficiaria));
			BeanUtils.copyProperty(form, "idEntidadBeneficiaria", entidadBeneficiariaBean.getId());
			BeanUtils.copyProperty(form, "txtEntidadBeneficiaria", entidadBeneficiariaBean.getDenominacion());
		}
		//TODO Hay que Completar esto?

	}

	public void setWfIdeaProyectoServicio(WFIdeaProyectoServicio wfIdeaProyectoServicio) {
		this.wfIdeaProyectoServicio = wfIdeaProyectoServicio;
	}

	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}

	@Override
	protected void validateEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.validateEditar(mapping, form, request, response);
		ActionMessages messages =  this.getMessages( request );
		ActionUtil.alertForEncription(request, messages);
		saveMessages(request, messages);
	}
	
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);
		ActionUtil.checkValidEncryptionContext(messages);
	}

	protected ActionForward forwardEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages =  this.getErrors( request );
		return messages.isEmpty() ? mapping.findForward("success") : mapping.findForward("invalid");
	}

	@Override
	protected void validateAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateAgregar(mapping, form, request, response, messages);
		ActionMessages infoMessages = getMessages(request);
		ActionUtil.alertForEncription(request, infoMessages);
		saveMessages(request, infoMessages);
	}

}
