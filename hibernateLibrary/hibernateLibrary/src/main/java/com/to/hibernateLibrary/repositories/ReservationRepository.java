package com.to.hibernateLibrary.repositories;


import com.to.hibernateLibrary.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //List<Reservation> findAllByBook(Book bookId);
    //List<Reservation> findAllByUser(User user);
    //void deleteAll(Reservation reservation);
}
