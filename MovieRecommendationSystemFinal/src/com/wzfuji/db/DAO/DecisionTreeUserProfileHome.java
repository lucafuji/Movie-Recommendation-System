package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.DecisionTreeUserProfile;

/**
 * Home object for domain model class DecisionTreeUserProfile.
 * @see com.wzfuji.db.entity.DecisionTreeUserProfile
 * @author Hibernate Tools
 */
@Stateless
public class DecisionTreeUserProfileHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(DecisionTreeUserProfile transientInstance) {
		log.debug("persisting DecisionTreeUserProfile instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(DecisionTreeUserProfile persistentInstance) {
		log.debug("removing DecisionTreeUserProfile instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public DecisionTreeUserProfile merge(
			DecisionTreeUserProfile detachedInstance) {
		log.debug("merging DecisionTreeUserProfile instance");
		try {
			DecisionTreeUserProfile result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DecisionTreeUserProfile findById(int id) {
		log.debug("getting DecisionTreeUserProfile instance with id: " + id);
		try {
			DecisionTreeUserProfile instance = entityManager.find(
					DecisionTreeUserProfile.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
