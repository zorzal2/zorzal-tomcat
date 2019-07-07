package com.fontar.web.decorator.seguimientos.evaluacion;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fontar.data.api.dao.EntidadEvaluadoraDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.seguridad.ObjectUtils;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.util.Util;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.util.ContextUtil;

/**
 * @author ssanchez
 * @version %I%, %G%
 */
public class EvaluacionSeguimientoWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() throws UnsupportedEncodingException {

		EvaluacionGeneralBean evaluacionBean = (EvaluacionGeneralBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarEvaluacionSeguimiento.do", "app.alt.visualizarEvaluacion", evaluacionBean.getId());
		visualizarLink.setPermissionsByInstrumentoRequired(evaluacionBean.getProyecto().getIdInstrumento(), "EVALUACIONESSEGUIMIENTO-VISUALIZAR");

		return visualizarLink.displayValue();		
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
				if (evaluadorBean.getEvaluador()!=null) {
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
}
