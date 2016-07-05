package com.jeff.common.persistence;


import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;

import com.jeff.common.persistence.page.Page;
import com.jeff.common.persistence.page.PageInfo;

/**
 * @author 王存煊
 * BaseDao实现
 * @param <T>
 * 2015/08/26
 */

public interface BaseDao<T> {
	

	/**
	 * 查找该表中的所有记录数目
	 * @return
	 */
	public int countAll();
	
	/**
	 * 通过hql语句查找记录数目
	 * @param hql
	 * @return
	 */
	public int countByHql(String hql);
	
	
	
	
	
	
	
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 保存大数据，针对大型数据的导入很有作用
	 * @param entitys
	 */
	public void saveBigData(List<T> entitys);
	
	
	/**
	 * 通过Id和类名删除实体
	 * @param id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 通过ID数组来删除
	 * @param idList
	 */
	public void deleteByIdList(List idList);
	
	/**
	 * 通过类名删除全部记录
	 * @param className
	 */
	public void deleteByClassName(String className);
	
	/**
	 * 通过Hql语句删除
	 */
	public void deleteByHql(String hql);
	
	
	/**
	 * 通过参数值删除
	 * @param value
	 */
	public void deleteByParamAndValue(String hql, String param,Object value);
	
	
	/**
	 * 通过多个参数和参数值删除
	 * @param params
	 * @param values
	 */
	public void deleteByParamsAndValues(String hql, String[] params, List<Object> values);
	
	
	
	
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public void update(T entity);
	
	
	/**
	 * 通过hql语句更新
	 * @param hql
	 */
	public void updateByHql(String hql);
	
	
	/**
	 * 
	 * @author jeffwcx
	 * @method updateByParamAndValue
	 * @param @param param
	 * @param @param value
	 * @return void
	 * @description 更新单个值 
	 * @date 2015年12月8日 上午2:07:47
	 */
//	public void updateByParamAndValue(String param, Object value);
	
	
	
	/**
	 * 通过Id查询
	 * @param id
	 * @return
	 */
	public T getById(Serializable id);
	
	
	
	
	
	
	/**
	 * 
	 * @method getByParamNameAndValue
	 * @param @param name
	 * @param @param value
	 * @param @return
	 * @return T
	 * @desc 通过参数名和参数值获取单个实体
	 * @date 2015-11-26 下午10:17:55
	 */
	public T getByParamNameAndValue(String name, Object value);

	
	
	
	public T getByParamNamesAndValues(String[] names, Object[] values);
	
	/**
	 * 通过id数组查询
	 * @param idList
	 * @return
	 */
	public List<T> listByIdList(List<Serializable> idList);
	/**
	 * 查询所有实体
	 * @return
	 */
	public List<T> listAll();
	
	/**
	 * 通过hql语句得到实体
	 * @param hql
	 * @return
	 */
	public List<T> listByHql(String hql);
	
	
	/**
	 * 通过怎么样的方式排列list
	 * @param order
	 * @return
	 */
	public List<T> listOrderBy(String condition);
	
	
	
	
	
	
	/**
	 * 通过主键获取实体并加锁，如果没有实体抛出异常
	 * @param id
	 * @param lock
	 * @return
	 */
	public T getWithLock(Serializable id,LockMode lock);
	
	
	/**
	 * 根据主键获取实体如果没有实体抛出异常
	 * @param id
	 * @return
	 */
	public T load(Serializable id);
	
	
	/**
	 * 根据主键加载实体并加锁。如果没有实体抛出异常
	 * @param id
	 * @param lock
	 * @return
	 */
	public T loadWidthLock(Serializable id,LockMode lock);
	
	
	
	
	
	
	/**
	 * 更新实体并加锁
	 * @param entity
	 * @param lock
	 */
	public void updateWithLock(T entity, LockMode lock);
	
	/**
	 * 加锁并删除指定实体
	 * @param entity
	 * @param lock
	 */
	public void deleteWithLock(T entity, LockMode lock);
	
	
	
	/**
	 * 加锁并根据id删除指定实体
	 * @param id
	 * @param lock
	 */
	public void deleteByIdWithLock(Serializable id, LockMode lock);
	
	//参数查询部分
	/**
	 * 根据单个参数查询
	 * @param hql
	 * @param value
	 * @return
	 */
	public List<T> listByValue(String hql,Object value);
	
	
	/**
	 * 根据多个参数查询
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> listByValues(String hql,Object[] values);
	
	
	
	
	/**
	 * 根据参数名和参数来查询
	 * @param hql
	 * @param paramName
	 * @param value
	 * @return
	 */
	public List<T> listByNameParamAndValue(String paramName,Object value);
	
	
	
	/**
	 * 根据参数名数组和参数数组查询
	 * @param hql
	 * @param paramNames
	 * @param values
	 * @return
	 */
	public List<T> listByNameParamsAndValues(String[] paramNames, Object[] values);
	
	
	/**
	 * 
	 * @author jeffwcx
	 * @method listPage
	 * @param @param page
	 * @param @return
	 * @return PageInfo
	 * @description  通过Page信息获取PageInfo
	 * @date 2015年12月6日 上午3:21:59
	 */
	public PageInfo<T> listPage(Page page);
	
	
	/**
	 * 
	 * @author jeffwcx
	 * @method listPage
	 * @param @param page
	 * @param @param hql
	 * @param @return
	 * @return PageInfo
	 * @description  通过Page信息和hql获取PageInfo
	 * @date 2015年12月6日 上午3:22:21
	 */
	public PageInfo<T> listPage(String hql, Page page);
	
	/**
	 * 
	 * @author jeffwcx
	 * @method listDataTablePage
	 * @param @param page
	 * @param @return
	 * @return PageInfo<T>
	 * @description  Datatable插件通用单表查询
	 * @date 2016年1月10日 下午8:22:46
	 */
	public PageInfo<T> listDataTablePage(Page page);
	
	
	/**
	 * 
	 * @author jeffwcx
	 * @method listBySql
	 * @param @param sql
	 * @param @return
	 * @return List<Object[]>
	 * @description  通过sql返回List<Object[]>
	 * @date 2016年1月12日 下午3:10:58
	 */
	public List<Object[]> listBySql(String sql);
	
	
}
