package com.waracle.cakemgr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CakeRepository extends CrudRepository<CakeEntity, Long> {

    CakeEntity findByTitle(String title);
}
