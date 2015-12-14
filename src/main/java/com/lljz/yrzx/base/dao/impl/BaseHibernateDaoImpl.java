package com.lljz.yrzx.base.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.lljz.yrzx.base.dao.BaseHibernateDao;
import com.lljz.yrzx.base.page.PageUtil;
import com.lljz.yrzx.base.page.Pagination;

@Repository
public class BaseHibernateDaoImpl implements BaseHibernateDao {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private static final int INIT_CAPACITY = 128;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add(Object object) {
		Assert.notNull(object, "add object can not be null");
		getSession().save(object);
	}

	@Override
	public void update(Object object) {
		Assert.notNull(object, "update object can not be null");
		getSession().saveOrUpdate(object);
	}

	@Override
	public void delete(Object object) {
		Assert.notNull(object, "delete object can not be null");
		getSession().delete(object);

	}

	@Override
	public void delete(Class<?> clzss, Long id) {
		Assert.notNull(id, "delete id can not be null");
		getSession().delete((load(clzss, id)));

	}

	/** 代理来延迟加载 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T load(Class<T> clzss, Long id) {
		Assert.notNull(id, "load id can not be null");
		return (T) getSession().load(clzss, id);
	}

	/** 不用代理 真实数据 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> clzss, Long id) {
		Assert.notNull(id, "get id can not be null");
		return (T) getSession().get(clzss, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findList(String hql, Map<String, Object> paramMap) {
		Query query = getSession().createQuery(hql);
		setQuery(query, paramMap);
		return query.list();
	}

	private void setQuery(Query query, final Map<String, Object> paramMap) {
		if (paramMap == null || paramMap.isEmpty()) {
			return;
		}
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			Object v = entry.getValue();
			if (v instanceof Object[]) {
				query.setParameterList(entry.getKey(), (Object[]) v);
			} else if (v instanceof Collection) {
				query.setParameterList(entry.getKey(), (Collection<?>) v);
			} else {
				query.setParameter(entry.getKey(), v);
			}
		}
	}

	@Override
	public <T> T findOne(String hql, Map<String, Object> paramMap) {
		List<T> list = findList(hql, paramMap);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findList(String hql, Map<String, Object> paramMap, int start, int max) {
		Query query = getSession().createQuery(hql);
		setQuery(query, paramMap);
		return query.setFirstResult(start).setMaxResults(max).list();
	}

	@Override
	public <T> Pagination<T> list(final String hql, final Map<String, Object> paramMap, int page, int pageSize) {
		int record = count(hql, paramMap);
		int p = PageUtil.validatePage(record, page, pageSize);
		if (record == 0) {
			return new Pagination<T>(new ArrayList<T>(0), record, p, pageSize);
		}
		List<T> result = findList(hql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
		return new Pagination<T>(result, record, p, pageSize);
	}

	private int count(String hql, Map<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer("").append("select count(*) from ")
				.append(StringUtils.substringBeforeLast(StringUtils.substringAfter(hql, "from"), "order "));
		Query query = getSession().createQuery(sb.toString());
		setQuery(query, paramMap);
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public <T> T findNativeUnique(String nativeSql, Map<String, Object> paramMap) {
		List<T> list = findByNativeSql(nativeSql, paramMap);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap) {
		SQLQuery query = getSession().createSQLQuery(nativeSql);
		setQuery(query, paramMap);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap, int start, int max) {
		SQLQuery query = getSession().createSQLQuery(nativeSql);
		setQuery(query, paramMap);
		return query.setFirstResult(start).setMaxResults(max).list();
	}

	@Override
	public int countByNativeSql(String nativeSql, Map<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer(INIT_CAPACITY).append("select count(1) from ")
				.append(StringUtils.substringBeforeLast(StringUtils.substringAfter(nativeSql, "from"), "order "));
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		setQuery(query, paramMap);
		return ((Number) query.uniqueResult()).intValue();

	}

	@Override
	public <T> Pagination<T> listByNativeSql(String nativeSql, Map<String, Object> paramMap, int page, int pageSize) {
		int record = countByNativeSql(nativeSql, paramMap);
		int p = PageUtil.validatePage(record, page, pageSize);
		if (record == 0) {
			return new Pagination<T>(new ArrayList<T>(0), record, p, pageSize);
		}
		List<T> result = findByNativeSql(nativeSql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
		return new Pagination<T>(result, record, p, pageSize);
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> paramMap) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (paramMap != null) {
			setQuery(query, paramMap);
		}
		return query.executeUpdate();
	}

	@Override
	public void doWork(Work work) {
		getSession().doWork(work);
	}
}
