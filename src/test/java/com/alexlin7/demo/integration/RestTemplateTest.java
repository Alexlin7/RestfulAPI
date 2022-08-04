package com.alexlin7.demo.integration;

import com.alexlin7.demo.entity.product.Product;
import com.alexlin7.demo.entity.product.ProductResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class RestTemplateTest extends BaseTest {
    @LocalServerPort
    private int port;

    private String domain;
    private RestTemplate restTemplate;

    @Before
    public void init() {
        domain = "http://localhost:" + port;
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetProduct() {
        Product product = createProduct("snack", 50);

        String url = domain + "/products/" + product.getId();
        ProductResponse productResponse = restTemplate.getForObject(url, ProductResponse.class);

        Assert.assertEquals(product.getId(), productResponse.getId());
        Assert.assertEquals(product.getPrice(), productResponse.getPrice());
        Assert.assertEquals(product.getName(), productResponse.getName());
    }

    private Product createProduct(String name, int price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return productRepository.insert(product);
    }
}
