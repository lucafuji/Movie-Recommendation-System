package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.Occupation;

/**
 * Home object for domain model class Occupation.
 * @see com.wzfuji.db.entity.Occupation
 * @author Hibernate Tools
 */
@Stateless
public class OccupationHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(Occupation transientInstance) {
		log.debug("persisting Occupation instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Occupation persistentInstance) {
		log.debug("removing Occupation instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Occupation merge(Occupation detachedInstance) {
		log.debug("merging Occupation instance");
		try {
			Occupation result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Occupation findById(String id) {
		log.debug("getting Occupation instance with id: " + id);
		try {
			Occupation instance = entityManager.find(Occupation.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
