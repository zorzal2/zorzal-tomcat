package com.fontar.web.decorator.evaluacion;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadEvaluadoraDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.EvaluacionGeneralDAO;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.seguridad.ObjectUtils;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.util.Util;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;

public class EvaluacionWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() throws UnsupportedEncodingException {

		EvaluacionBean evaluacionBean = (EvaluacionBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarEvaluacion.do", "app.alt.visualizarEvaluacion", evaluacionBean.getId());
		visualizarLink.setPermissionsByInstrumentoRequired(evaluacionBean.getProyecto().getIdInstrumento(), "EVALUACIONES-VISUALIZAR");
		return visualizarLink.displayValue();
	}
	
	public String getTipoDescripcion() throws UnsupportedEncodingException {
		EvaluacionBean evaluacion = (EvaluacionBean) this.getCurrentRowObject();
		
		StringBuffer sTipo = new StringBuffer();
		boolean agrege = false;

		if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_GEN)) {
			VisualizarEvaluacionAssembler visualizarEvaluacionAssembler = new VisualizarEvaluacionAssembler();
			VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator)visualizarEvaluacionAssembler.buildDTO( evaluacion );
			
			if(dto.getEstado().equals(EstadoEvaluacion.CONFIRMADA)) {
				sTipo.append(dto.getTipos() + " / " + visualizarEvaluacionAssembler.getShowEvaluadores(dto.getEvaluadores()).replaceAll("<br/>",""));
				agrege = true;
			}
		}
		if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_PAQ)) {
			PaqueteDAO paqueteDAO = (PaqueteDAO) WebContextUtil.getBeanFactory().getBean("paqueteDao");
			PaqueteBean paqueteBean = paqueteDAO.read(evaluacion.getIdPaquete());
			agrege = true;
			if(paqueteBean.getComision() == null)
				sTipo.append(evaluacion.getTipo().getDescripcion() + " " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo());
			else
				sTipo.append(evaluacion.getTipo().getDescripcion() + " " + paqueteBean.getId().toString() + " para " + paqueteBean.getTipo() + ":" + paqueteBean.getComision().getDenominacion());
			
			if(evaluacion.isEsDictamen())
				sTipo.append(" -  DICTAMEN");
		}
		if(!agrege)
			sTipo.append(evaluacion.getTipo().getDescripcion());
		
		return sTipo.toString();
	}
	
	public String getLinkAdjuntos() throws UnsupportedEncodingException
	{
		EvaluacionBean lObject= (EvaluacionBean)this.getCurrentRowObject();
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),EvaluacionBean.class);
		return adjuntosLink.displayValue();
	}	

	public String getInstitucionesEvaluadora() throws UnsupportedEncodingException{
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		if(!service.encyptionAvailable())
			return ObjectUtils.ENCRIPTION_WARNING;
		
		EvaluacionBean lObject= (EvaluacionBean)this.getCurrentRowObject();
		StringBuffer sInstEval = new StringBuffer();
		EvaluacionEvaluadorDAO evaluacionEvaluadorDAO = (EvaluacionEvaluadorDAO)ContextUtil.getBean("evaluacionEvaluadorDao");		
		EntidadDAO entidadDAO = (EntidadDAO)ContextUtil.getBean("entidadDao");
			List<EvaluacionEvaluadorBean> eeList = evaluacionEvaluadorDAO.findByEvaluacion(lObject.getId());
			for (EvaluacionEvaluadorBean evaluadorBean : eeList) {
				if (!Util.isBlank(evaluadorBean.getInstitucion())) {
					Long idEntidad = new Long(evaluadorBean.getInstitucion());
					EntidadBean entidadBean = entidadDAO.read(idEntidad);
					if (entidadBean != null) {
						sInstEval.append(entidadBean.getDenominacion()+ "\n");
					}
				}
			}
		return sInstEval.toString();
	}

	public String getEvaluador() throws UnsupportedEncodingException{
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		if(!service.encyptionAvailable())
			return ObjectUtils.ENCRIPTION_WARNING;
			
		EvaluacionBean lObject= (EvaluacionBean)this.getCurrentRowObject();
		StringBuffer sEvaluador = new StringBuffer();
		EvaluacionEvaluadorDAO evaluacionEvaluadorDAO = (EvaluacionEvaluadorDAO)ContextUtil.getBean("evaluacionEvaluadorDao");		
		PersonaDAO personaDAO = (PersonaDAO)ContextUtil.getBean("personaDao");		
			List<EvaluacionEvaluadorBean> eeList = evaluacionEvaluadorDAO.findByEvaluacion(lObject.getId());
			for (EvaluacionEvaluadorBean evaluadorBean : eeList) {
				if (!StringUtil.isEmpty(evaluadorBean.getEvaluador())) {
					Long idPersona = new Long(evaluadorBean.getEvaluador());
					PersonaBean personaBean = personaDAO.read(idPersona);
					if (personaBean != null) {
						sEvaluador.append(personaBean.getNombre() + "\n");
					}
				}
			}
		return sEvaluador.toString();
	}

	public String getEvaluadorInstitucion() throws UnsupportedEncodingException{
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		if(!service.encyptionAvailable())
			return ObjectUtils.ENCRIPTION_WARNING;
			
		EvaluacionBean lObject= (EvaluacionBean)this.getCurrentRowObject();
		StringBuffer sEvaluador = new StringBuffer();
		EvaluacionEvaluadorDAO evaluacionEvaluadorDAO = (EvaluacionEvaluadorDAO)ContextUtil.getBean("evaluacionEvaluadorDao");
		PersonaDAO personaDAO = (PersonaDAO)ContextUtil.getBean("personaDao");
		EntidadEvaluadoraDAO entidadEvaluadoraDAO = (EntidadEvaluadoraDAO)ContextUtil.getBean("entidadEvaluadoraDao");
			List<EvaluacionEvaluadorBean> eeList = evaluacionEvaluadorDAO.findByEvaluacion(lObject.getId());
			for (EvaluacionEvaluadorBean evaluadorBean : eeList) {
				if (!Util.isBlank(evaluadorBean.getEvaluador())) {
					Long idPersona = new Long(evaluadorBean.getEvaluador());
					PersonaBean personaBean = personaDAO.read(idPersona);
					if (personaBean != null) {
						sEvaluador.append(personaBean.getNombre() + " ");
					}
				}
				if(!Util.isBlank(evaluadorBean.getInstitucion())) {
					Long idInstitucion = new Long(evaluadorBean.getInstitucion()); 
					EntidadEvaluadoraBean entidadBean = entidadEvaluadoraDAO.read(idInstitucion);
					if (entidadBean != null) {
						sEvaluador.append("(" + entidadBean.getDenominacion() + ")\n");
					}
				}
			}
		return sEvaluador.toString();
	}

	
	public String getTipoEvaluacion() throws UnsupportedEncodingException{
		EvaluacionBean lObject= (EvaluacionBean)this.getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		EvaluacionGeneralDAO evaluacionGeneralDAO = (EvaluacionGeneralDAO)ContextUtil.getBean("evaluacionGeneralDao");		
		if (lObject.getTipo().equals(TipoEvaluacion.EVAL_GEN)) {
			EvaluacionGeneralBean bean = evaluacionGeneralDAO.read(lObject.getId());
			if (bean != null) {
				Boolean token = false;

				if (bean.getEsTecnica()) {
					buffer.append("Técnica");
					buffer.append(" ");
					token = true;
				}

				if (bean.getEsEconomica()) {
					if (token)
						buffer.append(" - ");
					else
						token = true;
					buffer.append("Económica");
				}

				if (bean.getEsFinanciera()) {
					if (token)
						buffer.append(" - ");
					buffer.append("Financiera");
					buffer.append(" (");
					buffer.append(bean.getTipoEvaluacionFinanciera());
					buffer.append(") ");
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * Obtiene los tipos de Evaluación separadas
	 * por un guión.<br>
	 * @return tipos de evaluación separadas por guión (técnica - contable - económica - financiera)
	 */
	public String getTiposEvaluacion() {
		EvaluacionGeneralBean bean = (EvaluacionGeneralBean)this.getCurrentRowObject();
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(bean.obtenerTiposDeEvaluacion());
		
		return buffer.toString();
	}
}
