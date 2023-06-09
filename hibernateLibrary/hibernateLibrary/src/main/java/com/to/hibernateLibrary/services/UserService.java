package com.to.hibernateLibrary.services;


import com.to.hibernateLibrary.dto.UserCriteriaDto;
import com.to.hibernateLibrary.dto.UserDto;
import com.to.hibernateLibrary.entities.User;
import com.to.hibernateLibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.Predicate;
import java.net.URI;
import java.util.*;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> {
            UserDto userDto = this.entityToDto(user);
            userDtoList.add(userDto);
        });

        return userDtoList;
    }

    public UserDto findById(Long userId){

        return this.entityToDto(userRepository
                .findById(userId).orElseThrow(() -> new RuntimeException("User " + userId + " not found")));

    }

    public List<UserDto> findUserBySurname(String surname){
        List<User> userList = userRepository.findAllBySurname(surname);
        List<UserDto> reservationDtoList = new ArrayList<>();
        userList.forEach(user -> {
            UserDto userDto = this.entityToDto(user);
            reservationDtoList.add(userDto);
        });

        return reservationDtoList;
    }

    public List<UserDto> getAllUsers(UserCriteriaDto criteriaDto) {
        List<User> userList = this.userRepository.findAll(hasValidCriteria(criteriaDto));
        List<UserDto> reservationDtoList = new ArrayList<>();
        userList.forEach(user -> {
            UserDto userDto = this.entityToDto(user);
            reservationDtoList.add(userDto);
        });

        return reservationDtoList;
    }

    private Specification<User> hasValidCriteria(UserCriteriaDto dto) {

        return (rt, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getEmail() != null) {
                predicates.add(cb.and(cb.like(rt.get("email"), "%" + dto.getEmail() + "%")));
            }

           /* if (dto.getStatus() != null)
                predicates.add(cb.and(cb.equal(rt.get("active"), dto.getStatus())));

    /**
    * NB: questi due predicate servono per selezionare gli utenti che hanno la password valorizzata (colonna password) e l'otp verificato (colonna otp_code_verified) a DB.
    * Questi devono essere sempre inseriti nella query di selezione.
    */
          /*  predicates.add(cb.and(cb.isNotNull(rt.get("password")),cb.notEqual(rt.get("password"), "")));

            predicates.add(cb.and(cb.isTrue(rt.get("otpCodeVerified"))));*/

            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }

    public ResponseEntity<UserDto> save(User user){
        UserDto userDto = this.entityToDto(userRepository.save(user));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .buildAndExpand(userDto.getUserId()).toUri();

        return ResponseEntity.created(location).build();


    }

    public ResponseEntity<UserDto> update(Long userId, User userDetails) {
        UserDto userDto = this.findById(userId);

        userDto.setName(userDetails.getName());
        userDto.setSurname(userDetails.getSurname());
        userDto.setEmail(userDetails.getEmail());
        userDto.setDateOfRegistration(userDetails.getDateOfRegistration());
        userDto.setBirthday(userDetails.getBirthday());

        final UserDto updatedUser = this.entityToDto(userRepository.save(dtoToEntity(userDto)));

        return ResponseEntity.ok(updatedUser);
    }

    public Map<String, Boolean>  delete(Long userId){

        UserDto userDto = this.findById(userId);

        userRepository.delete(dtoToEntity(userDto));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    private UserDto entityToDto(User user) {
        return UserDto.builder().userId(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .dateOfRegistration(user.getDateOfRegistration())
                .birthday(user.getBirthday())
                .build();
    }

    public static User dtoToEntity(UserDto userDto) {
        User user = new User();

        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setDateOfRegistration(userDto.getDateOfRegistration());
        user.setBirthday(userDto.getBirthday());

        return user;
    }

}
