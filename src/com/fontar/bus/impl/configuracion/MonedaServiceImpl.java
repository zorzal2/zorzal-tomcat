package com.fontar.bus.impl.configuracion;

import java.util.List;

import com.fontar.bus.api.configuracion.MonedaService;
import com.fontar.data.api.dao.MonedaDAO;
import com.fontar.data.impl.assembler.MonedaAssembler;
import com.fontar.data.impl.domain.bean.MonedaBean;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.seguridad.acegi.SecuredService;
import com.pragma.util.StringUtil;
import com.pragma.util.exception.IllegalArgumentException;


@SecuredService
public class MonedaServiceImpl implements MonedaService {

	private MonedaDAO monedaDAO;

	public void setMonedaDAO(MonedaDAO monedaDAO) {
		this.monedaDAO = monedaDAO;
	}

	public void create(MonedaDTO moneda) {
		MonedaBean monedaBean = MonedaAssembler.getInstance().buildBean(moneda);
		checkValidNewInstance(moneda);
		monedaDAO.create(monedaBean);
	}

	public void update(MonedaDTO moneda) {
		MonedaBean monedaBean = MonedaAssembler.getInstance().buildBean(moneda);
		checkValidInstance(moneda);
		monedaDAO.update(monedaBean);
	}

	public MonedaDTO load(Long id) {
		return MonedaAssembler.getInstance().buildDto(monedaDAO.read(id));
	}

	public List<MonedaDTO> getAll() {
		return MonedaAssembler.getInstance().buildDto(monedaDAO.getAll());
	}
	private void checkValidNewInstance(MonedaDTO moneda) {
		if(StringUtil.anyNotEmpty(moneda.getDescripcion(), moneda.getTipoMoneda()))
			return;
		throw new IllegalArgumentException("Las propiedades 'código' y 'descripción' de una moneda no pueden estar vacías.");
	}
	private void checkValidInstance(MonedaDTO moneda) {
		checkValidNewInstance(moneda);
		if(moneda.getId()==null)
			throw new IllegalArgumentException("El objeto no tiene ID.");
	}

	public MonedaDTO getPesoArgentino() {
		return MonedaAssembler.getInstance().buildDto(monedaDAO.read(PESO_ARGENTINO_ID));
	}
}
