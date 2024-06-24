package org.example.skillboxbooks.contorller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link org.example.skillboxbooks.entity.Book}
 */
public record BookRequest(@NotBlank @Size(min = 1, max = 255) String author,
                          @NotBlank @Size(min = 1, max = 255) String name,
                          @NotBlank @Size(min = 1, max = 255) String categoryName) {
}