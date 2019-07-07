package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.util.StringUtil;

/**
 * Dto de visualizacion de un proyecto
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */
public class ProyectoVisualizacionDTO extends ProyectoEdicionDTO { 
	private static final long serialVersionUID = 1L;
	
	private PersonaDTO personaLegal;

	private PersonaDTO personaDirector;

	private PersonaDTO personaRepresentante;

	private String txtTipoProyecto;

	private String txtEntidadBancaria;

	private String instrumento;

	private String tipoInstrumentoDef;

	private EstadoProyecto estado;

	private Boolean estaEnReconsideracion;

	private Boolean estaEnPaquete;

	private Boolean permiteAdquisicion;

	private Boolean aplicaCargaAlicuotaCF;

	private EncryptedObject montoSolicitado;
	
	private EncryptedObject montoSolicitadoOriginal;
	
	private String motivoCierre;
	
	private String montoBeneficioFONTARSolicitado;

	private String montoBeneficioFONTARAprobado;
	//Solo para CF
	private String porcentajeAlicuotaSolicitada;
	//Solo para CF
	private String porcentajeAlicuotaAdjudicada;
	
	public String getMontoBeneficioFONTARAprobado() {
		return montoBeneficioFONTARAprobado;
	}

	public void setMontoBeneficioFONTARAprobado(String montoBeneficioFONTARAprobado) {
		this.montoBeneficioFONTARAprobado = montoBeneficioFONTARAprobado;
	}

	public String getMontoBeneficioFONTARSolicitado() {
		return montoBeneficioFONTARSolicitado;
	}

	public void setMontoBeneficioFONTARSolicitado(String montoBeneficioFONTARSolicitado) {
		this.montoBeneficioFONTARSolicitado = montoBeneficioFONTARSolicitado;
	}

	public String getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(String motivoCierre) {
		if (com.fontar.util.Util.isBlank(motivoCierre)) {
			this.motivoCierre = motivoCierre;
		}
		else {
			this.motivoCierre = MotivoCierre.valueOf(motivoCierre).getDescripcion();
		}
	}

	public Boolean getEstaEnPaquete() {
		return estaEnPaquete;
	}

	public void setEstaEnPaquete(Boolean estaEnPaquete) {
		this.estaEnPaquete = estaEnPaquete;
	}

	public Boolean getEstaEnReconsideracion() {
		return estaEnReconsideracion;
	}

	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		this.estaEnReconsideracion = estaEnReconsideracion;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getTxtEntidadBancaria() {
		return txtEntidadBancaria;
	}

	public void setTxtEntidadBancaria(String txtEntidadBancaria) {
		this.txtEntidadBancaria = txtEntidadBancaria;
	}

	public String getTxtTipoProyecto() {
		return txtTipoProyecto;
	}

	public void setTxtTipoProyecto(String txtTipoProyecto) {
		this.txtTipoProyecto = txtTipoProyecto;
	}

	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof ProyectoEdicionDTO && this.getId()!=null) {
			ProyectoEdicionDTO other = (ProyectoEdicionDTO) obj;
			return this.getId().equals(other.getId());
		} else {
			return false;
		}
	}

	public EmpleoPermanenteDTO getEmpleo() {
		EmpleoPermanenteDTO empleo2 = super.getEmpleo();
		if(empleo2==null) {
			empleo2 = new EmpleoPermanenteDTO();
			this.setEmpleo(empleo2);
		}
		return empleo2;
	}

	public int hashCode() {
		if(this.getId()==null) return 0;
		else return this.getId().hashCode();
	}

	public EncryptedObject getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(EncryptedObject montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	public EncryptedObject getMontoSolicitadoOriginal() {
		return montoSolicitadoOriginal;
	}

	public void setMontoSolicitadoOriginal(EncryptedObject montoSolicitadoOriginal) {
		this.montoSolicitadoOriginal = montoSolicitadoOriginal;
	}

	public String getMontoSolicitadoAsString() {
		try {
			if (this.getMontoSolicitado()== null) {
				return "";
			}
			return StringUtil.formatMoneyForPresentation((Number)getMontoSolicitado().getObject());
		}
		catch (Exception e) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}
	
	public String getMontoSolicitadoOriginalAsString() {
		try {
			if (this.getMontoSolicitadoOriginal() == null) {
				return "";
			}
			return StringUtil.formatMoneyForPresentation((Number)this.getMontoSolicitadoOriginal().getObject());
		}
		catch (Exception e) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
		
	}
	public Boolean getIsCerrado() {
		return (this.estado.equals(EstadoProyecto.CERRADO));
	}

	public Boolean getPermiteAdquisicion() {
		return permiteAdquisicion;
	}

	public void setPermiteAdquisicion(Boolean permiteAdquisicion) {
		this.permiteAdquisicion = permiteAdquisicion;
	}

	public String getTipoInstrumentoDef() {
		return tipoInstrumentoDef;
	}

	public void setTipoInstrumentoDef(String tipoInstrumentoDef) {
		this.tipoInstrumentoDef = tipoInstrumentoDef;
	}

	public String getPorcentajeAlicuotaAdjudicada() {
		return porcentajeAlicuotaAdjudicada;
	}

	public void setPorcentajeAlicuotaAdjudicada(String porcentajeAlicuotaAdjudicada) {
		this.porcentajeAlicuotaAdjudicada = porcentajeAlicuotaAdjudicada;
	}

	public String getPorcentajeAlicuotaSolicitada() {
		return porcentajeAlicuotaSolicitada;
	}

	public void setPorcentajeAlicuotaSolicitada(String porcentajeAlicuotaSolicitada) {
		this.porcentajeAlicuotaSolicitada = porcentajeAlicuotaSolicitada;
	}

	public Boolean getAplicaCargaAlicuotaCF() {
		return aplicaCargaAlicuotaCF;
	}

	public void setAplicaCargaAlicuotaCF(Boolean correspondeAlicuotaCF) {
		this.aplicaCargaAlicuotaCF = correspondeAlicuotaCF;
	}

	public PersonaDTO getPersonaDirector() {
		return personaDirector;
	}

	public void setPersonaDirector(PersonaDTO personaDirector) {
		this.personaDirector = personaDirector;
	}

	public PersonaDTO getPersonaLegal() {
		return personaLegal;
	}

	public void setPersonaLegal(PersonaDTO personaLegal) {
		this.personaLegal = personaLegal;
	}

	public PersonaDTO getPersonaRepresentante() {
		return personaRepresentante;
	}

	public void setPersonaRepresentante(PersonaDTO personaRepresentante) {
		this.personaRepresentante = personaRepresentante;
	}

}
