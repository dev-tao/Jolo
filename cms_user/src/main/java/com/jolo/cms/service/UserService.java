package com.jolo.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jolo.basic.model.Pager;
import com.jolo.cms.dao.IGroupDao;
import com.jolo.cms.dao.IRoleDao;
import com.jolo.cms.dao.IUserDao;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.User;


@Service("userService")
public class UserService implements IUserService {
	
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	
	
	
	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void udpate(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void udpatePwd(int uid, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateState(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pager<User> findUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> listUserRoles(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserGroup(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listUserRoleIds(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listUserGroupIds(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listRoleUsers(int rid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listGroupUser(int gid) {
		// TODO Auto-generated method stub
		return null;
	}

}
