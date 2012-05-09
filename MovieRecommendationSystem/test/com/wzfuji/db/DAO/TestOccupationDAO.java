package com.wzfuji.db.DAO;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wzfuji.db.DAO.OccupationHome;
import com.wzfuji.db.entity.Occupation;

public class TestOccupationDAO {

	@Test
	public void test() {
		OccupationHome  oHome = new OccupationHome();
		oHome.persist(new Occupation("shit"));
	}

}
