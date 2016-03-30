package com.jolo.cms.service;

import java.util.List;

import com.jolo.basic.model.Pager;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.User;

public interface IUserService {
	
	/**
	 * 添加用户 判断用户是否存在 若存在抛出异常	
	 * @param user 用户对象
	 * @param rids 用户的所有角色对象
	 * @param gids 用户的所有组对象
	 */
	public void add(User user ,Integer[] rids,Integer[] gids);
	
	
	/**
	 * 删除用户 
	 * 需要把用户、用户角色、用户组关系删除
	 * 如果用户存在相应的文章不能删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 用户的更新，如果rids的角色在用户中已经存在，就不做操作
	 * 如果rids的角色在用户中不存在就做添加，如果用户的角色不存在于rids中需要删除
	 * 对于group同样要做这个操作
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void update(User user,Integer[] rids,Integer[] gids);
	
	public void update(User user);
	
	/**
	 * 更新密码
	 * @param uid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(int uid,String oldPwd, String newPwd);
	
	/**
	 * 列表用户
	 * @return
	 */
	public Pager<User> findUser();
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	public User load(int id);
	
	/**
	 * 获取用户的所有角色ID
	 * @param id
	 * @return
	 */
	public List<Role> listUserRoles(int id);
	
	/**
	 * 获取用户的所有组信息
	 * @param id
	 * @return
	 */
	public List<Group> listUserGroups(int id);
	
	public List<Integer> listUserRoleIds(int id);
	
	public List<Integer> listUserGroupIds(int id);
	
	public List<User> listRoleUsers(int rid);
	
	public List<User> listGroupUser(int gid);
	
	/**
	 * 更新用户状态
	 * @param id
	 */
	public void updateStatus(int id);
	
}
