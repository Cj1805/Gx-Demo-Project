package com.wellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.entities.Product;
import com.wellness.entities.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

	List<Reviews> findByProduct(Product product);
}
