package com.wzfuji.db.DAO;

// Generated May 9, 2012 3:14:10 PM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wzfuji.db.entity.Userinfo;

/**
 * Home object for domain model class Userinfo.
 * @see com.wzfuji.db.entity.Userinfo
 * @author Hibernate Tools
 */
@Stateless
public class UserinfoHome extends DAOBase{

	private static final Log log = LogFactory.getLog(OccupationHome.class);

	public void persist(Userinfo transientInstance) {
		log.debug("persisting Userinfo instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Userinfo persistentInstance) {
		log.debug("removing Userinfo instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Userinfo merge(Userinfo detachedInstance) {
		log.debug("merging Userinfo instance");
		try {
			Userinfo result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Userinfo findById(int id) {
		log.debug("getting Userinfo instance with id: " + id);
		try {
			Userinfo instance = entityManager.find(Userinfo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
