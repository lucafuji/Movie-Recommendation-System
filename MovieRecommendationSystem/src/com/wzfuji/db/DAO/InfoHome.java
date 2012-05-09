package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.Info;

/**
 * Home object for domain model class Info.
 * @see com.wzfuji.db.entity.Info
 * @author Hibernate Tools
 */
@Stateless
public class InfoHome {

	private static final Log log = LogFactory.getLog(InfoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Info transientInstance) {
		log.debug("persisting Info instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Info persistentInstance) {
		log.debug("removing Info instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Info merge(Info detachedInstance) {
		log.debug("merging Info instance");
		try {
			Info result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Info findById(String id) {
		log.debug("getting Info instance with id: " + id);
		try {
			Info instance = entityManager.find(Info.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
