package com.example.tutorial_webflux.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    @Min(value = 1, message = "price must be greater than 0")
    private float price;
}
