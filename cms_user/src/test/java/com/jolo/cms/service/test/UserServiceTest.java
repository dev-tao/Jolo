package com.jolo.cms.service.test;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jolo.basic.model.Pager;
import com.jolo.cms.dao.GroupDao;
import com.jolo.cms.dao.IGroupDao;
import com.jolo.cms.dao.IRoleDao;
import com.jolo.cms.dao.IUserDao;
import com.jolo.cms.dao.RoleDao;
import com.jolo.cms.dao.UserDao;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.service.IUserService;
import com.jolo.cms.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-beans.xml"})
public class UserServiceTest {

	@Inject
	IUserService userService;
	
	@Inject
	IUserDao userDao;
	
	@Inject
	IGroupDao groupDao;
	
	@Inject
	IRoleDao roleDao;
	
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testAdd() {
		
		Integer[] rids = {1,2};
		Integer[] gids = {1,2};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处","");
		User user = new User(2,"admin2","123","admin2","admin2@email.com","120",1);
		userService.add(user, rids, gids);
		
		
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUdpate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUdpatePwd() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUser() {
		//easy mock第一个测试
		EasyMock.reset(userDao);
		EasyMock.expect(userDao.findUser()).andReturn(new Pager<User>());
		EasyMock.replay(userDao);
		userService.findUser();
		EasyMock.verify(userDao);
	}

	@Test
	public void testLoad() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUserRoles() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUserGroups() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUserRoleIds() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUserGroupIds() {
		fail("Not yet implemented");
	}

	@Test
	public void testListRoleUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testListGroupUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStatus() {
		fail("Not yet implemented");
	}

}
