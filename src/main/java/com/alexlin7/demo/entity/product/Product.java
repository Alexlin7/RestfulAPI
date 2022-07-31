package com.alexlin7.demo.entity.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String creator;

}
