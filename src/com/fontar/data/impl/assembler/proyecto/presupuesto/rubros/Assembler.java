package com.fontar.data.impl.assembler.proyecto.presupuesto.rubros;

import com.pragma.util.interfaces.Bean;
import com.pragma.util.interfaces.DTO;

/*
 * Define las operaciones para un assembler. 
 * Un assembler básicamente implementa un mecanismo para obtener un cierto DTO (Data Transfer Object) a partir
 *  de un determiando Bean, y también la inversa.
 */
public interface Assembler {

	public abstract DTO buildDto(Bean bean);
	public abstract Bean updateBean(DTO dto);
	public abstract boolean canHandle(Bean bean);
	public abstract boolean canHandle(DTO dto);
}