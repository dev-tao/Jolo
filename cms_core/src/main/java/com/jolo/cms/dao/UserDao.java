package com.jolo.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jolo.basic.dao.BaseDao;
import com.jolo.basic.model.Pager;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserGroup;
import com.jolo.cms.model.UserRole;

@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<Role> listUserRoles(int userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserRolesIds(int userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Group> listUserGroups(int userId) {
		String hql = "select ug.group from UserGroup ug where ug.user.id=?";
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
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		return this.list(hql, roleId);
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql, roleType);
	}

	@Override
	public List<User> listGroupUsers(int groupId) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql,groupId);
	}

	@Override
	public void addUserGroup(User user, Group g) {
		UserGroup ug = this.loadUserGroup(user.getId(), g.getId());
		if(ug!=null) return;
		ug = new UserGroup();
		ug.setUser(user);
		ug.setGroup(g);
		this.getSession().save(ug);
	}

	@Override
	public void addUserRole(User user, Role r) {
		UserRole ur = this.loadUserRole(user.getId(), r.getId());
		if(ur!=null)  return;
		ur = new UserRole();
		ur.setRole(r);
		ur.setUser(user);
		this.getSession().save(ur);
	}

	@Override
	public void deleteUserGroups(int id) {
		String hql = "delete UserGroup ug where ug.user.id = ?";
		this.updateByHql(hql, id);
	}

	@Override
	public void deleteUserRoles(int id) {
		String hql = "delete UserRole ur where ur.user.id=?";
		this.updateByHql(hql, id);
	}

	@Override
	public void deleteUserRole(int uid, int rid) {
		String hql = "delete UserRole ur where ur.user.id=? and ur.role.id=?";
		this.updateByHql(hql, new Object[]{uid,rid});
		
	}

	@Override
	public void deleteUserGroup(int uid, int gid) {
		String hql = "delete UserGroup ug where ug.user.id = ? and ug.group.id =?";
		this.updateByHql(hql, new Object[]{uid,gid});
		
	}

	@Override
	public Pager<User> findUser() {
		String hql = "from User";
		return this.find(hql);
	}
	
}
