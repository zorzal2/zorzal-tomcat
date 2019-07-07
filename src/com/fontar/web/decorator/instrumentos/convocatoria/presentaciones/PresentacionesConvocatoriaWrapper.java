package com.fontar.web.decorator.instrumentos.convocatoria.presentaciones;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.CargarProyectoLink;
import com.fontar.web.decorator.link.impl.EditarLink;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Presentaciones
 * Convocatorias
 */
public class PresentacionesConvocatoriaWrapper extends TableDecoratorSupport {

	
	//FIXME eliminar
	public String getLinkCargarProyecto() throws UnsupportedEncodingException {
		PresentacionConvocatoriaBean lObject = (PresentacionConvocatoriaBean) this.getCurrentRowObject();
		
		String link = "";						
		String action = "CargarProyecto.do";
		
		if (lObject.estaPresentada()){									
			InstrumentoBean instrumento = lObject.getInstrumento();
			
			if ((!instrumento.esVentanilla()) && ((LlamadoConvocatoriaBean) instrumento).getEsIdeaProyectoPitec()) {  			
					action = "CargarIdeaProyectoPitec.do";
			}
			CargarProyectoLink cargarProyectoLink = new CargarProyectoLink(action,"app.alt.cargarProyecto",lObject.getId());
			cargarProyectoLink.setIdPresentacion( lObject.getId() );
			cargarProyectoLink.setPermissionsByInstrumentoRequired(lObject.getId(), "PROYECTOS-CARGAR");
			return cargarProyectoLink.displayValue();

//				
//			link = "<a href=\""+action+".do?" + "idPresentacion="
//					+ URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8")
//					+ "&tipoProyecto=PresentacionConvocatoria" + "\"><img src='images/cargarproyecto.gif' alt="
//					+ ResourceManager.getAltResource("app.alt.cargarProyecto") + " border=0 /></a>";		
		}
//		
		return link;
	}

	public String getLinkEditarPresentacion(){
		
		PresentacionConvocatoriaBean lObject = (PresentacionConvocatoriaBean) this.getCurrentRowObject();
		if (lObject.estaPresentada()) {
			EditarLink editarLink = new EditarLink("PresentacionConvocatoriaEditar.do","app.alt.editarPresentacion",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getIdInstrumento(), "PRESENTACIONCONVOCATORIA-EDITAR");
			return editarLink.displayValue();
		}
		return "";
	}

	public String getLinkEliminarPresentacion() throws UnsupportedEncodingException {
		
		PresentacionConvocatoriaBean lObject = (PresentacionConvocatoriaBean) this.getCurrentRowObject();
		if (lObject.estaPresentada()) {
			BorrarLink borrarLink = new BorrarLink("PresentacionConvocatoriaBorrar.do","app.alt.eliminarPresentacion",lObject.getId());
			borrarLink.setPermissionsByInstrumentoRequired(lObject.getIdInstrumento(), "PRESENTACIONCONVOCATORIA-ELIMINAR");
			return borrarLink.displayValue();
		}
		return "";
	}

}
