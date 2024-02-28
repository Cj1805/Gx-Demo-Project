package com.wellness.mapper;

import org.springframework.stereotype.Component;

import com.wellness.dto.UserDetailsDTO;
import com.wellness.entities.UserInfo;

@Component
public class UserDetailsMapper {

	public UserDetailsDTO convertToDto(UserInfo userDetails) {
		UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
		userDetailsDTO.setFirstName(userDetails.getFirstName());
		userDetailsDTO.setLastName(userDetails.getLastName());
		userDetailsDTO.setPhone(userDetails.getPhone());
		userDetailsDTO.setEmail(userDetails.getEmail());
		userDetailsDTO.setPassword(userDetails.getPassword());
//		userDetailsDTO.setUserRole(userDetails.getUserRole());
		return userDetailsDTO;
	}

	public UserInfo convertToEntity(UserDetailsDTO userDetailsDTO) {
		UserInfo userDetails = new UserInfo();
		userDetails.setFirstName(userDetailsDTO.getFirstName());
		userDetails.setLastName(userDetailsDTO.getLastName());
		userDetails.setPhone(userDetailsDTO.getPhone());
		userDetails.setEmail(userDetailsDTO.getEmail());
		userDetails.setPassword(userDetailsDTO.getPassword());
//		userDetails.setUserRole(userDetailsDTO.getUserRole());
		return userDetails;
	}
}
