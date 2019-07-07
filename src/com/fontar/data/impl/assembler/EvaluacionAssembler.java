package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.fontar.data.api.domain.Evaluable;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluableDTO;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;

public class EvaluacionAssembler extends GenericAssembler<EvaluacionBean, EvaluacionDTO> {

	private static class SingletonHolder {
		private static EvaluacionAssembler instance = new EvaluacionAssembler();
	}

	public static EvaluacionAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@Override
	public Map<String, Object> getBean2DtoMap(EvaluacionBean bean) throws ParseException {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", bean.getId());
		map.put("tipo", bean.getTipo());
		map.put("estado", bean.getEstado());
		//map.put("resultado", bean.getResultado());
		map.put("recomendacion", bean.getRecomendacion());
		map.put("fundamentacion", bean.getFundamentacion());
		map.put("fecha", DateTimeUtil.formatDate(bean.getFecha()));
		map.put("fechaInicial", DateTimeUtil.formatDate(bean.getFechaInicial()));
		map.put("idCronograma", bean.getIdCronograma());
		map.put("idPlanTrabajo", bean.getIdPlanTrabajo());
		map.put("idPresupuesto", bean.getIdPresupuesto());
		map.put("idPresupuestoInicial", bean.getIdPresupuestoInicial());
		map.put("observacion", bean.getObservacion());
		map.put("idWorkFlow", bean.getIdWorkFlow());
		map.put("idProyecto", bean.getIdProyecto());

		// Evaluable
		map.put("evaluable", this.buildDTO(bean.getProyecto()));

		return map;
	}

	@Override
	public Map<String, Object> getDto2BeanMap(EvaluacionDTO dto) throws ParseException {

		Map<String, Object> map = new HashMap<String, Object>();

		// FF : La bitacora no se pasa de DTO to

		map.put("id", dto.getId());
		map.put("tipo", dto.getTipo());
		map.put("estado", dto.getEstado());
		map.put("resultado", dto.getResultado());
		map.put("recomendacion", dto.getRecomendacion());
		map.put("fundamentacion", dto.getFundamentacion());
		if (dto.getFecha() != null)
			map.put("fecha", dto.getFecha());
		if (dto.getFechaInicial() != null)
			map.put("fechaInicial", dto.getFechaInicial());
		map.put("idCronograma", dto.getIdCronograma());
		map.put("idPlanTrabajo", dto.getIdPlanTrabajo());
		map.put("idPresupuesto", dto.getIdPresupuesto());
		map.put("idPresupuestoInicial", dto.getIdPresupuestoInicial());
		map.put("observacion", dto.getObservacion());
		map.put("idWorkFlow", dto.getIdWorkFlow());
		map.put("idProyecto", dto.getIdProyecto());

		return map;
	}

	public EvaluableDTO buildDTO(Evaluable evaluable) {

		EvaluableDTO dto = null;

		if (evaluable instanceof ProyectoBean)
			dto = ProyectoAssembler.buildDto((ProyectoBean) evaluable);

		if (evaluable instanceof IdeaProyectoBean){
			IdeaProyectoDTOAssembler assembler = new IdeaProyectoDTOAssembler(); 
			dto = (EvaluableDTO) assembler.buildDTO((IdeaProyectoBean) evaluable);
		}	
			
		
		return dto;
	}

	@Override
	public EvaluacionDTO buildDto(EvaluacionBean bean) {

		EvaluacionDTO dto = super.buildDto(bean);
		try{
			dto.setResultadoEvaluacion(bean.getResultadoEvaluacion());
		}catch(SecurityException e){
			
		}
		return dto;
		
	}

	@Override
	protected EvaluacionBean newBeanInstance() {
		return new EvaluacionBean();
	}

	@Override
	protected EvaluacionDTO newDtoInstance() {
		return new EvaluacionDTO();
	}
	
	
	
}
