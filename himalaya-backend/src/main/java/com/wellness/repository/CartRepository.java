package com.wellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
