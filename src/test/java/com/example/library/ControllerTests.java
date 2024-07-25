package com.example.library;

import com.example.library.controller.LibraryController;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LibraryController.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper mapper;

    private Page<Book> booksPage;

    private Book firstBook;

    private Book secondBook;

    @BeforeEach
    public void setUp() {
        firstBook = Book.builder()
                .id(1L)
                .title("The Great Gatsby")
                .authors(new ArrayList<>())
                .build();
        Author firstAuthor = Author.builder()
                .id(1L)
                .name("F. Scott Fitzgerald")
                .books(new ArrayList<>())
                .build();
        secondBook = Book.builder()
                .id(2L)
                .title("1984")
                .authors(new ArrayList<>())
                .build();
        Author secondAuthor = Author.builder()
                .id(2L)
                .name("George Orwell")
                .books(new ArrayList<>())
                .build();
        firstBook.getAuthors().add(firstAuthor);
        secondBook.getAuthors().add(secondAuthor);
        booksPage = new PageImpl<>(Arrays.asList(firstBook, secondBook),
                PageRequest.of(0, 2), 2);

    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(libraryService.getAllBooks()).thenReturn(List.of(firstBook, secondBook));
        mockMvc.perform(get("/api/v1/library")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("The Great Gatsby"))
                .andExpect(jsonPath("$[0].authors[0].id").value("1"))
                .andExpect(jsonPath("$[0].authors[0].name").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("1984"))
                .andExpect(jsonPath("$[1].authors[0].id").value("2"))
                .andExpect(jsonPath("$[1].authors[0].name").value("George Orwell"));
    }


    @Test
    public void testGetFixedSizeBooks() throws Exception {
        when(libraryService.getFixedSizeBooks(0, 2)).thenReturn(booksPage);
        mockMvc.perform(get("/api/v1/library/fixed")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.content[0].authors").exists())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[1].title").value("1984"))
                .andExpect(jsonPath("$.content[1].authors").exists());


    }

    @Test
    public void testAddBook() throws Exception {
        when(libraryService.addBook(any(Book.class))).thenReturn(firstBook);
        mockMvc.perform(post("/api/v1/library")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firstBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.authors[0].id").value("1"))
                .andExpect(jsonPath("$.authors[0].name").value("F. Scott Fitzgerald"));
    }

}