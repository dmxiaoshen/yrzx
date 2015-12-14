
package com.lljz.yrzx.base.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;

import com.lljz.yrzx.base.page.Pagination;


public interface BaseHibernateDao {
    
    void add(Object object);
    
    void update(Object object);
    
    void delete(Object object);
    
    void delete(Class<?> clzss,Long id);
    
    <T> T load(Class<T> clzss,Long id);
    
    <T> T get(Class<T> clzss,Long id);
    
    <T> List<T> findList(String hql,Map<String,Object> paramMap);
    
    <T> List<T> findList(String hql,Map<String,Object> paramMap,int start,int max);
    
    <T> T findOne(String hql,Map<String,Object> paramMap);
    
    <T> Pagination<T> list(final String hql, final Map<String, Object> paramMap, int page, int pageSize);
    
    
    <T> T findNativeUnique(final String nativeSql, final Map<String, Object> paramMap);
    
    <T> List<T> findByNativeSql(final String nativeSql, Map<String, Object> paramMap);
    
    <T> List<T> findByNativeSql(final String nativeSql, final Map<String, Object> paramMap, int start,int max);
    
    int countByNativeSql(String nativeSql, Map<String, Object> paramMap);
    
    <T> Pagination<T> listByNativeSql(final String nativeSql, final Map<String, Object> paramMap, int page, int pageSize);

    int executeSqlUpdate(String sql, Map<String, Object> paramMap);
    
    void doWork(Work work);
}
