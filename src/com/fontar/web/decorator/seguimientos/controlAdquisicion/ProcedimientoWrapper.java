package com.fontar.web.decorator.seguimientos.controlAdquisicion;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.util.StringUtil;

/**
 * Muestra links en cada fila del inventario
 * @author ssanchez 
 */
public class ProcedimientoWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		ProcedimientoBean procedimiento = (ProcedimientoBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarDatosGeneralesProcedimiento.do", "app.alt.visualizarProcedimiento", procedimiento.getId());
		visualizarLink.setSimplePermissionsRequired("PROCEDIMIENTOS-VISUALIZAR");
		
		return visualizarLink.displayValue();
	}

	public String getLinkVisualizarItem() throws UnsupportedEncodingException {
		ProcedimientoItemBean procedimiento = (ProcedimientoItemBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarDatosGeneralesProcedimiento.do", "app.alt.visualizarProcedimiento", procedimiento.getProcedimiento().getId());
		visualizarLink.setSimplePermissionsRequired("PROCEDIMIENTOS-VISUALIZAR");
		
		return visualizarLink.displayValue();
	}
	
	public String getRemision() throws UnsupportedEncodingException {
		ProcedimientoItemBean procedimiento = (ProcedimientoItemBean) this.getCurrentRowObject();
		String devolver = "";
		if (procedimiento.getResultadoFontar() != null) {
			if (procedimiento.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_BID)) {
				devolver = "Al BID";
			}
			if (procedimiento.getResultadoFontar().equals(ResultadoFontar.APROB_PEND_UFFA)) {
				devolver = "A la UFFA";
			}
		}
		return devolver;
	}

	public String getMontoAdjudicacion() throws UnsupportedEncodingException {
		ProcedimientoItemBean procedimiento = (ProcedimientoItemBean) this.getCurrentRowObject();
		BigDecimal montoAdjudicacion = procedimiento.getPacItem().getMontoAdjudicacion();
		if(montoAdjudicacion==null) {
			return "";
		} else {
			return StringUtil.formatMoneyForPresentation(montoAdjudicacion, procedimiento.getPacItem().getMoneda().getTipoMoneda());
		}
	}
}
