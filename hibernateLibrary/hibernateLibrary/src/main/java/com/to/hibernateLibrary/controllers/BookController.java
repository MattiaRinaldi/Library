package com.to.hibernateLibrary.controllers;

import com.to.hibernateLibrary.dto.BookDto;
import com.to.hibernateLibrary.entities.Book;
import com.to.hibernateLibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService bookService;

    // GET method to fetch all books
    @GetMapping("/all")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    // GET method to fetch book by id
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.findById(id);

        return ResponseEntity.ok().body(bookDto);
    }

    // GET method to fetch all books by genre
    @GetMapping("/genre/{genre}")
    public List<BookDto> getAllBooksByGenre(@PathVariable String genre){
        return bookService.findBookByGenre(genre);
    }

    // GET method to fetch all books by year of publication
    @GetMapping("/year/{year}")
    public List<BookDto> getAllBooksByYear(@PathVariable Long year){
        return bookService.findBookByYear(year);
    }

    // GET method to fetch all books by author
    @GetMapping("/author/{authorId}")
    public List<BookDto> getAllBooksByAuthor(@PathVariable Long authorId){
        return bookService.findBookByAuthor(authorId);
    }


    // POST method to create a book
    @PostMapping(value = "/",consumes = {"application/json"})
    public ResponseEntity<BookDto> createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    // PUT method to update a book's details
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id, @RequestBody Book bookDetails
    ) {
        return bookService.update(id,bookDetails);

    }

    // DELETE method to delete a book
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteBook(@PathVariable Long id) {
        return bookService.delete(id);
    }
}
