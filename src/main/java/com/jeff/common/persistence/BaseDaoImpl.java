package com.jeff.common.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.jeff.common.persistence.page.Column;
import com.jeff.common.persistence.page.Order;
import com.jeff.common.persistence.page.Page;
import com.jeff.common.persistence.page.PageInfo;
import com.jeff.common.utils.GernericsUtils;

/**
 * BaseDao实现类
 * @author 王存煊
 * 2015/08/26
 * @param <T>
 */

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	private Logger LOGGER;
	
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		this.clazz=(Class<T>)GernericsUtils.getSuperClassGenricType(getClass());
		LOGGER = Logger.getLogger(clazz);
		
	}
	
	
	
	@Override
	public int countAll() {
		
		String hql = "select count(*) from "+clazz.getName();
		return countByHql(hql);
		
	}

	@Override
	public int countByHql(String hql) {
		Long count = (Long)hibernateTemplate.find(hql).listIterator().next();
		return count.intValue();
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void save(T entity) {
			hibernateTemplate.save(entity);
	}

	@Override
	public void saveBigData(final List<T> entitys) {
		
		hibernateTemplate.execute(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				for(int i=0;i<entitys.size();i++){
					session.save(entitys.get(i));
					if(i%25==0){
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
		
	}
	
	
	
	
	@Override
	public void deleteById(Serializable id) {
		Assert.notNull(id, "通过ID删除时，id不能为空");
		
		String hql ="delete from "+clazz.getName()+" a where a.id="+id;
		hibernateTemplate.bulkUpdate(hql);
		
	}

	@Override
	public void deleteByIdList(final List idList) {
		Assert.notNull(idList, "通过IDList删除时，idList不能为空");
		final String hql = "delete from "+clazz.getName()+" a where a.id in (:idList)";
		hibernateTemplate.execute(new HibernateCallback <Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("idList", idList);
				return query.executeUpdate();
			}
		});
		
	}

	@Override
	public void deleteByClassName(String className) {
		Assert.notNull(className, "通过实体名删除全部数据时，class不能为空");
		String hql = "delete from "+className;
		hibernateTemplate.bulkUpdate(hql);
		
	}

	@Override
	public void deleteByHql(String hql) {
		Assert.notNull(hql,"通过hql语句删除时，hql不能为空");
		hibernateTemplate.bulkUpdate(hql);
		
	}
	
	
	
	@Override
	public void deleteByParamAndValue(final String hql, final String param,final Object value) {
		hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(param, value);
				return query.executeUpdate();
			}
		});
	}
	
	
	@Override
	public void deleteByParamsAndValues(final String hql, final String[] params, final List<Object> values) {
		hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				for(int i=0;i<params.length;i++){
					query.setParameter(params[i], values.get(i));
				}
				return query.executeUpdate();
			}
		});
		
	}

	
	
	
	
	
	@Override
	public void update(T entity) {
		Assert.notNull(entity, "通过更新时，entity不能为空");
		
		hibernateTemplate.update(entity);
		
	}

	@Override
	public void updateByHql(String hql) {
		Assert.notNull(hql, "通过hql更新时，hql不能为空");
		
		hibernateTemplate.bulkUpdate(hql);
		
	}
	
	
	
	
	
	
	

	@Override
	public T getById(Serializable id) {
		Assert.notNull(id, "通过id查询时，id不能为空");
		
		return hibernateTemplate.get(clazz, id);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByIdList(final List<Serializable> idList) {
		
		Assert.notNull(idList, "通过idList查询时，idList不能为空");
		
		
		final String hql = "from "+clazz.getName()+" where id in (:idList)";
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("idList", idList);
				return query.list();
			}
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll() {
		
		return (List<T>) hibernateTemplate.find("from "+clazz.getName());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByHql(String hql) {
		Assert.notNull(hql,"通过hql得到一个集合是hql不能为空");
		
		return (List<T>) hibernateTemplate.find(hql);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listOrderBy(String condition) {
		String hql = "from "+this.clazz.getName()+" order by "+condition;
		return (List<T>) hibernateTemplate.find(hql);
	}
	



	@Override
	public T getWithLock(Serializable id, LockMode lock) {
		Assert.notNull(id);
		Assert.notNull(lock);
		
		return hibernateTemplate.get(clazz, id, lock);
		
	}



	@Override
	public T load(Serializable id) {
		Assert.notNull(id);
		
		return hibernateTemplate.load(clazz, id);
		
	}



	@Override
	public T loadWidthLock(Serializable id, LockMode lock) {
		Assert.notNull(id);
		Assert.notNull(lock);
		
		return hibernateTemplate.load(clazz, id, lock);
		
	}



	



	@Override
	public void updateWithLock(T entity, LockMode lock) {
		Assert.notNull(entity);
		Assert.notNull(lock);
		
		hibernateTemplate.update(entity, lock);
		
		
	}



	@Override
	public void deleteWithLock(T entity, LockMode lock) {
		Assert.notNull(entity);
		Assert.notNull(lock);
		
		hibernateTemplate.delete(entity, lock);
		
	}



	@Override
	public void deleteByIdWithLock(Serializable id, final LockMode lock) {
		
		Assert.notNull(id);
		Assert.notNull(lock);
		
		final String hql = "delete from "+clazz.getName()+" a where a.id="+id;
		hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setLockMode("a", lock);
				return query.executeUpdate();
			}
		});
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByValue(String hql, Object value) {
		Assert.notNull(hql);
		Assert.notNull(value);
		return (List<T>) hibernateTemplate.find(hql, value);
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByValues(String hql, Object[] values) {
		Assert.notNull(hql);
		Assert.notNull(values);
		
		return (List<T>) hibernateTemplate.find(hql, values);
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByNameParamAndValue(String paramName,
			Object value) {
		Assert.notNull(paramName);
		Assert.notNull(value);
		String hql = "from "+clazz.getName()+" where "+paramName+"=:"+paramName;
		
		return (List<T>) hibernateTemplate.findByNamedParam(hql, paramName, value);
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> listByNameParamsAndValues(String[] paramNames,
			Object[] values) {
		Assert.notNull(paramNames);
		Assert.notNull(values);
		String hql = "from "+clazz.getName()+" where ";
		for(int i = 0; i<paramNames.length; i++){
			String temp = paramNames[i];
			if(i==paramNames.length-1){
				hql+=temp+"=:"+temp;
				break;
			}
			hql+=temp+"=:"+temp+" and ";
		}
		return (List<T>) hibernateTemplate.findByNamedParam(hql, paramNames, values);
		
	}
	
	
	
	
	
	@Override
	public PageInfo<T> listPage(Page page) {
		return listPage("from "+clazz.getName(),page);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<T> listPage(final String hql, final Page page) {
		
		int all = countAll();
		if(all<=0){
			return null;
		}
		
		List<T> list =  hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setMaxResults(page.getLength());
				query.setFirstResult(page.getStart());
				return query.list();
			}
		});
		if(list.size()<=0){
			return null;
		}
		PageInfo<T> pageInfo = new PageInfo<T>();
		pageInfo.setDraw(page.getDraw());
		pageInfo.setRecordsFiltered(all);
		pageInfo.setRecordsTotal(all);
		pageInfo.setData(list);
		return pageInfo;
		
	}
	
	
	@Override
	public List<Object[]> listBySql(final String sql){
		List<Object[]> list = hibernateTemplate.execute(new HibernateCallback<List<Object[]>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return list;
	}
	
	@Override
	public PageInfo<T> listDataTablePage(final Page page){
		
		List<Column> columnList = page.getColumns();
		StringBuffer hqlTemp = new StringBuffer("");
		List<String> pageTables = page.getTables();
		if(page.getTables()!=null){
			hqlTemp.append("from ");
			for(int i=0; i<pageTables.size(); i++){
				if(i==0){
					hqlTemp.append(pageTables.get(i));
				}
				else{
					hqlTemp.append(","+pageTables.get(i));
				}
			}
		}
		else
		{
			hqlTemp.append("from "+clazz.getName());
		}
		Boolean isSearch = false;
		String allSearch="";
		String searchStr=page.getSearchValue();
		if(searchStr!=null){
			searchStr=searchStr.replace("'", "");
			allSearch=" or '"+searchStr+"'";
		}
		for(int i=0; i<columnList.size(); i++){
			Column column  = columnList.get(i);
			
			if(column.getSearchable()&&column.getData()!=null&&column.getSearchValue()!=null){
				if(!isSearch){
					hqlTemp.append(" where ");
				}
				String columnStr = column.getSearchValue();
				hqlTemp.append(column.getName()+" like '%"+columnStr.replace("'", "")+"%'"+allSearch+" or ");
				isSearch=true;
			}
			else
			{
				if(searchStr!=null){
					if(!isSearch){
						hqlTemp.append(" where ");
					}
					hqlTemp.append(column.getName()+" like '%"+searchStr+"%' or ");
					isSearch=true;
				}
			}
		}
		Order order = page.getOrder();
		final String hql = (!isSearch?hqlTemp.toString():hqlTemp.substring(0, hqlTemp.length()-4))
				+(order==null?"":" order by "+order.getName()+" "+order.getDir());
		List<T> list =  hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setMaxResults(page.getLength());
				query.setFirstResult(page.getStart());
				return query.list();
			}
		});
		int records = list.size();
		if(records<=0){
			list=new ArrayList<>();
			records=0;
		}
		
		PageInfo<T> pageInfo = new PageInfo<T>();
		pageInfo.setData(list);
		pageInfo.setDraw(page.getDraw());
		pageInfo.setRecordsFiltered(records);
		int all = countAll();
		if(all<=0){
			all=0;
		}
		pageInfo.setRecordsTotal(all);
		return pageInfo;
	}
	
	


	@Override
	public T getByParamNameAndValue(String name, Object value) {
		List<T>  list = listByNameParamAndValue(name, value);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}



	@Override
	public T getByParamNamesAndValues(String[] names, Object[] values) {
		
		List<T> list = listByNameParamsAndValues(names, values);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}


	
	
}
