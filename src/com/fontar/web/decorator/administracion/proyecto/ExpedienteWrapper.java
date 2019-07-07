package com.fontar.web.decorator.administracion.proyecto;

import com.fontar.data.impl.domain.bean.ExpedienteBean;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;
import com.pragma.util.DecoratorUtil;

public class ExpedienteWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkBorrar() {
		ExpedienteBean bean = (ExpedienteBean) this.getCurrentRowObject();
		String link = "";

		link = DecoratorUtil.getLinkBorrado("ExpedienteBorrar.do", "app.alt.borrarBitacora", bean.getId(), true);

		return link;
	}

	public String getLinkEditar() {
		ExpedienteBean bean = (ExpedienteBean) this.getCurrentRowObject();
		String link = "";

		link = DecoratorUtil.getLinkEdicion("ExpedienteEditar.do", "app.alt.editarBitacora", bean.getId());

		return link;
	}

}
