package com.wzfuji.db.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.wzfuji.db.DAO.DAOBase;

/**
 * @author lucafuji
 * an wrapper of the DB operation:merge,persist,delete or others
 * using the decorator design pattern to merge several operation into one single
 * operation  
 */
public class Operation {
	public Operation() {
		super();
	}

	/**
	 * @param innerOperation the wrapper Operation
	 */
	public Operation(Operation innerOperation) {
		super();
		this.innerOperation = innerOperation;
	}
	
	public void setParameter(Object[] parameter) {
		this.parameters = parameter;
	}
	/**
	 *  the parameters of the operation on the DAO 
	 */
	private Object[] parameters;
	private Operation innerOperation;
	private String operationName;
	private DAOBase dao;
	/**
	 * only the outermost class should be in charge of the begin of transction
	 * and commit of the operation
	 */
	private boolean outerMost;

	public void setOuterMost(boolean outerMost) {
		this.outerMost = outerMost;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public void setDao(DAOBase dao) {
		this.dao = dao;
	}
	
	/**
	 * execution of the actual operation, from the innermost operation to the outermost operation
	 * using reflection to get the method of DAO
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public void execute() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		if(this.outerMost){
			DAOBase.getEntityManager().getTransaction().begin();
		}
		if(this.innerOperation!=null){
			this.innerOperation.execute();
		}
		Class clazz =dao.getClass();
		Class[] parameterTypes = new Class[parameters.length];
		int i=0;
		for(Object parameter:parameters){
			parameterTypes[i++] = parameter.getClass();
		}
		@SuppressWarnings("unchecked")
		Method method =clazz.getDeclaredMethod(operationName, parameterTypes);
	    method.setAccessible(true);
		method.invoke(dao, parameters);
		if(this.outerMost){
			DAOBase.getEntityManager().getTransaction().commit();
		}
	}
}
