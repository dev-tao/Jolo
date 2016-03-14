package com.jolo.basic.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jolo.basic.dao.BaseDao;
import com.jolo.basic.model.Pager;
import com.jolo.basic.test.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<User> listTest(String string, Object[] objects) {
		return super.list(string, objects);
	}

	@Override
	public List<User> listTest(String string, Object[] objects,
			Map<String, Object> alias) {
		return super.list(string, objects, alias);
	}

	@Override
	public Pager<User> findTest(String string, Object[] objects,
			Map<String, Object> alias) {
		return super.find(string, objects, alias);
	}

	@Override
	public List<User> listBySqlTest(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return super.listBySql(string, objects, class1, b);
	}

	@Override
	public List<User> listBySqlTest(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return super.listBySql(string, objects, alias, class1, b);
	}

	@Override
	public Pager<User> findBySqlTest(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return super.findBySql(string, objects, class1, b);
	}

	@Override
	public Pager<User> findBySqlTest(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return super.findBySql(string, objects, alias, class1, b);
	}

	@Override
	public Pager<User> findTest(String string, Object[] objects) {
		return super.find(string,objects);
	}
	
}

