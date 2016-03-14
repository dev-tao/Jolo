package com.jolo.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.jolo.basic.model.PageContext;
import com.jolo.basic.model.Pager;

@SuppressWarnings("unchecked")
public class BaseDao<T>  implements IBaseDao<T>{

	private SessionFactory sessionFactory;
	
	
	/**
	 * 初始化hql
	 * @param hql
	 * @return
	 */
	private String initSort(String hql){
		String order = PageContext.getOrder();
		String sort = PageContext.getSort();
		if (sort!=null&&!"".equals(sort.trim())){
			hql+=" order by "+sort;
			if(!"desc".equals(order)){
				hql+=" asc";
			}else hql+=" desc";
		}
		return hql;
	}
	
	/**
	 * 设置别名
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias){
		if (alias!=null){
		Set<String> keys = alias.keySet();
		for(String key:keys){
			Object val = alias.get(key);
			if (val instanceof Collection){
				//查询条件是列表
				query.setParameterList(key, (Collection)val);
			}else{
				query.setParameter(key, val);
			}
		}
	  }
	}
	
	/**
	 * 设置参数
	 * @param query
	 * @param args
	 */
	private void setParameter(Query query,Object[] args){
		if (args!=null&&args.length>0){
			int index = 0;
			for(Object arg:args){
				query.setParameter(index++,arg);
			}
		}
	}
	
	
	/**
	 * 创建一个class的对象，来获取泛型的class
	 */	
private Class<T> clz;
	
	public Class<T> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<T>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
		
	}
	@Override
	public void update(T t) {
		getSession().update(t);
	}
	
	@Override
	public T load(int id) {
		return (T)getSession().load(getClz(),id);
	}
	
	/**
	 * 不分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}
	public List<T> list(String hql) {
		return this.list(hql, null);
	}
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] {arg});
	}
	

	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名对象
	 * @return
	 */
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query,args);
		return query.list();
	}
	
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}
	
	
	/**
	 * 分页列表对象
	 * @param hql 查询对象的hql
	 * @param args 查询参数
	 * @return 一组不分页的列表对象
	 */
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}
	
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}
	
	@SuppressWarnings("rawtypes")
	private void setPagers(Query query,Pager pager){
		Integer pageSize = PageContext.getPageSize();
		Integer pageOffset = PageContext.getPageOffset();
		if(pageOffset == null||pageOffset<0) pageOffset =0;
		if(pageSize == null||pageSize<0) pageSize =0;
		pager.setOffset(pageOffset);
		pager.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	
	private String getCountHql(String hql,boolean isHql){
		String sql = hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+sql;
		if(isHql) c.replaceAll("fetch","");
		return c;
	}
	
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名对象
	 * @return
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		initSort(cq);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
		
	}
	
	/**
	 * 根据hql查询一组对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObject(String hql, Object[] args) {
		return queryObject(hql, args, null);
	}
	public Object queryObject(String hql, Object arg) {
		return queryObject(hql, new Object[]{arg});
	}
	public Object queryObject(String hql) {
		return queryObject(hql,null);
	}
	
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return queryObject(hql, null, alias);

	}
	
	/**
	 * 根据hql更新一组对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}
	
	/**
	 * 根据Sql查询对象，不包含关联对象
	 * @param sql 查询sql语句
	 * @param arts 查询条件
	 * @param clz 查询对象
	 * @param hasEntity 该对象是否是由hibernate管理的实体对象，如果不是使用setResultTransForm查询
	 * @return
	 */
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity){
			sq.addEntity(clz);
		}else{
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return sq.list();
	}
	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}
	
	
	/**
	 * 根据Sql查询分页对象，不包含关联对象
	 * @param sql 查询sql语句
	 * @param arts 查询条件
	 * @param clz 查询对象
	 * @param hasEntity 该对象是否是由hibernate管理的实体对象，如果不是使用setResultTransForm查询
	 * @return
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		
		String cq = getCountHql(sql, false);
		sql = initSort(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		SQLQuery squery= getSession().createSQLQuery(sql);
		setAliasParameter(cquery, alias);
		setAliasParameter(squery, alias);
		setParameter(cquery, args);
		setParameter(squery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(squery, pages);
		if(hasEntity){
			squery.addEntity(clz);
		}else{
			squery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas = squery.list();
		pages.setDatas(datas);
		long total = ((BigInteger)cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;

	}
	public <N extends Object>Pager<N>findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

}
