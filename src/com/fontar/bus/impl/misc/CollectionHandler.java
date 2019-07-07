package com.fontar.bus.impl.misc;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.util.LabelValueBean;

import com.fontar.bus.api.convocatoria.LlamadoConvocatoriaServicio;
import com.fontar.data.Constant.ResultadoGestionPago;
import com.fontar.data.api.dao.CarteraDAO;
import com.fontar.data.api.dao.CiiuDAO;
import com.fontar.data.api.dao.ComisionDAO;
import com.fontar.data.api.dao.DesembolsoUFFADAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.EmpleoPermanenteDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.FuenteFinanciamientoDAO;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.InstrumentoDefDAO;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.api.dao.LlamadoConvocatoriaDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.api.dao.MatrizCriterioItemDAO;
import com.fontar.data.api.dao.MatrizPresupuestoDAO;
import com.fontar.data.api.dao.MonedaDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.RegionDAO;
import com.fontar.data.api.dao.TipoAdquisicionDAO;
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.api.dao.TributariaDAO;
import com.fontar.data.api.dao.VentanillaPermanenteDAO;
import com.fontar.data.api.dao.proyecto.RubroDAO;
import com.fontar.data.api.dao.proyecto.presupuesto.plan.EtapaDAO;
import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.domain.bean.CarteraBean;
import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.bean.DesembolsoUFFABean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.EntidadBancariaBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioItemBean;
import com.fontar.data.impl.domain.bean.MatrizPresupuestoBean;
import com.fontar.data.impl.domain.bean.MonedaBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.RegionBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.fontar.data.impl.domain.bean.TributariaBean;
import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;
import com.fontar.data.impl.domain.codes.ComboValuesTransformer;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacionFinanciera;
import com.fontar.data.impl.domain.codes.general.EstadoEntidad;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.ProximoPaso;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.SeguimientoGestionPagoDTO;
import com.fontar.util.Util;
import com.pragma.bus.api.GenericABMService;
import com.pragma.util.ContextUtil;
import com.pragma.util.exception.IllegalArgumentException;

@SuppressWarnings("unchecked")
public class CollectionHandler {
	

	public static String EMPTY_VALUE = "";

	public static String EMPTY_LABEL = "----- seleccione -----";

	public static String NO_ACTION = "---- sin acciones ----";

	public static String NO_WORKFLOW = "---- sin workflow ----";

	public static String LABEL_TODOS = "Todos";

	public static String LABEL_TODAS = "Todas";
	
	static ArrayList<String> opcionesPredefinidas =new ArrayList<String>();
	
	static {
		opcionesPredefinidas.add (EMPTY_VALUE);
		opcionesPredefinidas.add (EMPTY_LABEL);
		opcionesPredefinidas.add (NO_ACTION);
		opcionesPredefinidas.add (NO_WORKFLOW);
		opcionesPredefinidas.add (LABEL_TODOS);
		opcionesPredefinidas.add (LABEL_TODAS);
	}
	
	private static CollectionHandler defaultInstance;
	
	public static CollectionHandler defaultInstance() {
		if(defaultInstance==null) defaultInstance = new CollectionHandler();
		return defaultInstance;
	}
	
	private void addEmptyLabel(Collection<LabelValueBean> collection) {
		
		collection.add(new LabelValueBean(EMPTY_LABEL, null));
	}
	
	private void addEmptyLabelAll(Collection<LabelValueBean> collection) {
		
		collection.add(new LabelValueBean(EMPTY_LABEL, "all"));
	}

	private void addLabelTodos(Collection<LabelValueBean> collection) {
		collection.add(new LabelValueBean(LABEL_TODOS, null));
	}

	private void addLabelTodas(Collection<LabelValueBean> collection) {
		collection.add(new LabelValueBean(LABEL_TODAS, null));
	}
	
	private static class LabelValueCollection extends TreeSet<LabelValueBean> {
		private static final long serialVersionUID = 1L;
		private static Comparator<LabelValueBean> labelComparator = new Comparator<LabelValueBean>() {
			public int compare(LabelValueBean o1, LabelValueBean o2) {
				String stringLabel1 = o1.getLabel();
				String stringLabel2 = o2.getLabel();
				
				if (opcionesPredefinidas.contains(stringLabel1) 
						&& !opcionesPredefinidas.contains(stringLabel2))
					return -1;
				else if (!opcionesPredefinidas.contains(stringLabel1) 
						&& opcionesPredefinidas.contains(stringLabel2))
					return 1;
				else
					return stringLabel1.compareTo(stringLabel2);
			}
		};
		public LabelValueCollection() {
			super(labelComparator);
		}
	}

	public Collection<LabelValueBean> getJurisdicciones(JurisdiccionDAO jurisdiccionDAO) throws SQLException {
		LabelValueCollection jurisdicciones = new LabelValueCollection();

		Collection<JurisdiccionBean> jurisdiccionesCollection = jurisdiccionDAO.getAll();

		addEmptyLabel(jurisdicciones);
		
		for (JurisdiccionBean jurisdiccion : jurisdiccionesCollection) {
			jurisdicciones.add(new LabelValueBean(jurisdiccion.getDescripcion(), jurisdiccion.getId().toString()));
		}

		return jurisdicciones;
	}

	public Collection<LabelValueBean> getEntidadesBeneficiarias(EntidadBeneficiariaDAO entidadesBeneficiariasDAO) throws SQLException {
		LabelValueCollection entidadesBeneficiarias = new LabelValueCollection();

		Collection entidadesBeneficiariasCollection = entidadesBeneficiariasDAO.getAll();
		EntidadBeneficiariaBean entidadBeneficiariaBean = new EntidadBeneficiariaBean();

		addEmptyLabel(entidadesBeneficiarias);
		for (int i = 0; i < entidadesBeneficiariasCollection.size(); i++) {
			entidadBeneficiariaBean = (EntidadBeneficiariaBean) entidadesBeneficiariasCollection.toArray()[i];
			entidadesBeneficiarias.add(new LabelValueBean(entidadBeneficiariaBean.getDenominacion(), entidadBeneficiariaBean.getId().toString()));
		}
		return entidadesBeneficiarias;
	}

	public Collection<LabelValueBean> getEntidadesBancarias(EntidadBancariaDAO entidadesBancariasDAO) throws SQLException {
		LabelValueCollection entidadesBancarias = new LabelValueCollection();

		Collection entidadesBancariasCollection = entidadesBancariasDAO.getAll();
		EntidadBancariaBean entidadBancariaBean = new EntidadBancariaBean();

		addEmptyLabel(entidadesBancarias);
		for (int i = 0; i < entidadesBancariasCollection.size(); i++) {
			entidadBancariaBean = (EntidadBancariaBean) entidadesBancariasCollection.toArray()[i];
			entidadesBancarias.add(new LabelValueBean(entidadBancariaBean.getEntidad().getDenominacion(), entidadBancariaBean.getId().toString()));
		}
		return entidadesBancarias;
	}

	public Collection<LabelValueBean> getPersonas(PersonaDAO personaDAO) throws SQLException {
		
		LabelValueCollection personas = new LabelValueCollection();

		Collection personasCollection = personaDAO.getAll();
		PersonaBean personaBean = new PersonaBean();

		addEmptyLabel(personas);
		for (int i = 0; i < personasCollection.size(); i++) {
			personaBean = (PersonaBean) personasCollection.toArray()[i];
			personas.add(new LabelValueBean(personaBean.getNombre(), personaBean.getId().toString()));
		}

		return personas;
	}

	public Collection<LabelValueBean> getTiposProyecto(DistribucionTipoProyectoDAO tipoProyectoDAO, ProyectoBean proyecto) throws SQLException {

		
		LabelValueCollection tiposProyectos = new LabelValueCollection();

		List<DistribucionTipoProyectoBean> distribucionProyectosDLT = tipoProyectoDAO.findByInstrumento(proyecto.getIdInstrumento());
		DistribucionTipoProyectoBean bean = new DistribucionTipoProyectoBean();

		addEmptyLabel(tiposProyectos);
		for (int i = 0; i < distribucionProyectosDLT.size(); i++) {
			bean = (DistribucionTipoProyectoBean) distribucionProyectosDLT.toArray()[i];
			tiposProyectos.add(new LabelValueBean(bean.getTipoProyecto().getNombre(), bean.getId().toString()));
		}
		return tiposProyectos;
	}

	public Collection<LabelValueBean> getLocalizaciones(LocalizacionDAO localizacionDAO) throws SQLException {
		
		LabelValueCollection localizaciones = new LabelValueCollection();

		Collection localizacionesCollection = localizacionDAO.getAll();
		LocalizacionBean localizacionBean = new LocalizacionBean();

		addEmptyLabel(localizaciones);
		for (int i = 0; i < localizacionesCollection.size(); i++) {
			localizacionBean = (LocalizacionBean) localizacionesCollection.toArray()[i];
			localizaciones.add(new LabelValueBean(localizacionBean.getLocalidad(), localizacionBean.getId().toString()));
		}
		return localizaciones;
	}

	public Collection<LabelValueBean> getEmpleoPermanente(EmpleoPermanenteDAO empleoPermanenteDAO) throws SQLException {
		
		LabelValueCollection empleoPermanente = new LabelValueCollection();

		Collection empleoPermanenteCollection = empleoPermanenteDAO.getAll();
		EmpleoPermanenteBean empleoPermanenteBean = new EmpleoPermanenteBean();

		addEmptyLabel(empleoPermanente);
		for (int i = 0; i < empleoPermanenteCollection.size(); i++) {
			empleoPermanenteBean = (EmpleoPermanenteBean) empleoPermanenteCollection.toArray()[i];
			empleoPermanente.add(new LabelValueBean(empleoPermanenteBean.getId().toString(), empleoPermanenteBean.getId().toString()));
		}
		return empleoPermanente;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<LabelValueBean> getCiius(CiiuDAO ciiuDAO) throws SQLException {

		
		LabelValueCollection comboList = new LabelValueCollection();

		Collection<CiiuBean> collection = ciiuDAO.getAll();

		addEmptyLabel(comboList);
		for(CiiuBean bean : collection) {
			comboList.add(new LabelValueBean(bean.getNombre(), bean.getId().toString()));
		}
		return comboList;
	}

	public Collection<LabelValueBean> getMatrizPresupuesto(MatrizPresupuestoDAO matrizPresupuestoDAO) {
		
		LabelValueCollection comboList = new LabelValueCollection();

		Collection<MatrizPresupuestoBean> collection = matrizPresupuestoDAO.getAll();

		// addEmptyLabel(comboList);
		for(MatrizPresupuestoBean bean : collection) {
			comboList.add(new LabelValueBean(bean.getNombre(), bean.getId().toString()));
		}

		return comboList;
	}

	public Collection<LabelValueBean> getMatrizCriterio(MatrizCriterioDAO matrizCriterioDAO) {
		
		LabelValueCollection comboList = new LabelValueCollection();

		Collection collection = matrizCriterioDAO.getAll();
		MatrizCriterioBean bean = new MatrizCriterioBean();

		// addEmptyLabel(comboList);
		for (int i = 0; i < collection.size(); i++) {
			bean = (MatrizCriterioBean) collection.toArray()[i];
			comboList.add(new LabelValueBean(bean.getNombre(), bean.getId().toString()));
		}

		return comboList;
	}

	public Collection<LabelValueBean> getTiposProyectos(TipoProyectoDAO tipoProyectoDAO) throws SQLException {
		
		LabelValueCollection tipoProyecto = new LabelValueCollection();

		Collection<TipoProyectoBean> tipoProyectoCollection = tipoProyectoDAO.getActives(true);

		addEmptyLabel(tipoProyecto);
		for(TipoProyectoBean bean : tipoProyectoCollection) {
			tipoProyecto.add(new LabelValueBean(bean.getNombre(), bean.getId().toString()));
		}
		return tipoProyecto;
	}

	public Collection<LabelValueBean> getInstrumentosDef(InstrumentoDefDAO instrumentosDefDAO) throws SQLException {

		
		LabelValueCollection instrumentos = new LabelValueCollection();

		Collection instrumentosCollection = instrumentosDefDAO.getAll();
		InstrumentoDefBean instrumentoBean = new InstrumentoDefBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentosCollection.size(); i++) {
			instrumentoBean = (InstrumentoDefBean) instrumentosCollection.toArray()[i];
			if (instrumentoBean.getActivo())
				instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}
		return instrumentos;
	}

	public Collection<LabelValueBean> getInstrumentos(GenericABMService<InstrumentoDefBean> instrumentoDefServicio) throws SQLException {
		
		LabelValueCollection instrumentos = new LabelValueCollection();
		Collection instrumentosCollection = instrumentoDefServicio.getAll();

		InstrumentoDefBean instrumentoBean = new InstrumentoDefBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentosCollection.size(); i++) {
			instrumentoBean = (InstrumentoDefBean) instrumentosCollection.toArray()[i];
			instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}
		return instrumentos;
	}

	/**
	 * Obtiene los intrumentos activos para comision
	 * @return
	 * @throws SQLException
	 */
	public Collection<LabelValueBean> getInstrumentoComision(InstrumentoDAO instrumentoDAO) throws SQLException {
		
		LabelValueCollection instrumentos = new LabelValueCollection();

		Collection instrumentoCollection = instrumentoDAO.findByComision();
		InstrumentoBean instrumentoBean = new InstrumentoBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentoCollection.size(); i++) {
			instrumentoBean = (InstrumentoBean) instrumentoCollection.toArray()[i];
			instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}

		return instrumentos;
	}

	/**
	 * Obtiene los intrumentos activos para secretaría
	 * @return
	 * @throws SQLException
	 */
	public Collection<LabelValueBean> getInstrumentoSecretaria(InstrumentoDAO instrumentoDAO) throws SQLException {
		
		LabelValueCollection instrumentos = new LabelValueCollection();

		Collection instrumentoCollection = instrumentoDAO.findBySecretaria();
		InstrumentoBean instrumentoBean = new InstrumentoBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentoCollection.size(); i++) {
			instrumentoBean = (InstrumentoBean) instrumentoCollection.toArray()[i];
			instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}

		return instrumentos;
	}

	/**
	 * Obtiene los intrumentos activos para directorio
	 * @return
	 * @throws SQLException
	 */
	public Collection<LabelValueBean> getInstrumentoDirectorio(InstrumentoDAO instrumentoDAO) throws SQLException {
		
		LabelValueCollection instrumentos = new LabelValueCollection();

		Collection instrumentoCollection = instrumentoDAO.findByDirectorio();
		InstrumentoBean instrumentoBean = new InstrumentoBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentoCollection.size(); i++) {
			instrumentoBean = (InstrumentoBean) instrumentoCollection.toArray()[i];
			instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}

		return instrumentos;
	}

	/**
	 * Obtiene los intrumentos activos
	 * @return
	 * @throws SQLException
	 */
	public Collection<LabelValueBean> getInstrumentoActivos(InstrumentoDAO instrumentoDAO) throws SQLException {
		
		LabelValueCollection instrumentos = new LabelValueCollection();

		Collection instrumentoCollection = instrumentoDAO.findAllActivos();
		InstrumentoBean instrumentoBean = new InstrumentoBean();

		addEmptyLabel(instrumentos);
		for (int i = 0; i < instrumentoCollection.size(); i++) {
			instrumentoBean = (InstrumentoBean) instrumentoCollection.toArray()[i];
			instrumentos.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}

		return instrumentos;
	}
	
	public Collection<LabelValueBean> getInstrumentosActivos() throws SQLException {
		InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");
		return this.getInstrumentoActivos( instrumentoDAO );
	}
	
	public Collection<LabelValueBean> getInstrumentosConfiguracion() throws SQLException {
		InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoConfiguracionDao");
		return this.getInstrumentoActivos( instrumentoDAO );
	}
	

	// TODO: SS-ver como implementar algo generico para excluir algunos valores
	// que no queremos
	public Collection<LabelValueBean> getTratamientosPaquete(Class enumerable, String tipoPaquete) {
		Collection tratamientos = new ArrayList();

		tratamientos = getComboFiltro(enumerable);

		if (!tipoPaquete.equals(TipoPaquete.DIRECTORIO.name())) {
			tratamientos.remove(new LabelValueBean(TratamientoPaquete.ADJUDICACION.getDescripcion(), TratamientoPaquete.ADJUDICACION.name()));
		}

		return tratamientos;
	}

	public Collection<LabelValueBean> getComisiones(ComisionDAO comisionesDAO) throws SQLException {

		
		LabelValueCollection comisiones = new LabelValueCollection();

		Collection comisionesCollection = comisionesDAO.getAll();
		ComisionBean comisionBean = new ComisionBean();

		addEmptyLabel(comisiones);
		for (int i = 0; i < comisionesCollection.size(); i++) {
			comisionBean = (ComisionBean) comisionesCollection.toArray()[i];
			comisiones.add(new LabelValueBean(comisionBean.getDescripcion(), comisionBean.getId().toString()));
		}

		return comisiones;
	}
	
	public Collection<LabelValueBean> getFuenteFinanciamiento() throws SQLException {

		
		LabelValueCollection labels = new LabelValueCollection();
		FuenteFinanciamientoDAO dao = (FuenteFinanciamientoDAO) ContextUtil.getBean("fuenteFinanciamientoDao");
		Iterator<FuenteFinanciamientoBean> elements = dao.getAll().iterator();

		while(elements.hasNext()){
			FuenteFinanciamientoBean bean  = elements.next();
			labels.add(new LabelValueBean(bean.getDenominacion(),String.valueOf(bean.getId())));
		}

		return labels;
	}

	public Collection<LabelValueBean> getMatrices(MatrizPresupuestoDAO matricesDAO) throws SQLException {
		
		LabelValueCollection matrices = new LabelValueCollection();

		// Collection matricesCollection = matricesDAO.getAll();
		Collection matricesCollection = matricesDAO.getActives(true);
		MatrizPresupuestoBean matrizBean = new MatrizPresupuestoBean();

		addEmptyLabel(matrices);
		for (int i = 0; i < matricesCollection.size(); i++) {
			matrizBean = (MatrizPresupuestoBean) matricesCollection.toArray()[i];
			matrices.add(new LabelValueBean(matrizBean.getNombre(), matrizBean.getId().toString()));
		}

		return matrices;
	}

	public Collection<LabelValueBean> getVentanillas(VentanillaPermanenteDAO ventanillasDAO) throws SQLException {
		
		LabelValueCollection ventanillas = new LabelValueCollection();

		Collection ventanillasCollection = ventanillasDAO.getAll();
		VentanillaPermanenteBean ventanillaBean = new VentanillaPermanenteBean();

		Iterator iter = ventanillasCollection.iterator();

		addEmptyLabel(ventanillas);
		while (iter.hasNext()) {
			ventanillaBean = (VentanillaPermanenteBean) iter.next();
			ventanillas.add(new LabelValueBean(ventanillaBean.getIdentificador(), ventanillaBean.getId().toString()));
		}

		return ventanillas;
	}


	public Collection<LabelValueBean> getTipoEvaluacionFinanciera() {
		return this.getComboFormulario(TipoEvaluacionFinanciera.class, true);
	}

	public String getLabelEvaluacion(EvaluacionGeneralDTO eval) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(eval.getId());
		if (Boolean.valueOf(eval.getEsContable())) {
			buffer.append(" - Contable");
		}
		if (Boolean.valueOf(eval.getEsEconomica())) {
			buffer.append(" - Económica");
		}
		if (Boolean.valueOf(eval.getEsFinanciera())) {
			buffer.append(" - Financiera");
		}

		return buffer.toString();
	}

	/**
	 * Recomendaciones para una evaluación de proyecto
	 * @return Collection<LabelValueBean>
	 */
	public Collection<LabelValueBean> getRecomendacionesEvaluacion() {
		Collection collection = new ArrayList();

		addEmptyLabel(collection);

		LabelValueBean labelValue = new LabelValueBean();

		labelValue.setLabel(ResultadoEvaluacion.APROBADO.getDescripcion());
		labelValue.setValue(ResultadoEvaluacion.APROBADO.getName());
		collection.add(labelValue);

		labelValue = new LabelValueBean();
		labelValue.setLabel(ResultadoEvaluacion.APRO_MODIF_MONTO.getDescripcion());
		labelValue.setValue(ResultadoEvaluacion.APRO_MODIF_MONTO.getName());
		collection.add(labelValue);

		labelValue = new LabelValueBean();
		labelValue.setLabel(ResultadoEvaluacion.RECHAZADO.getDescripcion());
		labelValue.setValue(ResultadoEvaluacion.RECHAZADO.getName());
		collection.add(labelValue);

		labelValue = new LabelValueBean();
		labelValue.setLabel(ResultadoEvaluacion.A_DEFINIR.getDescripcion());
		labelValue.setValue(ResultadoEvaluacion.A_DEFINIR.getName());
		collection.add(labelValue);

		return collection;
	}
	
	public Collection<LabelValueBean> getRecomendacionPostAprobacion() {
		
		Collection collection = new ArrayList();

		addEmptyLabel(collection);

		LabelValueBean labelValue = new LabelValueBean();

		labelValue.setLabel(Recomendacion.APROBADO_ADJUDICADO.getDescripcion());
		labelValue.setValue(Recomendacion.APROBADO_ADJUDICADO.getName());
		collection.add(labelValue);

		labelValue = new LabelValueBean();
		labelValue.setLabel(Recomendacion.APROBADO_MM_ADJUDICADO.getDescripcion());
		labelValue.setValue(Recomendacion.APROBADO_MM_ADJUDICADO.getName());
		collection.add(labelValue);

		return collection;
	}

	public Collection<LabelValueBean> getEstadosEntidad() throws SQLException {
		Collection estados = new ArrayList();

		estados.add(new LabelValueBean(EstadoEntidad.TRUE.getDescripcion(), EstadoEntidad.TRUE.getName().toLowerCase()));
		estados.add(new LabelValueBean(EstadoEntidad.FALSE.getDescripcion(), EstadoEntidad.FALSE.getName().toLowerCase()));

		return estados;
	}

	public Collection<LabelValueBean> getInstrumentosPaquetes(List<PaqueteBean> paquetes) {
		
		LabelValueCollection instrumentos = new LabelValueCollection();
		InstrumentoBean instrumentoBean;

		for (PaqueteBean paqueteBean : paquetes) {
			instrumentoBean = paqueteBean.getInstrumento();
			instrumentos.add(new LabelValueBean(instrumentoBean.getId().toString(), instrumentoBean.getIdentificador()));
		}

		return instrumentos;
	}

	public Collection<LabelValueBean> getLabelValueInstrumentos(List<InstrumentoBean> instrumentos) {
		
		LabelValueCollection instrumentosLV = new LabelValueCollection();
		
		addEmptyLabel(instrumentosLV);
		for (InstrumentoBean instrumentoBean : instrumentos) {
			instrumentosLV.add(new LabelValueBean(instrumentoBean.getIdentificador(), instrumentoBean.getId().toString()));
		}

		return instrumentosLV;
	}

	public Collection<LabelValueBean> getLabelValueLlamadosConvocatoria(List<LlamadoConvocatoriaBean> llamados) {
		Collection llamadosLV = new ArrayList();

		addEmptyLabel(llamadosLV);
		for (LlamadoConvocatoriaBean llamadosConvocatoriaBean : llamados) {
			llamadosLV.add(new LabelValueBean(llamadosConvocatoriaBean.getIdentificador(), llamadosConvocatoriaBean.getId().toString()));
		}

		return llamadosLV;
	}

	public Collection<LabelValueBean> getLabelValueLlamadosConvocatoria(){
		LlamadoConvocatoriaDAO dao = (LlamadoConvocatoriaDAO) ContextUtil.getBean("llamadoConvocatoriaDao");
		return this.getLabelValueLlamadosConvocatoria( dao.findAllActivosOrdenados());
	}
	
	public Collection<LabelValueBean> getLabelValueProyectoRaiz(List<ProyectoRaizBean> proyectos) {
		
		LabelValueCollection proyectosLV = new LabelValueCollection();
		
		addEmptyLabel(proyectosLV);
		for (ProyectoRaizBean proyectoRaizBean : proyectos) {
			proyectosLV.add(new LabelValueBean(proyectoRaizBean.getCodigo(), proyectoRaizBean.getId().toString()));
		}

		return proyectosLV;
	}
	
	
	/**
	 * Obtiene el listado de valores de una enumeración con formato, agrega la
	 * opcion SELECCIONE por defecto para los combos de filtrado de inventarios
	 * @author mrouaux, ssanchez
	 * @version 1.01, 14/12/06
	 */
	public Collection<LabelValueBean> getComboFiltro(Class enumerable) {
		Collection<LabelValueBean> values = new ArrayList<LabelValueBean>();
		addEmptyLabel(values);

		addEnumOptions(enumerable, values);

		return values;
	}

	public Collection<LabelValueBean> getComboFiltroConEmptyLabelAll(Class enumerable) {
		Collection<LabelValueBean> values = new ArrayList<LabelValueBean>();
		addEmptyLabelAll(values);

		addEnumOptions(enumerable, values);

		return values;
	}

	private void addEnumOptions(Class enumerable, Collection<LabelValueBean> values) {
		Method method;
		try {
			method = enumerable.getMethod("values", new Class[] {});
			Enumerable[] codigos = (Enumerable[]) method.invoke(enumerable, new Object[] {});

			CollectionUtils.addAll(values, codigos);
			CollectionUtils.transform(values, new ComboValuesTransformer());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el listado de valores de una enumeración con formato, NO agrega
	 * la opcion SELECCIONE, este es usado para seleccion de datos obligatorios en la
	 * edición o agregado de alguna entidad
	 * @author ssanchez
	 * @version 1.00, 14/12/06
	 */
	public Collection<LabelValueBean> getComboFormulario(Class enumerable, boolean seleccione) {
		Collection values = new ArrayList();
		
		if(seleccione)
			addEmptyLabel(values);

		Method method;
		try {
			method = enumerable.getMethod("values", new Class[] {});
			Enumerable[] codigos = (Enumerable[]) method.invoke(enumerable, new Object[] {});

			CollectionUtils.addAll(values, codigos);
			CollectionUtils.transform(values, new ComboValuesTransformer());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	
	public Collection<LabelValueBean> getConvocatorias(boolean emptyLabel)  {
		return this.getConvocatorias((LlamadoConvocatoriaDAO) ContextUtil.getBean("llamadoConvocatoriaDao"), emptyLabel);
	}
	
	public Collection<LabelValueBean> getConvocatorias(LlamadoConvocatoriaDAO llamadosConvocatoriaDAO) {
		return this.getConvocatorias(llamadosConvocatoriaDAO,true);
		
	}
	
	public Collection<LabelValueBean> getConvocatorias(LlamadoConvocatoriaDAO llamadosConvocatoriaDAO , boolean emptyLabel) {

		Collection convocatorias = new ArrayList();
		Collection llamadosConvocatoriaCollection = llamadosConvocatoriaDAO.findAllActivosOrdenados();
		
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = new LlamadoConvocatoriaBean();

		Iterator iter = llamadosConvocatoriaCollection.iterator();
		
		if(emptyLabel)
			addEmptyLabel(convocatorias);
		
		while (iter.hasNext()) {
			llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) iter.next();
			convocatorias.add(new LabelValueBean(llamadoConvocatoriaBean.getIdentificador(), llamadoConvocatoriaBean.getId().toString()));
		}

		return convocatorias;
	}

	/**
	 * Devuelve un Collection<LabelValue> con los tareas definidas en todos los
	 * WorkFlow
	 * @param tasks
	 * @return
	 */
	public Collection<LabelValueBean> getWorkFlowTasks(List<Object[]> tasks) {
		Collection tareas = new ArrayList();
		addLabelTodas(tareas);

		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			Object[] taskObject = (Object[]) iter.next();

			String taskName = (String) taskObject[0];
			String taskProcessName = (String) taskObject[1];

			tareas.add(new LabelValueBean("[" + taskProcessName + "]" + " " + taskName, taskName+taskProcessName));
		}
		return tareas;
	}

	/**
	 * Devuelve un Collection<LabelValue> con los procesos definidos en wl
	 * WorkFlow
	 * @param process
	 * @return
	 */
	public Collection<LabelValueBean> getWorkFlowDefinitions(List<String> process) {
		Collection procesos = new ArrayList();
		addLabelTodos(procesos);

		for (Iterator iter = process.iterator(); iter.hasNext();) {
			String processName = (String) iter.next();
			procesos.add(new LabelValueBean(processName, processName));
		}
		return procesos;
	}
	
	/**
	 * Devuelve una collection con los MatrizCriterioItem's que son "Criterios", y NO subcategorías 
	 * (o sea que tienen "idItemPadre == null").
	 * 
	 */
	public Collection<LabelValueBean> getCriterios(MatrizCriterioItemDAO criterioItemsDAO) throws SQLException {
		
		
		LabelValueCollection criterios = new LabelValueCollection();
		
		Collection criteriosCollection = criterioItemsDAO.findCriterios();
		
		MatrizCriterioItemBean criterioBean = new MatrizCriterioItemBean();

		addEmptyLabel(criterios);
		for (int i = 0; i < criteriosCollection.size(); i++) {
			criterioBean = (MatrizCriterioItemBean) criteriosCollection.toArray()[i];
			criterios.add(new LabelValueBean(criterioBean.getDenominacion(), criterioBean.getId().toString()));
		}

		return criterios;
	}
	
	
	public Collection<LabelValueBean> getProximosPasos(Boolean incluyeComision, Boolean incluyeSecretaria) {
		LinkedList opciones = new LinkedList();
		
		addEmptyLabel(opciones);
		opciones.addLast(new LabelValueBean( ProximoPaso.EVALUAR.getDescripcion(), ProximoPaso.EVALUAR.name()));
		if (incluyeComision)
			opciones.addLast(new LabelValueBean( ProximoPaso.COMISION.getDescripcion(), ProximoPaso.COMISION.name()));
		
		if (incluyeSecretaria)
			opciones.addLast(new LabelValueBean( ProximoPaso.SECRETARIA.getDescripcion(), ProximoPaso.SECRETARIA.name()));
		
		opciones.addLast(new LabelValueBean( ProximoPaso.DIRECTORIO_EVAL.getDescripcion(), ProximoPaso.DIRECTORIO_EVAL.name()));
		
		return opciones;
	}

	/**
	 *	Devuelve una Collection con los Rubros Hijos guardados en la BD
	 *  Se usa para armar el combo de Rubros en la pantalla de carga de Rendiciones 
	 */
	public Collection<LabelValueBean> getRubros(RubroDAO rubroDAO) throws SQLException {
		
		LabelValueCollection rubros = new LabelValueCollection();

		Collection rubrosCollection = rubroDAO.getAll();
		RubroBean rubroBean = new RubroBean();

		addEmptyLabel(rubros);
		for (int i = 0; i < rubrosCollection.size(); i++) {
			rubroBean = (RubroBean) rubrosCollection.toArray()[i];
			if (!rubroBean.getTipo().equals(TipoRubro.RUBRO_PADRE)) {
				rubros.add(new LabelValueBean(rubroBean.getNombre(), rubroBean.getId().toString()));
			}
		}

		return rubros;
	}

	/**
	 * Recomendaciones para una evaluación de proyecto
	 * @return Collection<LabelValueBean>
	 */
	public Object getRecomendacionesEvaluacionSeguimiento() {
			Collection collection = new ArrayList();

			addEmptyLabel(collection);

			LabelValueBean labelValue = new LabelValueBean();

			labelValue.setLabel(ResultadoEvaluacion.APROBADO.getDescripcion());
			labelValue.setValue(ResultadoEvaluacion.APROBADO.getName());
			collection.add(labelValue);

			labelValue = new LabelValueBean();
			labelValue.setLabel(ResultadoEvaluacion.RECHAZADO.getDescripcion());
			labelValue.setValue(ResultadoEvaluacion.RECHAZADO.getName());
			collection.add(labelValue);

			return collection;
	}

	
	public Collection<LabelValueBean> getRegiones() {
		RegionDAO regionDao = (RegionDAO) ContextUtil.getBean("regionDao");
		Collection regiones = new ArrayList();
		addEmptyLabel(regiones);
		for (RegionBean region: regionDao.getAll()) {
			regiones.add( new LabelValueBean(region.getNombre(), region.getId().toString()));
		}
		return regiones;
	}

	public Collection<LabelValueBean> getInstrumentos() {
		InstrumentoDAO instrumentoDao = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");		
		Collection instrumentos = new ArrayList();		
		addEmptyLabel(instrumentos);
		
		for (InstrumentoBean instrumento: instrumentoDao.getAll()) {
			instrumentos.add( new LabelValueBean(instrumento.getIdentificador(), instrumento.getId().toString()));
		}
		
		return instrumentos;
	}

	public Collection<LabelValueBean> getEntidadesBeneficiarias() {
		
		LabelValueCollection entidadesBeneficiarias = new LabelValueCollection();
		
		EntidadBeneficiariaDAO  entidadBeneficiariaDAO = (EntidadBeneficiariaDAO) ContextUtil.getBean("entidadBeneficiariaDao");
		
		Collection entidadesBeneficiariasCollection = entidadBeneficiariaDAO.getAll();
		EntidadBeneficiariaBean entidadBeneficiariaBean = new EntidadBeneficiariaBean();

		addEmptyLabel(entidadesBeneficiarias);
		for (int i = 0; i < entidadesBeneficiariasCollection.size(); i++) {
			entidadBeneficiariaBean = (EntidadBeneficiariaBean) entidadesBeneficiariasCollection.toArray()[i];
			entidadesBeneficiarias.add(new LabelValueBean(entidadBeneficiariaBean.getDenominacion(), entidadBeneficiariaBean.getId().toString()));
		}
		return entidadesBeneficiarias;
	}


	public Collection<LabelValueBean> getCiiu() {
		CiiuDAO ciiuDAO = (CiiuDAO) ContextUtil.getBean("ciiuDao");
		Collection ciius = new ArrayList();
		addEmptyLabel(ciius);
		for (CiiuBean ciiuBean: ciiuDAO.getAll()) {
			ciius.add( new LabelValueBean(ciiuBean.getCodigo(), ciiuBean.getId().toString()));
		}
		return ciius;
	}

	public Collection<LabelValueBean> getJurisdicciones() {
		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) ContextUtil.getBean("jurisdiccionDao");
		Collection jurisdicciones = new ArrayList();
		addEmptyLabel(jurisdicciones);
		for (JurisdiccionBean jurisdiccionBean: jurisdiccionDAO.getAll()) {
			jurisdicciones.add( new LabelValueBean(jurisdiccionBean.getDescripcion(), jurisdiccionBean.getId().toString()));
		}
		return jurisdicciones;
	}

	public Collection<LabelValueBean> getCartera(CarteraDAO carteraDAO, Long id) {
		
		LabelValueCollection carteras = new LabelValueCollection();
		Collection carterasCollection;
		// Collection matricesCollection = matricesDAO.getAll();
		if (id != null) {
			carterasCollection = carteraDAO.getAllConActiveSumandoElemento(true,id);
		}
		else {
			carterasCollection = carteraDAO.getActives(true);
		}
		CarteraBean bean = new CarteraBean();

		addEmptyLabel(carteras);
		for (int i = 0; i < carterasCollection.size(); i++) {
			bean = (CarteraBean) carterasCollection.toArray()[i];
			carteras.add(new LabelValueBean(bean.getCodigo(), bean.getId().toString()));
		}

		return carteras;
	}

	public Collection<LabelValueBean> getTributaria(TributariaDAO tributariaDAO, Long id) {
		
		LabelValueCollection tributarias = new LabelValueCollection();

		// Collection matricesCollection = matricesDAO.getAll();
		Collection tributariasCollection;
		if (id != null) {
			tributariasCollection = tributariaDAO.getAllConActiveSumandoElemento(true,id);
		}
		else {
			tributariasCollection = tributariaDAO.getActives(true);
		}
		TributariaBean bean = new TributariaBean();

		addEmptyLabel(tributarias);
		for (int i = 0; i < tributariasCollection.size(); i++) {
			bean = (TributariaBean) tributariasCollection.toArray()[i];
			tributarias.add(new LabelValueBean(bean.getCodigo(), bean.getId().toString()));
		}

		return tributarias;
	}

	public Collection<LabelValueBean> getResultadosGestionSeguimiento(SeguimientoGestionPagoDTO seguimiento) {
			Collection<LabelValueBean> collection = new ArrayList<LabelValueBean>();

			addEmptyLabel(collection);


			LabelValueBean labelValue;
			if(seguimiento.estaHabilitadoParaGestionar()) {
				labelValue = new LabelValueBean();
				labelValue.setLabel(ResultadoGestionPago.GESTIONAR);
				labelValue.setValue(EstadoSeguimiento.GESTIONADO.getName());
				collection.add(labelValue);
			}

			if(seguimiento.estaHabilitadoParaNoGestionar()) {
				labelValue = new LabelValueBean();
				labelValue.setLabel(ResultadoGestionPago.NO_GESTIONAR);
				labelValue.setValue(EstadoSeguimiento.NO_GESTIONADO.getName());
				collection.add(labelValue);
			}

			if(seguimiento.estaHabilitadoParaRevaluar()) {
				labelValue = new LabelValueBean();
				labelValue.setLabel(ResultadoGestionPago.REVALUAR);
				labelValue.setValue(EstadoSeguimiento.EVALUACION.getName());
				collection.add(labelValue);
			}

			return collection;
	}

	public Collection<LabelValueBean> getEtapas(String etapa,Long id) {
		EtapaDAO etapaDAO = (EtapaDAO) ContextUtil.getBean("etapaDao");
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean bean = proyectoDAO.read(id);
		Long idPresupuesto = bean.getIdPresupuesto();
		Collection etapas = new ArrayList();
		addEmptyLabel(etapas);
		if (!Util.isBlank(etapa)) {
			etapas.add( new LabelValueBean(etapa, etapa));
		}
		for (String nombre: etapaDAO.findByNombres(idPresupuesto)) {
			if (!nombre.equalsIgnoreCase(etapa)) {
				etapas.add( new LabelValueBean(nombre, nombre));
			}
		}
		return etapas;
	}

	/**
	 * Devuelve la lista de rubros específicos para la carga
	 * de items de pac. Esto es: rubros raiz, salvo recursos
	 * humanos y bienes, maquinarias e infraestructura.
	 * @return
	 */
	public Collection<LabelValueBean> getRubrosParaCargaDePac() {
		RubroDAO rubroDAO = (RubroDAO) ContextUtil.getBean("rubroDao");
		Collection rubros = new ArrayList();
		addEmptyLabel(rubros);
		for (RubroBean rubro: rubroDAO.findSinPadres()) {
			//salteo recursos humanos
			if(!rubro.getCodigo().equals("recursosHumanos")) {
				if(rubro.getCodigo().equals("bienes")) {
					//despliego bienes en maquinarias e infraestructura
					RubroBean infraestructura = rubroDAO.findByCodigo("bienes.infraestructura").get(0);
					rubros.add( new LabelValueBean(infraestructura.getNombre(), infraestructura.getId().toString()));

					RubroBean maquinarias = rubroDAO.findByCodigo("bienes.maquinarias").get(0);
					rubros.add( new LabelValueBean(maquinarias.getNombre(), maquinarias.getId().toString()));
				} else {
					//Resto de los rubros raiz
					rubros.add( new LabelValueBean(rubro.getNombre(), rubro.getId().toString()));
				}				
			}
		}
		return rubros;
	}

	public Collection<LabelValueBean> getAdquisiciones() {
		TipoAdquisicionDAO adquisicionDAO = (TipoAdquisicionDAO) ContextUtil.getBean("tipoAdquisicionDao");
		
		LabelValueCollection tiposAdquisicionLV = new LabelValueCollection();
		addEmptyLabel(tiposAdquisicionLV);
		for (TipoAdquisicionBean adquisicionBean: adquisicionDAO.getAll()) {
			tiposAdquisicionLV.add( new LabelValueBean(adquisicionBean.getDescripcion(), adquisicionBean.getId().toString()));
		}
		return tiposAdquisicionLV;
	}
	
	public Collection<LabelValueBean> getLabelValueTiposAdquisicion(List<TipoAdquisicionBean> tiposAdquisicion) {
		
		LabelValueCollection tiposAdquisicionLV = new LabelValueCollection();
		
		addEmptyLabel(tiposAdquisicionLV);
		for (TipoAdquisicionBean tipoAdquisicion : tiposAdquisicion) {
			tiposAdquisicionLV.add(new LabelValueBean(tipoAdquisicion.getDescripcion(),tipoAdquisicion.getId().toString()));
		}

		return tiposAdquisicionLV;
	}

	public Collection<LabelValueBean> getRubrosNombre() {
		RubroDAO rubroDAO = (RubroDAO) ContextUtil.getBean("rubroDao");
		Collection rubros = new ArrayList();
		addEmptyLabel(rubros);
		for (RubroBean rubroBean: rubroDAO.findSinPadres()) {
			rubros.add( new LabelValueBean(rubroBean.getNombre(), rubroBean.getNombre()));
		}
		return rubros;
	}

	public Collection<LabelValueBean> getAdquisicionesDescripcion() {
		TipoAdquisicionDAO adquisicionDAO = (TipoAdquisicionDAO) ContextUtil.getBean("tipoAdquisicionDao");
		Collection adquisiciones = new ArrayList();
		addEmptyLabel(adquisiciones);
		for (TipoAdquisicionBean adquisicionBean: adquisicionDAO.getAll()) {
			adquisiciones.add( new LabelValueBean(adquisicionBean.getDescripcion(), adquisicionBean.getDescripcion()));
		}
		return adquisiciones;
	}

	public Collection<LabelValueBean> getMonedas() {
		MonedaDAO monedaDAO = (MonedaDAO) ContextUtil.getBean("monedaDao");
		Collection monedas = new ArrayList();
		addEmptyLabel(monedas);
		for (MonedaBean bean: monedaDAO.getAll()) {
			monedas.add( new LabelValueBean(bean.getDescripcion(), bean.getId().toString()));
		}
		return monedas;
	}
	
	public Collection<LabelValueBean> getLabelValueEvaluadores(List<EvaluadorBean> evaluadores) {
		
		LabelValueCollection evaluadoresLV = new LabelValueCollection();
		
		addEmptyLabel(evaluadoresLV);
		for (EvaluadorBean evaluador : evaluadores) {
			evaluadoresLV.add(new LabelValueBean(evaluador.getNombre(),evaluador.getId().toString()));
		}

		return evaluadoresLV;
	}
	public Collection<LabelValueBean> getCuotas(String id) {
		DesembolsoUFFADAO desembolsoUFFADAO = (DesembolsoUFFADAO) ContextUtil.getBean("desembolsoDao");
		Collection cuotasImpagas = new ArrayList();
		Collection cuotasPagas = new ArrayList();
		List<DesembolsoUFFABean> list = desembolsoUFFADAO.findByPac(new Long(id));
		for (DesembolsoUFFABean bean: list) {
			if (bean.getMontoPago() == null) {
				cuotasImpagas.add( new LabelValueBean(bean.getCuota().toString(), bean.getCuota().toString()));
			} else {
				cuotasPagas.add( new LabelValueBean(bean.getCuota().toString()+ " (Modificar)", bean.getCuota().toString()));
			}
		}
		Collection cuotas = new ArrayList();
		addEmptyLabel(cuotas);
		cuotas.addAll(cuotasImpagas);
		cuotas.addAll(cuotasPagas);
		return cuotas;
	}	
	
	public Collection<LabelValueBean> getLabelValueMonedas(List<MonedaBean> monedas) {
		
		LabelValueCollection monedaLV = new LabelValueCollection();
		
		addEmptyLabel(monedaLV);
		for (MonedaBean moneda : monedas) {
			monedaLV.add(new LabelValueBean(moneda.getDescripcion(),moneda.getId().toString()));
		}

		return monedaLV;
	}
	/**
	 * Devuelve una coleccion ordenada alfabeticamente de LabelValueBeans a partir
	 * de la coleccion dada. Usa las propiedades labelPropertyName y valuePropertyName
	 * de los objetos de la coleccion para identificar su etiqueta y valor. Si no es
	 * null el argumento emptyLabel, agrega al principio una opcion vacia.
	 * Ejemplo:
	 *  new CollectionHandler().buildSortedLabelValue(personaDao.getAll(), "nombre", "id", CollectionHandler.LABEL_TODAS);
	 * @param <T>
	 * @param items
	 * @param labelPropertyName
	 * @param valuePropertyName
	 * @param emptyLabel
	 * @return
	 */
	public <T> Collection<LabelValueBean> buildSortedLabelValue(
			Collection<T> items, 
			String labelPropertyName, 
			String valuePropertyName, 
			String emptyLabel) {
		
		LabelValueCollection sortedCollection  = new LabelValueCollection();
		
		if(emptyLabel!=null) {
			sortedCollection.add(new LabelValueBean(emptyLabel, null));
		}
		
		for(T t : items) {
			try {
				sortedCollection.add(
					new LabelValueBean(
							BeanUtils.getProperty(t, labelPropertyName), 
							BeanUtils.getProperty(t, valuePropertyName) 
					) 
				);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("El objeto "+t+" no tiene las propiedades "+labelPropertyName+" o "+valuePropertyName+", o son inaccesibles.");
			}
		}
		return sortedCollection;
	}

	public Collection<LabelValueBean> getRecomendacionesProyecto() {
		
		LabelValueCollection recomendaciones = new LabelValueCollection();
		
		addEmptyLabel(recomendaciones);
		for (Recomendacion recomendacion : Recomendacion.values()) {
			recomendaciones.add(new LabelValueBean(recomendacion.getDescripcion(),recomendacion.name()));
		}
		return recomendaciones;
	}
	
	public Collection<LabelValueBean> getLlamadosAConvocatoria() {
		LabelValueCollection convocatoriasLabelValue = new LabelValueCollection();
		
		LlamadoConvocatoriaServicio llamadoConvocatoriaServicio = ContextUtil.getBean("llamadoConvocatoriaService");
		Collection convocatorias = llamadoConvocatoriaServicio.getAll();
		addLabelTodas(convocatoriasLabelValue);
		
		
		for (Object object : convocatorias) {
			String label = ((LlamadoConvocatoriaBean)object).getIdentificador();
			String value = ((LlamadoConvocatoriaBean)object).getId().toString();
			convocatoriasLabelValue.add(new LabelValueBean(label, value));
		}
		return convocatoriasLabelValue;
	}
	
	public Collection<LabelValueBean> getLlamadosAConvocatoriaDeCredito() {
		LabelValueCollection convocatoriasLabelValue = new LabelValueCollection();

		LlamadoConvocatoriaServicio llamadoConvocatoriaServicio = ContextUtil.getBean("llamadoConvocatoriaService");
		Collection convocatorias = llamadoConvocatoriaServicio.getLlamadosConvocatoriasDeCredito();
		addLabelTodas(convocatoriasLabelValue);
		
		
		for (Object object : convocatorias) {
			LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean)object;
			String label = llamadoConvocatoriaBean.getIdentificador();
			String value = llamadoConvocatoriaBean.getId().toString();
			convocatoriasLabelValue.add(new LabelValueBean(label, value));
		}
		return convocatoriasLabelValue;
	}

	public Collection<LabelValueBean> getAñoDeFacturacion() {
		LabelValueCollection años = new LabelValueCollection();
		addLabelTodos(años);
		for(int año=2000; año<=2100; año++) {
			años.add(new LabelValueBean(String.valueOf(año), String.valueOf(año)));
		}
		return años;
	}
}





