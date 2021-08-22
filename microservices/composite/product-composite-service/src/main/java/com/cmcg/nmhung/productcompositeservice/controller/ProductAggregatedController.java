package com.cmcg.nmhung.productcompositeservice.controller;

import com.cmcg.nmhung.productcompositeservice.model.Product;
import com.cmcg.nmhung.productcompositeservice.model.ProductAggregated;
import com.cmcg.nmhung.productcompositeservice.model.Recommendation;
import com.cmcg.nmhung.productcompositeservice.model.Review;
import com.cmcg.nmhung.productcompositeservice.service.ProductAggregatedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductAggregatedController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductAggregatedController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductAggregatedService productAggregatedService;

    @RequestMapping("/")
    public String getProduct() {
        return "{\"timestamp\":\"" + LocalDateTime.now() + "\",\"content\":\"Hello from ProductAPi\"}";
    }

    @RequestMapping("/product/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable int productId) {
        try {
            Product product = productAggregatedService.getProduct(productId).getBody();
            List<Recommendation> lstRecommendation = productAggregatedService.getRecommendations(productId).getBody();
            List<Review> lstReview = productAggregatedService.getReviews(productId).getBody();

            return ResponseEntity.status(HttpStatus.OK).body(new ProductAggregated(product, lstRecommendation, lstReview));
        } catch (Exception e) {
            LOG.error("Error: ", e);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
