package com.fontar.web.action.instrumento.convocatoria.llamados;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.web.action.instrumento.VisualizarInstrumentoAction;

/**
 * Muestra los datos de un llamado a convocatoria
 * sin la posibilidad de edición de los mismos.<br> 
 * @author ssanchez
 */
public class VisualizarLlamadoConvocatoriaAction extends VisualizarInstrumentoAction {
	
	/**
	 * Muestra los datos de un llamado a convocatoria.<br>
	 * El llamado mostrado corresponde al <i>id</i> 
	 * que el método recibe como parámetro en el request.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDatosGenerales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		cargarLlamadoConvocatoria(request);
		
		return super.visualizarDatosGenerales(mapping, form, request, response);
	}
	
	/**
	 * Muestra los datos de distribución de financiamiento
	 * de un llamado a convocatoria.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDistribucionFinanciamiento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		cargarLlamadoConvocatoria(request);
		
		return super.visualizarDistribucionFinanciamiento(mapping, form, request,response);
	}
	
	/**
	 * Muestra los datos de distribución de tipo proyecto
	 * de un llamado a convocatoria.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDistribucionTipoProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		cargarLlamadoConvocatoria(request);
		
		return super.visualizarDistribucionTipoProyecto(mapping, form, request,response);
	}	

	/**
	 * Setea un <code>LlamadoConvocatoriaBean</code> en el
	 * request obtenido mediante el <i>idLlamado</i>.<br>
	 * @param request
	 * @return
	 */
	private Long cargarLlamadoConvocatoria(HttpServletRequest request){

		Long idLlamado = getIdInstrumento(request);
		
		LlamadoConvocatoriaBean llamadoConvocatoria = (LlamadoConvocatoriaBean) instrumentosServicio.obtenerInstrumento(idLlamado);
		request.setAttribute("instrumento", llamadoConvocatoria);
		
		return idLlamado;
	}
}
