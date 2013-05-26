package com.aeeventos.core.dao;



import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.hibernate.HibernateUtil;




/**
 * Home object for domain model class Acreditacion.
 * @see .Acreditacion
 * @author Hibernate Tools
 */
public class AcreditacionHome {

	private static final Log log = LogFactory.getLog(AcreditacionHome.class);

	private final SessionFactory sessionFactory = com.aeeventos.hibernate.HibernateUtil
	.getSessionFactory();

	/**
	 * Para la implementacion del Singleton
	 */
	public static AcreditacionHome instance;

	/**
	 * Devuelve un objeto del DAO (Singleton)
	 * 
	 * @uml.property name="instance"
	 */
	public static AcreditacionHome getInstance() {
		if (instance == null) {
			instance = new AcreditacionHome();
		}
		return instance;
	}

	public void persist(Acreditacion transientInstance) {
		log.debug("persisting Acreditacion instance");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			transientInstance.setFechaInsert(new Date());
			sessionFactory.getCurrentSession().persist(transientInstance);
			session.getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void saveOrUpdate(Acreditacion transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void Update(Acreditacion transientInstance) {
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

	public void attachDirty(Acreditacion instance) {
		log.debug("attaching dirty Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Acreditacion instance) {
		log.debug("attaching clean Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Acreditacion persistentInstance) {
		log.debug("deleting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Acreditacion merge(Acreditacion detachedInstance) {
		log.debug("merging Acreditacion instance");
		try {
			Acreditacion result = (Acreditacion) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Acreditacion findById(int id) {
		log.debug("getting Acreditacion instance with id: " + id);
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Acreditacion instance = (Acreditacion) sessionFactory
					.getCurrentSession().get("com.aeeventos.core.bean.Acreditacion", id);
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
	public List<Acreditacion> findByExample(Acreditacion instance) {
		log.debug("finding Acreditacion instance by example");
		try {
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria("com.aeeventos.core.bean.Acreditacion");
			Example example = Example.create(instance).ignoreCase().enableLike(MatchMode.ANYWHERE);
			Example example2 = Example.create(instance.getAsistente()).ignoreCase().enableLike(MatchMode.ANYWHERE);
			Example example3 = Example.create(instance.getEvento()).ignoreCase().enableLike(MatchMode.ANYWHERE);
			
			criteria.add(example).createCriteria("asistente").add(example2).addOrder(Order.asc("apellido"));
			criteria.createCriteria("evento").add(example3);
			List<Acreditacion> results = (List<Acreditacion>)criteria .list();
			
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
	public List<Acreditacion> findByExampleEnviarMails(Acreditacion instance) {
		log.debug("finding Acreditacion instance by example");
		try {
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria("com.aeeventos.core.bean.Acreditacion");
			Example example = Example.create(instance).ignoreCase().enableLike(MatchMode.ANYWHERE);
			Example example2 = Example.create(instance.getEvento()).ignoreCase().enableLike(MatchMode.ANYWHERE);
			criteria.add(example).createCriteria("evento").add(example2);
			List<Acreditacion> results = (List<Acreditacion>)criteria .list();
			
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
	public Acreditacion validarAcceso(Acreditacion instance)
	{
		log.debug("validating access");
		
		if(instance.getAsistente()==null || instance.getEvento()==null)
		{
			log.debug("el asistente o el evento son nulos");
			return null;
		}
		try {
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
	
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria("com.aeeventos.core.bean.Acreditacion");
			Example example = Example.create(instance);
			Example example2 = Example.create(instance.getAsistente());
			Example example3 = Example.create(instance.getEvento());
			
			criteria.add(example).createCriteria("asistente").add(example2);
			criteria.createCriteria("evento").add(example3);
			List<Acreditacion> results = (List<Acreditacion>)criteria .list();
			session.getTransaction().commit();
			
			if(results.size()>0)
			{
				return results.get(0);
			}
			else
			{
				return null;
			}

	
		}
		catch (RuntimeException re ){
			log.error("validar acceso failed", re);
			throw re;
		}
		
	}
	
	
	
	
}
