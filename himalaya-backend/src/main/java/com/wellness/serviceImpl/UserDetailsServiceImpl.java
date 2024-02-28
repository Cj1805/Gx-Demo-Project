package com.wellness.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wellness.dto.UserDetailsDTO;
import com.wellness.entities.UserInfo;
import com.wellness.exception.UserException;
import com.wellness.mapper.UserDetailsMapper;
import com.wellness.repository.UserDetailsRepository;
import com.wellness.service.UserDetailsService;
import com.wellness.utils.ExceptionUtils;
import com.wellness.entities.Cart;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsMapper userDetailsMapper;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/**
	 * This method returns list of all users present in database
	 */

	@Override
	public List<UserInfo> getAllUser() {
		List<UserInfo> userDetails;
		if (!userDetailsRepository.findAll().isEmpty()) {
			userDetails = userDetailsRepository.findAll();
			return userDetails;
		} else {
			throw new UserException(ExceptionUtils.USER_NOTFOUND);
		}
	}
 
	
	
	/**
	 * This method get user details based on id given
	 */
	
	@Override
	public Optional<UserInfo> getUserById(int id) {
		return userDetailsRepository.findById(id);
	}
	
	/**
	 * This method save user in database.
	 * @param userDetailsDto
	 * @return UserDetails
	 */

	@Override
	public UserInfo saveUser(UserDetailsDTO userDetailsDTO) {
		
		if(userDetailsRepository.existsByEmail(userDetailsDTO.getEmail())) {
			throw new UserException(ExceptionUtils.EMAIL_EXISTS);
		}

		if (userDetailsRepository.findByPhone(userDetailsDTO.getPhone()).isPresent()) {
			throw new UserException(ExceptionUtils.PHONE_EXISTS);
		}
		UserInfo userDetails = userDetailsMapper.convertToEntity(userDetailsDTO);
		String encodedPassword = bCryptPasswordEncoder.encode(userDetailsDTO.getPassword());
		userDetails.setPassword(encodedPassword);
		Cart cart = new Cart();
		cart.setUserDetails(userDetails);
		userDetails.setCart(cart);
		return userDetailsRepository.save(userDetails);
	}
	

	/**
	 * This method helps in login user by validating credentials
	 * @param email , password
	 * @return UserDetails
	 */

	@Override
	public Optional<UserInfo> findByEmail(String email){
		Optional<UserInfo> userInfo = userDetailsRepository.findByEmail(email);
		
		return userInfo;
	}

	/**
	 * 
	 * 
	 */
	
	@Override
	public Optional<UserInfo> findByEmailAndPassword(String email, String password) {
		Optional<UserInfo> userDetails = Optional.of(userDetailsRepository.findByEmail(email)
				.orElseThrow(()-> new UserException(ExceptionUtils.USEREMAIL_NOTFOUND)));
		String encryptedPassword = userDetails.get().getPassword();
		if (!bCryptPasswordEncoder.matches(password, encryptedPassword)) {
			throw new UserException(ExceptionUtils.PASSWORD_NOTMATCH);
		} else {
			return userDetails;
		}
	}

}
