package com.fontar.web.decorator.seguimientos.controlAdquisicion;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

/**
 * Wrapper para items de pac.<br>
 * @author ssanchez
 */
public class ItmesPedirAutorizacionWrapper extends TableDecoratorSupport {

	/**
	 * Muestra el campo checkbox para seleccionar el items
	 * @return código html para mostrar un checkbox indexado
	 * @throws UnsupportedEncodingException
	 */
	public String getSelectorItem() throws UnsupportedEncodingException {
		PacItemDTO item = (PacItemDTO) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		String strChecked = new String();

		link.append("<input title='seleccionar/deseleccionar' type='checkbox' name='itemsArray' value='" 
				+ item.getId().toString() + "' "
				+ strChecked + ">");
		
		return link.toString();
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
	
	public String getMontoEstimadoFontar() {

		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		
		return StringUtil.formatTwoDecimalForPresentation(bean.getMontoFontar());
	}	
	
	public String getFechaEstimadaCompra() {

		ProcedimientoItemBean bean = (ProcedimientoItemBean) this.getCurrentRowObject();
		
		return DateTimeUtil.formatDate(bean.getFecha());
	}	
}
