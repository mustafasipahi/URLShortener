package com.project.repository.baserepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyEntityRepository<E> extends JpaRepository<E, Integer> {

}
