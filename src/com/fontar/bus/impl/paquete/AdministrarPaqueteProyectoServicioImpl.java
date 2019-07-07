package com.fontar.bus.impl.paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fontar.bus.api.paquete.AdministrarPaqueteProyectoServicio;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.Evaluacion;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.pragma.util.ContextUtil;
import com.pragma.web.WebContextUtil;

/**
 * Servicio para la administración de paquetes/proyectos
 * 
 * @author mberra
 * @version 1.01, 31/01/07
 */
public class AdministrarPaqueteProyectoServicioImpl implements AdministrarPaqueteProyectoServicio {
	private EvaluacionDAO	evaluacionDAO;
	private PaqueteDAO		paqueteDAO;

/**
 * Busca la evaluación del paquete y devuelve si tiene Dictamen
 */
	public boolean tieneDictamen(ProyectoBean proyectoBean, PaqueteDTO paqueteDto) {
		boolean dictamen = false;
		for (EvaluacionBean evaluacionBean : proyectoBean.getEvaluaciones()) {
			if(evaluacionBean.getTipo().equals(TipoEvaluacion.EVAL_PAQ))
				if(evaluacionBean.getIdPaquete() <= paqueteDto.getId())
					if(evaluacionBean.isEsDictamen())
						dictamen = true;
		}
		return dictamen;
	}
	
/**
 * Evaluaciones Generales No Elegibles
 * -----------------------------------
 * Evalua si en el paquete en el que se encuentra el proyecto hay que agregar la evaluación de 'No Elegida'.
 * Si el paquete en el que estoy es > al paquete que la rechazo entonces no se debe agregar.
 */
	public Collection<EvaluarResultadoProyectoDTO>
				obtenerEvaluacionesElegibles(ProyectoBean proyectoBean, PaqueteDTO paqueteDto) {
	
		VisualizarEvaluacionAssembler visualizarEvaluacionAssembler = new VisualizarEvaluacionAssembler();
		Collection<EvaluarResultadoProyectoDTO> lista = new ArrayList<EvaluarResultadoProyectoDTO>();
		
		for (EvaluacionBean evaluacion : proyectoBean.getEvaluaciones())
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_GEN) && !evaluacion.esAnulada()) {

				if(evaluacion.getIdPaqRechElegibilidad() == null ||
				   evaluacion.getIdPaqRechElegibilidad() != null && paqueteDto.getId() <= evaluacion.getIdPaqRechElegibilidad()) {
						
					EvaluacionGeneralBean evaluacionGeneralBean = (EvaluacionGeneralBean) evaluacionDAO.read(evaluacion.getId());
					VisualizarEvaluacionGeneralDecorator egDto = (VisualizarEvaluacionGeneralDecorator) visualizarEvaluacionAssembler.buildDTO(evaluacionGeneralBean);

					EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();			
					erpDTO.setIdEvaluacion(evaluacion.getId());
					erpDTO.setEvaluaciones(visualizarEvaluacionAssembler.getShowEvaluacion(egDto.getEvaluacionGeneral()));
					if(evaluacion.getResultado() != null) {
						erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
					}
					erpDTO.setElegibleString("checked");
					if(paqueteDto.getId().equals(evaluacion.getIdPaqRechElegibilidad()))
						erpDTO.setElegibleString("");
					lista.add(erpDTO);
				}
			}
		return lista;
	}

/**
 * Evaluaciones de Paquete (Dictamen)
 * ----------------------------------
 * Una evaluación de Paquete puede ser dictamen o no.  Cuando es dictamen tiene en true el isEsDictamen().
 * Tambien,  todas las evaluaciones de "Paquete" tienen seteado el idPaquete.
 * Estos dictamenes se deben mostrar solo cuando me encuentro en un paquete 'posterior' al paquete de
 * la evaluación.
 */
	public Collection<EvaluarResultadoProyectoDTO>
			obtenerEvaluacionesDictamen(ProyectoBean proyectoBean, long idPaquete) {
		PaqueteDTO p = new PaqueteDTO();
		p.setId(idPaquete);
		return this.obtenerEvaluacionesDictamen(proyectoBean, p);
	}
	
	public Collection<EvaluarResultadoProyectoDTO>
			obtenerEvaluacionesDictamen(ProyectoBean proyectoBean, PaqueteDTO paqueteDto) {
		if (paqueteDAO == null) {
			paqueteDAO = (PaqueteDAO) WebContextUtil.getBeanFactory().getBean("paqueteDao");
		}
		Collection<EvaluarResultadoProyectoDTO> lista = new ArrayList<EvaluarResultadoProyectoDTO>();
		
		for (EvaluacionBean evaluacion : proyectoBean.getEvaluaciones()) {
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_PAQ)) {
				if(evaluacion.isEsDictamen()) {
					if(paqueteDto.getId() >= evaluacion.getIdPaquete()) {
// Agregar la evaluación
						EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();			
						erpDTO.setIdEvaluacion(evaluacion.getId());
						
						PaqueteBean paqueteBean = paqueteDAO.read(evaluacion.getIdPaquete());
						if(paqueteBean.getComision() == null)
							erpDTO.setEvaluaciones("Paquete " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo() + " -  DICTAMEN");
						else
							erpDTO.setEvaluaciones("Paquete " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo() + ":" + paqueteBean.getComision().getDenominacion() + " -  DICTAMEN");
						erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
						lista.add(erpDTO);
					}
				}
			}
		}
		return lista;
	}
	
	public Collection<EvaluarResultadoProyectoDTO> obtenerEvaluacionesPaquete(ProyectoBean proyectoBean) {
		paqueteDAO = (PaqueteDAO) ContextUtil.getBean("paqueteDao");
		Collection<EvaluarResultadoProyectoDTO> lista = new ArrayList<EvaluarResultadoProyectoDTO>();
		
		for (EvaluacionBean evaluacion : proyectoBean.getEvaluaciones()) {
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_PAQ)) {
				EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();			
				erpDTO.setIdEvaluacion(evaluacion.getId());
				
				PaqueteBean paqueteBean = paqueteDAO.read(evaluacion.getIdPaquete());
				if(paqueteBean.getComision() == null)
					erpDTO.setEvaluaciones("Paquete " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo());
				else
					erpDTO.setEvaluaciones("Paquete " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo() + ":" + paqueteBean.getComision().getDenominacion());
				if(evaluacion.isEsDictamen())
					erpDTO.setEvaluaciones(erpDTO.getEvaluaciones() + " -  DICTAMEN");
				erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
				lista.add(erpDTO);
			}
		}
		return lista;
	}
	
	public Long[] obtenerIdEvaluaciones(Collection<EvaluarResultadoProyectoDTO> evaluacionesList) {
		Iterator i = evaluacionesList.iterator();
		Collection<Long> c = new ArrayList<Long>();
		while(i.hasNext()) {
			Evaluacion erpDTO = (Evaluacion)i.next();
			c.add(erpDTO.getIdEvaluacion());
		}
		Long listaLong[] = new Long[c.size()];
		int j = 0;
		Iterator k = c.iterator();
		while(k.hasNext()) {
			listaLong[j++] = Long.parseLong(k.next().toString());
		}
		return listaLong;
	}

	public void cambiarElegible(String[] elegibleArray, Collection evaluacionesList) {
		Iterator i = evaluacionesList.iterator();
		while(i.hasNext()) {
			boolean encontre = false;
			EvaluarResultadoProyectoDTO erpDTO = (EvaluarResultadoProyectoDTO)i.next();
			erpDTO.setElegibleString("checked");
			for(int j = 0; j<elegibleArray.length; j++) {
				if(erpDTO.getIdEvaluacion().equals(new Long(elegibleArray[j]))) {
					encontre = true;
				}
			}
			if(!encontre) {
				erpDTO.setElegibleString("");
			}
		}
	}
	public void cambiarNoElegible(Long[] idEvaluacionesNoElegibles, Collection evaluacionesList) {
		Iterator i = evaluacionesList.iterator();
		while(i.hasNext()) {
			boolean encontre = false;
			EvaluarResultadoProyectoDTO erpDTO = (EvaluarResultadoProyectoDTO)i.next();
			erpDTO.setElegibleString("checked");
			for(int j = 0; j<idEvaluacionesNoElegibles.length; j++) {
				if(erpDTO.getIdEvaluacion().equals(idEvaluacionesNoElegibles[j])) {
					encontre = true;
				}
			}
			if(encontre) {
				erpDTO.setElegibleString("");
			}
		}
	}	
	
	public void setEvaluacionDAO(EvaluacionDAO evaluacionDAO) {
		this.evaluacionDAO = evaluacionDAO;
	}
	public void setPaqueteDAO(PaqueteDAO paqueteDAO) {
		this.paqueteDAO = paqueteDAO;
	}
}