package com.fontar.data.impl.domain.codes.proyecto;


import com.fontar.data.impl.domain.hibernate.EncryptedEnumUserType;

public class RecomendacionUserType extends EncryptedEnumUserType<Recomendacion> {

	public RecomendacionUserType() {
		super(Recomendacion.class);
	}
	
}