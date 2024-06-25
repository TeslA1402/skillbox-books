package org.example.skillboxbooks.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxbooks.configuration.CacheNames;
import org.example.skillboxbooks.contorller.request.BookRequest;
import org.example.skillboxbooks.contorller.response.BookResponse;
import org.example.skillboxbooks.entity.Book;
import org.example.skillboxbooks.entity.Category;
import org.example.skillboxbooks.exception.AlreadyExistsException;
import org.example.skillboxbooks.exception.NotFoundException;
import org.example.skillboxbooks.mapper.BookMapper;
import org.example.skillboxbooks.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.BOOKS_BY_CATEGORY)
    public List<BookResponse> getBookByCategoryName(String categoryName) {
        List<Book> books = bookRepository.findByCategoryNameIgnoreCase(categoryName);
        return bookMapper.toBookResponses(books);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.BOOKS_BY_AUTHOR_AND_NAME, key = "#author + #name")
    public BookResponse getBookByAuthorAndName(String author, String name) {
        Book book = bookRepository.findByAuthorAndName(author.trim(), name.trim())
                .orElseThrow(() -> new NotFoundException(MessageFormat
                        .format("Book with name {0} and author {1} not found", name, author)));
        return bookMapper.toBookResponse(book);
    }

    @Transactional
    @CacheEvict(cacheNames = {CacheNames.BOOKS_BY_CATEGORY, CacheNames.BOOKS_BY_AUTHOR_AND_NAME}, allEntries = true)
    public BookResponse createBook(BookRequest bookRequest) {
        checkExistsBook(bookRequest);
        Category category = categoryService.getOrCreateCategoryByName(bookRequest.categoryName());
        Book book = bookRepository.save(Book.builder()
                .author(bookRequest.author().trim())
                .name(bookRequest.name().trim())
                .category(category)
                .build());
        category.getBooks().add(book);
        return bookMapper.toBookResponse(book);
    }

    @Transactional
    @CacheEvict(cacheNames = {CacheNames.BOOKS_BY_CATEGORY, CacheNames.BOOKS_BY_AUTHOR_AND_NAME}, allEntries = true)
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat
                .format("Book with id {0} not found", id)));
        checkExistsBook(bookRequest);
        Category oldCategory = book.getCategory();
        Category category = categoryService.getOrCreateCategoryByName(bookRequest.categoryName());
        book.setName(bookRequest.name().trim());
        book.setAuthor(bookRequest.author().trim());
        book.setCategory(category);
        if (!category.equals(oldCategory)) {
            oldCategory.getBooks().remove(book);
            category.getBooks().add(book);
        }
        return bookMapper.toBookResponse(book);
    }

    private void checkExistsBook(BookRequest bookRequest) {
        String normalizedName = bookRequest.name().trim();
        String normalizedAuthor = bookRequest.author().trim();
        if (bookRepository.existsByAuthorAndName(normalizedAuthor, normalizedName)) {
            throw new AlreadyExistsException(MessageFormat.format("Book with name {0} and author {1} already exists", normalizedAuthor, normalizedName));
        }
    }

    @Transactional
    @CacheEvict(cacheNames = {CacheNames.BOOKS_BY_CATEGORY, CacheNames.BOOKS_BY_AUTHOR_AND_NAME}, allEntries = true)
    public void deleteBook(Long id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.getCategory().getBooks().remove(book);
            bookRepository.delete(book);
        });
    }
}
