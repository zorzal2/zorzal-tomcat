package com.fontar.data.impl.domain.bean;


/**
 * Esta clase permite definir objetos que representan las características físicas 
 * del expediente de cada proyecto.
 * 
 * @see com.fontar.data.impl.domain.bean.ProyectoBean   
 */
public class ExpedienteBean extends Auditable{

	private Long id;
	
	private Long cuerpo;
	
	private Long folioDesde;
	
	private Long folioHasta;

	public Long getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(Long cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Long getFolioDesde() {
		return folioDesde;
	}

	public void setFolioDesde(Long folioDesde) {
		this.folioDesde = folioDesde;
	}

	public Long getFolioHasta() {
		return folioHasta;
	}

	public void setFolioHasta(Long folioHasta) {
		this.folioHasta = folioHasta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}