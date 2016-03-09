package com.jolo.cms.test.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jolo.cms.dao.IUserDao;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.test.util.AbstractDbUnitTestCase;
import com.jolo.cms.test.util.EntitiesHelper;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/beans.xml"})
public class TestUserDao extends AbstractDbUnitTestCase{
	
	@Inject
	private IUserDao userDao;
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException{
		Session s = sessionFactory.openSession();  //打开一个新的session
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));  //将session绑定到事务异步管理器中 
		//backupAllTable();
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);//从事务管理器中获取session  并刷新session
		this.resumeTable();
	}
	
	@Test
	public void testListUserRoles() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<Role> roles = userDao.listUserRoles(2);
		List<Role> actuals = Arrays.asList(new Role(2,"发布人",RoleType.ROLE_PUBLISH),new Role(3,"审核人",RoleType.ROLE_AUDIT));
		
		EntitiesHelper.assertRoles(roles, actuals);
	}
	
	
	
}
