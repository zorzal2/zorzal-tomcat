package com.fontar.bus.impl.proyecto.presupuesto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

import com.fontar.bus.api.proyecto.presupuesto.ProyectoPresupuestoServicio;
import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.ActividadDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.impl.assembler.ProyectoPresupuestoDTOAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioANRParser;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioARAIParser;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioCFConsejeriasParser;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioCFParser;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioConsejeriasParser;
import com.fontar.proyecto.presupuesto.excel.parser.FormularioPatenteParser;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParser;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.util.ExcelUtil;
import com.pragma.excel.exception.ParsingException;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * 
 * @author ssanchez Implementa los metodos de CRUD para ProyectoBean
 */
public class ProyectoPresupuestoServicioImpl implements ProyectoPresupuestoServicio {

	private ProyectoPresupuestoDAO proyectoPresupuestoDAO;
	private ProyectoDAO proyectoDao;
	private EtapaDAO etapaDao;
	private ActividadDAO actividadDao;
	private AdjuntoServicio adjuntoServicio;

	public AdjuntoServicio getAdjuntoServicio() {
		return adjuntoServicio;
	}

	public void setAdjuntoServicio(AdjuntoServicio adjuntoServicio) {
		this.adjuntoServicio = adjuntoServicio;
	}

	public PresupuestoAdjuntoDTO savePresupuesto(PresupuestoAdjuntoDTO adjunto) {
		ProyectoPresupuestoDTO presupuesto = adjunto.getPresupuesto();
		ProyectoPresupuestoBean presupuestoBean = ProyectoPresupuestoDTOAssembler.updateBean(presupuesto);

		BitacoraBean bitacora = presupuestoBean.getBitacora();
		bitacora.setIdProyecto(adjunto.getIdProyecto());
		bitacora.setTipo(TipoBitacora.PRESUPUESTO);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);
		bitacora.setDescripcion(Constant.BitacoraDescripcion.NA);
		
		bitacora.setIdUsuario( this.getUserId() );
		
		presupuestoBean = getProyectoPresupuestoDAO().save(presupuestoBean);
		
		//Grabo el plan de trabajo
		for(EtapaBean etapa : presupuestoBean.getEtapas()) {
			//completo la etapa
			etapa.setPresupuesto(presupuestoBean);
			etapa.setIdPresupuesto(presupuestoBean.getId());
			etapaDao.save(etapa);
			for(ActividadBean actividad : etapa.getActividades()) {
				actividad.setEtapa(etapa);
				actividadDao.save(actividad);
			}
		}
		
		//adjuntos
		AdjuntoDTO adjuntoDTO = new AdjuntoDTO();
		adjuntoDTO.setCantidadLongitud(adjunto.getArchivo().getLongitud());
		adjuntoDTO.setNombre(adjunto.getArchivo().getNombre());
		adjuntoDTO.setTipoContenido(adjunto.getArchivo().getTipoContenido());
		adjuntoDTO.setFecha(adjunto.getArchivo().getFecha());
		adjuntoDTO.setDescripcion(adjunto.getPresupuesto().getFundamentacion());

		AdjuntoContenidoDTO adjuntoContenidoDTO = new AdjuntoContenidoDTO();
		adjuntoContenidoDTO.setBlArchivo(adjunto.getArchivo().getBytes());
		
		adjuntoServicio.uploadAdjunto(adjuntoDTO, adjuntoContenidoDTO, presupuestoBean.getId());
		
		//actualizo el proyecto
		//ProyectoBean proyectoBean = proyectoDao.read(adjunto.getIdProyecto());
		//proyectoBean.setIdPresupuesto(presupuestoBean.getId());
		//if(proyectoBean.getIdPresupuestoOriginal()==null)proyectoBean.setIdPresupuestoOriginal(presupuestoBean.getId());
		//proyectoDao.save(proyectoBean);
		
		adjunto.setPresupuesto(ProyectoPresupuestoDTOAssembler.buildDto(presupuestoBean));
		
		//Hago el PosProcesamiento si esta definido 
		if(adjunto.getTask()!=null) adjunto.getTask().proccess(adjunto);
		
		return adjunto;
	}

	public ProyectoPresupuestoDAO getProyectoPresupuestoDAO() {
		return proyectoPresupuestoDAO;
	}

	public void setProyectoPresupuestoDAO(ProyectoPresupuestoDAO proyectoPresupuestoDAO) {
		this.proyectoPresupuestoDAO = proyectoPresupuestoDAO;
	}

	public void setActividadDao(ActividadDAO actividadDao) {
		this.actividadDao = actividadDao;
	}

	public void setEtapaDao(EtapaDAO etapaDao) {
		this.etapaDao = etapaDao;
	}

	public ActividadDAO getActividadDao() {
		return actividadDao;
	}

	public EtapaDAO getEtapaDao() {
		return etapaDao;
	}

	public ProyectoDAO getProyectoDao() {
		return proyectoDao;
	}

	public void setProyectoDao(ProyectoDAO proyectoDAO) {
		this.proyectoDao = proyectoDAO;
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}

	public ProyectoPresupuestoDTO load(Long idPresupuesto) {
		ProyectoPresupuestoBean bean = proyectoPresupuestoDAO.read(idPresupuesto);
		return ProyectoPresupuestoDTOAssembler.buildDto(bean);
	}

	public AdjuntoDTO getAdjunto(Long idPresupuesto) {
		Collection adjuntos = adjuntoServicio.obtenerAdjuntos(idPresupuesto);
		//El adjunto es exactamente uno.
		return (AdjuntoDTO)adjuntos.iterator().next();
	}

	public ProyectoPresupuestoDTO savePresupuestoYActualizarProyecto(ProyectoPresupuestoDTO presupuesto, ProyectoRaizDTO proyecto) {
		ProyectoPresupuestoBean presupuestoBean = ProyectoPresupuestoDTOAssembler.updateBean(presupuesto);

		BitacoraBean bitacora = presupuestoBean.getBitacora();
		bitacora.setIdProyecto(Long.valueOf(proyecto.getId()));
		bitacora.setTipo(TipoBitacora.PRESUPUESTO);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setTema(Constant.BitacoraTema.CREACION_PRESUPUESTO_PROYECTO);
		bitacora.setDescripcion(Constant.BitacoraDescripcion.NA);
		
		bitacora.setIdUsuario( this.getUserId() );
		
		ProyectoRaizBean proyectoBean = proyectoDao.read(Long.valueOf(proyecto.getId()));

		//Propago datos del presupuesto anterior
		if(proyectoBean.getUltimoPresupuesto()!=null) proyectoBean.getUltimoPresupuesto().propagarDatosA(presupuestoBean);
		
		presupuestoBean = getProyectoPresupuestoDAO().save(presupuestoBean);
		
		//actualizo el proyecto
		proyectoBean.setIdPresupuesto(presupuestoBean.getId());
		if(proyectoBean.getIdPresupuestoOriginal()==null)
			proyectoBean.setIdPresupuestoOriginal(presupuestoBean.getId());
		((ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao")).save(proyectoBean);
		
		return presupuesto;
	}

	public ProyectoPresupuestoDTO parseAdjuntoANR(ArchivoDTO archivo) throws PresupuestoParsingException {
		return baseParseAdjunto(archivo, new FormularioANRParser());
	}
	
	public ProyectoPresupuestoDTO parseAdjuntoCF(ArchivoDTO archivo) throws PresupuestoParsingException {
		return baseParseAdjunto(archivo, new FormularioCFParser());
	}

	public ProyectoPresupuestoDTO parseAdjuntoCFConsejerias(ArchivoDTO archivo) throws PresupuestoParsingException {
		return baseParseAdjunto(archivo, new FormularioCFConsejeriasParser());
	}
	
	public ProyectoPresupuestoDTO parseAdjuntoARAI(ArchivoDTO archivo) throws PresupuestoParsingException {
		return baseParseAdjunto(archivo, new FormularioARAIParser());
	}
	
	public ProyectoPresupuestoDTO parseAdjuntoConsejerias(ArchivoDTO dto) throws PresupuestoParsingException {
		return baseParseAdjunto(dto, new FormularioConsejeriasParser());
	}
	
	public ProyectoPresupuestoDTO parseAdjuntoPatente(ArchivoDTO dto) throws PresupuestoParsingException {
		return baseParseAdjunto(dto, new FormularioPatenteParser());
	}
	
	private ProyectoPresupuestoDTO baseParseAdjunto(ArchivoDTO archivo, PresupuestoParser parser) throws PresupuestoParsingException {
		Workbook workbook = getWorkbook(archivo);
		ProyectoPresupuestoBean presupuesto;
		try {
			presupuesto = parser.parse(workbook);
		}
		/*catch (ValidationException e) {
			throw new PresupuestoParsingException("app.file.validationError", e.getMessage());
		}
		catch (IllegalFormatException e) {
			throw new PresupuestoParsingException("app.proyecto.presupuesto.invalidFormatPresupuesto", e.getMessage());
		}*/
		catch (ParsingException e) {
			PresupuestoParsingException exception = new PresupuestoParsingException();
			exception.appendMessages(e.getMessages());
			throw exception;
		}
		return ProyectoPresupuestoDTOAssembler.buildDto(presupuesto);
	}

	private Workbook getWorkbook(ArchivoDTO archivo) throws PresupuestoParsingException {
		//abro el excel
		InputStream inputStream = new ByteArrayInputStream(archivo.getBytes());
		Workbook workbook;
		try {
			workbook = ExcelUtil.getWorkbook(inputStream);
		}
		catch (PasswordException e) {
			throw new PresupuestoParsingException("app.file.requierePassword");
		}
		catch (BiffException e) {
			throw new PresupuestoParsingException("app.file.invalidFormat");
		}
		catch (IOException e) {
			throw new PresupuestoParsingException("app.file.fileNotFound");
		}
		catch (Exception e) {
			throw new PresupuestoParsingException("app.proyecto.presupuesto.unknownError");
		}
		return workbook;
	}
}
