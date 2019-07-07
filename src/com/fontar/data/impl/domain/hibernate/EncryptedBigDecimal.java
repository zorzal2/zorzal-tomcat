package com.fontar.data.impl.domain.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;

public class EncryptedBigDecimal implements UserType {

	public final void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException,
			SQLException {
		if (value == null) {
			st.setNull(index, Types.DECIMAL);
		}
		else {
			noNullSet(st, value, index);
		}

	}

	protected void noNullSet(PreparedStatement preparedStatement, Object value, int index) throws SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.DECIMAL);
		}
		else {
			BigDecimal bigDecimal = (BigDecimal) ((EncryptedObject) value).getObject();
			if (null == bigDecimal) {
				preparedStatement.setNull(index, Types.VARCHAR);
			}
			else {
				preparedStatement.setBytes(index, getCryptographicService().encrypt(bigDecimal, this.getClass()));
			}
		}

	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		EncryptedObject value = null;
		byte[] bytes = rs.getBytes(names[0]);
		if (bytes != null) {
			try {
				value = new EncryptedObject((BigDecimal) getCryptographicService().decrypt(bytes, this.getClass()), null, true);
			}
			catch (Exception e) {
				value = new EncryptedObject(null, null, false);
			}
		}
		else {
			value = new EncryptedObject(null, null, true);
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
		return EncryptedObject.class;
	}

	private FontarCryptographicService getCryptographicService() {
		return (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
	}

}
