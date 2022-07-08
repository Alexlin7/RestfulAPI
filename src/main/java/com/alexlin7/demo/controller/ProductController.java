package com.alexlin7.demo.controller;

import com.alexlin7.demo.entity.Product;
import com.alexlin7.demo.parameter.ProductQueryParameter;
import com.alexlin7.demo.service.ProductService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "/products",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductQueryParameter parm){
        List<Product> products = productService.getProducts(parm);
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product request){
        Product product = productService.createProduct(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(
            @PathVariable("id") String id,
            @RequestBody Product request) {
        Product product = productService.replaceProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
