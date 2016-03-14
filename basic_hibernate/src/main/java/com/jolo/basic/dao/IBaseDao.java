package com.jolo.basic.dao;

import java.util.List;
import java.util.Map;

import com.jolo.basic.model.Pager;

/**
 * 公共的Dao处理对象
 * 包含hql,sql的基本操作
 * @author devtao
 *
 */
public interface IBaseDao<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 根据ID删除对象
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 根据ID加载对象
	 * @param id
	 * @return
	 */
	public T load(int id);
	
	
}
