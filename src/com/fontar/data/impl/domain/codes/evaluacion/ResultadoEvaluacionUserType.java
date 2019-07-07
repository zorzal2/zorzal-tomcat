package com.fontar.data.impl.domain.codes.evaluacion;

import com.fontar.data.impl.domain.hibernate.EncryptedEnumUserType;

public class ResultadoEvaluacionUserType extends EncryptedEnumUserType<ResultadoEvaluacion>{

	
	public ResultadoEvaluacionUserType() { 
        super(ResultadoEvaluacion.class); 
    } 
}
