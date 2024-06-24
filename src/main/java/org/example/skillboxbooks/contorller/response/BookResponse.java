package org.example.skillboxbooks.contorller.response;

import java.io.Serializable;

/**
 * DTO for {@link org.example.skillboxbooks.entity.Book}
 */
public record BookResponse(Long id, String author, String name, String categoryName) implements Serializable {
}