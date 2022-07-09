package com.alexlin7.demo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

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
