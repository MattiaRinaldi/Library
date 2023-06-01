package com.to.hibernateLibrary.repositories;


import com.to.hibernateLibrary.entities.Book;
import com.to.hibernateLibrary.entities.Reservation;
import com.to.hibernateLibrary.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByBookId(Book bookId);
    List<Reservation> findAllByUserId(User userId);

}
