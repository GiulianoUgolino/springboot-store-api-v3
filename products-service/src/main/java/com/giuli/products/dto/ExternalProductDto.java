package com.giuli.products.dto;

// Jackson ignores JSON properties that are not defined in this DTO.
public record ExternalProductDto(String title, double price) {
}
