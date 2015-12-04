package com.woowol.gutenmorgen.dao;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public abstract class BaseDAO<T> {

    private Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDAO.class);

    @PersistenceContext
    EntityManager entityManager;

    public T findOne(String id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        return (List<T>)entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(String entityId) {
        delete(findOne(entityId));
    }
}