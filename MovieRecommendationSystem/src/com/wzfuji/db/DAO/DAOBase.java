package com.wzfuji.db.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DAOBase {
	protected static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("msr");
	protected static EntityManager entityManager = emf.createEntityManager(); 
	
	protected List findEntityBySql(String strQuery){
		Query query = entityManager.createQuery(strQuery);
		return query.getResultList();
	}
	
	protected Object findSingleEntityBySql(String strQuery){
		Query query = entityManager.createQuery(strQuery);
		return query.getSingleResult();
	}
	
	protected List findEntityBySql(String strQuery,int instanceLimited){
		Query query = entityManager.createQuery(strQuery);
		query.setMaxResults(instanceLimited);
		return query.getResultList();
	}
}
