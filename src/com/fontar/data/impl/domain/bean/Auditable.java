package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan archivos de la bitacora de proyecto (o IdeaProyecto).
 * El objetivo de la bitacora de un proyecto es el de registrar los sucesivos eventos relacionados con el mismo, por ejemplo que el proyecto fue admitido, que se asigno a una evaluacion, etc.  
 */
public class Auditable {

	private BitacoraBean bitacora;

	public BitacoraBean getBitacora() {
		if (bitacora == null) {
			bitacora = new BitacoraBean();
		}
		return bitacora;
	}

	public void setBitacora(BitacoraBean bitacora) {
		this.bitacora = bitacora;
	}
}
