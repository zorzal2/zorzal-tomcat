package com.pragma.toolbar.data;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pragma.toolbar.exception.UnControlledException;
import com.pragma.util.hibernate.HibernateUtil;

public class DatabaseContext {
	/*
	 * Miembros de clase
	 */
	private static Map Contexts = new Hashtable();
	private int transactionCount = 0; 
	/**
	 * Devuelve el contexto actual o uno nuevo si no existe todavía.
	 * @return
	 */
	public static DatabaseContext create() {
		//Si no hay un contexto para el thread actual creo uno
		DatabaseContext context = getCurrent();
		if(context==null) {
			context = new DatabaseContext();
			synchronized (Contexts) {
				Contexts.put(Thread.currentThread(), context);
			}
			//Log.getInstance().debug(" <CONTEXT id=\""+context+"\">");
		}
		//Agrego una referencia
		context.addRef();
		return context;		
	}
	private void setSession(Session session) {
		this.session = session;
	}
	/**
	 * devuelve el contexto actual. No lo crea si no existe.
	 * @return
	 */
	public static DatabaseContext getCurrent() {
		DatabaseContext context;
		synchronized (Contexts) {
			context = (DatabaseContext)Contexts.get(Thread.currentThread());
		}
		return context;
	} 
	
	/**
	 * Cierra el contexto actual cerrando la sesion y commiteando la transaccion.
	 * @throws Exception 
	 */
	public static void destroy() throws UnControlledException {
		DatabaseContext context = (DatabaseContext)Contexts.get(Thread.currentThread());
		if(context!=null) {
			context.delRef();
			//Si nadie mas va a usar este contexto lo libero.
			if(context.hasNotReferences()) {
				synchronized (Contexts) {
					context.freeResources();
					Contexts.remove(Thread.currentThread());
				}
				//Log.getInstance().debug(" </CONTEXT>");
			}
		}
	}
	
	/*
	 * Miembros de instancia
	 */
	
	private Session session=null;
	private Transaction transaction= null;
	private boolean canCloseTransaction = true;
	private int references = 0;
	
	private void addRef() {
		references++;
	}
	private void delRef() {
		references--;
	}
	private boolean hasNotReferences() {
		return references<=0; 
	} 
	
	public void commitTransaction() throws Exception {
		transactionCount --;
		if(transactionCount<0) throw new IllegalStateException();
		if (transactionCount == 0 && getTransaction()!=null) {
			// Con conexiones JDBC
			getSession().flush();
			getTransaction().commit();
			//Log.getInstance().debug("        <COMMIT tx=\""+getTransaction()+"\"/>");
			clearTransaction();
			getSession().flush();
			//Log.getInstance().debug("        </TRANSACTION>");
			//getSession().connection().
			//getSession().reconnect();
			//getSession().flush();
			//getSession().connection().commit();
			//Log.getInstance().debug("[DATABASE] Transaction Commit");
			// Con conexiones JTA
			//getTransaction().commit();
		}
		canCloseTransaction=true;
	}	
	public void rollbackTransaction() {
		transactionCount --;
		if(transactionCount == 0 && getTransaction()!=null) {
			// Con conexiones JDBC
			try {
				//getSession().connection().rollback();
				//Log.getInstance().debug("        <ROLLBACK tx=\""+getTransaction()+"\"/>");
				getTransaction().rollback();
				clearTransaction();
				//Log.getInstance().debug("        </TRANSACTION>");
				getSession().clear();
			} catch (HibernateException exception) {
				exception.printStackTrace();
				throw new UnControlledException("Could not ROLLBACK");
			}
			// Con conexiones JTA
			//getTransaction().rollback();
			//Log.getInstance().debug("[DATABASE] Transaction Rollback");
		}
		canCloseTransaction=true;
	}

	private void freeResources() {
		if(canCloseTransaction) {
			transaction=null;
			Session session = getSession(); 
			if(session!=null && session.isOpen()) {
				try {
					session.flush();
					session.clear();
					//session.connection().close();
					session.close();
					//Log.getInstance().debug("    </SESSION>");
				} catch (Exception exception) {
					exception.printStackTrace();
					throw new UnControlledException("Could ot close the connection");
				}
				setSession(null);
			}
			//Log.getInstance().debug("[DATABASE] freeResources");
		} else {
			System.out.println("COMMIT/ROLLBACK MISSING!");
			throw new UnControlledException("Transaction: COMMIT/ROLLBACK MISSING");
		}
	}

/*	
	private static class SessionGhost implements InvocationHandler {
		Object target;
		public SessionGhost(Object target) {
			this.target = target;
		}
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = method.invoke(this.target, args);
			if (method.getName().equals("save") || method.getName().equals("update")) {
				Session ses = (Session)this.target;
				ses.flush();
			}
			return result;
		}
	}
*/	
	
	/**
	 * Devuelve una sesion. Abre una nueva si es necesario.
	 * Devuelve null solo si no pudo abrir la sesion.
	 * @return
	 * @throws HibernateException 
	 * @throws SQLException 
	 */
	public Session getSession() {
		if(session==null) {
			try {
				Session realSession = HibernateUtil.getSessionFactory().openSession();
				realSession.setFlushMode(FlushMode.COMMIT);
				//realSession.flu
				session = realSession;
				//Log.getInstance().debug("    <SESSION id=\""+realSession+"\">");
				return realSession;
 				//session = (Session)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Session.class}, new SessionGhost(realSession));
			} catch (HibernateException exception) {
				exception.printStackTrace();
				throw new UnControlledException("Could not retrieve session");
			}
		}
		return session;
	}

	/**
	 * Devuelve la transacción actual. Si no hay ninguna en curso devuelve null.
	 * @return
	 */
	private Transaction getTransaction() {
		return transaction;
	}
	private void setTransaction(Transaction tx) {
		transaction=tx;
	}
	private void clearTransaction() {
		transaction=null;
	}
	/**
	 * Inicia una transaccion si no hay ninguna en curso.
	 * @return
	 * @throws HibernateException 
	 * @throws SQLException 
	 */
	public void beginTransaction() {
		if(getTransaction()==null) {
			try {
				setTransaction(getSession().beginTransaction());
				//Log.getInstance().debug("        <TRANSACTION id=\""+transaction+"\">");
			} catch (HibernateException exeption) {
				exeption.printStackTrace();
				throw new UnControlledException("Unable to begin transaction.");
			}
			canCloseTransaction = false;
		}
		transactionCount ++;
	}
}