package com.wellness.mapper;

import org.springframework.stereotype.Component;

import com.wellness.dto.ReviewsDTO;
import com.wellness.entities.Reviews;

@Component
public class ReviewsMapper {
	
	public ReviewsDTO toReviewsDto(Reviews review) {
		ReviewsDTO reviewsDTO = new ReviewsDTO();
		reviewsDTO.setInfo(review.getInfo());
		reviewsDTO.setSummary(review.getSummary());
		reviewsDTO.setRating(review.getRating());
		reviewsDTO.setReviewDate(review.getReviewDate());
		reviewsDTO.setName(review.getName());
		reviewsDTO.setEmail(review.getEmail());
		return reviewsDTO;
	}
	
	public Reviews toReviewsEntity(ReviewsDTO reviewDto) {
		Reviews review = new Reviews();
		review.setInfo(reviewDto.getInfo());
		review.setSummary(reviewDto.getSummary());
		review.setRating(reviewDto.getRating());
		review.setReviewDate(reviewDto.getReviewDate());
		review.setName(reviewDto.getName());
		review.setEmail(reviewDto.getEmail());
		return review;
	}
}
