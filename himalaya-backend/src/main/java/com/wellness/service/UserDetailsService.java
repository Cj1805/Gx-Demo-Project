package com.wellness.service;

import java.util.List;
import java.util.Optional;

import com.wellness.dto.UserDetailsDTO;
import com.wellness.entities.UserInfo;
import com.wellness.exception.UserException;

public interface UserDetailsService {
	
	List<UserInfo> getAllUser();
	Optional<UserInfo> getUserById(int id);
	UserInfo saveUser(UserDetailsDTO userDetailsDTO)throws UserException;
	Optional<UserInfo> findByEmailAndPassword(String Email, String Password)throws UserException;
	Optional<UserInfo> findByEmail(String email);
}
