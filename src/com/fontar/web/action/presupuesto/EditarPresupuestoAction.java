package com.fontar.web.action.presupuesto;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.api.proyecto.presupuesto.PresupuestoPosProcessingTask;
import com.fontar.bus.api.proyecto.presupuesto.ProyectoPresupuestoServicio;
import com.fontar.bus.impl.proyecto.presupuesto.PresupuestoParsingException;
import com.fontar.data.Constant;
import com.fontar.data.impl.assembler.ProyectoRaizCabeceraAssembler;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle.ProyectoPresupuestoDetalleDTO;
import com.fontar.web.form.proyecto.presupuesto.EditarPresupuestoForm;
import com.fontar.web.form.proyecto.presupuesto.PresupuestoDownloadForm;
import com.fontar.web.form.proyecto.presupuesto.PresupuestoUploadForm;
import com.pragma.toolbar.NotImplementedException;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class EditarPresupuestoAction extends BaseMappingDispatchAction {

	protected AdministrarProyectoRaizServicio proyectoServicio;
	protected ProyectoPresupuestoServicio presupuestoServicio;

	protected static Map<String, String> PaginaDetalle = new Hashtable<String, String>();
	static {
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.ANR, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.ARAI, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CF, "detalle.cf.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CF_CONSEJERIAS, "detalle.cf.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.CONSEJERIAS, "detalle.anr.jsp");
		PaginaDetalle.put(Constant.MatrizPresupuestoTipo.PATENTE, "detalle.patente.jsp");
	}

	protected static final String SESSION_TEMP_VAR = "TempPresupuestoAdjuntoData";

	private static String adjuntoTemporarioLocation() {
		return "session:"+SESSION_TEMP_VAR;
	}
	private static void guardarAdjuntoTemporario(PresupuestoAdjuntoDTO adjuntoData) {
		WebContextUtil.getSession().setAttribute(SESSION_TEMP_VAR, adjuntoData);
	}
	private static void borrarAdjuntoTemporario() {
		WebContextUtil.getSession().removeAttribute(SESSION_TEMP_VAR);
	}
	private static PresupuestoAdjuntoDTO getAdjuntoTemporario() {
		return (PresupuestoAdjuntoDTO)WebContextUtil.getSession().getAttribute(SESSION_TEMP_VAR);
	}
	
	protected void setPosProcessingTask(PresupuestoPosProcessingTask task) {
		PresupuestoAdjuntoDTO adjuntoTemporario = getAdjuntoTemporario();
		if(adjuntoTemporario!=null)
			adjuntoTemporario.setTask(task);
	}
	
	public void setPresupuestoServicio(ProyectoPresupuestoServicio presupuestoServicio) {
		this.presupuestoServicio = presupuestoServicio;
	}
	public void setProyectoServicio(AdministrarProyectoRaizServicio proyectoServicio) {
		this.proyectoServicio = proyectoServicio;
	}

	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;
		ProyectoPresupuestoDTO presupuesto = null;
		if(presupuestoForm.getIdPresupuesto()==null) {
			//trato de buscarlo en el request
			presupuestoForm.setIdPresupuesto((Long)request.getAttribute("idPresupuesto"));
		}
		if(presupuestoForm.getIdPresupuesto()==null) {
			//si sigo sin tener el id lo busco en la sesion y lo consumo.
			presupuestoForm.setIdPresupuesto((Long)request.getSession().getAttribute("idPresupuesto"));
			request.getSession().removeAttribute("idPresupuesto");
		}
		if(presupuestoForm.getIdPresupuesto()!=null) {
			presupuesto = presupuestoServicio.load(presupuestoForm.getIdPresupuesto());
		}
		
		ProyectoPresupuestoDetalleDTO presupuestoDecorator = new ProyectoPresupuestoDetalleDTO(presupuesto, (presupuesto==null ? "id:0" : "id:"+presupuestoForm.getIdPresupuesto()));
		request.setAttribute("presupuesto", presupuestoDecorator);
		
		if(presupuestoForm.getIdProyecto()==null) {
			//trato de buscarlo en el request
			presupuestoForm.setIdProyecto((Long)request.getAttribute("idProyecto"));
		}
		if(presupuestoForm.getIdProyecto()==null) {
			//trato de buscarlo en la sesion
			presupuestoForm.setIdProyecto((Long)request.getSession().getAttribute("idProyecto"));
			request.getSession().removeAttribute("idProyecto");
		}

		ProyectoRaizDTO proyecto = proyectoServicio.getProyectoRaizDTO(presupuestoForm.getIdProyecto());
		request.setAttribute("proyectoRaiz", proyecto);

		request.setAttribute("proyecto", ProyectoRaizCabeceraAssembler.buildDTO(proyecto));
		request.setAttribute("paginaDetalle", PaginaDetalle.get(proyecto.getTipoMatrizPresupuesto()));
		
		//Acciones
		request.setAttribute("cargarAction", "/EditarPresupuestoCargar.do");
		request.setAttribute("cerrarAction", "/EditarPresupuestoCerrar.do");
		request.setAttribute("downloadAction", "/EditarPresupuestoDownload.do?idPresupuesto="+String.valueOf(presupuestoDecorator.getId()));
		
		//configuracion
		request.setAttribute("showProjectHeader", Boolean.FALSE);

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PresupuestoUploadForm uploadForm = (PresupuestoUploadForm) form;

		request.setAttribute("idProyecto", uploadForm.getIdProyecto());
		request.setAttribute("idPresupuesto", uploadForm.getIdPresupuesto());
		
		ArchivoDTO archivo = uploadForm.archivo();
		
		if(archivo==null) {
			ActionMessages errors = getErrors(request);
			addError(errors, "app.file.fileNotFound");
			saveErrors(request, errors);
			return mapping.findForward(FORWARD_INVALID);
		}
		
		ProyectoRaizDTO proyecto = proyectoServicio.getProyectoRaizDTO(uploadForm.getIdProyecto());
		ProyectoPresupuestoDTO presupuesto;
		try {
			if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.ANR)) {
				presupuesto = presupuestoServicio.parseAdjuntoANR(archivo); 
			} else {
				if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.CF)) {
					presupuesto = presupuestoServicio.parseAdjuntoCF(archivo); 
				} else {
					if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.CF_CONSEJERIAS)) {
						presupuesto = presupuestoServicio.parseAdjuntoCFConsejerias(archivo); 
					} else {
						if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.ARAI)) {
							presupuesto = presupuestoServicio.parseAdjuntoARAI(archivo); 
						} else {
							if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.CONSEJERIAS)) {
								presupuesto = presupuestoServicio.parseAdjuntoConsejerias(archivo); 
							} else {
								if(proyecto.getTipoMatrizPresupuesto().equals(Constant.MatrizPresupuestoTipo.PATENTE)) {
									presupuesto = presupuestoServicio.parseAdjuntoPatente(archivo); 
								} else {
									throw new NotImplementedException();
								}
							}
						}
					}
				}
			}
		} catch (PresupuestoParsingException e) {
			ActionMessages errors = getErrors(request);
			addError(errors, "app.proyecto.presupuesto.invalidPresupuesto");
			for(String message : e.getMessages()) {
				addLiteralError(errors, message);
				
			}
			saveErrors(request, errors);
			return mapping.findForward(FORWARD_INVALID);
		}

		request.setAttribute("presupuesto", new ProyectoPresupuestoDetalleDTO(presupuesto, adjuntoTemporarioLocation()+".presupuesto"));
		request.setAttribute("proyectoRaiz", proyecto);
		request.setAttribute("proyecto", ProyectoRaizCabeceraAssembler.buildDTO(proyecto));			
		
		guardarAdjuntoTemporario(
				new PresupuestoAdjuntoDTO(
						archivo,
						presupuesto,
						Long.valueOf(proyecto.getId()),
						uploadForm.getIdPresupuesto()
				)
		);
		request.setAttribute("paginaDetalle", PaginaDetalle.get(proyecto.getTipoMatrizPresupuesto()));
		//Acciones
		request.setAttribute("guardarAction", "/GuardarPresupuesto.do");
		request.setAttribute("cancelarAction", "/EditarPresupuestoCancelar.do");
		
		//Configuracion
		request.setAttribute("showProjectHeader", Boolean.FALSE);

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cancelar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EditarPresupuestoForm presupuestoForm = (EditarPresupuestoForm) form;
		
		PresupuestoAdjuntoDTO adjuntoTemporario = getAdjuntoTemporario();
		presupuestoForm.setIdProyecto(adjuntoTemporario.getIdProyecto());
		presupuestoForm.setIdPresupuesto(adjuntoTemporario.getIdPresupuestoAnterior());
		
		borrarAdjuntoTemporario();
		return mapping.findForward(FORWARD_SUCCESS);
	}
	public ActionForward cerrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS);
	}
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PresupuestoDownloadForm downloadForm = (PresupuestoDownloadForm) form;
		Long idPresupuesto = downloadForm.getIdPresupuesto();
		AdjuntoDTO adjunto = presupuestoServicio.getAdjunto(idPresupuesto);
		downloadForm.setContentType(adjunto.getTipoContenido());
		
		request.setAttribute("contentType", adjunto.getTipoContenido());
		
		downloadForm.setFilename(adjunto.getNombre());
		request.setAttribute("filename", adjunto.getNombre());
		
		downloadForm.setId(adjunto.getIdAdjuntoContenido());
		request.setAttribute("id", adjunto.getIdAdjuntoContenido());
		
		//Esto es para que sobre ssl no de error al querer ver el adjunto
		response.setHeader("PRAGMA","");
		response.setHeader("EXPIRES","");
		
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
