package com.alexlin7.demo.service;

import com.alexlin7.demo.entity.Product;
import com.alexlin7.demo.entity.ProductRequest;
import com.alexlin7.demo.exception.NotFoundException;
import com.alexlin7.demo.parameter.ProductQueryParameter;

import com.alexlin7.demo.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository= repository;
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return repository.insert(product);
    }

    public Product getProduct(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product."));
    }

    public Product replaceProduct(String id, ProductRequest request) {
        Product oldProduct = getProduct(id);

        Product product = new Product();
        product.setId(oldProduct.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return repository.save(product);
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

}
