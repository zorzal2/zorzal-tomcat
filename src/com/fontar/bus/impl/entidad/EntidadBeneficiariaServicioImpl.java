package com.fontar.bus.impl.entidad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fontar.bus.api.entidad.CuitNoCoincideException;
import com.fontar.bus.api.entidad.EntidadBeneficiariaServicio;
import com.fontar.bus.api.entidad.NombreNoCoincideException;
import com.fontar.bus.impl.EntidadBeneficiariaDatosInsuficientesException;
import com.fontar.data.api.dao.EmpleoPermanenteDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.util.Util;
import com.pragma.bus.impl.GenericABMServiceImpl;
import com.pragma.util.CollectionUtils;
import com.pragma.util.StringUtil;
import com.pragma.util.BeanUtils.BeanUtils;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;
import com.pragma.util.exception.IllegalArgumentException;

public class EntidadBeneficiariaServicioImpl extends GenericABMServiceImpl<EntidadBeneficiariaBean> implements EntidadBeneficiariaServicio {
	public EntidadBeneficiariaServicioImpl() {
		super(EntidadBeneficiariaBean.class);
	}

	private EntidadBeneficiariaDAO entidadBeneficiariaDao;
	private EntidadDAO entidadDao;
	private LocalizacionDAO localizacionDao;
	private EmpleoPermanenteDAO empleoPermanenteDao;
	public EmpleoPermanenteDAO getEmpleoPermanenteDao() {
		return empleoPermanenteDao;
	}
	public void setEmpleoPermanenteDao(EmpleoPermanenteDAO empleoPermanenteDao) {
		this.empleoPermanenteDao = empleoPermanenteDao;
	}
	public EntidadDAO getEntidadDao() {
		return entidadDao;
	}
	public void setEntidadDao(EntidadDAO entidadDao) {
		this.entidadDao = entidadDao;
	}
	public LocalizacionDAO getLocalizacionDao() {
		return localizacionDao;
	}
	public void setLocalizacionDao(LocalizacionDAO localizacionDao) {
		this.localizacionDao = localizacionDao;
	}
	public EntidadBeneficiariaDAO getEntidadBeneficiariaDao() {
		return entidadBeneficiariaDao;
	}
	public void setEntidadBeneficiariaDao(EntidadBeneficiariaDAO entidadBeneficiariaDao) {
		this.entidadBeneficiariaDao = entidadBeneficiariaDao;
	}
	public void create(EntidadBeneficiariaBean entidadBeneficiaria) {
		EntidadBean entidad = entidadBeneficiaria.getEntidad(); 
		//Guardo la localizacion de la entidad si no fue guardada
		if (entidad.getLocalizacion()==null) entidad.setLocalizacion(new LocalizacionBean());
		if (entidad.getLocalizacion().getId()==null) localizacionDao.save(entidad.getLocalizacion());
		entidad.setIdLocalizacion(entidad.getLocalizacion().getId());
		
		//Me aseguro de que la entidad este marcada como beneficiaria y que esActivo esta seteado
		entidad.setBeneficiaria(Boolean.TRUE);
		if(entidad.getActivo()==null) entidad.setActivo(true);
		if(entidad.getEvaluadora()==null) entidad.setEvaluadora(false);
		if(entidad.getBancaria()==null) entidad.setBancaria(false);
		
		//Guardo la entidad
		if(entidad.getId()==null) entidadDao.save(entidad);
		//Guardo la localizacion fiscal
		if (entidadBeneficiaria.getLocalizacionEconomica()==null) entidadBeneficiaria.setLocalizacionEconomica(new LocalizacionBean());
		if(entidadBeneficiaria.getLocalizacionEconomica().getId()==null)localizacionDao.save(entidadBeneficiaria.getLocalizacionEconomica());
		entidadBeneficiaria.setIdLocalizacionEconomica(entidadBeneficiaria.getLocalizacionEconomica().getId());
		//Guardo el empleo permanente
		if(entidadBeneficiaria.getTipo().equals("EMPRESA")) {
			if(entidadBeneficiaria.getEmpleoPermanente()==null)entidadBeneficiaria.setEmpleoPermanente(new EmpleoPermanenteBean());
			if(entidadBeneficiaria.getEmpleoPermanente().getId()==null)empleoPermanenteDao.save(entidadBeneficiaria.getEmpleoPermanente());
			entidadBeneficiaria.setIdEmpleoPermanente(entidadBeneficiaria.getEmpleoPermanente().getId());
		}
		//Guardo la entidad beneficiaria
		if(entidadBeneficiaria.getId()==null)entidadBeneficiariaDao.save(entidadBeneficiaria);
	}
	public EntidadBeneficiariaBean createOrUpdate(EntidadBeneficiariaBean entidadBeneficiaria) {
		if(entidadBeneficiaria.getId()==null) {
			//Intento encontrar la institucion a partir del Cuit
			List<EntidadBean> entidades = entidadBeneficiaria.getCuit()!=null ? 
												entidadDao.findByCuit(entidadBeneficiaria.getCuit()):
												null;
			if(entidades!=null && entidades.size()==1) {
				EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidades.get(0).getId());
				//Actualizo
				
				EntidadBean entidadOriginal = entidadBeneficiariaOriginal.getEntidad();
				LocalizacionBean localizacionEconomicaOriginal = entidadBeneficiariaOriginal.getLocalizacionEconomica();
				EmpleoPermanenteBean empleoPermanenteOriginal = entidadBeneficiariaOriginal.getEmpleoPermanente();

				entidadBeneficiariaOriginal = updateBean(entidadBeneficiariaOriginal, entidadBeneficiaria);
				
				//Entidad
				{
					LocalizacionBean localizacionOriginal = entidadOriginal.getLocalizacion();

					entidadOriginal = updateBean(entidadOriginal, entidadBeneficiaria.getEntidad());
					
					localizacionOriginal = updateBean(localizacionOriginal, entidadBeneficiaria.getEntidad().getLocalizacion());
					if(localizacionOriginal==null) localizacionOriginal = new LocalizacionBean();
					if(localizacionOriginal.getId()==null) localizacionDao.save(localizacionOriginal);
					entidadOriginal.setIdLocalizacion(localizacionOriginal.getId());
					entidadOriginal.setLocalizacion(localizacionOriginal);
					
					//Defaults para los campos obligatorios
					entidadOriginal.setBeneficiaria(Boolean.TRUE);
					if(entidadOriginal.getActivo()==null) entidadOriginal.setActivo(true);
					if(entidadOriginal.getEvaluadora()==null) entidadOriginal.setEvaluadora(false);
					if(entidadOriginal.getBancaria()==null) entidadOriginal.setBancaria(false);
					
					if(entidadOriginal.getId()==null)entidadDao.save(entidadOriginal);
					entidadBeneficiariaOriginal.setEntidad(entidadOriginal);
				}				
				//Localizacion economica
				{
					localizacionEconomicaOriginal = updateBean(localizacionEconomicaOriginal, entidadBeneficiaria.getLocalizacionEconomica());
					if(localizacionEconomicaOriginal==null) localizacionEconomicaOriginal = new LocalizacionBean();
					if(localizacionEconomicaOriginal.getId()==null) localizacionDao.save(localizacionEconomicaOriginal);
					entidadBeneficiariaOriginal.setIdLocalizacionEconomica(localizacionEconomicaOriginal.getId());
					entidadBeneficiariaOriginal.setLocalizacionEconomica(localizacionEconomicaOriginal);
				}
				//Empleo permanente
				{
					empleoPermanenteOriginal = updateBean(empleoPermanenteOriginal, entidadBeneficiaria.getEmpleoPermanente());
					if(empleoPermanenteOriginal==null) empleoPermanenteOriginal = new EmpleoPermanenteBean();
					if(empleoPermanenteOriginal.getId()==null) empleoPermanenteDao.save(empleoPermanenteOriginal);
					entidadBeneficiariaOriginal.setIdEmpleoPermanente(empleoPermanenteOriginal.getId());
					entidadBeneficiariaOriginal.setEmpleoPermanente(empleoPermanenteOriginal);
				}
				if(entidadBeneficiariaOriginal.getId()==null) entidadBeneficiariaDao.save(entidadBeneficiariaOriginal);
				return entidadBeneficiariaOriginal;
			} else {
				//no hay resultados. creo la empresa
				create(entidadBeneficiaria);
				return entidadBeneficiaria;
			}			
		} else {
			//Entidad
			{
				LocalizacionBean localizacion = entidadBeneficiaria.getEntidad().getLocalizacion();
				EntidadBean entidad = entidadBeneficiaria.getEntidad();
	
				if(localizacion.getId()==null) localizacionDao.save(localizacion);
				entidad.setIdLocalizacion(localizacion.getId());
				
				//Defaults para los campos obligatorios
				entidad.setBeneficiaria(Boolean.TRUE);
				if(entidad.getActivo()==null) entidad.setActivo(true);
				if(entidad.getEvaluadora()==null) entidad.setEvaluadora(false);
				if(entidad.getBancaria()==null) entidad.setBancaria(false);
				
				if(entidad.getId()==null) entidadDao.save(entidad);
			}
			//Localizacion
			{
				LocalizacionBean localizacion = entidadBeneficiaria.getLocalizacionEconomica();
	
				if(localizacion.getId()==null) localizacionDao.save(localizacion);
				entidadBeneficiaria.setIdLocalizacionEconomica(localizacion.getId());
			}
			//Empleo permanente
			{
				EmpleoPermanenteBean empleoPermanente = entidadBeneficiaria.getEmpleoPermanente();
				if(empleoPermanente==null) empleoPermanente = new EmpleoPermanenteBean();
				if(empleoPermanente.getId()==null) empleoPermanenteDao.save(empleoPermanente);
				entidadBeneficiaria.setEmpleoPermanente(empleoPermanente);
				entidadBeneficiaria.setIdEmpleoPermanente(empleoPermanente.getId());
			}
			if(entidadBeneficiaria.getId()==null)entidadBeneficiariaDao.save(entidadBeneficiaria);
			return entidadBeneficiaria;
		} 
	}
	
	/**
	 * Toma un bean original y uno con datos nuevos. Actualiza los campos del original
	 * con los no nulos del objeto nuevo. Si el nuevo esta persistido lo usa directamente.
	 * Si el original es nulo usa el nuevo.
	 * @param <T>
	 * @param original
	 * @param nuevo
	 * @return
	 */
	private <T> T updateBean(T original, T nuevo) {
		if(original==null) return nuevo;
		if(nuevo==null) return original;
		Long idNuevo = BeanUtils.getId(nuevo);
		//Si esta persistido el nuevo lo uso directamente
		if(idNuevo!=null) return nuevo;
		//No esta persistido. Entonces actualizo
		try {
			BeanUtils.copyNotNullProperties(nuevo, original);
		}
		catch (ConversionException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		return original;
	}
	
	/**
	 * Busca una entidad que coincida con la dada. Si existe la actualiza y la 
	 * devuelve. Si no, devuelve la misma o una nueva con los datos de la dada.
	 * @throws EntidadBeneficiariaDatosInsuficientesException 
	 */
	public EntidadBeneficiariaBean obtenerEntidadBeneficiariaCoincidente(
				EntidadBeneficiariaBean entidadBeneficiaria
			) throws 
				EntidadBeneficiariaDatosInsuficientesException,
				NombreNoCoincideException,
				CuitNoCoincideException
				{
		if(entidadBeneficiaria.getId()==null) {
			//Intento encontrar la institucion a partir del Cuit
			List<EntidadBean> entidades;
			if(!Util.isBlank(entidadBeneficiaria.getCuit())) {
				entidades = entidadDao.findByCuit(entidadBeneficiaria.getCuit());
				if(entidades.size()==1) {
					EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidades.get(0).getId());
					entidadBeneficiariaOriginal = actualizarEntidadBeneficiaria(entidadBeneficiaria, entidadBeneficiariaOriginal);
					return entidadBeneficiariaOriginal;
				} 
			}
			
			//Busco por nombre, ya sea porque no hay cuit o porque no encontre entidades con el mismo cuit.
			if(!Util.isBlank(entidadBeneficiaria.getDenominacion())) {
				entidades = entidadDao.findByName(entidadBeneficiaria.getDenominacion());
				if(entidades.size()==1) {
					EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidades.get(0).getId());
					entidadBeneficiariaOriginal = actualizarEntidadBeneficiaria(entidadBeneficiaria, entidadBeneficiariaOriginal);
					return entidadBeneficiariaOriginal;
				} 
				if(entidades.size()>1) {
					//Encontre demasiadas coincidencias como para saber que entidad es
					throw new EntidadBeneficiariaDatosInsuficientesException(entidadBeneficiaria.getDenominacion());
				}
			} else throw new EntidadBeneficiariaDatosInsuficientesException(entidadBeneficiaria.getDenominacion());
			
			return entidadBeneficiaria;
						
		} return entidadBeneficiaria;
	}
	
	private EntidadBeneficiariaBean actualizarEntidadBeneficiaria(EntidadBeneficiariaBean entidadBeneficiaria, EntidadBeneficiariaBean entidadBeneficiariaOriginal) {
		EntidadBean entidadOriginal = entidadBeneficiariaOriginal.getEntidad();
		LocalizacionBean localizacionEconomicaOriginal = entidadBeneficiariaOriginal.getLocalizacionEconomica();
		EmpleoPermanenteBean empleoPermanenteOriginal = entidadBeneficiariaOriginal.getEmpleoPermanente();

		entidadBeneficiariaOriginal = updateBean(entidadBeneficiariaOriginal, entidadBeneficiaria);
		
		//Entidad
		{
			LocalizacionBean localizacionOriginal = entidadOriginal.getLocalizacion();

			entidadOriginal = updateBean(entidadOriginal, entidadBeneficiaria.getEntidad());
			
			localizacionOriginal = updateBean(localizacionOriginal, entidadBeneficiaria.getEntidad().getLocalizacion());
			if(localizacionOriginal==null) localizacionOriginal = new LocalizacionBean();
			entidadOriginal.setLocalizacion(localizacionOriginal);
			
			entidadBeneficiariaOriginal.setEntidad(entidadOriginal);
		}				
		//Localizacion economica
		{
			localizacionEconomicaOriginal = updateBean(localizacionEconomicaOriginal, entidadBeneficiaria.getLocalizacionEconomica());
			if(localizacionEconomicaOriginal==null) localizacionEconomicaOriginal = new LocalizacionBean();

			entidadBeneficiariaOriginal.setLocalizacionEconomica(localizacionEconomicaOriginal);
		}
		//Empleo permanente
		{
			empleoPermanenteOriginal = updateBean(empleoPermanenteOriginal, entidadBeneficiaria.getEmpleoPermanente());
			if(empleoPermanenteOriginal==null) empleoPermanenteOriginal = new EmpleoPermanenteBean();

			entidadBeneficiariaOriginal.setEmpleoPermanente(empleoPermanenteOriginal);
		}
		return entidadBeneficiariaOriginal;
	}
	
	public Set<EntidadBeneficiariaBean> obtenerEntidadesCoincidentes(EntidadBeneficiariaBean entidadBeneficiaria) {
		Set<EntidadBean> entidades = new HashSet<EntidadBean>();
		if(entidadBeneficiaria.getCuit()!=null) {
			entidades.addAll(entidadDao.findByCuit(entidadBeneficiaria.getCuit()));
		}
		if(entidadBeneficiaria.getDenominacion()!=null) {
			entidades.addAll(entidadDao.findByName(entidadBeneficiaria.getDenominacion()));
		}
			
		Set<EntidadBeneficiariaBean> entidadesBeneficiarias = new HashSet<EntidadBeneficiariaBean>();
		for(EntidadBean entidad : entidades) {
			if(entidad.getBeneficiaria()) {
				EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidad.getId());
				entidadesBeneficiarias.add(entidadBeneficiariaOriginal);
			}
		}
		return entidadesBeneficiarias;
	}
	public Set<EntidadBeneficiariaBean> obtenerEntidadesCoincidentes(String denominacion, String cuit) {
		Collection<EntidadBean> entidadesPorCuit = new ArrayList<EntidadBean>(1);
		Collection<EntidadBean> entidadesPorDenominacion = new ArrayList<EntidadBean>(1);
		
		if(StringUtil.isNotEmpty(cuit)) {
			entidadesPorCuit = entidadDao.findByCuit(cuit);
		}
		if(StringUtil.isNotEmpty(denominacion)) {
			entidadesPorDenominacion = entidadDao.findByName(denominacion);
		}
		
		Set<EntidadBeneficiariaBean> entidadesBeneficiariasPorCuit = new HashSet<EntidadBeneficiariaBean>();
		for(EntidadBean entidad : entidadesPorCuit) {
			if(entidad.getBeneficiaria()) {
				EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidad.getId());
				entidadesBeneficiariasPorCuit.add(entidadBeneficiariaOriginal);
			}
		}
		Set<EntidadBeneficiariaBean> entidadesBeneficiariasPorDenominacion = new HashSet<EntidadBeneficiariaBean>();
		for(EntidadBean entidad : entidadesPorDenominacion) {
			if(entidad.getBeneficiaria()) {
				EntidadBeneficiariaBean entidadBeneficiariaOriginal = entidadBeneficiariaDao.read(entidad.getId());
				entidadesBeneficiariasPorDenominacion.add(entidadBeneficiariaOriginal);
			}
		}
		Set<EntidadBeneficiariaBean> bestMatch = CollectionUtils.intersect(entidadesBeneficiariasPorCuit, entidadesBeneficiariasPorDenominacion);
		if(!bestMatch.isEmpty()) return bestMatch;
		else return CollectionUtils.union(entidadesBeneficiariasPorCuit, entidadesBeneficiariasPorDenominacion);
	}
}
