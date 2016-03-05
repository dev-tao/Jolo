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
	
	/**
	 * 不分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public List<T>list(String hql,Object[] args);
	public List<T> list(String hql);
	public List<T> list(String hql,Object arg);
	
	
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名对象
	 * @return
	 */
	public List<T> list(String hql,Object[] args,Map<String,Object> alias);
	public List<T>listByAlias(String hql,Map<String,Object> alias);
	
	
	/**
	 * 分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public Pager<T> find(String hql,Object[] args);
	public Pager<T> find(String hql);
	public Pager<T> find(String hql,Object arg);
	
	
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名对象
	 * @return
	 */
	public Pager<T> find(String hql,Object[] args,Map<String,Object> alias);
	public Pager<T>findByAlias(String hql,Map<String,Object> alias);
	
	/**
	 * 根据hql查询一组对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObject(String hql,Object[] args);
	public Object queryObject(String hql,Object arg);
	public Object queryObject(String hql);
	public Object queryObject(String hql,Object[] args,Map<String,Object> alias);
	public Object queryObjectByAlias(String hql,Map<String,Object> alias);
	
	
	/**
	 * 根据hql更新一组对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public void updateByHql(String hql,Object[] args);
	public void updateByHql(String hql,Object arg);
	public void updateByHql(String hql);
	
	/**
	 * 根据Sql查询对象，不包含关联对象
	 * @param sql 查询sql语句
	 * @param arts 查询条件
	 * @param clz 查询对象
	 * @param hasEntity 该对象是否是由hibernate管理的实体对象，如果不是使用setResultTransForm查询
	 * @return
	 */
	public <N extends Object>List<N> listBySql(String sql,Object[] args,Class<?> clz,boolean hasEntity);
	public <N  extends Object>List<N> listBySql(String sql,Object  arg,Class<?> clz,boolean hasEntity);
	public <N  extends Object>List<N> listBySql(String sql,Object[] args,Map<String,Object> alias ,Class<?> clz,boolean hasEntity);
	public <N  extends Object>List<N>listBySql(String sql,Class<?> clz,boolean hasEntity);
	public <N  extends Object>List<N>listByAliasSql(String sql,Map<String,Object> alias ,Class<?> clz,boolean hasEntity);
	
	/**
	 * 根据Sql查询分页对象，不包含关联对象
	 * @param sql 查询sql语句
	 * @param arts 查询条件
	 * @param clz 查询对象
	 * @param hasEntity 该对象是否是由hibernate管理的实体对象，如果不是使用setResultTransForm查询
	 * @return
	 */
	public <N extends Object>Pager<N> findBySql(String sql,Object[] args,Class<?> clz,boolean hasEntity);
	public <N extends Object>Pager<N> findBySql(String sql,Object  arg,Class<?> clz,boolean hasEntity);
	public <N extends Object>Pager<N> findBySql(String sql,Class<?> clz,boolean hasEntity);
	public <N extends Object>Pager<N> findBySql(String sql,Object[] args,Map<String,Object> alias ,Class<?> clz,boolean hasEntity);
	public <N extends Object>Pager<N> findByAliasSql(String sql,Map<String,Object> alias ,Class<?> clz,boolean hasEntity);
	
	
	
	
	
}
