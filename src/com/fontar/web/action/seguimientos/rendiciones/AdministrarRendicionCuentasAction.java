package com.fontar.web.action.seguimientos.rendiciones;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.fontar.util.Util;
import com.pragma.util.CollectionUtils;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.TemplateCargarGuardarAction;

/**
 * 
 * @author gboaglio
 * 
 */
public abstract class AdministrarRendicionCuentasAction extends TemplateCargarGuardarAction {

	protected AdministrarSeguimientoServicio administrarSeguimientoService;

	/**
	 * Prepara la solapa para cargar una rendición de cuentas
	 * 
	 */
	protected void executeCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception{
		
		setCabeceraVisualizacionSeguimiento(request);		
		setCollections(request);
		
		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;
		
		// Si es edición pueblo el formulario
		String strId = request.getParameter("id");
		
		if(!Util.isBlank(strId)) {
			RendicionCuentasBean rendicion = administrarSeguimientoService.obtenerRendicionCuentas(new Long(strId));
									
			dyna.set("id",rendicion.getId().toString());
			
			String descripcion = rendicion.getDescripcion();
			dyna.set("descripcion", descripcion);
			dyna.set("funcion", descripcion);
			
			dyna.set("fecha",DateTimeUtil.formatDate(rendicion.getFecha()));						
			
			PersonaBean persona = rendicion.getPersona(); 
			
			if (persona != null) {
				dyna.set("idPersonaRendicion",rendicion.getIdPersona().toString());
				dyna.set("txtPersonaRendicion", persona.getNombre());	
			}
						
			dyna.set("idRubro",rendicion.getIdRubro().toString());			
			
			Long mesesParticipacion = rendicion.getMesesParticipacion();
			if (mesesParticipacion != null) {
				dyna.set("mesesParticipacion",mesesParticipacion.toString());
			}

			setMontos(dyna, rendicion);
			
			BigDecimal montoSueldoMensual = rendicion.getMontoSueldoMensual(); 
			if (montoSueldoMensual != null) {
				String strTotalMensual = montoSueldoMensual.toString();
				
				dyna.set("sueldoMensual", strTotalMensual);
				dyna.set("costoTotalMensual", strTotalMensual);
			}
			
			dyna.set("proveedor",rendicion.getNombreProveedor());
			
			String nroFactura = rendicion.getNumeroFactura();
			if (nroFactura != null) {
				dyna.set("nroFactura", nroFactura);
			}
			
			String nroRecibo = rendicion.getNumeroRecibo();
			if (nroRecibo != null) {
				dyna.set("nroRecibo",nroRecibo);
			}
			
			dyna.set("paisProveedor",rendicion.getPaisProveedor());
			
			BigDecimal prcDedicacion = rendicion.getPorcentajeDedicacion();
			if (prcDedicacion != null) {
				dyna.set("porcentajeDedicacion",prcDedicacion.toString());
			}
			
			dyna.set("profesion",rendicion.getProfesionPersona());
			dyna.set("tipoRubro",rendicion.getRubro().getTipo().getDescripcion());
			dyna.set("tipoRendicion",rendicion.getRubro().getTipoRendicion().getDescripcion());			
						
			dyna.set("tieneCertificadoProveedor",Boolean.toString(rendicion.getTieneCertificadoProveedor()));
			
			request.setAttribute("rubro",rendicion.getRubro());
			
			
			if(!rendicion.getSeguimiento().estaRecienIniciado()){
				ActionMessages infoMessages = getMessages(request);
				addInformationMessage(infoMessages, "app.msj.rendicionEnEvaluacion");
				saveMessages(request, infoMessages);
			}
		}
	
		String strIdRubro = request.getParameter("idRubro");
		
		if (!Util.isBlank(strIdRubro)) {
			Long idRubro = new Long(strIdRubro);
			
			RubroDAO rubroDao = (RubroDAO) WebContextUtil.getBeanFactory().getBean("rubroDao");
			RubroBean rubro = rubroDao.read(idRubro);
			
			request.setAttribute("rubro", rubro);
			dyna.set("idRubro", strIdRubro);
			
		}
	}
	
	protected abstract void setMontos(PragmaDynaValidatorForm dyna, RendicionCuentasBean rendicion);

	/**
	 * Persiste una Rendición de Cuentas con los datos ingresados por el usuario
	 * 
	 */
	protected void executeGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;	
		
		TipoRendicion tipoRendicion = TipoRendicion.getByDescription(dyna.getString("tipoRendicion"));
		
		// Levanto campos del form en un mapa
		Map<String, Object> map = CollectionUtils.mapWith(
				
				"id", FormUtil.getLongValue(form,"id"),
				"idRubro", FormUtil.getLongValue(form,"idRubro"),
				"idSeguimiento", SessionHelper.getIdSeguimiento(request),				
				"fecha", FormUtil.getDateValue(form,"fecha"),
				"version", new Date(),
				
				"nroFactura", FormUtil.getStringValue(form,"nroFactura"),
				"nroRecibo", FormUtil.getStringValue(form,"nroRecibo"),			
				"proveedor", (String) dyna.get("proveedor"),					
				"montoParte", getMontoParte(form),
				"montoContraparte", getMontoContraparte(form),
				"tieneCertificado", FormUtil.getBooleanValue(form,"tieneCertificadoProveedor"),
				"paisProveedor", (String) dyna.get("paisProveedor"),
				"descripcion", FormUtil.getStringValue(form,"descripcion"),
				"idPersona", FormUtil.getLongValue(form,"idPersonaRendicion"),
				"profesion", (String) dyna.get("profesion"),
				"funcion", FormUtil.getStringValue(form,"funcion"),
				"sueldo", FormUtil.getBigDecimalValue(form,"sueldoMensual"),
				"costoTotalMensual", FormUtil.getBigDecimalValue(form, "costoTotalMensual"),
				"dedicacion", FormUtil.getBigDecimalValue(form,"porcentajeDedicacion"),
				"participacion", FormUtil.getLongValue(form,"mesesParticipacion")				
		);
		
		

		administrarSeguimientoService.cargarRendicionDesdeMap(tipoRendicion, map);
	}

	protected abstract BigDecimal getMontoContraparte(ActionForm form) throws Exception;
	protected abstract BigDecimal getMontoParte(ActionForm form) throws Exception;
	

		
	/**
	 * 	Setea en el request los datos necesarios para armar la cabecera    
	 */
	public void setCabeceraVisualizacionSeguimiento(HttpServletRequest request) {		
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		SeguimientoVisualizacionCabeceraDTO dto = administrarSeguimientoService.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);				
		
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);		
	}
	
	/**
	 *  Carga los rubros en el combo de la pantalla
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();
		
		RubroDAO rubroDao = (RubroDAO) WebContextUtil.getBeanFactory().getBean("rubroDao");		
		Collection rubros = new ArrayList();

		rubros.addAll(collectionHandler.getRubros(rubroDao));		
		request.setAttribute("rubros", rubros);		
	}
	
	/**
	 *  
	 */
	public void setAdministrarSeguimientoService(AdministrarSeguimientoServicio administrarSeguimientoService) {
		this.administrarSeguimientoService = administrarSeguimientoService;
	}

}
