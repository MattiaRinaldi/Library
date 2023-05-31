package com.to.hibernateLibrary.services;

import com.to.hibernateLibrary.dto.AuthorDto;
import com.to.hibernateLibrary.entities.Author;
import com.to.hibernateLibrary.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorDto> findAll(){
        List<Author> authorList = authorRepository.findAll();
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorList.forEach(author -> {
            AuthorDto authorDto = this.entityToDto(author);
            authorDtoList.add(authorDto);
        });

        return authorDtoList;
    }

    public AuthorDto findById(Long authorId){

        AuthorDto authorDto = this.entityToDto(authorRepository
                .findById(authorId).orElseThrow(() -> new RuntimeException("Author " + authorId + " not found")));

        return authorDto;

    }

    public List<AuthorDto> findAuthorByNation(String nation){
        List<Author> authorList = authorRepository.findAllByNation(nation);
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorList.forEach(author -> {
            AuthorDto authorDto = this.entityToDto(author);
            authorDtoList.add(authorDto);
        });

        return authorDtoList;
    }

    public ResponseEntity<AuthorDto> save(Author author){
        AuthorDto authorDto = this.entityToDto(authorRepository.save(author));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .buildAndExpand(authorDto.getAuthorId()).toUri();

        return ResponseEntity.created(location).build();

    }

    public ResponseEntity<AuthorDto> update(Long authorId, Author authorDetails) {
        AuthorDto authorDto = this.findById(authorId);

        authorDto.setName(authorDetails.getName());
        authorDto.setSurname(authorDetails.getSurname());
        authorDto.setNation(authorDetails.getNation());
        authorDto.setBirthday(authorDetails.getBirthday());

        final AuthorDto updatedAuthor = this.entityToDto(authorRepository.save(dtoToEntity(authorDto)));

        return ResponseEntity.ok(updatedAuthor);
    }

    public Map<String, Boolean>  delete(Long authorId){

        AuthorDto authorDto = this.findById(authorId);

        authorRepository.delete(dtoToEntity(authorDto));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    private AuthorDto entityToDto(Author author) {
        return AuthorDto.builder().authorId(author.getAuthorId())
                .name(author.getName())
                .surname(author.getSurname())
                .nation(author.getNation())
                .birthday(author.getBirthday())
                .build();
    }

    public Author dtoToEntity(AuthorDto authorDto) {
        Author author = new Author();

        author.setAuthorId(authorDto.getAuthorId());
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        author.setNation(authorDto.getNation());
        author.setBirthday(authorDto.getBirthday());

        return author;
    }

}
