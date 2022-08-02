package com.alexlin7.demo;

import com.alexlin7.demo.converter.ProductConverter;
import com.alexlin7.demo.entity.product.Product;
import com.alexlin7.demo.entity.product.ProductResponse;
import com.alexlin7.demo.repository.ProductRepository;
import com.alexlin7.demo.service.ProductService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Test
    public void testConvertProductToResponse() {
        Product product = new Product();
        product.setName("Snack");
        product.setPrice(50);
        product.setId("123");
        product.setCreator("abc");
        ProductResponse productResponse = ProductConverter.toProductResponse(product);

        Assert.assertEquals(product.getId(), productResponse.getId());
        Assert.assertEquals(product.getName(), productResponse.getName());
        Assert.assertEquals(product.getPrice(), productResponse.getPrice());
        Assert.assertEquals(product.getCreator(), productResponse.getCreator());
    }

    @Test
    public void testGetProduct() {
        String productId = "123";
        Product testProduct = new Product();
        testProduct.setId(productId);
        testProduct.setName("Snack");
        testProduct.setPrice(50);
        testProduct.setCreator("abc");

        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(testProduct));
        ProductService productService = new ProductService(productRepository);

        Product resultProduct = productService.getProduct(productId);
        Assert.assertEquals(testProduct.getId(), resultProduct.getId());
        Assert.assertEquals(testProduct.getName(), resultProduct.getName());
        Assert.assertEquals(testProduct.getPrice(), resultProduct.getPrice());
        Assert.assertEquals(testProduct.getCreator(), resultProduct.getCreator());

    }
}
