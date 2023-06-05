package com.to.hibernateLibrary.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCriteriaDto {

    private String genre;

    private Long year1;

    private Long year2;
}
