package com.fontar.web.decorator.proyectos.proyectos;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.fontar.bus.impl.evaluacion.AdministrarEvaluacionesServicioImpl;
import com.fontar.bus.impl.paquete.AdministrarPaqueteProyectoServicioImpl;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.VisualizarProyectoFilaDTO;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * 
 * @author ssanchez
 * @version 1.00, 29/11/2006
 */
public class VisualizarProyectoFilaDTOWrapper extends TableDecoratorSupport {

	public String getLinkVerPresupuesto() {
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=''>");
		link.append("Ver presup");
		link.append("</a>");

		return link.toString();
	}

	public String getHiddenArrays() {
		VisualizarProyectoFilaDTO dto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();

		StringBuffer hiddenField = new StringBuffer();
		hiddenField.append("<input type='hidden' name='proyectoArray' value='" + dto.getIdProyecto() + "'>");
		hiddenField.append("<input type='hidden' name='fundamentacionArray' value=''>");
		hiddenField.append("<input type='hidden' name='presupuestoArray' value=''>");
		return hiddenField.toString();
	}

	public String getPorcentajeSolicitado() {
		VisualizarProyectoFilaDTO dto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();
		StringBuffer prc = new StringBuffer();

		BigDecimal porcentajeSolicitado = dto.getPorcentajeSolicitado();
		if (porcentajeSolicitado != null) {
			prc.append(porcentajeSolicitado.toString() + "%");
		}

		return prc.toString();
	}

	public String getEvaluaciones() {
		VisualizarProyectoFilaDTO vpfDto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();

		AdministrarEvaluacionesServicioImpl aes = new AdministrarEvaluacionesServicioImpl();
		AdministrarPaqueteProyectoServicioImpl apps = new AdministrarPaqueteProyectoServicioImpl();
		aes.setDao((EvaluacionDAO) ContextUtil.getBean("evaluacionDao"));
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		
		ProyectoBean proyectoBean = proyectoDao.read(vpfDto.getIdProyecto());
		
		StringBuffer sb = new StringBuffer();
		
		Collection<EvaluarResultadoProyectoDTO> eval = aes.obtenerEvaluaciones(vpfDto.getIdProyecto());
		
		Iterator i = eval.iterator();
		while(i.hasNext()) {
			EvaluarResultadoProyectoDTO erpDTO = (EvaluarResultadoProyectoDTO)i.next();
			sb.append(erpDTO.getIdEvaluacion() + ":" 
					+ erpDTO.getEvaluaciones().replaceAll( " ","&nbsp;") + ":" 
					+ erpDTO.getResultado());
			if(!erpDTO.isEsElegible())
				sb.append(": No Elegible");
			sb.append("<br/><br/>");
		}
		// Agrego los dictamenes
		Collection<EvaluarResultadoProyectoDTO> lista = apps.obtenerEvaluacionesDictamen(proyectoBean, vpfDto.getIdPaquete());
		i = lista.iterator();
		while(i.hasNext()) {
			EvaluarResultadoProyectoDTO e = (EvaluarResultadoProyectoDTO)i.next();
			sb.append(e.getEvaluaciones());
			sb.append("<br/><br/>");
		}
		return sb.toString();
	}
	public String getMontoSolicitado() {
		VisualizarProyectoFilaDTO vpfDto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();
		return StringUtil.formatMoneyForPresentation(vpfDto.getMontoSolicitado());
	}
	public String getMontoAprobado() {
		VisualizarProyectoFilaDTO vpfDto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();
		return StringUtil.formatMoneyForPresentation(vpfDto.getMontoAprobado());
	}
	public String getMontoAdjudicado() {
		VisualizarProyectoFilaDTO vpfDto = (VisualizarProyectoFilaDTO) this.getCurrentRowObject();
		return StringUtil.formatMoneyForPresentation(vpfDto.getMontoAdjudicado());
	}
}