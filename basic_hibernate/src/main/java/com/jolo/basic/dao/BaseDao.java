package com.jolo.basic.dao;

import java.lang.reflect.ParameterizedType;
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
		return sessionFactory.openSession();
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
	@Override
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}
	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}
	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] {arg});
	}
	
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
	
	@Override
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query,args);
		return query.list();
	}
	
	@Override
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}
	@Override
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}
	
	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}
	@Override
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
		String c = "select count(1) "+sql;
		if(isHql) c.replaceAll("fetch","");
		return c;
	}
	
	@Override
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
	@Override
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
		
	}
	@Override
	public Object queryObject(String hql, Object[] args) {
		return queryObject(hql, args, null);
	}
	@Override
	public Object queryObject(String hql, Object arg) {
		return queryObject(hql, new Object[]{arg});
	}
	@Override
	public Object queryObject(String hql) {
		return queryObject(hql,null);
	}
	
	@Override
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	@Override
	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return queryObject(hql, null, alias);

	}
	@Override
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}
	@Override
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}
	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}
	@Override
	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}
	@Override
	public List<Object> listBySql(String sql, Object arg, Class<Object> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	@Override
	public List<Object> listBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}
	@Override
	public List<Object> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
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
	@Override
	public List<Object> listByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}
	@Override
	public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}
	@Override
	public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	@Override
	public Pager<Object> findBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql, null, clz, hasEntity);
	}
	@Override
	public Pager<Object> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		
		String cq = getCountHql(sql, false);
		cq = initSort(cq);
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(cq);
		SQLQuery squery= getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setAliasParameter(squery, alias);
		setParameter(sq, args);
		setParameter(squery, args);
		Pager<Object> pages = new Pager<Object>();
		setPagers(sq, pages);
		if(hasEntity){
			sq.addEntity(clz);
		}else{
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return pages;

	}
	@Override
	public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

}
