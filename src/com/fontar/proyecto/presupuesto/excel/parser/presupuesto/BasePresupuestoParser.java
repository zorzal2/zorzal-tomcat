package com.fontar.proyecto.presupuesto.excel.parser.presupuesto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;

import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanImp;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo.Flujo;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.util.ContextUtil;

public abstract class BasePresupuestoParser implements PresupuestoParser {
	
	private PresupuestoParserSettings settings = null;
	
	protected abstract PresupuestoParserSettings createSettings();

	public ProyectoPresupuestoBean parse(Workbook workbook) throws ParsingException {
		WorkbookCursor cursor = new WorkbookCursor(workbook);
		
		//Excepcion para acumular errores
		ParsingException parsingException = new ParsingException();
		
		//Parseo los rubros
		Map<RubroBean, PresupuestoRubroGeneralBean> presupuestoRubros;
		try {
			presupuestoRubros = parseRubros(cursor);
		} catch(ParsingException e) {
			presupuestoRubros = null;
			//Guardo todos los mensajes de error y continuo
			parsingException.appendMessages(e.getMessages());
		}

		//Parseo los flujos si corresponde
		cursor.reset();
		
		Flujo flujo;
		try {
			flujo = parseFlujo(cursor);
		} catch(ParsingException e) {
			flujo = null;
			//Guardo los errores y continuo
			parsingException.appendMessages(e.getMessages());
		}
		
		//Busco el plan, si corresponde
		cursor.reset();
		
		Set<EtapaBean> plan;
		try {
			plan = parsePlan(cursor);
		} catch(ParsingException e) {
			plan = null;
			//Guardo los errores y continuo
			parsingException.appendMessages(e.getMessages());
		}
		
		if(parsingException.hasMessages()) throw parsingException;

		//Rubros involucrados. Solo los que tienen definido un flujo o un presupuesto
		Collection<RubroBean> rubros = new HashSet<RubroBean>();
		rubros.addAll(presupuestoRubros.keySet());
		if(flujo!=null)rubros.addAll(flujo.getRubros());
		/*
		 * Por cada rubro presente como una linea de flujo de fondos o como
		 * una lista de items quiero:
		 *  - Que haya un PresupuestoRubro asociado con montos consistentes
		 *  - Que si es raiz y se definio un flujo, asignarselo.
		 *    Si es raiz y se definio un flujo pero no incluye a ese rubro se
		 *    considera un error en el flujo.
		 *  - Caluclar la suma total de la parte y la contraparte para dejarlas
		 *    en el presupuesto. 
		 */
		Set<PresupuestoRubroGeneralBean> presupuestoRubrosSet = new HashSet<PresupuestoRubroGeneralBean>();
		double total = 0.0, parte = 0.0;
		for (RubroBean rubro : rubros) {
			PresupuestoRubroGeneralBean presupuestoRubro = presupuestoRubros.get(rubro);
			if(presupuestoRubro==null) {
				presupuestoRubro = emptyPresupuestoRubro(rubro);
			}
			
			if(rubro.esRaiz() && flujo!=null) {
				//Debe haber un flujo asociado a este rubro
				List<FlujoBean> flujoPorRubro = flujo.buildBean(presupuestoRubro);
				if(flujoPorRubro==null) 
					throw new ValidationException(PresupuestoMessages.flujo.faltanFlujos()); 
				presupuestoRubro.setFlujoDeFondos(flujoPorRubro);
			}
			total+=presupuestoRubro.getMontoContraparte()+presupuestoRubro.getMontoParte();
			parte+=presupuestoRubro.getMontoParte();
			
			presupuestoRubrosSet.add(presupuestoRubro);
		}
		//clausuro: Si un rubro esta en el conjunto y tiene padre, 
		//el padre tiene que estar.
		presupuestoRubrosSet = clausuraPorPaternidad(presupuestoRubrosSet);
		//propago los montos: si un rubro es padre debe tener montos iguales
		//a las sumas de los montos de sus hijos.
		propagarMontos(presupuestoRubrosSet);
		
		//Armo el presupuesto
		ProyectoPresupuestoBean ret = new ProyectoPresupuestoBean();
		PresupuestoRubroCollectionBean presupuestoRubroCollectionBean = new PresupuestoRubroCollectionBeanImp();
		presupuestoRubroCollectionBean.addAll(presupuestoRubrosSet);
		ret.setPresupuestoRubros(presupuestoRubroCollectionBean);
		ret.setMontoSolicitado(new BigDecimal(parte));
		ret.setMontoTotal(new BigDecimal(total));
		ret.setEtapas(plan);
		
		validate(ret);
		
		return ret;
	}

	protected void validate(ProyectoPresupuestoBean presupuesto) throws ValidationException {
		//Chequeo la coherencia entre el flujo de fondos y el detalle por items
		ValidationException exception = new ValidationException();
		for(PresupuestoRubroBean rubro : presupuesto.getPresupuestoRubros()) {
			PresupuestoRubroGeneralBean rubroGeneral = (PresupuestoRubroGeneralBean) rubro;
			List<FlujoBean> flujoDeFondos = rubroGeneral.getFlujoDeFondos();
			if(flujoDeFondos!=null) {
				double parte = 0.0;
				double contraparte = 0.0;
				double total = 0.0;
				for (FlujoBean flujo : flujoDeFondos) {
					parte+=flujo.getParte().doubleValue();
					contraparte+=flujo.getContraparte().doubleValue();
					total+=flujo.getTotal().doubleValue();
				}
				if(
						!ParseUtils.similar(rubro.getMontoParte(), parte) ||
						!ParseUtils.similar(rubro.getMontoContraparte(), contraparte) ||
						!ParseUtils.similar(rubro.getMontoTotal(), total)
						
				) {
					//Guardo el error y continuo
					exception.appendMessage(PresupuestoMessages.global.flujoNoCoincideConDetalleDelRubro(rubro.getRubro()));
				}
			}
		}
		if(exception.hasMessages()) throw exception;
	}

	private Set<EtapaBean> parsePlan(WorkbookCursor cursor) throws ParsingException {
		Set<EtapaBean> plan = null;
		if(getSettings().tienePlan()) {
			//desde el principio del cuaderno
			plan = getSettings().getPlanParser().parse(cursor);
			if(plan==null && !getSettings().planEsOpcional())
				throw new ParsingException(PresupuestoMessages.plan.planInvalido());
		}
		if(plan==null) plan = new HashSet<EtapaBean>();
		return plan;
	}

	private Flujo parseFlujo(WorkbookCursor cursor) throws ParsingException, ValidationException {
		Flujo flujo = null;
		if(getSettings().tieneFlujo()) {
			flujo = getSettings().getFlujoParser().parse(cursor);
			if(flujo==null){
				if(!getSettings().flujoEsOpcional())
					throw new ValidationException(PresupuestoMessages.flujo.faltanFlujos());
			}
		}
		return flujo;
	}

	protected Set<PresupuestoRubroGeneralBean> clausuraPorPaternidad(Set<PresupuestoRubroGeneralBean> set) {
		//caso base. nada que hacer
		if(set.size()==0) return set;
		
		Set<PresupuestoRubroGeneralBean> ret = new LinkedHashSet<PresupuestoRubroGeneralBean>(set);
		Set<PresupuestoRubroGeneralBean> temp = new LinkedHashSet<PresupuestoRubroGeneralBean>();
		for (PresupuestoRubroGeneralBean bean : ret) {
			//Si no tiene padre lo ignoro
			if(bean.getRubro().esRaiz()) continue;
			//Busco el padre
			RubroBean padre = bean.getRubro().getRubroPadre();
			boolean parentFound = false;
			for (PresupuestoRubroGeneralBean bean2 : ret) {
				if(bean2.getRubro().getId()==padre.getId()) {
					parentFound = true;
					break;
				}
			}
			//si no encontre el padre, puede estar en los temporales
			if(!parentFound) {
				for (PresupuestoRubroGeneralBean bean2 : temp) {
					if(bean2.getRubro().getId()==padre.getId()) {
						parentFound = true;
						break;
					}
				}
			}
			//Definitivamente no esta. lo agrego
			if(!parentFound) {
				temp.add(emptyPresupuestoRubro(padre));
			}
		}
		//clausuro los nuevos
		temp = clausuraPorPaternidad(temp);
		ret.addAll(temp);
		return ret;
	}

	private PresupuestoRubroGeneralBean emptyPresupuestoRubro(RubroBean rubro) {
		PresupuestoRubroGeneralBean presupuestoRubro = new PresupuestoRubroGeneralBean();
		presupuestoRubro.setRubro(rubro);
		presupuestoRubro.setMontoContraparte(0.0);
		presupuestoRubro.setMontoParte(0.0);
		return presupuestoRubro;
	}

	protected void propagarMontos(Set<PresupuestoRubroGeneralBean> set) {
		Set<PresupuestoRubroGeneralBean> pendientes = new LinkedHashSet<PresupuestoRubroGeneralBean>(set);
		while(!pendientes.isEmpty()) {
			Set<PresupuestoRubroGeneralBean> nuevosPendientes = new LinkedHashSet<PresupuestoRubroGeneralBean>(pendientes);
			for (PresupuestoRubroGeneralBean bean : pendientes) {
				if(!bean.getRubro().esRaiz()) {
					//actualizo el padre:
					RubroBean padre = bean.getRubro().getRubroPadre();
					for (PresupuestoRubroGeneralBean bean2 : set) {
						if(bean2.getRubro().getId()==padre.getId()) {
							//Le sumo al padre mi monto...
							bean2.setMontoParte(bean2.getMontoParte()+bean.getMontoParte());
							bean2.setMontoContraparte(bean2.getMontoContraparte()+bean.getMontoContraparte());
							//y lo paso a pendiente
							nuevosPendientes.add(bean2);
							break;
						}
					}
				}
				//Sale de pendiente porque ya actualizo al padre
				nuevosPendientes.remove(bean);
			}
			pendientes = nuevosPendientes;
		}
	}
	
	protected Map<RubroBean, PresupuestoRubroGeneralBean> parseRubros(WorkbookCursor cursor) throws ParsingException {
		Map<RubroBean, PresupuestoRubroGeneralBean> presupuestoRubros = new LinkedHashMap<RubroBean, PresupuestoRubroGeneralBean>();
		RubroDAO dao = (RubroDAO)ContextUtil.getBean("rubroDao");
		List<RubroBean> rubros = dao.getAll();
		ParsingException exception = new ParsingException();
		for (RubroBean rubro : rubros) {
			RubroParser parser = getSettings().getRubroParserSet().getFor(rubro);
			if(parser!=null) {
				//Eliminar esta linea si importara el orden
				cursor.saveCheckpoint();
				try {
					PresupuestoRubroGeneralBean rubroData = parser.parse(cursor);
					if(rubroData!=null)presupuestoRubros.put(rubro, rubroData);
				} catch(ParsingException e) {
					//Hubo una excepcion. Guardar el error y continuar.
					exception.appendMessages(e.getMessages());
				}
				//Eliminar esta linea si importara el orden
				cursor.restoreToCheckpoint();
			}
		}
		if(exception.hasMessages()) throw exception;
		return presupuestoRubros;
	}

	protected PresupuestoParserSettings getSettings() {
		if(settings==null) settings = createSettings();
		return settings;
	}
}
