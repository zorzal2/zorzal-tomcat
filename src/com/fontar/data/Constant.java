package com.fontar.data;




public interface Constant {

	// public final static String FONTAR_HOME = "file:C:/Documents and
	// Settings/jdaccorso/workspace/fontar-web/webroot";//"file:C:/proyecto/fontar-web/webroot";
	// //"C:\\Proyecto\\Fontar\\workspace";
	public final static String FONTAR_HOME = "file:c:/eclipse/workspace_entrega_final/fontar-web/webroot";

	public interface EsActivoConvocatoria {
		public final static Boolean ACTIVO = true;

		public final static Boolean INACTIVO = false;
	}

	public interface EsActivoVentanilla {
		public final static Boolean ACTIVO = true;

		public final static Boolean INACTIVO = false;
	}

	public interface TipoDistribucionFinanciamiento {
		public final static String GLOBAL = "GLOBAL";

		public final static String JURISDICCIONAL = "JURISDICCIONAL";

		public final static String REGIONAL = "REGIONAL";
	}

	public interface PersonaSexo {
		public final static String FEMENINO = "F";

		public final static String MASCULINO = "M";
	}


	public interface EmpleoPermanenteDetalleTipo {
		public final static String PROFESIONAL = "PROFESIONAL";

		public final static String TECNICO = "TECNICO";

		public final static String OPERARIO_CALIFICADO = "OPERARIO_CALIFICADO";

		public final static String OPERARIO_NO_CALIF = "OPERARIO_NO_CALIF";
	}

	public interface NotificacionTipo {
		public final static String ADMISION = "ADMISION";

		public final static String READMISION = "READMISION";

		public final static String POSIB_RECONSIDERACION = "POSIB_RECON";

		public final static String OTRO = "OTRO";
	}


	public interface MatrizPresupuestoTipo {
		public final static String ANR = "ANR";

		public final static String ARAI = "ARAI";

		public final static String CONSEJERIAS = "CONSEJERIAS";

		public final static String CF = "CF";

		public final static String CF_CONSEJERIAS = "CF_CONSEJERIAS";

		public final static String PATENTE = "PATENTE";
	}

	public interface ProyAdmisionResultado {
		public final static String APROBADO = "APROBADO";

		public final static String RECHAZADO = "RECHAZADO";
	}

	public interface ProyReconsideracionResultado {
		public final static String APROBADO = "APROBADO";

		public final static String RECHAZADO = "RECHAZADO";
	}

	public interface ProySeguimientoTipo {
		public final static String FINANCIERO = "FINANCIERO";

		public final static String TECNICO = "TECNICO";
	}


	public interface BitacoraTema {
		public final static String PROY_DATOS_IDEA_PROYECTO = "Carga de Datos de la Idea Proyecto";

		public final static String PROY_DATOS_PROYECTO = "Carga de Datos del Proyecto";

		public final static String ALTA_IDEA_PROYECTO = "Alta Idea Proyecto";

		public final static String ASOCIACION_PRESUPUESTO = "Asociación de Presupuesto";

		public final static String PRESUPUESTO = "Presupuesto";

		public final static String JURISDICCIÓN = "Asignación de Jurisdicción";

		public final static String CREACION_PROYECTOS_PAQUETES = "Creación de Paquete";

		public final static String MODIFICACION_PROYECTOS_PAQUETES = "Modificación de Paquete";

		public final static String EVALUACION_PAQUETE = "Evaluación de Paquete";

		public final static String CONFIRMACION_EVALUACION_PAQUETE = "Confirmación Paquete";

		public final static String CONTROL_PROYECTOS_PAQUETES = "Control de Paquete";

		public final static String CREACION_PRESUPUESTO_PROYECTO = "Carga del Presupuesto del Proyecto";
		
		public final static String PEDIDO_DE_RECONSIDERACION = "Pedido de Reconsideración";

		public final static String FINALIZAR_POSIBILIDAD_RECONSIDERACION = "Finalización de posibilidad de Reconsideración";
		
		public final static String FIRMA_CONTRATO = "Firma de Contrato";
		
		public final static String NUEVO_SEGUIMIENTO = "Nuevo Seguimiento";
		
		public final static String ACT_SEGUIMIENTO = "Act. Seguimiento";
		
		public final static String ANULACION_SEGUIMIENTO = "Anulación Seguimiento";
		
		public final static String CERRADO_SEGUIMIENTO = "Cierre de Seguimiento";
		
		public final static String FINALIZACION_PROYECTO = "Finalización del Proyecto";
		
		public final static String GESTION_PAGO_SEGUIMIENTO = "Gestión de Pago del Seguimiento";
		
		public final static String FINALIZAR_CONTROL_EVALUACIONES = "Finalizar Control de Evaluaciones";
	}

	public interface BitacoraDescripcion {
		public final static String ASOCIACION_PRESUPUESTO = "Asociación de Presupuesto";

		public final static String ASOCIACION_JURISDICCIÓN = "Asociación de Jurisdicción";

		public final static String NA = "NA";

		public final static String SEGUIMIENTO_HISTORICO = "Seguimiento Histórico";		
	}

	public interface InstrumentoTipoDistribFinan {
		public final static String GLOBAL = "GLOBAL";

		public final static String REGIONAL = "REGIONAL";

		public final static String JURISDICCIONAL = "JURISDICCIONAL";
	}


	public interface Numericas {
		public final static String CERO = "0";
	}
	
	public interface InstanciaProyectoRaiz {
		public final static String PROYECTO = "Proyecto";
		public final static String IDEA_PROYECTO = "IdeaProyecto";
		public final static String PROYECTO_PITEC = "ProyectoPitec";
	}
	
	public interface PreProyectos {
		public final static String IDEA_PROYECTO = "IdeaProyecto";
		public final static String PRESENTACION = "Presentacion";
		public final static String PRESENTACION_CONVOCATORIA = "PresentacionConvocatoria";
	}
	
	public interface CabeceraAttribute {
		public final static String PROYECTO = "proyecto";
		public final static String IDEA_PROYECTO = "ideaProyecto";
		public final static String PRESENTACION = "presentacion";
		public final static String PAQUETE = "paquete";
		public final static String SEGUIMIENTO = "seguimiento";
		public final static String INSTRUMENTO = "instrumento";
		public final static String EVALUACION_SEGUIMIENTO = "evaluacionSeguimiento";
		public final static String EVALUACION = "evaluacion";
	}
	
	public interface AdministrarEvaluacionAttribute {
		public static final String FINALIZAR_CONTROL = "Finalizar Control";
		public static final String EVALUAR_RESULTADO = "Evaluar Resultado";
		public static final String RECONSIDERACION = "Reconsideración";
	}
	
	public static final String ACTION_AUTHORIZE = "ACTION_AUTHORIZE";
	
	public interface TiposEvaluacion {
		public static final String AUDITORIA_CONTABLE = "Auditoría Contable";
		public static final String CONTABLE = "Contable";
		public static final String ECONOMICA = "Económica";
		public static final String FINANCIERA = "Financiera";
		public static final String TECNICA = "Técnica";
		public static final String VISITA_TECNICA = "Visita Técnica";
	}
	
	public interface ClasificacionEvaluacionSeguimiento {
		public static final String ES_TECNICA = "ES_TECNICA";
		public static final String ES_CONTABLE = "ES_CONTABLE";
	}
	
	public interface ResultadoGestionPago {
		public static final String GESTIONAR = "Gestionar";
		public static final String NO_GESTIONAR = "No Gestionar";
		public static final String REVALUAR = "Revaluar";
	}
	
	public interface AccionesCircuitoAutorizacion {
		public static final String DESIGNAR_EVALUADOR_TECNICO = "DesignarEvaluadorTecnico"; 
		public static final String CARGAR_FUNDAMENTACION_EVALUADOR = "CargarFundamentacionEvaluador"; 
	}
	
	public interface FiltroProcedimientoItem {
		public static final String ITEMS_UFFA = "ITEMS_UFFA"; 
		public static final String ITEMS_BID = "ITEMS_BID"; 
	}
	
	public interface RendicionCuentasDescripcion {
		public static final String RENDICION_HISTORICA = "Rendición Histórica";
	}
}


