package com.jolo.cms.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.jolo.basic.model.Pager;
import com.jolo.cms.dao.IGroupDao;
import com.jolo.cms.dao.IRoleDao;
import com.jolo.cms.dao.IUserDao;
import com.jolo.cms.model.CmsException;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserRole;


@Service("userService")
public class UserService implements IUserService {
	
	@Inject
	private IUserDao userDao;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IGroupDao groupDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	private void addUserGroup(User user,Integer i) {
		Group g = groupDao.load(i);
		if(g==null) throw new CmsException("要添加的用户组不存在");
		userDao.addUserGroup(user,g);
		
	}

	private void addUserRole(User user,Integer i) {
		Role r = roleDao.load(i);
		//1、检查角色对象是否存在，如果不存在，就抛出异常
		if(r ==null) throw new CmsException("要添加的用户角色不存在");		
		//2、检查用户角色对象是否已经存在，如果存在，就不添加
		userDao.addUserRole(user,r);
	}

	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		User tmpUser = userDao.loadByUsername(user.getUsername());
		if (tmpUser!=null) throw new CmsException("添加的用户对象已经存在，不能添加");
		userDao.add(user);
		for(Integer i:rids){
			//添加角色对象
			this.addUserRole(user,i);
		}
		for(Integer i:gids){
			//添加用户对象 
			this.addUserGroup(user,i);
		}
	}

	@Override
	public void delete(int id) {
		//TODO 判断用户是否有文章
		
		//1.删除用户管理的角色对象
		userDao.deleteUserGroups(id);
		//2.删除用户管理的组对象
		userDao.deleteUserRoles(id);
		userDao.delete(id);
	}

	@Override
	public void udpate(User user, Integer[] rids, Integer[] gids) {
		//1. 获取用户已经存在的角色ID和组ID
		List<Integer> erids = userDao.listUserRolesId(user.getId());
		List<Integer> egids = userDao.listUserGroupsIds(user.getId());
		
		//2.判断erids中不存在rids就要添加
		for(Integer i :rids){
			if(!erids.contains(i)){
				addUserRole(user, i);
			}
		}
		for(Integer i:gids){
			if(!egids.contains(i)){
				addUserGroup(user,i);
			}
		}
		//3进行删除
		for(Integer i:erids){
			if(!ArrayUtils.contains(rids, i)){
				userDao.deleteUserRole(user.getId(), i);
			}
		}
		for(Integer i:egids){
			if(!ArrayUtils.contains(gids,i)){
				userDao.deleteUserGroup(user.getId(), i);
			}
		}
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void udpatePwd(int uid, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pager<User> findUser() {
		 	return userDao.findUser();
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public List<Role> listUserRoles(int id) {
		return userDao.listUserRoles(id);
	}

	@Override
	public List<Group> listUserGroups(int id) {
		return userDao.listUserGroups(id);

	}

	@Override
	public List<Integer> listUserRoleIds(int id) {
		return userDao.listUserRolesId(id);
	}

	@Override
	public List<Integer> listUserGroupIds(int id) {
		return userDao.listUserGroupsIds(id);
	}

	@Override
	public List<User> listRoleUsers(int rid) {
		return userDao.listRoleUsers(rid);
	}

	@Override
	public List<User> listGroupUser(int gid) {
		return userDao.listGroupUsers(gid);
	}

	@Override
	public void updateStatus(int id) {
		User user = userDao.load(id);
		if(user==null) throw new CmsException("修改状态的用户不存在");
		if(user.getStatus()==1) user.setStatus(0);
		else user.setStatus(1);
		userDao.update(user);
	}

}
