package com.fontar.data.impl.assembler.proyecto.presupuesto.plan;

import java.util.HashSet;
import java.util.Set;

import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.ActividadDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.EtapaDTO;
import com.pragma.util.ContextUtil;

public class EtapaDTOAssembler {
	public static EtapaDTO buildDto(EtapaBean bean) {
		if(bean==null) return null;
		EtapaDTO dto = new EtapaDTO();
		dto.setId(bean.getId());
		dto.setNombre(bean.getNombre());
		dto.setDescripcion(bean.getDescripcion());
		dto.setInicio(bean.getInicio());
		dto.setFin(bean.getFin());
		dto.setAvance(bean.getAvance());
		dto.setObservaciones(bean.getObservaciones());
		
		if(bean.getPresupuesto()!=null)dto.setProyectoPresupuestoId(bean.getPresupuesto().getId());
		
		
		Set<ActividadDTO> actividades = new HashSet<ActividadDTO>();
		for(ActividadBean actividad : bean.getActividades()) {
			actividades.add(ActividadDTOAssembler.buildDto(actividad));
		}
		dto.setActividades(actividades);
		return dto;
	}

	public static EtapaBean updateBean(EtapaDTO dto) {
		if(dto==null) return null;
		EtapaBean bean = new EtapaBean();
		bean.setId(dto.getId());
		bean.setNombre(dto.getNombre());
		bean.setDescripcion(dto.getDescripcion());
		bean.setInicio(dto.getInicio());
		bean.setFin(dto.getFin());
		bean.setAvance(dto.getAvance());
		bean.setObservaciones(dto.getObservaciones());

		Long presupuestoId = dto.getProyectoPresupuestoId();
		if(presupuestoId==null) {
			bean.setPresupuesto(null);
		} else {
			ProyectoPresupuestoDAO dao = (ProyectoPresupuestoDAO)ContextUtil.getBean("proyectoPresupuestoDao");
			bean.setPresupuesto(dao.read(presupuestoId));
		}
		//convierto a ben las actividades componentes
		Set<ActividadBean> actividades = new HashSet<ActividadBean>();
		for(ActividadDTO actividad : dto.getActividades()) {
			actividades.add(ActividadDTOAssembler.updateBean(actividad));
		}
		bean.setActividades(actividades);
		return bean;
	}
}
