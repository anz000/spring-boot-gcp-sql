package com.myapp.user.controller;

import com.myapp.user.entity.User;
import com.myapp.user.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.createNewUser(newUser);
    }

    @GetMapping
    public List findAll(){
        return userService.getAllUsers();
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
        return userService.deleteUserById(id);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User editUser){
        return userService.updateUserById(id,editUser);
    }
}
