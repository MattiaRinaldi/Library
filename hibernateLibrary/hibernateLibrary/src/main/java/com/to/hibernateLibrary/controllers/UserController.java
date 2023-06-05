package com.to.hibernateLibrary.controllers;

import com.to.hibernateLibrary.dto.UserCriteriaDto;
import com.to.hibernateLibrary.dto.UserDto;
import com.to.hibernateLibrary.entities.User;
import com.to.hibernateLibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // GET method to fetch all users
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }


    // GET method to fetch user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id)
            throws Exception {
        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok().body(userDto);
    }

    // GET method to fetch all users by surname
    @GetMapping("/user/{surname}")
    public List<UserDto> getAllUsersBySurname(@PathVariable String surname){
        return userService.findUserBySurname(surname);
    }

    // POST method to create a user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // POST method to fetch all users by a specific criteria
    @PostMapping("/list")
    public ResponseEntity<List<UserDto>> listUsers(@RequestBody UserCriteriaDto criteriaDto) {
        return ResponseEntity.ok(this.userService.getAllUsers(criteriaDto));
    }

    // PUT method to update a user's details
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id, @RequestBody User userDetails
    ) throws Exception {
        return userService.update(id,userDetails);
    }

    // DELETE method to delete a user
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable Long id) throws Exception {
        return userService.delete(id);
    }
}