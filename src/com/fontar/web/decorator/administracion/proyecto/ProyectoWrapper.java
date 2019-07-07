package com.fontar.web.decorator.administracion.proyecto;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.displaytag.exception.DecoratorException;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadIntervinientesDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoAdmisionDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoAdmisionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;
import com.fontar.util.Util;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.AdministrarBitacoraLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.EditarMonto;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.toolbar.web.decorator.ShortDateWrapper;
import com.pragma.util.ContextUtil;
import com.pragma.util.MathUtils;
import com.pragma.util.StringUtil;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina Presentaciones
 * Convocatorias
 */
public class ProyectoWrapper extends BaseEntityWorkFlowWrapper {

	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getLinkBitacora() throws UnsupportedEncodingException {
		ProyectoBean proyecto = (ProyectoBean) this.getCurrentRowObject();
		AdministrarBitacoraLink link = new AdministrarBitacoraLink("BitacoraInventario.do","app.alt.administrarBitacora",proyecto.getId());
		link.setClase("P");
		link.setPermissionsByInstrumentoRequired(proyecto.getIdInstrumento(), "PROYECTOS-ADMINISTRAR-BITACORA");
		return link.displayValue();
	}

	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getLinkEdicion() throws UnsupportedEncodingException {
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		EditarLink editarLink= new EditarLink("ProyectoEditar.do","app.alt.editarProyecto",lObject.getId());
		editarLink.setPermissionsByInstrumentoRequired(lObject.getIdInstrumento(), "PROYECTOS-EDITAR");
		return editarLink.displayValue();
	}

	/**
	 * @author gboaglio
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("ProyectoVisualizar.do","app.alt.visualizarProyecto",lObject.getId());
		visualizarLink.setPermissionsByInstrumentoRequired(lObject.getIdInstrumento(), "PROYECTOS-VISUALIZAR");
		return visualizarLink.displayValue();
	}
	
	public String getLinkAdjuntos() throws UnsupportedEncodingException{
		ProyectoBean lObject= (ProyectoBean)this.getCurrentRowObject();
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),ProyectoBean.class);
		adjuntosLink.setPermissionsByInstrumentoRequired(lObject.getIdInstrumento(), "PROYECTOS-EDITAR");
		return adjuntosLink.displayValue();
	}

	public BigDecimal getMontoDesembolsado() throws UnsupportedEncodingException{
		ProyectoBean lObject= (ProyectoBean)this.getCurrentRowObject();
		Set<SeguimientoBean> segList = lObject.getSeguimientos();
		
		BigDecimal montoParteEvaluacion = BigDecimal.ZERO;
		
		for (SeguimientoBean seguimientoBean : segList) {
			if (seguimientoBean.getEstado().equals(EstadoSeguimiento.AUTORIZADO) 
					||seguimientoBean.getEstado().equals(EstadoSeguimiento.GESTIONADO)) {
				
				Set<RendicionCuentasBean> rendList = seguimientoBean.getRendiciones();
				
				for (RendicionCuentasBean rendicionCuentasBean : rendList) { 
					BigDecimal monto = rendicionCuentasBean.getMontoParteEvaluacion();
					if (monto != null) {
						montoParteEvaluacion = montoParteEvaluacion.add(monto);
					}
				}				
			}
		}
		
		return montoParteEvaluacion;
	}

	public String getMontoADesembolsar() throws UnsupportedEncodingException{
		ProyectoBean lObject= (ProyectoBean)this.getCurrentRowObject();
		
		BigDecimal montoADesembolsar = this.getMontoDesembolsado();
		try {
			BigDecimal valor = BigDecimal.ZERO;
			if (lObject.getMontoBeneficioFONTARAprobado() != null) {
		
				valor = lObject.getMontoBeneficioFONTARAprobado().subtract(montoADesembolsar);
			}
			
			return StringUtil.formatMoneyForPresentation(valor);
		} catch(SecurityException exception) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}

	/**
	 * Devuelve las entidades intervinientes separadas por un separador de linea html "<br>"
	 * para mostrarlas en la consulta de Proyectos Indicando Representante, Director y Empresas.
	 * 
	 */
	public String getEntidadesIntervinientes() throws UnsupportedEncodingException {

		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		
		BitacoraDAO bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");		
		EntidadIntervinientesDAO entidadIntervinientesDao = (EntidadIntervinientesDAO)ContextUtil.getBean("entidadIntervinientesDao");
		EntidadDAO entidadDao = (EntidadDAO)ContextUtil.getBean("entidadDao");
		
		List<BitacoraBean> list = bitacoraDao.findByProyectoTipo(lObject.getId(), TipoBitacora.ENTIDAD_INTERVINIENTE.getName());
				
		StringBuffer link = new StringBuffer();
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				EntidadIntervinientesBean intervinientesBean = (EntidadIntervinientesBean) entidadIntervinientesDao.read(list.get(i).getId());
				if (intervinientesBean.getActivo()) {
					EntidadBean entidadBean = (EntidadBean) entidadDao.read(intervinientesBean.getIdEntidad());
					link.append(entidadBean.getDenominacion()+"\n");
				}
			}
		}

		return link.toString();
	}

	public String getEsVentanilla() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String tipo = "Convocatoria";
		if (lObject.getInstrumento() != null) {
			if (lObject.getInstrumento().esVentanilla()) {
				tipo = "Ventanilla";
			}
		}
		return tipo;
	}
	
	public String getNrAdmision() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		BitacoraDAO bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");		
		ProyectoAdmisionDAO admisionDAO = (ProyectoAdmisionDAO)ContextUtil.getBean("proyectoAdmisionDao");
		List<BitacoraBean> list = bitacoraDao.findByProyectoTipo(lObject.getId(), TipoBitacora.ADMISION.getName());
		String nrAdmision = "";
		Date fechaBitacora = null;
		if (list != null) {
			for (BitacoraBean bean : list) {
				ProyectoAdmisionBean admisionBean = admisionDAO.read(bean.getId());
				if (fechaBitacora == null || (admisionBean.getFecha().after(fechaBitacora))) {
					fechaBitacora = admisionBean.getFecha();
					nrAdmision = admisionBean.getDisposicion();
				}
			}
		}
		
		return nrAdmision;
	}

	public String getFechaNrAdmision() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		BitacoraDAO bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");		
		ProyectoAdmisionDAO admisionDAO = (ProyectoAdmisionDAO)ContextUtil.getBean("proyectoAdmisionDao");
		List<BitacoraBean> list = bitacoraDao.findByProyectoTipo(lObject.getId(), TipoBitacora.ADMISION.getName());
		Date fechaBitacora = null;
		if (list != null) {
			for (BitacoraBean bean : list) {
				ProyectoAdmisionBean admisionBean = admisionDAO.read(bean.getId());
				if (fechaBitacora == null || (admisionBean.getFecha().after(fechaBitacora))) {
					fechaBitacora = admisionBean.getFecha();
				}
			}
		}
		String fecha = "";
		if (fechaBitacora != null) {
			ShortDateWrapper shortDateWrapper = new ShortDateWrapper();
			try {
				fecha = shortDateWrapper.decorate(fechaBitacora);
			}catch (DecoratorException e) {
				throw new RuntimeException(e);
			}
		}
		return fecha;
	}

	public String getResultadoAdmision() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		BitacoraDAO bitacoraDao = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");		
		ProyectoAdmisionDAO admisionDAO = (ProyectoAdmisionDAO)ContextUtil.getBean("proyectoAdmisionDao");
		List<BitacoraBean> list = bitacoraDao.findByProyectoTipo(lObject.getId(), TipoBitacora.ADMISION.getName());
		String resultadoAdmision = "";
		Date fechaBitacora = null;
		if (list != null) {
			for (BitacoraBean bean : list) {
				ProyectoAdmisionBean admisionBean = admisionDAO.read(bean.getId());
				if (fechaBitacora == null || (admisionBean.getFecha().after(fechaBitacora))) {
					fechaBitacora = admisionBean.getFecha();
					resultadoAdmision = admisionBean.getResultado();
				}
			}
		}
		return resultadoAdmision;
	}

	//Se utiliza para la consulta de proyectos TOTAL
	public String getCorrespondeActual() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String fin = "";
		if (lObject.getProyectoPresupuesto() != null) {
			if (lObject.getProyectoPresupuesto().getMontoSolicitado() != null && lObject.getProyectoPresupuesto().getMontoTotal() != null) {
				if (lObject.getProyectoPresupuesto().getMontoTotal().signum() != 0) {
					fin = StringUtil.formatTwoDecimalForPresentation(MathUtils.getPorcentaje(lObject.getProyectoPresupuesto().getMontoSolicitado(),lObject.getProyectoPresupuesto().getMontoTotal()));
				}
			}
		}
		return fin;
	}

	//Se utiliza para la consulta de proyectos TOTAL
	public String getCorrespondeOriginal() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String fin = "";
		if (lObject.getProyectoPresupuestoOriginal() != null) {
			if (lObject.getProyectoPresupuestoOriginal().getMontoSolicitado() != null && lObject.getProyectoPresupuestoOriginal().getMontoTotal() != null) {
				if (lObject.getProyectoPresupuestoOriginal().getMontoTotal().signum() != 0) {
					fin = StringUtil.formatTwoDecimalForPresentation(MathUtils.getPorcentaje(lObject.getProyectoPresupuestoOriginal().getMontoSolicitado(),lObject.getProyectoPresupuestoOriginal().getMontoTotal()));
				}
			}
		}
		return fin;
	}
	
	public String getLe() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String ciiu = "";
		if (lObject.getProyectoDatos().getCiiu() != null) {
			ciiu = lObject.getProyectoDatos().getCiiu().getCodigo();
			ciiu = ciiu.substring(0,1);
		}
		return ciiu;
	}
	
	public String getSd() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String ciiu = "";
		if (lObject.getProyectoDatos().getCiiu() != null) {
			ciiu = lObject.getProyectoDatos().getCiiu().getCodigo();
			ciiu = ciiu.substring(1,3);
		}
		return ciiu;
	}
	
	public String getCd() throws UnsupportedEncodingException {
		
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String ciiu = "";
		if (lObject.getProyectoDatos().getCiiu() != null) {
			ciiu = lObject.getProyectoDatos().getCiiu().getCodigo();
			ciiu = ciiu.substring(1,ciiu.length());
		}
		return ciiu;
	}
	
	public String getLeBeneficiaria() throws UnsupportedEncodingException {
		
		String ciiu = this.getCiiuBeneficiaria();
		if (!Util.isBlank(ciiu)) {
			ciiu = ciiu.substring(0,1);
		}
		return ciiu;
	}
	
	public String getSdBeneficiaria() throws UnsupportedEncodingException {
		
		String ciiu = this.getCiiuBeneficiaria();
		if (!Util.isBlank(ciiu)) {
			ciiu = ciiu.substring(1,3);
		}
		return ciiu;
	}
	
	public String getCdBeneficiaria() throws UnsupportedEncodingException {
		
		String ciiu = this.getCiiuBeneficiaria();
		if (!Util.isBlank(ciiu)) {
			ciiu = ciiu.substring(1,ciiu.length());
		}
		return ciiu;
	}
	
	public String getCiiuBeneficiaria() throws UnsupportedEncodingException {
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String ciiu = "";
		CiiuDAO ciiuDAO = (CiiuDAO)ContextUtil.getBean("ciiuDao");		
		if (lObject.getProyectoDatos().getEntidadBeneficiaria() != null) {
			if (lObject.getProyectoDatos().getEntidadBeneficiaria().getIdCiiu() != null) {
				CiiuBean ciiuBean = ciiuDAO.read(lObject.getProyectoDatos().getEntidadBeneficiaria().getIdCiiu());
				ciiu = ciiuBean.getCodigo();
			}
		}
		return ciiu;
	}
	

	public String getMontoBienesCapital() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if ((bean.getRubro().getTipo().equals(TipoRubro.BIEN) && (bean.getRubro().getCodigo().equals("bienes.maquinarias")))) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoBienesCapitalInfra() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if ((bean.getRubro().getTipo().equals(TipoRubro.BIEN) && (bean.getRubro().getCodigo().equals("bienes.infraestructura")))) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoBienesCapitalOtro() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if ((bean.getRubro().getTipo().equals(TipoRubro.BIEN) && (bean.getRubro().getCodigo().equals("bienes.otros")))) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoRHProp() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if (bean.getRubro().getTipo().equals(TipoRubro.RECURSO_HUMANO_PROPIO) && (bean.getRubro().getCodigo().equals("recursosHumanos.propios"))) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoRHAd() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if (bean.getRubro().getTipo().equals(TipoRubro.RECURSO_HUMANO_ADICIONAL)) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoConYSer() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if (bean.getRubro().getTipo().equals(TipoRubro.CONSULTOR)) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}

	public String getMontoMatEIn() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if (bean.getRubro().getTipo().equals(TipoRubro.INSUMO)) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
		DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
		fin = twoPlaces.format(total);
		return fin;
	}
//////////////////////////////////////////////////////////////////////////
	public String getNumeroDePaquete() throws UnsupportedEncodingException {
		ProyectoBean proyecto = (ProyectoBean) this.getCurrentRowObject();
		if (proyecto.estaEnPaquete()) {
			return proyecto.getProyectoPaquete().getPaquete().getId().toString();
		}
		else {
		return ""; }
	}
//////////////////////////////////////////////////////////////////////////
	public String getMontoOtros() throws UnsupportedEncodingException {
		Double total = 0.0;
		String fin = "";
		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		if (lObject.getProyectoPresupuesto() != null) {
			PresupuestoRubroCollectionBean arrayList = lObject.getProyectoPresupuesto().getPresupuestoRubros();
			if(arrayList!=null) {
				for (PresupuestoRubroBean bean : arrayList) {
					if ((bean.getRubro().getTipo().equals(TipoRubro.BIEN) && (bean.getRubro().getCodigo().equals("otrosCostos")))) {
						total = total + bean.getMontoTotal();
					}
				}
			}
		}
        DecimalFormat twoPlaces = new DecimalFormat("###,##0.00");
        fin = twoPlaces.format(total);
		return fin;
	}
	
	public String getPresentacionOrigen(){
		ProyectoBean proyectoBean  = (ProyectoBean) this.getCurrentRowObject();
		Long idPresentacion = proyectoBean.getIdPresentacion();
		return idPresentacion != null ? this.getCodigoPresentacion( idPresentacion ) : "";
	}
	
	public String getIpOrigen(){
		ProyectoBean proyectoBean  = (ProyectoBean) this.getCurrentRowObject();
		Long idProyectoOrigen = proyectoBean.getIdProyectoOrigen();
		return (idProyectoOrigen != null ? this.getCodigoProyecto( idProyectoOrigen) : "");
	}
	
	public String getMontoBeneficioFONTARSolicitado() {
		ProyectoBean proyectoBean  = (ProyectoBean) this.getCurrentRowObject();
		String ret;
		try {
			ret = StringUtil.formatMoneyForPresentation(proyectoBean.getMontoBeneficioFONTARSolicitado());
		} catch(SecurityException exception) {
			ret = ObjectUtils.ENCRIPTION_WARNING;
		}
		return ret;
	}

	public String getMontoBeneficioFONTARAprobado() {
		ProyectoBean proyectoBean  = (ProyectoBean) this.getCurrentRowObject();
		String ret;
		try {
			ret = StringUtil.formatMoneyForPresentation(proyectoBean.getMontoBeneficioFONTARAprobado());
		} catch(SecurityException exception) {
			ret = null;
		}
		return ret;
	}
	
	public EncryptedObject getRecomendacionProyecto() {
		ProyectoBean proyectoBean  = (ProyectoBean) this.getCurrentRowObject();
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService");
		return administrarProyectoServicio.getRecomendacionSiEsAccesible(proyectoBean);
	}
	
	private String getCodigoPresentacion(Long idPresentacion){
		PresentacionConvocatoriaDAO dao = (PresentacionConvocatoriaDAO) ContextUtil.getBean("presentacionConvocatoriaDao");
		PresentacionConvocatoriaBean convocatoria = dao.read(idPresentacion);
		return convocatoria.getCodigo();
	}
	
	private String getCodigoProyecto(Long idProyecto){
		ProyectoRaizDAO dao = (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
		ProyectoRaizBean proyectoBean = dao.read(idProyecto);
		return proyectoBean.getCodigo();
	}
	public String getMontoTotalSolicitado() {
		
		ProyectoBean proyecto = (ProyectoBean) this.getCurrentRowObject();
		try {
			BigDecimal montoTotalSolicitado = proyecto.getMontoTotalSolicitado();
			return StringUtil.formatMoneyForPresentation(montoTotalSolicitado);
		} catch(SecurityException exception) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}		
	}
	public String getMontoTotalAprobado() {
		ProyectoBean proyecto = (ProyectoBean) this.getCurrentRowObject();
		try {
			BigDecimal montoTotalAprobado = proyecto.getMontoTotalAprobado();
			return StringUtil.formatMoneyForPresentation(montoTotalAprobado);
		} catch(SecurityException exception) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}		
	}
	
}
