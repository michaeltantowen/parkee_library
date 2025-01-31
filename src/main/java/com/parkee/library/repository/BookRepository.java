package com.parkee.library.repository;

import com.parkee.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnNumber(Long isbnNumber);

    List<Book> findByStockGreaterThan(int stock);

}
