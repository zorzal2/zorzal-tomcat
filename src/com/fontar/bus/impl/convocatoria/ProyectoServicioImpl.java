package com.fontar.bus.impl.convocatoria;

import java.util.Date;

import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.bus.api.convocatoria.ProyectoServicio;
import com.fontar.bus.api.entidad.EntidadBeneficiariaServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.impl.assembler.EmpleoPermanenteAssembler;
import com.fontar.data.impl.assembler.LocalizacionAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.bus.impl.GenericServiceImpl;
import com.pragma.util.DateTimeUtil;
import com.pragma.web.WebContextUtil;

/**
 * 
 * @author ssanchez Implementa los metodos de CRUD para ProyectoBean
 */
public class ProyectoServicioImpl extends GenericServiceImpl implements ProyectoServicio {

	private EntidadBeneficiariaServicio entidadBeneficiariaServicio;
	
	public ProyectoServicioImpl(Class type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Guarda un nuevo Proyecto y sus objetos relacionados
	 */
	public void storeBeans(Object... arguments) {
		// Alta de workflow de proyecto
		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

		// levanto la definición
		GraphSession graphSession = jbpmContext.getGraphSession();
		ProcessDefinition processDefinition = graphSession.findLatestProcessDefinition(JbpmConstants.ProcessNames.PROYECTO);

		// creo la instancia
		ProcessInstance processInstance = new ProcessInstance(processDefinition);

		// cargo el ID de Workflow al bean
		ProyectoBean proyectoBean = (ProyectoBean) arguments[0];
		proyectoBean.setIdWorkFlow(processInstance.getId());		//  
		proyectoBean.setEstadoEnPaquete(false);						// 

		// ---------Ini:Guardo proyecto y objetos relacionados---------//
		// guardo un nuevo proyecto
		saveBean(proyectoBean);
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ID_PROYECTO, proyectoBean.getId());
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ES_VENTANILLA, (proyectoBean.getIdPresentacion() == null) ? "SI":"NO");
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, proyectoBean.getIdInstrumento());

		// guardo la bitacora del Proyecto
		BitacoraBean bitacoraProy = (BitacoraBean) arguments[1];
		bitacoraProy.setIdProyecto(proyectoBean.getId());
		bitacoraProy.setIdUsuario(this.getUserId());
		saveBean(bitacoraProy);

		LocalizacionBean localizacionBean = LocalizacionAssembler.getInstance().buildBean((LocalizacionDTO) arguments[4]);
		EmpleoPermanenteBean empleoPermanenteBean = EmpleoPermanenteAssembler.getInstance().buildBean((EmpleoPermanenteDTO) arguments[5]);

		// guardo nuevo proyecto datos
		ProyectoDatosBean datosBean = (ProyectoDatosBean) arguments[2];
		datosBean.getBitacora().setIdProyecto(proyectoBean.getId());

		if (localizacionBean != null) { // Si es una idea proyecto
			saveBean(localizacionBean);
			datosBean.setIdLocalizacion(localizacionBean.getId());
		}

		if (empleoPermanenteBean != null) {
			saveBean(empleoPermanenteBean);
			proyectoBean.setIdEmpleoPermanente(empleoPermanenteBean.getId());
		}

		saveBean(datosBean);

		// guardo nueva jurisdiccion
		ProyectoJurisdiccionBean jurisdiccionBean = (ProyectoJurisdiccionBean) arguments[3];
		jurisdiccionBean.getBitacora().setIdProyecto(proyectoBean.getId());
		saveBean(jurisdiccionBean);

		// cambio el estado de la presentacion
		boolean vieneDePresentacion = proyectoBean.getIdPresentacion() != null;
		PresentacionConvocatoriaBean convocatoriaBean = null;
		IdeaProyectoBean ideaProyectoBean = null;
		if (vieneDePresentacion) {
			PresentacionConvocatoriaDAO presentacionConvocatoriaDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
			convocatoriaBean = (PresentacionConvocatoriaBean) presentacionConvocatoriaDAO.read(proyectoBean.getIdPresentacion());
			convocatoriaBean.setEstadoFinalizada();
			updateBean(convocatoriaBean);
		}
		else {
			IdeaProyectoDAO ideaProyectoDAO = (IdeaProyectoDAO) WebContextUtil.getBeanFactory().getBean("ideaProyectoDao");
			ideaProyectoBean = (IdeaProyectoBean) ideaProyectoDAO.read(proyectoBean.getIdProyectoOrigen());
			ideaProyectoBean.setEstadoFinalizada();
			updateBean(ideaProyectoBean);
		}

		// actualizo las referencias de el objeto Proyecto a sus objetos
		// relacionados
		proyectoBean.setIdDatos(datosBean.getId());
		proyectoBean.setIdProyectoJurisdiccion(jurisdiccionBean.getId());
		//		
		// if (vieneDePresentacion) {
		// proyectoBean.setIdPresentacion(convocatoriaBean.getId());
		// }
		updateBean(proyectoBean);
		// ---------Fin:Guardo proyecto y objetos relacionados---------//

		// avanzo el token para salir del startstate
		processInstance.getRootToken().signal();

		// guardo la instancia
		jbpmContext.save(processInstance);
	}

	public void createProyecto(ProyectoBean proyectoBean) {
		// cargo el estado
		
		boolean vieneDePresentacion = !proyectoBean.getInstrumento().esVentanilla();
		Boolean paraProyectoHistorico = proyectoBean.getInstrumento().getParaProyectoHistorico();
		
		if (vieneDePresentacion) {
			proyectoBean.setEstado(EstadoProyecto.INICIADO);

		} else {
			proyectoBean.setEstado(EstadoProyecto.ADMITIDO);
			if (paraProyectoHistorico) {
				proyectoBean.setEstado(EstadoProyecto.INICIADO);
			}
		}
		
		proyectoBean.setIdInstrumento(proyectoBean.getInstrumento().getId());
		
		proyectoBean.setEstadoReconsideracion(false);
		proyectoBean.setEstadoEnPaquete(false); 

		Date now = DateTimeUtil.getDate();
		ProyectoDatosBean proyectoDatos = proyectoBean.getProyectoDatos();
		proyectoDatos.setFechaIngreso(now);
		
		//Representante
		PersonaBean personaRepresentante = proyectoDatos.getPersonaRepresentante();
		if(personaRepresentante!=null) {
			if(personaRepresentante.getId()==null) {
				if(personaRepresentante.getLocalizacion()!=null) {
					if(personaRepresentante.getLocalizacion().getId()==null) {
						saveBean(personaRepresentante.getLocalizacion());	
					}
					personaRepresentante.setIdLocalizacion(personaRepresentante.getLocalizacion().getId());
				}
				saveBean(personaRepresentante);
			}
			proyectoDatos.setIdPersonaRepresentante(personaRepresentante.getId());
		}
		//Director
		PersonaBean personaDirector = proyectoDatos.getPersonaDirector();
		if(personaDirector!=null) {
			if(personaDirector.getId()==null) {
				if(personaDirector.getLocalizacion()!=null) {
					if(personaDirector.getLocalizacion().getId()==null) {
						saveBean(personaDirector.getLocalizacion());
					}					
					personaDirector.setIdLocalizacion(personaDirector.getLocalizacion().getId());
				}
				saveBean(personaDirector);
			}
			proyectoDatos.setIdPersonaDirector(personaDirector.getId());
		}
		//Responsable
		PersonaBean personaResponsableLegal = proyectoDatos.getPersonaLegal();
		if(personaResponsableLegal!=null) {
			if(personaResponsableLegal.getId()==null) {
				if(personaResponsableLegal.getLocalizacion()!=null) {
					if(personaResponsableLegal.getLocalizacion().getId()==null) {
						saveBean(personaResponsableLegal.getLocalizacion());
					}					
					personaResponsableLegal.setIdLocalizacion(personaResponsableLegal.getLocalizacion().getId());
				}
				saveBean(personaResponsableLegal);
			}
			proyectoDatos.setIdPersonaLegal(personaResponsableLegal.getId());
		}
		

		BitacoraBean bitacoraProy = new BitacoraBean();
		bitacoraProy.setTipo(TipoBitacora.BASICO);
		bitacoraProy.setFechaRegistro(now);
		bitacoraProy.setFechaAsunto(now);
		bitacoraProy.setTema("Alta Proyecto");
		bitacoraProy.setDescripcion("NA");
		bitacoraProy.setIdUsuario(this.getUserId());

		// cargo datos de bitacora correspondiente a Proyecto Datos
		BitacoraBean bitacoraDatos = proyectoDatos.getBitacora();
		bitacoraDatos.setTipo( TipoBitacora.PROY_DATOS);
		bitacoraDatos.setFechaRegistro(now);
		bitacoraDatos.setFechaAsunto(now);
		bitacoraDatos.setTema(Constant.BitacoraTema.PROY_DATOS_PROYECTO);
		bitacoraDatos.setDescripcion("NA");
		bitacoraDatos.setIdUsuario(this.getUserId());

		LocalizacionBean localizacionBean = proyectoDatos.getLocalizacion();
		if(localizacionBean!=null) {
			if(localizacionBean.getId()==null) saveBean(localizacionBean);
			proyectoDatos.setIdLocalizacion(localizacionBean.getId());
		}
		
		//Jurisdiccion
		BitacoraBean bitacoraJurisdiccion = null;
		ProyectoJurisdiccionBean proyectoJurisdiccionBean = null;
		if(proyectoBean.getProyectoJurisdiccion()!=null) {
			proyectoJurisdiccionBean = proyectoBean.getProyectoJurisdiccion();

			bitacoraJurisdiccion = proyectoJurisdiccionBean.getBitacora();
			bitacoraJurisdiccion.setTipo( TipoBitacora.PROY_JURISDICCION);
			bitacoraJurisdiccion.setFechaRegistro(now);
			bitacoraJurisdiccion.setFechaAsunto(now);
			bitacoraJurisdiccion.setTema("Jurisdicción");
			bitacoraJurisdiccion.setDescripcion("Asociación de Jurisdicción");
			bitacoraJurisdiccion.setIdUsuario(this.getUserId());
		}
		
		if (proyectoBean.getEmpleoPermanente() == null) proyectoBean.setEmpleoPermanente(new EmpleoPermanenteBean()); 
		if (proyectoBean.getEmpleoPermanente().getId() == null) saveBean(proyectoBean.getEmpleoPermanente());
		proyectoBean.setIdEmpleoPermanente(proyectoBean.getEmpleoPermanente().getId());
		
		// Alta de workflow de proyecto
		JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
		
		// levanto la definición
		GraphSession graphSession = jbpmContext.getGraphSession();

		ProcessDefinition processDefinition = new ProcessDefinition();
		if(proyectoBean.getInstrumento().getParaProyectoHistorico()) {
			processDefinition = graphSession.findLatestProcessDefinition(JbpmConstants.ProcessNames.PROYECTO_HISTORICO);
		} else {
			processDefinition = graphSession.findLatestProcessDefinition(JbpmConstants.ProcessNames.PROYECTO);
		}
			
		// creo la instancia
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
		// cargo el ID de Workflow al bean
		proyectoBean.setIdWorkFlow(processInstance.getId());

		// guardo un nuevo proyecto
		saveBean(proyectoBean);
		
		proyectoDatos.setEntidadBeneficiaria(entidadBeneficiariaServicio.createOrUpdate(proyectoDatos.getEntidadBeneficiaria()));
		proyectoDatos.setIdEntidadBeneficiaria(proyectoDatos.getEntidadBeneficiaria().getId());
		
		proyectoDatos.getBitacora().setIdProyecto(proyectoBean.getId());
		saveBean(proyectoDatos);
		proyectoBean.setIdDatos(proyectoDatos.getId());
		
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ID_PROYECTO, proyectoBean.getId());
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ES_VENTANILLA, (!vieneDePresentacion) ? "SI":"NO");
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, proyectoBean.getIdInstrumento());
	
		// guardo nuevo proyecto datos
		bitacoraDatos.setIdProyecto(proyectoBean.getId());

		// guardo la bitacora del Proyecto
		bitacoraProy.setIdProyecto(proyectoBean.getId());
		bitacoraProy.setIdUsuario(this.getUserId());
		saveBean(bitacoraProy);
		
		// guardo nueva jurisdiccion
		if(proyectoJurisdiccionBean!=null) {
			bitacoraJurisdiccion.setIdProyecto(proyectoBean.getId());
			saveBean(proyectoJurisdiccionBean);
			proyectoBean.setIdProyectoJurisdiccion(proyectoJurisdiccionBean.getId());
		}
		
		// cambio el estado de la presentacion
		if (vieneDePresentacion) {
			PresentacionConvocatoriaBean convocatoriaBean = proyectoBean.getPresentacionConvocatoria();
			if(convocatoriaBean!=null) {
				convocatoriaBean.setEstadoFinalizada();
				updateBean(convocatoriaBean);
			}
		}
		else if(proyectoBean.getIdProyectoOrigen()!=null) {
			IdeaProyectoBean ideaProyectoBean = null;
			IdeaProyectoDAO ideaProyectoDAO = (IdeaProyectoDAO) WebContextUtil.getBeanFactory().getBean("ideaProyectoDao");
			ideaProyectoBean = (IdeaProyectoBean) ideaProyectoDAO.read(proyectoBean.getIdProyectoOrigen());
			ideaProyectoBean.setEstadoFinalizada();
			updateBean(ideaProyectoBean);
		}

		//actualizo porque hice modificaciones despues de guardarlo
		updateBean(proyectoBean);

		// avanzo el token para salir del startstate
		processInstance.getRootToken().signal();

		// guardo la instancia
		jbpmContext.save(processInstance);
	}

	public void setEntidadBeneficiariaServicio(EntidadBeneficiariaServicio entidadBeneficiariaServicio) {
		this.entidadBeneficiariaServicio = entidadBeneficiariaServicio;
	}
}