package org.example.skillboxbooks.repository;

import org.example.skillboxbooks.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByCategoryNameIgnoreCase(String name);

    Optional<Book> findByAuthorAndName(String author, String name);

    boolean existsByAuthorAndName(String author, String name);
}