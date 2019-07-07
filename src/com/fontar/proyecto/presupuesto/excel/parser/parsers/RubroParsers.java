package com.fontar.proyecto.presupuesto.excel.parser.parsers;

import java.util.Hashtable;
import java.util.Map;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.codes.rubro.TipoRubro;

public abstract class RubroParsers {
	private Map<TipoRubro, Class<? extends RubroParser>> parsers;
	protected abstract void addParsers();
	
	protected RubroParsers() {
		parsers = new Hashtable<TipoRubro, Class<? extends RubroParser>>();
		addParsers();
	}
	
	protected void addParser(TipoRubro tipoRubro, Class<? extends RubroParser> class1) {
		parsers.put(tipoRubro, class1);
	}

	public RubroParser getFor(RubroBean rubro) {
		Class[] constructorParam = {RubroBean.class};
		Object[] param = {rubro};
		Class<? extends RubroParser> parserClass = parsers.get(rubro.getTipo());
		if(parserClass==null) return null;
		try {
			return parserClass.getConstructor(constructorParam).newInstance(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("No se pudo crear la instancia");
		}
	}
}
