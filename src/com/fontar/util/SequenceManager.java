package com.fontar.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.id.IdentifierGeneratorFactory;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;

public class SequenceManager {

	private static SequenceManager instance;

	private SequenceManager() {
	}

	public synchronized static SequenceManager getInstance() {
		if (instance == null) {

			instance = new SequenceManager();
		}
		return instance;
	}

	public Serializable generate(Session session, String sequence) throws HibernateException {

		return generate(session, "java.lang.Long", sequence);
	}

	public Serializable generate(Session session, String typeName, String sequence) throws HibernateException {

		Type identifierType = TypeFactory.heuristicType(typeName, null);

		String sql = "SELECT " + sequence + ".nextval FROM DUAL";
		try {

			PreparedStatement st = ((SessionImplementor) session).getBatcher().prepareSelectStatement(sql);
			try {
				ResultSet rs = st.executeQuery();
				final Serializable result;
				try {
					rs.next();
					result = IdentifierGeneratorFactory.get(rs, identifierType);
				}
				finally {
					rs.close();
				}
				return result;
			}
			finally {
				((SessionImplementor) session).getBatcher().closeStatement(st);
			}
		}
		catch (SQLException sqle) {
			throw JDBCExceptionHelper.convert(((SessionImplementor) session).getFactory().getSQLExceptionConverter(), sqle, "could not get next sequence value", sql);
		}
	}

}
