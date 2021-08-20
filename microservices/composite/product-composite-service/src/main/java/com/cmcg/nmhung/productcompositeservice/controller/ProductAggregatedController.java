package com.cmcg.nmhung.productcompositeservice.controller;

import com.cmcg.nmhung.productcompositeservice.model.Product;
import com.cmcg.nmhung.productcompositeservice.model.ProductAggregated;
import com.cmcg.nmhung.productcompositeservice.model.Recommendation;
import com.cmcg.nmhung.productcompositeservice.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductAggregatedController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductAggregatedController.class);

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public String getProduct() {
        return "{\"timestamp\":\"" + LocalDateTime.now() + "\",\"content\":\"Hello from ProductAPi\"}";
    }

    @RequestMapping("/product/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable int productId) {
        try {
            ResponseEntity<Product> responseProduct = restTemplate.getForEntity("http://product-service/product/" + productId, Product.class);
            if (!responseProduct.getStatusCode().is2xxSuccessful()) {
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Product product = responseProduct.getBody();
            if (Objects.isNull(product)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
            List<Recommendation> lstRecommendation = null;
            ResponseEntity<List<Recommendation>> responseLstRecommendation = restTemplate.exchange(
                    "http://recommendation-service/recommendation?productId=" + productId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            if (!responseLstRecommendation.getStatusCode().is2xxSuccessful()) {
                LOG.debug("Call to getRecommendations failed: {}", responseLstRecommendation.getStatusCode());
            } else {
                lstRecommendation = responseLstRecommendation.getBody();
            }
            List<Review> lstReview = null;
            ResponseEntity<List<Review>> responseLstReview = restTemplate.exchange(
                    "http://review-service/review?productId=" + productId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            if (!responseLstReview.getStatusCode().is2xxSuccessful()) {
                LOG.debug("Call to getReviews failed: {}", responseLstReview.getStatusCode());
            } else {
                lstReview = responseLstReview.getBody();
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ProductAggregated(product, lstRecommendation, lstReview));
        } catch (Exception e) {
            LOG.error("Error: ", e);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
