package com.wellness.entities;

import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserDetails")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String password;
	private String userRole;

	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private Cart cart;

	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<Orders> order;

	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<Address> address;

	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<ShippingAddress> shippingAddress;

	@JsonIgnore
	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<Reviews> reviws;
	
	@PrePersist
	public void setDefaultValues() {
		if (userRole == null || userRole.isEmpty()) {
			userRole = "ROLE_USER";
		}
	}
}
