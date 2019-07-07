package com.fontar.bus.api.proyecto.presupuesto;

import com.fontar.bus.impl.proyecto.presupuesto.PresupuestoParsingException;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.PresupuestoAdjuntoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;

public interface ProyectoPresupuestoServicio {
	public ProyectoPresupuestoDTO parseAdjuntoANR(ArchivoDTO dto) throws PresupuestoParsingException;
	public ProyectoPresupuestoDTO parseAdjuntoARAI(ArchivoDTO dto) throws PresupuestoParsingException;
	public ProyectoPresupuestoDTO parseAdjuntoConsejerias(ArchivoDTO dto) throws PresupuestoParsingException;
	public ProyectoPresupuestoDTO parseAdjuntoPatente(ArchivoDTO dto) throws PresupuestoParsingException;
	public ProyectoPresupuestoDTO parseAdjuntoCF(ArchivoDTO dto) throws PresupuestoParsingException;
	public ProyectoPresupuestoDTO parseAdjuntoCFConsejerias(ArchivoDTO dto) throws PresupuestoParsingException;
	public PresupuestoAdjuntoDTO savePresupuesto(PresupuestoAdjuntoDTO adjunto);
	public AdjuntoDTO getAdjunto(Long idPresupuesto);
	public ProyectoPresupuestoDTO load(Long idPresupuesto);
}
