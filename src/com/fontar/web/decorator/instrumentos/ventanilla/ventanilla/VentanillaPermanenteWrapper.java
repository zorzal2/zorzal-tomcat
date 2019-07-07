package com.fontar.web.decorator.instrumentos.ventanilla.ventanilla;

import static com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente.ANULADO;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;
import com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.CargarProyectoLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Ventanillas Permanentes
 */
public class VentanillaPermanenteWrapper extends TableDecoratorSupport {
	

	public String getLinkEdicion(){
		
		VentanillaPermanenteBean lObject = (VentanillaPermanenteBean) this.getCurrentRowObject();

		if (!lObject.getEstado().equals(EstadoVentanillaPermanente.ANULADO)) {
			EditarLink editarLink = new EditarLink("VentanillaPermanenteEditar.do","app.alt.editarVentanilla",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getId(), "VENTANILLAPERMANENTE-EDITAR");
			return editarLink.displayValue();
		}
		return "";
	}

	public String getLinkBorrado() {
		
		VentanillaPermanenteBean lObject = (VentanillaPermanenteBean) this.getCurrentRowObject();
		if (!lObject.getEstado().equals(ANULADO)) {
			BorrarLink borrarLink = new BorrarLink("VentanillaPermanenteBorrar.do","app.alt.eliminarVentanilla",lObject.getId());
			borrarLink.setPermissionsByInstrumentoRequired(lObject.getId(), "VENTANILLAPERMANENTE-ELIMINAR");
			return borrarLink.displayValue();
		}
		return "";
	}
	
	public String getLinkAdjuntos(){
		VentanillaPermanenteBean lObject= (VentanillaPermanenteBean)this.getCurrentRowObject();
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),VentanillaPermanenteBean.class);
		adjuntosLink.setPermissionsByInstrumentoRequired(lObject.getId(), "VENTANILLAPERMANENTE-EDITAR");
		return adjuntosLink.displayValue();
	}	
	
	
	public String getLinkCargarProyectoCAE(){
		VentanillaPermanenteBean lObject= (VentanillaPermanenteBean)this.getCurrentRowObject();
		if(!lObject.getAceptaIdeaProyecto()){
			CargarProyectoLink cargarProyectoLink = new CargarProyectoLink("VentanillaPermanenteCargaProyecto.do","app.alt.cargarProyecto",lObject.getId());
			cargarProyectoLink.setPermissionsByInstrumentoRequired(lObject.getId(), "PROYECTOS-CARGAR");
			return cargarProyectoLink.displayValue();
		}
		return "";
	}
	
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		VentanillaPermanenteBean ventanillaPermanente = (VentanillaPermanenteBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarVentanillaPermanenteDatosGenerales.do", "app.alt.visualizarVentanillaPermanente", ventanillaPermanente.getId());
		visualizarLink.setSimplePermissionsRequired("VENTANILLAPERMANENTE-INVENTARIO");
		return visualizarLink.displayValue();
	}
}