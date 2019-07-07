package com.fontar.bus.impl.configuracion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fontar.bus.api.configuracion.AdministrarMatrizCriteriosServicio;
import com.fontar.data.api.dao.MatrizCriterioDAO;
import com.fontar.data.api.dao.MatrizCriterioItemDAO;
import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.fontar.data.impl.domain.bean.MatrizCriterioItemBean;
import com.fontar.data.impl.domain.dto.MatrizCriterioDTO;
import com.fontar.data.impl.domain.dto.MatrizCriterioItemDTO;
import com.fontar.util.Util;

public class AdministrarMatrizCriteriosServicioImpl implements AdministrarMatrizCriteriosServicio {
	
	private MatrizCriterioDAO matrizCriterioDAO;
	private MatrizCriterioItemDAO matrizCriterioItemDAO;

	/**
	 * Persiste una Matriz en la base de datos con sus Criterios y Categorías 
	 */
	public void updateDatosMatriz(String id, Boolean activo, String denominacion, String[] idItem, String[] tipoItem, String[] txtItem, String[] puntaje) {
		
		Long idMatrizCriterios = null;
		if(id == null || id == "")
			idMatrizCriterios = cargarMatrizCriterio(id, denominacion, activo);
		else {
			idMatrizCriterios = cargarMatrizCriterio(id, denominacion, activo);
			idMatrizCriterios = new Long(id);
		}
			
		Long idPadre = null;		
		// Guardo Items		
		for (int i = 0; i < tipoItem.length; i++) {
			String descItem = txtItem[i];
			
			if (tipoItem[i].equals("criterio")) {
				idPadre = cargarCriterio(idItem[i], descItem, idMatrizCriterios);	
			}
			else {
				cargarCategoria(idItem[i], descItem,puntaje[i],idPadre, idMatrizCriterios);				
			}
		}
		if(id != null && id != "")
			deleteDatosMatriz(id, idItem);
	}

	/**
	 * Crea una Matriz de criterios a partir de una denominacion y la persiste
     */
	public Long cargarMatrizCriterio(String id, String denominacion, Boolean activo) {
		MatrizCriterioBean matrizCriterioBean;
		if(id == null || id == "") {
			matrizCriterioBean = new MatrizCriterioBean();
			matrizCriterioBean.setNombre(denominacion);
			matrizCriterioBean.setActivo(activo);
			matrizCriterioDAO.save(matrizCriterioBean);
		} else {
			matrizCriterioBean = matrizCriterioDAO.read(new Long(id));
			matrizCriterioBean.setNombre(denominacion);
			matrizCriterioBean.setActivo(activo);
			matrizCriterioDAO.update(matrizCriterioBean);
		}
		
		return matrizCriterioBean.getId();
	}
	
	/**
	 *	Persiste un Criterio  
	 */
	public Long cargarCriterio(String idItem, String descItem, Long idMatrizCriterios) {
		MatrizCriterioItemBean item = new MatrizCriterioItemBean();
		item.setDenominacion(descItem);
		item.setIdMatrizCriterio(idMatrizCriterios);		
		if(idItem == null || idItem.equals("") || idItem.equals("0"))
			matrizCriterioItemDAO.save(item);
		else {
			item.setId(new Long(idItem));
			matrizCriterioItemDAO.update(item);
		}
		return item.getId();
	}
	
	/**
	 *  Persiste en la base de datos una Categoria asociada a un Criterio 
	 */
	public void cargarCategoria(String idItem, String descItem,String puntaje,Long idPadre, Long idMatrizCriterios) {
		MatrizCriterioItemBean item = new MatrizCriterioItemBean();

		item.setDenominacion(descItem);
		
		if (!Util.isBlank(puntaje)) {
			item.setPuntaje(new Long(puntaje));
		}
		item.setIdItemPadre(idPadre);
		item.setIdMatrizCriterio(idMatrizCriterios);

		if(idItem == null || idItem.equals("") || idItem.equals("0"))
			matrizCriterioItemDAO.save(item);
		else {
			item.setId(new Long(idItem));
			matrizCriterioItemDAO.update(item);
		} 
	}

	public MatrizCriterioDTO obtenerMatrizCriterio(Long id) {
		MatrizCriterioDTO mcDto = new MatrizCriterioDTO();
		
		MatrizCriterioBean matrizCriterioBean = matrizCriterioDAO.read(id);
		mcDto.setIdMatrizCriterio(id.toString());
		mcDto.setActivo(matrizCriterioBean.getActivo());
		mcDto.setDenominacion(matrizCriterioBean.getNombre());
		
		Collection<MatrizCriterioItemDTO> mciDtoColl = new ArrayList<MatrizCriterioItemDTO>();
		
		Collection<MatrizCriterioItemBean> items = matrizCriterioBean.getItems();
		Iterator i = items.iterator();
		while(i.hasNext()) {
			MatrizCriterioItemBean mciBean = (MatrizCriterioItemBean)i.next();
			
			MatrizCriterioItemDTO mciDto = new MatrizCriterioItemDTO();
			
			mciDto.setIdItem(mciBean.getId());
			if(mciBean.getIdItemPadre() != null) 
				mciDto.setTipoItem("categoria");
			else
				mciDto.setTipoItem("criterio");
			
			mciDto.setCriterio(mciBean.getDenominacion());
			if(mciBean.getPuntaje() == null)
				mciDto.setPuntaje("");
			else
				mciDto.setPuntaje(mciBean.getPuntaje().toString());
			
			mciDtoColl.add(mciDto);
		}
		
		List l = new ArrayList();
		l.addAll(mciDtoColl);
		Collections.sort(l);
		mciDtoColl.removeAll(l);
		mciDtoColl.addAll(l);
		
		mcDto.setCriterioList(mciDtoColl);
		return mcDto;
	}

	/**
	 * Deletea elementos de una Matriz en la base de datos con sus Criterios y Categorías 
	 */
	public void deleteDatosMatriz(String id, String[] idItem) {
		
		MatrizCriterioDTO mcDto = this.obtenerMatrizCriterio(new Long(id));

		Collection<MatrizCriterioItemDTO> mciDto = mcDto.getCriterioList();

// Elimino las categorias		
		Iterator it = mciDto.iterator();
		while(it.hasNext()) {
			MatrizCriterioItemDTO mciiDto = (MatrizCriterioItemDTO)it.next();
			if(mciiDto.esCategoria()) {
				boolean encontre = false;
				for(int i = 0; i < idItem.length; i++) {
					if(idItem[i] != "")
						if(mciiDto.getIdItem() == new Long(idItem[i]))
							encontre = true;
				}
				if(!encontre) {
					MatrizCriterioItemBean item = matrizCriterioItemDAO.read(mciiDto.getIdItem()); 
					matrizCriterioItemDAO.delete(item);
					MatrizCriterioBean mcBean = matrizCriterioDAO.read(new Long(mcDto.getIdMatrizCriterio()));
					mcBean.getItems().remove(item);
				}
			}
		}
	
// Elimino los criterios		
		it = mciDto.iterator();
		while(it.hasNext()) {
			MatrizCriterioItemDTO mciiDto = (MatrizCriterioItemDTO)it.next();
			if(mciiDto.esCriterio()) {
				boolean encontre = false;
				for(int i = 0; i < idItem.length; i++) {
					if(idItem[i] != "")
						if(mciiDto.getIdItem() == new Long(idItem[i]))
							encontre = true;
				}
				if(!encontre) {
					MatrizCriterioItemBean item = matrizCriterioItemDAO.read(mciiDto.getIdItem()); 
					matrizCriterioItemDAO.delete(item);
					MatrizCriterioBean mcBean = matrizCriterioDAO.read(new Long(mcDto.getIdMatrizCriterio()));
					mcBean.getItems().remove(item);
				}
			}
		}
	}
	
	public boolean existeMatrizConNombre(String denominacion) {		
		List<MatrizCriterioBean> list;		
		list = matrizCriterioDAO.findByNombre(denominacion);		
		
		return !list.isEmpty();				
	}
	
	public MatrizCriterioDAO getMatrizCriterioDAO() {
		return matrizCriterioDAO;
	}

	public void setMatrizCriterioDAO(MatrizCriterioDAO matrizCriterioDAO) {
		this.matrizCriterioDAO = matrizCriterioDAO;
	}

	public MatrizCriterioItemDAO getMatrizCriterioItemDAO() {
		return matrizCriterioItemDAO;
	}

	public void setMatrizCriterioItemDAO(MatrizCriterioItemDAO matrizCriterioItemDAO) {
		this.matrizCriterioItemDAO = matrizCriterioItemDAO;
	}
}