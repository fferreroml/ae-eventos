package com.aeeventos.hibernate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase para obtener la factoria de sesiones de hibernate.
 *
 * @author fferrero
 */
public class HibernateUtil {
	  private static final SessionFactory sessionFactory = buildSessionFactory();
	//  private static URL url = Thread.currentThread().getContextClassLoader().getResource("/WEB-INF/classes/hibernate.cfg.xml"); 
	    
	 /* File f=new File(servlet.getServletContext()
			  .getRealPath("/WEB-INF/classes/hibernate.cfg.xml"));*/
	  
	  private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            return new Configuration().configure().buildSessionFactory();

	        }
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	}

