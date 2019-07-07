package com.fontar.web.form.administracion.seguimiento.rendiciones.cf;
	    
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.util.ResourceManager;
import com.fontar.util.Util;
import com.pragma.web.PragmaDynaValidatorForm;

/**
 * 
 * @author gboaglio
 *
 */
@SuppressWarnings("serial")
public class CargaRendicionForm extends PragmaDynaValidatorForm {
		
	private ActionErrors errors;
	
	
	/**
	 * 
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		ActionErrors errors = super.validate(mapping, request);
		
		this.errors = errors;
		
		String strTipoRendicion = (String) this.get("tipoRendicion");
				
		boolean esGeneral = true;
		boolean esPersona = false;
		boolean esPropio = false;
		boolean esAdicional = false;
		boolean esConsultor = false;
		boolean esDirectorExperto = false;
		boolean esConsejeroTecnologico = false;
		boolean esCanonInstitucional = false;
		
		if (!strTipoRendicion.equals("")) {
			esGeneral   = strTipoRendicion.equals(TipoRendicion.GENERAL.getDescripcion());		
			esPersona   = 
				strTipoRendicion.equals(TipoRendicion.RECURSO_HUMANO_ADICIONAL.getDescripcion()) ||
				strTipoRendicion.equals(TipoRendicion.RECURSO_HUMANO_PROPIO.getDescripcion()) ||
				strTipoRendicion.equals(TipoRendicion.CONSEJERO_TECNOLOGICO.getDescripcion()) ||
				strTipoRendicion.equals(TipoRendicion.DIRECTOR_EXPERTO.getDescripcion());
			esPropio    = strTipoRendicion.equals(TipoRendicion.RECURSO_HUMANO_PROPIO.getDescripcion());
			esAdicional = strTipoRendicion.equals(TipoRendicion.RECURSO_HUMANO_ADICIONAL.getDescripcion());
			esConsultor = strTipoRendicion.equals(TipoRendicion.CONSULTOR.getDescripcion());			
			esDirectorExperto = strTipoRendicion.equals(TipoRendicion.DIRECTOR_EXPERTO.getDescripcion());			
			esConsejeroTecnologico = strTipoRendicion.equals(TipoRendicion.CONSEJERO_TECNOLOGICO.getDescripcion());			
			esCanonInstitucional = strTipoRendicion.equals(TipoRendicion.CANON_INSTITUCIONAL.getDescripcion());			
		}

		validateIf("nroFactura",true);
		//validateIf("nroRecibo", true);				
		validateIfAllTrue("descripcion",!esPersona, !esCanonInstitucional);
		validateIfAnyTrue("proveedor", esGeneral, esConsultor);
		validateIf("montoTotal",true);
		validateIfAnyTrue("tieneCertificadoProveedor", esGeneral, esConsultor);
		validateIf("txtPersonaRendicion", esPersona);
		validateIfAllTrue("profesion", esPersona, !esDirectorExperto);
		validateIf("sueldoMensual",esPropio);
		validateIf("porcentajeDedicacion", esPersona);
		validateIfAnyTrue("funcion", esPropio, esAdicional, esConsejeroTecnologico);
		validateIfAnyTrue("costoTotalMensual", esAdicional, esConsultor, esDirectorExperto, esConsejeroTecnologico);
		
		//Valido la longitud del campo Recibo
		validateLength("nroRecibo", 1000);
		
		return this.errors;		
	}	
	
	/**
	 * Efectúa la validación del campo <i>field</i>
	 * La validación consiste en chequear que el campo haya sido completado en el caso  
	 * de ser requerido, lo cual se verifica con la conjunción de todas las <i>conditions</i>
	 * 
	 * El valor retornado indica si el campo es requerido o no por el formulario.
	 * 
	 * @param field
	 * @param conditions
	 * @return req
	 */
	private void validateIfAllTrue(String field, boolean ...conditions ) {
		
		boolean req = true;
		
		for (boolean condition: conditions) {		
			req = req && condition;		
		}
		
		validateIf(field, req);
	}
	private void validateIfAnyTrue(String field, boolean ...conditions ) {
		
		boolean req = false;
		
		for (boolean condition: conditions) {		
			req = req || condition;		
		}
		
		validateIf(field, req);
	}
	private void validateIf(String field, boolean condition) {
	
		String value = (String) this.get(field);
		
		if (condition && Util.isBlank(value)) {
			this.errors.add(field, new ActionMessage("Se requiere el campo "+getLabel(field), false));
		}
	}
	private void validateLength(String field, int maxLength) {
		String value = (String) this.get(field);
		if (!Util.isBlank(field) && value.length()>maxLength) {
			this.errors.add(field, new ActionMessage("El campo "+getLabel(field)+" supera los "+maxLength+" caracteres", false));
		}
	}
	
	/**
	 * Devuelve el label asociado al nombre de campo <i>field</i> 
	 * pasado por parámetro.
	 * 
	 * @param field
	 * @return
	 */
	public String getLabel(String field) {
		return ResourceManager.getLabelResource("app.label."+field);
	}
}







