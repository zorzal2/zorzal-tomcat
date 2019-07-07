package com.fontar.seguridad.acegi;

import com.fontar.seguridad.FontarSecurityAttributeType;

public class InstrumentoSecurityConfig extends FontarSecurityConfig {

	private static final long serialVersionUID = -1099531094918238053L;
	
	private Long idInstrumento;
		
	public InstrumentoSecurityConfig(String config , Long idInstrumento) {
		super(config);
		this.idInstrumento = idInstrumento;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	@Override
	public FontarSecurityAttributeType getType() {
		return FontarSecurityAttributeType.PERMISSION_BY_INSTRUMENTO;
	}

	public String getAttribute() {
		return getType().getPrefix()+"["+getIdInstrumento()+"]"+getName();
	}
}
