package com.fontar.proyecto.presupuesto.xml.writer.writers;

import java.util.Hashtable;
import java.util.Map;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBeanImp;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroGeneralBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.flujo.FlujoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemBienBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalBean;
import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioBean;
import com.pragma.xml.writer.Writer;
import com.pragma.xml.writer.WriterLibrary;
import com.pragma.xml.writer.writers.DefaultWriterLibrary;


public class PresupuestoWriterLibrary implements WriterLibrary {

	private static final Map<Class, Writer> All = new Hashtable<Class, Writer>();
	static {
		All.put(FlujoBean.class, new FlujoWriter());
		All.put(PresupuestoItemBienBean.class, new ItemBienWriter());
		All.put(PresupuestoItemConsultorBean.class, new ItemConsultorWriter());
		All.put(PresupuestoItemConsejeroTecnologicoBean.class, new ItemConsejeroTecnologicoWriter());
		All.put(PresupuestoItemDirectorExpertoBean.class, new ItemDirectorExpertoWriter());
		All.put(PresupuestoItemCanonInstitucionalBean.class, new ItemCanonInstitucionalWriter());
		All.put(PresupuestoItemInsumoBean.class, new ItemInsumoWriter());
		All.put(PresupuestoItemRecursoHumanoPropioBean.class, new ItemRecursoHumanoPropioWriter());
		All.put(PresupuestoItemRecursoHumanoAdicionalBean.class, new ItemRecursoHumanoAdicionalWriter());
		All.put(PresupuestoRubroGeneralBean.class, new PresupuestoRubroGeneralWriter());
		All.put(PresupuestoRubroCollectionBean.class, new PresupuestoRubroCollectionWriter());
		All.put(PresupuestoRubroCollectionBeanImp.class, new PresupuestoRubroCollectionWriter());
	}

	public static final PresupuestoWriterLibrary instance = new PresupuestoWriterLibrary();

	public <Bean> Writer oneCanHandle(Bean bean) {
		Writer writer = All.get(bean.getClass());
		if(writer==null) return DefaultWriterLibrary.instance.oneCanHandle(bean);
		else return writer;
	}
}
