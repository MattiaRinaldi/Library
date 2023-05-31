package com.to.hibernateLibrary.entities;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "author")
@EntityListeners(AuditingEntityListener.class)
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "author_id", unique = true)
    private Long authorId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "nation", nullable = true)
    private String nation;
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @OneToMany(mappedBy="authorId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> bookList;

    public Author(){}

    public Author(String name, String surname, String nation, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.nation = nation;
        this.birthday = birthday;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

}
