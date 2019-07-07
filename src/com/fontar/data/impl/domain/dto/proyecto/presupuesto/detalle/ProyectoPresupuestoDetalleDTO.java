package com.fontar.data.impl.domain.dto.proyecto.presupuesto.detalle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan.EtapaDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.PresupuestoRubroDTO;
import com.pragma.util.StringUtil;

public class ProyectoPresupuestoDetalleDTO {

	private static final long serialVersionUID = 1L;
	
	private final ProyectoPresupuestoDTO decoratedObject;
	private String location;
	private FlujoDetalleDTO flujo;
	
	public ProyectoPresupuestoDetalleDTO(ProyectoPresupuestoDTO decoratedObject, String location) {
		this.decoratedObject = decoratedObject;
		this.location = location;
		this.flujo = FlujoDetalleDTO.build(decoratedObject);
	}

	public ProyectoPresupuestoDTO getDecoratedObject() {
		return decoratedObject;
	}

	public Set<EtapaDTO> getEtapas() {
		if(decoratedObject==null) return new HashSet<EtapaDTO>();
		return decoratedObject.getEtapas();
	}

	public String getFundamentacion() {
		if(decoratedObject==null) return "";
		return decoratedObject.getFundamentacion();
	}

	public Long getId() {
		if(decoratedObject==null) return null;
		return decoratedObject.getId();
	}

	public String getMontoParte() {
		if(decoratedObject==null) return "";
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoSolicitado());
	}
	
	public String getMontoContraparte() {
		if(decoratedObject==null) return "";
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoTotal().doubleValue()-decoratedObject.getMontoSolicitado().doubleValue());
	}

	public String getTotal() {
		if(decoratedObject==null) return "";
		return StringUtil.formatMoneyForPresentation(decoratedObject.getMontoTotal());
	}
	public String getPorcentajeParte() {
		if(decoratedObject==null) return "";
		double x = decoratedObject.getMontoSolicitado().doubleValue()/decoratedObject.getMontoTotal().doubleValue();
		return StringUtil.formatTwoDecimalForPresentation(100.0*x)+"%";
	}
	
	public String getPorcentajeContraparte() {
		if(decoratedObject==null) return "";
		double x = (decoratedObject.getMontoTotal().doubleValue()-decoratedObject.getMontoSolicitado().doubleValue())/decoratedObject.getMontoTotal().doubleValue();
		return StringUtil.formatTwoDecimalForPresentation(100.0*x)+"%";
	}

	public String getPorcentajeTotal() {
		if(decoratedObject==null) return "";
		return "100,00%";
	}

	public Collection<PresupuestoRubroDTO> getPresupuestoRubros() {
		if(decoratedObject==null) return new ArrayList<PresupuestoRubroDTO>();
		return sort(decoratedObject.getPresupuestoRubros());
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public FlujoDetalleDTO getFlujo() {
		return flujo;
	}

	public Collection<PresupuestoRubroDetalleDTO> getPresupuestoRubrosRaiz() {
		Collection<PresupuestoRubroDetalleDTO> ret = new ArrayList<PresupuestoRubroDetalleDTO>();
		for (PresupuestoRubroDTO presupuestoRubroDTO : getBasePresupuestoRubrosRaiz(decoratedObject)) {
			ret.add(new PresupuestoRubroDetalleDTO(presupuestoRubroDTO, this));
		}
		return sortDetalle(ret);
	}
	public static Collection<PresupuestoRubroDTO> getBasePresupuestoRubrosRaiz(ProyectoPresupuestoDTO proyectoPresupuestoDTO) {
		
		Collection<PresupuestoRubroDTO> ret = new ArrayList<PresupuestoRubroDTO>();
		Collection<PresupuestoRubroDTO> all;
		if(proyectoPresupuestoDTO!=null && (all = proyectoPresupuestoDTO.getPresupuestoRubros())!=null) {
			for (PresupuestoRubroDTO presupuestoRubroDTO : all) {
				if(presupuestoRubroDTO.getRubro().esRaiz()) ret.add(presupuestoRubroDTO);
			}
		}
		return ret;
	}

	private Collection<PresupuestoRubroDetalleDTO> sortDetalle(Collection<PresupuestoRubroDetalleDTO> rubros) {
		Comparator<PresupuestoRubroDetalleDTO> comparator = new Comparator<PresupuestoRubroDetalleDTO>() {
			public int compare(PresupuestoRubroDetalleDTO o1, PresupuestoRubroDetalleDTO o2) {
				return o1.getRubro().getNroOrden().intValue()-o2.getRubro().getNroOrden().intValue();
			}
		};
		
		TreeSet<PresupuestoRubroDetalleDTO> set = new TreeSet<PresupuestoRubroDetalleDTO>(comparator);
		set.addAll(rubros);
		return set;
	}
	private Collection<PresupuestoRubroDTO> sort(Collection<PresupuestoRubroDTO> rubros) {
		Comparator<PresupuestoRubroDTO> comparator = new Comparator<PresupuestoRubroDTO>() {
			public int compare(PresupuestoRubroDTO o1, PresupuestoRubroDTO o2) {
				return o1.getRubro().getNroOrden().intValue()-o2.getRubro().getNroOrden().intValue();
			}
		};
		
		TreeSet<PresupuestoRubroDTO> set = new TreeSet<PresupuestoRubroDTO>(comparator);
		set.addAll(rubros);
		return set;
	}
}