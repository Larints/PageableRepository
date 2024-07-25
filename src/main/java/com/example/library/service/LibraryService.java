package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LibraryService {

    public List<Book> getAllBooks();

    public Page<Book> getFixedSizeBooks(int page, int size);

    public Book addBook(Book book);

    public Book getBook(Long bookId);

    public Book updateBook(Long bookId, Book book);

    public Book deleteBook(Long bookId);


}
