package com.wellness.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.dto.ReviewsDTO;
import com.wellness.entities.Product;
import com.wellness.entities.Reviews;
import com.wellness.entities.UserInfo;
import com.wellness.exception.ProductException;
import com.wellness.exception.ReviewException;
import com.wellness.exception.UserException;
import com.wellness.mapper.ReviewsMapper;
import com.wellness.repository.ProductRepository;
import com.wellness.repository.ReviewsRepository;
import com.wellness.repository.UserDetailsRepository;
import com.wellness.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewsRepository reviewsRepository;	
	
	@Autowired
	private ReviewsMapper reviewsMapper;
	

	@Override
	public ReviewsDTO postReview(int userId, int productId, ReviewsDTO reviewsDTO) {
		Optional<UserInfo> user = userDetailsRepository.findById(userId);
		Optional<Product> product = productRepository.findById(productId);
		if (user.isPresent()) {
			UserInfo users = user.get();
			Product products = product.get();
			Reviews reviews = reviewsMapper.toReviewsEntity(reviewsDTO);
			reviews.setUserDetails(users);
			reviews.setProduct(products);
			reviews.setReviewDate(LocalDate.now());
			
			reviewsDTO.setReviewDate(LocalDate.now());
			reviewsRepository.save(reviews);
			return reviewsDTO;
		} else {
			throw new UserException("Login to submit review");
		}
	}

	@Override
	public List<ReviewsDTO> getReviews(int productId) {
		Optional<Product> prod = productRepository.findById(productId);
		if (prod.isPresent()) {
			Product product = prod.get();
			List<Reviews> reviews = reviewsRepository.findByProduct(product);
			if(reviews.isEmpty()) {
				throw new ReviewException("No Reviews for this Product");
			}else {
				return reviews.stream().map(reviewsMapper::toReviewsDto)
						.collect(Collectors.toList());
			}
					
		} else {
			throw new ProductException("Product not present");
		}
	}

}
