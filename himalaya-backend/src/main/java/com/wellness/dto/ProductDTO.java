package com.wellness.dto;


import com.wellness.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private int productId;
	private String productName;
	private String description;
	private double price;
	private String imageURL;
	private Category category;
}
