package com.to.hibernateLibrary.repositories;


import com.to.hibernateLibrary.entities.Author;
import com.to.hibernateLibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByYearOfPublication(Long yearOfPublication);
    List<Book> findAllByAuthorId(Author authorId);
    List<Book> findAllByYearOfPublicationGreaterThanEqualAndYearOfPublicationLessThanEqual(Long year1, Long year2);

}
