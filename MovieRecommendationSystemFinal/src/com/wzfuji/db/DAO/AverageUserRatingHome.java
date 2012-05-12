package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.AverageUserRating;
/**
 * Home object for domain model class AverageUserRating.
 * @see com.wzfuji.db.entity.AverageUserRating
 * @author Hibernate Tools
 */
@Stateless
public class AverageUserRatingHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(AverageUserRating transientInstance) {
		log.debug("persisting AverageUserRating instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(AverageUserRating persistentInstance) {
		log.debug("removing AverageUserRating instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public AverageUserRating merge(AverageUserRating detachedInstance) {
		log.debug("merging AverageUserRating instance");
		try {
			AverageUserRating result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public AverageUserRating findById(int id) {
		log.debug("getting AverageUserRating instance with id: " + id);
		try {
			AverageUserRating instance = entityManager.find(
					AverageUserRating.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
