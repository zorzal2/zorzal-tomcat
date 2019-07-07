package com.fontar.web.form.administracion.proyecto.desembolso;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;

import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.ProyectoDesembolsoDTO;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.EditarMonto;
import com.fontar.web.decorator.link.impl.PagarLink;
import com.pragma.toolbar.util.DateUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * Wrapper de Desembolso de Proyecto
 * @author ttoth, llobeto
 * @version 1.01, 15/08/07
 */
public class ProyectoDesembolsoWrapper extends TableDecoratorSupport {

    DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");

    public String getLinkEditar() throws UnsupportedEncodingException {
    	ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
    	
		String link = null;
		if (desembolso.getPuedeModificarseElMontoPrevisto()) {
			EditarLink editarLink = (EditarLink) new EditarLink(
					"ProyectoDesembolsoEditar.do",
					"app.alt.desembolso.editar",
					desembolso.getId()
				).openAsPopup();
			editarLink.setPermissionsByInstrumentoRequired(new Long(desembolso.getProyecto().getIdInstrumento()), "PROYECTOS-CRONOGRAMA-EDITAR");
			link = editarLink.displayValue();
		}
		return link;
	}

	public String getLinkEliminar() {
		ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		if(desembolso.getPuedeEliminarse()) {
			BorrarLink borrarLink = (BorrarLink) new BorrarLink(
					"ProyectoDesembolsoEliminar.do",
					"app.alt.desembolso.eliminar",
					desembolso.getId()
				).openAsPopup();
			borrarLink.setPermissionsByInstrumentoRequired(new Long(desembolso.getProyecto().getIdInstrumento()), "PROYECTOS-CRONOGRAMA-ELIMINAR");  
			return borrarLink.displayValue();
		} else return "";
	}

    public String getCalculateDate() {
		ProyectoDesembolsoDTO proyectoDesembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		String link = "";
		Integer plazo = proyectoDesembolso.getPlazo();
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean bean = proyectoDAO.read(proyectoDesembolso.getIdProyecto());
		if (bean.getFechaFirmaDeContrato() != null) {
			Date fecha = DateUtils.addDays(bean.getFechaFirmaDeContrato(),plazo);
			link = StringUtil.formatDate(fecha);
		}
		return link;
	}
    
    public String getFechaPagoInventario() {
    	ProyectoDesembolsoDTO proyectoDesembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
    	String link = "";
    	if (proyectoDesembolso.getFechaPago() != null) {
    		link = StringUtil.formatDate(proyectoDesembolso.getFechaPago());
    	}
    	return link;
    }

    public String getObjetivo() {
		ProyectoDesembolsoDTO proyectoDesembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		if(proyectoDesembolso.getObjetivo()==null) return "";
		return StringUtil.trimToSize(proyectoDesembolso.getObjetivo(), 50);
	}
    /**
     * Se puede desembolsar siempre y cuando el proyecto este en seguimiento
     * o con contrato firmado, el desembolso fue autorizado y no es un anticipo
     * y estoy en el mismo seguimiento que el del desembolso.
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getLinkDesembolsar() throws UnsupportedEncodingException {
    	ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
    	String link = null;
		if (desembolso.getPuedePagarse()) {
    		PagarLink pagarLink = (PagarLink) new PagarLink(
    				"ProyectoDesembolsoPagar.do",
    				"app.alt.desembolso.desembolsar",
    				desembolso.getId()
    			).openAsPopup();
    		pagarLink.setPermissionsByInstrumentoRequired(new Long(desembolso.getProyecto().getIdInstrumento()), "PROYECTOS-CRONOGRAMA-PAGAR");
    		link = pagarLink.displayValue();
    	}
    	return link;
    }
    /**
     * Permite pagar siempre y cuando el item sea un anticipo.
     * Si el item esta pagado sirve para editar el monto.
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getLinkPagarAnticipo() throws UnsupportedEncodingException {
    	ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		String link = null;
		if (desembolso.getPuedePagarseComoAnticipo()) {
			PagarLink pagarLink = (PagarLink) new PagarLink(
					"ProyectoDesembolsoPagoDeAnticipo.do",
					"app.alt.desembolso.pagarAnticipo",
					desembolso.getId()
				).openAsPopup();
			pagarLink.setPermissionsByInstrumentoRequired(new Long(desembolso.getProyecto().getIdInstrumento()), "PROYECTOS-CRONOGRAMA-PAGAR");
			link = pagarLink.displayValue();
		}
		return link;
	}

    public String getEsAnticipo() throws UnsupportedEncodingException {
    	ProyectoDesembolsoDTO lObject = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
    	if(lObject.getEsAnticipo()) return ResourceManager.getCodesResource("app.codes.valordeverdad.si");
    	else return ResourceManager.getCodesResource("app.codes.valordeverdad.no");
    	
    }

    /**
     * Permito autorizar solo si:
     * - El item no fue pagado
     * - Si el item esta autorizado, funciona como una edicion. El item
     *   debe estar autorizado en el mismo seguimiento.
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getLinkAutorizar() throws UnsupportedEncodingException {
    	ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		String link = null;
		//Si el item esta autorizado funciona como edicion.
		if (desembolso.getPuedeAutorizarse()) {
			EditarLink editarLink = (EditarLink) new EditarLink(
					"ProyectoDesembolsoIngresarAutorizacion.do?idSeguimientoDeAutorizacion="+desembolso.getIdSeguimientoCorriente(),
					"app.alt.desembolso.autorizar",
					desembolso.getId()
				).openAsPopup();
			link = editarLink.displayValue();
		}
		return link;
	}
    
    
	public EditableMoney getMontoAutorizado(){
		EditarMonto editarMonto = null;
		ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		if(desembolso.getMontoAutorizado() != null){
			editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoAutorizado",desembolso.getId() ,"ProyectoDesembolso", "montoAutorizado", desembolso.getMontoAutorizado());	
		}
		return (new EditableMoney(desembolso.getMontoAutorizado(), editarMonto));
	}
	public EditableMoney getMontoDesembolsado(){
		EditarMonto editarMonto = null;
		ProyectoDesembolsoDTO desembolso = (ProyectoDesembolsoDTO) this.getCurrentRowObject();
		if(desembolso.getMontoDesembolsado() != null){
			editarMonto = new EditarMonto("CargarEdicionMonto.do","app.alt.Editar.MontoDesembolsado",desembolso.getId() ,"ProyectoDesembolso", "montoDesembolsado", desembolso.getMontoDesembolsado());	
		}
		return (new EditableMoney(desembolso.getMontoDesembolsado(), editarMonto));
	}
}
