package com.fontar.bus.impl.configuracion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.CuitNoConcuerdaException;
import com.fontar.bus.impl.ObjetoEnUsoException;
import com.fontar.bus.impl.PersonaDatosInsuficientesException;
import com.fontar.data.api.dao.ComisionDAO;
import com.fontar.data.api.dao.ComposicionDAO;
import com.fontar.data.api.dao.EmpleoPermanenteDAO;
import com.fontar.data.api.dao.EntidadBancariaDAO;
import com.fontar.data.api.dao.EntidadBeneficiariaDAO;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadEvaluadoraDAO;
import com.fontar.data.api.dao.EvaluadorDAO;
import com.fontar.data.api.dao.ExpedienteMovimientoDAO;
import com.fontar.data.api.dao.FacturacionDAO;
import com.fontar.data.api.dao.LocalizacionDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProcedimientoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.api.dao.RendicionCuentasDAO;
import com.fontar.data.api.dao.Rol;
import com.fontar.data.impl.assembler.ComisionAssembler;
import com.fontar.data.impl.assembler.EmpleoPermanenteAssembler;
import com.fontar.data.impl.assembler.EntidadAssembler;
import com.fontar.data.impl.assembler.EntidadBeneficiariaAssembler;
import com.fontar.data.impl.assembler.EntidadEvaluadoraAssembler;
import com.fontar.data.impl.assembler.EvaluadorAssembler;
import com.fontar.data.impl.assembler.LocalizacionAssembler;
import com.fontar.data.impl.assembler.PersonaAssembler;
import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.bean.ComposicionBean;
import com.fontar.data.impl.domain.bean.EntidadBancariaBean;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.bean.ExpedienteMovimientoBean;
import com.fontar.data.impl.domain.bean.FacturacionBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.dto.ComisionDTO;
import com.fontar.data.impl.domain.dto.EntidadBeneficiariaDTO;
import com.fontar.data.impl.domain.dto.EntidadDTO;
import com.fontar.data.impl.domain.dto.EntidadEvaluadoraDTO;
import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.fontar.data.impl.domain.dto.FacturacionDTO;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.pragma.util.CollectionUtils;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.util.exception.IllegalStateException;
import com.pragma.util.hibernate.HibernateUtil;

public class ConfiguracionServicioImpl implements ConfiguracionServicio {

	private RendicionCuentasDAO rendicionCuentasDao;
	private ProyectoRaizDAO proyectoRaizDao;
	private ExpedienteMovimientoDAO expedienteMovimientoDao;
	private PersonaDAO personaDAO;
	private EvaluadorDAO evaluadorDAO;
	private LocalizacionDAO localizacionDAO;
	private ComisionDAO comisionDAO;
	private EntidadDAO entidadDAO;
	private FacturacionDAO facturacionDAO;
	private ComposicionDAO composicionDAO;
	private EntidadEvaluadoraDAO entidadEvaluadoraDAO;
	private EmpleoPermanenteDAO empleoPermanenteDAO;
	private EntidadBeneficiariaDAO entidadBeneficiariaDAO;
	private EntidadBancariaDAO entidadBancariaDAO;
	private ProcedimientoDAO procedimientoDao;

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.ConfiguracionServicio#setLocalizacionDAO(com.fontar.data.api.dao.LocalizacionDAO)
	 */
	public void setComisionDAO(ComisionDAO comisionDAO) {
		this.comisionDAO = comisionDAO;
	}
	public void setLocalizacionDAO(LocalizacionDAO localizacionDAO) {
		this.localizacionDAO = localizacionDAO;
	}
	public void setEntidadEvaluadoraDAO(EntidadEvaluadoraDAO entidadEvaluadoraDAO) {
		this.entidadEvaluadoraDAO = entidadEvaluadoraDAO;
	}
	public void setEntidadBeneficiariaDAO(EntidadBeneficiariaDAO entidadBeneficiariaDAO) {
		this.entidadBeneficiariaDAO = entidadBeneficiariaDAO;
	}
	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}
	public void setEntidadBancariaDAO(EntidadBancariaDAO entidadBancariaDAO) {
		this.entidadBancariaDAO = entidadBancariaDAO;
	}

	public void savePersona(PersonaDTO personaDTO) {
		if ((personaDTO.getId() == null) || (personaDTO.getId() == 0)) {
			createPersona(personaDTO);
		} else {
			updatePersona(personaDTO);
		}
	}
	private void updatePersona(PersonaDTO personaDTO) {
		PersonaBean personaBean = null;
		EvaluadorBean evaluadorBean;
		// alta
		personaBean = personaDAO.read(personaDTO.getId());
		
		if (personaBean.getEsEvaluador()) {
			/*
			 * Es evaluador. Si lo sigue siendo actualizo los datos
			 * de su evaluadorBean. Si no, borro el evaluadorBean.
			 */
			evaluadorBean = evaluadorDAO.read(personaDTO.getId());
			
			if(personaDTO.getEsEvaluador()) {
				//Sigue siendo evaluador
				EvaluadorAssembler.getInstance().updateBeanNotNull(evaluadorBean, personaDTO.getEvaluadorDTO());
				evaluadorDAO.update(evaluadorBean);
				actualizarEntidadDesemp(evaluadorBean, personaDTO.getEvaluadorDTO().getIdEntidadesDesemp());
			} else {
				//ya no es mas evaluador
				actualizarEntidadDesemp(evaluadorBean, null);
				evaluadorDAO.delete(evaluadorBean);
			}
		} else {
			/*
			 * No era evaluador. Puede empezar a serlo.
			 */
			if(personaDTO.getEsEvaluador()) {
				/*
				 * Empieza a ser evaluador. Tengo que crear o resucitar un evaluadorBean.
				 * (Puede haber habido un borrado logico)
				 */
				evaluadorBean = evaluadorDAO.read(personaBean.getId());
				if(evaluadorBean==null) {
					evaluadorBean = new EvaluadorBean();
					EvaluadorAssembler.getInstance().updateBeanNotNull(evaluadorBean, personaDTO.getEvaluadorDTO());
					evaluadorBean.setId(personaBean.getId());
					evaluadorBean.setPersona(personaBean);
					evaluadorDAO.create(evaluadorBean);
				} else {
					//Resurrección del evaluador
					EvaluadorAssembler.getInstance().updateBeanNotNull(evaluadorBean, personaDTO.getEvaluadorDTO());
					evaluadorBean.setBorrado(false);
					evaluadorBean.setPersona(personaBean);
					evaluadorDAO.update(evaluadorBean);
				}
				actualizarEntidadDesemp(evaluadorBean, personaDTO.getEvaluadorDTO().getIdEntidadesDesemp());
			} else {
				//No era evaluador ni lo va a ser. Nada que hacer en este caso
			}
		}
		
		PersonaAssembler.getInstance().updateBeanNotNull(personaBean, personaDTO);
		if (personaDTO.getLocalizacion() != null) {
			LocalizacionAssembler.getInstance().updateBeanNotNull(personaBean.getLocalizacion(), personaDTO.getLocalizacion());
			personaBean.getLocalizacion().setId(personaBean.getIdLocalizacion());
			if(personaBean.getLocalizacion().getId()==null) localizacionDAO.save(personaBean.getLocalizacion());
			else localizacionDAO.update(personaBean.getLocalizacion());
			personaBean.setIdLocalizacion(personaBean.getLocalizacion().getId());
		}
	
		personaDAO.update(personaBean);
	}
	private void createPersona(PersonaDTO personaDTO) {
		PersonaBean personaBean = null;
		EvaluadorBean evaluadorBean;
		// alta
		personaBean = PersonaAssembler.getInstance().buildBean(personaDTO);
		localizacionDAO.save(personaBean.getLocalizacion());
		personaBean.setIdLocalizacion(personaBean.getLocalizacion().getId());

		if (personaBean.getEsEvaluador()) {
			evaluadorBean = EvaluadorAssembler.getInstance().buildBean(personaDTO.getEvaluadorDTO());
			evaluadorBean.setPersona(personaBean);
			evaluadorBean.setId(personaBean.getId());
			evaluadorDAO.save(evaluadorBean);
			if(personaDTO.getEvaluadorDTO().getIdEntidadesDesemp() != null) { 
				EntidadEvaluadoraBean eeBean = (EntidadEvaluadoraBean) entidadEvaluadoraDAO.read(personaDTO.getEvaluadorDTO().getIdEntidadesDesemp()); 
				eeBean.getEvaluadores().add(evaluadorBean); 
				entidadEvaluadoraDAO.update(eeBean); 
			} 
		}

		personaDAO.save(personaBean);
	}

	public void saveComision(ComisionDTO comisionDTO) {
		ComisionBean comisionBean = ComisionAssembler.getInstance().buildBean(comisionDTO);

		if (comisionBean.getId() == null || comisionBean.getId() == 0) {
			comisionDAO.save(comisionBean);
		}
		else {
			comisionDAO.update(comisionBean);
		}
	}

	public PersonaDTO obtenerPersona(Long idPersona) {
		return PersonaAssembler.getInstance().buildDto(personaDAO.read(idPersona));
	}

	public EvaluadorDTO obtenerEvaluador(Long id) {
		EvaluadorDTO evaluadorDTO = EvaluadorAssembler.getInstance().buildDto(evaluadorDAO.read(id));
	//	evaluadorDTO.setIdEntidadesDesemp(new Long(4));
		return evaluadorDTO;
	}
	
	public ComisionDTO obtenerComision(Long idComision) {
		return ComisionAssembler.getInstance().buildDto(comisionDAO.read(idComision));
	}

	public void saveEntidad(EntidadDTO entidadDTO) throws CuitNoConcuerdaException {
		EntidadBean entidadBean;
		EntidadEvaluadoraBean entidadEvaluadoraBean;
		EntidadBeneficiariaBean entidadBeneficiariaBean;
		EntidadBancariaBean entidadBancariaBean;
		
		/*
		 * Este chequeo ya se hace (lamentablemente) en la accion.
		//En caso de que sea entidad beneficiaria chequeo que el cuit se corresponda con el tipo
		//de empresa que es. El cuit puede estar vacio.
		if(Boolean.TRUE.equals(entidadDTO.getBeneficiaria()) && !StringUtils.isEmpty(entidadDTO.getCuit())) {
			EntidadBeneficiariaDTO entidadBeneficiaria = entidadDTO.getEntidadBeneficiaria();
			if(TipoEmpresa.UNIPERSONAL.name().equals(entidadBeneficiaria.getTipoEmpresa())) {
				//El cuit debe ser de persona
				if(!Cuit.esCuitDePersonaValido(entidadDTO.getCuit())) {
					throw new CuitNoConcuerdaException(); 
				}
			} else {
				//El cuit debe ser de una entidad
				if(!Cuit.esCuitDeEntidadValido(entidadDTO.getCuit())) {
					throw new CuitNoConcuerdaException(); 
				}
			}
		}*/

		// guardar Entidad si no existe, osea agregar
		if ((entidadDTO.getId() == null) || (entidadDTO.getId() == 0)) {
			entidadBean = EntidadAssembler.getInstance().buildBean(entidadDTO);
			if (!entidadDTO.getLocalizacion().isEmpty()) {
				localizacionDAO.save(entidadBean.getLocalizacion());
				entidadBean.setIdLocalizacion(entidadBean.getLocalizacion().getId());
			}
			// check si el Usuario quiere guardar una Evaluadora -> guardalo con
			// la misma id como entidad
			if (entidadBean.getEvaluadora()) {
				entidadEvaluadoraBean = EntidadEvaluadoraAssembler.getInstance().buildBean(entidadDTO.getEntidadEvaluadora());
				entidadEvaluadoraBean.setEntidad(entidadBean);
				entidadEvaluadoraBean.setId(entidadBean.getId());
				entidadEvaluadoraDAO.save(entidadEvaluadoraBean);
			}

			// check si el Usuario quiere guardar una Beneficiaria -> guardalo
			// con la misma id como entidad
			if (entidadBean.getBeneficiaria()) {
				entidadBeneficiariaBean = EntidadBeneficiariaAssembler.getInstance().buildBean(entidadDTO.getEntidadBeneficiaria());
				if (!entidadDTO.getEntidadBeneficiaria().getLocalizacionEconomica().isEmpty()) {
					localizacionDAO.save(entidadBeneficiariaBean.getLocalizacionEconomica());
					entidadBeneficiariaBean.setIdLocalizacionEconomica(entidadBeneficiariaBean.getLocalizacionEconomica().getId());
				}

				// check cual tipo -> Empresa tiene empleo Permanente y
				// Facturacion
				if ((entidadBeneficiariaBean.getTipo().equals("EMPRESA")) && (entidadBeneficiariaBean.getEmpleoPermanente() != null)) { 
					empleoPermanenteDAO.save(entidadBeneficiariaBean.getEmpleoPermanente());
					entidadBeneficiariaBean.setIdEmpleoPermanente(entidadBeneficiariaBean.getEmpleoPermanente().getId());
				}
				entidadBeneficiariaBean.setEntidad(entidadBean);
				entidadBeneficiariaBean.setId(entidadBean.getId());
				entidadBeneficiariaDAO.save(entidadBeneficiariaBean);

				if ((entidadBeneficiariaBean.getTipo().equals("EMPRESA"))
						&& (entidadDTO.getEntidadBeneficiaria().getFacturacionDTO() != null)) {

					String[] periodico = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroPeriodico();
					String[] facturacion = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroFacturacion();

					// alta de facturación
					for (int i = 0; i < periodico.length; i++) {

						FacturacionBean facturacionBean = new FacturacionBean();
						facturacionBean.setIdEntidadBeneficiaria((entidadBeneficiariaBean.getId()));

						facturacionBean.setNumeroPeriodico(new Integer(periodico[i]));
						facturacionBean.setNumeroFacturacion(new BigDecimal(facturacion[i]));

						facturacionDAO.save(facturacionBean);
					}
				}
				String[] idEntidades = entidadDTO.getEntidadBeneficiaria().getIdEntidades();
				if (idEntidades != null) {
					for (int i = 0; i < idEntidades.length; i++) {
					
						ComposicionBean composicionBean = new ComposicionBean();
						composicionBean.setIdEntidadBeneficiaria((entidadBeneficiariaBean.getId()));
					
						composicionBean.setIdEntidad(new Long(idEntidades[i]));
					
						composicionDAO.save(composicionBean);
					}
				}
			}

			// editar, update de Entidad
		}
		else {
			entidadBean = entidadDAO.read(entidadDTO.getId());
			EntidadAssembler.getInstance().updateBeanNotNull(entidadBean, entidadDTO);
			if (!entidadDTO.getLocalizacion().isEmpty()) {
				LocalizacionAssembler.getInstance().updateBeanNotNull(entidadBean.getLocalizacion(), entidadDTO.getLocalizacion());
				entidadBean.getLocalizacion().setId(entidadBean.getIdLocalizacion());
				localizacionDAO.update(entidadBean.getLocalizacion());
			}
			// buscar Evaludaora de la base de datos con la id de Entidad
			entidadEvaluadoraBean = entidadEvaluadoraDAO.read(entidadDTO.getId());

			// check si el Usuario quiere guardar una Evaluadora
			if (entidadBean.getEvaluadora()) {
				// check si no existe una en la base de datos -> guardalo con la
				// id de Entidad
				if (entidadEvaluadoraBean == null) {
					entidadEvaluadoraBean = EntidadEvaluadoraAssembler.getInstance().buildBean(entidadDTO.getEntidadEvaluadora());
					entidadEvaluadoraBean.setEntidad(entidadBean);
					entidadEvaluadoraBean.setId(entidadBean.getId());
					entidadEvaluadoraDAO.save(entidadEvaluadoraBean);
				}
				// si no update la Evaluadora
				else {
					EntidadEvaluadoraAssembler.getInstance().updateBeanNotNull(entidadEvaluadoraBean, entidadDTO.getEntidadEvaluadora());
					entidadEvaluadoraDAO.update(entidadEvaluadoraBean);
				}
			}
			// no quiere Guardar
			else {
				// check si existia una antes
				if (entidadEvaluadoraBean != null) {
					// borralo
					entidadEvaluadoraDAO.delete(entidadEvaluadoraBean);
				}
			}

			// buscar Beneficiaria con la id de Entidad en la base de datos
			entidadBeneficiariaBean = entidadBeneficiariaDAO.read(entidadDTO.getId());

			// check si el Usuario quiere guardar una Beneficiaria
			if (entidadBean.getBeneficiaria()) {
				// check si no habia una antes -> guardalo con la id de Entidad
				if (entidadBeneficiariaBean == null) {
					entidadBeneficiariaBean = EntidadBeneficiariaAssembler.getInstance().buildBean(entidadDTO.getEntidadBeneficiaria());
					if (!entidadDTO.getEntidadBeneficiaria().getLocalizacionEconomica().isEmpty()) {
						localizacionDAO.save(entidadBeneficiariaBean.getLocalizacionEconomica());
						entidadBeneficiariaBean.setIdLocalizacionEconomica(entidadBeneficiariaBean.getLocalizacionEconomica().getId());
					}
					if ((entidadBeneficiariaBean.getTipo().equals("EMPRESA")) && (entidadBeneficiariaBean.getEmpleoPermanente() != null)) {
						empleoPermanenteDAO.save(entidadBeneficiariaBean.getEmpleoPermanente());
						entidadBeneficiariaBean.setIdEmpleoPermanente(entidadBeneficiariaBean.getEmpleoPermanente().getId());
					}
					entidadBeneficiariaBean.setEntidad(entidadBean);
					entidadBeneficiariaBean.setId(entidadBean.getId());
					entidadBeneficiariaDAO.save(entidadBeneficiariaBean);

					if ((entidadBeneficiariaBean.getTipo().equals("EMPRESA"))
							&& (entidadDTO.getEntidadBeneficiaria().getFacturacionDTO() != null)) {
						String[] periodico = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroPeriodico();
						String[] facturacion = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroFacturacion();

						for (int i = 0; i < periodico.length; i++) {

							FacturacionBean facturacionBean = new FacturacionBean();

							facturacionBean.setIdEntidadBeneficiaria((entidadDTO.getId()));

							facturacionBean.setNumeroPeriodico(new Integer(periodico[i]));
							facturacionBean.setNumeroFacturacion(new BigDecimal(facturacion[i]));
							facturacionDAO.save(facturacionBean);
						}
					}
					
					String[] idEntidades = entidadDTO.getEntidadBeneficiaria().getIdEntidades();
					
					if (idEntidades != null) {
						for (int i = 0; i < idEntidades.length; i++) {
							
							ComposicionBean composicionBean = new ComposicionBean();
							composicionBean.setIdEntidadBeneficiaria((entidadBeneficiariaBean.getId()));
							
							composicionBean.setIdEntidad(new Long(idEntidades[i]));
							
							composicionDAO.save(composicionBean);
						}
					}
				}
				// si no updatela
				else {
					entidadBeneficiariaBean.setIdTributaria(null);
					entidadBeneficiariaBean.setEmerix(null);
					EntidadBeneficiariaAssembler.getInstance().updateBeanNotNull(entidadBeneficiariaBean, entidadDTO.getEntidadBeneficiaria());
					if (!entidadDTO.getEntidadBeneficiaria().getLocalizacionEconomica().isEmpty()) {
						LocalizacionAssembler.getInstance().updateBeanNotNull(entidadBeneficiariaBean.getLocalizacionEconomica(), entidadDTO.getEntidadBeneficiaria().getLocalizacionEconomica());
						entidadBeneficiariaBean.getLocalizacionEconomica().setId(entidadBeneficiariaBean.getIdLocalizacionEconomica());
						localizacionDAO.update(entidadBeneficiariaBean.getLocalizacionEconomica());
					}
					if ((entidadBeneficiariaBean.getTipo().equals("EMPRESA"))  && (entidadBeneficiariaBean.getEmpleoPermanente() != null)) {
						EmpleoPermanenteAssembler.getInstance().updateBeanNotNull(entidadBeneficiariaBean.getEmpleoPermanente(), entidadDTO.getEntidadBeneficiaria().getEmpleoPermanente());
						entidadBeneficiariaBean.getEmpleoPermanente().setId(entidadBeneficiariaBean.getIdEmpleoPermanente());
						empleoPermanenteDAO.update(entidadBeneficiariaBean.getEmpleoPermanente());
					}
					entidadBeneficiariaDAO.update(entidadBeneficiariaBean);

					if (entidadDTO.getEntidadBeneficiaria().getFacturacionDTO() != null) {
						List facturacionList = facturacionDAO.findByID(entidadBean.getId());

						String[] periodico = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroPeriodico();
						String[] facturacion = entidadDTO.getEntidadBeneficiaria().getFacturacionDTO().getNumeroFacturacion();

						// check si existe el año de la BD todavia
						if (!(facturacionList.isEmpty())) {
							for (int i = 0; i < facturacionList.size(); i++) {
								FacturacionBean facturacionBean = new FacturacionBean();
								facturacionBean = (FacturacionBean) facturacionList.get(i);
								Boolean exist = false;
								if (periodico != null) {
									for (int j = 0; j < periodico.length && !exist; j++) {
										if (facturacionBean.getNumeroPeriodico().toString().equals(periodico[j])) {
											exist = true;
										}
									}
								}
								if (!exist) {
									facturacionDAO.delete(facturacionBean);
								}
							}
						}
						if (periodico != null) {
							for (int i = 0; i < periodico.length; i++) {
	
								FacturacionBean facturacionBean = new FacturacionBean();
								facturacionBean.setIdEntidadBeneficiaria((entidadBean.getId()));
	
								facturacionBean.setNumeroPeriodico(new Integer(periodico[i]));
								facturacionBean.setNumeroFacturacion(new BigDecimal(facturacion[i]));
	
								Boolean exist = false;
								// check si existe el año ingresado para updatele o
								// crearle
								for (int j = 0; j < facturacionList.size() && !exist; j++) {
									FacturacionBean bean = (FacturacionBean) facturacionList.get(j);
									if (periodico[i].equals(bean.getNumeroPeriodico().toString())) {
										// ya existe en la BD, actualizo el monto
										bean.setNumeroFacturacion(new BigDecimal(facturacion[i]));
										facturacionDAO.update(bean);
										exist = true;
									}
								}
	
								if (!exist) {
									facturacionDAO.save(facturacionBean);
								}
							}
						}
					}
					List composicionList = composicionDAO.findByID(entidadBean.getId());

					String[] idEntidades = entidadDTO.getEntidadBeneficiaria().getIdEntidades();

					// check si existe el año de la BD todavia
					if (!(composicionList.isEmpty())) {
						for (int i = 0; i < composicionList.size(); i++) {
							ComposicionBean composicionBean = new ComposicionBean();
							composicionBean = (ComposicionBean) composicionList.get(i);
							Boolean exist = false;
							if (idEntidades != null) {
								for (int j = 0; j < idEntidades.length && !exist; j++) {
									if (composicionBean.getIdEntidad().toString().equals(idEntidades[j])) {
										exist = true;
									}
								}
							}
							if (!exist) {
								composicionDAO.delete(composicionBean);
							}
						}
					}
					if (idEntidades != null) {
						for (int i = 0; i < idEntidades.length; i++) {
	
							ComposicionBean composicionBean = new ComposicionBean();
							composicionBean.setIdEntidadBeneficiaria((entidadBean.getId()));
	
							composicionBean.setIdEntidad(new Long(idEntidades[i]));
	
							Boolean exist = false;
							// check si existe el año ingresado para updatele o
							// crearle
							for (int j = 0; j < composicionList.size() && !exist; j++) {
								ComposicionBean bean = (ComposicionBean) composicionList.get(j);
								if (idEntidades[i].equals(bean.getIdEntidad().toString())) {
									// ya existe en la BD, actualizo el monto
									bean.setIdEntidad(new Long(idEntidades[i]));
									composicionDAO.update(bean);
									exist = true;
								}
							}
	
							if (!exist) {
								composicionDAO.save(composicionBean);
							}
						}
					}
				}
			}

			// el Usuario no quiere guardar una Beneficiaria
//			else {
//				// check si existia una Beneficiaria con la id de Entidad ->
//				// borrarlo y despues sus hijos
//				if (entidadBeneficiariaBean != null) {
//					LocalizacionBean bean = new LocalizacionBean(); 
//					bean = entidadBeneficiariaBean.getLocalizacionEconomica();
//					EmpleoPermanenteBean empleo = new EmpleoPermanenteBean();
//					List composicionList = composicionDAO.findByID(entidadDTO.getId());
//					for (int i = 0; i < composicionList.size(); i++) {
//						ComposicionBean composicionBean = new ComposicionBean();
//						composicionBean = (ComposicionBean) composicionList.get(i);
//						composicionDAO.delete(composicionBean);
//					}
//					if (entidadBeneficiariaBean.getTipo().equals("EMPRESA")) {
//						empleo = entidadBeneficiariaBean.getEmpleoPermanente();
//						List facturacionList = facturacionDAO.findByID(entidadDTO.getId());
//						for (int i = 0; i < facturacionList.size(); i++) {
//							FacturacionBean facturacionBean = new FacturacionBean();
//							facturacionBean = (FacturacionBean) facturacionList.get(i);
//							facturacionDAO.delete(facturacionBean);
//						}
//					}
//					entidadBeneficiariaDAO.delete(entidadBeneficiariaBean);
//					if (bean != null) {
//						localizacionDAO.delete(bean);
//					}
//					if (empleo != null) {
//						if (empleo.getId() != null) {
//							empleoPermanenteDAO.delete(empleo);
//						}
//					}
//				}
//			}
		}

		entidadBancariaBean =  entidadDTO.getId() != null ? entidadBancariaDAO.read(entidadDTO.getId() ): null;
		if (entidadBean.getBancaria()) {
			if (entidadBancariaBean == null) {
				entidadBancariaBean = new EntidadBancariaBean();
				entidadBancariaBean.setEntidad(entidadBean);
				entidadBancariaBean.setId(entidadBean.getId());
				entidadBancariaDAO.save(entidadBancariaBean);
			}
			else {
				entidadBancariaBean.setEntidad(entidadBean);
				entidadBancariaBean.setId(entidadBean.getId());
				entidadBancariaDAO.update(entidadBancariaBean);
			}
		}
//		else { 
//			if (entidadBancariaBean != null) {
//				entidadBancariaDAO.delete(entidadBancariaBean);
//				
//			}
//		}
		
		// Entidad
		if ((entidadBean.getId() == null) || (entidadBean.getId() == 0)) {
			entidadDAO.save(entidadBean);
		}
		else {
			entidadDAO.update(entidadBean);
		}
	}

	public EntidadDTO obtenerEntidad(Long idEntidad) {
		EntidadBean eBean = entidadDAO.read(idEntidad);
		EntidadDTO eDto = EntidadAssembler.getInstance().buildDto(eBean);
		FacturacionDTO facturacionDTO = new FacturacionDTO();
		FacturacionBean facturacionBean = new FacturacionBean();
		ComposicionBean composicionBean = new ComposicionBean();

		if (eBean.getEvaluadora()) {
			EntidadEvaluadoraBean eeBean = entidadEvaluadoraDAO.read(idEntidad);
			EntidadEvaluadoraDTO eeDto = EntidadEvaluadoraAssembler.getInstance().buildDto(eeBean);
			eDto.setEntidadEvaluadora(eeDto);
		}
		if (eBean.getBeneficiaria()) {
			EntidadBeneficiariaBean ebBean = entidadBeneficiariaDAO.read(idEntidad);
			if ("EMPRESA".equals(ebBean.getTipo())) {
				List facturacionList = facturacionDAO.findByID(idEntidad);
				String[] periodico = new String[facturacionList.size()];
				String[] facturacion = new String[facturacionList.size()];
				if (facturacionList.size() > 0) {
					for (int i = 0; i < facturacionList.size(); i++) {
						facturacionBean = (FacturacionBean) facturacionList.get(i);
						periodico[i] = facturacionBean.getNumeroPeriodico().toString();
						facturacion[i] = facturacionBean.getNumeroFacturacion().toString();
					}
					facturacionDTO.setNumeroPeriodico(periodico);
					facturacionDTO.setNumeroFacturacion(facturacion);
					facturacionDTO.setId(facturacionBean.getId());
					facturacionDTO.setIdEntidadBeneficiaria(facturacionBean.getIdEntidadBeneficiaria());
				}
			}
			List composicionList = composicionDAO.findByID(idEntidad);
			String[] idEntidades = new String[composicionList.size()];
			String[] cuits = new String[composicionList.size()];
			String[] denominaciones = new String[composicionList.size()];
			if (composicionList.size() > 0) {
				EntidadBean eHelp;
				for (int i=0; i < composicionList.size(); i++) {
					composicionBean = (ComposicionBean) composicionList.get(i);
					idEntidades[i] = composicionBean.getIdEntidad().toString();
					eHelp = entidadDAO.read(composicionBean.getIdEntidad());
					cuits[i] = eHelp.getCuit();
					denominaciones[i] = eHelp.getDenominacion();
				}
			}

			EntidadBeneficiariaDTO ebDto = EntidadBeneficiariaAssembler.getInstance().buildDto(ebBean);
			if (ebDto.getTipo().equals("EMPRESA")) {
				ebDto.setFacturacionDTO(facturacionDTO);
			}
			ebDto.setIdEntidades(idEntidades);
			ebDto.setCuits(cuits);
			ebDto.setDenominaciones(denominaciones);
			eDto.setEntidadBeneficiaria(ebDto);
		}
		return eDto;
	}

	public void setEntidadDAO(EntidadDAO entidadDAO) {
		this.entidadDAO = entidadDAO;
	}

	public void setEmpleoPermanenteDAO(EmpleoPermanenteDAO empleoPermanenteDAO) {
		this.empleoPermanenteDAO = empleoPermanenteDAO;
	}

	public void setEvaluadorDAO(EvaluadorDAO evaluadorDAO) {
		this.evaluadorDAO = evaluadorDAO;
	}

	public void setFacturacionDAO(FacturacionDAO facturacionDAO) {
		this.facturacionDAO = facturacionDAO;
	}

	public void setComposicionDAO(ComposicionDAO composicionDAO) {
		this.composicionDAO = composicionDAO;
	}

	public EntidadEvaluadoraBean obtenerEntidadDesemp(EvaluadorBean evaluador) {
		EntidadEvaluadoraBean entidad = null;
		EntidadEvaluadoraDAO dao  = (EntidadEvaluadoraDAO) ContextUtil.getBean("entidadEvaluadoraDao");
		List<EntidadEvaluadoraBean> entidades = dao.findByEvaluador(evaluador);
		if(!entidades.isEmpty()){
			if(entidades.size() > 1 )
				throw  new IllegalStateException("El evaluador esta referenciado por mas de una entidad evaluadora");
			else
				entidad = entidades.get(0);
		}
		return entidad;
	}
	/**
	 * Actualiza la vinculacion entre evaluador y entidad en la que se desempeña.
	 * @param evaluador
	 * @param entidadDesemp
	 */
	private void actualizarEntidadDesemp(EvaluadorBean evaluador , Long idEntidadNueva) {
		EntidadEvaluadoraDAO dao  = (EntidadEvaluadoraDAO) ContextUtil.getBean("entidadEvaluadoraDao");
		
		List<EntidadEvaluadoraBean> entidadesEnLasQueSeDesempeñaActualmente = dao.findByEvaluador(evaluador);
		for(EntidadEvaluadoraBean entidad : entidadesEnLasQueSeDesempeñaActualmente) {
			entidad.getEvaluadores().remove(evaluador);
			dao.update(entidad);
		}
		if(idEntidadNueva!=null) {
			EntidadEvaluadoraBean entidadNueva = dao.read(idEntidadNueva);
			entidadNueva.getEvaluadores().add(evaluador);
			dao.update(entidadNueva);
		}
	}

	public void createOrUpdatePersona(PersonaDTO dto) throws PersonaDatosInsuficientesException {
		if(dto.getId()!=null) savePersona(dto);
		else {
			//La identifico por otras propiedades
			if(dto.getCuit()!=null) {
				List<PersonaBean> personas = personaDAO.findByCuit(dto.getCuit());
				if(personas.size()==1) {
					dto.setId(personas.get(0).getId());
					savePersona(dto);
					return;
				}
				//No puede haber varios. Si hay cero busco por nombre
			}
	
			List<PersonaBean> personas = personaDAO.findByName(dto.getNombre());
			if(personas.size()==1) {
				dto.setId(personas.get(0).getId());
				savePersona(dto);
			} else {
				throw new PersonaDatosInsuficientesException(dto);
			}
		}
	}
	/**
	 * Obtiene la persona que coincide con los datos del dto dado. Si no encuentra
	 * una persona coincidente devuelve una nueva. Si encuentra mas de una coincidencia
	 * dispara una excepcion PersonaDatosInsuficientesException.
	 */
	public PersonaBean obtenerPersonaCoincidente(PersonaDTO dto) throws PersonaDatosInsuficientesException {
		if(dto.getId()!=null) return personaDAO.read(dto.getId());
		else {
			//La identifico por otras propiedades
			if(!StringUtil.isEmpty(dto.getCuit())) {
				List<PersonaBean> personas = personaDAO.findByCuit(dto.getCuit());
				if(personas.size()==1) {
					return personas.get(0);
				}
				//No puede haber varios. Si hay cero busco por nombre
			}
	
			List<PersonaBean> personas = personaDAO.findByName(dto.getNombre());
			if(personas.size()==1) {
				return personas.get(0);
			} else {
				if(personas.size()>1) {
					//demasiadas coincidencias. no se quien es.
					throw new PersonaDatosInsuficientesException(dto);
				} else {
					//No hay coincidencias. Creo una nueva
					PersonaBean bean = PersonaAssembler.getInstance().buildBean(dto);
					if(StringUtil.isEmpty(bean.getSexo()) || StringUtil.isEmpty(bean.getNombre()))							
						throw new PersonaDatosInsuficientesException(dto);
					if(bean.getActivo()==null) bean.setActivo(true);
					if(bean.getEsEvaluador()==null) bean.setEsEvaluador(false);
					return bean;
				}
			}
		}
	}
	public Set<PersonaBean> obtenerPersonasCoincidentes(String nombre, String cuit) {
		Collection<PersonaBean> personasConMismoCuit = new ArrayList<PersonaBean>(1);
		Collection<PersonaBean> personasConMismoNombre = new ArrayList<PersonaBean>(1);
		if(!StringUtil.isEmpty(cuit)) {
			personasConMismoCuit = personaDAO.findByCuit(cuit);
		}
		if(!StringUtil.isEmpty(nombre)) {
			personasConMismoNombre = personaDAO.findByName(nombre);
		}
		Set<PersonaBean> bestMatch = CollectionUtils.intersect(personasConMismoCuit, personasConMismoNombre);
		
		if(bestMatch.isEmpty()) return CollectionUtils.union(personasConMismoCuit, personasConMismoNombre);
		else return bestMatch;
	}
	public void deleteEntidad(Long idEntidad) throws ObjetoEnUsoException {
		Map<Rol, Collection<Object>> apariciones = entidadDAO.buscarUsosDeEntidad(idEntidad);
		if(apariciones.isEmpty()) {
			entidadDAO.delete(entidadDAO.read(idEntidad));
		} else {
			HashMap<Rol, Collection<String>> usos = new HashMap<Rol, Collection<String>>(); 
			for(Entry<Rol, Collection<Object>> entry : apariciones.entrySet()) {
				Collection<String> entidades = new ArrayList<String>();
				for(Object entidad : entry.getValue()) {
					if(entidad instanceof EvaluadorBean) {
						entidades.add(((EvaluadorBean)entidad).getPersona().getNombre());
					} else {
						if(entidad instanceof EntidadEvaluadoraBean) {
							entidades.add(((EntidadEvaluadoraBean)entidad).getDenominacion());
						} else {
							if(entidad instanceof ProyectoRaizBean) {
								entidades.add(((ProyectoRaizBean)entidad).getCodigo());
							} else {
								//No se que es.
								entidades.add(null);
							}
						}
					}
				}
				if(!entidades.isEmpty())usos.put(entry.getKey(), entidades);
			}
			throw new ObjetoEnUsoException(usos);
		}
	}
	public void deletePersona(Long idPersona) throws ObjetoEnUsoException {
		Map<Rol, Collection<String>> usos = new HashMap<Rol, Collection<String>>();
		
		//Me fijo si es evaluador
		List<EvaluadorBean> evaluadores = evaluadorDAO.findByIdDePersona(idPersona);
		if(evaluadores.size()>0) {
			usos.put(Rol.PERSONA_DE_EVALUADOR, new ArrayList<String>());
		}

		//Me fijo si es parte de un proyecto
		ArrayList<String> proyectos = new ArrayList<String>();
		ArrayList<String> ips = new ArrayList<String>();
		for(ProyectoRaizBean proyecto : proyectoRaizDao.findProyectosPorIdDePersonaParticipante(idPersona)) {
			if(proyecto instanceof IdeaProyectoBean || proyecto instanceof IdeaProyectoPitecBean) {
				ips.add(proyecto.getCodigo());
			} else {
				proyectos.add(proyecto.getCodigo());
			}
		}
		if(proyectos.size()>0) {
			usos.put(Rol.PARTICIPANTE_DE_PROYECTO, proyectos);
		}
		if(ips.size()>0) {
			usos.put(Rol.PARTICIPANTE_DE_IP, ips);
		}
		//Me fijo si hay registrado un movimiento de expediente.
		List<ExpedienteMovimientoBean> expedientes = expedienteMovimientoDao.findPorIdDePersona(idPersona);
		if(expedientes.size()>0) {
			usos.put(Rol.MOVIMIENTO_DE_EXPEDIENTE, new ArrayList<String>());
		}
		List<RendicionCuentasBean> rendiciones = rendicionCuentasDao.findRendicionesBeanPorPersona(idPersona);
		if(rendiciones.size()>0) {
			Collection<String> seguimientos = new HashSet<String>();
			for(RendicionCuentasBean rendicion : rendiciones) {
				seguimientos.add(rendicion.getSeguimiento().getDescripcion());
			}
			usos.put(Rol.INVOLUCRADO_EN_RENDICION_DEL_SEGUIMIENTO, seguimientos);
		}
		if(usos.isEmpty()) {
			personaDAO.delete(personaDAO.read(idPersona));
			for(EvaluadorBean evaluador : evaluadores) {
				// Borro su caracter de evaluador
				// Lo quito de las entidades en las que se desempeña
				List<EntidadEvaluadoraBean> entidades = entidadEvaluadoraDAO.findByEvaluador(evaluador);
				for(EntidadEvaluadoraBean entidad : entidades) {
					entidad.getEvaluadores().removeAll(evaluadores);
					entidadEvaluadoraDAO.update(entidad);
				}
				evaluadorDAO.delete(evaluador);
			}
		} else throw new ObjetoEnUsoException(usos); 
	}
	public void setProyectoRaizDao(ProyectoRaizDAO proyectoRaizDao) {
		this.proyectoRaizDao = proyectoRaizDao;
	}
	public void setExpedienteMovimientoDao(ExpedienteMovimientoDAO expedienteMovimientoDao) {
		this.expedienteMovimientoDao = expedienteMovimientoDao;
	}
	public void setRendicionCuentasDao(RendicionCuentasDAO rendicionCuentasDao) {
		this.rendicionCuentasDao = rendicionCuentasDao;
	}
	public void setProcedimientoDao(ProcedimientoDAO procedimientoDao) {
		this.procedimientoDao = procedimientoDao;
	}
	public PersonaDTO obtenerPersonaConCuit(String cuit) {
		List<PersonaBean> personas = personaDAO.findByCuit(cuit);
		if(personas.isEmpty()) return null;
		else return PersonaAssembler.getInstance().buildDto(personas.get(0));
	}
	
	public void saveEdicionMonto(String entidad, Long id, String propiedad, BigDecimal valorNuevo){
		String query = StringUtil.inject("Update $1 Set $2 = $3 Where id = $4", entidad, propiedad, valorNuevo.toString(), id.toString());
		HibernateUtil.getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
	}
	
}