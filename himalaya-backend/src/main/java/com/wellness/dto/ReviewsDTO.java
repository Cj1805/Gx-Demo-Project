package com.wellness.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDTO {

	private float rating;
	private String summary;
	private String info;
	private LocalDate reviewDate;
	private String name;
	private String email;
	
}
