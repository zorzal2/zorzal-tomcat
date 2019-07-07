package com.fontar.web.action.instrumento.convocatoria.general;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.dto.DistribucionTipoProyectoDTO;
import com.fontar.util.SessionHelper;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericAction;

public class DistribucionTipoProyectoAction extends GenericAction {

	private static final String MONTO_TOTAL_INSTRUMENTO = "montoTotalInstrumento";

	private static final String MONTO_TOTAL_ACUMULADO = "montoTotalAcumulado";

	private static final String TIPO_PROYECTO_LIST = "tipoProyectoList";

	private static final String CRITERIO_LIST = "criterioList";

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataAgregar(mapping, form, request, response);
		setCollections(request);

		List tipoProyectoList = (List) SessionHelper.getDistribucionTipoProyecto(request);

		if (getErrors(request).isEmpty() && tipoProyectoList != null) {
			request.setAttribute(TIPO_PROYECTO_LIST, tipoProyectoList);
		}

		MatrizCriterioDAO matrizCriterioDAO = (MatrizCriterioDAO) WebContextUtil.getBeanFactory().getBean("matrizCriterioDao");
		List<MatrizCriterioBean> matrizCriterioList = matrizCriterioDAO.getAll();
		BeanUtils.setProperty(form, CRITERIO_LIST, serializeMatrizCriterio(matrizCriterioList));

		String montoTotalInstrumento = request.getParameter(MONTO_TOTAL_INSTRUMENTO);
		request.setAttribute(MONTO_TOTAL_INSTRUMENTO, montoTotalInstrumento);
		BeanUtils.setProperty(form, MONTO_TOTAL_INSTRUMENTO, montoTotalInstrumento);

		request.setAttribute(MONTO_TOTAL_ACUMULADO, getTotalAcumulado(tipoProyectoList));
	}

	private String getTotalAcumulado(List tipoProyectoList) {
		float total = 0F;
		float montoTotal = 0F;
		if (tipoProyectoList != null) {
			for (Object object : tipoProyectoList) {
				montoTotal = ((DistribucionTipoProyectoDTO) object).getMontoTotalAsignado() == null ? 0
						: ((DistribucionTipoProyectoDTO) object).getMontoTotalAsignado().floatValue();
				total = total + montoTotal;
			}
		}
		String totalFormat = String.valueOf(total).replaceFirst(".0", "");// TODO
																			// reveer
																			// porque
																			// puede
																			// traer
																			// problemas

		return String.valueOf(totalFormat);
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

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataEditar(mapping, form, request, response);
		setCollections(request);
	}

	@Override
	@SuppressWarnings( { "unused", "unchecked" })
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PragmaDynaValidatorForm dyna = (PragmaDynaValidatorForm) form;

		String[] idTipoProyecto = (String[]) dyna.get("idTipoProyecto");
		String[] tipoProyecto = (String[]) dyna.get("tipoProyecto");
		String[] montoTotalAsignado = (String[]) dyna.get("montoTotalAsignado");
		String[] limiteMaximoProyecto = (String[]) dyna.get("limiteMaximoProyecto");
		String[] plazoEjecucion = (String[]) dyna.get("plazoEjecucion");
		String[] idMatrizCriterio = (String[]) dyna.get("idMatrizCriterio");
		List distribucionTipoProyectoList = new ArrayList();
		DistribucionTipoProyectoDTO dto;
		for (int i = 0; i < idTipoProyecto.length; i++) {

			dto = new DistribucionTipoProyectoDTO();
			dto.setIdTipoProyecto(new Long(idTipoProyecto[i]));
			dto.setTipoProyecto(tipoProyecto[i]);
			dto.setMontoTotalAsignado(montoTotalAsignado[i].trim().equals("") ? null
					: new BigDecimal(montoTotalAsignado[i]));
			dto.setLimiteMaximoProyecto(limiteMaximoProyecto[i].trim().equals("") ? null
					: new BigDecimal(limiteMaximoProyecto[i]));
			dto.setPlazoEjecucion(new Integer(plazoEjecucion[i]));
			dto.setIdMatrizCriterio(new Long(idMatrizCriterio[i]));

			distribucionTipoProyectoList.add(dto);
		}

		SessionHelper.setDistribucionTipoProyecto(request, distribucionTipoProyectoList);

		BeanUtils.setProperty(form, "windowClose", "true");
	}

	protected void validateCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		TipoProyectoDAO tipoProyectoDAO = (TipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("tipoProyectoDao");
		MatrizCriterioDAO matrizCriterioDAO = (MatrizCriterioDAO) WebContextUtil.getBeanFactory().getBean("matrizCriterioDao");

		Collection tipoProyectoList = new ArrayList();
		Collection matrizCriterioList = new ArrayList();
		tipoProyectoList.addAll(collectionHandler.getTiposProyectos(tipoProyectoDAO)); // getTipoProyecto(tipoProyectosDAO);
		matrizCriterioList.addAll(collectionHandler.getMatrizCriterio(matrizCriterioDAO)); // getTipoProyecto(tipoProyectosDAO);

		request.setAttribute("tiposProyectos", tipoProyectoList);
		request.setAttribute("idMatrizCriterios", matrizCriterioList);
		// TODO tendría q llamarse matrizCriterio
	}
}
