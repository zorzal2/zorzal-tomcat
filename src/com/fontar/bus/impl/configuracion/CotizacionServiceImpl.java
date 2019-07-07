package com.fontar.bus.impl.configuracion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fontar.bus.api.configuracion.CotizacionService;
import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.api.configuracion.MonedaService;
import com.fontar.data.api.dao.CotizacionDAO;
import com.fontar.data.api.dao.MonedaDAO;
import com.fontar.data.impl.assembler.CotizacionAssembler;
import com.fontar.data.impl.assembler.MonedaAssembler;
import com.fontar.data.impl.domain.bean.CotizacionBean;
import com.fontar.data.impl.domain.bean.MonedaBean;
import com.fontar.data.impl.domain.dto.CotizacionDTO;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.seguridad.acegi.SecuredService;
import com.pragma.util.ContextUtil;
import com.pragma.util.Utils;
import com.pragma.util.exception.IllegalArgumentException;


@SecuredService
public class CotizacionServiceImpl implements CotizacionService {

	private CotizacionDAO cotizacionDAO;
    private MonedaDAO monedaDAO;

    public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
    	this.cotizacionDAO = cotizacionDAO;
    }
	public void setMonedaDAO(MonedaDAO monedaDAO) {
		this.monedaDAO = monedaDAO;
	}

	public void create(CotizacionDTO cotizacion) {
		CotizacionBean cotizacionBean = CotizacionAssembler.getInstance().buildBean(cotizacion);
		checkValidNewInstance(cotizacion);
		cotizacionDAO.create(cotizacionBean);
	}

	public void update(CotizacionDTO cotizacion) {
		CotizacionBean cotizacionBean = CotizacionAssembler.getInstance().buildBean(cotizacion);
		checkValidInstance(cotizacion);
		cotizacionDAO.update(cotizacionBean);
	}

	public CotizacionDTO load(Long id) {
		return CotizacionAssembler.getInstance().buildDto(cotizacionDAO.read(id));
	}

	public List<CotizacionDTO> getAll() {
		return CotizacionAssembler.getInstance().buildDto(cotizacionDAO.getAll());
	}

	public void delete(Long id) {
		cotizacionDAO.delete(cotizacionDAO.read(id));
	}

	public List<CotizacionDTO> getAllByMoneda(Long idMoneda) {
		return CotizacionAssembler.getInstance().buildDto(cotizacionDAO.findByMoneda(idMoneda));
	}

	private void checkValidNewInstance(CotizacionDTO cotizacion) {
		if(Utils.isAnyNull(
				cotizacion.getFecha(), 
				cotizacion.getIdMoneda(),
				cotizacion.getMonto()
			)
		) throw new IllegalArgumentException("Las propiedades 'fecha', 'idMoneda' y 'monto' de una cotizacion no pueden estar vacías.");
	}
	private void checkValidInstance(CotizacionDTO cotizacion) {
		checkValidNewInstance(cotizacion);
		if(cotizacion.getId()==null)
			throw new IllegalArgumentException("El objeto no tiene ID.");
	}
	public List<MonedaDTO> getMonedasCotizables() {
		List<MonedaBean> all = monedaDAO.getAll();
		List<MonedaDTO> ret = new ArrayList<MonedaDTO>();
		for(MonedaBean monedaBean : all) {
			if(!monedaBean.getId().equals(MonedaService.PESO_ARGENTINO_ID)) {
				ret.add(MonedaAssembler.getInstance().buildDto(monedaBean));
			}
		}
		return ret;
	}
	public BigDecimal convertir(BigDecimal monto, Long idMonedaOrigen, Long idMonedaDestino, Date fecha) throws FaltanCotizacionesException {
		if(idMonedaOrigen.equals(idMonedaDestino)) return monto;
		List<Long> monedasFaltantes = new ArrayList<Long>(2);
		//Convierto a pesos
		BigDecimal enPesos;
		if(MonedaService.PESO_ARGENTINO_ID.equals(idMonedaOrigen)) {
			enPesos = monto;
		} else {
			CotizacionBean cotizacion = cotizacionVigente(idMonedaOrigen, fecha);
			if(cotizacion==null) {
				monedasFaltantes.add(idMonedaOrigen);
				/*
				 * Aunque no haya cotizacion sigo adelante para chequear que 
				 * la otra cotizacion exista. Solo por proveer una descripcion
				 * mas completa del error.
				 */
				enPesos = BigDecimal.ZERO;
			} else { 
				enPesos = monto.multiply(cotizacion.getMonto());
			}
		}
		
		//Convierto pesos a la moneda de destino
		BigDecimal enMonedaDestino; 
		if(MonedaService.PESO_ARGENTINO_ID.equals(idMonedaDestino)) {
			enMonedaDestino = enPesos;
		} else {
			CotizacionBean cotizacion = cotizacionVigente(idMonedaDestino, fecha);
			if(cotizacion==null) { 
				monedasFaltantes.add(idMonedaDestino);
				enMonedaDestino = BigDecimal.ZERO;
			} else {
				enMonedaDestino = enPesos.divide(cotizacion.getMonto(), MathContext.DECIMAL128);
			}
		}
		raiseFaltanCotizacionesException(monedasFaltantes);
		return enMonedaDestino;
	}
	private void raiseFaltanCotizacionesException(List<Long> monedasFaltantes) throws FaltanCotizacionesException {
		if(monedasFaltantes.isEmpty()) //no faltan monedas. no hay excepcion
			return;
		else {
			FaltanCotizacionesException exception = new FaltanCotizacionesException();
			MonedaService monedaService = (MonedaService) ContextUtil.getBean("monedaService");
			for(Long idMoneda : monedasFaltantes)
				exception.agregarMoneda(monedaService.load(idMoneda));
			throw exception;
		}
	}

	private CotizacionBean cotizacionVigente(Long idMoneda, Date fecha) {
		List<CotizacionBean> cotizaciones = cotizacionDAO.findVigente(idMoneda, fecha);
		if(cotizaciones.isEmpty()) return null;
		//si hay varias elige una cualquiera
		else return cotizaciones.get(0);
	}
	/*private CotizacionBean cotizacionVigente(Long idMoneda, Date fecha) {
		if(MonedaService.PESO_ARGENTINO_ID.equals(idMoneda)) {
			//Solo debe haber una sola
			List<CotizacionBean> cotizaciones = cotizacionDAO.findByMoneda(idMoneda);
			return cotizaciones.get(0);
		} else {
			//tomo la mas cercana anterior a la fecha dada
			List<CotizacionBean> cotizaciones = cotizacionDAO.findByMoneda(idMoneda);
			long mejorTime = 0;
			CotizacionBean mejorCotizacion = null;
			for(CotizacionBean cotizacion : cotizaciones) {
				long time = cotizacion.getFecha().getTime();
				//Si la fecha de la cotizacion es anterior a la dada
				//y si es mayor a mejorTime...
				if(time <= fecha.getTime() && time > mejorTime) {
					//Esta es mi cotizacion mas ajustada.
					mejorCotizacion = cotizacion;
					mejorTime = time;
				}
			}
			return mejorCotizacion;
		}
	}*/
}
