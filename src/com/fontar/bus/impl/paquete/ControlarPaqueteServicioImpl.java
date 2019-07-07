package com.fontar.bus.impl.paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fontar.bus.api.paquete.AdministrarPaqueteServicio;
import com.fontar.bus.api.paquete.ControlarPaqueteServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoPaqueteDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class ControlarPaqueteServicioImpl implements ControlarPaqueteServicio {

	private ProyectoPaqueteDAO proyectoPaqueteDao;
	private PaqueteDAO paqueteDao;
	private ProyectoDAO proyectoDao;
	private BitacoraDAO bitacoraDao;

	public void setProyectoDao(ProyectoDAO proyectoDao) {
		this.proyectoDao = proyectoDao;
	}
	public void setProyectoPaqueteDao(ProyectoPaqueteDAO proyectoPaqueteDao) {
		this.proyectoPaqueteDao = proyectoPaqueteDao;
	}
	public void setPaqueteDAO(PaqueteDAO paqueteDao) {
		this.paqueteDao = paqueteDao;
	}
	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public void controlarPaquete(Long idPaquete, String[] proyectosSeleccionados) {

		//Convierto el array de strings en un array de longs
		List<Long> idsProyectosSeleccionados = new ArrayList<Long>(proyectosSeleccionados.length);
		
		for(String id : proyectosSeleccionados)
			idsProyectosSeleccionados.add(new Long(id));
		
		PaqueteBean paqueteBean = paqueteDao.read(idPaquete);
		Collection<ProyectoBean> proyectosDelPaquete = proyectoDao.findByPaqueteActivo(paqueteBean.getId());
		Collection<ProyectoBean> proyectosAEliminar = new ArrayList<ProyectoBean>();
		Collection<ProyectoBean> proyectosAConservar = new ArrayList<ProyectoBean>();
		
		for(ProyectoBean proyecto : proyectosDelPaquete) {
			if(idsProyectosSeleccionados.contains(proyecto.getId()))
				proyectosAConservar.add(proyecto);
			else 
				proyectosAEliminar.add(proyecto);
		}
		AdministrarPaqueteServicio administrarPaqueteServicio = (AdministrarPaqueteServicio) ContextUtil.getBean("administracionPaqueteService");
		//Elimino los proyectos no seleccionados
		administrarPaqueteServicio.eliminarProyectosDePaquete(proyectosAEliminar, paqueteBean);
		//Controlo los otros
		for(ProyectoBean proyecto : proyectosAConservar)
			controlarProyecto(proyecto, paqueteBean);
		
		// modifico el paquete a estado controlado
		paqueteBean.setEstado(EstadoPaquete.CONTROLADO);
		paqueteDao.update(paqueteBean);
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
	
	private void controlarProyecto(ProyectoBean proyecto, PaqueteBean paquete) {
		ProyectoPaqueteBean proyectoPaquete = proyectoPaqueteDao.findByProyectoPaquete(proyecto.getId(), paquete.getId()).get(0);
		// cargo datos de bitacora
		BitacoraBean bitacoraBean = proyectoPaquete.getBitacora();
		bitacoraBean.setIdProyecto(proyecto.getId());
		bitacoraBean.setTipo( TipoBitacora.PROY_PAQUETE);
		bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
		bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
		bitacoraBean.setTema(Constant.BitacoraTema.CONTROL_PROYECTOS_PAQUETES + " Nro. " + paquete.getId());
		bitacoraBean.setDescripcion(Constant.BitacoraDescripcion.NA);
		bitacoraBean.setIdUsuario(this.getUserId());
		bitacoraDao.save(bitacoraBean);
	}
}
