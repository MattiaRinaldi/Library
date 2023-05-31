package com.to.hibernateLibrary.repositories;


import com.to.hibernateLibrary.entities.Author;
import com.to.hibernateLibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByYearOfPublication(Long yearOfPublication);
    //List<Book> findAllBookByAuthorId(Author authorId);
    @Query(value = "select * from book where author_id=?1",nativeQuery = true)
    List<Book> findAllBookByAuthorId(Long authorId);
}
