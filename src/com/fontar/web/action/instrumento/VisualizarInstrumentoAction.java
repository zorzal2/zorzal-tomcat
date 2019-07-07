package com.fontar.web.action.instrumento;

import static com.fontar.data.impl.assembler.DistribucionTipoProyectoDTOAssembler.buildListDtos;
import static com.pragma.util.StringUtil.formatTwoDecimalForPresentation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.data.api.dao.DistribucionFinanciamientoDAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.impl.assembler.DistribucionFinanciamientoDTOAssembler;
import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.dto.DistribucionFinanciamientoDTO;
import com.fontar.data.impl.domain.dto.DistribucionTipoProyectoDTO;
import com.fontar.util.SessionHelper;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Muestra los datos de un instrumento (<code>Llamado
 * a Convocotoria</code> o <code>Ventanilla Permanente</code>
 * sin la posibilidad de edición de los mismos.<br> 
 * @author ssanchez
 */
public class VisualizarInstrumentoAction extends BaseMappingDispatchAction {
	
	private static final String DISTRIBUCION_FINANCIAMIENTO_LIST = "distribucionFinanciamientoList";
	private static final String TIPO_PROYECTO_LIST = "tipoProyectoList";
	private static final String MONTO_TOTAL_ACUMULADO = "montoTotalAcumulado";

	protected AdministrarInstrumentosServicio instrumentosServicio;
	
	public void setInstrumentosServicio(AdministrarInstrumentosServicio instrumentosServicio) {
		this.instrumentosServicio = instrumentosServicio;
	}

	/**
	 * Muestra los datos de un instrumento.<br>
	 * El instrumento mostrado corresponde al <i>id</i> 
	 * que el método recibe como parámetro en el request.<br>
	 * @author ssanchez
	 */
	public ActionForward visualizarDatosGenerales(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}

	/**
	 * Muestra los datos de distribución de financiamiento
	 * de un instrumento.<br>
	 * @author ssanchez
	 */
	public ActionForward visualizarDistribucionFinanciamiento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idInstrumento = getIdInstrumento(request);
		
		DistribucionFinanciamientoDAO financiamientoDAO = (DistribucionFinanciamientoDAO) WebContextUtil.getBeanFactory().getBean("distribucionFinanciamientoDao");
		List<DistribucionFinanciamientoBean> distribucionBeanList = financiamientoDAO.findByInstrumento(idInstrumento);
		
		if (!distribucionBeanList.isEmpty()) {
			List<DistribucionFinanciamientoDTO> distribucionDtoList = DistribucionFinanciamientoDTOAssembler.buildListDtos(distribucionBeanList);
	
			request.setAttribute(DISTRIBUCION_FINANCIAMIENTO_LIST, distribucionDtoList);
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * Muestra los datos de distribución de tipo proyecto
	 * de un instrumento.<br>
	 * @author ssanchez
	 */
	public ActionForward visualizarDistribucionTipoProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idInstrumento = getIdInstrumento(request);
		
		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) WebContextUtil.getBeanFactory().getBean("distribucionTipoProyectoDao");
		List<DistribucionTipoProyectoBean> tipoProyectoList = tipoProyectoDAO.findByInstrumento(idInstrumento);

		if (!tipoProyectoList.isEmpty()) {
			List<DistribucionTipoProyectoDTO> distribucionDtoList = buildListDtos(tipoProyectoList);

			request.setAttribute(TIPO_PROYECTO_LIST, distribucionDtoList);
			request.setAttribute(MONTO_TOTAL_ACUMULADO, getTotalAcumulado(distribucionDtoList));
		}
		
		return mapping.findForward("success");
	}

	/**
	 * Obtiene el total de montos asignados de 
	 * los tipos de proyecto de un instrumento.<br>
	 * @param tipoProyectoList
	 * @return el total... 
	 */
	protected String getTotalAcumulado(List tipoProyectoList) {
		float total = 0F;
		float montoTotal = 0F;
		if (tipoProyectoList != null) {
			for (Object object : tipoProyectoList) {
				montoTotal = ((DistribucionTipoProyectoDTO) object).getMontoTotalAsignado() == null ? 0
							: ((DistribucionTipoProyectoDTO) object).getMontoTotalAsignado().floatValue();
				total = total + montoTotal;
			}
		}

		return formatTwoDecimalForPresentation(total);
	}
	
	/**
	 * Obtiene el <i>idInstrumento</i> desde
	 * el request y lo pone en la sesión.<br>
	 * @param request
	 * @return el id... 
	 */
	protected Long getIdInstrumento(HttpServletRequest request){

		Long idInstrumento = null;
		
		if (validateParameter(request,"id")){
			idInstrumento = new Long(request.getParameter("id"));
			SessionHelper.setIdInstrumento(request,idInstrumento);
		} else {
			idInstrumento = SessionHelper.getIdInstrumento(request);
		}
		
		return idInstrumento;
	}	
}
