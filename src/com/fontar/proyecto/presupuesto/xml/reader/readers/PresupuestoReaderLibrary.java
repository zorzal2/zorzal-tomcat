package com.fontar.proyecto.presupuesto.xml.reader.readers;

import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Node;

import com.fontar.proyecto.presupuesto.xml.Names;
import com.pragma.xml.reader.Reader;
import com.pragma.xml.reader.ReaderLibrary;
import com.pragma.xml.reader.readers.DefaultReaderLibrary;


public class PresupuestoReaderLibrary implements ReaderLibrary {

	private static final Map<String, Reader> All = new Hashtable<String, Reader>();
	static {
		All.put(Names.flujo, new FlujoReader());
		All.put(Names.presupuestoItemBien, new ItemBienReader());
		All.put(Names.presupuestoItemConsultor, new ItemConsultorReader());
		All.put(Names.presupuestoItemDirectorExperto, new ItemDirectorExpertoReader());
		All.put(Names.presupuestoItemConsejeroTecnologico, new ItemConsejeroTecnologicoReader());
		All.put(Names.presupuestoItemCanonInstitucional, new ItemCanonInstitucionalReader());
		All.put(Names.presupuestoItemInsumo, new ItemInsumoReader());
		All.put(Names.presupuestoItemRecursoHumanoPropio, new ItemRecursoHumanoPropioReader());
		All.put(Names.presupuestoItemRecursoHumanoAdicional, new ItemRecursoHumanoAdicionalReader());
		All.put(Names.presupuestoRubroGeneral, new PresupuestoRubroGeneralReader());
		All.put(Names.presupuestoRubros, new PresupuestoRubroCollectionReader());
	}

	public static final PresupuestoReaderLibrary instance = new PresupuestoReaderLibrary();

	public Reader oneCanHandle(Node node) {
		Reader reader = All.get(node.getNodeName());
		if(reader==null) 
			return DefaultReaderLibrary.instance.oneCanHandle(node);
		else return reader;
	}
}
