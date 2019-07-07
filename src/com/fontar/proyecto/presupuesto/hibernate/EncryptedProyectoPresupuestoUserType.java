package com.fontar.proyecto.presupuesto.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.PresupuestoRubroCollectionBean;
import com.fontar.data.impl.domain.hibernate.EncryptedStringUserType;
import com.fontar.proyecto.presupuesto.xml.reader.PresupuestoXMLReader;
import com.fontar.proyecto.presupuesto.xml.writer.PresupuestoXMLSerializer;
import com.fontar.seguridad.ObjectUtils;

public class EncryptedProyectoPresupuestoUserType extends EncryptedStringUserType {
			
		
		protected void noNullSet(PreparedStatement st, Object presupuestoRubroCollection, int index) throws SQLException {
			String xmlString = PresupuestoXMLSerializer.instance.serialize((PresupuestoRubroCollectionBean) presupuestoRubroCollection);
			super.noNullSet(st, xmlString, index);
		}

		public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
			String xmlString = (String)super.nullSafeGet(rs, names, owner);
			if(xmlString==null || xmlString.equals(ObjectUtils.ENCRIPTION_WARNING)) return null;
			else return PresupuestoXMLReader.instance.unserialize(xmlString);
		}
	
	    public Class returnedClass() {
	        return PresupuestoRubroCollectionBean.class;
	    }

}
