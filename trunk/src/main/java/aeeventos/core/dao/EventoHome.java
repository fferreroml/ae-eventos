package aeeventos.core.dao;


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import aeeventos.core.bean.Evento;
import aeeventos.hibernate.HibernateUtil;




/**
 * Home object for domain model class Evento.
 * @see .Evento
 * @author Hibernate Tools
 */



public class EventoHome {
	
    public static final String EVENTO_NOMBRE = "nombre";

	private static final Log log = LogFactory.getLog(EventoHome.class);

	private final SessionFactory sessionFactory = aeeventos.hibernate.HibernateUtil
	.getSessionFactory();

	
	
	/**
	 * Para la implementacion del Singleton
	 */
	public static EventoHome instance;

	/**
	 * Devuelve un objeto del DAO (Singleton)
	 * 
	 * @uml.property name="instance"
	 */
	public static EventoHome getInstance() {
		if (instance == null) {
			instance = new EventoHome();
		}
		return instance;
	}
	

	public void persist(Evento transientInstance) {
		log.debug("persisting Evento instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void saveOrUpdate(Evento transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void update(Evento transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			sessionFactory.getCurrentSession().update(transientInstance);
			session.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Evento instance) {
		log.debug("attaching dirty Evento instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Evento instance) {
		log.debug("attaching clean Evento instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Evento persistentInstance) {
		log.debug("deleting Evento instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Evento merge(Evento detachedInstance) {
		log.debug("merging Evento instance");
		try {
			Evento result = (Evento) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Evento findById(int id) {
		log.debug("getting Evento instance with id: " + id);
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Evento instance = (Evento) sessionFactory.getCurrentSession().get(
					"com.acreditaciones.core.bean.Evento", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			session.getTransaction().commit();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Evento> findByExampleUsuario(Evento instance) {
		log.debug("finding Evento instance by example");
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Evento> results = (List<Evento>) sessionFactory
					.getCurrentSession().createCriteria("com.acreditaciones.core.bean.Evento")
					.add(Example.create(instance).ignoreCase().enableLike(MatchMode.ANYWHERE))
					.createCriteria("usuario").add(Example.create(instance.getUsuario()).enableLike(MatchMode.ANYWHERE))
					.addOrder(Order.asc(EVENTO_NOMBRE)).list();
			session.getTransaction().commit();
						
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> findByExample(Evento instance) {
		log.debug("finding Evento instance by example");
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Evento> results = (List<Evento>) sessionFactory
					.getCurrentSession().createCriteria("com.acreditaciones.core.bean.Evento")
					.add(Example.create(instance)).list();
			session.getTransaction().commit();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
		
	/*
	 * Metodo no autogenerado por herramienta HibernateTools.
	 */
	
	@SuppressWarnings("unchecked")
	public List<Evento> findAll() {
		log.debug("finding all Empleado instances");
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String queryString = "from Evento order by nombre asc";
			Query queryObject = sessionFactory
			.getCurrentSession().createQuery(queryString);
                        List<Evento> list= (List<Evento>)queryObject.list();
            session.getTransaction().commit();
                        
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	

}
