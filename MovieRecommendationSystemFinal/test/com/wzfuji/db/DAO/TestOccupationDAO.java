package com.wzfuji.db.DAO;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.wzfuji.db.DAO.OccupationHome;
import com.wzfuji.db.entity.Occupation;
import com.wzfuji.db.util.Operation;

public class TestOccupationDAO {

	@Test
	public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OccupationHome  oHome = new OccupationHome();
		Operation op = new Operation();
		op.setDao(oHome);
		op.setOperationName("persist");
		op.setOuterMost(true);
		op.setParameter(new Object[]{new Occupation("student")});
		op.execute();
	}

}
