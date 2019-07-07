package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.Constant.InstanciaProyectoRaiz;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.VentanillaPermanenteDAO;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.assembler.ProyectoRaizAssembler;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoCargarDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.web.action.proyecto.ProyectoRaizBaseTaskAction;
import com.fontar.web.form.administracion.proyecto.CargarProyectoDynaForm;
import com.pragma.web.WebContextUtil;

//FIXME: SSanchez-no usar este action como ejemplo, es un action comun tranformado a un action
// de workflow, se reutilizo el código.
/**
 * Acción de workflow para cargar/crear un <code>Proyecto</code>
 * desde una <code>IdeaProyecto</code>.<br>
 * Esta acción permite cargar los datos de un <code>Proyecto</code>, 
 * finaliza el workflow de <code>IdeaProyecto</code> 
 * y crea una instancia del workflow de <code>Proyecto</code>.<br>
 * 
 * @author ssanchez
 */
public class IdeaProyectoCargarProyectoAction extends ProyectoRaizBaseTaskAction {
	
	/**
	 * Carga de dto's y variables necesarias para poder seleccionar
	 * el instrumento del <code>Proyecto</code>.<br>
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		setCollections(request);
		
		if (!new Boolean(request.getParameter("atras"))) {
			((DynaActionForm) form).getMap().clear();
		}

		String action = "/IdeaProyectoPitecProcesarCargarVentanilla";

		ProyectoRaizDTO proyectoRaiz = (ProyectoRaizDTO) wfProyectoRaizServicio.getProyectoRaizDTO(idTaskInstance, new ProyectoRaizAssembler());
		if (proyectoRaiz.getClase().equals(InstanciaProyectoRaiz.IDEA_PROYECTO)){
			action = "/IdeaProyectoProcesarCargarVentanilla";
		}
		IdeaProyectoDTO ideaProyectoDTO = (IdeaProyectoDTO) wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance, new IdeaProyectoDTOAssembler());
		BeanUtils.setProperty(form, "titulo", ideaProyectoDTO.getTitulo());
		BeanUtils.setProperty(form, "idEntidadBeneficiaria", ideaProyectoDTO.getIdEntidadBeneficiaria());
		BeanUtils.setProperty(form, "txtEntidadBeneficiaria", ideaProyectoDTO.getEntidadBeneficiaria());
		BeanUtils.setProperty(form, "duracion", ideaProyectoDTO.getDuracion());
		request.setAttribute("action", action);
		request.setAttribute("numeroOrigen", proyectoRaiz.getCodigo());
		
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance,new IdeaProyectoCabeceraAssembler());
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);

		CargarProyectoDynaForm cargarProyectoDynaForm = (CargarProyectoDynaForm) form;
		cargarProyectoDynaForm.set("id", proyectoRaiz.getId());
	}

	/**
	 * Carga de dto's y variables necesarias para podar ingresar datos
	 * del <code>Proyecto</code>.
	 */
	public ActionForward cargarProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);

		// GB / Guardamos el token porque pasa por el mismo "guardar" que para
		// la carga de Proyectos desde una "Presentación"
		saveToken(request);
		
		this.overrideForward(request);

		// cargo el id de la Idea Proyecto a la que se le cargará un Proyecto
		CargarProyectoDynaForm dyna = (CargarProyectoDynaForm) form;
		String idIdeaProyecto = request.getParameter("id");
		dyna.set("id", idIdeaProyecto);

		LocalizacionDTO localizacion = (LocalizacionDTO) dyna.get("localizacion");
		if (localizacion == null) {
			localizacion = new LocalizacionDTO();
		}
		dyna.set("localizacion", localizacion);

		EmpleoPermanenteDTO empleo = (EmpleoPermanenteDTO) dyna.get("empleo");
		if (empleo == null) {
			empleo = new EmpleoPermanenteDTO();
		}
		dyna.set("empleo", empleo);
		dyna.set("idInstrumento", request.getParameter("idInstrumento"));
		request.setAttribute("accion", BeanUtils.getProperty(form, "accion"));
		request.setAttribute("tipoProyecto", "IdeaProyecto");

				
		// Levanto la IdeaProyecto / IdeaProyectoPitec
		Long idTaskInstance = (Long) request.getSession().getAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK);
		ProyectoRaizDTO proyectoRaizDTO = (ProyectoRaizDTO) wfProyectoRaizServicio.getProyectoRaizDTO(idTaskInstance, new ProyectoRaizAssembler());
		
		request.setAttribute("entidadBeneficiariaOrigen",proyectoRaizDTO.getEntidadBeneficiaria());
		request.setAttribute("codigo", proyectoRaizDTO.getCodigo());				
		request.setAttribute("labelNumeroOrigen", "numeroIdeaProyecto");
		
		// cargo el campo entidadBeneficiaria si estamos modificando
		if (!BeanUtils.getProperty(form, "accion").equals("altaProyecto")) {
			request.setAttribute("idProyectoEntidadBeneficiaria", proyectoRaizDTO.getIdEntidadBeneficiaria());
			request.setAttribute("entidadBeneficiaria", proyectoRaizDTO.getEntidadBeneficiaria());
		}
		
		String submitAction = "/IdeaProyectoPitecProcesarCargarProyecto"; 
		String cancelAction = "/IdeaProyectoPitecCargarVentanilla";		
				
		if (proyectoRaizDTO.getClase().equals(Constant.InstanciaProyectoRaiz.IDEA_PROYECTO)) {
			submitAction = "/IdeaProyectoProcesarCargarProyecto";
			cancelAction = "/IdeaProyectoCargarVentanilla?atras=true";
			String numeroOrigen = proyectoRaizDTO.getCodigo();
			request.setAttribute("numeroOrigen", numeroOrigen);
			
		} else {
			String strCodigo = proyectoRaizDTO.getCodigo();
			request.setAttribute("numeroOrigen", strCodigo);
		}
		
		request.setAttribute("cancelAction", cancelAction);
		request.setAttribute("submitAction", submitAction);
		
		// cargo datos de cabecera
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO)wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance, new IdeaProyectoCabeceraAssembler());
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);

		return mapping.findForward("success");
	}

	/**
	 * Guarda los datos del <code>Proyecto</code>, crea una instancia del
	 * workflow de <code>Proyecto</code>, finaliza el workflow de 
	 * <code>IdeaProyecto</code>.<br>
	 */
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		CargarProyectoDynaForm proyectoDynaForm = (CargarProyectoDynaForm) form;

		ProyectoCargarDTO proyectoCargarDTO = new ProyectoCargarDTO();
		proyectoCargarDTO = proyectoDynaForm.getDTO(request);

		Boolean vieneDePresentacion = false;
		if (proyectoCargarDTO.getTipoProyecto() != null && proyectoCargarDTO.getTipoProyecto().equals(Constant.PreProyectos.PRESENTACION_CONVOCATORIA)) {
			vieneDePresentacion = true;
		}		
		wfIdeaProyectoServicio.cargarProyecto(proyectoCargarDTO,vieneDePresentacion,idTaskInstance);
	}	
	
	/**
	 * LLeno los combos para agregar y editar
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");

		Collection jurisdicciones = new ArrayList();
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));
		request.setAttribute("jurisdicciones", jurisdicciones);

		VentanillaPermanenteDAO ventanillaPermanenteDAO = (VentanillaPermanenteDAO) WebContextUtil.getBeanFactory().getBean("ventanillaPermanenteDao");
		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		EntidadBeneficiariaDAO entidadBeneficiariaDAO = (EntidadBeneficiariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBeneficiariaDao");
		PersonaDAO personasDAO = (PersonaDAO) WebContextUtil.getBeanFactory().getBean("personaDao");
		CiiuDAO ciiuDAO = (CiiuDAO) WebContextUtil.getBeanFactory().getBean("ciiuDao");
		EntidadBancariaDAO entidadBancariaDAO = (EntidadBancariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBancariaDao");

		Collection ventanillaPermanenteList = new ArrayList();
		ventanillaPermanenteList.addAll(collectionHandler.getVentanillas(ventanillaPermanenteDAO));

		Collection tipoProyectoList = new ArrayList();
		tipoProyectoList.addAll(collectionHandler.getTiposProyectos(tipoProyectoDAO)); // getTipoProyecto(tipoProyectosDAO);

		Collection entidadBeneficiariaList = new ArrayList();
		entidadBeneficiariaList.addAll(collectionHandler.getEntidadesBeneficiarias(entidadBeneficiariaDAO));

		Collection personasList = new ArrayList();
		personasList.addAll(collectionHandler.getPersonas(personasDAO));

		Collection ciiusList = new ArrayList();
		ciiusList.addAll(collectionHandler.getCiius(ciiuDAO));

		Collection entidadBancariaList = new ArrayList();
		entidadBancariaList.addAll(collectionHandler.getEntidadesBancarias(entidadBancariaDAO));

		request.setAttribute("ventanillasPermanentes", ventanillaPermanenteList);
		request.setAttribute("tiposProyectos", tipoProyectoList);
		request.setAttribute("entidadesBeneficiarias", entidadBeneficiariaList);
		request.setAttribute("personas", personasList);
		request.setAttribute("ciius", ciiusList);
		request.setAttribute("entidadesBancarias", entidadBancariaList);
	}
	
	public ActionForward doNext(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("next");
	}

	public ActionForward doFinish(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("finish");
	}
}
