package com.alexlin7.demo.service;

import com.alexlin7.demo.converter.ProductConverter;
import com.alexlin7.demo.entity.Product;
import com.alexlin7.demo.entity.ProductRequest;
import com.alexlin7.demo.entity.ProductResponse;
import com.alexlin7.demo.exception.NotFoundException;
import com.alexlin7.demo.parameter.ProductQueryParameter;

import com.alexlin7.demo.repository.ProductRepository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository= repository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        repository.insert(product);

        return ProductConverter.toProductResponse(product);
    }

    public Product getProduct(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    public ProductResponse replaceProduct(String id, ProductRequest request) {
        Product oldProduct = getProduct(id);
        Product newProduct = ProductConverter.toProduct(request);
        newProduct.setId(oldProduct.getId());

        repository.save(newProduct);

        return ProductConverter.toProductResponse(newProduct);
    }

    public  void deleteProduct(String id) {
        repository.deleteById(id);
    }

    public List<Product> getProducts(ProductQueryParameter parm) {
        String keyword = Optional.ofNullable(parm.getKeyword()).orElse("");
        int priceFrom = Optional.ofNullable(parm.getPriceFrom()).orElse(0);
        int priceTo = Optional.ofNullable(parm.getPriceTo()).orElse(Integer.MAX_VALUE);

        Sort sort = genSortingStrategy(parm.getOrderBy(), parm.getSortRule());

        return  repository.findByPriceBetweenAndNameLikeIgnoreCase(priceFrom, priceTo, keyword, sort);
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
