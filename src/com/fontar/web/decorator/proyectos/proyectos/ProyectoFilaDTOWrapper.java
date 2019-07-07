package com.fontar.web.decorator.proyectos.proyectos;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.impl.evaluacion.AdministrarEvaluacionesServicioImpl;
import com.fontar.bus.impl.paquete.AdministrarPaqueteProyectoServicioImpl;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * 
 * @author gboaglio
 * 
 */
public class ProyectoFilaDTOWrapper extends TableDecoratorSupport {

	public String createOption(LabelValueBean labelValue) throws UnsupportedEncodingException {
		StringBuffer option = new StringBuffer();
		option.append("<option value='" + labelValue.getValue() + "'>" + labelValue.getLabel() + "</option>");
		return option.toString();
	}
	public String createOptionSelected(LabelValueBean labelValue) throws UnsupportedEncodingException {
		StringBuffer option = new StringBuffer();
		option.append("<option selected='selected' value='" + labelValue.getValue() + "'>" + labelValue.getLabel() + "</option>");
		return option.toString();
	}
	
	public LabelValueBean getLabelValueBean( Enumerable e) {
		return new LabelValueBean(e.getDescripcion(), e.getName());
	}
	
	public String AgregarOpcion( ResultadoEvaluacion opcionRecomendacion, ProyectoFilaDTO proyecto) throws UnsupportedEncodingException {
		String opcion="";
		
		String evaluacionActual = proyecto.getResultado();
		Recomendacion recomendacionProyecto = proyecto.getRecomendacion();
		
		LabelValueBean labelValue= getLabelValueBean( opcionRecomendacion);
		
		if(evaluacionActual != null && opcionRecomendacion.getDescripcion().equals(evaluacionActual))
			opcion= createOptionSelected(labelValue);
		
		else if(evaluacionActual == null && recomendacionProyecto != null 
			&& recomendacionProyecto.equals(opcionRecomendacion.getRecomendacionProyecto()))
			opcion = createOptionSelected(labelValue);
		
		else
			opcion = createOption(labelValue);
		
		return opcion;
	}
	
	public String getComboResultado() throws UnsupportedEncodingException {
		StringBuffer link = new StringBuffer();
		
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		
		link.append("<button onclick='popUpEvaluarProyecto(" + proyecto.getIdProyecto().toString() + ")'>");
		link.append("...");
		link.append("</button>");

		return link.toString();
	}

	public String getLinkVerPresupuesto() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();

		if(!mostrarEdicionDePresupuesto(proyecto)) return "&nbsp;";
			
		StringBuffer link = new StringBuffer();
		
		link.append("<a href=\"#\" onclick=\"window.open('");
		link.append("EditarPresupuestoEvaluarPaqueteIngresar.do?");
		link.append("idProyecto=" + proyecto.getIdProyecto());
		link.append("&idEvaluacion=" + proyecto.getIdEvaluacion());
		link.append("', 'EditarPresupuestoEvaluarPaquete', 'width=700,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1');");
		link.append("\"><img class=\"imageButton\" src='images/edicion.gif' title=");
		link.append("'" + ResourceManager.getAltResource("app.alt.editarPresupuesto") + "' border=0 /></a>");

		return link.toString();
	}

	public String getHiddenArrays() {
		ProyectoFilaDTO dto = (ProyectoFilaDTO) this.getCurrentRowObject();

		StringBuffer hiddenField = new StringBuffer();
		hiddenField.append("<input type='hidden' name='proyectoArray' value='" + dto.getIdProyecto() + "'/>");
		hiddenField.append("<input type='hidden' name='fundamentacionArray' value=''/>");
		hiddenField.append("<input type='hidden' name='presupuestoArray' value=''/>");
		return hiddenField.toString();
	}

	/**
	 * Monto total del proyecto que se presupuestó inicialmente.
	 * @return
	 */
	public String getTotalSolicitado() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal montoSolicitado = proyecto.getTotalSolicitado();
		return StringUtil.formatMoneyForPresentation(montoSolicitado);
	}
	public String getBeneficioFONTARSolicitado() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal montoSolicitado = proyecto.getBeneficioFONTARSolicitado();
		return StringUtil.formatMoneyForPresentation(montoSolicitado);
	}
	public String getTotalAprobado() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal x = proyecto.getTotalAprobado();
		return StringUtil.formatMoneyForPresentation(x);
	}
	public String getBeneficioFONTARAprobado() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal x = proyecto.getBeneficioFONTARAprobado();
		return StringUtil.formatMoneyForPresentation(x);
	}
	public String getAlicuotaSolicitada() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal x = proyecto.getPorcentajeAlicuotaSolicitada();
		return StringUtil.formatMoneyForPresentation(x);
	}
	public String getAlicuotaAdjudicada() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal x = proyecto.getPorcentajeAlicuotaAdjudicada();
		return StringUtil.formatMoneyForPresentation(x);
	}
	public String getBeneficioFONTARAdjudicado() {
		ProyectoFilaDTO proyecto = (ProyectoFilaDTO) this.getCurrentRowObject();
		BigDecimal x = proyecto.getBeneficioFONTARAdjudicado();
		return StringUtil.formatMoneyForPresentation(x);
	}


	public String getEvaluaciones() {
		AdministrarEvaluacionesServicioImpl aes = new AdministrarEvaluacionesServicioImpl();
		AdministrarPaqueteProyectoServicioImpl apps = new AdministrarPaqueteProyectoServicioImpl();
		aes.setDao((EvaluacionDAO) ContextUtil.getBean("evaluacionDao"));
		
		StringBuffer sb = new StringBuffer();
		
		ProyectoFilaDTO dto = (ProyectoFilaDTO) this.getCurrentRowObject();
		Collection<EvaluarResultadoProyectoDTO> eval = aes.obtenerEvaluaciones(dto.getIdProyecto());
		
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
		Collection<EvaluarResultadoProyectoDTO> lista = apps.obtenerEvaluacionesDictamen(dto.getProyectoBean(), dto.getPaqueteDTO());
		i = lista.iterator();
		while(i.hasNext()) {
			EvaluarResultadoProyectoDTO e = (EvaluarResultadoProyectoDTO)i.next();
			sb.append(e.getEvaluaciones());
			sb.append("<br/><br/>");
		}
		return sb.toString();
	}
	
	private boolean mostrarEdicionDePresupuesto(ProyectoFilaDTO proyecto) {
		return 
			proyecto.getIdEvaluacion()!=null &&
			!proyecto.getTratamientoPaquete().equals(TratamientoPaquete.ADJUDICACION);
	}
	
}