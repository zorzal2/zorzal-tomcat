package com.fontar.bus.impl.varios;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.data.api.dao.AdjuntoContenidoDAO;
import com.fontar.data.api.dao.AdjuntosDAO;
import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.impl.assembler.AdjuntoAssembler;
import com.fontar.data.impl.domain.bean.AdjuntoBean;
import com.fontar.data.impl.domain.bean.AdjuntoContenidoBean;
import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.pragma.data.genericdao.GenericDao;

public class AdjuntoServicioImpl implements AdjuntoServicio {

	protected AdjuntosDAO adjuntoDao;
	protected AdjuntoContenidoDAO adjuntoContenidoDAO;
	
	protected GenericDao adjuntableDao;

	
	public AdjuntoContenidoDAO getAdjuntoContenidoDAO() {
		return adjuntoContenidoDAO;
	}

	public void setAdjuntoContenidoDAO(AdjuntoContenidoDAO adjuntoContenidoDAO) {
		this.adjuntoContenidoDAO = adjuntoContenidoDAO;
	}

	public GenericDao getAdjuntableDao() {
		return adjuntableDao;
	}

	public AdjuntosDAO getAdjuntoDao() {
		return adjuntoDao;
	}

	public void setAdjuntoContenidoDao(AdjuntoContenidoDAO adjuntoContenidoDAO) {
		this.adjuntoContenidoDAO = adjuntoContenidoDAO;
	}
	
	public void setAdjuntoDao(AdjuntosDAO adjuntoDao) {
		this.adjuntoDao = adjuntoDao;
	}
		
	public void setAdjuntableDao(GenericDao adjuntableDao) {
		this.adjuntableDao = adjuntableDao;
	}

	/**
	 * Retornar todos los adjuntos de la aplicación
	 */
	public Collection obtenerAdjuntos() {
		List adjuntosBean = null;
		List adjuntosDTO = new ArrayList();

		adjuntosBean = adjuntoDao.getAll();
		for (Iterator iter = adjuntosBean.iterator(); iter.hasNext();) {
			AdjuntoBean adjuntoBean = (AdjuntoBean) iter.next();
			AdjuntoDTO adjuntoDTO = new AdjuntoDTO();

			adjuntoDTO = AdjuntoAssembler.getInstance().getBean2Dto(adjuntoBean);
			adjuntosDTO.add(adjuntoDTO);
		}
		return adjuntosDTO;
	}

	/**
	 * 
	 */
	public Collection obtenerAdjuntos(Long id) {
				
		Adjuntable adjuntableBean = (Adjuntable)adjuntableDao.read(id);
		
		Collection adjuntosBean = null;
		List adjuntosDTO= new ArrayList();
		
		//adjuntosBean = adjuntoDao.getAll();
		if(adjuntableBean!=null){
		
			adjuntosBean = adjuntableBean.getAdjuntos();
			for (Iterator iter = adjuntosBean.iterator(); iter.hasNext();) {
				AdjuntoBean adjuntoBean = (AdjuntoBean) iter.next();
				AdjuntoDTO adjuntoDTO = new AdjuntoDTO();
				
				adjuntoDTO = AdjuntoAssembler.getInstance().getBean2Dto(adjuntoBean);
				adjuntosDTO.add(adjuntoDTO);
			}
		}
		
		return adjuntosDTO;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void uploadAdjunto(AdjuntoDTO adjuntoDTO, AdjuntoContenidoDTO adjuntoContenidoDTO, Long id) {
		Object adjuntableBean = adjuntableDao.read(id);
				
		AdjuntoContenidoBean adjuntoContenidoBean = new AdjuntoContenidoBean();
		adjuntoContenidoBean.setBlArchivo(adjuntoContenidoDTO.getBlArchivo());

		adjuntoContenidoBean = adjuntoContenidoDAO.save(adjuntoContenidoBean);

		AdjuntoBean adjuntoBean = new AdjuntoBean();
		adjuntoBean.setIdAdjuntoContenido(adjuntoContenidoBean.getIdAdjuntoContenido());
		adjuntoBean.setCantidadLongitud(adjuntoDTO.getCantidadLongitud());
		adjuntoBean.setDescripcion(adjuntoDTO.getDescripcion());
		adjuntoBean.setFecha(adjuntoDTO.getFecha());
		adjuntoBean.setNombre(adjuntoDTO.getNombre());
		adjuntoBean.setTipoContenido(adjuntoDTO.getTipoContenido());

		adjuntoDao.save(adjuntoBean);
		
		((Adjuntable)adjuntableBean).getAdjuntos().add(adjuntoBean);
		adjuntableDao.update(adjuntableBean);
		
	}

	/**
	 * 
	 */
	public void borrarAdjunto(Long idAdjunto, Long id) {
		Object adjuntableBean = adjuntableDao.read(id);

		AdjuntoBean adjuntoBean = adjuntoDao.read(idAdjunto);
		AdjuntoContenidoBean adjuntoContenidoBean = adjuntoContenidoDAO.read(adjuntoBean.getIdAdjuntoContenido());
//		adjuntoBean.setIdAdjuntoContenido(null);
//		adjuntoDao.update(adjuntoBean);
		
		// elimino el adjunto de la entidad
		((Adjuntable)adjuntableBean).getAdjuntos().remove(adjuntoBean);
		
		// actualizo el objeto
		adjuntableDao.update(adjuntableBean);
		
		if(adjuntoBean != null){
			adjuntoDao.delete(adjuntoBean);
		}
		
		if(adjuntoContenidoBean != null){
			adjuntoContenidoDAO.delete(adjuntoContenidoBean);
		}
	}

	/**
	 * 
	 */
	public AdjuntoContenidoDTO obtenerAdjuntoContenido(Long idAdjuntoContenido) {
		
		AdjuntoContenidoDTO adjuntoContenidoDTO = new AdjuntoContenidoDTO();
		AdjuntoContenidoBean adjuntoContenidoBean = null;
		
		adjuntoContenidoBean = adjuntoContenidoDAO.read(idAdjuntoContenido);
		try {
			if(adjuntoContenidoBean!=null){
				BeanUtils.copyProperties(adjuntoContenidoDTO,adjuntoContenidoBean);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adjuntoContenidoDTO;
	}

}
