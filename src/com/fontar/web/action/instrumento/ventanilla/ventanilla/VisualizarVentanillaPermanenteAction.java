package com.fontar.web.action.instrumento.ventanilla.ventanilla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;
import com.fontar.web.action.instrumento.VisualizarInstrumentoAction;

/**
 * Muestra los datos de una ventanilla permanente
 * sin la posibilidad de edición de los mismos.<br> 
 * @author ssanchez
 */
public class VisualizarVentanillaPermanenteAction extends VisualizarInstrumentoAction {
	
	/**
	 * Muestra los datos de una ventanilla permanente.<br>
	 * La ventanilla mostrado corresponde al <i>id</i> 
	 * que el método recibe como parámetro en el request.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDatosGenerales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		cargarVentanillaPermanente(request);
		
		return super.visualizarDatosGenerales(mapping, form, request, response);
	}
	
	/**
	 * Muestra los datos de distribución de financiamiento
	 * de una ventanilla permanente.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDistribucionFinanciamiento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		cargarVentanillaPermanente(request);
		
		return super.visualizarDistribucionFinanciamiento(mapping, form, request,response);
	}
	
	/**
	 * Muestra los datos de distribución de tipo proyecto
	 * de una ventanilla permanente.<br>
	 * @author ssanchez
	 */
	@Override
	public ActionForward visualizarDistribucionTipoProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		cargarVentanillaPermanente(request);
		
		return super.visualizarDistribucionTipoProyecto(mapping, form, request,response);
	}	

	/**
	 * Setea una <code>VentanillaPermanenteBean</code> en el
	 * request obtenido mediante el <i>idVentanilla</i>.<br>
	 * @param request
	 * @return
	 */
	private Long cargarVentanillaPermanente(HttpServletRequest request){

		Long idVentanilla = getIdInstrumento(request);
		
		VentanillaPermanenteBean ventanillaPermanente = (VentanillaPermanenteBean) instrumentosServicio.obtenerInstrumento(idVentanilla);
		request.setAttribute("instrumento", ventanillaPermanente);
		
		return idVentanilla;
	}
}
