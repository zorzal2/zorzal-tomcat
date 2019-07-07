package com.fontar.web.action.administracion.paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.impl.assembler.PaqueteCabeceraAssembler;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.dto.PaqueteCabeceraDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.pragma.web.WebContextUtil;

/**
 * Control de Paquete por un directorio: Controlar en fontar significa sacar y
 * poner proyectos a un paquete!!?
 * @author ssanchez
 */
public class ControlarPaqueteAction extends PaqueteBaseTaskAction {

	/**
	 * Persiste el control del paquete
	 */
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Long idPaquete = new Long(BeanUtils.getProperty(form, "id"));
		// obtengo los proyectos siguen seleccionados desde la vista
		String[] proyectoSeleccionados = ((DynaActionForm) form).getStrings("proyectoArray");

		wfPaqueteServicio.controlarPaquete(idPaquete, proyectoSeleccionados, idTaskInstance);
	}

	/**
	 * Muestra los proyectos de un paquete y los proyectos que pueden ser
	 * empaquetados
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		// levanto el paquete
		PaqueteDTO paqueteDto = wfPaqueteServicio.obtenerPaquete(idTaskInstance);
		String tipoPaquete = paqueteDto.getTipo();
		request.setAttribute("tipoPaquete", tipoPaquete);

		// obtengo el listado de proyecto segun los filtros instrumento y
		// tratamiento
		Long instrumento = paqueteDto.getIdInstrumento();
		String tratamiento = paqueteDto.getTratamiento().getDescripcion();

		List<ProyectoFilaModificacionPaqueteDTO> proyectosList = new ArrayList<ProyectoFilaModificacionPaqueteDTO>();
		// traigo para mostrar los proyectos que son parte del paquete...
		proyectosList = wfPaqueteServicio.obtenerProyectosPaquete(idTaskInstance, tipoPaquete);
		// ...y los que son candidatos a ser agregados al paquete
		proyectosList.addAll(wfPaqueteServicio.obtenerProyectos(instrumento, tratamiento, tipoPaquete));

		// lleno los combos
		setCollections(request);

		// usados para validar que los filtros se hayan aplicados
		// TODO: SS-implementar el control de filtrado. Avisar a Javier
		request.setAttribute("instrumentoFiltrado", instrumento);
		request.setAttribute("tratamientoFiltrado", tratamiento);

		// seteo en el form los valores de id, instrumento y tratamiento del
		// paquete
		((DynaActionForm) form).set("id", paqueteDto.getId().toString());
		((DynaActionForm) form).set("instrumento", paqueteDto.getDescInstrumento());
		((DynaActionForm) form).set("tratamiento", tratamiento);

		// guardo la collection en el request
		request.setAttribute("proyectosList", proyectosList);

		// uso una variable para modificar el action del mostrar
		request.setAttribute("mostrarAciton", "PaqueteEditar");
		
		PaqueteCabeceraDTO cabeceraDTO = (PaqueteCabeceraDTO) wfPaqueteServicio.getPaqueteDTO(idTaskInstance,new PaqueteCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PAQUETE,cabeceraDTO);
	}

	/**
	 * LLeno los combos para agregar y editar
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		InstrumentoDAO instrumentoDAO = (InstrumentoDAO) WebContextUtil.getBeanFactory().getBean("instrumentoDao");

		String tipoPaquete;
		if (request.getParameter("tipoPaquete") != null && !request.getParameter("tipoPaquete").equals("")) {
			tipoPaquete = request.getParameter("tipoPaquete");
		}
		else {
			tipoPaquete = request.getAttribute("tipoPaquete").toString();
		}

		Collection instrumentoList = new ArrayList();
		if (tipoPaquete.equals(TipoPaquete.COMISION.name())) {
			instrumentoList.addAll(collectionHandler.getInstrumentoComision(instrumentoDAO));
		}
		else if (tipoPaquete.equals(TipoPaquete.SECRETARIA.name())) {
			instrumentoList.addAll(collectionHandler.getInstrumentoSecretaria(instrumentoDAO));
		}
		else {
			instrumentoList.addAll(collectionHandler.getInstrumentoActivos(instrumentoDAO));
		}

		Collection tratamientoList = new ArrayList();
		tratamientoList.addAll(collectionHandler.getTratamientosPaquete(TipoPaquete.class, tipoPaquete));

		request.setAttribute("instrumentos", instrumentoList);
		request.setAttribute("tratamientos", tratamientoList);
	}

}
