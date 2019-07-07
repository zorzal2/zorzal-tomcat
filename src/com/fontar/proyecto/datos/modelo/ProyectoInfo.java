package com.fontar.proyecto.datos.modelo;

import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;
import com.pragma.util.exception.IllegalArgumentException;


public class ProyectoInfo {
	private String codigo;
	private Long ciiuId;
	private String ideaProyecto;
	private Long ideaProyectoId;
	private String instrumento;
	private Long instrumentoId;
	private Long presentacionConvocatoriaId;
	private String ciiu;
	private String objetivo;
	private String titulo;
	private Long tipoId;
	private String tipo;
	private Integer duracion;
	private EmpresaInfo empresa = new EmpresaInfo();
	private LocalizacionInfo localizacion = new LocalizacionInfo();
	private PersonaInfo representante = new PersonaInfo(); 
	private PersonaInfo director = new PersonaInfo(); 
	private PersonaInfo responsableLegal = new PersonaInfo();
	private EmpleoPermanenteDTO empleoPermanente = new EmpleoPermanenteDTO();
	private String resumen;
	private String palabrasClave;
	private String jurisdiccion;
	private Long   idJurisdiccion;
	
	public EmpleoPermanenteDTO getEmpleoPermanente() {
		return empleoPermanente;
	}
	public void setEmpleoPermanente(EmpleoPermanenteDTO empleoPermanente) {
		this.empleoPermanente = empleoPermanente;
	}
	public String getPalabrasClave() {
		return palabrasClave;
	}
	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}
	public PersonaInfo getResponsableLegal() {
		return responsableLegal;
	}
	public void setResponsableLegal(PersonaInfo responsableLegal) {
		this.responsableLegal = responsableLegal;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getCiiu() {
		return ciiu;
	}
	public void setCiiu(String ciiu) {
		this.ciiu = ciiu;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public PersonaInfo getDirector() {
		return director;
	}
	public void setDirector(PersonaInfo director) {
		this.director = director;
	}
	public LocalizacionInfo getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(LocalizacionInfo localizacion) {
		this.localizacion = localizacion;
	}
	public EmpresaInfo getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaInfo empresa) {
		this.empresa = empresa;
	}
	public PersonaInfo getRepresentante() {
		return representante;
	}
	public void setRepresentante(PersonaInfo representante) {
		this.representante = representante;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Object getProperty(String name) {
		try {
			return BeanUtils.getProperty(this, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No existe la propiedad " + name);
		}
	}
	public void setProperty(String name, Object value) throws ConversionException {
		try {
			BeanUtils.setProperty(this, name, value);
		}
		catch (ConversionException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No existe la propiedad " + name);
		}
	}
	public Long getTipoId() {
		return tipoId;
	}
	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}
	public Long getCiiuId() {
		return ciiuId;
	}
	public void setCiiuId(Long ciiuId) {
		this.ciiuId = ciiuId;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	public Long getInstrumentoId() {
		return instrumentoId;
	}
	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}
	public String getIdeaProyecto() {
		return ideaProyecto;
	}
	public void setIdeaProyecto(String ideaProyecto) {
		this.ideaProyecto = ideaProyecto;
	}
	public Long getIdeaProyectoId() {
		return ideaProyectoId;
	}
	public void setIdeaProyectoId(Long ideaProyectoId) {
		this.ideaProyectoId = ideaProyectoId;
	}
	public Long getPresentacionConvocatoriaId() {
		return presentacionConvocatoriaId;
	}
	public void setPresentacionConvocatoriaId(Long presentacionConvocatoriaId) {
		this.presentacionConvocatoriaId = presentacionConvocatoriaId;
	}
	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}
	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
}
