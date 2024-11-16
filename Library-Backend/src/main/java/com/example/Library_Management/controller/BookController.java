package com.example.Library_Management.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.Library_Management.models.Book;
import com.example.Library_Management.repository.BookRepository;
import com.example.Library_Management.services.BookService;

@RequestMapping("/library")
@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("genre") String genre,
            @RequestParam("isAvailable") boolean isAvailable,
            @RequestParam("photo") MultipartFile photo) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAvailable(isAvailable);

        try {
            if (photo.isEmpty()) {
                return ResponseEntity.status(400).body("Photo cannot be empty");
            }
            book.setPhoto(photo.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error savig photo");
        }

        bookRepository.save(book);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable("id") long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(book);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(
            @RequestParam("id") long id,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("genre") String genre,
            @RequestParam("isAvailable") boolean isAvailable,
            @RequestParam("photo") MultipartFile photo) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setAvailable(isAvailable);
            try {
                book.setPhoto(photo.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            bookRepository.save(book);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id){
        Optional<Book> book=bookRepository.findById(id);

        if(book.isPresent()){
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book is deleted successfully");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Optional<List<Book>>> getByGenre(@PathVariable("genre") String genre){
        return bookService.getByGenre(genre);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Optional<List<Book>>> getByAuthor(@PathVariable("author") String author){
        return bookService.getByAuthor(author);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Optional<List<Book>>> getByTitle(@PathVariable("title") String title){
        return bookService.getByTitle(title);
    }

}
