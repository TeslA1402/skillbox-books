package org.example.skillboxbooks.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheNames {
    public static final String BOOKS_BY_AUTHOR_AND_NAME = "booksByAuthorAndName";
    public static final String BOOKS_BY_CATEGORY = "booksByCategory";
}
