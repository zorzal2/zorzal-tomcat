package com.fontar.web.decorator.seguimientos.controlAdquisicion;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;

/**
 * Muestra links en cada fila del inventario
 * @author ssanchez 
 */
public class ItemsProcedimientoWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		
		ProcedimientoItemBean item = (ProcedimientoItemBean) this.getCurrentRowObject();
		//VisualizarLink visualizarLink = new VisualizarLink("VisualizarDatosGeneralesProcedimiento.do", "app.alt.visualizarProcedimiento", item.getId());
		//visualizarLink.setSimplePermissionsRequired("SSPROCEDIMIENTOS-VISUALIZAR");
		VisualizarLink visualizarLink = new VisualizarLink("HistoriaPACInvBreve.do?OPCION_CORTA=true&ID_PROYECTO="+ item.getIdProyecto(),"app.alt.visualizarPac",item.getPacItem().getId());
		visualizarLink.setSimplePermissionsRequired("PAC-HISTORIA-VISUALIZAR");
		return visualizarLink.displayValue();
	}
	
	public String getResultadoUffaBid() {

		String resultadoUffaBid = null;
		
		ProcedimientoItemBean procedimientoItem = (ProcedimientoItemBean) this.getCurrentRowObject();
		
		if(ResultadoFontar.APROB_PEND_UFFA.equals(procedimientoItem.getResultadoFontar())) {
			resultadoUffaBid = procedimientoItem.getResultadoUffa()!=null ? procedimientoItem.getResultadoUffa().getDescripcion() : null;
		} else if(ResultadoFontar.APROB_PEND_BID.equals(procedimientoItem.getResultadoFontar())) {
			resultadoUffaBid = procedimientoItem.getResultadoBid()!=null ? procedimientoItem.getResultadoBid().getDescripcion() : null;
		}
		
		return resultadoUffaBid;
	}
	
	public BigDecimal getMontoUffaBid() {

		BigDecimal montoUffaBid = null;
		
		ProcedimientoItemBean procedimientoItem = (ProcedimientoItemBean) this.getCurrentRowObject();
		
		if(ResultadoFontar.APROB_PEND_UFFA.equals(procedimientoItem.getResultadoFontar())) {
			montoUffaBid = procedimientoItem.getMontoUffa();
		} else if(ResultadoFontar.APROB_PEND_BID.equals(procedimientoItem.getResultadoFontar())) {
			montoUffaBid = procedimientoItem.getMontoBid();
		}
		
		return montoUffaBid;
	}
	
}
