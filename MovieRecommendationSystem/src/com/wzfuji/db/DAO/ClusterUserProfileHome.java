package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.ClusterUserProfile;

/**
 * Home object for domain model class ClusterUserProfile.
 * @see com.wzfuji.db.entity.ClusterUserProfile
 * @author Hibernate Tools
 */
@Stateless
public class ClusterUserProfileHome {

	private static final Log log = LogFactory
			.getLog(ClusterUserProfileHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ClusterUserProfile transientInstance) {
		log.debug("persisting ClusterUserProfile instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ClusterUserProfile persistentInstance) {
		log.debug("removing ClusterUserProfile instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ClusterUserProfile merge(ClusterUserProfile detachedInstance) {
		log.debug("merging ClusterUserProfile instance");
		try {
			ClusterUserProfile result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ClusterUserProfile findById(int id) {
		log.debug("getting ClusterUserProfile instance with id: " + id);
		try {
			ClusterUserProfile instance = entityManager.find(
					ClusterUserProfile.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
