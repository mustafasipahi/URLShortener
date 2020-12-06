package com.project.services.baseservice;

import com.project.model.basemodel.MyEntity;

import java.util.List;
import java.util.Optional;

public interface MyEntityService<E extends MyEntity> {

    public long count();
    public E save(E entity);
    public void delete(Integer id);
    public Optional<E> findById(Integer id);
    public List<E> findAll();
    public boolean existById(Integer id);
}
