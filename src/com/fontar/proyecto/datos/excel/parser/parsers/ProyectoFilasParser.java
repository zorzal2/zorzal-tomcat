package com.fontar.proyecto.datos.excel.parser.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;

import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.codes.entidad.TipoEmpresa;
import com.fontar.data.impl.domain.codes.entidad.TipoNoEmpresa;
import com.fontar.proyecto.datos.excel.parser.ProyectosExcelValidationException;
import com.fontar.proyecto.datos.message.ProyectosExcelMessages;
import com.fontar.proyecto.datos.modelo.EmpresaInfo;
import com.fontar.proyecto.datos.modelo.PersonaInfo;
import com.fontar.proyecto.datos.modelo.ProyectoInfo;
import com.fontar.util.Cuit;
import com.fontar.util.ResourceManager;
import com.fontar.util.Sexo;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.excel.parser.util.pattern.patterns.ItemsMatching;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;
import com.pragma.util.exception.IllegalArgumentException;


public class ProyectoFilasParser {

	public Collection<ProyectoInfo> parseProyectos(WorkbookCursor cursor) throws ParsingException {

		ItemsMatching itemsPattern;
		
		//Estructura de la lista de proyectos
		PatternParser pattern = PatternLibrary.composition.of(
			//Header
			PatternLibrary.header.havingFields(campos()).named("header proyectos"),
			//Items
			itemsPattern =
				PatternLibrary.itemsMatchingNonStrict("header proyectos")
		);
		
		//Busco el pattern
		List<Cell[]> rows = new ParsingProcess(pattern).find(cursor);
		if(rows==null) return null;
		
		Collection<ProyectoInfo> ret = new ArrayList<ProyectoInfo>();
		List<String> errorMessages = new ArrayList<String>();
		
		for(Map<Label, Cell> row : itemsPattern.getMatchingResults()) {
			ProyectoInfo proyecto = new ProyectoInfo();

			for(Entry<Label, Cell> entry : row.entrySet()) {
				try {
					proyecto.setProperty(entry.getKey().toString(), ParseUtils.getCellValue(entry.getValue()));
				}
				catch (ConversionException e) {
					errorMessages.add(
							invalido(proyecto, entry.getKey().toString())
					);
				}
				catch (IllegalArgumentException e) {
					return null;
				}
			}
			ret.add(proyecto);
		}
		validate(ret, errorMessages);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private void validate(Collection<ProyectoInfo> proyectos, List<String> errorMessages) throws ProyectosExcelValidationException {
		//chequeo obligatoriedad
		for(ProyectoInfo proyecto : proyectos) {
			for(String campo : camposObligatorios()) {
				if(proyecto.getProperty(campo)==null || proyecto.getProperty(campo).toString().equals("")) {
					if(proyecto.getCodigo()==null)
						errorMessages.add(error("error.faltaCampo", campo));
					else
						errorMessages.add(ResourceManager.getMessageResource("error.requerido", proyecto.getCodigo(), ProyectosExcelMessages.instance().getString(campo)));
				}
			}
		}
		if(!errorMessages.isEmpty()) { 
			ProyectosExcelValidationException exception = new ProyectosExcelValidationException();
			exception.appendMessages(errorMessages);
			throw exception;
		}
		//chequeo primer nivel de validez
		for(ProyectoInfo proyecto : proyectos) {
			//Código de proyecto no repetido
			String invalidSize;
			invalidSize = invalidSize(proyecto, "codigo", proyecto.getCodigo(), 20);
			if(invalidSize!=null) errorMessages.add(invalidSize); 
			else if(parseProyecto(proyecto.getCodigo())!=null) errorMessages.add(ResourceManager.getMessageResource("error.codigoRepetido", proyecto.getCodigo()));
			//Titulo
			invalidSize = invalidSize(proyecto, "titulo", proyecto.getTitulo(), 3500);
			if(invalidSize!=null) errorMessages.add(invalidSize);
			//Resumen
			invalidSize = invalidSize(proyecto, "resumen", proyecto.getResumen(), 3500);
			if(invalidSize!=null) errorMessages.add(invalidSize);
			//Palabras clave
			invalidSize = invalidSize(proyecto, "palabrasClave", proyecto.getPalabrasClave(), 3500);
			if(invalidSize!=null) errorMessages.add(invalidSize);
			//Objetivo
			invalidSize = invalidSize(proyecto, "objetivo", proyecto.getObjetivo(), 3500);
			if(invalidSize!=null) errorMessages.add(invalidSize);
			//instrumento
			InstrumentoBean instrumento = parseInstrumento(proyecto.getInstrumento());
			if(instrumento==null) errorMessages.add(invalido(proyecto, "instrumento"));
			else {
				proyecto.setInstrumentoId(instrumento.getId());
				if(instrumento.esVentanilla()) {
					//Puede estar la idea proyecto asociada.
					if(proyecto.getIdeaProyecto()!=null) {
						/*
						 * Chequeo:
						 * - Idea proyecto existe
						 * - Idea proyecto elegible
						 * Hago
						 * - Vinculo el proyecto con su ideaProyecto
						 */
						if(proyecto.getIdeaProyecto()!=null && !proyecto.getIdeaProyecto().equals("")) {
							IdeaProyectoBean ideaProyecto = parseIdeaProyecto(proyecto.getIdeaProyecto());
							if(ideaProyecto==null) errorMessages.add(invalido(proyecto, "ideaProyecto"));
							else {
								if(!ideaProyecto.estaElegible()) errorMessages.add(invalido(proyecto, "ideaProyecto"));
								else {
									proyecto.setIdeaProyectoId(ideaProyecto.getId());
								}
							}
						}
					}
				} else {
					//Es convocatoria
					/*
					 * Si existe una presentacion con el mismo codigo:
					 * - Chequeo que este iniciada
					 * - La vinculo al proyecto
					 */
					PresentacionConvocatoriaBean presentacionConvocatoria =  parsePresentacion(proyecto.getCodigo());
					if(presentacionConvocatoria!=null) {
						if(!presentacionConvocatoria.estaPresentada()) {
							ProyectosExcelMessages messages = ProyectosExcelMessages.instance();
							errorMessages.add(messages.getString("error.presentacionInvalida", proyecto.getCodigo()));
						} else {
							proyecto.setPresentacionConvocatoriaId(presentacionConvocatoria.getId());
						}
					}
				}
			}
			//jurisdiccion
			if(StringUtil.isNotEmpty(proyecto.getJurisdiccion())) {
				Long idJurisdiccion = parseJurisdiccion(proyecto.getJurisdiccion());
				if(idJurisdiccion==null) errorMessages.add(invalido(proyecto, "jurisdiccion"));
				else  proyecto.setIdJurisdiccion(idJurisdiccion);
			}
			//duracion
			if(proyecto.getDuracion()!=null && proyecto.getDuracion().longValue()<1)
				errorMessages.add(invalido(proyecto, "duracion"));
			//Tipo de Empresa
			if(
					ParseUtils.fuzzyEquals(
							proyecto.getEmpresa().getTipo(), 
							ResourceManager.getLabelResource("app.label.noEmpresa")
					)
			) {
				proyecto.getEmpresa().setEsEmpresa(Boolean.FALSE);
				//Subtipo
				String subTipoEmpresaProyecto = proyecto.getEmpresa().getSubtipo();
				if(StringUtil.isNotEmpty(subTipoEmpresaProyecto)) {
					for(TipoNoEmpresa tipoNoEmpresa : TipoNoEmpresa.values()) {
						if(ParseUtils.fuzzyEquals(
								subTipoEmpresaProyecto, 
								tipoNoEmpresa.getDescripcion()
							) ||
							ParseUtils.fuzzyEquals(
								subTipoEmpresaProyecto, 
								tipoNoEmpresa.getDescripcionCorta()
							)
						) {
							proyecto.getEmpresa().setSubTipoNoEmpresaEnum(tipoNoEmpresa);
							break;
						}
					}
					if(proyecto.getEmpresa().getSubTipoNoEmpresaEnum()==null)
						errorMessages.add(invalido(proyecto, "empresa.subtipo"));
				}
			}
			if(
					ParseUtils.fuzzyEquals(
							proyecto.getEmpresa().getTipo(), 
							ResourceManager.getLabelResource("app.label.empresa")
					)
			) {
				proyecto.getEmpresa().setEsEmpresa(Boolean.TRUE);
				//Subtipo
				String subTipoEmpresaProyecto = proyecto.getEmpresa().getSubtipo();
				if(StringUtil.isNotEmpty(subTipoEmpresaProyecto)) {
					for(TipoEmpresa tipoEmpresa : TipoEmpresa.values()) {
						if(ParseUtils.fuzzyEquals(
								subTipoEmpresaProyecto, 
								tipoEmpresa.getDescripcion()
							)
						) {
							proyecto.getEmpresa().setSubTipoEmpresaEnum(tipoEmpresa);
							break;
						}
					}
					if(proyecto.getEmpresa().getSubTipoEmpresaEnum()==null)
						errorMessages.add(invalido(proyecto, "empresa.subtipo"));
				}
			}
			//Fin tipo y subtipo
			//empresa.localizacion.jurisdiccion
			if(StringUtil.isNotEmpty(proyecto.getEmpresa().getLocalizacion().getJurisdiccion())) {
				Long idJurisdiccion = parseJurisdiccion(proyecto.getEmpresa().getLocalizacion().getJurisdiccion());
				if(idJurisdiccion==null) errorMessages.add(invalido(proyecto, "empresa.localizacion.jurisdiccion"));
				else  proyecto.getEmpresa().getLocalizacion().setIdJurisdiccion(idJurisdiccion);
			}
			//localizacionActividad.jurisdiccion
			if(StringUtil.isNotEmpty(proyecto.getEmpresa().getLocalizacionActividad().getJurisdiccion())) {
				Long idJurisdiccion = parseJurisdiccion(proyecto.getEmpresa().getLocalizacionActividad().getJurisdiccion());
				if(idJurisdiccion==null) errorMessages.add(invalido(proyecto, "empresa.localizacionActividad.jurisdiccion"));
				else  proyecto.getEmpresa().getLocalizacionActividad().setIdJurisdiccion(idJurisdiccion);
			}
			//localizacion.jurisdiccion
			if(StringUtil.isNotEmpty(proyecto.getLocalizacion().getJurisdiccion())) {
				Long idJurisdiccion = parseJurisdiccion(proyecto.getLocalizacion().getJurisdiccion());
				if(idJurisdiccion==null) errorMessages.add(invalido(proyecto, "localizacion.jurisdiccion"));
				else  proyecto.getLocalizacion().setIdJurisdiccion(idJurisdiccion);
			}
			String masculino = ResourceManager.getLabelResource("app.label.M");
			String femenino = ResourceManager.getLabelResource("app.label.F");
			//representante.sexo
			String representanteSexo = proyecto.getRepresentante().getSexo();
			if(StringUtil.isNotEmpty(representanteSexo)) {
				if(ParseUtils.fuzzyEquals(representanteSexo, femenino)) {
					proyecto.getRepresentante().setSexo(femenino);
				} else {
					if(ParseUtils.fuzzyEquals(masculino, representanteSexo)) {
						proyecto.getRepresentante().setSexo(masculino);
					} else {
						errorMessages.add(invalido(proyecto, "representante.sexo"));
					}
				}
			}
			//director.sexo
			String directorSexo = proyecto.getDirector().getSexo();
			if(StringUtil.isNotEmpty(directorSexo)) {
				if(ParseUtils.fuzzyEquals(directorSexo, femenino)) {
					proyecto.getDirector().setSexo(femenino);
				} else {
					if(ParseUtils.fuzzyEquals(masculino, directorSexo)) {
						proyecto.getDirector().setSexo(masculino);
					} else {
						errorMessages.add(invalido(proyecto, "director.sexo"));
					}
				}
			}
			//responsableLegal.sexo
			String responsableLegalSexo = proyecto.getResponsableLegal().getSexo();
			if(StringUtil.isNotEmpty(responsableLegalSexo)) {
				if(ParseUtils.fuzzyEquals(femenino, responsableLegalSexo)) {
					proyecto.getResponsableLegal().setSexo(femenino);
				} else {
					if(ParseUtils.fuzzyEquals(masculino, responsableLegalSexo)) {
						proyecto.getResponsableLegal().setSexo(masculino);
					} else {
						errorMessages.add(invalido(proyecto, "responsableLegal.sexo"));
					}
				}
			}
			
			//tipo
			if(StringUtil.isNotEmpty(proyecto.getTipo())) {
				Long tipoId = parseTipo(proyecto.getTipo());
				if(tipoId==null) errorMessages.add(invalido(proyecto, "tipo"));
				else proyecto.setTipoId(tipoId);
			}
			//Ciiu
			//No es null seguro
			invalidSize = invalidSize(proyecto, "ciiu", proyecto.getCiiu(), 20);
			if(invalidSize!=null) errorMessages.add(invalidSize);
			else {
				String proyectoCiiu = proyecto.getCiiu();
				CiiuDAO ciiuDao = (CiiuDAO)ContextUtil.getBean("ciiuDao");
				Collection<CiiuBean> ciius = ciiuDao.getCiiuElegibles(); 
				for(CiiuBean ciiu : ciius) {
					if(ParseUtils.fuzzyEquals(proyectoCiiu, ciiu.getCodigo())) {
						proyecto.setCiiuId(ciiu.getId());
						break;
					}
				}
				if(proyecto.getCiiuId()==null) errorMessages.add(invalido(proyecto, "ciiu"));
			}
			validarEmpresa(proyecto, "empresa", errorMessages);
			//cuit empresa
			if(!validarCuitEntidad(proyecto.getEmpresa())) errorMessages.add(invalido(proyecto, "empresa.cuit"));

			//cuits personas
			if(!validarCuitPersona(proyecto.getRepresentante())) errorMessages.add(invalido(proyecto, "representante.cuit"));
			validarPersona(proyecto, "representante", errorMessages);

			if(!validarCuitPersona(proyecto.getDirector())) errorMessages.add(invalido(proyecto, "director.cuit"));
			validarPersona(proyecto, "director", errorMessages);

			if(!validarCuitPersona(proyecto.getResponsableLegal())) errorMessages.add(invalido(proyecto, "responsableLegal.cuit"));
			validarPersona(proyecto, "responsableLegal", errorMessages);
		}
		if(!errorMessages.isEmpty()) {
			ProyectosExcelValidationException exception = new ProyectosExcelValidationException();
			exception.appendMessages(errorMessages);
			throw exception;
		}
	}

	private boolean validarPersona(ProyectoInfo proyecto, String propiedad, List<String> errorMessages) {
		boolean ret = true;
		//Campos
		String[] campos = {
				"nombre"
		};
		for(String campo : campos) {
			String fullPropertyName = propiedad+'.'+campo;
			String invalidSize = invalidSize(proyecto, fullPropertyName, (String)proyecto.getProperty(fullPropertyName), 60);
			if(invalidSize!=null) {
				errorMessages.add(invalidSize);
				ret = false;
			}
		}
		return ret && validarLocalizacion(proyecto, propiedad+".localizacion", errorMessages);
	}
	private boolean validarLocalizacion(ProyectoInfo proyecto, String propiedad, List<String> errorMessages) {
		boolean ret = true;
		//Campos
		String[] campos = {
				"direccion",
				"localidad",
				"cp",
				"pais",
				"telefono",
				"fax",
				"email",
				"web"
		};
		for(String campo : campos) {
			String fullPropertyName = propiedad+'.'+campo;
			String invalidSize = invalidSize(proyecto, fullPropertyName, (String)proyecto.getProperty(fullPropertyName), 500);
			if(invalidSize!=null) {
				errorMessages.add(invalidSize);
				ret = false;
			}
		}
		return ret;
	}
	private boolean validarEmpresa(ProyectoInfo proyecto, String propiedad, List<String> errorMessages) {
		String invalidSize = invalidSize(proyecto, propiedad, proyecto.getEmpresa().getNombre(), 100);
		if(invalidSize!=null) {
			errorMessages.add(invalidSize);
			return false;
		}
		return  validarLocalizacion(proyecto, propiedad+".localizacionActividad", errorMessages) &&
				validarLocalizacion(proyecto, propiedad+".localizacion", errorMessages);
	}
	private boolean validarCuitPersona(PersonaInfo persona) {
		
		String cuit = persona.getCuit();
		if(cuit==null || cuit.equals("")) {
			//Corrijo las ""
			persona.setCuit(null);
			return true;
		}
		return Cuit.esCuitDePersonaValido(cuit, Sexo.fromString(persona.getSexo()));
	}
	private boolean validarCuitEntidad(EmpresaInfo empresa) {
		
		String cuit = empresa.getCuit();
		if(cuit==null || cuit.equals("")) {
			//Corrijo "" por null
			empresa.setCuit(null);
			return true;
		}
		Cuit objCuit = Cuit.from(cuit);
		if(objCuit==null) return false;
		else {
			if(
					empresa.getEsEmpresa()!=null && 
					empresa.getEsEmpresa() && 
					empresa.getSubTipoEmpresaEnum()!=null
			) 
			{
				//Para empresas unipersonales se valida que el cuit sea de persona
				if(empresa.getSubTipoEmpresaEnum().equals(TipoEmpresa.UNIPERSONAL)) {
					if(!objCuit.esDePersona()) return false;
				} else {
					//Para tipo de empresa OTRO no puedo saber si es una entidad o una persona,
					//asi que cualquier cuit valido me sirve
					if(empresa.getSubTipoEmpresaEnum().equals(TipoEmpresa.OTRO)) {
						//No hago nada. devuelvo true.
					} else {
						//El resto de los tipos de empresa tienen necesariamente que ser entidades
						if(!objCuit.esDeEntidad()) return false;
					}
				}
			} else {
				//Si no puedo determinar que sea una empresa unipersonal todavia puede ser una
				//persona fisica.
				if(		empresa.getEsEmpresa() != null
					&&	!empresa.getEsEmpresa()
					&&	TipoNoEmpresa.PERSONA.equals(empresa.getSubTipoNoEmpresaEnum())
				) {
					//Es una persona fisica.
					if(!objCuit.esDePersona()) return false;
				} else {
					//Si no, asumo que es una entidad
					if(!objCuit.esDeEntidad()) return false;
				}
			}
			//es un cuit valido para algun tipo cualquiera de persona o entidad.
			//Lo dejo normalizado de onda ;)
			empresa.setCuit(objCuit.normalizado());
			return true;
		}
	}
	private String error(String errorKey, String... arguments) {
		ProyectosExcelMessages messages = ProyectosExcelMessages.instance();
		String[] parameters = new String[arguments.length];
		for(int i=0; i<arguments.length;i++) {
			parameters[i] = messages.getString(arguments[i]);
		}
		return messages.getString(errorKey, parameters);
	}
	private String invalido(ProyectoInfo proyecto, String propiedad) {
		ProyectosExcelMessages messages = ProyectosExcelMessages.instance();
		return messages.getString("error.valorInvalido", messages.getString(propiedad), proyecto.getCodigo());
	}
	private String invalidSize(ProyectoInfo proyecto, String propiedad, String valor, int len) {
		if(String.valueOf(valor).length()>len) {
			ProyectosExcelMessages messages = ProyectosExcelMessages.instance();
			return messages.getString("error.campoMuyGrande", messages.getString(propiedad), proyecto.getCodigo(), ""+len);
		}
		return null;
	}

	private Long parseJurisdiccion(String name) {
		JurisdiccionDAO jurisdiccionDao = (JurisdiccionDAO)ContextUtil.getBean("jurisdiccionDao");
		for(JurisdiccionBean jurisdiccion : jurisdiccionDao.getAll()) {
			if(ParseUtils.fuzzyEquals(
					jurisdiccion.getDescripcion(), 
					name
			)
			) return jurisdiccion.getId();
			if(ParseUtils.fuzzyEquals(
					jurisdiccion.getCodigo(), 
					name
				)
			) return jurisdiccion.getId();
		}
		return null;
	}
	private InstrumentoBean parseInstrumento(String name) {
		InstrumentoDAO instrumentoDao = (InstrumentoDAO)ContextUtil.getBean("instrumentoDao");
		for(InstrumentoBean instrumento : instrumentoDao.findByIdentificador(name)) {
			return instrumento;
		}
		return null;
	}
	private ProyectoBean parseProyecto(String codigo) {
		ProyectoDAO proyectoDao = (ProyectoDAO)ContextUtil.getBean("proyectoDao");
		for(ProyectoBean proyecto : proyectoDao.findByCodigo(codigo)) {
			return proyecto;
		}
		return null;
	}
	private IdeaProyectoBean parseIdeaProyecto(String name) {
		IdeaProyectoDAO ideaProyectoDao = (IdeaProyectoDAO)ContextUtil.getBean("ideaProyectoDao");
		for(IdeaProyectoBean ideaProyecto : ideaProyectoDao.findByCodigo(name)) {
			return ideaProyecto;
		}
		return null;
	}
	private PresentacionConvocatoriaBean parsePresentacion(String name) {
		PresentacionConvocatoriaDAO presentacionDao = (PresentacionConvocatoriaDAO)ContextUtil.getBean("presentacionConvocatoriaDao");
		for(PresentacionConvocatoriaBean presentacion : presentacionDao.findByCodigo(name)) {
			return presentacion;
		}
		return null;
	}
	private Long parseTipo(String name) {
		TipoProyectoDAO tpDao = (TipoProyectoDAO)ContextUtil.getBean("tipoProyectoDao");
		for(TipoProyectoBean tpBean : tpDao.getAll()) {
			if(ParseUtils.fuzzyEquals(
					tpBean.getNombre(), 
					name
				) ||
				ParseUtils.fuzzyEquals(
						tpBean.getNombreCorto(), 
						name
				)
			) return tpBean.getId();
		}
		return null;
	}

	private static Label[] Campos = new Label[] {
			new Label("codigo"),
			new Label("ciiu"),
			new Label("ideaProyecto"),
			new Label("jurisdiccion"),
			new Label("instrumento"),
			new Label("objetivo"),
			new Label("titulo"),
			new Label("duracion"),
			new Label("tipo"),
			new Label("resumen"),
			new Label("palabrasClave"),
			
			new Label("empleoPermanente.cantidadTecnicos"),
			new Label("empleoPermanente.cantidadOperariosCalificados"),
			new Label("empleoPermanente.cantidadOperariosNoCalificados"),
			new Label("empleoPermanente.cantidadProfesionales"),
			
			new Label("empresa.nombre"),
			new Label("empresa.cuit"),
			new Label("empresa.tipo"),
			new Label("empresa.subtipo"),
			new Label("empresa.localizacion.direccion"),
			new Label("empresa.localizacion.localidad"),
			new Label("empresa.localizacion.jurisdiccion"),
			new Label("empresa.localizacion.pais"),
			new Label("empresa.localizacion.telefono"),
			new Label("empresa.localizacion.email"),
			new Label("empresa.localizacion.web"),
			new Label("empresa.localizacion.cp"),
			new Label("empresa.localizacion.fax"),
			new Label("empresa.localizacionActividad.direccion"),
			new Label("empresa.localizacionActividad.localidad"),
			new Label("empresa.localizacionActividad.jurisdiccion"),
			new Label("empresa.localizacionActividad.pais"),
			new Label("empresa.localizacionActividad.telefono"),
			new Label("empresa.localizacionActividad.email"),
			new Label("empresa.localizacionActividad.cp"),
			new Label("empresa.localizacionActividad.fax"),

			new Label("localizacion.direccion"),
			new Label("localizacion.localidad"),
			new Label("localizacion.jurisdiccion"),
			new Label("localizacion.pais"),
			new Label("localizacion.telefono"),
			new Label("localizacion.email"),
			new Label("localizacion.cp"),
			new Label("localizacion.fax"),
			new Label("representante.nombre"),
			new Label("representante.sexo"),
			new Label("representante.cuit"),
			new Label("director.nombre"),
			new Label("director.sexo"),
			new Label("director.fechaNacimiento"),
			new Label("director.cuit"),
			new Label("director.localizacion.email"),
			new Label("responsableLegal.nombre"),
			new Label("responsableLegal.sexo"),
			new Label("responsableLegal.fechaNacimiento"),
			new Label("responsableLegal.cuit"),
			new Label("responsableLegal.localizacion.email")
	};
	private static Label[] campos() {		
		return Campos;
	}
	private String[] camposObligatorios() {
		return new String[] {
			"codigo",
			"instrumento",
			"jurisdiccion",
			"titulo",
			"tipo",

			"empresa.nombre",
			//"empresa.cuit",
			"empresa.localizacion.direccion",
			"empresa.localizacion.localidad",
			"empresa.localizacion.jurisdiccion",
			"empresa.localizacion.pais",

			"localizacion.direccion",
			"localizacion.localidad",
			"localizacion.jurisdiccion",
			"localizacion.pais"
		};
	}
}
