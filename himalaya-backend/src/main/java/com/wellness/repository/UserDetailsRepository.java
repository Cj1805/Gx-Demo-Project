package com.wellness.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wellness.entities.UserInfo;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserInfo, Integer> {
	
	Optional<UserInfo> findByPhone(String phone);
	Boolean existsByEmail(String email);
	
	
	@Query(value = "SELECT * FROM user_details WHERE email = :email", nativeQuery = true)
	Optional<UserInfo> findByEmail(@Param("email") String email);
	
	
	@Query(value = "SELECT * FROM user_details WHERE email = :email AND password= :password", nativeQuery = true)
	UserInfo findByEmailAndPassword(@Param("email")String email,@Param("password")String password);
	

}
