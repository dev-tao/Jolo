package com.jolo.basic.dao;

import org.springframework.stereotype.Repository;

import com.jolo.basic.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IBaseDao<User> {

	
	
}

