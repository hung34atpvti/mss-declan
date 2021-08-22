package com.cmcg.nmhung.productcompositeservice.service;

import com.cmcg.nmhung.productcompositeservice.model.Product;
import com.cmcg.nmhung.productcompositeservice.model.Recommendation;
import com.cmcg.nmhung.productcompositeservice.model.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductAggregatedService {
    ResponseEntity<Product> getProduct(int productId);

    ResponseEntity<List<Recommendation>> getRecommendations(int productId);

    ResponseEntity<List<Review>> getReviews(int productId);

}
