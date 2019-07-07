package com.fontar.data.impl.assembler.proyecto.presupuesto;

import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.Assembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.PresupuestoRubroGeneralDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.RubroDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemBienDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemCanonInstitucionalDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemConsejeroTecnologicoDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemConsultorDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemDirectorExpertoDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemInsumoAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoAdicionalDTOAssembler;
import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.item.PresupuestoItemRecursoHumanoPropioDTOAssembler;
import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

public class AssemblerFactory {

	private static final Assembler[] assemblers = {
		RubroDTOAssembler.instance,
		PresupuestoItemBienDTOAssembler.instance,
		PresupuestoRubroGeneralDTOAssembler.instance,
		PresupuestoItemConsultorDTOAssembler.instance,
		PresupuestoItemDirectorExpertoDTOAssembler.instance,
		PresupuestoItemCanonInstitucionalDTOAssembler.instance,
		PresupuestoItemConsejeroTecnologicoDTOAssembler.instance,
		PresupuestoItemInsumoAssembler.instance,
		PresupuestoItemRecursoHumanoAdicionalDTOAssembler.instance,
		PresupuestoItemRecursoHumanoPropioDTOAssembler.instance
	};
	public static Assembler forDTO(DTO dto) {
		for(Assembler assembler : assemblers) {
			if(assembler.canHandle(dto)) return assembler;
		}
		return null;
	}
	public static Assembler forBean(Bean bean) {
		for(Assembler assembler : assemblers) {
			if(assembler.canHandle(bean)) return assembler;
		}
		return null;
	}
}
