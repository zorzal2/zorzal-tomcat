package com.fontar.bus.api.proyecto;

import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoReconsideracionDTO;

/**
 *	Servicios para manipulación de reconsideraciones de proyectos.   
 */
public interface ProyectoReconsideracionService {

    /**
     * Devuelve el bean correspondiente a la reconsideración con 
     * el id dado.
     * @param id identificador de reconsideracion
     * @return
     */
    public ProyectoReconsideracionDTO load(Long id);
    
    /**
     * Obtiene los datos del proyecto asociado a una reconsideracion.
     * @param id Identidicador de la reconsideracion
     * @return
     */
    public ProyectoCabeceraDTO getProyectoCabecera(Long id);
}