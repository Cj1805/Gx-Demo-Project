package com.wellness.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ShippingAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressID;
	private String firstName;
	private String lastName;
	private String addressLine;
	private String city;
	private String state;
	private long pinCode;
	private String phone;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInfo userDetails;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Orders> order;
	
}
