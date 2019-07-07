package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.paquete.AdministrarPaqueteProyectoServicio;
import com.fontar.bus.api.paquete.EvaluarPaqueteServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoFilaDTOAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.EvaluacionProyectoPaqueteDTO;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.PaqueteEvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.action.TemplateCargarGuardarAction;

/**
 * Action para Evaluar el Resultado de un Proyecto
 * @author mdberra
 * @version 1.00, 25/01/07 
 */
public class EvaluarResultadoProyectoAction extends TemplateCargarGuardarAction {

	protected AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	protected AdministrarProyectoServicio administrarProyectoServicio;
	protected AdministrarPaqueteProyectoServicio administrarPaqueteProyectoServicio;
	private EvaluarPaqueteServicio evaluarPaqueteServicio;
	
	static final String EVALUACIONES_LIST = "evaluacionesList";
	static final String PERMITE_CARGA_ALICUOTA = "permiteCargaAlicuota";
	
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		
		String resultado = BeanUtils.getSimpleProperty(form, "resultado");
		try {
			ResultadoEvaluacion.valueOf(resultado);
		}catch (Exception e) {
			addError(messages, "app.paquete.evaluarResultado.requiereResultado");
		}
		
		if (messages.isEmpty()) {
			String alicuota = BeanUtils.getSimpleProperty(form, "alicuota");
			if(alicuota != null && alicuota.compareTo("") > 0)
				try {
					alicuota=alicuota.replace(',', '.');
					double d = new BigDecimal(alicuota).doubleValue();
					if(d < 0 || d > 100)
						addError(messages, "app.paquete.evaluarResultado.alicuotaFueraRango");
				} catch(Exception e) {
					addError(messages, "app.paquete.evaluarResultado.alicuotaIncorrecta");
				}
		}
	}
/**
 * Carga de datos para la vista de la tarea, también se extraen los datos de
 * contexto desde la tarea.
 */
	@Override
	protected void executeCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {

		ProyectoBean proyectoBean = null;
		Long idProyecto = null;
	
		if (!getErrors(request).isEmpty()){
			idProyecto = new Long(BeanUtils.getProperty(form, "idProyecto"));
		} else {
			// el id es el del proyecto
			String id = request.getParameter("id");
			idProyecto = new Long(id);
			BeanUtils.setProperty(form, "idProyecto", idProyecto);
		}
		proyectoBean = administrarEvaluacionesServicio.obtenerProyecto(idProyecto);
		BeanUtils.setProperty(form, "montoAprobado", proyectoBean.getMontoAprobado());
//----------------------
		PaqueteDTO paqueteDto = (PaqueteDTO)SessionHelper.getPaquete(request);

		// Si estamos en directorio y permite adjudicacion
		boolean instAdjudicacion = paqueteDto.getTipoPaquete().getName().equals(TipoPaquete.DIRECTORIO.getName()) &&
								   proyectoBean.getInstrumento().getPermiteAdjudicacion();

		boolean circuitoAdjudicacion =  instAdjudicacion || paqueteDto.getTratamiento().equals(TratamientoPaquete.ADJUDICACION); 

		boolean permiteCargaAlicuota = circuitoAdjudicacion && proyectoBean.getInstrumento().aplicaCargaAlicuotaCF();
		
		request.setAttribute(PERMITE_CARGA_ALICUOTA, permiteCargaAlicuota);

		if(permiteCargaAlicuota)
			if(proyectoBean.getPorcentajeAlicuotaSolicitada() != null) {
				BigDecimal porcentajeAlicuota = proyectoBean.getPorcentajeAlicuotaAdjudicada();
				if(porcentajeAlicuota==null) porcentajeAlicuota = proyectoBean.getPorcentajeAlicuotaSolicitada();
				String strPorcentajeAlicuota;
				if(porcentajeAlicuota==null) {
					strPorcentajeAlicuota = "";
				} else {
					strPorcentajeAlicuota = StringUtil.formatTwoDecimalForPresentation(porcentajeAlicuota);
				}
				BeanUtils.setProperty(form, "alicuota", strPorcentajeAlicuota);
			}

//----------------------		
		ProyectoCabeceraDTO cabeceraDTO = (ProyectoCabeceraDTO) administrarProyectoServicio.getProyectoDTO(idProyecto, new ProyectoCabeceraAssembler());
		request.setAttribute(PROYECTO, cabeceraDTO);

//		obtiene las evaluaciones desde la persistencia
		Collection<EvaluarResultadoProyectoDTO> evaluacionesList = administrarPaqueteProyectoServicio.obtenerEvaluacionesElegibles(proyectoBean, paqueteDto);
		boolean dictamen;
		
		PaqueteEvaluarResultadoProyectoDTO perpDTO = SessionHelper.getEvaluarPaqueteResultPry(request,proyectoBean.getId());
		if(perpDTO == null) {
//			obtiene si la evaluación del paquete es dictamen
			dictamen = administrarPaqueteProyectoServicio.tieneDictamen(proyectoBean, paqueteDto);
		} else {
			dictamen = perpDTO.getDictamen();
//			private String	resultado;
			if(perpDTO.getIdEvaluacionesNoElegibles() != null)
				administrarPaqueteProyectoServicio.cambiarNoElegible(perpDTO.getIdEvaluacionesNoElegibles(), evaluacionesList);
			if(perpDTO.getAlicuota() != null && perpDTO.getAlicuota().compareTo("") != 0)
				BeanUtils.setProperty(form, "alicuota", perpDTO.getAlicuota());
			if(perpDTO.getFundamentacion() != null)
				BeanUtils.setProperty(form, "fundamentacion", perpDTO.getFundamentacion());
		} 
//		cambio los datos a mostrar por si fueron modificados en la vista
		this.cambioVista(proyectoBean, evaluacionesList, dictamen, form, request);

		if(perpDTO == null || perpDTO.getResultado() == null)
			request.setAttribute("resultados", getComboResultado(proyectoBean, paqueteDto, new String("")));
		else
			request.setAttribute("resultados", getComboResultado(proyectoBean, paqueteDto, perpDTO.getResultado()));
		request.setAttribute(EVALUACIONES_LIST, evaluacionesList);
		BeanUtils.setProperty(form, "idPaquete", paqueteDto.getId());
	}

	private void cambioVista(ProyectoBean proyectoBean, Collection<EvaluarResultadoProyectoDTO> evaluacionesList, boolean dictamen , ActionForm form, HttpServletRequest request) {
		PaqueteEvaluarResultadoProyectoDTO perpDTO = new PaqueteEvaluarResultadoProyectoDTO();
		perpDTO.setIdEvaluaciones(administrarPaqueteProyectoServicio.obtenerIdEvaluaciones(evaluacionesList));
		perpDTO.setDictamen(dictamen);
		SessionHelper.setEvaluarPaqueteResultPry(request,proyectoBean.getId(),perpDTO);
		
		if(dictamen) {
			DynaActionForm dyna = (DynaActionForm) form;
			dyna.set("dictamen", "true");
			
			PaqueteDTO paqueteDto = (PaqueteDTO)SessionHelper.getPaquete(request);
			
			Collection<EvaluarResultadoProyectoDTO> lista = administrarPaqueteProyectoServicio.obtenerEvaluacionesDictamen(proyectoBean, paqueteDto);
			Iterator i = lista.iterator();
			while(i.hasNext()) {
				EvaluarResultadoProyectoDTO e = (EvaluarResultadoProyectoDTO)i.next();
				evaluacionesList.add(e);
			}
		}
	}

	@Override
	protected void executeGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = new Long(BeanUtils.getProperty(form, "idProyecto"));
		String idPaquete = FormUtil.getStringValue(form, "idPaquete");
//		String[] proyectoArray = ((DynaActionForm) form).getStrings("proyectoArray");
		String userName = request.getUserPrincipal().getName();

		
		PaqueteEvaluarResultadoProyectoDTO perpDTO = SessionHelper.getEvaluarPaqueteResultPry(request, idProyecto); 
		perpDTO.setIdProyecto(idProyecto);
		
		this.determinarNoElegidas(perpDTO, form);
		
		perpDTO.setDictamenStr(BeanUtils.getSimpleProperty(form, "dictamen"));
		perpDTO.setResultado(BeanUtils.getSimpleProperty(form, "resultado"));
		perpDTO.setFundamentacion(BeanUtils.getSimpleProperty(form, "fundamentacion"));
		perpDTO.setAlicuota(BeanUtils.getSimpleProperty(form, "alicuota"));
		
		SessionHelper.setEvaluarPaqueteResultPry(request, idProyecto, perpDTO);

		Collection<EvaluacionProyectoPaqueteDTO> datosEvaluacionProyecto = new ArrayList<EvaluacionProyectoPaqueteDTO>();
		EvaluacionProyectoPaqueteDTO dto;

		if(perpDTO != null) {
			dto = new EvaluacionProyectoPaqueteDTO();
			dto.setIdProyecto(String.valueOf(idProyecto));
			dto.setResultado(perpDTO.getResultado());
			dto.setFundamentacion(perpDTO.getFundamentacion());
			dto.setIdEvaluacionesNoElegibles(perpDTO.getIdEvaluacionesNoElegibles());
			dto.setIdpaqueteNoElegible(new Long(idPaquete));
			dto.esDictamen(perpDTO.getDictamen());
			
			String alicuota = perpDTO.getAlicuota();
			if(StringUtil.isNotEmpty(alicuota)) {
				alicuota=alicuota.replace(',', '.');
				dto.setAlicuotaAdjudicada(new BigDecimal(alicuota));
			}
			
			datosEvaluacionProyecto.add(dto);
		}
		evaluarPaqueteServicio.cargarEvaluacion(new Long(idPaquete), datosEvaluacionProyecto, userName);
		SessionHelper.setEvaluarPaqueteResultPry(request,null);
		SessionHelper.setPaquete(request,null);
		
	}
	
	private void determinarNoElegidas(PaqueteEvaluarResultadoProyectoDTO perpDTO, ActionForm form) {
// De todas las evaluaciones, busco las que no fueron elegidas y las agrego en la lista
		Collection<Long> c = new ArrayList<Long>();
		String[] elegibleArray = ((DynaActionForm) form).getStrings("elegibleArray");
		for(int i = 0; i<perpDTO.getIdEvaluaciones().length; i++) {
			boolean encontre = false;
			for(int j = 0; j<elegibleArray.length; j++) {
				if(perpDTO.getIdEvaluaciones()[i].equals(new Long(elegibleArray[j]))) {
					encontre = true;
				}
			}
			if(!encontre) {
				c.add(perpDTO.getIdEvaluaciones()[i]);
			}
		}
		Long[] evaluacionesNoElegibles = new Long[c.size()];
		int j = 0;
		Iterator k = c.iterator();
		while(k.hasNext()) {
			evaluacionesNoElegibles[j++] = Long.parseLong(k.next().toString());
		}
		perpDTO.setIdEvaluacionesNoElegibles(evaluacionesNoElegibles);		
	}
	
	private Collection getComboResultado(ProyectoBean proyectoBean, PaqueteDTO paqueteDto, String resultado) throws UnsupportedEncodingException {
		Collection<LabelValueBean> collection = new ArrayList<LabelValueBean>();

		ProyectoFilaDTO proyectoDto = ProyectoFilaDTOAssembler.buildDto(proyectoBean);
		proyectoDto.setPaqueteDTO(paqueteDto);
		proyectoDto.setProyectoBean(proyectoBean);
		proyectoDto.setTipoPaquete(paqueteDto.getTipoPaquete());
		proyectoDto.setTratamientoPaquete(paqueteDto.getTratamiento());
//		
// Agrego el primer elemento
//		
		if(resultado.equals(ResultadoEvaluacion.A_DEFINIR.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.A_DEFINIR, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.A_EVALUAR.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.A_EVALUAR, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.ADJUDICADO.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.ADJUDICADO, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.APRO_MODIF_MONTO.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.APRO_MODIF_MONTO, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.APROB_ADJ.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.APROB_ADJ, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.APROB_MM_ADJ.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.APROB_MM_ADJ, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.APROBADO.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.APROBADO, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.NO_ADJUDICADO.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.NO_ADJUDICADO, proyectoDto);
		else if(resultado.equals(ResultadoEvaluacion.RECHAZADO.getName()))
			AgregarOpcion(collection, ResultadoEvaluacion.RECHAZADO, proyectoDto);
		else
			collection.add(new LabelValueBean("----- seleccione -----","----- seleccione -----"));
//
		//TIPOS DE PAQUETE
		//Si proyectoDto.getTipoPaquete() es null las tres opciones dan false ¿puede pasar?
		boolean esPaqueteDeDirectorio = TipoPaquete.DIRECTORIO.equals(proyectoDto.getTipoPaquete());
		boolean esPaqueteDeComision = TipoPaquete.COMISION.equals(proyectoDto.getTipoPaquete());
		boolean esPaqueteDeSecretaria = TipoPaquete.SECRETARIA.equals(proyectoDto.getTipoPaquete());
		//TIPOS DE TRATAMIENTO
		boolean esReconsideracion = TratamientoPaquete.RECONSIDERACION.equals(proyectoDto.getTratamientoPaquete());
		boolean esEvaluacion = TratamientoPaquete.EVALUACION.equals(proyectoDto.getTratamientoPaquete());
		boolean esAdjudicacion = TratamientoPaquete.ADJUDICACION.equals(proyectoDto.getTratamientoPaquete());
		
		
		if(esPaqueteDeDirectorio && esAdjudicacion) {
			AgregarOpcion(collection, ResultadoEvaluacion.ADJUDICADO, proyectoDto);
			AgregarOpcion(collection, ResultadoEvaluacion.NO_ADJUDICADO, proyectoDto);
		} else { 
			//tipo es Comisión o Secretaria o tipo es Directorio (Evaluación o Reconsideración)
			//Si el paquete es de directorio y el instrumento es de 
			//adjudicacion directa no muestro las opciones:
			//    APROBADO y APROBADO CON REDUCCION DE MONTO
			if(!esPaqueteDeDirectorio || !proyectoDto.getPermiteAdjudicacionInstrumento()) {
				AgregarOpcion(collection, ResultadoEvaluacion.APROBADO, proyectoDto);
				AgregarOpcion(collection, ResultadoEvaluacion.APRO_MODIF_MONTO, proyectoDto);
			}
			AgregarOpcion(collection, ResultadoEvaluacion.A_EVALUAR, proyectoDto);
			AgregarOpcion(collection, ResultadoEvaluacion.A_DEFINIR, proyectoDto);
			AgregarOpcion(collection, ResultadoEvaluacion.RECHAZADO, proyectoDto);
		
			if (esPaqueteDeDirectorio && 
					proyectoDto.getPermiteAdjudicacionInstrumento()) // y tratamiento: Evaluación o Reconsideración
			{
				AgregarOpcion(collection, ResultadoEvaluacion.APROB_ADJ, proyectoDto);
				AgregarOpcion(collection, ResultadoEvaluacion.APROB_MM_ADJ, proyectoDto);
			}
		}

		return collection;
	}
	private void AgregarOpcion(Collection<LabelValueBean> collection, ResultadoEvaluacion opcionRecomendacion, ProyectoFilaDTO proyecto) throws UnsupportedEncodingException {
		String evaluacionActual = proyecto.getResultado();
		Recomendacion recomendacionProyecto = proyecto.getRecomendacion();
		
		LabelValueBean labelValue= getLabelValueBean( opcionRecomendacion);
		
		if(evaluacionActual != null && opcionRecomendacion.getDescripcion().equals(evaluacionActual))
			agregar(collection, labelValue);
		else if(evaluacionActual == null && recomendacionProyecto != null 
			&& recomendacionProyecto.equals(opcionRecomendacion.getRecomendacionProyecto()))
			agregar(collection, labelValue);
		else
			agregar(collection, labelValue);
	}
	private LabelValueBean getLabelValueBean( Enumerable e) {
		return new LabelValueBean(e.getDescripcion(), e.getName());
	}
	private void agregar(Collection<LabelValueBean> collection, LabelValueBean labelValue) {
		Iterator i = collection.iterator();
		boolean encontre = false;
		while(i.hasNext()) {
			LabelValueBean lvBean = (LabelValueBean)i.next();
			if(lvBean.equals(labelValue))
				encontre = true;
		}
		if(!encontre)
			collection.add(labelValue);
	}
//------------------------------------------------------------------------------------------------------------	
	
	
	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}
	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}
	public void setAdministrarPaqueteProyectoServicio(AdministrarPaqueteProyectoServicio administrarPaqueteProyectoServicio) {
		this.administrarPaqueteProyectoServicio = administrarPaqueteProyectoServicio;
	}
	public void setEvaluarPaqueteServicio(EvaluarPaqueteServicio evaluarPaqueteServicio) {
		this.evaluarPaqueteServicio = evaluarPaqueteServicio;
	}
}