package com.example.Library_Management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Library_Management.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<List<Book>> findByGenre(String genre);
    Optional<List<Book>> findByAuthor(String author);
    Optional<List<Book>> findByTitle(String title);
}

