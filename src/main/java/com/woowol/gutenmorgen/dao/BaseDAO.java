package com.woowol.gutenmorgen.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseDAO<T> {
	abstract public SessionFactory getSessionFactory();
	abstract public Map<String, ?> createMapByObject(T t);
	
	private final Class<T> genericType;
	
	@SuppressWarnings("unchecked")
	protected BaseDAO() {
		genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDAO.class);
	}
	
	private Criteria getCriteria() {
		return getSessionFactory().getCurrentSession().createCriteria(genericType);
	}
	
	@SuppressWarnings("unchecked")
	public T selectOne(T o) {
		return (T) getCriteria().add(Restrictions.allEq(createMapByObject(o))).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> selectList(T o) {
		return (List<T>) getCriteria().add(Restrictions.allEq(createMapByObject(o))).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> selectList() {
		return (List<T>) getCriteria().list();
	}
	
	public void update(T o) {
		getSessionFactory().getCurrentSession().update(o);
	}
	
	public void persist(T o) {
		getSessionFactory().getCurrentSession().persist(o);
	}
	
	public void delete(T o) {
		getSessionFactory().getCurrentSession().delete(o);
	}
}
