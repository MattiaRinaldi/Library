package com.to.hibernateLibrary.services;




import com.to.hibernateLibrary.dto.AuthorDto;
import com.to.hibernateLibrary.dto.BookDto;
import com.to.hibernateLibrary.dto.BookCriteriaDto;
import com.to.hibernateLibrary.entities.Book;
import com.to.hibernateLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.net.URI;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;


    public List<BookDto> findAll(){
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = this.entityToDto(book);
            bookDtoList.add(bookDto);
        });

        return bookDtoList;
    }

    public BookDto findById(Long bookId){

        return this.entityToDto(bookRepository
                .findById(bookId).orElseThrow(() -> new RuntimeException("Book " + bookId + " not found")));

    }

    public List<BookDto> findBookByGenre(String genre){
        List<Book> bookList = bookRepository.findAllByGenre(genre);
        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = this.entityToDto(book);
            bookDtoList.add(bookDto);
        });

        return bookDtoList;
    }

    public List<BookDto> findBookByYear(Long year){
        List<Book> bookList = bookRepository.findAllByYearOfPublication(year);
        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = this.entityToDto(book);
            bookDtoList.add(bookDto);
        });

        return bookDtoList;
    }

    public List<BookDto> findBookByAuthor(Long authorId){
        AuthorDto authorDto = authorService.findById(authorId);
        List<Book> bookList = bookRepository.findAllByAuthorId(AuthorService.dtoToEntity(authorDto));

        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = this.entityToDto(book);
            bookDtoList.add(bookDto);
        });
        return bookDtoList;

    }

    public List<BookDto> findAllByYearOfPublicationGreaterThanEqualAndYearOfPublicationLessThanEqual(Long year1, Long year2){
        List<Book> bookList = bookRepository.findAllByYearOfPublicationGreaterThanEqualAndYearOfPublicationLessThanEqual(year1,year2);
        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = this.entityToDto(book);
            bookDtoList.add(bookDto);
        });
        return bookDtoList;
    }

    public Long getNumberOfBookByGenre(BookCriteriaDto dto){
        List<Book> numberOfBookByGenre = bookRepository.findAll(hasValidCriteria(dto));
        return (long) numberOfBookByGenre.size();
    }
    private Specification<Book> hasValidCriteria(BookCriteriaDto dto) {

        return (rt, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getGenre() != null) {
                predicates.add(cb.and(cb.like(rt.get("genre"),"%" + dto.getGenre() + "%")));
            }

            if (dto.getYear1() != null) {
                predicates.add(cb.greaterThanOrEqualTo(rt.get("yearOfPublication"),dto.getYear1()));
            }

            if(dto.getYear2() != null){
                predicates.add(cb.lessThanOrEqualTo(rt.get("yearOfPublication"),dto.getYear2()));
            }

            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }

    public ResponseEntity<BookDto> save(Book book){
        BookDto bookDto = this.entityToDto(bookRepository.save(book));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .buildAndExpand(bookDto.getBookId()).toUri();

        return ResponseEntity.created(location).build();

    }

    public ResponseEntity<BookDto> update(Long bookId, Book bookDetails) {
        BookDto bookDto = this.findById(bookId);

        bookDto.setAuthor(bookDetails.getAuthorId());
        bookDto.setTitle(bookDetails.getTitle());
        bookDto.setGenre(bookDetails.getGenre());
        bookDto.setYearOfPublication(bookDetails.getYearOfPublication());
        bookDto.setNumberOfPages(bookDetails.getNumberOfPages());

        final BookDto updatedBook = this.entityToDto(bookRepository.save(dtoToEntity(bookDto)));

        return ResponseEntity.ok(updatedBook);
    }

    public Map<String, Boolean>  delete(Long bookId){

        BookDto bookDto = this.findById(bookId);

        bookRepository.delete(dtoToEntity(bookDto));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    private BookDto entityToDto(Book book) {
        return BookDto.builder().bookId(book.getBookId())
                .author(book.getAuthorId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .yearOfPublication(book.getYearOfPublication())
                .numberOfPages(book.getNumberOfPages())
                .build();
    }

    public static Book dtoToEntity(BookDto bookDto) {
        Book book = new Book();

        book.setBookId(bookDto.getBookId());
        book.setTitle(bookDto.getTitle());
        book.setAuthorId(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setYearOfPublication(bookDto.getYearOfPublication());
        book.setNumberOfPages(bookDto.getNumberOfPages());

        return book;
    }

}
