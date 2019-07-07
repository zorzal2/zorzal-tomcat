package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class IngresarPagoAction extends GenericABMAction<DesembolsoUFFABean> {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	@SuppressWarnings("unchecked")
	public IngresarPagoAction(Class<DesembolsoUFFABean> type) {
		super(type);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages errors = getErrors(request);		

		Long idProyecto = null;

		String sId = null;
		
		if (errors.isEmpty()) {
			sId = request.getParameter("id");
//			String test = "";
////			BeanUtils.copyProperties(form, new DesembolsoUFFADTO(
//			BeanUtils.setProperty(form, "montoDesembolsado", test);
//			BeanUtils.setProperty(form, "cuota", test);
//			BeanUtils.setProperty(form, "idMoneda", test);
//			BeanUtils.setProperty(form, "ordenPago", test);
//			BeanUtils.setProperty(form, "fechaPedido", test);
		}
		else {
			sId = FormUtil.getStringValue(form,"id");
		}
		Long id = new Long(sId);
		setCollections(request,sId);
		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		VisualizarPacItemDTO pacDTO = administrarPACServicio.obtenerDatosItemTabla(id);
		request.setAttribute("pac", pacDTO);
		
		Collection<DesembolsoUFFADTO> desembolsos = administrarPACServicio.obtenerDesembolsos(id);
		
		//Ordeno los items
		Comparator<DesembolsoUFFADTO> comparator = new Comparator<DesembolsoUFFADTO>() {
			public int compare(DesembolsoUFFADTO o1, DesembolsoUFFADTO o2) {
				return o1.getCuota().compareTo(o2.getCuota());
			}};
		Collection<DesembolsoUFFADTO> desembolsosOrdenados = new TreeSet<DesembolsoUFFADTO>(comparator);
		desembolsosOrdenados.addAll(desembolsos);
			
		request.setAttribute("desembolsos", desembolsosOrdenados);
//		BeanUtils.setProperty(form,"id",id);
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//super.dataEditar(mapping, form, request, response);
		Long idProyecto = null;

		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
	}

	@SuppressWarnings("unchecked")
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DesembolsoUFFADTO dto = new DesembolsoUFFADTO();
		dto.setIdPac(FormUtil.getLongValue(form, "id"));
		dto.setCuota(FormUtil.getIntegerValue(form, "cuota"));
		dto.setEsAnticipo(FormUtil.getBooleanValue(form, "esAnticipo"));
		dto.setFechaPago(FormUtil.getDateValue(form, "fechaPago"));
		dto.setMontoPago(FormUtil.getBigDecimalValue(form, "montoPago"));
		administrarPACServicio.updateDesembolso(dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);
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
	
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request, String id) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection cuotas = new ArrayList();
		cuotas.addAll(collectionHandler.getCuotas(id));
		request.setAttribute("cuotas", cuotas);

	}
}
