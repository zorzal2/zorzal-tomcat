package com.fontar.web.decorator.instrumentos.convocatoria.llamados;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.BorrarLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina LLamados convocatorias
 */
public class LlamadosConvocatoriaWrapper extends TableDecoratorSupport {

	public String getLinkEdicion() throws UnsupportedEncodingException {
		LlamadoConvocatoriaBean lObject = (LlamadoConvocatoriaBean) this.getCurrentRowObject();
		if (!lObject.getEstado().equals(EstadoLlamadoConvocatoria.ANULADO)) {
			EditarLink editarLink = new EditarLink("LlamadoConvocatoriaEditar.do","app.alt.editarConvocatoria",lObject.getId());
			editarLink.setPermissionsByInstrumentoRequired(lObject.getId(), "LLAMADOSCONVOCATORIA-EDITAR");
			return editarLink.displayValue();
		}
		return "";
	}

	public String getLinkBorrado() throws UnsupportedEncodingException {
		LlamadoConvocatoriaBean lObject = (LlamadoConvocatoriaBean) this.getCurrentRowObject();
		if (!lObject.getEstado().equals(EstadoLlamadoConvocatoria.ANULADO)) {
			BorrarLink borrarLink = new BorrarLink("LlamadoConvocatoriaBorrar.do","app.alt.eliminarConvocatoria",lObject.getId());
			borrarLink.setPermissionsByInstrumentoRequired(lObject.getId(), "LLAMADOSCONVOCATORIA-ELIMINAR");
			return borrarLink.displayValue();
		}
		return "";
	}

	public String getBooleanEsVigente() {
		LlamadoConvocatoriaBean lObject = (LlamadoConvocatoriaBean) this.getCurrentRowObject();

		String clave = "app.label.si";

		if (!lObject.getEsVigente()) {
			clave = "app.label.no";
		}

		StringBuffer strbEsVigente = new StringBuffer();
		strbEsVigente.append(ResourceManager.getLabelResource(clave));

		return strbEsVigente.toString().toUpperCase();
	}
	
	public String getLinkAdjuntos() throws UnsupportedEncodingException{
		LlamadoConvocatoriaBean lObject= (LlamadoConvocatoriaBean)this.getCurrentRowObject();
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),LlamadoConvocatoriaBean.class);
		adjuntosLink.setPermissionsByInstrumentoRequired(lObject.getId(), "LLAMADOSCONVOCATORIAADJUNTOS-EDITAR", "LLAMADOSCONVOCATORIAADJUNTOS-VIZUALIZAR");
		return adjuntosLink.displayValue();
	}
	
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		LlamadoConvocatoriaBean llamadoConvocatoria = (LlamadoConvocatoriaBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarLlamadoConvocatoriaDatosGenerales.do", "app.alt.visualizarLlamadoConvocatoria", llamadoConvocatoria.getId());
		visualizarLink.setSimplePermissionsRequired("LLAMADOSCONVOCATORIA-INVENTARIO");
		return visualizarLink.displayValue();
	}
}
