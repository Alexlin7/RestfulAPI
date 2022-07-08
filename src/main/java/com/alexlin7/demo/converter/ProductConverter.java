package com.alexlin7.demo.converter;

import com.alexlin7.demo.entity.Product;
import com.alexlin7.demo.entity.ProductRequest;

public class ProductConverter {

    private ProductConverter() {}

    public static Product toProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return product;
    }
}
