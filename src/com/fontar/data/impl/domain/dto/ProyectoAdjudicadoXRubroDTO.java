package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;

/**
 * DTO que contiene datos básicos de <code>Proyecto</code>
 * y los totales desagregados por <code>Rubro</code>.<br>
 * @author ssanchez
 */
public class ProyectoAdjudicadoXRubroDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String id;
	private String instrumento;
	private String proyecto;
	private String entidadBeneficiaria;
	private String titulo;
	private String jurisdiccion;
	private BigDecimal montoParte;
	private BigDecimal montoContraparte;
	private String ciiu;
	private BigDecimal montoBienesCapital;
	private BigDecimal montoRrhh;
	private BigDecimal montoMaterialesInsumos;
	private BigDecimal montoConsultoriaServicio;
	private BigDecimal montoOtros;
	
	/**
	 * Crea el Dto con los datos de proyecto.
	 * Desglosa los presupuestos por rubro.
	 * @param proyecto
	 */
	public ProyectoAdjudicadoXRubroDTO(ProyectoRaizBean proyecto) {

		rellenarConBlancos();

		RendicionCuentasAnalisisGastosCuadroDTO presupuestoRubros = obtenerPresupuestoDesagregadoXRubro(proyecto);
		
		this.id = proyecto.getId().toString();
		this.instrumento = proyecto.getInstrumento()!=null ? proyecto.getInstrumento().getIdentificador():"";
		this.proyecto = proyecto.getCodigo();
		this.entidadBeneficiaria = proyecto.getProyectoDatos().getEntidadBeneficiaria()!=null ? proyecto.getProyectoDatos().getEntidadBeneficiaria().getDenominacion():"";
		this.titulo = proyecto.getProyectoDatos().getTitulo();
		this.jurisdiccion = proyecto.getProyectoJurisdiccion()!=null ? proyecto.getProyectoJurisdiccion().getJurisdiccion().getDescripcion():"";
		this.ciiu = proyecto.getProyectoDatos().getCiiu()!=null ? proyecto.getProyectoDatos().getCiiu().getCodigo():"";
		
		ProyectoPresupuestoBean proyectoPresupuesto = proyecto.getProyectoPresupuesto();
		if (proyectoPresupuesto == null) {
			proyectoPresupuesto = proyecto.getProyectoPresupuestoOriginal();
		} 
		if (proyectoPresupuesto != null) {
			this.montoParte = proyectoPresupuesto.getMontoTotal();
			this.montoContraparte = proyectoPresupuesto.getMontoSolicitado();
		} 
		
		if (presupuestoRubros.getCuadro() != null) {
			for(RendicionCuentasResumenRubroDTO resumenRubro: presupuestoRubros.getCuadro()) {
				switch (Integer.valueOf(resumenRubro.getNroOrden().toString())) {
					case 1:
						this.montoBienesCapital = resumenRubro.getCostoTotal();
						break;
					case 5:
						this.montoRrhh = resumenRubro.getCostoTotal();
						break;
					case 10:
						this.montoConsultoriaServicio = resumenRubro.getCostoTotal();
						break;
					case 13:
						this.montoMaterialesInsumos = resumenRubro.getCostoTotal();						
						break;
					case 14:
						this.montoOtros = resumenRubro.getCostoTotal();
						break;
				}
			}

		}
	}
	
	/**	 
	 *	 Obtiene el presupuesto actual u original 
	 *   del <code>Proyecto</code> según corresponda y con sus datos construye 
	 *   el DTO RendicionCuentasAnalisisGastosCuadroDTO con los totales 
	 *   del presupuesto desagregados por rubros.  
	 */
	private RendicionCuentasAnalisisGastosCuadroDTO obtenerPresupuestoDesagregadoXRubro(ProyectoRaizBean proyecto) {

		ProyectoPresupuestoBean presupuesto = proyecto.getProyectoPresupuesto();
				
		if (presupuesto == null) {
			presupuesto = proyecto.getProyectoPresupuestoOriginal();
		}

		RendicionCuentasAnalisisGastosCuadroDTO cuadroCostosTotales = new RendicionCuentasAnalisisGastosCuadroDTO();
		
		if (presupuesto != null) {
			PresupuestoRubroCollectionBean  costosTotales = presupuesto.getPresupuestoRubros();
			
			if (costosTotales != null) {
				RendicionCuentasResumenRubroDTO dto;
				List<RendicionCuentasResumenRubroDTO> listaCostosTotales = new ArrayList<RendicionCuentasResumenRubroDTO>();
				
				for(PresupuestoRubroBean costoRubro : costosTotales) {
					
					Long idRubroPadre = costoRubro.getRubro().getIdRubroPadre();
					if (idRubroPadre == null) {
						BigDecimal montoParte = BigDecimal.ZERO;
						BigDecimal montoContraparte = BigDecimal.ZERO;			
					
//					if (costoRubro.getRubro().esHoja()) {
						montoParte = new BigDecimal(costoRubro.getMontoParte());
						montoContraparte = new BigDecimal(costoRubro.getMontoContraparte());			
//					}
					
						dto = new RendicionCuentasResumenRubroDTO(costoRubro.getRubro(),montoParte,montoContraparte);
						listaCostosTotales.add(dto);
					}
				}
	
				cuadroCostosTotales.setCuadro(listaCostosTotales);
//				RubroDAO rubroDao = (RubroDAO)ContextUtil.getBean("rubroDao");
//				List<Long> idPadres = rubroDao.findIdsPadres();
//				cuadroCostosTotales = new RendicionCuentasAnalisisGastosCuadroDTO(listaCostosTotales, idPadres);
			}
		}
		
		return cuadroCostosTotales;
	}		
	
	public String getCiiu() {
		return ciiu;
	}
	public void setCiiu(String ciiu) {
		this.ciiu = ciiu;
	}
	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}
	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public String getProyecto() {
		return proyecto;
	}
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getMontoBienesCapital() {
		return montoBienesCapital;
	}
	public void setMontoBienesCapital(BigDecimal montoBienesCapital) {
		this.montoBienesCapital = montoBienesCapital;
	}
	public BigDecimal getMontoConsultoriaServicio() {
		return montoConsultoriaServicio;
	}
	public void setMontoConsultoriaServicio(BigDecimal montoConsultoriaServicio) {
		this.montoConsultoriaServicio = montoConsultoriaServicio;
	}
	public BigDecimal getMontoContraparte() {
		return montoContraparte;
	}
	public void setMontoContraparte(BigDecimal montoContraparte) {
		this.montoContraparte = montoContraparte;
	}
	public BigDecimal getMontoMaterialesInsumos() {
		return montoMaterialesInsumos;
	}
	public void setMontoMaterialesInsumos(BigDecimal montoMaterialesInsumos) {
		this.montoMaterialesInsumos = montoMaterialesInsumos;
	}
	public BigDecimal getMontoOtros() {
		return montoOtros;
	}
	public void setMontoOtros(BigDecimal montoOtros) {
		this.montoOtros = montoOtros;
	}
	public BigDecimal getMontoParte() {
		return montoParte;
	}
	public void setMontoParte(BigDecimal montoParte) {
		this.montoParte = montoParte;
	}
	public BigDecimal getMontoRrhh() {
		return montoRrhh;
	}
	public void setMontoRrhh(BigDecimal montoRrhh) {
		this.montoRrhh = montoRrhh;
	}
	private void rellenarConBlancos() {
		this.id = "";
		this.instrumento = "";
		this.proyecto = "";
		this.entidadBeneficiaria = "";
		this.titulo = "";
		this.jurisdiccion = "";
		this.montoParte = BigDecimal.ZERO;
		this.montoContraparte = BigDecimal.ZERO;
		this.ciiu = "";
		this.montoBienesCapital = BigDecimal.ZERO;
		this.montoRrhh = BigDecimal.ZERO;
		this.montoMaterialesInsumos = BigDecimal.ZERO;
		this.montoConsultoriaServicio = BigDecimal.ZERO;
		this.montoOtros = BigDecimal.ZERO;	
	}	
}
