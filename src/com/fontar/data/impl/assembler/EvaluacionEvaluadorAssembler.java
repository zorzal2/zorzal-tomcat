package com.fontar.data.impl.assembler;

import java.text.ParseException;
import java.util.Map;

import com.fontar.data.api.dao.EntidadEvaluadoraDAO;
import com.fontar.data.api.dao.EvaluadorDAO;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTODecorator;
import com.fontar.seguridad.ObjectUtils;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.util.Util;
import com.pragma.data.assembler.GenericAssembler;
import com.pragma.util.ContextUtil;

public class EvaluacionEvaluadorAssembler extends GenericAssembler<EvaluacionEvaluadorBean, EvaluacionEvaluadorDTO> {

	private static class SingletonHolder {
		private static EvaluacionEvaluadorAssembler instance = new EvaluacionEvaluadorAssembler();
	}

	public static EvaluacionEvaluadorAssembler getInstance() {
		return SingletonHolder.instance;
	}


	/***************************************************************************
	 * Crea un nuevo dto con la información base
	 **************************************************************************/
	public EvaluacionEvaluadorDTO buildDto(String entidadEvaluadora, String evaluador, String lugar) {
		EvaluacionEvaluadorDTO dto = new EvaluacionEvaluadorDTO();
		dto.setIdEntidadEvaluadora(entidadEvaluadora);
		dto.setIdEvaluador(evaluador);
		dto.setLugar(lugar);
		return dto;
	}

	public EvaluacionEvaluadorDTODecorator buildDto(EvaluacionEvaluadorBean bean) {
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		if(service.encyptionAvailable()) {
			String evaluador = bean.getEvaluador();
			return this.buildDto(this.buildDto(bean.getInstitucion(),(evaluador==null || evaluador.length()==0)? null : evaluador,bean.getLugarEvaluacion()));
		}
		return this.buildDto(this.buildDto(ObjectUtils.ENCRIPTION_WARNING,ObjectUtils.ENCRIPTION_WARNING,ObjectUtils.ENCRIPTION_WARNING));
	}

	/**
	 * Crea un decorador que agrega la denominacion del evaluador y entidad
	 * evaluadora
	 */
	public EvaluacionEvaluadorDTODecorator buildDto(EvaluacionEvaluadorDTO dto) {

		EvaluacionEvaluadorDTODecorator newDto = new EvaluacionEvaluadorDTODecorator(dto);
		newDto.setEntidadEvaluadora( this.resolveEntidadEvaluadora(dto.getIdEntidadEvaluadora()) );
		newDto.setEvaluador(this.resolveEvaluador( dto.getIdEvaluador()));
		return newDto;
	}

	private String resolveEntidadEvaluadora(String idEntidadEvaluadora) {

		if ( !Util.isBlank( idEntidadEvaluadora ) ) {
			
			if(idEntidadEvaluadora.equals(ObjectUtils.ENCRIPTION_WARNING)  ) { 
				return idEntidadEvaluadora;
			}else{
				EntidadEvaluadoraDAO dao = (EntidadEvaluadoraDAO) ContextUtil.getBean("entidadEvaluadoraDao");
				EntidadEvaluadoraBean entidadEvaluadoraBean = dao.read(Long.valueOf(idEntidadEvaluadora));
				return entidadEvaluadoraBean.getDenominacion();
			}
		}
		return ObjectUtils.ENCRIPTION_EMPTY;
	}

	private String resolveEvaluador(String idEvaluador) {
		
		if ( !Util.isBlank(idEvaluador) ){
			if( idEvaluador.equals(ObjectUtils.ENCRIPTION_WARNING)  ) {
				return idEvaluador;
			}else{
				EvaluadorDAO evaluadorDAO = (EvaluadorDAO) ContextUtil.getBean("evaluadorDao");
				EvaluadorBean evaluadorBean = evaluadorDAO.read(Long.valueOf(idEvaluador));
				return evaluadorBean.getNombre();
			}
		}
		return ObjectUtils.ENCRIPTION_EMPTY;
	}

	@Override
	public Map<String, Object> getBean2DtoMap(EvaluacionEvaluadorBean bean) throws ParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getDto2BeanMap(EvaluacionEvaluadorDTO dto) throws ParseException {
		throw new UnsupportedOperationException();
	}


	@Override
	protected EvaluacionEvaluadorBean newBeanInstance() {
		return new EvaluacionEvaluadorBean();
	}


	@Override
	protected EvaluacionEvaluadorDTO newDtoInstance() {
		return new EvaluacionEvaluadorDTO();
	}

}
