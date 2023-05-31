package com.to.hibernateLibrary.dto;


import com.to.hibernateLibrary.entities.Author;
import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    private String name;

    private String surname;

    private String email;

    private Date dateOfRegistration;

    private Date birthday;
}
