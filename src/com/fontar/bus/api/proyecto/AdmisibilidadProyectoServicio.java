package com.fontar.bus.api.proyecto;

import java.util.Date;

import com.fontar.data.impl.domain.dto.AdmisibilidadVisualizarDTO;

public interface AdmisibilidadProyectoServicio {
	public void cargarAdmisionAlProyecto(Long idProyecto, java.util.Date fecha, String fundamentacion,
			String disposicion, String resultado, String observacion);

	public void solicitarReadmisionAlProyecto(Long idProyecto, java.util.Date fecha, String observacion);

	public boolean isAdmitido(Long idAdmision);

	public AdmisibilidadVisualizarDTO getVisualizacionAdmisibilidad(Long id);

	public void cargarReadmisionAlProyecto(Long idProyecto, Date fecha, String fundamentacion, String resultado, String resolucion, String observacion);

	public void analizarReconsideracionAlProyecto(Long idProyecto, Date fecha, String fundamentacion, String resultado, String resolucion, String observacion, String dictamen);

	public void reconsiderarProyecto(Long idProyecto, Date fecha, String paso, String observacion);
}
