package com.jolo.basic.test.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jolo.basic.model.PageContext;
import com.jolo.basic.model.Pager;
import com.jolo.basic.test.model.User;
import com.jolo.basic.test.util.AbstractDbUnitTestCase;
import com.jolo.basic.test.util.EntitiesHelper;


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
		backupAllTable();
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
	public void testLoad() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		User u = (User)userDao.load(1);
		EntitiesHelper.assertUser(u);
	}
	
	@Test(expected=ObjectNotFoundException.class)
	public void testDelete() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		userDao.delete(1);
		User u = userDao.load(1);
		System.out.println(u.getUsername());
	}
	
	/*********************************HQL测试************************************/
	@Test
	public void testListsByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		
		List<User> expected = userDao.list("from User where id >? and id <?",new Object[]{1,4});
		List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testListsByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("asc");
		PageContext.setSort("id");
		
		Map<String,Object> alias = new HashMap<String,Object>();
		alias.put("ids", Arrays.asList(1,2,3,5,6,7,8,9,10));
		
		List<User> expected = userDao.list("from User where id >? and id <? and id in (:ids)",new Object[]{1,5},alias);
		List<User> actuals = Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(2==expected.size());
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testFindByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		PageContext.setPageSize(2);
		PageContext.setPageOffset(0);
		
		Pager<User> expected = userDao.find("from User where id >=? and id <=? ",new Object[]{1,10});
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(10==expected.getTotal());
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		PageContext.setPageSize(3);
		PageContext.setPageOffset(0);
		
		Map<String,Object> alias = new HashMap<String,Object>();
		alias.put("ids", Arrays.asList(1,2,3,5,6,7,9,10));
		
		Pager<User> expected = userDao.find("from User where id >=? and id <=?  and id in(:ids)",new Object[]{1,10},alias);
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(7,"admin7"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(8==expected.getTotal());
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	/*********************************SQL测试************************************/
	@Test
	public void testListsBySqlArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		
		List<User> expected = userDao.listBySql("select * from t_user  where id >? and id <?",new Object[]{1,4},User.class,true);
		List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testListsBySqlArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("asc");
		PageContext.setSort("id");
		
		Map<String,Object> alias = new HashMap<String,Object>();
		alias.put("ids", Arrays.asList(1,2,3,5,6,7,8,9,10));
		
		List<User> expected = userDao.listBySql("select * from t_user where id >? and id <? and id in (:ids)",new Object[]{1,5},alias,User.class,true);
		List<User> actuals = Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(2==expected.size());
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	
	@Test
	public void testFindBySqlArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		PageContext.setPageSize(2);
		PageContext.setPageOffset(0);
		
		Pager<User> expected = userDao.findBySql("select * from t_user where id >=? and id <=? ",new Object[]{1,10},User.class,true);
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(10==expected.getTotal());
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testFindBySqlArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		PageContext.setOrder("desc");
		PageContext.setSort("id");
		PageContext.setPageSize(3);
		PageContext.setPageOffset(0);
		
		Map<String,Object> alias = new HashMap<String,Object>();
		alias.put("ids", Arrays.asList(1,2,3,5,6,7,9,10));
		
		Pager<User> expected = userDao.findBySql("select * from t_user where id >=? and id <=?  and id in(:ids)",new Object[]{1,10},alias,User.class,true);
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(7,"admin7"));
		
		Assert.assertNotNull(expected);
		Assert.assertTrue(8==expected.getTotal());
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	
	
	
}
