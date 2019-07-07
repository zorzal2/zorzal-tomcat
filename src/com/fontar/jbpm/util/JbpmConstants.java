package com.fontar.jbpm.util;

public interface JbpmConstants {

	/**
	 * @author fferrara Nombre de las Variables utilizadas en el Jbpm
	 */
	public interface VariableNames {
		public static String ID_PROYECTO = "ID_PROYECTO";

		public static String ID_EVALUACION = "ID_EVALUACION";

		public static String ID_PAQUETE = "ID_PAQUETE";

		public static String ID_PROCESO_PROYECTOS = "ID_PROCESO_PROYECTOS";

		public static String TIPO_PAQUETE = "TIPO_PAQUETE";

		public static String BEAN_ACTION_NAME = "BEAN_ACTION_NAME";

		public static String LEAVE_TRANSITION = "LEAVE_TRANSITION";

		public static String ID_SEGUIMIENTO= "ID_SEGUIMIENTO";
		
		// FF: NO se setean desde la aplicación
		public static String ESTADO = "ESTADO";

		public static String RESULTADO_ADMISION = "RESULTADO_ADMISION";
		
		public static String ES_VENTANILLA = "ES_VENTANILLA";
		
		public static String RECOMENDACION = "RECOMENDACION";
		
		public static String ID_INSTRUMENTO = "ID_INSTRUMENTO";

		public static String PERMISSION_REQUIRED  = "PERMISSION";
		
		public static String ID_ACTION_WORKFLOW = "idActionWorkflow";
		
		public static String ID_NOTIFICACION = "ID_NOTIFICACION";
		
		public static String REQUIERE_ACUSE = "REQUIERE_ACUSE";
		
		public static String ES_FINANCIERO = "ES_FINANCIERO";
		
		public static String CON_ITEMS_UFFA = "CON_ITEMS_UFFA"; 

		public static String CON_ITEMS_BID = "CON_ITEMS_BID"; 

		public static String CON_ITEMS_PLIEGO_PRECLASIFICACION = "CON_ITEMS_PLIEGO_PRECLASIFICACION";
		
		public static String ID_PROCEDIMIENTO = "ID_PROCEDIMIENTO";
	}

	/**
	 * @author fferrara Nombre de los Beans de servicios Fontar que se llaman
	 * desde Jbpm
	 */
	public interface BeanServiceNames {
		public static String EVALUAR_ADMISIBILDAD = "evaluarAdmisibilidadService";
	}

	/**
	 * @author fferrara Nombre de los Beans de servicios Fontar que se llaman
	 * desde Jbpm
	 */
	public interface ProcessNames {
		public static String PROYECTO = "Proyecto";
		
		public static String PROYECTO_HISTORICO = "ProyectoHistorico";

		public static String EVALUACION = "Evaluacion";

		public static String EVALUACIONSEGUIMIENTO = "EvaluacionSeguimiento";

		public static String PAQUETE = "Paquete";

		public static String PAQUETE_DIRECTORIO = "PaqueteDirectorio";

		public static String IDEA_PROYECTO = "IdeaProyecto";
	
		public static String PROYECTO_PITEC = "ProyectoPitec";
		
		public static String IDEA_PROYECTO_PITEC = "IdeaProyectoPitec";
		
		public static String SEGUIMIENTO = "Seguimiento";
		
		public static String NOTIFICACION = "Notificacion";

		public static String CONTROL_ADQUISICION = "ControlAdquisicion";
		
	}

	/**
	 * @author fferrara
	 * 
	 */
	public interface WebVariableNames {
		public static String CURRENT_TASK = "CURRENT_TASK";

		public static String ID_TASK_INSTANCE = "idTaskInstance";
	}

}
