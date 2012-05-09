package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.Iteminfo;

/**
 * Home object for domain model class Iteminfo.
 * @see com.wzfuji.db.entity.Iteminfo
 * @author Hibernate Tools
 */
@Stateless
public class IteminfoHome {

	private static final Log log = LogFactory.getLog(IteminfoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Iteminfo transientInstance) {
		log.debug("persisting Iteminfo instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Iteminfo persistentInstance) {
		log.debug("removing Iteminfo instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Iteminfo merge(Iteminfo detachedInstance) {
		log.debug("merging Iteminfo instance");
		try {
			Iteminfo result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Iteminfo findById(int id) {
		log.debug("getting Iteminfo instance with id: " + id);
		try {
			Iteminfo instance = entityManager.find(Iteminfo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
