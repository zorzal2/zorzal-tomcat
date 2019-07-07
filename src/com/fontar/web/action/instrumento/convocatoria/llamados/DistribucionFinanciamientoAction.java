package com.fontar.web.action.instrumento.convocatoria.llamados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.Constant;
import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.fontar.data.impl.domain.bean.RegionBean;
import com.fontar.data.impl.domain.dto.DistribucionFinanciamientoDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.form.instrumento.ventanilla.DistribucionFinanciamientoForm;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericAction;

public class DistribucionFinanciamientoAction extends GenericAction {

	private static final String TYPE_PARAMETER = "tipo";

	private static final String ASIGNACION_PARAMETER = "asignacion";

	private static final String PARAMETER_REGIONAL = "REGIONAL";

	private static final String PARAMETER_JURISDICCION = "JURISDICCIONAL";

	private static final String DISTRIBUCION_FINANCIAMIENTO_LIST = "distribucionFinanciamientoList";

	private static final String MONTO_TOTAL_PARAMETER = "montoTotal";

	@Override
	@SuppressWarnings("unused")
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaActionForm dyna = (DynaActionForm)form;
		// me lo guarda en una variable y luego debo setear un parámetro para
		// cerrar la ventana
		DistribucionFinanciamientoForm myForm = (DistribucionFinanciamientoForm) form;
		myForm.setWindowClose("true");

		String type = FormUtil.getStringValue(form, TYPE_PARAMETER);
		if (type.equals(PARAMETER_JURISDICCION)) {
			SessionHelper.setDistribucionFinanciamientoJurisdiccion(request, myForm.getDistribucionFinanciamientos());
		}
		else if (type.equals(PARAMETER_REGIONAL)) {
			SessionHelper.setDistribucionFinanciamientoRegion(request, myForm.getDistribucionFinanciamientos());
		}
		request.setAttribute(DISTRIBUCION_FINANCIAMIENTO_LIST, myForm.getDistribucionFinanciamientos());
	}

	@SuppressWarnings("unchecked")
	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		saveToken(request);
		String type = (String) request.getParameter(TYPE_PARAMETER);
		String asignacion = (String) request.getParameter(ASIGNACION_PARAMETER);
		String montoTotal = (String) request.getParameter(MONTO_TOTAL_PARAMETER);
		BeanUtils.setProperty(form, TYPE_PARAMETER, type);
		BeanUtils.setProperty(form, "asignacion", asignacion);
		request.setAttribute("asignacion", asignacion);
		BeanUtils.setProperty(form, MONTO_TOTAL_PARAMETER, montoTotal);

		List distribucion = null;

		if (type.equals(PARAMETER_JURISDICCION)) {

			distribucion = (List) SessionHelper.getDistribucionFinanciamientoJurisdiccion(request);
			if (distribucion == null) {
				// TODO Habría q poner la carga del dto en un nivel inferior

				JurisdiccionBean jurisdiccionBean;
				DistribucionFinanciamientoDTO distribucionDTO;
				distribucion = new ArrayList();
				// Si no existen jurisdicciones cargadas
				Collection jurisdiccionList = getServicio().getAll(JurisdiccionBean.class);
				Iterator iterator = jurisdiccionList.iterator();
				while (iterator.hasNext()) {
					jurisdiccionBean = (JurisdiccionBean) iterator.next();
					distribucionDTO = new DistribucionFinanciamientoDTO();
					if (jurisdiccionBean.getActivo()) {
						distribucionDTO.setNombre(jurisdiccionBean.getDescripcion());
						distribucionDTO.setIdJurisdiccion(jurisdiccionBean.getId().toString());
						distribucionDTO.setMonto(Constant.Numericas.CERO);
						distribucionDTO.setPorcentaje(Constant.Numericas.CERO);
						distribucion.add(distribucionDTO);
					}
				}
			}
		}
		else if (type.equals(PARAMETER_REGIONAL)) {

			distribucion = (List) SessionHelper.getDistribucionFinanciamientoRegion(request);
			if (distribucion == null) {
				// TODO Habría q poner la carga del dto en un nivel inferior

				RegionBean regionBean;
				DistribucionFinanciamientoDTO distribucionDTO;
				distribucion = new ArrayList();
				// Si no existen jurisdicciones cargadas
				Collection jurisdiccionList = getServicio().getAll(RegionBean.class);
				Iterator iterator = jurisdiccionList.iterator();
				while (iterator.hasNext()) {
					regionBean = (RegionBean) iterator.next();
					if (regionBean.getActivo()) {
						distribucionDTO = new DistribucionFinanciamientoDTO();
						distribucionDTO.setNombre(regionBean.getNombre());
						distribucionDTO.setIdRegion(regionBean.getId().toString());
						distribucionDTO.setMonto(Constant.Numericas.CERO);
						distribucionDTO.setPorcentaje(Constant.Numericas.CERO);
						distribucion.add(distribucionDTO);
					}
				}
			}
		}
		request.setAttribute(DISTRIBUCION_FINANCIAMIENTO_LIST, distribucion);
	}
}