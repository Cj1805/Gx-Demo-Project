package com.wellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wellness.entities.Address;

import jakarta.transaction.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	@Transactional
	void deleteByaddressID(int addressID);

	@Transactional
	@Modifying
	@Query(value = "Update address SET first_name = :firstName last_name = :lastName address_line1 = :addressLine1 address_line2 = :addressLine2 city = :city state = :state pincode = :pincode phone = :phone where address_id = :addressId" , nativeQuery = true )
	void updateAddress(int addressId, String firstName, String lastName ,
			String addressLine1, String addressLine2, String city, String state, String pincode, String phone);
	
}
