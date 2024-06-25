package org.example.skillboxbooks.mapper;

import org.example.skillboxbooks.contorller.response.BookResponse;
import org.example.skillboxbooks.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BookMapper {
    List<BookResponse> toBookResponses(List<Book> books);

    @Mapping(target = "categoryName", source = "category.name")
    BookResponse toBookResponse(Book book);
}
