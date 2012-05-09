package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.Rating;
import com.wzfuji.db.entity.RatingId;

/**
 * Home object for domain model class Rating.
 * @see com.wzfuji.db.entity.Rating
 * @author Hibernate Tools
 */
@Stateless
public class RatingHome {

	private static final Log log = LogFactory.getLog(RatingHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Rating transientInstance) {
		log.debug("persisting Rating instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Rating persistentInstance) {
		log.debug("removing Rating instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Rating merge(Rating detachedInstance) {
		log.debug("merging Rating instance");
		try {
			Rating result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Rating findById(RatingId id) {
		log.debug("getting Rating instance with id: " + id);
		try {
			Rating instance = entityManager.find(Rating.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
