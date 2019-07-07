package com.fontar.web.form.administracion.proyecto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.fontar.bus.impl.CargarProyectoExistenteException;
import com.fontar.bus.impl.ProyectoConFinanciamientoBancarioSinEntidadException;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.EntidadIntervinientesDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoCargarDTO;
import com.fontar.util.Util;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.PragmaDynaValidatorForm;
import com.pragma.web.WebContextUtil;

/**
 * ActionForm usado para obtener los datos de carga de un proyecto
 * Devuelve un DTO con datos preprocesados y validados
 * 
 * @author ssanchez
 *
 */
public class CargarProyectoDynaForm extends PragmaDynaValidatorForm {

	private static final long serialVersionUID = 1L;
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors actionErrors = super.validate(mapping, request);
		if(StringUtil.isEmpty(request.getParameter("idJurisdiccion")) && requiereJurisdiccion()) {
			actionErrors.add("idJurisdiccion", new ActionMessage("Debe completarse la jurisdicción", false));
		}
		return actionErrors;
	}

	public ProyectoCargarDTO getDTO(HttpServletRequest request) throws Exception {
	
		ProyectoCargarDTO proyectoCargarDTO = new ProyectoCargarDTO();
		proyectoCargarDTO = obtenerDatosVista(request);
	
		//validar por duplicado
		ProyectoDAO proyectoDAO = (ProyectoDAO) WebContextUtil.getBeanFactory().getBean("proyectoDao");
		List<ProyectoBean> list = proyectoDAO.findByCodigo(FormUtil.getStringValue(this, "codigo")); 
		if (!list.isEmpty()) {
			throw new CargarProyectoExistenteException();
		}
		
		// Si el instrumento tiene Financiación Bancaria el Proyecto debe tener Entidad Bancaria (solo el nombre)
		if(permiteFinanciamientoBancario()){
			if(FormUtil.getStringValue(this, "idEntidadBancaria") == null) {
				throw new ProyectoConFinanciamientoBancarioSinEntidadException();
			}
		}
		
		String strIdPresentacion = FormUtil.getStringValue(this, "idPresentacion");
		Long idPresentacion = null;		
		if (!Util.isBlank(strIdPresentacion)) {
			idPresentacion = new Long(strIdPresentacion);
		}	
		proyectoCargarDTO.setIdPresentacion(idPresentacion);
		
		Long idInstrumento = new Long(FormUtil.getStringValue(this, "idInstrumento"));
		proyectoCargarDTO.setIdInstrumento(idInstrumento);
		
		Collection<EntidadIntervinientesDTO> listaEntidadIntervinientes = new ArrayList<EntidadIntervinientesDTO>();
		String[] idEntidad = FormUtil.getStringArrayValue(this,"idEntidad");
		String[] tipoEntidad = FormUtil.getStringArrayValue(this,"tipoEntidad");
		String[] relacion = FormUtil.getStringArrayValue(this,"relacion");
		String[] funcion = FormUtil.getStringArrayValue(this,"funcion");
		if (idEntidad != null) {
			for (int i = 0; i < idEntidad.length; i++) {
	
				EntidadIntervinientesDTO dto = new EntidadIntervinientesDTO();
				dto.setIdEntidad(idEntidad[i]);
				dto.setTipoEntidad(tipoEntidad[i]);
				dto.setRelacion(relacion[i]);
				dto.setFuncion(funcion[i]);
				listaEntidadIntervinientes.add(dto);
			}
			proyectoCargarDTO.setIntervinientes(listaEntidadIntervinientes);
		}
		
		String strIdProyectoPitec = FormUtil.getStringValue(this,"idProyectoPitec");		
		Long idProyectoPitec = null;		
		if (!Util.isBlank(strIdProyectoPitec)) {
			idProyectoPitec = new Long(strIdProyectoPitec);
		}
		proyectoCargarDTO.setIdProyectoPitec(idProyectoPitec);
		
		proyectoCargarDTO.setTipoProyecto(FormUtil.getStringValue(this,"tipoProyecto"));
		
		if(requiereJurisdiccion()) {
			proyectoCargarDTO.setIdJurisdiccion(FormUtil.getLongValue(this,"idJurisdiccion"));
		}
		
		return proyectoCargarDTO;		
	}
	
	private Boolean permiteFinanciamientoBancario() throws Exception {
		PresentacionConvocatoriaDAO presentacionDAO = (PresentacionConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
		String idPresentacion = FormUtil.getStringValue(this,"idPresentacion");
		if(idPresentacion == null || idPresentacion == "") {
			return false;
		} else {
			PresentacionConvocatoriaBean bean = presentacionDAO.read(new Long(idPresentacion));
			return bean.getInstrumento().getPermiteFinanciamientoBancario();
		}
	}	
	
	/**
	 * Obtiene los datos cargados en la vista al dar de alta o editar un proyecto.
	 */
	protected ProyectoCargarDTO obtenerDatosVista(HttpServletRequest request) throws Exception {
		ProyectoCargarDTO datosDto = new ProyectoCargarDTO();
		
		datosDto.setCodigo(FormUtil.getStringValue(this, "codigo"));		
				
		String strIdProyecto = request.getParameter("id");
		if (!Util.isBlank(strIdProyecto)) {
			datosDto.setIdProyecto(new Long(strIdProyecto));
		}
		
		datosDto.setIdEntidadBeneficiaria(FormUtil.getLongValue(this, "idEntidadBeneficiaria"));
		datosDto.setDuracion(FormUtil.getStringValue(this, "duracion"));
		datosDto.setIdCiiu(FormUtil.getLongValue(this, "idCiiu"));
		datosDto.setIdPersonaDirector(FormUtil.getLongValue(this, "idPersonaDirector"));
		datosDto.setIdPersonaLegal(FormUtil.getLongValue(this, "idPersonaLegal"));
		datosDto.setIdPersonaRepresentante(FormUtil.getLongValue(this, "idPersonaRepresentante"));
		datosDto.setIdTipoProyecto(FormUtil.getLongValue(this, "idTipoProyecto"));
		datosDto.setObjetivo(FormUtil.getStringValue(this, "objetivo"));
		datosDto.setObservacion(FormUtil.getStringValue(this, "observacion"));
		datosDto.setPalabraClave(FormUtil.getStringValue(this, "palabraClave"));
		datosDto.setResumen(FormUtil.getStringValue(this, "resumen"));
		datosDto.setTitulo(FormUtil.getStringValue(this, "titulo"));
		datosDto.setIdEntidadBancaria(FormUtil.getLongValue(this, "idEntidadBancaria"));
		datosDto.setDescripcionEntidadBancaria(BeanUtils.getProperty(this, "descripcionEntidadBancaria"));
		datosDto.setPorcentajeTasaInteres(FormUtil.getBigDecimalValue(this, "porcentajeTasaInteres"));
		
		String proyectoPitecRelacionado = FormUtil.getStringValue(this,"proyectoPitec");
		if (!Util.isBlank(proyectoPitecRelacionado)) {
			datosDto.setProyectoPitec(proyectoPitecRelacionado);
		}

		LocalizacionDTO localizacionDto = (LocalizacionDTO) this.get("localizacion");
		datosDto.setLocalizacion(localizacionDto);		

		EmpleoPermanenteDTO empleoPermanenteDTO = (EmpleoPermanenteDTO) this.get("empleo");
		datosDto.setEmpleo(empleoPermanenteDTO);
		
		return datosDto;
	}		
	
	public boolean requiereJurisdiccion() {
		Boolean requiereJurisdiccion = (Boolean)this.get("requiereJurisdiccion");
		if(requiereJurisdiccion==null) return false;
		else return requiereJurisdiccion;
	}
	public Object getIdentidadBeneficiaria() {
		System.out.println();
		return null;
	}
}

