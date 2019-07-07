/**
 * 
 */
package com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;
import com.fontar.util.ResourceManager;
import com.pragma.util.StringUtil;

public class PresupuestoRubroDetalleDTO {
	final PresupuestoRubroDTO decoratedObject;
	private ProyectoPresupuestoDetalleDTO parent;
	public PresupuestoRubroDetalleDTO(PresupuestoRubroDTO decoratedObject, ProyectoPresupuestoDetalleDTO parent) {
		this.decoratedObject = decoratedObject;
		this.parent = parent;
	}
	public PresupuestoRubroDTO getDecoratedObject() {
		return decoratedObject;
	}
	public Long getId() {
		return decoratedObject.getId();
	}
	public String getMontoContraparte() {
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoContraparte());
	}
	public String getMontoParte() {
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoParte());
	}
	public String getMontoTotal() {
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoParte()+decoratedObject.getMontoContraparte());
	}
	public RubroDTO getRubro() {
		return decoratedObject.getRubro();
	}
	public String getDetalleLink() {
		return "<A href=\"#\" onclick=\"verItems('"+parent.getLocation()+"', "+getRubro().getId()+")\"><img src='images/visualizar.gif' title='"+ResourceManager.getAltResource("app.alt.verItems")+"' border=0 /></A>";
	}
}