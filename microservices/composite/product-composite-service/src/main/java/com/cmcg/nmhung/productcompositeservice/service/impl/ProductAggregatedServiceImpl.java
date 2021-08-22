package com.cmcg.nmhung.productcompositeservice.service.impl;

import com.cmcg.nmhung.productcompositeservice.model.Product;
import com.cmcg.nmhung.productcompositeservice.model.Recommendation;
import com.cmcg.nmhung.productcompositeservice.model.Review;
import com.cmcg.nmhung.productcompositeservice.service.ProductAggregatedService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class ProductAggregatedServiceImpl implements ProductAggregatedService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductAggregatedServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultProduct")
    @Override
    public ResponseEntity<Product> getProduct(int productId) {
        ResponseEntity<Product> responseProduct = restTemplate.getForEntity("http://product-service/product/" + productId, Product.class);
        if (!responseProduct.getStatusCode().is2xxSuccessful()) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product product = responseProduct.getBody();
        if (Objects.isNull(product)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    // --------------- //
    // RECOMMENDATIONS //
    // --------------- //

    @HystrixCommand(fallbackMethod = "defaultRecommendations")
    @Override
    public ResponseEntity<List<Recommendation>> getRecommendations(int productId) {
        try {
            LOG.info("GetRecommendations...");

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
                LOG.debug("GetRecommendations.cnt {}", lstRecommendation.size());
            }

            return ResponseEntity.status(HttpStatus.OK).body(lstRecommendation);
        } catch (Exception e) {
            LOG.error("Error in Recommendation: ", e);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Recommendation");
        }
    }

    // ------- //
    // REVIEWS //
    // ------- //

    @HystrixCommand(fallbackMethod = "defaultReviews")
    @Override
    public ResponseEntity<List<Review>> getReviews(int productId) {
        try {
            LOG.info("GetReviews...");

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
                LOG.debug("GetReviews.cnt {}", lstReview.size());
            }

            return ResponseEntity.status(HttpStatus.OK).body(lstReview);
        } catch (Exception e) {
            LOG.error("Error in Review: ", e);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Review");
        }
    }

    /**
     * Fallback method for getProduct()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<Product> defaultProduct(int productId) {
        LOG.warn("Using fallback method for product-service");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }

    /**
     * Fallback method for getReviews()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<List<Review>> defaultReviews(int productId) {
        LOG.warn("Using fallback method for review-service");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }

    /**
     * Fallback method for getRecommendations()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<List<Recommendation>> defaultRecommendations(int productId) {
        LOG.warn("Using fallback method for recommendation-service");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }
}
