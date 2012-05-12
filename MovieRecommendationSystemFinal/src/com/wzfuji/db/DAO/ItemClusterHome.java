package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.ItemCluster;
import com.wzfuji.db.entity.ItemClusterId;

/**
 * Home object for domain model class ItemCluster.
 * @see com.wzfuji.db.entity.ItemCluster
 * @author Hibernate Tools
 */
@Stateless
public class ItemClusterHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(ItemCluster transientInstance) {
		log.debug("persisting ItemCluster instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ItemCluster persistentInstance) {
		log.debug("removing ItemCluster instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ItemCluster merge(ItemCluster detachedInstance) {
		log.debug("merging ItemCluster instance");
		try {
			ItemCluster result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ItemCluster findById(ItemClusterId id) {
		log.debug("getting ItemCluster instance with id: " + id);
		try {
			ItemCluster instance = entityManager.find(ItemCluster.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
