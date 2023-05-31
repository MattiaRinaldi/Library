package com.to.hibernateLibrary.entities;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "book_id", unique = true)
    private Long bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JsonBackReference
    @JoinColumn (name = "author_id", nullable = false)
    private Author authorId;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "year_of_publication", nullable = true)
    private Long yearOfPublication;

    @Column(name = "number_of_pages", nullable = true)
    private Long numberOfPages;

}
