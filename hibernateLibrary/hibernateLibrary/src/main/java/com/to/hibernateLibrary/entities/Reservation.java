package com.to.hibernateLibrary.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "reservation_id", unique = true)
    private Long reservationId;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn (name = "book_id" , nullable = false)
    private Book bookId;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn (name = "user_id", nullable = false)
    private User userId;

    @Column(name = "date_of_reservation", nullable = false)
    private Date dateOfReservation;
}
