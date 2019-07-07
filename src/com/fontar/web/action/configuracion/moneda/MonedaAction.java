package com.fontar.web.action.configuracion.moneda;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.MonedaService;
import com.fontar.data.impl.domain.dto.MonedaDTO;

public class MonedaAction extends MappingDispatchAction {

	private MonedaService monedaService;

	public void setMonedaService(MonedaService monedaService) {
		this.monedaService = monedaService;
	}
	
	public ActionForward agregar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		return mapping.findForward("success");
	}

	public ActionForward editar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		MonedaDTO moneda = monedaService.load(id);
		
		dynaForm.set("tipoMoneda", moneda.getTipoMoneda());
		dynaForm.set("descripcion", moneda.getDescripcion());
		dynaForm.set("codigoEmerix", moneda.getCodigoEmerix());
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		MonedaDTO moneda = new MonedaDTO();
		moneda.setId((Long) dynaForm.get("id"));
		moneda.setTipoMoneda(dynaForm.getString("tipoMoneda"));
		moneda.setDescripcion(dynaForm.getString("descripcion"));
		moneda.setCodigoEmerix(dynaForm.getString("codigoEmerix"));
		
		monedaService.update(moneda);
		
		return mapping.findForward("success");
	}

	public ActionForward crear(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		MonedaDTO moneda = new MonedaDTO();
		moneda.setTipoMoneda(dynaForm.getString("tipoMoneda"));
		moneda.setDescripcion(dynaForm.getString("descripcion"));
		moneda.setCodigoEmerix(dynaForm.getString("codigoEmerix"));
		
		monedaService.create(moneda);
		
		return mapping.findForward("success");
	}

	public ActionForward inventario(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		List<MonedaDTO> all = monedaService.getAll();
		
		request.setAttribute("collection", all);
		
		return mapping.findForward("success");
	}
}
