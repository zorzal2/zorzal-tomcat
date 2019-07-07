package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBean;
import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.xml.XMLUtils;
import com.pragma.xml.writer.Writer;
import com.pragma.xml.writer.WriterLibrary;

public class PresupuestoRubroGeneralWriter implements Writer {

	public Element writeOn(Node node, Object presupuestoRubroGeneral, WriterLibrary library) {
		PresupuestoRubroGeneralBean presupuestoRubro = (PresupuestoRubroGeneralBean) presupuestoRubroGeneral;

		Element presupuestoRubroNode = XMLUtils.appendChild(node, Names.presupuestoRubroGeneral);
		//Atributos
		presupuestoRubroNode.setAttribute(Names.rubroName, presupuestoRubro.getRubro().getNombre());
		presupuestoRubroNode.setAttribute(Names.rubroId, presupuestoRubro.getRubro().getId().toString());
		presupuestoRubroNode.setAttribute(Names.parte, presupuestoRubro.getMontoParte().toString());
		presupuestoRubroNode.setAttribute(Names.contraparte, presupuestoRubro.getMontoContraparte().toString());

		
		//Items
		List<PresupuestoItemBean> presupuestoItems = presupuestoRubro.getItems();
		if(presupuestoItems!=null) {
			Element presupuestoItemsNode = XMLUtils.appendChild(presupuestoRubroNode, Names.presupuestoItems);
			library.oneCanHandle(presupuestoItems).writeOn(presupuestoItemsNode, presupuestoItems, library);
		}

		//Flujo
		List<FlujoBean> presupuestoFlujos = presupuestoRubro.getFlujoDeFondos();
		if(presupuestoFlujos!=null) {
			Element presupuestoFlujoNode = XMLUtils.appendChild(presupuestoRubroNode, Names.presupuestoFlujos);
			library.oneCanHandle(presupuestoFlujos).writeOn(presupuestoFlujoNode, presupuestoFlujos, library);
		}
		
		
		return presupuestoRubroNode;
	}

}
