package com.jolo.basic.test.dao;

import java.util.List;
import java.util.Map;

import com.jolo.basic.dao.IBaseDao;
import com.jolo.basic.model.Pager;
import com.jolo.basic.test.model.User;

public interface IUserDao extends IBaseDao<User>  {

	List<User> listTest(String string, Object[] objects);

	List<User> listTest(String string, Object[] objects, Map<String, Object> alias);

	Pager<User> findTest(String string, Object[] objects, Map<String, Object> alias);

	List<User> listBySqlTest(String string, Object[] objects, Class<User> class1,
			boolean b);

	List<User> listBySqlTest(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> findBySqlTest(String string, Object[] objects, Class<User> class1,
			boolean b);

	Pager<User> findBySqlTest(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> findTest(String string, Object[] objects);

	
	
	
	
}
