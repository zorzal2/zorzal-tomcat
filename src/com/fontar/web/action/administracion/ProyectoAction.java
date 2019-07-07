package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PRESENTACION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.EmpleoPermanenteDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.VentanillaPermanenteDAO;
import com.fontar.data.impl.assembler.PresentacionCabeceraAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericAction;

/**
 * 
 * @author ssanchez CRUD de proyectos
 */
public class ProyectoAction extends GenericAction {

	public ProyectoAction() {
	}

	@Override
	/**
	 * Método que muestra pantalla para agregar un proyecto Previamente llena
	 * los combos con datos
	 */
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO esta solo armado para la carga de proyectos desde una
		// presentación
		// TODO : Debería cargar ciertos campos con los datos de la presentación
		// TODO Generar codigo solo lectura

		setCollections(request);

		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;

		PresentacionConvocatoriaDAO presentacionDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
		Long idPresentacion = new Long(request.getParameter("idPresentacion"));
		PresentacionConvocatoriaBean bean = presentacionDAO.read(idPresentacion);

		request.setAttribute("entidadBeneficiariaOrigen", bean.getNombreEntidad());
		request.setAttribute("numeroOrigen", bean.getCodigo());
		request.setAttribute("labelNumeroOrigen", "numeroPresentacion");

		request.setAttribute("accion", "altaProyecto");

		dyna.set("idInstrumento", bean.getIdInstrumento().toString());
		dyna.set("idPresentacion", idPresentacion.toString());
		// dyna.set("localizacion.idJurisdiccion",bean.getIdJurisdiccion().toString());

		// para saber si muestro o no entidad bancaria
		request.setAttribute("permiteFinanciamientoBancario", permiteFinanciamientoBancario(request));
		
		request.setAttribute("cancelAction", "/PresentacionConvocatoriaInventario");
		request.setAttribute("submitAction", "/ProyectoGuardar");
		
		//cargo datos de cabecera
		PresentacionCabeceraAssembler assembler = new PresentacionCabeceraAssembler();
		PresentacionCabeceraDTO cabeceraDTO = assembler.buildDTO(bean);
		request.setAttribute(PRESENTACION, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.PRESENTACION);		
	}

	@Override
	/**
	 * Método que muestra pantalla para editar un proyecto Previamente carga los
	 * datos de la idea proyecto en un bean
	 */
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
		super.dataEditar(mapping, form, request, response);
	}

	@SuppressWarnings("deprecation")
	@Override
	/**
	 * Metodo invocado cuando se guarda un proyecto nuevo (ya sea desde Presentación o desde IdeaProyecto)
	 */
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		// determino si el metodo es llamado desde presentacion o idea proyecto
		boolean vieneDePresentacion = BeanUtils.getProperty(form, "tipoProyecto").equals("PresentacionConvocatoria");

		// creo instancias de objetos relacionados a un proyecto para agregar
		// datos
		ProyectoBean proyectoBean = new ProyectoBean();
		LocalizacionDTO localizacionDTO = (LocalizacionDTO) ((PragmaDynaValidatorForm) form).get("localizacion");
		EmpleoPermanenteDTO empleoPermanenteDTO = (EmpleoPermanenteDTO) ((PragmaDynaValidatorForm) form).get("empleo");

		// String idJurisdiccion = "";

		// cargo el estado
		if (vieneDePresentacion) {
			proyectoBean.setEstado(EstadoProyecto.INICIADO);
			// cargo el id de presentacion si viene de presetnacion convocatoria
			proyectoBean.setIdPresentacion(new Long(BeanUtils.getProperty(form, "idPresentacion")));
			// idJurisdiccion = BeanUtils.getProperty(form,"idJurisdiccion");
		}
		else {
			proyectoBean.setEstado(EstadoProyecto.ADMITIDO);
			// referencia a la Idea Proyecto que origino este proyecto
			proyectoBean.setIdProyectoOrigen(new Long(BeanUtils.getProperty(form, "id")));
		}

		proyectoBean.setIdInstrumento(FormUtil.getLongValue(form, "idInstrumento"));

		proyectoBean.setEstadoReconsideracion(false);
		proyectoBean.setCodigo(BeanUtils.getProperty(form, "codigo"));
		proyectoBean.setIdProyectoPitec(FormUtil.getLongValue(form, "idProyectoPitec"));
		proyectoBean.setIdEmpleoPermanente(empleoPermanenteDTO.getId());

		// cargo la entidad beneficiaria
		ProyectoDatosBean proyectoDatosBean = new ProyectoDatosBean();
		proyectoDatosBean.setIdEntidadBeneficiaria(FormUtil.getLongValue(form, "idEntidadBeneficiaria"));

		// cargo el titulo
		proyectoDatosBean.setTitulo(BeanUtils.getProperty(form, "titulo"));
		proyectoDatosBean.setObjetivo(BeanUtils.getProperty(form, "objetivo"));
		proyectoDatosBean.setIdPersonaLegal(FormUtil.getLongValue(form, "idPersonaLegal"));
		proyectoDatosBean.setIdPersonaDirector(FormUtil.getLongValue(form, "idPersonaDirector"));
		proyectoDatosBean.setIdPersonaRepresentante(FormUtil.getLongValue(form, "idPersonaRepresentante"));
		proyectoDatosBean.setIdCiiu(FormUtil.getLongValue(form, "idCiiu"));
		proyectoDatosBean.setResumen(BeanUtils.getProperty(form, "resumen"));
		proyectoDatosBean.setPalabraClave(BeanUtils.getProperty(form, "palabraClave"));
		proyectoDatosBean.setDuracion(FormUtil.getIntegerValue(form, "duracion"));

		proyectoDatosBean.setIdLocalizacion(localizacionDTO.getId());
		proyectoDatosBean.setIdTipoProyecto(FormUtil.getLongValue(form, "idTipoProyecto"));
		proyectoDatosBean.setIdEntidadBancaria(FormUtil.getLongValue(form, "idEntidadBancaria"));
		proyectoDatosBean.setDescripcionEntidadBancaria(BeanUtils.getProperty(form, "descripcionEntidadBancaria"));
		proyectoDatosBean.setPorcentajeTasaInteres(FormUtil.getBigDecimalValue(form, "porcentajeTasaInteres"));

		// cargo la fecha de ingreso con la actual
		proyectoDatosBean.setFechaIngreso(DateTimeUtil.getDate());

		proyectoDatosBean.setObservacion(BeanUtils.getProperty(form, "observacion"));
		
		BitacoraBean bitacoraProy = new BitacoraBean();
		// TODO ver como afecta esto a la edición
		// cargo datos de bitacora correspondiente al bean Proyecto
		bitacoraProy.setTipo(TipoBitacora.BASICO);
		bitacoraProy.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraProy.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraProy.setTema("Alta Proyecto");
		bitacoraProy.setDescripcion("NA");
		bitacoraProy.setIdUsuario(this.getUserId());

		// cargo datos de bitacora correspondiente a Proyecto Datos
		BitacoraBean bitacoraDatos = proyectoDatosBean.getBitacora();
		bitacoraDatos.setTipo( TipoBitacora.PROY_DATOS);
		bitacoraDatos.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraDatos.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraDatos.setTema("Proyecto Datos del Proyecto");
		bitacoraDatos.setDescripcion("NA");
		bitacoraDatos.setIdUsuario(this.getUserId());

		ProyectoJurisdiccionBean jurisdiccionBean = new ProyectoJurisdiccionBean();
		jurisdiccionBean.setIdJurisdiccion(new Long(localizacionDTO.getIdJurisdiccion()));
		jurisdiccionBean.setCodigo(proyectoBean.getCodigo());

		BitacoraBean bitacoraJurisdiccion = jurisdiccionBean.getBitacora();
		bitacoraJurisdiccion.setTipo( TipoBitacora.PROY_JURISDICCION);
		bitacoraJurisdiccion.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraJurisdiccion.setTema("Jurisdicción");
		bitacoraJurisdiccion.setDescripcion("Asociación de Jurisdicción");
		bitacoraJurisdiccion.setIdUsuario(this.getUserId());
		
		// guardo el proyecto y sus objetos relacionados
		getServicio().save(proyectoBean, bitacoraProy, proyectoDatosBean, jurisdiccionBean, localizacionDTO, empleoPermanenteDTO);
	}

	@Override
	protected ActionForward forwardGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!BeanUtils.getProperty(form, "idPresentacion").equals("")) {
			return mapping.findForward("successPresentacionConvocatoria");
		}
		else {
			return mapping.findForward("successIdeaProyecto");
		}
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		setCollections(request);
		super.validateGuardar(mapping, form, request, response, messages);
		
		// alta
		String id = request.getParameter("id");
		if (id == null || id == "") {
			String codigo = BeanUtils.getProperty(form, "codigo");
			ProyectoDAO proyectoDAO = (ProyectoDAO) getServicio().getDao(ProyectoBean.class);
			List<ProyectoBean> list = proyectoDAO.findByCodigo(codigo);
			if (!list.isEmpty()) {
				addError(messages, "app.proyecto.existeProyecto");
			}
		}
		boolean vieneDePresentacion = BeanUtils.getProperty(form, "tipoProyecto").equals("PresentacionConvocatoria");
		
		// Si viene de Presentación y el instrumento tiene Financiación Bancaria 
		// el Proyecto debe tener Entidad Bancaria (solo el nombre)
		if(vieneDePresentacion)
			if(permiteFinanciamientoBancario(request)){
				if(FormUtil.getLongValue(form, "idEntidadBancaria") == null)
					addError(messages, "app.proyecto.entidadFinanciera");
		}
	}

	private Boolean permiteFinanciamientoBancario(HttpServletRequest request) {
		PresentacionConvocatoriaDAO presentacionDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
		Long idPresentacion = new Long(request.getParameter("idPresentacion"));
		PresentacionConvocatoriaBean bean = presentacionDAO.read(idPresentacion);
		return bean.getInstrumento().getPermiteFinanciamientoBancario();
	}
	/*
	 * 
	 * Lleno las collections para Agregar y Edición
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		EntidadBeneficiariaDAO entidadBeneficiariaDAO = (EntidadBeneficiariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBeneficiariaDao");
		PersonaDAO personasDAO = (PersonaDAO) WebContextUtil.getBeanFactory().getBean("personaDao");
		CiiuDAO ciiuDAO = (CiiuDAO) WebContextUtil.getBeanFactory().getBean("ciiuDao");
		VentanillaPermanenteDAO ventanillaPermanenteDAO = (VentanillaPermanenteDAO) WebContextUtil.getBeanFactory().getBean("ventanillaPermanenteDao");
		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		EntidadBancariaDAO entidadBancariaDAO = (EntidadBancariaDAO) WebContextUtil.getBeanFactory().getBean("entidadBancariaDao");
		// TODO: SS-ver que hacer con la localizacion
		LocalizacionDAO localizacionDAO = (LocalizacionDAO) WebContextUtil.getBeanFactory().getBean("localizacionDao");
		// TODO: SS-se debe llenar el 'combo' empleos permanentes
		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
		EmpleoPermanenteDAO empleoPermanenteDAO = (EmpleoPermanenteDAO) WebContextUtil.getBeanFactory().getBean("empleoPermanenteDao");

		Collection tipoProyectoList = new ArrayList();
		tipoProyectoList.addAll(collectionHandler.getTiposProyectos(tipoProyectoDAO)); // getTipoProyecto(tipoProyectosDAO);

		Collection entidadBeneficiariaList = new ArrayList();
		entidadBeneficiariaList.addAll(collectionHandler.getEntidadesBeneficiarias(entidadBeneficiariaDAO));

		Collection personasList = new ArrayList();
		personasList.addAll(collectionHandler.getPersonas(personasDAO));

		Collection ciiusList = new ArrayList();
		ciiusList.addAll(collectionHandler.getCiius(ciiuDAO));

		Collection ventanillaPermanenteList = new ArrayList();
		ventanillaPermanenteList.addAll(collectionHandler.getVentanillas(ventanillaPermanenteDAO));

		Collection entidadBancariaList = new ArrayList();
		entidadBancariaList.addAll(collectionHandler.getEntidadesBancarias(entidadBancariaDAO));

		Collection localizacionList = new ArrayList();
		localizacionList.addAll(collectionHandler.getLocalizaciones(localizacionDAO));

		Collection jurisdicciones = new ArrayList();
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));

		Collection empleoPermanente = new ArrayList();
		empleoPermanente.addAll(collectionHandler.getEmpleoPermanente(empleoPermanenteDAO));

		request.setAttribute("jurisdicciones", jurisdicciones);
		request.setAttribute("tiposProyectos", tipoProyectoList);
		request.setAttribute("entidadesBeneficiarias", entidadBeneficiariaList);
		request.setAttribute("personas", personasList);
		request.setAttribute("ciius", ciiusList);
		request.setAttribute("ventanillasPermanentes", ventanillaPermanenteList);
		request.setAttribute("entidadesBancarias", entidadBancariaList);
		request.setAttribute("localizaciones", localizacionList);
		request.setAttribute("empleo", empleoPermanente);
	}
}
