package com.woowol.gutenmorgen.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.woowol.gutenmorgen.config.HibernateOltp;

@Transactional
public abstract class OltpBaseDAO<T> extends BaseDAO<T>{
	@Autowired
	@Qualifier(HibernateOltp.db +"SessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}