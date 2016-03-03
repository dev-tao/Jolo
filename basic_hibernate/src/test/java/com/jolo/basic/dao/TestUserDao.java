package com.jolo.basic.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jolo.basic.model.User;
import com.jolo.basic.test.util.AbstractDbUnitTestCase;
import com.jolo.basic.test.util.EntitiesHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/beans.xml"})
public class TestUserDao extends AbstractDbUnitTestCase{
	
	@Inject
	private IUserDao userDao;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException{
		this.backupAllTable();
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		this.resumeTable();
	}
	
	@Test
	public void testLoad() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		User u = userDao.load(1);
		EntitiesHelper.assertUser(u);
		
	}
	
	
}
