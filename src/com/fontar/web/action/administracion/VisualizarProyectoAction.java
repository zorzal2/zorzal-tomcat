package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadIntervinientesDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.entidad.TipoEntidad;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;

/**
 * 
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */

public class VisualizarProyectoAction extends ProyectoBaseAction {

	private BitacoraDAO bitacoraDao;
	
	private EntidadDAO entidadDao;
	
	private EntidadIntervinientesDAO entidadIntervinientesDao;
	
	public void setBitacoraDAO(BitacoraDAO bitacoraDAO) {
		this.bitacoraDao = bitacoraDAO;
	}
	public void setEntidadDao(EntidadDAO entidadDao) {
		this.entidadDao = entidadDao;
	}
	public void setEntidadIntervinientesDao(EntidadIntervinientesDAO entidadIntervinientesDao) {
		this.entidadIntervinientesDao = entidadIntervinientesDao;
	}
	/**
	 * Obtiene los datos de un proyecto para su visualización
	 */
	@SuppressWarnings("unchecked")
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}

		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		
		//Chequeo de seguridad
		verifyGranted(visualizacionProyectoDto.getIdInstrumento(), "PROYECTOS-VISUALIZAR");
		
		request.setAttribute(PROYECTO, visualizacionProyectoDto);

		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());
		setCollections(request);

		List<BitacoraBean> list;
		bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		entidadDao = (EntidadDAO)ContextUtil.getBean("entidadDao");
		entidadIntervinientesDao = (EntidadIntervinientesDAO)ContextUtil.getBean("entidadIntervinientesDao");
		list = bitacoraDao.findByProyectoTipo(new Long(idProyecto), TipoBitacora.ENTIDAD_INTERVINIENTE.getName());
		Collection intervinientes = new LinkedList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				EntidadIntervinientesBean intervinientesBean = (EntidadIntervinientesBean) entidadIntervinientesDao.read(list.get(i).getId());
				if (intervinientesBean.getActivo()) {
					EntidadBean entidadBean = (EntidadBean) entidadDao.read(intervinientesBean.getIdEntidad());
					TipoEntidad tipo = intervinientesBean.getTipoEntidad();
					String[] datos = { entidadBean.getDenominacion(), tipo.getDescripcion(), intervinientesBean.getRelacion(), intervinientesBean.getFuncion() };
					intervinientes.add(datos);
				}
			}
		}
		request.setAttribute("intervinientes", intervinientes);
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
