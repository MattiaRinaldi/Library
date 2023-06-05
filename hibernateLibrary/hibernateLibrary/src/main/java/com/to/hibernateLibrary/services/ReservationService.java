package com.to.hibernateLibrary.services;


import com.to.hibernateLibrary.dto.BookDto;
import com.to.hibernateLibrary.dto.ReservationDto;
import com.to.hibernateLibrary.dto.UserDto;
import com.to.hibernateLibrary.entities.Reservation;
import com.to.hibernateLibrary.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Date;
import java.net.URI;
import java.util.*;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    public List<ReservationDto> findAll(){
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> ReservationDtoList = new ArrayList<>();
        reservationList.forEach(reservation -> {
            ReservationDto reservationDto = this.entityToDto(reservation);
            ReservationDtoList.add(reservationDto);
        });

        return ReservationDtoList;
    }

    public ReservationDto findById(Long reservationId){

        return this.entityToDto(reservationRepository
                .findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation " + reservationId + " not found")));


    }

    public List<ReservationDto> findReservationByBook(Long bookId){
        BookDto bookDto = bookService.findById(bookId);
        List<Reservation> reservationList = reservationRepository.findAllByBookId(BookService.dtoToEntity(bookDto));

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservationList.forEach(reservation -> {
            ReservationDto reservationDto = this.entityToDto(reservation);
            reservationDtoList.add(reservationDto);
        });

        return reservationDtoList;
    }

    public List<ReservationDto> findReservationByUser(Long userId){
        UserDto userDto = userService.findById(userId);
        List<Reservation> reservationList = reservationRepository.findAllByUserId(UserService.dtoToEntity(userDto));

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservationList.forEach(reservation -> {
            ReservationDto reservationDto = this.entityToDto(reservation);
            reservationDtoList.add(reservationDto);
        });

        return reservationDtoList;
    }

    public List<Date> findDateOfReservationByUserId(Long userId){
        List<ReservationDto> reservationDtoList = findReservationByUser(userId);
        List<Date> reservationDate = new ArrayList<>();
        reservationDtoList.forEach(reservationDto -> {
            reservationDate.add(reservationDto.getDateOfReservation());
        });
        return reservationDate;
    }

    public ResponseEntity<ReservationDto> save(Reservation reservation){
        ReservationDto reservationDto = this.entityToDto(reservationRepository.save(reservation));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .buildAndExpand(reservationDto.getReservationId()).toUri();

        return ResponseEntity.created(location).build();

    }

    public ResponseEntity<ReservationDto> update(Long reservationId, Reservation reservationDetails) {
        ReservationDto reservationDto = this.findById(reservationId);

        reservationDto.setBook(reservationDetails.getBookId());
        reservationDto.setUser(reservationDetails.getUserId());
        reservationDto.setDateOfReservation(reservationDetails.getDateOfReservation());

        final ReservationDto updatedReservation = this.entityToDto(reservationRepository.save(dtoToEntity(reservationDto)));

        return ResponseEntity.ok(updatedReservation);
    }

    public Map<String, Boolean>  delete(Long reservationId){

        ReservationDto reservationDto = this.findById(reservationId);

        reservationRepository.delete(dtoToEntity(reservationDto));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    public Map<String, Boolean>  deleteReservationByUser(Long userId){

        List<ReservationDto> reservationList = this.findReservationByUser(userId);
        reservationList.forEach(reservation -> {
            Reservation reservationTemp = this.dtoToEntity(reservation);
            reservationRepository.delete(reservationTemp);
        });

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    private ReservationDto entityToDto(Reservation reservation) {
        return ReservationDto.builder().reservationId(reservation.getReservationId())
                .book(reservation.getBookId())
                .user(reservation.getUserId())
                .dateOfReservation(reservation.getDateOfReservation())
                .build();
    }

    private Reservation dtoToEntity(ReservationDto ReservationDto) {
        Reservation reservation = new Reservation();

        reservation.setReservationId(ReservationDto.getReservationId());
        reservation.setBookId(ReservationDto.getBook());
        reservation.setUserId(ReservationDto.getUser());
        reservation.setDateOfReservation(ReservationDto.getDateOfReservation());

        return reservation;
    }

}
