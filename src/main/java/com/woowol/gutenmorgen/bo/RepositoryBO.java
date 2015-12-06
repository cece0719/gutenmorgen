package com.woowol.gutenmorgen.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class RepositoryBO<T, ID extends Serializable, REPOSITORY extends JpaRepository<T,ID>> implements JpaRepository<T, ID> {
    @Autowired
    private REPOSITORY jpaRepository;

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) {
        return jpaRepository.findAll(ids);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public void delete(ID id) {
        jpaRepository.delete(id);
    }

    @Override
    public void delete(T entity) {
        jpaRepository.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        jpaRepository.delete(entities);
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public <S extends T> S save(S entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        return jpaRepository.save(entities);
    }

    @Override
    public T findOne(ID id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public boolean exists(ID id) {
        return jpaRepository.exists(id);
    }

    @Override
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return jpaRepository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        jpaRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }

    @Override
    public T getOne(ID id) {
        return jpaRepository.getOne(id);
    }
}