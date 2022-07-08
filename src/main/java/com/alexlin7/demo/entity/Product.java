package com.alexlin7.demo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private String id;
    private String name;
    private int price;

}
