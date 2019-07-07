package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.ExpedienteDAO;
import com.fontar.data.api.dao.ExpedienteMovimientoDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ExpedienteBean;
import com.fontar.data.impl.domain.bean.ExpedienteMovimientoBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericAction;


/**
 * 
 * @author ttoth
 * @version 1.01, 19/02/06
 */

public class ExpedienteProyectoAction extends GenericAction {
		
	private ExpedienteDAO expedienteDAO;
	
	private WFProyectoServicio wfProyectoServicio;
	
	private BitacoraDAO bitacoraDAO;
	
	private ExpedienteMovimientoDAO expedienteMovimientoDAO;
	
	private PersonaDAO personaDAO;
	
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	@Override
	public void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());

		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());

		ActionMessages errors = getErrors(request);
		
		// si tengo errores en el form es que vengo de una validación NO exitosa
		if (errors.isEmpty()){

			bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
			expedienteDAO = (ExpedienteDAO)ContextUtil.getBean("expedienteDao");
			List<BitacoraBean> list = bitacoraDAO.findByProyectoTipo(new Long(idProyecto),TipoBitacora.EXPEDIENTE.getName());
			
			Collection expedientes = new LinkedList();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					ExpedienteBean bean = (ExpedienteBean) expedienteDAO.read(list.get(i).getId());
					String[] datos = { bean.getCuerpo().toString(), bean.getFolioDesde().toString(), bean.getFolioHasta().toString() };
					expedientes.add(datos);
				}
				request.setAttribute("expedientes", expedientes);
			}
			
		}
		else {
				
			String[] cuerpo = FormUtil.getStringArrayValue(form, "cuerpo");
			String[] folioDesde = FormUtil.getStringArrayValue(form, "folioDesde");
			String[] folioHasta = FormUtil.getStringArrayValue(form, "folioHasta");
	
			Collection expedientes = new LinkedList();
			if (cuerpo != null) {
				for (int i = 0; i < cuerpo.length; i++) {
					String[] datos = { cuerpo[i], folioDesde[i], folioHasta[i] };
					expedientes.add(datos);
				}
				request.setAttribute("expedientes", expedientes);
			}
		}
		
		expedienteMovimientoDAO = (ExpedienteMovimientoDAO)ContextUtil.getBean("expedienteMovimientoDao");
		personaDAO = (PersonaDAO)ContextUtil.getBean("personaDao");
		List<BitacoraBean> listMov = bitacoraDAO.findByProyectoTipo(new Long(idProyecto),TipoBitacora.MOV_EXPEDIENTE.getName());
		
		Collection movimiento = new LinkedList();
		if (!listMov.isEmpty()) {
			for (int i = 0; i < listMov.size(); i++) {
				ExpedienteMovimientoBean bean = (ExpedienteMovimientoBean) expedienteMovimientoDAO.read(listMov.get(i).getId());
				if (!bean.getEstado()) {
					PersonaBean personaBean = (PersonaBean) personaDAO.read(bean.getIdPersona());
					String[] datos = { bean.getUbicacion().toString(), personaBean.getNombre(), DateTimeUtil.formatDate(bean.getFecha()) };
					movimiento.add(datos); 
				}
			}
			request.setAttribute("movimiento", movimiento);
		}

	}

	@Override
	public void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		
		request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
		
		FormUtil.copyProperties(form, visualizacionDTO);		
		BeanUtils.setProperty(form, "id", idProyecto);
		
		
		Long idPresentacion = visualizacionDTO.getIdPresentacion(); 
		BeanUtils.setProperty(form, "idPresentacion", idPresentacion);
		
		setHeaderData(request, visualizacionDTO.getDatosProyectoDto());		
		
		bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		expedienteDAO = (ExpedienteDAO)ContextUtil.getBean("expedienteDao");
		List<BitacoraBean> list = bitacoraDAO.findByProyectoTipo(new Long(idProyecto),TipoBitacora.EXPEDIENTE.getName());
		
		Collection expedientes = new LinkedList();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				ExpedienteBean bean = (ExpedienteBean) expedienteDAO.read(list.get(i).getId());
				String[] datos = { bean.getCuerpo().toString(), bean.getFolioDesde().toString(), bean.getFolioHasta().toString() };
				expedientes.add(datos);
			}
			request.setAttribute("expedientes", expedientes);
		}
//		request.setAttribute("movimiento",expedientes);


	}

	@Override
	public void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	
	@Override
	public void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		form.validate(mapping, request);
		
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		String[] cuerpo = FormUtil.getStringArrayValue(form, "cuerpo");
		String[] folioDesde = FormUtil.getStringArrayValue(form, "folioDesde");
		String[] folioHasta = FormUtil.getStringArrayValue(form, "folioHasta");

		wfProyectoServicio.guardarExpediente(cuerpo,folioDesde,folioHasta,idProyecto);
				
	}

	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		ActionMessages errors = getErrors(request);

		super.validateGuardar(mapping, form, request, response, messages);

		String[] cuerpo = FormUtil.getStringArrayValue(form, "cuerpo");

		if (cuerpo != null) {	
			for (int i = 0; i < cuerpo.length; i++) {
				for (int j = i+1; j < cuerpo.length; j++) {
					if (cuerpo[i].equals(cuerpo[j])) {
						addError(messages, "app.proyecto.expediente.cuerpo");
					}
				}
			}
		}
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

	@Override
	protected boolean useToken() {
		return false;
	}

}
