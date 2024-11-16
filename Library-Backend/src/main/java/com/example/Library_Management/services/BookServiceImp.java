package com.example.Library_Management.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Library_Management.models.Book;
import com.example.Library_Management.repository.BookRepository;

@Service
public class BookServiceImp implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<Optional<List<Book>>> getByGenre(String genre) {
        Optional<List<Book>> books=bookRepository.findByGenre(genre);

        if(books.isPresent() && !books.get().isEmpty()){
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Optional<List<Book>>> getByAuthor(String author) {
        Optional<List<Book>> books=bookRepository.findByAuthor(author);

        if(books.isPresent() && !books.get().isEmpty()){
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.noContent().build();
    } 
    
    @Override
    public ResponseEntity<Optional<List<Book>>> getByTitle(String title) {
        Optional<List<Book>> books=bookRepository.findByTitle(title);

        if(books.isPresent() && !books.get().isEmpty()){
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.noContent().build();
    }
}
