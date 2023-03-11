package com.radovan.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

	@Query(value = "select * from carts where customer_id = :customerId",nativeQuery = true)
	CartEntity findByCustomerId(@Param ("customerId") Integer customerId);
}
