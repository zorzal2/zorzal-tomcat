package com.fontar.data.impl.assembler;

import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;

public class CompositeVisualizarEvaluacionAssembler implements EvaluacionAssembler {


	//Resuelve el DTO de evaluacion general
	VisualizarEvaluacionAssembler evaluacionGeneralAssembler;
	
	//Resuleve el DTO de evaluacion
	com.fontar.data.impl.assembler.EvaluacionAssembler evaluacionAssembler;
	
	public CompositeVisualizarEvaluacionAssembler(){
		super();
		//this.evaluacionAssembler = new com.fontar.data.impl.assembler.EvaluacionAssembler();
		this.evaluacionGeneralAssembler = new VisualizarEvaluacionAssembler();
		this.evaluacionAssembler = com.fontar.data.impl.assembler.EvaluacionAssembler.getInstance();
	}

	public EvaluacionDTO buildDTO(EvaluacionBean bean) {
		
		//Resuelve el DTO si es una evaluacion general
		if( bean instanceof EvaluacionGeneralBean){
			return this.evaluacionGeneralAssembler.buildDTO((EvaluacionGeneralBean) bean );
		}
		
		if( bean instanceof EvaluacionPaqueteBean){
			//Para EvaluacionPaquete se trata como Evaluacion (no hay particualridades)
			return this.evaluacionAssembler.buildDto( bean );
		}
		
		//Resuelve el DTO cuando es una evaluacion
		if( bean.getClass().equals(EvaluacionBean.class) ){
			return this.evaluacionAssembler.buildDto( bean );
		}else{
			//No es posible resolver el DTO
			throw new IllegalArgumentException("Tipo de evaluacion no soportada");
		}
	}
}