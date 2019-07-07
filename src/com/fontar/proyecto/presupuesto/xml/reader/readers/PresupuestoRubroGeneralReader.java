package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.util.ContextUtil;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.reader.Reader;
import com.pragma.xml.reader.ReaderLibrary;

public class PresupuestoRubroGeneralReader implements Reader {

	public Object readFrom(Node node, ReaderLibrary library) {

		Element presupuestoRubroNode = XMLUtils.asElement(node);
		PresupuestoRubroGeneralBean presupuestoRubro = new PresupuestoRubroGeneralBean();
		//Atributos
		RubroDAO rubroDAO = (RubroDAO)ContextUtil.getBean("rubroDao");
		RubroBean rubro = rubroDAO.read(Long.valueOf(presupuestoRubroNode.getAttribute(Names.rubroId)));
		presupuestoRubro.setRubro(rubro);
		
		presupuestoRubro.setMontoParte(Double.valueOf(presupuestoRubroNode.getAttribute(Names.parte)));
		presupuestoRubro.setMontoContraparte(Double.valueOf(presupuestoRubroNode.getAttribute(Names.contraparte)));
		
		//Items
		Element presupuestoItemsNode = XMLUtils.findChild(presupuestoRubroNode, Names.presupuestoItems);
		List<PresupuestoItemBean> presupuestoItems = new ArrayList<PresupuestoItemBean>();
		if(presupuestoItemsNode!=null) {
			XMLUtils.fill(presupuestoItemsNode, presupuestoItems, library);
			presupuestoRubro.setItems(presupuestoItems);
		}
		
		//Flujo
		Element presupuestoFlujoNode = XMLUtils.findChild(presupuestoRubroNode, Names.presupuestoFlujos);
		List<FlujoBean> presupuestoFlujos = new ArrayList<FlujoBean>();
		if(presupuestoFlujoNode!=null) {
			XMLUtils.fill(presupuestoFlujoNode, presupuestoFlujos, library);
			presupuestoRubro.setFlujoDeFondos(presupuestoFlujos);
		}

		
		return presupuestoRubro;
	}


}
