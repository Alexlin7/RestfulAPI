package com.alexlin7.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProductRequest {
    @NotEmpty(message = "Product name is undefine.")
    private String name;
    @Min(value = 0, message = "Price should be greater or equal to 0.")
    private Integer price;


}
