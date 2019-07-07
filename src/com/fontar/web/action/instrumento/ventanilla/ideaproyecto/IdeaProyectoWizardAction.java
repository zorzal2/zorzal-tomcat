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
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.VentanillaPermanenteDAO;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.util.Util;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericWizardAction;

/**
 * 
 * @author ssanchez
 * @version 1.01, 11/01/07 última modificación
 */
public class IdeaProyectoWizardAction extends GenericWizardAction {

	AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;

	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}

	/**
	 * Muestra el formulario para cargar una ventanilla de la idea proyecto
	 */
	public ActionForward cargarVentanilla(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		setCollections(request);
		
		// si el id no viene como parametro podría venir desde
		// cargarProyecto->debe levantar datos de session
		String idIdeaProyecto = request.getParameter("id");
		if (!Util.isBlank(idIdeaProyecto)) {
			// Limpio el formulario para que no muestre los datos que podrian
			// estar en session
			((DynaActionForm) form).getMap().clear();
		}
		else if ((idIdeaProyecto = ((DynaActionForm) form).get("id").toString()) == null
				|| ((DynaActionForm) form).get("id").equals("")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}		
		
		// Levanto la Idea Proyecto / Idea Proyecto Pitec 
		ProyectoRaizBean ideaProyectoBean = (ProyectoRaizBean) getServicio().load(ProyectoRaizBean.class, new Long(idIdeaProyecto));
		
		String action = "/IdeaProyectoProcesarCargarVentanilla";
		
		if (ideaProyectoBean instanceof IdeaProyectoPitecBean) {		
			action = "/IdeaProyectoPitecProcesarCargarVentanilla";			
		}
		
		request.setAttribute("action", action);
		
		request.setAttribute("numeroOrigen", ideaProyectoBean.getCodigo());

		// cargo datos de cabecera
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(new Long(idIdeaProyecto), new IdeaProyectoCabeceraAssembler());
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);

		DynaActionForm dyna = (DynaActionForm) form;
		dyna.set("id", idIdeaProyecto);

		return mapping.findForward("success");
	}
		
	/**
	 * Llama a la pantalla para cargar un proyecto desde una idea proyecto
	 */
	public ActionForward cargarProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);

		// GB / Guardamos el token porque pasa por el mismo "guardar" que para
		// la carga de Proyectos desde una "Presentación"
		saveToken(request);

		// cargo el id de la Idea Proyecto a la que se le cargará un Proyecto
		DynaActionForm dyna = (DynaActionForm) form;
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

		// cargo del id de Ventanilla
		dyna.set("idInstrumento", request.getParameter("idInstrumento"));

		// seteo la accion que se esta llevando a cabo
		request.setAttribute("accion", BeanUtils.getProperty(form, "accion"));

		// seteo la entidad que genera el proyecto
		request.setAttribute("tipoProyecto", "IdeaProyecto");

				
		// Levanto la IdeaProyecto / IdeaProyectoPitec
		ProyectoRaizBean ideaProyectoBean = (ProyectoRaizBean) getServicio().load(ProyectoRaizBean.class, new Long(idIdeaProyecto));		

		// cargo el campo entidadBeneficiariaOrigen
		Long idDatos = ideaProyectoBean.getIdDatos();
		ProyectoDatosBean proyectoDatosBean = (ProyectoDatosBean) getServicio().load(ProyectoDatosBean.class, idDatos);
		EntidadBeneficiariaBean entidadBeneficiaria = (EntidadBeneficiariaBean) getServicio().load(EntidadBeneficiariaBean.class, new Long(proyectoDatosBean.getIdEntidadBeneficiaria()));
		request.setAttribute("entidadBeneficiariaOrigen", entidadBeneficiaria.getDenominacion());
		
		request.setAttribute("codigo", ideaProyectoBean.getCodigo());				
		request.setAttribute("labelNumeroOrigen", "numeroIdeaProyecto");
		
		// cargo el campo entidadBeneficiaria si estamos modificando
		if (!BeanUtils.getProperty(form, "accion").equals("altaProyecto")) {
			request.setAttribute("idProyectoEntidadBeneficiaria", proyectoDatosBean.getIdEntidadBeneficiaria().toString());
			request.setAttribute("entidadBeneficiaria", entidadBeneficiaria.getDenominacion());
		}
		
		String submitAction = "/IdeaProyectoPitecProcesarCargarProyecto"; 
		String cancelAction = "/IdeaProyectoPitecCargarVentanilla";		
				
		if (ideaProyectoBean instanceof IdeaProyectoBean) {			
			submitAction = "/IdeaProyectoProcesarCargarProyecto";
			cancelAction = "/IdeaProyectoCargarVentanilla";
			Long numeroOrigen = ((IdeaProyectoBean)ideaProyectoBean).getCodigoIdeaProyecto();
			request.setAttribute("numeroOrigen", numeroOrigen);
		}
		else {
			//	TODO: Confirmar esto.
			String strCodigo = ((IdeaProyectoPitecBean) ideaProyectoBean).getCodigo();
			request.setAttribute("numeroOrigen", strCodigo);
//			if (!Util.isBlank(strCodigo)) {
//				numeroOrigen = new Long(strCodigo);
//			}
		}
		
		request.setAttribute("cancelAction", cancelAction);
		request.setAttribute("submitAction", submitAction);
		
		// cargo datos de cabecera
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(new Long(idIdeaProyecto), new IdeaProyectoCabeceraAssembler());
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);

		return mapping.findForward("success");
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
		LocalizacionDAO localizacionDAO = (LocalizacionDAO) WebContextUtil.getBeanFactory().getBean("localizacionDao");
		// TODO: SS-se debe llenar el 'combo' empleos permanentes

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

		Collection localizacionList = new ArrayList();
		localizacionList.addAll(collectionHandler.getLocalizaciones(localizacionDAO));

		request.setAttribute("ventanillasPermanentes", ventanillaPermanenteList);
		request.setAttribute("tiposProyectos", tipoProyectoList);
		request.setAttribute("entidadesBeneficiarias", entidadBeneficiariaList);
		request.setAttribute("personas", personasList);
		request.setAttribute("ciius", ciiusList);
		request.setAttribute("entidadesBancarias", entidadBancariaList);
		request.setAttribute("localizaciones", localizacionList);
	}
}
