package com.fontar.bus.impl.proyecto.datos.matcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.pragma.PragmaControlledException;
import com.pragma.util.CollectionUtils;
import com.pragma.web.userfeedback.UserFeedbackRequest;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * Clase auxiliar que premite ubicar a las objetos a partir de objeto objetoInfo.
 * @author llobeto
 *
 */
public abstract class Matcher<Info, Bean> {
	
	private Map<Info, Matching<Bean>> unresolvedMatchings = new HashMap<Info, Matching<Bean>>();
	private Map<Info, Bean> resolvedBeans = new HashMap<Info, Bean>();
	private Map<String, UserFeedbackResponse> feedbackResponses;
	private boolean cancelled = false;
	
	public Matcher(Map<String, UserFeedbackResponse> feedbackResponses) {
		this.feedbackResponses = feedbackResponses;
	}
	
	public Set<UserFeedbackRequest> feedbackNeeded() {
		Set<UserFeedbackRequest> ret = new HashSet<UserFeedbackRequest>();
		for(Matching<?> matchingInfo : unresolvedMatchings.values()) {
			if(matchingInfo.getFeedbackRequired()!=null) {
				 ret.add(matchingInfo.getFeedbackRequired());
			}
		}
		return ret;
	}
	
	public boolean cancelled() {
		return cancelled;
	}
	
	/**
	 * Busca coincidencias del objeto dado con las objetos guardadas en el sistema.
	 * Devuelve un objeto con la informacion de las coincidencias halladas.
	 * @param objeto
	 * @return
	 * @throws objetoDatosInsuficientesException
	 */
	public void add(Info objeto) throws PragmaControlledException {
		if(objeto==null || isEmpty(objeto)) return;
		//Busco si ya resolvi este caso
		Bean bean = findEquivalentInResolved(objeto);
		if(bean!=null) {
			resolvedBeans.put(objeto, bean);
			return;
		}
		//Busco si coincide con otro caso no resuelto
		Matching<Bean> matching = findInUnresolved(objeto);
		if(matching!=null) {
			unresolvedMatchings.put(objeto, matching);
			return;
		}
		//Es un caso nuevo
		matching = crateMatch(objeto);
		//se puede resolver?
		bean = matching.solveWith(feedbackResponses);
		if(bean==null) {
			//No se puede resolver.
			unresolvedMatchings.put(objeto, matching);
		} else {
			resolvedBeans.put(objeto, bean);
		}
	}

	private Matching<Bean> findInUnresolved(Info objeto) {
		//Busco entre los no resueltos hasta ahora uno que coincida
		for(Entry<Info, Matching<Bean>> entry : unresolvedMatchings.entrySet()) {
			Info objetoGuardada = entry.getKey();
			if(matches(objeto, objetoGuardada)) {
				return entry.getValue();
			}
		}
		return null;
	}
	private Bean findEquivalentInResolved(Info objeto) {
		//Busco entre los resueltos hasta ahora uno que coincida
		for(Entry<Info, Bean> entry : resolvedBeans.entrySet()) {
			Info objetoGuardada = entry.getKey();
			if(matches(objeto, objetoGuardada)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public Bean match(Info objeto) {
		return resolvedBeans.get(objeto);
	}
	/*
	 * Homenaje a Dennis Ritchie, creador del C.
	 */
	private Matching<Bean> crateMatch(Info objeto) throws PragmaControlledException {
		Set<Bean> objetosCoincidentes = 
			obtenerBeansCoincidentes(
					nombreInfo(objeto),
					cuitInfo(objeto)
			);

		if(objetosCoincidentes.isEmpty()) {
			//No hubo coincidencias. Creo la entidad. Si hay una excepcion la dejo propagar.
			Bean objetoBean = crearBean(objeto);
			return new MatchingImpl<Bean>(
						CollectionUtils.setWith(objetoBean)
					);
		}
		
		/*
		 * Hay coincidencias
		 */

		if(nombreInfo(objeto)!=null) {
			if(cuitInfo(objeto)!=null) {
				/*
				 * Las coincidencias pudieron haber sido por nombre, por cuit
				 * o por ambos
				 */
				Bean objetoCoincidentePorCuit = beanConCuit(cuitInfo(objeto), objetosCoincidentes);
				if(objetoCoincidentePorCuit==null) {
					//No hay coincidencias por cuit. solo por nombre.
					Bean objetoBean;
					try {
						objetoBean = crearBean(objeto);
					}
					catch (PragmaControlledException e) {
						objetoBean = null;
					}
					AbstractUserFeedbackRequest<Bean> cuitDistinto = cuitDistinto(objetosCoincidentes, objetoBean, cuitInfo(objeto));
					if(objetoBean!=null)objetosCoincidentes.add(objetoBean);
					return new MatchingImpl<Bean>(
							objetosCoincidentes,
							cuitDistinto
						);
				} else {
					//Hay una coincidencia por cuit ¿y el nombre?
					if(nombreBean(objetoCoincidentePorCuit).equalsIgnoreCase(nombreInfo(objeto))) {
						//Coincidencia perfecta
						return new MatchingImpl<Bean>(
								CollectionUtils.setWith(objetoCoincidentePorCuit)
							);
					} else {
						//El nombre no coincide. solo el cuit
						return new MatchingImpl<Bean>(
								CollectionUtils.setWith(objetoCoincidentePorCuit),
								nombreDistinto(nombreInfo(objeto), objetoCoincidentePorCuit)
							);
					}
				}
			} else {
				//Bean sin cuit pero con nombre. Coincidencias por nombre
				Bean objetoBean;
				try {
					objetoBean = crearBean(objeto);
				}
				catch (PragmaControlledException e) {
					objetoBean = null;
				}
				AbstractUserFeedbackRequest<Bean> cuitDistinto = cuitDistinto(objetosCoincidentes, objetoBean, null);
				if(objetoBean!=null)objetosCoincidentes.add(objetoBean);
				return new MatchingImpl<Bean>(
						objetosCoincidentes,
						cuitDistinto
					);
			} 
		} else {
			//No tiene nombre. Si hubo coincidencia es por cuit y debe ser unica.
			Bean objetoBean = objetosCoincidentes.iterator().next();
			return new MatchingImpl<Bean>(
					CollectionUtils.setWith(objetoBean),
					faltaNombre(objetoBean)
				);
		}
	} 
	
	/**
	 * Decide si la primer objeto cargada encaja con la segunda.
	 * @param objeto1
	 * @param objeto2
	 * @return
	 */
	private boolean matches(Info objeto1, Info objeto2) {
		if(cuitInfo(objeto1)!=null) {
			if(!cuitInfo(objeto1).equals(cuitInfo(objeto2))) return false;
		} else {
			if(cuitInfo(objeto2)!=null) return false;
		}
		if(nombreInfo(objeto1)!=null) {
			if(!nombreInfo(objeto1).equals(nombreInfo(objeto2))) return false;
		} else {
			if(nombreInfo(objeto2)!=null) return false;
		}
		return true;
	}
	private Bean beanConCuit(String cuit, Collection<Bean> objetos) {
		for (Bean objeto : objetos) {
			if(cuit==null) {
				if(cuitBean(objeto)==null) return objeto; 
			} else {
				if(cuit.equals(cuitBean(objeto))) return objeto;
			} 
		}
		return null;
	}
	/**
	 * Decide si el objeto se considera vacio.
	 * @param objeto
	 * @return
	 */
	protected abstract boolean isEmpty(Info objeto);

	protected abstract AbstractUserFeedbackRequest<Bean> nombreDistinto(String string, Bean objetoCoincidentePorCuit);
	protected abstract AbstractUserFeedbackRequest<Bean> cuitDistinto(Set<Bean> objetosCoincidentes, Bean bean, String cuit);
	protected abstract AbstractUserFeedbackRequest<Bean> faltaNombre(Bean objetoBean);

	/**
	 * Crea el bean correspondiente a partir del info.
	 * @param objeto
	 * @return
	 */
	protected abstract Bean crearBean(Info objeto) throws PragmaControlledException;
	/**
	 * Invoca al servicio correspondiente para obtener los beans persistidos que coinciden
	 * con los datos dados.
	 * @param nombre
	 * @param cuit
	 * @return
	 */
	protected abstract Set<Bean> obtenerBeansCoincidentes(String nombre, String cuit);

	protected abstract String cuitInfo(Info objeto);
	protected abstract String cuitBean(Bean objeto);
	protected abstract String nombreInfo(Info objeto);
	protected abstract String nombreBean(Bean objetoCoincidentePorCuit);
}