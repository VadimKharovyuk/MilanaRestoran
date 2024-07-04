package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Review;
import com.example.milanarestoran.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review save (Review review){
       return reviewRepository.save(review);

    }

   public List<Review> getAll(){
        return reviewRepository.findAll();
   }
}
