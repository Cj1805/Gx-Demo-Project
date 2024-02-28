package com.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.dto.ReviewsDTO;
import com.wellness.entities.Reviews;
import com.wellness.exception.ProductException;
import com.wellness.exception.UserException;
import com.wellness.service.ReviewService;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/post/{userId}/{productId}")
	public ResponseEntity<?> postReview(@PathVariable int userId, @PathVariable int productId,
			@RequestBody ReviewsDTO review) {
		try {
			ReviewsDTO reviews = reviewService.postReview(userId, productId, review);
			return new ResponseEntity<>(reviews, HttpStatus.OK);
		} catch (UserException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
