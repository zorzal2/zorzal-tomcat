package com.fontar.seguimientos.rendiciones.excel.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.DateCell;
import jxl.Workbook;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.PersonaDatosInsuficientesException;
import com.fontar.data.Constant.PersonaSexo;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.seguimientos.rendiciones.message.RendicionesMessages;
import com.fontar.util.Cuit;
import com.fontar.util.ResourceManager;
import com.fontar.util.Sexo;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.parser.util.Field;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.cusor.exception.InvalidSheetNameException;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

public abstract class BaseRendicionesParser {
	//Longitudes de campos
	private static final int NUMERO_FACTURA_MAXLEN = 14;
	private static final int NUMERO_RECIBO_MAXLEN = 1000;
	private static final int NOMBRE_PERSONA_MAXLEN = 60;
	private static final int DESCRIPCION_MAXLEN = 500;
	private static final int FUNCION_PERSONA_MAXLEN = 500;
	private static final int DESCRIPCION_PROVEEDOR_MAXLEN = 500;
	private static final int PAIS_PROVEEDOR_MAXLEN = 500;
	private static final int PROFESION_PERSONA_MAXLEN = 500;
	
	public List<RendicionCuentasBean> parse(Workbook workbook, SeguimientoBean seguimiento) throws ParsingException {
		initPersonas();
		WorkbookCursor cursor = new WorkbookCursor(workbook);
		try {
			cursor.goToSheet(RendicionesLabels.SHEET_NAME);
		} catch(InvalidSheetNameException exception) {
			//El archivo no tiene solapa de rendiciones
			throw new RendicionesValidationException(RendicionesMessages.noSeEncuentranRendiciones());
		}
		String sheetName = workbook.getSheets()[cursor.getPosition().getSheetIndex()].getName();
		//Busco los campos
		PatternParser pattern = PatternLibrary.composition.of(
				//Encabezado
				PatternLibrary.header.havingFields(getFields()).named("Encabezado"),
				//Items
				PatternLibrary.itemsMatchingStrict("Encabezado").named("Items")
		);
		ParsingProcess process = new ParsingProcess(pattern);
		process.find(cursor);
		if(process.getResult()==null) {
			//Formato no valido
			throw new RendicionesValidationException(RendicionesMessages.rendicionesInvalidas(sheetName));
		}
		
		FieldMatching matching = (FieldMatching) process.getVar("Encabezado");
		List<Cell[]> items = process.getNamedRowset("Items");
		List<RendicionCuentasBean> ret = new ArrayList<RendicionCuentasBean>();
		List<String> errores = new ArrayList<String>();
		for(Cell[] row : items) {
			try {
				RendicionCuentasBean item = tryCreateBean(row, matching);
				if(item!=null) {
					item.setSeguimiento(seguimiento);
					item.setIdSeguimiento(seguimiento.getId());
					ret.add(item);
				}
			} catch(RendicionesValidationException e) {
				errores.add(e.getMessage());
			}
		}
		if(!errores.isEmpty()) {
			RendicionesValidationException exception = new RendicionesValidationException();
			exception.appendMessages(errores);
			throw exception;
		}
		return  ret;
	}

	protected class Item {
		public Map<Label, Cell> itemAsMap;
		public RendicionCuentasBean bean;
		public Item(Cell[] row, FieldMatching matching, RendicionCuentasBean bean) {
			itemAsMap = matching.applyNonStrict(row);
			this.bean = bean;
		}
		protected BigDecimal getPositiveNumber(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoPositivoNoValido(label.toString()));
			if(!ParseUtils.isNumeric(cell))
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoPositivoNoValido(label.toString()));
			Double cellValue = (Double)ParseUtils.getCellValue(cell);
			if(cellValue<=0)
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoPositivoNoValido(label.toString()));
			return new BigDecimal(cellValue);
		}
		
		protected BigDecimal getNumber(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoNoValido(label.toString()));
			if(!ParseUtils.isNumeric(cell))
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoNoValido(label.toString()));
			Double cellValue = (Double)ParseUtils.getCellValue(cell);
			return new BigDecimal(cellValue);
		}
		
		protected BigDecimal getNonNegativeNumber(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoNoNegativoNoValido(label.toString()));
			if(!ParseUtils.isNumeric(cell))
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoNoNegativoNoValido(label.toString()));
			Double cellValue = (Double)ParseUtils.getCellValue(cell);
			if(cellValue<0)
				throw new RendicionesValidationException(RendicionesMessages.campoNumericoNoNegativoNoValido(label.toString()));
			return new BigDecimal(cellValue);
		}
		
		protected Long getNonNegativeInteger(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoEnteroNoNegativoNoValido(label.toString()));
			try {
				BigDecimal nonNegativeNumber = getNonNegativeNumber(label);
				return nonNegativeNumber.longValueExact();
			} catch(Exception e) {
				throw new RendicionesValidationException(RendicionesMessages.campoEnteroNoNegativoNoValido(label.toString()));
			}
		}
		
		protected Long getPositiveInteger(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoEnteroPositivoNoValido(label.toString()));
			try {
				BigDecimal positiveNumber = getPositiveNumber(label);
				return positiveNumber.longValueExact();
			} catch(Exception e) {
				throw new RendicionesValidationException(RendicionesMessages.campoEnteroPositivoNoValido(label.toString()));
			}
		}
		
		protected BigDecimal getPorcentajeNoNulo(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				throw new RendicionesValidationException(RendicionesMessages.campoPorcentualNoValido(label.toString()));
			try {
				BigDecimal positiveNumber = getPositiveNumber(label);
				if(positiveNumber.compareTo(BigDecimal.ZERO)<1)
					throw new RendicionesValidationException(RendicionesMessages.campoPorcentualNoValido(label.toString()));
				return positiveNumber;
			} catch(Exception e) {
				throw new RendicionesValidationException(RendicionesMessages.campoPorcentualNoValido(label.toString()));
			}
		}
		
		protected String getString(Label label) throws RendicionesValidationException {
			String stringOrNull = getStringOrNull(label);
			if(stringOrNull==null)
				throw new RendicionesValidationException(RendicionesMessages.campoNoValido(bean, label.toString()));
			else return stringOrNull;
		}
		protected String getStringOrNullMaxLength(Label label, int maxLength) throws RendicionesValidationException {
			String stringOrNull = getStringOrNull(label);
			if(stringOrNull==null) return null;
			else {
				if(stringOrNull.length()>maxLength)
					throw new RendicionesValidationException(RendicionesMessages.textoDemasiadoLargo(bean, label.toString(), maxLength));
				else return stringOrNull;
			}
		}
		protected String getStringMaxLength(Label label, int maxLength) throws RendicionesValidationException {
			String string = getString(label);
			if(string.length()>maxLength)
				throw new RendicionesValidationException(RendicionesMessages.textoDemasiadoLargo(bean, label.toString(), maxLength));
			return string;
		}
		protected String getStringOrNull(Label label) {
			Cell cell = itemAsMap.get(label);
			if(cell==null) return null;
			if(StringUtil.isEmpty(cell.getContents())) return null;
			return cell.getContents();
		}

		protected Date getDate(Label label) throws RendicionesValidationException {
			Date date = getDateOrNull(label);
			if(date==null)
				throw new RendicionesValidationException(RendicionesMessages.faltaCampo(bean, label.toString()));
			return date;
		}
		protected Date getDateOrNull(Label label) throws RendicionesValidationException {
			Cell cell = itemAsMap.get(label);
			if(cell==null)
				return null;
			if(ParseUtils.isDate(cell))
				return ParseUtils.getDate((DateCell) cell);
			else
				throw new RendicionesValidationException(RendicionesMessages.fechaNoValida(bean, label.toString()));
		}
	} 
	private RendicionCuentasBean tryCreateBean(Cell[] row, FieldMatching matching) throws RendicionesValidationException {
		
		RendicionCuentasBean bean = new RendicionCuentasBean();
		Item item = new Item(row, matching, bean);
		
		//Rubro
		RubroBean rubroByName = null;
		String rubroName = null;
		rubroName = item.getStringOrNull(RendicionesLabels.RUBRO);
		rubroByName = getRubroByName(rubroName);
		if(rubroByName==null)
			throw new RendicionesValidationException(RendicionesMessages.rubroNoValidoOVacio(rubroName));
		bean.setRubro(rubroByName);
		bean.setIdRubro(rubroByName.getId());
		
		//Numero de factura
		String nroFactura = item.getStringMaxLength(RendicionesLabels.NRO_FACTURA, NUMERO_FACTURA_MAXLEN);
		bean.setNumeroFactura(nroFactura);

		//Numero de recibo
		String nroRecibo = item.getStringOrNullMaxLength(RendicionesLabels.NRO_RECIBO, NUMERO_RECIBO_MAXLEN);
		bean.setNumeroRecibo(nroRecibo);

		//Fecha
		bean.setFecha(item.getDate(RendicionesLabels.FECHA));
		
		//Montos
		cargarMontos(item);
		
		//Descripcion: !esPersona, !esCanonInstitucional
		if(!bean.getRubro().getEsPersona() && !bean.getRubro().getEsCanonInstitucional()) {
			bean.setDescripcion(item.getStringMaxLength(RendicionesLabels.DESCRIPCION_GASTO, DESCRIPCION_MAXLEN));
		} else {
			if(bean.getRubro().getEsCanonInstitucional()) {
				//descripcion por defecto
				bean.setDescripcion(ResourceManager.getCodesResource("app.codes.rendicion.tipo.canonInstitucional"));
			} else {
				if(bean.getRubro().getEsDirectorExperto()) {
					//descripcion por defecto
					bean.setDescripcion(ResourceManager.getCodesResource("app.codes.rendicion.tipo.directorExperto"));
				}				
			}
		}
		
		if(bean.getRubro().getEsPersona()) {
			//Persona
			String nombre = item.getStringMaxLength(RendicionesLabels.NOMBRE, NOMBRE_PERSONA_MAXLEN); 
			String sexo = item.getString(RendicionesLabels.SEXO); 
			if(!sexo.equals(PersonaSexo.FEMENINO) && !sexo.equals(PersonaSexo.MASCULINO))
				throw new RendicionesValidationException(RendicionesMessages.campoNoValido(bean, RendicionesLabels.SEXO.toString()));
			String cuit = item.getStringOrNull(RendicionesLabels.CUIT_CUIL); 
			if(cuit!=null) {
				Cuit oCuit = Cuit.from(cuit);
				if(oCuit==null) {
					throw new RendicionesValidationException(RendicionesMessages.campoNoValido(bean, RendicionesLabels.CUIT_CUIL.toString()));
				} else {
					if(!oCuit.esValidoParaSexo(Sexo.fromString(sexo))) 
						throw new RendicionesValidationException(RendicionesMessages.campoNoValido(bean, RendicionesLabels.CUIT_CUIL.toString()));
				}
				cuit = oCuit.normalizado();
			}

			PersonaBean persona = null;
			try {
				persona = toBean(nombre, cuit, sexo);
			}
			catch (PersonaDatosInsuficientesException e) {
				throw new RendicionesValidationException(RendicionesMessages.personaNoIdentificable(bean));
			}
			bean.setPersona(persona);
			
			//Funcion: esPropio, esAdicional, esConsejeroTecnologico
			if(
					bean.getRubro().getEsRecursoHumanoAdicional() ||
					bean.getRubro().getEsRecursoHumanoPropio() ||
					bean.getRubro().getEsConsejeroTecnologico()
			) {
				if(StringUtil.isEmpty(bean.getDescripcion())) 
					bean.setDescripcion(item.getStringMaxLength(RendicionesLabels.FUNCION, FUNCION_PERSONA_MAXLEN));
			}	
			
			//Profesion: esPersona, !esDirectorExperto
			if(!bean.getRubro().getEsDirectorExperto()) {
				bean.setProfesionPersona(item.getStringMaxLength(RendicionesLabels.PROFESION, PROFESION_PERSONA_MAXLEN));
				if(StringUtil.isEmpty(bean.getDescripcion())) 
					bean.setDescripcion(item.getString(RendicionesLabels.PROFESION));
			}				
			
			//dedicacion
			bean.setPorcentajeDedicacion(item.getPorcentajeNoNulo(RendicionesLabels.DEDICACION));
		}
		if(bean.getRubro().getEsPersona() || bean.getRubro().getEsConsultor()) {
			//mesesParticipacion: esConsultor, esPersona
			String participacion = item.getStringOrNull(RendicionesLabels.PARTICIPACION);
			if(participacion!=null)bean.setMesesParticipacion(item.getPositiveInteger(RendicionesLabels.PARTICIPACION));
			
		}

		//sueldo: esPropio
		//costo mensual: esAdicional | esConsultor | esDirectorExperto | esConsejeroTecnologico
		if(
				bean.getRubro().getEsRecursoHumanoPropio() ||
				bean.getRubro().getEsRecursoHumanoAdicional() ||
				bean.getRubro().getEsConsultor() ||
				bean.getRubro().getEsDirectorExperto() ||
				bean.getRubro().getEsConsejeroTecnologico()
		) {
			bean.setMontoSueldoMensual(item.getPositiveNumber(RendicionesLabels.COSTO_MENSUAL));
		}
		
		//proveedor esGeneral | esConsultor
		if(bean.getRubro().getEsGeneral() || bean.getRubro().getEsConsultor()){
			//descripcion del proveedor
			bean.setNombreProveedor(item.getStringMaxLength(RendicionesLabels.DESCRIPCION_PROVEEDOR, DESCRIPCION_PROVEEDOR_MAXLEN));
			//certificado
			String tieneCertificado = item.getStringOrNull(RendicionesLabels.TIENE_CERTIFICADO);
			if(tieneCertificado!=null) {
				if(RendicionesLabels.SI.matches(tieneCertificado)){
					bean.setTieneCertificadoProveedor(true);
				} else {
					if(RendicionesLabels.NO.matches(tieneCertificado)){
						bean.setTieneCertificadoProveedor(false);
					} else {
						throw new RendicionesValidationException(RendicionesMessages.campoNoValido(bean, RendicionesLabels.TIENE_CERTIFICADO.toString()));								
					}
				}
			}
			
			//pais del certificado
			bean.setPaisProveedor(item.getStringOrNullMaxLength(RendicionesLabels.PAIS_DE_CERTIFICADO, PAIS_PROVEEDOR_MAXLEN));

		}
		Date version = item.getDateOrNull(RendicionesLabels.VERSION);
		bean.setVersion(version);
		if(StringUtil.isEmpty(bean.getDescripcion())) bean.setDescripcion("N/A");
		return bean;
	}

	private RubroBean getRubroByName(String name) {
		if(name==null) return null;
		if(allRubros==null) loadRubros();
		for (Entry<Label, RubroBean> rubroLabel : allRubros.entrySet()) {
			if(rubroLabel.getKey().matches(name)) return rubroLabel.getValue(); 
		}
		return null;
	}
	
	private Map<Label, RubroBean> allRubros = null;
	private void loadRubros() {
		//Mapa de abreviaturas de rubros
		allRubros = new HashMap<Label, RubroBean>();
		allRubros.put(RendicionesLabels.bienes_infraestructura, findRubroByCodigo("bienes.infraestructura"));
		allRubros.put(RendicionesLabels.bienes_maquinarias, findRubroByCodigo("bienes.maquinarias"));
		allRubros.put(RendicionesLabels.bienes_otros, findRubroByCodigo("bienes.otros"));
		allRubros.put(RendicionesLabels.consejerosTecnologicos, findRubroByCodigo("consejerosTecnologicos"));
		allRubros.put(RendicionesLabels.cys_canonInstitucional, findRubroByCodigo("cys.canonInstitucional"));
		allRubros.put(RendicionesLabels.cys_general, findRubroByCodigo("cys.general"));
		allRubros.put(RendicionesLabels.directorExperto, findRubroByCodigo("directorExperto"));
		allRubros.put(RendicionesLabels.insumos, findRubroByCodigo("insumos"));
		allRubros.put(RendicionesLabels.otrosCostos, findRubroByCodigo("otrosCostos"));
		allRubros.put(RendicionesLabels.recursosHumanos_adicionales, findRubroByCodigo("recursosHumanos.adicionales"));
		allRubros.put(RendicionesLabels.recursosHumanos_propios, findRubroByCodigo("recursosHumanos.propios"));
	}
	
	private RubroDAO dao = (RubroDAO)ContextUtil.getBean("rubroDao");
	private RubroBean findRubroByCodigo(String codigo) {
		List<RubroBean> list = dao.findByCodigo(codigo);
		if(list.size()!=1) return null;
		return list.get(0);
	}
	
	
	//Guardo las personas que se van agregando
	private ConfiguracionServicio configuracionServicio = (ConfiguracionServicio) ContextUtil.getBean("configuracionService");
	private Map<PersonaDTO, PersonaBean> personas;
	private void initPersonas() {
		personas = new Hashtable<PersonaDTO, PersonaBean>();
	}
	private PersonaBean personaExistente(PersonaDTO personaDTO) {
		PersonaBean bean;
		for(PersonaDTO personaExistenteDTO : personas.keySet()) {
			if(!StringUtil.isEmpty(personaExistenteDTO.getCuit()) &&
			   !StringUtil.isEmpty(personaDTO.getCuit())) {
				if(personaExistenteDTO.getCuit().equals(personaDTO.getCuit())) return personas.get(personaExistenteDTO);
				else continue;
			}
			//comparo por nombre
			if(personaExistenteDTO.getNombre().equals(personaDTO.getNombre())) {
				bean = personas.get(personaExistenteDTO);
				if(		//Si el bean no tiene CUIL pero el dto si, lo copio
						StringUtil.isEmpty(bean.getCuit()) &&
						!StringUtil.isEmpty(personaDTO.getCuit())  ) 
					bean.setCuit(personaDTO.getCuit());

				return bean;
			}
		}
		return null;
	}

	/**
	 * Devuelve la PersonaBean que identifica el objeto PersonaInfo dado. Si no existe devuelve un bean nuevo.
	 * Si existe la persona actualiza sus datos.
	 * @param persona
	 * @return
	 * @throws PersonaDatosInsuficientesException 
	 */
	private PersonaBean toBean(String nombre, String cuit, String sexo) throws PersonaDatosInsuficientesException {
		if(StringUtil.isEmpty(nombre) || StringUtil.isEmpty(sexo)) return null;
		PersonaDTO dto = new PersonaDTO();
		dto.setLocalizacion(new LocalizacionDTO());
		dto.setNombre(nombre);
		dto.setCuit(cuit);
		dto.setSexo(sexo);
		PersonaBean ret = personaExistente(dto);
		if(ret == null) {
			ret = configuracionServicio.obtenerPersonaCoincidente(dto);
			//lo dejo disponible por si se repite
			personas.put(dto, ret);
		}
		//Actualizo el cuit de la persona con los del dto
		if(ret.getCuit()==null) ret.setCuit(dto.getCuit());
		
		return ret;
	}

	protected abstract Field[] getFields();
	protected abstract void cargarMontos(Item item) throws RendicionesValidationException;
}
