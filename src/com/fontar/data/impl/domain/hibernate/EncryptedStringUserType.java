package com.fontar.data.impl.domain.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;

public class EncryptedStringUserType implements UserType {

	public final void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException,
			SQLException {
		if (value == null) {
			st.setNull(index, Types.VARBINARY);
		}
		else {
			noNullSet(st, value, index);
		}
	}

	protected void noNullSet(PreparedStatement st, Object value, int index) throws SQLException {
		st.setBytes(index, this.getCryptographicService().encrypt(value, this.getClass()));
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String value = null;
		byte[] bytes = rs.getBytes(names[0]);
		if (bytes != null) {
			try {
				value = (String) getCryptographicService().decrypt(bytes, this.getClass());
			}
			catch (Exception e) {
				value = com.fontar.seguridad.ObjectUtils.ENCRIPTION_WARNING;
			}
		}
		return value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode(); // FIXME
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return target;
	}

	public final int[] sqlTypes() {
		return new int[] { Types.LONGVARBINARY };
	}

	public Class returnedClass() {
		return String.class;
	}

	private FontarCryptographicService getCryptographicService() {
		return (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
	}

}
