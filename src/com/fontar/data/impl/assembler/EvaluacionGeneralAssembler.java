package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTODecorator;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.DateTimeUtil;

public class EvaluacionGeneralAssembler extends GenericAssembler<EvaluacionGeneralBean, EvaluacionGeneralDTO> {


	private static class SingletonHolder {
		private static EvaluacionGeneralAssembler instance = new EvaluacionGeneralAssembler();
	}

	public static EvaluacionGeneralAssembler getInstance() {
		return SingletonHolder.instance;
	}

	@Override
	public Map<String, Object> getBean2DtoMap(EvaluacionGeneralBean bean) throws ParseException {

		Map<String, Object> map = new HashMap<String, Object>();

		// cargo el mapa de evaluación
		EvaluacionAssembler evaluacionAssembler = EvaluacionAssembler.getInstance();
		map.putAll(evaluacionAssembler.getBean2DtoMap(bean));
		map.remove("estado"); // FIXME hay que hacer el DTO sin usar estas
		map.remove("tipo"); // FIXME hay que hacer el DTO sin usar estas utils
		//map.remove("resultado"); // FIXME hay que hacer el DTO sin usar estas utils
		
		map.put("esAuditoriaContable", bean.getEsAuditoriaContable());
		map.put("esContable", bean.getEsContable());
		map.put("esEconomica", bean.getEsEconomica());
		map.put("esFinanciera", bean.getEsFinanciera());
		map.put("esTecnica", bean.getEsTecnica());
		map.put("esVisitaTecnica", bean.getEsVisitaTecnica());
		map.put("fechaEntregaComprometida", DateTimeUtil.formatDate(bean.getFechaEntregaComprometida()));
		//map.put("tipoEvaluacionFinanciera", bean.getTipoEvaluacionFinanciera());

		map.put("aceptada", ObjectUtils.booleanSafeGet(bean, "aceptada"));

		return map;
	}

	@Override
	public Map<String, Object> getDto2BeanMap(EvaluacionGeneralDTO dto) throws ParseException {

		Map<String, Object> map = new HashMap<String, Object>();

		// cargo el mapa de evaluación
		EvaluacionAssembler evaluacionAssembler = EvaluacionAssembler.getInstance();
		map.putAll(evaluacionAssembler.getDto2BeanMap(dto));

		map.put("esAuditoriaContable", dto.getEsAuditoriaContable());
		map.put("esContable", dto.getEsContable());
		map.put("esEconomica", dto.getEsEconomica());
		map.put("esFinanciera", dto.getEsFinanciera());
		map.put("esTecnica", dto.getEsTecnica());
		map.put("esVisitaTecnica", dto.getEsVisitaTecnica());

		if (dto.getFechaEntregaComprometida() != null)
			map.put("fechaEntregaComprometida", dto.getFechaEntregaComprometida());

		

		if (dto.getFechaInicial() != null)
			map.put("fechaInicial", dto.getFechaInicial());

		return map;
	}

	public EvaluacionGeneralDTO buildDto(EvaluacionGeneralBean bean) {
		// FIXME esto debe ser removido y armar el DTO con los get del bean y
		// set del dto
		EvaluacionGeneralDTO dto = super.buildDto(bean);

		dto.setEstado(bean.getEstado());
		dto.setTipo(bean.getTipo());
		dto.setTipoEvaluacionFinanciera( bean.getTipoEvaluacionFinanciera());
		
		//TODO, ver como se resuelve esto
		try{
			dto.setResultadoEvaluacion(bean.getResultadoEvaluacion());
			dto.setAceptada(bean.getAceptada());
		}catch(SecurityException e){
			
		}

		Iterator<EvaluacionEvaluadorBean> evaluadores = bean.getEvaluadores().iterator();
		Collection<EvaluacionEvaluadorDTO> evaluadoresDto = new HashSet<EvaluacionEvaluadorDTO>();
		EvaluacionEvaluadorAssembler assembler = EvaluacionEvaluadorAssembler.getInstance();

		while (evaluadores.hasNext()) {
			EvaluacionEvaluadorBean evaluador = evaluadores.next();
			EvaluacionEvaluadorDTODecorator evaluacionEvaluadorDTO = assembler.buildDto(evaluador);
			evaluadoresDto.add(evaluacionEvaluadorDTO);
		}

		dto.setEvaluadores(evaluadoresDto);
		
		
		dto.setBitacora(BitacoraAssembler.buildDto(bean.getBitacora()));

		dto.setClasificacion(bean.obtenerTiposDeEvaluacion());
		
		return dto;

	}

	public EvaluacionGeneralBean buildBean(EvaluacionGeneralDTO dto) {

		EvaluacionGeneralBean evaluacionGeneralBean = this.emptyBean();

		try {
			// Comun a evaluacion
			evaluacionGeneralBean.setId(dto.getId());
			evaluacionGeneralBean.setTipo(dto.getTipo());

			evaluacionGeneralBean.setEstado(dto.getEstado());
			evaluacionGeneralBean.setRecomendacion(dto.getRecomendacion());
			evaluacionGeneralBean.setFundamentacion(dto.getFundamentacion());

			if (dto.getFecha() != null)
				evaluacionGeneralBean.setFecha(DateTimeUtil.getDate(dto.getFecha()));
			if (dto.getFechaInicial() != null)
				evaluacionGeneralBean.setFechaInicial(DateTimeUtil.getDate(dto.getFechaInicial()));

			evaluacionGeneralBean.setIdCronograma(dto.getIdCronograma());
			evaluacionGeneralBean.setIdPlanTrabajo(dto.getIdPlanTrabajo());
			evaluacionGeneralBean.setIdPresupuesto(dto.getIdPresupuesto());
			evaluacionGeneralBean.setObservacion(dto.getObservacion());
			evaluacionGeneralBean.setIdWorkFlow(dto.getIdWorkFlow());
			evaluacionGeneralBean.setIdProyecto(dto.getIdProyecto());

			// Evaluacion gral
			BeanUtils.copyProperty(evaluacionGeneralBean, "esAuditoriaContable", dto.getEsAuditoriaContable()); // requiere
			// conversion
			BeanUtils.copyProperty(evaluacionGeneralBean, "esContable", dto.getEsContable()); // requiere
			// conversion
			BeanUtils.copyProperty(evaluacionGeneralBean, "esEconomica", dto.getEsEconomica()); // requiere
			// conversion
			BeanUtils.copyProperty(evaluacionGeneralBean, "esFinanciera", dto.getEsFinanciera()); // requiere
			// conversion
			BeanUtils.copyProperty(evaluacionGeneralBean, "esTecnica", dto.getEsTecnica()); // requiere
			// conversion
			BeanUtils.copyProperty(evaluacionGeneralBean, "esVisitaTecnica", dto.getEsVisitaTecnica()); // requiere
			// conversion

			evaluacionGeneralBean.setTipoEvaluacionFinanciera(dto.getTipoEvaluacionFinanciera());
			if (dto.getFechaEntregaComprometida() != null)
				evaluacionGeneralBean.setFechaEntregaComprometida(DateTimeUtil.getDate(dto.getFechaEntregaComprometida()));
			if (dto.getFechaInicial() != null)
				evaluacionGeneralBean.setFechaInicial(DateTimeUtil.getDate(dto.getFechaInicial()));

		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return evaluacionGeneralBean;
	}

	@Override
	protected EvaluacionGeneralBean newBeanInstance() {
		return new EvaluacionGeneralBean();
	}

	@Override
	protected EvaluacionGeneralDTO newDtoInstance() {
		return new EvaluacionGeneralDTO();
	}
}
