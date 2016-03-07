package com.jolo.cms.dao;

import java.util.List;

import com.jolo.basic.dao.BaseDao;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserGroup;
import com.jolo.cms.model.UserRole;

@SuppressWarnings("unchecked")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<Role> listUserRoles(int userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserRolesId(int userId) {
		String hql = "select ru.role.id from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Group> listUserGroups(int userId) {
		String hql = "select ug.group from UserGroup ug wehre ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserGroupsIds(int userId) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public UserRole loadUserRole(int userId, int roleId) {
		String hql = "select ur from UserRole ur where ur.user.id=? and ur.role.id=?";
		return (UserRole)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
		
	}

	@Override
	public UserGroup loadUserGroup(int userId, int groupId) {
		String hql = "select ug from UserGroup ug where ug.user.id=? and ug.group.id=?";
		return (UserGroup)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, groupId).uniqueResult();
	}

	@Override
	public User loadByUsername(String username) {
		String hql = "from User u where u.username=?";
		return (User)this.queryObject(hql, username);
	}

	@Override
	public List<User> listRoleUsers(int roleId) {
		String hql = "select ur.user from UserRole ur wehre ur.role.id=?";
		return this.list(hql, roleId);
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql, roleType);
	}

	@Override
	public List<User> listGroupUsers(int groupId) {
		String hql = "select ug.user from UserGroup ug wehre ug.group.id=?";
		return this.list(hql,groupId);
	}

	@Override
	public void add(User user, Integer[] roleIds, Integer[] groupIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user, Integer[] roleIds, Integer[] groupIds) {
		// TODO Auto-generated method stub
		
	}
	
}
