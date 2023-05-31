package com.to.hibernateLibrary.dto;


import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

    private Long authorId;

    private String name;

    private String surname;

    private String nation;

    private Date birthday;
}
