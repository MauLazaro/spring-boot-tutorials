package com.example.tutorial_webflux.handler;

import com.example.tutorial_webflux.dto.ProductDto;
import com.example.tutorial_webflux.model.Product;
import com.example.tutorial_webflux.service.ProductService;
import com.example.tutorial_webflux.validation.ObjectValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductService service;
    private final ObjectValidation validation;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Product> products = service.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<Product> product = service.findById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductDto> product = request.bodyToMono(ProductDto.class).doOnNext(validation::validate);
        return product.flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.save(p), Product.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ProductDto> product = request.bodyToMono(ProductDto.class).doOnNext(validation::validate);
        return product.flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.update(p, id), Product.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.delete(id), Product.class);
    }
}
