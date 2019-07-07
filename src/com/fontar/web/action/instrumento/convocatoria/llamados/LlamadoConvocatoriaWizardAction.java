package com.fontar.web.action.instrumento.convocatoria.llamados;

import static com.pragma.util.FormUtil.getLongValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.CarteraDAO;
import com.fontar.data.api.dao.ComisionDAO;
import com.fontar.data.api.dao.DistribucionFinanciamientoDAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.InstrumentoDefDAO;
import com.fontar.data.api.dao.LlamadoConvocatoriaDAO;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.api.dao.MatrizPresupuestoDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.assembler.DistribucionFinanciamientoDTOAssembler;
import com.fontar.data.impl.assembler.DistribucionTipoProyectoDTOAssembler;
import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.bean.InstrumentoVersionBean;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.codes.instrumento.TipoFinanciamientoAsignacionInstrumento;
import com.fontar.data.impl.domain.codes.instrumento.TipoFinanciamientoInstrumento;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.fontar.data.impl.domain.dto.DistribucionFinanciamientoDTO;
import com.fontar.data.impl.domain.dto.DistribucionTipoProyectoDTO;
import com.fontar.util.SessionHelper;
import com.fontar.util.Util;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericWizardAction;

//FIXME: SS-Este esquema de implementacion del Action esta deprecado, no seguirlo como ejemplo.

/**
 * Wizar para agregar/editar un llamado convocatoria, y carga de version
 * @author ssanchez
 * @version 1.02, 14/12/06
 */
public class LlamadoConvocatoriaWizardAction extends GenericWizardAction {
	// TODO: SS-hacer refactoring de estas funcionalidades
	/**
	 * Muestra el formulario para agregar un llamado a convocatoria
	 */
	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Limpio el formulario para que no muestre los datos que podrian estar
		// en session
		if (getErrors(request).isEmpty()) {
			((DynaActionForm) form).getMap().clear();
			SessionHelper.setDistribucionFinanciamientoJurisdiccion(request, null);
			SessionHelper.setDistribucionFinanciamientoRegion(request, null);
			SessionHelper.setDistribucionTipoProyecto(request, null);
		}
		setCollections(form, request);

		return mapping.findForward("success");
	}

	/**
	 * Muestra el formulario para editar un llamado a convocatoria
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errores = getErrors(request);

		if (errores.size() <= 0) {
			// si el id no viene como parametro podría venir desde version->debe
			// levantar datos de session
			if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				// Limpio el formulario para que no muestre los datos que
				// podrian estar en session
				((DynaActionForm) form).getMap().clear();

				// obtengo llamado a convocatoria para mostrar
				LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(new Long(request.getParameter("id")));
				BeanUtils.copyProperties(form, llamadoConvocatoriaBean);
				request.setAttribute("llamadoConvocatoriaBean", llamadoConvocatoriaBean);

				// limpio de session distribucion financiamiento
				SessionHelper.setDistribucionFinanciamientoJurisdiccion(request, null);
				SessionHelper.setDistribucionFinanciamientoRegion(request, null);
				// obtengo los datos de distribucion financiamiento para este
				// llamado
				setDistribucionFinanciamiento(request, llamadoConvocatoriaBean.getId());

				// limpio de session distribucion tipo proyecto
				SessionHelper.setDistribucionTipoProyecto(request, null);
				// obtengo los datos de distribucion tipo proyecto para este
				// llamado
				setDistribucionTipoProyecto(request, llamadoConvocatoriaBean.getId());
			}
		}
		if (((DynaActionForm) form).get("id") != null && !((DynaActionForm) form).get("id").equals("")) {
			// obtengo la version para mostrar
			LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(new Long(((DynaActionForm) form).get("id").toString()));
			addDataMostrar(request, llamadoConvocatoriaBean);
		}
		setSelectors(form, request);
		setCollections(form, request);

		return mapping.findForward("success");
	}

	@SuppressWarnings("unchecked")
	private void addDataMostrar(HttpServletRequest request, LlamadoConvocatoriaBean llamadoConvocatoriaBean)
			throws Exception {
		// obtengo la version para mostrar
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) getServicio().load(InstrumentoVersionBean.class, llamadoConvocatoriaBean.getIdInstrumentoVersion());
		request.setAttribute("version", instrumentoVersionBean.getVersion());
		// obtengo el instrumento
		CollectionHandler collectionHandler = new CollectionHandler();
		Collection instrumentosDef = new ArrayList();
		InstrumentoDefDAO instrumentoDefDAO = (InstrumentoDefDAO) WebContextUtil.getBeanFactory().getBean("instrumentoDefDao");
		instrumentosDef.addAll(collectionHandler.getInstrumentosDef(instrumentoDefDAO));
		Iterator i = instrumentosDef.iterator();
		while (i.hasNext()) {
			LabelValueBean lvBean = (LabelValueBean) i.next();
			if (lvBean.getValue() != null
					&& lvBean.getValue().compareTo(String.valueOf(llamadoConvocatoriaBean.getIdInstrumentoDef())) == 0) {
				request.setAttribute("instrumentoNombre", lvBean.getLabel());
			}
		}

		request.setAttribute("llamadoConvocatoriaBean", llamadoConvocatoriaBean);
	}

	/**
	 * Muestra el formulario para cargar los datos de la version del llamado a
	 * convocatoria
	 */
	public ActionForward versionar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
			crearVersionNuevaConvocatoria(mapping, form, request, response);
		}
		else {
			crearVersionModificacionConvocatoria(mapping, form, request, response);
		}

		return mapping.findForward("success");
	}

	/**
	 * Crea una version para un nuevo llamado convocatoria
	 */
	public void crearVersionNuevaConvocatoria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// obtengo el id de la llamadaConvocatoria
		DynaActionForm dyna = (DynaActionForm) form;

		// cargo el formulario con los datos de version y fecha
		dyna.set("version", "1"); // no siempre es malo el hardcore; heavy;
		dyna.set("fecha", DateTimeUtil.getStringFormatDateTime());

		request.setAttribute("identificador", FormUtil.getStringValue(form, "identificador"));
		request.setAttribute("version", "1");
		request.setAttribute("fecha", DateTimeUtil.getStringFormatDateTime());
	}

	/**
	 * Crea una nueva version para una modificación de llamado convocatoria
	 */
	public void crearVersionModificacionConvocatoria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// nueva version para nuevo llamado o para uno existente
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(FormUtil.getLongValue(form, "id"));
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) getServicio().load(InstrumentoVersionBean.class, llamadoConvocatoriaBean.getIdInstrumentoVersion());
		Long version = instrumentoVersionBean.getVersion() + 1L;

		// cargo el formulario con los datos de version y fecha
		DynaActionForm dyna = (DynaActionForm) form;
		dyna.set("version", version.toString());
		dyna.set("fecha", DateTimeUtil.getStringFormatDateTime());

		request.setAttribute("identificador", FormUtil.getStringValue(form, "identificador"));
		request.setAttribute("version", version.toString());
		request.setAttribute("fecha", DateTimeUtil.getStringFormatDateTime());
	}

	@Override
	/**
	 * Sobreescribo el metodo para poder validar el instrumento de esta página
	 */
	public ActionForward doNext(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		processNext(mapping, form, request, response);

		String forward = "next";
		double monto = 0.0;

		if (((DynaActionForm) form).get("id") != null && !((DynaActionForm) form).get("id").equals("")) {
			LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(new Long(((DynaActionForm) form).get("id").toString()));
			addDataMostrar(request, llamadoConvocatoriaBean);
		}

		// Cola de mensajes de error
		ActionMessages messages = getErrors(request);

		validarInstrumento(messages, mapping, form, request, response);
		List distribucionFinanciamientos = new ArrayList();
		BigDecimal montoFinanciamientoTotal = FormUtil.getBigDecimalValue(form, "montoFinanciamientoTotal");
		String tipoDistribucionFinanciamiento = FormUtil.getStringValue(form, "tipoDistribucionFinanciamiento");
		//			
		// Validación de Distribución por Zona (Global, Región, Jurisdicción)
		//
		if(montoFinanciamientoTotal == null) {
			if (!tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.GLOBAL))
					addError(messages, "app.instrumento.mntFmtTotNull");
		}
		if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.REGIONAL)
				|| tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.JURISDICCIONAL)) {

			if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.REGIONAL)) {
				distribucionFinanciamientos = (List) SessionHelper.getDistribucionFinanciamientoRegion(request);
			}
			else if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.JURISDICCIONAL)) {
				distribucionFinanciamientos = (List) SessionHelper.getDistribucionFinanciamientoJurisdiccion(request);
			}
			if (distribucionFinanciamientos == null)
				addError(messages, "app.instrumento.distribZonaEmpty");
			else {
				if(montoFinanciamientoTotal != null) {
					monto = sumatoriaMontos(messages, form, distribucionFinanciamientos, new Integer(1));
					if (montoFinanciamientoTotal.compareTo(BigDecimal.valueOf(monto)) != 0)
						addError(messages, "app.instrumento.montoDifInstrumento");
				}
			}
		}
		//			
		// Validación de Distribución por tipo de proyecto
		//
		distribucionFinanciamientos = (List) SessionHelper.getDistribucionTipoProyecto(request);
		if (distribucionFinanciamientos != null && montoFinanciamientoTotal != null) {
			monto = sumatoriaMontos(messages, form, distribucionFinanciamientos, new Integer(2));
			if (montoFinanciamientoTotal.compareTo(BigDecimal.valueOf(monto)) < 0)
				addError(messages, "app.instrumento.montoMenorInstrumento");
		}
		//			
		// Validación de Límite de Financiación Por_Beneficiario
		//
		String tipoFinanciamiento = FormUtil.getStringValue(form, "codigoTipoFinanciamiento");
		if (tipoFinanciamiento.compareTo(TipoFinanciamientoInstrumento.POR_BENEFICIARIO.name()) == 0) {
			if (FormUtil.getStringValue(form, "montoFinanciamiento") == null) {
				addError(messages, "app.instrumento.montoTipoFinanBenef");
			}
			else {
				BigDecimal montoFinanciamiento = FormUtil.getBigDecimalValue(form, "montoFinanciamiento");
				if (montoFinanciamiento.compareTo(BigDecimal.ZERO) <= 0) {
					addError(messages, "app.instrumento.montoEmptyTipoFinanBenef");
				}
				if (montoFinanciamientoTotal != null && montoFinanciamientoTotal.compareTo(montoFinanciamiento) < 0) {
					addError(messages, "app.instrumento.montoFinanMenorInstrumento");
				}
			}
		}

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			forward = "invalid";
		}
		return mapping.findForward(forward);
	}

	private double sumatoriaMontos(ActionMessages messages, ActionForm form, List distribucionFinanciamientos, int tipo)
			throws Exception {
		double monto = 0;
		Iterator i = distribucionFinanciamientos.iterator();
		while (i.hasNext()) {
			switch (tipo) {
			case 1:
				DistribucionFinanciamientoDTO d1DTO = (DistribucionFinanciamientoDTO) i.next();
				if (d1DTO.getMonto() != null) {
					monto += Double.parseDouble(d1DTO.getMonto().toString());
				}
				break;
			case 2:
				DistribucionTipoProyectoDTO d2DTO = (DistribucionTipoProyectoDTO) i.next();
				if (d2DTO.getMontoTotalAsignado() != null) {
					monto += Double.parseDouble(d2DTO.getMontoTotalAsignado().toString());
				}
				break;
			default:
				addError(messages, "app.instrumento.tipoInexTipoDistrib");
			}
		}
		return monto;
	}

	/**
	 * Valida que el código no exista cuando se crea un nuevo llamado
	 * convocatoria o cuando se edita
	 */
	private void validarInstrumento(ActionMessages messages, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		form.validate(mapping, request);

		List<LlamadoConvocatoriaBean> list;
		String identificador = FormUtil.getStringValue(form, "identificador");
		String idLlamado = FormUtil.getStringValue(form, "id");

		LlamadoConvocatoriaDAO convocatoriaDAO = (LlamadoConvocatoriaDAO) getServicio().getDao(LlamadoConvocatoriaBean.class);

		if (idLlamado == null || idLlamado == "") {
			list = convocatoriaDAO.findByIdentificador(identificador);
		}
		else {
			list = convocatoriaDAO.findByIdentificadorId(new Long(idLlamado), identificador);
		}

		if (!list.isEmpty()) {
			addError(messages, "app.instrumento.existeIdentificador");
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
	}

	/**
	 * Guarda/Modifica un llamado a convocatoria
	 */
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Guardo los datos en DB
		if (request.getParameter("id") == "" || request.getParameter("id").equals("")) {
			crear(mapping, form, request, response);
		}
		else {
			modificar(mapping, form, request, response);
			request.setAttribute("id", request.getParameter("id"));
		}

		// Limpio el formulario para que no muestre los datos que podrian estar
		// en session
		((DynaActionForm) form).getMap().clear();

		return mapping.findForward("success");
	}

	/**
	 * Guarda un nuevo llamado a convocatoria
	 */
	@SuppressWarnings("unchecked")
	private void crear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = new LlamadoConvocatoriaBean();

		BeanUtils.setProperty(llamadoConvocatoriaBean, "id", request.getParameter("id"));// TODO:
																							// esto
																							// se
																							// puede
																							// reempñazar
																							// con
																							// un
																							// campo
																							// con
																							// nombre
																							// ID y
																							// hidden

		llamadoConvocatoriaBean.setIdInstrumentoDef(FormUtil.getLongValue(form, "idInstrumentoDef"));
		llamadoConvocatoriaBean.setIdentificador(BeanUtils.getProperty(form, "identificador"));
		llamadoConvocatoriaBean.setEsIdeaProyectoPitec(FormUtil.getBooleanValue(form, "esIdeaProyectoPitec"));

		llamadoConvocatoriaBean.setFechaApertura(FormUtil.getDateValue(form, "fechaApertura"));
		llamadoConvocatoriaBean.setFechaCierre(FormUtil.getDateValue(form, "fechaCierre"));

		llamadoConvocatoriaBean.setDenominacion(BeanUtils.getProperty(form, "denominacion"));
		llamadoConvocatoriaBean.setModalidad(BeanUtils.getProperty(form, "modalidad"));
		
		llamadoConvocatoriaBean.setParaProyectoHistorico(FormUtil.getBooleanValue(form,"paraProyectoHistorico"));

		llamadoConvocatoriaBean.setPermiteFinanciamientoBancario(FormUtil.getBooleanValue(form, "permiteFinanciamientoBancario_"));

		Boolean permiteComision = FormUtil.getBooleanValue(form, "permiteComision_");

		llamadoConvocatoriaBean.setPermiteComision(permiteComision);

		if (permiteComision) {
			llamadoConvocatoriaBean.setIdComision(FormUtil.getLongValue(form, "idComision"));
		}

		llamadoConvocatoriaBean.setPermiteSecretaria(FormUtil.getBooleanValue(form, "permiteSecretaria_"));
		llamadoConvocatoriaBean.setPermiteAdjudicacion(FormUtil.getBooleanValue(form, "permiteAdjudicacion_"));

		llamadoConvocatoriaBean.setPlazoReadmision(FormUtil.getIntegerValue(form, "plazoReadmision"));
		llamadoConvocatoriaBean.setPlazoReconsideracion(FormUtil.getIntegerValue(form, "plazoReconsideracion"));
		llamadoConvocatoriaBean.setFirmaContrato(FormUtil.getIntegerValue(form, "firmaContrato"));

		llamadoConvocatoriaBean.setPermiteMultipleJurisdiccion(FormUtil.getBooleanValue(form, "permiteMultipleJurisdiccion"));
		llamadoConvocatoriaBean.setPermitePropiciado(FormUtil.getBooleanValue(form, "permitePropiciado"));
		
		BigDecimal montoFinanciamientoTotal = FormUtil.getBigDecimalValue(form, "montoFinanciamientoTotal");
		if(montoFinanciamientoTotal != null)
			llamadoConvocatoriaBean.setMontoFinanciamientoTotal(montoFinanciamientoTotal);

		String tipoDistribucionFinanciamiento = FormUtil.getStringValue(form, "tipoDistribucionFinanciamiento");
		llamadoConvocatoriaBean.setTipoDistribucionFinanciamiento(tipoDistribucionFinanciamiento);
		// obtengo los datos cargados en distribucion financiamiento
		List distribucionFinanciamientos = new ArrayList();
		if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.REGIONAL)) {
			distribucionFinanciamientos = DistribucionFinanciamientoDTOAssembler.buildListBeans((List) SessionHelper.getDistribucionFinanciamientoRegion(request));
		}
		else if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.JURISDICCIONAL)) {
			distribucionFinanciamientos = DistribucionFinanciamientoDTOAssembler.buildListBeans((List) SessionHelper.getDistribucionFinanciamientoJurisdiccion(request));
		}

		llamadoConvocatoriaBean.setTipoFinanciamiento(TipoFinanciamientoInstrumento.valueOf(BeanUtils.getProperty(form, "codigoTipoFinanciamiento")));
		llamadoConvocatoriaBean.setMontoFinanciamiento(FormUtil.getBigDecimalValue(form, "montoFinanciamiento"));
		llamadoConvocatoriaBean.setFechaReconocimientoGastos(FormUtil.getDateValue(form, "fechaReconocimientoGastos"));
		llamadoConvocatoriaBean.setProporcionApoyo(FormUtil.getBigDecimalValue(form, "proporcionApoyo"));

		llamadoConvocatoriaBean.setTipoFinanciamientoAsignacion(TipoFinanciamientoAsignacionInstrumento.valueOf(FormUtil.getStringValue(form, "codigoTipoFinanciamientoAsignacion")));

		llamadoConvocatoriaBean.setPeriodoGracia(BeanUtils.getProperty(form, "periodoGracia"));
		llamadoConvocatoriaBean.setPlazoEjecucion(BeanUtils.getProperty(form, "plazoEjecucion"));
		llamadoConvocatoriaBean.setPlazoAmortizacion(BeanUtils.getProperty(form, "plazoAmortizacion"));
		llamadoConvocatoriaBean.setTasaInteres(BeanUtils.getProperty(form, "tasaInteres"));
		llamadoConvocatoriaBean.setGarantia(BeanUtils.getProperty(form, "garantia"));
		llamadoConvocatoriaBean.setIdMatrizPresupuesto(FormUtil.getLongValue(form, "idMatrizPresupuesto"));
		llamadoConvocatoriaBean.setObservaciones(BeanUtils.getProperty(form, "observaciones"));
		
		String emerix = FormUtil.getStringValue(form, "emerix");
		if (!StringUtil.isEmpty(emerix))
			llamadoConvocatoriaBean.setEmerix(new Long(emerix));
		else llamadoConvocatoriaBean.setEmerix(null);
		llamadoConvocatoriaBean.setVarianteEmerix(BeanUtils.getProperty(form, "varianteEmerix"));
		llamadoConvocatoriaBean.setIdCartera(getLongValue(form, "idCartera"));

		// cargo datos de version
		InstrumentoVersionBean versionBean = new InstrumentoVersionBean();
		versionBean.setVersion(FormUtil.getLongValue(form, "version"));
		versionBean.setFecha(DateTimeUtil.getDate());
		versionBean.setCodigo(FormUtil.getStringValue(form, "codigo"));
		versionBean.setDescripcion(FormUtil.getStringValue(form, "descripcion"));

		llamadoConvocatoriaBean.setEstado(EstadoLlamadoConvocatoria.ACTIVO);

		// obtengo datos de la distribucion de tipo de proyecto
		List distribucionTipoProyectos = DistribucionTipoProyectoDTOAssembler.buildListBeans(((List) SessionHelper.getDistribucionTipoProyecto(request)));

		getServicio().save(llamadoConvocatoriaBean, versionBean, distribucionFinanciamientos, distribucionTipoProyectos);
	}

	/**
	 * Modifica los datos de un llamado a convocatoria
	 */
	@SuppressWarnings("unchecked")
	private void modificar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO: SS-el responsable de esta accion debe controlar que joraca hace
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(FormUtil.getLongValue(form, "id"));

		llamadoConvocatoriaBean.setIdInstrumentoDef(FormUtil.getLongValue(form, "idInstrumentoDef"));
		llamadoConvocatoriaBean.setIdentificador(BeanUtils.getProperty(form, "identificador"));
		llamadoConvocatoriaBean.setEsIdeaProyectoPitec(FormUtil.getBooleanValue(form, "esIdeaProyectoPitec"));

		llamadoConvocatoriaBean.setFechaApertura(FormUtil.getDateValue(form, "fechaApertura"));
		llamadoConvocatoriaBean.setFechaCierre(FormUtil.getDateValue(form, "fechaCierre"));

		llamadoConvocatoriaBean.setDenominacion(BeanUtils.getProperty(form, "denominacion"));
		llamadoConvocatoriaBean.setModalidad(BeanUtils.getProperty(form, "modalidad"));

		llamadoConvocatoriaBean.setParaProyectoHistorico(FormUtil.getBooleanValue(form,"paraProyectoHistorico"));
		
		llamadoConvocatoriaBean.setPermiteFinanciamientoBancario(FormUtil.getBooleanValue(form, "permiteFinanciamientoBancario_"));

		Boolean permiteComision = FormUtil.getBooleanValue(form, "permiteComision_");
		llamadoConvocatoriaBean.setPermiteComision(permiteComision);

		if (permiteComision) {
			llamadoConvocatoriaBean.setIdComision(FormUtil.getLongValue(form, "idComision"));
		}
		else {
			llamadoConvocatoriaBean.setIdComision(null);
		}

		llamadoConvocatoriaBean.setPermiteSecretaria(FormUtil.getBooleanValue(form, "permiteSecretaria_"));
		llamadoConvocatoriaBean.setPermiteAdjudicacion(FormUtil.getBooleanValue(form, "permiteAdjudicacion_"));

		llamadoConvocatoriaBean.setPlazoReadmision(FormUtil.getIntegerValue(form, "plazoReadmision"));
		llamadoConvocatoriaBean.setPlazoReconsideracion(FormUtil.getIntegerValue(form, "plazoReconsideracion"));
		llamadoConvocatoriaBean.setFirmaContrato(FormUtil.getIntegerValue(form, "firmaContrato"));

		llamadoConvocatoriaBean.setPermiteMultipleJurisdiccion(FormUtil.getBooleanValue(form, "permiteMultipleJurisdiccion"));
		llamadoConvocatoriaBean.setPermitePropiciado(FormUtil.getBooleanValue(form, "permitePropiciado"));
		llamadoConvocatoriaBean.setMontoFinanciamientoTotal(FormUtil.getBigDecimalValue(form, "montoFinanciamientoTotal"));

		String tipoDistribucionFinanciamiento = FormUtil.getStringValue(form, "tipoDistribucionFinanciamiento");
		llamadoConvocatoriaBean.setTipoDistribucionFinanciamiento(tipoDistribucionFinanciamiento);

		// obtengo los datos cargados en distribucion financiamiento
		List distribucionFinanciamientos = new ArrayList();
		if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.REGIONAL)) {
			distribucionFinanciamientos = DistribucionFinanciamientoDTOAssembler.buildListBeans((List) SessionHelper.getDistribucionFinanciamientoRegion(request));
		}
		else if (tipoDistribucionFinanciamiento.equals(Constant.TipoDistribucionFinanciamiento.JURISDICCIONAL)) {
			distribucionFinanciamientos = DistribucionFinanciamientoDTOAssembler.buildListBeans((List) SessionHelper.getDistribucionFinanciamientoJurisdiccion(request));
		}

		llamadoConvocatoriaBean.setTipoFinanciamiento(TipoFinanciamientoInstrumento.valueOf(BeanUtils.getProperty(form, "codigoTipoFinanciamiento")));
		llamadoConvocatoriaBean.setMontoFinanciamiento(FormUtil.getBigDecimalValue(form, "montoFinanciamiento"));
		llamadoConvocatoriaBean.setFechaReconocimientoGastos(FormUtil.getDateValue(form, "fechaReconocimientoGastos"));
		llamadoConvocatoriaBean.setProporcionApoyo(FormUtil.getBigDecimalValue(form, "proporcionApoyo"));

		llamadoConvocatoriaBean.setTipoFinanciamientoAsignacion(TipoFinanciamientoAsignacionInstrumento.valueOf(FormUtil.getStringValue(form, "codigoTipoFinanciamientoAsignacion")));

		llamadoConvocatoriaBean.setPeriodoGracia(BeanUtils.getProperty(form, "periodoGracia"));
		llamadoConvocatoriaBean.setPlazoEjecucion(BeanUtils.getProperty(form, "plazoEjecucion"));
		llamadoConvocatoriaBean.setPlazoAmortizacion(BeanUtils.getProperty(form, "plazoAmortizacion"));
		llamadoConvocatoriaBean.setTasaInteres(BeanUtils.getProperty(form, "tasaInteres"));
		llamadoConvocatoriaBean.setGarantia(BeanUtils.getProperty(form, "garantia"));
		llamadoConvocatoriaBean.setIdMatrizPresupuesto(FormUtil.getLongValue(form, "idMatrizPresupuesto"));
		llamadoConvocatoriaBean.setObservaciones(BeanUtils.getProperty(form, "observaciones"));

		String emerix = FormUtil.getStringValue(form, "emerix");
		if (!StringUtil.isEmpty(emerix))
			llamadoConvocatoriaBean.setEmerix(new Long(emerix));
		else llamadoConvocatoriaBean.setEmerix(null);
		llamadoConvocatoriaBean.setVarianteEmerix(BeanUtils.getProperty(form, "varianteEmerix"));
		llamadoConvocatoriaBean.setIdCartera(FormUtil.getLongValue(form, "idCartera"));
		

		// cargo datos de version
		// TODO: SS-cuando esten los DTo implementar bien este load
		// InstrumentoVersionBean versionBean =
		// llamadoConvocatoriaBean.getInstrumentoVersionActual();
		InstrumentoVersionBean versionBean = (InstrumentoVersionBean) getServicio().load(InstrumentoVersionBean.class, llamadoConvocatoriaBean.getIdInstrumentoVersion());
		versionBean.setIdInstrumento(llamadoConvocatoriaBean.getId());
		versionBean.setVersion(FormUtil.getLongValue(form, "version"));
		versionBean.setFecha(DateTimeUtil.getDate());
		versionBean.setCodigo(FormUtil.getStringValue(form, "codigo"));
		versionBean.setDescripcion(FormUtil.getStringValue(form, "descripcion"));

		// obtengo datos de la distribucion de tipo de proyecto para modificar
		List distribucionTipoProyectos = DistribucionTipoProyectoDTOAssembler.buildListBeans(((List) SessionHelper.getDistribucionTipoProyecto(request)));

		getServicio().update(llamadoConvocatoriaBean, versionBean, distribucionFinanciamientos, distribucionTipoProyectos);
	}

	/**
	 * Obtiene los datos del instrumento seleccionado
	 */
	public ActionForward getData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dyna = (DynaActionForm) form;

		InstrumentoDefDAO dao = (InstrumentoDefDAO) WebContextUtil.getBeanFactory().getBean("instrumentoDefDao");
		InstrumentoDefBean instrumentoDefBean = new InstrumentoDefBean();

		String idInstrumento = BeanUtils.getProperty(form, "idInstrumentoDef");

		if (idInstrumento != null && !idInstrumento.equals("")) {
			instrumentoDefBean = (InstrumentoDefBean) dao.read(new Long(idInstrumento));
		}

		// Guardo el campo "id" del form para evitar que quede pisado por
		// el "id" del instrumento seleccionado
		String id = BeanUtils.getProperty(form, "id");

		BeanUtils.copyProperties(form, instrumentoDefBean);

		// Actualizo el checkbox y texto del selector de comisiones
		String txtComision = "";
		String permiteComision = "false";

		if (instrumentoDefBean != null && instrumentoDefBean.getPermiteComision() != null) {
			if (instrumentoDefBean.getPermiteComision()) {
				permiteComision = "true";
			}
		}
		dyna.set("txtComision", txtComision);
		dyna.set("permiteComision", permiteComision);
		dyna.set("observaciones", instrumentoDefBean.getObservacion());

		// Vuelvo a setear en el form el "id" original del form
		dyna.set("id", id);

		setCollections(form, request);

		return mapping.findForward("success");
	}

	/**
	 * Carga el listado de DTO para la distribución financiamiento
	 */
	@SuppressWarnings("unchecked")
	private void setDistribucionFinanciamiento(HttpServletRequest request, Long idInstrumento) {
		DistribucionFinanciamientoDAO financiamientoDAO = (DistribucionFinanciamientoDAO) getServicio().getDao(DistribucionFinanciamientoBean.class);
		List<DistribucionFinanciamientoBean> distribucionBeanList = financiamientoDAO.findByInstrumento(idInstrumento);

		if (distribucionBeanList.isEmpty()) {
			return;
		}

		List<DistribucionFinanciamientoDTO> distribucionDtoList = DistribucionFinanciamientoDTOAssembler.buildListDtos(distribucionBeanList);

		if (((DistribucionFinanciamientoBean) distribucionBeanList.get(0)).getIdJurisdiccion() != null) {
			SessionHelper.setDistribucionFinanciamientoJurisdiccion(request, distribucionDtoList);
		}
		else {
			SessionHelper.setDistribucionFinanciamientoRegion(request, distribucionDtoList);
		}
	}

	/**
	 * Carga el listado de DTO para la distribución de tipo de proyectos
	 */
	@SuppressWarnings("unchecked")
	private void setDistribucionTipoProyecto(HttpServletRequest request, Long idInstrumento) {
		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) getServicio().getDao(DistribucionTipoProyectoBean.class);
		List<DistribucionTipoProyectoBean> distribucionBeanList = tipoProyectoDAO.findByInstrumento(idInstrumento);

		if (distribucionBeanList.isEmpty()) {
			return;
		}

		List<DistribucionTipoProyectoDTO> distribucionDtoList = DistribucionTipoProyectoDTOAssembler.buildListDtos(distribucionBeanList);

		SessionHelper.setDistribucionTipoProyecto(request, distribucionDtoList);
	}

	/**
	 * Selectores la pantalla
	 */
	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {
		ComisionDAO comisionesDao = (ComisionDAO) WebContextUtil.getBeanFactory().getBean("comisionDao");
		String idComision = BeanUtils.getProperty(form, "idComision");

		if (idComision != null && !idComision.equals("")) {
			ComisionBean comisionBean = new ComisionBean();
			comisionBean = (ComisionBean) comisionesDao.read(new Long(idComision));
			BeanUtils.copyProperty(form, "idComision", comisionBean.getId());
			BeanUtils.copyProperty(form, "txtComision", comisionBean.getDenominacion());
		}
	}

	private String serializeMatrizCriterio(List<MatrizCriterioBean> matrizCriterioList) {

		StringBuffer buffer = new StringBuffer();
		for (MatrizCriterioBean bean : matrizCriterioList) {

			buffer.append(bean.getNombre() + "---" + bean.getId() + "|||");
		}
		if (buffer.length() > 0) {
			buffer.replace(buffer.length() - 3, buffer.length(), "");
		}

		return buffer.toString();
	}

	/**
	 * Carga los combos que se muestran en la pantalla
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(ActionForm form, HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		MatrizPresupuestoDAO matricesDao = (MatrizPresupuestoDAO) WebContextUtil.getBeanFactory().getBean("matrizPresupuestoDao");
		Collection matrices = new ArrayList();
		matrices.addAll(collectionHandler.getMatrices(matricesDao));
		request.setAttribute("matrices", matrices);

		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		Collection tipoProyectoList = new ArrayList();
		tipoProyectoList.addAll(collectionHandler.getTiposProyectos(tipoProyectoDAO));
		request.setAttribute("tiposProyectos", tipoProyectoList);

		InstrumentoDefDAO instrumentoDefDAO = (InstrumentoDefDAO) WebContextUtil.getBeanFactory().getBean("instrumentoDefDao");
		Collection instrumentosDef = new ArrayList();
		instrumentosDef.addAll(collectionHandler.getInstrumentosDef(instrumentoDefDAO));
		request.setAttribute("instrumentosDef", instrumentosDef);

		Collection tiposFinanciamiento = new ArrayList();
		tiposFinanciamiento.addAll(collectionHandler.getComboFormulario(TipoFinanciamientoInstrumento.class, false));
		request.setAttribute("tiposFinanciamiento", tiposFinanciamiento);

		Collection tiposFinanciamientoAsignacion = new ArrayList();
		tiposFinanciamientoAsignacion.addAll(collectionHandler.getComboFormulario(TipoFinanciamientoAsignacionInstrumento.class, false));
		request.setAttribute("tiposFinanciamientoAsignacion", tiposFinanciamientoAsignacion);

		MatrizCriterioDAO matrizCriterioDAO = (MatrizCriterioDAO) WebContextUtil.getBeanFactory().getBean("matrizCriterioDao");
		List<MatrizCriterioBean> matrizCriterioList = matrizCriterioDAO.getAll();
		BeanUtils.setProperty(form, "criterioList", serializeMatrizCriterio(matrizCriterioList));

		CarteraDAO carteraDAO = (CarteraDAO) WebContextUtil.getBeanFactory().getBean("carteraDao");
		Collection carteras = new ArrayList();
		Long idCartera = null;
		if (!Util.isBlank(request.getParameter("id"))) {
			LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) getServicio().load(new Long(request.getParameter("id")));
			idCartera = llamadoConvocatoriaBean.getIdCartera();
		}
		carteras.addAll(collectionHandler.getCartera(carteraDAO,idCartera));
		request.setAttribute("carteras", carteras);
	}
}