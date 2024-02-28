package com.wellness.controller;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.wellness.dto.AddressDTO;
import com.wellness.dto.LoginResponse;
import com.wellness.dto.UserDetailsDTO;
import com.wellness.entities.Address;
import com.wellness.entities.UserInfo;
import com.wellness.exception.UserException;
import com.wellness.service.AddressService;
import com.wellness.serviceImpl.JwtServiceImpl;

import config.UserDetailsInfoService;
import config.UserInfoUserDetails;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private com.wellness.service.UserDetailsService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private JwtServiceImpl jwtServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * This API return userDetails Based on particular Id
	 * 
	 * @param id
	 * @return UserDetails and HttpStatus
	 */

	@GetMapping("/{id}")
	public ResponseEntity<UserInfo> getUserById(@PathVariable int id) {
		return userService.getUserById(id).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * This API create new User
	 * 
	 * @param userDTO (user required details)
	 * @return userDetails
	 * @throws UserException if not saved
	 */

	@PostMapping("/save")
	public ResponseEntity<UserInfo> saveUser(@RequestBody UserDetailsDTO userDTO) {
		UserInfo savedUser = userService.saveUser(userDTO);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	/**
	 * This API helps in login
	 * 
	 * @param email
	 * @param password
	 * @return matched userDetails with credentials
	 */

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {		
		try {
			Optional<UserInfo> userDetails = userService.findByEmailAndPassword(email, password);
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			authenticate.isAuthenticated();
//				UserInfoUserDetails user = (UserInfoUserDetails)authenticate.getPrincipal();
				String token = jwtServiceImpl.generateToken(email);

//				LoginResponse response = new LoginResponse();
//				response.setToken(token);
				return new ResponseEntity<>(token, HttpStatus.OK);
		
		}catch(UserException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/userInfo")
	public ResponseEntity<?> getUserData(@AuthenticationPrincipal UserDetails user){
		String email = user.getUsername();
		Optional<UserInfo> userInfo = userService.findByEmail(email);
		return new ResponseEntity<>(userInfo,HttpStatus.OK);
	}

	/**
	 * This API saves address in database.
	 * 
	 * @param id
	 * @param addressDTO
	 * @return Address
	 */

	@PostMapping("/saveAddress/{id}")
	public ResponseEntity<Address> saveAddress(@PathVariable int id, @RequestBody AddressDTO addressDTO) {
		Address saveAddress = addressService.addAddress(id, addressDTO);
		return new ResponseEntity<>(saveAddress, HttpStatus.CREATED);
	}

	/**
	 * This API gives address based on given ID.
	 * 
	 * @param id
	 * @return Address based on given ID
	 */

	@GetMapping("/getAddress/{id}")
	public ResponseEntity<List<Address>> getAddress(@PathVariable int id) {
		List<Address> address = addressService.getAddressForUser(id);
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	/**
	 * This API delete the address based on ID.
	 * 
	 * @param id
	 * @param addressId
	 * @return
	 */

	@DeleteMapping("/delete/{id}/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable int id, @PathVariable int addressId) {
		String delete = addressService.removeAddress(id, addressId);
		return new ResponseEntity<>(delete, HttpStatus.OK);
	}

	@PutMapping("/updateAddress/{addressId}")
	public ResponseEntity<Address> updateAddress(@PathVariable int addressId, @RequestBody AddressDTO addressDTO) {
		Address address = addressService.updateAddress(addressId, addressDTO);
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

}
