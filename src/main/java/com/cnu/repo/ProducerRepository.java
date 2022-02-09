package com.cnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnu.entity.ProducerEntity;

public interface ProducerRepository extends JpaRepository<ProducerEntity, Integer> {

}
