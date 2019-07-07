package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.Constant;

/**
 * Los objetos de esta clase se emplean para registrar 
 * el resultado de admisión legal de los proyectos.
 * Se debe tener en cuenta que la admisión es algo que sólo aplica a los proyectos que 
 * se presentan en instrumentos de LLamados a convocatoria.  
 * 
 * En el caso que el proyecto resulte no admitido y que luego el beneficiario del proyecto 
 * solicite una recosideración dicho proceso de reconsideración se registrará en los objetos  
 * de la clase ProyectoReconsideracionBean.
 * 
 *  @see com.fontar.data.impl.domain.bean.ProyectoReconsideracionBean
 * 
 */
public class ProyectoAdmisionBean extends Auditable {

	private Long id;

	/* ATT */
	private Date fecha;

	private String fundamentacion;

	private String disposicion;

	private String resolucion;

	private String resultado;

	private String observacion;

	public String getDisposicion() {
		return disposicion;
	}

	public void setDisposicion(String disposicion) {
		this.disposicion = disposicion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public boolean esAdmitido() {
		return this.resultado.equals(Constant.ProyAdmisionResultado.APROBADO);
	}

}
