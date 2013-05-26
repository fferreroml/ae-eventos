package aeeventos.core.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import aeeventos.core.bean.Usuario;
import aeeventos.util.ConvertirMD5;


/**
 * Home object for domain model class Usuario.
 * 
 * @see .Usuario
 * @author Hibernate Tools
 */
public class UsuarioHome {

	private static final Log log = LogFactory.getLog(UsuarioHome.class);

	private final SessionFactory sessionFactory = aeeventos.hibernate.HibernateUtil
			.getSessionFactory();

	/**
	 * Para la implementacion del Singleton
	 */
	public static UsuarioHome instance;

	/**
	 * Devuelve un objeto del DAO (Singleton)
	 * 
	 * @uml.property name="instance"
	 */
	public static UsuarioHome getInstance() {
		if (instance == null) {
			instance = new UsuarioHome();
		}
		return instance;
	}
	

	public void persist(Usuario transientInstance) {
		log.debug("persisting Usuario instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(Usuario transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void Update(Usuario transientInstance) {
		log.debug("persisting Acreditacion instance");
		try {
			sessionFactory.getCurrentSession().update(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Usuario instance) {
		log.debug("attaching dirty Usuario instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Usuario instance) {
		log.debug("attaching clean Usuario instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Usuario persistentInstance) {
		log.debug("deleting Usuario instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Usuario merge(Usuario detachedInstance) {
		log.debug("merging Usuario instance");
		try {
			Usuario result = (Usuario) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Usuario findById(int id) {
		log.debug("getting Usuario instance with id: " + id);
		try {
			Usuario instance = (Usuario) sessionFactory.getCurrentSession()
					.get("com.acreditaciones.core.bean.Usuario", id);
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
	public List<Usuario> findByExample(Usuario instance) {
		log.debug("finding Usuario instance by example");
		try {
			List<Usuario> results = (List<Usuario>) sessionFactory
					.getCurrentSession().createCriteria(
							"com.acreditaciones.core.bean.Usuario").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Usuario loguearUsuario(Usuario user) {

		try {
			user.setPassword(ConvertirMD5.stringToMD5(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		List<Usuario> users = this.findByExample(user);

		if (users != null && users.size() > 0) {

			return users.get(0);
		} else {
			return null;
		}

	}
}
