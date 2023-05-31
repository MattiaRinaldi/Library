package com.to.hibernateLibrary.controllers;

import com.to.hibernateLibrary.dto.AuthorDto;
import com.to.hibernateLibrary.entities.Author;
import com.to.hibernateLibrary.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // GET method to fetch all authors
    @GetMapping("/all")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }

    // GET method to fetch author by id
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id)
            throws Exception {
        AuthorDto author = authorService.findById(id);

        return ResponseEntity.ok().body(author);
    }

    // GET method to fetch all author by nation
    @GetMapping("/nation/{nation}")
    public List<AuthorDto> getAllAuthorsByNation(@PathVariable String nation){
        return authorService.findAuthorByNation(nation);
    }

    // POST method to create an author
    @PostMapping("/")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody Author author) {
        return authorService.save(author);
    }

    // PUT method to update an author's details
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable Long id, @RequestBody Author authorDetails
    ) throws Exception {
        return authorService.update(id,authorDetails);
    }

    // DELETE method to delete an author
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAuthor(@PathVariable Long id) throws Exception {
        return authorService.delete(id);
    }
}