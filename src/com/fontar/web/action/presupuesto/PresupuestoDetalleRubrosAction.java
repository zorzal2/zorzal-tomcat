package com.fontar.web.action.presupuesto;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.presupuesto.ProyectoPresupuestoServicio;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroGeneralDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item.PresupuestoItemDTO;
import com.fontar.web.decorator.proyectos.presupuesto.PresupuestoItemWrapper;
import com.fontar.web.form.proyecto.presupuesto.PresupuestoDetalleRubroForm;
import com.pragma.toolbar.NotImplementedException;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public abstract class PresupuestoDetalleRubrosAction extends BaseMappingDispatchAction {
	private Map<Class<? extends PresupuestoItemDTO>, RubroDetalleSettings> settings = new Hashtable<Class<? extends PresupuestoItemDTO>, RubroDetalleSettings>();

	protected PresupuestoDetalleRubrosAction() {
		fillSettings();
	}
	
	protected void addSetting(Class<? extends PresupuestoItemDTO> clazz, RubroDetalleSettings setting) {
		settings.put(clazz, setting);
	} 
	
	protected abstract void fillSettings();

	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PresupuestoDetalleRubroForm detalleRubroForm = (PresupuestoDetalleRubroForm) form;
		//Obtengo el presupuesto
		ProyectoPresupuestoDTO presupuesto = null;
		String localizacion = detalleRubroForm.getLocalizacionPresupuesto();
		if(localizacion.startsWith("id:")) {
			//Id
			Long id = Long.valueOf(localizacion.substring(3));
			ProyectoPresupuestoServicio presupuestoServicio = (ProyectoPresupuestoServicio)ContextUtil.getBean("proyectoPresupuestoServicio");
			presupuesto = presupuestoServicio.load(id);
		} else {
			if(localizacion.startsWith("session:")) {
				//Presupuesto en sesion
				String path = localizacion.substring(8);
				if(path.contains(".")) {
					int indexOfDot = path.indexOf('.');
					Object base = WebContextUtil.getSession().getAttribute(path.substring(0, indexOfDot));
					presupuesto = (ProyectoPresupuestoDTO) BeanUtils.getProperty(base, path.substring(indexOfDot+1));
				} else {
					presupuesto = (ProyectoPresupuestoDTO) WebContextUtil.getSession().getAttribute(path);
				}
			} else {
				throw new NotImplementedException();
			}
		}
		List<PresupuestoRubroDTO> presupuestoRubros = new ArrayList<PresupuestoRubroDTO>();
		Long idRubro = detalleRubroForm.getIdRubro();
		PresupuestoRubroGeneralDTO basePresupuestoRubro = null;
		for(PresupuestoRubroDTO pr : presupuesto.getPresupuestoRubros()) {
			if(pr.getRubro().getId().equals(idRubro)) {
				basePresupuestoRubro = (PresupuestoRubroGeneralDTO) pr;
				request.setAttribute("nombreRubro", pr.getRubro().getNombre());
				presupuestoRubros.add(basePresupuestoRubro);
				break;
			}
		}
		
		expand(presupuestoRubros, presupuesto.getPresupuestoRubros());
		Collection<PresupuestoRubroTabla> tablas = new TreeSet<PresupuestoRubroTabla>(
				new Comparator<PresupuestoRubroTabla>() {

					public int compare(PresupuestoRubroTabla o1, PresupuestoRubroTabla o2) {
						return o1.getOrder()-o2.getOrder();
					}
					
				}
		);
		
		for (PresupuestoRubroDTO pr : presupuestoRubros) {
			PresupuestoRubroGeneralDTO presupuestoRubro = (PresupuestoRubroGeneralDTO)pr;
			
			PresupuestoRubroTabla presupuestoRubroTabla = new PresupuestoRubroTabla(pr.getRubro().getNombre(), pr.getRubro().getNroOrden().intValue());

			if(presupuestoRubro.getItems().size()==0) {
				presupuestoRubroTabla.setItems(new ArrayList<PresupuestoItemWrapper>());
				presupuestoRubroTabla.setCampos(new ArrayList<Campo>());
			} else {
				PresupuestoItemDTO itemReferencia = presupuestoRubro.getItems().get(0);
				RubroDetalleSettings detalleSettings = settings.get(itemReferencia.getClass());
				presupuestoRubroTabla.setItems(CollectionUtils.transform(presupuestoRubro.getItems(), detalleSettings.getTransformation()));
				presupuestoRubroTabla.setCampos(detalleSettings.getCampos());
			}
			tablas.add(presupuestoRubroTabla);
		}
		request.setAttribute("tablas", tablas);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}
	private void expand(List<PresupuestoRubroDTO> presupuestoRubros, Collection<PresupuestoRubroDTO> all) {
		while(!isAllExpanded(presupuestoRubros)) {
			for (PresupuestoRubroDTO rubroDTO : presupuestoRubros) {
				if(!rubroDTO.getRubro().esHoja()) {
					//requiere expansion
					//lo quito
					presupuestoRubros.remove(rubroDTO);
					//busco a sus hijos
					for (PresupuestoRubroDTO posibleHijo : all) {
						if(!posibleHijo.getRubro().esRaiz() && posibleHijo.getRubro().getRubroPadre().getId().equals(rubroDTO.getRubro().getId()))
							presupuestoRubros.add(posibleHijo);
					}
					break;
				}
			}
		}
	}
	private boolean isAllExpanded(List<PresupuestoRubroDTO> presupuestoRubros) {
		for (PresupuestoRubroDTO rubroDTO : presupuestoRubros) {
			if(!rubroDTO.getRubro().esHoja()) return false;
		}
		return true;
	}
}
