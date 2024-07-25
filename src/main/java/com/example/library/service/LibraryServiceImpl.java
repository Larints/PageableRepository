package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getFixedSizeBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    public Book addBook(Book book) {
        Book newBook = Book.builder()
                .title(book.getTitle())
                .authors(new ArrayList<>())
                .build();
        Optional.ofNullable(book.getAuthors()).ifPresent(authors -> newBook.getAuthors().addAll(authors));
        bookRepository.save(newBook);
        return newBook;
    }

    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow();
    }

    public Book updateBook(Long bookId, Book book) {
        Book updatedBook = getBook(bookId);
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthors(book.getAuthors());
        bookRepository.save(updatedBook);
        return updatedBook;
    }

    public Book deleteBook(Long bookId) {
        Book book = getBook(bookId);
        bookRepository.delete(book);
        return book;
    }

}
