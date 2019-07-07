package com.fontar.consultas.reportes;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.ProyectoAdjudicadoXRubroDTO;
import com.fontar.web.action.consultas.Result;

/**
 * Obtiene los <code>Proyectos</code> que fueron adjudicados 
 * (<i>fechaResolucion</i> is not null) ordenado por código.
 * También obtiene los <code>Presupuestos</code> desglosados
 * por <code>Rubros</code> padres (Bienes, RRHH, Consultoría
 * y Servicios, Materiales e Insumos, Otros Costos).<br>
 * @author ssanchez
 */
public class ReporteProyectosAdjudicadosXRubroResolver extends ReporteProyectoResolver implements Resolver {
	
	@Override
	public void setInitialContextImpl(HttpServletRequest request) {
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {

		proyecto.add(Expression.isNotNull("fechaResolucion"));
		
		proyecto.addOrder(Order.desc("codigo"));
	}

	/**
	 * Por cada <code>Proyecto</code> de la collection obtendia
	 * por los criterias se aplica un transformer.<br>
	 * Este transformer obtiene los <code>Presupuestos</code>
	 * desglosados por <code>Rubro</code> padre.
	 */
	private class ProyectoPresupuestoXRubroTransformer implements Transformer {
		public Object transform(Object object) {

			if (object instanceof ProyectoBean) {
				ProyectoRaizBean proyecto = (ProyectoRaizBean) object;
				
				ProyectoAdjudicadoXRubroDTO adjudicadoXRubroDTO = new ProyectoAdjudicadoXRubroDTO(proyecto);
				
				return adjudicadoXRubroDTO;
			}

			return object;
		}
	}

	@Override
	public Collection result(HttpServletRequest request) {
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		
		Collection proyectos = new ArrayList();
		CollectionUtils.addAll(proyectos, result.getList().iterator());
		CollectionUtils.transform(proyectos,new ProyectoPresupuestoXRubroTransformer());
		
		return proyectos;
	}
}

	
