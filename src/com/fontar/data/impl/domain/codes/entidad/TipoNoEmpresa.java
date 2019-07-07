package com.fontar.data.impl.domain.codes.entidad;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de beneficiarios no empresas
 */
public enum TipoNoEmpresa implements Enumerable {


	INSTITUCION("app.codes.entidad.tipo.institucion", "app.codes.entidad.tipo.nombreCorto.institucion"),
	UNIVERSIDAD("app.codes.entidad.tipo.universidad", "app.codes.entidad.tipo.nombreCorto.universidad"),
	ORGANIZACION("app.codes.entidad.tipo.organizacion", "app.codes.entidad.nombreCorto.tipo.organizacion"),
	ASOCIACION("app.codes.entidad.tipo.asociacion", "app.codes.entidad.nombreCorto.tipo.asociacion"),
	GOBIERNO("app.codes.entidad.tipo.gobierno", "app.codes.entidad.nombreCorto.tipo.gobierno"),
	UVT("app.codes.entidad.tipo.unidadDeVinculacionTecnologica", "app.codes.entidad.nombreCorto.tipo.unidadDeVinculacionTecnologica"),
	PERSONA("app.codes.entidad.tipo.personaFisica", "app.codes.entidad.nombreCorto.tipo.personaFisica");

	private String descripcion;
	private String descripcionCorta;

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	private TipoNoEmpresa(String key, String descripcionCorta) {
		this.descripcion = ResourceManager.getCodesResource(key);
		this.descripcionCorta = ResourceManager.getCodesResource(descripcionCorta);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
