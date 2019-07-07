package com.fontar.bus.impl.proyecto.datos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.api.convocatoria.ProyectoServicio;
import com.fontar.bus.api.entidad.EntidadBeneficiariaServicio;
import com.fontar.bus.api.proyecto.datos.ProyectosExcelServicio;
import com.fontar.bus.impl.proyecto.datos.matcher.AbstractUserFeedbackRequest;
import com.fontar.bus.impl.proyecto.datos.matcher.EntidadMatcher;
import com.fontar.bus.impl.proyecto.datos.matcher.PersonaMatcher;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.assembler.EmpleoPermanenteAssembler;
import com.fontar.data.impl.assembler.ProyectoAssembler;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.proyecto.datos.excel.parser.FormularioAParser;
import com.fontar.proyecto.datos.modelo.LocalizacionInfo;
import com.fontar.proyecto.datos.modelo.ProyectoInfo;
import com.fontar.util.ExcelUtil;
import com.fontar.util.ResourceManager;
import com.pragma.PragmaControlledException;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.util.ContextUtil;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * 
 * @author ssanchez Implementa los metodos de CRUD para ProyectoBean
 */
public class ProyectosExcelServicioImpl implements ProyectosExcelServicio {

	private ProyectoServicio proyectoServicio;
	private CiiuDAO ciiuDao;
	private EntidadBeneficiariaServicio entidadBeneficiariaServicio;
	private EntidadDAO entidadDao;
	private LocalizacionDAO localizacionDao;
	private JurisdiccionDAO jurisdiccionDao;
	private ConfiguracionServicio configuracionServicio;
	private TipoProyectoDAO tipoProyectoDao;
	private InstrumentoDAO instrumentoDao;
	private PresentacionConvocatoriaDAO presentacionConvocatoriaDao;
	private PersonaMatcher personaMatcher;
	private EntidadMatcher entidadMatcher;
	

	public Collection<ProyectoDTO> parseArchivo(ArchivoDTO archivo) throws ProyectosExcelParsingException, ParsingException, ProyectosExcelFeedbackRequiredException, UserCancelledException {
		return parseArchivo(archivo, new HashMap<String, UserFeedbackResponse>());
	}
	public Collection<ProyectoDTO> parseArchivo(
			ArchivoDTO archivo, 
			Collection<UserFeedbackResponse> feedbackResponses) 
			throws 
				ProyectosExcelParsingException, 
				ParsingException, 
				ProyectosExcelFeedbackRequiredException, UserCancelledException {
		
		HashMap<String, UserFeedbackResponse> map = new HashMap<String, UserFeedbackResponse>();
		for(UserFeedbackResponse userFeedback : feedbackResponses) {
			map.put(userFeedback.getId(), userFeedback);
		}
		return parseArchivo(archivo, map);
	}
	private Collection<ProyectoDTO> parseArchivo(
			ArchivoDTO archivo, 
			Map<String, UserFeedbackResponse> feedbackResponses) 
			throws 
				ProyectosExcelParsingException, 
				ParsingException, 
				ProyectosExcelFeedbackRequiredException, UserCancelledException {
		
		//chequeo la respuesta del usuario
		for(UserFeedbackResponse response : feedbackResponses.values()) {
			if(AbstractUserFeedbackRequest.canelled(response))
				throw new UserCancelledException();
		}
		
		//abro el excel
		InputStream inputStream = new ByteArrayInputStream(archivo.getBytes());
		Workbook workbook;
		try {
			workbook = ExcelUtil.getWorkbook(inputStream); 
		}
		catch (PasswordException e) {
			throw new IllegalFormatException(ResourceManager.getErrorResource("app.file.requierePassword"));
		}
		catch (BiffException e) {
			throw new IllegalFormatException(ResourceManager.getErrorResource("app.file.invalidFormat"));
		}
		catch (IOException e) {
			throw new ProyectosExcelParsingException("app.file.fileNotFound");
		}
		catch (Exception e) {
			throw new ProyectosExcelParsingException("app.unknownError");
		}
		Collection<ProyectoInfo> proyectoInfos = new FormularioAParser().parseProyectosDatos(workbook);
		if(proyectoInfos==null) throw new IllegalFormatException(ResourceManager.getErrorResource("app.file.invalidFormat"));
		
		//Chequeo que no haya proyectos con codigos existentes
		checkCodigosUnicos(proyectoInfos);
		
		//Resuelve las personas y dispara una excepcion en caso de que no puedan
		//resolverse todas.
		try {
			resolverEntidadesYPersonas(proyectoInfos, feedbackResponses);
		} catch (PragmaControlledException e) {
			throw new ProyectosExcelParsingException(e.getBundleKey(), e.getParam());
		}
		
		
		Collection<ProyectoBean> proyectos = new ArrayList<ProyectoBean>();
		
		for (ProyectoInfo info : proyectoInfos) {
	
			ProyectoBean proyecto = new ProyectoBean();
			ProyectoDatosBean datos = new ProyectoDatosBean();
		
			//Instrumento
			proyecto.setInstrumento(instrumentoDao.read(info.getInstrumentoId()));
			proyecto.setIdInstrumento(info.getInstrumentoId());
			
			//Jurisdiccion
			ProyectoJurisdiccionBean proyectoJurisdiccion = new ProyectoJurisdiccionBean();
			if(info.getIdJurisdiccion() != null) {
				proyectoJurisdiccion.setIdJurisdiccion(info.getIdJurisdiccion());
				proyectoJurisdiccion.setCodigo(info.getCodigo());
			}
			proyecto.setProyectoJurisdiccion(proyectoJurisdiccion);
			
			//convocatoria
			Long idPresentacion = info.getPresentacionConvocatoriaId();
			if(info.getPresentacionConvocatoriaId()!=null) {
				proyecto.setIdPresentacion(idPresentacion);
				proyecto.setPresentacionConvocatoria(presentacionConvocatoriaDao.read(idPresentacion));
			}
			
			//idea proyecto
			Long idIdeaProyecto = info.getIdeaProyectoId();
			if(idIdeaProyecto!=null) proyecto.setIdProyectoOrigen(idIdeaProyecto);
			
			//Director
			datos.setPersonaDirector(personaMatcher.match(info.getDirector()));
			//Responsable legal
			datos.setPersonaLegal(personaMatcher.match(info.getResponsableLegal()));
			datos.setLocalizacion(toBean(info.getLocalizacion()));

			//Entidad beneficiaria
			datos.setEntidadBeneficiaria(entidadMatcher.match(info.getEmpresa()));

			datos.setCiiu(ciiuDao.read(info.getCiiuId()));
			datos.setIdCiiu(datos.getCiiu().getId());
			proyecto.setCodigo(info.getCodigo());
			datos.setDuracion(info.getDuracion());
			datos.setLocalizacion( toBean(info.getLocalizacion()) );
			datos.setIdLocalizacion(datos.getLocalizacion().getId());
			datos.setObjetivo(info.getObjetivo());
			//Representante
			datos.setPersonaRepresentante(personaMatcher.match(info.getRepresentante()));

			datos.setTipoProyecto(tipoProyectoDao.read(info.getTipoId()));
			datos.setIdTipoProyecto(datos.getTipoProyecto().getId());
			datos.setTitulo(info.getTitulo());
			datos.setResumen(info.getResumen());
			datos.setPalabraClave(info.getPalabrasClave());
			proyecto.setProyectoDatos(datos);

			proyecto.setEmpleoPermanente(EmpleoPermanenteAssembler.getInstance().buildBean(info.getEmpleoPermanente()));
			
			proyectos.add(proyecto);
		}
		
		Collection<ProyectoDTO> ret = new ArrayList<ProyectoDTO>();
		for (ProyectoBean proyecto : proyectos) {
			proyectoServicio.createProyecto(proyecto);
			ret.add(ProyectoAssembler.buildDto(proyecto));
		}
		return ret;
	}
	
	private void resolverEntidadesYPersonas(Collection<ProyectoInfo> proyectos, Map<String, UserFeedbackResponse> feedbackResponses) throws PragmaControlledException, ProyectosExcelFeedbackRequiredException {
		personaMatcher = new PersonaMatcher(configuracionServicio, feedbackResponses);
		entidadMatcher = new EntidadMatcher(entidadBeneficiariaServicio, jurisdiccionDao, feedbackResponses);
		ProyectosExcelFeedbackRequiredException feedbackNeeded = new ProyectosExcelFeedbackRequiredException();
		
		for(ProyectoInfo proyecto : proyectos) {
			personaMatcher.add(proyecto.getDirector());
			personaMatcher.add(proyecto.getResponsableLegal());
			personaMatcher.add(proyecto.getRepresentante());
			entidadMatcher.add(proyecto.getEmpresa());
		}
		feedbackNeeded.addFeedbackRequests(personaMatcher.feedbackNeeded());
		feedbackNeeded.addFeedbackRequests(entidadMatcher.feedbackNeeded());
		if(feedbackNeeded.getFeedbackRequests().size()>0)
			throw feedbackNeeded;
		
	}

	/**
	 * Verifica que los codigos de los proyectos que se estan ingresando no coincidan
	 * entre ellos ni con otros proyectos no anulados cargados previamente en el
	 * sistema. 
	 * @param proyectos
	 * @throws ProyectosExcelParsingException 
	 */
	private void checkCodigosUnicos(Collection<ProyectoInfo> proyectos) throws ProyectosExcelParsingException {
		Set<String> codigosEncontrados = new HashSet<String>();
		for(ProyectoInfo proyecto : proyectos) {
			if(codigosEncontrados.contains(proyecto.getCodigo())) {
				//El codigo coincide con el de un proyecto cargado antes
				throw new ProyectosExcelParsingException("app.proyecto.existeProyectoConCodigo", proyecto.getCodigo());
			} else {
				//El codigo puede coincidir con el de un proyecto existente
				ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
				List<ProyectoBean> proyectosConMismoCodigo = proyectoDAO.findByCodigo(proyecto.getCodigo());
				if(!proyectosConMismoCodigo.isEmpty()) 
					throw new ProyectosExcelParsingException("app.proyecto.existeProyectoConCodigo", proyecto.getCodigo());
			}
			codigosEncontrados.add(proyecto.getCodigo());
		}
	}
	
	/**
	 * Convierte a Localizacion un DomicilioInfo
	 * @param localizacion
	 * @return
	 */
	private LocalizacionBean toBean(LocalizacionInfo localizacion) {
		LocalizacionBean bean = new LocalizacionBean();
		bean.setCodigoPostal(localizacion.getCp());
		bean.setDireccion(localizacion.getDireccion());
		bean.setEmail(localizacion.getEmail());
		bean.setFax(localizacion.getFax());
		Long idJurisdiccion = localizacion.getIdJurisdiccion();
		bean.setIdJurisdiccion(idJurisdiccion);
		if(idJurisdiccion != null)bean.setJurisdiccion(jurisdiccionDao.read(idJurisdiccion));
		bean.setLocalidad(localizacion.getLocalidad());
		bean.setPaginaWeb(localizacion.getWeb());
		bean.setPais(localizacion.getPais());
		bean.setTelefono(localizacion.getTelefono());
		return bean;
	}
	public CiiuDAO getCiiuDao() {
		return ciiuDao;
	}

	public void setCiiuDao(CiiuDAO ciiuDao) {
		this.ciiuDao = ciiuDao;
	}

	public ConfiguracionServicio getConfiguracionServicio() {
		return configuracionServicio;
	}

	public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
		this.configuracionServicio = configuracionServicio;
	}

	public EntidadBeneficiariaServicio getEntidadBeneficiariaServicio() {
		return entidadBeneficiariaServicio;
	}

	public void setEntidadBeneficiariaServicio(EntidadBeneficiariaServicio entidadBeneficiariaServicio) {
		this.entidadBeneficiariaServicio = entidadBeneficiariaServicio;
	}

	public EntidadDAO getEntidadDao() {
		return entidadDao;
	}

	public void setEntidadDao(EntidadDAO entidadDao) {
		this.entidadDao = entidadDao;
	}

	public JurisdiccionDAO getJurisdiccionDao() {
		return jurisdiccionDao;
	}

	public void setJurisdiccionDao(JurisdiccionDAO jurisdiccionDao) {
		this.jurisdiccionDao = jurisdiccionDao;
	}

	public LocalizacionDAO getLocalizacionDao() {
		return localizacionDao;
	}

	public void setLocalizacionDao(LocalizacionDAO localizacionDao) {
		this.localizacionDao = localizacionDao;
	}

	public TipoProyectoDAO getTipoProyectoDao() {
		return tipoProyectoDao;
	}

	public void setTipoProyectoDao(TipoProyectoDAO tipoProyectoDao) {
		this.tipoProyectoDao = tipoProyectoDao;
	}

	public ProyectoServicio getProyectoServicio() {
		return proyectoServicio;
	}

	public void setProyectoServicio(ProyectoServicio proyectoServicio) {
		this.proyectoServicio = proyectoServicio;
	}

	public PresentacionConvocatoriaDAO getPresentacionConvocatoriaDao() {
		return presentacionConvocatoriaDao;
	}

	public void setPresentacionConvocatoriaDao(PresentacionConvocatoriaDAO presentacionConvocatoriaDao) {
		this.presentacionConvocatoriaDao = presentacionConvocatoriaDao;
	}

	public InstrumentoDAO getInstrumentoDao() {
		return instrumentoDao;
	}

	public void setInstrumentoDao(InstrumentoDAO instrumentoDao) {
		this.instrumentoDao = instrumentoDao;
	}
}
