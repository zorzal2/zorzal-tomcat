package com.fontar.data.impl.domain.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProyectoEntidadBeneficiariaBean implements Serializable {

	private Long id;

	private Long idProyecto;

	private Long idEntidadBeneficiaria;

	private EntidadBeneficiariaBean entidadBeneficiaria;

	private ProyectoBean proyecto;

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof ProyectoEntidadBeneficiariaBean))
			return false;
		ProyectoEntidadBeneficiariaBean bean = (ProyectoEntidadBeneficiariaBean) o;
		if (!id.equals(bean.getId()))
			return false;
		if (!idProyecto.equals(bean.getIdProyecto()))
			return false;
		return true;
	}

	public int hashCode() {
		return id.hashCode() + idProyecto.hashCode();
	}

	public EntidadBeneficiariaBean getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(EntidadBeneficiariaBean entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public ProyectoBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}

}
