package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.action.GenericABMAction;

public class PedirDesembolsoAction extends GenericABMAction<DesembolsoUFFABean> {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	@SuppressWarnings("unchecked")
	public PedirDesembolsoAction(Class<DesembolsoUFFABean> type) {
		super(type);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages errors = getErrors(request);		

		setCollections(request);
		Long idProyecto = null;

		String id = null;
		
		if (errors.isEmpty()) {
			id = request.getParameter("id");
//			String test = "";
////			BeanUtils.copyProperties(form, new DesembolsoUFFADTO(
//			BeanUtils.setProperty(form, "montoDesembolsado", test);
//			BeanUtils.setProperty(form, "cuota", test);
//			BeanUtils.setProperty(form, "idMoneda", test);
//			BeanUtils.setProperty(form, "ordenPago", test);
//			BeanUtils.setProperty(form, "fechaPedido", test);
		}
		else {
			id = FormUtil.getStringValue(form,"id");
		}
		Long idPac = new Long(id);
		idProyecto = SessionHelper.getIdProyecto(request);

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		VisualizarPacItemDTO pacDTO = administrarPACServicio.obtenerDatosItemTabla(idPac);
		request.setAttribute("pac", pacDTO);
		
		Collection<DesembolsoUFFADTO> desembolsos = administrarPACServicio.obtenerDesembolsos(idPac);
		
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
		Boolean esUltimo = FormUtil.getBooleanValue(form, "esAnticipo");
		dto.setFechaPedido(FormUtil.getDateValue(form, "fechaPedido"));
		dto.setIdMoneda(FormUtil.getLongValue(form, "idMoneda"));
		dto.setMontoDesembolsado(FormUtil.getBigDecimalValue(form, "montoDesembolsado"));
		dto.setOrdenPago(FormUtil.getStringValue(form, "ordenPago"));
		administrarPACServicio.createOrUpdateDesembolso(dto, esUltimo);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);
		//Levanto los datos de la pagina
		Long idPac = FormUtil.getLongValue(form, "id");
		Integer cuota =  FormUtil.getIntegerValue(form, "cuota");
		BigDecimal montoDesembolsado = FormUtil.getBigDecimalValue(form, "montoDesembolsado");
		Long idMoneda = FormUtil.getLongValue(form, "idMoneda");
		Date fechaPedido = FormUtil.getDateValue(form, "fechaPedido");
		
		try {
			Boolean tieneMargenDisponibleParaDesembolsar;
			if (administrarPACServicio.existePedidoDeDesembolsoConEseNumeroDeCuota(idPac, cuota)) {
				//addError(messages, "app.pac.existeCuota");
				//La cuota ya existe. Veo si puede ser reemplazada por el nuevo valor sin romper nada.
				tieneMargenDisponibleParaDesembolsar = administrarPACServicio.puedeCambiarDesembolso(idPac, cuota, montoDesembolsado, idMoneda, fechaPedido);
			} else {
				tieneMargenDisponibleParaDesembolsar = administrarPACServicio.puedeDesembolsar(idPac, montoDesembolsado, idMoneda, fechaPedido);
			}
	
			if (!tieneMargenDisponibleParaDesembolsar) {
				addError(messages, "app.pac.bajoZero");
			}
		} catch(FaltanCotizacionesException e) {
			Collection<MonedaDTO> monedasSinCotizacion = e.monedasSinCotizacion();
			ArrayList<String> monedasSinCotizacionDesc = new ArrayList<String>();
			for(MonedaDTO moneda : monedasSinCotizacion) monedasSinCotizacionDesc.add(moneda.getDescripcion());
			if(monedasSinCotizacion.size()==1) {
				addError(messages, "app.pac.noHayCotizacionDeUnaMoneda", monedasSinCotizacionDesc.get(0));
			} else {
				addError(
						messages, 
						"app.pac.noHayCotizacionDeVariasMonedas", 
						StringUtil.join(monedasSinCotizacionDesc, ", ")
					);
			}
		}
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
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection monedas = new ArrayList();
		monedas.addAll(collectionHandler.getMonedas());
		request.setAttribute("monedas", monedas);

	}
}
