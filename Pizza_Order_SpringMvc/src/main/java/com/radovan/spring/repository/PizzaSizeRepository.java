package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.PizzaSizeEntity;

@Repository
public interface PizzaSizeRepository extends JpaRepository<PizzaSizeEntity, Integer> {

	@Query(value = "select * from pizza_sizes where pizza_id = :pizzaId",nativeQuery = true)
	List<PizzaSizeEntity> findAllByPizzaId(@Param("pizzaId") Integer pizzaId);
	
	@Modifying
	@Query(value = "delete from pizza_sizes where pizza_id = :pizzaId",nativeQuery = true)
	void deleteAllByPizzaId(@Param ("pizzaId") Integer pizzaId);
	
	@Query(value = "select * from pizza_sizes where name = :name and pizza_id = :pizzaId",nativeQuery = true)
	PizzaSizeEntity findByNameAndPizzaId(@Param ("name") String name,@Param ("pizzaId") Integer pizzaId);

}
