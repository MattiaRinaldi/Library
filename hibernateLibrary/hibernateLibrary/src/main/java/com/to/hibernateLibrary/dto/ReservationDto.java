package com.to.hibernateLibrary.dto;


import com.to.hibernateLibrary.entities.Book;
import com.to.hibernateLibrary.entities.User;
import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {

    private Long reservationId;

    private Book book;

    private User user;

    private Date dateOfReservation;

}
