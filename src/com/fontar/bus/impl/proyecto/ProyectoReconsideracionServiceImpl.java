package com.fontar.bus.impl.proyecto;

import com.fontar.bus.api.proyecto.ProyectoReconsideracionService;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.ProyectoReconsideracionDAO;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoReconsideracionAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoReconsideracionDTO;
import com.fontar.seguridad.acegi.SecuredService;


@SecuredService
public class ProyectoReconsideracionServiceImpl implements ProyectoReconsideracionService {

	private ProyectoReconsideracionDAO proyectoReconsideracionDao;
	private BitacoraDAO bitacoraDao;

	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public void setProyectoReconsideracionDao(ProyectoReconsideracionDAO proyectoReconsideracionDAO) {
		this.proyectoReconsideracionDao = proyectoReconsideracionDAO;
	}

	public ProyectoReconsideracionDTO load(Long id) {
		return ProyectoReconsideracionAssembler.getInstance().buildDto(proyectoReconsideracionDao.read(id));
	}

	public ProyectoCabeceraDTO getProyectoCabecera(Long id) {
		//Obtengo la bitacora. El id de la reconsideracion
		//coincide con el de la bitacora.
		BitacoraBean bitacora = bitacoraDao.read(id);
		return new ProyectoCabeceraAssembler().buildDTO(bitacora.getProyecto());
	}
}