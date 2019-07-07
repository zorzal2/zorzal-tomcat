package com.fontar.proyecto.presupuesto.excel.parser.presupuesto;

import com.fontar.proyecto.presupuesto.excel.parser.flujo.modelo.Flujo;
import com.fontar.proyecto.presupuesto.excel.parser.parsers.RubroParsers;
import com.fontar.proyecto.presupuesto.excel.parser.plan.PlanParser;
import com.pragma.excel.parser.util.Parser;

public class PresupuestoParserSettings {
	private boolean tieneFlujo = false;
	private boolean flujoEsOpcional = false;
	private Parser<Flujo> flujoParser = null;

	private boolean tienePlan = false;
	private boolean planEsOpcional = false;
	private PlanParser planParser = null;
	
	private RubroParsers rubroParserSet;

	public boolean flujoEsOpcional() {
		return flujoEsOpcional;
	}
	public void flujoEsOpcional(boolean flujoEsOpcional) {
		this.flujoEsOpcional = flujoEsOpcional;
	}
	public Parser<Flujo> getFlujoParser() {
		return flujoParser;
	}
	public void setFlujoParser(Parser<Flujo> flujoParser) {
		this.flujoParser = flujoParser;
	}
	public boolean planEsOpcional() {
		return planEsOpcional;
	}
	public void planEsOpcional(boolean planEsOpcional) {
		this.planEsOpcional = planEsOpcional;
	}
	public PlanParser getPlanParser() {
		return planParser;
	}
	public void setPlanParser(PlanParser planParser) {
		this.planParser = planParser;
	}
	public boolean tieneFlujo() {
		return tieneFlujo;
	}
	public void tieneFlujo(boolean tieneFlujo) {
		this.tieneFlujo = tieneFlujo;
	}
	public boolean tienePlan() {
		return tienePlan;
	}
	public void tienePlan(boolean tienePlan) {
		this.tienePlan = tienePlan;
	}
	public RubroParsers getRubroParserSet() {
		return rubroParserSet;
	}
	public void setRubroParserSet(RubroParsers rubroParserSet) {
		this.rubroParserSet = rubroParserSet;
	}
}
