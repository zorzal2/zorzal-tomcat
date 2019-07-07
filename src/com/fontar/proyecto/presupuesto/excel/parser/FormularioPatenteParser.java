package com.fontar.proyecto.presupuesto.excel.parser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jxl.Workbook;

import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanImp;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBienBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorBean;
import com.fontar.proyecto.presupuesto.excel.parser.labels.PatenteLabels;
import com.fontar.proyecto.presupuesto.excel.parser.presupuesto.PresupuestoParser;
import com.fontar.proyecto.presupuesto.message.PresupuestoMessages;
import com.pragma.excel.exception.IllegalFormatException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.excel.exception.ValidationException;
import com.pragma.excel.parser.util.ParseUtils;
import com.pragma.excel.parser.util.cusor.WorkbookCursor;
import com.pragma.excel.parser.util.pattern.ParsingProcess;
import com.pragma.excel.parser.util.pattern.PatternLibrary;
import com.pragma.excel.parser.util.pattern.PatternParser;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;

public class FormularioPatenteParser implements PresupuestoParser {

	public ProyectoPresupuestoBean parse(Workbook workbook) throws ParsingException {
		WorkbookCursor cursor = new WorkbookCursor(workbook);
		cursor.saveCheckpoint();
		
		//Busco los campos
		PatternParser pattern = PatternLibrary.composition.of(
				PatternLibrary.title(PatenteLabels.instance.TOTAL_DEL_PROYECTO),
				PatternLibrary.blank,
				PatternLibrary.title(PatenteLabels.instance.TOTAL_CYS).named("CyS"), 
				PatternLibrary.title(PatenteLabels.instance.TOTAL_OTROS_COSTOS).named("Otros Costos")
		);
		ParsingProcess process = new ParsingProcess(pattern);
		process.find(cursor);
		if(process.getResult()==null) {
			//Formato no valido
			cursor.restoreToCheckpoint();
			throw new IllegalFormatException(PresupuestoMessages.presupuestoRubro.formatoNoValido());
		}
		cursor.clearCheckpoint();

		Double[] cysValues = ParseUtils.findNumbers(process.getNamedRowset("CyS").get(0));
		Double[] otrosCostosValues = ParseUtils.findNumbers(process.getNamedRowset("Otros Costos").get(0));

		validate(cysValues, otrosCostosValues);
		
		//Creo los items y los lleno
		PresupuestoItemConsultorBean itemCyS = new PresupuestoItemConsultorBean();
		itemCyS.setTotal(cysValues[0]);
		itemCyS.setParte(cysValues[1]);
		itemCyS.setContraparte(cysValues[2]);
		PresupuestoRubroGeneralBean prCyS = new PresupuestoRubroGeneralBean();
		RubroBean rubroCyS = getRubro("CONSULTORIA Y SERVICIOS TECNOLOGICOS A CONTRATAR");
		prCyS.setRubro(rubroCyS);
		prCyS.setItems(CollectionUtils.listWith((PresupuestoItemBean)itemCyS));
		prCyS.setMontoContraparte(itemCyS.getContraparte());
		prCyS.setMontoParte(itemCyS.getParte());

		PresupuestoItemBienBean itemOC = new PresupuestoItemBienBean();
		itemOC.setTotal(otrosCostosValues[0]);
		itemOC.setParte(otrosCostosValues[1]);
		itemOC.setContraparte(otrosCostosValues[2]);
		PresupuestoRubroGeneralBean prOC = new PresupuestoRubroGeneralBean();
		prOC.setRubro(getRubro("OTROS COSTOS"));
		prOC.setItems(CollectionUtils.listWith((PresupuestoItemBean)itemOC));
		prOC.setMontoContraparte(itemOC.getContraparte());
		prOC.setMontoParte(itemOC.getParte());
		
		Set<PresupuestoRubroGeneralBean> presupuestoRubros = CollectionUtils.setWith(prOC, prCyS);
		
		//clausuro: Si un rubro esta en el conjunto y tiene padre, 
		//el padre tiene que estar.
		presupuestoRubros = clausuraPorPaternidad(presupuestoRubros);
		
		//propago los montos: si un rubro es padre debe tener montos iguales
		//a las sumas de los montos de sus hijos.
		propagarMontos(presupuestoRubros);
		

		BigDecimal total = new BigDecimal(cysValues[0]+otrosCostosValues[0]);
		BigDecimal parte = new BigDecimal(cysValues[1]+otrosCostosValues[1]);

		//Armo el presupuesto
		ProyectoPresupuestoBean ret = new ProyectoPresupuestoBean();
		PresupuestoRubroCollectionBean presupuestoRubroCollectionBean = new PresupuestoRubroCollectionBeanImp();
		presupuestoRubroCollectionBean.addAll(presupuestoRubros);
		ret.setPresupuestoRubros(presupuestoRubroCollectionBean);
		ret.setMontoSolicitado(parte);
		ret.setMontoTotal(total);
		ret.setEtapas(new HashSet<EtapaBean>());
		
		return ret;
	}

	private void validate(Double[] cysValues, Double[] otrosCostosValues) throws ParsingException {
		if(cysValues.length<3 || otrosCostosValues.length<3)
			throw new ParsingException(PresupuestoMessages.presupuestoRubro.formatoNoValido());
		if(
			cysValues[1]<0 || 
			cysValues[2]<0 || 
			otrosCostosValues[1]<0 ||
			otrosCostosValues[2]<0
			)
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.valoresNegativos());
		if(!ParseUtils.similar(cysValues[0], cysValues[1]+cysValues[2]))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.totalDifiereDePartes());
		if(!ParseUtils.similar(otrosCostosValues[0], otrosCostosValues[1]+otrosCostosValues[2]))
			throw new ValidationException(PresupuestoMessages.presupuestoRubro.totalDifiereDePartes());
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
	
	private List<RubroBean> allRubros = null;
	private RubroBean getRubro(String name) {
		if(allRubros==null) {
			RubroDAO dao = (RubroDAO)ContextUtil.getBean("rubroDao");
			allRubros = dao.getAll();
		}
		for (RubroBean rubro : allRubros) {
			if(rubro.hasCode(name)) return rubro;
		}
		return null;
	}
}
