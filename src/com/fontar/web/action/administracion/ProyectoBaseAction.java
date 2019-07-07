package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PRESENTACION;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadIntervinientesDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.entidad.TipoEntidad;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.EntidadIntervinientesDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.seguridad.AuthorizationService;
import com.fontar.seguridad.cripto.AccesoDenegadoException;
import com.fontar.util.Util;
import com.fontar.web.action.proyecto.ProyectoBaseTaskAction;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;

/**
 * Acá quedan todas las acciones comunes a las entidades Proyecto, ProyectoPitec e IdeaProyectoPitec.
 * @author gboaglio
 * 
 */

public class ProyectoBaseAction extends ProyectoBaseTaskAction {
		
	private BitacoraDAO bitacoraDao;
	
	private EntidadDAO entidadDao;
	
	private EntidadIntervinientesDAO entidadIntervinientesDao;
	
	public void setBitacoraDAO(BitacoraDAO bitacoraDAO) {
		this.bitacoraDao = bitacoraDAO;
	}

	/**
	 * Prepara la pantalla para agregar un proyecto, proyecto pitec, o idea proyecto pitec 
	 * 
	 */
	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String strIdPresentacion = request.getParameter("idPresentacion");
		Long idPresentacion = null;
		
		if (!Util.isBlank(strIdPresentacion)) {
			idPresentacion = new Long(strIdPresentacion);
			ProyectoAgregarDTO proyectoAgregarDto = wfProyectoServicio.obtenerDatosAgregarProyecto(idPresentacion);
			BeanUtils.setProperty(form, "idInstrumento", proyectoAgregarDto.getIdInstrumento().toString());
			setHeaderData(request, proyectoAgregarDto);
			BeanUtils.setProperty(form, "codigo", proyectoAgregarDto.getCodigoPresentacion());
			//cargo datos de cabecera
			PresentacionCabeceraDTO cabeceraDTO = wfProyectoServicio.obtenerDatosCabeceraProyecto(idPresentacion);
			
			request.setAttribute(PRESENTACION, cabeceraDTO);
			request.setAttribute("clase", PreProyectos.PRESENTACION);
		}
		else {
			request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);
		}
				
		String strIdProyecto = request.getParameter("id");
		String forward = "success";

		if (!Util.isBlank(strIdProyecto)) {
			// Esto es sólo para cuando entra después de una edición inválida. Setea la cabecera.
			Long idProyecto = new Long(strIdProyecto);
			ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
			ponerDatosDeEdicionDeRecomendacion(form, request, idProyecto, visualizacionDTO.getIdInstrumento());
			request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
			forward = "edicionInvalida";			
		}
		else {
			request.setAttribute("accion", "altaProyecto");
			request.setAttribute("cancelAction", "/PresentacionConvocatoriaInventario");
		}
								
		setCollections(request);
								
		request.setAttribute("submitAction", getSubmitAction());
			String[] tipoEntidad = FormUtil.getStringArrayValue(form, "tipoEntidad");
			String[] idEntidad = FormUtil.getStringArrayValue(form, "idEntidad");
			String[] relacion = FormUtil.getStringArrayValue(form, "relacion");
			String[] funcion = FormUtil.getStringArrayValue(form, "funcion");

			entidadDao = (EntidadDAO)ContextUtil.getBean("entidadDao");
			Collection<String[]> intervinientes = new LinkedList<String[]>();
			if (idEntidad != null) {
				for (int i = 0; i < idEntidad.length; i++) {

					EntidadBean entidadBean = (EntidadBean) entidadDao.read(new Long(idEntidad[i]));
					TipoEntidad tipo = TipoEntidad.valueOf(tipoEntidad[i]);
					String[] datos = { entidadBean.getDenominacion(), tipo.getDescripcion(), relacion[i], funcion[i], idEntidad[i], tipoEntidad[i] };
					intervinientes.add(datos);
				}
			}
			request.setAttribute("intervinientes", intervinientes);

		return mapping.findForward(forward);
	}
			
	/**
	 * Obtiene los datos de un Proyecto, PP, o IPP, para su Edición.
	 * 
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String strIdProyecto = request.getParameter("id");
		
		Long idProyecto = new Long(strIdProyecto);
		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		verifyGranted(visualizacionDTO.getIdInstrumento(), "PROYECTOS-EDITAR");
		
		request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
		
		FormUtil.copyProperties(form, visualizacionDTO);		
		BeanUtils.setProperty(form, "id", strIdProyecto);
		
		Long idPresentacion = visualizacionDTO.getIdPresentacion(); 
		BeanUtils.setProperty(form, "idPresentacion", idPresentacion);
		
		setHeaderData(request, visualizacionDTO.getDatosProyectoDto());		
		
		setCollections(request);
		
		List<BitacoraBean> list;
		bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		entidadDao = (EntidadDAO)ContextUtil.getBean("entidadDao");
		entidadIntervinientesDao = (EntidadIntervinientesDAO)ContextUtil.getBean("entidadIntervinientesDao");
		list = bitacoraDao.findByProyectoTipo(idProyecto, TipoBitacora.ENTIDAD_INTERVINIENTE.getName());
		Collection<String[]> intervinientes = new LinkedList<String[]>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				EntidadIntervinientesBean intervinientesBean = (EntidadIntervinientesBean) entidadIntervinientesDao.read(list.get(i).getId());
				if (intervinientesBean.getActivo()) {
					EntidadBean entidadBean = (EntidadBean) entidadDao.read(intervinientesBean.getIdEntidad());
					TipoEntidad tipo = intervinientesBean.getTipoEntidad();
					String[] datos = { entidadBean.getDenominacion(), tipo.getDescripcion(), intervinientesBean.getRelacion(), intervinientesBean.getFuncion(), intervinientesBean.getIdEntidad().toString(), tipo.getName() };
					intervinientes.add(datos);
				}
			}
		}
		request.setAttribute("intervinientes", intervinientes);
		
		ponerDatosDeEdicionDeRecomendacion(form, request, idProyecto, visualizacionDTO.getIdInstrumento());
		//Fin de edicion de recomendacion
		
		return mapping.findForward("success");
	}

	private void ponerDatosDeEdicionDeRecomendacion(ActionForm form, HttpServletRequest request, Long idProyecto, Long idInstrumento) throws IllegalAccessException, InvocationTargetException {
		/*
		 * Edicion de recomendacion
		 * Solo se puede hacer si:
		 * (a) El proyecto tiene una recomendacion ya ingresada
		 * (b) El usuario tiene el permiso PROYECTOS-CAMBIAR-RECOMENDACION
		 * (c) El usuario tiene contexto de encriptacion
		 * Entonces:
		 * (a)+(b)+(c) => Se muestra el combo con las recomendaciones para que el usuario elija
		 * !(b) => Se muestra un mensaje informativo diciendo que no se puede editar la rec. sin ctx de enc.
		 * Demas casos: No se muestra nada.
		 * Se ponen en la pagina:
		 * 1) La variable MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION, que es True si hay que mostrar el mensaje o False si no.
		 * 2) MOSTRAR_COMBO_RECOMENDACION que es true si estan dadas las condiciones para mostrar el combo y en
		 * ese caso esta presente el listado en la variable recomendaciones y el valor actual en recomendacion. 
		 */
		boolean MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION;
		boolean MOSTRAR_COMBO_RECOMENDACION;
		try {
			AuthorizationService authorizationService = (AuthorizationService) ContextUtil.getBean("authorizationService");
			if(authorizationService.grantedPermissionByInstrumento("PROYECTOS-CAMBIAR-RECOMENDACION", idInstrumento)) {
				Recomendacion recomendacion;
				try {
					recomendacion = administrarProyectoServicio.obtenerRecomendacionDeProyecto(new Long(idProyecto));
					MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION = false;
					if(recomendacion==null) {
						MOSTRAR_COMBO_RECOMENDACION = false;
					} else {
						MOSTRAR_COMBO_RECOMENDACION = true;
						request.setAttribute("recomendaciones", new CollectionHandler().getRecomendacionesProyecto());
						BeanUtils.setProperty(form, "recomendacion", recomendacion.name());
					}
				}
				catch (AccesoDenegadoException e) {
					MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION = true;
					MOSTRAR_COMBO_RECOMENDACION = false;
				}
			} else {
				MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION = false;
				MOSTRAR_COMBO_RECOMENDACION = false;
			}
		} catch(SecurityException e) {
			MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION = true;
			MOSTRAR_COMBO_RECOMENDACION = false;
		}
		request.setAttribute("MOSTRAR_COMBO_RECOMENDACION", MOSTRAR_COMBO_RECOMENDACION);
		request.setAttribute("MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION", MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION);
	}

	/**
	 * Guarda los datos de un proyecto, pp, o ipp. (edición)
	 */	
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		form.validate(mapping, request);
				
		ProyectoEdicionDTO datosDto = obtenerDatosVistaEdicion(request, form);
		

		if(!checkCodigoValido(datosDto, request))
			return mapping.findForward(FORWARD_INVALID);
		
		ActionMessages messages = getErrors(request);
		// Si el instrumento tiene Financiación Bancaria 
		// el Proyecto debe tener Entidad Bancaria (solo el nombre)
		if(permiteFinanciamientoBancario(request)){
			if(FormUtil.getLongValue(form, "idEntidadBancaria") == null) {
				addError(messages, "app.proyecto.entidadFinanciera");
				saveErrors(request,messages);				
				return mapping.findForward(FORWARD_INVALID);
			}
		}

		setearEntidadesIntervinientes(form, datosDto);

		wfProyectoServicio.guardarDatosEdicionProyecto(datosDto);
		return mapping.findForward("success");
	}

	private void setearEntidadesIntervinientes(ActionForm form, ProyectoEdicionDTO datosDto) throws Exception {
		String[] tipoEntidad = FormUtil.getStringArrayValue(form, "tipoEntidad");
		String[] idEntidad = FormUtil.getStringArrayValue(form, "idEntidad");
		String[] relacion = FormUtil.getStringArrayValue(form, "relacion");
		String[] funcion = FormUtil.getStringArrayValue(form, "funcion");

		Collection<EntidadIntervinientesDTO> listaEntidadIntervinientes = new ArrayList<EntidadIntervinientesDTO>();
		if (idEntidad != null) {
			for (int i = 0; i < idEntidad.length; i++) {

				EntidadIntervinientesDTO dto = new EntidadIntervinientesDTO();
				dto.setIdEntidad(idEntidad[i]);
				dto.setTipoEntidad(tipoEntidad[i]);
				dto.setRelacion(relacion[i]);
				dto.setFuncion(funcion[i]);
				listaEntidadIntervinientes.add(dto);
			}
			datosDto.setIntervinientes(listaEntidadIntervinientes);
		}
	}
	private Boolean permiteFinanciamientoBancario(HttpServletRequest request) {
		PresentacionConvocatoriaDAO presentacionDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
		if(request.getParameter("idPresentacion") == null || request.getParameter("idPresentacion") == "") {
			return false;
		} else {
			Long idPresentacion = new Long(request.getParameter("idPresentacion"));
			PresentacionConvocatoriaBean bean = presentacionDAO.read(idPresentacion);
			return bean.getInstrumento().getPermiteFinanciamientoBancario();
		}
	}
	/**
	 * Guarda los datos de un proyecto, pp, o ipp. (alta)
	 */
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		form.validate(mapping, request);
		
		boolean vieneDePresentacion = BeanUtils.getProperty(form, "tipoProyecto").equals("PresentacionConvocatoria");
		
		ProyectoEdicionDTO datosDto = obtenerDatosVista(request, form);

		if(!checkCodigoValido(datosDto, request))
			return mapping.findForward(FORWARD_INVALID);
		
		// Si el instrumento tiene Financiación Bancaria 
		// el Proyecto debe tener Entidad Bancaria (solo el nombre)
		if(permiteFinanciamientoBancario(request)){
			if(FormUtil.getLongValue(form, "idEntidadBancaria") == null) {
				ActionMessages messages = getErrors(request);
				addError(messages, "app.proyecto.entidadFinanciera");
				saveErrors(request,messages);				
				return mapping.findForward(FORWARD_INVALID);
			}
		}
				
		String strIdProyectoPitec = BeanUtils.getProperty(form, "idProyectoPitec");		
		Long idProyectoPitec = null;		
		if (!Util.isBlank(strIdProyectoPitec)) {
			idProyectoPitec = new Long(strIdProyectoPitec);
		}
		
		String strForward = "successIdeaProyecto";
		String strIdPresentacion = BeanUtils.getProperty(form, "idPresentacion");
		Long idPresentacion = null;		
		if (!Util.isBlank(strIdPresentacion)) {
			idPresentacion = new Long(strIdPresentacion);
			strForward="successPresentacionConvocatoria";
		}		
		
		Long idInstrumento = FormUtil.getLongValue(form, "idInstrumento");	
		
		setearEntidadesIntervinientes(form, datosDto);
		
		this.wfProyectoServicio.cargarProyecto(datosDto, vieneDePresentacion, idInstrumento, idPresentacion, idProyectoPitec);		
		
		return mapping.findForward(strForward);
	}
	
	private boolean checkCodigoValido(ProyectoEdicionDTO datosDto, HttpServletRequest request) {
//		 validar por duplicado
		ActionMessages messages = getErrors(request);
		ProyectoDAO proyectoDAO = (ProyectoDAO) WebContextUtil.getBeanFactory().getBean("proyectoDao");
		List<ProyectoBean> list = proyectoDAO.findByCodigo(datosDto.getCodigo());

		for(ProyectoBean proyecto : list) {
			if(!proyecto.estaAnulado()) {
				//Potencial problematico
				//Solo cabe la posibilidad de que sea el mismo proyecto
				//que actualmente se esta modificando (si lo hubiere)
				if(	
						datosDto.getIdProyecto()==null
					|| 	!datosDto.getIdProyecto().equals(proyecto.getId())) {
					
					addError(messages, "app.proyecto.existeProyecto");
					saveErrors(request,messages);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Obtiene los datos cargados en la vista al editar un proyecto.
	 */	
	protected ProyectoEdicionDTO obtenerDatosVistaEdicion(HttpServletRequest request, ActionForm form) throws Exception {
		ProyectoEdicionDTO datosEdicionDto = obtenerDatosVista(request,form);		
		datosEdicionDto.setTir(FormUtil.getBigDecimalValue(form, "tir"));
		datosEdicionDto.setVan(FormUtil.getBigDecimalValue(form, "van"));	
		String sRecomendacion = FormUtil.getStringValue(form, "recomendacion");
		if(sRecomendacion==null) datosEdicionDto.setRecomendacion(null);
		else datosEdicionDto.setRecomendacion(Recomendacion.valueOf(sRecomendacion));
		return datosEdicionDto;
	}
	
	/**
	 * Obtiene los datos cargados en la vista al dar de alta o editar un proyecto.
	 */
	protected ProyectoEdicionDTO obtenerDatosVista(HttpServletRequest request, ActionForm form) throws Exception {
		// -- ProyectoDatos
		ProyectoEdicionDTO datosDto = new ProyectoEdicionDTO();
		datosDto.setCodigo(FormUtil.getStringValue(form, "codigo"));		
		
		String strIdProyecto = request.getParameter("id");
		if (!Util.isBlank(strIdProyecto)) {
			datosDto.setIdProyecto(new Long(strIdProyecto));
		}
				
		String proyectoPitecRelacionado = request.getParameter("proyectoPitec");
		if (!Util.isBlank(proyectoPitecRelacionado)) {
			datosDto.setProyectoPitec(proyectoPitecRelacionado);
		}
		
		datosDto.setIdEntidadBeneficiaria(FormUtil.getLongValue(form, "idEntidadBeneficiaria"));
		datosDto.setDuracion(FormUtil.getStringValue(form, "duracion"));
		datosDto.setIdCiiu(FormUtil.getLongValue(form, "idCiiu"));
		datosDto.setIdPersonaDirector(FormUtil.getLongValue(form, "idPersonaDirector"));
		datosDto.setIdPersonaLegal(FormUtil.getLongValue(form, "idPersonaLegal"));
		datosDto.setIdPersonaRepresentante(FormUtil.getLongValue(form, "idPersonaRepresentante"));
		datosDto.setIdTipoProyecto(FormUtil.getLongValue(form, "idTipoProyecto"));
		datosDto.setObjetivo(FormUtil.getStringValue(form, "objetivo"));
		datosDto.setObservacion(FormUtil.getStringValue(form, "observacion"));
		datosDto.setPalabraClave(FormUtil.getStringValue(form, "palabraClave"));
		datosDto.setResumen(FormUtil.getStringValue(form, "resumen"));
		datosDto.setTitulo(FormUtil.getStringValue(form, "titulo"));
		datosDto.setIdEntidadBancaria(FormUtil.getLongValue(form, "idEntidadBancaria"));
		datosDto.setDescripcionEntidadBancaria(BeanUtils.getProperty(form, "descripcionEntidadBancaria"));
		datosDto.setPorcentajeTasaInteres(FormUtil.getBigDecimalValue(form, "porcentajeTasaInteres"));
		datosDto.setEmerix(FormUtil.getStringValue(form, "emerix"));

		// -- Localizacion
		LocalizacionDTO localizacionDto = (LocalizacionDTO) ((PragmaDynaValidatorForm) form).get("localizacion");
		datosDto.setLocalizacion(localizacionDto);

		// -- Empleo Permanente
		EmpleoPermanenteDTO empleo = (EmpleoPermanenteDTO) ((PragmaDynaValidatorForm) form).get("empleo");
		datosDto.setEmpleo(empleo);
		
		return datosDto;
	}	
	
	/**
	 * Este método permite que ProyectoPitec e IdeaProyectoPitec, haciendo override,
	 * definan su propia acción de submit al dar el alta para guardar la entidad que corresponda.
	 * 
	 */
	public String getSubmitAction() {
		return "/ProyectoGuardar";
	}
	
	/**
	 * Lleno las collections para la Edición GB / este método debería ir en un
	 * ProyectoBaseAction para implementarlo una sola vez
	 */
	protected void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		EntidadBancariaDAO entidadBancariaDAO = (EntidadBancariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBancariaDao");
		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");

		Collection<LabelValueBean> jurisdicciones = collectionHandler.getJurisdicciones(jurisdiccionDAO);

		request.setAttribute("jurisdicciones", jurisdicciones);
		Collection<LabelValueBean> tipoProyectoList = collectionHandler.getTiposProyectos(tipoProyectoDAO);

		Collection<LabelValueBean> entidadBancariaList = collectionHandler.getEntidadesBancarias(entidadBancariaDAO);

		request.setAttribute("tiposProyectos", tipoProyectoList);
		request.setAttribute("entidadesBancarias", entidadBancariaList);
	}

	/**
	 * Método que setea en el request información necesaria para el encabezado
	 * de las pantallas de edición o creación de proyecto, y también si el
	 * proyecto permite o no financiamiento bancario
	 * 
	 */
	protected void setHeaderData(HttpServletRequest request, ProyectoAgregarDTO dto) {
		Boolean permiteFinanciamientoBancario = dto.getPermiteFinanciamientoBancario();
		request.setAttribute("permiteFinanciamientoBancario", permiteFinanciamientoBancario);

		// TODO: Información para el Encabezado.
		request.setAttribute("labelNumeroOrigen", "numeroPresentacion");
		request.setAttribute("entidadBeneficiariaOrigen", dto.getEntidadBeneficiariaOrigen());
		request.setAttribute("numeroOrigen", dto.getCodigoPresentacion());
	}

	public void setEntidadIntervinientesDao(EntidadIntervinientesDAO entidadIntervinientesDao) {
		this.entidadIntervinientesDao = entidadIntervinientesDao;
	}

	public void setEntidadDao(EntidadDAO entidadDao) {
		this.entidadDao = entidadDao;
	}
}