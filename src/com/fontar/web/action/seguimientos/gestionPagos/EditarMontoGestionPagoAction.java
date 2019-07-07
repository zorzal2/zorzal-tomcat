package com.fontar.web.action.seguimientos.gestionPagos;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.impl.seguimientos.seguimientos.RendicionesException;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Permite modificar los montos gestionados
 * de un seguimiento.<br>
 * @author ssanchez
 */
public class EditarMontoGestionPagoAction extends BaseMappingDispatchAction{

	private AdministrarSeguimientoServicio administrarSeguimientoServicio;
	
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	/**
	 * Obtiene el <i>idRendicion</i> desde el request.<br>
	 * @param request
	 * @return idRendicion
	 */
	protected Long getIdRendicion(HttpServletRequest request){
		Long idRendicion = null;
		
		if (validateParameter(request,"id")){
			idRendicion = new Long(request.getParameter("id"));
		}
		return idRendicion;
	}
	
	/**
	 * Muestra los montos gestionados y observación
	 * para su modificación. 
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idRendicion = getIdRendicion(request);
		
		RendicionCuentasBean cuentasBean = administrarSeguimientoServicio.obtenerRendicionCuentas(idRendicion);
		
		BeanUtils.copyProperties(form,cuentasBean);
		request.setAttribute("desplegarEnParteYContraparte", !cuentasBean.getSeguimiento().getProyecto().getInstrumento().aplicaCargaAlicuotaCF());
		request.setAttribute("id", cuentasBean.getId());

		return mapping.findForward("success");
	}
	
	/**
	 * Persiste los montos gestionados y 
	 * la observación.
	 */
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		
		ActionMessages messages = getErrors(request);
		
		if (!messages.isEmpty()) {
			saveErrors(request,messages);
			return mapping.findForward("invalid");

		} else {
			Long idRendicion = getIdRendicion(request);
			
			BigDecimal montoParteGestion = FormUtil.getBigDecimalValue(form,"montoParteGestion");
			BigDecimal montoContraparteGestion = FormUtil.getBigDecimalValue(form,"montoContraparteGestion");
			BigDecimal montoTotalGestion = FormUtil.getBigDecimalValue(form,"montoTotalGestion");
			String observaciones = FormUtil.getStringValue(form,"observaciones");
			try {
				administrarSeguimientoServicio.guardarRendicionEnEvaluacionDeGestionDePago(
						idRendicion, 
						montoParteGestion, 
						montoContraparteGestion,
						montoTotalGestion,
						observaciones);
			} catch(RendicionesException e) {
				addError(messages, e.getMessageKey(), (Object[])e.getMessageParameters());
				saveErrors(request,messages);
				return mapping.findForward("invalid");
			}
			return mapping.findForward("success");
		}	
	}
}
