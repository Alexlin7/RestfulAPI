package com.alexlin7.demo.unit;

import com.alexlin7.demo.auth.UserIdentity;
import com.alexlin7.demo.converter.ProductConverter;
import com.alexlin7.demo.entity.product.Product;
import com.alexlin7.demo.entity.product.ProductRequest;
import com.alexlin7.demo.entity.product.ProductResponse;
import com.alexlin7.demo.repository.ProductRepository;
import com.alexlin7.demo.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

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
        ProductService productService = new ProductService(productRepository, null);

        Product resultProduct = productService.getProduct(productId);
        Assert.assertEquals(testProduct.getId(), resultProduct.getId());
        Assert.assertEquals(testProduct.getName(), resultProduct.getName());
        Assert.assertEquals(testProduct.getPrice(), resultProduct.getPrice());
        Assert.assertEquals(testProduct.getCreator(), resultProduct.getCreator());

    }

    @Test
    public void testCreteProduct() {
        ProductRepository productRepository = mock(ProductRepository.class);
        UserIdentity userIdentity = mock(UserIdentity.class);

        String creatorId = "abc";
        when(userIdentity.getId())
                .thenReturn(creatorId);
        ProductService productService = new ProductService(productRepository, userIdentity);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Snack");
        productRequest.setPrice(50);
        ProductResponse productResponse = productService.createProduct(productRequest);

        InOrder inOrder = Mockito.inOrder(productRepository, userIdentity);
        inOrder.verify(userIdentity, times(1)).getId();
        inOrder.verify(productRepository, times(1)).insert(any(Product.class));

        Assert.assertEquals(productRequest.getName(), productResponse.getName());
        Assert.assertEquals(productRequest.getPrice().intValue(), productResponse.getPrice());
        Assert.assertEquals(creatorId, productResponse.getCreator());

    }
}
