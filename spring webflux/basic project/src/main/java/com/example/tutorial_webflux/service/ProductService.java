package com.example.tutorial_webflux.service;

import com.example.tutorial_webflux.dto.ProductDto;
import com.example.tutorial_webflux.exception.CustomException;
import com.example.tutorial_webflux.model.Product;
import com.example.tutorial_webflux.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final static String NF_MESSAGE = "Product not found";
    private final static String BR_MESSAGE = "Product name already use";
    private final ProductRepository repository;

    public Flux<Product> findAll() {
        return repository.findAll();
    }

    public Mono<Product> findById(int id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }

    public Mono<Product> save(ProductDto dto) {
        Mono<Boolean> isExist = repository.findByName(dto.getName()).hasElement();
        return isExist.flatMap(e -> e ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, BR_MESSAGE)) : repository.save(Product.builder().name(dto.getName()).price(dto.getPrice()).build()));
    }

    public Mono<Product> update(ProductDto dto, int id) {
        Mono<Boolean> isValidId = repository.findById(id).hasElement();
        Mono<Boolean> isExist = repository.repeatedName(id, dto.getName()).hasElement();

        return isValidId.flatMap(eId -> eId ?
            isExist.flatMap(e -> e ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, BR_MESSAGE)) : repository.save(new Product(id, dto.getName(), dto.getPrice())))
            : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE))
        );
    }

    public Mono<Void> delete(int id) {
        Mono<Boolean> isValidId = repository.findById(id).hasElement();
        return isValidId.flatMap(v -> v ? repository.deleteById(id) : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }
}
