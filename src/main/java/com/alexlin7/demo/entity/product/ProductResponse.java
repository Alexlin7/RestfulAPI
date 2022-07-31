package com.alexlin7.demo.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private String id;
    private String name;
    private int price;
    private String creator;

}
