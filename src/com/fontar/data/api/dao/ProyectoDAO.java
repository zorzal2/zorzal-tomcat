package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoPresupuestosDTO;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

@HasNamedParams
public interface ProyectoDAO extends GenericDao<ProyectoBean, Long> {
	List<ProyectoBean> findByCodigo(String codigo);
	
	List<ProyectoBean> findByNombre(String nombre);

	List<ProyectoBean> findByEstadoEnPaquete();

	List<ProyectoBean> findByInstrumento(@ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByInstrumentoEvaluacionEstado(@ParamName("estado") String estado, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByInstrumentoReconsideracionEstado(@ParamName("estado") String estado, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByInstrumentoAdjudicacion(@ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByInstrumentoTratamiento(@ParamName("idInstrumento") String idInstrumento, @ParamName("tratamiento") String tratamiento);

	List<ProyectoBean> findByPaquete(@ParamName("idPaquete") Long idPaquete);
	
	List<ProyectoBean> findByPaqueteActivo(@ParamName("idPaquete") Long idPaquete);

	List<ProyectoBean> findByPaqueteInstrumento(@ParamName("idPaquete") Long idPaquete, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByPaqueteInstrumentoEvaluacionEstado(@ParamName("estado") String estado, @ParamName("idPaquete") Long idPaquete, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByPaqueteInstrumentoReconsideracionEstado(@ParamName("estado") String estado, @ParamName("idPaquete") Long idPaquete, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByPaqueteInstrumentoAdjudicacion(@ParamName("idPaquete") Long idPaquete, @ParamName("idInstrumento") Long idInstrumento);

	List<ProyectoBean> findByInstrumentoNoAnuladas(Long idInstrumento);

	List<ProyectoBean> findByPresentacionNoAnulados(Long idPresentacion);

	List findCountProyectosAprobadosByInstrumento(Long idInstrumento);
	
	List findCountByInstrumentoNoAnuladas(Long idInstrumento);
	
	List<ProyectoPresupuestosDTO> findPresupuestosByInstrumento(Long idInstrumento);
		
	List<ProyectoBean> findByProyectoOriginal(Long id);

}
