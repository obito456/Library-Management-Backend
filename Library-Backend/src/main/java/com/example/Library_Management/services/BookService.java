package com.example.Library_Management.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import com.example.Library_Management.models.Book;

public interface BookService {
    ResponseEntity<Optional<List<Book>>> getByGenre(String genre);
    ResponseEntity<Optional<List<Book>>> getByAuthor(String author);
    ResponseEntity<Optional<List<Book>>> getByTitle(String title);
}
