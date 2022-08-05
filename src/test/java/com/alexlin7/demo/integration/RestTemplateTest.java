package com.alexlin7.demo.integration;

import com.alexlin7.demo.auth.AuthRequest;
import com.alexlin7.demo.entity.appUser.AppUser;
import com.alexlin7.demo.entity.appUser.UserAuthority;
import com.alexlin7.demo.entity.product.Product;
import com.alexlin7.demo.entity.product.ProductRequest;
import com.alexlin7.demo.entity.product.ProductResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testGetProducts() {
        Product p1 = createProduct("Operation Management", 350);
        Product p2 = createProduct("Marketing Management", 200);
        Product p3 = createProduct("Financial Statement Analysis", 400);
        Product p5 = createProduct("Enterprise Resource Planning", 420);
        Product p4 = createProduct("Human Resource Management", 440);

        String url = domain + "/products?keyword={name}&orderBy={orderField}&sortRule={sortDirection}";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "manage");
        queryParams.put("orderField", "price");
        queryParams.put("sortDirection", "asc");

        ProductResponse[] productResList = restTemplate.getForObject(url, ProductResponse[].class, queryParams);

        Assert.assertNotNull(productResList);
        Assert.assertEquals(3, productResList.length);
        Assert.assertEquals(p2.getId(), productResList[0].getId());
        Assert.assertEquals(p1.getId(), productResList[1].getId());
        Assert.assertEquals(p4.getId(), productResList[2].getId());
    }

    @Test
    public void testCreateProduct() {
        AppUser appUser = createUser("Alexlin7", Collections.singletonList(UserAuthority.NORMAL));

        ProductRequest productReq = new ProductRequest();
        productReq.setName("Snack");
        productReq.setPrice(50);

        String token = obtainAccessToken(appUser.getEmailAddress());
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<ProductRequest> httpEntity = new HttpEntity<>(productReq, httpHeaders);
        String url = domain + "/products";
        ProductResponse productRes = restTemplate.postForObject(url, httpEntity, ProductResponse.class);

        Assert.assertNotNull(productRes);
        Assert.assertEquals(productReq.getName(), productRes.getName());
        Assert.assertEquals(productReq.getPrice().intValue(), productRes.getPrice());
        Assert.assertEquals(appUser.getId(), productRes.getCreator());

    }

    private String obtainAccessToken(String username) {
        AuthRequest authReq = new AuthRequest();
        authReq.setUsername(username);
        authReq.setPassword("12345678");

        String url = domain + "/auth";
        Map tokenRes = restTemplate.postForObject(url, authReq, Map.class);

        Assert.assertNotNull(tokenRes);
        String token = (String) tokenRes.get("token");
        Assert.assertNotNull(token);

        return token;
    }

    private Product createProduct(String name, int price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return productRepository.insert(product);
    }
}
