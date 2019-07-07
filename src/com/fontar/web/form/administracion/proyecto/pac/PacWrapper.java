package com.fontar.web.form.administracion.proyecto.pac;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;

import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.data.api.dao.DesembolsoUFFADAO;
import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.AnularLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.PagarLink;
import com.fontar.web.decorator.link.impl.PedirDesembolsoLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * Wrapper de Pac
 * @author ttoth
 * @version 1.01, 21/12/06
 */
public class PacWrapper extends TableDecoratorSupport {

    DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");

    public String getLinkEditar() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		//Precondicion: Solo puede editarse si el proyecto no se termino
		if(lObject.getProyecto().estaTerminado()) return null;
		
		EditarLink editarLink;
		String link = null;
		
		switch(lObject.getCodigoEstado()) {
		case PENDIENTE_DE_COMPRA:
			editarLink = new EditarLink("ProyectoPACEditar.do","app.alt.editarPac",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getProyecto().getIdInstrumento(), "PAC-MODIFICAR");
			link = editarLink.displayValue();
			break;
		case EN_PROCESO_DE_COMPRA:
			/*
			 * La precondicion es que tenga ya un monto de adjudicacion pero que no este
			 * en espera de resultado por parte de la Uffa o el Bid
			 */
			AdministrarPACServicio pacService = ContextUtil.getBean("administrarPACService");
			if(lObject.getMontoAdjudicacion()!=null && !pacService.enEsperaDeAprobacionExterna(lObject.getId())) {
				editarLink = new EditarLink("ProyectoPACEditarMontoAdjudicacion.do","app.alt.editarMontoAdjudicacion",lObject.getId());
				editarLink.setPermissionsByInstrumentoRequired(lObject.getProyecto().getIdInstrumento(), "PAC-MODIFICAR");
				link = editarLink.displayValue();
			}
			break;
		case ADJUDICADO:
			editarLink = new EditarLink("ProyectoPACEditarMontoAdjudicacion.do","app.alt.editarMontoAdjudicacion",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getProyecto().getIdInstrumento(), "PAC-MODIFICAR");
			link = editarLink.displayValue();
			break;
		case ANULADO:
		case DESEMBOLSADO:
			//Ya no pueden editarse estos montos.
			break;
		default:
			throw new RuntimeException("No implementado. Código no alcanzable");			
		}
		return link;
	}

	public String getLinkPedirDesembolso() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		
		//Precondicion: Solo puede pedirse desembolso si el proyecto no se termino
		if(lObject.getProyecto().estaTerminado()) return null;
		
		String link = null;
		if (lObject.getCodigoEstado().equals(EstadoPacItem.ADJUDICADO)) {
			PedirDesembolsoLink editarLink = new PedirDesembolsoLink("ProyectoPACPedir.do","app.alt.pedirDesembolso",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getProyecto().getIdInstrumento(), "PAC-DESEMBOLSO");
			link = editarLink.displayValue();
		}
		return link;
	}

	public String getLinkPatrimonio() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		
		//Precondicion: Solo puede editarse si el proyecto no se termino
		if(lObject.getProyecto().estaTerminado()) return null;
		
		StringBuffer link = new StringBuffer();
		if (!lObject.getCodigoEstado().equals(EstadoPacItem.ANULADO)) {
			link.append("<a href=\"");
			link.append("ProyectoPACInventario.do");
			link.append('?');
			link.append("id=");
			link.append( URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
			link.append('&');
			link.append("patrimonio='true'");
			link.append("\"><img src='");
			link.append("images/patrimonio.gif");
			link.append("' title=");
			link.append("'Ingresar a Patrimonio' border=0 /></a>");
		}
		return link.toString();
//		String link = null;
//		if (!lObject.getCodigoEstado().equals(EstadoPacItem.ANULADO)) {
//			EditarLink editarLink = new EditarLink("ProyectoPACInventario.do","app.alt.patrimonio",lObject.getId());
//			editarLink.setPermissionRequired("PAC-PATRIMONIO");
//			link = editarLink.displayValue();
//		}
//		return link;
	}

	public String getLinkIngresarPago() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		String link = null;
		/*
		 * Se muestra solo si:
		 *   - El proyecto no está terminado
		 *   - Hay desembolsos pedidos.
		 *   - El objeto no esta anulado.
		 */
		
		//Precondicion: Solo puede pagar si el proyecto no se termino
		if(lObject.getProyecto().estaTerminado()) return null;
		
		boolean show = false;
		boolean hayPagosPendientes = false;
		DesembolsoUFFADAO desembolsoUFFADAO = (DesembolsoUFFADAO) ContextUtil.getBean("desembolsoDao");
		List<DesembolsoUFFABean> list = desembolsoUFFADAO.findByPac(lObject.getId());
		if (!list.isEmpty()) {
			for (DesembolsoUFFABean bean : list) {
				if (bean.getMontoDesembolsado() != null) {
					//Tiene pedidos de desembolso
					show = true;
					if (bean.getMontoPago() == null) {
						//Algunos de los desembolsos pedidos no fueron pagados
						hayPagosPendientes = true;
					}
				}
			}
		}
		show =
			show 
			&& !lObject.getCodigoEstado().equals(EstadoPacItem.ANULADO);

		if (show) {
			PagarLink editarLink = new PagarLink("ProyectoPACPago.do",(hayPagosPendientes? "app.alt.ingresarPago" : "app.alt.modificarPagos"),lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getProyecto().getIdInstrumento(), "PAC-PAGO");
			link = editarLink.displayValue();
		}
		return link;
	}

	public String getLinkBorrar() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();

		//Precondicion: Solo puede anularse si el proyecto no se termino
		if(lObject.getProyecto().estaTerminado()) return null;
		
		AdministrarPACServicio pacService = ContextUtil.getBean("administrarPACService");
		if(pacService.bloqueadoPorProcedimiento(lObject.getId())) return null;
		
		String link = null;
		if (!lObject.getCodigoEstado().equals(EstadoPacItem.ANULADO)) {
			AnularLink anularLink = new AnularLink("ProyectoPACAnular.do","app.alt.anularPac",lObject.getId());
			anularLink.setSimplePermissionsRequired("PAC-ANULAR");
			link = anularLink.displayValue();
		}
		return link; 
	}
	
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		String link = null;
		VisualizarLink visualizarLink = new VisualizarLink("HistoriaPACInventario.do","app.alt.visualizarPac",lObject.getId());
		visualizarLink.setSimplePermissionsRequired("PAC-HISTORIA-VISUALIZAR");
		link = visualizarLink.displayValue();
		return link; 
	}
	
	public String getRubro() {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		return lObject.getRubro().getNombre();
	}

	public String getMontoAdjudicacionMoneda() {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		String link = "";
		if (lObject.getMontoAdjudicacion() != null) {
			link = twoPlaces.format(lObject.getMontoAdjudicacion());
			if (lObject.getMoneda() != null) {
				link = link + " " + lObject.getMoneda().getTipoMoneda();
			}
		}
		return link;
	}

	public String getMontoPagado() {
		PacBean pacItem = (PacBean) this.getCurrentRowObject();
		BigDecimal montoPagado = pacItem.getMontoPagado();
		return StringUtil.formatMoneyForPresentation(montoPagado);
	}

	public String getMontoADesembolsarConMoneda() {
		PacBean lObject = (PacBean) this.getCurrentRowObject();
		AdministrarPACServicio pacService = ContextUtil.getBean("administrarPACService");
		BigDecimal montoPendienteADesembolsar = null;
		try {
			montoPendienteADesembolsar = pacService.getMontoPedidoPendienteDeDesembolsoEnMonedaDeAdjudicacion(lObject);
		} catch (FaltanCotizacionesException e) {
			return ResourceManager.getInformationalResource("app.msg.pac.SinCotizacion");
		}
		if (montoPendienteADesembolsar != null) {
			return twoPlaces.format(montoPendienteADesembolsar) + " " + lObject.getMoneda().getTipoMoneda();
		} else return null;
	}

	
}
