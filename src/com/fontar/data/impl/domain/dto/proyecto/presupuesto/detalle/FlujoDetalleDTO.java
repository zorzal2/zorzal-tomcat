package com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroGeneralDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.flujo.FlujoDTO;
import com.fontar.util.ResourceManager;
import com.pragma.util.StringUtil;

public class FlujoDetalleDTO {
	private List<String> periodos = null;

	private Collection<BloqueFlujoDetalleDTO> flujoParte = null;
	private BloqueFlujoDetalleDTO totalesParte;
	
	private Collection<BloqueFlujoDetalleDTO> flujoContraparte = null;
	private BloqueFlujoDetalleDTO totalesContraparte;
	
	private Collection<BloqueFlujoDetalleDTO> flujoTotal = null;
	private BloqueFlujoDetalleDTO totales;
	
	public static FlujoDetalleDTO build(ProyectoPresupuestoDTO proyectoPresupuestoDTO) {
		return fillFlujoData(proyectoPresupuestoDTO, new FlujoDetalleDTO());		
	}
	
	private FlujoDetalleDTO() {}
	
	private static FlujoDetalleDTO fillFlujoData(ProyectoPresupuestoDTO proyectoPresupuestoDTO, FlujoDetalleDTO flujoDetalleDTO) {
			
		flujoDetalleDTO.flujoParte = newBloqueFlujoDetalleDTOCollection();
		flujoDetalleDTO.totalesParte = new BloqueFlujoDetalleDTO(ResourceManager.getTitleResource("app.title.totales"), 0);
		double[] totalesParteTemp = new double[12];
		flujoDetalleDTO.flujoContraparte = newBloqueFlujoDetalleDTOCollection();
		flujoDetalleDTO.totalesContraparte = new BloqueFlujoDetalleDTO(ResourceManager.getTitleResource("app.title.totales"), 0);
		double[] totalesContraparteTemp = new double[12];

		flujoDetalleDTO.flujoTotal = newBloqueFlujoDetalleDTOCollection();
		flujoDetalleDTO.totales = new BloqueFlujoDetalleDTO(ResourceManager.getTitleResource("app.title.totales"), 0);
		double[] totalesTemp = new double[12];

		int tempIndex = 0;
		List<String> tmpPeriodos = new ArrayList<String>();
		for (PresupuestoRubroDTO presupuestoRubro : getPresupuestoRubrosRaiz(proyectoPresupuestoDTO)) {
			PresupuestoRubroGeneralDTO presupuestoRubroGeneral;
			try {
				presupuestoRubroGeneral = PresupuestoRubroGeneralDTO.class.cast(presupuestoRubro);
				
				if(presupuestoRubroGeneral.getFlujoDeFondos()==null) {
					return null;
				}
			} catch(ClassCastException exception) {continue;}
			
			//Parte
			BloqueFlujoDetalleDTO bloqueFlujoParte = new BloqueFlujoDetalleDTO(presupuestoRubro.getRubro().getNombre(), presupuestoRubro.getRubro().getNroOrden().intValue());
			flujoDetalleDTO.flujoParte.add(bloqueFlujoParte);
			//Contraparte
			BloqueFlujoDetalleDTO bloqueFlujoContraparte = new BloqueFlujoDetalleDTO(presupuestoRubro.getRubro().getNombre(), presupuestoRubro.getRubro().getNroOrden().intValue());
			flujoDetalleDTO.flujoContraparte.add(bloqueFlujoContraparte);
			//Total
			BloqueFlujoDetalleDTO bloqueFlujoTotal = new BloqueFlujoDetalleDTO(presupuestoRubro.getRubro().getNombre(), presupuestoRubro.getRubro().getNroOrden().intValue());
			flujoDetalleDTO.flujoTotal.add(bloqueFlujoTotal);
			
			tempIndex = 0;
			for (FlujoDTO flujo : presupuestoRubroGeneral.getFlujoDeFondos()) {
				if(flujoDetalleDTO.periodos==null) tmpPeriodos.add(flujo.getPeriodo());
				//Parte
				bloqueFlujoParte.addValor(StringUtil.formatMoneyForPresentation(flujo.getParte()));
				totalesParteTemp[tempIndex]+=flujo.getParte();
				//Contraparte
				bloqueFlujoContraparte.addValor(StringUtil.formatMoneyForPresentation(flujo.getContraparte()));
				totalesContraparteTemp[tempIndex]+=flujo.getContraparte();
				//Total
				bloqueFlujoTotal.addValor(StringUtil.formatMoneyForPresentation(flujo.getContraparte()+flujo.getParte()));
				totalesTemp[tempIndex]+=flujo.getContraparte() + flujo.getParte();
				
				tempIndex++;
			}
			if(flujoDetalleDTO.periodos==null) flujoDetalleDTO.periodos = tmpPeriodos;
		}
		for(int i=0; i<tempIndex; i++) {
			flujoDetalleDTO.totalesParte.addValor(StringUtil.formatMoneyForPresentation(totalesParteTemp[i]));
			flujoDetalleDTO.totalesContraparte.addValor(StringUtil.formatMoneyForPresentation(totalesContraparteTemp[i]));
			flujoDetalleDTO.totales.addValor(StringUtil.formatMoneyForPresentation(totalesTemp[i]));
		}
		return flujoDetalleDTO;
	}
	/*public Collection<PresupuestoRubroDetalleDTO> getPresupuestoRubrosRaiz(ProyectoPresupuestoDTO proyectoPresupuestoDTO) {
		Collection<PresupuestoRubroDetalleDTO> ret = new ArrayList<PresupuestoRubroDetalleDTO>();
		for (PresupuestoRubroDTO presupuestoRubroDTO : getBasePresupuestoRubrosRaiz(proyectoPresupuestoDTO)) {
			ret.add(new PresupuestoRubroDetalleDTO(presupuestoRubroDTO, this));
		}
		return ret;
	}*/
	public static Collection<PresupuestoRubroDTO> getPresupuestoRubrosRaiz(ProyectoPresupuestoDTO proyectoPresupuestoDTO) {
		
		Collection<PresupuestoRubroDTO> ret = new ArrayList<PresupuestoRubroDTO>();
		Collection<PresupuestoRubroDTO> all;
		if(proyectoPresupuestoDTO!=null && (all = proyectoPresupuestoDTO.getPresupuestoRubros())!=null) {
			for (PresupuestoRubroDTO presupuestoRubroDTO : all) {
				if(presupuestoRubroDTO.getRubro().esRaiz()) ret.add(presupuestoRubroDTO);
			}
		}
		return ret;
	}

	public Collection<BloqueFlujoDetalleDTO> getFlujoContraparte() {
		return flujoContraparte;
	}

	public Collection<BloqueFlujoDetalleDTO> getFlujoParte() {
		return flujoParte;
	}

	public Collection<BloqueFlujoDetalleDTO> getFlujoTotal() {
		return flujoTotal;
	}

	public List<String> getPeriodos() {
		return periodos;
	}

	public BloqueFlujoDetalleDTO getTotales() {
		return totales;
	}

	public BloqueFlujoDetalleDTO getTotalesContraparte() {
		return totalesContraparte;
	}

	public BloqueFlujoDetalleDTO getTotalesParte() {
		return totalesParte;
	}
	private static Collection<BloqueFlujoDetalleDTO> newBloqueFlujoDetalleDTOCollection() {
		Comparator<BloqueFlujoDetalleDTO> comparator = new Comparator<BloqueFlujoDetalleDTO>() {
			public int compare(BloqueFlujoDetalleDTO o1, BloqueFlujoDetalleDTO o2) {
				return o1.getOrder()-o2.getOrder();
			}
		};
		return new TreeSet<BloqueFlujoDetalleDTO>(comparator);
	} 
}
