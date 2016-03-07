package com.jolo.basic.test.dao;

import org.springframework.stereotype.Repository;

import com.jolo.basic.dao.BaseDao;
import com.jolo.basic.test.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	
	
}

