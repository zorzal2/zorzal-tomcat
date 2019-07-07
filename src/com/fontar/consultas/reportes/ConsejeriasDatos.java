package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoBean;

public class ConsejeriasDatos {

	public Collection llenar(Collection proyectos,ProyectoBean proyectoBean) {
		ProyectoPresupuestoBean proyectoPresupuestoBean = proyectoBean.getProyectoPresupuesto();
		PresupuestoRubroCollectionBean  costosTotales = proyectoPresupuestoBean.getPresupuestoRubros();
		if (costosTotales != null) {
			for(PresupuestoRubroBean costoRubro : costosTotales) {
				RubroBean rubroBean = costoRubro.getRubro();
				if (rubroBean.getCodigo().equals("consejerosTecnologicos")) {
					PresupuestoRubroGeneralBean presupuestoRubroGeneralBean = (PresupuestoRubroGeneralBean)costoRubro;
					List<PresupuestoItemBean> list = presupuestoRubroGeneralBean.getItems();
					for (PresupuestoItemBean presupuestoItemBean : list) {
						PresupuestoItemConsejeroTecnologicoBean bean = (PresupuestoItemConsejeroTecnologicoBean) presupuestoItemBean;
						ConsejeriasDatosDTO dto = new ConsejeriasDatosDTO();
						dto.setIdentifier(proyectoBean.getCodigo());
						dto.setNombre(presupuestoItemBean.getNombre());
						dto.setSueldo(bean.getCostoMensual());
						dto.setCuil(bean.getIdentificacionTributaria());
						dto.setDedicacion(bean.getDedicacion());
						dto.setFuncion(bean.getFuncion());
						dto.setParticipacion(bean.getParticipacion());
						dto.setProfesion(bean.getProfesion());
						dto.setCosto(bean.getTotal());
						proyectos.add(dto);
					}
				}
			}
		}
		return proyectos;
	}
	
	
}
