package com.example.tutorial_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    private int id;
    private String name;
    private float price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
