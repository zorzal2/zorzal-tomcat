package com.fontar.util;

import static com.fontar.data.Constant.ACTION_AUTHORIZE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.navigator.menu.PermissionsAdapter;

import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.PaqueteEvaluarResultadoProyectoDTO;

public class SessionHelper {

	private static final String ARCHIVO_EXCEL = "ARCHIVO_EXCEL";
	
	private static final String EMPLEO_PERMANENTE = "EMPLEO_PERMANENTE";

	private static final String EVALUADOR = "EVALUADOR";

	private static final String ENTIDADBENEFICIARIA = "ENTIDADBENEFICIARIA";

	private static final String IDCRITERIOS = "IDCRITERIOS";

	private static final String NOMBRECRITERIOS = "NOMBRECRITERIOS";

	private static final String DISTRIBUCION_FINANCIAMIENTO_JURISDICCION = "DISTRIBUCION_FINANCIAMIENTO_JURISDICCION";

	private static final String DISTRIBUCION_FINANCIAMIENTO_REGION = "DISTRIBUCION_FINANCIAMIENTO_REGION";

	private static final String DISTRIBUCION_TIPO_PROYECTO = "DISTRIBUCION_TIPO_PROYECTO";
	
	private static final String ID_PROYECTO = "ID_PROYECTO";
	
	private static final String ID_SEGUIMIENTO = "ID_SEGUIMIENTO";
	
	private static final String ID_INSTRUMENTO = "ID_INSTRUMENTO";
	
	private static final String ID_PROCEDIMIENTO = "ID_PROCEDIMIENTO";
	
	private static final String ID_PROCEDIMIENTO_ITEM = "ID_PROCEDIMIENTO_ITEM";
	
	private static final String ES_FINANCIERO  = "ES_FINANCIERO";
	
	private static final String MENU_PERMISION_ADAPTER = "menuPermissions";
	
	private static final String CLASE_PROYECTO = "CLASE_PROYECTO";
		
	private static final String EVALUAR_PAQUETE_RESULT_PRY = "EVALUAR_PAQUETE_RESULT_PRY";
	
	private static final String PAQUETE = "PAQUETE";
	
	private static final String ID_EVALUACION = "ID_EVALUACION";

	private static final String ID_PAC = "ID_PAC";

	private static final String PAC_BEAN = "PAC_BEAN";

	private static final String ES_ANR  = "ES_ANR";
	private static final String ES_ARAI  = "ES_ARAI";
	private static final String ES_CF  = "ES_CF";
	private static final String ES_CF_CONSEJERIAS  = "ES_CF_CONSEJERIAS";
	private static final String ES_PATENTE  = "ES_PATENTE";
	
	private static final String FORWARD_TILES = "FORWARD_TILES";
	
	// Distribución Financiamiento Jurisdicción
	public static void setDistribucionFinanciamientoJurisdiccion(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(DISTRIBUCION_FINANCIAMIENTO_JURISDICCION, value);
	}

	public static Object getDistribucionFinanciamientoJurisdiccion(HttpServletRequest request) {
		return request.getSession().getAttribute(DISTRIBUCION_FINANCIAMIENTO_JURISDICCION);
	}

	// Distribución Financiamiento Región
	public static void setDistribucionFinanciamientoRegion(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(DISTRIBUCION_FINANCIAMIENTO_REGION, value);
	}

	public static Object getDistribucionFinanciamientoRegion(HttpServletRequest request) {
		return request.getSession().getAttribute(DISTRIBUCION_FINANCIAMIENTO_REGION);
	}

	// Empleo Permanente
	public static void setEmpleoPermanente(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(EMPLEO_PERMANENTE, value);
	}

	public static Object getEmpleoPermanente(HttpServletRequest request) {
		return request.getSession().getAttribute(EMPLEO_PERMANENTE);
	}

	// Distribucion Tipo Proyecto
	public static void setDistribucionTipoProyecto(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(DISTRIBUCION_TIPO_PROYECTO, value);
	}

	public static Object getDistribucionTipoProyecto(HttpServletRequest request) {
		return request.getSession().getAttribute(DISTRIBUCION_TIPO_PROYECTO);
	}

	// Localización
	public static void setEvaluador(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(EVALUADOR, value);
	}

	public static Object getEvaluador(HttpServletRequest request) {
		return request.getSession().getAttribute(EVALUADOR);
	}

	public static Object getEntidadBeneficiaria(HttpServletRequest request) {
		return request.getSession().getAttribute(ENTIDADBENEFICIARIA);
	}

	public static void setEntidadBeneficiaria(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(ENTIDADBENEFICIARIA, value);
	}
	
	public static Object getIdCriterios(HttpServletRequest request) {
		return request.getSession().getAttribute(IDCRITERIOS);
	}

	public static void setIdCriterios(HttpServletRequest request, String[] value) {
		request.getSession().setAttribute(IDCRITERIOS, value);
	}

	public static Long getIdInstrumento(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_INSTRUMENTO);
	}
	
	public static void setIdInstrumento(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_INSTRUMENTO, value);
	}
	
	// idProyecto para mantener en los TABS
	public static Long getIdProyecto(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_PROYECTO);
	}

	public static void setIdProyecto(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_PROYECTO, value);
	}
	
	public static String getClaseProyecto(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(CLASE_PROYECTO);
	}

	public static void setClaseProyecto(HttpServletRequest request, String value) {
		request.getSession().setAttribute(CLASE_PROYECTO, value);
	}	
	
	public static void setMenuPermissionsAdapter(HttpServletRequest request , PermissionsAdapter adapter){
		request.getSession().setAttribute(MENU_PERMISION_ADAPTER, adapter);
	}
	
	public static PermissionsAdapter getMenuPermissionsAdapter(HttpServletRequest request ){
		return (PermissionsAdapter) request.getSession().getAttribute(MENU_PERMISION_ADAPTER);
	}
	
	public static void setEvaluarPaqueteResultPry(HttpServletRequest request, Map<Long,PaqueteEvaluarResultadoProyectoDTO> mapa){
		if(mapa == null)
			request.getSession().setAttribute(EVALUAR_PAQUETE_RESULT_PRY, new HashMap<Long,PaqueteEvaluarResultadoProyectoDTO>());
		else
			request.getSession().setAttribute(EVALUAR_PAQUETE_RESULT_PRY, mapa);
	}
	public static void setEvaluarPaqueteResultPry(HttpServletRequest request, Long idProyecto, PaqueteEvaluarResultadoProyectoDTO dto) {
		Map<Long,PaqueteEvaluarResultadoProyectoDTO> paqueteEvaluarResultadoProyecto = null;
		try{
			paqueteEvaluarResultadoProyecto = getEvaluarPaqueteResultPry(request);
			if(getEvaluarPaqueteResultPry(request) == null) {
				setEvaluarPaqueteResultPry(request, null);
				paqueteEvaluarResultadoProyecto = getEvaluarPaqueteResultPry(request);
			}
		} catch(Exception e) {
			setEvaluarPaqueteResultPry(request, null);
			paqueteEvaluarResultadoProyecto = getEvaluarPaqueteResultPry(request);
		}
		paqueteEvaluarResultadoProyecto.put(idProyecto,dto);
	}
	public static Map<Long,PaqueteEvaluarResultadoProyectoDTO> getEvaluarPaqueteResultPry(HttpServletRequest request) {
		return (Map<Long,PaqueteEvaluarResultadoProyectoDTO>)request.getSession().getAttribute(EVALUAR_PAQUETE_RESULT_PRY);
	}
	public static PaqueteEvaluarResultadoProyectoDTO getEvaluarPaqueteResultPry(HttpServletRequest request, Long idProyecto) {
		Map<Long,PaqueteEvaluarResultadoProyectoDTO> paqueteEvaluarResultadoProyecto = getEvaluarPaqueteResultPry(request);
		if(paqueteEvaluarResultadoProyecto == null)
			return null;
		else
			return paqueteEvaluarResultadoProyecto.get(idProyecto);
	}
	
	public static void setPaquete(HttpServletRequest request, Object value) {
		request.getSession().setAttribute(PAQUETE, value);
	}

	public static Object getPaquete(HttpServletRequest request) {
		return request.getSession().getAttribute(PAQUETE);
	}

	public static Object getNombreCriterios(HttpServletRequest request) {
		return request.getSession().getAttribute(NOMBRECRITERIOS);
	}

	public static void setNombreCriterios(HttpServletRequest request, String nombre) {
		request.getSession().setAttribute(NOMBRECRITERIOS, nombre);
	}

	// idSeguimiento para mantener en los TABS
	public static Long getIdSeguimiento(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_SEGUIMIENTO);
	}

	public static void setIdSeguimiento(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_SEGUIMIENTO, value);
	}

	public static Boolean getEsFinanciero(HttpServletRequest request) {
		return (Boolean) request.getSession().getAttribute(ES_FINANCIERO);
	}

	public static void setEsFinanciero(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_FINANCIERO, value);
	}
	
	// idEvaluacion para mantener el id entre los TABS
	public static Long getIdEvaluacion(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_EVALUACION);
	}	
	public static void setIdEvaluacion(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_EVALUACION, value);
	}
	
	//usado para establecer autorizaciones a nivel de action
	public static Boolean getActionAuthorize(HttpServletRequest request) {
		return (Boolean)request.getSession().getAttribute(ACTION_AUTHORIZE);
	}
	public static void setActionAuthorize(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ACTION_AUTHORIZE,value);
	}
	
	//guarda en sesión en la variable "variableName" el valor Booleano "value"
	public static void setVariableValue(HttpServletRequest request, String variableName, Boolean value) {
		request.getSession().setAttribute(variableName,value);
	}
	public static Boolean getVariableValue(HttpServletRequest request, String variableName) {
		return (Boolean)request.getSession().getAttribute(variableName);
	}
	
	public static Boolean getEsANR(HttpServletRequest request) {
		return (Boolean) request.getSession().getAttribute(ES_ANR);
	}
	public static void setEsANR(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_ANR, value);
	}
	
	public static Boolean getEsARAI(HttpServletRequest request) {
		return (Boolean) request.getSession().getAttribute(ES_ARAI);
	}
	public static void setEsARAI(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_ARAI, value);
	}

	public static Boolean getEsCreditoFiscal(HttpServletRequest request) {
		return (Boolean) request.getSession().getAttribute(ES_CF);
	}
	public static void setEsCreditoFiscal(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_CF, value);
	}

	public static Boolean getEsCreditoFiscalConsejerias(HttpServletRequest request) {
		return (Boolean) request.getSession().getAttribute(ES_CF_CONSEJERIAS);
	}
	public static void setEsCreditoFiscalConsejerias(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_CF_CONSEJERIAS, value);
	}
	public static void setEsPatente(HttpServletRequest request, Boolean value) {
		request.getSession().setAttribute(ES_PATENTE, value);
	}

	public static Long getIdPac(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_PAC);
	}

	public static void setIdPac(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_PAC, value);
	}

	public static PacBean getPacBean(HttpServletRequest request) {
		return (PacBean)request.getSession().getAttribute(PAC_BEAN);
	}

	public static void setPacBean(HttpServletRequest request, PacBean pacBean) {
		request.getSession().setAttribute(PAC_BEAN, pacBean);
	}
	
	public static Long getIdProcedimiento(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_PROCEDIMIENTO);
	}
	public static void setIdProcedimiento(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_PROCEDIMIENTO, value);
	}
	
	public static String getForwardTiles(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(FORWARD_TILES);
	}
	public static void setForwardTiles(HttpServletRequest request, String value) {
		request.getSession().setAttribute(FORWARD_TILES, value);
	}
	
	public static Long getIdProcedimientoItem(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(ID_PROCEDIMIENTO_ITEM);
	}
	public static void setIdProcedimientoItem(HttpServletRequest request, Long value) {
		request.getSession().setAttribute(ID_PROCEDIMIENTO_ITEM, value);
	}

	public static ArchivoDTO getArchivoExcel(HttpServletRequest request) {
		return (ArchivoDTO)request.getSession().getAttribute(ARCHIVO_EXCEL);
	}
	public static void setArchivoExcel(HttpServletRequest request, ArchivoDTO value) {
		request.getSession().setAttribute(ARCHIVO_EXCEL, value);
	}
	
}



