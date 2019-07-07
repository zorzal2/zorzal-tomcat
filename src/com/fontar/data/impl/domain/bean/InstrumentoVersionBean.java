package com.fontar.data.impl.domain.bean;

import java.util.Date;

/**
 * Estos objetos registran las versiones y motivos de modificaciones en relación
 * a los instrumentos de Beneficios. 
 * 
 */
public class InstrumentoVersionBean {

	private Long id;

	private Long idInstrumento;

	private Long version;

	private String descripcion;

	private String codigo;

	private Date fecha;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
