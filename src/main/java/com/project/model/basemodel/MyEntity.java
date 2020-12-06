package com.project.model.basemodel;

import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.CriteriaBuilder;

@MappedSuperclass
public abstract class MyEntity {

    public abstract int getId();
    public abstract void setId(int id);
}
