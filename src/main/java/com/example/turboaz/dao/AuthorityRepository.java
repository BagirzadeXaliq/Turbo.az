package com.example.turboaz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer>, JpaSpecificationExecutor<AuthorityEntity> {

}