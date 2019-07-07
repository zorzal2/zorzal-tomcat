package com.fontar.proyecto.presupuesto.excel.parser.flujo.anr;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.pragma.excel.parser.util.FieldMatching;
import com.pragma.excel.parser.util.Label;

public class ItemFlujoConfigANR {
	public final int PERIODOS = 12;
	
	private FieldMatching matching;
	
	private Map<RubroBean, Label> rubroLabel = new LinkedHashMap<RubroBean, Label>();
	public Label getLabelDeRubro(RubroBean rubro) {
		Label label = rubroLabel.get(rubro);
		if(label==null) {
			//creo la etiquieta
			String[] split = rubro.getCodigoLargo().split("\\|");
			label = new Label(split);
			rubroLabel.put(rubro, label);
		}
		return label;
	}
	public FieldMatching getMatching() {
		return matching;
	}
	public void setMatching(FieldMatching matching) {
		this.matching = matching;
	}
	private Label[] labelsDePeriodos;
	public Label[] LabelsDePeriodos() {
		if(labelsDePeriodos==null) {
			labelsDePeriodos = new Label[PERIODOS];
			for(int i=1; i<=PERIODOS; i++) {
				labelsDePeriodos[i-1] = new Label(Integer.toString(i), "per?odo "+Integer.toString(i));
			}
		}
		return labelsDePeriodos;
	}
}
