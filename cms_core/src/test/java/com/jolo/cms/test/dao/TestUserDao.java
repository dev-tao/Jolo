package com.jolo.cms.test.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

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
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserGroup;
import com.jolo.cms.model.UserRole;
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
	public void testListUserRoles() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<Role> actuals = userDao.listUserRoles(2);
		List<Role>  expected= Arrays.asList(new Role(2,"发布人",RoleType.ROLE_PUBLISH),new Role(3,"审核人",RoleType.ROLE_AUDIT));
		
		EntitiesHelper.assertRoles(actuals, expected);
	}
	
	@Test
	public void testListUserRolesId() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		int userId = 2;
		List<Integer>actual =  userDao.listUserRolesId(userId);
		List<Integer> expected = Arrays.asList(2,3);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testListUserGroups() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
	    List<Group> actual = userDao.listUserGroups(2);
	    List<Group> expected = Arrays.asList(new Group(1,"财务处","负责财务部门网站"),new Group(2,"计科系","负责计科系网站"),new Group(3,"宣传部","负责宣传部网站"));
	    
	    EntitiesHelper.assertGroups(expected, actual);
	}
	
	@Test
	public void testListUserGroupsIds() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<Integer> actual = userDao.listUserGroupsIds(2);
		List<Integer> expected = Arrays.asList(1,2,3);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLoadUserRole() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		UserRole actual = userDao.loadUserRole(2, 3);
		User eu = new User(2,"admin2","123","admin2","admin2@email.com","120",1);
		Role er = new Role(3,"审核人",RoleType.ROLE_AUDIT);
		
		EntitiesHelper.assertUser(eu,actual.getUser());
		EntitiesHelper.assertRole(er,actual.getRole());
	}
	
	@Test
	public void testLoadUserGroup() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		UserGroup actual= userDao.loadUserGroup(2, 1);
		User eu = new User(2,"admin2","123","admin2","admin2@email.com","120",1);
		Group eg = new Group(1,"财务处","负责财务部门网站");
		
		EntitiesHelper.assertUser(eu,actual.getUser());
		EntitiesHelper.assertGroup(eg,actual.getGroup());
	}
	
	@Test
	public void testLoadByUsername() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		User actual = userDao.loadByUsername("admin2");
		User expected = new User(2,"admin2","123","admin2","admin2@email.com","120",1);
		EntitiesHelper.assertUser(expected,actual);
		
	}
	
	@Test
	public void testListRoleUsers() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<User> actual = userDao.listRoleUsers(2);
		List<User> expected = Arrays.asList(new User(2,"admin2","123","admin2","admin2@email.com","120",1),new User(3,"admin3","123","admin3","admin3@email.com","130",1));
		
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testListRoleUsersByType() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<User> actual = userDao.listRoleUsers(RoleType.ROLE_AUDIT);
		List<User> expected = Arrays.asList(new User(2,"admin2","123","admin2","admin2@email.com","120",1));
		EntitiesHelper.assertUsers(expected, actual);
	}
	
	@Test
	public void testListGroupUsers() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("dataset");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		List<User> actual = userDao.listGroupUsers(3);
		List<User> expected = Arrays.asList(new User(2,"admin2","123","admin2","admin2@email.com","120",1),new User(3,"admin3","123","admin3","admin3@email.com","130",1));

		EntitiesHelper.assertUsers(expected, actual);
	}
	
}
