package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.PizzaEntity;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Integer> {

	@Query(value = "select * from pizzas where name ilike CONCAT('%', :keyword, '%')",nativeQuery = true)
	List<PizzaEntity> findAllByKeyword(@Param ("keyword") String keyword);
	
}
