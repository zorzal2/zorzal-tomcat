package com.fontar.web.form.instrumento.ventanilla;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.dto.DistribucionFinanciamientoDTO;

@SuppressWarnings("serial")
public class DistribucionFinanciamientoForm extends ActionForm {

	private List distribucionFinanciamientos = new ArrayList();

	private String windowClose;

	private String tipo;

	private String asignacion;

	private String montoTotal;

	public DistribucionFinanciamientoDTO getDistribucionFinanciamiento(int index) {
		/*
		 * HS 12.09.2004 as long as the arraylist is to short, add entries
		 * (solution for struts design problem) import logic:iterator or
		 * nested:iterator has to use apple as index !! or you will easily face
		 * a arrayindexoutufbounds error
		 */
		while (distribucionFinanciamientos.size() <= index) {
			distribucionFinanciamientos.add(new DistribucionFinanciamientoDTO());
		}
		return (DistribucionFinanciamientoDTO) distribucionFinanciamientos.get(index);
	}

	public List getDistribucionFinanciamientos() {
		return distribucionFinanciamientos;
	}

	public void setDistribucionFinanciamientos(List distribucionFinanciamientos) {
		this.distribucionFinanciamientos = distribucionFinanciamientos;
	}

	public void setDistribucionFinanciamiento(int index, DistribucionFinanciamientoDTO name) {
		this.distribucionFinanciamientos.set(index, name);
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// prepopulate the list with empty strings
		distribucionFinanciamientos = new ArrayList();
	}

	public String getWindowClose() {
		return windowClose;
	}

	public void setWindowClose(String windowClose) {
		this.windowClose = windowClose;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public String getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}

}
