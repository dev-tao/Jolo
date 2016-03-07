package com.jolo.cms.dao;

import java.util.List;

import com.jolo.basic.dao.BaseDao;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserGroup;

public class UserDao extends BaseDao<User> implements IUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> listUserRoles(int userId) {
		String hql = "select ru.role from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserRolesId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserGroups(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listUserGroupsIds(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.jolo.cms.model.UserRole loadUserRole(int userId, int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroup loadUserGroup(int userId, int gourpId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loadByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listRoleUsers(int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listGroupUsers(int goupId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
