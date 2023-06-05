package com.to.hibernateLibrary.controllers;

import com.to.hibernateLibrary.dto.ReservationDto;
import com.to.hibernateLibrary.entities.Reservation;
import com.to.hibernateLibrary.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    // GET method to fetch all reservations
    @GetMapping("/all")
    public List<ReservationDto> getAllReservations() {
        return reservationService.findAll();
    }

    // GET method to fetch reservation by id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        ReservationDto reservationDto = reservationService.findById(id);

        return ResponseEntity.ok().body(reservationDto);
    }

    // GET method to fetch all reservations by book
    @GetMapping("/book/{bookId}")
    public List<ReservationDto> getAllReservationByBookId(@PathVariable Long bookId){
        return reservationService.findReservationByBook(bookId);
    }

    // GET method to fetch all reservations by user
    @GetMapping("/user/{userId}")
    public List<ReservationDto> getAllReservationByUserId(@PathVariable Long userId){
        return reservationService.findReservationByUser(userId);
    }

    // GET method to fetch all date of reservations by user
    @GetMapping("/date/{userId}")
    public List<Date> getAllDateByUserId(@PathVariable Long userId){
        return reservationService.findDateOfReservationByUserId(userId);
    }

    // POST method to create a reservation
    @PostMapping("/")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    // PUT method to update a reservation's details
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(
            @PathVariable Long id, @RequestBody Reservation reservationDetails
    ) {
        return reservationService.update(id,reservationDetails);
    }

    // DELETE method to delete a reservation
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteReservation(@PathVariable Long id) {
        return reservationService.delete(id);
    }

    // DELETE method to delete a reservation
    @DeleteMapping("/all/{userId}")
    public Map<String, Boolean> deleteReservationByUserId(@PathVariable Long userId) throws Exception {
        return reservationService.deleteReservationByUser(userId);
    }
}
