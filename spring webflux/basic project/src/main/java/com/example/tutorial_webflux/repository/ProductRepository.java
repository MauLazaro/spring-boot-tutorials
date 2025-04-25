package com.example.tutorial_webflux.repository;

import com.example.tutorial_webflux.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Mono<Product> findByName(String name);

    // use native sql. use the table name no use to name of modal.
    @Query("SELECT * FROM products WHERE id <> :id AND name = :name")
    Mono<Product> repeatedName(int id, String name);
}
