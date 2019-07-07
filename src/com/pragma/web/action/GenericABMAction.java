package com.pragma.web.action;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pragma.bus.api.GenericABMService;
import com.pragma.util.DateTimeUtil;

/**
 * Service Generico para hacer los ABM
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 * 
 * @param <T> Type Class
 */
public class GenericABMAction<T> extends TemplateABMAction {

	protected static final String FORWARD_SUCCESS = "success";
	protected static final String SELECTOR_COLLECTION = "collection";
	protected static final String SELECTED_OBJECT = "selected";

	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// private GenericAssembler<T,D>;
	private GenericABMService servicio;

	private Class<T> type;

	// ~ Constructors
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Crea un objeto <code>GenericABMAction</code>.
	 * 
	 * @param type Class
	 */
	public GenericABMAction(Class<T> type) {
		this.type = type;
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param servicio Documentar el parametro!
	 */
	public void setServicio(GenericABMService servicio) {
		this.servicio = servicio;
	}

	public GenericABMService getServicio() {
		return servicio;
	}

	@SuppressWarnings("unused")
	protected void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List collection = getCollectionInventario();

		if (collection == null) {
			collection = servicio.getAll();
		}

		// guardo la collection en el request
		request.setAttribute("collection", collection);

	}

	protected List getCollectionInventario() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// levanto el id de la entidad a editar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		/**
		 * Clase que convierte un objeto de tipo Date a String para mostrarlo en
		 * un form.
		 */

		// FIX: FF / Ojo con esto! deberian estar en el PlugIn de inicio
		class MyStringConverter implements Converter {
			public Object convert(Class type, Object value) {

				if (value == null)
					return "";

				if (value instanceof java.util.Date) {
					return DateTimeUtil.formatDate((java.util.Date) value);
				}
				else {
					return value.toString();
				}
			}
		}
		;
		// FIX: FF / Ojo con esto! deberian estar en el PlugIn de inicio
		/**
		 * Clase que convierte un objeto de tipo String a Date para obtenerlo
		 * correctamente de un form.
		 */
		class MyDateConverter implements Converter {
			public Object convert(Class type, Object value) {

				if (value == null)
					return "";

				Date date = null;

				if (value instanceof String) {
					try {
						date = DateTimeUtil.getDate(value.toString());
					}
					catch (ParseException e) {
					}
				}
				else {
					return value.toString();
				}

				return date;
			}
		}
		;

		ConvertUtils.register(new MyStringConverter(), String.class);
		ConvertUtils.register(new MyDateConverter(), Date.class);

		T bean = (T) servicio.load(new Long(id));
		BeanUtils.copyProperties(form, bean);
	}

	protected ActionForward forwardEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// levanto el id de la entidad a visualizar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		T bean = (T) servicio.load(new Long(id));
		BeanUtils.copyProperties(form, bean);
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// creo una instancia
		T bean = type.newInstance();

		// copio los datos del Form al Bean
		BeanUtils.copyProperties(bean, form);
		BeanUtils.setProperty(bean, "id", request.getParameter("id"));// TODO:
																		// esto
																		// se
																		// puede
																		// reempñazar
																		// con
																		// un
																		// campo
																		// con
																		// nombre
																		// ID y
																		// hidden

		String id = request.getParameter("id");

		// Guardo los datos en DB
		if ((id == null) || (id == "")) {
			servicio.save(bean);
		}
		else {
			servicio.update(bean);
		}
	}

	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// levanto el id de la entidad a editar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}
		servicio.delete(servicio.load(new Long(id)));
	}
	
	
	/** 
	 * Debe sobreescribirse en caso de querer agregar a la coleccion
	 * el objeto seleccionado
	 ***/
	@SuppressWarnings("unchecked")
	protected  Collection coleccionSeleccion(Long objectId){
		Collection collection = this.getServicio().getActives(true); 
		if(objectId!=null) //FIXME controlar que no este en la coleccion
			collection.add( this.getServicio().load( objectId));
		return collection;
	}
	
	public ActionForward selector(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter(SELECTED_OBJECT);
		Long  objectId = (!GenericValidator.isBlankOrNull(id))?GenericTypeValidator.formatLong(id) : null;
		request.setAttribute(SELECTOR_COLLECTION, this.coleccionSeleccion( objectId));
		return mapping.findForward(FORWARD_SUCCESS);
	}
}