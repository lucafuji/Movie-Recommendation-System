package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.MaxentUserProfile;

/**
 * Home object for domain model class MaxentUserProfile.
 * @see com.wzfuji.db.entity.MaxentUserProfile
 * @author Hibernate Tools
 */
@Stateless
public class MaxentUserProfileHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(MaxentUserProfile transientInstance) {
		log.debug("persisting MaxentUserProfile instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(MaxentUserProfile persistentInstance) {
		log.debug("removing MaxentUserProfile instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public MaxentUserProfile merge(MaxentUserProfile detachedInstance) {
		log.debug("merging MaxentUserProfile instance");
		try {
			MaxentUserProfile result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MaxentUserProfile findById(int id) {
		log.debug("getting MaxentUserProfile instance with id: " + id);
		try {
			MaxentUserProfile instance = entityManager.find(
					MaxentUserProfile.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
