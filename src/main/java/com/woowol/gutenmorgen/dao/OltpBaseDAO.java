package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.config.HibernateOltp;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class OltpBaseDAO<T> extends BaseDAO<T> {
    @Autowired
    @Getter
    @Qualifier(HibernateOltp.db + "SessionFactory")
    private SessionFactory sessionFactory;
}