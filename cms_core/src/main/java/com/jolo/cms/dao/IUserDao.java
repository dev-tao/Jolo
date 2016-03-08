package com.jolo.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jolo.basic.dao.IBaseDao;
import com.jolo.cms.model.Group;
import com.jolo.cms.model.Role;
import com.jolo.cms.model.RoleType;
import com.jolo.cms.model.User;
import com.jolo.cms.model.UserGroup;
import com.jolo.cms.model.UserRole;


public interface IUserDao extends IBaseDao<User>{
	
	/**
	 * 添加用户对象
	 * @param user 用户对象
	 * @param roleIds 用户的所有角色ID
	 * @param groupIds 用户的所有组ID
	 */
	public void add(User user ,Integer[] roleIds,Integer[] groupIds);
	
	/**
	 * 更新用户对象
	 * @param user 用户对象
	 * @param roleIds 用户的所有角色ID
	 * @param groupIds 用户的所有组ID
	 */
	public void update(User user,Integer[] roleIds,Integer[] groupIds);
	
	/**
	 * 获取用户的所有角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	
	/**
	 * 获取用户的所有角色ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserRolesId(int userId);
	
	/**
	 * 获取用户的所有组信息
	 * @param userId
	 * @return
	 */
	public List<Group> listUserGroups(int userId);
	
	/**
	 * 获取用户的所有组ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserGroupsIds(int userId);
	
	/**
	 * 根据用户id和角色id获取用户角色关联对象
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int userId,int roleId);
	
	/**
	 * 根据用户id和组id获取组关联对象
	 * @param userId
	 * @param gourpId
	 * @return
	 */
	public UserGroup loadUserGroup(int userId,int group);
	
	/**
	 * 通过用户名获取用户对象
	 * @param username
	 * @return
	 */
	public User loadByUsername(String username);
	
	/**
	 * 根据角色ID获取所有用户
	 * @param roleId
	 * @return
	 */
	public List<User> listRoleUsers(int roleId);
	
	/**
	 * 根据角色类型获取所有用户
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers(RoleType roleType);
	
	/**
	 * 获取某个组的用户对象
	 * @param goupId
	 * @return
	 */
	public List<User> listGroupUsers(int groupId);
	
	
	
}
