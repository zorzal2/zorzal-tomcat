package com.fontar.web.decorator.seguimientos.seguimientos;

import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.pragma.util.StringUtil;

/**
 * 
 * @author gboaglio
 *  
 */
public abstract class RendicionesWrapper extends TableDecoratorSupport {
	
	public String getVersion() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return StringUtil.formatDateTime(bean.getVersion());
	}
	
	public String getLinkEditar() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		String link = "";
		Boolean permiteEdicionDeMontosSolicitados = (Boolean) this.getPageContext().findAttribute("permiteEdicionDeMontosSolicitados");
		if (permiteEdicionDeMontosSolicitados) {
			EditarLink editarLink = new EditarLink(editarAction(), "app.alt.editarRendicion", bean.getId());
			Long idInstrumento = bean.getSeguimiento().getProyecto().getIdInstrumento();
			editarLink.setPermissionsByInstrumentoRequired(idInstrumento, "SEGUIMIENTOS-EDITAR");
			link = editarLink.displayValue(); 
		}
		
		return link; 
	}

	public String getLinkBorrar() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		
		String link = "";
		Boolean permiteAgregarOQuitarRendiciones = (Boolean) this.getPageContext().findAttribute("permiteAgregarOQuitarRendiciones");
		if (permiteAgregarOQuitarRendiciones) {
			BorrarLink borrarLink = new BorrarLink(borrarAction(), "app.alt.borrarRendicion", bean.getId());
			Long idInstrumento = bean.getSeguimiento().getProyecto().getIdInstrumento();
			borrarLink.setPermissionsByInstrumentoRequired(idInstrumento, "SEGUIMIENTOS-EDITAR");
			link = borrarLink.displayValue();
		}
		
		return link;
				
	}
	
	public BigDecimal getMontoTotalSolicitado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoTotal();
	}
	
	public BigDecimal getMontoParteSolicitado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoParteRendicion();
	}
	
	public BigDecimal getMontoContraparteSolicitado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoContraparteRendicion();
	}
	
	/*

	*/
	
	/*
	public BigDecimal getMontoParteAprobadoOGestionado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoParteAprobadoOGestionado();
	}
	
	public BigDecimal getMontoContraparteAprobadoOGestionado() {
		RendicionCuentasBean bean = (RendicionCuentasBean) this.getCurrentRowObject();
		return bean.getMontoContraparteAprobadoOGestionado();
	}
	*/
	protected abstract String editarAction();
	protected abstract String borrarAction();
}


