package com.jolo.cms.test.util;

import java.util.Date;
import java.util.List;

import org.junit.Assert;

import com.jolo.cms.dao.UserDao;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.User;



public class EntitiesHelper {
	private static User baseUser = new User(1,"admin1","123","admin1","admin1@admin.com","110",1);
	private UserDao userDao;
	
	public static void assertUser(User expected,User actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getNickname(),actual.getNickname());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getStatus(),actual.getStatus());
	}
	
	public static void assertUsers(List<User> expected,List<User> actuals) {
		for(int i=0;i<expected.size();i++) {
			User eu = expected.get(i);
			User au = actuals.get(i);
			assertUser(eu, au);
		}
	}
	
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
	
	
	public static void assertRole(Role expected,Role actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getRoleType(),actual.getRoleType());
	}
	
	public static void assertRoles(List<Role> expected,List<Role> actuals) {
		for(int i=0;i<expected.size();i++) {
			Role eu = expected.get(i);
			Role au = actuals.get(i);
			assertRole(eu, au);
		}
	}
	
	
	
}
