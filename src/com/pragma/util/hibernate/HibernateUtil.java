package com.pragma.util.hibernate;

import org.hibernate.SessionFactory;

import com.pragma.util.ContextUtil;

/**
 * Clase singleton para mantener la SessionFactory de Hibernate 
 * @author fferrara
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = (SessionFactory)ContextUtil.getBean("sessionFactory");
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
