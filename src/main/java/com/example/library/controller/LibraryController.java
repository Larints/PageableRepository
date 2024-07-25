package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("api/v1/library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/fixed")
    public ResponseEntity<Page<Book>> getFixedSizeBooks(@RequestParam int page, @RequestParam int size) {
        Page<Book> book = libraryService.getFixedSizeBooks(page, size);
        return ResponseEntity.ok(book);
    }

    @PostMapping()
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = libraryService.addBook(book);
        return ResponseEntity.ok(addedBook);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        Book book = libraryService.getBook(bookId);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        Book updatedBook = libraryService.updateBook(bookId, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long bookId) {
        Book book = libraryService.deleteBook(bookId);
        return ResponseEntity.ok(book);
    }
}
