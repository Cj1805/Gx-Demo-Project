package com.wellness.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.dto.AddressDTO;
import com.wellness.entities.Address;
import com.wellness.entities.UserInfo;
import com.wellness.exception.AddressException;
import com.wellness.exception.UserException;
import com.wellness.mapper.AddressMapper;
import com.wellness.repository.AddressRepository;
import com.wellness.repository.UserDetailsRepository;
import com.wellness.service.AddressService;
import com.wellness.utils.ExceptionUtils;

import jakarta.transaction.Transactional;


@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AddressMapper addressMapper;

	@Override
	public Address addAddress(int userId, AddressDTO addressDto) {
		Optional<UserInfo> user = userDetailsRepository.findById(userId);
		if(user.isPresent()) {
			UserInfo users = user.get();
			Address address = addressMapper.toAddressEntity(addressDto);
			address.setUserDetails(users);
			addressRepository.save(address);
			return address;
		}
		throw new AddressException(ExceptionUtils.USER_NOTFOUND);
	}

	

	@Override
	@Transactional
	public String removeAddress(int userId, int addressID) {
		UserInfo user = userDetailsRepository.findById(userId).get();
		if(user!=null) {
			List<Address> address=user.getAddress();
			if(address!=null) {
				for(Address item : address) {
					if(item.getAddressID() == addressID) {
						addressRepository.deleteByaddressID(addressID);
						address.remove(item);
						return ExceptionUtils.ADDRESS_REMOVED;
					}
				}
			}
		}
		return ExceptionUtils.ADDRESS_NOTREMOVED;
	}

	@Override
	public List<Address> getAddressForUser(int userId){
		UserInfo user = userDetailsRepository.findById(userId).orElse(null);
		if(user!=null) {
			List<Address> address = user.getAddress();
			if(!address.isEmpty()) {
				return address;
			}else {
				throw new AddressException(ExceptionUtils.ADDRESS_NOTFOUND);
			}
		}
		throw new UserException(ExceptionUtils.USER_NOTFOUND);
	}



	@Override
	public Address updateAddress(int addressId, AddressDTO addressDTO) {
		Optional<Address> address = addressRepository.findById(addressId);
		if(address != null) {
			Address add = address.get();
			add.setFirstName(addressDTO.getFirstName());
			add.setLastName(add.getLastName());
			add.setAddressLine1(addressDTO.getAddressLine1());
			add.setAddressLine2(addressDTO.getAddressLine2());
			add.setCity(addressDTO.getCity());
			add.setState(addressDTO.getState());
			add.setPinCode(addressDTO.getPinCode());
			add.setPhone(addressDTO.getPhone());
			addressRepository.save(add);
			return add;
		}
		throw new AddressException(ExceptionUtils.ADDRESS_NOTFOUND);
	}

}
