package com.wellness.service;

import java.util.List;

import com.wellness.dto.ReviewsDTO;
import com.wellness.entities.Reviews;

public interface ReviewService {
	
	public ReviewsDTO postReview(int userId, int productId, ReviewsDTO reviewsDTO);
	public List<ReviewsDTO> getReviews(int productId);
}
