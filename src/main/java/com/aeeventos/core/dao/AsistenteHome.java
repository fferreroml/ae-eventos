package com.aeeventos.core.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.exception.ConstraintViolationException;

import com.aeeventos.core.bean.Asistente;
import com.aeeventos.hibernate.HibernateUtil;



/**
 * Home object for domain model class Asistente.
 * 
 * @see .Asistente
 * @author Hibernate Tools
 */
public class AsistenteHome {

	private static final Log log = LogFactory.getLog(AsistenteHome.class);

	private final SessionFactory sessionFactory = com.aeeventos.hibernate.HibernateUtil
			.getSessionFactory();

	/**
	 * Para la implementacion del Singleton
	 */
	public static AsistenteHome instance;

	/**
	 * Devuelve un objeto del DAO (Singleton)
	 * 
	 * @uml.property name="instance"
	 */
	public static AsistenteHome getInstance() {
		if (instance == null) {
			instance = new AsistenteHome();
		}
		return instance;
	}

	public void persist(Asistente transientInstance) {
		log.debug("persisting Asistente instance");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			transientInstance.setFechaInsert(new Date());
			sessionFactory.getCurrentSession().persist(transientInstance);
			session.getTransaction().commit();
			log.debug("persist successful");
		}
		catch (ConstraintViolationException ce) {
			session.getTransaction().rollback();
			log.error("Dni repetido" , ce);
			throw ce;
		}
		catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(Asistente transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void Update(Asistente transientInstance) {
		log.debug("persisting Acreditacion instance");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			sessionFactory.getCurrentSession().update(transientInstance);
			session.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Asistente instance) {
		log.debug("attaching dirty Asistente instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Asistente instance) {
		log.debug("attaching clean Asistente instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Asistente persistentInstance) {
		log.debug("deleting Asistente instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Asistente merge(Asistente detachedInstance) {
		log.debug("merging Asistente instance");
		try {
			Asistente result = (Asistente) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Asistente findById(int id) {
		log.debug("getting Asistente instance with id: " + id);
		try {
			Asistente instance = (Asistente) sessionFactory.getCurrentSession()
					.get("com.aeeventos.core.bean.Asistente", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Asistente> findByExample(Asistente instance) {
		log.debug("finding Asistente instance by example");
		try {
			List<Asistente> results = (List<Asistente>) sessionFactory
					.getCurrentSession().createCriteria(
							"com.aeeventos.core.bean.Asistente").add(
							Example.create(instance).ignoreCase().enableLike(
									MatchMode.ANYWHERE)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Asistente findByDni(String dni) {
		log.debug("getting Asistente instance with dni: " + dni);
		Asistente instance = new Asistente();
		instance.setDni(dni);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {
			List<Asistente> results = (List<Asistente>) sessionFactory
					.getCurrentSession().createCriteria(
							"com.aeeventos.core.bean.Asistente").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			if(results.size()>0)
			{
			return results.get(0);
			}
			else return null;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	


}
