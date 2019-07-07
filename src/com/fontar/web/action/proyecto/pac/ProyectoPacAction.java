package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.math.BigDecimal;
import java.sql.SQLException;
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

import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.PacDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.dto.PacDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.util.SessionHelper;
import com.fontar.util.Util;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.action.GenericABMAction;

public class ProyectoPacAction extends GenericABMAction<PacBean> {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	@SuppressWarnings("unchecked")
	public ProyectoPacAction(Class<PacBean> type) {
		super(type);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages errors = getErrors(request);		
		// si tengo errores en el form es que vengo de una validación NO exitosa
		Long idProyecto = null;
		
		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		PragmaDynaValidatorForm test = (PragmaDynaValidatorForm) form;
		if (!test.getDynaClass().getName().equalsIgnoreCase("ProyectoPACAnularDynaForm")) {
			if (errors.isEmpty()){
				BeanUtils.copyProperties(form, new PacDTO());
				BeanUtils.setProperty(form, "montoParte", null);
				SessionHelper.setIdPac(request,null);	
			}
			setCollections(request);
	//		request.setAttribute("idProyecto", idProyecto);
	//		BeanUtils.setProperty(form, "idProyecto", idProyecto);
			PacDAO pacDAO = (PacDAO) ContextUtil.getBean("pacDao");
			List<PacBean> list = pacDAO.findByProyecto(idProyecto);
			if (list.isEmpty()) {
				request.setAttribute("item", 1);
			}
			request.setAttribute("item", list.size()+1);
	//		BeanUtils.setProperty(form, "item", list.size()+1);
			BeanUtils.setProperty(form, "idProyecto", idProyecto);
		}
		else {
			String sPacId;
			if (errors.isEmpty()){
				sPacId = request.getParameter("id");
			}
			else {
				sPacId = SessionHelper.getIdPac(request).toString();
			}
			VisualizarPacItemDTO pacDTO = administrarPACServicio.obtenerDatosItemTabla(new Long(sPacId));
			request.setAttribute("pac", pacDTO);
		}
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//super.dataEditar(mapping, form, request, response);
		setCollections(request);
		Long idProyecto = null;

		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
//		PacDAO pacDAO = (PacDAO) ContextUtil.getBean("pacDao");
//		ActionMessages errors = getErrors(request);
		// si tengo errores en el form es que vengo de una validación NO exitosa
		String sIdPac = request.getParameter("id");
		if (Util.isBlank(sIdPac)) {
			sIdPac = SessionHelper.getIdPac(request).toString();
		}
		Long idPac = new Long(sIdPac);
		
		PacDTO pacDTO = administrarPACServicio.loadPac(idPac);
		request.setAttribute("item", pacDTO.getItem());
		request.setAttribute("descripcion", pacDTO.getDescripcion());
		request.setAttribute("etapa", pacDTO.getEtapa());
		request.setAttribute("rubro", pacDTO.getRubro().getNombre());
		request.setAttribute("idRubro", pacDTO.getRubro().getId());
		BeanUtils.setProperty(form, "id", pacDTO.getId());
		BeanUtils.setProperty(form, "montoParte", pacDTO.getMontoFontar());
		BeanUtils.setProperty(form, "idTipoAdquisicion", pacDTO.getIdTipoAdquisicion());
		BeanUtils.setProperty(form, "fecha", StringUtil.formatDate(pacDTO.getFecha()));
		SessionHelper.setIdPac(request, idPac);
	}

	@SuppressWarnings("unchecked")
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PacDTO dto = new PacDTO();
		Long id = SessionHelper.getIdPac(request);
		PragmaDynaValidatorForm test = (PragmaDynaValidatorForm) form;
		if (test.getDynaClass().getName().equalsIgnoreCase("ProyectoPACAnularDynaForm")) {
			administrarPACServicio.anularBean(id,BeanUtils.getProperty(form,"observaciones"));
		}
		else {
//		BeanUtils.setProperty(bean, "id", request.getParameter("id"));
			String fecha = BeanUtils.getProperty(form,"fecha");
			dto.setFecha(DateTimeUtil.getDate(fecha));
			dto.setIdRubro(FormUtil.getLongValue(form,"idRubro"));
			dto.setIdTipoAdquisicion(FormUtil.getLongValue(form,"idTipoAdquisicion"));
			dto.setItem(FormUtil.getLongValue(form,"item"));
			dto.setMontoFontar(FormUtil.getBigDecimalValue(form,"montoParte"));
			dto.setEtapa(BeanUtils.getProperty(form,"etapa"));
			dto.setDescripcion(BeanUtils.getProperty(form,"descripcion"));
			dto.setIdProyecto(SessionHelper.getIdProyecto(request));
			dto.setCodigoEstado(EstadoPacItem.PENDIENTE_DE_COMPRA);
			dto.setFechaEstado(dto.getFecha());
			dto.setEsPatrimonio(false);
			// Guardo los datos en DB
			if (id == null) {
				administrarPACServicio.createPac(dto);
			}
			else {
				administrarPACServicio.actualizarPac(dto,id);
			}
		}
	}

	@Override
	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long idProyecto = null;

		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		Long id = new Long(request.getParameter("id"));
		VisualizarPacItemDTO pacDTO = administrarPACServicio.obtenerDatosItemTabla(id);
		request.setAttribute("pac", pacDTO);
		// Pantalla con tabla y observaciones
		if (useToken())
			saveToken(request);
		SessionHelper.setIdPac(request,new Long(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);
		PragmaDynaValidatorForm test = (PragmaDynaValidatorForm) form;
		if (!test.getDynaClass().getName().equalsIgnoreCase("ProyectoPACAnularDynaForm")) {
			BigDecimal monto = FormUtil.getBigDecimalValue(form,"montoParte");
			ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
			Long idProyecto = SessionHelper.getIdProyecto(request);
			ProyectoBean proyectoBean = proyectoDAO.read(idProyecto);
			ProyectoPresupuestoBean presupuestoBean = proyectoBean.getProyectoPresupuesto();
			PacDAO pacDAO = (PacDAO) ContextUtil.getBean("pacDao");
			Long idPac = SessionHelper.getIdPac(request);
			if (idPac == null) {
				idPac = new Long(0);
			}
			List<PacBean> list = pacDAO.findByProyecto(idProyecto);
			for (PacBean pacBean : list) {
				if ((!(pacBean.getCodigoEstado().equals(EstadoPacItem.ANULADO))) && (!(pacBean.getId().equals(idPac)))) {
					monto = monto.add(pacBean.getMontoFontar());
				}
			}
			if (!ActionUtil.isEncryptionContextAvailable()){
				addError(messages,"app.error.encrypt");
			}
			else {
				if (monto.compareTo(presupuestoBean.getMontoSolicitado()) > 0) {
					addError(messages, "app.proyecto.pac.monto");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();
		Collection etapas = new ArrayList();
		etapas.addAll(collectionHandler.getEtapas(request.getParameter("etapa"),SessionHelper.getIdProyecto(request)));
		request.setAttribute("etapas", etapas);

		Collection rubros = new ArrayList();
		rubros.addAll(collectionHandler.getRubrosParaCargaDePac());
		request.setAttribute("rubros", rubros);

		Collection adquisiciones = new ArrayList();
		adquisiciones.addAll(collectionHandler.getAdquisiciones());
		request.setAttribute("adquisiciones", adquisiciones);

	}

//	@Override
//	protected void validateEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		super.validateEditar(mapping, form, request, response);
//		ActionMessages messages =  this.getErrors( request );
//		this.ActionUtil.checkValidEncryptionContext( messages );
//		saveErrors(request, messages);
//	}
//
//	@Override
//	protected void validateAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response, ActionMessages messages) throws Exception {
//		super.validateAgregar(mapping, form, request, response, messages);
//		this.ActionUtil.checkValidEncryptionContext( messages );
//		saveErrors(request, messages);
//	}
	
	public final ActionForward editarMontoAdjudicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PragmaDynaValidatorForm form2 = (PragmaDynaValidatorForm) form;
		Long pacId = (Long) form2.get("id");
		PacDTO pacDTO = administrarPACServicio.loadPac(pacId);
		request.setAttribute("pac", pacDTO);
		form2.set("montoAdjudicacion", pacDTO.getMontoAdjudicacion().toString());
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(SessionHelper.getIdProyecto(request));
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		return mapping.findForward("success");
	}

	public final ActionForward guardarMontoAdjudicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PragmaDynaValidatorForm form2 = (PragmaDynaValidatorForm) form;
		Long pacId = (Long) form2.get("id");
		BigDecimal montoAdjudicacion = new BigDecimal(form2.getString("montoAdjudicacion"));
		administrarPACServicio.updateMontoAdjudicacion(pacId, montoAdjudicacion);
		return mapping.findForward("success");
	}

	public AdministrarPACServicio getAdministrarPACServicio() {
		return administrarPACServicio;
	}

	public void setAdministrarPACServicio(AdministrarPACServicio administrarPACServicio) {
		this.administrarPACServicio = administrarPACServicio;
	}

	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
}
