package com.wzfuji.db.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author lucafuji
 * this class functions as base for other dao which provides an singleton of entityManagerFactory
 * and entity manager
 */
public class DAOBase {
	/**
	 * initialize Entity Manager Factory with the persistence unit name
	 */
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("msr");;
	protected static EntityManager entityManager =emf.createEntityManager();
	
	public static EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param strQuery
	 * @return all the entries that match the query
	 */
	public List findEntityBySql(String strQuery) {
		Query query = entityManager.createQuery(strQuery);
		return query.getResultList();
	}

	/**
	 * @param strQuery
	 * @return single result that match the query
	 */
	public Object findSingleEntityBySql(String strQuery) {
		Query query = entityManager.createQuery(strQuery);
		return query.getSingleResult();
	}

	
	/**
	 * @param strQuery
	 * @param instanceLimited upperbound of the number of result that match the query
	 * @return all the entries that match the sqlQuery
	 */
	public List findEntityBySql(String strQuery, int instanceLimited) {
		Query query = entityManager.createQuery(strQuery);
		query.setMaxResults(instanceLimited);
		return query.getResultList();
	}
	
}
