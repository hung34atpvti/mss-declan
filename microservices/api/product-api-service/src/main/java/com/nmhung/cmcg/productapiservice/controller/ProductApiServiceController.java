package com.nmhung.cmcg.productapiservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import java.security.Principal;

@RestController
public class ProductApiServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductApiServiceController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{productId}")
    @HystrixCommand(fallbackMethod = "defaultProductComposite")
    public ResponseEntity<String> getProductComposite(@PathVariable int productId) {

//        LOG.info("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);

        ResponseEntity<String> result = restTemplate.getForEntity("http://product-composite-service/product/" + productId, String.class);
        LOG.info("GetProductComposite http-status: {}", result.getStatusCode());
        LOG.debug("GetProductComposite body: {}", result.getBody());

        return result;
    }

    /**
     * Fallback method for getProductComposite()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<String> defaultProductComposite(
            @PathVariable int productId
    ) {
//        public ResponseEntity<String> defaultProductComposite(
//        @PathVariable int productId,
//        @RequestHeader(value = "Authorization") String authorizationHeader,
//        Principal currentUser
//    )

//        LOG.warn("Using fallback method for product-composite-service. User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        return new ResponseEntity<String>("", HttpStatus.BAD_GATEWAY);
    }
}
