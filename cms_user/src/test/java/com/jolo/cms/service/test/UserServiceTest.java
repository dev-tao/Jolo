package com.jolo.cms.service.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;





import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.Assert;
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
	private IUserService userService;
	
	@Inject
	private IUserDao userDao;
	
	@Inject
	private IGroupDao groupDao;
	
	@Inject
	private IRoleDao roleDao;

	private User baseUser = new User(1,"admin1","123","admin1","admin1@","123",0);
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testAdd() {
		EasyMock.reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处","");
		EasyMock.expect(userDao.loadByUsername("admin1")).andReturn(null);
		EasyMock.expect(userDao.add(baseUser)).andReturn(baseUser);
		
		EasyMock.expect(roleDao.load(rids[0])).andReturn(r);
		userDao.addUserRole(baseUser, r);
		EasyMock.expectLastCall();
		
		r.setId(2);
		EasyMock.expect(roleDao.load(rids[1])).andReturn(r);
		userDao.addUserRole(baseUser, r);
		EasyMock.expectLastCall();
		
		EasyMock.expect(groupDao.load(gids[0])).andReturn(g);
		userDao.addUserGroup(baseUser, g);
		EasyMock.expectLastCall();
		
		g.setId(3);
		EasyMock.expect(groupDao.load(gids[1])).andReturn(g);
		userDao.addUserGroup(baseUser, g);
		EasyMock.expectLastCall();
		
		EasyMock.replay(userDao,roleDao,groupDao);
		
		userService.add(baseUser, rids, gids);
		
		EasyMock.verify(userDao,roleDao,groupDao);
		
		
	}

	@Test
	public void testDelete() {
		EasyMock.reset(userDao);
		int uid = 2;
		userDao.deleteUserGroups(uid);
		EasyMock.expectLastCall();
		userDao.deleteUserRoles(uid);
		EasyMock.expectLastCall();
		userDao.delete(uid);
		EasyMock.expectLastCall();
		EasyMock.replay(userDao);
		userService.delete(uid);
		EasyMock.verify(userDao);
	}

	@Test
	public void testUpdate() {
		EasyMock.reset(userDao,roleDao,groupDao);
		Integer[] nids = {1,2};
		List<Integer> eids = Arrays.asList(2,3);
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(1,"财务处","");
		
		//验证获取存在的角色id和组id
		EasyMock.expect(userDao.listUserRolesIds(baseUser.getId())).andReturn(eids);
		EasyMock.expect(userDao.listUserGroupsIds(baseUser.getId())).andReturn(eids);
		EasyMock.expect(roleDao.load(1)).andReturn(r);
		//验证添加角色和组是否正确
		EasyMock.expect(groupDao.load(1)).andReturn(g);
		userDao.addUserRole(baseUser,r);
		EasyMock.expectLastCall();
		userDao.addUserGroup(baseUser, g);
		EasyMock.expectLastCall();

		//验证删除角色和组是否正确
		userDao.deleteUserRole(baseUser.getId(),3);
		EasyMock.expectLastCall();
		userDao.deleteUserGroup(baseUser.getId(),3);
		EasyMock.expectLastCall();
		
		EasyMock.replay(userDao,roleDao,groupDao);
		userService.update(baseUser,nids,nids);
		
		EasyMock.verify(userDao,roleDao,groupDao);
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
	public void testUpdateStatus() {
		int uid = 2;
		EasyMock.reset(userDao);
		EasyMock.expect(userDao.load(uid)).andReturn(baseUser);
		userDao.update(baseUser);
		EasyMock.expectLastCall();
		EasyMock.replay(userDao);
		userService.updateStatus(uid);
		Assert.assertEquals(baseUser.getStatus(), 1);
		EasyMock.verify(userDao);
		
	}

}
