package com.alexlin7.demo.service;

import com.alexlin7.demo.aop.ActionType;
import com.alexlin7.demo.aop.EntityType;
import com.alexlin7.demo.aop.SendEmail;
import com.alexlin7.demo.auth.UserIdentity;
import com.alexlin7.demo.converter.ProductConverter;
import com.alexlin7.demo.entity.product.Product;
import com.alexlin7.demo.entity.product.ProductRequest;
import com.alexlin7.demo.entity.product.ProductResponse;
import com.alexlin7.demo.exception.NotFoundException;
import com.alexlin7.demo.parameter.ProductQueryParameter;
import com.alexlin7.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductService {

    @Autowired
    private UserIdentity userIdentity;

    private final ProductRepository repository;


    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @SendEmail(entity = EntityType.PRODUCT, action = ActionType.CRATE)
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCreator(userIdentity.getId());

        repository.insert(product);

        return ProductConverter.toProductResponse(product);
    }

    public Product getProduct(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    @SendEmail(entity = EntityType.PRODUCT, action = ActionType.UPDATE, idParmIndex = 0)
    public ProductResponse replaceProduct(String id, ProductRequest request) {
        Product oldProduct = getProduct(id);
        Product newProduct = ProductConverter.toProduct(request);
        newProduct.setId(oldProduct.getId());
        newProduct.setCreator(oldProduct.getCreator());

        repository.save(newProduct);

        return ProductConverter.toProductResponse(newProduct);
    }

    @SendEmail(entity = EntityType.PRODUCT, action = ActionType.DELETE, idParmIndex = 0)
    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    public List<Product> getProducts(ProductQueryParameter parm) {
        String keyword = Optional.ofNullable(parm.getKeyword()).orElse("");
        int priceFrom = Optional.ofNullable(parm.getPriceFrom()).orElse(0);
        int priceTo = Optional.ofNullable(parm.getPriceTo()).orElse(Integer.MAX_VALUE);

        Sort sort = genSortingStrategy(parm.getOrderBy(), parm.getSortRule());

        return repository.findByPriceBetweenAndNameLikeIgnoreCase(priceFrom, priceTo, keyword, sort);
    }

    private Sort genSortingStrategy(String orderBy, String sortRule) {
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = Sort.by(direction, orderBy);
        }

        return sort;
    }

    public ProductResponse getProductResponse(String id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));

        return ProductConverter.toProductResponse(product);
    }
}
