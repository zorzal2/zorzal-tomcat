package com.fontar.web.action.seguimientos.evaluaciones.rendiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Permite modificar los montos aprobados 
 * (de evaluación) de un seguimento.<br>
 * @author ssanchez
 */
public abstract class EditarMontoRendicionEvaluacionSeguimientoAction extends BaseMappingDispatchAction{

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
	 * Muestra los datos de <i>montoContraparteEvalucion</i>, 
	 * <i>montroParteEvaluacion</i> y <i>observaciones</i> para
	 * modificarlas.<br> 
	 */
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idRendicion = getIdRendicion(request);
		
		RendicionCuentasBean cuentasBean = administrarSeguimientoServicio.obtenerRendicionCuentas(idRendicion);
		
		BeanUtils.copyProperties(form,cuentasBean);
		request.setAttribute("id", cuentasBean.getId());

		return mapping.findForward("success");
	}
	
	/**
	 * Persiste los datos de <i>montoContraparteEvalucion</i>, 
	 * <i>montroParteEvaluacion</i> y <i>observaciones</i> cargados
	 * en el formulario.<br> 
	 */
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idRendicion = getIdRendicion(request);
		
		RendicionCuentasBean rendicionCuentasBean = administrarSeguimientoServicio.obtenerRendicionCuentas(idRendicion);
		
		setMontoParteEvaluacion(form, rendicionCuentasBean);
		setMontoContraparteEvaluacion(form, rendicionCuentasBean);
		rendicionCuentasBean.setObservaciones(FormUtil.getStringValue(form,"observaciones"));

		administrarSeguimientoServicio.cargarRendicion(rendicionCuentasBean);
		
		return mapping.findForward("success");
	}

	protected abstract void setMontoContraparteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception;
	protected abstract void setMontoParteEvaluacion(ActionForm form, RendicionCuentasBean rendicionCuentasBean) throws Exception;
}
