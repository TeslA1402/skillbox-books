package org.example.skillboxbooks.contorller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxbooks.contorller.request.BookRequest;
import org.example.skillboxbooks.contorller.response.BookResponse;
import org.example.skillboxbooks.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Validated
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody @Valid BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse searchBook(@RequestParam String name, @RequestParam String author) {
        return bookService.getBookByAuthorAndName(author, name);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(@RequestBody @Valid BookRequest bookRequest, @PathVariable Long bookId) {
        return bookService.updateBook(bookId, bookRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooksByCategory(@RequestParam String categoryName) {
        return bookService.getBookByCategoryName(categoryName);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long bookId) {
        bookService.deleteBook(bookId);
    }
}
