package com.to.hibernateLibrary.dto;


import com.to.hibernateLibrary.entities.Author;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private Long bookId;

    private String title;

    private Author author;

    private String genre;

    private Long yearOfPublication;

    private Long numberOfPages;
}
