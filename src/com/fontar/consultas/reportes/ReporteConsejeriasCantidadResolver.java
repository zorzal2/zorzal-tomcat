package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Property;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.data.api.dao.ComposicionDAO;
import com.fontar.data.impl.domain.bean.ComposicionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.ContextUtil;

public class ReporteConsejeriasCantidadResolver extends ReporteProyectoResolver implements Resolver {
	
	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
				
		proyecto.add(Property.forName("proyectoPresupuesto").isNotNull());		
		proyecto.add(Property.forName("fechaResolucion").isNotNull());		
		proyecto.add(Expression.sqlRestriction("TP_PROYECTO = 'P'"));
		proyecto.add(Property.forName("instrumento").isNotNull());		
		Criteria instrumento = proyecto.createCriteria("instrumento");
		Criteria matrizPresupuesto = instrumento.createCriteria("matrizPresupuesto");
		matrizPresupuesto.add(Expression.sqlRestriction("TP_MATRIZ in ('CF_CONSEJERIAS','CONSEJERIAS')"));
		
	}

	@Override
	public Collection result(HttpServletRequest request) {
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		List proyecto = result.getList();
		CollectionUtils.transform(proyecto, new ConsejeriasCantidad());
		Comparator comparator2 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				ConsejeriasCantidadDTO dto1 = (ConsejeriasCantidadDTO) arg0;
				ConsejeriasCantidadDTO dto2 = (ConsejeriasCantidadDTO) arg1;
				return dto1.getIdentificador().compareToIgnoreCase(dto2.getIdentificador());
			}};
			
			
//		List<ProyectoIntervinientesDTO> list = result;
		Collections.sort(proyecto,comparator2);
		return proyecto;
	}


	@Override
	public void setInitialContextImpl(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	private class ConsejeriasCantidad implements Transformer {

		public Object transform(Object object) {
			ProyectoBean proyectoBean = (ProyectoBean) object;
			ConsejeriasCantidadDTO dto = new ConsejeriasCantidadDTO();
			Boolean exist = false;
			dto.setCantidadConsejeros(0);
			ProyectoPresupuestoBean proyectoPresupuestoBean = proyectoBean.getProyectoPresupuesto();
			if (proyectoBean.getProyectoPresupuesto() != null) {
				dto.setMontoFontar(proyectoBean.getProyectoPresupuesto().getMontoSolicitado());
				dto.setMontoTotal(proyectoBean.getProyectoPresupuesto().getMontoTotal());
				PresupuestoRubroCollectionBean  costosTotales = proyectoPresupuestoBean.getPresupuestoRubros();
				if (costosTotales != null) {
					for(PresupuestoRubroBean costoRubro : costosTotales) {
						RubroBean rubroBean = costoRubro.getRubro();
						if (rubroBean.getCodigo().equals("consejerosTecnologicos")) {
							PresupuestoRubroGeneralBean presupuestoRubroGeneralBean = (PresupuestoRubroGeneralBean)costoRubro;
							List<PresupuestoItemBean> list = presupuestoRubroGeneralBean.getItems();
							dto.setCantidadConsejeros(list.size());
							exist = true;
						}
					}
				}
			}
			//if (exist) {
				dto.setEntidadBeneficiaria(proyectoBean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
				List<ComposicionBean> list = this.getComposicionDao().findByID(proyectoBean.getProyectoDatos().getIdEntidadBeneficiaria());
				if (!list.isEmpty()) {
					dto.setCantidadEmpresas(list.size());
				}
				dto.setCodigo(proyectoBean.getInstrumento().getDenominacion());
				if (proyectoBean.getProyectoDatos().getCiiu() != null) {
					dto.setCodigoCiiu(proyectoBean.getProyectoDatos().getCiiu().getCodigo());
				}
				dto.setIdentificador(proyectoBean.getCodigo());
				dto.setJurisdiccion(proyectoBean.getProyectoJurisdiccion().getJurisdiccion().getCodigo());
				dto.setTitulo(proyectoBean.getProyectoDatos().getTitulo());
			//}
			//else {
			//	dto = null;
			//}
			return dto;
		}
		
		private ComposicionDAO getComposicionDao(){
			return (ComposicionDAO) ContextUtil.getBean("composicionDao");
			
		}
	}
}






