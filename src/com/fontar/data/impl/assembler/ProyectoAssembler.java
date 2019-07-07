package com.fontar.data.impl.assembler;

import java.util.HashMap;
import java.util.Map;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.LocalizacionBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.dto.EmpleoPermanenteDTO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.pragma.util.ContextUtil;

/**
 * Dto para proyectos
 * @author ssanchez
 * @version 1.01, 13/12/06
 */
public class ProyectoAssembler {

	@SuppressWarnings("unchecked")
	public static ProyectoDTO buildDto(ProyectoBean bean) {
		LocalizacionBean localizacionBean = (LocalizacionBean) bean.getProyectoDatos().getLocalizacion();

		LocalizacionDTO localizacion = new LocalizacionDTO();

		if (localizacionBean != null) {
			localizacion = LocalizacionAssembler.getInstance().buildDto(bean.getProyectoDatos().getLocalizacion());
		}

		EmpleoPermanenteBean empleoPermanenteBean = (EmpleoPermanenteBean) bean.getEmpleoPermanente();
		EmpleoPermanenteDTO empleo = new EmpleoPermanenteDTO();

		if (empleoPermanenteBean != null) {
			empleo = EmpleoPermanenteAssembler.getInstance().buildDto(bean.getEmpleoPermanente());
		}

		ProyectoDatosBean proyectoDatosBean = (ProyectoDatosBean) bean.getProyectoDatos();
		ProyectoEdicionDTO proyectoDatosDto = new ProyectoEdicionDTO();

		if (proyectoDatosBean != null) {
			proyectoDatosDto = ProyectoEdicionAssembler.getInstance().buildDto(bean.getProyectoDatos());
		}

		ProyectoDTO proyectoDTO = new ProyectoDTO();

		proyectoDTO.setId(bean.getId() == null ? "" : bean.getId().toString());
		proyectoDTO.setCodigo(bean.getCodigo());

		//Chequeo si el usuario tiene permiso para obtener el dato.
		AdministrarProyectoServicio administrarProyectoServicio = (AdministrarProyectoServicio)ContextUtil.getBean("administrarProyectoService");
		proyectoDTO.setRecomendacionProyecto(administrarProyectoServicio.getRecomendacionSiEsAccesible(bean));

		proyectoDTO.setEstado(bean.getEstado());
		proyectoDTO.setEstadoEnPaquete(bean.getEstadoEnPaquete() == null ? "" : bean.getEstadoEnPaquete().toString());
		proyectoDTO.setIdLocalizacion(localizacion.getId());
		proyectoDTO.setLocalizacion(localizacion);
		proyectoDTO.setEmpleo(empleo);
		proyectoDTO.setProyectoDatos(proyectoDatosDto);

		PresentacionConvocatoriaBean presentacionBean = bean.getPresentacionConvocatoria();

		if (presentacionBean != null) {
			proyectoDTO.setCodigoPresentacion(presentacionBean.getCodigo());
		}

		proyectoDTO.setIdDatos(bean.getIdDatos() == null ? "" : bean.getIdDatos().toString());
		proyectoDTO.setIdEmpleoPermanente(bean.getIdEmpleoPermanente() == null ? ""
				: bean.getIdEmpleoPermanente().toString());
		proyectoDTO.setIdInstrumento(bean.getIdInstrumento() == null ? "" : bean.getIdInstrumento().toString());
		proyectoDTO.setIdPlanTrabajo(bean.getIdPlanTrabajo() == null ? "" : bean.getIdPlanTrabajo().toString());
		proyectoDTO.setIdPresentacion(bean.getIdPresentacion() == null ? "" : bean.getIdPresentacion().toString());
		proyectoDTO.setIdPresupuesto(bean.getIdPresupuesto() == null ? "" : bean.getIdPresupuesto().toString());
		proyectoDTO.setIdPresupuestoOriginal(bean.getIdPresupuestoOriginal() == null ? ""
				: bean.getIdPresupuestoOriginal().toString());
		proyectoDTO.setIdProyectoJurisdiccion(bean.getIdProyectoJurisdiccion() == null ? ""
				: bean.getIdProyectoJurisdiccion().toString());
		proyectoDTO.setIdProyectoOrigen(bean.getIdProyectoOrigen() == null ? "" : bean.getIdProyectoOrigen().toString());
		proyectoDTO.setIdProyectoPaquete(bean.getIdProyectoPaquete() == null ? ""
				: bean.getIdProyectoPaquete().toString());
		proyectoDTO.setIdProyectoPitec(bean.getIdProyectoPitec() == null ? "" : bean.getIdProyectoPitec().toString());
		proyectoDTO.setIdWorkFlow(bean.getIdWorkFlow() == null ? "" : bean.getIdWorkFlow().toString());
		proyectoDTO.setPorcentajeAlicuotaAdjudicada(bean.getPorcentajeAlicuotaAdjudicada() == null ? ""
				: bean.getPorcentajeAlicuotaAdjudicada().toString());
		proyectoDTO.setPorcentajeAlicuotaSolicitada(bean.getPorcentajeAlicuotaSolicitada() == null ? ""
				: bean.getPorcentajeAlicuotaSolicitada().toString());
	//FIXME esto este repetido??	proyectoDTO.setRecomendacion(bean.getRecomendacion());

		EntidadBeneficiariaBean entidadBancariaBean = bean.getProyectoDatos().getEntidadBeneficiaria();
		if (entidadBancariaBean != null)
			proyectoDTO.setEntidadBeneficiaria(EntidadBeneficiariaAssembler.getInstance().buildDto(entidadBancariaBean));
		return proyectoDTO;
	}

	@SuppressWarnings("unchecked")
	public static Map buildBean(ProyectoDTO dto) {

		LocalizacionBean localizacion = LocalizacionAssembler.getInstance().buildBean(dto.getLocalizacion());
		EmpleoPermanenteBean empleoPermanenteBean = EmpleoPermanenteAssembler.getInstance().buildBean(dto.getEmpleo());

		Map map = new HashMap();

		map.put("id", dto.getId());
		map.put("codigo", dto.getCodigo());
		map.put("recomendacion", dto.getRecomendacion());
		map.put("localizacion", localizacion);
		map.put("idLocalizacion", localizacion.getId());
		map.put("empleo", empleoPermanenteBean);

		return map;
	}

	public static ProyectoDTO buildDto(IdeaProyectoBean bean) {		
		return null;
	}

	public static ProyectoDTO buildDto(ProyectoRaizBean proyecto) {
		if (proyecto instanceof ProyectoBean) {
			return buildDto((ProyectoBean) proyecto);
		}
		else
			return buildDto((IdeaProyectoBean) proyecto);
	}
}
