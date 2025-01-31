package com.parkee.library.service;

import com.parkee.library.model.Book;
import com.parkee.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByStockGreaterThan(0);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookByIsbnNumber(Long isbnNumber) {
        return bookRepository.findByIsbnNumber(isbnNumber);
    }

}
