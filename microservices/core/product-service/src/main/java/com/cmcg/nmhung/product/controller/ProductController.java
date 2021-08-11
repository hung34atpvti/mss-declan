package com.cmcg.nmhung.product.controller;

import com.cmcg.nmhung.product.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProductController {
    @RequestMapping("{productId}")
    public Product getProduct(@PathVariable int productId) {

        return new Product(productId, "name", 123);
    }
}
