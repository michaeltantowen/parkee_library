package com.parkee.library.controller;

import com.parkee.library.model.Book;
import com.parkee.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable Long isbn) {
        Optional<Book> matchedBook = bookService.getBookByIsbnNumber(isbn);
        if (matchedBook.isPresent()) {
            return ResponseEntity.ok(matchedBook.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found!, isbn: " + isbn);
        }
    }
}
